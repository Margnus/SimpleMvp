package com.test.audio.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.test.audio.BaseApplication;

/**
 * Created by 李小凡 on 2018/4/13.
 */

public class AudioReceiver extends BroadcastReceiver {
    private BaseApplication application;
    private IMusicControl music;

    @Override
    public void onReceive(Context context, Intent intent) {

        application = (BaseApplication) context.getApplicationContext();
        String ctrl_code = intent.getAction();//获取action标记，用户区分点击事件

        music = application.musicControl;//获取全局播放控制对象，该对象已在Activity中初始化
        if (music != null) {
            if ("play".equals(ctrl_code)) {
                music.play();
                System.out.println("play");
            } else if ("pause".equals(ctrl_code)) {
                music.pause();
                System.out.println("pause");
            } else if ("next".equals(ctrl_code)) {
                music.next();
                System.out.println("next");
            } else if ("last".equals(ctrl_code)) {
                music.prev();
                System.out.println("last");
            }
        }
    }

}