package com.saiyi.aircleaner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：页视图适配器
 * 创建作者：黎丝军
 * 创建时间：2016/8/30 11:15
 */
public class DpViewAdapter extends PagerAdapter {

    private List<View> mViews;

    public DpViewAdapter() {
        mViews = new ArrayList<>();
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    /**
     * 添加页视图
     * @param view 视图
     */
    public void addView(View view) {
        if(!mViews.contains(view)) {
            mViews.add(view);
        }
    }
}
