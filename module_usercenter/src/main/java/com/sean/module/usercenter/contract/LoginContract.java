package com.sean.module.usercenter.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.usercenter.bean.LoginResult;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public interface LoginContract {
    interface View extends IView {
        void loginSuccess(LoginResult result);
    }

    interface Presenter{
        void login(String phone, String password);
    }
}
