package com.sean.module.main.fragment;

import android.view.View;

import com.sean.base.library.base.BaseMVPFragment;
import com.sean.module.main.bean.BannerResult;
import com.sean.module.main.bean.HomeArticleResult;
import com.sean.module.main.bean.WeChatAuthorResult;
import com.sean.module.main.mvp.contract.HomeContract;
import com.sean.module.main.mvp.presenter.HomePresenter;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/8/13.
 * Description:
 */
public class HomeFragment extends BaseMVPFragment<HomePresenter> implements HomeContract.View {

    @Override
    protected HomePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void onBanner(List<BannerResult> bannerResults) {

    }

    @Override
    public void onWeChatAuthors(List<WeChatAuthorResult> weChatAuthorResults) {

    }

    @Override
    public void onHomeArticles(HomeArticleResult result) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
