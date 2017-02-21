package com.saiyi.aircleaner.other.dp;

import android.content.Context;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.dp.AbsShowTextDp;
import com.saiyi.aircleaner.util.NotificationUtils;
import com.saiyi.aircleaner.util.PreferencesUtils;

import java.text.DecimalFormat;

/**
 * 文件描述：滤网剩余
 * 创建作者：黎丝军
 * 创建时间：16/8/17 AM10:34
 */
public class FilterResidue extends AbsShowTextDp {

    //滤网剩余值
    private float mValue;
    //全值
    private String mValueStr;
    //是否通知
    private boolean isNotification = false;

    public FilterResidue(TextView showText) {
        super(showText);
    }

    //是否需要提醒
    public boolean isRemind() {
        if(mValue <= 20) {
            return true;
        }
        return false;
    }

    @Override
    public String getDpKey() {
        return DP_KEY_10;
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        mValueStr = context.getString(R.string.control_interface_filter);
    }

    @Override
    public void setDpValue(Object value) {
        int intValue = (int)value;
        if(intValue < 0) {
            intValue = 0;
        } else if(intValue > 5000) {
            intValue = 5000;
        }
        mValue = (1 - (intValue / (5000 * 60f))) * 100;
        final boolean isSendNotify = PreferencesUtils.getBoolean(getDevice().getDevId(),false);
        if(isSendNotify && !isNotification) {
            if(isRemind()) {
                isNotification = true;
                NotificationUtils.sendNotify(getContext(), R.string.notify_title, R.string.notify_content);
            }
        }
        setText(mValueStr + getRadixPoint(mValue) + "%");
    }
}
