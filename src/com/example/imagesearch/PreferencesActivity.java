package com.example.imagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class PreferencesActivity extends Activity {

	EditText etSiteFilter;
	Spinner spImageSize;
	Spinner spColorFilter;
	Spinner spImageType;
	Preferences p = new Preferences();
	
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
		p = (Preferences) getIntent().getSerializableExtra("pref");
		
		spImageSize = (Spinner) findViewById(R.id.spImageSize);
		ArrayAdapter<CharSequence> adapterImageSize = ArrayAdapter.createFromResource(this, R.array.image_size_array, android.R.layout.simple_spinner_item);
		adapterImageSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageSize.setAdapter(adapterImageSize);
		
		spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
		ArrayAdapter<CharSequence> adapterColorFilter = ArrayAdapter.createFromResource(this, R.array.color_filter_array, android.R.layout.simple_spinner_item);
		adapterImageSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spColorFilter.setAdapter(adapterColorFilter);
		
		spImageType = (Spinner) findViewById(R.id.spImageType);
		ArrayAdapter<CharSequence> adapterImageType = ArrayAdapter.createFromResource(this, R.array.image_type_array, android.R.layout.simple_spinner_item);
		adapterImageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageType.setAdapter(adapterImageType);
		
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		if(etSiteFilter != null){
			if(p != null){
				Log.d("passed class pref", p.getSiteFilter());
				etSiteFilter.setText(p.getSiteFilter());
			}
			else{
				Log.d("DEBUG", "passed pref is null <PreferencesActivity>");
			}
		}
	}
	
	
	public void onBackPressed() {
		if(p != null){
			p.setImageSize(spImageSize.getSelectedItem().toString());
			p.setColorFilter(spColorFilter.getSelectedItem().toString());
			p.setImageType(spImageType.getSelectedItem().toString());
			p.setSiteFilter(etSiteFilter.getText().toString());
			
			// create intent
			Intent data = new Intent();
			data.putExtra("newPref", p);
			setResult(RESULT_OK, data);
		}
		else{
			Log.d("DEBUG", "intent not passed <PreferencesActivity>");
		}
		finish();
	}

}
