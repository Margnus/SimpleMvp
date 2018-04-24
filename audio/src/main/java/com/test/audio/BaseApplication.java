package com.test.audio;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.test.audio.music.MusicEntity;
import com.test.audio.service.IMusicControl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaofan on 2017/6/27.
 */

public class BaseApplication extends MultiDexApplication {

    public static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        if (isDebug()) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
        initSongList();
    }

    private void initSongList() {
        if(songs.isEmpty()){
            songs.add(new MusicEntity(songList[0], "红玫瑰","陈奕迅" ));
            songs.add(new MusicEntity(songList[0], "白玫瑰","陈奕迅" ));
            songs.add(new MusicEntity(songList[0], "黑玫瑰","陈奕迅" ));
            songs.add(new MusicEntity(songList[0], "绿玫瑰","陈奕迅" ));
            songs.add(new MusicEntity(songList[0], "紫玫瑰","陈奕迅" ));
            songs.add(new MusicEntity(songList[0], "黄玫瑰","陈奕迅" ));
        }
    }

    //https 音频播放不了
    private String[] songList = {
            "http://audio2.xmcdn.com/group42/M0A/2A/B8/wKgJ81rLK7GgZNc4AREI9xnB8u0532.m4a",
            "http://audio2.xmcdn.com/group42/M0A/2A/B8/wKgJ81rLK7GgZNc4AREI9xnB8u0532.m4a",
            "http://audio2.xmcdn.com/group42/M0A/2A/B8/wKgJ81rLK7GgZNc4AREI9xnB8u0532.m4a",
    };

    public List<MusicEntity> songs = new ArrayList<>();

    public int songItemPos = 0;

    public IMusicControl musicControl;

    public boolean isDebug() {
        return true;
    }

    public static BaseApplication getInstance() {
        return application;
    }
}
