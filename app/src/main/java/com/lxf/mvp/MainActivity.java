package com.lxf.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxf.mvp.dagger.AppModule;
import com.lxf.mvp.imageloader.ImageLoader;
import com.lxf.mvp.utils.ARouterPaths;
import com.lxf.mvp.utils.PermissionUtil;
import com.lxf.mvp.utils.ToastUtil;
import com.hannesdorfmann.mosby3.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;

public class MainActivity extends BaseActivity {

    @Inject
    ImageLoader imageLoader;

    private RxErrorHandler mErrorHandler;

    @BindView(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        reqPermission();

        initView();
    }

    private void initView() {
//        imageLoader.with(this)
//                .load("https://www.baidu.com/img/bd_logo1.png")
//                .transformation(ImageLoader.TransformationType.ROUND).into(image);
    }

    @Override
    protected void injectDependencies() {
        DaggerMainComponent.builder().appModule(new AppModule(this)).build().inject(this);
    }

    private void reqPermission() {
        mErrorHandler = RxErrorHandler
                .builder()
                .with(getApplication())
                .responseErrorListener(ResponseErrorListener.EMPTY)
                .build();

        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.callPhone(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
                ToastUtil.show(MainActivity.this, "Request permissons success");
            }

            @Override
            public void onRequestPermissionFailure() {
                ToastUtil.show(MainActivity.this, "Request permissons failure");
            }
        }, new RxPermissions(MainActivity.this), mErrorHandler);
    }

    /**
     * Android原生分享功能
     *
     * @param appName:要分享的应用程序名称
     */
    private void share(String appName) {
//        Intent share_intent = new Intent();
//        share_intent.setAction(Intent.ACTION_SEND);
//        share_intent.setType("text/plain");
//        share_intent.putExtra(Intent.EXTRA_SUBJECT, "f分享");
//        share_intent.putExtra(Intent.EXTRA_TEXT, "HI 推荐您使用一款软件:" + appName);
//        share_intent = Intent.createChooser(share_intent, "分享");
//        startActivity(share_intent);
//        String testImagePath = Environment.getExternalStorageDirectory()
//                + "/img.jpg";
//        File fileImage = new File(testImagePath);
//        ShareUtil shareUtil = new ShareUtil(this);
//        shareUtil.shareImgToWXCircle("狗狗图片", "com.tencent.mm",
//                "com.tencent.mm.ui.tools.ShareToTimeLineUI", fileImage);
    }

    @OnClick({R.id.textView, R.id.textView2, R.id.textView3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView:
                ARouter.getInstance().build(ARouterPaths.MOVIE_LIST).navigation();
                break;
            case R.id.textView2:
                ARouter.getInstance().build(ARouterPaths.TWEET_LIST).navigation();
                break;
            case R.id.textView3:
                ARouter.getInstance().build("/audio/main").navigation();
                break;
        }
    }
}
