package com.saiyi.aircleaner.blls;

import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.FindPassActivity;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.other.LauncherTimer;
import com.saiyi.aircleaner.util.ProgressUtils;
import com.saiyi.aircleaner.util.ToastUtils;
import com.tuya.smart.android.user.api.IResetPasswordCallback;
import com.tuya.smart.android.user.api.IValidateCallback;
import com.tuya.smart.sdk.TuyaUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件描述：找密码业务服务类
 * 创建作者：黎丝军
 * 创建时间：16/7/29 PM6:06
 */
public class FindPassBusiness extends AbsBaseBusiness<FindPassActivity>{

    //用于判断是否点击了下一步
    private boolean isClickNext = false;
    //用于判断是否显示密码
    private boolean isShowPass = true;
    //倒计时器
    private LauncherTimer mDownTimer;
    //手机号
    private String mPhone;
    //按钮
    private Button mRightButton;

    @Override
    public void initObject() {
        mDownTimer = new LauncherTimer();
    }

    @Override
    public void initData() {
        setInputType(getActivity().getNewPassEdt(), InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_find_pass_get_verify:
                gainVerify();
                break;
            case R.id.tv_hide_password:
                if(!isShowPass) {
                    isShowPass = true;
                    setInputType(getActivity().getNewPassEdt(), InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    getActivity().getShowPassBtn().setText(R.string.find_password_hide_password);
                } else {
                    isShowPass = false;
                    setInputType(getActivity().getNewPassEdt(),InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    getActivity().getShowPassBtn().setText(R.string.register_show_password);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void actionBarLeftClick(View leftView) {
        backHandle();
    }

    /**
     * 返回键处理
     */
    public void backHandle() {
        if(isClickNext) {
            isClickNext = false;
            getActivity().nextPage(View.VISIBLE,View.GONE);
            getActivity().getRightBtn().setText(R.string.register_next);
        } else {
            getActivity().finish();
        }
    }

    @Override
    protected void actionBarRightClick(final View rightView) {
        //在这里写请求验证的密码
        if(!isClickNext) {
            mPhone = getActivity().getPhoneEdt().getText().toString().trim();
            if(!TextUtils.isEmpty(mPhone)) {
                if(mPhone.length() >= 11 && isMobile(mPhone)) {
                    isClickNext = true;
                    mRightButton = (Button)rightView;
                    getActivity().nextPage(View.GONE,View.VISIBLE);
                    mRightButton.setText(R.string.find_password_finish);
                } else {
                    ToastUtils.toast(getContext(),R.string.find_password_error_phone);
                }
            } else {
                ToastUtils.toast(getContext(),R.string.register_no_input_phone);
            }
        } else  {
            resetPasswordHandle();
        }
    }

    /**
     * 处理重置密码的业务逻辑
     */
    private void resetPasswordHandle() {
        final String verifyCode = getActivity().getVerifyEdt().getText().toString().trim();
        final String password = getActivity().getNewPassEdt().getText().toString().trim();
        if(!TextUtils.isEmpty(verifyCode) && !TextUtils.isEmpty(password)) {
            if(password.length() >= 6) {
                ProgressUtils.showDialog(getContext(),R.string.progress_reset_password);
                TuyaUser.getUserInstance().resetPhonePassword(Constant.COUNTRY_CODE,
                        mPhone, verifyCode, password, new IResetPasswordCallback() {
                            @Override
                            public void onSuccess() {
                                mHintDialog.setSureListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ProgressUtils.dismissDialog();
                                        getActivity().finish();
                                        mHintDialog.dismiss();
                                    }
                                });
                                showHintDialog(R.string.find_password_title,R.string.find_password_success,false);
                            }

                            @Override
                            public void onError(String s, String s1) {
                                ProgressUtils.dismissDialog();
                                showHintDialog(R.string.find_password_fail_title,s1,false);
                            }
                        });
            } else {
                ToastUtils.toast(getContext(),R.string.find_password_pass_length_error);
            }
        } else {
            ToastUtils.toast(getContext(),R.string.find_password_input_error);
        }
    }

    /**
     * 判断是否是手机号
     * @param mobile 手机号
     * @return true
     */
    private  boolean isMobile(String mobile){
        final Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        final Matcher m = p.matcher(mobile);
        return m.matches();
    }

    //获取验证码
    private void gainVerify() {
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
        TuyaUser.getUserInstance().getValidateCode(Constant.COUNTRY_CODE, mPhone, new IValidateCallback() {
            @Override
            public void onSuccess() {
                getActivity().getShowSendStateTv().setVisibility(View.VISIBLE);
                getActivity().getShowSendStateTv().setText(getContext().getString(
                        R.string.find_password_show_phone )+ mPhone);
                ToastUtils.toast(getContext(),R.string.register_gain_verify_success);
            }

            @Override
            public void onError(String s,final String s1) {
                mDownTimer.cancelTimer();
                verify.setEnabled(true);
                verify.setText(getActivity().getString(R.string.register_get_verify_code));
                verify.setTextColor(getActivity().getResources().getColor(R.color.login_text_color2));
                mHintDialog.setSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.equals(s1,getContext().getString(R.string.find_password_network_error))) {
                            isClickNext = false;
                            getActivity().nextPage(View.VISIBLE,View.GONE);
                            mRightButton.setText(R.string.register_next);
                        }
                        mHintDialog.dismiss();
                    }
                });
                showHintDialog(R.string.device_hint_text,s1,false);
            }
        });
    }

    private void setInputType(EditText editText, int inputType) {
        editText.setInputType(inputType);
    }
}
