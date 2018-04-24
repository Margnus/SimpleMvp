package com.lxf.mvp.api;

import com.lxf.mvp.model.TweetsResult;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by lixiaofan on 2017/7/24.
 */

public interface CodingApi {
    @GET("tweet/public_tweets")
    Observable<TweetsResult> getTweetList();
}
