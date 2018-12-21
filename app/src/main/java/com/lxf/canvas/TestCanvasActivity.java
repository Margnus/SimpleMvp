package com.lxf.canvas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lxf.mvp.R;

@Route(path = "/canvas/test")
public class TestCanvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_test);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
