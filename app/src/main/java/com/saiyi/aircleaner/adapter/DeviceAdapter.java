package com.saiyi.aircleaner.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.SelectNetworkActivity;
import com.saiyi.aircleaner.http.HttpRqtMgr;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.util.PreferencesUtils;
import com.saiyi.aircleaner.util.ProgressUtils;
import com.saiyi.aircleaner.util.ToastUtils;
import com.saiyi.aircleaner.view.InfoHintDialog;
import com.tuya.smart.android.hardware.model.IControlCallback;
import com.tuya.smart.sdk.TuyaDevice;
import com.tuya.smart.sdk.TuyaUser;
import com.tuya.smart.sdk.bean.DeviceBean;

/**
 * 文件描述：设备列表适配器，主要用于设备管理界面
 * 创建作者：黎丝军
 * 创建时间：16/7/28 PM2:33
 */
public class DeviceAdapter extends AbsSwipeAdapter<DeviceBean,DeviceAdapter.DeviceViewHolder> {

    //判断是否有侧滑视图打开
    private boolean isSwipeOpen = false;
    //用于提示dialgo
    private InfoHintDialog mHintDialog;

    public DeviceAdapter(Context context) {
        super(context, R.layout.listview_device_item);
        mHintDialog = new InfoHintDialog(context);
    }

    @Override
    public DeviceViewHolder onCreateVH(View itemView, int ViewType) {
        return new DeviceViewHolder(itemView);
    }

    @Override
    public void onBindDataForItem(DeviceViewHolder viewHolder, DeviceBean bean, int position) {
        viewHolder.isLineRbt.setChecked(bean.getIsOnline());
        viewHolder.deviceNameTv.setText(bean.getName());
        viewHolder.deviceInfoTv.setText(bean.getDevId());
        viewHolder.deviceIconIv.setImageResource(R.mipmap.ic_device);
//        HttpRqtMgr.instance().loadImageView(bean.getIconUrl(),viewHolder.deviceIconIv,0);
        bindSwipeLayout(viewHolder.itemSwipe,position);
    }

    @Override
    protected void setItemListeners(final DeviceViewHolder holder, final DeviceBean deviceBean, final int position) {
        super.setItemListeners(holder, deviceBean, position);
        holder.itemSwipe.addSwipeListener(mSwipeListener);
        holder.changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deviceBean.isShare) {
                    ToastUtils.toast(getContext(),R.string.my_share_no_limit);
                } else {
                    changeHandle(holder,deviceBean,position);
                }
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deviceBean.isShare) {
                    ToastUtils.toast(getContext(),R.string.my_share_no_limit);
                } else {
                    deleteHandle(holder,deviceBean,position);
                }
            }
        });
    }

    /**
     * 处理器更好网络的逻辑
     * @param holder
     * @param deviceBean
     * @param position
     */
    private void changeHandle(final DeviceViewHolder holder, final DeviceBean deviceBean, final int position) {
        mHintDialog.setTitle(R.string.device_hint_text);
        mHintDialog.setContentText(R.string.device_change_hint_content);
        mHintDialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TuyaDevice device = new TuyaDevice(deviceBean.getDevId());
                if(device != null) {
                    ProgressUtils.showDialog(getContext(),R.string.progress_change_hint);
                    device.removeDevice(new IControlCallback() {

                        @Override
                        public void onError(String s, String s1) {
                            ProgressUtils.dismissDialog();
                        }

                        @Override
                        public void onSuccess() {
                            getContext().startActivity(new Intent(getContext(), SelectNetworkActivity.class));
                            ProgressUtils.dismissDialog();
                            TuyaUser.getDeviceInstance().queryDevList();
                        }
                    });
                }
                closeAllItems();
                mHintDialog.dismiss();
            }
        });
        mHintDialog.show();
    }

    /**
     * 处理删除设备的逻辑
     * @param holder
     * @param deviceBean
     * @param position
     */
    private void deleteHandle(final DeviceViewHolder holder, final DeviceBean deviceBean, final int position) {
        mHintDialog.setTitle(R.string.device_hint_text);
        mHintDialog.setContentText(R.string.control_interface_delete_sure);
        mHintDialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TuyaDevice device = new TuyaDevice(deviceBean.getDevId());
                if(device != null) {
                    ProgressUtils.showDialog(getContext(),R.string.progress_delete_hint);
                    device.removeDevice(new IControlCallback() {
                        @Override
                        public void onError(String s, String s1) {
                            ProgressUtils.dismissDialog();
                            ToastUtils.toast(getContext(),R.string.control_interface_delete_fail);
                        }
                        @Override
                        public void onSuccess() {
                            isSwipeOpen = false;
                            removeShownLayouts(holder.itemSwipe);
                            removeData(position);
                            closeAllItems();
                            ProgressUtils.dismissDialog();
                            TuyaUser.getDeviceInstance().queryDevList();
                            ToastUtils.toast(getContext(),R.string.control_interface_delete_success);
                            PreferencesUtils.remove(deviceBean.getDevId());
                        }
                    });
                } else {
                    ToastUtils.toast(getContext(),R.string.control_interface_delete_fail);
                }
                mHintDialog.dismiss();
            }
        });
        mHintDialog.show();
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.sl_device;
    }

    /**
     * 设备视图复用类
     */
    public class DeviceViewHolder extends BaseViewHolder {
        //设备照片
        ImageView deviceIconIv;
        //设备名
        TextView deviceNameTv;
        //设备mac信息
        TextView deviceInfoTv;
        //设备是否在线
        RadioButton isLineRbt;
        //更换按钮
        Button changeBtn;
        //删除按钮
        Button deleteBtn;
        //底部线条
        View bottomLine;
        //用于绑定
        SwipeLayout itemSwipe;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            itemSwipe = getViewById(R.id.sl_device);
            deviceIconIv = getViewById(R.id.iv_device_icon);
            deviceNameTv = getViewById(R.id.tv_device_name);
            deviceInfoTv = getViewById(R.id.tv_device_info);
            isLineRbt = getViewById(R.id.rbt_is_line);
            changeBtn = getViewById(R.id.btn_change);
            deleteBtn = getViewById(R.id.btn_delete);
            bottomLine = getViewById(R.id.v_bottom_line);
        }
    }

    /**
     * 用于监听侧滑过程
     */
    private SwipeLayout.SwipeListener mSwipeListener = new SimpleSwipeListener() {
        @Override
        public void onStartOpen(SwipeLayout swipeLayout) {
            closeAllItems();
        }

        @Override
        public void onOpen(SwipeLayout layout) {
            isSwipeOpen = true;
        }

        @Override
        public void onStartClose(SwipeLayout layout) {
            isSwipeOpen = false;
        }
    };

    /**
     * 判断是item的侧滑视图是否打开，用于按返回键时判断
     * @return false表示没有打开，否则表示打开
     */
    public boolean isSwipeOpen() {
        return isSwipeOpen;
    }
}
