package com.sean.module.main.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sean.base.library.base.BaseActivity;
import com.sean.module.main.R;
import com.sean.module.main.fragment.SystemFragment;

import java.util.List;
//com.xing.main.activity.MainActivity
/**
 * Author WenPing
 * CreateTime 2019/8/11.
 * Description:
 * 阿里路由使用 注意:1.依赖要关联,补充要ok;2.路径不要有误解
 */

@Route(path = "/main/MainActivity")
public class MainActivity extends BaseActivity {

    private RadioGroup mRadioGroup;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList;
    private RadioButton mHomeRadioButton;
    private RadioButton mMineRadioButton;
    private int currentSelectedId = R.id.rb_home;
    private SystemFragment mSystemFragment;

    @Override
    protected void initView() {
        mRadioGroup = findViewById(R.id.rg_radio_group);
        mHomeRadioButton = findViewById(R.id.rb_home);
        mMineRadioButton = findViewById(R.id.rb_mine);
    }

    @Override
    protected void initData() {
        super.initData();
        mFragmentManager = getSupportFragmentManager();
        createFragment();
        selectFragment(0);
        mHomeRadioButton.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //如果是当前已经选中的 tab,不切换
                if (checkedId == currentSelectedId) {
                    return;
                }
                if (checkedId == R.id.rb_home) {

                } else if (checkedId == R.id.rb_project) {

                } else if (checkedId == R.id.rb_system) {

                } else if (checkedId == R.id.rb_mine) {

                }
            }
        });




    }

    private void selectFragment(int i) {

    }

    private void createFragment() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
}
