package com.squeezer.asr2application.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squeezer.asr2application.R;
import com.squeezer.asr2application.core.ListItemWrapper;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ListItemWrapper> mListContent;


    public CustomAdapter(Context context, ArrayList<ListItemWrapper> listContent) {
        mContext = context;
        mListContent = listContent;
        //get the layout inflater
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListContent.size();
    }

    @Override
    public Object getItem(int position) {
        return mListContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.v("slim","position to update = "+position);

        convertView = mLayoutInflater.inflate(R.layout.custom_list_item_layout,parent,false);

        ImageView image = (ImageView)convertView.findViewById(R.id.image);
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView description = (TextView)convertView.findViewById(R.id.description);

        image.setImageResource(mListContent.get(position).getImageRes());
        title.setText(mListContent.get(position).getTitle());
        description.setText(mListContent.get(position).getDescription());

        return convertView;
    }
}
