package com.saiyi.aircleaner.blls;

import android.view.View;

import com.saiyi.aircleaner.activity.ExplainActivity;

/**
 * 文件描述：说明界面业务逻辑
 * 创建作者：黎丝军
 * 创建时间：2016/8/20 9:19
 */
public class ExplainBusiness extends AbsBaseBusiness<ExplainActivity>{

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
