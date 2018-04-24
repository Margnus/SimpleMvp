package com.lxf.mvp.adapter;

import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxf.mvp.R;
import com.lxf.mvp.imageloader.ImageLoader;
import com.lxf.mvp.model.TweetsResult;
import com.lxf.mvp.utils.DateUtil;
import com.lxf.mvp.utils.HtmlContent;

import java.util.ArrayList;


/**
 * Created by lixiaofan on 2017/7/25.
 */

public class TweetAdapter extends BaseQuickAdapter<TweetsResult.DataBean, BaseViewHolder> {

    private ImageLoader imageLoader;

    public TweetAdapter(ImageLoader imageLoader) {
        super(R.layout.item_maopao_list, new ArrayList<TweetsResult.DataBean>());
        this.imageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder helper, final TweetsResult.DataBean item) {
        helper.setText(R.id.name, item.getOwner().getName())
                .setText(R.id.time, DateUtil.dayToNow(item.getCreated_at()))
                .setText(R.id.content, HtmlContent.parseToText(item.getContent()))
                .setText(R.id.location, item.getLocation())
                .setText(R.id.photoType, item.getDevice())
                .setText(R.id.commentBtn, String.valueOf(item.getComments()))
                .setText(R.id.rewardCount, String.valueOf(item.getRewards()));
        CheckBox likeBtn = helper.getView(R.id.likeBtn);
        likeBtn.setChecked(item.isLiked());
        likeBtn.setText(String.valueOf(item.getLikes()));
        imageLoader.with(mContext).load(item.getOwner().getAvatar()).transformation(ImageLoader.TransformationType.CIRCLE)
                .into((ImageView) helper.getView(R.id.icon));
        LinearLayout likeUsersLayout = helper.getView(R.id.llyt_like_container);
        likeUsersLayout.removeAllViews();
        if(item.getLike_users() != null && item.getLike_users().size() > 0){
            for(TweetsResult.DataBean.LikeUsersBean bean : item.getLike_users()){
                ImageView icon = new ImageView(mContext);
                likeUsersLayout.addView(icon);
                ViewGroup.LayoutParams params = icon.getLayoutParams();
                params.height = likeUsersLayout.getHeight();
                params.width = likeUsersLayout.getHeight();
                imageLoader.with(mContext).load(bean.getAvatar()).transformation(ImageLoader.TransformationType.CIRCLE)
                        .into(icon);
            }
        }

    }
}
