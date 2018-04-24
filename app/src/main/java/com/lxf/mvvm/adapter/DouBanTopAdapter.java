package com.lxf.mvvm.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.lxf.mvp.R;
import com.lxf.mvp.databinding.ItemDoubanTopBinding;
import com.lxf.mvvm.base.BaseRecyclerViewAdapter;
import com.lxf.mvvm.base.BaseRecyclerViewHolder;
import com.lxf.mvvm.bean.DbMovieBean;

/**
 * Created by jingbin on 2016/12/10.
 */

public class DouBanTopAdapter extends BaseRecyclerViewAdapter<DbMovieBean.SubjectsBean> {


    private Activity activity;

    public DouBanTopAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_douban_top);
    }

    class ViewHolder extends BaseRecyclerViewHolder<DbMovieBean.SubjectsBean, ItemDoubanTopBinding> {

        ViewHolder(ViewGroup parent, int layout) {
            super(parent, layout);
        }

        @Override
        public void onBindViewHolder(final DbMovieBean.SubjectsBean bean, final int position) {
            binding.setBean(bean);
            /**
             * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
             */
            binding.executePendingBindings();


        }
    }
}
