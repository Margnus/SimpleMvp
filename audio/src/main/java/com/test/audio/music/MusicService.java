package com.test.audio.music;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.test.audio.lock.LockActivity;

/**
 * Created by 李小凡 on 2018/4/17.
 */

public class MusicService extends Service {

    public static final String SESSION_TAG = "myMusic";
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_LAST = "last";
    public static final String PARAM_TRACK_URI = "uri";
    public static final String PLAY_DATA = "data";
    private static final String TAG = "lxf";
    public static final String LOCK_SCREEN = "com.test.audio.music.lock";

    private MediaPlayerHelper mediaPlayerHelper;


    public class ServiceBinder extends Binder {

        public MusicService getService() {
            return MusicService.this;
        }
    }

    private Binder mBinder = new ServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取audio service
        mediaPlayerHelper = new MediaPlayerHelper(getApplicationContext());

        // Initialize the intent filter and each action
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(LOCK_SCREEN);
        // Attach the broadcast listener
        registerReceiver(mIntentReceiver, filter);
    }

    private boolean mIsLocked;

    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String command = intent.getStringExtra("command");
            final String action = intent.getAction();
            Log.d(TAG, "onreceive" + intent.toURI());
            if (Intent.ACTION_SCREEN_OFF.equals(action) ){
                handleScreenOff();
            } else if (LOCK_SCREEN.equals(action)){
                mIsLocked = intent.getBooleanExtra("islock",true);
            }
        }
    };

    private void handleScreenOff() {
        if(mediaPlayerHelper.getmMediaController().getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING && !mIsLocked){
            Intent lockscreen = new Intent(this, LockActivity.class);
            lockscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(lockscreen);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_PLAY:
                    mediaPlayerHelper.getmMediaController().getTransportControls().play();
                    break;
                case ACTION_NEXT:
                    mediaPlayerHelper.getmMediaController().getTransportControls().skipToNext();
                    break;
                case ACTION_LAST:
                    mediaPlayerHelper.getmMediaController().getTransportControls().skipToPrevious();
                    break;
                case ACTION_PAUSE:
                    mediaPlayerHelper.getmMediaController().getTransportControls().pause();
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mIntentReceiver);
        mediaPlayerHelper.destoryService();
    }

    public MediaSessionCompat.Token getMediaSessionToken() {
        return mediaPlayerHelper.getMediaSessionToken();
    }
    public MediaPlayerHelper getMediaPlayerHelper() {
        return mediaPlayerHelper;
    }
}