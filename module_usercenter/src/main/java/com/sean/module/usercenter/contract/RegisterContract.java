package com.sean.module.usercenter.contract;

import com.sean.base.library.mvp.IView;
import com.sean.module.usercenter.bean.RegisterResult;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public interface RegisterContract {

    interface View extends IView {
        void registerSuccess(RegisterResult result);
    }

    interface Presenter {
        void regist(String phone, String password, String repassword);
    }

}
