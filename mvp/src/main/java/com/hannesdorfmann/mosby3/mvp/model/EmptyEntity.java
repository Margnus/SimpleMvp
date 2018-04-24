package com.hannesdorfmann.mosby3.mvp.model;

/**
 * Created by lixiaofan on 2017/1/4.
 */

public class EmptyEntity {
    private String description;
    private int image = -1;
    private boolean loadMore = false;

    public EmptyEntity(String description, int image) {
        this.description = description;
        this.image = image;
    }

    public EmptyEntity(String description, int image, boolean loadMore) {
        this.description = description;
        this.image = image;
        this.loadMore = loadMore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isLoadMore() {
        return loadMore;
    }

    public void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
    }
}
