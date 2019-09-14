package com.sean.module.usercenter.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.usercenter.apiservice.UserCenterApiService;
import com.sean.module.usercenter.bean.RegisterResult;
import com.sean.module.usercenter.contract.RegisterContract;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    @Override
    public void regist(String phone, String password, String repassword) {
        addSubscribe(create(UserCenterApiService.class)
                        .regist(phone, password, repassword)
                , new BaseObserver<RegisterResult>() {
                    @Override
                    protected void onSuccess(RegisterResult data) {
                        if (isViewAttached()) {
                            getView().registerSuccess(data);
                        }
                    }
                });
    }
}
