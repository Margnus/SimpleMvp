package com.lxf.mvp.test;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hannesdorfmann.mosby3.base.BaseActivity;
import com.lxf.mvp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/scroll/orientation")
public class TestOrientationActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orienta);
        ButterKnife.bind(this);
        initOrientation();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "PORTRAIT", Toast.LENGTH_SHORT).show();
        }else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, "LANDSCAPE", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 手机重力监听
     */
    private OrientationEventListener orientationEventListener;

    private void initOrientationEventListener() {
        orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                textView.setText(orientation + "");
            }
        };
    }


    /**
     * 设置方向感应监听
     */
    private void initOrientation() {
        if (orientationEventListener == null) {
            initOrientationEventListener();
        }
        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable();
        } else {
            orientationEventListener.disable();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orientationEventListener != null) {
            orientationEventListener.disable();
            orientationEventListener = null;
        }
    }
}
