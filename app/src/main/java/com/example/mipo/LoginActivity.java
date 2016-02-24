package com.example.mipo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button logginButton;
    String number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);

        number = readFromFile ("verify");
        if (number != null && !number.equals ("")) {
            GlobalVariables.CUSTOMER_PHONE_NUM = number;
        }
        logginButton = (Button) findViewById (R.id.button_loggin);
        if (number != null && !number.equals ("")) {
            logginButton.setText ("Log In as " + number);
        } else {
            logginButton.setText ("Log In as guest");
        }
        logginButton.setOnClickListener (this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.button_loggin:
                if (number != null && !number.equals ("")) {
                    login ();
                }
                else {
                    guestLogin ();
                }
                break;
        }
    }

    public void guestLogin() {
        try {
            ParseUser.logIn ("m", "d"); // m,d = bar refaeli (default user).
        } catch (ParseException e1) {
            e1.printStackTrace ();
        }
        Toast.makeText (getApplicationContext (), "Successfully Logged in", Toast.LENGTH_SHORT).show ();
        Intent intent = new Intent (this, MainPageActivity.class);
        startActivity (intent);
        finish ();

    }

    public void login() {
        try {
            ParseUser.logIn (GlobalVariables.CUSTOMER_PHONE_NUM,
                                    GlobalVariables.CUSTOMER_PHONE_NUM);
            Toast.makeText (getApplicationContext (),
                                   "Successfully Logged in",
                                   Toast.LENGTH_SHORT).show ();
            Intent intent = new Intent (LoginActivity.this, MainPageActivity.class);
            startActivity (intent);
            finish ();
        } catch (ParseException e) {
            e.printStackTrace ();
        }

    }

    private String readFromFile(String file) {
        String s = "";
        try {
            InputStream inputStream = openFileInput (file + ".txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader (inputStream);
                BufferedReader bufferedReader = new BufferedReader (inputStreamReader);
                String receiveString = "";
                while ((receiveString = bufferedReader.readLine ()) != null) {
                    s = receiveString;
                }
                inputStream.close ();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return s;
    }
}
