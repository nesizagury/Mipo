package com.example.mipo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mipo.model.Profile;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateProfileActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "CreateProfileActivity";
    TextView tv_username;
    TextView tv_password;
    TextView tv_create;
    EditText et_username;
    EditText et_password;
    EditText et_name;
    EditText et_age;
    EditText et_status;
    EditText et_nation;
    EditText et_height;
    EditText et_weight;
    EditText et_body_type;
    EditText et_rs_status;
    EditText et_looking_for;
    EditText et_about;
    Button btn_next;
    Button btn_next1;
    Button btn_next2;
    Button btn_pic;
    ImageView pic;
    LinearLayout login_second;
    LinearLayout login_third;
    private String username;
    private String password;
    private static final int SELECT_PICTURE = 1;
    String picturePath;
    private boolean pictureSelected;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        tv_create = (TextView) findViewById(R.id.tv_create);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_password = (TextView) findViewById(R.id.tv_password);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_status = (EditText) findViewById(R.id.et_status);
        et_nation = (EditText) findViewById(R.id.et_nation);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_body_type = (EditText) findViewById(R.id.et_body_type);
        et_rs_status = (EditText) findViewById(R.id.et_rs_status);
        et_looking_for = (EditText) findViewById(R.id.et_looking_for);
        et_about = (EditText) findViewById(R.id.et_about);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next1 = (Button) findViewById(R.id.btn_next1);
        btn_next2 = (Button) findViewById(R.id.btn_next2);
        btn_pic = (Button) findViewById(R.id.btn_pic);
        pic = (ImageView) findViewById(R.id.pic);
        btn_next.setOnClickListener(this);
        btn_next1.setOnClickListener(this);
        btn_next2.setOnClickListener(this);
        btn_pic.setOnClickListener(this);
        login_second = (LinearLayout) findViewById(R.id.include_login_second);
        //login_second.setVisibility(View.GONE);
        login_third = (LinearLayout) findViewById(R.id.include_login_third);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                dialog = new ProgressDialog(this);
                dialog.setMessage("Uploading...");
                dialog.show();
                signup();
                break;
            case R.id.btn_next1:
                gotoThird();
                break;
            case R.id.btn_next2:
                dialog = new ProgressDialog(this);
                dialog.setMessage("Uploading...");
                dialog.show();
                uploadProfile();
                break;
            case R.id.btn_pic:
                uploadPic();
                break;

        }

    }

    private void uploadPic() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            pic.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            pic.setVisibility(View.VISIBLE);
            pictureSelected = true;


        }
    }

    private void uploadProfile() {
        Profile profile = new Profile();

        if (et_looking_for.getText().length() != 0) {

            profile.setName(et_name.getText().toString());
            profile.setAge(et_age.getText().toString());
            profile.setStatus(et_status.getText().toString());
            profile.setNation(et_nation.getText().toString());
            profile.setHeight(et_height.getText().toString());
            profile.setWeight(et_weight.getText().toString());
            profile.setBody_type(et_body_type.getText().toString());
            profile.setRelationship_status(et_rs_status.getText().toString());
            profile.setLooking_for(et_looking_for.getText().toString());
            profile.setAbout(et_about.getText().toString());

            if (pictureSelected) {
                Bitmap bitmap = pic.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] image = stream.toByteArray();
                ParseFile file = new ParseFile("picturePath", image);
                try {
                    file.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                profile.put("pic", file);
            }

            profile.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Profile was created successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.e(TAG, "Not saved!!! " + e.toString());
                        Toast.makeText(CreateProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "Please fill the  empty fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoThird() {
        if (et_name.getText().length() != 0 && et_age.getText().length() != 0) {
            login_second.setVisibility(View.GONE);
            login_third.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(CreateProfileActivity.this, "Please enter name and age", Toast.LENGTH_SHORT).show();
        }
    }

    public void signup() {
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            Toast.makeText(CreateProfileActivity.this, "Please enter text", Toast.LENGTH_SHORT).show();
        } else {

            List<ParseUser> list = new ArrayList<ParseUser>();
            ParseQuery<ParseUser> query1 = ParseUser.getQuery();
            try {
                list = query1.find();
            } catch (ParseException e) {
                Toast.makeText(this, "Error " + e, Toast.LENGTH_SHORT).show();
            }
            boolean exists = false;
            for (ParseUser user : list) {
                if (user.getUsername().equals(username)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                Toast.makeText(getApplicationContext(), "Username exists, try again :)", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.isNew();
                try {
                    user.signUp();
                    Toast.makeText(getApplicationContext(), "Successfully Signed up", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    tv_create.setVisibility(View.GONE);
                    tv_username.setVisibility(View.GONE);
                    tv_password.setVisibility(View.GONE);
                    et_password.setVisibility(View.GONE);
                    et_username.setVisibility(View.GONE);
                    btn_next.setVisibility(View.GONE);
                    login_second.setVisibility(View.VISIBLE);
                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(), "Error" + e, Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        }
    }
}
