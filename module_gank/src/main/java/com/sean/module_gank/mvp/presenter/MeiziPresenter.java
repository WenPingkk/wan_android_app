package com.sean.module_gank.mvp.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module_gank.apiservice.GankApiService;
import com.sean.module_gank.bean.MeiziResult;
import com.sean.module_gank.mvp.contract.MeiziContract;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/10/5.
 * Description:
 */
public class MeiziPresenter extends BasePresenter<MeiziContract.View> implements MeiziContract.Presenter {

    @Override
    public void getMeiziList(int pageSize, int page) {
        addSubscribe(create(GankApiService.class).getMeiziList(pageSize, page), new BaseObserver<List<MeiziResult>>(getView()) {
            @Override
            protected void onSuccess(List<MeiziResult> data) {
                if (isViewAttached()) {
                    if (data != null) {
                        getView().onMeiziList(data);
                    }
                }
            }
        });
    }
}
