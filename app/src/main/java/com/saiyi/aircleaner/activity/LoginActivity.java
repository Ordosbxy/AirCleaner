package com.saiyi.aircleaner.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.aircleaner.AppHelper;
import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.LoginBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.util.PreferencesUtils;
import com.tuya.smart.sdk.TuyaUser;

/**
 * 文件描述：登录界面
 * 创建作者：黎丝军
 * 创建时间：16/7/28 PM4:11
 */
public class LoginActivity extends AbsBaseActivity {

    //用于获取用户输入的帐号信息
    private EditText mAccountEdt;
    //用于获取用户输入的密码
    private EditText mPassEdt;
    //用于在用户输入帐号时
    //右边冒出的删除按钮
    private ImageView mDeleteBtn;
    //用于用户点击找回密码
    private TextView mFindPassBtn;
    //用于用户点击登录
    private Button mLoginBtn;

    @Override
    public void onContentView() {
        //权限请求
        ActivityCompat.requestPermissions(this, getPermissions(), REQUEST_CODE);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void findViews() {
        mAccountEdt = getViewById(R.id.edt_account);
        mPassEdt = getViewById(R.id.edt_password);
        mDeleteBtn = getViewById(R.id.iv_delete);
        mFindPassBtn = getViewById(R.id.tv_find_password);
        mLoginBtn = getViewById(R.id.btn_login);
    }

    @Override
    public void initObjects() {
        mBusiness = new LoginBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.login_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setRightButtonText(R.string.register_title);
        actionBar.setRightButtonTextColor(Color.WHITE);

        if(TuyaUser.getUserInstance().isLogin()) {
            mAccountEdt.setText(PreferencesUtils.getString(Constant.USER_ACCOUNT));
            mPassEdt.setText(PreferencesUtils.getString(Constant.USER_PASSWORD));
        }
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_OTHER,mPassEdt);
        registerListener(IListener.ON_OTHER,mAccountEdt);
        registerListener(IListener.ON_CLICK,mDeleteBtn);
        registerListener(IListener.ON_CLICK,mLoginBtn);
        registerListener(IListener.ON_CLICK,mFindPassBtn);
        registerListener(IListener.ON_ACTION_BAR_RIGHT_CLICK,actionBar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppHelper.instance().exitApp();
        }
        return true;
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    public EditText getPassEdt() {
        return mPassEdt;
    }

    public EditText getAccountEdt() {
        return mAccountEdt;
    }

    public ImageView getDeleteBtn() {
        return mDeleteBtn;
    }

    public Button getLoginBtn() {
        return mLoginBtn;
    }
}
