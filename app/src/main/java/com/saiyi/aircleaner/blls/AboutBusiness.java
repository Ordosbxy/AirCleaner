package com.saiyi.aircleaner.blls;

import android.view.View;

import com.saiyi.aircleaner.activity.AboutActivity;

/**
 * 文件描述：关于界面业务逻辑
 * 创建作者：黎丝军
 * 创建时间：2016/8/20 9:17
 */
public class AboutBusiness extends AbsBaseBusiness<AboutActivity>{

    @Override
    public void initObject() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void actionBarLeftClick(View leftView) {
        getActivity().finish();
    }
}
