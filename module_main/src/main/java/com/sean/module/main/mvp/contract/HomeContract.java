package com.sean.module.main.mvp.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.main.bean.BannerResult;
import com.sean.module.main.bean.HomeArticleResult;
import com.sean.module.main.bean.WeChatAuthorResult;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/8/13.
 * Description:
 * 主页面 协议
 */
public interface HomeContract {

    interface View extends IView {
        /**
         * banner 数据回调
         */
        void onBanner(List<BannerResult> bannerResults);

        /**
         * 公众号数据回调
         */
        void onWeChatAuthors(List<WeChatAuthorResult> weChatAuthorResults);


        /**
         * 首页文章列表数据回调
         *
         * @param result
         */
        void onHomeArticles(HomeArticleResult result);
    }

    interface Presenter {
        /**
         * 获取 banner 数据
         */
        void getBanner();

        /**
         * 获取公众号列表
         */
        void getWeChatAuthors();

        /**
         * 获取首页文章列表
         */
        void getHomeArticles(int page);

    }

}
