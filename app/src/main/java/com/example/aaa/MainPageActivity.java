package com.example.aaa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPageActivity extends Activity implements AdapterView.OnItemClickListener {

    static   GridView grid;
    ImageView iv;
    public static List list;
    int favorites_barButton;
    public static ListOfFavorites lov = new ListOfFavorites();
    public static List <UserDetailes> ud = new ArrayList<UserDetailes>();
    List <Room> rList = new ArrayList <Room> ();
    List<MessageRoomBean> mrbList = new ArrayList<MessageRoomBean>();
    List<Message> mMessages = new ArrayList<Message>();
    int combinedConversationId;
    int conversationId;
    int otherConversationId;
    static Adapts adapts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        uploadUserData();
        refreshMessages();

        grid = (GridView) findViewById(R.id.gridView1);
        iv = (ImageView) findViewById(R.id.imageView2);
        adapts = new Adapts(this, iv, addToList());
        grid.setAdapter(adapts);
        grid.setOnItemClickListener(this);


    }

    private void refreshMessages() {

        mrbList.clear();
        mrbList = loadMessages();
    }

    public List addToList() {

        list = new ArrayList<User>();
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

        refreshMessages();
        Intent intent = new Intent(this,MessagesRoom.class);
        Bundle b = new Bundle();
        intent.putExtra("body", (Serializable) mrbList);
        b.putInt("otherConvId", otherConversationId);
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
                    list.get(7),list.get(8),list.get(9),list.get(10),list.get(11),list.get(12),list.get(13),Integer.parseInt(list.get(14)),Integer.parseInt(list.get(15))));

            if(ParseUser.getCurrentUser().getObjectId().equals(ud.get(i).getId()))
            {
                conversationId = ud.get(i).getMessage_roomId();
            }

        }
      //  mrbList = loadMessages();


    }


    public List loadMessages(){


        final ParseQuery <Room> query = ParseQuery.getQuery(Room.class);
        String name = ParseUser.getCurrentUser().getObjectId();
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<Room>() {
            public void done(List<Room> rooms, ParseException e) {

                rList.addAll(rooms);

                for (int i = 0; i < rList.size(); i++) {

                    for (int j = 0; j < ud.size(); j++) {
                        combinedConversationId = rList.get(i).getConversationId();
                        otherConversationId = ud.get(j).getMessage_roomId();
                        if (combinedConversationId / conversationId == otherConversationId ){

                            Toast.makeText(getApplicationContext(),ud.get(j).getName(), Toast.LENGTH_SHORT).show();
                            mrbList.add(new MessageRoomBean(0, "", ud.get(j).getName(),otherConversationId));
                            getLastMessage(ud.get(j).getId(), mrbList.size()-1);


                        }

                    }

                }


            }
        });




        return mrbList;


    }


    private List  readFromFile() {

        List<String > l = new ArrayList<String>();

        try {
            InputStream inputStream = openFileInput("room.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    l.add(receiveString);
                }

                inputStream.close();

            }

        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }


        for (int i = 0; i < l.size(); i++) {

           // mrbList.add(new MessageRoomBean(0, l.get(i), "body",l.get(i)));
           // receiveMessage(l.get(i), i);

        }

        return mrbList;

    }

    public void getLastMessage(String otherId, final int index) {


        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        String[] names = {ParseUser.getCurrentUser().getObjectId()
                + " - " + otherId, otherId + " - " + ParseUser.getCurrentUser().getObjectId()};
        query.whereContainedIn("des", Arrays.asList(names));
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {

                if (e == null) {

                    mMessages.clear ();
                    mMessages.addAll(messages);
                    try {
                        mrbList.get(index).setBody( mMessages.get(0).getBody().toString());
                    } catch(Exception ee){

                    }

                } else {
                    Log.d ("message", "Error: " + e.getMessage ());
                }
            }
        });


    }


    public void goToFilter(View v){

        Intent intent = new Intent(this,FilterActivity.class);
        startActivity(intent);


    }

    public static void ref()
    {

        adapts.notifyDataSetChanged();

    }


}
