package com.squeezer.asr2application;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squeezer.asr2application.view.CustomLabelEditView;


public class Activity2 extends ActionBarActivity implements View.OnClickListener {


    private CustomLabelEditView mNameField;
    private CustomLabelEditView mLastNameField;
    private CustomLabelEditView mAgeField;

    private Button mOkButton;
    private Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_activity2);

        initViews();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int age = intent.getIntExtra(MainActivity.EXTRA_AGE_KEY, -1);
                CharSequence name = bundle.getCharSequence(MainActivity.EXTRA_NAME_KEY, "Default Name");
                CharSequence lastName = bundle.getCharSequence(MainActivity.EXTRA_LAST_NAME_KEY, "Default Last Name");

                mNameField.setFieldText(name.toString());
                mLastNameField.setFieldText(lastName.toString());
                mAgeField.setFieldText(String.valueOf(age));
            }

        }


    }


    private void initViews() {
        mNameField = (CustomLabelEditView) findViewById(R.id.name_component);
        mLastNameField = (CustomLabelEditView) findViewById(R.id.last_name_component);
        mAgeField = (CustomLabelEditView) findViewById(R.id.age_component);

        mNameField.setLabelText(getResources().getString(R.string.name_label));
        mLastNameField.setLabelText(getResources().getString(R.string.last_name_label));
        mAgeField.setLabelText(getResources().getString(R.string.age_label));


        mOkButton = (Button) findViewById(R.id.ok_button);
        mOkButton.setOnClickListener(this);
        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_button:

                Bundle bundle = new Bundle();
                bundle.putInt(MainActivity.EXTRA_AGE_KEY, Integer.valueOf(mAgeField.getFieldText()));
                bundle.putCharSequence(MainActivity.EXTRA_NAME_KEY, mNameField.getFieldText());
                bundle.putCharSequence(MainActivity.EXTRA_LAST_NAME_KEY, mLastNameField.getFieldText());

                Intent intent = new Intent();
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.cancel_button:
                setResult(RESULT_CANCELED);
                finish();
                break;
            default:
                break;
        }
    }
}
