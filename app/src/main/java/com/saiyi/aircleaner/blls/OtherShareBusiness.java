package com.saiyi.aircleaner.blls;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.saiyi.aircleaner.adapter.OtherShareAdapter;
import com.saiyi.aircleaner.fragment.OtherShareFragment;
import com.tuya.smart.android.user.bean.GroupReceivedMemberBean;
import com.tuya.smart.sdk.TuyaMember;
import com.tuya.smart.sdk.api.share.IQueryReceiveMemberListCallback;

import java.util.ArrayList;

/**
 * 文件描述：其他分享
 * 创建作者：黎丝军
 * 创建时间：2016/9/1 9:12
 */
public class OtherShareBusiness extends AbsBaseBusiness<OtherShareFragment>
        implements IQueryReceiveMemberListCallback{

    private OtherShareAdapter mAdapter;
    //用于操作分享
    private TuyaMember mTuyaMember;
    //数据观察
    private RecyclerView.AdapterDataObserver mDataObserver;

    @Override
    public void initObject() {
        mTuyaMember = new TuyaMember(getContext());
        mAdapter = new OtherShareAdapter(getContext());
        mDataObserver = new RecyclerView.AdapterDataObserver() {

            @Override
            public void onChanged() {
                if(mAdapter.getItemCount() <= 0) {
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
        mTuyaMember.queryReceiveMemberList(this);
        mAdapter.registerAdapterDataObserver(mDataObserver);
        getFragment().getShareListRlv().setAdapter(mAdapter);
    }

    @Override
    public void onError(String s, String s1) {
        if(mAdapter.getItemCount() <= 0) {
            getFragment().setShareViewVisible(View.VISIBLE,View.GONE);
        }
    }

    @Override
    public void onSuccess(ArrayList<GroupReceivedMemberBean> arrayList) {
        if(arrayList != null){
            mAdapter.setListData(arrayList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        mAdapter.unregisterAdapterDataObserver(mDataObserver);
    }

}
