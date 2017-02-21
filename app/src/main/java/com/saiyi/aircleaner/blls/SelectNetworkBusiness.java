package com.saiyi.aircleaner.blls;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.ConnectDeviceActivity;
import com.saiyi.aircleaner.activity.SelectNetworkActivity;
import com.saiyi.aircleaner.util.PreferencesUtils;
import com.saiyi.aircleaner.util.ToastUtils;

/**
 * 文件描述：选择wifi配置业务逻辑界面
 * 创建作者：黎丝军
 * 创建时间：16/8/5 AM9:25
 */
public class SelectNetworkBusiness extends AbsBaseBusiness<SelectNetworkActivity> {

    @Override
    public void initObject() {

    }

    @Override
    public void initData() {
        getActivity().getWifiNameEdt().setText(getSSid());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_net_next:
                nextHandle();
                break;
            case R.id.btn_net_cancel:
                break;
            default:
                break;
        }
    }

    @Override
    protected void actionBarLeftClick(View leftView) {
        getActivity().finish();
    }

    /**
     * 处理点击一下步的逻辑
     */
    private void nextHandle() {
        final String wifiName = getActivity().getWifiNameEdt().getText().toString().trim();
        final String wifiPassword = getActivity().getWifiPasswordEdt().getText().toString().trim();
        if(!TextUtils.isEmpty(wifiName) && !TextUtils.isEmpty(wifiPassword)) {
            PreferencesUtils.putString("wifiName",wifiName);
            PreferencesUtils.putString("wifiPassword",wifiPassword);
            final Intent intent = new Intent(getContext(),ConnectDeviceActivity.class);
            intent.putExtra("wifiName",wifiName);
            intent.putExtra("wifiPassword",wifiPassword);
            getActivity().startActivityForResult(intent,0);
            getActivity().finish();
        } else {
            ToastUtils.toast(getActivity(),R.string.select_net_next_fail);
        }
    }

    /**
     * 获取网络SSid
     * @return wifi名
     */
    private String getSSid() {
        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                String ssid = connectionInfo.getSSID();
                if (Build.VERSION.SDK_INT >= 17 && ssid.startsWith("\"") && ssid.endsWith("\""))
                    ssid = ssid.replaceAll("^\"|\"$", "");
                return ssid;
            }
        }
        return "";
    }
}
