package com.squeezer.asr2application;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.squeezer.asr2application.core.ListItemWrapper;


public class Activity4 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_layout);
        TextView textView = (TextView) findViewById(R.id.Text4);
        Bundle bundle =  getIntent().getExtras();
        if (bundle != null){
            ListItemWrapper listItemWrapper = (ListItemWrapper) bundle.getSerializable(Activity3.LIST_ITEM_KEY);
            if (listItemWrapper != null){
                textView.setText(listItemWrapper.getTitle()+"\n"+listItemWrapper.getDescription());
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
