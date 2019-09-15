package com.sean.module.main.mvp.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.main.bean.SearchResult;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public interface SearchResultContract {
    interface View extends IView {
        void onSearchResult(SearchResult searchResults);
    }

    interface Presenter {
        /**
         * 保存搜索历史记录
         *
         * @param keyword
         */
        void saveSearchHistory(String keyword);

        /**
         * 获取搜索结果
         *
         * @param page
         * @param keyword
         */
        void getSearchResult(int page, String keyword);
    }
}
