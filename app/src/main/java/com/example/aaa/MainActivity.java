package com.example.aaa;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {



	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		connectToParse();

		// Intent intent = new Intent(MainActivity.this,FileTestActivity.class);
		// startActivity(intent);

	}



	public void connectToParse() {

		ParseObject.registerSubclass(com.example.aaa.Message.class);
		Parse.initialize(this, "VxNKZoBXZHTyFmx3cRhLFpGOQNMl9NMySrE6PiLP", "lZaGELhSL2Fon7Kd7TyouMBaA4zrBPg1Hm5GQYu2");
		ParseUser.enableAutomaticUser();
		ParseACL defaultAcl = new ParseACL();
		defaultAcl.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultAcl, true);

		if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())){
			Intent intent = new Intent(MainActivity.this,LoginActivity.class);
			startActivity(intent);
			finish();
		}

		else {

			ParseUser currentUser = ParseUser.getCurrentUser();

			if(currentUser != null) {

				Toast.makeText(getApplicationContext(), "Successfully Signed in", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this,MainPageActivity.class);
				startActivity(intent);
				finish();

			}

			else {

				Intent intent = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();

			}

		}


	}







}