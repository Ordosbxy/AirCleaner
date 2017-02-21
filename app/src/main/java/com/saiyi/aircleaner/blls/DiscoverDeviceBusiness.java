package com.saiyi.aircleaner.blls;

import android.view.View;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.DiscoverDeviceActivity;
import com.saiyi.aircleaner.activity.SelectNetworkActivity;

/**
 * 文件描述：发现设备业务逻辑类
 * 创建作者：黎丝军
 * 创建时间：16/8/5 AM8:49
 */
public class DiscoverDeviceBusiness extends AbsBaseBusiness<DiscoverDeviceActivity> {

    @Override
    public void initObject() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_discover_connect:
                getActivity().startActivity(SelectNetworkActivity.class);
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void actionBarLeftClick(View leftView) {
        super.actionBarLeftClick(leftView);
        getActivity().finish();
    }
}
