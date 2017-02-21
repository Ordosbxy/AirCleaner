package com.saiyi.aircleaner.activity;

import android.os.Bundle;
import android.widget.Button;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.DiscoverDeviceBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;

/**
 * 文件描述：发现设备界面
 * 创建作者：黎丝军
 * 创建时间：16/8/3 PM12:32
 */
public class DiscoverDeviceActivity extends AbsBaseActivity {

    //连接按钮
    private Button mConnectBtn;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_discover_device);
    }

    @Override
    public void findViews() {
        mConnectBtn = getViewById(R.id.btn_discover_connect);
    }

    @Override
    public void initObjects() {
        mBusiness = new DiscoverDeviceBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.discover_device_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonDrawable(R.mipmap.ic_back,0,0,0);
        actionBar.setLeftBtnDrawablePadding(-13);
        actionBar.setLeftButtonText(R.string.discover_device_back_cancel);
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_CLICK,mConnectBtn);
        registerListener(IListener.ON_ACTION_BAR_LEFT_CLICK,actionBar);
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }
}
