package com.squeezer.asr2application.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squeezer.asr2application.R;

/**
 * Created by mariem on 11/03/15.
 */
public class CustomLabelEditView extends RelativeLayout {


    private TextView mLabel;
    private EditText mField;

    public CustomLabelEditView(Context context) {
        this(context, null);
    }


    public CustomLabelEditView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initViews(context);;
    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_label_edit_view, this);


        mLabel = (TextView) view.findViewById(R.id.label);
        mField = (EditText) view.findViewById(R.id.field);
    }

    public void setLabelText(String text) {
        mLabel.setText(text);
    }

    public void setFieldText(String text) {
        mField.setText(text);
    }

    public String getLabelText() {
        return mLabel.getText().toString();
    }

    public String getFieldText() {
        return mField.getText().toString();
    }


}
