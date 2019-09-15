package com.sean.module.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.sean.base.library.base.BaseMVPFragment;
import com.sean.base.library.constants.Constants;
import com.sean.base.library.widget.LinearItemDecoration;
import com.sean.module.main.R;
import com.sean.module.main.adapter.SearchResultAdapter;
import com.sean.module.main.bean.SearchResult;
import com.sean.module.main.mvp.contract.SearchResultContract;
import com.sean.module.main.mvp.presenter.SearchResultPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * 搜索结果
 * Description:
 */
public class SearchResultFragment extends BaseMVPFragment<SearchResultPresenter> implements SearchResultContract.View {

    private RecyclerView recyclerView;
    private String keyword;
    private int page = 0;
    private List<SearchResult.DatasBean> dataList = new ArrayList<>();
    private SearchResultAdapter searchResultAdapter;
    private RefreshLayout refreshLayout;

    /**
     * 读取 关键词
     * @param keyword
     * @return
     */
    public static SearchResultFragment newInstance(String keyword) {
        SearchResultFragment searchResultFragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_WORD, keyword);
        searchResultFragment.setArguments(bundle);
        return searchResultFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
            if (arguments != null) {
            keyword = arguments.getString(Constants.KEY_WORD);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void initView(View rootView) {
        refreshLayout = rootView.findViewById(R.id.srl_search_result);
        recyclerView = rootView.findViewById(R.id.rv_search_result);
    }


    @Override
    protected SearchResultPresenter createPresenter() {
        return new SearchResultPresenter();
    }

    @Override
    protected void initData() {
        //保存搜索记录
        saveSearchHistory(keyword);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(0.8f)
                .color(Color.parseColor("#aacccccc"))
                .margin(12, 12);//单位dp
        recyclerView.addItemDecoration(itemDecoration);

        presenter.getSearchResult(page, keyword);

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getSearchResult(page, keyword);
            }
        });
    }

    private void saveSearchHistory(String keyword) {
        presenter.saveSearchHistory(keyword);
    }

    @Override
    public void onSearchResult(SearchResult searchResults) {
        page ++;
        if (searchResults != null) {
            dataList.addAll(searchResults.getDatas());
            if (searchResultAdapter == null) {
                searchResultAdapter = new SearchResultAdapter(R.layout.item_search_result, dataList);
                searchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        gotoWebViewActivity(dataList.get(position));
                    }
                });
                recyclerView.setAdapter(searchResultAdapter);
            } else {
                searchResultAdapter.setNewData(dataList);
            }

        }
    }

    /**
     * 携带url跳转到webview页面
     * @param bean
     */
    private void gotoWebViewActivity(SearchResult.DatasBean bean) {
        if (bean == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", bean.getLink());
        ARouter.getInstance()
                .build("/web/WebViewActivity")
                .with(bundle)
                .navigation();
        getActivity().overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        refreshLayout.finishLoadMore();
    }
}
