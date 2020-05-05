package com.nmc.NMCWorkforce;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.List;
import com.list.WebServiceSave;
import com.list.WebServiceUser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivitySave extends Activity {

	Spinner spGroup;
	Spinner spProcess;
	Spinner spGroupType;
	Spinner spFault;
	
	Spinner spClassification;
	Spinner spZone;
	Spinner spRegion;
	Spinner spDistrict;
	
	Spinner spHandover;
	
	LinearLayout l1;
	LinearLayout l2;
	LinearLayout l3;
	//LinearLayout l4;
	
	RelativeLayout r4;
	
	EditText txtSummary;
	EditText txtDescription;
	EditText txtindividu;
	
	Button btDatePicker, btTimePicker;
    TextView txtDate, txtTime;
    static String resultat;
    private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
    
    AsyncCallWS task = null;
    private ProgressDialog loading;
	
	private RadioGroup radioGroup;
	private RadioButton radioOnly;
	
	
	String choixProcess;
	String choixGroupType;
	String choixZone;
	String choixRegion;
	String choixFault;
	String choixDispatchGroup;
	String choixClassification;
	String choixDistrict;
	String choixHandoverMode;
	
	Button btSave;
	ActionBar actionBar;
	static String MOTDEPASSE;
	static String LOGIN;
	static String NAME_OPS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_save);
		
		l1 = (LinearLayout) findViewById(R.id.linearLayout1);
		l2 = (LinearLayout) findViewById(R.id.linearLayout2);
		l3 = (LinearLayout) findViewById(R.id.linearLayout3);
	//	l4 = (LinearLayout) findViewById(R.id.linearLayout4);
		r4 = (RelativeLayout) findViewById(R.id.linearLayout4);
		
		btDatePicker = (Button) findViewById(R.id.btDate);
		btTimePicker = (Button) findViewById(R.id.btTime);
		txtDate = (TextView) findViewById(R.id.txtDate);
		txtTime = (TextView) findViewById(R.id.txtTime);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		
		spGroup = (Spinner) findViewById(R.id.spGroup);
		spProcess = (Spinner) findViewById(R.id.spProcess);
		spGroupType = (Spinner) findViewById(R.id.spGroupType);
		spFault = (Spinner) findViewById(R.id.spFault);
		
		spClassification = (Spinner) findViewById(R.id.spClassification);
		spZone = (Spinner) findViewById(R.id.spZone);
		spRegion = (Spinner) findViewById(R.id.spRegion);
		spDistrict = (Spinner) findViewById(R.id.spDistrict);
		
		spHandover = (Spinner) findViewById(R.id.spHandover);
		
		btSave = (Button) findViewById(R.id.btSave);
		
		txtSummary = (EditText) findViewById(R.id.txtSummary);
		txtDescription = (EditText) findViewById(R.id.txtDescription);
		txtindividu = (EditText) findViewById(R.id.txtIndividual);
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
		setTitle("Create TT");
		
		setFont(txtSummary);
		setFont(txtDescription);
		setFont(txtindividu);
		setFont(btDatePicker);
		setFont(btTimePicker);
		setFont(btSave);
		//setFont(textView);
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				switch(checkedId)
                {
                case R.id.radio0:
                    l1.setVisibility(LinearLayout.VISIBLE);
                    
                    l2.setVisibility(LinearLayout.INVISIBLE);
                    l3.setVisibility(LinearLayout.INVISIBLE);
                 //   l4.setVisibility(LinearLayout.INVISIBLE);
                    r4.setVisibility(LinearLayout.INVISIBLE);
                    break;
                case R.id.radio1:
                	
                    l2.setVisibility(LinearLayout.VISIBLE);
                    
                    l1.setVisibility(LinearLayout.INVISIBLE);
                	l3.setVisibility(LinearLayout.INVISIBLE);
                	//l4.setVisibility(LinearLayout.INVISIBLE);
                	r4.setVisibility(LinearLayout.INVISIBLE);
                    break;
                case R.id.radio2:
                	l3.setVisibility(LinearLayout.VISIBLE);
                	
                	l1.setVisibility(LinearLayout.INVISIBLE);
                	l2.setVisibility(LinearLayout.INVISIBLE);
                	//l4.setVisibility(LinearLayout.INVISIBLE);
                	r4.setVisibility(LinearLayout.INVISIBLE);
                    break;
                case R.id.radio3:
                	//l4.setVisibility(LinearLayout.VISIBLE);
                	r4.setVisibility(LinearLayout.VISIBLE);
                	
                	l1.setVisibility(LinearLayout.INVISIBLE);
                	l2.setVisibility(LinearLayout.INVISIBLE);
                	l3.setVisibility(LinearLayout.INVISIBLE);

                	
                   break;
                }
				
			}
		});
		
		
		
		btDatePicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get Current Date
	            final Calendar c = Calendar.getInstance();
	            mYear = c.get(Calendar.YEAR);
	            mMonth = c.get(Calendar.MONTH);
	            mDay = c.get(Calendar.DAY_OF_MONTH);
	            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivitySave.this, new DatePickerDialog.OnDateSetListener(){

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						txtDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
					}
	            	
	            }, mYear, mMonth, mDay);
	            datePickerDialog.show();
			}
		});
		
		btTimePicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
	            mHour = c.get(Calendar.HOUR_OF_DAY);
	            mMinute = c.get(Calendar.MINUTE);
	            mSecond = c.get(Calendar.SECOND);
	 
	            // Launch Time Picker Dialog
	            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivitySave.this,
	                    new TimePickerDialog.OnTimeSetListener() {
	 
	                        @Override
	                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	 
	                            txtTime.setText(hourOfDay + ":" + minute +":" + mSecond );
	                        }
	                    }, mHour, mMinute, false);
	            timePickerDialog.show();

				
			}
		});
		
		// Classification
		String[] classification = {"Bronze","Gold", "Platinum", "Silver"};
		ArrayAdapter<String> adpterClassification = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classification);
		spClassification.setAdapter(adpterClassification);
		
		// Zone
		String[] zone = {"BasCongo","Central", "Eastern", "Kinshasa", "Northern", "Southern", "Western"};
		ArrayAdapter<String> adpterZone = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, zone);
		spZone.setAdapter(adpterZone);
		
		// Group Ops
		String[] group_region = getResources().getStringArray(R.array.group_region);
		ArrayAdapter<String> adpter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, group_region);
		spGroup.setAdapter(adpter);
		
		
		// Process
		String[] process = getResources().getStringArray(R.array.process);
		ArrayAdapter<String> adpterProcess = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, process);
		spProcess.setAdapter(adpterProcess);
		
		// Handover Mode
		String[] handoverMode = getResources().getStringArray(R.array.handoverMode);
		ArrayAdapter<String> adpterHandoverMode = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, handoverMode);
		spHandover.setAdapter(adpterHandoverMode);
		
		
		spProcess.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			choixProcess = parent.getItemAtPosition(position).toString();
				spinnerGroup(choixProcess);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
			
		});
		
		spGroupType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choixGroupType = parent.getItemAtPosition(position).toString();
				spinnerFault(choixGroupType);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		
		spFault.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choixFault = parent.getItemAtPosition(position).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		spGroup.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choixDispatchGroup = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		spClassification.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choixClassification = parent.getItemAtPosition(position).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		spZone.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choixZone = parent.getItemAtPosition(position).toString();
				spinnerRegion(choixZone);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				
			}
		});
		
		spRegion.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choixRegion = parent.getItemAtPosition(position).toString();
				spinnerDistrict(choixRegion);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		spDistrict.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choixDistrict = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		spHandover.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choixHandoverMode = parent.getItemAtPosition(position).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo ninfo = cmanager.getActiveNetworkInfo();
				if(ninfo != null && ninfo.isConnected()){
					if (txtSummary.getText().length() < 3 || txtDescription.getText().length() < 3 ||
						spFault.getSelectedItemPosition() == -1 ||
						txtDate.getText().length() < 5 || txtTime.getText().length() < 3){
						Toast.makeText(MainActivitySave.this, "Please fill in the fields correctly", Toast.LENGTH_LONG).show();
					} else {
						//Toast.makeText(getBaseContext(), LOGIN + "    " + MOTDEPASSE, Toast.LENGTH_LONG).show();
					task = new AsyncCallWS(MainActivitySave.this);
					task.execute(); 
					}	
				}
				
				else {
					Toast.makeText(MainActivitySave.this, "Please enable mobile network", Toast.LENGTH_LONG).show();
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
	
	private void spinnerDistrict(String region) {
		List<String> district = new ArrayList<>();
		if (region.equals("Bas-Congo")){
			district.clear();
			district.add("Bangu");
			district.add("Boma");
			district.add("Gombe_Matadi");
			district.add("Inkisi");
			district.add("Kaivemba");
			district.add("Kamba");
			district.add("Kasangulu");
			district.add("Kimbenza");
			district.add("Kimpangu");
			district.add("Kimpese");
			district.add("Kimvula");
			district.add("KinzauMvuete");
			district.add("Kipholo");
			district.add("Kizulu");
			district.add("Kolofuma");
			district.add("Kwilu_Ngongo");
			district.add("Lemfu");
			district.add("Lukala");
			district.add("Lukula");
			district.add("Luozi");
			district.add("Madimba");
			district.add("Matadi");
			district.add("MbanzaNgungu");
			district.add("Moanda");
			district.add("Nkandu");
			district.add("Nsanda");
			district.add("Nzadi-Kongo");
			district.add("Patou");
			district.add("Songololo");
			district.add("Tsanga_Nord");
			district.add("Tshiende");
			district.add("Yema");
			district.add("Zongo_Dam");		
		} else if (region.equals("Kasai-Occidental")){
			district.clear();
			district.add("Bakwasumpi");
			district.add("Biponga");
			district.add("Demba_B");
			district.add("DjokoPunda");
			district.add("Domiongo");
			district.add("Ilebo");
			district.add("Kafunda");
			district.add("Kakenge");
			district.add("Kakulu");
			district.add("Kamako");
			district.add("Kamonia");
			district.add("Kananga");
			district.add("Kashama");
			district.add("Luebo");
			district.add("Luiza");
			district.add("LuizaMasuika");
			district.add("Mayimbi");
			district.add("Mukono");
			district.add("Musangana");
			district.add("Mutshima");
			district.add("Mwambambuyi");
			district.add("Saposapo");
			district.add("Shinateke");
			district.add("Sumbula");
			district.add("Shinateke");
			district.add("Tshikapa");
			district.add("Tshimbulu");
			district.add("Yengembana");
		} else if (region.equals("Kasai-Oriental")){
			district.clear();
			district.add("Baluba_Shankadi");
			district.add("cyacyacya");
			district.add("Kabinda");
			district.add("Kamukongo");
			district.add("Kaniki");
			district.add("Katakokombe");
			district.add("Katanda");
			district.add("Lodja");
			district.add("Lubao");
			district.add("Lukalaba");
			district.add("Lunyeka");
			district.add("Luputa");
			district.add("Lusambo");
			district.add("Lusanga");
			district.add("Lusuku");
			district.add("Mbuji-Mayi");
			district.add("Mweneditu");
			district.add("Miabi");
			district.add("Mweneditu");
			district.add("Ngandajika");
			district.add("Tshala");
			district.add("Tshibombo");
			district.add("Tshumbe");
			district.add("Wikong");
		} else if (region.equals("Maniema")){
			district.clear();
			district.add("Bikenge");
			district.add("Kabambare");
			district.add("Kailo");
			district.add("Kalima");
			district.add("Kampene");
			district.add("Kasese");
			district.add("Kasongo");
			district.add("Kibangula");
			district.add("Kibombo");
			district.add("Kindu");
			district.add("Lubutu");
			district.add("Punia");
			district.add("Salamabila");
		} else if (region.equals("Nord-Kivu")){
			district.clear();
			district.add("Beni");
			district.add("Butembo");
			district.add("Butuhe");
			district.add("Erengeti");
			district.add("Goma");
			district.add("Idjwi");
			district.add("Kamiunga");
			district.add("Kanyabayonga");
			district.add("Kaseghe");
			district.add("Kasindi");
			district.add("Kibati");
			district.add("Kibumba");
			district.add("Kirumba");
			district.add("Kyondo");
			district.add("Lubero");
			district.add("Mangurujipa");
			district.add("Masisi");
			district.add("Mubi");
			district.add("Mutwanga");
			district.add("Ndjingala");
			district.add("Nyamilima");
			district.add("Oicha");
			district.add("Rumangabo");
			district.add("Rutshuru");
			district.add("Sake");
			district.add("Tongo");
			district.add("Walikale");
		} else if (region.equals("Sud-Kivu")){
			district.clear();
			district.add("Baraka");
			district.add("Bukavu");
			district.add("Fizi");
			district.add("Kamanyola");
			district.add("Kamituga");
			district.add("Katana");
			district.add("Kiliba");
			district.add("Misisi");
			district.add("Ngalula");
			district.add("Shabunda");
			district.add("Twangiza");
			district.add("Uvira");
			district.add("Walungu");
		} else if (region.equals("Kinshasa")){
			district.clear();
			district.add("Kinshasa");
		} else if (region.equals("Province-Orientale")){
			district.clear();
			district.add("Abba");
			district.add("Ariwara");
			district.add("Aru");
			district.add("Awuzi");
			district.add("Bafwambaya");
			district.add("Bafwasende");
			district.add("Basoko");
			district.add("Baye");
			district.add("Bogoro");
			district.add("Bolebole");
			district.add("Bondo");
			district.add("Bunia");
			district.add("Buta");
			district.add("Dha");
			district.add("Djalasika");
			district.add("Ingbokolo");
			district.add("Isangi");
			district.add("Isiro");
			district.add("Kilo");
			district.add("Kisangani");
			district.add("Komanda");
			district.add("Kpandruma");
			district.add("Mahagi");
			district.add("Mambasa");
			district.add("Manzobe");
			district.add("Moku");
			district.add("Mungwalu");
			district.add("Ndimu");
			district.add("Nizi");
			district.add("Nyakunde");
			district.add("Ondolea");
			district.add("Tadu");
			district.add("Tchomia");
			district.add("Ubundu");
			district.add("Watsa");
		} else if (region.equals("Katanga")){
			district.clear();
			district.add("Ankoro");
			district.add("Biano");
			district.add("Bukama");
			district.add("Diabulongo");
			district.add("Dikulushi");
			district.add("Dilolo");
			district.add("Fungurume");
			district.add("Kabalo");
			district.add("Kabimba");
			district.add("KabondoDianda");
			district.add("Kabongo");
			district.add("Kabumba");
			district.add("Kabwa_Katanda");
			district.add("Kakanda");
			district.add("Kalemie");
			district.add("Kambove");
			district.add("Kamina");
			district.add("Kaniama");
			district.add("Kapanga");
			district.add("Kapolowe");
			district.add("Kasaji");
			district.add("Kasenga");
			district.add("Kashobwe");
			district.add("Kasumbalesa");
			district.add("Katenga");
			district.add("Katongola");
			district.add("Kibula");
			district.add("Kilwa");
			district.add("Kimanda");
			district.add("Kinsever");
			district.add("Kipushi");
			district.add("Kisamfu");
			district.add("Kisenge");
			district.add("Kishiko");
			district.add("Kitenge");
			district.add("Kolwezi");
			district.add("Kongolo");
			district.add("Likasi");
			district.add("Lualaba");
			district.add("Luambo");
			district.add("Lubudi");
			district.add("Lubumbashi");
			district.add("Luena");
			district.add("Luisha");
			district.add("Luita");
			district.add("MalembaNkulu");
			district.add("Manono");
			district.add("Mitwaba");
			district.add("Moba");
			district.add("Mokambo");
			district.add("Muleba");
			district.add("Mutanda");
			district.add("Mwadingusha");
			district.add("Mwango");
			district.add("Mweyi");
			district.add("Nyunzu");
			district.add("Pumpi");
			district.add("Pweto");
			district.add("Sakania");
			district.add("Thumbwe");
			district.add("Tshilongo");
		} else if (region.equals("Bandundu")){
			district.clear();
			district.add("Bamba");
			district.add("Bandundu");
			district.add("Bokoro");
			district.add("Bolobo");
			district.add("Bulungu");
			district.add("Bwalenge");
			district.add("DibayaLubwe");
			district.add("Gungu");
			district.add("Idiofa");
			district.add("Inongo");
			district.add("Kabuba");
			district.add("Kahemba");
			district.add("Kashimba");
			district.add("KasongoLunda");
			district.add("Kenge");
			district.add("Kikwit");
			district.add("Kwamouth");
			district.add("Kwango");
			district.add("LabaMakingi");
			district.add("Mangayi");
			district.add("Masimanimba");
			district.add("Misele");
			district.add("Mpunza");
			district.add("Mushie");
			district.add("Muzabala");
			district.add("MwanaHuta");
			district.add("NgongoKaunda");
			district.add("Nioki");
			district.add("Pomongo");
			district.add("Popokabaka");
			district.add("Tembo");
			district.add("Vanga");
		} else if (region.equals("Equateur")){
			district.clear();
			district.add("Basankusu");
			district.add("Binga");
			district.add("Boende");
			district.add("Bokungu");
			district.add("Bumba");
			district.add("Bwamanda");
			district.add("Dongo");
			district.add("Gbadolite");
			district.add("Gemena");
			district.add("Gwaka");
			district.add("Ikela");
			district.add("Karawa");
			district.add("Kotakoli");
			district.add("Lukolela");
			district.add("Mbandaka");
			district.add("Yakoma");
			district.add("Zongo");
		}
		ArrayAdapter<String> adpterDistrict = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, district);
		spDistrict.setAdapter(adpterDistrict);
	}
	private void spinnerRegion(String zone) {
		List<String> region = new ArrayList<>();
		if (zone.equals("BasCongo")){
			region.clear();
			region.add("Bas-Congo");
		} else if (zone.equals("Central")){
			region.clear();
			region.add("Kasai-Occidental");
			region.add("Kasai-Oriental");
		} else if (zone.equals("Eastern")){
			region.clear();
			region.add("Maniema");
			region.add("Nord-Kivu");
			region.add("Sud-Kivu");
		} else if (zone.equals("Kinshasa")){
			region.clear();
			region.add("Kinshasa");
		} else if (zone.equals("Northern")){
			region.clear();
			region.add("Province-Orientale");
		} else if (zone.equals("Southern")){
			region.clear();
			region.add("Katanga");
		} else if (zone.equals("Western")){
			region.clear();
			region.add("Bandundu");
			region.add("Equateurl");
		}
		
		ArrayAdapter<String> adpterRegion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, region);
		spRegion.setAdapter(adpterRegion);
	}
	
	private void spinnerFault(String groupType) {
		List<String> faultType = new ArrayList<>();
		if (groupType.equals("Billing and Admin")){
			faultType.clear();
			faultType.add("Add SMO");
			faultType.add("Billing Server Problem");
			faultType.add("Billing System Down");
			faultType.add("Billing System Problem");
			faultType.add("Cannot Receive");
			faultType.add("CDR Files");
			faultType.add("Create Mailbox");
			faultType.add("Create PP Sub");
			faultType.add("CTU Busy Requests");
			faultType.add("Delete Mailbox");
			faultType.add("Delete PP Sub");
			faultType.add("Disk Space");
			faultType.add("Frequent Mailbox Exp");
			faultType.add("Front End Problem");
			faultType.add("Link Mailboxes");
			faultType.add("Mailbox Provision Failure");
			faultType.add("Missing Files");
			faultType.add("MML Files");
			faultType.add("Order Incorrect");
			faultType.add("Process Failed");
			faultType.add("Recreate Mailbox");
			faultType.add("Remove SMO");
			faultType.add("Resend Files");
			faultType.add("SIM Swop");
			faultType.add("SMS Activation Delay");
			faultType.add("Special Investigation/Diversity");
			faultType.add("Subsync Tasks");
			faultType.add("System Problem");
			faultType.add("Unlock Sub");
		} else if (groupType.equals("Call Centre")){
			faultType.clear();
			faultType.add("112 - Emergency Service");
			faultType.add("Call Centre Down");
			faultType.add("Call Centre Problem");
			faultType.add("Community Services (199)");
			faultType.add("DQ Problem");
			faultType.add("Gen Enq (111) Problem");
			faultType.add("Reconnect (119) Problem");
			faultType.add("Replay Problem");
			faultType.add("Special Investigation");
			faultType.add("System Event");
			faultType.add("Tape Loaded");
		}  else if (groupType.equals("CBNL") || groupType.equals("Fiber") || groupType.equals("Microwave") || groupType.equals("VL") || groupType.equals("Walkair") || groupType.equals("Wimax+") ){
			faultType.clear();
			faultType.add("Instability");
			faultType.add("Out Of Service");
			faultType.add("Slowness");
		} else if (groupType.equals("BSC")){
			faultType.clear();
			faultType.add("A / Interface Upgrade");
			faultType.add("AC Power Failure");
			faultType.add("BSC Equipment Failure");
			faultType.add("BSC Overload");
			faultType.add("Call Drops BSS");
			faultType.add("Capacity link OOS");
			faultType.add("CIC Blocked");
			faultType.add("Database change");
			faultType.add("Decommission Equipment");
			faultType.add("EAS");
			faultType.add("MSC");
			faultType.add("MTL");
			faultType.add("Special Investigation");
			faultType.add("VSWR");
		} else if (groupType.equals("BTS")){
			faultType.clear();
			faultType.add("AC Phase failure");
			faultType.add("Antenna");
			faultType.add("BTS Reset");
			faultType.add("Cell Sleeping");
			faultType.add("EAS");
			faultType.add("Equipment Over Voltage");
			faultType.add("GCLK  Failure");
			faultType.add("GPRS Faults");
			faultType.add("LOSS-OF-ALL-CHAN");
			faultType.add("MAINTENANCE");
			faultType.add("Performance Problem");
			faultType.add("Special Investigation");
			faultType.add("TCH Seizure Failure");
			faultType.add("Transcoder DROPS");
			faultType.add("XCDR Alarms");
		} else if (groupType.equals("Fuel")){
			faultType.clear();
			faultType.add("Fuel Low Level");
			faultType.add("Fuel Removed");
		} else if (groupType.equals("Generator")){
			faultType.clear();
			faultType.add("Generator Controller Fail");
			faultType.add("Generator on Manual Mode");
			faultType.add("Generator Start Fail");
			faultType.add("Irregular Consumption");
			faultType.add("Operation Maintenance");
			faultType.add("Operations Planned Work");
			faultType.add("Service Due");
			faultType.add("SKM Energy");
			faultType.add("Unicomplex Maintenance");
			faultType.add("Unicomplex Planned work");
		} else if (groupType.equals("Refueling")){
			faultType.clear();
			faultType.add("Full of Tank 1");
			faultType.add("Full of Tank 2");
			faultType.add("Quater Tank 1");
			faultType.add("Quater Tank 2");
			faultType.add("Three Quaters of Tank 1");
			faultType.add("Three Quaters of Tank 2");
			faultType.add("Two Quaters of Tank 2");
			faultType.add("Two Quaters of Tank1");
		}  else if (groupType.equals("SAM 2 Moduel")){
			faultType.clear();
			faultType.add("Communication problem");
		} else if (groupType.equals("Tank")){
			faultType.clear();
			faultType.add("Fuel Probe Fail");
			faultType.add("Water in tank");
		} else if (groupType.equals("TRANSMISSION")){
			faultType.clear();
			faultType.add("1 PDH Ring");
			faultType.add("CAPACITY");
			faultType.add("Compression Gear System");
			faultType.add("ENVIRONMENT");
			faultType.add("EQUIPMENT FAILURE");
			faultType.add("FAULTY DISK");
			faultType.add("HANDSET");
			faultType.add("HUB");
			faultType.add("LACK OF COVERAGE");
			faultType.add("NM Monitoring");
			faultType.add("PDH System");
			faultType.add("Remote Earth Station");
			faultType.add("Roaming Links");
			faultType.add("SDH System");
			faultType.add("STANDARISATION");
			faultType.add("USAGE INCREASE");
			faultType.add("VSAT");
		} else if (groupType.equals("SWITCHING")){
			faultType.clear();
			faultType.add("1 OMT");
			faultType.add("1 X25");
			faultType.add("All X25's");
			faultType.add("C7 Link Set");
			faultType.add("C7 Route Set");
			faultType.add("CDR Files");
			faultType.add("Environmental Alarms");
			faultType.add("HLR");
			faultType.add("NM Monitoring");
			faultType.add("OMC");
			faultType.add("Power Alarm");
			faultType.add("RCP");
			faultType.add("SSP");
		} else if (groupType.equals("NMC Equipment Alarm")){
			faultType.clear();
			faultType.add("NMC equipment alarms");
			faultType.add("Special Investigation/Diversity");
			faultType.add("Special Investigation/Failure");
		} else if (groupType.equals("RADIO")){
			faultType.clear();
			faultType.add("Environmental Alarms");
			faultType.add("NM Monitoring");
			faultType.add("Power Alarm");
		} else if (groupType.equals("QUALITY")){
			faultType.clear();
			faultType.add("Add Capacity");
			faultType.add("Cell Extender");
			faultType.add("ENVIRONMENT");
			faultType.add("EQUIPMENT FAILURE");
			faultType.add("HANDSET");
			faultType.add("LACK OF COVERAGE");
			faultType.add("NEW  SITE");
			faultType.add("No  Fault Found");
			faultType.add("No Economic Solution");
			faultType.add("OPERATOR ERROR");
			faultType.add("optimisation");
			faultType.add("REFER  TO MASTER FAULTS");
			faultType.add("RURAL");
			faultType.add("SPECIAL EVENTS");
			faultType.add("URBAN");
			faultType.add("USAGE INCREASE");
		} else if (groupType.equals("Out of Service")){
			faultType.clear();
			faultType.add("BSC");
			faultType.add("BTS");
			faultType.add("CELL");
			faultType.add("DCME");
		}
		
		ArrayAdapter<String> adpterGroupType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, faultType);
		spFault.setAdapter(adpterGroupType);
	}
	
	private void spinnerGroup(String process) {
		List<String> groupType = new ArrayList<>();
		if (process.equals("Billing")){
			groupType.clear();
			groupType.add("Billing and Admin");
		} else if (process.equals("Customer Services")){
			groupType.clear();
			groupType.add("Call Centre");
		} else if(process.equals("EBU")){
			groupType.clear();
			groupType.add("CBNL");
			groupType.add("Fiber");
			groupType.add("Microwave");
			groupType.add("VL");
			groupType.add("Walkair");
			groupType.add("Walkair");
		} else if(process.equals("EDGE")){
			groupType.clear();
			groupType.add("BSC");
			groupType.add("BTS");
			groupType.add("CELL");
			groupType.add("MFS");
		} else if(process.equals("Fuel Management")){
			groupType.clear();
			groupType.add("Fuel");
			groupType.add("Generator");
			groupType.add("Refueling");
			groupType.add("SAM 2 Moduel");
			groupType.add("Tank");
		} else if(process.equals("Network Fault")){
			groupType.clear();
			groupType.add("BSC");
			groupType.add("BTS");
			groupType.add("CELL");
			groupType.add("CIC GSM Network");
			groupType.add("CIC Network Faults Addressed");
			groupType.add("DATA");
			groupType.add("GPRS");
			groupType.add("HLR");
			groupType.add("IN");
			groupType.add("MSC");
			groupType.add("Network");
			groupType.add("Network Faults Addressed");
			groupType.add("Network Services");
			groupType.add("NMC Applications");
			groupType.add("NMC Equipment Alarm");
			groupType.add("NMC GSM performance");
			groupType.add("NMC IN platform");
			groupType.add("NMC Other");
			groupType.add("NMC Subscriber Admin");
			groupType.add("NodeB");
			groupType.add("OMCR");
			groupType.add("Out of Service");
			groupType.add("PDH");
			groupType.add("PROPERTIES");
			groupType.add("QUALITY");
			groupType.add("RADIO");
			groupType.add("RCU");
			groupType.add("RNC");
			groupType.add("Roaming");
			groupType.add("SDH");
			groupType.add("Signalling");
			groupType.add("SPSD VAS");
			groupType.add("Stolen");
			groupType.add("Subscriber Provisioning");
			groupType.add("SWITCHING");
			groupType.add("Transcoder");
			groupType.add("TRANSMISSION");
			groupType.add("TRE");
			groupType.add("VAS");
		} else if(process.equals("Network Quality")){
			groupType.clear();
			groupType.add("CELL");
			groupType.add("lock");
			groupType.add("TRE");
		} else if(process.equals("Network Quality")){
			groupType.clear();
			groupType.add("Circuit Identification Code");
			groupType.add("MSC - NSS");
			groupType.add("RCP");
			groupType.add("Signalling links");
			groupType.add("SSP");
		} else if(process.equals("NSS")){
			groupType.clear();
			groupType.add("Circuit Identification Code");
			groupType.add("MSC - NSS");
			groupType.add("RCP");
			groupType.add("Signalling links");
			groupType.add("SSP");
		} else if(process.equals("Planned Work")){
			groupType.clear();
			groupType.add("Job Orders");
			groupType.add("NodeB");
			groupType.add("RNC");
		} else if(process.equals("Site Access")){
			groupType.clear();
			groupType.add("BSC");
			groupType.add("BTS");
			groupType.add("GENERATOR");
			groupType.add("HUB");
			groupType.add("MSC");
		} else if(process.equals("Systems Administration")){
			groupType.clear();
			groupType.add("EPPIX");
			groupType.add("M2000");
			groupType.add("OMC R");
			groupType.add("OMC S");
			groupType.add("REMEDY ARS");
			groupType.add("U2000");
		} else if(process.equals("Value Added Services (VAS)")){
			groupType.clear();
			groupType.add("CIC VAS");
			groupType.add("COVERAGE");
			groupType.add("GSM Network");
			groupType.add("Intelligent Network");
			groupType.add("Mailbox");
			groupType.add("MSC- VAS");
			groupType.add("Value Added Services");
		} else if(process.equals("Vodanet")){
			groupType.clear();
			groupType.add("Access Unit");
		} else if(process.equals("Walkair")){
			groupType.clear();
			groupType.add("BS");
			groupType.add("BSBU");
			groupType.add("TS");
		} else if(process.equals("Wimax")){
			groupType.clear();
			groupType.add("BS");
		}
		
		ArrayAdapter<String> adpterGroupType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groupType);
		spGroupType.setAdapter(adpterGroupType);
	}

	
	static WebServiceSave connexion = new WebServiceSave();
	
	private class AsyncCallWS extends AsyncTask<String, Void, Boolean> {
		private WeakReference<MainActivitySave> mActivity = null;
		public AsyncCallWS(MainActivitySave activitySave) {
			link(activitySave);
		}
		@Override
		protected Boolean doInBackground(String... params) {
			try {
				Bundle id = getIntent().getExtras();
				if(id != null){	
					MOTDEPASSE = id.getString("MOTDEPASSE");
					LOGIN = id.getString("LOGIN");
				}
				resultat = connexion.CreateTicket("createTicket", LOGIN, MOTDEPASSE, txtSummary.getText().toString(), txtDescription.getText().toString(), choixProcess, choixGroupType, choixFault, choixDispatchGroup, txtindividu.getText().toString(), choixClassification, choixZone, choixRegion, choixDistrict, txtDate.getText() + " " + txtTime.getText(), choixHandoverMode);
				//NAME_OPS = resultat;
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
			loading = new ProgressDialog(MainActivitySave.this); 
            loading.setMessage("Checking..."); 
            loading.setTitle("Loading..."); 
            loading.show();}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		
		public void link (MainActivitySave activitySave) {
			mActivity = new WeakReference<MainActivitySave>(activitySave);
		}
	}
	
	public void res(){
		try {
			Toast.makeText(MainActivitySave.this, resultat, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
		//	Toast.makeText(MainActivityUser.this, "Failure recovery... Try again", Toast.LENGTH_LONG).show();
		}
		
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_save, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
