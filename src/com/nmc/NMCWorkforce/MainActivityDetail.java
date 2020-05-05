package com.nmc.NMCWorkforce;

import java.lang.ref.WeakReference;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.list.WebServiceList;
import com.list.WebServiceUpdate;

public class MainActivityDetail extends AppBaseActivity{
//  <!--   android:uiOptions="splitActionBarWhenNarrow" --> 
	TextView txtTitre;
	TextView textDetail;
	String resultat = "";
	String resultatAccept;
	AsyncCallWS task = null;
	AsyncCallWSAccept taskAccept = null;
	private ProgressDialog loading;
	String ID_TICKET_SENDbyDETAIL;
	String NAME_OPS;
	ActionBar actionBar;
	static String SOURCE;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_detail);
		registerBaseActivityReceiver();
		setTitle("TT Detail");
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
		
		actionBar.show();
		
		textDetail = (TextView) findViewById(R.id.textDetail);
		txtTitre = (TextView) findViewById(R.id.titreDetail);
		ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
		if(ninfo != null && ninfo.isConnected()){
			task = new AsyncCallWS(MainActivityDetail.this);
	        //Call execute 
	        task.execute();	
		}
		else {
			Toast.makeText(MainActivityDetail.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
		}
		
		task = (AsyncCallWS) getLastNonConfigurationInstance();
		taskAccept = (AsyncCallWSAccept) getLastNonConfigurationInstance();
		
		if(task != null){
			// On lie l'activité à l'AsyncTask
			task.link(this);
			}
		
		if(taskAccept != null){
			taskAccept.link(this);
			}
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub
		return task;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	
		//getMenuInflater().inflate(R.menu.detail_bis, menu);
		getMenuInflater().inflate(R.menu.menu, menu);
	    
	//	MenuInflater menuInflater = getMenuInflater();
	//	menuInflater.inflate(R.menu.menu, menu);
	//	onPrepareOptionsMenu(menu);
		Bundle id = getIntent().getExtras();
		if(id != null){	
			SOURCE = id.getString("SOURCE");
		}
		if(SOURCE.length() < 5){
			return true;
		} else {
			return false;
		}
		
	};
	
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.option:
			Bundle id = getIntent().getExtras();
			if(id != null){	
				NAME_OPS = id.getString("NAME_OPS");
			}
			
			// Toast.makeText(getBaseContext(), "NAME ===> " + NAME_OPS, Toast.LENGTH_LONG).show();
			return true;
		case R.id.update :
			Intent forUpdate = new Intent(MainActivityDetail.this, MainActivityUpdate.class);
			forUpdate.putExtra("ID_TICKET_SENDbyDETAIL", ID_TICKET_SENDbyDETAIL);
			forUpdate.putExtra("NAME_OPS", NAME_OPS);
			startActivityForResult(forUpdate, RESULT_OK);
			
			return true;
			
		case R.id.accept :
			
				ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
				if(ninfo != null && ninfo.isConnected()){
					taskAccept = new AsyncCallWSAccept(MainActivityDetail.this);
					taskAccept.execute();
				}
				else {
					Toast.makeText(MainActivityDetail.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
				}
			
			
			return true;
			
		case R.id.reAssign :
			Intent reAssign = new Intent(MainActivityDetail.this, MainActivityReAssign.class);
			reAssign.putExtra("ID_TICKET_SENDbyDETAIL", ID_TICKET_SENDbyDETAIL);
			reAssign.putExtra("NAME_OPS", NAME_OPS);
			startActivity(reAssign);
			return true;
			
		case R.id.resolve :
			Intent resolved = new Intent(MainActivityDetail.this, MainActivityResolved.class);
			resolved.putExtra("ID_TICKET_SENDbyDETAIL", ID_TICKET_SENDbyDETAIL);
			resolved.putExtra("NAME_OPS", NAME_OPS);
			startActivity(resolved);
			return true;
			
		case R.id.quitter:
			finish();
			return true;
		}
		
		return false;	
	};
	
	WebServiceList service = new WebServiceList();
	
	private class AsyncCallWS extends AsyncTask<String, Void, Boolean> {
		private WeakReference<MainActivityDetail> mActivity = null;
		public AsyncCallWS(MainActivityDetail activityDetail) {
			link(activityDetail);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			String valeurID = "";
			String NAME_OPS = "";
			Bundle id = getIntent().getExtras();
			if(id != null){	
				valeurID = id.getString("ID_TICKET");
				NAME_OPS = id.getString("NAME_OPS");
				SOURCE = id.getString("SOURCE");
			}
			ID_TICKET_SENDbyDETAIL = valeurID;
			try {
				String[] result = service.getTicketbyIndividual("getTicketbyID", valeurID, "Id");
				resultat = result[0];
				return true;
			} catch (Exception e) {
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (mActivity.get() != null) {
				if(result){
					textDetail.setMovementMethod(new ScrollingMovementMethod());
					textDetail.setText(resultat.toString());
					loading.dismiss();
				} else {
					Toast.makeText(mActivity.get(), "Echec de recuperation", Toast.LENGTH_LONG).show();
				}
			}
			
		}

		@Override
		protected void onPreExecute() {
			if(mActivity.get() != null){
				loading = new ProgressDialog(MainActivityDetail.this); 
	            loading.setMessage("Recovery of detail in the Server... Please wait"); 
	            loading.setTitle("Loading"); 
	            loading.show();	
			}	
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		public void link (MainActivityDetail activityDetail) {
			mActivity = new WeakReference<MainActivityDetail>(activityDetail);
			}

	}
	
	private class AsyncCallWSAccept extends AsyncTask<String, Void, Boolean>{

		private WeakReference<MainActivityDetail> mActivity = null;
		
		public AsyncCallWSAccept(MainActivityDetail activityDetail) {
			link(activityDetail);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			String valeurID = "";
			String NAME_OPS = "";
			Bundle id = getIntent().getExtras();
			if(id != null){	
				valeurID = id.getString("ID_TICKET");
				NAME_OPS = id.getString("NAME_OPS");
			}
					try {
						resultatAccept = WebServiceUpdate.UpdateTicket("modifyActivity", valeurID, "TT Accepted by "+ NAME_OPS + " and Resolution Process in Progress", "entryId", "activity");
						return true;
					} catch (Exception e) {
						return false;
					}	
			
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if (mActivity.get() != null) {
				if(result){
					res();
					loading.dismiss();
				} else {
					Toast.makeText(mActivity.get(), "Echec de recuperation", Toast.LENGTH_LONG).show();
				}
			}
		}
		
		@Override
		protected void onPreExecute() {
			if(mActivity.get() != null){
			loading = new ProgressDialog(MainActivityDetail.this); 
            loading.setMessage("Ongoing treatment..."); 
            loading.setTitle("Loading..."); 
            loading.show();
			}
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		public void link (MainActivityDetail activityDetail) {
			mActivity = new WeakReference<MainActivityDetail>(activityDetail);
			}
		}
	
	private void res(){
		Toast.makeText(MainActivityDetail.this, resultatAccept, Toast.LENGTH_LONG).show();
		
	}
	
	
	@Override
		public boolean onPrepareOptionsMenu(Menu menu) {
			//return true;
		
			//Toast.makeText(getBaseContext(), resultat, Toast.LENGTH_LONG).show();
		if (resultat.indexOf("WIP") > -1){
			MenuItem item = menu.findItem(R.id.accept);
			item.setEnabled(false);
			
		}
		
			if(resultat.indexOf("Resolved") > -1 || resultat.indexOf("Closed") > -1){
			
			MenuItem itemReAssign = menu.findItem(R.id.option);
			itemReAssign.setVisible(false);
		}
		
		if(resultat.indexOf("Assigned") > -1){
			MenuItem itemReAssign = menu.findItem(R.id.option);
			itemReAssign.setEnabled(true);
		}
		
		if (resultat.isEmpty()){
			return true;
		} else{ 
		return true;
		}  
	} 
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
    	unRegisterBaseActivityReceiver();
	}

	
	
}
