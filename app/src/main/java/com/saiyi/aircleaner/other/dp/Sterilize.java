package com.saiyi.aircleaner.other.dp;

import android.widget.CheckBox;

import com.saiyi.aircleaner.dp.AbsBooleanDp;

/**
 * 文件描述：杀菌功能
 * 创建作者：黎丝军
 * 创建时间：16/8/11 PM2:52
 */
public class Sterilize extends AbsBooleanDp {

    public Sterilize(CheckBox commonBox) {
        super(commonBox);
    }

    @Override
    public String getDpKey() {
        return DP_KEY_4;
    }
}
