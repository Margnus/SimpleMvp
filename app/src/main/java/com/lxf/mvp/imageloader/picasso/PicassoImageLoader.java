package com.lxf.mvp.imageloader.picasso;

import android.content.Context;
import android.widget.ImageView;

import com.lxf.mvp.imageloader.ImageConfig;
import com.lxf.mvp.imageloader.ImageLoader;
import com.squareup.picasso.Picasso;

/**
 * Created by lixiaofan on 2017/7/19.
 */
public class PicassoImageLoader implements ImageLoader {

  private Context context;
  private ImageConfig.Builder builder;

  @Override
  public ImageLoader with(Context context) {
    this.context = context;
    builder = ImageConfig.builder();
    return this;
  }

  @Override
  public ImageLoader load(String url) {
    builder.url(url);
    return this;
  }

  @Override
  public ImageLoader placeholder(int placeholder) {
    builder.placeholder(placeholder);
    return this;
  }

  @Override
  public ImageLoader errorPic(int errorPic) {
    builder.errorPic(errorPic);
    return this;
  }

  @Override
  public void into(ImageView imageView) {
    ImageConfig config = builder.build();
    Picasso.with(context)
            .load(config.getUrl())
            .placeholder(config.getPlaceholder())
            .error(config.getErrorPic())
            .into(imageView);
  }

  @Override
  public ImageLoader transformation(TransformationType transformation) {
    builder.transformation(transformation);
    return this;
  }
}
