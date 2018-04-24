package com.lxf.mvp.retrofit;

import com.lxf.mvp.Application;
import com.lxf.mvp.utils.NetworkUtils;
import com.hannesdorfmann.mosby3.mvp.Constants;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by lixiaofan on 2017/7/19.
 */
public abstract class RxRetrofitSubscribe<T> extends DisposableObserver<T> {
  @Override
  public void onNext(T t) {
    _onNext(t);
  }

  @Override
  public void onError(Throwable e) {
    e.printStackTrace();
    if (!NetworkUtils.isNetworkAvailable(Application.getInstance())) {
      _onError("连不上网络，点击重新加载", Constants.RESPONSE_NETWORK_ERROR);
    } else if (e instanceof ServerException) {
      _onError(e.getMessage(), Constants.RESPONSE_SERVER_ERROR);
    } else {
      _onError("服务器炸了，请稍后再试", Constants.RESPONSE_UNKNOWN_ERROR);
    }
  }

  @Override
  public void onComplete() {

  }

  protected abstract void _onNext(T t);

  protected abstract void _onError(String message, int type);
}