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
		this.imageSize = "no selection";
		this.colorFilter = "no selection";
		this.imageType = "no selection";
		this.siteFilter = "";
	}
	
	public String getSiteFilter() {
		return siteFilter;
	}
	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}
	public String getImageSize() {
		return imageSize.toLowerCase();
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	public String getColorFilter() {
		return colorFilter.toLowerCase();
	}
	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}
	public String getImageType() {
		return imageType.toLowerCase();
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
}
