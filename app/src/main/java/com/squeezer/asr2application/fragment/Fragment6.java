package com.squeezer.asr2application.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
import android.widget.SeekBar;

import com.squeezer.asr2application.AudioService;
import com.squeezer.asr2application.R;

import java.lang.ref.WeakReference;


public class Fragment6 extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    public static final String MEDIA_PLAYER_APP_MESSENGER_KEY = "app_messenger";


    private Button mPlayButton;
    private Button mStopButton;

    private SeekBar mSeekBar;
    private boolean isSeek =false;

    private AppHandler mHandler;
    private Messenger mAppMessenger;
    private MediaPlayerServiceConnection mConnection = new MediaPlayerServiceConnection();
    private Messenger messengerToService;

    private boolean isServiceConnected = false;

    private boolean isPlaying = false;


    public static Fragment6 newInstance() {

        return new Fragment6();
    }

    public Fragment6() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_6_layout, container, false);
        mPlayButton = (Button) rootView.findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(this);

        mStopButton = (Button) rootView.findViewById(R.id.stop_button);
        mStopButton.setOnClickListener(this);

        mSeekBar = (SeekBar) rootView.findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(this);

        mHandler = new AppHandler(this);
        mAppMessenger = new Messenger(mHandler);

        Intent serviceIntent = new Intent(getActivity(),
                AudioService.class);
        serviceIntent.putExtra(MEDIA_PLAYER_APP_MESSENGER_KEY, mAppMessenger);
        getActivity().startService(serviceIntent);
        Log.v("iit", "service started in fragment");
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
                if (!isPlaying) {
                    playAudio();
                } else {
                    pauseAudio();
                }
                break;
            case R.id.stop_button:
                stopAudio();
                break;
        }
    }


    private void playAudio() {
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

    private void pauseAudio() {
        if (messengerToService != null) {
            try {
                Message message = Message.obtain();
                message.what = AudioService.MEDIA_PLAYER_CONTROL_PAUSE;
                messengerToService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    private void stopAudio() {
        if (messengerToService != null) {
            try {
                Message message = Message.obtain();
                message.what = AudioService.MEDIA_PLAYER_CONTROL_STOP;
                messengerToService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    private void seekAudio(int progress) {
        if (messengerToService != null) {
            try {
                Message message = Message.obtain();
                message.what = AudioService.MEDIA_PLAYER_CONTROL_PROGRESS;
                message.arg1 = progress;
                messengerToService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }


    private void doBind() {
        Log.v("iit", "request service bind in fragment");
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

    private void updatePlayButton() {
        isPlaying = true;
        mPlayButton.setText("Pause");

    }

    private void updatePauseButton() {
        isPlaying = false;
        mPlayButton.setText("Play");
    }

    private void stopPerformed() {
        isPlaying = false;
        mPlayButton.setText("Play");
        mSeekBar.setProgress(0);
    }

    private void progressPerformed(int duration, int currentPosition) {
        int progress =  (int)((float)currentPosition/(float)duration*100);
        mSeekBar.setProgress(progress);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (isSeek) {
            seekAudio(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isSeek = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
isSeek = false;
    }


    /***********************************************************/
    /***************** private classes *************************/
    /**
     * *******************************************************
     */

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
                    target.updatePlayButton();
                    break;
                case AudioService.MEDIA_PLAYER_CONTROL_PAUSE:
                    target.updatePauseButton();
                    break;
                case AudioService.MEDIA_PLAYER_CONTROL_STOP:
                    target.stopPerformed();
                    break;
                case AudioService.MEDIA_PLAYER_CONTROL_PROGRESS:
                    Log.v("asr","progress in app with duration = "+message.arg1+" and current position = "+message.arg2);
                    target.progressPerformed(message.arg1, message.arg2);
                    break;
            }
        }
    }


}