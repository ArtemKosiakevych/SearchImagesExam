package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codepath.apps.restclienttemplate.client.FlickrClient;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.oauth.OAuthLoginActivity;

public class LoginActivity extends OAuthLoginActivity<FlickrClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
    @Override
    public void onLoginSuccess() {
    	Intent i = new Intent(this, MainActivity.class);
    	startActivity(i);
    }

    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    public void loginToRest(View view) {
        getClient().connect();
    }

}
