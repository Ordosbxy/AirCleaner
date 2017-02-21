package com.saiyi.aircleaner.other.dp;


import android.widget.CheckBox;

import com.saiyi.aircleaner.dp.AbsBooleanDp;

/**
 * 文件描述：照明
 * 创建作者：黎丝军
 * 创建时间：2016/8/30 12:16
 */
public class Bright extends AbsBooleanDp {

    public Bright(CheckBox commonBox) {
        super(commonBox);
    }

    @Override
    public String getDpKey() {
        return DP_KEY_11;
    }
}
