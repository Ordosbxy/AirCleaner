package com.saiyi.aircleaner.dp;

import android.content.Context;
import android.view.View;

import com.tuya.smart.sdk.TuyaDevice;

/**
 * 文件描述：IDP监听器
 * 创建作者：黎丝军
 * 创建时间：2016/9/2 13:51
 */
public interface IDP extends View.OnClickListener{

    //功能键1
    String DP_KEY_1 = "1";
    //功能键2
    String DP_KEY_2 = "2";
    //功能键3
    String DP_KEY_3 = "3";
    //功能键4
    String DP_KEY_4 = "4";
    //功能键5
    String DP_KEY_5 = "5";
    //功能键6
    String DP_KEY_6 = "6";
    //功能键7
    String DP_KEY_7 = "7";
    //功能键8
    String DP_KEY_8 = "8";
    //功能键9
    String DP_KEY_9 = "9";
    //功能键10
    String DP_KEY_10 = "10";
    //功能键11
    String DP_KEY_11 = "11";
    //功能键12
    String DP_KEY_12 = "12";
    //功能键13
    String DP_KEY_13 = "13";
    //功能键14
    String DP_KEY_14 = "14";
    //功能键15
    String DP_KEY_15 = "15";

    /**
     * 获取dp键
     * @return 1-100以为的数字字符串
     */
    String getDpKey();

    /**
     * 获取dp值
     * @return 任何类型
     */
    Object getDpValue();

    /**
     * 设置dp值，主要用来初始化
     * 或者接收命令执行后的回馈信息
     * @param dpValue dp值
     */
    void setDpValue(Object dpValue);

    /**
     * 按钮状态改变，
     * 比如总开关开或者关时操控其他dp按钮状态改变
     * @param isSwitch 开关是否开关
     */
    void changeState(boolean isSwitch);

    /**
     * 发送dp数据
     */
    void sendDp();

    /**
     * 设置dp执行监听器
     * @param listener 监听实例
     */
    void setDpListener(IDpListener listener);

    /**
     * 设置设备实体类
     * @param device 设备实例
     */
    void setDevice(TuyaDevice device);

    /**
     * 设置运行环境
     * @param context 运行环境实例
     */
    void setContext(Context context);

    /**
     * 获取设备实例
     * @return 设备实例
     */
    TuyaDevice getDevice();

    /**
     * 判断按钮是否打开还是关闭
     * @return true为打开，false为关闭
     */
    boolean isOpen();

    /**
     * 获取枚举类型
     * @param emCl 枚举cl
     * @param value 字符串
     * @return Enum
     */
    <T extends Enum<T>> T getEnum(Class<T> emCl, Object value);

    /**
     * 该监听类主要是为了后面扩展开发
     * 子类自己去实现在哪儿了需要监听
     */
    interface IDpListener {
        /**
         * 监听实现方法
         * @param dp 实例
         */
        void onDp(IDP dp);
    }
}
