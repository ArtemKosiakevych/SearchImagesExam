package com.codepath.apps.restclienttemplate.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.codepath.apps.restclienttemplate.client.FlickrClient;
import com.codepath.apps.restclienttemplate.client.FlickrClientApp;
import com.codepath.apps.restclienttemplate.adapters.PhotoArrayAdapter;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.FlickrPhoto;
import com.loopj.android.http.JsonHttpResponseHandler;

public class PhotosActivity extends Activity {
	public static final String TAG_COLUMN_NUM = "TAG_COLUMN_NUM";
	public static final String TAG_QUERY = "TAG_QUERY";
	private FlickrClient client;
	private ArrayList<FlickrPhoto> photoItems;
	private GridView gvPhotos;
	private ImageView btnBack;
	private PhotoArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);

		initData();
		loadPhotos();

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(PhotosActivity.this, MainActivity.class));
				overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			}
		});
	}

	private void initData() {
		client = FlickrClientApp.getRestClient();
		photoItems = new ArrayList<FlickrPhoto>();
		gvPhotos = (GridView) findViewById(R.id.gvPhotos);
		btnBack = (ImageView) findViewById(R.id.back);
		adapter = new PhotoArrayAdapter(this, photoItems);
		gvPhotos.setAdapter(adapter);

		if (getIntent().getExtras()!=null){
			int mColumnNum = getIntent().getIntExtra(TAG_COLUMN_NUM,2);
			String mQuery = getIntent().getStringExtra(TAG_QUERY);
			gvPhotos.setNumColumns(mColumnNum);
			FlickrClient.query = mQuery;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		ProgressDialog mProgressDialog = new ProgressDialog(
				PhotosActivity.this);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setMessage(getString(R.string.dialog_mess));
		return mProgressDialog;
	}

	public void loadPhotos() {
		showDialog(1);
		client.getInterestingnessList(new JsonHttpResponseHandler() { 
    		public void onSuccess(JSONObject json) {
                try {
					JSONArray photos = json.getJSONObject("photos").getJSONArray("photo");
					for (int x = 0; x < photos.length(); x++) {
						String uid  = photos.getJSONObject(x).getString("id");
						FlickrPhoto p = FlickrPhoto.byPhotoUid(uid);
						if (p == null) { p = new FlickrPhoto(photos.getJSONObject(x)); };
						p.save();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				dismissDialog(1);
				for (FlickrPhoto p : FlickrPhoto.recentItems()) {
					adapter.add(p);
				}
            }
    	});
	}

}
