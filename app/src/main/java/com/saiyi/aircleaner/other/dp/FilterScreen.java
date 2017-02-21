package com.saiyi.aircleaner.other.dp;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.dp.AbsBooleanDp;
import com.saiyi.aircleaner.util.ToastUtils;

/**
 * 文件描述：滤网复位
 * 创建作者：黎丝军
 * 创建时间：2016/8/22 9:17
 */
public class FilterScreen extends AbsBooleanDp {

    public FilterScreen() {
        super(null);
        currentState = true;
    }

    @Override
    public String getDpKey() {
        return DP_KEY_7;
    }

    @Override
    public void onFail(String code, String error) {
        ToastUtils.toast(getContext(), R.string.control_interface_filter_fail);
    }

    @Override
    protected void onSendSuccess() {
        super.onSendSuccess();
        ToastUtils.toast(getContext(), R.string.control_interface_filter_success);
    }
}
