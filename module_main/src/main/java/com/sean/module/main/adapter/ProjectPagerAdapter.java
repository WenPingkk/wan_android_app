package com.sean.module.main.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.sean.module.main.bean.ProjectPageItem;

import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/9/15.
 * Description:
 */
public class ProjectPagerAdapter extends FragmentPagerAdapter {

    private List<ProjectPageItem> mProjectPageItems;

    public ProjectPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 设置数据和 更新数据一次
     * @param data
     */
    public void setPages(List<ProjectPageItem> data) {
        this.mProjectPageItems = data;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return mProjectPageItems.get(i).getFragment();
    }

    @Override
    public int getCount() {
        return mProjectPageItems == null ? 0 : mProjectPageItems.size();
    }

    /**
     * 获取标题
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mProjectPageItems.get(position).getName();
    }

    /**
     * 重写 destroyItem 方法，不调用父类的destroy方法；否则页面没了
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
