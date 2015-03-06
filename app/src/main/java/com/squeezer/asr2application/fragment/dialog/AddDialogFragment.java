package com.squeezer.asr2application.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squeezer.asr2application.R;


public class AddDialogFragment extends DialogFragment {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static OnAddFragmentInteractionListener mListener;

    public static AddDialogFragment newInstance(OnAddFragmentInteractionListener listener) {

        mListener = listener;
        AddDialogFragment fragment = new AddDialogFragment();
        return fragment;
    }

    public AddDialogFragment() {

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onOk();
                    }
                }
        ).setNegativeButton(android.R.string.cancel, null).setTitle("my dialog");

        return builder.create();

    }


    public interface OnAddFragmentInteractionListener {

        public void onOk();

        public void onCancel();
    }

}
