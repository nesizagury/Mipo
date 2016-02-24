package com.example.mipo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends Activity implements View.OnClickListener {

    TextView tv_create;
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
    Button btn_next1;
    Button btn_next2;
    Button btn_pic;
    ImageView pic;
    LinearLayout login_second;
    LinearLayout login_third;
    private static final int SELECT_PICTURE = 1;
    String picturePath;
    private boolean pictureSelected;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.login);
        tv_create = (TextView) findViewById (R.id.tv_create);
        et_name = (EditText) findViewById (R.id.et_name);
        et_age = (EditText) findViewById (R.id.et_age);
        et_status = (EditText) findViewById (R.id.et_status);
        et_nation = (EditText) findViewById (R.id.et_nation);
        et_height = (EditText) findViewById (R.id.et_height);
        et_weight = (EditText) findViewById (R.id.et_weight);
        et_body_type = (EditText) findViewById (R.id.et_body_type);
        et_rs_status = (EditText) findViewById (R.id.et_rs_status);
        et_looking_for = (EditText) findViewById (R.id.et_looking_for);
        et_about = (EditText) findViewById (R.id.et_about);
        btn_next1 = (Button) findViewById (R.id.btn_next1);
        btn_next2 = (Button) findViewById (R.id.btn_next2);
        btn_pic = (Button) findViewById (R.id.btn_pic);
        pic = (ImageView) findViewById (R.id.pic);
        btn_next1.setOnClickListener (this);
        btn_next2.setOnClickListener (this);
        btn_pic.setOnClickListener (this);
        login_second = (LinearLayout) findViewById (R.id.include_login_second);
        login_second.setVisibility (View.VISIBLE);
        login_third = (LinearLayout) findViewById (R.id.include_login_third);
        if (GlobalVariables.CUSTOMER_PHONE_NUM != null && !GlobalVariables.CUSTOMER_PHONE_NUM.equals ("")) {
            downloadPreviousProfileData ();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.btn_next1:
                gotoThird ();
                pic.setVisibility (View.VISIBLE);
                break;
            case R.id.btn_next2:
                uploadProfile ();
                break;
            case R.id.btn_pic:
                uploadPic ();
                break;

        }
    }

    private void uploadPic() {
        Intent i = new Intent (
                                      Intent.ACTION_PICK,
                                      MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult (i, SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData ();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver ().query (selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst ();
            int columnIndex = cursor.getColumnIndex (filePathColumn[0]);
            picturePath = cursor.getString (columnIndex);
            cursor.close ();
            pic.setImageBitmap (BitmapFactory.decodeFile (picturePath));
            pictureSelected = true;
            bmp = BitmapFactory.decodeFile (picturePath);
        }
    }

    private void uploadProfile() {
        if (et_looking_for.getText ().length () != 0) {
            ParseQuery<Profile> query = ParseQuery.getQuery ("Profile");
            query.whereEqualTo ("number", GlobalVariables.CUSTOMER_PHONE_NUM);
            query.getFirstInBackground (new GetCallback<Profile> () {
                public void done(Profile profile, ParseException e) {
                    if (e == null) {
                        profile.setName (et_name.getText ().toString ());
                        profile.setAge (et_age.getText ().toString ());
                        profile.setStatus (et_status.getText ().toString ());
                        profile.setNation (et_nation.getText ().toString ());
                        profile.setHeight (et_height.getText ().toString ());
                        profile.setWeight (et_weight.getText ().toString ());
                        profile.setBody_type (et_body_type.getText ().toString ());
                        profile.setRelationship_status (et_rs_status.getText ().toString ());
                        profile.setLooking_for (et_looking_for.getText ().toString ());
                        profile.setAbout (et_about.getText ().toString ());
                        if (pictureSelected) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream ();
                            bmp.compress (Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] image = stream.toByteArray ();
                            ParseFile file = new ParseFile ("picturePath", image);
                            try {
                                file.save ();
                            } catch (ParseException e1) {
                                e1.printStackTrace ();
                            }
                            profile.setPic (file);
                        }
                        profile.saveInBackground ();
                        Toast.makeText (getApplicationContext (), "Profile was created successfully!", Toast.LENGTH_SHORT).show ();
                    } else{
                        e.printStackTrace ();
                    }
                    finish ();
                }
            });
        }
    }

    private void gotoThird() {
        if (et_name.getText ().length () != 0 && et_age.getText ().length () != 0) {
            login_second.setVisibility (View.GONE);
            login_third.setVisibility (View.VISIBLE);
        } else {
            Toast.makeText (EditProfileActivity.this, "Please enter name and age", Toast.LENGTH_SHORT).show ();
        }
    }

    private void downloadPreviousProfileData() {
        ParseQuery<Profile> query = ParseQuery.getQuery ("Profile");
        query.whereEqualTo ("number", GlobalVariables.CUSTOMER_PHONE_NUM);
        query.getFirstInBackground (new GetCallback<Profile> () {
            public void done(Profile profile, ParseException e) {
                if (e == null) {
                    et_name.setText (profile.getName ());
                    et_age.setText (profile.getAge ());
                    et_height.setText (profile.getHeight ());
                    ParseFile imageFile = (ParseFile) profile.getPic ();
                    if (imageFile != null) {
                        imageFile.getDataInBackground (new GetDataCallback () {
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                    Bitmap bmp = BitmapFactory
                                                         .decodeByteArray (
                                                                                  data, 0,
                                                                                  data.length);
                                    pic.setImageBitmap (bmp);
                                } else {
                                    e.printStackTrace ();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
