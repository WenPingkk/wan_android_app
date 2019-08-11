package com.sean.module.usercenter.activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sean.base.library.base.BaseActivity;
import com.sean.module.usercenter.R;

/**
 * Author WenPing
 * CreateTime 2019/8/11.
 * Description:启动页
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gotoMainActivity();
    }

    private void gotoMainActivity() {
        ARouter.getInstance()
                .build("/main/MainActivity")
                .navigation(this);
        finish();
    }
}
