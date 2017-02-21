package com.saiyi.aircleaner.listener;

/**
 * 文件描述：用于监听设备操作改变的监听，主要用在设备界面
 *          在控制界面，修改状态后，同时改变设备界面的状态
 * 创建作者：黎丝军
 * 创建时间：16/8/9 PM3:37
 */
public interface IDeviceChangeListener {

    //dp更新
    int DP_UPDATE = 0;
    //移除设备
    int REMOVE = 1;
    //状态改变
    int STATE_CHANGE = 2;
    //网络改变
    int NETWORK_CHANGE = 3;
    //设备信息更新
    int DEVICE_INFO_UPDATE = 4;
    /**
     * 设备改变监听器
     * @param changeType 改变类型
     * @param devId 设备Id
     * @param dpStr
     * @param state 状态值
     */
    void onDeviceChange(int changeType,String devId, String dpStr,boolean state);
}
