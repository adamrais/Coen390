package com.farmingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class PopHelp6 extends Activity{

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
        body_text.setText("This is where you can find your individual plant. By clicking on check measurement you can go get the soil moisture of your plant if you" +
                "\n" + "enabled the bluetooth. Put the soil moisture sensor inside the pot and get ready to get the data. If the soil moisture is too low you will received an alert telling you that its time to water it");
        body_text.setMovementMethod(new ScrollingMovementMethod());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8) ,(int) (height*.6));

    }
}
