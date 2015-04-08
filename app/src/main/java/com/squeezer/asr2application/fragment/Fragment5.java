package com.squeezer.asr2application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squeezer.asr2application.R;


public class Fragment5 extends Fragment implements View.OnClickListener {

    private Button mButton;

    private static OnButtonClicked mListener;


    public static Fragment5 newInstance(OnButtonClicked listener) {

        mListener = listener;

        return new Fragment5();
    }

    public Fragment5() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mButton = (Button) rootView.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if (mListener != null) {
                    mListener.buttonClicked();
                }
                break;
        }
    }


    public interface OnButtonClicked {
        public void buttonClicked();
    }


}