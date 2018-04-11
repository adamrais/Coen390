package com.farmingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

// profile of each plant of the garden
public class singlePlantProfile extends AppCompatActivity {

    public TextView nameOfPlant;
    @Override // create the menu action bar
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_2, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.help){ // if user click help button
            startActivity(new Intent(singlePlantProfile.this,PopHelp6.class));
        // pop up message that will display help for the user need to implement that asap
        }
        else if (item.getItemId() == R.id.logout){

            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
    //check measurements button
    public void checkMeasurements(View view){

        Intent intent =  new Intent(getApplicationContext(), measurementPage.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_plant_profile);

        nameOfPlant = (TextView) findViewById(R.id.NamePlant);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        Intent intent = getIntent();
        //Bundle bundle = intent.getExtras();
        String activePlant = intent.getStringExtra("plant");
        nameOfPlant.setText(activePlant);


        /*if(bundle != null){
            String getName = (String) bundle.get("plant");
            nameOfPlant.setText(getName);
        }*/

        //retrive image saved by user

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("plant");

        //query.whereEqualTo("plant",);
       // query.orderByDescending("createdAt");
        query.whereEqualTo("plant", activePlant);
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByDescending("createdAt");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {

                            ParseFile file = (ParseFile) object.get("image");

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if (e == null && data != null){

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0 , data.length);

                                        ImageView imageView = new ImageView(getApplicationContext());

                                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));

                                        // put the image in the plant profile
                                        imageView.setImageBitmap(bitmap);

                                        linearLayout.addView(imageView);


                                    } else{
                                        Toast.makeText(singlePlantProfile.this, "", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                } else {
                    //TODO in case the user doesnt put an image make NOT WORKING FOR NOW
                    ImageView imageView = new ImageView(getApplicationContext());

                    imageView.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    imageView.setImageResource(R.drawable.plant);
                    linearLayout.addView(imageView);
                }
            }
        });


    }
}
