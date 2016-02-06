package com.example.mipo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MessagesRoom extends Activity implements AdapterView.OnItemClickListener {

    private ListView list_view;
    private ArrayList<MessageRoomBean> mrbList;
    int myConversationId;
    private MessagesRoomAdapter mra;
    private Handler handler = new Handler ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_messages_room);

        list_view = (ListView) findViewById (R.id.listView);
        list_view.setTranscriptMode (1);
        myConversationId = MainPageActivity.currentUser.getMessage_roomId ();
        mrbList = new ArrayList<MessageRoomBean> ();
        mra = new MessagesRoomAdapter (this, mrbList);
        list_view.setAdapter (mra);
        list_view.setOnItemClickListener (this);
        loadMessages ();
        handler.postDelayed (runnable, 500);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent (this, ChatActivity.class);
        MessageItemHolder holder = (MessageItemHolder) view.getTag ();
        MessageRoomBean mrb = (MessageRoomBean) holder.name.getTag ();
        intent.putExtra ("userId", mrb.id);
        intent.putExtra ("userName", mrb.name);
        startActivity (intent);
    }

    public void loadMessages() {
        final ParseQuery<Room> query = ParseQuery.getQuery (Room.class);
        query.orderByDescending ("updatedAt");
        List<Room> rooms = null;
        ArrayList<MessageRoomBean> mrbListNew = new ArrayList<MessageRoomBean> ();
        try {
            rooms = query.find ();
            List<String> IdsFound = new ArrayList<String> ();
            for (int i = 0; i < rooms.size (); i++) {
                int combinedConversationId = rooms.get (i).getConversationId ();
                if (combinedConversationId % myConversationId == 0) {
                    int otherConvId = combinedConversationId / myConversationId;
                    for (int j = 0; j < MainPageActivity.userDataList.size (); j++) {
                        UserDetails user = MainPageActivity.userDataList.get (j);
                        if (!IdsFound.contains (user.getId ()) && user.getMessage_roomId () == otherConvId) {
                            IdsFound.add (user.getId ());
                            mrbListNew.add (new MessageRoomBean (R.drawable.pic0,
                                                                        user.getName (),
                                                                        rooms.get (i).getDes (),
                                                                        otherConvId));
                            MessageRoomBean bean = mrbListNew.get (mrbListNew.size () - 1);
                            bean.setId (user.getId ());
                        }
                    }
                }
            }
            mrbList.clear ();
            mrbList.addAll (mrbListNew);
            mra.notifyDataSetChanged ();
        } catch (ParseException e) {
            e.printStackTrace ();
        }
    }

    public void loadMessagesInBackground() {
        final ParseQuery<Room> query = ParseQuery.getQuery (Room.class);
        query.orderByDescending ("updatedAt");
        query.findInBackground (new FindCallback<Room> () {
            public void done(List<Room> rooms, ParseException e) {
                if (e == null) {
                    List<String> IdsFound = new ArrayList<String> ();
                    ArrayList<MessageRoomBean> mrbListNew = new ArrayList<MessageRoomBean> ();
                    for (int i = 0; i < rooms.size (); i++) {
                        int combinedConversationId = rooms.get (i).getConversationId ();
                        if (combinedConversationId % myConversationId == 0) {
                            int otherConvId = combinedConversationId / myConversationId;
                            for (int j = 0; j < MainPageActivity.userDataList.size (); j++) {
                                UserDetails user = MainPageActivity.userDataList.get (j);
                                if (!IdsFound.contains (user.getId ()) && user.getMessage_roomId () == otherConvId) {
                                    IdsFound.add (user.getId ());
                                    mrbListNew.add (new MessageRoomBean (R.drawable.pic0,
                                                                                user.getName (),
                                                                                rooms.get (i).getDes (),
                                                                                otherConvId));
                                    MessageRoomBean bean = mrbListNew.get (mrbListNew.size () - 1);
                                    bean.setId (user.getId ());
                                }
                            }
                        }
                    }
                    mrbList.clear ();
                    mrbList.addAll (mrbListNew);
                    mra.notifyDataSetChanged ();
                } else {
                    e.printStackTrace ();
                }
            }
        });
    }

    private Runnable runnable = new Runnable () {
        @Override
        public void run() {
            loadMessagesInBackground ();
            handler.postDelayed (this, 500);
        }
    };

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