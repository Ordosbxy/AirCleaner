package com.saiyi.aircleaner.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.blls.ExplainBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.Constant;

/**
 * 文件描述：说明界面
 * 创建作者：黎丝军
 * 创建时间：2016/8/18 10:30
 */
public class ExplainActivity extends AbsBaseActivity {

    private WebView mExplainWv;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_explain);
    }

    @Override
    public void findViews() {
        mExplainWv = getViewById(R.id.wv_explain);
    }

    @Override
    public void initObjects() {
        mBusiness = new ExplainBusiness();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.explain_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);
        mExplainWv.loadUrl("file:///android_asset/explain.html");
        mExplainWv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
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
