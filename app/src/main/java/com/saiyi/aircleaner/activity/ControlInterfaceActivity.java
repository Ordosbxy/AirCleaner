package com.saiyi.aircleaner.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.adapter.DpViewAdapter;
import com.saiyi.aircleaner.blls.ControlFaceBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.view.CirclePageIndicator;

/**
 * 文件描述：控制界面
 * 创建作者：黎丝军
 * 创建时间：16/8/5 AM8:37
 */
public class ControlInterfaceActivity extends AbsBaseActivity {

    //供弹出框使用
    private View mPopupUse;
    //返回设备列表
    private ImageView mBackBtn;
    //显示设备名
    private TextView mDeviceNameTv;
    //更多按钮
    private ImageView mMoreBtn;
    //显示PM2.5的值
    private TextView mPM2_5Tv;
    //显示滤芯剩余值
    private TextView mFilterHintTv;
    //显示空气质量
    private TextView mAirTv;
    //显示甲醛值
    private TextView mJiaQuanTv;
    //模式开关
    private CheckBox mPatternChb;
    //杀菌开关
    private CheckBox mSterilizeChb;
    //负离子开关
    private CheckBox mAnionChb;
    //风俗开关
    private CheckBox mAirSpeedChb;
    //定时器开关
    private CheckBox mTimerChb;
    //总开关
    private CheckBox mOnOrOffChb;
    //照明
    private CheckBox mLightChb;
    //雾化
    private CheckBox mAtomizeChb;
    //用于控制亮色还是暗色
    private View mRootView;
    //用于装载dp功能点
    private ViewPager mViewPager;
    //功能点适配器
    private DpViewAdapter mDpAdapter;
    //指示器
    private CirclePageIndicator mCircleIndicator;
    //功能视图1
    private View mDp1View;
    //功能视图2
    private View mDp2View;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_control_interface);
    }

    @Override
    public void findViews() {
        mPopupUse = getViewById(R.id.view_popup);
        mRootView = getViewById(R.id.ll_control);
        mBackBtn = getViewById(R.id.iv_control_back);
        mDeviceNameTv = getViewById(R.id.tv_control_name);
        mMoreBtn = getViewById(R.id.iv_more);
        mPM2_5Tv = getViewById(R.id.tv_mp2_5);
        mFilterHintTv = getViewById(R.id.tv_filter);
        mAirTv = getViewById(R.id.tv_air);
        mJiaQuanTv = getViewById(R.id.tv_oxymethylene);
        mOnOrOffChb = getViewById(R.id.chb_device_off_or_on);
        mViewPager = getViewById(R.id.vp_dps);
        mCircleIndicator = getViewById(R.id.cpi_dps);

        //初始化各种View
        mDp1View = LayoutInflater.from(this).inflate(R.layout.fragment_dp1,null);
        mPatternChb = (CheckBox) mDp1View.findViewById(R.id.chb_device_pattern);
        mSterilizeChb = (CheckBox) mDp1View.findViewById(R.id.chb_sterilize);
        mAnionChb = (CheckBox) mDp1View.findViewById(R.id.chb_anion);
        mAirSpeedChb = (CheckBox) mDp1View.findViewById(R.id.chb_air_speed);
        mTimerChb = (CheckBox) mDp1View.findViewById(R.id.chb_timer);

        mDp2View = LayoutInflater.from(this).inflate(R.layout.fragment_dp2,null);
        mLightChb = (CheckBox) mDp2View.findViewById(R.id.chb_device_light);
        mAtomizeChb = (CheckBox) mDp2View.findViewById(R.id.chb_atomize);
    }

    @Override
    public void initObjects() {
        mBusiness = new ControlFaceBusiness();
        mDpAdapter = new DpViewAdapter();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setStatusBarBackgroundColor(Color.parseColor("#BEBEBE"));
        mDpAdapter.addView(mDp1View);
        mDpAdapter.addView(mDp2View);
        mViewPager.setAdapter(mDpAdapter);
        mCircleIndicator.setViewPager(mViewPager,0);
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_CLICK,mBackBtn);
        registerListener(IListener.ON_CLICK,mMoreBtn);
    }

    public TextView getDeviceNameTv() {
        return mDeviceNameTv;
    }

    public View getPopupUse() {
        return mPopupUse;
    }

    public TextView getPM2_5Tv() {
        return mPM2_5Tv;
    }

    public TextView getFilterHintTv() {
        return mFilterHintTv;
    }

    public TextView getAirTv() {
        return mAirTv;
    }

    public TextView getJiaQuanTv() {
        return mJiaQuanTv;
    }

    public CheckBox getPatternChb() {
        return mPatternChb;
    }

    public CheckBox getSterilizeChb() {
        return mSterilizeChb;
    }

    public CheckBox getAnionChb() {
        return mAnionChb;
    }

    public CheckBox getAirSpeedChb() {
        return mAirSpeedChb;
    }

    public CheckBox getTimerChb() {
        return mTimerChb;
    }

    public CheckBox getOnOrOffChb() {
        return mOnOrOffChb;
    }

    public CheckBox getAtomizeChb() {
        return mAtomizeChb;
    }

    public CheckBox getLightChb() {
        return mLightChb;
    }

    /***
     * 设置打开或关闭
     * @param isOpen 是否打开
     */
    public void setSwitch(boolean isOpen) {
        mOnOrOffChb.setChecked(isOpen);
        if(isOpen) {
            setStatusBarBackgroundColor(Color.parseColor("#35A2F0"));
            mRootView.setBackgroundResource(R.mipmap.ic_bg_on);
            mCircleIndicator.setFillColor(Color.parseColor("#35A2F0"));
            mCircleIndicator.setStrokeColor(Color.parseColor("#35A2F0"));
        } else {
            setStatusBarBackgroundColor(Color.parseColor("#BEBEBE"));
            mRootView.setBackgroundResource(R.mipmap.ic_bg_off);
            mCircleIndicator.setStrokeColor(Color.parseColor("#BEBEBE"));
            mCircleIndicator.setFillColor(Color.parseColor("#BEBEBE"));
        }
    }
}
