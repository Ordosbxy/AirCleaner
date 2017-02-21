package com.saiyi.aircleaner.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.util.ProgressUtils;
import com.saiyi.aircleaner.util.ToastUtils;
import com.saiyi.aircleaner.view.InfoHintDialog;
import com.tuya.smart.android.user.bean.PersonBean;
import com.tuya.smart.sdk.TuyaMember;
import com.tuya.smart.sdk.api.share.IRemoveMemberCallback;

/**
 * 文件描述：我的分享列表适配器
 * 创建作者：黎丝军
 * 创建时间：2016/8/22 13:49
 */
public class MyShareAdapter extends AbsSwipeAdapter<PersonBean,MyShareAdapter.MyShareViewHolder> {

    //判断是否有侧滑视图打开
    private boolean isSwipeOpen = false;
    //用于移除分享
    private TuyaMember mTuyaMember;
    //用于监听移除分享
    private OnRemoveItemListener mListener;

    public MyShareAdapter(Context context, TuyaMember member) {
        super(context, R.layout.listview_share_item);
        mTuyaMember = member;
    }

    @Override
    public MyShareViewHolder onCreateVH(View itemView, int ViewType) {
        return new MyShareViewHolder(itemView);
    }

    @Override
    public void onBindDataForItem(final MyShareViewHolder viewHolder, final PersonBean bean,final int position) {
        viewHolder.accountTv.setText(bean.getMobile());
        viewHolder.nickNameTv.setText(bean.getName());
        bindSwipeLayout(viewHolder.itemSwipe,position);

    }

    @Override
    protected void setItemListeners(final MyShareViewHolder holder, final PersonBean personBean,final  int position) {
        super.setItemListeners(holder, personBean, position);
        holder.itemSwipe.addSwipeListener(mSwipeListener);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHandle(holder,personBean,position);
        }});
    }

    /**
     * 处理删除事件
     * @param holder holder
     * @param personBean bean
     * @param position 位置
     */
    private void deleteHandle(final MyShareViewHolder holder, final PersonBean personBean,final  int position) {
        final InfoHintDialog dialog = new InfoHintDialog(getContext());
        dialog.setTitle(R.string.device_hint_text);
        dialog.setContentText(R.string.my_share_remove_share);
        dialog.setCancelBtnVisibility(View.VISIBLE);
        dialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ProgressUtils.showDialog(getContext(),R.string.progress_removing_share);
                mTuyaMember.removeMember(personBean.getId(), new IRemoveMemberCallback() {
                    @Override
                    public void onSuccess() {
                        isSwipeOpen = false;
                        ProgressUtils.dismissDialog();
                        removeShownLayouts(holder.itemSwipe);
                        removeData(position);
                        closeAllItems();
                        if(mListener != null) mListener.onRemove();
                        ToastUtils.toast(getContext(),R.string.my_share_remove_share_success);
                    }
                    @Override
                    public void onError(String s, String s1) {
                        ProgressUtils.dismissDialog();
                        ToastUtils.toast(getContext(),R.string.my_share_remove_share_fail);
                    }
                });
            }
        });
        dialog.show();
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.sl_share;
    }

    /**
     * 设置移除分享监听器
     * @param listener
     */
    public void setOnRemoveItemListener(OnRemoveItemListener listener) {
        mListener = listener;
    }

    /**
     * 用于复用视图
     */
    class MyShareViewHolder extends BaseViewHolder {
        //账号
        TextView accountTv;
        //昵称
        TextView nickNameTv;
        //删除
        Button deleteBtn;
        //swipe视图
        SwipeLayout itemSwipe;

        public MyShareViewHolder(View itemView) {
            super(itemView);
            accountTv = getViewById(R.id.tv_share_account);
            nickNameTv = getViewById(R.id.tv_share_name);
            deleteBtn = getViewById(R.id.btn_share_delete);
            itemSwipe = getViewById(R.id.sl_share);
        }
    }

    /**
     * 移除分享监听器
     */
    public interface OnRemoveItemListener {
        //移除分享
        void onRemove();
    }

    /**
     * 用于监听侧滑过程
     */
    private SwipeLayout.SwipeListener mSwipeListener = new SimpleSwipeListener() {
        @Override
        public void onStartOpen(SwipeLayout swipeLayout) {
            closeAllItems();
        }

        @Override
        public void onOpen(SwipeLayout layout) {
            isSwipeOpen = true;
        }

        @Override
        public void onStartClose(SwipeLayout layout) {
            isSwipeOpen = false;
        }
    };

    /**
     * 判断是item的侧滑视图是否打开，用于按返回键时判断
     * @return false表示没有打开，否则表示打开
     */
    public boolean isSwipeOpen() {
        return isSwipeOpen;
    }
}
