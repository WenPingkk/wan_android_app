package com.sean.module.main.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sean.base.library.base.BaseActivity;
import com.sean.module.main.R;
//com.xing.main.activity.MainActivity
/**
 * Author WenPing
 * CreateTime 2019/8/11.
 * Description:
 * 阿里路由使用 注意:1.依赖要关联,补充要ok;2.路径不要有误解
 */

@Route(path = "/main/MainActivity")
public class MainActivity extends BaseActivity {
    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
}
