package com.saiyi.aircleaner.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.AddShareBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;

/**
 * 文件描述：添加分享设备的界面
 * 创建作者：黎丝军
 * 创建时间：2016/8/22 8:44
 */
public class AddShareActivity extends AbsBaseActivity{

    //分享昵称
    private EditText mShareNameEdt;
    //分享手机号
    private EditText mSharePhoneEdt;
    //确定分享按钮
    private Button mSureBtn;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_add_share);
    }

    @Override
    public void findViews() {
        mShareNameEdt = getViewById(R.id.edt_share_name);
        mSharePhoneEdt = getViewById(R.id.edt_share_phone);
        mSureBtn = getViewById(R.id.btn_share_device);
    }

    @Override
    public void initObjects() {
        mBusiness = new AddShareBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.add_share_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_CLICK,mSureBtn);
        registerListener(IListener.ON_ACTION_BAR_LEFT_CLICK,actionBar);
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    public EditText getShareNameEdt() {
        return mShareNameEdt;
    }

    public EditText getSharePhoneEdt() {
        return mSharePhoneEdt;
    }
}
