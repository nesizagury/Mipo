package com.example.mipo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class ChatListAdapter extends ArrayAdapter<Message> {

    ImageLoader imageLoader;
    DisplayImageOptions options;
    int pixels;
    Context context;
    int otherUserIndex;

    public ChatListAdapter(Context context, int otherUserIndex, List<Message> messages) {
        super (context, 0, messages);
        this.otherUserIndex = otherUserIndex;
        this.context = context;
        setImageLoader ();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from (getContext ()).inflate (R.layout.chat_item, parent, false);
            final ViewHolder holder = new ViewHolder ();
            holder.imageLeft = (ImageView) convertView.findViewById (R.id.ivProfileLeft);
            holder.imageRight = (ImageView) convertView.findViewById (R.id.ivProfileRight);
            holder.body = (TextView) convertView.findViewById (R.id.tvBody);
            convertView.setTag (holder);
        }
        final Message message = (Message) getItem (position);
        final ViewHolder holder = (ViewHolder) convertView.getTag ();
        final boolean isMe = message.getSenderId ().equals (GlobalVariables.CUSTOMER_PHONE_NUM);
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            imageLoader.displayImage (GlobalVariables.currentUser.getPicUrl (), holder.imageRight);
            holder.imageRight.setVisibility (View.VISIBLE);
            holder.imageLeft.setVisibility (View.GONE);
            holder.body.setGravity (Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else {
            UserDetails userDetails = GlobalVariables.userDataList.get (otherUserIndex);
            imageLoader.displayImage (userDetails.getPicUrl (), holder.imageLeft);
            holder.imageLeft.setVisibility (View.VISIBLE);
            holder.imageRight.setVisibility (View.GONE);
            holder.body.setGravity (Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        holder.body.setText (message.getMessageBody ());
        return convertView;
    }

    final class ViewHolder {
        public ImageView imageLeft;
        public ImageView imageRight;
        public TextView body;
    }

    public void setImageLoader() {

        float density = context.getResources ().getDisplayMetrics ().density;
        pixels = (int) (64 * density + 0.5f);

        options = new DisplayImageOptions.Builder ()
                          .cacheOnDisk (true)
                          .cacheInMemory (true)
                          .bitmapConfig (Bitmap.Config.RGB_565)
                          .imageScaleType (ImageScaleType.EXACTLY)
                          .resetViewBeforeLoading (true)
                          .build ();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder (context)
                                                  .defaultDisplayImageOptions (options)
                                                  .threadPriority (Thread.MAX_PRIORITY)
                                                  .threadPoolSize (8)
                                                  .memoryCache (new WeakMemoryCache ())
                                                  .denyCacheImageMultipleSizesInMemory ()
                                                  .build ();
        imageLoader = ImageLoader.getInstance ();
        imageLoader.init (config);
    }


}