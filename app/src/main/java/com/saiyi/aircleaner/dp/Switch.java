package com.saiyi.aircleaner.dp;

import android.view.View;
import android.widget.CheckBox;

import com.saiyi.aircleaner.util.LogUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * 文件描述：总开关
 * 创建作者：黎丝军
 * 创建时间：2016/9/2 16:16
 */
public class Switch extends AbsBooleanDp {

    //保存其他dp实例
    private Map<String,IDP> mOtherDp;

    public Switch(CheckBox commonBox) {
        super(commonBox);
    }

    @Override
    public String getDpKey() {
        return DP_KEY_1;
    }

    @Override
    protected void networkConnect(View v) {
        super.networkConnect(v);
        otherDpStateChange();
        dpListenerHandle();
    }

    @Override
    protected void onFail(String code, String error) {
        switchOpen = currentState = beforeState;
        otherDpStateChange();
        dpListenerHandle();
        setChecked(currentState);
        LogUtils.d("error=" + error);
    }

    @Override
    public void setDpValue(Object dpValue) {
        switchOpen = beforeState = currentState = (boolean) dpValue;
        otherDpStateChange();
        dpListenerHandle();
        setChecked(currentState);
    }

    /**
     * 其他dp显示状态改变
     */
    private void otherDpStateChange() {
        if(mOtherDp != null) {
            IDP dp;
            final Iterator<String> keys =  mOtherDp.keySet().iterator();
            while (keys.hasNext()) {
                dp = mOtherDp.get(keys.next());
                dp.changeState(currentState);
            }
        }
    }

    /**
     * 添加其他Dp
     * @param otherDp 其他dp数据
     */
    public void setOtherDps(Map<String,IDP> otherDp) {
        if(otherDp != null) {
            mOtherDp = otherDp;
        }
    }
}
