package com.example.aaa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class Adapts extends BaseAdapter {

	List <User> list = new ArrayList <User> ();
	Context context;
	ImageView on_off;

	public Adapts(Context c, ImageView on_off, List list) {

		this.on_off = on_off;
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
		Holder holder = null;



		if(row == null)
		{
			LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflator.inflate(R.layout.grid_item, viewGroup, false);
			holder = new Holder(row);
			row.setTag(holder);

		}
		else
		{
			holder = (Holder) row.getTag();
		}

		User user = list.get(i);
		holder.image.setImageResource(user.imageId);
		holder.image2.setImageResource(user.on_off);
		holder.name.setText(user.name);
        holder.image.setTag(user);
        holder.name.setTag(user);
		return row;
	}




}
