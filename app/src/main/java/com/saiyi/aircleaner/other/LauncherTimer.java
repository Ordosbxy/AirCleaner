package com.saiyi.aircleaner.other;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.saiyi.aircleaner.AppHelper;
import com.saiyi.aircleaner.util.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 文件描述：启动页定时器和倒计时器
 * 创建作者：黎丝军
 * 创建时间：16/7/28 PM3:45
 */
public class LauncherTimer {

    //循环周期
    private static final int PERIOD = 1 * 1000;
    //停止值
    private static final int STOP_VALUE = -1;
    //定时器
    private Timer mTimer;
    //定时任务
    private TimerTask mTimerTask;
    //启动监听器
    private OnLauncherListener mListener;
    //倒计时监听器
    private OnCountDownListener mDownListener;
    //用于线程之间传递信息
    private Message mMessage;
    //总时间，默认是十秒
    private int mAllTime;
    //判断是否是倒计
    private boolean isDownTime = false;
    //需要被finish掉的activity
    private Class<?> mFinishActivityCl;
    //将子线转到Ui线程
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            LogUtils.d("handle message:" + msg.arg1);
            if(isDownTime) {
                if(mDownListener != null) {
                    mDownListener.updateTime(msg.what,STOP_VALUE);
                }
                if(msg.what <= -1) {
                    cancelTimer();
                }
            } else {
                delayResult();
            }
        }
    };

    //是否取消倒计时
    private boolean isCancel = false;

    /**
     * 延迟启动处理结果
     */
    private void delayResult() {
        if(mListener != null) {
            if(mListener.isLogin()) {
                mListener.login();
            } else {
                mListener.unLogin();
            }
            final Activity activity = AppHelper.instance().getActivity(mFinishActivityCl);
            if(activity != null) activity.finish();
        }
    }

    public LauncherTimer() {
        this(null);
    }

    public LauncherTimer(Class<?> activityCl) {
        mFinishActivityCl = activityCl;
    }

    /**
     * 获取定时器任务
     * @return 任务
     */
    private TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                if(isDownTime) {
                    int time = mAllTime;
                    for(;time >= -1 && !isCancel;time--) {
                        mMessage = mHandler.obtainMessage();
                        mMessage.what = time;
                        mMessage.sendToTarget();
                        try{
                            Thread.sleep(PERIOD);
                        } catch (Exception e) {
                        }
                    }
                } else {
                    mMessage = mHandler.obtainMessage();
                    mMessage.what = 1;
                    mMessage.sendToTarget();
                }
                LogUtils.d("定时器执行");
            }
        };
    }

    /**
     * 延时启动的方法，该方法必须要调用，否则无效
     * @param delayTime 延时毫秒时间，默认是2秒
     */
    public void delayLauncher(int delayTime) {
       delayLauncher(delayTime,null);
    }

    /**
     * 延时启动的方法，该方法必须要调用，否则无效
     * @param delayTime 延时毫秒时间，默认是2秒
     */
    public void delayLauncher(int delayTime,OnLauncherListener listener) {
        isDownTime = false;
        mTimer = new Timer();
        mTimerTask = getTimerTask();
        if(listener != null) mListener = listener;
        mTimer.schedule(mTimerTask,delayTime == 0 ? 2000:delayTime);
    }

    /**
     * 倒计时
     * @param allTime 倒计时时间，如果是0则默认是10秒
     * @param delayTime 延迟启动时间
     */
    public void countDown(int allTime,int delayTime) {
       countDown(allTime,delayTime,null);
    }

    /**
     * 倒计时
     * @param allTime 倒计时时间，如果是0则默认是10秒
     * @param delayTime 延迟启动时间
     */
    public void countDown(int allTime,int delayTime,OnCountDownListener listener) {
        isDownTime = true;
        isCancel = false;
        mTimer = new Timer();
        mTimerTask = getTimerTask();
        mAllTime = allTime == 0 ? 10:allTime;
        if(listener != null)mDownListener = listener;
        mTimer.schedule(mTimerTask,delayTime);
    }

    /**
     * 取消定时器
     */
    public void cancelTimer() {
        isCancel = true;
        if(mTimerTask != null && mTimer != null) {
            mTimerTask.cancel();
            mTimer.cancel();
            mTimer = null;
            mTimerTask = null;
        }
    }

    /**
     * 设置监听器
     * @param listener 监听实例
     */
    public void setOnLauncherListener(OnLauncherListener listener) {
        mListener = listener;
    }

    /**
     * 倒计时器监听
     * @param listener 监听实例
     */
    public void setOnCountDownListener(OnCountDownListener listener) {
        mDownListener = listener;
    }

    /**
     * 启动监听器
     */
    public interface OnLauncherListener {

        /**
         * 在这里写用户是否登录的判断
         * @return false表示没有登录，true表示已经登录
         */
        boolean isLogin();

        /**
         * 用户没有登录
         */
        void unLogin();

        /**
         * 用户已经登录
         */
        void login();
    }

    /**
     * 倒计时监听器
     */
    public interface OnCountDownListener {
        /**
         * 更新时间
         * @param time 时间
         * @param stopValue 终点值
         */
        void updateTime(int time,int stopValue);
    }


}
