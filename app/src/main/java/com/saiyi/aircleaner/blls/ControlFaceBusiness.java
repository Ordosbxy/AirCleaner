package com.saiyi.aircleaner.blls;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.ControlInterfaceActivity;
import com.saiyi.aircleaner.dp.DpManager;
import com.saiyi.aircleaner.dp.IDP;
import com.saiyi.aircleaner.dp.Switch;
import com.saiyi.aircleaner.listener.IDeviceChangeListener;
import com.saiyi.aircleaner.listener.ListenersMgr;
import com.saiyi.aircleaner.other.CustomViewWindow;
import com.saiyi.aircleaner.other.dp.AirQuality;
import com.saiyi.aircleaner.other.dp.AirSpeed;
import com.saiyi.aircleaner.other.dp.Anion;
import com.saiyi.aircleaner.other.dp.Atomize;
import com.saiyi.aircleaner.other.dp.Bright;
import com.saiyi.aircleaner.other.dp.FilterResidue;
import com.saiyi.aircleaner.other.dp.FilterScreen;
import com.saiyi.aircleaner.other.dp.Oxymethylene;
import com.saiyi.aircleaner.other.dp.Pm2_5;
import com.saiyi.aircleaner.other.dp.Schema;
import com.saiyi.aircleaner.other.dp.Sterilize;
import com.saiyi.aircleaner.other.dp.Timing;
import com.saiyi.aircleaner.util.LogUtils;
import com.saiyi.aircleaner.util.ToastUtils;
import com.saiyi.aircleaner.view.CInformDialog;
import com.tuya.smart.android.hardware.model.IControlCallback;
import com.tuya.smart.sdk.TuyaDevice;
import com.tuya.smart.sdk.TuyaUser;
import com.tuya.smart.sdk.api.IDevListener;
import com.tuya.smart.sdk.bean.DeviceBean;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 文件描述：控制器界面业务类
 * 创建作者：黎丝军
 * 创建时间：16/8/5 PM4:12
 */
public class ControlFaceBusiness extends AbsBaseBusiness<ControlInterfaceActivity>
        implements CustomViewWindow.OnWindowClickListener, IDevListener,IDP.IDpListener{

    //用于弹出框
    private CustomViewWindow mWindow;
    //数据
    private DeviceBean mDeviceBean;
    //涂鸦设备
    private TuyaDevice mTuyaDevice;
    //dp管理器
    private DpManager mDpManager;
    //设备改变监听器
    private IDeviceChangeListener mDeviceChangeListener;

    @Override
    public void initObject() {
        mWindow = CustomViewWindow.instance();
        mDpManager = DpManager.getInstance(getContext());
        mDeviceChangeListener = ListenersMgr.getInstance().getListener(IDeviceChangeListener.class);
    }

    @Override
    public void initData() {
        final String deviceId = (String)getActivity().getIntent().getSerializableExtra("deviceId");
        mDeviceBean = TuyaUser.getDeviceInstance().getDev(deviceId);
        mWindow.setData(mDeviceBean);
        getActivity().getDeviceNameTv().setText(mDeviceBean.getName());
        mTuyaDevice = new TuyaDevice(deviceId);
        mTuyaDevice.registerDevListener(this);
        mWindow.addOnWindowClickListener(this);

        initDp();
        initDpData(deviceId);
        mWindow.init(getContext(),deviceId);
    }

    private void initDp() {
        mDpManager.setDevice(mTuyaDevice);
        mDpManager.addDp(new Switch(getActivity().getOnOrOffChb()),this);
        mDpManager.addDp(new Schema(getActivity().getPatternChb()));
        mDpManager.addDp(new Sterilize(getActivity().getSterilizeChb()));
        mDpManager.addDp(new Anion(getActivity().getAnionChb()));
        mDpManager.addDp(new AirSpeed(getActivity().getAirSpeedChb()));
        mDpManager.addDp(new Timing(getActivity().getTimerChb()));
        mDpManager.addDp(new Bright(getActivity().getLightChb()));
        mDpManager.addDp(new Atomize(getActivity().getAtomizeChb()));
        mDpManager.addDp(new Pm2_5(getActivity().getPM2_5Tv()));
        mDpManager.addDp(new AirQuality(getActivity().getAirTv()));
        mDpManager.addDp(new Oxymethylene(getActivity().getJiaQuanTv()));
        mDpManager.addDp(new FilterResidue(getActivity().getFilterHintTv()));
        mDpManager.addDp(new FilterScreen());
    }

    /**
     * 初始化数据
     * @param deviceId 设备Id
     */
    private void initDpData(String deviceId) {
        final Map<String,Object> dpData = TuyaUser.getDeviceInstance().getDps(deviceId);
        if(dpData != null) {
            mDpManager.dpUpdate(dpData);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_control_back:
                getActivity().finish();
                break;
            case R.id.iv_more:
                mWindow.popWindow(getActivity().getPopupUse());
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        if(mTuyaDevice != null) {
            mTuyaDevice.unRegisterDevListener();
            mTuyaDevice.onDestroy();
        }
        if(mWindow != null) {
            mWindow.dismiss();
            mWindow = null;
        }
        mDpManager.clear();
    }

    @Override
    public void onRename(Object data) {
        final CInformDialog dialog = new CInformDialog();
        dialog.create(getContext());
        dialog.setModel(CInformDialog.Model.INPUT);
        dialog.setDialogTitle(getContext().getString(R.string.control_interface_rename));
        dialog.setSureButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rename = dialog.getInputText();
                if(!TextUtils.isEmpty(rename)) {
                    if(mTuyaDevice != null) {
                        mTuyaDevice.renameDevice(dialog.getInputText(), new IControlCallback() {
                            @Override
                            public void onError(String s, String s1) {
                                ToastUtils.toast(getContext(),R.string.control_interface_rename_fail);
                            }

                            @Override
                            public void onSuccess() {
                                getActivity().getDeviceNameTv().setText(rename);
                                ToastUtils.toast(getContext(),R.string.control_interface_rename_success);
                            }
                        });
                    } else {
                        ToastUtils.toast(getContext(),R.string.control_interface_rename_fail);
                    }
                    dialog.cancelDialog();
                } else {
                    ToastUtils.toast(getContext(),R.string.control_interface_null);
                }
            }
        });
        dialog.showDialog();
    }

    @Override
    public void onFilter(Object data) {
        final FilterScreen filterScreen = mDpManager.getDp(IDP.DP_KEY_7);
        final FilterResidue filterResidue = mDpManager.getDp(IDP.DP_KEY_10);
        if(filterResidue.isRemind()) {
            filterScreen.setDpValue(true);
            filterScreen.sendDp();
        } else {
            ToastUtils.toast(getContext(), R.string.control_interface_filter_reset);
        }
    }

    @Override
    public void onDpUpdate(String devId, String dpStr) {
        LogUtils.d(dpStr);
        mDpManager.dpUpdate(dpStr);
    }

    @Override
    public void onRemoved(String devId) {
        getActivity().finish();
    }

    @Override
    public void onStatusChanged(String devId, boolean online) {
        deviceChange(IDeviceChangeListener.STATE_CHANGE,devId,null,online);
    }

    @Override
    public void onNetworkStatusChanged(String devId, boolean status) {
        deviceChange(IDeviceChangeListener.NETWORK_CHANGE,devId,null,status);
    }

    @Override
    public void onDevInfoUpdate(String devId) {
        deviceChange(IDeviceChangeListener.DEVICE_INFO_UPDATE,devId,null,true);
    }

    /**
     * 给设备界面调用更新列表
     * @param changeType 改变类型
     * @param devId 设备Id
     * @param dpStr dp数据
     * @param state 状态值
     */
    private void deviceChange(int changeType,String devId, String dpStr,boolean state) {
        if(mDeviceChangeListener != null) {
            mDeviceChangeListener.onDeviceChange(changeType,devId,dpStr,state);
        }
    }

    @Override
    public void onDp(IDP dp) {
        if(dp != null) {
            //当点击开关键的时候，改变背景或者其他之类的信息
            if(TextUtils.equals(dp.getDpKey(),IDP.DP_KEY_1)) {
                getActivity().setSwitch(dp.isOpen());
                mWindow.setRemindAndFilterEnable(dp.isOpen());
            }
        }
    }
}
