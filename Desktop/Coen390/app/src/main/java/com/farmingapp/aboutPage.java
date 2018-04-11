package com.farmingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by adamrais on 18-03-27.
 */

public class aboutPage extends Activity {

    public TextView main_text;
    public TextView body_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pophelp);

        main_text = (TextView) findViewById(R.id.Main_Text);
        body_text = (TextView) findViewById(R.id.Body_Text);

        main_text.setText("About Us");
        body_text.setText("Welcome to Plantlea!\n" +
                "Does your overwhelming, busy life make it difficult to remember everyday chores (like watering\n" +
                "your plants)? Well, Plantlea is just what you need to keep your plants healthy and your home\n" +
                "as green as ever! With a notification system to alert you when your plants are drying out, your\n" +
                "plants will undoubtedly be in tip-top shape!\n" +
                "Say hello to our development team: Aarij Ali Mirjat, Adam Rais, Hassan Siddiqui, Shivangi Saini\n" +
                "and Xuting Zhang! We have worked tirelessly over the past 4 months to provide a solution for\n" +
                "you!");
        body_text.setMovementMethod(new ScrollingMovementMethod());


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8) ,(int) (height*.6));
    }
}
