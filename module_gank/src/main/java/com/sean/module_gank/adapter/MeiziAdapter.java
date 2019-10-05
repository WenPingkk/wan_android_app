package com.sean.module_gank.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sean.module_gank.R;
import com.sean.module_gank.bean.MeiziResult;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/10/5.
 * Description:加载妹子的图片
 */
public class MeiziAdapter extends BaseQuickAdapter<MeiziResult, BaseViewHolder> {


    public MeiziAdapter(int layoutResId, @Nullable List<MeiziResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiziResult item) {
        ImageView imageView = helper.getView(R.id.iv_meizi_image);
        Glide.with(mContext)
                .load(item.getUrl())
                .into(imageView);
    }
}
