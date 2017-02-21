package com.saiyi.aircleaner.blls;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.saiyi.aircleaner.AppHelper;
import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.ControlInterfaceActivity;
import com.saiyi.aircleaner.activity.DeviceActivity;
import com.saiyi.aircleaner.activity.DiscoverDeviceActivity;
import com.saiyi.aircleaner.activity.LoginActivity;
import com.saiyi.aircleaner.activity.SelectNetworkActivity;
import com.saiyi.aircleaner.adapter.AbsBaseAdapter;
import com.saiyi.aircleaner.adapter.DeviceAdapter;
import com.saiyi.aircleaner.listener.IActivateListener;
import com.saiyi.aircleaner.listener.IDeviceChangeListener;
import com.saiyi.aircleaner.listener.ListenersMgr;
import com.saiyi.aircleaner.util.ToastUtils;
import com.saiyi.aircleaner.view.slidemenu.SlideMenu;
import com.tuya.smart.android.device.api.response.GwDevResp;
import com.tuya.smart.android.device.event.DeviceListChangeEventModel;
import com.tuya.smart.android.device.event.GwRelationEvent;
import com.tuya.smart.android.device.event.GwRelationUpdateEventModel;
import com.tuya.smart.android.device.event.GwUpdateEvent;
import com.tuya.smart.android.device.event.GwUpdateEventModel;
import com.tuya.smart.sdk.TuyaSdk;
import com.tuya.smart.sdk.TuyaUser;
import com.tuya.smart.sdk.api.INeedLoginListener;
import com.tuya.smart.sdk.bean.DeviceBean;

/**
 * 文件描述：我的设备业务逻辑类
 * 创建作者：黎丝军
 * 创建时间：16/8/1 AM9:40
 */
public class DeviceBusiness extends AbsBaseBusiness<DeviceActivity>
        implements AbsBaseAdapter.OnItemClickListener<DeviceBean>,IActivateListener,
        IDeviceChangeListener,GwRelationEvent, GwUpdateEvent,SlideMenu.onSlideMenuListener,INeedLoginListener{

    //用于二维码扫描结果失败
    public final static int RESULT_FAIL = 2;
    //用于二维码请求返回
    public final static int SCAN_REQUEST_CODE = 1;
    //设备列表适配器
    private DeviceAdapter mDeviceAdapter;
    //退出时间
    private long mExitTime = 0;
    //列表数据观察者,用于监听列表item删除或者添加
    private RecyclerView.AdapterDataObserver mDataObserver;

    @Override
    public void initObject() {
        mDeviceAdapter = new DeviceAdapter(getActivity());
        mDataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                if(itemCount <= 0) {
                    getActivity().setDeviceViewVisible(View.VISIBLE,View.GONE);
                }
            }

            @Override
            public void onChanged() {
                if(mDeviceAdapter.getItemCount() <= 0) {
                    getActivity().setDeviceViewVisible(View.VISIBLE,View.GONE);
                } else {
                    getActivity().setDeviceViewVisible(View.GONE,View.VISIBLE);
                }
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                if(itemCount >= 0) {
                    getActivity().setDeviceViewVisible(View.GONE,View.VISIBLE);
                }
            }
        };
    }

    @Override
    public void initData() {
        TuyaSdk.getEventBus().register(this);
        mDeviceAdapter.setOnItemClickListener(this);
        mDeviceAdapter.registerAdapterDataObserver(mDataObserver);
        getActivity().getDeviceListRv().setAdapter(mDeviceAdapter);
        ListenersMgr.getInstance().addListener(IActivateListener.class,this);
        ListenersMgr.getInstance().addListener(IDeviceChangeListener.class,this);

        //注册是否需要登录
        TuyaSdk.setOnNeedLoginListener(this);
        //预装载数据
        TuyaUser.getDeviceInstance().queryDevList();
    }

    @Override
    public void register(int eventType, View registerView, Object registerObj) {
        super.register(eventType, registerView, registerObj);
        if(eventType == ON_OTHER) ((SlideMenu)registerView).setOnSlideMenuListener(this);
    }

    @Override
    public void onDestroy() {
        TuyaSdk.getEventBus().unregister(this);
        mDeviceAdapter.unregisterAdapterDataObserver(mDataObserver);
        ListenersMgr.getInstance().removeListener(IActivateListener.class);
        ListenersMgr.getInstance().removeListener(IDeviceChangeListener.class);
    }

    @Override
    protected void actionBarLeftClick(View leftView) {
        getActivity().slideMgr.openSlideMenu();
    }

    @Override
    protected void actionBarRightClick(View rightView) {
        getActivity().startActivity(SelectNetworkActivity.class);
//        暂时被遗弃，扫描二维码发现设备的这一步
//        final Intent intent = new Intent();
//        intent.setClass(getActivity(), TwoCodeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        getActivity().startActivityForResult(intent,SCAN_REQUEST_CODE);
    }

    /**
     * 退出应用程序，用于按返回键时调用
     */
    public void exitBackApp() {
        if(getActivity().slideMgr.isOpen()) {
            getActivity().slideMgr.closeSlideMenu();
        } else {
            if(mDeviceAdapter.isSwipeOpen()) {
                mDeviceAdapter.closeAllItems();
            } else {
                long currentTime = System.currentTimeMillis();
                if (currentTime - mExitTime < 2000) {
                    AppHelper.instance().exitApp();
                } else {
                    mExitTime = currentTime;
                    ToastUtils.toast(getActivity(), R.string.device_back_exit);
                }
            }
        }
    }

    /**
     * 添加设备，暂时该方法没有被用到，
     * 主要是扫二维码的功能没有了
     * @param deviceId 设备信息
     */
    public void addDevice(String deviceId) {
        getActivity().startActivity(DiscoverDeviceActivity.class);
    }

    @Override
    public void onItemClick(View view, DeviceBean bean, int position) {
        final Intent intent = new Intent(getContext(), ControlInterfaceActivity.class);
        intent.putExtra("deviceId",bean.devId);
        getActivity().startActivity(intent);
//        if(bean.getIsOnline()) {
//
//        } else {
//            showHintDialog(R.string.device_hint_text,R.string.device_not_online_hint,false);
//        }
    }

    @Override
    public void onUpdateData(GwDevResp gwDevResp) {
        TuyaUser.getDeviceInstance().queryDevList();
    }

    @Override
    public void onDeviceChange(int changeType, String devId, String dpStr, boolean state) {
        TuyaUser.getDeviceInstance().queryDevList();
    }

    @Override
    public void onEventMainThread(GwRelationUpdateEventModel gwRelationUpdateEventModel) {
        updateData();
    }

    @Override
    public void onEventMainThread(GwUpdateEventModel gwUpdateEventModel) {
        updateData();
    }

    private void updateData() {
        mDeviceAdapter.setListData(TuyaUser.getDeviceInstance().getDevList());
        mDeviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStateChange(int currentState, int currentOffset) {
        if(currentState == SlideMenu.STATE_CLOSE) {
            getActivity().getLeftBtn().setVisibility(View.VISIBLE);
        } else {
            if(mDeviceAdapter.isSwipeOpen()) mDeviceAdapter.closeAllItems();
            getActivity().getLeftBtn().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNeedLogin(Context context) {
        appHelper.finishAllActivity();
        getActivity().startActivity(LoginActivity.class);
    }
}
