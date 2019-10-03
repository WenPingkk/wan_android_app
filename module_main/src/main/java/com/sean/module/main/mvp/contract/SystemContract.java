package com.sean.module.main.mvp.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.main.bean.SystemResult;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/10/3.
 * Description:
 */
public interface SystemContract {
    interface View extends IView {
        //回调
        void onSystemList(List<SystemResult> systemResults);
    }

    interface Presenter {
        /**
         * 去获取体系分类列表
         */
        void getSystemList();
    }
}
