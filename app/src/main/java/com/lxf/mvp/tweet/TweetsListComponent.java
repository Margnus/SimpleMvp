package com.lxf.mvp.tweet;

import com.lxf.mvp.dagger.AppModule;

import javax.inject.Singleton;

import dagger.Component;
/**
 * Created by lixiaofan on 2017/7/24.
 */
@Singleton
@Component(
        modules = AppModule.class)
public interface TweetsListComponent {
    void inject(TweetsListActivity activity);
    TweetsListPresenter presenter();
}