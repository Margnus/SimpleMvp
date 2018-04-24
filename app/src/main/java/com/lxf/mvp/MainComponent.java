package com.lxf.mvp;

import com.lxf.mvp.dagger.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lixiaofan on 2017/10/25.
 */
@Singleton
@Component(
        modules = AppModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
