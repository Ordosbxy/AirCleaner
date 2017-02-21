package com.saiyi.aircleaner.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.DeviceBusiness;
import com.saiyi.aircleaner.blls.TwoCodeBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;
import com.saiyi.aircleaner.other.twocode.camera.CameraManager;
import com.saiyi.aircleaner.other.twocode.view.ViewfinderView;

/**
 * 文件描述：二维码扫描界面
 * 创建作者：黎丝军
 * 创建时间：16/8/2 PM4:18
 */
public class TwoCodeActivity extends AbsBaseActivity {

    //用于显示照相机画面
    private SurfaceView surfaceView;
    //扫描二维码中间那个矩形框视图
    private ViewfinderView viewfinderView;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_two_code);
    }

    @Override
    public void findViews() {
        surfaceView = getViewById(R.id.preview_view);
        viewfinderView = getViewById(R.id.viewfinder_view);
    }

    @Override
    public void initObjects() {
        mBusiness = new TwoCodeBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.two_code_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);
        actionBar.setRightButtonText(R.string.two_code_right);
        actionBar.setRightButtonTextColor(Color.WHITE);
        CameraManager.init(getApplication());
        viewfinderView.setHintText(R.string.two_code_hint);
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_ACTION_BAR_LEFT_CLICK,actionBar);
        registerListener(IListener.ON_ACTION_BAR_RIGHT_CLICK,actionBar);
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case DeviceBusiness.SCAN_REQUEST_CODE:
                ((TwoCodeBusiness)mBusiness).handlePhoto(data);
                break;
            default:
                break;
        }
    }
}
