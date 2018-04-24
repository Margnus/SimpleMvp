package com.lxf.mvvm.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.lxf.mvp.R;
import com.lxf.mvp.databinding.ActivityBaseBinding;
import com.lxf.mvvm.base.utils.statusbar.StatusBarUtil;
import com.hannesdorfmann.mosby3.mvp.lce.LceAnimator;
import com.jakewharton.rxbinding2.view.RxView;
import java.util.concurrent.TimeUnit;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;


/**
 * 描述：
 *
 * @author lixiaofan
 * @date 2017/11/3 10:21
 */

public class BaseMvvmActivity<SV extends ViewDataBinding> extends AppCompatActivity {

    // 布局view
    protected SV bindingView;

    private ActivityBaseBinding mBaseBinding;
    private AnimationDrawable mAnimationDrawable;
    private CompositeDisposable mCompositeSubscription;

    protected View loadingView;
    protected View errorView;

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);

        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(bindingView.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());

        // 设置透明状态栏
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary),0);

        loadingView = getView(R.id.loadingView);
        errorView = getView(R.id.errorView);

        // 加载动画
        ImageView imageView = (ImageView) loadingView.findViewById(R.id.iv_loading);
        mAnimationDrawable = (AnimationDrawable) imageView.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

//        setToolBar();
        // 点击加载失败布局

        RxView.clicks(errorView.findViewById(R.id.btn_retry))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        showLoading();
                        onRefresh();
                    }
                });

        bindingView.getRoot().setVisibility(View.GONE);
    }

    protected void showLoading() {
        LceAnimator.showLoading(loadingView, bindingView.getRoot(), errorView);
    }

    protected void showContentView() {
        LceAnimator.showContent(loadingView, bindingView.getRoot(), errorView);
    }

    protected void showError() {
        LceAnimator.showErrorView(loadingView, bindingView.getRoot(), errorView);
    }

    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }

    public void addSubscription(DisposableObserver s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeDisposable();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null && !mCompositeSubscription.isDisposed()) {
            this.mCompositeSubscription.isDisposed();
        }
    }

    public void removeSubscription() {
        if (this.mCompositeSubscription != null && !mCompositeSubscription.isDisposed()) {
            this.mCompositeSubscription.isDisposed();
        }
    }
}
