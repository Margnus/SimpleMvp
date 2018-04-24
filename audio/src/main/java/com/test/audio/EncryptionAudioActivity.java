package com.test.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

@Route(path = "/audio/main")
public class EncryptionAudioActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "VoiceEncryptionActivity";
    //种子,就是密匙  ，种子不同就无法解密，可以深入了解下加密原理
    private static final String seed = "VoiceEncryptionActivity";
    private MediaPlayer mPlayer;
    private Button mPlayButton;
    private Button mStopButton;
    private Button mEncryptionButton;
    private Button mDecryptionButton;
    private Button mJLayerButton;
    private Button mPlayListButton;
    private Button mPlayByServiceButton;
    private FileInputStream fis = null;
    private FileOutputStream fos = null;
    private String rootPath = Environment.getExternalStorageDirectory().getPath();
    private String playerPath = rootPath + File.separator + "54wall";
    private File oldFile = new File(playerPath, "q.mp3");
    File oldFile_each;
    File oldFile_each_after;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_encryption);
        requestPermissions();
        mPlayButton = (Button) findViewById(R.id.playButton_encryp);
        mPlayButton.setOnClickListener(this);
        mStopButton = (Button) findViewById(R.id.stopButton);
        mStopButton.setOnClickListener(this);
        mEncryptionButton = (Button) findViewById(R.id.encryptionButton);
        mEncryptionButton.setOnClickListener(this);
        mDecryptionButton = (Button) findViewById(R.id.decryptionButton);
        mDecryptionButton.setOnClickListener(this);
        //开始忘记注册toJLayerButton了，结果出现了Timeline: Activity_launch_request time:163908273
        mJLayerButton = (Button) findViewById(R.id.toJLayerButton);
        mJLayerButton.setOnClickListener(this);
        mPlayListButton = (Button) findViewById(R.id.toPlayList);
        mPlayListButton.setOnClickListener(this);
        mPlayByServiceButton = (Button) findViewById(R.id.PlayByServiceList);
        mPlayByServiceButton.setOnClickListener(this);
    }

    private void requestPermissions() {

        Observable<Boolean> observable = new RxPermissions(EncryptionAudioActivity.this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
        observable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Log.d("jinlong", "observable time :");
                } else {
                    Toast.makeText(EncryptionAudioActivity.this, "无法操作", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RxPermissions rxPermission = new RxPermissions(EncryptionAudioActivity.this);
        rxPermission
                .requestEach(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                        }
                    }
                });


    }

    private long time = 0;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.playButton_encryp) {
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }
            // mPlayer = MediaPlayer.create(this, R.raw.recording_old);
            boolean isSuccess = true;

            try {
                fis = new FileInputStream(oldFile);
                mPlayer = new MediaPlayer();
                mPlayer.setDataSource(fis.getFD());
                mPlayer.prepare();
                mPlayer.start();
            } catch (FileNotFoundException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (IOException e) {
                isSuccess = false;
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!isSuccess)
                Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.stopButton) {
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }

        } else if (i == R.id.encryptionButton) {
            boolean isSuccess;// 加密保存
            isSuccess = true;
            time = System.currentTimeMillis();
            try {
                fis = new FileInputStream(oldFile);
                byte[] oldByte = new byte[(int) oldFile.length()];
                fis.read(oldByte); // 读取
                byte[] newByte = AESUtils.encryptVoice(seed, oldByte);
                // 加密
                fos = new FileOutputStream(oldFile);
                fos.write(newByte);

            } catch (FileNotFoundException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (IOException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (Exception e) {
                isSuccess = false;
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            time = System.currentTimeMillis() - time;
            Log.e("voice", "encryptionButton time ==>" + time);
            if (isSuccess)
                Toast.makeText(this, "加密成功", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "加密失败", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "保存成功");

        } else if (i == R.id.decryptionButton) {
            boolean isSuccess;// 解密保存
            isSuccess = true;
            time = System.currentTimeMillis();
            byte[] oldByte = new byte[(int) oldFile.length()];
            try {
                fis = new FileInputStream(oldFile);
                fis.read(oldByte);
                byte[] newByte = AESUtils.decryptVoice(seed, oldByte);
                // 解密
                fos = new FileOutputStream(oldFile);
                fos.write(newByte);

            } catch (FileNotFoundException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (IOException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (Exception e) {
                isSuccess = false;
                e.printStackTrace();
            }
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            time = System.currentTimeMillis() - time;
            Log.e("voice", "decryptionButton time ==>" + time);
            if (isSuccess)
                Toast.makeText(this, "解密成功", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "解密失败", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.toJLayerButton) {
            Intent it = new Intent(EncryptionAudioActivity.this,
                    JLayerActivity.class);
            EncryptionAudioActivity.this.startActivity(it);

        } else if (i == R.id.toPlayList) {
            ARouter.getInstance().build("/audio/list").navigation();
        }else if (i == R.id.PlayByServiceList) {
            ARouter.getInstance().build("/audio/song").navigation();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
