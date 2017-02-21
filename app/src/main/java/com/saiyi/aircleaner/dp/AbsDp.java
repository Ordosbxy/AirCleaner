package com.saiyi.aircleaner.dp;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.saiyi.aircleaner.util.LogUtils;
import com.tuya.smart.android.common.utils.NetworkUtil;
import com.tuya.smart.android.hardware.model.IControlCallback;
import com.tuya.smart.sdk.TuyaDevice;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件描述：对面dp接口简单实现的抽象类
 * 创建作者：黎丝军
 * 创建时间：2016/9/2 14:24
 */
public abstract class AbsDp implements IDP{

    //运行环境
    private Context mContext;
    //按钮当前状态
    protected boolean currentState;
    //按钮之前的状态
    protected boolean beforeState;
    //设备实例
    private TuyaDevice mDevice;
    //总开关打开关闭状态
    protected boolean switchOpen;
    //监听实现类
    private IDpListener mDpListener;

    public AbsDp(View commonBox) {
        if(commonBox != null && isNeedClick()) {
            commonBox.setOnClickListener(this);
        }
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void changeState(boolean isSwitch) {
        switchOpen = isSwitch;
    }

    @Override
    public void sendDp() {
        if(mDevice != null) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put(getDpKey(),getDpValue());
            mDevice.publishDps(JSONObject.toJSONString(hashMap), new IControlCallback() {
                @Override
                public void onError(String code, String error) {
                    onFail(code,error);
                }

                @Override
                public void onSuccess() {
                    onSendSuccess();
                }
            });
        } else {
            onFail("0","the device is null");
        }
    }

    /**
     * 发送dp成功
     */
    protected  void onSendSuccess(){
        beforeState = currentState;
        LogUtils.d("currentState=" + currentState);
    }

    /**
     * 发送dp失败
     * @param code 错误代码
     * @param error 错误原因
     */
    protected void onFail(String code, String error){
        currentState = beforeState;
        LogUtils.d("error=" + error);
    }

    @Override
    public void setDpListener(IDpListener listener) {
        mDpListener = listener;
    }

    /**
     * 该类主要给子类调用使用
     */
    protected void dpListenerHandle() {
        if(mDpListener != null) {
            mDpListener.onDp(this);
        }
    }

    @Override
    public void setDevice(TuyaDevice device) {
        mDevice = device;
    }

    @Override
    public TuyaDevice getDevice() {
        return mDevice;
    }

    @Override
    public boolean isOpen() {
        return currentState;
    }

    @Override
    public void onClick(View v) {
        if(isNetworkConnect()) {
            beforeState = currentState;
            networkConnect(v);
        } else {
            networkNoConnect(v);
        }
    }

    @Override
    public <T extends Enum<T>> T getEnum(Class<T> emCl, Object value) {
        return Enum.valueOf(emCl,String.valueOf(value));
    }

    /**
     * 网络连接正常
     * @param v 按钮视图
     */
    protected abstract void networkConnect(View v);

    /**
     * 网络没有连接
     * @param v 按钮视图
     */
    protected abstract void networkNoConnect(View v);

    /**
     * 该类主要时间网络是否正常连接
     * @return true表示连接，false表示关闭
     */
    private boolean isNetworkConnect() {
        return NetworkUtil.networkUsable(mContext);
    }

    /**
     * 用于判断是否需要注册点击监听
     * @return true表示需要，false表示不需要，
     *          默认是返回true
     */
    protected boolean isNeedClick() {
        return true;
    }

    /**
     * 处理开关是否打开ui变更
     */
    protected abstract void switchOpenHandle();

    /**
     * 获取运行环境
     * @return Context
     */
    protected Context getContext(){
        return mContext;
    }
}
