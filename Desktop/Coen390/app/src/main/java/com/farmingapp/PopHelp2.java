package com.farmingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class PopHelp2 extends Activity {

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
        body_text.setText("Welcome to your Garden!\n" +
                "This is where you will have a list of all the plants you want to monitor!\n" +
                "\n" +
                "If this is your first time on the page, click on the “Add Plant” button to add your sensor/plant to\n" +
                "the list.");
        body_text.setMovementMethod(new ScrollingMovementMethod());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8) ,(int) (height*.6));

    }
}
