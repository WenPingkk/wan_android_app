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
 * Description:
 */
public class SystemLeftAdapter extends BaseQuickAdapter<SystemResult, BaseViewHolder> {

    public SystemLeftAdapter(int layoutResId, @Nullable List<SystemResult> data) {
        super(layoutResId, data);
    }

    /**
     * 设置数据以及根据选中状态显示背景色
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder helper, SystemResult item) {
        helper.setText(R.id.tv_system_left_title, item.getName())
                .setBackgroundColor(R.id.tv_system_left_title, item.isSelected() ? 0xffffffff : 0xffeeeeee);
    }
}
