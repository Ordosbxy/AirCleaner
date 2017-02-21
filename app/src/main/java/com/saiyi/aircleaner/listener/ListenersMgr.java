package com.saiyi.aircleaner.listener;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件描述：监听管理器
 * 创建作者：黎丝军
 * 创建时间：16/8/9 AM11:38
 */
public class ListenersMgr {

    private Map<Class<?>,Object> mListeners = new HashMap<>();

    private static ListenersMgr ourInstance = new ListenersMgr();

    public static ListenersMgr getInstance() {
        return ourInstance;
    }

    private ListenersMgr() {
    }

    /**
     * 添加监听器
     * @param listener 监听实例
     */
    public void addListener(Class<?> listenerCl,Object listener) {
        if(!mListeners.containsKey(listenerCl)) {
            mListeners.put(listenerCl,listener);
        }
    }

    /**
     * 获取监听器
     * @param listenerCl 监听class
     * @param <T> 泛型
     * @return 返回具体的监听实例
     */
    public <T> T getListener(Class<?> listenerCl) {
        return (T)mListeners.get(listenerCl);
    }

    /**
     * 移除监听器
     * @param listenerCl 监听class
     */
    public void removeListener(Class<?> listenerCl) {
        if(mListeners.containsKey(listenerCl)) {
            mListeners.remove(listenerCl);
        }
    }

    /**
     * 清除内存
     */
    public void clear() {
        mListeners.clear();
        mListeners = null;
    }

}
