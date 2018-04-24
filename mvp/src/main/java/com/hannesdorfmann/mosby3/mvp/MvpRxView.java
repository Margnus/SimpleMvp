package com.hannesdorfmann.mosby3.mvp;

/**
 * Created by lixiaofan on 2017/7/10.
 */
public interface MvpRxView extends MvpView {
    public void showError(String errorMsg, int type);
    public void showError(String errorMsg);
}
