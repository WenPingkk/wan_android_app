package com.sean.module.main.mvp.presenter;


import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.main.apiservice.MainApiService;
import com.sean.module.main.bean.BannerResult;
import com.sean.module.main.bean.HomeArticleResult;
import com.sean.module.main.bean.WeChatAuthorResult;
import com.sean.module.main.mvp.contract.HomeContract;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/8/13.
 * Description:
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    @Override
    public void getBanner() {
        addSubscribe(create(MainApiService.class)
                .getBanner(), new BaseObserver<List<BannerResult>>(getView()) {
            @Override
            protected void onSuccess(List<BannerResult> data) {
                if (isViewAttached()) {
                    getView().onBanner(data);
                }
            }
        });
    }

    @Override
    public void getWeChatAuthors() {
        addSubscribe(create(MainApiService.class).getWeChatAuthors(),
                new BaseObserver<List<WeChatAuthorResult>>() {
                    @Override
                    protected void onSuccess(List<WeChatAuthorResult> data) {
                        if (isViewAttached()) {
                            getView().onWeChatAuthors(data);
                        }
                    }
                });
    }

    @Override
    public void getHomeArticles(int page) {
        addSubscribe(create(MainApiService.class).getHomeArticles(page)
                , new BaseObserver<HomeArticleResult>(getView()) {
                    @Override
                    protected void onSuccess(HomeArticleResult data) {
                        if (isViewAttached()) {
                            getView().onHomeArticles(data);
                        }
                    }
                });
    }
}
