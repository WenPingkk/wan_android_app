package com.sean.module.main.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sean.base.library.base.BaseActivity;
import com.sean.module.main.R;

/**
 * Author WenPing
 * CreateTime 2019/10/3.
 * Description:
 */
@Route(path = "/main/AboutActivity")
public class AboutActivity extends BaseActivity {
    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.about);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about;
    }
}
