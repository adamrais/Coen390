package com.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class plantProfile extends AppCompatActivity {


    //TODO implement refresh activity function


    // button that jumps to add plant activity
    public void addPlant(View view) {

        Intent intent =  new Intent(getApplicationContext(), addPlant.class);
        startActivity(intent);

    }

    @Override // create the menu action bar
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_3, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.help){ // if user click help button
            startActivity(new Intent(plantProfile.this, PopHelp2.class));

        }
        else if (item.getItemId() == R.id.alert){
            startActivity(new Intent(plantProfile.this, AlarmMainActivity.class));
        }
        else if (item.getItemId() == R.id.logout){

            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_profile);

        final List<Map<String, String>> plantData = new ArrayList<Map<String, String>>(); //create map to store the plant

        final ListView plantListView = (ListView) findViewById(R.id.plantListView);

        plantListView.setEmptyView(findViewById(R.id.emptyElement)); // in case the list is empty display message to ask user to create a plant


        //making the listview clickable to redirect each plant to the singlePlantProfile with the name of the plant
        plantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(plantProfile.this,singlePlantProfile.class);
                String Value = (String) ((HashMap) adapterView.getItemAtPosition(i)).get("plant");
                String name = Value.replace("Name of the plant: ", "");
                intent.putExtra("plant", name);
                startActivity(intent);

                Log.i("working", name );
            }
        });

        // query method to put the name of each plant into the listview to display them
        //user will create a plant, and the name and the date of creation of the plant will be
        // displayed her
        ParseQuery<ParseObject> query = ParseQuery.getQuery("plant");

        //query.whereContainedIn("username",ParseUser.getCurrentUser().getList("plant"));
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.addDescendingOrder("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0){

                        List<HashMap<String, String>> plantData = new ArrayList<HashMap<String, String>>();

                        for (ParseObject Plant : objects){


                                HashMap<String, String> plantInfo = new HashMap<String, String>();

                                plantInfo.put("username","Created by " + Plant.getString("username"));
                                plantInfo.put("plant", "Name of the plant: "+ Plant.getString("plant"));

                                plantData.add(plantInfo);

                        }
                        SimpleAdapter simpleAdapter = new SimpleAdapter(plantProfile.this, plantData, android.R.layout.simple_list_item_2, new String[] {"username", "plant"}, new int[] {android.R.id.text1, android.R.id.text2});

                        plantListView.setAdapter(simpleAdapter);

                    }

                }
            }
        });

    }
}
