package com.sean.module.main.mvp.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.main.apiservice.MainApiService;
import com.sean.module.main.bean.FavoriteAddResult;
import com.sean.module.main.mvp.contract.WebContract;

import io.reactivex.Observable;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {

    @Override
    public void addArticleFavorite(int id, String title, String author, String link) {
        Observable observable;
        if (id == -1) {
            //站外文章
            observable = create(MainApiService.class)
                    .addFavorite(title, author, link);
        } else {
            //站外文章
            observable = create(MainApiService.class)
                    .addFavorite(id);
        }
        addSubscribe(observable, new BaseObserver<FavoriteAddResult>() {

            @Override
            protected void onSuccess(FavoriteAddResult data) {
                if (isViewAttached()) {
                    getView().onFavoriteAdded();
                }
            }
        });
    }
}
