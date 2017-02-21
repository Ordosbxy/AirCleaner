package com.saiyi.aircleaner.other.dp;

import android.content.Context;
import android.widget.CheckBox;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.dp.AbsEnumDp;

/**
 * 文件描述：定时功能
 * 创建作者：黎丝军
 * 创建时间：16/8/11 PM3:07
 */
public class Timing extends AbsEnumDp<Timing.Time> {

    public Timing(CheckBox commonBox) {
        super(commonBox);
    }

    @Override
    public String getDpKey() {
        return DP_KEY_6;
    }

    @Override
    public void setDpValue(Object value) {
        final Time time = getEnum(Time.class,value);
        super.setDpValue(time);
    }

    @Override
    protected boolean isBright() {
        if(mClickCount == 0) {
            currentState = false;
        } else {
            currentState = true;
        }
        return currentState;
    }

    @Override
    public boolean isChangeText() {
        return true;
    }

    @Override
    public boolean isChangeDrawable() {
        return false;
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        putText(Time.Time0H, R.string.timer_0h);
        putText(Time.Time1H, R.string.timer_1h);
        putText(Time.Time2H, R.string.timer_2h);
        putText(Time.Time4H, R.string.timer_4h);
        putText(Time.Time8H, R.string.timer_8h);
    }

    /**
     * 枚举时间
     */
    public enum Time {
        /**
         * 0小时
         */
        Time0H,
        /**
         * 1小时
         */
        Time1H,
        /**
         * 2小时
         */
        Time2H,
        /**
         * 4小时
         */
        Time4H,
        /**
         * 三小时
         */
        Time8H
    }
}
