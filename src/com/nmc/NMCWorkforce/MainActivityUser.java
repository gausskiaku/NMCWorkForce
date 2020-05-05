package com.nmc.NMCWorkforce;

import java.lang.ref.WeakReference;

import com.list.WebServiceUser;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityUser extends AppBaseActivity {

	TextView txtPrincipal;
	static EditText edtUser;
	static EditText edtPass;
	Button bt;
	static String resultat;
	private static ProgressDialog loading;
	static String NAME_OPS;
	static String MOTDEPASSE;
	static String LOGIN;
	AsyncCallWS task = null;
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_user);
		registerBaseActivityReceiver();
		setTitle("Login");
		txtPrincipal = (TextView) findViewById(R.id.titreAccueil);
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED)); // Color.rgb(241, 21, 62)
		
		edtUser =  (EditText) findViewById(R.id.edtUserName);
		edtPass = (EditText) findViewById(R.id.edtPassword);
		bt = (Button) findViewById(R.id.btLogin);
		
		setFont(txtPrincipal);
		setFont(edtUser);
		setFont(edtPass);
		
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
				if(ninfo != null && ninfo.isConnected()){
					if (edtUser.length() < 1 || edtPass.length() < 1){
						Toast.makeText(MainActivityUser.this, "Please fill in the fields correctly", Toast.LENGTH_LONG).show();
					} else {
					task = new AsyncCallWS(MainActivityUser.this);
					task.execute(); 
					}	
				}
				
				else {
					Toast.makeText(MainActivityUser.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
    	unRegisterBaseActivityReceiver();
	}
	
	static WebServiceUser connexion = new WebServiceUser();
	
	private class AsyncCallWS extends AsyncTask<String, Void, Boolean> {
		private WeakReference<MainActivityUser> mActivity = null;
		public AsyncCallWS(MainActivityUser activityUser) {
			link(activityUser);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			try {
				resultat = connexion.ConnexionRemedy("connexionRemedy", edtUser.getText().toString().trim(), edtPass.getText().toString().trim()); //(editText, "hello");
				NAME_OPS = resultat;
				LOGIN = edtUser.getText().toString();
				MOTDEPASSE = edtPass.getText().toString();
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
			loading = new ProgressDialog(MainActivityUser.this); 
            loading.setMessage("Checking..."); 
            loading.setTitle("Loading..."); 
            loading.show();}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		
		public void link (MainActivityUser activityUser) {
			mActivity = new WeakReference<MainActivityUser>(activityUser);
		}
	}
	
	public void res(){
		try {
			if(resultat.length() > 25){
				Intent intentError = new Intent(MainActivityUser.this, ErrorUser.class);
				startActivity(intentError);	
			} else {
			Intent intentOk = new Intent(MainActivityUser.this, MainActivityList.class);
			intentOk.putExtra("NAME_OPS", NAME_OPS);
			intentOk.putExtra("LOGIN", LOGIN);
			intentOk.putExtra("MOTDEPASSE", MOTDEPASSE);
			
			
			startActivityForResult(intentOk, RESULT_OK);
			finish();
			}
		} catch (Exception e) {
		//	Toast.makeText(MainActivityUser.this, "Failure recovery... Try again", Toast.LENGTH_LONG).show();
		}
		
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
