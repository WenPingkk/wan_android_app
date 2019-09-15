package com.sean.module.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sean.module.main.R;
import com.sean.module.main.bean.db.SearchHistory;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public class SearchHistoryAdapter extends BaseQuickAdapter<SearchHistory, BaseViewHolder> {

    public SearchHistoryAdapter(int layoutResId, @Nullable List<SearchHistory> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistory item) {
        helper.setText(R.id.tv_search_history_title, item.getKeyword())
                .addOnClickListener(R.id.iv_search_history_del);
    }
}
