package com.test.audio.record;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.audio.R;

import java.util.ArrayList;

public class AudioRecordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public AudioRecordAdapter() {
        super(R.layout.item_audio_record, new ArrayList<String>());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.content_tv, item);
    }
}
