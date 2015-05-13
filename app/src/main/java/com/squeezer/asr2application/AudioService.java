package com.squeezer.asr2application;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.squeezer.asr2application.fragment.Fragment6;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class AudioService extends Service {

    public static final String MEDIA_PLAYER_STARTED_KEY = "started";

    public static final int MEDIA_PLAYER_SERVICE_STARTED = 10;
    public static final int MEDIA_PLAYER_CONTROL_START = 21;
    public static final int MEDIA_PLAYER_CONTROL_PAUSE = 22;
    public static final int MEDIA_PLAYER_SERVICE_CLIENT_UNBOUND = 30;



    private MediaPlayer mMediaPlayer;

    private Messenger mServiceMessenger;
    private Messenger messengerToApp;

    private boolean isServiceBound = false;


    @Override
    public IBinder onBind(Intent arg0) {
        isServiceBound = true;
        return mServiceMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        mMediaPlayer = new MediaPlayer();
        mServiceMessenger = new Messenger(new ServiceHandler(this));
        playMgs();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int stratdId) {

        if (intent != null) {

            messengerToApp = intent
                    .getParcelableExtra(Fragment6.MEDIA_PLAYER_APP_MESSENGER_KEY);
            if (messengerToApp != null) {
                try {
                    Message message = Message.obtain();
                    message.what = MEDIA_PLAYER_SERVICE_STARTED;
                    messengerToApp.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceBound = false;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
        mMediaPlayer.release();
    }


    private void playMgs(){

        Log.v("iit","playMgs");

        try {
            mMediaPlayer.setDataSource("/sdcard/Download/mgs-theme.mp3");
            mMediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    /***********************************************************/
    /***************** private classes *************************/
    /***********************************************************/

    private static class ServiceHandler extends Handler {

        private final WeakReference<AudioService> mTarget;

        private ServiceHandler(AudioService target) {
            mTarget = new WeakReference<AudioService>(target);
        }

        @Override
        public void handleMessage(Message message) {

            AudioService target = mTarget.get();
            Bundle bundle;

            switch (message.what) {

                case AudioService.MEDIA_PLAYER_CONTROL_START:
                    Log.v("iit","start requested in service");
                    target.mMediaPlayer.start();

//                    try {
//                        Message messagePlay = Message.obtain();
//                        messagePlay.what = MEDIA_PLAYER_CONTROL_START;
//                        target.messengerToApp.send(messagePlay);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
                    break;
                case AudioService.MEDIA_PLAYER_CONTROL_PAUSE:
                    target.mMediaPlayer.pause();
                    try {
                        Message messagePause = Message.obtain();
                        messagePause.what = MEDIA_PLAYER_CONTROL_PAUSE;
                        target.messengerToApp.send(messagePause);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                case AudioService.MEDIA_PLAYER_SERVICE_CLIENT_UNBOUND:

                    target.isServiceBound = false;

                    break;
            }
        }
    }

}
