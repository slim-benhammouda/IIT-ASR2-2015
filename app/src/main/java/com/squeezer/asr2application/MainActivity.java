package com.squeezer.asr2application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

    private static final String PREFERENCE_BOTTOM_FRAGMENT_KEY = "bottom_layout_key";
    private static final String PREFERENCE_BOTTOM_FRAGMENT_VALUE_1 = "fragment_2";
    private static final String PREFERENCE_BOTTOM_FRAGMENT_VALUE_2 = "fragment_3";


    private SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("slim", "onCreate");
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            Log.v("slim", "savedInstanceState == null");
        } else {
            Log.v("slim", "savedInstanceState != null");
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, MainFragment.newInstance(this))
                .commit();


        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultBottomContent = mSharedPref.getString(PREFERENCE_BOTTOM_FRAGMENT_KEY, PREFERENCE_BOTTOM_FRAGMENT_VALUE_1);
        if (defaultBottomContent.equals(PREFERENCE_BOTTOM_FRAGMENT_VALUE_1)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.bottom_layout, new Fragment2())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.bottom_layout, new Fragment3())
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
                startActivityForResult(intent, RESULT_REQUEST_CODE);
                break;
            case R.id.action_add:
                AddDialogFragment addDialogFragment = AddDialogFragment.newInstance(this);
                addDialogFragment.show(getSupportFragmentManager(), "test");
                break;
            case R.id.action_about:
                Intent intent3 = new Intent(getApplicationContext(), Activity3.class);
                startActivity(intent3);
                break;
            case R.id.action_view:
                Intent intent5 = new Intent(getApplicationContext(), Activity5.class);
                startActivity(intent5);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == RESULT_REQUEST_CODE) {
            Log.v("slim", "onActivityResult called with request code = RESULT_REQUEST_CODE and result code = " + resultCode);
            //DO here what ever you want

        }

    }


    @Override
    public void buttonClicked() {
        SharedPreferences.Editor prefEditor = mSharedPref.edit();
        prefEditor.putString(PREFERENCE_BOTTOM_FRAGMENT_KEY, PREFERENCE_BOTTOM_FRAGMENT_VALUE_2);
       prefEditor.commit();
    }

    @Override
    public void onOk(String title, String description) {
        Log.v("slim", "ok clicked");
    }


}
