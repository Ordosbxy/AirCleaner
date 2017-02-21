package com.saiyi.aircleaner.blls;

import android.text.TextUtils;
import android.view.View;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.activity.AddShareActivity;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.util.ProgressUtils;
import com.saiyi.aircleaner.util.ToastUtils;
import com.tuya.smart.sdk.TuyaMember;
import com.tuya.smart.sdk.api.share.IAddMemberCallback;

/**
 * 文件描述：添加分享业务逻辑
 * 创建作者：黎丝军
 * 创建时间：2016/8/22 8:57
 */
public class AddShareBusiness extends AbsBaseBusiness<AddShareActivity>
        implements IAddMemberCallback{

    //涂鸦成员
    private TuyaMember mTuyaMember;

    @Override
    public void initObject() {
        mTuyaMember = new TuyaMember(getContext());
    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_share_device:
                final String name = getActivity().getShareNameEdt().getText().toString().trim();
                final String phone = getActivity().getSharePhoneEdt().getText().toString().trim();
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)) {
                    ProgressUtils.showDialog(getContext(),R.string.progress_adding_device);
                    mTuyaMember.addMember(Constant.COUNTRY_CODE,phone,name,null,this);
                } else {
                    ToastUtils.toast(getContext(),R.string.add_share_no_finish);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void actionBarLeftClick(View leftView) {
        getActivity().finish();
    }

    @Override
    public void onSuccess(Integer integer) {
        ProgressUtils.dismissDialog();
        ToastUtils.toast(getContext(),R.string.add_share_success);
        getActivity().setResult(getActivity().RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void onError(String code, String error) {
        ProgressUtils.dismissDialog();
        ToastUtils.toast(getContext(),error);
    }
}
