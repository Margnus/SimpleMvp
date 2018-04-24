package com.lxf.mvp;

import com.squareup.leakcanary.LeakCanary;
import com.test.audio.BaseApplication;

/**
 * Created by lixiaofan on 2017/6/27.
 */

public class Application extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
