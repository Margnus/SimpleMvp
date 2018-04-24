package com.lxf.mvp.tweet;

import android.content.Context;

import com.lxf.mvp.api.CodingApi;
import com.lxf.mvp.base.MvpLceRxPresenter;
import com.lxf.mvp.model.TweetsResult;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by lixiaofan on 2017/7/24.
 */

public class TweetsListPresenter extends MvpLceRxPresenter<TweetsListView,TweetsResult, List<TweetsResult.DataBean>> {

    CodingApi mApi;
    Context mContext;

    @Inject
    public TweetsListPresenter(CodingApi api, Context context) {
        this.mApi = api;
        this.mContext = context;
    }

    public void getTweetList(boolean pullToRefresh) {
        subscribe(mApi.getTweetList(), pullToRefresh);
    }
}
