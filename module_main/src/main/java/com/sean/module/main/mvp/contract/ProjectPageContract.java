package com.sean.module.main.mvp.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.main.bean.ProjectResult;

/**
 * Author WenPing
 * CreateTime 2019/9/15.
 * Description:
 */
public interface ProjectPageContract {
    interface View extends IView {
        void onProjectList(ProjectResult projectResult);
    }

    interface Presenter {
        void getProjects(int id, int page);
    }
}
