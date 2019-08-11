package com.sean.base.library.base;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.sean.base.library.annotation.BindEventBus;
import com.sean.base.library.receiver.NetworkChangeReceiver;
import com.sean.base.library.util.EventBusHelper;
import com.sean.base.library.util.StatusBarUtil;

/**
 * Author WenPing
 * CreateTime 2019/8/8.
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private NetworkChangeReceiver mReceiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mContext = this;
        setStatusBarColor();

        registerNetworkChangeReceiver();
        initView();
        initData();
    }

    protected abstract void initView();

    protected void initData() {

    }


    /**
     * 注册 网络变化广播
     */
    private void registerNetworkChangeReceiver() {
        mReceiver = new NetworkChangeReceiver(this);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, intentFilter);
    }

    /**
     * 设置状态栏颜色
     */
    private void setStatusBarColor() {
        StatusBarUtil.setColor(this, getResources()
                .getColor(android.R.color.white), 0);
    }

    /**
     * 布局
     *
     * @return
     */
    protected abstract int getLayoutResId();


    /**
     * 反注册广播
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            //把Tip 移除并 setNull
            mReceiver.onDestroy();
            mReceiver = null;
        }
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusHelper.unregister(this);
        }
    }

    /**
     * 设置 状态栏字体颜色 白色和深色
     *
     * @param window
     * @param lightStatusBar
     */
    public void setStatusBarTextColor(Window window, boolean lightStatusBar) {
        View decor = window.getDecorView();
        int ui = decor.getSystemUiVisibility();
        ui |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (lightStatusBar) {
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
        }
        decor.setSystemUiVisibility(ui);
    }
}
