package com.nmc.NMCWorkforce;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import com.list.TicketInfo;
import com.list.WebServiceList;
import com.list.WebServiceNameGroup;
import com.nmc.NMCWorkforce.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityList extends AppBaseActivity {

	String table_ticket[] = null;
	String table_Closed[] = null;
	AsyncCallWS task = null;
	AsyncCallWSClosed taskClosed= null;
	AsyncCallWSNameGroup taskNameGroup = null;
	//TextView txt1;
	Button bt_Assign;
	Button bt_Closed, bt_Create, bt_Group;
	ListView lw;
	String NAME_OPS;
	static String MOTDEPASSE;
	static String LOGIN;
	TextView aucunTicket;
	static String SOURCE = "";
	private ProgressDialog loading; 
	ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map;
	String ID_TICKET;
	ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_list);
		registerBaseActivityReceiver();
		setTitle("TTs");
		lw = (ListView) findViewById(R.id.listView1);
		//txt1 = (TextView) findViewById(R.id.textView1);
		aucunTicket = (TextView) findViewById(R.id.aucunTicket);
		
		bt_Assign = (Button) findViewById(R.id.bt_Assign);
		bt_Closed = (Button) findViewById(R.id.bt_Closed);
		bt_Group = (Button) findViewById(R.id.bt_Group);
		bt_Create = (Button) findViewById(R.id.bt_Create);
		
	//	setFont(lw);
		setFont(bt_Assign);
		setFont(bt_Closed);
		setFont(bt_Group);
		setFont(bt_Create);
		setFont(aucunTicket);
	//	setFont(txt1);
		
		
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable( new ColorDrawable(Color.RED));
		
		
		bt_Assign.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
				if(ninfo != null && ninfo.isConnected()){
					
					setTitle("Assigned TTs");
					 
					try {
						Bundle id = getIntent().getExtras();
						if(id != null){	
							NAME_OPS = id.getString("NAME_OPS");
							MOTDEPASSE = id.getString("MOTDEPASSE");
							LOGIN = id.getString("LOGIN");
						}
						
						if (NAME_OPS == null){
							Toast.makeText(MainActivityList.this, "Please wait, the server is busy...", Toast.LENGTH_LONG).show();
						} else {
					//	Toast.makeText(MainActivityList.this, "Welcome : " + NAME_OPS, Toast.LENGTH_SHORT).show();
						task = new AsyncCallWS(MainActivityList.this);
		                //Call execute 
		                task.execute();
						}
					} catch (NullPointerException e) {
						Toast.makeText(MainActivityList.this, "Please wait, the server is busy...", Toast.LENGTH_LONG).show();
					}
				}
				else {
					Toast.makeText(MainActivityList.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
		bt_Closed.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
				if(ninfo != null && ninfo.isConnected()){
					setTitle("Resolved or Closed TTs");
					try {
						Bundle id = getIntent().getExtras();
						if(id != null){	
							NAME_OPS = id.getString("NAME_OPS");
							MOTDEPASSE = id.getString("MOTDEPASSE");
							LOGIN = id.getString("LOGIN");
						}
						if (NAME_OPS == null){
							Toast.makeText(MainActivityList.this, "Please wait, the server is busy...", Toast.LENGTH_LONG).show();
						} else {
					//	Toast.makeText(MainActivityList.this, "You are : "+ NAME_OPS, Toast.LENGTH_SHORT).show();
						taskClosed = new AsyncCallWSClosed(MainActivityList.this);
		                //Call execute 
		                taskClosed.execute();
		                }
					} catch (Exception e) {
						Toast.makeText(MainActivityList.this, "Please wait, the server is busy...", Toast.LENGTH_LONG).show();
					}	
					
				}
				else {
					Toast.makeText(MainActivityList.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		bt_Group.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
				if(ninfo != null && ninfo.isConnected()){
					 
					try {
						Bundle id = getIntent().getExtras();
						if(id != null){	
							NAME_OPS = id.getString("NAME_OPS");
							MOTDEPASSE = id.getString("MOTDEPASSE");
							LOGIN = id.getString("LOGIN");
						}
						
						if (NAME_OPS == null){
							Toast.makeText(MainActivityList.this, "Please wait, the server is busy...", Toast.LENGTH_LONG).show();
						} else {
				//		Toast.makeText(MainActivityList.this, "Name : "+ NAME_OPS, Toast.LENGTH_LONG).show();
						taskNameGroup = new AsyncCallWSNameGroup(MainActivityList.this);
		                //Call execute 
		                taskNameGroup.execute();
						}
					} catch (NullPointerException e) {
						Toast.makeText(MainActivityList.this, "Please wait, the server is busy...", Toast.LENGTH_LONG).show();
					}
				}
				else {
					Toast.makeText(MainActivityList.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		bt_Create.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle id = getIntent().getExtras();
				if(id != null){	
					NAME_OPS = id.getString("NAME_OPS");
					MOTDEPASSE = id.getString("MOTDEPASSE");
					LOGIN = id.getString("LOGIN");
				}
				
			//	Toast.makeText(getBaseContext(), LOGIN + "  " + MOTDEPASSE, Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(MainActivityList.this, MainActivitySave.class);
				intent.putExtra("NAME_OPS", NAME_OPS);
				intent.putExtra("MOTDEPASSE", MOTDEPASSE);
				intent.putExtra("LOGIN", LOGIN);
				
				startActivityForResult(intent, RESULT_OK);
				
			}
		});
		
		
		lw.setOnItemClickListener( new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
					HashMap<String, String> map = (HashMap<String, String>) lw.getItemAtPosition(position);
					
					// Get ID
					ID_TICKET = map.get("ID");
					
					
					// Get NAME OPS
					Bundle idInt = getIntent().getExtras();
					if(idInt != null){	
						NAME_OPS = idInt.getString("NAME_OPS");
						MOTDEPASSE = idInt.getString("MOTDEPASSE");
						LOGIN = idInt.getString("LOGIN");
						
					}
					
					Intent forMainDetail = new Intent(MainActivityList.this, MainActivityDetail.class);
					forMainDetail.putExtra("ID_TICKET", ID_TICKET);
					forMainDetail.putExtra("NAME_OPS", NAME_OPS);
					forMainDetail.putExtra("SOURCE", SOURCE);
					startActivity(forMainDetail);
						
					}
		});
		
		task = (AsyncCallWS) getLastNonConfigurationInstance();
		taskClosed = (AsyncCallWSClosed) getLastNonConfigurationInstance();
		taskNameGroup = (AsyncCallWSNameGroup) getLastNonConfigurationInstance();
		
		if(task != null){
			// On lie l'activité à l'AsyncTask
			task.link(this);
			}
		if(taskClosed != null){
			taskClosed.link(this);
		}
		
		if(taskNameGroup != null){
			taskNameGroup.link(this);
		}
	}
	@Override
	public Object onRetainNonConfigurationInstance() {
		try {
			return task;
		} catch (Exception e) {
			return table_Closed;
		}
	}
	
	
	
	// Tickets by Group
	String[] gp;
	
	WebServiceNameGroup group = new WebServiceNameGroup();
	
	private class AsyncCallWSNameGroup extends AsyncTask<String, Void, Boolean> {
		private WeakReference<MainActivityList> mActivity = null;
		
		public AsyncCallWSNameGroup(MainActivityList activityList) {
			link(activityList);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			try {
				Bundle id = getIntent().getExtras();
				if(id != null){	
					MOTDEPASSE = id.getString("MOTDEPASSE");
					LOGIN = id.getString("LOGIN");
				}
				//Toast.makeText(mActivity.get(), LOGIN + " Gauss " + MOTDEPASSE, Toast.LENGTH_LONG).show();
				gp = group.getGroup("getGroup", LOGIN, MOTDEPASSE);
				
				return true;
			} catch (Exception e) {
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (mActivity.get() != null) {
				if(result){					
					loading.dismiss();
					traitemntGroup();
					
				} else {
					Toast.makeText(mActivity.get(), "Probleme " + gp, Toast.LENGTH_LONG).show();
				}
				
			}
		}

		@Override
		protected void onPreExecute() {
			if(mActivity.get() != null){
			loading = new ProgressDialog(MainActivityList.this); 
            loading.setMessage("Getting all the groups to which you belong..."); 
            loading.setTitle("Loading..."); 
            loading.show();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		public void link (MainActivityList activityList) {
			mActivity = new WeakReference<MainActivityList>(activityList);
			}
	}
	
	
	private void traitemntGroup() {
		try {
			if(gp.toString().length() < 3){
				Toast.makeText(getBaseContext(), "Sorry, you belong to no group!", Toast.LENGTH_LONG).show();
			} else {
			Intent intentOk = new Intent(MainActivityList.this, MainActivityGroup.class);
			intentOk.putExtra("NAME_OPS", NAME_OPS);
			intentOk.putExtra("GROUP", gp);
			
			startActivityForResult(intentOk, RESULT_OK);
			}
		} catch (Exception e) {
		//	Toast.makeText(MainActivityUser.this, "Failure recovery... Try again", Toast.LENGTH_LONG).show();
		}

	}
	
	
	
	// Tickets Assigned
WebServiceList service = new WebServiceList();
	
	private class AsyncCallWS extends AsyncTask<String, Void, Boolean> {
		private WeakReference<MainActivityList> mActivity = null;
		
		public AsyncCallWS(MainActivityList activityList) {
			link(activityList);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			try {
				Bundle id = getIntent().getExtras();
				if(id != null){	
					NAME_OPS = id.getString("NAME_OPS");
					MOTDEPASSE = id.getString("MOTDEPASSE");
					LOGIN = id.getString("LOGIN");
				}
					table_ticket = service.getTicketbyIndividual("getTicketbyIndividual", NAME_OPS, "individual");
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (mActivity.get() != null) {
				if(result){
					if (table_ticket.length == 0) {
						listItem.clear();
						aucunTicket.setText("Sorry! No ticket has been assigned to you for now...");
						aucunTicket.setVisibility(View.VISIBLE);
					} else if (NAME_OPS == null){
						Intent intentTimeOut = new Intent(MainActivityList.this, MainActivityTimeOut.class);
						startActivity(intentTimeOut);
						finish();
					}
					else {
					aaa(table_ticket);
					lw.setVisibility(View.VISIBLE);
					}
					//txt1.setText(resultat);
					bt_Assign.setVisibility(View.INVISIBLE);
					bt_Closed.setVisibility(View.INVISIBLE);
					bt_Group.setVisibility(View.INVISIBLE);
					bt_Create.setVisibility(View.INVISIBLE);
					loading.dismiss(); 
				} else {
					Toast.makeText(mActivity.get(), "Echec de recuperation", Toast.LENGTH_LONG).show();
				}
				
			}
		}

		@Override
		protected void onPreExecute() {
			if(mActivity.get() != null){
			loading = new ProgressDialog(MainActivityList.this); 
            loading.setMessage("The server file recovery..."); 
            loading.setTitle("Loading..."); 
            loading.show();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		public void link (MainActivityList activityList) {
			mActivity = new WeakReference<MainActivityList>(activityList);
			}

	}
	
	

	public void aaa (String[] tableau){
		
		List<String> liste = new ArrayList<String>();
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
				
				
				
				String[] status= null;
				// get Status
				try {
					status = Alldescription[1].split("=");
				} catch (Exception e) {
					status[1]= "";
				}
				
			//	Log.e("Gauss Status",status[1]);
				
				
				String[] summary = null;
				// get Summary
				try {
					summary = Alldescription[2].split("=");
				} catch (Exception e) {
					summary[1] = "";
				}
				
				
				
				String[] ticket = null;
				// get ID Ticket
				try {
					ticket = Alldescription[3].split("=");
				} catch (Exception e) {
					ticket[1] = "";
				}
				
				
				//Log.e("Gauss description","ID : " + ticket[1] + " Descr :" +description[1] + " Status :"+ status[1] + "Summary : " + summary[1]);
			
				map = new HashMap<String, String>();
				map.put("ID", ticket[1]);
				map.put("SUMMARY", "SUMMARY : " + summary[1]);
				map.put("DESCRIPTION", "DESCRIPTION : " + description[1]);
			//	map.put("IMAGE", String.valueOf(R.drawable.logovoda));
				
				
				listItem.add(map);
				
				
			} catch (Exception e) {
				//Toast.makeText(MainActivityList.this, "Non traiter " + e.getMessage() +" et Resultat : " + tableau[i].toString(), Toast.LENGTH_LONG).show();
				
				String[] ticket = tableau[i].toString().split("trouble_Ticket_ID=");
	            String[] id = ticket[1].split(";");
	            System.out.println(id[0]);
	            
	            map = new HashMap<String, String>();
				map.put("ID", id[0]);
				map.put("SUMMARY", "");
				map.put("DESCRIPTION", "");
				
				listItem.add(map);
			}
			
			
			liste.add(tableau[i]);	
		}
		
		Log.e("Gauss", liste.get(0));
		String[] ss = new String[] {"text1","text2"};
		int[] s = new int[] {android.R.id.text1, android.R.id.text2};
		
		SimpleAdapter adapter = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.affichageitem, new String[] {"ID", "SUMMARY", "DESCRIPTION"}, new int[] {R.id.idTicket, R.id.summaryTicket, R.id.descriptionTicket});
		//ArrayAdapter<String> ladap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, liste);
		//lw.setAdapter(ladap);
		lw.setAdapter(adapter);

	}
	
	public void ToastResult (String message){
		
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(lw.getVisibility() == View.VISIBLE){
			
			lw.setVisibility(View.INVISIBLE);
			listItem.clear();
			bt_Assign.setVisibility(View.VISIBLE);
			bt_Closed.setVisibility(View.VISIBLE);
			bt_Group.setVisibility(View.VISIBLE);
			bt_Create.setVisibility(View.VISIBLE);
			setTitle("TTs");
		} else if(aucunTicket.getVisibility() == View.VISIBLE ){
			aucunTicket.setVisibility(View.INVISIBLE);
			listItem.clear();
			bt_Assign.setVisibility(View.VISIBLE);
			bt_Closed.setVisibility(View.VISIBLE);
			bt_Group.setVisibility(View.VISIBLE);
			bt_Create.setVisibility(View.VISIBLE);
			setTitle("TTs");
		} else if(bt_Assign.getVisibility() == View.VISIBLE && bt_Closed.getVisibility() == View.VISIBLE){
			AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
			a_builder.setMessage("Do you want to exit ????").setCancelable(false)
			.setNegativeButton("No", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			})
			.setPositiveButton("Yes", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					closeAllActivities();
				}
			});
			AlertDialog alert = a_builder.create();
			alert.setTitle("Warning");
			alert.show();
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return false;	
	}
	
	
	private class AsyncCallWSClosed extends AsyncTask<String, Void, Boolean> {
private WeakReference<MainActivityList> mActivity = null;
		
		public AsyncCallWSClosed(MainActivityList activityList) {
			link(activityList);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			//try {
			Bundle id = getIntent().getExtras();
			if(id != null){	
				NAME_OPS = id.getString("NAME_OPS");
				MOTDEPASSE = id.getString("MOTDEPASSE");
				LOGIN = id.getString("LOGIN");
			}
			try {
				table_Closed = service.getTicketbyIndividual("getTicketbyIndividualClosed", NAME_OPS, "individual");
				return true;
			} catch (Exception e) {
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (mActivity.get() != null) {
					if(result){
						if (table_Closed.length < 1) {
							listItem.clear();
							aucunTicket.setText("Sorry! No ticket closed or resolved");
							aucunTicket.setVisibility(View.VISIBLE);
						} else if (NAME_OPS == null){
							Intent intentTimeOut = new Intent(MainActivityList.this, MainActivityTimeOut.class);
							startActivity(intentTimeOut);
							finish();
						}else {
						aaa(table_Closed);
						lw.setVisibility(View.VISIBLE);
						}
						//txt1.setText(resultat);
						bt_Assign.setVisibility(View.INVISIBLE);
						bt_Closed.setVisibility(View.INVISIBLE);
						bt_Group.setVisibility(View.INVISIBLE);
						bt_Create.setVisibility(View.INVISIBLE);
						loading.dismiss(); 
					} else {
						Toast.makeText(mActivity.get(), "Echec de recuperation", Toast.LENGTH_LONG).show();
					}
				}
			
		}

		@Override
		protected void onPreExecute() {
			if(mActivity.get() != null){
			loading = new ProgressDialog(MainActivityList.this); 
            loading.setMessage("The server file recovery..."); 
            loading.setTitle("Loading..."); 
            loading.show();
            }
		}
		@Override
		protected void onProgressUpdate(Void... values) {
		}

		public void link (MainActivityList activityList) {
			mActivity = new WeakReference<MainActivityList>(activityList);
			}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 0){
			Bundle bundle = data.getExtras();
			NAME_OPS = bundle.getString("NAME_OPS");
			MOTDEPASSE = bundle.getString("MOTDEPASSE");
			LOGIN = bundle.getString("LOGIN");
		} else if (resultCode == RESULT_OK){
			Bundle bundle = data.getExtras();
			NAME_OPS = bundle.getString("NAME_OPS");	
			MOTDEPASSE = bundle.getString("MOTDEPASSE");
			LOGIN = bundle.getString("LOGIN");
		}else if (resultCode == 1){
			Bundle bundle = data.getExtras();
			NAME_OPS = bundle.getString("NAME_OPS");
			MOTDEPASSE = bundle.getString("MOTDEPASSE");
			LOGIN = bundle.getString("LOGIN");
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
