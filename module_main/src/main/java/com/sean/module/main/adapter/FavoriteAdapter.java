package com.sean.module.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sean.module.main.R;
import com.sean.module.main.bean.FavoriteResult;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/10/5.
 * Description:
 */
public class FavoriteAdapter extends BaseQuickAdapter<FavoriteResult.DatasBean, BaseViewHolder> {


    public FavoriteAdapter(int layoutResId, @Nullable List<FavoriteResult.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FavoriteResult.DatasBean item) {
        helper.setText(R.id.tv_article_title, item.getTitle())
                .setText(R.id.tv_article_author, item.getAuthor())
                .setText(R.id.tv_article_time, item.getNiceDate());
    }
}
