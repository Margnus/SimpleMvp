package com.test.audio.record;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.test.audio.R;
import com.test.audio.R2;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanbo on 2017/1/20.
 */
@Route(path = "/audio/record")
public class AudioRecorderActivity extends AppCompatActivity {

    @BindView(R2.id.tv_mic)
    TextView tvMic;
    @BindView(R2.id.bottom)
    LinearLayout bottom;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.time_display)
    Chronometer timeDisplay;
    @BindView(R2.id.mic_icon)
    ImageView micIcon;
    @BindView(R2.id.tv_info)
    TextView tvInfo;
    @BindView(R2.id.audio_layout)
    RelativeLayout audioLayout;

    private boolean isCancel;
    private String duration;

    AudioRecordManager recordManager;

    AudioRecordAdapter recordAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ButterKnife.bind(this);

        tvMic.setOnTouchListener(touchListener);
        timeDisplay.setOnChronometerTickListener(tickListener);

        initRecord();
    }

    private void initRecord() {
//        mediaUtils = new MediaUtils();
//        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
//        mediaUtils.setTargetName(UUID.randomUUID() + ".m4a");

        AudioRecordManager.init();
        recordManager = AudioRecordManager.NewInstance();
        recordManager.setOnStateListener(new AudioRecordManager.OnState() {
            @Override
            public void onStateChanged(AudioRecordManager.WindState currentState) {
                if(recordAdapter != null){
                    recordAdapter.setNewData(recordManager.getRecords());
                }
            }
        });
        recordAdapter = new AudioRecordAdapter();
        recordAdapter.setNewData(recordManager.getRecords());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recordAdapter);
        recordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(recordAdapter.getData() != null && recordAdapter.getData().size() > position){
                    recordManager.startPlayWav(new File(recordAdapter.getData().get(position)));
                }
            }
        });
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean ret = false;
            float downY = 0;
            int action = event.getAction();
            int i = v.getId();
            if (i == R.id.tv_mic) {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startAnim(true);
//                        mediaUtils.record();
                        recordManager.startRecord(true);
                        ret = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        stopAnim();
                        if (isCancel) {
                            isCancel = false;
//                            mediaUtils.stopRecordUnSave();
                            recordManager.stopRecord();
                            Toast.makeText(AudioRecorderActivity.this, "取消保存", Toast.LENGTH_SHORT).show();
                        } else {
                            int duration = getDuration(timeDisplay.getText().toString());
                            switch (duration) {
                                case -1:
                                    break;
                                case -2:
//                                    mediaUtils.stopRecordUnSave();
                                    recordManager.stopRecord();
                                    Toast.makeText(AudioRecorderActivity.this, "时间太短", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
//                                    mediaUtils.stopRecordSave();
//                                    String path = mediaUtils.getTargetFilePath();
                                    recordManager.stopRecord();
//                                    Toast.makeText(AudioRecorderActivity.this, "文件以保存至：" + path, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float currentY = event.getY();
                        if (downY - currentY > 10) {
                            moveAnim();
                            isCancel = true;
                        } else {
                            isCancel = false;
                            startAnim(false);
                        }
                        break;
                }

            }
            return ret;
        }
    };

    Chronometer.OnChronometerTickListener tickListener = new Chronometer.OnChronometerTickListener() {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            if (SystemClock.elapsedRealtime() - chronometer.getBase() > 60 * 1000) {
                stopAnim();
                recordManager.stopRecord();
                Toast.makeText(AudioRecorderActivity.this, "录音超时", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private int getDuration(String str) {
        String a = str.substring(0, 1);
        String b = str.substring(1, 2);
        String c = str.substring(3, 4);
        String d = str.substring(4);
        if (a.equals("0") && b.equals("0")) {
            if (c.equals("0") && Integer.valueOf(d) < 1) {
                return -2;
            } else if (c.equals("0") && Integer.valueOf(d) > 1) {
                duration = d;
                return Integer.valueOf(d);
            } else {
                duration = c + d;
                return Integer.valueOf(c + d);
            }
        } else {
            duration = "60";
            return -1;
        }

    }

    private void startAnim(boolean isStart) {
        audioLayout.setVisibility(View.VISIBLE);
        tvInfo.setText("上滑取消");
        tvMic.setBackgroundResource(R.drawable.mic_pressed_bg);
        micIcon.setImageResource(R.drawable.ic_mic_white_24dp);
        if (isStart) {
            timeDisplay.setBase(SystemClock.elapsedRealtime());
            timeDisplay.setFormat("%S");
            timeDisplay.start();
        }
    }

    private void stopAnim() {
        audioLayout.setVisibility(View.GONE);
        tvMic.setBackgroundResource(R.drawable.mic_bg);
        timeDisplay.stop();
    }

    private void moveAnim() {
        tvInfo.setText("松手取消");
        micIcon.setImageResource(R.drawable.ic_undo_black_24dp);
    }
}
