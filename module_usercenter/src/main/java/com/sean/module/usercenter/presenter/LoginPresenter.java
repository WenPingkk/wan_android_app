package com.sean.module.usercenter.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.constants.Constants;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.base.library.util.SharedPreferenceUtil;
import com.sean.module.usercenter.apiservice.UserCenterApiService;
import com.sean.module.usercenter.bean.LoginResult;
import com.sean.module.usercenter.contract.LoginContract;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    @Override
    public void login(String username, String password) {
        addSubscribe(create(UserCenterApiService.class)
        .login(username,password),new BaseObserver<LoginResult>(getView()){

            @Override
            protected void onSuccess(LoginResult data) {
                if (isViewAttached()) {
                    getView().loginSuccess(data);
                }
            }
        });
    }

    private void saveCookie(LoginResult result) {
        if (result != null) {
//            SharedPreferenceUtils.write(Constants.File_TOKEN, Constants.ACCESS_TOKEN, result.getAccessToken());
        }
    }

    /**
     * 保存 用户信息到本地
     * @param username
     * @param password
     */
    public void saveUsernamePassword(String username, String password) {
        SharedPreferenceUtil.write(Constants.USER_LOGIN, Constants.USERNAME, username);
        SharedPreferenceUtil.write(Constants.USER_LOGIN, Constants.PASSWORD, password);
    }

    /**
     * 读取用户信息到本地
     * @param key
     * @return
     */
    public String readUsernamePassword(String key) {
        return SharedPreferenceUtil.read(Constants.USER_LOGIN, key, "");
    }
}
