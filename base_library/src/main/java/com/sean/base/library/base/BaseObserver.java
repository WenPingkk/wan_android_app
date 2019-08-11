package com.sean.base.library.base;

import com.sean.base.library.http.ApiException;
import com.sean.base.library.http.ExceptionHandler;
import com.sean.base.library.mvp.IView;

import io.reactivex.observers.DisposableObserver;

/**
 * Author WenPing
 * CreateTime 2019/8/11.
 * Description:
 * 封装DisposableObserver 处理请求结果
 */
public abstract class BaseObserver<T> extends DisposableObserver<BaseResponse<T>> {

    private IView baseView;

    public BaseObserver() {

    }

    public BaseObserver(IView baseView) {
        this.baseView = baseView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (baseView != null) {
            baseView.showLoading();
        }
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        if (baseView != null) {
            baseView.hideLoading();
        }

        int errorCode = baseResponse.getErrorCode();
        String errorMsg = baseResponse.getErrorMsg();

        //兼容gank api
        boolean isOk = !baseResponse.isError();
        if (errorCode == 0 || errorCode == 200) {
            T data = baseResponse.getData();
            //将服务端获取正常的数据传递给上层调用方
            onSuccess(data);
        } else if (isOk) {
            T data = baseResponse.getResult();
            onSuccess(data);
        } else {
            onError(new ApiException(errorCode, errorMsg));
        }
    }


    /**
     * 回调正常数据
     *
     * @param data
     */
    protected abstract void onSuccess(T data);

    /**
     * 异常处理，包括两方面数据：
     * (1) 服务端没有没有返回数据，HttpException，如网络异常，连接超时;
     * (2) 服务端返回了数据，但 errcode!=0,即服务端返回的data为空，如 密码错误,App登陆超时,token失效
     */
    @Override
    public void onError(Throwable e) {
        ExceptionHandler.handleException(e);
    }

    @Override
    public void onComplete() {
        if (baseView != null) {
            baseView.hideLoading();
        }
    }
}
