package com.test.audio.music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;

import com.test.audio.R;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 李小凡 on 2018/4/17.
 */

public class MediaPlayerHelper implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener {

    public MediaPlayer mMediaPlayer;
    private PlaybackStateCompat mPlaybackState;
    private MediaSessionCompat mMediaSession;
    private MediaControllerCompat mMediaController;
    private MediaPlayerUpdateCallBack playerUpdateListener;
    private Context mContext;
    private AppCompatSeekBar seekBar;
    private List<MusicEntity> list_data;
    private int last_index;
    public final static String KEY_INDEX = "index";

//    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            int position = mMediaPlayer.getCurrentPosition();//当前位置
//            int duration = mMediaPlayer.getDuration();//持续时间
//            if (duration > 0) {
//                // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
//                long pos = (seekBar.getMax() * position / duration);
//                seekBar.setProgress((int) pos);
//                if (mMediaPlayer.getDuration() / 1000 == pos + 1) {
//                    mPlaybackState = new PlaybackStateCompat.Builder()
//                            .setState(PlaybackStateCompat.STATE_PAUSED, 0, 1.0f)
//                            .build();
//                    mMediaSession.setPlaybackState(mPlaybackState);
//                    MediaPlayerReset();
//                    playerUpdateListener.onCompletion(mMediaPlayer);
//                }
//            }
//            return false;
//        }
//    });


    public MediaPlayerHelper(Context mContext) {
        this.mContext = mContext;
        initService(mContext);
    }


    private MediaSessionCompat.Callback mMediaSessionCallback = new MediaSessionCompat.Callback() {

        @Override
        public void onPlayFromSearch(String query, Bundle extras) {
            last_index = extras.getInt(KEY_INDEX);
            MusicEntity entity = list_data.get(last_index);
            try {
                switch (mPlaybackState.getState()) {
                    case PlaybackStateCompat.STATE_PLAYING:
                    case PlaybackStateCompat.STATE_PAUSED:
                    case PlaybackStateCompat.STATE_NONE:
                        MediaPlayerReset();
                        //设置播放地址
                        mMediaPlayer.setDataSource(entity.getUrl());
                        //异步进行播放
                        mMediaPlayer.prepareAsync();
                        //设置当前状态为连接中
                        mPlaybackState = new PlaybackStateCompat.Builder()
                                .setState(PlaybackStateCompat.STATE_CONNECTING, 0, 2.0f)
                                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE
                                        | PlaybackStateCompat.ACTION_SKIP_TO_NEXT | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
                                .build();
                        //告诉MediaSession当前最新的音频状态。
                        mMediaSession.setPlaybackState(mPlaybackState);
                        //设置音频信息；
                        mMediaSession.setMetadata(getMusicEntity(entity.getMusicTitle(),
                                entity.getSinger(), entity.getAlbum()));
                        break;
                }
            } catch (IOException e) {
            }
//            Uri uri = extras.getParcelable(AudioPlayerService.PARAM_TRACK_URI);
//            onPlayFromUri(uri, null);
//            onPlayFromUri(null, null);
        }

        //播放网络歌曲
        //就是activity和notification的播放的回调方法。都会走到这里进行加载网络音频
        @Override
        public void onPlayFromUri(Uri uri, Bundle extras) {
            MusicEntity entity = list_data.get(last_index);
            try {
                switch (mPlaybackState.getState()) {
                    case PlaybackStateCompat.STATE_PLAYING:
                    case PlaybackStateCompat.STATE_PAUSED:
                    case PlaybackStateCompat.STATE_NONE:
                        MediaPlayerReset();
                        //设置播放地址
                        mMediaPlayer.setDataSource(entity.getUrl());
                        //异步进行播放
                        mMediaPlayer.prepareAsync();
                        //设置当前状态为连接中
                        mPlaybackState = new PlaybackStateCompat.Builder()
                                .setState(PlaybackStateCompat.STATE_CONNECTING, 0, 2.0f)
                                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE
                                        | PlaybackStateCompat.ACTION_SKIP_TO_NEXT | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
                                .build();
                        //告诉MediaSession当前最新的音频状态。
                        mMediaSession.setPlaybackState(mPlaybackState);
                        //设置音频信息；
                        mMediaSession.setMetadata(getMusicEntity(entity.getMusicTitle(),
                                entity.getSinger(), entity.getAlbum()));
                        break;
                }
            } catch (IOException e) {
            }
        }

        @Override
        public void onPause() {
            switch (mPlaybackState.getState()) {
                case PlaybackStateCompat.STATE_PLAYING:
                    mMediaPlayer.pause();
                    mPlaybackState = new PlaybackStateCompat.Builder()
                            .setState(PlaybackStateCompat.STATE_PAUSED, 0, 2.0f)
                            .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE
                                    | PlaybackStateCompat.ACTION_SKIP_TO_NEXT | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
                            .build();
                    mMediaSession.setPlaybackState(mPlaybackState);
                    updateNotification();
                    break;

            }
        }

        @Override
        public void onPlay() {
            switch (mPlaybackState.getState()) {
                case PlaybackStateCompat.STATE_PAUSED:
                    mMediaPlayer.start();
                    mPlaybackState = new PlaybackStateCompat.Builder()
                            .setState(PlaybackStateCompat.STATE_PLAYING, 0, 2.0f)
                            .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE
                                    | PlaybackStateCompat.ACTION_SKIP_TO_NEXT | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
                            .build();
                    mMediaSession.setPlaybackState(mPlaybackState);
                    updateNotification();
                    break;
            }
        }

        //下一曲
        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            if (last_index < list_data.size() - 1)
                last_index++;
            else
                last_index = 0;
            onPlayFromUri(null, null);

        }

        //上一曲
        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            if (last_index > 0)
                last_index--;
            else
                last_index = list_data.size() - 1;
            onPlayFromUri(null, null);
        }
    };

    /**
     * 重置player和seekbar进度
     */
    private void MediaPlayerReset() {
        seekBar.setProgress(0);
        mMediaPlayer.reset();
    }


    /**
     * 初始化各服务
     *
     * @param mContext
     */
    private void initService(Context mContext) {


        //初始化MediaSessionCompant
        mMediaSession = new MediaSessionCompat(mContext, MusicService.SESSION_TAG);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        //传递播放的状态信息
        mPlaybackState = new PlaybackStateCompat.Builder().
                setState(PlaybackStateCompat.STATE_NONE, 0, 2.0f)
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE
                        | PlaybackStateCompat.ACTION_SKIP_TO_NEXT | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
                .build();
        mMediaSession.setPlaybackState(mPlaybackState);//状态回调
        mMediaSession.setCallback(mMediaSessionCallback);//设置播放控制回调
        //设置可接受媒体控制
        mMediaSession.setActive(true);

        //初始化MediaPlayer
        mMediaPlayer = new MediaPlayer();
        // 设置音频流类型
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        // 初始化MediaController
        try {
            mMediaController = new MediaControllerCompat(mContext, mMediaSession.getSessionToken());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    //播放前的准备动作回调
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
        mPlaybackState = new PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 2.0f)
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE
                        | PlaybackStateCompat.ACTION_SKIP_TO_NEXT | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
                .build();
        mMediaSession.setPlaybackState(mPlaybackState);
        updateNotification();
        if (playerUpdateListener != null)
            playerUpdateListener.onPrepared(mediaPlayer);

    }

    public void setSpeed(float speed){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mMediaPlayer.setPlaybackParams(mMediaPlayer.getPlaybackParams().setSpeed(speed));
        }
    }

    //缓冲更新
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        if (playerUpdateListener != null)
            playerUpdateListener.onBufferingUpdate(mediaPlayer, percent);
    }

    public void destoryService() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mMediaSession != null) {
            mMediaSession.release();
            mMediaSession = null;
        }
    }

    private NotificationCompat.Action createAction(int iconResId, String title, String action) {
        Intent intent = new Intent(mContext, MusicService.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 1, intent, 0);
        return new NotificationCompat.Action(iconResId, title, pendingIntent);
    }

    private PendingIntent createPendingIntent(int iconResId, String title, String action) {
        Intent intent = new Intent(mContext, MusicService.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 1, intent, 0);
        return pendingIntent;
    }

    private PendingIntent createContentIntent() {
        final Intent nowPlayingIntent = new Intent();
        nowPlayingIntent.setComponent(new ComponentName("com.lxf.mvp", "com.test.audio.music.SongListActivity"));
        nowPlayingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent click = PendingIntent.getActivity(mContext, 0, nowPlayingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return click;
    }



    /**
     * 更新通知栏
     */
    private void updateNotification() {
        NotificationCompat.Action playPauseAction = mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING ?
                createAction(R.drawable.playbar_btn_pause, "暂停", MusicService.ACTION_PAUSE) :
                createAction(R.drawable.playbar_btn_play, "播放", MusicService.ACTION_PLAY);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(mContext)
                .setContentTitle(list_data.get(last_index).getMusicTitle())
                .setContentText(list_data.get(last_index).getSinger())
                .setContentIntent(createContentIntent())
                .setOngoing(mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING)
                .setShowWhen(false)
                .setSmallIcon(R.drawable.ic_notification)


                .setAutoCancel(false)
                .addAction(createAction(R.drawable.notify_prev, "上一首", MusicService.ACTION_LAST))
                .addAction(playPauseAction)
                .addAction(createAction(R.drawable.notify_next, "下一首", MusicService.ACTION_NEXT))
                .setStyle(new android.support.v7.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mMediaSession.getSessionToken())
                        .setCancelButtonIntent(createPendingIntent(R.drawable.notify_next, null, MusicService.ACTION_NEXT))
                        .setShowCancelButton(true)
                        .setShowActionsInCompactView(1, 2));
        Notification notification = notificationCompat.build();
        ((NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);


        //播放新歌曲时需要更新seekbar的max与总秒数对应。保证每一秒seekbar会走动一格。回传到View层来更新时间
        this.seekBar.setMax(mMediaPlayer.getDuration() / 1000);

        updateSeekBar();
    }

    /**
     * seekbar每秒更新一格进度
     */
    private void updateSeekBar() {
        // 计时器每一秒更新一次进度条
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
////                mediaPlayer在播放并且seebar没有被按下
//                if (mMediaPlayer != null && mMediaPlayer.isPlaying() && !seekBar.isPressed()) {
//                    MediaPlayerHelper.this.handler.sendEmptyMessage(0); //发送消息
//                }
//            }
//        };
//        // 每一秒触发一次
//        mTimer.schedule(timerTask, 0, 1000);

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
//                        long currentPosition = mPlaybackState.getPosition();
//                        if (mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING) {
//                            // Calculate the elapsed time between the last position update and now and unless
//                            // paused, we can assume (delta * speed) + current position is approximately the
//                            // latest position. This ensure that we do not repeatedly call the getPlaybackState()
//                            // on MediaControllerCompat.
//                            long timeDelta = SystemClock.elapsedRealtime() -
//                                    mPlaybackState.getLastPositionUpdateTime();
//                            currentPosition += (int) timeDelta * mPlaybackState.getPlaybackSpeed();
//                        }
//                        seekBar.setProgress((int) currentPosition);

                        int position = mMediaPlayer.getCurrentPosition();//当前位置
                        int duration = mMediaPlayer.getDuration();//持续时间
                        if (duration > 0) {
                            // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                            long pos = (seekBar.getMax() * position / duration);
                            seekBar.setProgress((int) pos);
                            if (mMediaPlayer.getDuration() / 1000 == pos + 1) {
                                mPlaybackState = new PlaybackStateCompat.Builder()
                                        .setState(PlaybackStateCompat.STATE_PAUSED, 0, 2.0f)
                                        .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE
                                                | PlaybackStateCompat.ACTION_SKIP_TO_NEXT | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
                                        .build();
                                mMediaSession.setPlaybackState(mPlaybackState);
                                MediaPlayerReset();
                                playerUpdateListener.onCompletion(mMediaPlayer);
                            }
                        }
                    }
                });
    }

    public MediaControllerCompat getmMediaController() {
        return mMediaController;
    }

    public MediaSessionCompat.Token getMediaSessionToken() {
        return mMediaSession.getSessionToken();
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void setSeekBar(AppCompatSeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public void setMediaPlayerUpdateListener(MediaPlayerUpdateCallBack listener) {
        this.playerUpdateListener = listener;
    }

    public void setPlayeData(List<MusicEntity> list_data) {
        this.list_data = list_data;
    }

    /**
     * 设置通知(mediasession歌曲)信息
     */
    private MediaMetadataCompat getMusicEntity(String musicName, String Singer, String album) {
        MediaMetadataCompat.Builder mediaMetadataCompat = new MediaMetadataCompat.Builder();
        mediaMetadataCompat.putString(MediaMetadataCompat.METADATA_KEY_AUTHOR, Singer)//歌手
        .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)//专辑
        .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST, "METADATA_KEY_ALBUM_ARTIST")//专辑
        .putLong(MediaMetadata.METADATA_KEY_DURATION, 222345)
        .putLong(MediaMetadata.METADATA_KEY_TRACK_NUMBER, 10)
        .putLong(MediaMetadata.METADATA_KEY_NUM_TRACKS, 11)
//        .putBitmap()
        .putString(MediaMetadataCompat.METADATA_KEY_TITLE, musicName);//title


//        mMediaSession.setMetadata(new MediaMetadata.Builder()
//                .putString(MediaMetadata.METADATA_KEY_ARTIST, getArtistName())
//                .putString(MediaMetadata.METADATA_KEY_ALBUM_ARTIST, getAlbumArtistName())
//                .putString(MediaMetadata.METADATA_KEY_ALBUM, getAlbumName())
//                .putString(MediaMetadata.METADATA_KEY_TITLE, getTrackName())
//                .putLong(MediaMetadata.METADATA_KEY_DURATION, duration())
//                .putLong(MediaMetadata.METADATA_KEY_TRACK_NUMBER, getQueuePosition() + 1)
//                .putLong(MediaMetadata.METADATA_KEY_NUM_TRACKS, getQueue().length)
//                .putString(MediaMetadata.METADATA_KEY_GENRE, getGenreName())
//                .putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART,
//                        mShowAlbumArtOnLockscreen ? albumArt : null)
//                .build());
        return mediaMetadataCompat.build();
    }


    public interface MediaPlayerUpdateCallBack {
        void onCompletion(MediaPlayer mediaPlayer);

        void onBufferingUpdate(MediaPlayer mediaPlayer, int percent);

        void onPrepared(MediaPlayer mediaPlayer);
    }

    /**
     * 秒转为分:秒
     *
     * @param second
     * @return
     */
    public static String turnTime(int second) {
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
        return (d > 0 ? d > 9 ? d : "0" + d : "00") + ":" + (s > 9 ? s : "0" + s);
    }
}
