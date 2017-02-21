package com.saiyi.aircleaner.other.dp;

import android.widget.CheckBox;

import com.saiyi.aircleaner.dp.AbsBooleanDp;

/**
 * 文件描述：雾化
 * 创建作者：黎丝军
 * 创建时间：2016/8/30 12:17
 */
public class Atomize extends AbsBooleanDp {

    public Atomize(CheckBox commonBox) {
        super(commonBox);
    }

    @Override
    public String getDpKey() {
        return DP_KEY_12;
    }
}
