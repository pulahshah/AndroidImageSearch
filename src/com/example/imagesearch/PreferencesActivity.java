package com.example.imagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class PreferencesActivity extends Activity {

	EditText etSiteFilter;
	
	Preferences p;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		
		initViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferences, menu);
		return true;
	}
	
	
	public void initViews(){
		// get values from preferences and load the defaults
		p = (Preferences) getIntent().getSerializableExtra("pref");
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		if(etSiteFilter != null){
			Log.d("passed class pref", p.getSiteFilter());
			if(p != null){
				etSiteFilter.setText(p.getSiteFilter());
			}			
		}
	}
	
	
	public void onBackPressed() {
		// get values from all preferences
		p.setSiteFilter(etSiteFilter.getText()+"");
		
		// save them to the preference object
		
		// create intent
		Intent data = new Intent();
		data.putExtra("newPref", p);
		
		setResult(RESULT_OK, data);
		finish();
	}

}
