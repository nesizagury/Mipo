package com.example.mipo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class MessagesRoomAdapter extends ArrayAdapter<MessageRoomBean> {

    List<MessageRoomBean> list;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    int pixels;
    Context c;

    public MessagesRoomAdapter(Context c, List<MessageRoomBean> list) {
        super (c, 0, list);
        this.list = list;
        this.c = c;
        setImageLoader ();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from (getContext ()).inflate (R.layout.messages_room_item, viewGroup, false);
            final MessageItemHolder m_holder = new MessageItemHolder (view);
            view.setTag (m_holder);
        }
        final MessageRoomBean message_bean = (MessageRoomBean) getItem (i);
        final MessageItemHolder holder = (MessageItemHolder) view.getTag ();

        if (list.get (i).picUrl != null) {
            imageLoader.displayImage (list.get (i).picUrl, holder.image);
        } else {
            holder.image.setImageResource (R.drawable.favorite);
        }
        holder.body.setText (message_bean.getBody ());
        holder.name.setText (message_bean.getName ());

        holder.image.setTag (message_bean);
        holder.name.setTag (message_bean);
        holder.body.setTag (message_bean);

        return view;
    }

    public void setImageLoader() {
        float density = c.getResources ().getDisplayMetrics ().density;
        pixels = (int) (80 * density + 0.5f);

        options = new DisplayImageOptions.Builder ()
                          .cacheOnDisk (true)
                          .cacheInMemory (true)
                          .bitmapConfig (Bitmap.Config.RGB_565)
                          .imageScaleType (ImageScaleType.EXACTLY)
                          .resetViewBeforeLoading (true)
                          .build ();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder (c)
                                                  .defaultDisplayImageOptions (options)
                                                  .threadPriority (Thread.MAX_PRIORITY)
                                                  .threadPoolSize (4)
                                                  .memoryCache (new WeakMemoryCache ())
                                                  .denyCacheImageMultipleSizesInMemory ()
                                                  .build ();
        imageLoader = ImageLoader.getInstance ();
        imageLoader.init (config);
    }
}
