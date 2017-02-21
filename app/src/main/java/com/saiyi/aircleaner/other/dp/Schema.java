package com.saiyi.aircleaner.other.dp;

import android.content.Context;
import android.widget.CheckBox;

import com.saiyi.aircleaner.dp.AbsEnumDp;

/**
 * 文件描述：模式功能
 * 创建作者：黎丝军
 * 创建时间：16/8/11 PM2:40
 */
public class Schema extends AbsEnumDp<Schema.Mode> {

    public Schema(CheckBox commonBox) {
        super(commonBox);
    }

    @Override
    public String getDpKey() {
        return DP_KEY_2;
    }

    @Override
    public void setDpValue(Object value) {
        final Mode mode = Enum.valueOf(Mode.class,String.valueOf(value));
        super.setDpValue(mode);
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
    public void setContext(Context context) {
        super.setContext(context);
        putObject(Mode.ModeManual,false);
        putObject(Mode.ModeAuto,true);
    }

    @Override
    public boolean isChangeDrawable() {
        return false;
    }

    /**
     * 泛型模式
     */
    public enum Mode {
        /**
         * 自动
         */
        ModeAuto,
        /**
         * 手动
         */
        ModeManual
    }
}
