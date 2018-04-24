package com.hannesdorfmann.mosby3.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.R;

import org.greenrobot.eventbus.EventBus;


public class BaseActivity extends AppCompatActivity implements MvpView {
  public MaterialDialog mDialog;
  protected View mBackBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.injectDependencies();
    mDialog = new MaterialDialog.Builder(this)
        .content("加载数据中...")
        .canceledOnTouchOutside(false)
        .widgetColor(Color.parseColor("#00aaee"))
        .progress(true, 0).build();
    if(useEventBus()){
      EventBus.getDefault().register(this);
    }
  }

  @Override
  public void onContentChanged() {
    super.onContentChanged();
    mBackBtn = findViewById(R.id.toolbar_left_layout);
    if (mBackBtn != null) {
      mBackBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          BaseActivity.this.finish();
        }
      });
    }
  }

  protected void injectDependencies() {
  }

  /**
   * 是否使用eventBus,默认为使用(false)，
   *
   * @return
   */
  public boolean useEventBus() {
    return false;
  }

  @Override
  protected void onResume() {
    super.onResume();
//    MobclickAgent.onPageStart(getClass().getSimpleName());
//    MobclickAgent.onResume(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
//    MobclickAgent.onPageEnd(getClass().getSimpleName());
//    MobclickAgent.onPause(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if(useEventBus()){
      EventBus.getDefault().unregister(this);
    }
    if (mDialog != null) {
      if (mDialog.isShowing()) {
        mDialog.dismiss();
      }
      mDialog = null;
    }
  }
}
