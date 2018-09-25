package com.lxf.mvp.test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hannesdorfmann.mosby3.base.BaseActivity;
import com.lxf.mvp.R;
import com.lxf.mvp.view.ScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/scroll/test")
public class TestActivity extends BaseActivity {

    @BindView(R.id.text)
    Button text;
    @BindView(R.id.dragView)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.text)
    public void onViewClicked() {
        scrollView.startScroll(-100, -100);
    }
}
