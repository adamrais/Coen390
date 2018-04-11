package com.farmingapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class addPlant extends AppCompatActivity{

    //TODO add field for description of the plant. Implement this if time allows it

    public Button takePicture;
    public ImageView cameraImage;
    public EditText plantName;


    //create parse object plant to add plant to the server
    public ParseObject plant = new ParseObject("plant");




    //save button that save the information of the plant in parse Server.
    public void savePlant(View view){

        plantName = (EditText) findViewById(R.id.plantName); // we define the name of the edit text

        //EditText plantSpecie = (EditText) findViewById(R.id.); // we define the species of the plant

        //EditText plantDescription = (EditText) findViewById(R.id.); // user will be able to edit description of his plant

        if(plantName.getText().toString().matches("")){ // if its not empty

            Toast.makeText(this, "A name is required for the plant", Toast.LENGTH_SHORT).show();
        } else {

            //TODO add the camera parse object in this field to synchronyse everything once the user click save



            plant.put("username", ParseUser.getCurrentUser().getUsername()); // the name of the person that has the plant

            plant.put("plant", plantName.getText().toString());

            //plant.put()

            Button savePlant = (Button) findViewById(R.id.savePlant); //call button

            plant.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {

                        Toast.makeText(addPlant.this, "Your plant was saved", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(addPlant.this, "not done", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
    //method to get the image from the camera of the phone
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();
            // surround if case it fails
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                //convert image to parse to send it
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //TODO change code to JPEG for the demo, PNG format crashes the app
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

                byte[] byteArray = stream.toByteArray();

                ParseFile file = new ParseFile("image.jpeg", byteArray);

                //ParseObject object = new ParseObject("Image");

                plant.put("image",file);

                plant.put("username", ParseUser.getCurrentUser().getUsername());

                //object.put("plant", plantName.getText().toString());

                plant.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){

                            Toast.makeText(addPlant.this, "Image saved", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(addPlant.this, "Image not saved", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getPhoto();
            }
        }
    }


    // method to jump to the camera
    public void getPhoto() {

        //jump to media store
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
        //startActivity(intent);

        //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //startActivityForResult(intent,1);
    }
    // menu option
    @Override // create the menu action bar
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.camera_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.help){ // if user click help button
            startActivity(new Intent(addPlant.this,PopHelp3.class));


        }
        else if (item.getItemId() == R.id.takePicture){

            startActivity(new Intent(addPlant.this, Camera.class));
        }
        else if (item.getItemId() == R.id.getPhoto){

            getPhoto();
        }
        else if (item.getItemId() == R.id.logout){

            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } //if user click to see the weather


        return super.onOptionsItemSelected(item);
    }


}


