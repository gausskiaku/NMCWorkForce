package com.nmc.NMCWorkforce;


import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Metier.Mail;
import com.list.WebServiceGroups;

public class MainActivityGroup extends Activity {
	Button btInitDate, btEndDate, btSearch;
	Spinner sp;
	List<String> liste = new ArrayList<String>();
	private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
	private static ProgressDialog loading;
	String NAME_GROUP;
	ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map;
	String ID_TICKET;
	ListView lw;
	AsyncCallWS task;
	ActionBar actionBar;
	String NAME_OPS;
	static String MOTDEPASSE;
	static String LOGIN;
	static String SOURCE = "Tickets de tout un group";
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_group);
		
		btInitDate = (Button) findViewById(R.id.btInitDate);
		btEndDate = (Button) findViewById(R.id.btEndDate);
		btSearch = (Button) findViewById(R.id.btSearch);
		
		sp = (Spinner) findViewById(R.id.spGroupTicket);
		
		lw = (ListView) findViewById(R.id.listViewGroupTT);
		
		setFont(btEndDate);
		setFont(btInitDate);
		setFont(btSearch);
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
		
		setTitle("TT by Group");
		
		String[] GROUP = null;
		Bundle id = getIntent().getExtras();
		if(id != null){	
			 GROUP = id.getStringArray("GROUP");
		}
		try {
			
			for(String onlyGroup : GROUP) { 
				liste.add(onlyGroup);
			}
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "Sorry, you belong to no group", Toast.LENGTH_LONG).show();
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, liste);
		sp.setAdapter(adapter);
		
		btInitDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Get Current Date
	            final Calendar c = Calendar.getInstance();
	            mYear = c.get(Calendar.YEAR);
	            mMonth = c.get(Calendar.MONTH);
	            mDay = c.get(Calendar.DAY_OF_MONTH);
	            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivityGroup.this, new DatePickerDialog.OnDateSetListener(){

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						btInitDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
					}
	            }, mYear, mMonth, mDay);
	            datePickerDialog.show();
			}
		});
		
		
		btEndDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get Current Date
	            final Calendar c = Calendar.getInstance();
	            mYear = c.get(Calendar.YEAR);
	            mMonth = c.get(Calendar.MONTH);
	            mDay = c.get(Calendar.DAY_OF_MONTH);
	            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivityGroup.this, new DatePickerDialog.OnDateSetListener(){

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						btEndDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
					}
	            	
	            }, mYear, mMonth, mDay);
	            datePickerDialog.show();	
			}
		});
		
		btSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
				if(ninfo != null && ninfo.isConnected()){
					 
					try {
					//	dt_Start.getDate().after(dt_End.getDate())
						if(btInitDate.getText().toString().indexOf("/") == -1 || btEndDate.getText().toString().indexOf("/") == -1){
							Toast.makeText(MainActivityGroup.this, "Please complete all fields for the filter or verify date...", Toast.LENGTH_LONG).show();
						} else{
							Date dateBegin = dateFormat.parse(btInitDate.getText().toString());
							 Date dateEnd =  dateFormat.parse(btEndDate.getText().toString());
						 if (dateBegin.after(dateEnd) || sp.getSelectedItemPosition() == -1){
							 Toast.makeText(MainActivityGroup.this, "The date begin is after to date End or the group not choose", Toast.LENGTH_LONG).show(); 
						 } else {
							 lw.setAdapter(null);
							 task = new AsyncCallWS(MainActivityGroup.this);
				             task.execute();
						 }
						}
						
					//	if (NAME_OPS == null){
					//		Toast.makeText(MainActivityList.this, "Please wait, the server is busy...", Toast.LENGTH_LONG).show();
					//	} else {
					//	Toast.makeText(MainActivityList.this, "Welcome : "+ NAME_OPS + " Login : " + LOGIN + " " + MOTDEPASSE, Toast.LENGTH_LONG).show();
						
					//	}
		                
		            //  Mail mail = new Mail();
		            //  mail.sendMessage(MainActivityGroup.this);
					} catch (NullPointerException e) {
						Toast.makeText(MainActivityGroup.this, "Please wait, the server is busy...", Toast.LENGTH_LONG).show();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //
				}
				else {
					Toast.makeText(MainActivityGroup.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				NAME_GROUP = parent.getItemAtPosition(position).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
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
				//SOURCE = idInt.getString("SOURCE");
			}
			
			Intent forMainDetail = new Intent(MainActivityGroup.this, MainActivityDetail.class);
			forMainDetail.putExtra("ID_TICKET", ID_TICKET);
			forMainDetail.putExtra("NAME_OPS", NAME_OPS);
			forMainDetail.putExtra("SOURCE", SOURCE);
			startActivity(forMainDetail);
				
			}
});
		sp.setSelection(-1);
		task = (AsyncCallWS) getLastNonConfigurationInstance();
	}
	
	public Object onRetainNonConfigurationInstance() {
			return task;
	}
	
	
	
WebServiceGroups service = new WebServiceGroups();
String table_ticket[] = null;
	
	private class AsyncCallWS extends AsyncTask<String, Void, Boolean> {
		private WeakReference<MainActivityGroup> mActivity = null;
		
		public AsyncCallWS(MainActivityGroup activityGroup) {
			link(activityGroup);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			try {
					table_ticket = service.getByGroup("getTicketbyGroup", NAME_GROUP, btInitDate.getText().toString(), btEndDate.getText().toString());
					// NAME_GROUP, btInitDate.getText().toString(), btEndDate.getText().toString()
				return true;
			} catch (Exception e) {
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
		//	Toast.makeText(getBaseContext(), "Voila : " + table_ticket.toString() + " Group : "+ NAME_GROUP + " Init : " + btInitDate.getText().toString() + " End : " + btEndDate.getText().toString(), Toast.LENGTH_LONG).show();
			if (mActivity.get() != null) {
				if(result){
					if (table_ticket.length == 0) {
						listItem.clear();
						Toast.makeText(getBaseContext(), "Sorry! No TT was assigned to your group during this period...", Toast.LENGTH_LONG).show();
					} else {
						try {
							aaa(table_ticket);
						} catch (Exception e) {
							// TODO: handle exception
						}
					
					}
					loading.dismiss(); 
				} else {
					Toast.makeText(mActivity.get(), "Echec de recuperation", Toast.LENGTH_LONG).show();
			}
				
			}
		}

		@Override
		protected void onPreExecute() {
			if(mActivity.get() != null){
			loading = new ProgressDialog(MainActivityGroup.this); 
            loading.setMessage("The server file recovery..."); 
            loading.setTitle("Loading..."); 
            loading.show();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		public void link (MainActivityGroup activityGroup) {
			mActivity = new WeakReference<MainActivityGroup>(activityGroup);
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
				
			
				String[] summary = null;
				// get Summary
				try {
					summary = Alldescription[2].split("=");
				} catch (Exception e) {
					summary[1] = "";
				}
				
				
				
				String[] ticket = null;
				try {
					ticket = Alldescription[3].split("=");
				} catch (Exception e) {
					ticket[1] = "";
				}
				
				
				map = new HashMap<String, String>();
				map.put("ID", ticket[1]);
				map.put("SUMMARY", "SUMMARY : " + summary[1]);
				map.put("DESCRIPTION", "DESCRIPTION : " + description[1]);
				
				
				listItem.add(map);
				
				
			} catch (Exception e) {
				
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
		
		SimpleAdapter adapter = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.affichageitem, new String[] {"ID", "SUMMARY", "DESCRIPTION"}, new int[] {R.id.idTicket, R.id.summaryTicket, R.id.descriptionTicket});
		lw.setAdapter(adapter);
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
