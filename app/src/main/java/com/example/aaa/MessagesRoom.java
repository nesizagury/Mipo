package com.example.aaa;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagesRoom extends Activity implements AdapterView.OnItemClickListener{

    ListView list_view;
    List <Room> list = new ArrayList <Room> ();
    List<MessageRoomBean> mrbList = new ArrayList<MessageRoomBean>();
    int conversationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_room);

        list_view = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent();

        mrbList = (ArrayList<MessageRoomBean>) getIntent().getSerializableExtra("body");
        Bundle b = getIntent().getExtras();
        conversationId = b.getInt("otherConvId");


        for (int k = 0; k < mrbList.size(); k++) {

            for (int i = 0; i < MainPageActivity.ud.size() ; i++) {

                if(MainPageActivity.ud.get(i).getMessage_roomId() == mrbList.get(k).getConversationId())
                {
                    mrbList.get(k).setImageId(R.drawable.pic0 + MainPageActivity.ud.get(i).getImage_source());
                    mrbList.get(k).setName(MainPageActivity.ud.get(i).getName());
                    mrbList.get(k).setId(MainPageActivity.ud.get(i).getId());
                }

            }

        }

        MessagesRoomAdapter mra = new MessagesRoomAdapter(this, mrbList);
        list_view.setAdapter(mra);
        list_view.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(this,ChatActivity.class);
        MessageItemHolder holder = (MessageItemHolder) view.getTag();
        MessageRoomBean mrb = (MessageRoomBean)holder.name.getTag();
        intent.putExtra("userId", mrb.id);
        intent.putExtra("userName",mrb.name);
        startActivity(intent);

    }


}
