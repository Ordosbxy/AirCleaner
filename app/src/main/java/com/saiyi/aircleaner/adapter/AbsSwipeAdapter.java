package com.saiyi.aircleaner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;

import java.util.List;

/**
 * 文件描述：适配列表item带侧滑布局的数据适配器
 * 创建作者：黎丝军
 * 创建时间：16/8/1 PM1:46
 */
public abstract class AbsSwipeAdapter<T,VH extends RecyclerView.ViewHolder> extends AbsBaseAdapter<T,VH>
        implements SwipeItemMangerInterface, SwipeAdapterInterface {

    //用于管理item收放
    private SwipeItemRecyclerMangerImpl mItemManger;

    public AbsSwipeAdapter(Context context, int layoutId) {
        super(context, layoutId);
        mItemManger = new SwipeItemRecyclerMangerImpl(this);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        mItemManger.setMode(mode);
    }

    /**
     * 绑定视图
     * @param swipeLayout swipe视图
     * @param position 位置
     */
    protected void bindSwipeLayout(View swipeLayout,int position) {
        mItemManger.bindView(swipeLayout,position);
    }
}
