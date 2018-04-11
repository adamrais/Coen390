package com.farmingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by adamrais on 18-03-26.
 */
// Profile page activity help page
public class PopHelp extends Activity{


    public TextView main_text;
    public TextView body_text;



    //body_text.setText("this is a simple test to display information");
    //body_text.setMovementMethod(new ScrollingMovementMethod());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pophelp);


        main_text = (TextView) findViewById(R.id.Main_Text);
        body_text = (TextView) findViewById(R.id.Body_Text);

        main_text.setText("HELP");
        body_text.setText("Welcome to the home page!\n" +
                "First, start by enabling your phone’s Bluetooth. Then Click on the “Bluetooth” option in the\n" +
                "drop down menu where you can pair your phone to your plant’s sensor.\n" +
                "Then return to the homepage and click on “Check your Plants” to add the plant you want to\n" +
                "monitor to your phone OR check out the “Plant Wiki” to learn more about different plants that\n" +
                "exist in our world!\n" +
                "If you’re curious about the weather in a specific city, you can also click on the “Check the\n" +
                "Weather” feature in the drop down menu to get the weather details.");
        body_text.setMovementMethod(new ScrollingMovementMethod());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8) ,(int) (height*.6));

    }
}
