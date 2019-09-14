package com.sean.module.main.mvp.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.main.bean.WeChatArticleResult;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:公众号文章
 */
public interface WeChatArticleListContract {

    interface View extends IView {
        void onWeChatArticleList(WeChatArticleResult result);
    }

    interface Presenter {
        void getWeChatArticle(int id, int page);
    }
}