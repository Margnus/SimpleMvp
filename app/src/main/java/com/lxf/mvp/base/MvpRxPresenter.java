package com.lxf.mvp.base;

import com.lxf.mvp.model.BaseResult;
import com.lxf.mvp.retrofit.RxRetrofitProtoHelper;
import com.lxf.mvp.retrofit.RxRetrofitSubscribe;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpRxView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lixiaofan on 2017/7/24.
 */
public abstract class MvpRxPresenter<V extends MvpRxView, M extends BaseResult, S> extends MvpBasePresenter<V> {

  protected DisposableObserver<S> subscriber;

  protected void unsubscribe() {
    if (subscriber != null && !subscriber.isDisposed()) {
      subscriber.dispose();
    }

    subscriber = null;
  }


  public void subscribe(Observable<M> observable) {

    unsubscribe();

    subscriber = new RxRetrofitSubscribe<S>() {
      @Override
      protected void _onNext(S s) {
        MvpRxPresenter.this.onNext(s);
      }

      @Override
      protected void _onError(String message, int type) {
        getView().showError(message, type);
      }
    };

    observable.compose(RxRetrofitProtoHelper.<S>handleResult()).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
  }

  protected abstract void onNext(S data);


  @Override
  public void detachView(boolean retainInstance) {
    super.detachView(retainInstance);
    if (!retainInstance) {
      unsubscribe();
    }
  }
}