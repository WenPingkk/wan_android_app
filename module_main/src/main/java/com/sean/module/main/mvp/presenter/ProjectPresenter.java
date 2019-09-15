package com.sean.module.main.mvp.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.main.apiservice.MainApiService;
import com.sean.module.main.bean.ProjectPageItem;
import com.sean.module.main.bean.ProjectTabItem;
import com.sean.module.main.fragment.ProjectPageFragment;
import com.sean.module.main.mvp.contract.ProjectContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/9/15.
 * Description:
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    @Override
    public void getProjectTabs() {
        addSubscribe(create(MainApiService.class)
                .getProjectTabs(), new BaseObserver<List<ProjectTabItem>>(getView()) {
            @Override
            protected void onSuccess(List<ProjectTabItem> data) {
                List<ProjectPageItem> projectPageItemList = createProjectPages(data);
                if (isViewAttached()) {
                    getView().onProjectTabs(projectPageItemList);
                }
            }
        });
    }

    /**
     * 根据tab数，创建对应数量的fragment 的project怕个item
     * 对应fragment 根据id来请求对应页面数据
     * @param data
     * @return
     */
    private List<ProjectPageItem> createProjectPages(List<ProjectTabItem> data) {
        if (data == null || data.size() == 0) {
            return new ArrayList<>();
        }
        List<ProjectPageItem> pageItemList = new ArrayList<>();
        for (ProjectTabItem projectTabItem : data) {
            ProjectPageItem projectPageItem = new ProjectPageItem(projectTabItem.getId(), projectTabItem.getName(), ProjectPageFragment.newInstance(projectTabItem.getId()));
            pageItemList.add(projectPageItem);
        }
        return pageItemList;
    }
}
