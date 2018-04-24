package com.test.audio.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.test.audio.BaseApplication;
import com.test.audio.R;
import com.test.audio.music.MusicEntity;

import java.io.IOException;
import java.util.List;

/**
 * Created by 李小凡 on 2018/4/9.
 */

public class AudioPlayService extends Service {

    private static final String TAG = "lxf";
    private MediaPlayer mPlayer;
    private BaseApplication application;
    private List<MusicEntity> songs;
    private int songItemPos = 0;

    private NotificationManager mNotificationManager;
    private int mNotificationId = 1000;
    Notification mNotification;
    private long mNotificationPostTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        application = (BaseApplication) getApplication();
        songs = application.songs;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        playMusic(songItemPos);
        return new MusicListener();
    }

    public void playMusic(int position) {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        mPlayer = new MediaPlayer();
        try {
            mPlayer.reset();
            mPlayer.setOnErrorListener(onErrorListener);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(songs.get(position).getUrl());
            mPlayer.setOnPreparedListener(preparedListener);
            mPlayer.prepareAsync();

//            File file = new File(path);
//            FileInputStream fis = new FileInputStream(file);
//            mPlayer.setDataSource(fis.getFD());
//            mPlayer.prepare();
//            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.setOnBufferingUpdateListener(bufferingUpdateListener);
    }

    private void playMusic(String path) {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        mPlayer = new MediaPlayer();
        try {
            mPlayer.reset();
            mPlayer.setOnErrorListener(onErrorListener);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(path);
            mPlayer.setOnPreparedListener(preparedListener);
            mPlayer.prepareAsync();

//            File file = new File(path);
//            FileInputStream fis = new FileInputStream(file);
//            mPlayer.setDataSource(fis.getFD());
//            mPlayer.prepare();
//            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.setOnBufferingUpdateListener(bufferingUpdateListener);
    }

    MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.d(TAG, "MediaPlayer.OnPreparedListener ~");
            mp.start();
            mp.setOnCompletionListener(onCompletionListener);
            updateNotification();
            listener.onBufferingUpdate(mp, 0);
        }
    };

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Log.d(TAG, "MediaPlayer.OnCompletionListener ~");
//            playMusic(index < songList.length  - 1 ? songList[++index] : songList[index = 0]);
            playMusic(songItemPos < songs.size() ? ++songItemPos : (songItemPos = 0));
        }
    };

    MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            Log.d(TAG, "MediaPlayer.OnErrorListener ~");
            mediaPlayer.release();
            return false;
        }
    };

    MediaPlayer.OnBufferingUpdateListener bufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {

        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if(listener != null){
                listener.onBufferingUpdate(mp, percent);
            }
        }
    };

    public interface OnBufferingUpdateListener{
        void onBufferingUpdate(MediaPlayer mp, int percent);
    }

    private OnBufferingUpdateListener listener;

    public void setBufferingUpdateListener(OnBufferingUpdateListener bufferingUpdateListener) {
        this.listener = bufferingUpdateListener;
    }

    public void playOrPause() {
        try {
            if (mPlayer != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                } else {
                    mPlayer.start();
                }
            }
        } catch (final Exception ignored) {
        }
    }

    public void playNext() {
//        playMusic(index < songList.length - 1 ? songList[++index] : songList[index = 0]);
        playMusic(songItemPos < songs.size() - 1 ? ++songItemPos : (songItemPos = 0));
    }

    public void playPrev() {
//        playMusic(index > 0 ? songList[--index] : songList[index = songList.length - 1]);
        playMusic(songItemPos > 0 ? --songItemPos : (songItemPos = songs.size() - 1));
    }


    private class MusicListener extends Binder implements IMusicControl {

        @Override
        public void play() {
            if (mPlayer != null) {
                mPlayer.start();
            }
        }

        @Override
        public void pause() {
            if (mPlayer != null) {
                mPlayer.pause();
            }
        }

        @Override
        public void stop() {
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }
        }

        @Override
        public void next() {
            if (mPlayer != null) {
                mPlayer.stop();
                playMusic(++application.songItemPos);
            }
        }

        @Override
        public void prev() {
            if (mPlayer != null) {
                mPlayer.stop();
                playMusic(--application.songItemPos);
            }
        }

        @Override
        public int seekTo(int position) {
            if (mPlayer != null && mPlayer.isPlaying()) {
                int duration = mPlayer.getDuration();
                position = position * duration / 1000;
                if (position < 0) {
                    position = 0;
                } else if (position > duration) {
                    position = duration;
                }
                mPlayer.seekTo(position);
                return position;
            }
            return -1;
        }

        @Override
        public void setOnCompletionListener(MediaPlayer.OnPreparedListener listener) {

        }

        @Override
        public void setOnPreparedListener(MediaPlayer.OnPreparedListener listener) {

        }

        @Override
        public void setOnErrorListener(MediaPlayer.OnErrorListener listener) {

        }

        @Override
        public void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener listener) {

        }
    }

    private void updateNotification() {
        if(mNotification != null){
            cancelNotification();
        }
        mNotificationManager.notify(mNotificationId, getNotification());
    }

    public static final String TOGGLEPAUSE_ACTION = "com.test.audio.togglepause";
    public static final String PAUSE_ACTION = "com.test.audio.pause";
    public static final String STOP_ACTION = "com.test.audio.stop";
    public static final String PREVIOUS_ACTION = "com.test.audio.previous";
    public static final String PREVIOUS_FORCE_ACTION = "com.test.audio.force";
    public static final String NEXT_ACTION = "com.test.audio.next";
    public static final String MUSIC_CHANGED = "com.test.audio.change_music";

    private Notification getNotification() {
        final int PAUSE_FLAG = 0x1;
        final int NEXT_FLAG = 0x2;
        final int STOP_FLAG = 0x3;

        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.title, "我是标题");
        remoteViews.setTextViewText(R.id.text, "我是内容");

        //此处action不能是一样的 如果一样的 接受的flag参数只是第一个设置的值
        Intent pauseIntent = new Intent(TOGGLEPAUSE_ACTION);
        pauseIntent.putExtra("FLAG", PAUSE_FLAG);
        PendingIntent pausePIntent = PendingIntent.getBroadcast(this, 0, pauseIntent, 0);
//        remoteViews.setImageViewResource(R.id.iv_pause, R.drawable.playbar_btn_pause);
        remoteViews.setOnClickPendingIntent(R.id.iv_pause, pausePIntent);

        Intent nextIntent = new Intent(NEXT_ACTION);
        nextIntent.putExtra("FLAG", NEXT_FLAG);
        PendingIntent nextPIntent = PendingIntent.getBroadcast(this, 0, nextIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_next, nextPIntent);

        Intent preIntent = new Intent(STOP_ACTION);
        preIntent.putExtra("FLAG", STOP_FLAG);
        PendingIntent prePIntent = PendingIntent.getBroadcast(this, 0, preIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_stop, prePIntent);

        final Intent nowPlayingIntent = new Intent();
        nowPlayingIntent.setComponent(new ComponentName("com.lxf.mvp", "com.test.audio.PlayListActivity"));
        nowPlayingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent click = PendingIntent.getActivity(this, 0, nowPlayingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.image, R.drawable.placeholder_disk_210);


        if (mNotificationPostTime == 0) {
            mNotificationPostTime = System.currentTimeMillis();
        }
        if (mNotification == null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setContent(remoteViews)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(click)
                    .setWhen(mNotificationPostTime);
            builder.setShowWhen(false);
            mNotification = builder.build();
        } else {
            mNotification.contentView = remoteViews;
        }

        return mNotification;
    }

    private void cancelNotification() {
        //mNotificationManager.cancel(hashCode());
        mNotificationManager.cancel(mNotificationId);
        mNotificationPostTime = 0;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
