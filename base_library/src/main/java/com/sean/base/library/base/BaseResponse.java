package com.sean.base.library.base;

/**
 * Author WenPing
 * CreateTime 2019/8/11.
 * Description:
 * 网络请求返回的数据,按照格式统一包装成 BaseResponse 类
 */
public class BaseResponse <T>{

    private int errorCode = -1;
    private String errorMsg;
    private T data;

    /**
     * 兼容gangk io
     */
    private T result;
    private boolean error = true;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                ", result=" + result +
                ", error=" + error +
                '}';
    }
}
