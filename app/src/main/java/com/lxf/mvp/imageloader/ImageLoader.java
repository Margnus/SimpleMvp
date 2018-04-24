package com.lxf.mvp.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by lixiaofan on 2017/7/19.
 */
public interface ImageLoader {
  enum TransformationType {
    NORMAL,
    CIRCLE,
    ROUND
  }

  ImageLoader with(Context context);
  ImageLoader load(String url);
  ImageLoader placeholder(int placeholder);
  ImageLoader errorPic(int errorPic);
  void into(ImageView imageView);
  ImageLoader transformation(TransformationType transformation);

}
