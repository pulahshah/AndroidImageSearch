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
	
	Preferences pref = new Preferences();
	
	ArrayList<Result> imageResult = new ArrayList<Result>();
	ImageArrayAdapter imageAdapter;
	
	private final int REQUEST_CODE = 1;
	private boolean firstRun = true;
	private int queryCount = 0;
	
	int startingPoint = 0;
	
	
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
		
		
		gvImageResults.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
	            customLoadMoreDataFromApi(page); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
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
	
	// Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	
    	Log.d("DEBUG", "<custom load more data - offset: >" + offset + " ;; firstRun: " + firstRun);
    	
    	fireQuery(offset);
    	
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
		Intent i = new Intent(MainActivity.this, PreferencesActivity.class);
		i.putExtra("pref", pref); 
		startActivityForResult(i, REQUEST_CODE);
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			pref = (Preferences) data.getSerializableExtra("newPref");
			if(pref != null){
				Log.d("DEBUG", "Image size: " + pref.getImageSize().toString());
				Log.d("DEBUG", "Color filter: " + pref.getColorFilter().toString());
				Log.d("DEBUG", "Image type: " + pref.getImageType().toString());
			}
			else{
				Log.d("DEBUG", "Pref is null <onActivityResult>");
			}
		}
	}
	
	public void onSearch(View v){
		// clear pagination count
		startingPoint = 0;
		
		String inputQuery = etSearch.getText().toString(); 
		if(inputQuery.equals("")){
			Toast.makeText(getApplicationContext(), "Enter search query", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(getApplicationContext(), "Searching for " + inputQuery + " ...", Toast.LENGTH_SHORT).show();
			fireQuery(0);
		}
	}
	
	
	private void fireQuery(final int param){
		String url = createURL(param);
		queryCount++;
		
			AsyncHttpClient client = new AsyncHttpClient();
			Log.d("DEBUG", "firing query " + queryCount);
			client.get(url, new JsonHttpResponseHandler(){
				public void onSuccess(JSONObject res){
					JSONArray imageJsonResults = null;
					
					try{
						imageJsonResults = res.getJSONObject("responseData").getJSONArray("results");
						if(param == 0){
							imageResult.clear();
						}
						
						imageAdapter.addAll(Result.fromJSONArray(imageJsonResults));
						Log.d("url", imageResult.toString());
					} catch(JSONException e){
						e.printStackTrace();
					}
					
				}
			});
		
		
	}

	private String createURL(int offset) {
		String imageSize = null; 
		String colorFilter = null;
		String imageType = null;
		String siteSearch = null;
		String temp = "";
		
		if(pref != null){
			imageSize = pref.getImageSize();
			colorFilter = pref.getColorFilter();
			imageType = pref.getImageType();
			siteSearch = pref.getSiteFilter();
		}
		else{
			Log.d("DEBUG", "Pref is null");
		}
		
		if(siteSearch != null){
			temp += "&as_sitesearch=" + siteSearch;
		}
		
		if(colorFilter != null){
			temp += "&imgcolor=" + colorFilter;
		}
		
		if(imageSize != null){
			temp += "&imgsz=" + imageSize;
		}
		
		if(imageType != null){
			temp += "&imgtype=" + imageType;
		}
		
		String url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8" + "&start=" +
				(offset*8) + temp + "&v=1.0&q=" + Uri.encode(etSearch.getText().toString());
		return url;
	}
	
}
