package com.sean.module.main.aop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Author WenPing
 * CreateTime 2019/10/3.
 * Description:AOP拦截使用
 * 使用参考: https://www.jianshu.com/p/8cb0a1450cbb
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
     * @param joinPoint
     */
    @Around("loginPointCut()")
    public void execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "---handleLoginPointCut---");
        joinPoint.proceed();
    }
}
