package com.sean.module.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sean.base.library.base.BaseMVPFragment;
import com.sean.base.library.widget.LinearItemDecoration;
import com.sean.module.main.R;
import com.sean.module.main.adapter.SystemLeftAdapter;
import com.sean.module.main.adapter.SystemRightAdapter;
import com.sean.module.main.bean.SystemResult;
import com.sean.module.main.mvp.contract.SystemContract;
import com.sean.module.main.mvp.presenter.SystemPresenter;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/8/14.
 * Description:
 */
public class SystemFragment extends BaseMVPFragment<SystemPresenter> implements SystemContract.View {

    private RecyclerView leftRecyclerView;
    private RecyclerView rightRecyclerView;

    private SystemLeftAdapter systemLeftAdapter;
    private SystemRightAdapter systemRightAdapter;

    private int leftCurrentPosition = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initView(View rootView) {
        final View mFakeStatusBar = rootView.findViewById(R.id.fake_status_bar);
        mFakeStatusBar.post(new Runnable() {
            @Override
            public void run() {
                int height = mFakeStatusBar.getHeight();
                Log.e("mTag", "mFakeStatusBar height:" + height);
            }
        });
        leftRecyclerView = rootView.findViewById(R.id.rv_system_left);
        rightRecyclerView = rootView.findViewById(R.id.rv_system_right);
    }

    @Override
    protected void initData() {
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rightRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        //设置 分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(0.5f)
                .color(Color.parseColor("#aacccccc"))
                .margin(12, 12);
        leftRecyclerView.addItemDecoration(itemDecoration);
        /**
         * 获取体系数据
         */
        presenter.getSystemList();
    }

    /**
     * 获取体系数据信息
     *
     * @param systemResults
     */
    @Override
    public void onSystemList(final List<SystemResult> systemResults) {
        if (systemResults == null) {
            return;
        }
        if (systemLeftAdapter == null) {
            //左侧
            systemLeftAdapter = new SystemLeftAdapter(R.layout.item_system_left, systemResults);
            //默认选择第一个为选中状态
            systemResults.get(0).setSelected(true);
            systemLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (leftCurrentPosition == position) {
                        return;
                    }
                    //设置对应position为选中状态
                    leftCurrentPosition = position;
                    for (int i = 0; i < systemResults.size(); i++) {
                        SystemResult result = systemResults.get(i);
                        result.setSelected(i == leftCurrentPosition);
                    }
                    systemLeftAdapter.notifyDataSetChanged();
                    //设置right
                    SystemResult result = systemResults.get(position);
                    if (result != null) {
                        List<SystemResult.ChildrenBean> children = result.getChildren();
                        systemRightAdapter.setNewData(children);
                    }
                }
            });
            leftRecyclerView.setAdapter(systemLeftAdapter);
        } else {
            systemLeftAdapter.setNewData(systemResults);
        }
        
        //右侧
        if (systemRightAdapter == null) {
            systemRightAdapter = new SystemRightAdapter(R.layout.item_system_right, systemResults.get(0).getChildren());
            systemRightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    gotoSystemArticleActivity(systemRightAdapter.getData().get(position));
                }
            });
            rightRecyclerView.setAdapter(systemRightAdapter);
        }
        
    }

    private void gotoSystemArticleActivity(SystemResult.ChildrenBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SystemResult", bean);
        ARouter.getInstance().build("/system/SystemArticleActivity")
                .with(bundle)
                .navigation();
    }

    @Override
    protected SystemPresenter createPresenter() {
        return new SystemPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
