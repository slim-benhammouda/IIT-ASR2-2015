package com.squeezer.asr2application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.squeezer.asr2application.adapter.CustomAdapter;
import com.squeezer.asr2application.core.ListItemWrapper;
import com.squeezer.asr2application.fragment.dialog.AddDialogFragment;
import com.squeezer.asr2application.view.CustomLabelEditView;

import java.util.ArrayList;


public class Activity3 extends ActionBarActivity implements AddDialogFragment.OnAddFragmentInteractionListener, AdapterView.OnItemClickListener {

    private static final String LIST_CONTENT_KEY = "list_content";
    public static final String LIST_ITEM_KEY = "list_item";

    private ListView mListView;
    private CustomAdapter mAdapter;
    private ArrayList<ListItemWrapper> mListContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_3_layout);


        initViews();


        if (savedInstanceState == null) {
            Log.v("slim", "savedInstanceState == null");
            mListContent = new ArrayList<ListItemWrapper>();

            fillAdapter();
        } else {
            Log.v("slim", "savedInstanceState != null");
            mListContent = (ArrayList) savedInstanceState.getSerializable(LIST_CONTENT_KEY);
        }

        mAdapter = new CustomAdapter(getApplicationContext(), mListContent);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);


    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.list_view);
        // list with array adapter
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_list_item,R.id.text, getResources().getStringArray(R.array.my_list_content));
        //mListView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(LIST_CONTENT_KEY, mListContent);
    }

    private void fillAdapter() {


        for (int i = 0; i < 200; i++) {
            mListContent.add(new ListItemWrapper(R.drawable.ic_launcher, "item title "+i, "item description "+i));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.action_add:
                AddDialogFragment addDialogFragment = AddDialogFragment.newInstance(this);
                addDialogFragment.show(getSupportFragmentManager(), "test");
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onOk(String title, String description) {
        mListContent.add(new ListItemWrapper(R.drawable.ic_launcher, title, description));
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.v("slim","clicked position = "+position);
        Intent intent = new Intent(getApplicationContext(), Activity4.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST_ITEM_KEY, mListContent.get(position));
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
