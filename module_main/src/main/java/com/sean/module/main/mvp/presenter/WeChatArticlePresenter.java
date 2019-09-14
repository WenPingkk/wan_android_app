package com.sean.module.main.mvp.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.main.apiservice.MainApiService;
import com.sean.module.main.bean.WeChatArticleResult;
import com.sean.module.main.mvp.contract.WeChatArticleListContract;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:WeChatArticlePresenter 具体实现
 * 获取公众号信息
 */
public class WeChatArticlePresenter extends BasePresenter<WeChatArticleListContract.View> implements WeChatArticleListContract.Presenter {

    @Override
    public void getWeChatArticle(int id, int page) {
        addSubscribe(create(MainApiService.class)
                .getWeChatArticles(id, page), new BaseObserver<WeChatArticleResult>(getView()) {
            @Override
            protected void onSuccess(WeChatArticleResult data) {
                if (isViewAttached()) {
                    getView().onWeChatArticleList(data);
                }
            }
        });
    }
}
