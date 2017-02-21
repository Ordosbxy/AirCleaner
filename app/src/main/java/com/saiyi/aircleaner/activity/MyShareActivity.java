package com.saiyi.aircleaner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.DeviceBusiness;
import com.saiyi.aircleaner.blls.MyShareBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.util.ProgressUtils;

/**
 * 文件描述：该界面是显示我的分享和别人分享给我的设备列表
 * 创建作者：黎丝军
 * 创建时间：2016/8/22 8:50
 */
public class MyShareActivity extends AbsBaseActivity{

    //没有分享视图
    private TextView mNoShareView;
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
        mBusiness = new MyShareBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.my_share_title2);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);
        mShareListRlv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_ITEM_CLICK,mShareListRlv);
        registerListener(IListener.ON_ACTION_BAR_LEFT_CLICK,actionBar);
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    public RecyclerView getShareListRlv() {
        return mShareListRlv;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    ((MyShareBusiness)mBusiness).updateList();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ((MyShareBusiness)mBusiness).backHandle();
        }
        return true;
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
