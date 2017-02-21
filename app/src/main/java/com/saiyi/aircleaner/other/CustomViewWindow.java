package com.saiyi.aircleaner.other;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.AddShareActivity;
import com.saiyi.aircleaner.listener.IFilterChangeListener;
import com.saiyi.aircleaner.listener.ListenersMgr;
import com.saiyi.aircleaner.util.DensityUtils;
import com.saiyi.aircleaner.util.PreferencesUtils;
import com.saiyi.aircleaner.util.ToastUtils;
import com.tuya.smart.sdk.TuyaUser;
import com.tuya.smart.sdk.bean.DeviceBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：自定义视图弹出框
 * 创建作者：黎丝军
 * 创建时间：16/8/5 PM5:12
 */
public class CustomViewWindow implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    //根视图
    private View rootView;
    //windows
    private PopupWindow mPopupWindow;
    //运行环境
    private Context mContext;
    //重命名
    private TextView mRenameBtn;
    //分享设备
    private TextView mShareDevice;
    //滤网复位
    private TextView mFitlerScreen;
    //用于滤网提醒
    private CheckBox mFilterRemindCbx;
    //数据格式
    private Object mData;
    //设备Id
    private String mDeviceId;

    private static CustomViewWindow ourInstance;

    public static CustomViewWindow instance() {
        if(ourInstance == null) {
            ourInstance = new CustomViewWindow();
        }
        return ourInstance;
    }

    private List<OnWindowClickListener> mListeners = new ArrayList<>();

    private CustomViewWindow() {
    }

    /**
     * 初始化window环境
     * @param context 运行环境
     */
    public void init(Context context,String deviceId) {
        mDeviceId = deviceId;
        mContext = context;
        if(mContext == null) {
            return;
        }
        rootView = LayoutInflater.from(mContext).inflate(R.layout.control_interface_popup_window_more,null);
        mRenameBtn = (TextView)rootView.findViewById(R.id.tv_popup_window_rename);
        mShareDevice = (TextView) rootView.findViewById(R.id.tv_popup_window_share);
        mFitlerScreen = (TextView) rootView.findViewById(R.id.tv_popup_window_filter);
        mFilterRemindCbx = (CheckBox) rootView.findViewById(R.id.chb_remind);
        mRenameBtn.setOnClickListener(this);
        mShareDevice.setOnClickListener(this);
        mFitlerScreen.setOnClickListener(this);
        mFilterRemindCbx.setOnCheckedChangeListener(this);
        mFilterRemindCbx.setChecked(PreferencesUtils.getBoolean(deviceId,false));
        mPopupWindow = new PopupWindow(rootView, DensityUtils.dpToPx(mContext,185),DensityUtils.dpToPx(mContext,160));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    /**
     * 弹出window
     * @param dropView 弹出window
     */
    public void popWindow(View dropView) {
        mPopupWindow.showAsDropDown(dropView,0,0);
    }

    /**
     * 设置数据
     * @param data 数据
     */
    public void setData(Object data) {
        mData = data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_popup_window_rename:
                handOut(true);
                break;
            case R.id.tv_popup_window_share:
                final DeviceBean bean = TuyaUser.getDeviceInstance().getDev(mDeviceId);
                if(bean != null) {
                    if(!bean.isShare) {
                        final Intent addShare = new Intent(mContext, AddShareActivity.class);
                        addShare.putExtra(Constant.DEVICE_ID,mDeviceId);
                        mContext.startActivity(addShare);
                    } else {
                        ToastUtils.toast(mContext,R.string.my_share_no_limit);
                    }
                }
                break;
            case R.id.tv_popup_window_filter:
                handOut(false);
            default:
                break;
        }
        mPopupWindow.dismiss();
    }

    /**
     * 分发事件
     * @param isRename 是否是重命名
     */
    private void handOut(boolean isRename) {
        if(mListeners != null && mListeners.size() > 0) {
            for(OnWindowClickListener listener:mListeners) {
                if(isRename) {
                    listener.onRename(mData);
                } else {
                    listener.onFilter(mData);
                }
            }
        }
    }

    /**
     * 关闭窗体
     */
    public void dismiss() {
        if(mPopupWindow != null) {
            mPopupWindow.dismiss();
            mListeners.clear();
        }
    }

    /**
     * 添加监听器
     * @param listener 监听实例
     */
    public void addOnWindowClickListener(OnWindowClickListener listener) {
        if(!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    /**
     * 移除监听器
     * @param listener 监听实例
     */
    public void removeOnWindowClickListener(OnWindowClickListener listener) {
        if(mListeners.contains(listener)) {
            mListeners.remove(listener);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        PreferencesUtils.putBoolean(mDeviceId,isChecked);
    }

    /**
     * 弹出框点击事件
     */
    public interface OnWindowClickListener {
        /**
         * 重命名监听
         */
        void onRename(Object data);

        /**
         * 滤网复位
         */
        void onFilter(Object data);
    }

    /**
     * 在设备关闭的时候，
     * 滤网提醒和滤网复位不可以点击
     * @param isEnable true表示不可以点击，false表示可以点击
     */
    public void setRemindAndFilterEnable(boolean isEnable) {
        if(mFitlerScreen != null && mFilterRemindCbx != null) {
            mFilterRemindCbx.setEnabled(isEnable);
            mFitlerScreen.setEnabled(isEnable);
        }
    }

}
