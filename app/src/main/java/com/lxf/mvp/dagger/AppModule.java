package com.lxf.mvp.dagger;

import android.app.Activity;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.lxf.mvp.api.CodingApi;
import com.lxf.mvp.imageloader.ImageLoader;
import com.lxf.mvp.imageloader.ImageLoaderFactory;
import com.lxf.mvp.imageloader.ImageLoaderType;
import com.lxf.mvvm.api.DoubanApi;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lixiaofan on 2017/7/24.
 */
@Module()
public class AppModule {

    private final static String API_DOUBAN = "Https://api.douban.com/";

    private Activity context;

    public AppModule(Activity context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public EventBus providesEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    RequestManager providesGlide() {
        return Glide.with(context);
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader() {
        return ImageLoaderFactory.getImageLoader(ImageLoaderType.GLIDE);
    }


    @Provides
    @Singleton
    public Retrofit providesRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://coding.net/api/")
                .client(okClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public CodingApi providesCodingApi(Retrofit retrofit) {
        return retrofit.create(CodingApi.class);
    }

    @Provides
    @Singleton
    public DoubanApi providesDoubanApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_DOUBAN)
                .client(okClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(DoubanApi.class);
    }
}
