package com.squeezer.asr2application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squeezer.asr2application.core.JokeWrapper;
import com.squeezer.asr2application.fragment.Fragment2;
import com.squeezer.asr2application.fragment.Fragment3;
import com.squeezer.asr2application.fragment.MainFragment;
import com.squeezer.asr2application.fragment.dialog.AddDialogFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity implements MainFragment.OnButtonClicked, AddDialogFragment.OnAddFragmentInteractionListener, View.OnClickListener {

    public static final String EXTRA_NAME_KEY = "name";
    public static final String EXTRA_LAST_NAME_KEY = "last_name";
    public static final String EXTRA_AGE_KEY = "age";
    public static final int RESULT_REQUEST_CODE = 1000;

    private static final String PREFERENCE_BOTTOM_FRAGMENT_KEY = "bottom_layout_key";
    private static final String PREFERENCE_BOTTOM_FRAGMENT_VALUE_1 = "fragment_2";
    private static final String PREFERENCE_BOTTOM_FRAGMENT_VALUE_2 = "fragment_3";


    private SharedPreferences mSharedPref;

    private TextView mJokeText;
    private Button mJokeButton;


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


        mJokeText = (TextView) findViewById(R.id.text);
        mJokeButton = (Button) findViewById(R.id.joke_button);
        mJokeButton.setOnClickListener(this);


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
            case R.id.action_player:
                Intent intent6 = new Intent(getApplicationContext(), Activity6.class);
                startActivity(intent6);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.joke_button:
                retrieveJoke();
                break;
        }
    }


    private void retrieveJoke() {
        JokeRequestAsyncTask jokeTask = new JokeRequestAsyncTask();
        jokeTask.execute();

    }


    private class JokeRequestAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String result = null;
            HttpURLConnection con = null;
            InputStream is = null;
            try {
                String wsUrl = "http://api.icndb.com/jokes/random";

                con = (HttpURLConnection) (new URL(wsUrl)).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();

                // read the response
                StringBuffer buffer = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null) {
                    buffer.append(line + "\r\n");
                }

                is.close();
                con.disconnect();
                result = buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }

        protected void onPostExecute(String data) {

            Gson gson = new GsonBuilder().create();
            JokeWrapper
                    jokeWrapper = gson.fromJson(data,
                    JokeWrapper.class);


            mJokeText.setText(jokeWrapper.getJoke());
        }

    }
}
