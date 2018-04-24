package com.lxf.mvvm.activity;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lxf.mvp.R;
import com.lxf.mvp.dagger.AppModule;
import com.lxf.mvp.databinding.ActivityDoubanTopBinding;
import com.lxf.mvp.utils.ARouterPaths;
import com.lxf.mvvm.adapter.DouBanTopAdapter;
import com.lxf.mvvm.api.DoubanApi;
import com.lxf.mvvm.base.BaseMvvmActivity;
import com.lxf.mvvm.bean.DbMovieBean;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jingbin on 2016/12/10.
 */
@Route(path = ARouterPaths.MOVIE_LIST)
public class DbActivity extends BaseMvvmActivity<ActivityDoubanTopBinding> {

    private DouBanTopAdapter mDouBanTopAdapter;
    private int mStart = 0;
    private int mCount = 21;

    @Inject
    DoubanApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_top);
        DaggerDbActivityComponent.builder().appModule(new AppModule(this)).build().inject(this);
        setTitle("豆瓣电影Top250");
        mDouBanTopAdapter = new DouBanTopAdapter(DbActivity.this);
        loadDouBanTop250();
    }

    private void loadDouBanTop250() {
        DisposableObserver observer = new DisposableObserver<DbMovieBean>() {

            @Override
            public void onError(Throwable e) {
                if (mDouBanTopAdapter.getItemCount() == 0) {
                    showError();
                }
            }

            @Override
            public void onComplete() {
                showContentView();
            }

            @Override
            public void onNext(DbMovieBean hotMovieBean) {
                if (mStart == 0) {
                    if (hotMovieBean != null && hotMovieBean.getSubjects() != null && hotMovieBean.getSubjects().size() > 0) {

                        mDouBanTopAdapter.clear();
                        mDouBanTopAdapter.addAll(hotMovieBean.getSubjects());
                        //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向,瀑布流
                        bindingView.xrvTop.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                        bindingView.xrvTop.setAdapter(mDouBanTopAdapter);
                        mDouBanTopAdapter.notifyDataSetChanged();
                        return;
                    }
                }
                showError();
            }
        };
         mApi.getMovieTop250(mStart, mCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        addSubscription(observer);
    }

    @Override
    protected void onRefresh() {
        loadDouBanTop250();
    }
}
