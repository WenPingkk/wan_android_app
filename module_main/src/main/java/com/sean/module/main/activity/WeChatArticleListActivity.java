package com.sean.module.main.activity;

import android.graphics.Color;
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
import com.sean.module.main.adapter.WeChatArticleAdapter;
import com.sean.module.main.bean.WeChatArticleResult;
import com.sean.module.main.bean.WeChatAuthorResult;
import com.sean.module.main.mvp.contract.WeChatArticleListContract;
import com.sean.module.main.mvp.presenter.WeChatArticlePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
@Route(path = "/wechat/WeChatArticleListActivity")
public class WeChatArticleListActivity extends BaseMVPActivity<WeChatArticlePresenter> implements WeChatArticleListContract.View {

    private RecyclerView recyclerView;
    private int page;
    private WeChatArticleAdapter adapter;
    private List<WeChatArticleResult.DatasBean> dataList = new ArrayList<>();
    private RefreshLayout refreshLayout;
    private int id;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_we_chat_article_list;
    }

    @Override
    protected WeChatArticlePresenter createPresenter() {
        return new WeChatArticlePresenter();
    }

    @Override
    protected void initView() {
        refreshLayout = findViewById(R.id.srl_wechat);
        recyclerView = findViewById(R.id.rv_wechat_article);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置 分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(0.8f)
                .margin(10, 10)
                .color(Color.parseColor("#66dddddd"));
        recyclerView.addItemDecoration(itemDecoration);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            WeChatAuthorResult weChatAuthorResult = (WeChatAuthorResult) bundle.getSerializable("WeChatAuthorResult");
            id = weChatAuthorResult.getId();
            String name = weChatAuthorResult.getName();
            getSupportActionBar().setTitle(name);
            presenter.getWeChatArticle(id, page);
        }

        setListener();
    }

    private void setListener() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getWeChatArticle(id, page);
            }
        });
    }

    @Override
    public void onWeChatArticleList(WeChatArticleResult result) {
        page++;
        if (result != null) {
            List<WeChatArticleResult.DatasBean> resultDatas = result.getDatas();
            if (resultDatas != null) {
                dataList.addAll(resultDatas);
                if (adapter == null) {
                    adapter = new WeChatArticleAdapter(R.layout.item_home_article, dataList);
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
            } else {
                refreshLayout.setNoMoreData(true);
            }
        }
    }

    /**
     * 携带数据 跳转到webviewactivity
     * @param datasBean
     */
    private void gotoWebViewActivity(WeChatArticleResult.DatasBean datasBean) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, datasBean.getTitle());
        bundle.putString(Constants.AUTHOR, datasBean.getAuthor());
        bundle.putInt(Constants.ID, datasBean.getId());
        bundle.putString(Constants.URL, datasBean.getLink());
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
