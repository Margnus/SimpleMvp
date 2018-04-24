package com.test.audio;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.audio.music.MusicEntity;

import java.util.List;

/**
 * Created by 李小凡 on 2018/4/11.
 */

public class SongListAdapter extends BaseQuickAdapter<MusicEntity, BaseViewHolder>{

    public SongListAdapter(int layoutResId, @Nullable List<MusicEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicEntity item) {
        helper.setText(R.id.name, item.getMusicTitle());
        helper.setText(R.id.singer, item.getSinger());
        helper.setText(R.id.content, item.getUrl());
    }
}
