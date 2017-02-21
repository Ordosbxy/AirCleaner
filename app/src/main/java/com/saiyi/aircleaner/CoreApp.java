package com.saiyi.aircleaner;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;
import com.saiyi.aircleaner.util.LogUtils;
import com.tuya.smart.android.base.TuyaSmartSdk;

/**
 * 文件描述：应用首次运行的入口类
 * 创建作者：黎丝军
 * 创建时间：16/7/28 AM9:09
 */
public class CoreApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //是否处在debug
        LogUtils.isDebug = true;
        //设置涂鸦不打印日志
        TuyaSmartSdk.DEBUG = false;
        //初始化涂鸦sdk
        TuyaSmartSdk.init(this);
        //初始化app帮助类
        AppHelper.instance().initCoreApp(this);
        //蒲光英崩溃日志上传注册
        PgyCrashManager.register(this);
    }
}
