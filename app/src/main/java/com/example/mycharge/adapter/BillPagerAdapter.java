package com.example.mycharge.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mycharge.fragment.BillFragment;


public class BillPagerAdapter extends FragmentPagerAdapter {
    private int mYear; // 声明当前账单所处的年份
    private String username;

    // 碎片页适配器的构造方法
    public BillPagerAdapter(FragmentManager fm, int year,String username) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mYear = year;
        this.username = username;
    }

    // 获取碎片Fragment的个数，一年有12个月
    public int getCount() {
        return 12;
    }

    // 获取指定月份的碎片Fragment
    public Fragment getItem(int position) {
        return BillFragment.newInstance(mYear*100 + (position + 1),username);
    }

    // 获得指定月份的标题文本
    public CharSequence getPageTitle(int position) {
        return (position + 1) + "月份";
    }

}
