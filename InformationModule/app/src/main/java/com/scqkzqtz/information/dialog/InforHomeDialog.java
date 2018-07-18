package com.scqkzqtz.information.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.scqkzqtz.information.MainActivity;
import com.scqkzqtz.information.R;
import com.scqkzqtz.information.databinding.DialogInforHomeBinding;
import com.scqkzqtz.information.entity.DBOperationSite;
import com.scqkzqtz.information.utils.OperationSiteUtils;

import java.util.List;

/**
 * Created by hghl on 2017/7/4.
 */

public class InforHomeDialog {
    private Dialog dialog;
    private DialogInforHomeBinding binding;
    private Context context;

    /**
     * @param context
     */
    public InforHomeDialog(Context context) {
        this.context = context;
        binding = DialogInforHomeBinding.inflate(LayoutInflater.from(context));
        dialog = new Dialog(context, R.style.custom_dialog);// 创建自定义样式dialog
//        dialog.setCancelable(false);// 可以用“返回键”取消
//        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        binding.dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.setContentView(binding.getRoot(), new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
    }

    public void dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void show(String str, final OnConfirmListener onConfirmListener) {
        binding.dialogContent.setText(str);
        binding.dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onConfirmListener) {
                    onConfirmListener.onSure();
                }
                dismiss();
            }
        });
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                if (null != onConfirmListener) {
//                    onConfirmListener.onSure();
//                }
//            }
//        });
        dialog.show();
    }


    String introduceUrl = "";

    /**
     * 跳转产品详情页
     *
     * @param status //CP-ZS正式版3    CP-GP股票池版4   CP-TG投顾版5
     */
    public void show(int status) {
        String url_status = "";
        switch (status) {
            case 3:
                url_status = "CP-ZS";
                break;
            case 4:
                url_status = "CP-GP";
                break;
            case 5:
                url_status = "CP-TG";
                break;
        }
        OperationSiteUtils.queryOperationSite(url_status, new OperationSiteUtils.HttpListener() {
            @Override
            public void succeed(List<DBOperationSite> list, String[] images) {
                if (list != null && list.size() > 0) {
                    introduceUrl = list.get(0).getUrl();
                }
            }
        });
        binding.dialogContent.setText("您还没有该权限哦，\n快去查看怎么获取权限吧！");
        binding.dialogSure.setText("立即查看");
        binding.dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(introduceUrl)) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("new_title", "");
                    intent.putExtra("url", introduceUrl);
                    intent.putExtra("title", "");
                    context.startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public interface OnConfirmListener {
        void onSure();
    }
}
