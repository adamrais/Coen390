package com.farmingapp;

/**
 * Created by adamrais on 18-03-04.
 */
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;


public class parseServer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("7ead04413220c9122b3bd67f921a12307b953774")
                .clientKey("bf4bfb239d262dedcec69a25db895fa8b31e00ba")
                .server("http://35.183.0.49:80/parse/")
                .build()
        );

        //ParseUser.enableAutomaticUser(); // we dont need automatic user as we control who login or not in the app
        ParseACL defaultACL = new ParseACL();
        //Optionnally enable public read access.
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL,true);
    }
}


