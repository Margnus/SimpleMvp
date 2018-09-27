package com.test.audio.record;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by wanbo on 2017/1/18.
 */

public class MediaUtils{
    private static final String TAG = "MediaUtils";
    private MediaRecorder mMediaRecorder;
    private File targetDir;
    private String targetName;
    private File targetFile;
    private boolean isRecording;

    public MediaUtils() {
    }


    public void setTargetDir(File file) {
        this.targetDir = file;
    }

    public void setTargetName(String name) {
        this.targetName = name;
    }

    public String getTargetFilePath() {
        return targetFile.getPath();
    }

    public boolean deleteTargetFile() {
        if (targetFile.exists()) {
            return targetFile.delete();
        } else {
            return false;
        }
    }

    public void record() {
        if (isRecording) {
            try {
                mMediaRecorder.stop();  // stop the recording
            } catch (RuntimeException e) {
                // RuntimeException is thrown when stop() is called immediately after start().
                // In this case the output file is not properly constructed ans should be deleted.
                Log.d(TAG, "RuntimeException: stop() is called immediately after start()");
                //noinspection ResultOfMethodCallIgnored
                targetFile.delete();
            }
            releaseMediaRecorder(); // release the MediaRecorder object
            isRecording = false;
        } else {
            startRecordThread();
        }
    }

    public static final int DEFAULT_SAMPLE_RATE = 16000;
    public static final int DEFAULT_BIT_RATE = 16000;

    private boolean prepareRecord() {
        try {
            mMediaRecorder = new MediaRecorder();
            //从麦克风采集
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //保存文件为MP4格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
            //所有android系统都支持的适中采样的频率
            mMediaRecorder.setAudioSamplingRate(44100);
            //通用的AAC编码格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //设置音质频率
            mMediaRecorder.setAudioEncodingBitRate(96000);
            mMediaRecorder.setAudioChannels(AudioFormat.CHANNEL_IN_MONO);

            targetFile = new File(targetDir, targetName);
            mMediaRecorder.setOutputFile(targetFile.getPath());

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("MediaRecorder", "Exception prepareRecord: ");
            releaseMediaRecorder();
            return false;
        }
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d("MediaRecorder", "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d("MediaRecorder", "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    public void stopRecordSave() {
        Log.d("Recorder", "stopRecordSave");
        if (isRecording) {
            isRecording = false;
            try {
                mMediaRecorder.stop();
                Log.d("Recorder", targetFile.getPath());
            } catch (RuntimeException r) {
                Log.d("Recorder", "RuntimeException: stop() is called immediately after start()");
            } finally {
                releaseMediaRecorder();
            }
        }
    }

    public void stopRecordUnSave() {
        Log.d("Recorder", "stopRecordUnSave");
        if (isRecording) {
            isRecording = false;
            try {
                mMediaRecorder.stop();
            } catch (RuntimeException r) {
                Log.d("Recorder", "RuntimeException: stop() is called immediately after start()");
                if (targetFile.exists()) {
                    //不保存直接删掉
                    targetFile.delete();
                }
            } finally {
                releaseMediaRecorder();
            }
            if (targetFile.exists()) {
                //不保存直接删掉
                targetFile.delete();
            }
        }
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            // clear recorder configuration
            mMediaRecorder.reset();
            // release the recorder object
            mMediaRecorder.release();
            mMediaRecorder = null;
            // Lock camera for later use i.e taking it back from MediaRecorder.
            // MediaRecorder doesn't need it anymore and we will release it if the activity pauses.
            Log.d("Recorder", "release Recorder");
        }
    }



    private void startRecordThread() {
        if (prepareRecord()) {
            try {
                mMediaRecorder.start();
                isRecording = true;
                Log.d("Recorder", "Start Record");
            } catch (RuntimeException r) {
                releaseMediaRecorder();
                Log.d("Recorder", "RuntimeException: start() is called immediately after stop()");
            }
        }
    }


}
