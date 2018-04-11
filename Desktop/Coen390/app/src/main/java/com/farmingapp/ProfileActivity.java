package com.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {


    //TODO https://unsplash.com website where you can find background image

    TextView welcomeMessage;
    TextView welcomeActivity;


    @Override // create the menu action bar
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.help){ // if user click help button
            startActivity(new Intent(ProfileActivity.this,PopHelp.class));


        }
        else if (item.getItemId() == R.id.about){ // if user click about button
            startActivity(new Intent(ProfileActivity.this,aboutPage.class));


        }
        else if (item.getItemId() == R.id.logout){

            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } //if user click to see the weather
        else if (item.getItemId() == R.id.checkWeather){

            startActivity(new Intent(ProfileActivity.this,Weather.class));
        }
        else if (item.getItemId() == R.id.Bluetooth){

            startActivity(new Intent(ProfileActivity.this, Bluetooth.class));
        }

        return super.onOptionsItemSelected(item);
    }





    //check plants button
    public void checkPlants(View view) {

        Intent intent =  new Intent(getApplicationContext(), plantProfile.class);
        startActivity(intent);

    }
    // Herbarium button
    public void herbarium(View view) {

        Intent intent =  new Intent(getApplicationContext(), herbarium.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);

        welcomeActivity = (TextView) findViewById(R.id.welcomeActivity);

    }
}
