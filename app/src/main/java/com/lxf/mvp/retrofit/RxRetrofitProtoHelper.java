package com.lxf.mvp.retrofit;


import com.lxf.mvp.model.BaseResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author lixiaofan
 * @date 2017/7/19
 */

public class RxRetrofitProtoHelper {
    public static <T> ObservableTransformer<BaseResult, T> handleResult() {

        return new ObservableTransformer<BaseResult, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseResult> upstream) {
                return upstream.flatMap(new Function<BaseResult, Observable<T>>() {
                    @Override
                    public Observable<T> apply(@NonNull BaseResult baseResult) throws Exception {
                        if (baseResult.code == 0) {
                            return (Observable<T>) Observable.just(baseResult.data);
                        } else {
                            return Observable.error(new ServerException(baseResult.code, baseResult.msg));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
