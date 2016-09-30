package com.codepath.apps.restclienttemplate.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.adapters.PhotoArrayAdapter;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView search;
    private SeekBar seekBar;
    private TextView countColumn;
    private EditText query;
    private int mColumnNum = 2;
    private String mQuery = "animals";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        query = (EditText) findViewById(R.id.query);
        search = (TextView) findViewById(R.id.search);
        countColumn = (TextView) findViewById(R.id.count);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(this);
        search.setOnClickListener(this);
    }

    private void findImages() {
        if (validData()){
            mQuery = mQuery.replace(" ","_");
            Intent intent = new Intent(MainActivity.this, PhotosActivity.class);
            intent.putExtra(PhotosActivity.TAG_COLUMN_NUM,mColumnNum);
            intent.putExtra(PhotosActivity.TAG_QUERY,mQuery);
            startActivity(intent);
        }
    }

    private boolean validData() {
        if (mQuery.trim().length()<1){
            query.setError("Enter your query");
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
            findImages();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        countColumn.setText(String.valueOf(i+1));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
