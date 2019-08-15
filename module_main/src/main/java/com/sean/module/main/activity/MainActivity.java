package com.sean.module.main.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sean.base.library.base.BaseActivity;
import com.sean.base.library.util.StatusBarUtil;
import com.sean.module.main.R;
import com.sean.module.main.fragment.HomeFragment;
import com.sean.module.main.fragment.SystemFragment;
import com.sean.module.main.fragment.ThirdFragment;
import com.sean.module.main.fragment.FourthFragment;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;
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
                    selectFragment(0);
                } else if (checkedId == R.id.rb_project) {
                    selectFragment(1);
                } else if (checkedId == R.id.rb_system) {
                    selectFragment(2);
                } else if (checkedId == R.id.rb_mine) {
                    selectFragment(3);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AndPermission
                .with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {

            }
        }).start();
    }

    private void selectFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (int i = 0; i < mFragmentList.size(); i++) {
            if (i == index) {
                transaction.show(mFragmentList.get(i));
            } else {
                transaction.hide(mFragmentList.get(i));
            }
        }
        transaction.commit();
    }

    private void createFragment() {
        mFragmentList = new ArrayList<>();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        transaction.add(R.id.fl_main_container, homeFragment);
        mFragmentList.add(homeFragment);

        SystemFragment systemFragment = new SystemFragment();
        transaction.add(R.id.fl_main_container, systemFragment);
        mFragmentList.add(systemFragment);

        ThirdFragment thirdFragment = new ThirdFragment();
        transaction.add(R.id.fl_main_container, thirdFragment);
        mFragmentList.add(thirdFragment);

        FourthFragment fourthFragment = new FourthFragment();
        transaction.add(R.id.fl_main_container, fourthFragment);
        mFragmentList.add(fourthFragment);

        transaction.commit();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void setStatusBarColor() {
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);
    }

    public void setStatusBarTranslucent(int alpha) {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, alpha, null);
    }

}
