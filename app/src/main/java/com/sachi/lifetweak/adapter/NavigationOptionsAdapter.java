package com.sachi.lifetweak.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sachi.lifetweak.R;
import com.sachi.lifetweak.model.Options;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jombay on 30/3/16.
 */
public class NavigationOptionsAdapter extends BaseAdapter {

    private ArrayList<Options> optionsArrayList;
    private Activity activity;
    LayoutInflater inflater;

    public NavigationOptionsAdapter(ArrayList<Options> optionsArrayList, Activity activity) {
        this.optionsArrayList = optionsArrayList;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return optionsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return optionsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.options_list, null);
        TextView textView = (TextView) view.findViewById(R.id.textView1);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
        Picasso.with(activity).load(optionsArrayList.get(i).getOptionsImg()).into(imageView);
        textView.setText(optionsArrayList.get(i).getTxtOptions());

        return view;
    }
}
