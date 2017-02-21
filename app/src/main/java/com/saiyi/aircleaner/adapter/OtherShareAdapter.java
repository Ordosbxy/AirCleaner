package com.saiyi.aircleaner.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.saiyi.aircleaner.R;
import com.tuya.smart.android.user.bean.GroupReceivedMemberBean;

/**
 * 文件描述：其他人的分享
 * 创建作者：黎丝军
 * 创建时间：2016/9/1 9:31
 */
public class OtherShareAdapter extends AbsBaseAdapter<GroupReceivedMemberBean,OtherShareAdapter.OtherShareViewHolder>{

    public OtherShareAdapter(Context context) {
        super(context, R.layout.listview_other_share_item);
    }

    @Override
    public OtherShareViewHolder onCreateVH(View itemView, int ViewType) {
        return new OtherShareViewHolder(itemView);
    }

    @Override
    public void onBindDataForItem(OtherShareViewHolder viewHolder, GroupReceivedMemberBean bean, int position) {
        if(bean.getDevice() != null) {
            if(bean.getDevice().get(0) != null) {
                viewHolder.deviceNameTv.setText(bean.getDevice().get(0).get(GroupReceivedMemberBean.TYPE_GW));
            }
        }
        viewHolder.accountTv.setText(bean.getUser().getMobile());
    }

    /**
     * 其他人的分享视图
     */
    public class OtherShareViewHolder extends BaseViewHolder {
        //设备名
        TextView deviceNameTv;
        //分享的账户
        TextView accountTv;

        public OtherShareViewHolder(View itemView) {
            super(itemView);
            deviceNameTv = getViewById(R.id.tv_other_device_name);
            accountTv = getViewById(R.id.tv_other_account);
        }
    }
}
