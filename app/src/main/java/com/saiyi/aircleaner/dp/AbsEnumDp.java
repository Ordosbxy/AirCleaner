package com.saiyi.aircleaner.dp;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckBox;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 文件描述：
 * 创建作者：黎丝军
 * 创建时间：2016/9/2 15:42
 */
public abstract class AbsEnumDp<E extends Enum> extends AbsDp{

    //目标枚举值
    protected E mEnum;
    //点击次数
    protected int mClickCount = 0;
    //保存老的值
    private int mOldClickCount = 0;
    //按钮
    protected CheckBox mCommonBox;
    //用于存储背景改变drawable Id
    private Map<E,Drawable> mDrawId;
    //显示文本值
    private Map<E,Object> mTextMap;
    //根据点击次数来存储枚举类型值
    private Map<Integer,E> mEnumMap;
    //用于保存现在背景
    private Drawable mTopDraw;

    public AbsEnumDp(CheckBox commonBox) {
        super(commonBox);
        mCommonBox = commonBox;
        mDrawId = new HashMap<>();
        mTextMap = new HashMap<>();
        mEnumMap = new HashMap<>();
    }

    @Override
    protected void networkConnect(View v) {
        mOldClickCount = mClickCount;
        if(mClickCount >= mEnumMap.size() - 1) {
            mClickCount = 0;
        } else {
            mClickCount ++;
        }
        updateState();
        sendDp();
    }

    @Override
    protected void networkNoConnect(View v) {
        mCommonBox.setChecked(currentState);
    }

    @Override
    protected void switchOpenHandle() {
        if(isBright() && switchOpen) {
            mCommonBox.setChecked(true);
        } else {
            mCommonBox.setChecked(false);
        }
    }

    @Override
    public Object getDpValue() {
        return mEnum;
    }

    @Override
    public void changeState(boolean isSwitch) {
        super.changeState(isSwitch);
        if(switchOpen) {
            mCommonBox.setChecked(currentState);
            mCommonBox.setEnabled(true);
        } else {
            mCommonBox.setChecked(false);
            mCommonBox.setEnabled(false);
        }
    }

    @Override
    protected void onSendSuccess() {
    }

    @Override
    protected void onFail(String code, String error) {
        mClickCount = mOldClickCount;
        updateState();
    }

    @Override
    public void setDpValue(Object dpValue) {
        mEnum = (E) dpValue;
        mOldClickCount = mClickCount = getClickCount(mEnum);
        updateState();
    }

    /**
     * 根据键来获取点击的值
     * @param key 键
     * @return 点击次数
     */
    private int getClickCount(E key) {
        final Set set = mEnumMap.keySet();
        final Iterator<Integer> iterator = set.iterator();
        E value;
        int clickCount;
        while (iterator.hasNext()) {
            clickCount = iterator.next();
            value = mEnumMap.get(clickCount);
            if (key == value) {
                return clickCount;
            }
        }
        return mOldClickCount;
    }


    /**
     * 更新状态值
     */
    private void updateState() {
        mEnum = getEnum(mClickCount);
        if(isChangeDrawable()) {
            mTopDraw = getDrawable(mEnum);
            mTopDraw.setBounds(0, 0,mTopDraw.getIntrinsicWidth() , mTopDraw.getIntrinsicHeight());
            mCommonBox.setCompoundDrawables(null,mTopDraw,null,null);
        }
        if(isChangeText()) {
            mCommonBox.setText(getText(mEnum));
        }
        switchOpenHandle();
    }

    /**
     * 添加变化的背景资源drawable
     * @param key 键
     * @param drawResId 资源ID
     */
    public void putDrawable( E key, int drawResId) {
        putDrawable(key,drawResId,0);
    }

    /**
     * 添加变化的文本
     * @param key 键
     * @param textResId 资源ID
     */
    public void putText(E key, int textResId) {
        putDrawable(key,0,textResId);
    }

    /**
     * 添加类型值
     * @param key 键
     * @param value 值
     */
    public void putObject(E key, Object value) {
        mTextMap.put(key,value);
        mEnumMap.put(mTextMap.size() - 1,key);
    }

    /**
     * 添加变化的背景资源drawable
     * @param key 键
     * @param drawResId 资源ID
     * @param textResId 文本资源Id，如果不需要只需传值为0就可以了
     */
    public void putDrawable(E key, int drawResId,int textResId) {
        if(isChangeDrawable() && drawResId != 0) {
            mDrawId.put(key,getContext().getResources().getDrawable(drawResId));
            mEnumMap.put(mDrawId.size() - 1,key);
        }
        if(isChangeText() && textResId != 0) {
            mTextMap.put(key,getContext().getString(textResId));
            mEnumMap.put(mTextMap.size() - 1,key);
        }
    }

    /**
     * 获取背景drawable
     * @param key 键
     * @return drawable实例
     */
    protected Drawable getDrawable(E key) {
        return mDrawId.get(key);
    }

    /**
     * 根据键获取对应的文本值
     * @param key 键
     * @return 文本值
     */
    protected String getText(E key) {
        return (String)mTextMap.get(key);
    }

    /**
     * 获取对象值
     * @param key 键
     * @return 对象
     */
    protected Object getObject(E key) {
        return mTextMap.get(key);
    }

    /**
     * 获取boolean类型值
     * @param key 键
     * @return 类型值
     */
    protected boolean getBoolean(E key) {
        return (boolean)mTextMap.get(key);
    }

    /**
     * 根据点击次数来获取相应的枚举值
     * @param clickCount 点击次数
     * @return 对应的枚举
     */
    protected E getEnum(int clickCount){
        return mEnumMap.get(clickCount);
    }

    /**
     * 控件是否保持常亮
     * @return true表示保持常亮，否则相反
     */
    protected boolean isBright() {
        return true;
    }

    /**
     * 是否需要视图改变其文本
     * @return 默认是false,true表示需要
     */
    public boolean isChangeText() {
        return false;
    }

    /**
     * 是否需要改变其背景
     * @return 不需要则返回false
     */
    public boolean isChangeDrawable() {
        return true;
    }
}
