package com.example.mipo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class DetailedProfileActivity extends Activity {

    TableRow akevot;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.detailed_profile);

        Intent intent = getIntent ();
        ImageView user_image = (ImageView) findViewById (R.id.detailed_image);
        Boolean user_current = intent.getBooleanExtra ("currentUser", false);
        int userIndex = intent.getIntExtra ("index", 0);
        UserDetails userDetails = GlobalVariables.userDataList.get (userIndex);
        akevot = (TableRow) findViewById (R.id.tableRow3);
        if (user_current) {
            akevot.setVisibility (View.INVISIBLE);
        }
        TextView userNameAgeTF = (TextView) findViewById (R.id.detailed_name_age);
        TextView currentStatusTF = (TextView) findViewById (R.id.detailed_current_status);
        TextView heightWeightTF = (TextView) findViewById (R.id.detailed_height_wheight);
        TextView bodyNationTF = (TextView) findViewById (R.id.detailed_body_nation);
        TextView relationSeekingTF = (TextView) findViewById (R.id.detailed_relatioStatus_seeking);
        TextView aboutTF = (TextView) findViewById (R.id.detailed_about);
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
                                                  .threadPoolSize (1)
                                                  .memoryCache (new WeakMemoryCache ())
                                                  .denyCacheImageMultipleSizesInMemory ()
                                                  .build ();
        imageLoader = ImageLoader.getInstance ();
        imageLoader.init (config);
        imageLoader.displayImage (userDetails.getPicUrl (), user_image);
        userNameAgeTF.setText (userDetails.getName () + " , " + userDetails.getAge ());
        currentStatusTF.setText (userDetails.getStatus ());
        heightWeightTF.setText (userDetails.getHeight () + " | " + userDetails.getWeight ());
        bodyNationTF.setText (userDetails.getBody_type () + " | " + userDetails.getNation ());
        relationSeekingTF.setText (userDetails.getRelationship_status () + " | " + userDetails.getLooking_for ());
        aboutTF.setText (userDetails.getAbout ());
    }

    public void like(View view) {
        if (!StaticMethods.isGuestUser ()) {
            Toast.makeText (getApplicationContext (), "You sent Like to her.", Toast.LENGTH_SHORT).show ();
        } else {
            Toast.makeText (getApplicationContext (), "to continue please create a profile", Toast.LENGTH_SHORT).show ();
        }

    }

    public void hot(View view) {
        if (!StaticMethods.isGuestUser ())
            Toast.makeText (getApplicationContext (), "You sent \"You'r Hot!\" to her.", Toast.LENGTH_SHORT).show ();
        else
            Toast.makeText (getApplicationContext (), "to continue please create a profile", Toast.LENGTH_SHORT).show ();

    }

    public void love(View view) {
        if (!StaticMethods.isGuestUser ())
            Toast.makeText (getApplicationContext (), "You sent \"I Love your profile\" to her.", Toast.LENGTH_SHORT).show ();
        else
            Toast.makeText (getApplicationContext (), "to continue please create a profile", Toast.LENGTH_SHORT).show ();

    }

    public void hi(View view) {
        if (!StaticMethods.isGuestUser ())
            Toast.makeText (getApplicationContext (), "You sent \"Hi\" to her.", Toast.LENGTH_SHORT).show ();
        else
            Toast.makeText (getApplicationContext (), "to continue please create a profile", Toast.LENGTH_SHORT).show ();

    }
}
