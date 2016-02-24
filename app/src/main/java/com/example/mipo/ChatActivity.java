package com.example.mipo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity {

    private EditText etMessage;
    private ListView lvChat;
    private ArrayList<Message> mMessages;
    private ChatListAdapter mAdapter;
    private boolean mFirstLoad;
    private Handler handler = new Handler ();
    static String otherUserId;
    static UserDetails otherUser;
    static String other_user_name;
    int combinedConversationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_chat);
        Intent intent = getIntent ();

        otherUserId = intent.getStringExtra ("userId");
        other_user_name = intent.getStringExtra ("userName");
        int otherUserIndex = intent.getIntExtra ("index", 0);
        otherUser = GlobalVariables.userDataList.get (otherUserIndex);

        etMessage = (EditText) findViewById (R.id.etMessage);
        lvChat = (ListView) findViewById (R.id.lvChat);

        mMessages = new ArrayList<Message> ();
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvChat.setTranscriptMode (1);

        mFirstLoad = true;
        mAdapter = new ChatListAdapter (ChatActivity.this, otherUserIndex, mMessages);
        lvChat.setAdapter (mAdapter);
        receiveNoBackGround ();
        handler.postDelayed (runnable, 500);
    }

    public void sendMessage(View view) {
        String body = etMessage.getText ().toString ();
        Message message = new Message ();
        String currentPhone = GlobalVariables.CUSTOMER_PHONE_NUM;
        String otherPhone = otherUser.getUserPhoneNum ();
        if (currentPhone.compareTo (otherPhone) > 0) {
            message.setUserNum1 (currentPhone);
            message.setUserNum2 (otherPhone);
        } else {
            message.setUserNum1 (otherPhone);
            message.setUserNum2 (currentPhone);
        }
        message.setSenderId (currentPhone);
        message.setMessageBody (body);
        try {
            message.save ();
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        etMessage.setText ("");
        receiveNoBackGround ();
        deleteMessageRoomItem (body);
    }


    private void receiveMessage() {
        ParseQuery<Message> query = ParseQuery.getQuery (Message.class);
        String currentPhone = GlobalVariables.CUSTOMER_PHONE_NUM;
        String otherPhone = otherUser.getUserPhoneNum ();
        if (currentPhone.compareTo (otherPhone) > 0) {
            query.whereEqualTo ("userNum1", currentPhone);
            query.whereEqualTo ("userNum2", otherPhone);
        } else {
            query.whereEqualTo ("userNum1", otherPhone);
            query.whereEqualTo ("userNum2", currentPhone);
        }
        query.orderByAscending ("createdAt");
        query.findInBackground (new FindCallback<Message> () {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    if (messages.size () > mMessages.size ()) {
                        mMessages.clear ();
                        mMessages.addAll (messages);
                        mAdapter.notifyDataSetChanged (); // update adapter
                        // Scroll to the bottom of the list on initial load
                        if (mFirstLoad) {
                            lvChat.setSelection (mAdapter.getCount () - 1);
                            mFirstLoad = false;
                        }
                    }
                } else {
                    e.printStackTrace ();
                }
            }
        });
    }

    private void receiveNoBackGround() {
        ParseQuery<Message> query = ParseQuery.getQuery (Message.class);
        String currentPhone = GlobalVariables.CUSTOMER_PHONE_NUM;
        String otherPhone = otherUser.getUserPhoneNum ();
        if (currentPhone.compareTo (otherPhone) > 0) {
            query.whereEqualTo ("userNum1", currentPhone);
            query.whereEqualTo ("userNum2", otherPhone);
        } else {
            query.whereEqualTo ("userNum1", otherPhone);
            query.whereEqualTo ("userNum2", currentPhone);
        }
        query.orderByAscending ("createdAt");
        List<Message> messages = null;
        try {
            messages = query.find ();
            if (messages.size () > mMessages.size ()) {
                mMessages.clear ();
                mMessages.addAll (messages);
                mAdapter.notifyDataSetChanged (); // update adapter
                // Scroll to the bottom of the list on initial load
                if (mFirstLoad) {
                    lvChat.setSelection (mAdapter.getCount () - 1);
                    mFirstLoad = false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace ();
        }
    }

    private Runnable runnable = new Runnable () {
        @Override
        public void run() {
            receiveMessage ();
            handler.postDelayed (this, 500);
        }
    };

    public void deleteMessageRoomItem(final String body) {
        ParseQuery<Room> query = ParseQuery.getQuery (Room.class);
        String currentPhone = GlobalVariables.CUSTOMER_PHONE_NUM;
        String otherPhone = otherUser.getUserPhoneNum ();
        if (currentPhone.compareTo (otherPhone) > 0) {
            query.whereEqualTo ("userNum1", currentPhone);
            query.whereEqualTo ("userNum2", otherPhone);
        } else {
            query.whereEqualTo ("userNum1", otherPhone);
            query.whereEqualTo ("userNum2", currentPhone);
        }
        query.findInBackground (new FindCallback<Room> () {
            public void done(List<Room> list, ParseException e) {
                if (e == null) {
                    String currentPhone = GlobalVariables.CUSTOMER_PHONE_NUM;
                    String otherPhone = otherUser.getUserPhoneNum ();
                    if (list.size () == 0) {
                        Room room = new Room ();
                        ParseACL parseACL = new ParseACL ();
                        parseACL.setPublicWriteAccess (true);
                        parseACL.setPublicReadAccess (true);
                        room.setACL (parseACL);
                        if (currentPhone.compareTo (otherPhone) > 0) {
                            room.setUserNum1 (currentPhone);
                            room.setUserNum2 (otherPhone);
                        } else {
                            room.setUserNum1 (otherPhone);
                            room.setUserNum2 (currentPhone);
                        }
                        room.setLastMessage (currentPhone + ": " + body);
                        room.saveInBackground ();
                    } else {
                        if (list.size () > 0) {
                            for (int i = 1; i < list.size (); i++) {
                                list.get (i).deleteInBackground ();
                            }
                        }
                        Room room = list.get (0);
                        if (currentPhone.compareTo (otherPhone) > 0) {
                            room.setUserNum1 (currentPhone);
                            room.setUserNum2 (otherPhone);
                        } else {
                            room.setUserNum1 (otherPhone);
                            room.setUserNum2 (currentPhone);
                        }
                        room.setLastMessage (currentPhone + ": " + body);
                        room.saveInBackground ();
                    }
                } else {
                    e.printStackTrace ();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause ();
        handler.removeCallbacks (runnable);
    }

    @Override
    public void onRestart() {
        super.onRestart ();
        handler.postDelayed (runnable, 500);
    }
}