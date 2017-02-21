package com.saiyi.aircleaner.listener;

import android.view.View;

/**
 * 文件描述：监听注册接口
 * 创建作者：黎丝军
 * 创建时间：16/7/28
 */
public interface IListener {
    //点击事件
    int ON_CLICK = 0x111110;
    //长按事件
    int ON_LONG_PRESS = 0x1111101;
    //触摸事件
    int ON_TOUCH = 0x1111102;
    //选中事件
    int ON_SELECT = 0x1111103;
    //勾选事件
    int ON_CHECK = 0x1111104;
    //每一项的点击事件
    int ON_ITEM_CLICK = 0x1111105;
    //每一项选择事件
    int ON_ITEM_SELECT = 0x1111106;
    //头部标签左边按钮点击事件
    int ON_ACTION_BAR_LEFT_CLICK = 0x1111108;
    //头部标签右边按钮点击事件
    int ON_ACTION_BAR_RIGHT_CLICK = 0x1111109;
    //其他事件
    int ON_OTHER = 0x1111110;

    /**
     * 注册监听事件
     * @param eventType 事件类型
     * @param registerView 注册视图
     * @param registerObj 注册对象
     */
     void register(int eventType, View registerView, Object registerObj);

}
