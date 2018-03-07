package com.farmingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements  View.OnClickListener, View.OnKeyListener{

    Boolean signUpModeActive = true; // boolean to change the view of the button
    TextView changeSignupModeTextView;
    EditText passwordEditText;

    // function to jump to profile activity
    public void profileActivity(){

        Intent intent =  new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent){ // method in case the user click on the screen after the password is input

        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){ // check if key has been entered and enter down to avoid duplication

            signUp(view);
        }
        return false;
    }

    @Override
    public void onClick(View view) { // button to swap between login and register

        if(view.getId() == R.id.changeSignupModeTextView){

            Button signupButton = (Button) findViewById(R.id.signupButton);

            if (signUpModeActive){

                signUpModeActive = false;
                signupButton.setText("Login");
                changeSignupModeTextView.setText("Signup");

            } else {

                signUpModeActive = true;
                signupButton.setText("Signup");
                changeSignupModeTextView.setText("Login");

            }
        } else if (view.getId() == R.id.backgroundRelativeLayout || view.getId() == R.id.logoImageView){

            // when user tap background or logo, method will remove the keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); // get access to the keyboard
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //call the method toshut down the keyboard
        }
    }

    public void signUp(View view){

        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText); // username field

        // show pop up if both field are empty
        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("") ) {

            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();

        } else {

            if (signUpModeActive) {

                ParseUser user = new ParseUser(); // calling parse server to send the data to Parse

            user.setUsername(usernameEditText.getText().toString()); // username field
            user.setPassword(passwordEditText.getText().toString()); // password field

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {

                        Log.i("Signup", "Successful");

                        profileActivity(); // jump to the profile when user is login

                    } else {

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        } else {
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user != null){

                            Log.i("Signup", "login successful");

                            profileActivity(); // jump to the profile when user signed up

                        } else {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }

    }

    @Override // onclick method to make the button clickable
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeSignupModeTextView = (TextView) findViewById(R.id.changeSignupModeTextView);

        changeSignupModeTextView.setOnClickListener(this);

        passwordEditText = (EditText) findViewById(R.id.passwordEditText); // password field

        passwordEditText.setOnKeyListener(this);

        RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayout); // on click method for the Relative layout
        ImageView logoImageView = (ImageView) findViewById(R.id.logoImageView); // on click method for the logo

        backgroundRelativeLayout.setOnClickListener(this);

        logoImageView.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null){ // if user is already connected calling the parse server to display profileActivity

            profileActivity();
        }


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

}
