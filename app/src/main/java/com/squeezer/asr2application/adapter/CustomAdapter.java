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
import java.util.Calendar;

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

        Log.v("slim", "position to update = " + position);

        long startTime = Calendar.getInstance().getTimeInMillis();
        ViewHolder viewHolder;

        if (convertView == null) {
            Log.v("slim", "convertView==null");
            convertView = mLayoutInflater.inflate(R.layout.custom_list_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.mTitleTextView = (TextView) convertView.findViewById(R.id.title);
            viewHolder.mDescriptionTextView = (TextView) convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);

        } else {
            Log.v("slim", "convertView!=null");
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImageView.setImageResource(mListContent.get(position).getImageRes());
        viewHolder.mTitleTextView.setText(mListContent.get(position).getTitle());
        viewHolder.mDescriptionTextView.setText(mListContent.get(position).getDescription());

        long endTime = Calendar.getInstance().getTimeInMillis();
        Log.v("slim", "-----spent time to update = " + (endTime - startTime));
        return convertView;
    }


    private static class ViewHolder {

        ImageView mImageView;
        TextView mTitleTextView;
        TextView mDescriptionTextView;

    }
}
