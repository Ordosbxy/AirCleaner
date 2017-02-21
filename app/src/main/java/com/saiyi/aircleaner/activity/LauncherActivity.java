package com.saiyi.aircleaner.activity;


import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.other.LauncherTimer;
import com.saiyi.aircleaner.util.LogUtils;
import com.tuya.smart.android.base.TuyaSmartSdk;
import com.tuya.smart.android.network.TuyaSmartNetWork;
import com.tuya.smart.sdk.TuyaSdk;
import com.tuya.smart.sdk.TuyaUser;
import com.tuya.smart.sdk.api.INeedLoginListener;

/**
 * 文件描述：启动页面，程序启动的第一个界面
 * 创建作者：黎丝军
 * 创建时间：16/7/28 AM10:16
 */
public class LauncherActivity extends AbsBaseActivity
        implements LauncherTimer.OnLauncherListener{
    //用于延时启动主程序
    private LauncherTimer mLauncherTimer;
    //判断是否需要登录
    private boolean isNeed = false;

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_launcher);
    }

    @Override
    public void findViews() {
        mLauncherTimer = new LauncherTimer();
    }

    @Override
    public void initObjects() {
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        LogUtils.d("启动页初始化");
        //涂鸦智能初始化网络请求
        TuyaSmartNetWork.initialize(this, TuyaSmartSdk.getAppkey(), TuyaSmartSdk.getAppSecret(),"android");
        //注册是否需要登录监听
        TuyaSdk.setOnNeedLoginListener(mNeedLoginListener);
        //启动延时
        mLauncherTimer.delayLauncher(0);
        LogUtils.d("开始延迟启动");
    }

    //监听登录是否失效
    private INeedLoginListener mNeedLoginListener = new INeedLoginListener() {
        @Override
        public void onNeedLogin(Context context) {
            isNeed = true;
            LogUtils.d("是否启动了登录 isNeed=" + isNeed);
            startActivity(LoginActivity.class);
            if(mLauncherTimer != null) mLauncherTimer.cancelTimer();
            finish();
        }
    };

    @Override
    public void setListeners() {
        mLauncherTimer.setOnLauncherListener(this);
    }

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public void unLogin() {
        LogUtils.d("isNeed=" + isNeed);
        if(!isNeed) startActivity(LoginActivity.class);
    }

    @Override
    public void login() {
        startActivity(DeviceActivity.class);
    }
}
