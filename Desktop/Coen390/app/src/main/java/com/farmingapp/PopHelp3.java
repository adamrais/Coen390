package com.farmingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class PopHelp3 extends Activity {

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
        body_text.setText("Welcome to the “Add your Plant” Page!\n" +
                "To add your plant, you can add a name of your choice and a picture to help you remember\n" +
                "which plant is your plant.\n" +
                "To add a picture, click on the drop down menu found at the top right menu. You can either take\n" +
                "a picture or you can take an existing picture from the gallery found on your phone.\n" +
                "To add a name, click on the text field that says “enter a name” and type in a name of your\n" +
                "choice!");
        body_text.setMovementMethod(new ScrollingMovementMethod());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8) ,(int) (height*.6));

    }
}
