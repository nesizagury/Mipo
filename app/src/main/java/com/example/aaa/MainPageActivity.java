package com.example.aaa;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainPageActivity extends Activity implements AdapterView.OnItemClickListener {

    GridView grid;
    ImageView iv;
    public static List list;
    int favorites_barButton;
    public static ListOfFavorites lov = new ListOfFavorites();
    public static List <UserDetailes> ud = new ArrayList<UserDetailes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        uploadUserData();
        grid = (GridView) findViewById(R.id.gridView1);
        iv = (ImageView) findViewById(R.id.imageView2);
        Adapts adapts = new Adapts(this, iv, addToList());
        grid.setAdapter(adapts);
        grid.setOnItemClickListener(this);


    }

    public List addToList() {

        list = new ArrayList();
        String[] userName_list;
        userName_list = getResources().getStringArray(R.array.userNames);

            for (int i = 0; i < 15; i++) {

                if (ud.get(i).getOn_off().equals("Online"))
                    list.add(new User(R.drawable.pic0 + i,ud.get(i).getName(), R.drawable.online));
                else
                    list.add(new User(R.drawable.pic0 + i, ud.get(i).getName(), 0));

            }

        for (int i = 0; i < 13; i++) {

            if (ud.get(i).getOn_off().equals("Online"))
                list.add(new User(R.drawable.i0 + i,ud.get(i).getName(), R.drawable.online));
            else
                list.add(new User(R.drawable.i0 + i, ud.get(i).getName(), 0));

        }

        for (int i = 0; i < 15; i++) {

            if (ud.get(i).getOn_off().equals("Online"))
                list.add(new User(R.drawable.pic0 + i,ud.get(i).getName(), R.drawable.online));
            else
                list.add(new User(R.drawable.pic0 + i, ud.get(i).getName(), 0));

        }

        for (int i = 0; i < 13; i++) {

            if (ud.get(i).getOn_off().equals("Online"))
                list.add(new User(R.drawable.i0 + i,ud.get(i).getName(), R.drawable.online));
            else
                list.add(new User(R.drawable.i0 + i, ud.get(i).getName(), 0));

        }

        for (int i = 0; i < 15; i++) {

            if (ud.get(i).getOn_off().equals("Online"))
                list.add(new User(R.drawable.pic0 + i,ud.get(i).getName(), R.drawable.online));
            else
                list.add(new User(R.drawable.pic0 + i, ud.get(i).getName(), 0));

        }

        for (int i = 0; i < 13; i++) {

            if (ud.get(i).getOn_off().equals("Online"))
                list.add(new User(R.drawable.i0 + i,ud.get(i).getName(), R.drawable.online));
            else
                list.add(new User(R.drawable.i0 + i, ud.get(i).getName(), 0));

        }

        for (int i = 0; i < 15; i++) {

            if (ud.get(i).getOn_off().equals("Online"))
                list.add(new User(R.drawable.pic0 + i,ud.get(i).getName(), R.drawable.online));
            else
                list.add(new User(R.drawable.pic0 + i, ud.get(i).getName(), 0));

        }


            return list;

    }


    public static User getUser(int i){

        return (User) list.get(i);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Bundle b = new Bundle();
        Intent intent = new Intent(this,UserPage.class);
        Holder holder = (Holder) view.getTag();
        User user = (User)holder.image.getTag();
        intent.putExtra("userImage",user.imageId);
        intent.putExtra("userName", user.name);
        b.putInt("userIndex", i);
        b.putInt("online",user.on_off);
        intent.putExtras(b);
        startActivity(intent);

    }


    public void goToMessages(View view){

        Intent intent = new Intent(this,ChatActivity.class);
        startActivity(intent);

    }

    public void goToFavorites(View view){

        Intent i = new Intent(this,FavoritesPage.class);
        startActivity(i);

    }

    public void uploadUserData() {

        InputStream is;
        BufferedReader input;
        List <String> list;


        for(int i = 0; i < 15 ; i++) {

            is = this.getResources().openRawResource(R.raw.user0+i);
            input = new BufferedReader(new InputStreamReader(is), 1024 * 8);

            String line = null;
            list = new ArrayList<String>();

            try {
                int j = 0;
                while ((line = input.readLine()) != null) {
                    if (j % 2 == 0)
                        list.add(line);
                    j++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            ud.add(new UserDetailes(list.get(0),list.get(1),list.get(2),list.get(3),list.get(4),list.get(5),list.get(6),
                    list.get(7),list.get(8),list.get(9),list.get(10),list.get(11),list.get(12),list.get(13)));

        }


    }


}
