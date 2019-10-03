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
import com.sean.base.library.base.BaseLazyFragment;
import com.sean.base.library.constants.Constants;
import com.sean.base.library.widget.LinearItemDecoration;
import com.sean.module.main.R;
import com.sean.module.main.adapter.ProjectRecyclerAdapter;
import com.sean.module.main.bean.ProjectResult;
import com.sean.module.main.mvp.contract.ProjectPageContract;
import com.sean.module.main.mvp.presenter.ProjectPagePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/9/15.
 * Description:
 * 懒加载的fragment
 */
public class ProjectPageFragment extends BaseLazyFragment<ProjectPagePresenter> implements ProjectPageContract.View {

    private RecyclerView recyclerView;
    private int id;
    private int page = 0;
    private ProjectRecyclerAdapter recyclerAdapter;
    private List<ProjectResult.DatasBean> mDataList = new ArrayList<>();
    private RefreshLayout refreshLayout;


    public static ProjectPageFragment newInstance(int id) {
        ProjectPageFragment fragment = new ProjectPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getInt("id");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project_page;
    }

    @Override
    protected void initView(View view) {
        refreshLayout = view.findViewById(R.id.srl_project);
        recyclerView = view.findViewById(R.id.rv_home_page);
    }

    /**
     * 懒加载 加载数据
     * o'nViewVisible 和onViewcreated 调用
     */
    @Override
    protected void loadData() {
        presenter.getProjects(id,page);

    }

    @Override
    protected void initData() {
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(0.8f)
                .color(Color.parseColor("#aacccccc"))
                .margin(12, 12);//单位 dp
        recyclerView.addItemDecoration(itemDecoration);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getProjects(id, page);
            }
        });
    }

    @Override
    protected ProjectPagePresenter createPresenter() {
        return new ProjectPagePresenter();
    }


    @Override
    public void onProjectList(ProjectResult projectResult) {
        //获取完数据后 page进行自加，后续get数据时继续请求
        //这种方式不太好，具体参考yizhi方式会更好。
        page++;
        if (projectResult != null) {
            List<ProjectResult.DatasBean> datas = projectResult.getDatas();
            if (datas != null) {
                mDataList.addAll(datas);
                if (recyclerAdapter == null) {
                    recyclerAdapter = new ProjectRecyclerAdapter(R.layout.item_recycler_project, mDataList);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            gotoWebViewActivity(mDataList.get(position));
                        }
                    });
                } else {
                    recyclerAdapter.setNewData(mDataList);
                }
            } else {
                refreshLayout.setNoMoreData(true);
            }
        }
    }

    /**
     * 点击跳转到webviewactivity
     *
     * @param datasBean
     */
    private void gotoWebViewActivity(ProjectResult.DatasBean datasBean) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, datasBean.getLink());
        bundle.putInt(Constants.ID, datasBean.getId());
        bundle.putString(Constants.AUTHOR, datasBean.getAuthor());
        bundle.putString(Constants.TITLE, datasBean.getTitle());
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
