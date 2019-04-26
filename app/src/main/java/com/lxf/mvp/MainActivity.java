package com.lxf.mvp;

import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby3.base.BaseActivity;
import com.lxf.mvp.dagger.AppModule;
import com.lxf.mvp.imageloader.ImageLoader;
import com.lxf.mvp.utils.ARouterPaths;
import com.lxf.mvp.utils.PermissionUtil;
import com.lxf.mvp.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Inject
    ImageLoader imageLoader;
    @BindView(R.id.textView6)
    TextView textView6;

    private RxErrorHandler mErrorHandler;

    @BindView(R.id.image)
    ImageView image;

    private int[] arrs = {10, 9, 34, 123, 5, 76, 199, 62, 4, 88};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        reqPermission();

        initView();
        getExif();
//        quickSort(arrs, 0, arrs.length - 1);
        System.out.print(arrs.toString());
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
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
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
        PermissionUtil.location(new PermissionUtil.RequestPermission() {
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

    private void getExif(){
        ExifInterface exifInterface = null;
        String file = Environment.getExternalStorageDirectory().getPath() +  File.separator + "DCIM/Camera/IMG_20181217_180441.jpg";
//        String file = Environment.getExternalStorageDirectory().getPath() +  File.separator + "DCIM/GalleryFinal/IMG_20181219_153135.jpg";
        Glide.with(this).load(new File(file)).into(image);
        try {
            exifInterface = new ExifInterface(
                    file);
            if(exifInterface != null){
                String FFNumber = exifInterface
                        .getAttribute(ExifInterface.TAG_APERTURE);
                String FDateTime = exifInterface
                        .getAttribute(ExifInterface.TAG_DATETIME);
                String FExposureTime = exifInterface
                        .getAttribute(ExifInterface.TAG_EXPOSURE_TIME);
                String FFlash = exifInterface
                        .getAttribute(ExifInterface.TAG_FLASH);
                String FFocalLength = exifInterface
                        .getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
                String FImageLength = exifInterface
                        .getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
                String FImageWidth = exifInterface
                        .getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
                String FISOSpeedRatings = exifInterface
                        .getAttribute(ExifInterface.TAG_ISO);
                String FMake = exifInterface
                        .getAttribute(ExifInterface.TAG_MAKE);
                String FModel = exifInterface
                        .getAttribute(ExifInterface.TAG_MODEL);
                String FOrientation = exifInterface
                        .getAttribute(ExifInterface.TAG_ORIENTATION);
                String FWhiteBalance = exifInterface
                        .getAttribute(ExifInterface.TAG_WHITE_BALANCE);

                Log.i(TAG, "FFNumber:" + FFNumber);
                Log.i(TAG, "FDateTime:" + FDateTime);
                Log.i(TAG, "FExposureTime:" + FExposureTime);
                Log.i(TAG, "FFlash:" + FFlash);
                Log.i(TAG, "FFocalLength:" + FFocalLength);
                Log.i(TAG, "FImageLength:" + FImageLength);
                Log.i(TAG, "FImageWidth:" + FImageWidth);
                Log.i(TAG, "FISOSpeedRatings:" + FISOSpeedRatings);
                Log.i(TAG, "FMake:" + FMake);
                Log.i(TAG, "FModel:" + FModel);
                Log.i(TAG, "FOrientation:" + FOrientation);
                Log.i(TAG, "FWhiteBalance:" + FWhiteBalance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    @OnClick({R.id.textView, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6,
            R.id.textView7, R.id.textView8, R.id.textView9, R.id.textView10, R.id.textView11})
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
            case R.id.textView4:
                ARouter.getInstance().build("/rxjava/demo").navigation();
                break;
            case R.id.textView5:
                ARouter.getInstance().build("/scroll/test").navigation();
                break;
            case R.id.textView6:
                ARouter.getInstance().build("/scroll/orientation").navigation();
                break;
            case R.id.textView7:
                ARouter.getInstance().build("/audio/record").navigation();
                break;
            case R.id.textView8:
                ARouter.getInstance().build("/scroll/location").navigation();
                break;
            case R.id.textView9:
                ARouter.getInstance().build("/canvas/test").navigation();
                break;
            case R.id.textView10:
                ToastUtil.show(getBaseContext(), "Tinker!!!");
                break;
            case R.id.textView11:
                ARouter.getInstance().build("/socket/test").navigation();
                break;
        }
    }

    private int portion(int[] ars, int left, int right) {
        int temp = ars[left];
        while (right > left) {
            while (temp <= ars[right] && left < right) {
                right--;
            }
            ars[left] = ars[right];
            while (temp >= ars[left] && left < right) {
                left++;
            }
            ars[right] = ars[left];
        }
        temp = ars[left];
        return left;
    }

    private void quickSort(int[] ars, int left, int right) {
        if (ars == null && left >= right && right <= 1) {
            return;
        }

        int mid = portion(ars, 0, right);
        quickSort(ars, 0, mid - 1);
        quickSort(ars, mid + 1, right);
    }


    private void printArray() {

    }

}
