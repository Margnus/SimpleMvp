package com.lxf.mvp.imageloader.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.lxf.mvp.imageloader.ImageConfig;
import com.lxf.mvp.imageloader.ImageLoader;

/**
 * Created by lixiaofan on 2017/7/19.
 */
public class GlideImageLoader implements ImageLoader {

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
        RequestManager manager;
        ImageConfig config = builder.build();
        manager = Glide.with(context);//如果context是activity则自动使用Activity的生命周期
        DrawableRequestBuilder<String> requestBuilder = manager.load(config.getUrl())
                .crossFade()
                .centerCrop();
        if (config.getTransformation() != null) {//glide用它来改变图形的形状
            if (config.getTransformation() == TransformationType.CIRCLE) {
                requestBuilder.transform(new GlideCircleTransform(context));
            } else if (config.getTransformation() == TransformationType.ROUND) {
                requestBuilder.transform(new GlideRoundTransform(context));
            }
        }
        if (config.getPlaceholder() != 0)//设置占位符
        {
            requestBuilder.placeholder(config.getPlaceholder());
        }
        if (config.getErrorPic() != 0)//设置错误的图片
        {
            requestBuilder.error(config.getErrorPic());
        }
        requestBuilder
                .into(imageView);
    }

    @Override
    public ImageLoader transformation(TransformationType transformation) {
        builder.transformation(transformation);
        return this;
    }
}
