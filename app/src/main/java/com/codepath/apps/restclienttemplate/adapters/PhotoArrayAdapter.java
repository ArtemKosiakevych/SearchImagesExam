package com.codepath.apps.restclienttemplate.adapters;

import java.util.List;

import com.codepath.apps.restclienttemplate.models.CustomImageView;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.FlickrPhoto;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PhotoArrayAdapter extends ArrayAdapter<FlickrPhoto> {
	Context mContext;
	public PhotoArrayAdapter(Context context, List<FlickrPhoto> photoList) {
		super(context, R.layout.photo_item, photoList);
		this.mContext = context;
	}

	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		FlickrPhoto photo = this.getItem(position);
		ImageView ivImage;
		ImageLoader imageLoader = ImageLoader.getInstance();
        if (convertView == null) {
			ivImage = new CustomImageView(mContext);
			ivImage.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
			ivImage = (ImageView) convertView;
        }
        imageLoader.displayImage(photo.getUrl(), ivImage);
        return ivImage;
	}
}
