package com.squeezer.asr2application.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squeezer.asr2application.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class MainFragment extends Fragment implements View.OnClickListener {

    private TextView mTextView;
    private Button mButton;
    private Button mSaveButton;

    private Handler mHandler;

    private static OnButtonClicked mListener;


    public static MainFragment newInstance(OnButtonClicked listener) {

        mListener = listener;

        return new MainFragment();
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.text);
        mButton = (Button) rootView.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mSaveButton = (Button) rootView.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(this);
        mHandler = new Handler();
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
            case R.id.save_button:
                //Solution with thread
                //WriteFileThread thread = new WriteFileThread();
                //thread.start();

                //Solution with simple AsyncTask
                //WriteFileAsyncTask writeFileAsyncTask = new WriteFileAsyncTask();
                //writeFileAsyncTask.execute();

                //Solution with AsyncTask with params
                WriteFileWithMessageAsyncTask asyncTask = new WriteFileWithMessageAsyncTask();
                asyncTask.execute(new String[]{"test.txt"});
                // if you want to run many AsyncTasks at the same time
                // asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,new String[]{"test.txt"});

                break;
        }
    }


    public interface OnButtonClicked {
        public void buttonClicked();
    }

    private void saveButtonClickedPrivate() {


        Log.v("slim", "private folder path = " + getActivity().getFilesDir().getAbsolutePath());

        try {
            String filePath = getActivity().getFilesDir().getAbsolutePath() + File.separator + "filename.txt";

            File file = new File(filePath);
            PrintWriter out = new PrintWriter(file);
            out.println("test write file android " + System.currentTimeMillis());
            out.close();

            //FileUtils.

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void saveButtonClickedPublic() {


        Log.v("slim", "public folder path = " + Environment.getExternalStorageDirectory());

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            try {
                String filePath = Environment.getExternalStorageDirectory() + File.separator + "filename.txt";
                File file = new File(filePath);
                PrintWriter out = new PrintWriter(file);
                out.println("test write file android " + System.currentTimeMillis());
                out.close();
                //Solution 1
                //getActivity().runOnUiThread(new Runnable() {
                //    @Override
                //    public void run() {
                //        mTextView.setText("sauvegarde terminée");
                //    }
                //});
                //Solution 2
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("sauvegarde terminée");
                    }
                });


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private class WriteFileThread extends Thread {

        public void run() {
            saveButtonClickedPublic();
        }
    }

    private class WriteFileAsyncTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... param) {
            Log.v("slim", "do in background with media state " + Environment.getExternalStorageState());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                try {
                    String filePath = Environment.getExternalStorageDirectory() + File.separator + "filename.txt";
                    File file = new File(filePath);
                    PrintWriter out = new PrintWriter(file);
                    out.println("test write file android " + System.currentTimeMillis());
                    out.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Void param) {
            mTextView.setText("sauvegarde terminée");
        }
    }

    private class WriteFileWithMessageAsyncTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... param) {
            String result = "no file to write";
            if (param.length > 0) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                    try {
                        String filePath = Environment.getExternalStorageDirectory() + File.separator + param[0];
                        File file = new File(filePath);
                        PrintWriter out = new PrintWriter(file);
                        out.println("test write file android " + System.currentTimeMillis());
                        out.close();
                        result = param[0] + " written successfully";

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        result = "failed to wwrite file";
                    }
                } else {
                    result = "media not ready for save";
                }
            }
            return result;
        }

        protected void onPostExecute(String param) {
            mTextView.setText(param);
        }
    }

}