package com.saiyi.aircleaner;

import android.app.Activity;
import android.os.Process;

import com.saiyi.aircleaner.http.HttpRqtMgr;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.util.PreferencesUtils;
import com.tuya.smart.sdk.TuyaUser;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 文件描述：用于app全局管理，比如退出应用程序
 * 创建作者：黎丝军
 * 创建时间：16/7/28 AM10:16
 */
public final class AppHelper {

    //用户保存单列的唯一对象
    private static final AppHelper mOurInstance = new AppHelper();
    /**
     *  用于保存启动的每一个activity,
     *  为后面退出应用程序使完成退出
     */
    private final Map<Class<?>,Activity> mActivityList = new WeakHashMap();
    //用于保存全局运行环境实例
    private CoreApp mCoreApp;

    private AppHelper() {
    }

    /**
     * 初始化CoreApp实例
     * 该方法在CoreApp里的onCreate方法中使用
     * @param app 全局app实例
     */
    public void initCoreApp(CoreApp app) {
        mCoreApp = app;
        //http请求队列初始化
        HttpRqtMgr.instance().init(app);
        //存储初始化
        PreferencesUtils.initContext(app);
    }

    /**
     * 用于获取app全局帮助类的实例
     * @return AppHelper实例
     */
    public static AppHelper instance() {
        return mOurInstance;
    }

    /**
     * 添加启动的activity
     * 该方法再BaseActivity的onCreate方法中已经使用
     * @param activity 已经启动的Activity实例
     */
    public void putActivity(Activity activity) {
        if(!mActivityList.containsKey(activity.getClass())) {
            mActivityList.put(activity.getClass(),activity);
        }
    }

    /**
     * 移除已经退出的activity
     * 该方法已经再BaseActivity里的onDestroy方法里使用
     * @param activity 已经退出的activity实例
     */
    public void removeActivity(Activity activity) {
        if(mActivityList.containsKey(activity.getClass())) {
            mActivityList.remove(activity.getClass());
        }
    }

    /**
     * 获取已经存在的Activity实例
     * @param activityCl activity的class
     * @return 返回具体的activity实例
     */
    public Activity getActivity(Class<?> activityCl) {
        if(mActivityList.containsKey(activityCl)) {
            return mActivityList.get(activityCl);
        }
        return null;
    }

    /**
     * 退出应用程序，主要是finish掉没有finish掉的activity
     * 该方法在用户连续按返回键两次时调用
     */
    public void exitApp() {
        finishAllActivity();
        Process.killProcess(Process.myPid());
    }

    /**
     * 退出登录时，所用到
     */
    public void finishAllActivity() {
        Class<?> key;
        Activity value;
        final Set<Class<?>> setKeys = mActivityList.keySet();
        final Iterator<Class<?>> it = setKeys.iterator();
        while (it.hasNext()) {
            key = it.next();
            value =  mActivityList.get(key);
            if(value != null) {
                value.finish();
            }
        }
        TuyaUser.getDeviceInstance().onDestroy();
    }

    //获取CoreApp
    public CoreApp getApplication() {
        return mCoreApp;
    }

}
