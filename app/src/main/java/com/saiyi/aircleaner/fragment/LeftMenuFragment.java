package com.saiyi.aircleaner.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.LeftMenuBusiness;
import com.saiyi.aircleaner.listener.IListener;

/**
 * 文件描述：左菜单碎片
 * 创建作者：黎丝军
 * 创建时间：16/8/5 PM1:50
 */
public class LeftMenuFragment extends BaseFragment {

    //显示用户名
    private TextView mUserNameTv;
    //显示用户手机
    private TextView mUserPhoneTv;
    //首页
    private TextView mHomeBtn;
    //说明
    private TextView mExplainBtn;
    //设备分享
    private TextView mShareDeviceBtn;
    //关于
    private TextView mAboutBtn;
    //退出登录
    private TextView mQuitBtn;

    @Override
    public void onContentView() {
        setContentView(R.layout.fragment_left_menu);
    }

    @Override
    public void findViews() {
        mUserNameTv = getViewById(R.id.tv_user_name);
        mUserPhoneTv = getViewById(R.id.tv_user_phone);
        mHomeBtn = getViewById(R.id.tv_home);
        mExplainBtn = getViewById(R.id.tv_reader);
        mAboutBtn = getViewById(R.id.tv_about);
        mQuitBtn = getViewById(R.id.tv_quit);
        mShareDeviceBtn = getViewById(R.id.tv_share);
    }

    @Override
    public void initObjects() {
        mBusiness = new LeftMenuBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_CLICK, mHomeBtn);
        registerListener(IListener.ON_CLICK, mExplainBtn);
        registerListener(IListener.ON_CLICK, mAboutBtn);
        registerListener(IListener.ON_CLICK, mQuitBtn);
        registerListener(IListener.ON_CLICK, mShareDeviceBtn);
    }

    public TextView getUserPhoneTv() {
        return mUserPhoneTv;
    }

    public TextView getUserNameTv() {
        return mUserNameTv;
    }

}