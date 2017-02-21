package com.saiyi.aircleaner.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.RegisterBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;

/**
 * 文件描述：注册界面，主要在登录时，如果用户没有注册则跳转到此界面
 * 创建作者：黎丝军
 * 创建时间：16/7/28 PM5:19
 */
public class RegisterActivity extends AbsBaseActivity {

    //用于获取用户手机号
    private EditText mPhoneEdt;
    //获取用户输入的验证码
    private EditText mVerifyCodeEdt;
    //用于获取用户输入的密码
    private EditText mPasswordEdt;
    //获取验证码按钮
    private TextView mGetVerifyBtn;
    //显示密码按钮
    private TextView mShowPassBtn;
    //显示验证码按钮
    private TextView mShowVerifyBtn;
    //注册按钮
    private Button mRegisterBtn;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void findViews() {
        mPhoneEdt = getViewById(R.id.edt_register_phone);
        mVerifyCodeEdt = getViewById(R.id.edt_register_verify);
        mPasswordEdt = getViewById(R.id.edt_register_password);
        mGetVerifyBtn = getViewById(R.id.tv_get_verify);
        mShowPassBtn = getViewById(R.id.tv_show_password);
        mShowVerifyBtn = getViewById(R.id.tv_show_verify);
        mRegisterBtn = getViewById(R.id.btn_register);
    }

    @Override
    public void initObjects() {
        mBusiness = new RegisterBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.register_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_OTHER,mPhoneEdt);
        registerListener(IListener.ON_OTHER,mVerifyCodeEdt);
        registerListener(IListener.ON_OTHER,mPasswordEdt);
        registerListener(IListener.ON_CLICK,mGetVerifyBtn);
        registerListener(IListener.ON_CLICK,mShowPassBtn);
        registerListener(IListener.ON_CLICK,mShowVerifyBtn);
        registerListener(IListener.ON_CLICK,mRegisterBtn);
        registerListener(IListener.ON_ACTION_BAR_LEFT_CLICK,actionBar);
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    public EditText getPhoneEdt() {
        return mPhoneEdt;
    }

    public EditText getVerifyCodeEdt() {
        return mVerifyCodeEdt;
    }

    public EditText getPasswordEdt() {
        return mPasswordEdt;
    }

    public TextView getGetVerifyBtn() {
        return mGetVerifyBtn;
    }

    public TextView getShowPassBtn() {
        return mShowPassBtn;
    }

    public TextView getShowVerifyBtn() {
        return mShowVerifyBtn;
    }

    public Button getRegisterBtn() {
        return mRegisterBtn;
    }
}
