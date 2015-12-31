package com.example.aaa;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailedProfileActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_profile);



        Intent intent = getIntent();
        ImageView user_image = (ImageView) findViewById(R.id.detailed_image);
        String user_name = intent.getStringExtra("userName");

        TextView userNameAgeTF = (TextView) findViewById(R.id.detailed_name_age);
        TextView currentStatusTF = (TextView) findViewById(R.id.detailed_current_status);
        TextView heightWeightTF = (TextView) findViewById(R.id.detailed_height_wheight);
        TextView bodyNationTF = (TextView) findViewById(R.id.detailed_body_nation);
        TextView relationSeekingTF = (TextView) findViewById(R.id.detailed_relatioStatus_seeking);
        TextView aboutTF = (TextView) findViewById(R.id.detailed_about);

        for(int i = 0; i < MainPageActivity.ud.size(); i ++)
        {
            if(MainPageActivity.ud.get(i).getName().equals(user_name))
            {
                user_image.setImageResource(R.drawable.pic0+i);
                userNameAgeTF.setText(MainPageActivity.ud.get(i).getName() + " , " + MainPageActivity.ud.get(i).getAge());
                currentStatusTF.setText(MainPageActivity.ud.get(i).getStatus());
                heightWeightTF.setText(MainPageActivity.ud.get(i).getHeight() + " | " + MainPageActivity.ud.get(i).getWeight());
                bodyNationTF.setText(MainPageActivity.ud.get(i).getBody_type() + " | " + MainPageActivity.ud.get(i).getNation());
                relationSeekingTF.setText(MainPageActivity.ud.get(i).getRelationship_status() + " | " + MainPageActivity.ud.get(i).getLooking_for());
                aboutTF.setText(MainPageActivity.ud.get(i).getAbout());
            }
        }

    }

}
