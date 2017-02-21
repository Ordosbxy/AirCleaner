package com.saiyi.aircleaner.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.ConnectDeviceBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.view.CircleProgressBar;

/**
 * 文件描述：连接设备
 * 创建作者：黎丝军
 * 创建时间：16/8/5 AM10:38
 */
public class ConnectDeviceActivity extends AbsBaseActivity {

    //圆形进度条
    private CircleProgressBar mProgressBar;
    //连接提示
    private TextView mConnectHintTv;
    //连接更多提示
    private TextView mConnectMoreHintTv;
    //取消按钮
    private Button mCancelBtn;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_connect_device);
    }

    @Override
    public void findViews() {
        mProgressBar = getViewById(R.id.cpb_progress);
        mConnectHintTv = getViewById(R.id.tv_connect_device_hint1);
        mConnectMoreHintTv = getViewById(R.id.tv_connect_device_hint2);
        mCancelBtn = getViewById(R.id.btn_connect_cancel);
    }

    @Override
    public void initObjects() {
        mBusiness = new ConnectDeviceBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.connect_device_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_CLICK,mCancelBtn);
        registerListener(IListener.ON_ACTION_BAR_LEFT_CLICK,actionBar);
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    public TextView getConnectMoreHintTv() {
        return mConnectMoreHintTv;
    }

    public TextView getConnectHintTv() {
        return mConnectHintTv;
    }

    public CircleProgressBar getProgressBar() {
        return mProgressBar;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ((ConnectDeviceBusiness)mBusiness).finishActivity(false);
        }
        return true;
    }

    public Button getCancelBtn() {
        return mCancelBtn;
    }
}
