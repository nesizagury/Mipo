package com.example.mipo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

    Button smsVerify;
    Button updateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_menu);
        smsVerify = (Button) findViewById (R.id.smsVerification);
        updateProfile = (Button) findViewById (R.id.editProfile);
        if (GlobalVariables.CUSTOMER_PHONE_NUM != null && !GlobalVariables.CUSTOMER_PHONE_NUM.isEmpty ()) {
            smsVerify.setVisibility (View.GONE);
        } else {
            updateProfile.setVisibility (View.GONE);
        }
    }

    public void editProfile(View v) {
        Intent intent = new Intent (MenuActivity.this, EditProfileActivity.class);
        startActivity (intent);
        finish ();
    }

    public void smsVerification(View v) {
        Intent intent = new Intent (MenuActivity.this, SmsSignUpActivity.class);
        startActivity (intent);
        finish ();
    }
}
