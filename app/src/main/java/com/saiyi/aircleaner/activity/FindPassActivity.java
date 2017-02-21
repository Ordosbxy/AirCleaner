package com.saiyi.aircleaner.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.ConnectDeviceBusiness;
import com.saiyi.aircleaner.blls.FindPassBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;
import com.tuya.smart.android.device.bean.FindDeviceBean;

/**
 * 文件描述：找密码界面，该界面有两个布局，第一个布局是find_password_first_page.xml输入手机号，
 *         第二个布局是find_password_first_page.xml获取验证码和填写新密码
 * 创建作者：黎丝军
 * 创建时间：16/7/29 AM11:03
 */
public class FindPassActivity extends AbsBaseActivity {

    //获取用户手机号
    private EditText mPhoneEdt;
    //获取用户验证码
    private EditText mVerifyEdt;
    //获取用户新密码
    private EditText mNewPassEdt;
    //获取验证码按钮
    private TextView mGetVerifyBtn;
    //显示验证码按钮
    private TextView mShowPassBtn;
    //显示发送验证的状态
    private TextView mShowSendStateTv;
    //用于隐藏第一页视图
    private View mFirstPageView;
    //点击下一步时显示第二页视图
    private View mSecondPageView;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_find_password);
    }

    @Override
    public void findViews() {
        mPhoneEdt = getViewById(R.id.edt_find_pass_phone);
        mVerifyEdt = getViewById(R.id.edt_find_pass_verify);
        mNewPassEdt = getViewById(R.id.edt_find_pass_new_password);
        mGetVerifyBtn = getViewById(R.id.tv_find_pass_get_verify);
        mShowPassBtn = getViewById(R.id.tv_hide_password);
        mShowSendStateTv = getViewById(R.id.tv_find_pass_show_state);
        mFirstPageView = getViewById(R.id.find_pass_first);
        mSecondPageView = getViewById(R.id.find_pass_second);
    }

    @Override
    public void initObjects() {
        mBusiness = new FindPassBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.find_password_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setRightButtonText(R.string.register_next);
        actionBar.setRightButtonTextColor(Color.WHITE);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_CLICK,mShowPassBtn);
        registerListener(IListener.ON_CLICK,mGetVerifyBtn);
        registerListener(IListener.ON_ACTION_BAR_RIGHT_CLICK,actionBar);
        registerListener(IListener.ON_ACTION_BAR_LEFT_CLICK,actionBar);
    }

    public Button getRightBtn() {
        return actionBar.getRightBtn();
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    public EditText getPhoneEdt() {
        return mPhoneEdt;
    }

    public EditText getVerifyEdt() {
        return mVerifyEdt;
    }

    public EditText getNewPassEdt() {
        return mNewPassEdt;
    }

    public TextView getGetVerifyBtn() {
        return mGetVerifyBtn;
    }

    public TextView getShowPassBtn() {
        return mShowPassBtn;
    }

    public TextView getShowSendStateTv() {
        return mShowSendStateTv;
    }

    /**
     * 用于点击头部下一步是使用
     * @param firstVisibility 第一页可见值
     * @param secondVisibility 第二页可见值
     */
    public void nextPage(int firstVisibility,int secondVisibility) {
        mFirstPageView.setVisibility(firstVisibility);
        mSecondPageView.setVisibility(secondVisibility);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ((FindPassBusiness)mBusiness).backHandle();
        }
        return true;
    }
}
