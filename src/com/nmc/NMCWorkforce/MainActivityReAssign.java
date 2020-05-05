package com.nmc.NMCWorkforce;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.list.WebServiceAllOps;
import com.list.WebServiceFullName;
import com.list.WebServiceList;
import com.list.WebServiceReAssign;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;



public class MainActivityReAssign extends AppBaseActivity {
	
	
	String[] table_user;
	String[] group_region;
	Spinner sp;
	String valeurID = "";
	Spinner sp1;
	EditText txtOps;
	Button bt;
	String resultat;
	String choixGroup;
	private ProgressDialog loading;
	private ProgressDialog loadingAllUser;
	private ProgressDialog loadingNameUser;
	String NAME_OPS = "";
	String FULL_NAME = "";
	String USER_NAME = "";
	AsyncCallWS task = null;
	AsyncCallWSFullName taskFullName = null;
	ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_re_assign);
		registerBaseActivityReceiver();
		setTitle("ReAssign to");
		sp = (Spinner) findViewById(R.id.sp);
		sp1 = (Spinner) findViewById(R.id.spinner1);
		txtOps =  (EditText) findViewById(R.id.txtOps);
		bt = (Button) findViewById(R.id.btReAssign);
		
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
		
		setFont(txtOps);
		setFont(bt);
		
	
		
		group_region = getResources().getStringArray(R.array.group_region);
		ArrayAdapter<String> adpter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, group_region);
		sp1.setAdapter(adpter);
		
		
		
		
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
				if(ninfo != null && ninfo.isConnected()){
					if(sp.getSelectedItemPosition() == 0 || sp1.getSelectedItemPosition() == 0){
						Toast.makeText(getBaseContext(), "Please complete all fields important", Toast.LENGTH_LONG).show();
					} else {
					task = new AsyncCallWS(MainActivityReAssign.this);
			        //Call execute 
			        task.execute();
			        }
				}
				else {
					Toast.makeText(MainActivityReAssign.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				USER_NAME = parent.getItemAtPosition(position).toString();
				
				if (sp.getSelectedItemPosition() == 0){
					txtOps.setText(""); 
				}else{
				taskFullName = new AsyncCallWSFullName(MainActivityReAssign.this);
		        //Call execute 
		        taskFullName.execute();
		        }
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		AsyncCallWSAllUser taskAllUser = new AsyncCallWSAllUser();
        //Call execute 
        taskAllUser.execute();
	/*	sp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//AsyncCallWSAllUser taskAllUser = new AsyncCallWSAllUser();
		        //Call execute 
		       // taskAllUser.execute();
				
			}
		});   */
		
		
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choixGroup = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		task = (AsyncCallWS) getLastNonConfigurationInstance();
		if(task != null){
			// On lie l'activité à l'AsyncTask
			task.link(this);
			}
		
		taskFullName = (AsyncCallWSFullName) getLastNonConfigurationInstance();
		if(taskFullName != null){
			// On lie l'activité à l'AsyncTask
			taskFullName.link(this);
			}
		}
	
	public Object onRetainNonConfigurationInstance() {
		return task;
	};
	
	
WebServiceReAssign service = new WebServiceReAssign();
	
	private class AsyncCallWS extends AsyncTask<String, Void, Boolean> {
		private WeakReference<MainActivityReAssign> mActivity = null;
		public AsyncCallWS(MainActivityReAssign activityReAssign) {
			link(activityReAssign);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			Bundle id = getIntent().getExtras();
			if(id != null){	
				valeurID = id.getString("ID_TICKET_SENDbyDETAIL");
				if (NAME_OPS.isEmpty() || valeurID.isEmpty()){
				NAME_OPS = id.getString("NAME_OPS");}
			}
			try {
				resultat = service.ReAssignTicket("reAssign", valeurID, NAME_OPS, FULL_NAME, choixGroup);
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
						txtOps.setText("");
						loading.dismiss();
						Res();
					} catch (Exception e) {
						loading.dismiss();
					}
					
				} else {
					Toast.makeText(mActivity.get(), "Echec de recuperation", Toast.LENGTH_LONG).show();
				}
			}
		}

		

		@Override
		protected void onPreExecute() {
			if(mActivity.get() != null){
			loading = new ProgressDialog(MainActivityReAssign.this); 
            loading.setMessage("Current reassignment..."); 
            loading.setTitle("Loading..."); 
            loading.show();}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		public void link (MainActivityReAssign activityReAssign) {
			mActivity = new WeakReference<MainActivityReAssign>(activityReAssign);
			}
	}
	
	private void Res() {
		try {
			Toast.makeText(this, resultat + " Id : "+ valeurID , Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(MainActivityReAssign.this, MainActivityList.class);
			intent.putExtra("NAME_OPS", NAME_OPS);
			startActivityForResult(intent, 0);
			finish();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_activity_re_assign, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.logout_ReAssign) {
		Intent intent = new Intent(MainActivityReAssign.this, MainActivityUser.class);
		startActivity(intent);
		finish();
		} else if (item.getItemId() == R.id.reset_ReAssign){
			sp.setSelection(0);
			sp1.setSelection(0);
			txtOps.setText("");
			
		}
		return false;	
	}
	
	WebServiceAllOps serviceAllUser = new WebServiceAllOps();
	
	private class AsyncCallWSAllUser extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			
				table_user = serviceAllUser.getAllUser("getAllUser");
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//tv.setText(fahren + "° F");
			//txt1.setText(result);
			
			aaa(table_user);
			loadingAllUser.dismiss();
			sp1.setSelection(0);
			sp.setSelection(0);
		}

		@Override
		protected void onPreExecute() {
			loadingAllUser = new ProgressDialog(MainActivityReAssign.this); 
			loadingAllUser.setMessage("Loading user list, please wait..."); 
			loadingAllUser.setTitle("Loading..."); 
			loadingAllUser.show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}
	
	

	public void aaa (String[] tableau){
		
		List<String> liste = new ArrayList<String>();
		
		 liste.add("Choose the User");
		for (int i = 0; i < tableau.length; i++) {
			
			try {
				// get Description
				String[] description = null;
				String[] Alldescription = tableau[i].split(";");
				
				try {
					description = Alldescription[0].split("=");
				} catch (Exception e) {
					description[1] = "";
				}
				
				
			//	Log.e("Gauss description"," User " + description[1]);
			
				
			//	map.put("IMAGE", String.valueOf(R.drawable.logovoda));
				
				liste.add(description[1]);
				
			} catch (Exception e) {
				Toast.makeText(MainActivityReAssign.this, "Non traiter " + e.getMessage() , Toast.LENGTH_LONG).show();
				
			}
			
				
		}
		
		
		String[] ss = new String[] {"text1","text2"};
		int[] s = new int[] {android.R.id.text1, android.R.id.text2};
		
		ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, liste);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);
	}
	
	
WebServiceFullName serviceFullName = new WebServiceFullName();
	
	private class AsyncCallWSFullName extends AsyncTask<String, Void, Boolean> {
		private WeakReference<MainActivityReAssign> mActivity = null;
		public AsyncCallWSFullName(MainActivityReAssign activityReAssign) {
			link(activityReAssign);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			try {
				FULL_NAME = serviceFullName.getFullName("getUser", USER_NAME);
				return true;
			} catch (Exception e) {
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (mActivity.get() != null) {
				if(result){
					loadingNameUser.dismiss();
					txtOps.setText(FULL_NAME);
				} else {
					Toast.makeText(mActivity.get(), "Echec de recuperation", Toast.LENGTH_LONG).show();
				}
			}
			
		}

		@Override
		protected void onPreExecute() {
			if(mActivity.get() != null){
			loadingNameUser = new ProgressDialog(MainActivityReAssign.this); 
			loadingNameUser.setMessage("Loading full name ops, please wait..."); 
			loadingNameUser.setTitle("Loading..."); 
			loadingNameUser.show();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

		public void link (MainActivityReAssign activityReAssign) {
			mActivity = new WeakReference<MainActivityReAssign>(activityReAssign);
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
