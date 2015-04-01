package com.squeezer.asr2application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.squeezer.asr2application.view.CustomLabelEditView;


public class Activity3 extends ActionBarActivity {


    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_3_layout);

        initViews();


    }


    private void initViews() {
        mListView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_list_item,R.id.text, getResources().getStringArray(R.array.my_list_content));
        mListView.setAdapter(arrayAdapter);
    }


}
