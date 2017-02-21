package com.saiyi.aircleaner.blls;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.DeviceActivity;
import com.saiyi.aircleaner.activity.FindPassActivity;
import com.saiyi.aircleaner.activity.LoginActivity;
import com.saiyi.aircleaner.activity.RegisterActivity;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.util.PreferencesUtils;
import com.saiyi.aircleaner.util.ProgressUtils;
import com.saiyi.aircleaner.view.InfoHintDialog;
import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.sdk.TuyaUser;

/**
 * 文件描述：登录业务逻辑处理类
 * 创建作者：黎丝军
 * 创建时间：16/7/29 AM11:19
 */
public class LoginBusiness extends AbsBaseBusiness<LoginActivity> {

    //判断是否输入了帐号
    private boolean isInputAccount = false;
    //判断是否输入了密码
    private boolean isInputPassword = false;
    //提示弹出框
    protected InfoHintDialog mHintDialog;
    @Override
    public void initObject() {
        mHintDialog = new InfoHintDialog(getActivity());
    }

    @Override
    public void initData() {
        if(TuyaUser.getUserInstance().isLogin()) {
            login();
            isInputAccount = true;
            isInputPassword = true;
            changeLoginBtnColor();
            getActivity().getDeleteBtn().setVisibility(View.VISIBLE);
        } else {
        }
    }

    @Override
    public void register(int eventType, View registerView, Object registerObj) {
        super.register(eventType, registerView, registerObj);
        switch (eventType) {
            case ON_OTHER:
                if(registerView.getId() == R.id.edt_account) {
                    ((EditText)registerView).addTextChangedListener(mAccountTextChangeListener);
                } else {
                    ((EditText)registerView).addTextChangedListener(mPasswordTextChangeListener);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_delete:
                getActivity().getAccountEdt().setText("");
                break;
            case R.id.tv_find_password:
                getActivity().startActivity(FindPassActivity.class);
                break;
            case R.id.btn_login:
                login();
                break;
            default:
                break;
        }
    }

    @Override
    protected void actionBarRightClick(View rightView) {
        super.actionBarRightClick(rightView);
        getActivity().startActivity(RegisterActivity.class);
    }

    /**
     * 点击登录业务
     */
    private void login() {
        final String account = getActivity().getAccountEdt().getText().toString().trim();
        final String password = getActivity().getPassEdt().getText().toString().trim();
        if(!TextUtils.isEmpty(account)) {
            ProgressUtils.showDialog(getContext());
            if(!TextUtils.isEmpty(password)) {
                TuyaUser.getUserInstance().loginWithPhonePassword("86",account,password, new ILoginCallback() {
                    @Override
                    public void onSuccess(User user) {
                        ProgressUtils.dismissDialog();
                        PreferencesUtils.putString(Constant.USER_ACCOUNT,account);
                        PreferencesUtils.putString(Constant.USER_PASSWORD,password);
                        getActivity().startActivity(DeviceActivity.class);
                        getActivity().finish();
                    }
                    @Override
                    public void onError(String s, String s1) {
                        ProgressUtils.dismissDialog();
                        showHintDialog(R.string.login_fail_hint,s1,false);
                    }
                });
            }
        }
    }

    /**
     * 改变登录按钮背景颜色
     */
    private void changeLoginBtnColor() {
        if(isInputAccount && isInputPassword) {
            getActivity().getLoginBtn().setEnabled(true);
            getActivity().getLoginBtn().setBackgroundDrawable(
                    getActivity().getResources().getDrawable(R.drawable.shape_button_login_bg_light));
        } else {
            getActivity().getLoginBtn().setEnabled(false);
            getActivity().getLoginBtn().setBackgroundDrawable(
                    getActivity().getResources().getDrawable(R.drawable.shape_button_login_background));
        }
    }

    /**
     * 用于数据帐号以后改变删除按钮的显示
     */
    private TextWatcher mAccountTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isInputAccount = true;
            changeLoginBtnColor();
            getActivity().getDeleteBtn().setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() <= 0) {
                isInputAccount = false;
                changeLoginBtnColor();
                getActivity().getDeleteBtn().setVisibility(View.GONE);
            } else {
                isInputAccount = true;
                changeLoginBtnColor();
                getActivity().getDeleteBtn().setVisibility(View.VISIBLE);
            }
        }
    };

    /**
     * 用于输入密码和账户后改变登录按钮颜色并使能点击
     */
    private TextWatcher mPasswordTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isInputPassword = true;
            changeLoginBtnColor();
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() <= 0) {
                isInputPassword = false;
                changeLoginBtnColor();
            } else {
                isInputPassword = true;
                changeLoginBtnColor();
            }
        }
    };

    @Override
    public void onDestroy() {
        if(mHintDialog != null) {
            mHintDialog.dismiss();
            mHintDialog = null;
        }
    }
}
