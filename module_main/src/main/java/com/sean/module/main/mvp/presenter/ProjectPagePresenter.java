package com.sean.module.main.mvp.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.main.apiservice.MainApiService;
import com.sean.module.main.bean.ProjectResult;
import com.sean.module.main.mvp.contract.ProjectPageContract;

/**
 * Author WenPing
 * CreateTime 2019/9/15.
 * Description:根据id和page获取获取项目结果
 */
public class ProjectPagePresenter extends BasePresenter<ProjectPageContract.View> implements ProjectPageContract.Presenter {

    @Override
    public void getProjects(int id, int page) {
        addSubscribe(create(MainApiService.class)
        .getProjects(page,id),new BaseObserver<ProjectResult>(getView()) {

            @Override
            protected void onSuccess(ProjectResult data) {
                if (isViewAttached()) {
                    getView().onProjectList(data);
                }
            }
        });
    }
}
