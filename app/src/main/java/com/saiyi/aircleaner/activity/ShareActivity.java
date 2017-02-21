package com.saiyi.aircleaner.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.adapter.ShareFragmentAdapter;
import com.saiyi.aircleaner.blls.ShareBusiness;
import com.saiyi.aircleaner.fragment.MyShareFragment;
import com.saiyi.aircleaner.fragment.OtherShareFragment;
import com.saiyi.aircleaner.other.Constant;

/**
 * 文件描述：分享界面
 * 创建作者：黎丝军
 * 创建时间：2016/8/31 17:29
 */
public class ShareActivity extends AbsBaseActivity {

    //选项卡
    private TabLayout mTabLayout;
    //滑动页
    private ViewPager mViewPager;
    //分享碎片
    private MyShareFragment mShareFragment;
    //适配器
    private ShareFragmentAdapter mShareAdapter;

    @Override
    public void onContentView() {
        setContentView(R.layout.activity_share);
    }

    @Override
    public void findViews() {
        mViewPager = getViewById(R.id.viewpager);
        mTabLayout = getViewById(R.id.tab_layout);
    }

    @Override
    public void initObjects() {
        mShareFragment = new MyShareFragment();
        mShareAdapter = new ShareFragmentAdapter(getSupportFragmentManager());
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.my_share_title);
        setTitleSize(Constant.TEXT_SIZE);
        setActionBarBackgroundColor(R.color.action_bar_color);
        actionBar.setLeftButtonBackground(R.mipmap.ic_back,25,25);

        mShareAdapter.putFragment(mShareFragment,"我的分享");
        mShareAdapter.putFragment(new OtherShareFragment(),"接收到的分享");
        mViewPager.setAdapter(mShareAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabTextColors(Color.GRAY,getResources().getColor(R.color.action_bar_color));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.action_bar_color));
    }

    @Override
    public void setListeners() {
        actionBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ((ShareBusiness)mShareFragment.getBusiness()).backHandle();
        }
        return true;
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }
}
