package com.sean.module.main.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.sean.base.library.base.BaseMVPFragment;
import com.sean.module.main.R;
import com.sean.module.main.adapter.ProjectPagerAdapter;
import com.sean.module.main.bean.ProjectPageItem;
import com.sean.module.main.mvp.contract.ProjectContract;
import com.sean.module.main.mvp.presenter.ProjectPresenter;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/8/14.
 * Description:
 */
public class ProjectFragment extends BaseMVPFragment<ProjectPresenter> implements ProjectContract.View {

    private View mFakeStatusBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView(View view) {
        mFakeStatusBar = view.findViewById(R.id.fake_status_bar);
        tabLayout = view.findViewById(R.id.tl_project);
        viewPager = view.findViewById(R.id.vp_project_page);
        mFakeStatusBar.post(new Runnable() {
            @Override
            public void run() {
                int height = mFakeStatusBar.getHeight();
                Log.e("mTag", "mFakeStatusBar height:" + height);
            }
        });

    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    /**
     * 获取项目数据
     * 重要逻辑
     */
    @Override
    protected void initData() {
        presenter.getProjectTabs();
    }

    /**
     * 是fragment里嵌套viewpager的fragment
     * 要使用 getChildFragmentManager
     * @param projectPageItemList
     */
    @Override
    public void onProjectTabs(List<ProjectPageItem> projectPageItemList) {
        ProjectPagerAdapter pagerAdapter = new ProjectPagerAdapter(getChildFragmentManager());
        pagerAdapter.setPages(projectPageItemList);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setStatusBarColor(int color) {
        mFakeStatusBar.setBackgroundColor(color);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
