package com.saiyi.aircleaner.blls;

import android.text.TextUtils;
import android.view.View;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.ConnectDeviceActivity;
import com.saiyi.aircleaner.activity.ConnectDeviceFailActivity;
import com.saiyi.aircleaner.activity.SelectNetworkActivity;
import com.saiyi.aircleaner.listener.IActivateListener;
import com.saiyi.aircleaner.listener.ListenersMgr;
import com.tuya.smart.android.device.api.response.GwDevResp;
import com.tuya.smart.sdk.TuyaActivator;
import com.tuya.smart.sdk.api.ITuyaActivator;
import com.tuya.smart.sdk.api.ITuyaActivatorGetToken;
import com.tuya.smart.sdk.api.ITuyaSmartActivatorListener;
import com.tuya.smart.sdk.builder.ActivatorBuilder;
import com.tuya.smart.sdk.enums.ActivatorModelEnum;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 文件描述：连接设备业务逻辑
 * 创建作者：黎丝军
 * 创建时间：16/8/5 AM10:40
 */
public class ConnectDeviceBusiness extends AbsBaseBusiness<ConnectDeviceActivity>
        implements ITuyaSmartActivatorListener{

    //用于进度
    private int sumProgress;
    //判断是否激活成功
    private boolean isActivatorSuccess = false;
    //设备激活器
    private ITuyaActivator mTuyaActivator;
    //构建网络配置信息
    private ActivatorBuilder mBuilder;
    //进度定时器
    private Timer mProgressTimer;
    //用于判断是否已经获取token
    private boolean isGainToken = false;
    //进度任务
    private TimerTask mProgressTask = new TimerTask() {

        public void run() {
            if(sumProgress < 40) {
                sumProgress += new Random().nextInt(10);
            } else if(sumProgress >= 40 && sumProgress < 80) {
                sumProgress += new Random().nextInt(5);
            } else if(sumProgress >= 80 && sumProgress < 99){
                sumProgress += new Random().nextInt(2);
            } else {
            }
            getActivity().getProgressBar().setProgressValue(sumProgress);
        }
    };

    @Override
    public void initObject() {
        mProgressTimer = new Timer("progress");
        mBuilder = new ActivatorBuilder();
    }

    @Override
    public void initData() {
        TuyaActivator.getInstance().getActivatorToken(new ITuyaActivatorGetToken() {
            @Override
            public void onSuccess(String s) {
                if(!isGainToken) {
                    isGainToken = true;
                    configNetwork(s);
                }
            }

            @Override
            public void onFailure(String s, String s1) {
                stopTimer();
                finishActivity(true);
            }
        });

    }

    /**
     * 配置网络
     * @param token token值
     */
    private void configNetwork(String token) {
        final String wifiName = getActivity().getIntent().getStringExtra("wifiName");
        final String wifiPassword = getActivity().getIntent().getStringExtra("wifiPassword");
        mBuilder.setSsid(wifiName);
        mBuilder.setPassword(wifiPassword);
        mBuilder.setContext(getContext());
        mBuilder.setTimeOut(100);
        mBuilder.setActivatorModel(ActivatorModelEnum.TY_EZ);
        if(token != null) mBuilder.setToken(token);
        mBuilder.setListener(this);
        mTuyaActivator = TuyaActivator.getInstance().newActivator(mBuilder);
        mTuyaActivator.start();
        mProgressTimer.schedule(mProgressTask,0,1000);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_connect_cancel:
                finishActivity(false);
                break;
            default:
                break;
        }
    }

    @Override
    protected void actionBarLeftClick(View leftView) {
        getActivity().startActivity(SelectNetworkActivity.class);
        finishActivity(false);
    }

    //结束配置网络的activity
    public void finishActivity(boolean cancel) {
        if (!isActivatorSuccess && mTuyaActivator != null) mTuyaActivator.stop();
        getActivity().getProgressBar().stopRoll();
        if(cancel) {
            getActivity().startActivity(ConnectDeviceFailActivity.class);
        }
        getActivity().finish();
    }

    private void stopTimer() {
        if(mProgressTimer != null) {
            mProgressTask.cancel();
            mProgressTimer.cancel();
            mProgressTimer = null;
        }
    }

    @Override
    public void onError(String s, String s1) {
        stopTimer();
        finishActivity(true);
    }

    @Override
    public void onActiveSuccess(GwDevResp gwDevResp) {
        sumProgress = 100;
        isActivatorSuccess = true;
        stopTimer();
        getActivity().getCancelBtn().setVisibility(View.GONE);
        getActivity().getConnectMoreHintTv().setVisibility(View.GONE);
        getActivity().getConnectHintTv().setText(R.string.connect_device_hint1_success);
        getActivity().getProgressBar().setProgressValue(sumProgress);
        IActivateListener listener = ListenersMgr.getInstance().getListener(IActivateListener.class);
        if(listener != null) {
            listener.onUpdateData(gwDevResp);
        }
        finishActivity(false);
    }

    @Override
    public void onStep(String s, Object o) {
        if(TextUtils.equals(s,"device_find")) {
            getActivity().getConnectMoreHintTv().setText(R.string.connect_device_find_device);
        } else {
            getActivity().getConnectMoreHintTv().setText(R.string.connect_device_bind_device);
        }
    }

    @Override
    public void onDestroy() {
        if(mTuyaActivator != null) {
            mTuyaActivator.stop();
            mTuyaActivator.onDestroy();
        }
    }

}
