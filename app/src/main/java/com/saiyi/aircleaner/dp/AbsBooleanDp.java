package com.saiyi.aircleaner.dp;

import android.view.View;
import android.widget.CheckBox;

/**
 * 文件描述：按钮只是两种状态类型的dp
 * 创建作者：黎丝军
 * 创建时间：2016/9/2 15:22
 */
public abstract class AbsBooleanDp extends AbsDp{

    //按钮
    private CheckBox mCommonBox;

    public AbsBooleanDp(CheckBox commonBox) {
        super(commonBox);
        mCommonBox = commonBox;
    }

    @Override
    public void changeState(boolean isSwitch) {
        super.changeState(isSwitch);
        if(isSwitch && mCommonBox != null) {
            mCommonBox.setChecked(currentState);
            mCommonBox.setEnabled(true);
        } else if(!isSwitch && mCommonBox != null){
            mCommonBox.setChecked(false);
            mCommonBox.setEnabled(false);
        }
    }

    @Override
    protected void onFail(String code, String error) {
        super.onFail(code, error);
        switchOpenHandle();
    }

    @Override
    protected void networkConnect(View v) {
        if(currentState) {
            currentState = false;
        } else {
            currentState = true;
        }
        if(mCommonBox != null) {
            mCommonBox.setChecked(currentState);
        }
        sendDp();
    }

    @Override
    protected void networkNoConnect(View v) {
        if(mCommonBox != null) {
            mCommonBox.setChecked(currentState);
        }
    }

    @Override
    public Object getDpValue() {
        return currentState;
    }

    @Override
    public void setDpValue(Object dpValue) {
        beforeState = currentState = (boolean) dpValue;
        switchOpenHandle();
    }

    @Override
    protected void switchOpenHandle() {
        if(switchOpen && mCommonBox != null) {
            mCommonBox.setChecked(currentState);
        } else if(!switchOpen && mCommonBox != null) {
            mCommonBox.setChecked(false);
        }
    }

    /**
     * 设置按钮的开关
     * @param checked boolean类型值
     */
    protected  void setChecked(boolean checked) {
       if(mCommonBox != null) {
           mCommonBox.setChecked(checked);
       }
   }
}
