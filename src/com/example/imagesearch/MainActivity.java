package com.example.imagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {

	EditText etSearch;
	Button btnSearch;
	GridView gvImageResults;
	
	Preferences pref;
	
	ArrayList<Result> imageResult = new ArrayList<Result>();
	ImageArrayAdapter imageAdapter;
	
	private final int REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		imageAdapter = new ImageArrayAdapter(this, imageResult);
		gvImageResults.setAdapter(imageAdapter);
		gvImageResults.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long arg3) {
				Intent z = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				Result ir = imageResult.get(position);
				z.putExtra("url", ir.getUrl());
				startActivity(z);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void initViews(){
		etSearch = (EditText) findViewById(R.id.etSearch);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		gvImageResults = (GridView) findViewById(R.id.gvImageResults);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            openSettings();
	            return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	public void openSettings(){
		pref = new Preferences();
		Intent i = new Intent(MainActivity.this, PreferencesActivity.class);
		i.putExtra("pref", pref); // pass arbitrary data to launched activity
		startActivityForResult(i, REQUEST_CODE);
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			pref = (Preferences) getIntent().getSerializableExtra("newPref");
		    
		}
	}
	
	public void onSearch(View v){
		String inputQuery = etSearch.getText().toString(); 
		if(inputQuery.equals("")){
			Toast.makeText(getApplicationContext(), "Enter search query", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(getApplicationContext(), "Searching for " + inputQuery + " ...", Toast.LENGTH_SHORT).show();
			/* 
			 * 1. get preferences
			 * 2. create url
			 * 3. fire query
			 */
			AsyncHttpClient client = new AsyncHttpClient();
			// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=barack%20obama
			client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + "0" + "&v=1.0&q=" + Uri.encode(etSearch.getText().toString()), 
					new JsonHttpResponseHandler(){
				public void onSuccess(JSONObject res){
					JSONArray imageJsonResults = null;
					
					try{
						imageJsonResults = res.getJSONObject("responseData").getJSONArray("results");
						imageResult.clear();
						
						imageAdapter.addAll(Result.fromJSONArray(imageJsonResults));
						Log.d("url", imageResult.toString());
					} catch(JSONException e){
						e.printStackTrace();
					}
					
				}
			});
		}
	}
	
}
