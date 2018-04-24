package com.lxf.mvp.base;

import com.lxf.mvp.model.BaseResult;
import com.lxf.mvp.retrofit.RxRetrofitProtoHelper;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.hannesdorfmann.mosby3.mvp.model.EmptyEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author lixiaofan
 * @date 2017/7/24
 */
public abstract class MvpLceRxPresenter<V extends MvpLceView<S>, M extends BaseResult, S>
    extends MvpBasePresenter<V>
    implements MvpPresenter<V> {

  protected DisposableObserver<S> subscriber;

  protected void unSubscribe() {
    if (subscriber != null && !subscriber.isDisposed()) {
      subscriber.dispose();
    }
    subscriber = null;
  }

  public void subscribe(Observable<M> observable, final boolean pullToRefresh) {

    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }

    unSubscribe();

    subscriber = new DisposableObserver<S>() {
      @Override
      public void onNext(@NonNull S s) {
        if (isViewAttached()) {
          EmptyEntity msg = getView().emptyData(s);
          if(msg != null){
            getView().showEmpty(msg);
          } else {
            getView().setData(s);
            if(!pullToRefresh){
              getView().showContent();
            }
          }
        }
        unSubscribe();
      }

      @Override
      public void onError(@NonNull Throwable e) {
        if (isViewAttached()) {
          getView().showError(e, pullToRefresh);
        }
        unSubscribe();
      }

      @Override
      public void onComplete() {
      }
    };
    observable.compose(RxRetrofitProtoHelper.<S>handleResult())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber);
  }


  @Override
  public void detachView(boolean retainInstance) {
    super.detachView(retainInstance);
    if (!retainInstance) {
      unSubscribe();
    }
  }
}
