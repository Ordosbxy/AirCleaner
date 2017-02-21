package com.saiyi.aircleaner.other.dp;
import android.widget.TextView;
import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.dp.AbsShowTextDp;

import java.text.DecimalFormat;

/**
 * 文件描述：TVOC(指数)
 * 创建作者：黎丝军
 * 创建时间：2016/9/6 8:39
 */
public class AirQuality extends AbsShowTextDp {

    public AirQuality(TextView commonBox) {
        super(commonBox);
    }

    @Override
    public String getDpKey() {
        return DP_KEY_9;
    }

    @Override
    public void setDpValue(Object dpValue) {
//        qualityValue = Float.valueOf(getRadixPoint(qualityValue));
//        if(qualityValue > 0 && qualityValue <= 0.6) {
//            quality = getContext().getString(R.string.quality_excellent);
//        } else if(qualityValue > 0.6 && qualityValue <= 1){
//            quality = getContext().getString(R.string.quality_fine);
//        } else if(qualityValue > 1 && qualityValue <= 1.6){
//            quality = getContext().getString(R.string.quality_general);
//        } else {
//            quality = getContext().getString(R.string.quality_bad);
//        }
        setText(String.valueOf(dpValue));
    }
}
