package com.saiyi.aircleaner.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.ShareBusiness;
import com.saiyi.aircleaner.listener.IListener;

/**
 * 文件描述：我的分享
 * 创建作者：黎丝军
 * 创建时间：2016/8/31 17:43
 */
public class MyShareFragment extends BaseFragment {

    //没有分享视图
    protected TextView mNoShareView;
    //有分享视图
    private View mHaveShareView;
    //装载分类的列表视图
    private RecyclerView mShareListRlv;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_my_share);
    }

    @Override
    public void findViews() {
        mNoShareView = getViewById(R.id.tv_no_share);
        mHaveShareView = getViewById(R.id.ll_have_share);
        mShareListRlv = getViewById(R.id.rv_share);
    }

    @Override
    public void initObjects() {
        mBusiness = new ShareBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mShareListRlv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public RecyclerView getShareListRlv() {
        return mShareListRlv;
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_ITEM_CLICK,mShareListRlv);
    }

    /**
     * 设置分享视图页面切换
     * @param noVisible
     * @param haveVisible
     */
    public void setShareViewVisible(int noVisible,int haveVisible) {
        mNoShareView.setVisibility(noVisible);
        mHaveShareView.setVisibility(haveVisible);
    }
}
