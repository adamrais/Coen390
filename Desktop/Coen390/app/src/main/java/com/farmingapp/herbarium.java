package com.farmingapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * all the information about the plants are found on wikipedia
 */
public class herbarium extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private SearchView searchView;
    private MenuItem searchMenuItem;
    ArrayList<String> myWiki; //= new ArrayList<String>();
    ListView myListView;// = (ListView) findViewById(R.id.myListView);
    ArrayAdapter<String> arrayAdapter;// = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myWiki);





    /*
    need to create a list that will display the name of different plant
    make that list clickable with onclick
    and retrieeve the information of each click with an intent method that will display the plant on the other windows

     */

    // this is the menu bar
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.search_menu, menu);

        // we declare function for search bar
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);

        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.help){ // if user click help button
            startActivity(new Intent(herbarium.this,PopHelp4.class));
            // pop up message that will display help for the user need to implement that asap
        }
        else if (item.getItemId() == R.id.logout){

            ParseUser.logOut(); // function that log out the user
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        arrayAdapter.getFilter().filter(s);
        return false;
    }




    //oncreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herbarium);

        myListView = (ListView) findViewById(R.id.myListView);

        myWiki = new ArrayList<String>();

        //adding the different plant wwiki in the array list
        myWiki.add("Airplant");
        myWiki.add("Aeonium");
        myWiki.add("Aibika");
        myWiki.add("Camellia");
        myWiki.add("Sugarberry");

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myWiki);
        myListView.setAdapter(arrayAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                // TODO Auto-generated method stub
                //super.onItemClick(l, view, i, l);

                if (l == 0) {
                    Intent intent = new Intent(herbarium.this, plant_1.class);
                    String name = "Airplant";
                    intent.putExtra("Airplant",name);
                    startActivity(intent);
                }
                else if (l == 1) {
                    Intent intent = new Intent(herbarium.this, plant_2.class);
                    String name = "Aeonium";
                    intent.putExtra("Aeonium",name);
                    startActivity(intent);
                }
                else if (l == 2) {
                    Intent intent = new Intent(herbarium.this, plant_3.class);
                    String name = "Aibika";
                    intent.putExtra("Aibika",name);
                    startActivity(intent);
                }
                else if (l == 3) {
                    Intent intent = new Intent(herbarium.this,plant_4.class);
                    String name = "Camellia";
                    intent.putExtra("Camellia",name);
                    startActivity(intent);
                }
                else if (l == 4) {
                    Intent intent = new Intent(herbarium.this,plant_5.class);
                    String name = "Sugarberry";
                    intent.putExtra("Sugarberry",name);
                    startActivity(intent);
                }
            }
        });
    }

}
