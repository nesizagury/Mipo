package com.example.mipo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class UserPage extends Activity implements ImageButton.OnClickListener {

    ImageButton detailed_button;
    ImageButton favorite_button;
    ImageButton report_button;
    ImageButton message_button;
    String user_name;
    private String userID;
    boolean user_current;
    int index;
    int online;
    ImageLoader imageLoader;
    DisplayImageOptions options;


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
            online = b.getInt ("online");
            UserDetails userDetails = GlobalVariables.userDataList.get (index);
            options = new DisplayImageOptions.Builder ()
                              .cacheOnDisk (true)
                              .cacheInMemory (true)
                              .bitmapConfig (Bitmap.Config.RGB_565)
                              .imageScaleType (ImageScaleType.EXACTLY)
                              .resetViewBeforeLoading (true)
                              .build ();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder (this)
                                                      .defaultDisplayImageOptions (options)
                                                      .threadPriority (Thread.MAX_PRIORITY)
                                                      .threadPoolSize (4)
                                                      .memoryCache (new WeakMemoryCache ())
                                                      .denyCacheImageMultipleSizesInMemory ()
                                                      .build ();
            imageLoader = ImageLoader.getInstance ();
            imageLoader.init (config);
            ImageView user_image = (ImageView) findViewById (R.id.usrPage_image);
            imageLoader.displayImage (userDetails.getPicUrl (), user_image);

            TextView userNameTF = (TextView) findViewById (R.id.name_profile);
            TextView seenTF = (TextView) findViewById (R.id.seen_profile);
            userNameTF.setText (user_name + " , " + userDetails.getAge ());
            if (user_current) {
                seenTF.setText ("Online" + " | " + "0 km away");
            } else {
                if(online < 10){
                    seenTF.setText ("Online" + " | " + userDetails.getDist () + " km away");
                } else{
                    seenTF.setText ("Seen " + online + " min ago" + " | " + userDetails.getDist () + " km away");
                }
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
            Bundle b = new Bundle ();
            user_current = getIntent ().getBooleanExtra ("userCurrent", false);
            Intent intent = new Intent (this, DetailedProfileActivity.class);
            intent.putExtra ("userName", user_name);
            intent.putExtra ("currentUser", user_current);
            b.putInt ("index", index);
            intent.putExtras (b);
            startActivity (intent);
        }

        if (v == favorite_button) {
            UserDetails userDetails = GlobalVariables.userDataList.get (index);
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
        if (!StaticMethods.isGuestUser ()) {
            Intent intent = new Intent (this, ChatActivity.class);
            Bundle b = new Bundle ();
            intent.putExtra ("userId", userID);
            intent.putExtra ("userName", user_name);
            b.putInt ("index", index);
            intent.putExtras (b);
            startActivity (intent);
        } else {
            Toast.makeText (getApplicationContext (), "to continue please create a profile", Toast.LENGTH_SHORT).show ();
        }
    }
}
