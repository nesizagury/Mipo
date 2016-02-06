package com.example.mipo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mipo.R;
import com.example.mipo.model.ProfileBean;

import java.util.List;


/**
 * Created by Sprintzin on 04/02/2016.
 */
public class MainPageAdapter extends ArrayAdapter<ProfileBean> {

    private static final String TAG = "MainPageAdapter";
    private  List<ProfileBean> profileBeans;
    private Context context;
    private LruCache<String, Bitmap> bitmapCache;

    public MainPageAdapter(Context context,  List<ProfileBean> profileBeans) {
        super(context, 0, profileBeans);
        this.profileBeans = profileBeans;
        this.context = context;


        Log.e(TAG, " aaa"+ profileBeans);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridHolder gridHolder;

        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.grid_item, parent, false);

            gridHolder = new GridHolder(convertView);
            convertView.setTag(gridHolder);
        } else {
            gridHolder = (GridHolder) convertView.getTag();

        }

        ProfileBean profileBean = profileBeans.get(position);
        if (profileBean != null) {
            gridHolder.image.setImageBitmap(profileBean.getPic());
            if(profileBean.isOnline()){
                gridHolder.image2.setImageResource(R.drawable.online2);
            }else{
                gridHolder.image2.setImageResource(R.drawable.offline);
            }
            gridHolder.name.setText(profileBean.getName());
            Log.e(TAG, "name in adapter " + profileBean.getName());
        }

        return convertView;
    }



    static class GridHolder {
        ImageView image;
        ImageView image2;
        TextView name;

        public GridHolder(View v) {
            image = (ImageView) v.findViewById(R.id.imageView1);
            image2 = (ImageView) v.findViewById(R.id.imageView2);
            name = (TextView) v.findViewById(R.id.textView1);
        }
    }
}
