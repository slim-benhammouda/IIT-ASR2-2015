package com.squeezer.asr2application;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.squeezer.asr2application.fragment.Fragment2;
import com.squeezer.asr2application.fragment.Fragment3;
import com.squeezer.asr2application.fragment.MainFragment;
import com.squeezer.asr2application.fragment.dialog.AddDialogFragment;


public class MainActivity extends ActionBarActivity implements MainFragment.OnButtonClicked, AddDialogFragment.OnAddFragmentInteractionListener {

    public static final String EXTRA_NAME_KEY = "name";
    public static final String EXTRA_LAST_NAME_KEY = "last_name";
    public static final String EXTRA_AGE_KEY = "age";
    public static final int RESULT_REQUEST_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("slim", "onCreate");
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.newInstance(this))
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.bottom_layout, new Fragment2())
                    .commit();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("slim", "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("slim", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("slim", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("slim", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("slim", "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), Activity2.class);
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_AGE_KEY, 25);
                bundle.putCharSequence(EXTRA_NAME_KEY, "Slim");
                bundle.putCharSequence(EXTRA_LAST_NAME_KEY, "BH");
                intent.putExtras(bundle);
                startActivityForResult(intent,RESULT_REQUEST_CODE);
                break;
            case R.id.action_add:
                AddDialogFragment addDialogFragment = AddDialogFragment.newInstance(this);
                addDialogFragment.show(getSupportFragmentManager(), "test");
                break;
            case R.id.action_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.bottom_layout, new Fragment2()).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void buttonClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_layout, new Fragment3()).commit();
    }

    @Override
    public void onOk() {
        Log.v("slim", "ok clicked");
    }

    @Override
    public void onCancel() {

    }
}
