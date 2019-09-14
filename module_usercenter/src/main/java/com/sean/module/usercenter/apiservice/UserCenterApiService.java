package com.sean.module.usercenter.apiservice;

import com.sean.base.library.base.BaseResponse;
import com.sean.module.usercenter.bean.LoginResult;
import com.sean.module.usercenter.bean.RegisterResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public interface UserCenterApiService {

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<RegisterResult>> regist(@Field("username") String username,
                                                    @Field("password") String password,
                                                    @Field("repassword") String repassword);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginResult>> login(@Field("username") String username,
                                                @Field("password") String password);

}
