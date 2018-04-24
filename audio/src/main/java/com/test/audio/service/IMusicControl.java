package com.test.audio.service;

import android.media.MediaPlayer;

/**
 * Created by 李小凡 on 2018/4/12.
 */

public interface IMusicControl {
    void play();
    void pause();
    void stop();
    void next();
    void prev();

    int seekTo(int position);

    void setOnCompletionListener(MediaPlayer.OnPreparedListener listener);
    void setOnPreparedListener(MediaPlayer.OnPreparedListener listener);
    void setOnErrorListener(MediaPlayer.OnErrorListener listener);
    void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener listener);
}
