package com.squeezer.asr2application.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squeezer.asr2application.AudioService;
import com.squeezer.asr2application.R;

import java.io.IOException;
import java.lang.ref.WeakReference;


public class Fragment6 extends Fragment implements View.OnClickListener {

    public static final String MEDIA_PLAYER_APP_MESSENGER_KEY = "app_messenger";


    private Button mButton;

    private static OnButtonClicked mListener;


    private AppHandler mHandler;
    private Messenger mAppMessenger;
    private MediaPlayerServiceConnection mConnection = new MediaPlayerServiceConnection();
    private Messenger messengerToService;

    private boolean isServiceConnected = false;



    public static Fragment6 newInstance(OnButtonClicked listener) {

        mListener = listener;

        return new Fragment6();
    }

    public Fragment6() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_6_layout, container, false);
        mButton = (Button) rootView.findViewById(R.id.play_button);
        mButton.setOnClickListener(this);

        mHandler = new AppHandler(this);
        mAppMessenger = new Messenger(mHandler);

        Intent serviceIntent = new Intent(getActivity(),
                AudioService.class);
        serviceIntent.putExtra(MEDIA_PLAYER_APP_MESSENGER_KEY, mAppMessenger);
        getActivity().startService(serviceIntent);
        Log.v("iit","service started in fragment");
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
                playAudio();
                break;
        }
    }


    private void playAudio(){
        if (messengerToService != null) {
            try {
                Message message = Message.obtain();
                message.what = AudioService.MEDIA_PLAYER_CONTROL_START;
                messengerToService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }


    public interface OnButtonClicked {
        public void buttonClicked();
    }

    private void doBind() {
        Log.v("iit","request service bind in fragment");
        getActivity().bindService(
                new Intent(getActivity(), AudioService.class),
                mConnection, Context.BIND_AUTO_CREATE);

    }

    private void doUnbindService() {
        if (messengerToService != null) {
            try {
                Message message = Message.obtain();
                message.what = AudioService.MEDIA_PLAYER_SERVICE_CLIENT_UNBOUND;
                messengerToService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        getActivity().unbindService(mConnection);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        doUnbindService();
    }


    /***********************************************************/
    /***************** private classes *************************/
    /***********************************************************/

    private class MediaPlayerServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {

            isServiceConnected = true;
            messengerToService = new Messenger(binder);

            //try {
                //Message message = Message.obtain();
                //message.what = MediaPlayerService.MEDIA_PLAYER_GET_PODCASTS;
                //messengerToService.send(message);
            //} catch (RemoteException e1) {
              //  e1.printStackTrace();
            //}
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            messengerToService = null;
        }
    }

    private static class AppHandler extends Handler {

        private final WeakReference<Fragment6> mTarget;

        private AppHandler(Fragment6 target) {
            mTarget = new WeakReference<Fragment6>(target);
        }

        @Override
        public void handleMessage(Message message) {

            Fragment6 target = mTarget.get();
            Bundle bundle;
            switch (message.what) {
                case AudioService.MEDIA_PLAYER_SERVICE_STARTED:
                    target.doBind();
                    break;

                case AudioService.MEDIA_PLAYER_CONTROL_START:
                    //target.updatePlayBouton();
                    break;
                case AudioService.MEDIA_PLAYER_CONTROL_PAUSE:
                    //target.updatePausePBouton();
                    break;


            }
        }
    }


}