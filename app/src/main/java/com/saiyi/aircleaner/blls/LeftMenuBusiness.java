package com.saiyi.aircleaner.blls;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.AboutActivity;
import com.saiyi.aircleaner.activity.ExplainActivity;
import com.saiyi.aircleaner.activity.LoginActivity;
import com.saiyi.aircleaner.activity.MyShareActivity;
import com.saiyi.aircleaner.activity.ShareActivity;
import com.saiyi.aircleaner.fragment.LeftMenuFragment;
import com.saiyi.aircleaner.fragment.MyShareFragment;
import com.saiyi.aircleaner.listener.IFilterChangeListener;
import com.saiyi.aircleaner.listener.ListenersMgr;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.util.PreferencesUtils;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.sdk.TuyaUser;

/**
 * 文件描述：左菜单业务类
 * 创建作者：黎丝军
 * 创建时间：16/8/5 PM3:27
 */
public class LeftMenuBusiness extends AbsBaseBusiness<LeftMenuFragment>
        implements CompoundButton.OnCheckedChangeListener{

    @Override
    public void initObject() {
    }

    @Override
    public void initData() {
        final User user = TuyaUser.getUserInstance().getUser();
        if (user != null) {
            final String phone = user.getMobile().substring(3, user.getMobile().length());
            getActivity().getUserNameTv().setText(user.getUsername());
            getActivity().getUserPhoneTv().setText(phone);
        }
    }

    @Override
    public void register(int eventType, View registerView, Object registerObj) {
        super.register(eventType, registerView, registerObj);
        switch (eventType) {
            case ON_CHECK:
                ((CheckBox) registerView).setOnCheckedChangeListener(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reader:
                getActivity().startActivity(ExplainActivity.class);
                break;
            case R.id.tv_about:
                getActivity().startActivity(AboutActivity.class);
                break;
            case R.id.tv_share:
                getActivity().startActivity(MyShareActivity.class);
                break;
            case R.id.tv_quit:
                mHintDialog.setSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHintDialog.dismiss();
                        TuyaUser.getUserInstance().removeUser();
                        PreferencesUtils.remove(Constant.USER_ACCOUNT);
                        PreferencesUtils.remove(Constant.USER_PASSWORD);
                        appHelper.finishAllActivity();
                        getFragment().startActivity(LoginActivity.class);
                    }
                });
                showHintDialog(R.string.left_menu_quit, R.string.left_menu_quit_hint, true);
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        ListenersMgr.getInstance().removeListener(IFilterChangeListener.class);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        PreferencesUtils.putBoolean(Constant.REMIND, isChecked);
    }

}
