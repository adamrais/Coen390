package com.farmingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

/**
 * Created by adamrais on 18-03-31.
 */

public class plant_4 extends AppCompatActivity {

    public TextView plantName;
    ImageView plantImage;
    public TextView plantDescription;
    public ParseObject plant = new ParseObject("plant");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_1);

        plantDescription = (TextView) findViewById(R.id.plantDescription);
        String description = "DESCRIPTION OF THE PLANT";
        String infoPlant = "Camellia is a genus of flowering plants in the family Theaceae. They are found in eastern and southern Asia, from the Himalayas east to Japan and Indonesia. There are 100–300 described species, with some controversy over the exact number.";
        String Family = "Family of the plant: Theaceae";
        String Water = "Water needed: Average";
        String Form = "Form: Shrub, Tree";
        String Fertilization = "Fertilization: Average";
        String Exposure = "Exposure: Half Shade";
        String Soil = "Soil: Medium textured (Acid)";

        plantDescription.setText(description + "\n\n" + infoPlant + "\n\n" + Family + "\n\n" + Water + "\n\n" + Form + "\n\n" + Fertilization + "\n\n" + Exposure +
                "\n\n" + Soil);

        plantImage = (ImageView) findViewById(R.id.plantImage);
        //loadImageFromUrl(url);
        plantDescription.setMovementMethod(new ScrollingMovementMethod());
        plantImage.setImageResource(R.drawable.camellia); // image of the plant

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // retrieve name of plant with intent extra
        plantName = (TextView) findViewById(R.id.plantName);

        if(bundle != null){
            String getName = (String) bundle.get("Camellia");
            plantName.setText(getName);
        }
    }
    // this is the menu bar
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_2, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.help){ // if user click help button
            startActivity(new Intent(plant_4.this,PopHelp5.class));
            // pop up message that will display help for the user need to implement that asap
        }
        else if (item.getItemId() == R.id.logout){

            ParseUser.logOut(); // function that log out the user
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
    public void savePlant(View view) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.camellia);
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteArray = stream.toByteArray();

        // Create the ParseFile
        final ParseFile file = new ParseFile("image.jpg", byteArray);
        // Upload the image into Parse Cloud
        file.saveInBackground();


        plant.put("username", ParseUser.getCurrentUser().getUsername()); // the name of the person that has the plant
        plant.put("plant", plantName.getText().toString());
        plant.put("image", file);


        plant.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {

                    Toast.makeText(plant_4.this, "Plant was added", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(plant_4.this, "Plant was not added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
