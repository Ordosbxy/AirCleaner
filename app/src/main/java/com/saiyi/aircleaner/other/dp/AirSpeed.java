package com.saiyi.aircleaner.other.dp;

import android.content.Context;
import android.widget.CheckBox;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.dp.AbsEnumDp;

/**
 * 文件描述：风速功能
 * 创建作者：黎丝军
 * 创建时间：16/8/11 PM2:56
 */
public class AirSpeed extends AbsEnumDp<AirSpeed.Speed> {

    public AirSpeed(CheckBox commonBox) {
        super(commonBox);
        currentState = true;
    }

    @Override
    public String getDpKey() {
        return DP_KEY_5;
    }

    @Override
    public void setDpValue(Object value) {
        final Speed speed = Enum.valueOf(Speed.class,String.valueOf(value));
        super.setDpValue(speed);
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        putDrawable(Speed.FanStandby, R.drawable.selector_device_air_speed);
        putDrawable(Speed.FanLow, R.drawable.selector_device_air_speed_1);
        putDrawable(Speed.FanMiddle, R.drawable.selector_device_air_speed_2);
        putDrawable(Speed.FanHigh, R.drawable.selector_device_air_speed_3);
    }

    /**
     * 枚举风速
     */
    public enum Speed {
        /**
         * 标准速度
         */
        FanStandby,
        /**
         * 慢速
         */
        FanLow,
        /**
         * 中速
         */
        FanMiddle,
        /**
         * 高速
         */
        FanHigh
    }
}
