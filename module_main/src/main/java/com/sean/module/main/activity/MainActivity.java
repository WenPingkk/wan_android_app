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
import com.sean.module.main.fragment.MineFragment;
import com.sean.module.main.fragment.ProjectFragment;
import com.sean.module.main.fragment.SystemFragment;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;
import java.util.List;
/**
 * Author WenPing
 * CreateTime 2019/8/11.
 * Description:
 * 阿里路由使用 注意:1.依赖要关联,补充要ok;2.路径不要有误解
 */

@Route(path = "/main/MainActivity")
public class MainActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private RadioButton homeRadioButton;
    private int currentSelectedId = R.id.rb_home;
    private SystemFragment systemFragment;
    private ProjectFragment projectFragment;
    private MineFragment mineFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        radioGroup = findViewById(R.id.rg_radio_group);
        homeRadioButton = findViewById(R.id.rb_home);
    }

    /**
     * 初始化fragment
     * 添加选择监听
     */
    @Override
    protected void initData() {
        super.initData();
        fragmentManager = getSupportFragmentManager();
        createFragment();
        selectFragment(0);
        homeRadioButton.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //如果是当前已经选中的 tab,不切换
                if (checkedId == currentSelectedId) {
                    return;
                }
                currentSelectedId = checkedId;
                if (checkedId == R.id.rb_home) {
                    selectFragment(0);
                } else if (checkedId == R.id.rb_project) {
                    selectFragment(1);
//                    projectFragment.setStatusBarColor(Color.WHITE);
                    StatusBarUtil.setLightMode(MainActivity.this);
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
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == index) {
                transaction.show(fragmentList.get(i));
            } else {
                transaction.hide(fragmentList.get(i));
            }
        }
        transaction.commit();
    }

    private void createFragment() {
        fragmentList = new ArrayList<>();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        transaction.add(R.id.fl_main_container, homeFragment);
        fragmentList.add(homeFragment);

        projectFragment = new ProjectFragment();
        transaction.add(R.id.fl_main_container, projectFragment);
        fragmentList.add(projectFragment);

        systemFragment = new SystemFragment();
        transaction.add(R.id.fl_main_container, systemFragment);
        fragmentList.add(systemFragment);


        mineFragment = new MineFragment();
        transaction.add(R.id.fl_main_container, mineFragment);
        fragmentList.add(mineFragment);

        transaction.commit();

    }

//    @Override
//    public void setStatusBarColor() {
//        StatusBarUtil.setTransparentForImageViewInFragment(this, null);
//    }

    public void setStatusBarTranslucent(int alpha) {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, alpha, null);
    }

}
