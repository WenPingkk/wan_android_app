package com.sean.module.main.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.sean.base.library.base.BaseMVPActivity;
import com.sean.base.library.constants.Constants;
import com.sean.base.library.widget.LinearItemDecoration;
import com.sean.module.main.R;
import com.sean.module.main.adapter.SystemArticleAdapter;
import com.sean.module.main.bean.SystemArticleResult;
import com.sean.module.main.bean.SystemResult;
import com.sean.module.main.mvp.contract.SystemArticleContract;
import com.sean.module.main.mvp.presenter.SystemArticlePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/10/3.
 * Description:
 */
@Route(path = "/system/SystemArticleActivity")
public class SystemArticleActivity extends BaseMVPActivity<SystemArticlePresenter> implements SystemArticleContract.View {

    private RecyclerView recyclerView;
    private List<SystemArticleResult.DatasBean> dataList = new ArrayList<>();
    private SystemArticleAdapter adapter;
    private int id = 0;
    private int page = 0;
    private RefreshLayout refreshLayout;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_system_article;
    }

    @Override
    protected SystemArticlePresenter createPresenter() {
        return new SystemArticlePresenter();
    }

    @Override
    protected void initView() {
        //toobar 设置
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.rv_system_article);
        refreshLayout = findViewById(R.id.srf_system_article);
    }

    @Override
    protected void initData() {
        super.initData();
        //1.设置toolbar的标题
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            SystemResult.ChildrenBean result = (SystemResult.ChildrenBean) extras.getSerializable("SystemResult");
            if (result != null) {
                id = result.getId();
                String name = result.getName();
                getSupportActionBar().setTitle(name);
            }
        }
        //2.数据显示
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        //分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(1f)    // dp
                .color(0xaa999999);  // color 的 int 值，不是 R.color 中的值
        recyclerView.addItemDecoration(itemDecoration);

        presenter.getSystemArticleList(page, id);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getSystemArticleList(page, id);
            }
        });
    }

    @Override
    public void onSystemArticleList(SystemArticleResult result) {
        page ++;
        if (result != null) {
            dataList.addAll(result.getDatas());
            if (adapter == null) {
                adapter = new SystemArticleAdapter(R.layout.item_home_article, dataList);
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        gotoWebViewActivity(dataList.get(position));
                    }
                });
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setNewData(dataList);
            }
        }
    }

    /**
     * 跳转到webviewActivity中
     * @param bean
     */
    private void gotoWebViewActivity(SystemArticleResult.DatasBean bean) {
        if (bean == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, bean.getLink());
        bundle.putInt(Constants.ID, bean.getId());
        bundle.putString(Constants.AUTHOR, null);
        bundle.putString(Constants.TITLE, bean.getTitle());
        ARouter.getInstance()
                .build("/web/WebViewActivity")
                .with(bundle)
                .navigation();
        overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        refreshLayout.finishLoadMore();
    }
}
