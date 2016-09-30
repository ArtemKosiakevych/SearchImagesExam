package com.codepath.apps.restclienttemplate.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.Preferences;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.adapters.PhotoArrayAdapter;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView search;
    private SeekBar seekBar;
    private TextView countColumn;
    private EditText query;
    private int mColumnNum;
    private String mQuery;
    private Preferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        query = (EditText) findViewById(R.id.query);
        search = (TextView) findViewById(R.id.search);
        countColumn = (TextView) findViewById(R.id.count);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        getLastData();
        seekBar.setOnSeekBarChangeListener(this);
        search.setOnClickListener(this);
    }

    private void getLastData() {
        pref = new Preferences(this);

        mQuery = pref.getLastQuery();
        mColumnNum = pref.getLastCount();

        query.setText(mQuery);
        countColumn.setText(String.valueOf(mColumnNum));
        seekBar.setProgress(mColumnNum - 1);

    }

    private void findImages() {
        if (validData()){
            mQuery = mQuery.replace(" ","_");
            Intent intent = new Intent(MainActivity.this, PhotosActivity.class);
            intent.putExtra(PhotosActivity.TAG_COLUMN_NUM,mColumnNum);
            intent.putExtra(PhotosActivity.TAG_QUERY,mQuery);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }
    }

    private boolean validData() {
        if (mQuery.trim().length()<1){
            query.setError(getString(R.string.error_query));
            YoYo.with(Techniques.Swing).playOn(query);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.search){
            mQuery = query.getText().toString();
            mColumnNum = Integer.parseInt(countColumn.getText().toString());
            pref.soreLastQuery(mQuery);
            findImages();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        countColumn.setText(String.valueOf(i+1));
        pref.storeLastCount(i+1);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
