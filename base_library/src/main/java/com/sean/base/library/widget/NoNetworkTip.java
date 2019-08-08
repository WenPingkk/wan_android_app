package com.sean.base.library.widget;

/**
 * Author WenPing
 * CreateTime 2019/8/8.
 * Description:
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.sean.base.library.R;

import java.lang.ref.WeakReference;

/**
 * 显示网络连接异常 提示条
 */
public class NoNetworkTip {

    private WindowManager mWindowManager;
    private WeakReference<Activity> mActivityWeakReference;
    private boolean isShowing = false;
    private View mView;
    private WindowManager.LayoutParams mLayoutParams;

    public NoNetworkTip(Activity activity) {
        //弱引用
        mActivityWeakReference = new WeakReference<>(activity);
        mWindowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        mLayoutParams.horizontalMargin = 100;
        mLayoutParams.horizontalWeight = 0.8f;
        mLayoutParams.y = 150;
        initView();
    }

    private void initView() {
        final Activity activity = mActivityWeakReference.get();
        if (activity != null) {
            mView = LayoutInflater.from(activity)
                    .inflate(R.layout.layout_no_network_tip, null, false);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //设置跳转到设置页面
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                }
            });
        }
    }

    public void show() {
        isShowing = true;
        if (mView == null) {
            initView();
        } else {
            mWindowManager.addView(mView, mLayoutParams);
        }
    }

    public void dismiss() {
        isShowing = false;
        mWindowManager.removeViewImmediate(mView);
        mView = null;
    }

    public boolean isShowing() {
        return isShowing;
    }
}
