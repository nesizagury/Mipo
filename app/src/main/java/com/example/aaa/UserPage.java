package com.example.aaa;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class UserPage extends Activity implements ImageButton.OnClickListener{


    ImageButton detailed_button;
    ImageButton favorite_button;
    String user_name;
    private int index;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        detailed_button = (ImageButton) findViewById(R.id.detailed_profile_button);
        favorite_button = (ImageButton) findViewById(R.id.favorite_main_button);
        detailed_button.setOnClickListener(this);
        favorite_button.setOnClickListener(this);

        Intent intent = getIntent();

        if (intent != null) {


            int image_id = intent.getIntExtra("userImage", R.drawable.pic0);
            ImageView user_image = (ImageView) findViewById(R.id.usrPage_image);
            user_image.setImageResource(image_id);
            Bundle b = getIntent().getExtras();
            index = b.getInt("userIndex");

            if(index < 15)
            user_id = MainPageActivity.ud.get(index).getId();

            user_name = intent.getStringExtra("userName");
            TextView userNameTF = (TextView) findViewById(R.id.name_profile);
            TextView seenTF = (TextView) findViewById(R.id.seen_profile);

            for(int i = 0; i < MainPageActivity.ud.size(); i ++)
            {
                if(MainPageActivity.ud.get(i).getName().equals(user_name))
                {
                    Toast.makeText(getApplicationContext(), "index = " + index, Toast.LENGTH_SHORT).show();

                    userNameTF.setText(user_name + " , " + MainPageActivity.ud.get(i).getAge());
                    seenTF.setText(MainPageActivity.ud.get(i).getDistance() + " | " + MainPageActivity.ud.get(i).getSeen());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v == detailed_button)
        {

            Intent intent = new Intent(this,DetailedProfileActivity.class);
            intent.putExtra("userName",user_name);
            startActivity(intent);
        }

        if(v == favorite_button) {

         MainPageActivity.lov.addToFavorites_list(MainPageActivity.getUser(index));
            Toast.makeText(getApplicationContext(), "Added To Favorites", Toast.LENGTH_SHORT).show();

        }

    }

    public void messaging(View view){

        Intent intent = new Intent(this,ChatActivity.class);
        intent.putExtra("userId",user_id);
        intent.putExtra("userName",user_name);
        startActivity(intent);

    }

}
