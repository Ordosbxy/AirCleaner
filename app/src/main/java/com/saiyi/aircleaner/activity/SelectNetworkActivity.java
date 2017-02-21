package com.saiyi.aircleaner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.SelectNetworkBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.view.CircleIndicatorView;

/**
 * 文件描述：选择网络界面
 * 创建作者：黎丝军
 * 创建时间：16/8/3 PM2:01
 */
public class SelectNetworkActivity extends AbsBaseActivity {

    //用于显示还是隐藏正在连接的云界面
    private View mConnectingView;
    //显示进度视图
    private CircleIndicatorView mProgressView;
    //显示正在连接提示
    private TextView mConnectingHintTv;
    //显示更多的连接信息
    private TextView mConnectionMoreHintTv;
    //选择网络提示视图
    private View mSelectNetHintView;
    //wifi输入视图
    private View mWifiInputView;
    //wifi名
    private EditText mWifiNameEdt;
    //wifi密码
    private EditText mWifiPasswordEdt;
    //下一步按钮
    private Button mNextBtn;
    //取消按钮
    private Button mCancelBtn;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_select_network);
    }

    @Override
    public void findViews() {
        mConnectingView = getViewById(R.id.rl_connecting);
        mProgressView = getViewById(R.id.civ_roll);
        mConnectingHintTv = getViewById(R.id.tv_connect_hint1);
        mConnectionMoreHintTv = getViewById(R.id.tv_connect_hint2);
        mSelectNetHintView = getViewById(R.id.rl_select_net_hint);
        mWifiInputView = getViewById(R.id.ll_wifi_input);
        mWifiNameEdt = getViewById(R.id.edt_wifi_name);
        mWifiPasswordEdt = getViewById(R.id.edt_wifi_password);
        mNextBtn = getViewById(R.id.btn_net_next);
        mCancelBtn = getViewById(R.id.btn_net_cancel);
    }

    @Override
    public void initObjects() {
        mBusiness = new SelectNetworkBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.select_net_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_CLICK,mNextBtn);
        registerListener(IListener.ON_CLICK,mCancelBtn);
        registerListener(IListener.ON_ACTION_BAR_LEFT_CLICK,actionBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    public EditText getWifiPasswordEdt() {
        return mWifiPasswordEdt;
    }

    public EditText getWifiNameEdt() {
        return mWifiNameEdt;
    }

    public Button getNextBtn() {
        return mNextBtn;
    }

    public Button getCancelBtn() {
        return mCancelBtn;
    }

    public CircleIndicatorView getProgressView() {
        return mProgressView;
    }

    public TextView getConnectingHintTv() {
        return mConnectingHintTv;
    }

    public TextView getConnectionMoreHintTv() {
        return mConnectionMoreHintTv;
    }

    /**
     * 用于在按下下一步后，布局切换
     */
    public void setLayoutChangeVisible(int isVisible,int isVisibleSelectNet) {
        mConnectingView.setVisibility(isVisible);
        mSelectNetHintView.setVisibility(isVisibleSelectNet);
        mWifiInputView.setVisibility(isVisibleSelectNet);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                break;
            default:
                break;
        }
    }
}
