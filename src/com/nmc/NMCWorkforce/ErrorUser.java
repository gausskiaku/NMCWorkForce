package com.nmc.NMCWorkforce;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ErrorUser extends Activity {

	Button bt;
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("NMC Workforce : Error");
		setContentView(R.layout.activity_error_user);
		bt = (Button) findViewById(R.id.btErreur);
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable( new ColorDrawable(Color.RED));
		
		setFont(bt);
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
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
