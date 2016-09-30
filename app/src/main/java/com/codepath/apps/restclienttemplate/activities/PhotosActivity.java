package com.codepath.apps.restclienttemplate.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.codepath.apps.restclienttemplate.client.FlickrClient;
import com.codepath.apps.restclienttemplate.client.FlickrClientApp;
import com.codepath.apps.restclienttemplate.adapters.PhotoArrayAdapter;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.FlickrPhoto;
import com.loopj.android.http.JsonHttpResponseHandler;

public class PhotosActivity extends Activity {
	public static final String TAG_COLUMN_NUM = "TAG_COLUMN_NUM";
	public static final String TAG_QUERY = "TAG_QUERY";
	FlickrClient client;
	ArrayList<FlickrPhoto> photoItems;
	GridView gvPhotos;
	PhotoArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		client = FlickrClientApp.getRestClient();
		photoItems = new ArrayList<FlickrPhoto>();
		gvPhotos = (GridView) findViewById(R.id.gvPhotos);
		adapter = new PhotoArrayAdapter(this, photoItems);
		gvPhotos.setAdapter(adapter);
		initData();
		loadPhotos();
	}

	private void initData() {
		if (getIntent().getExtras()!=null){
			int mColumnNum = getIntent().getIntExtra(TAG_COLUMN_NUM,2);
			String mQuery = getIntent().getStringExtra(TAG_QUERY);
			gvPhotos.setNumColumns(mColumnNum);
			FlickrClient.query = mQuery;
		}
	}


	public void loadPhotos() {
		client.getInterestingnessList(new JsonHttpResponseHandler() { 
    		public void onSuccess(JSONObject json) {
                try {
//					FlickrPhoto.delete(FlickrPhoto.class);
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

				for (FlickrPhoto p : FlickrPhoto.recentItems()) {
					adapter.add(p);
				}
            }
    	});
	}

}
