package com.saiyi.aircleaner.dp;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.tuya.smart.sdk.TuyaDevice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 文件描述：dp管理器，包含dp初始化，dp更新，dp扩展之类的
 *           使用该类时首先需要调用addDp方法添加开关dp，顺序反了添加其他dp无效
 * 创建作者：黎丝军
 * 创建时间：2016/9/1 17:01
 */
public class DpManager {
    //开关
    private Switch mSwitch;
    //运行环境
    private Context mContext;
    //设备
    private TuyaDevice mDevice;
    //保存其他dp实例
    private Map<String,IDP> mOtherDp;
    //保存单列模式
    private static DpManager ourInstance;
    //获取单列实例
    public static synchronized DpManager getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance = new DpManager(context);
        }
        return ourInstance;
    }

    private DpManager(Context context) {
        mContext = context;
        mOtherDp = new HashMap<>();
    }

    /**
     * 设置设备
     * @param device 设备实例
     */
    public void setDevice(TuyaDevice device) {
        mDevice = device;
    }

    /**
     * 添加其他dp
     * @param dp dp实例
     */
    public void addDp(IDP dp) {
       addDp(dp,null);
    }

    /**
     * 添加其他dp
     * @param dp dp实例
     */
    public void addDp(IDP dp, IDP.IDpListener listener) {
        dp.setContext(mContext);
        dp.setDevice(mDevice);
        dp.setDpListener(listener);
        if(TextUtils.equals(dp.getDpKey(),IDP.DP_KEY_1)) {
            mSwitch = (Switch)dp;
        } else {
            if(mSwitch != null) {
                if(!mOtherDp.containsKey(dp.getDpKey())) {
                    mOtherDp.put(dp.getDpKey(),dp);
                }
                mSwitch.setOtherDps(mOtherDp);
            }
        }
    }

    /**
     * 对某一个dp执行监听器
     * @param dpKey dp对应键
     * @param listener 监听实例
     */
    public void setDpListener(String dpKey, IDP.IDpListener listener) {
        final IDP dp = getDp(dpKey);
        if(dp != null) {
            dp.setDpListener(listener);
        }
    }

    /**
     * 更新dp数据
     * @param dpStr 数据
     */
    public void dpUpdate(String dpStr) {
        IDP dp;
        String key;
        Object value;
        Object switchValue = mSwitch.isOpen();
        final JSONObject jsonObject = JSONObject.parseObject(dpStr);
        final Set<String> ketSet = jsonObject.keySet();
        final Iterator<String> iterator = ketSet.iterator();
        while (iterator.hasNext()) {
            key = iterator.next();
            value = jsonObject.get(key);
            if(key == mSwitch.DP_KEY_1) {
                switchValue = value;
            } else {
                dp = mOtherDp.get(key);
                if(dp != null) {
                    dp.setDpValue(value);
                }
            }
        }
        mSwitch.setDpValue(switchValue);
    }

    /**
     * 更新dp数据
     * @param dpData 数据
     */
    public void dpUpdate(Map<String,Object> dpData) {
        IDP dp;
        final Iterator<String> keys = mOtherDp.keySet().iterator();
        while (keys.hasNext()) {
            dp = mOtherDp.get(keys.next());
            dp.setDpValue(dpData.get(dp.getDpKey()));
        }
        mSwitch.setDpValue(dpData.get(mSwitch.getDpKey()));
    }

    /**
     * 获取dp实例
     * @param key 键
     * @return 对应的dp实例
     */
    public <T extends IDP> T getDp(String key) {
        return (T)mOtherDp.get(key);
    }

    /**
     * 在退出控件界面时调用
     */
    public void clear() {
        mOtherDp.clear();
        mSwitch = null;
        mDevice = null;
    }

}
