package com.example.aaa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatActivity extends Activity {

    private EditText etMessage;
    private Button btSend;
    private ListView lvChat;
    private ArrayList<Message> mMessages;
    private ArrayList<Room> mRoom;
    private ChatListAdapter mAdapter;
    private boolean mFirstLoad;
    private Handler handler = new Handler();
    String otherUserId;
    String des;
    String des2;
    boolean isSaved = false;
    boolean exist = false;
    String other_user_name;

    int combinedConversationId;
    int conversationId;
    int otherConversationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();

        otherUserId = intent.getStringExtra("userId");
        other_user_name = intent.getStringExtra("userName");

        for (int i = 0; i < MainPageActivity.ud.size(); i++) {

            if(other_user_name.equals(MainPageActivity.ud.get(i).getName()))
            {
                otherConversationId = MainPageActivity.ud.get(i).getMessage_roomId();
            }

            if(ParseUser.getCurrentUser().getObjectId().equals(MainPageActivity.ud.get(i).getId()))
            {
                conversationId = MainPageActivity.ud.get(i).getMessage_roomId();

            }


        }

        combinedConversationId = (otherConversationId * conversationId);


        etMessage = (EditText) findViewById(R.id.etMessage);



        lvChat = (ListView) findViewById(R.id.lvChat);
        mMessages = new ArrayList <Message>();
        mRoom = new ArrayList <Room>();
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvChat.setTranscriptMode(1);
        des = ParseUser.getCurrentUser().getObjectId() + " - " + otherUserId;
        des2 = otherUserId + " - " + ParseUser.getCurrentUser().getObjectId();
        mFirstLoad = true;
        mAdapter = new ChatListAdapter(ChatActivity.this, ParseUser.getCurrentUser().getObjectId(), mMessages);
        lvChat.setAdapter(mAdapter);
        handler.postDelayed(runnable, 100);
       // Toast.makeText(getApplicationContext(),"length of = " + otherUserId + " = " + , Toast.LENGTH_SHORT).show();


    }

    public void sendMessage(View view){

        String body = etMessage.getText().toString();

        Message message = new Message();
        message.setUserId(ParseUser.getCurrentUser().getObjectId());
        message.setBody(body);

        message.setDes(des);
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                receiveMessage();
            }
        });
        etMessage.setText("");

        if(!isSaved)
        {

          //  saveToFile(readFromFile(otherUserId));
            deleteMessageRoomItem();
            // deleteObject("des2");
           saveToMessagesRoom("");


        }

    }



    private void receiveMessage() {

        ParseQuery <Message> query = ParseQuery.getQuery(Message.class);
        String[] names = {ParseUser.getCurrentUser().getObjectId()
                + " - " + otherUserId,otherUserId + " - " + ParseUser.getCurrentUser().getObjectId()};
        query.whereContainedIn("des", Arrays.asList(names));
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {

                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 100);
        }
    };

    private void refreshMessages() {
        receiveMessage();
    }

    private void saveToMessagesRoom(String des) {

        Room room = new Room();
        room.setUserId(ParseUser.getCurrentUser().getObjectId());
        room.setConversationId(combinedConversationId);
       // room.setDes2(des2);
        room.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                isSaved = true;
                Toast.makeText(getApplicationContext(), "saved!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void deleteObject(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
        String[] names = {ParseUser.getCurrentUser().getObjectId() + " - " +
                otherUserId,otherUserId + " - " + ParseUser.getCurrentUser().getObjectId()};
        query.whereContainedIn("Des", Arrays.asList(names));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                // Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
                if (e == null) {
                    Toast.makeText(getApplicationContext(), object.getObjectId(), Toast.LENGTH_SHORT).show();

                    try {
                        object.delete();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    object.saveInBackground();
                }

            }
        });

    }


    void re(){

        ParseQuery <Room> query = ParseQuery.getQuery(Room.class);
        String[] names = {ParseUser.getCurrentUser().getObjectId() + " - " +
                otherUserId, otherUserId + " - " + ParseUser.getCurrentUser().getObjectId()};
        query.whereContainedIn("des", Arrays.asList(names));
        query.findInBackground(new FindCallback<Room>() {
            public void done(List<Room> messages, ParseException e) {

                for (int i = 0; i < messages.size(); i++) {

                    Toast.makeText(getApplicationContext(), messages.size() + "", Toast.LENGTH_SHORT).show();


                    try {
                        messages.get(i).delete();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        messages.get(i).save();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                }

                saveToMessagesRoom(des);

            }
        });



    }


   void  saveToFile(List roomList){

       OutputStreamWriter outputStreamWriter = null;

       try {
            outputStreamWriter = new OutputStreamWriter(this.openFileOutput("room.txt", Context.MODE_PRIVATE));
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }

       PrintWriter writer = new PrintWriter(outputStreamWriter);

       for (int i = 0; i < roomList.size(); i++) {
           writer.println(roomList.get(i));

       }

       try {
           outputStreamWriter.close();
       } catch (IOException e) {
           e.printStackTrace();
       }


   }


    private List  readFromFile(String otherUserId) {

        List<String > l = new ArrayList<String>();
        List<String> newList = new ArrayList<String>();


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

        for (int i=0;i<l.size();i++)
        {
            if(otherUserId.equals(l.get(i)))
            {
                l.remove(i);
            }
        }

            newList = new ArrayList <String> ();
            newList.add(otherUserId);
            newList.addAll(l);


        return newList;

    }


    public void deleteMessageRoomItem(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
        Toast.makeText(getApplicationContext(),combinedConversationId+"", Toast.LENGTH_SHORT).show();

        query.whereEqualTo("ConversationId",combinedConversationId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                // Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
                if (e == null) {
                    Toast.makeText(getApplicationContext(),"deleted", Toast.LENGTH_SHORT).show();

                    try {
                        object.delete();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    object.saveInBackground();
                }

            }
        });

    }

}
