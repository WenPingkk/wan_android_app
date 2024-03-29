package com.sean.module_gank.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.sean.base.library.base.BaseMVPActivity;
import com.sean.module_gank.R;
import com.sean.module_gank.adapter.MeiziAdapter;
import com.sean.module_gank.bean.MeiziResult;
import com.sean.module_gank.mvp.contract.MeiziContract;
import com.sean.module_gank.mvp.presenter.MeiziPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/10/5.
 * Description:Meizi 页面
 * meiziresult 数据结构发生了改变；修改了baseresponse以及bean
 */
@Route(path = "/gank/ImageMeiziActivity")
public class ImageMeiziActivity extends BaseMVPActivity<MeiziPresenter> implements MeiziContract.View {

    private RecyclerView recyclerView;
    private ArrayList<MeiziResult> dataList = new ArrayList<>();
    private MeiziAdapter adapter;
    private RefreshLayout refreshLayout;
    private int pageSize = 16;
    private int page = 1;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_meizi_image;
    }

    @Override
    protected MeiziPresenter createPresenter() {
        return new MeiziPresenter();
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.meizi);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout = findViewById(R.id.srl_meizi);
        recyclerView = findViewById(R.id.rv_meizi);
    }

    @Override
    protected void initData() {
        super.initData();
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        presenter.getMeiziList(pageSize, page);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getMeiziList(pageSize, page);
            }
        });
    }

    @Override
    public void onMeiziList(List<MeiziResult> meiziResults) {
        page ++;
        dataList.addAll(meiziResults);
        if (adapter == null) {
            adapter = new MeiziAdapter(R.layout.item_meizi, dataList);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    gotoImagePreviewActivity(dataList, position);
                }
            });
            recyclerView.setAdapter(adapter);
        }else {
            adapter.setNewData(dataList);
        }

    }

    private void gotoImagePreviewActivity(ArrayList<MeiziResult> list, int position) {
        Bundle bundle = new Bundle();
        //使用putParcelableArrayList
        bundle.putParcelableArrayList("meizis", dataList);
        bundle.putInt("position", position);
        ARouter.getInstance()
                .build("/gank/ImagePreviewActivity")
                .with(bundle)
                .navigation();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        refreshLayout.finishLoadMore();
    }
}
