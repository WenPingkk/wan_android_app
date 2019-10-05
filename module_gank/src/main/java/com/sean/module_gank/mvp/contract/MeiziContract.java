package com.sean.module_gank.mvp.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module_gank.bean.MeiziResult;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/10/5.
 * Description:
 */
public interface MeiziContract {
    interface View extends IView {
        void onMeiziList(List<MeiziResult> meiziResults);
    }

    interface Presenter {
        void getMeiziList(int pageSize, int page);
    }
}
