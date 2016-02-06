package com.example.mipo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    private static final String TAG = "GridAdapter";
    private final List<User> list;
    private Context context;

    public GridAdapter(Context c, List<User> list) {
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

        User user = list.get(position);
        if (user != null) {
            gridHolder.image.setImageBitmap (user.imageId);
            Log.e(TAG, "image " + user.imageId);
            gridHolder.image2.setImageResource(user.on_off);
            gridHolder.name.setText(user.name);
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

