package com.lxf.rxjava;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lxf.mvp.R;
import com.lxf.rxjava.adapter.AppListAdapter;
import com.lxf.rxjava.model.AppInfo;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@Route(path = "/rxjava/demo")
public class RxJavaDemoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.pull_down_srl)
    SwipeRefreshLayout mPullDownSRL;
    @BindView(R.id.app_list_rv)
    RecyclerView mAppListRV;

    private List<AppInfo> mAppInfoList;
    private AppListAdapter mAppListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAppListRV.setLayoutManager(linearLayoutManager);
        mAppInfoList = new ArrayList<>();
        mAppListAdapter = new AppListAdapter(mAppInfoList);
        mAppListRV.setAdapter(mAppListAdapter);

        mPullDownSRL.setOnRefreshListener(this);

        mPullDownSRL.post(new Runnable() {
            @Override
            public void run() {
                mPullDownSRL.setRefreshing(true);
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        if (mAppInfoList != null) {
            mAppInfoList.clear();
            mAppListAdapter.notifyDataSetChanged();
        }
        loadApp();
    }

    private void loadApp() {
        final PackageManager pm = RxJavaDemoActivity.this.getPackageManager();
        Observable.create(new ObservableOnSubscribe<List<ApplicationInfo>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ApplicationInfo>> e) throws Exception {
                List<ApplicationInfo> infoList = getApplicationInfoList(pm);
                e.onNext(infoList);
                e.onComplete();
            }

        }).flatMap(new Function<List<ApplicationInfo>, Observable<ApplicationInfo>>() {
            @Override
            public Observable<ApplicationInfo> apply(@NonNull List<ApplicationInfo> applicationInfos) throws Exception {
                return Observable.fromIterable(applicationInfos);
            }
        }).filter(new Predicate<ApplicationInfo>() {
            @Override
            public boolean test(@NonNull ApplicationInfo applicationInfo) throws Exception {
                return (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0;
            }
        }).map(new Function<ApplicationInfo, AppInfo>() {
            @Override
            public AppInfo apply(@NonNull ApplicationInfo applicationInfo) throws Exception {
                AppInfo info = new AppInfo();
                info.setAppIcon(applicationInfo.loadIcon(pm));
                info.setAppName(applicationInfo.loadLabel(pm).toString());
                return info;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AppInfo>() {
                    @Override
                    public void onError(Throwable e) {
                        mPullDownSRL.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        mAppListAdapter.notifyDataSetChanged();
                        mPullDownSRL.setRefreshing(false);
                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        mAppInfoList.add(appInfo);
                    }
                });

    }

    private List<ApplicationInfo> getApplicationInfoList(final PackageManager pm) {
        return pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}