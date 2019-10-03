package com.sean.module.main.mvp.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.main.bean.SystemArticleResult;

/**
 * Author WenPing
 * CreateTime 2019/10/3.
 * Description:
 */
public interface SystemArticleContract {
    interface View extends IView {
        void onSystemArticleList(SystemArticleResult result);
    }

    interface Presenter {
        void getSystemArticleList(int page, int id);
    }
}
