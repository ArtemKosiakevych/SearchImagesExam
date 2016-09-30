package com.codepath.apps.restclienttemplate.client;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FlickrClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = FlickrApi.class;
    public static final String REST_URL = "http://www.flickr.com/services";
    public static final String REST_CONSUMER_KEY = "57ac210e2e82195e071f9a761d763ca0";
    public static final String REST_CONSUMER_SECRET = "7d359e4f4149545b";
    public static final String REST_CALLBACK_URL = "oauth://cprest";

    public static String query = "android";
    
    public FlickrClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
        setBaseUrl("https://api.flickr.com/services/rest");
    }

    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?&format=json&nojsoncallback=1&api_key=a50b8f697ceda9f6cb3d4225ed213b4e&method=flickr.photos.search&text=" + query);
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }
}
