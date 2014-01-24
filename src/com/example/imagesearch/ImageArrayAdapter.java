package com.example.imagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

public class ImageArrayAdapter extends ArrayAdapter<Result> {

	public ImageArrayAdapter(Context context, List<Result> images) {
		super(context, R.layout.item_image_result, images);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Result image = this.getItem(position);
		SmartImageView ivImage;
		if(convertView == null){
			LayoutInflater inflator = LayoutInflater.from(getContext());
			ivImage = (SmartImageView) inflator.inflate(R.layout.item_image_result, parent, false);
		}
		else{
			ivImage = (SmartImageView) convertView;
			ivImage.setImageResource(android.R.color.transparent);
		}
		ivImage.setImageUrl(image.getTbUrl());
		return ivImage;
	}

}
