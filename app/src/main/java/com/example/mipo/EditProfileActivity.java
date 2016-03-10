package com.example.mipo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditProfileActivity extends Activity implements View.OnClickListener {

    TextView tv_create;
    EditText et_name;
    EditText et_age;
    EditText et_height;
    EditText et_weight;
    EditText et_about;
    EditText et_status;
    Button btn_next1;
    Button btn_next2;
    Button btn_pic;
    ImageView pic;
    LinearLayout edit_profile_first_sub_part;
    LinearLayout edit_profile_second_sub_part;
    private static final int SELECT_PICTURE = 1;
    private boolean pictureSelected = false;
    Bitmap bmp;

    Spinner spinner_Looking_for;
    Spinner spinner_Body_type;
    Spinner spinner_Ethnicity;
    Spinner spinner_realationship_Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_edit_profile);
        tv_create = (TextView) findViewById (R.id.tv_create);
        et_name = (EditText) findViewById (R.id.et_name);
        et_status = (EditText) findViewById (R.id.et_status);
        et_age = (EditText) findViewById (R.id.et_age);
        et_height = (EditText) findViewById (R.id.et_height);
        et_weight = (EditText) findViewById (R.id.et_weight);
        et_about = (EditText) findViewById (R.id.et_about);
        btn_next1 = (Button) findViewById (R.id.btn_next1);
        btn_next2 = (Button) findViewById (R.id.btn_next2);
        btn_pic = (Button) findViewById (R.id.btn_pic);
        pic = (ImageView) findViewById (R.id.pic);
        setSpinners ();

        btn_next1.setOnClickListener (this);
        btn_next2.setOnClickListener (this);
        btn_pic.setOnClickListener (this);
        edit_profile_first_sub_part = (LinearLayout) findViewById (R.id.include_login_first);
        edit_profile_first_sub_part.setVisibility (View.VISIBLE);
        edit_profile_second_sub_part = (LinearLayout) findViewById (R.id.include_login_second);
        if (GlobalVariables.CUSTOMER_PHONE_NUM != null && !GlobalVariables.CUSTOMER_PHONE_NUM.equals ("")) {
            downloadPreviousProfileData ();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.btn_next1:
                gotoSecondSubPart ();
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
            ParcelFileDescriptor parcelFileDescriptor =
                    null;
            try {
                parcelFileDescriptor = this.getContentResolver ().openFileDescriptor (selectedImage, "r");
            } catch (FileNotFoundException e) {
                e.printStackTrace ();
            }
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor ();
            Bitmap image = BitmapFactory.decodeFileDescriptor (fileDescriptor);
            try {
                parcelFileDescriptor.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
            Matrix matrix = new Matrix ();
            int angleToRotate = StaticMethods.getOrientation (selectedImage, this);
            matrix.postRotate (angleToRotate);
            Bitmap rotatedBitmap = Bitmap.createBitmap (image,
                                                               0,
                                                               0,
                                                               image.getWidth (),
                                                               image.getHeight (),
                                                               matrix,
                                                               true);
            pic.setImageBitmap (rotatedBitmap);
            pictureSelected = true;
            bmp = rotatedBitmap;
        }
    }

    private void uploadProfile() {
        ParseQuery<Profile> query = ParseQuery.getQuery ("Profile");
        query.whereEqualTo ("number", GlobalVariables.CUSTOMER_PHONE_NUM);
        query.getFirstInBackground (new GetCallback<Profile> () {
            public void done(Profile profile, ParseException e) {
                if (e == null) {
                    profile.setName (et_name.getText ().toString ());
                    profile.setAge (et_age.getText ().toString ());
                    profile.setStatus (et_status.getText ().toString ());
                    if (spinner_Ethnicity.getVisibility () == View.VISIBLE) {
                        if (getEthnicity () != null) {
                            profile.setEthnicity (getEthnicity ());
                        }
                    }
                    profile.setHeight (et_height.getText ().toString ());
                    profile.setWeight (et_weight.getText ().toString ());
                    if (spinner_Body_type.getVisibility () == View.VISIBLE) {
                        if (getBodyType () != null) {
                            profile.setBody_type (getBodyType ());
                        }
                    }
                    if (spinner_realationship_Status.getVisibility () == View.VISIBLE) {
                        if(getRelationshipStatus () != null) {
                            profile.setRelationship_status (getRelationshipStatus ());
                        }
                    }
                    if (spinner_Looking_for.getVisibility () == View.VISIBLE) {
                        if(getLookingFor () != null) {
                            profile.setLooking_for (getLookingFor ());
                        }
                    }
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
                    try {
                        profile.save ();
                    } catch (ParseException e1) {
                        e1.printStackTrace ();
                    }
                    GlobalVariables.currentUser.setName (et_name.getText ().toString ());
                    GlobalVariables.currentUser.setAge (et_age.getText ().toString ());
                    GlobalVariables.currentUser.setAbout (et_about.getText ().toString ());
                    GlobalVariables.currentUser.setHeight (et_height.getText ().toString ());
                    GlobalVariables.currentUser.setWeight (et_weight.getText ().toString ());
                    GlobalVariables.currentUser.setStatus (et_status.getText ().toString ());
                    GlobalVariables.currentUser.setLooking_for (getLookingFor ());
                    GlobalVariables.currentUser.setRelationship_status (getRelationshipStatus ());
                    GlobalVariables.currentUser.setBody_type (getBodyType ());
                    GlobalVariables.currentUser.setEtnicity (getEthnicity ());
                    GlobalVariables.currentUser.setPicUrl (profile.getPic ().getUrl ());
                    Toast.makeText (getApplicationContext (), getResources ().getString (R.string.successProfileCreated), Toast.LENGTH_SHORT).show ();
                } else {
                    Toast.makeText (getApplicationContext (), "Profile was not saved!, error", Toast.LENGTH_SHORT).show ();
                    e.printStackTrace ();
                }
                finish ();
            }
        });
    }

    private void gotoSecondSubPart() {
        if (et_name.getText ().length () != 0 && et_age.getText ().length () != 0) {
            edit_profile_first_sub_part.setVisibility (View.GONE);
            edit_profile_second_sub_part.setVisibility (View.VISIBLE);
        } else {
            Toast.makeText (EditProfileActivity.this, getResources ().getString (R.string.enterNameAge), Toast.LENGTH_SHORT).show ();
        }
    }

    private void downloadPreviousProfileData() {
        ParseQuery<Profile> query = ParseQuery.getQuery ("Profile");
        query.whereEqualTo ("number", GlobalVariables.CUSTOMER_PHONE_NUM);
        query.getFirstInBackground (new GetCallback<Profile> () {
            public void done(Profile profile, ParseException e) {
                if (e == null) {
                    et_name.setText (profile.getName ());
                    et_name.setSelection (et_name.getText ().length ());
                    et_age.setText (profile.getAge ());
                    et_age.setSelection (et_age.getText ().length ());
                    et_height.setText (profile.getHeight ());
                    et_height.setSelection (et_height.getText ().length ());
                    if (profile.getStatus () != null) {
                        et_status.setText (profile.getStatus ());
                        et_status.setSelection (et_status.getText ().length ());
                    }
                    if (profile.getWeight () != null) {
                        et_weight.setText (profile.getWeight ());
                        et_weight.setSelection (et_weight.getText ().length ());
                    }
                    if (profile.getAbout () != null) {
                        et_about.setText (profile.getAbout ());
                        et_about.setSelection (et_about.getText ().length ());
                    }

                    if (spinner_Looking_for.getVisibility () == View.INVISIBLE) {
                        String current_looking_for = profile.getLooking_for ();
                        if (current_looking_for != null) {
                            for (int i = 0; i < GlobalVariables.array_spinner_filter_Looking_for.length; i++) {
                                if (current_looking_for.equals (GlobalVariables.array_spinner_filter_Looking_for[i])) {
                                    spinner_Looking_for.setSelection (i);
                                    spinner_Looking_for.setVisibility (View.VISIBLE);
                                    break;
                                }
                            }
                        }
                    }

                    if (spinner_Body_type.getVisibility () == View.INVISIBLE) {
                        String current_Body_type = profile.getBody_type ();
                        if (current_Body_type != null) {
                            for (int i = 0; i < GlobalVariables.array_spinner_filter_Body_type.length; i++) {
                                if (current_Body_type.equals (GlobalVariables.array_spinner_filter_Body_type[i])) {
                                    spinner_Body_type.setSelection (i);
                                    spinner_Body_type.setVisibility (View.VISIBLE);
                                    break;
                                }
                            }
                        }
                    }

                    if (spinner_Ethnicity.getVisibility () == View.INVISIBLE) {
                        String current_Ethnicity = profile.getEthnicity ();
                        if (current_Ethnicity != null) {
                            for (int i = 0; i < GlobalVariables.array_spinner_filter_Ethnicity.length; i++) {
                                if (current_Ethnicity.equals (GlobalVariables.array_spinner_filter_Ethnicity[i])) {
                                    spinner_Ethnicity.setSelection (i);
                                    spinner_Ethnicity.setVisibility (View.VISIBLE);
                                    break;
                                }
                            }
                        }
                    }

                    if (spinner_realationship_Status.getVisibility () == View.INVISIBLE) {
                        String current_realationship_Status = profile.getRelationship_status ();
                        if (current_realationship_Status != null) {
                            for (int i = 0; i < GlobalVariables.array_spinner_filter_Relationship_Status.length; i++) {
                                if (current_realationship_Status.equals (GlobalVariables.array_spinner_filter_Relationship_Status[i])) {
                                    spinner_realationship_Status.setSelection (i);
                                    spinner_realationship_Status.setVisibility (View.VISIBLE);
                                    break;
                                }
                            }
                        }
                    }

                    if (!pictureSelected) {
                        ParseFile imageFile = (ParseFile) profile.getPic ();
                        if (imageFile != null) {
                            imageFile.getDataInBackground (new GetDataCallback () {
                                public void done(byte[] data, ParseException e) {
                                    if (e == null) {
                                        bmp = BitmapFactory
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
            }
        });
    }

    private void setSpinners() {
        spinner_Looking_for = (Spinner) findViewById (R.id.spinner_looking_for);
        ArrayAdapter adapter = new ArrayAdapter (this,
                                                        android.R.layout.simple_spinner_item, getResources ().getStringArray (R.array.lookingForSpinner1));
        spinner_Looking_for.setAdapter (adapter);

        spinner_Body_type = (Spinner) findViewById (R.id.spinner_body_type);
        ArrayAdapter adapter2 = new ArrayAdapter (this,
                                                         android.R.layout.simple_spinner_item, getResources ().getStringArray (R.array.bodyTypeSpinner));
        spinner_Body_type.setAdapter (adapter2);

        spinner_Ethnicity = (Spinner) findViewById (R.id.spinner_ethnicity);
        ArrayAdapter adapter3 = new ArrayAdapter (this,
                                                         android.R.layout.simple_spinner_item, getResources ().getStringArray (R.array.ethnicitySpinner));
        spinner_Ethnicity.setAdapter (adapter3);

        spinner_realationship_Status = (Spinner) findViewById (R.id.spinner_rs_status);
        ArrayAdapter adapter4 = new ArrayAdapter (this,
                                                         android.R.layout.simple_spinner_item, getResources ().getStringArray (R.array.relationshipSpinner));
        spinner_realationship_Status.setAdapter (adapter4);
    }

    public String getLookingFor() {
        if (spinner_Looking_for.getVisibility () == View.INVISIBLE)
            return "";
        else {
            int position = spinner_Looking_for.getSelectedItemPosition ();
            if (position == 0) {
                return null;
            }
            return GlobalVariables.array_spinner_profile_Looking_for[position];
        }
    }

    public String getBodyType() {
        if (spinner_Body_type.getVisibility () == View.INVISIBLE)
            return "";
        else {
            int position = spinner_Body_type.getSelectedItemPosition ();
            if (position == 0) {
                return null;
            }
            return GlobalVariables.array_spinner_profile_Body_type[position];
        }
    }

    public String getEthnicity() {
        if (spinner_Ethnicity.getVisibility () == View.INVISIBLE)
            return "";
        else {
            int position = spinner_Ethnicity.getSelectedItemPosition ();
            if (position == 0) {
                return null;
            }
            return GlobalVariables.array_spinner_profile_Ethnicity[position];
        }
    }


    public String getRelationshipStatus() {
        if (spinner_realationship_Status.getVisibility () == View.INVISIBLE)
            return "";
        else {
            int position = spinner_realationship_Status.getSelectedItemPosition ();
            if (position == 0) {
                return null;
            }
            return GlobalVariables.array_spinner_profile_Relationship_Status[position];
        }
    }
}
