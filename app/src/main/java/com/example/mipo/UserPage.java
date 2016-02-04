package com.example.mipo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserPage extends Activity implements ImageButton.OnClickListener {


    private static final String TAG = "UserPage";
    ImageButton detailed_button;
    ImageButton favorite_button;
    ImageButton report_button;
    ImageButton message_button;
    String user_name;
    private String userID;
    boolean user_current;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        this.requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_user_page);
        detailed_button = (ImageButton) findViewById (R.id.detailed_profile_button);
        favorite_button = (ImageButton) findViewById (R.id.favorite_main_button);
        message_button = (ImageButton) findViewById (R.id.message_button);
        report_button = (ImageButton) findViewById (R.id.imageButton);
        detailed_button.setOnClickListener (this);
        favorite_button.setOnClickListener (this);
        report_button.setOnClickListener (this);

        Intent intent = getIntent ();
        if (intent != null) {
            Bundle b = getIntent ().getExtras ();
            user_current = intent.getBooleanExtra ("userCurrent", false);
            userID = b.getString ("userID");
            user_name = intent.getStringExtra ("userName");
            index = b.getInt ("index");
            User user = MainPageActivity.firstUsersList.get (index);
            UserDetails userDetails = MainPageActivity.userDataList.get (user.getIndexInUD ());

            int image_id = intent.getIntExtra ("userImage", R.drawable.pic0);
            ImageView user_image = (ImageView) findViewById (R.id.usrPage_image);
            /*
            measures the size of the imageView
             */
            user_image.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            Log.e(TAG, "height "+ user_image.getMeasuredHeight()+" width "+ user_image.getMeasuredWidth());
            if (user_current) {
                user_image.setImageResource (R.drawable.pic0 + userDetails.getImage_source ());
            } else {
                user_image.setImageResource (image_id);
            }

            TextView userNameTF = (TextView) findViewById (R.id.name_profile);
            TextView seenTF = (TextView) findViewById (R.id.seen_profile);
            userNameTF.setText (user_name + " , " + userDetails.getAge ());
            if (user_current) {
                seenTF.setText ("Online" + " | " + "0 meters away");
            } else if (userDetails.getDistanceType () == 0) {
                seenTF.setText (userDetails.getSeen () +
                                        " | " + userDetails.getDistance () + " meters away");
            } else if (userDetails.getDistanceType () == 1) {
                seenTF.setText (userDetails.getSeen () +
                                        " | " + userDetails.getDistance () + " km away");
            }
            if (user_current) {
                favorite_button.setVisibility (View.INVISIBLE);
                report_button.setVisibility (View.INVISIBLE);
                message_button.setVisibility (View.INVISIBLE);
            } else if (!user_current && userDetails.isFavorite ()) {
                favorite_button.setBackgroundResource (R.drawable.favoritecolored);
            } else if (!user_current && !userDetails.isFavorite ()) {
                favorite_button.setBackgroundResource (R.drawable.favorite);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == detailed_button) {
            user_current = getIntent ().getBooleanExtra ("userCurrent", false);
            Intent intent = new Intent (this, DetailedProfileActivity.class);
            intent.putExtra ("userName", user_name);
            intent.putExtra ("currentUser", user_current);
            startActivity (intent);
        }

        if (v == favorite_button) {
            User user = MainPageActivity.firstUsersList.get (index);
            UserDetails userDetails = MainPageActivity.userDataList.get (user.getIndexInUD ());
            if (userDetails.isFavorite ()) {
                userDetails.setFavorite (false);
                favorite_button.setBackgroundResource (R.drawable.favorite);
                Toast.makeText (getApplicationContext (), "Removed from Favorites", Toast.LENGTH_SHORT).show ();
            } else {
                userDetails.setFavorite (true);
                favorite_button.setBackgroundResource (R.drawable.favoritecolored);
                Toast.makeText (getApplicationContext (), "Added To Favorites", Toast.LENGTH_SHORT).show ();
            }
        }

        if (v == report_button) {
            Toast.makeText (getApplicationContext (), "You banned this profile!", Toast.LENGTH_SHORT).show ();
        }

    }

    public void messaging(View view) {
        Intent intent = new Intent (this, ChatActivity.class);
        intent.putExtra ("userId", userID);
        intent.putExtra ("userName", user_name);
        startActivity (intent);
    }

}
