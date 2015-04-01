package com.squeezer.asr2application.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.squeezer.asr2application.R;


public class AddDialogFragment extends DialogFragment {



    private EditText mTitleEditText;
    private EditText mDescriptionEditText;

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

//get the inflater
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate the dialog view
        View dialogView = layoutInflater.inflate(R.layout.fragment_add_dialog_layout,null);
        //get views references
        mTitleEditText = (EditText)dialogView.findViewById(R.id.title_input);
        mDescriptionEditText = (EditText)dialogView.findViewById(R.id.description_input);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onOk(mTitleEditText.getText().toString(),mDescriptionEditText.getText().toString() );
                    }
                }
        ).setNegativeButton(android.R.string.cancel, null).setTitle("Add a new record").setView(dialogView);

        return builder.create();

    }


    public interface OnAddFragmentInteractionListener {

        public void onOk(String title,String description);

            }

}
