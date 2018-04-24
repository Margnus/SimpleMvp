package com.lxf.mvvm.activity;

import com.lxf.mvp.dagger.AppModule;
import javax.inject.Singleton;
import dagger.Component;

/**
 * 描述：
 *
 * @author lixiaofan
 * @date 2017/11/6 15:15
 */
@Singleton
@Component(
        modules = AppModule.class)
public interface DbActivityComponent {
    void inject(DbActivity activity);
}
