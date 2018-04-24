package com.lxf.mvp.imageloader;

import com.lxf.mvp.R;

/**
 * Created by lixiaofan on 2017/7/24.
 */
public class ImageConfig {
  protected String url;
  protected int placeholder = R.drawable.ic_empty;
  protected int errorPic = R.drawable.ic_error;
  protected ImageLoader.TransformationType transformationType = ImageLoader.TransformationType.NORMAL;

  public ImageLoader.TransformationType getTransformation() {
    return transformationType;
  }

  public void setTransformation(ImageLoader.TransformationType transformation) {
    this.transformationType = transformation;
  }

  public ImageConfig(Builder builder) {
    this.url = builder.url;
    this.placeholder = builder.placeholder;
    this.errorPic = builder.errorPic;
    this.transformationType = builder.transformation;
  }

  public String getUrl() {
    return url;
  }

  public int getPlaceholder() {
    return placeholder;
  }

  public int getErrorPic() {
    return errorPic;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    protected String url;
    protected int placeholder = R.drawable.ic_empty;
    protected int errorPic = R.drawable.ic_empty;
    protected ImageLoader.TransformationType transformation;

    private Builder() {
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder placeholder(int placeholder) {
      this.placeholder = placeholder;
      return this;
    }

    public Builder errorPic(int errorPic) {
      this.errorPic = errorPic;
      return this;
    }

    public Builder transformation(ImageLoader.TransformationType transformation) {
      this.transformation = transformation;
      return this;
    }

    public ImageConfig build() {
      return new ImageConfig(this);
    }
  }
}
