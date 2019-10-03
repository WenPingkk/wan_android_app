package com.sean.module.main.aop;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sean.base.library.manager.UserLoginManager;
import com.sean.base.library.util.ToastUtil;
import com.sean.module.main.R;
import com.sean.module.main.annotation.UserLoginTrace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Author WenPing
 * CreateTime 2019/10/3.
 * Description:AOP拦截使用
 * 使用参考: https://www.jianshu.com/p/8cb0a1450cbb
 * https://blog.csdn.net/u010054982/article/details/88740122
 */
@Aspect
public class UserLoginAspect {

    private static final String TAG = UserLoginAspect.class.getSimpleName();

    /**
     * 定义切点，标记切点为所有被 @UserLoginAspect 注解修饰的方法
     */
    @Pointcut("execution(@com.sean.module.main.annotation.UserLoginTrace * *(..))")
    public void loginPointCut() {

    }


    /**
     *
     * 如果已经未登录则拦截ta
     *
     * @param joinPoint
     */
    @Around("loginPointCut()")
    public void execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "---handleLoginPointCut---");
        if (UserLoginManager.getInstance().isLoggedin()) {
            joinPoint.proceed();
        } else {
            //1.获取注解
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            //获取名字
            Method method = methodSignature.getMethod();
            //只处理被 userlogintrace修饰的方法
            if (!method.isAnnotationPresent(UserLoginTrace.class)) {
                return;
            }
            UserLoginTrace annotation = method.getAnnotation(UserLoginTrace.class);
            int value = annotation.value();
            Context context = getContext(joinPoint);
            if (context != null) {
                handleLogin(context, value);
            }
        }
    }

    /**
     * 跳转到登录页面
     * @param context
     * @param value
     */
    private void handleLogin(Context context, int value) {
        if (value == 0) {
            ToastUtil.show(context,context.getString(R.string.no_login_tips));
            ARouter.getInstance().build("/user/LoginActivity")
                    .navigation();
        } else {
            ToastUtil.show(context, context.getString(R.string.no_login_tips));
        }
    }

    /**
     * 获取上下文
     *
     * @param point
     * @return
     */
    private Context getContext(ProceedingJoinPoint point) {
        Object object = point.getThis();
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else if (object instanceof View) {
            return ((View) object).getContext();
        }
        return null;
    }


}
