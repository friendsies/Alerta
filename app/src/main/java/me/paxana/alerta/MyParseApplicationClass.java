package me.paxana.alerta;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

/**
 * Created by paxie on 1/11/16.
 */
public class MyParseApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this);

        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}

