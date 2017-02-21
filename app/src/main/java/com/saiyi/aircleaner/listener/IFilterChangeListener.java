package com.saiyi.aircleaner.listener;

/**
 * 文件描述：滤网提醒改变监听
 * 创建作者：黎丝军
 * 创建时间：16/8/17 AM10:00
 */
public interface IFilterChangeListener {
    /**
     * 界面改变设置方法
     * @param deviceId 设备Id
     */
    void onChange(String deviceId);
}
