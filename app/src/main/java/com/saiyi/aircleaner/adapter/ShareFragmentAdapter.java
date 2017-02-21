package com.saiyi.aircleaner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：dp fragment 适配器
 * 创建作者：黎丝军
 * 创建时间：2016/8/30 9:16
 */
public class ShareFragmentAdapter extends FragmentPagerAdapter {

    //页碎片集合
    private List<Fragment> mPageFragment;
    //分享标题
    private List<String> mShareTitles;

    public ShareFragmentAdapter(FragmentManager fm) {
        super(fm);
        mShareTitles = new ArrayList<>();
        mPageFragment = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mPageFragment.get(position);
    }

    @Override
    public int getCount() {
        return mPageFragment.size();
    }

    /**
     * 添加碎片
     * @param fragment 碎片实例
     */
    public void putFragment(Fragment fragment,String title) {
        if(!mPageFragment.contains(fragment)) {
            mPageFragment.add(fragment);
            mShareTitles.add(title);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mShareTitles.get(position);
    }

}