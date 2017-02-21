package com.saiyi.aircleaner.fragment;

import android.os.Bundle;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.OtherShareBusiness;

/**
 * 文件描述：其他人的分享
 * 创建作者：黎丝军
 * 创建时间：2016/8/31 17:46
 */
public class OtherShareFragment extends MyShareFragment {

    @Override
    public void initObjects() {
        mBusiness = new OtherShareBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mNoShareView.setText(R.string.my_share_other);
    }
}
