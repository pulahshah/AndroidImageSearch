package com.example.imagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Result {
	private String url;
	private String tbUrl;
	
	
	public Result(JSONObject obj){
		try{
			this.url = obj.getString("url");
			this.tbUrl = obj.getString("tbUrl");
		}
		catch(JSONException e){
			url = null;
			tbUrl = null;
		}
	}
	
	
	public String getUrl() {
		return url;
	}

	public String getTbUrl() {
		return tbUrl;
	}

	public String toString(){
		return this.url;
	}


	public static ArrayList<Result> fromJSONArray(JSONArray imageJsonResults) {
		ArrayList<Result> results = new ArrayList<Result>();
		
		for(int i=0; i<imageJsonResults.length(); i++){
			try{
				results.add(new Result(imageJsonResults.getJSONObject(i)));
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		
		return results;
	}
}
