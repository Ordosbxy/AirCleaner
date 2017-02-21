package com.saiyi.aircleaner.activity;

import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.AboutBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;

/**
 * 文件描述：关于界面
 * 创建作者：黎丝军
 * 创建时间：16/7/28 PM4:12
 */
public class AboutActivity extends AbsBaseActivity{

    private WebView mAboutWv;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    public void findViews() {
        mAboutWv = getViewById(R.id.wv_about);
    }

    @Override
    public void initObjects() {
        mBusiness = new AboutBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.about_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);

        mAboutWv.loadUrl("file:///android_asset/about.html");
    }

    @Override
    public void setListeners() {
        registerListener(IListener.ON_ACTION_BAR_LEFT_CLICK,actionBar);
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }
}
