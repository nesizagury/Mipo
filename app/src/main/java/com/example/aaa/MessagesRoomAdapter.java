package com.example.aaa;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MessagesRoomAdapter extends BaseAdapter {

    List <MessageRoomBean> list = new ArrayList <MessageRoomBean> ();
    Context context;

    public MessagesRoomAdapter (Context c, List list) {


        this.context = c;
        this.list = list;


    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int i) {

        return list.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        MessageItemHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflator.inflate(R.layout.messages_room_item, viewGroup, false);
            holder = new MessageItemHolder(row);
            row.setTag(holder);

        }
        else
        {
            holder = (MessageItemHolder) row.getTag();
        }

        MessageRoomBean message_bean = list.get(i);

        holder.image.setImageResource(message_bean.imageId);
        holder.body.setText(message_bean.body);
        holder.name.setText(message_bean.name);

        holder.image.setTag(message_bean);
        holder.name.setTag(message_bean);
        holder.body.setTag(message_bean);

        return row;

    }




}
