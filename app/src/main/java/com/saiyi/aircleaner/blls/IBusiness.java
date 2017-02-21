package com.saiyi.aircleaner.blls;

import com.saiyi.aircleaner.listener.IListener;

/**
 * 文件描述:业务类接口,该业务类继承于监听器接口，
 *         主要是方便点击和业务一起开发方便
 * 创建作者:黎丝军
 * 创建时间:16/7/28
 */
public interface IBusiness<T> extends IListener{

    /**
     * 设置对应界面类
     * @param t 对应的界面类
     */
    void setT(T t);

    /**
     * 如果对应的界面是Fragment
     * 那么就使用该类方法
     * @return Fragment实例
     */
    T getFragment();

    /**
     * 如果对应的界面是activity
     * 那么就是用该方法
     * @return activity实例
     */
    T getActivity();

    /**
     * 初始化基本数据对象
     */
    void initObject();

    /**
     * 初始化数据方法
     */
    void initData();

    /**
     * 生命周期方法和界面类里的onResume对应
     */
    void onResume();

    /**
     * 生命周期方法和界面里的onPause对应
     */
    void onPause();

    /**
     * 生命周期方法和界面里的onDestroy对应
     */
    void onDestroy();
}
