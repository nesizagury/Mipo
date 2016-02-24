package com.example.mipo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sinch.verification.CodeInterceptionException;
import com.sinch.verification.Config;
import com.sinch.verification.IncorrectCodeException;
import com.sinch.verification.InvalidInputException;
import com.sinch.verification.ServiceErrorException;
import com.sinch.verification.SinchVerification;
import com.sinch.verification.Verification;
import com.sinch.verification.VerificationListener;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public class SmsSignUpActivity extends Activity {

    private static final int SELECT_PICTURE = 1;
    Spinner s;
    private String array_spinner[];
    private String array_spinnerAge[];
    String username;
    EditText phoneET;
    String phone_number_to_verify;
    String phone_number_no_country_prefix;
    String area;
    TextView phoneTV;
    TextView usernameTV;
    EditText usernameTE;
    Button upload_button;
    Button signup;
    ImageView imageV;
    TextView optionalTV;
    TextView expTV;
    boolean image_selected;
    Profile previousDataFound;
    TextView ageTV;
    TextView heightTV;
    Spinner ageS;
    EditText heightET;
    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sms_login);

        ageTV = (TextView) findViewById (R.id.ageTV);
        heightTV = (TextView) findViewById (R.id.heightTV);
        ageS = (Spinner) findViewById (R.id.ageS);
        heightET = (EditText) findViewById (R.id.heightET);

        array_spinnerAge = new String[50];
        for (int i = 0; i < array_spinnerAge.length; i++)
            array_spinnerAge[i] = i + 18 + "";

        ArrayAdapter adapterAge = new ArrayAdapter (this,
                                                           android.R.layout.simple_spinner_item,
                                                           array_spinnerAge);
        ageS.setAdapter (adapterAge);


        array_spinner = new String[6];
        array_spinner[0] = "050";
        array_spinner[1] = "052";
        array_spinner[2] = "053";
        array_spinner[3] = "054";
        array_spinner[4] = "055";
        array_spinner[5] = "058";

        s = (Spinner) findViewById (R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter (this,
                                                        android.R.layout.simple_spinner_item,
                                                        array_spinner);
        s.setAdapter (adapter);

        usernameTV = (TextView) findViewById (R.id.usernameTV);
        usernameTE = (EditText) findViewById (R.id.usernameTE);
        phoneET = (EditText) findViewById (R.id.phoneET);
        phoneTV = (TextView) findViewById (R.id.phoneTV);
        imageV = (ImageView) findViewById (R.id.imageV);

        phoneET.setOnEditorActionListener (new TextView.OnEditorActionListener () {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null &&
                             (event.getKeyCode () == KeyEvent.KEYCODE_ENTER)) ||
                            (actionId == EditorInfo.IME_ACTION_DONE)) {
                    area = s.getSelectedItem ().toString ();
                    username = usernameTE.getText ().toString ();
                    phone_number_to_verify = getNumber (phoneET.getText ().toString (), area);
                    phone_number_no_country_prefix = area + phoneET.getText ().toString ();
                    getUserPreviousDetails ();
                    smsVerify (phone_number_to_verify);
                }
                return false;
            }
        });

        usernameTE.setOnEditorActionListener (new TextView.OnEditorActionListener () {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode () == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    usernameTE.setVisibility (View.INVISIBLE);
                    usernameTV.setVisibility (View.INVISIBLE);
                    imageV = (ImageView) findViewById (R.id.imageV);
                    imageV.setVisibility (View.VISIBLE);
                    upload_button = (Button) findViewById (R.id.upload_button);
                    upload_button.setVisibility (View.VISIBLE);
                    signup = (Button) findViewById (R.id.button2);
                    signup.setVisibility (View.VISIBLE);
                    optionalTV = (TextView) findViewById (R.id.optionalTV);
                    optionalTV.setVisibility (View.VISIBLE);
                    ageS.setVisibility (View.VISIBLE);
                    ageTV.setVisibility (View.VISIBLE);
                    heightTV.setVisibility (View.VISIBLE);
                    heightET.setVisibility (View.VISIBLE);
                }
                return false;
            }
        });
    }

    public void Signup(View view) {
        username = usernameTE.getText ().toString ();
        Profile profile;
        if (previousDataFound != null) {
            profile = previousDataFound;
            ParseUser.logOut ();
            try {
                ParseUser.logIn (phone_number_no_country_prefix, phone_number_no_country_prefix);
            } catch (ParseException e) {
                e.printStackTrace ();
            }
        } else {
            profile = new Profile ();
            ParseUser user = new ParseUser ();
            user.setUsername (phone_number_no_country_prefix);
            user.setPassword (phone_number_no_country_prefix);
            try {
                user.signUp ();
            } catch (ParseException e) {
                e.printStackTrace ();
            }
            profile.setNumber (area + phoneET.getText ().toString ());
            profile.setUser (user);
        }
        profile.setName (username);
        profile.setAge (ageS.getSelectedItem ().toString ());
        profile.setHeight (String.valueOf (heightET.getText ().toString ()));
        profile.setLastSeen (new Date ());
        if (image_selected) {
            imageV.buildDrawingCache ();
            ByteArrayOutputStream stream = new ByteArrayOutputStream ();
            bmp.compress (CompressFormat.JPEG, 100, stream);
            byte[] image = stream.toByteArray ();
            ParseFile file = new ParseFile ("picturePath", image);
            try {
                file.save ();
            } catch (ParseException e) {
                e.printStackTrace ();
            }
            ParseACL parseAcl = new ParseACL ();
            parseAcl.setPublicReadAccess (true);
            parseAcl.setPublicWriteAccess (true);
            profile.setACL (parseAcl);
            profile.put ("pic", file);
        }
        try {
            profile.save ();
            GlobalVariables.CUSTOMER_PHONE_NUM = phone_number_no_country_prefix;
            Toast.makeText (getApplicationContext (), "Successfully Signed up", Toast.LENGTH_SHORT).show ();
            saveToFile (phone_number_no_country_prefix);
            MainPageActivity.downloadProfilesDataInBackGround ();
            finish ();
        } catch (ParseException e) {
            Toast.makeText (getApplicationContext (), " Error ): ", Toast.LENGTH_SHORT).show ();
            e.printStackTrace ();
        }
    }

    public String getNumber(String number, String area) {
        switch (area) {
            case "050":
                number = "97250" + number;
                break;
            case "052":
                number = "97252" + number;
                break;
            case "053":
                number = "97253" + number;
                break;
            case "054":
                number = "97254" + number;
                break;
            case "055":
                number = "97255" + number;
                break;
            case "058":
                number = "97258" + number;
                break;
        }
        return number;
    }

    public void imageUpload(View view) {
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
                parcelFileDescriptor = getContentResolver ().openFileDescriptor (selectedImage, "r");
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
            int angleToRotate = getOrientation (selectedImage);
            matrix.postRotate (angleToRotate);
            Bitmap rotatedBitmap = Bitmap.createBitmap (image,
                                                               0,
                                                               0,
                                                               image.getWidth (),
                                                               image.getHeight (),
                                                               matrix,
                                                               true);
            bmp = rotatedBitmap;
            imageV.setImageBitmap (rotatedBitmap);
            image_selected = true;
        }
    }

    public void smsVerify(String phone_number) {
        Config config = SinchVerification.config ().applicationKey ("b9ee3da5-0dc9-40aa-90aa-3d30320746f3").context (getApplicationContext ()).build ();
        VerificationListener listener = new MyVerificationListener ();
        Verification verification = SinchVerification.createSmsVerification (config, phone_number, listener);
        verification.initiate ();
    }

    class MyVerificationListener implements VerificationListener {
        @Override
        public void onInitiated() {

        }

        @Override
        public void onInitiationFailed(Exception e) {
            if (e instanceof InvalidInputException) {
                // Incorrect number provided
                e.printStackTrace ();
            } else if (e instanceof ServiceErrorException) {
                // Sinch service error
                e.printStackTrace ();
            } else {
                // Other system error, such as UnknownHostException in case of network error
                e.printStackTrace ();
            }
        }

        @Override
        public void onVerified() {
            Toast.makeText (getApplicationContext (), phone_number_no_country_prefix + " Verified!",
                                   Toast.LENGTH_SHORT).show ();
            usernameTV.setVisibility (View.VISIBLE);
            usernameTE.setVisibility (View.VISIBLE);
            phoneET.setVisibility (View.INVISIBLE);
            phoneTV.setVisibility (View.INVISIBLE);
            expTV = (TextView) findViewById (R.id.explanationTV);
            expTV.setVisibility (View.INVISIBLE);
            s.setVisibility (View.INVISIBLE);

        }

        @Override
        public void onVerificationFailed(Exception e) {
            if (e instanceof InvalidInputException) {
                e.printStackTrace ();
                // Incorrect number or code provided
                Toast.makeText (getApplicationContext (), "invalid phone number try again.", Toast.LENGTH_SHORT).show ();
            } else if (e instanceof CodeInterceptionException) {
                // Intercepting the verification code automatically failed, input the code manually with verify()
                e.printStackTrace ();
            } else if (e instanceof IncorrectCodeException) {
                e.printStackTrace ();
            } else if (e instanceof ServiceErrorException) {
                e.printStackTrace ();
            } else {
                e.printStackTrace ();
            }
        }
    }

    void saveToFile(String phone_number) {
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter (this.openFileOutput ("verify.txt", Context.MODE_MULTI_PROCESS));
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        PrintWriter writer = new PrintWriter (outputStreamWriter);
        writer.println (phone_number);

        try {
            outputStreamWriter.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private void getUserPreviousDetails() {
        ParseQuery<Profile> query = ParseQuery.getQuery ("Profile");
        query.whereEqualTo ("number", phone_number_no_country_prefix);
        query.findInBackground (new FindCallback<Profile> () {
            public void done(List<Profile> profiles, ParseException e) {
                if (e == null) {
                    if (profiles.size () > 0) {
                        previousDataFound = profiles.get (0);
                        if (usernameTE.getText ().toString ().isEmpty ()) {
                            usernameTE.setText (profiles.get (0).get ("name") + "");
                            usernameTE.setSelection (usernameTE.getText ().length ());
                        }
                        if (!image_selected) {
                            ParseFile imageFile = (ParseFile) profiles.get (0).get ("pic");
                            if (imageFile != null) {
                                imageFile.getDataInBackground (new GetDataCallback () {
                                    public void done(byte[] data, ParseException e) {
                                        if (e == null) {
                                            image_selected = true;
                                            Bitmap bmp = BitmapFactory
                                                                 .decodeByteArray (
                                                                                          data, 0,
                                                                                          data.length);
                                            imageV.setImageBitmap (bmp);
                                        } else {
                                            e.printStackTrace ();
                                        }
                                    }
                                });
                            }
                        }
                    }
                } else {
                    e.printStackTrace ();
                }
            }
        });
    }

    public int getOrientation(Uri selectedImage) {
        int orientation = 0;
        final String[] projection = new String[]{MediaStore.Images.Media.ORIENTATION};
        final Cursor cursor = this.getContentResolver ().query (selectedImage, projection, null, null, null);
        if (cursor != null) {
            final int orientationColumnIndex = cursor.getColumnIndex (MediaStore.Images.Media.ORIENTATION);
            if (cursor.moveToFirst ()) {
                orientation = cursor.isNull (orientationColumnIndex) ? 0 : cursor.getInt (orientationColumnIndex);
            }
            cursor.close ();
        }
        return orientation;
    }
}