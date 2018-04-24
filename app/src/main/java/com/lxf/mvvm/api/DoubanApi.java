package com.lxf.mvvm.api;

import com.lxf.mvvm.bean.DbMovieBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lixiaofan on 2017/7/24.
 */

public interface DoubanApi {
    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("v2/movie/top250")
    Observable<DbMovieBean> getMovieTop250(@Query("start") int start, @Query("count") int count);
}
