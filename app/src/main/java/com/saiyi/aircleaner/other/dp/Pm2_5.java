package com.saiyi.aircleaner.other.dp;

import android.widget.TextView;

import com.saiyi.aircleaner.dp.AbsShowTextDp;

/**
 * 文件描述：pm2.5只是显示
 * 创建作者：黎丝军
 * 创建时间：16/8/16 AM10:05
 */
public class Pm2_5 extends AbsShowTextDp {

    public Pm2_5(TextView showTv) {
        super(showTv);
    }

    @Override
    public String getDpKey() {
        return DP_KEY_8;
    }

    @Override
    public void setDpValue(Object value) {
        int intValue = Integer.valueOf(String.valueOf(value));
        if(intValue < 0) {
            intValue = 0;
        } else if(intValue > 999) {
            intValue = 999;
        }
        setText(intValue + "");
    }
}
