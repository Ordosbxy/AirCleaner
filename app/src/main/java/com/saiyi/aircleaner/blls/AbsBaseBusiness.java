package com.saiyi.aircleaner.blls;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.saiyi.aircleaner.AppHelper;
import com.saiyi.aircleaner.view.ActionBarView;
import com.saiyi.aircleaner.view.InfoHintDialog;

/**
 * 文件描述:处理界面的业务逻辑抽象基础类
 *         该业务类实现了基本的点击事件监听，主要处理了导航栏左右点击事件
 * 创建作者:黎丝军
 * 创建时间:16/7/28
 */
public abstract class AbsBaseBusiness<T> implements IBusiness<T>,View.OnClickListener{

    protected AppHelper appHelper;
    /**
     * 对应的目标UI界面类
     */
    private T mUi;
    //提示Dialog
    protected InfoHintDialog mHintDialog;

    public AbsBaseBusiness() {
        appHelper = AppHelper.instance();
    }

    @Override
    public void register(int eventType, View registerView, Object registerObj) {
        switch (eventType) {
            case ON_CLICK:
                registerView.setOnClickListener(this);
                break;
            case ON_ACTION_BAR_LEFT_CLICK:
                ((ActionBarView)registerView).setLeftClickListener(this);
                break;
            case ON_ACTION_BAR_RIGHT_CLICK:
                ((ActionBarView)registerView).setRightClickListener(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case ActionBarView.LEFT_BUTTON_ID:
                actionBarLeftClick(v);
                break;
            case ActionBarView.RIGHT_BUTTON_ID:
                actionBarRightClick(v);
                break;
            default:
                break;
        }
    }

    /**
     * 头部按钮左边点击事件
     * @param leftView
     */
    protected void actionBarLeftClick(View leftView) {
    }

    /**
     * 头部按钮右边点击事件
     * @param rightView
     */
    protected void actionBarRightClick(View rightView) {
    }

    @Override
    public void setT(T targetUi) {
        mUi = targetUi;
        mHintDialog = new InfoHintDialog(getContext());
    }

    @Override
    public T getFragment() {
        return mUi;
    }

    @Override
    public T getActivity() {
        return mUi;
    }

    public Context getContext() {
        if(mUi instanceof Fragment) {
           return ((Fragment)mUi).getContext();
        } else if(mUi instanceof Activity){
            return (Context)mUi;
        } else {
            return AppHelper.instance().getApplication();
        }
    }

    /**
     * 显示提示框
     * @param titleResId 标题资源Id
     * @param content 内容
     * @param isShowCancel 是否显示取消按钮
     */
    protected void showHintDialog(int titleResId,String content,boolean isShowCancel) {
        mHintDialog.setTitle(titleResId);
        mHintDialog.setContentText(content);
        if(isShowCancel) {
            mHintDialog.setCancelBtnVisibility(View.VISIBLE);
        } else {
            mHintDialog.setCancelBtnVisibility(View.GONE);
        }
        mHintDialog.show();
    }

    /**
     * 显示提示框
     * @param titleResId 标题资源Id
     * @param contentResId 内容资源Id
     * @param isShowCancel 是否显示取消按钮
     */
    protected void showHintDialog(int titleResId,int contentResId,boolean isShowCancel) {
        showHintDialog(titleResId,getContext().getString(contentResId),isShowCancel);
    }


    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
    }
}
