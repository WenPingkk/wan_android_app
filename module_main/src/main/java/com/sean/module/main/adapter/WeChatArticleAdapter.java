package com.sean.module.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sean.module.main.R;
import com.sean.module.main.bean.WeChatArticleResult;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description: 公众号显示列表 适配器
 */
public class WeChatArticleAdapter extends BaseQuickAdapter<WeChatArticleResult.DatasBean, BaseViewHolder> {

    public WeChatArticleAdapter(int layoutResId, @Nullable List<WeChatArticleResult.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeChatArticleResult.DatasBean item) {
        helper.setText(R.id.tv_article_title, item.getTitle())
        .setText(R.id.tv_article_author,item.getAuthor())
        .setText(R.id.tv_article_time,item.getNiceDate());
    }
}
