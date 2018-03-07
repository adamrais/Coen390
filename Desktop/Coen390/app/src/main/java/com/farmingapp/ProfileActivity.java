package com.farmingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    //TextView todayDate;

    //TextView welcomeMessage;

    //TextView welcomeActivity;
/*
    Button checkPlants = (Button) findViewById(R.id.checkPlants);

    Button addPlant = (Button) findViewById(R.id.addPlant);

    Button addMeasurement = (Button) findViewById(R.id.addMeasurement);

    Button herbarium = (Button) findViewById(R.id.herbarium);

    Button logout = (Button) findViewById(R.id.logout);

    // check plants button
    public void checkPlants(View view){



    }
    //add plant button
    public void addPlant(View view){


    }
    // add measurement button
    public void addMeasurement(View view){

    }
    // Herbarium button
    public void herbarium(View view){

    }
    //logout button
    public void logout(View view){


    }

*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat();

        TextView todayDate = (TextView) findViewById(R.id.todayDate);

        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);

        TextView welcomeActivity = (TextView) findViewById(R.id.welcomeActivity);
    }
}
