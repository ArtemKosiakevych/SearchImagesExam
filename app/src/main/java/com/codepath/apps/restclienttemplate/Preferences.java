package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by KOS on 30.09.2016.
 */

public class Preferences {
    private Context _context;
    public SharedPreferences prefs;
    public final String KEY_LAST_QUERY = "KEY_LAST_QUERY";
    public final String KEY_LAST_COUNT = "KEY_LAST_COUNT";


    public Preferences(Context _context) {
        this._context = _context;
        prefs = PreferenceManager.getDefaultSharedPreferences(_context);
    }

    public String getLastQuery(){
        return prefs.getString(KEY_LAST_QUERY,"");
    }
    public int getLastCount(){
        return prefs.getInt(KEY_LAST_COUNT,1);
    }

    public void soreLastQuery(String query){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LAST_QUERY, query);
        editor.apply();
    }
    public void storeLastCount(int count){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_LAST_COUNT, count);
        editor.apply();
    }
}
