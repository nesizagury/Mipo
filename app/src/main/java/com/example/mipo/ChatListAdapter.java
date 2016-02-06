package com.example.mipo;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChatListAdapter extends ArrayAdapter<Message> {
    private String mUserId;

    public ChatListAdapter(Context context, String userId, List<Message> messages) {
        super (context, 0, messages);
        this.mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from (getContext ()).
                                                                     inflate (R.layout.chat_item, parent, false);
            final ViewHolder holder = new ViewHolder ();
            holder.imageLeft = (ImageView) convertView.findViewById (R.id.ivProfileLeft);
            holder.imageRight = (ImageView) convertView.findViewById (R.id.ivProfileRight);
            holder.body = (TextView) convertView.findViewById (R.id.tvBody);
            convertView.setTag (holder);
        }
        final Message message = (Message) getItem (position);
        final ViewHolder holder = (ViewHolder) convertView.getTag ();
        final boolean isMe = message.getUserId ().equals (mUserId);
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            holder.imageRight.setImageResource (R.drawable.pic0);
            holder.imageRight.setVisibility (View.VISIBLE);
            holder.imageLeft.setVisibility (View.GONE);
            holder.body.setGravity (Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else {
            for (int i = 0; i < MainPageActivity.userDataList.size (); i++) {
                UserDetails userDetails = MainPageActivity.userDataList.get (i);
                if (userDetails.getId ().equals (message.getUserId ())) {
                    holder.imageLeft.setImageResource (R.drawable.pic0);
                }
            }
            holder.imageLeft.setVisibility (View.VISIBLE);
            holder.imageRight.setVisibility (View.GONE);
            holder.body.setGravity (Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        holder.body.setText (message.getBody ());
        return convertView;
    }

    final class ViewHolder {
        public ImageView imageLeft;
        public ImageView imageRight;
        public TextView body;
    }

}