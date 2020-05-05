package com.nmc.NMCWorkforce;

import java.lang.ref.WeakReference;

import com.list.WebServiceList;
import com.list.WebServiceUpdate;
import com.nmc.NMCWorkforce.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityUpdate extends AppBaseActivity {

	private ProgressDialog loading; 
	String resultat = null;
	TextView txt;
	Button bt;
	TextView txtActivityUpdate;
	String NAME_OPS;
	String ID_TICKET;
	AsyncCallWS task = null;
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_update);
		registerBaseActivityReceiver();
		setTitle("Update TT");
		txt = (TextView) findViewById(R.id.textPrincipalUpdate);
		txtActivityUpdate = (EditText) findViewById(R.id.txtActivityUpdate);
		bt = (Button) findViewById(R.id.btSaveUpdate);
	
		setFont(txtActivityUpdate);
		setFont(bt);
		
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
				if(ninfo != null && ninfo.isConnected()){

					if (txtActivityUpdate.length() < 1){
						Toast.makeText(MainActivityUpdate.this, "Please fill in the fields correctly", Toast.LENGTH_LONG).show();
					} else {
					task = new AsyncCallWS(MainActivityUpdate.this);
			        task.execute(); 
			        }
				}
				else {
					Toast.makeText(MainActivityUpdate.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
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
		return task;
	}
	
	
WebServiceUpdate service = new WebServiceUpdate();
	
	private class AsyncCallWS extends AsyncTask<String, Void, Boolean> {
		private WeakReference<MainActivityUpdate> mActivity = null; 
		
		public AsyncCallWS(MainActivityUpdate activityUpdate) {
			link(activityUpdate);
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			
			Bundle id = getIntent().getExtras();
			if(id != null){	
				ID_TICKET = id.getString("ID_TICKET_SENDbyDETAIL");
				NAME_OPS = id.getString("NAME_OPS"); 	
			}
			try {
				resultat = service.UpdateTicket("modifyActivity", ID_TICKET, txtActivityUpdate.getText().toString() + "("+ NAME_OPS +")", "entryId", "activity");
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
						if (!resultat.trim().isEmpty()){
							Toast.makeText(mActivity.get(), resultat, Toast.LENGTH_LONG).show();
							loading.dismiss();
							txtActivityUpdate.setText("");
							Intent intent = new Intent(MainActivityUpdate.this, MainActivityList.class);
							intent.putExtra("NAME_OPS", NAME_OPS);
							startActivityForResult(intent, RESULT_OK);
							finish();
							}
					} catch (Exception e) {
						loading.dismiss();
					}
				} else {
					Toast.makeText(mActivity.get(), "Echec de recuperation, veillez reouvrir l'option", Toast.LENGTH_LONG).show();
					loading.dismiss();
				}
			}	
		}

		@Override
		protected void onPreExecute() {
			if(mActivity.get() != null){
			loading = new ProgressDialog(MainActivityUpdate.this); 
            loading.setMessage("Update in progress... Please wait"); 
            loading.setTitle("Loading..."); 
            loading.show();
            }	
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		public void link (MainActivityUpdate activityUpdate) {
			mActivity = new WeakReference<MainActivityUpdate>(activityUpdate);
			}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_activity_update, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.logout_Update) {
		Intent intent = new Intent(MainActivityUpdate.this, MainActivityUser.class);
		startActivity(intent);
		finish();
		} else if(item.getItemId() == R.id.reset_Update){
			txtActivityUpdate.setText("");
		}
		
		return false;	
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			Bundle bundle = data.getExtras();
			NAME_OPS = bundle.getString("NAME_OPS");
			ID_TICKET = bundle.getString("ID_TICKET_SENDbyDETAIL");
		} 	
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
    	unRegisterBaseActivityReceiver();
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
