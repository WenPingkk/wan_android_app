package com.sean.module.main.mvp.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.main.apiservice.MainApiService;
import com.sean.module.main.bean.FavoriteResult;
import com.sean.module.main.mvp.contract.FavoriteContract;

/**
 * Author WenPing
 * CreateTime 2019/10/5.
 * Description:
 */
public class FavoritePresenter extends BasePresenter<FavoriteContract.View>
        implements FavoriteContract.Presenter {

    @Override
    public void getFavoriteList(int page) {
        addSubscribe(create(MainApiService.class).getFavoriteList(page), new BaseObserver<FavoriteResult>(getView()) {
            @Override
            protected void onSuccess(FavoriteResult data) {
                if (isViewAttached()) {
                    getView().onFavoriteList(data);
                }
            }
        });
    }
}
