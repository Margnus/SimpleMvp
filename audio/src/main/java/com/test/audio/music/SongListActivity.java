package com.test.audio.music;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.edmodo.rangebar.RangeBar;
import com.test.audio.BaseApplication;
import com.test.audio.R;
import com.test.audio.R2;
import com.test.audio.SongListAdapter;
import com.test.audio.widget.PlayerSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 李小凡 on 2018/4/10.
 */

@Route(path = "/audio/song")
public class SongListActivity extends AppCompatActivity implements MediaPlayerHelper.MediaPlayerUpdateCallBack {


    private static final String TAG = "LXF";
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.time_play)
    TextView timePlay;
    @BindView(R2.id.song_progress_normal)
    PlayerSeekBar mProgress;
    @BindView(R2.id.time_total)
    TextView timeTotal;
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

    private SongListAdapter adapter;

    private MediaControllerCompat mMediaController;
    private MusicService musicService;
    private MediaPlayerHelper mMediaPlayerHelper;

    RangeBar rangeBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        ButterKnife.bind(this);
        initView();
        initService();
    }

    private MediaControllerCompat.Callback mMediaControllerCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            switch (state.getState()) {
                case PlaybackStateCompat.STATE_NONE://无任何状态
                    control.setImageResource(R.drawable.playbar_btn_pause);
                    break;
                case PlaybackStateCompat.STATE_PLAYING:
                    control.setImageResource(R.drawable.playbar_btn_pause);
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    control.setImageResource(R.drawable.playbar_btn_play);
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_NEXT://下一首
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS://上一首
                    break;
                case PlaybackStateCompat.STATE_FAST_FORWARDING://快进
                    break;
                case PlaybackStateCompat.STATE_REWINDING://快退
                    break;
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
            playbarInfo.setText(metadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
        }
    };

    private void initService() {
        Intent intent = new Intent(this, MusicService.class);
        getApplicationContext().bindService(intent, conn, 0);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof MusicService.ServiceBinder) {
                try {
                    //获取服务
                    musicService = ((MusicService.ServiceBinder) service).getService();
                    //获取帮助类
                    mMediaPlayerHelper = musicService.getMediaPlayerHelper();
                    //设置媒体播放回键听
                    mMediaPlayerHelper.setMediaPlayerUpdateListener(SongListActivity.this);
                    //设置数据源
                    mMediaPlayerHelper.setPlayeData(BaseApplication.getInstance().songs);
                    //设置更新的seekBaar
                    mMediaPlayerHelper.setSeekBar(mProgress);
                    //设置媒体控制器
                    mMediaController = new MediaControllerCompat(SongListActivity.this,
                            musicService.getMediaSessionToken());
                    //注册回调
                    mMediaController.registerCallback(mMediaControllerCallback);
                } catch (Exception e) {
                    Log.e(getClass().getName(), "serviceConnectedException==" + e.getMessage());
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getClass().getName(), "ComponentName==" + name);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
    }

    private void initView() {

        rangeBar = (RangeBar) findViewById(R.id.rangebar);

        rangeBar.setTickCount(4);//有多少分割线
        rangeBar.setTickHeight(25);//分割线的高度

        rangeBar.setBarWeight(6);//分割线和滑动杆的粗细
        rangeBar.setBarColor(0xff000000);//分割线和滑动杆的颜色，

        rangeBar.setConnectingLineWeight(5);//设置被选中的区域的宽度
        rangeBar.setConnectingLineColor(Color.parseColor("#ff0000"));//选中区域的颜色

        rangeBar.setThumbImageNormal(R.drawable.seek_thumb_normal);//滑块普通状态时显示的图片
        rangeBar.setThumbImagePressed(R.drawable.seek_thumb_pressed);//滑块按下时显示的图片

        /**
         * 如果setThumbRadius()，setThumbColorNormal()，setThumbColorPressed()其中的一个或多个
         * 进行了设置那么rangbar会自动忽略已经设置好的滑块图片，这是需要注意的!!!
         */

        //rangeBar.setThumbRadius(20);//滑块的半径(>0)，也就是设置滑块的大小。可以用setThumbRadius(-1)让其回到默认状态

        //设置滑块普通状态的颜色，这里会覆盖setThumbImageNormal的设置，如果想要恢复最原始的样子可以setThumbColorNormal(-1)
        //rangeBar.setThumbColorNormal(Color.parseColor("#0000ff"));
        //设置滑块普通状态的颜色，这里会覆盖setThumbImagePressed的设置，如果想要恢复最原始的样子可以setThumbColorPressed(-1)
        //rangeBar.setThumbColorPressed(Color.parseColor("#00ff00"));

        rangeBar.setThumbIndices(1, 2);//设置滑块距离左端的位置。这里设置为左滑块距离左边一个格子，右滑块距离左边5个格子


        //设置监听器
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int i, int i1) {
                Log.d("s", i + "  " + i1);
            }
        });

        mProgress.setIndeterminate(false);
        mProgress.setProgress(1);
        mProgress.setMax(1000);

        mProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timePlay.setText(MediaPlayerHelper.turnTime(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //更新拖动进度
                mMediaPlayerHelper.getMediaPlayer().seekTo(
                        seekBar.getProgress() * mMediaPlayerHelper.getMediaPlayer().getDuration()
                                / seekBar.getMax());
            }
        });

        adapter = new SongListAdapter(R.layout.item_song, BaseApplication.getInstance().songs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(MediaPlayerHelper.KEY_INDEX, position);
                mMediaController.getTransportControls().playFromSearch("", bundle);
            }
        });
        adapter.getData().addAll(BaseApplication.getInstance().songs);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R2.id.play_prev, R2.id.control, R2.id.play_next, R2.id.tv1, R2.id.tv2, R2.id.tv3, R2.id.tv4})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.play_prev) {
            mMediaController.getTransportControls().skipToPrevious();
        } else if (i == R.id.control) {
            if (mMediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING) {
                mMediaController.getTransportControls().pause();
            } else if (mMediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PAUSED) {
                mMediaController.getTransportControls().play();
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt(MediaPlayerHelper.KEY_INDEX, 0);
                mMediaController.getTransportControls().playFromSearch("", bundle);
            }
        } else if (i == R.id.play_next) {
            mMediaController.getTransportControls().skipToNext();
        }else if (i == R.id.tv1) {
            mMediaPlayerHelper.setSpeed(0.5f);
        }else if (i == R.id.tv2) {
            mMediaPlayerHelper.setSpeed(1f);
        }else if (i == R.id.tv3) {
            mMediaPlayerHelper.setSpeed(2f);
        }else if (i == R.id.tv4) {
            mMediaPlayerHelper.setSpeed(3f);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //自动播放下一首
        mMediaController.getTransportControls().skipToNext();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        mProgress.setSecondaryProgress(percent);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        int musicTime = mediaPlayer.getDuration() / 1000;
        int minute = musicTime / 60;
        int second = musicTime % 60;
        timeTotal.setText(minute + ":" + (second > 9 ? second : "0" + second));
    }
}
