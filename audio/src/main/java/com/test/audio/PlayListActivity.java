package com.test.audio;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.test.audio.music.MusicEntity;
import com.test.audio.widget.PlayerSeekBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 李小凡 on 2018/4/10.
 */

@Route(path = "/audio/list")
public class PlayListActivity extends AppCompatActivity {


    private static final String TAG = "LXF";

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.playbar_img)
    ImageView playbarImg;
    @BindView(R2.id.playbar_info)
    TextView playbarInfo;
    @BindView(R2.id.playbar_singer)
    TextView playbarSinger;
    @BindView(R2.id.play_prev)
    ImageView playPrev;
    @BindView(R2.id.control)
    ImageView control;
    @BindView(R2.id.play_next)
    ImageView playNext;
    @BindView(R2.id.song_progress_normal)
    PlayerSeekBar mProgress;

    private SongListAdapter adapter;

    private MediaPlayer mPlayer;

    private int index;

    private NotificationManager mNotificationManager;
    private int mNotificationId = 1000;
    Notification mNotification;
    private long mNotificationPostTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        ButterKnife.bind(this);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        initView();
    }

    private MusicEntity curSong;

    private void initView() {
        mProgress.setIndeterminate(false);
        mProgress.setProgress(1);
        mProgress.setMax(1000);

        mProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    seek(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        adapter = new SongListAdapter(R.layout.item_song, new ArrayList<MusicEntity>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                index = position;
                playMusic(position);
            }
        });
        adapter.getData().addAll(BaseApplication.getInstance().songs);
        adapter.notifyDataSetChanged();
    }

    public int seek(int position) {
        if (mPlayer != null && mPlayer.isPlaying()) {
            int durarion = mPlayer.getDuration();
            position = position * durarion / 1000;
            if (position < 0) {
                position = 0;
            } else if (position > durarion) {
                position = durarion;
            }
            mPlayer.seekTo(position);
            return position;
        }
        return -1;
    }

    private void playMusic(int position) {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        mPlayer = new MediaPlayer();
        try {
            BaseApplication.getInstance().songItemPos = position;
            curSong = BaseApplication.getInstance().songs.get(position);
            playbarInfo.setText(curSong.getMusicTitle());
            playbarSinger.setText(curSong.getSinger());

            mPlayer.reset();
            mPlayer.setOnErrorListener(onErrorListener);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(curSong.getUrl());
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
            mProgress.postDelayed(mUpdateProgress, 200);
        }
    };

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Log.d(TAG, "MediaPlayer.OnCompletionListener ~");
//            playMusic(index < songList.length  - 1 ? songList[++index] : songList[index = 0]);
            playMusic(index < BaseApplication.getInstance().songs.size() ? ++index : (index = 0));
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
            mProgress.setSecondaryProgress(percent * 10);
        }
    };

    private void playOrPause() {
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

    private Runnable mUpdateProgress = new Runnable() {

        @Override
        public void run() {
            if (mProgress != null && mPlayer != null && mPlayer.isPlaying()) {
                long position = mPlayer.getCurrentPosition();
                long duration = mPlayer.getDuration();
                if (duration > 0 && duration < 627080716) {
                    mProgress.setProgress((int) (1000 * position / duration));
//                    mTimePlayed.setText(MusicUtils.makeTimeString( position ));
                }
                if (mPlayer.isPlaying()) {
                    mProgress.postDelayed(mUpdateProgress, 200);
                } else {
                    mProgress.removeCallbacks(mUpdateProgress);
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mProgress.removeCallbacks(mUpdateProgress);
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgress.removeCallbacks(mUpdateProgress);
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @OnClick({R2.id.play_prev, R2.id.control, R2.id.play_next})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.play_prev) {
            playPrev();

        } else if (i == R.id.control) {
            playOrPause();

        } else if (i == R.id.play_next) {
            playNext();

        }
    }

    private void playNext() {
//        playMusic(index < songList.length - 1 ? songList[++index] : songList[index = 0]);
        playMusic(index < BaseApplication.getInstance().songs.size() - 1 ? ++index : (index = 0));
    }

    private void playPrev() {
//        playMusic(index > 0 ? songList[--index] : songList[index = songList.length - 1]);
        playMusic(index > 0 ? --index : (index = BaseApplication.getInstance().songs.size() - 1));
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
        remoteViews.setTextViewText(R.id.title, BaseApplication.getInstance().songs.get(BaseApplication.getInstance().songItemPos).getMusicTitle());
        remoteViews.setTextViewText(R.id.text, BaseApplication.getInstance().songs.get(BaseApplication.getInstance().songItemPos).getSinger());

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

}
