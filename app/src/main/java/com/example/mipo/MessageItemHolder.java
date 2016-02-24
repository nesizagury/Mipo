package com.example.mipo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageItemHolder {

    ImageView image;
    TextView body;
    TextView name;

    public MessageItemHolder(View v) {
        image = (ImageView) v.findViewById (R.id.message_itemIV);
        name = (TextView) v.findViewById (R.id.message_item_nameTV);
        body = (TextView) v.findViewById (R.id.message_item_bodyTV);
    }
}
