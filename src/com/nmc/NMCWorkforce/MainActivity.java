package com.nmc.NMCWorkforce;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.list.WebServiceNameGroup;
import com.nmc.NMCWorkforce.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	
	Button b;
	TextView tv;
	EditText et;
	ProgressBar pg;
	static String displayText;
	String editText;
	Button bt;
	Button btLancer;
	List<String> liste = new ArrayList<String>();
	ListView lv;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		 lv = new ListView(this);
		
		
		
		
		 //Name Text control
        et = (EditText) findViewById(R.id.editText1);
        //Display Text control
        tv = (TextView) findViewById(R.id.tv_result);
        //Button to trigger web service invocation
        b = (Button) findViewById(R.id.button1);
        
        bt = (Button) findViewById(R.id.button2);
        btLancer = (Button) findViewById(R.id.btCentral);
        //Display progress bar until web service invocation completes
        pg = (ProgressBar) findViewById(R.id.progressBar1);
        //Button Click Listener
        b.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Check if Name text control is not empty
                if (et.getText().length() != 0 && et.getText().toString() != "") {
                    //Get the text control value
                	editText = et.getText().toString();
                    //Create instance for AsyncCallWS
                    AsyncCallWS task = new AsyncCallWS();
                    //Call execute 
                    task.execute();
                    showDialogListView();
                //If text control is empty
                } else {
                    tv.setText("Please enter name");
                }
                
                
                
            }
        });
		
		btLancer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MainActivityUser.class);
				startActivity(intent);
				
			}
		});
        bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent activityTicket = new Intent(MainActivity.this, MainActivityList.class);
				startActivity(activityTicket);
				
			}
		});
		
	}
	private void listGroup() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.choixgroup, R.id.txtGroup, liste);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewGroup vg =  (ViewGroup) view;
				TextView txt = (TextView) vg.findViewById(R.id.txtGroup);
				Toast.makeText(getBaseContext(), "Groupe : " + txt.getText(), Toast.LENGTH_LONG).show();
				
			}
		});
	}
	
	private void showDialogListView() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setCancelable(true);
		//builder.setPositiveButton("Ok", null);
		builder.setView(lv);
		AlertDialog dialog = builder.create();
		dialog.show();
		
	}
	private void dialogS() {
		LayoutInflater factory = LayoutInflater.from(this);
	     final View alertDialogView = factory.inflate(R.layout.choixgroup, null);

	   //Création de l'AlertDialog
	     AlertDialog.Builder adb = new AlertDialog.Builder(this);
	 
	        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
	        adb.setView(alertDialogView);
	 
	        //On donne un titre à l'AlertDialog
	        adb.setTitle("Titre de notre boite de dialogue");

	      //On modifie l'icône de l'AlertDialog pour le fun ;)
	      //  adb.setIcon(android.R.drawable.ic_dialog_alert);
	        
	        
	      //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
	       // adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	       //    public void onClick(DialogInterface dialog, int which) {
	 
	            	// Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
	            	// EditText et = (EditText)alertDialogView.findViewById(R.id.EditText1);
	 
	            	// On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
	            	// Toast.makeText(Tutoriel18_Android.this, et.getText(), Toast.LENGTH_SHORT).show();
	       //   } });
	        
	      //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
	        adb.setNegativeButton("Back", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	//Lorsque l'on cliquera sur annuler on quittera l'application
	            	finish();
	          } });
	        adb.show();


	}
	
	WebService service = new WebService();
	WebServiceNameGroup group = new WebServiceNameGroup();
	String[] gp;
	String allGroup = "";
	
	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			displayText = service.invokeHelloWorldWS(editText, "hello");
			gp = group.getGroup("getGroup", "NMCnetcool", "Password0123@");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//tv.setText(fahren + "° F");
			tv.setText(displayText);
			pg.setVisibility(View.INVISIBLE);
			
			for(String onlyGroup : gp) {
				liste.add(onlyGroup);
			}
			
			//Toast.makeText(getBaseContext(), "" + allGroup, Toast.LENGTH_LONG).show();
			
			listGroup();
		}

		@Override
		protected void onPreExecute() {
			pg.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}
}
