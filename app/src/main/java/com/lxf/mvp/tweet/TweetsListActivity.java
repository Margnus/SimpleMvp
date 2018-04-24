package com.lxf.mvp.tweet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxf.mvp.Application;
import com.lxf.mvp.R;
import com.lxf.mvp.adapter.TweetAdapter;
import com.lxf.mvp.dagger.AppModule;
import com.lxf.mvp.imageloader.ImageLoader;
import com.lxf.mvp.model.TweetsResult;
import com.lxf.mvp.retrofit.ServerException;
import com.lxf.mvp.utils.ARouterPaths;
import com.lxf.mvp.utils.NetworkUtils;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceActivity;
import com.hannesdorfmann.mosby3.mvp.model.EmptyEntity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lixiaofan on 2017/7/24.
 */

@Route(path = ARouterPaths.TWEET_LIST)
public class TweetsListActivity extends MvpLceActivity<LinearLayout, List<TweetsResult.DataBean>, TweetsListView, TweetsListPresenter>
        implements TweetsListView, SwipeRefreshLayout.OnRefreshListener {
    @Inject
    TweetsListPresenter mPresenter;
    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.app_list_rv)
    RecyclerView mAppListRV;
    @BindView(R.id.pull_down_srl)
    SwipeRefreshLayout mPullDownSRL;

    private TweetAdapter tweetAdapter;

    private int mPageIndex = 0;

    @Override
    protected void injectDependencies() {
        DaggerTweetsListComponent.builder().appModule(new AppModule(this)).build().inject(this);
    }

    @NonNull
    @Override
    public TweetsListPresenter createPresenter() {
        return mPresenter;
    }


    @Override
    public void setData(List<TweetsResult.DataBean> tweetsResult) {
        mPullDownSRL.setRefreshing(false);
        tweetAdapter.loadMoreComplete();
        if (mPageIndex == 0) {
            tweetAdapter.getData().clear();
        }
        tweetAdapter.getData().addAll(tweetsResult);
        tweetAdapter.notifyDataSetChanged();
        Log.d("TweetsListActivity === ", "" + tweetsResult.size());
    }

    @Override
    public void loadData(boolean b) {
        mPresenter.getTweetList(b);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAppListRV.setLayoutManager(linearLayoutManager);
        tweetAdapter = new TweetAdapter(imageLoader);
        tweetAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);

//        tweetAdapter.openLoadAnimation(new BaseAnimation() {
//            @Override
//            public Animator[] getAnimators(View view) {
//                return new Animator[]{
//                        ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
//                        ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
//                };
//            }
//        });

        mAppListRV.setAdapter(tweetAdapter);

        mPullDownSRL.setOnRefreshListener(this);

        tweetAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPageIndex += 1;
                loadData(true);
            }
        }, mAppListRV);


        loadData(false);
    }

    @Override
    public void onRefresh() {
        mPageIndex = 0;
        loadData(true);
    }

    @Override
    public EmptyEntity emptyData(List<TweetsResult.DataBean> data) {
        mPullDownSRL.setRefreshing(false);
        tweetAdapter.loadMoreEnd();
        if (data == null || data.size() == 0) {
            if(mPageIndex == 0){
                return new EmptyEntity("啥都没有返回~", R.drawable.ic_empty);
            } else {
                return new EmptyEntity("没有更多数据", -1, true);
            }
        }
        return null;
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        mPullDownSRL.setRefreshing(false);
        if (mPageIndex > 1) {
            mPageIndex -= 1;
            tweetAdapter.loadMoreFail();
        }
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        if (!NetworkUtils.isNetworkAvailable(Application.getInstance())) {
            return "连不上网络，点击重新加载";
        } else if (e instanceof ServerException) {
            return ((ServerException) e).msg;
        } else {
            return"服务器炸了，请稍后再试";
        }
    }
}
