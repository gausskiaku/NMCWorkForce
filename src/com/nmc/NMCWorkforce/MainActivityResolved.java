package com.nmc.NMCWorkforce;

import java.lang.ref.WeakReference;

import com.list.WebServiceReAssign;
import com.list.WebServiceResolved;
import com.nmc.NMCWorkforce.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityResolved extends AppBaseActivity {
	
	EditText SolRemarks;
	EditText txtRsolutionMeth;
	Spinner spRootCause;
	Button bt;
	String choixRootCause;
	String valeurID = "";
	private ProgressDialog loading;
	String resultat;
	String[] rootCause;
	String NAME_OPS = "";
	AsyncCallWS task = null;
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_resolved);
		registerBaseActivityReceiver();
		setTitle("Resolve TT");
		SolRemarks = (EditText) findViewById(R.id.txtSolutionsRemarkResolved);
		txtRsolutionMeth = (EditText) findViewById(R.id.txtResolutionMethResolved);
		spRootCause = (Spinner) findViewById(R.id.spRootCause);
		bt = (Button) findViewById(R.id.saveResolved);
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
		
		setFont(SolRemarks);
		setFont(txtRsolutionMeth);
		setFont(bt);
		//setFont(spRootCause);
		
		
		rootCause = getResources().getStringArray(R.array.root_cause);
		ArrayAdapter<String> adpter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rootCause);
		spRootCause.setAdapter(adpter);
		
		spRootCause.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choixRootCause = parent.getItemAtPosition(position).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
			bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
			   if(ninfo != null && ninfo.isConnected()){
				   if(spRootCause.getSelectedItemPosition() == 0 || txtRsolutionMeth.length() < 1 || SolRemarks.length() < 1){
						Toast.makeText(getBaseContext(), "Please complete all fields important", Toast.LENGTH_LONG).show();
					} else {
					task = new AsyncCallWS(MainActivityResolved.this);
			        //Call execute 
			        task.execute();
					}
				}
				else {
					Toast.makeText(MainActivityResolved.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
				}
			}
		});
			task = (AsyncCallWS) getLastNonConfigurationInstance();
			
			if(task != null){
				// On lie l'activité à l'AsyncTask
				task.link(this);
				}
	}
	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub
		return task;
	}

	
WebServiceResolved service = new WebServiceResolved();

	private class AsyncCallWS extends AsyncTask<String, Void, Boolean> {
		private WeakReference<MainActivityResolved> mActivity = null;
		public AsyncCallWS(MainActivityResolved activityResolved) {
			link(activityResolved);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			Bundle id = getIntent().getExtras();
			if(id != null){	
				valeurID = id.getString("ID_TICKET_SENDbyDETAIL");
				NAME_OPS = id.getString("NAME_OPS");
			}
			try {
				resultat = service.ResolvedTicket("ticketResolved", valeurID, NAME_OPS, choixRootCause, txtRsolutionMeth.getText().toString(), SolRemarks.getText().toString());
			return true;
			} catch (Exception e) {
			return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (mActivity.get() != null) {
				if(result){
					try {
						txtRsolutionMeth.setText("");
						SolRemarks.setText("");
						loading.dismiss();
						Toast.makeText(mActivity.get(), resultat + " Id : "+ valeurID , Toast.LENGTH_LONG).show();
						if (!resultat.isEmpty()){
						Intent intent = new Intent(MainActivityResolved.this, MainActivityList.class);
						intent.putExtra("NAME_OPS", NAME_OPS);
						startActivityForResult(intent, 0);
						finish();
						}
					} catch (Exception e) {
						loading.dismiss();
					}
					
				} else {
					Toast.makeText(mActivity.get(), "Loading data failure", Toast.LENGTH_LONG).show();
				}
			}
		}
		
		@Override
		protected void onPreExecute() {
			if(mActivity.get() != null){
			loading = new ProgressDialog(MainActivityResolved.this); 
            loading.setMessage("Resolution in progress..."); 
            loading.setTitle("Loading..."); 
            loading.show();
            }
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		public void link (MainActivityResolved activityResolved) {
			mActivity = new WeakReference<MainActivityResolved>(activityResolved);
			}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_activity_resolved, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.logout_Resolved) {
		Intent intent = new Intent(MainActivityResolved.this, MainActivityUser.class);
		startActivity(intent);
		finish();
		} else if(item.getItemId() == R.id.reset_Resolved){
			SolRemarks.setText("");
			txtRsolutionMeth.setText("");
			spRootCause.setSelection(0);
		}
		return false;	
	}
	
	public void setFont(TextView textView) {
	       
        try {
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + "segoe.ttf");
            textView.setTypeface(typeface);
        } catch (Exception e) {
            Log.e("FONT"," not found", e);
        }
}
}
