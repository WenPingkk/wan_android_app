package com.sean.module.main.mvp.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.main.bean.ProjectPageItem;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/9/15.
 * Description:项目的协议类
 */
public interface ProjectContract {
    interface View extends IView {
        void onProjectTabs(List<ProjectPageItem> projectPageItemList);
    }

    interface Presenter {
        void getProjectTabs();
    }
}
