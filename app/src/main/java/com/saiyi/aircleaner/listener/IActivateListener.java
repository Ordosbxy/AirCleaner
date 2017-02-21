package com.saiyi.aircleaner.listener;

import com.tuya.smart.android.device.api.response.GwDevResp;

/**
 * 文件描述：设备激活监听器
 * 创建作者：黎丝军
 * 创建时间：16/8/9 AM11:24
 */
public interface IActivateListener {
    /**
     * 更新数据列表的方法
     * @param gwDevResp 设备信息
     */
    void onUpdateData(GwDevResp gwDevResp);
}
