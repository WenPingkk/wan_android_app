package com.sean.module.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sean.module.main.R;
import com.sean.module.main.bean.SystemArticleResult;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/10/3.
 * Description:
 */
public class SystemArticleAdapter extends BaseQuickAdapter<SystemArticleResult.DatasBean, BaseViewHolder> {

    public SystemArticleAdapter(int layoutResId, @Nullable List<SystemArticleResult.DatasBean> data) {
        super(layoutResId, data);
    }

    public SystemArticleAdapter(@Nullable List<SystemArticleResult.DatasBean> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemArticleResult.DatasBean item) {
        helper.setText(R.id.tv_article_title, item.getTitle())
                .setText(R.id.tv_article_author, item.getAuthor())
                .setText(R.id.tv_article_time, item.getNiceDate());

    }
}