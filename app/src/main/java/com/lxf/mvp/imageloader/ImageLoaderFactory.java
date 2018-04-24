package com.lxf.mvp.imageloader;

import com.lxf.mvp.imageloader.glide.GlideImageLoader;
import com.lxf.mvp.imageloader.picasso.PicassoImageLoader;

/**
 * Created by lixiaofan on 2017/7/19.
 */
public class ImageLoaderFactory {
  private static ImageLoader mImageLoader;

  public static ImageLoader getImageLoader(ImageLoaderType imageLoaderType) {
    if (mImageLoader == null) {
      if (imageLoaderType == ImageLoaderType.GLIDE) {
        mImageLoader = new GlideImageLoader();
      } else if (imageLoaderType == ImageLoaderType.PICASSO) {
        mImageLoader = new PicassoImageLoader();
      }
    }
    return mImageLoader;
  }
}
