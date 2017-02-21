package com.saiyi.aircleaner.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.saiyi.aircleaner.AppHelper;
import com.saiyi.aircleaner.blls.IBusiness;
import com.saiyi.aircleaner.listener.IListener;
import com.saiyi.aircleaner.other.IUi;
import com.saiyi.aircleaner.other.SystemBarTintManager;
import com.saiyi.aircleaner.view.ActionBarView;
import com.saiyi.aircleaner.view.slidemenu.SlideMenuMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：抽象基本activity，该类基类主要抽象了一些界面初始化的一些方法
 *          比如findViews，initObjects，initData，setListeners等方法用来规范
 *          界面初始化时有序不乱。并且我也写了一个getViewById()这个方法来获取UI控件对象
 *          这样你在使用时，就不需要再进行强制类型转换了，而废弃了findViewById这个方法。
 *          注意，如果有业务类需要将mBusiness在子类实现的initObject方法实例化具体的业务类，否则无效
 * 创建作者：黎丝军
 * 创建时间：16/4/26
 */
public abstract class AbsBaseActivity extends AppCompatActivity implements IUi {

    //权限请求
    protected static final int REQUEST_CODE = 111;
    //业务接口实例
    protected IBusiness mBusiness;
    //头部导航栏
    protected ActionBarView actionBar;
    //该容器是带有头部菜单栏
    private LinearLayout mRootContainer;
    //子容器
    private FrameLayout mChildContainer;
    //侧滑菜单管理器
    public SlideMenuMgr slideMgr;
    /**
     * 在后面子类中必须使用onCreateUi方法来替代,
     * 否则运行或许会出错
     */
    @Deprecated
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppHelper.instance().putActivity(this);
        if(isSlideMenu()) {
            slideMgr = SlideMenuMgr.instance();
            slideMgr.createSlideMenu(this);
        }
        initBaseLayout();
        onContentView();
        findViews();
        initObjects();
        initBusinessObjects();
        initData(savedInstanceState);
        initBusinessData();
        setListeners();
    }

    /**
     * 初始化基本布局，带有自定义头部导航栏
     */
    private void initBaseLayout() {
        if(isActionBar() || isSlideMenu()) {
            mRootContainer = new LinearLayout(this);
            mRootContainer.setOrientation(LinearLayout.VERTICAL);
            mRootContainer.setFitsSystemWindows(true);
            mRootContainer.setClipToPadding(false);
            mRootContainer.setBackgroundColor(Color.WHITE);
            mRootContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            if(isActionBar()) {
                actionBar = new ActionBarView(this);
                actionBar.setBackgroundColor(Color.parseColor("#50B2F3"));
                mRootContainer.addView(actionBar);
            }

            mChildContainer = getContentLayout();
            mChildContainer.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            mRootContainer.addView(mChildContainer);
            setStatusBarBackgroundColor(Color.parseColor("#50B2F3"));
        }
    }

    /**
     * 判断是否需要头部栏
     * @return false表示不要，否则表示要
     */
    protected boolean isActionBar() {
        return false;
    }

    /**
     * 获取子类的内容布局
     * @return 返回内容布局
     */
    protected FrameLayout getContentLayout() {
        return new FrameLayout(this);
    }

    @Override
    public void setTitle(CharSequence title) {
        if(actionBar != null) {
            actionBar.setActionBarTitle(title);
        } else {
            super.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        if(actionBar != null) {
            actionBar.setActionBarTitle(titleId);
        } else {
            super.setTitle(titleId);
        }
    }

    @Override
    public void setTitleColor(int textColor) {
        if(actionBar != null) {
            actionBar.setActionBarTitleColor(textColor);
        } else {
            super.setTitleColor(textColor);
        }
    }

    /**
     * 设置标题文本大小
     * @param textSize 文本大小
     */
    public void setTitleSize(float textSize) {
        if(actionBar != null) actionBar.setActionBarTitleSize(textSize);
    }

    /**
     * 设置头部横条的高度
     * @param height 高度值
     */
    protected void setActionBarHeight(int height) {
        if(actionBar != null) actionBar.setActionBarLayoutHeight(height);
    }

    /**
     * 设置头部横条的背景颜色
     * @param colorResId 颜色值
     */
    protected void setActionBarBackgroundColor(int colorResId) {
        if(actionBar != null) actionBar.setBackgroundColor(getResources().getColor(colorResId));
    }

    @Override
    public void setContentView(int layoutResID) {
        if(isActionBar()) {
            if(isSlideMenu()) {
                mChildContainer.addView(LayoutInflater.from(this).inflate(layoutResID,null));
                slideMgr.builderSlideMenu(mRootContainer,getSlideMenuFragment());
                getWindow().setContentView(slideMgr.getSlideMenu());
            } else {
                setContentView(LayoutInflater.from(this).inflate(layoutResID,null));
            }
        } else {
            if(isSlideMenu()) {
                mChildContainer.addView(LayoutInflater.from(this).inflate(layoutResID,null));
                slideMgr.builderSlideMenu(mRootContainer,getSlideMenuFragment());
                getWindow().setContentView(slideMgr.getSlideMenu());
            } else {
                super.setContentView(layoutResID);
            }
        }
    }

    @Override
    public void setContentView(View view) {
        if(isActionBar()) {
            mChildContainer.addView(view);
            getWindow().setContentView(mRootContainer);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if(isActionBar()) {
            mChildContainer.addView(view,params);
            getWindow().setContentView(mRootContainer);
        } else {
            super.setContentView(view,params);
        }
    }


    /**
     * 初始化业务类里的对象
     */
    private void initBusinessObjects() {
        if(isBusiness()) {
            mBusiness.setT(this);
            mBusiness.initObject();
        }
    }

    /**
     * 初始化业务类里的数据
     */
    private void initBusinessData() {
        if(isBusiness()) {
            mBusiness.initData();
        }
    }

    /**
     * 判断业务类是否为空
     * @return true表示不为空,否则表示为空
     */
    private boolean isBusiness() {
        if(mBusiness == null) {
            mBusiness = getBusiness();
        }
        return mBusiness != null;
    }

    @Override
    public <T> T getViewById(int id) {
        return (T) findViewById(id);
    }

    /**
     * 配置的颜色Id设置状态背景颜色
     * @param color 颜色值
     */
    public void setStatusBarBackgroundColor(int color) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 注册视图监听对象
     * @param eventType 事件类型
     * @param registerView 注册视图
     */
    protected void registerListener(int eventType,View registerView) {
        final IListener listenerMgr = getBusiness();
        if(listenerMgr != null) {
            listenerMgr.register(eventType,registerView,null);
        }
    }

    /**
     * 注册视图监听对象
     * @param eventType 事件类型
     * @param registerObj 需要注册的对象
     */
    protected void registerListener(int eventType,Object registerObj) {
        final IListener listenerMgr = getBusiness();
        if(listenerMgr != null) {
            listenerMgr.register(eventType,null,registerObj);
        }
    }

    /**
     * 获取业务类
     * @return IBusiness实例
     */
    public IBusiness getBusiness() {
        return mBusiness;
    }

    /**
     * 在子Activity中不建议使用这个方法，而使用getViewById来替代了
     * @param id 视图资源Id
     * @return 返回对应的视图实例
     */
    @Deprecated
    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }

    /**
     * 启动另一个Activity
     * @param activityCl 需要启动的Activity
     */
    public void startActivity(Class<?> activityCl) {
        startActivity(new Intent(this,activityCl));
    }

    /**
     * 根据指定视图容器Id来添加碎片
     * @param containId      视图容器Id
     * @param targetFragment 需要添加的碎片对象
     */
    public void addFragment(int containId, Fragment targetFragment) {
        addFragment(containId, targetFragment, null);
    }

    /**
     * 根据指定视图容器Id来添加碎片
     * @param containId      视图容器Id
     * @param targetFragment 需要添加的碎片对象
     * @param tagName        碎片标签名
     */
    public void addFragment(int containId, Fragment targetFragment, String tagName) {
        if (tagName != null) {
            getSupportFragmentManager().beginTransaction().add(containId, targetFragment, tagName).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(containId, targetFragment).commit();
        }
    }

    /**
     * 隐藏指定的碎片
     * @param targetFragment 需要隐藏的碎片对象
     */
    public void hideFragment(Fragment targetFragment) {
        getSupportFragmentManager().beginTransaction().hide(targetFragment).commit();
    }

    /**
     * 移除指定碎片
     * @param targetFragment 需要移除的碎片对象
     */
    public void removeFragment(Fragment targetFragment) {
        getSupportFragmentManager().beginTransaction().remove(targetFragment).commit();
    }

    /**
     * 替换某个视图Id对应位置的碎片
     * @param containId      需要替换的视图容器Id
     * @param targetFragment 需要替换的碎片对象
     */
    public void replaceFragment(int containId, Fragment targetFragment) {
        replaceFragment(containId, targetFragment, null);
    }

    /**
     * 显示目标碎片
     * @param targetFragment 目标碎片
     */
    public void showFragment(Fragment targetFragment) {
        getSupportFragmentManager().beginTransaction().show(targetFragment).commit();
    }

    /**
     * 替换某个视图Id对应位置的碎片
     * @param containId      需要替换的视图容器Id
     * @param targetFragment 需要替换的碎片对象
     * @param tagName        碎片标签名
     */
    public void replaceFragment(int containId, Fragment targetFragment, String tagName) {
        if (tagName != null) {
            getSupportFragmentManager().beginTransaction().replace(containId, targetFragment, tagName).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(containId, targetFragment).commit();
        }
    }

    /**
     * 设置Fragment进入或退出的方式
     * @param enterResId 进入动画资源Id
     * @param exitResId  退出时需要播放的资源Id
     */
    public void setCustomAnimations(int enterResId, int exitResId) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(enterResId, exitResId).commit();
    }

    /**
     * 添加碎片，此方法在添加碎片时会把原来的删除
     *
     * @param targetFragment 目标碎片
     */
    public void detach(Fragment targetFragment) {
        getSupportFragmentManager().beginTransaction().detach(targetFragment).commit();
    }

    @Override
    protected void onResume() {
        if(isBusiness()) {
            mBusiness.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(isBusiness()) {
            mBusiness.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(isBusiness()) {
            mBusiness.onDestroy();
        }
        AppHelper.instance().removeActivity(this);
        super.onDestroy();
    }

    protected boolean isSlideMenu() {
        return false;
    }

    protected Fragment getSlideMenuFragment() {
        return null;
    }

    //获取权限
    protected String[] getPermissions() {
        final String[] permissions = new String[12];
        permissions[0] = Manifest.permission.INTERNET;
        permissions[1] = Manifest.permission.CHANGE_NETWORK_STATE;
        permissions[2] = Manifest.permission.CHANGE_WIFI_STATE;
        permissions[3] = Manifest.permission.ACCESS_NETWORK_STATE;
        permissions[4] = Manifest.permission.ACCESS_WIFI_STATE;
        permissions[5] = Manifest.permission.CHANGE_WIFI_MULTICAST_STATE;
        permissions[6] = Manifest.permission.WAKE_LOCK;
        permissions[7] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        permissions[8] = Manifest.permission.READ_PHONE_STATE;
        permissions[9] = Manifest.permission.READ_EXTERNAL_STORAGE;
        permissions[10] = Manifest.permission.ACCESS_COARSE_LOCATION;
        permissions[11] = Manifest.permission.ACCESS_FINE_LOCATION;
        return permissions;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if(requestCode == REQUEST_CODE){
            PackageManager pm=getPackageManager();
            String[] rights=null;
            final List<String> deniedPermissions=new ArrayList<>();
            try{
                PackageInfo pi=pm.getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
                rights=pi.requestedPermissions;
            }catch (Exception e){
                e.printStackTrace();
            }
            if(rights!=null){
                for(int i=0;i<permissions.length;i++){
                    if(grantResults[i]==PackageManager.PERMISSION_DENIED){
                        for(int j=0;j<rights.length;j++){
                            if(rights[j].equals(permissions[i])){
                                String permission=permissions[i];
                                permission=permission.substring(permission.lastIndexOf('.')+1);
                                deniedPermissions.add(permission);
                                break;
                            }
                        }
                    }
                }
            }
            if(deniedPermissions.size()>0){
                StringBuffer temp=new StringBuffer();
                for(String s:deniedPermissions){
                    temp.append(s+"/");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}