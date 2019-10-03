package com.sean.module.main.mvp.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.main.apiservice.MainApiService;
import com.sean.module.main.bean.SystemArticleResult;
import com.sean.module.main.mvp.contract.SystemArticleContract;

/**
 * Author WenPing
 * CreateTime 2019/10/3.
 * Description:
 */
public class SystemArticlePresenter extends BasePresenter<SystemArticleContract.View> implements SystemArticleContract.Presenter{


    @Override
    public void getSystemArticleList(int page, int id) {
        addSubscribe(create(MainApiService.class)
                .getSystemArticles(page, id), new BaseObserver<SystemArticleResult>(getView()) {
            @Override
            protected void onSuccess(SystemArticleResult data) {
                if (isViewAttached()) {
                    getView().onSystemArticleList(data);
                }
            }
        });
    }
}
