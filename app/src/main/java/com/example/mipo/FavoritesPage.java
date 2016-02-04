package com.example.mipo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesPage extends Activity implements AdapterView.OnItemClickListener {


    GridView grid;
    public final static List<User> ListOfFavorites = new ArrayList<User> ();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_favorities_page);
        grid = (GridView) findViewById (R.id.gridView11);

        ListOfFavorites.clear ();
        for (int i = 1; i <= 26; i++) {
            User user = MainPageActivity.firstUsersList.get (i);
            UserDetails userDetails = user.getUserDetails ();
            if (userDetails.isFavorite ()) {
                ListOfFavorites.add (user);
            }
        }

        grid.setAdapter (new GridAdapter(this, ListOfFavorites));
        grid.setOnItemClickListener (this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent (this, UserPage.class);
        Bundle b = new Bundle ();
        User user = ListOfFavorites.get (position);
        intent.putExtra ("userImage", user.imageId);
        intent.putExtra ("userName", user.name);
        intent.putExtra ("userCurrent", user.currentUser);
        b.putString ("userID", user.id);
        b.putInt ("index", user.getIndexInUD ());
        b.putInt ("online", user.on_off);
        intent.putExtras (b);
        startActivity (intent);
    }

    @Override
    public void onRestart() {
        super.onRestart ();

        grid = (GridView) findViewById (R.id.gridView11);

        ListOfFavorites.clear ();
        List<User> listAll = MainPageActivity.firstUsersList;
        for (int i = 1; i <= 26; i++) {
            User user = listAll.get (i);
            UserDetails userDetails = user.getUserDetails ();
            if (userDetails.isFavorite ()) {
                ListOfFavorites.add (listAll.get (i));
            }
        }

        grid.setAdapter (new GridAdapter(this, ListOfFavorites));
        grid.setOnItemClickListener (this);
    }

}