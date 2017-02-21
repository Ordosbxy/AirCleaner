package com.saiyi.aircleaner.dp;

import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * 文件描述：显示文本dp功能
 * 创建作者：黎丝军
 * 创建时间：2016/9/2 16:10
 */
public abstract class AbsShowTextDp extends AbsDp {

    //显示dp值
    private TextView mShowText;
    //格式化小数点
    private DecimalFormat mFormat;

    public AbsShowTextDp(TextView commonBox) {
        super(commonBox);
        mShowText = commonBox;
    }

    @Override
    protected void networkConnect(View v) {
    }

    @Override
    protected void networkNoConnect(View v) {
    }

    @Override
    protected void switchOpenHandle() {
    }

    @Override
    public Object getDpValue() {
        return null;
    }

    /**
     * 显示文本信息
     * @param value 显示值
     */
    protected void setText(String value) {
        mShowText.setText(value);
    }

    /**
     * 获取浮点值后几位小数
     * @param digit 几位小数据点，格式如“0.00”,表示保留两位小数
     * @param value 需要保留的值
     * @return 得处理后的值
     */
    protected String getRadixPoint(String digit,double value) {
        mFormat = new DecimalFormat(digit);
        return mFormat.format(value);
    }

    /**
     * 获取浮点型后两位小数
     * @param value 需要转换的值
     * @return 返回保留后两位小数的值
     */
    protected String getRadixPoint(double value) {
        return getRadixPoint("0.00",value);
    }
}
