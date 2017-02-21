package com.saiyi.aircleaner.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.saiyi.aircleaner.R;

/**
 * 文件描述：任何简单进度弹出框
 * 创建作者：黎丝军
 * 创建时间：16/8/8 PM12:19
 */
public class ProgressUtils {

    //弹出框
    private static AlertDialog dialog = null;
    //视图
    private static View dialogView = null;

    private ProgressUtils() {
    }

    /**
     * 显示弹出框
     */
    public static void showDialog(Context context) {
        showDialog(context,null,false);
    }

    /**
     * 显示弹出框
     */
    public static void showDialog(Context context,String info) {
        showDialog(context,info,false);
    }

    /**
     * 显示弹出框
     */
    public static void showDialog(Context context,int resId) {
        showDialog(context,context.getString(resId),false);
    }

    /**
     * 显示弹出框
     * @param context 运行环境
     * @param cancelable 点击返回是否能被取消
     */
    public static void showDialog(Context context,boolean cancelable) {
        showDialog(context,null,cancelable);
    }

    /**
     * 显示弹出框
     * @param message 提示信息
     */
    public static void showDialog(Context context,String message,boolean cancelable) {
        dialogView = ResourcesUtils.findViewById(context, R.layout.dialog_progress);
        TextView tvMessage = (TextView) dialogView.findViewById(R.id.tv_dialog_msg);
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setText(message);
        } else {
        }
        dialog = new AlertDialog.Builder(context).create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(cancelable);
        dialog.show();
        dialog.setContentView(dialogView);
    }

    /**
     * 取消弹出框
     */
    public static void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
