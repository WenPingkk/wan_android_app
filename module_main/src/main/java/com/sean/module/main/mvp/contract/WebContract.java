package com.sean.module.main.mvp.contract;

import com.sean.base.library.mvp.IView;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public interface WebContract {

    interface View extends IView {
        /**
         * 添加收藏成功回调
         */
        void onFavoriteAdded();

        void onFavoriteDeleted();
    }

    interface Presenter {
        /**
         * 添加收藏
         *
         * @param id
         * @param title
         * @param author
         * @param link
         */
        void addArticleFavorite(int id, String title, String author, String link);
    }

}
