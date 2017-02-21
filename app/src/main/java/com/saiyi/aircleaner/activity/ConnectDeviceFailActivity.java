package com.saiyi.aircleaner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.LeftMenuBusiness;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.util.PreferencesUtils;
import com.saiyi.aircleaner.view.CircleIndicatorView;

/**
 * 文件描述：连接设备失败
 * 创建作者：黎丝军
 * 创建时间：2016/9/5 10:28
 */
public class ConnectDeviceFailActivity extends AbsBaseActivity{

    //用于显示还是隐藏正在连接的云界面
    private View mConnectingView;
    //显示进度视图
    private CircleIndicatorView mProgressView;
    //显示正在连接提示
    private TextView mConnectingHintTv;
    //显示更多的连接信息
    private TextView mConnectionMoreHintTv;
    //下一步按钮
    private Button mNextBtn;
    //取消按钮
    private Button mCancelBtn;
    //选择网络提示视图
    private View mSelectNetHintView;
    //wifi输入视图
    private View mWifiInputView;

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
        mNextBtn = getViewById(R.id.btn_net_next);
        mCancelBtn = getViewById(R.id.btn_net_cancel);
    }

    @Override
    public void initObjects() {
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.select_net_connect_fail);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);
        mConnectingView.setVisibility(View.VISIBLE);
        mSelectNetHintView.setVisibility(View.GONE);
        mWifiInputView.setVisibility(View.GONE);
        mCancelBtn.setVisibility(View.VISIBLE);
        mConnectingHintTv.setText(getString(R.string.select_net_connect_fail));
        mNextBtn.setText(getString(R.string.select_net_retry));
        mConnectionMoreHintTv.setText(getString(R.string.select_net_connect_fail_hint));
    }

    @Override
    public void setListeners() {
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConnectDeviceActivity();
            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SelectNetworkActivity.class);
                finish();
            }
        });
    }

    private void startConnectDeviceActivity() {
        final Intent intent = new Intent(this,ConnectDeviceActivity.class);
        intent.putExtra("wifiName", PreferencesUtils.getString("wifiName"));
        intent.putExtra("wifiPassword",PreferencesUtils.getString("wifiPassword"));
        startActivity(intent);
        finish();
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }
}
