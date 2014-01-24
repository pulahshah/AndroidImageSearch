package com.example.imagesearch;

import java.io.Serializable;

public class Preferences implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1289840116005349903L;
	private String imageSize;
	private String colorFilter;
	private String imageType;
	private String siteFilter;
	
	public Preferences(){
		this.imageSize = "small";
		this.colorFilter = "";
		this.imageType = "";
		this.siteFilter = "google.com";
	}
	
	public String getSiteFilter() {
		return siteFilter;
	}
	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}
	public String getImageSize() {
		return imageSize;
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	public String getColorFilter() {
		return colorFilter;
	}
	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
}
