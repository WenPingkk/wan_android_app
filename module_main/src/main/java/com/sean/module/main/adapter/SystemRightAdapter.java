package com.sean.module.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sean.module.main.R;
import com.sean.module.main.bean.SystemResult;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/10/3.
 * Description:右侧gridview item
 */
public class SystemRightAdapter extends BaseQuickAdapter<SystemResult.ChildrenBean, BaseViewHolder> {


    public SystemRightAdapter(int layoutResId, @Nullable List<SystemResult.ChildrenBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemResult.ChildrenBean item) {
        helper.setText(R.id.tv_system_right_title, item.getName());
    }
}
