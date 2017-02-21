package com.saiyi.aircleaner.blls;

import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.RegisterActivity;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.other.LauncherTimer;
import com.saiyi.aircleaner.util.ProgressUtils;
import com.saiyi.aircleaner.util.ToastUtils;
import com.tuya.smart.android.user.TuyaSmartUserManager;
import com.tuya.smart.android.user.api.IRegisterCallback;
import com.tuya.smart.android.user.api.IValidateCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.sdk.TuyaUser;

/**
 * 文件描述：注册业务类
 * 创建作者：黎丝军
 * 创建时间：16/7/29 PM4:25
 */
public class RegisterBusiness extends AbsBaseBusiness<RegisterActivity> {

    //手机输入框是否输入
    private boolean isPhoneInput = false;
    //验证码是否输入
    private boolean isVerifyInput = false;
    //密码是否输入
    private boolean isPassInput = false;
    //用于判断显示验证码还是隐藏
    private boolean isShowVerify = false;
    //用于判断显示密码还是隐藏
    private boolean isShowPass = false;
    //用于获取验证码倒计时
    private LauncherTimer mDownTimer;
    //保存用户手机号
    private String mPhone;

    @Override
    public void initObject() {
        mDownTimer = new LauncherTimer();
    }

    @Override
    public void initData() {

    }

    @Override
    public void register(int eventType, View registerView, Object registerObj) {
        super.register(eventType, registerView, registerObj);
        if(eventType == ON_OTHER) {
            switch (registerView.getId()) {
                case R.id.edt_register_phone:
                    ((EditText)registerView).addTextChangedListener(mPhoneTextChange);
                    break;
                case R.id.edt_register_verify:
                    ((EditText)registerView).addTextChangedListener(mVerifyTextChange);
                    break;
                case R.id.edt_register_password:
                    ((EditText)registerView).addTextChangedListener(mPassTextChange);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_get_verify:
                gainVerify();
                break;
            case R.id.tv_show_verify:
                if(!isShowVerify) {
                    isShowVerify = true;
                    setInputType(getActivity().getVerifyCodeEdt(),InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    getActivity().getShowVerifyBtn().setText(R.string.register_hide_verify);
                } else {
                    isShowVerify = false;
                    setInputType(getActivity().getVerifyCodeEdt(),InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    getActivity().getShowVerifyBtn().setText(R.string.register_show_verify);
                }
                break;
            case R.id.tv_show_password:
                if(!isShowPass) {
                    isShowPass = true;
                    setInputType(getActivity().getPasswordEdt(),InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    getActivity().getShowPassBtn().setText(R.string.find_password_hide_password);
                } else {
                    isShowPass = false;
                    setInputType(getActivity().getPasswordEdt(),InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    getActivity().getShowPassBtn().setText(R.string.register_show_password);
                }
                break;
            case R.id.btn_register:
                register();
                break;
            default:
                break;
        }
    }

    @Override
    protected void actionBarLeftClick(View leftView) {
        getActivity().finish();
    }

    //获取验证码
    private void gainVerify() {
        mPhone = getActivity().getPhoneEdt().getText().toString().trim();
        if(!TextUtils.isEmpty(mPhone)) {
            final TextView verify = getActivity().getGetVerifyBtn();
            verify.setTextColor(Color.GRAY);
            verify.setEnabled(false);
            //发送请求验证码的代码
            mDownTimer.countDown(120, 0, new LauncherTimer.OnCountDownListener() {
                @Override
                public void updateTime(int time,int stopValue) {
                    if(time <= stopValue) {
                        verify.setEnabled(true);
                        verify.setText(getActivity().getString(R.string.register_get_verify_code));
                        verify.setTextColor(getActivity().getResources().getColor(R.color.login_text_color2));
                    } else {
                        verify.setText(time + "秒");
                    }
                }
            });
            TuyaUser.getUserInstance().getValidateCode(Constant.COUNTRY_CODE,mPhone, new IValidateCallback() {
                @Override
                public void onSuccess() {
                    ToastUtils.toast(getContext(),R.string.register_gain_verify_success);
                }

                @Override
                public void onError(String s, String s1) {
                    mDownTimer.cancelTimer();
                    verify.setEnabled(true);
                    verify.setText(getActivity().getString(R.string.register_get_verify_code));
                    verify.setTextColor(getActivity().getResources().getColor(R.color.login_text_color2));
                    showHintDialog(R.string.device_hint_text,s1,false);
                }
            });
        } else {
            ToastUtils.toast(getContext(),R.string.register_no_input_phone);
        }
    }

    private void setInputType(EditText editText,int inputType) {
        editText.setInputType(inputType);
    }

    /**
     * 实现注册业务逻辑
     */
    private void register() {
        mPhone = getActivity().getPhoneEdt().getText().toString().trim();
        final String verify = getActivity().getVerifyCodeEdt().getText().toString().trim();
        final String password = getActivity().getPasswordEdt().getText().toString().trim();
        if(!TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(verify) && !TextUtils.isEmpty(password)) {
            ProgressUtils.showDialog(getContext(),R.string.progress_register_hint);
            TuyaUser.getUserInstance().registerAccountWithPhone(Constant.COUNTRY_CODE, mPhone, password, verify,
                    new IRegisterCallback() {
                @Override
                public void onSuccess(User user) {
                    ProgressUtils.dismissDialog();
                    mHintDialog.setSureListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().finish();
                            mHintDialog.dismiss();
                        }
                    });
                    showHintDialog(R.string.register_title,R.string.register_success,false);
                }

                @Override
                public void onError(String s, String s1) {
                    ProgressUtils.dismissDialog();
                    showHintDialog(R.string.register_fail_title,s1,false);
                }
            });
        }
    }

    private TextWatcher mPhoneTextChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isPhoneInput = true;
            registerBtnBgColorChange();
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() <= 0) {
                isPhoneInput = false;
                registerBtnBgColorChange();
            } else {
                isPhoneInput = true;
                registerBtnBgColorChange();
            }
        }
    };


    private TextWatcher mVerifyTextChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isVerifyInput = true;
            registerBtnBgColorChange();
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() <= 0) {
                isVerifyInput = false;
                registerBtnBgColorChange();
            } else {
                isVerifyInput = true;
                registerBtnBgColorChange();
            }
        }
    };

    private TextWatcher mPassTextChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isPassInput = true;
            registerBtnBgColorChange();
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() <= 0) {
                isPassInput = false;
                registerBtnBgColorChange();
            } else {
                isPassInput = true;
                registerBtnBgColorChange();
            }
        }
    };

    //改变注册按钮背景颜色
    private void registerBtnBgColorChange() {
        if(isPhoneInput && isVerifyInput && isPassInput) {
            getActivity().getRegisterBtn().setEnabled(true);
            getActivity().getRegisterBtn().setBackgroundDrawable(
                    getActivity().getResources().getDrawable(R.drawable.shape_button_login_bg_light));
        } else {
            getActivity().getRegisterBtn().setEnabled(false);
            getActivity().getRegisterBtn().setBackgroundDrawable(
                    getActivity().getResources().getDrawable(R.drawable.shape_button_login_background));
        }
    }


}
