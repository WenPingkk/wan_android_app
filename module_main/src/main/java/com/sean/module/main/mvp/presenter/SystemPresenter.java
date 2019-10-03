package com.sean.module.main.mvp.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.main.apiservice.MainApiService;
import com.sean.module.main.bean.SystemResult;
import com.sean.module.main.mvp.contract.SystemContract;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/10/3.
 * Description:获取体系数据列表
 */
public class SystemPresenter extends BasePresenter<SystemContract.View> implements SystemContract.Presenter {

    /**
     * 获取数据
     * 注意封装
     */
    @Override
    public void getSystemList() {
        addSubscribe(create(MainApiService.class).getSystemList()
                , new BaseObserver<List<SystemResult>>(getView()) {
                    @Override
                    protected void onSuccess(List<SystemResult> data) {
                        if (isViewAttached()) {
                            getView().onSystemList(data);
                        }
                    }
                });
    }
}
