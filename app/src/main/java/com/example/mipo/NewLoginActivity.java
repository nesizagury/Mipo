package com.example.mipo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class NewLoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "NewLoginActivity" ;
    private Button loginButton;
    private Button signupButton;
    private EditText usernameET;
    private EditText passwordET;
    private String username;
    private String password;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);



        usernameET = (EditText) findViewById (R.id.username_et);
        passwordET = (EditText) findViewById (R.id.password_et);

        loginButton = (Button) findViewById (R.id.button_login);
        signupButton = (Button) findViewById (R.id.button_signup);


        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                login();
                break;
            case R.id.button_signup:
                Intent intent = new Intent(this, CreateProfileActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void login() {
        username = usernameET.getText ().toString ();
        password = passwordET.getText ().toString ();

        List<ParseUser> list = new ArrayList<ParseUser>();
        ParseQuery<ParseUser> query1 = ParseUser.getQuery ();
        try {
            list = query1.find ();
        } catch (ParseException e) {
            Toast.makeText(this, "Error " + e, Toast.LENGTH_SHORT).show();
        }
        boolean exists = false;
        for (ParseUser user : list) {
            if (user.getUsername ().equals (username)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            try {
                ParseUser.logIn (username, password);
//                Intent serviceIntent = new Intent(this, Service.class);
//                serviceIntent.setAction(Service.ACTION_1);
//                serviceIntent.putExtra("ObjectId", ParseUser.getCurrentUser().getObjectId());
//                Log.e(TAG, "ObjectId " + ParseUser.getCurrentUser().getObjectId());
//                startService(serviceIntent);
                Toast.makeText (getApplicationContext (), "Successfully Loged in", Toast.LENGTH_SHORT).show ();
                Intent intent = new Intent (this, MainPageActivity.class);
                startActivity (intent);
                finish ();
            } catch (ParseException e1) {
                Toast.makeText (getApplicationContext (), "Wrong Password, try again :)", Toast.LENGTH_SHORT).show ();
            }
        } else {
            Toast.makeText (getApplicationContext (), "This user does not exist, try again :)", Toast.LENGTH_SHORT).show ();
        }
    }
}
