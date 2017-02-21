package com.saiyi.aircleaner.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.DeviceBusiness;
import com.saiyi.aircleaner.fragment.LeftMenuFragment;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.view.InfoHintDialog;

/**
 * 文件描述：我的设备界面
 * 创建作者：黎丝军
 * 创建时间：16/7/28 PM4:12
 */
public class DeviceActivity extends AbsBaseActivity {

    //用于装载设备
    private RecyclerView mDeviceListRv;
    //当没有设备的时候显示的视图
    private View mNoDeviceView;
    //用于显示设备的时候显示
    private View mHaveDeviceView;
    //用于提示
    private InfoHintDialog mHintDialog;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_my_device);
    }

    @Override
    public void findViews() {
        mNoDeviceView = getViewById(R.id.tv_no_device);
        mDeviceListRv = getViewById(R.id.rv_device);
        mHaveDeviceView = getViewById(R.id.ll_have_device);
    }

    @Override
    public void initObjects() {
        mHintDialog = new InfoHintDialog(this);
        mBusiness = new DeviceBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.device_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_device_head,25,25);
        actionBar.setRightButtonBackground(R.mipmap.ic_add,25,25);
        actionBar.setRightButtonTextColor(Color.WHITE);
        //初始化列表
        mDeviceListRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_OTHER,slideMgr.getSlideMenu());
        registerListener(IListener.ON_ACTION_BAR_LEFT_CLICK,actionBar);
        registerListener(IListener.ON_ACTION_BAR_RIGHT_CLICK,actionBar);
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    public RecyclerView getDeviceListRv() {
        return mDeviceListRv;
    }

    /**
     * 设置设备视图可见性
     * @param noVisible 没有设备
     * @param haveVisible 有设备
     */
    public void setDeviceViewVisible(int noVisible,int haveVisible) {
        mNoDeviceView.setVisibility(noVisible);
        mHaveDeviceView.setVisibility(haveVisible);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ((DeviceBusiness)mBusiness).exitBackApp();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case DeviceBusiness.SCAN_REQUEST_CODE:
                if(resultCode == RESULT_OK) {
                    ((DeviceBusiness)mBusiness).addDevice(data.getStringExtra("device"));
                } else if(resultCode == DeviceBusiness.RESULT_FAIL) {
                    mHintDialog.setTitle(R.string.two_code_title);
                    mHintDialog.setContentText(R.string.device_add_device_fail);
                    mHintDialog.setCancelBtnVisibility(View.GONE);
                    mHintDialog.show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isSlideMenu() {
        return true;
    }

    @Override
    public Fragment getSlideMenuFragment() {
        return new LeftMenuFragment();
    }

    public Button getRightBtn() {
        return actionBar.getRightBtn();
    }

    public Button getLeftBtn() {
        return actionBar.getLeftBtn();
    }
}
