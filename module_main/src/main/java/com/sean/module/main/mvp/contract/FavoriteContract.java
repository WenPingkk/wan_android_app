package com.sean.module.main.mvp.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.main.bean.FavoriteResult;

/**
 * Author WenPing
 * CreateTime 2019/10/5.
 * Description:
 */
public interface FavoriteContract {
    interface View extends IView {
        void onFavoriteList(FavoriteResult result);
    }

    interface Presenter {
        void getFavoriteList(int page);
    }
}