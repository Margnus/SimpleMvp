package com.lxf.mvp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lixiaofan on 16/9/12.
 */
public class ToastUtil {

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int res) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }
}
