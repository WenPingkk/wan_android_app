package com.sean.module.main.mvp.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.main.bean.SearchHotKey;
import com.sean.module.main.bean.db.SearchHistory;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public interface SearchHistoryContract {
    interface View extends IView {

        /**
         * 搜索热词
         */
        void onSearchHotKey(List<SearchHotKey> searchHotKeys);

        /**
         * 搜索历史
         */
        void onSearchHistory(List<SearchHistory> searchHistories);

        /**
         * 删除所有搜索历史
         */
        void onDeleteAllHistory();


    }

    interface Presenter {

        /**
         * 搜索热词
         */
        void getSearchHotKey();

        /**
         * 搜索历史
         */
        void getSearchHistory();

        /**
         * 删除所有搜索历史
         */
        void deleteAllHistory();
    }
}
