package com.saiyi.aircleaner.blls;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.saiyi.aircleaner.R;
import com.saiyi.aircleaner.adapter.MyShareAdapter;
import com.saiyi.aircleaner.fragment.MyShareFragment;
import com.saiyi.aircleaner.util.ProgressUtils;
import com.tuya.smart.android.user.bean.PersonBean;
import com.tuya.smart.sdk.TuyaMember;
import com.tuya.smart.sdk.api.share.IQueryMemberListCallback;

import java.util.ArrayList;

/**
 * 文件描述：我的分享界面的业务逻辑
 * 创建作者：黎丝军
 * 创建时间：2016/8/22 8:58
 */
public class ShareBusiness extends AbsBaseBusiness<MyShareFragment>
        implements IQueryMemberListCallback,MyShareAdapter.OnRemoveItemListener{

    //分享列表适配器
    private MyShareAdapter mShareAdapter;
    //用于操作分享
    private TuyaMember mTuyaMember;
    //数据观察
    private RecyclerView.AdapterDataObserver mDataObserver;

    @Override
    public void initObject() {
        mTuyaMember = new TuyaMember(getContext());
        mShareAdapter = new MyShareAdapter(getContext(),mTuyaMember);
        mDataObserver = new RecyclerView.AdapterDataObserver() {

            @Override
            public void onChanged() {
                if(mShareAdapter.getItemCount() <= 0) {
                    getFragment().setShareViewVisible(View.VISIBLE,View.GONE);
                } else {
                    getFragment().setShareViewVisible(View.GONE,View.VISIBLE);
                }
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                if(itemCount <= 0) {
                    getFragment().setShareViewVisible(View.VISIBLE,View.GONE);
                } else {
                    getFragment().setShareViewVisible(View.GONE,View.VISIBLE);
                }
            }
        };
    }

    @Override
    public void initData() {
        ProgressUtils.showDialog(getContext(), R.string.progress_loading_share);
        mTuyaMember.queryMemberList(this);
        mShareAdapter.registerAdapterDataObserver(mDataObserver);
        mShareAdapter.setOnRemoveItemListener(this);
        getFragment().getShareListRlv().setAdapter(mShareAdapter);
    }

    @Override
    public void onSuccess(ArrayList<PersonBean> arrayList) {
        ProgressUtils.dismissDialog();
        if(arrayList != null){
            mShareAdapter.setListData(arrayList);
            mShareAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String s, String s1) {
        ProgressUtils.dismissDialog();
        if(mShareAdapter.getItemCount() <= 0) {
            getFragment().setShareViewVisible(View.VISIBLE,View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        mShareAdapter.unregisterAdapterDataObserver(mDataObserver);
    }

    @Override
    public void onRemove() {
        updateList();
    }

    /**
     * 更新列表
     */
    public void updateList() {
        mTuyaMember.queryMemberList(this);
    }

    public void backHandle() {
        if(mShareAdapter.isSwipeOpen()) {
            mShareAdapter.closeAllItems();
        } else {
            getFragment().getActivity().finish();
        }
    }
}
