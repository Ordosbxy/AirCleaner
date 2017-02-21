package com.saiyi.aircleaner.other.dp;
import android.widget.TextView;
import com.saiyi.aircleaner.dp.AbsShowTextDp;
import com.saiyi.aircleaner.util.LogUtils;

/**
 * 文件描述：甲醛
 * 创建作者：黎丝军
 * 创建时间：2016/9/1 11:05
 */
public class Oxymethylene extends AbsShowTextDp {

    public Oxymethylene(TextView showText) {
        super(showText);
    }

    @Override
    public String getDpKey() {
        return DP_KEY_13;
    }

    @Override
    public void setDpValue(Object value) {
        float initValue = Float.valueOf(String.valueOf(value)) / 1000;
        setText(getRadixPoint(initValue));
    }
}
