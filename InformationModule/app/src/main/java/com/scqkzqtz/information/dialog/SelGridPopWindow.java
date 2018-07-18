package com.scqkzqtz.information.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.scqkzqtz.information.R;
import com.scqkzqtz.information.adapter.InforGridAdapter;
import com.scqkzqtz.information.databinding.DialogInforCardBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hghl on 2017/6/6.
 */

public class SelGridPopWindow extends PopupWindow {
    private DialogInforCardBinding binding;
    private Context context;
    private InforGridAdapter adapter;
    private List<String> nameTeacher = new ArrayList<>();
    private Window window = null;

    /**
     * @param context
     * @param onClickListener
     */
    public SelGridPopWindow(Activity context, final OnClickListener onClickListener) {
        this.context = context;
        this.window = context.getWindow();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = binding.inflate(inflater);
        adapter = new InforGridAdapter(context, nameTeacher);

        binding.pupwinSelteacherGridview.setAdapter(adapter);
        binding.pupwinSelteacherGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == adapter.theChoice) {
                    return;
                }
                adapter.theChoice = position;
                adapter.notifyDataSetChanged();
                onClickListener.onClick(position);
                dismiss();
            }
        });
        binding.dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                onClickListener.onDismiss();
                WindowManager.LayoutParams lp = window .getAttributes();
                lp.alpha = 1f; window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                window.setAttributes(lp);
            }
        });

        //设置PopupWindow的View
        this.setContentView(binding.getRoot());
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
       // this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);

    }

    /**
     * @param view
     * @param nameTeacher 列表数据
     * @param theChoice   当前选择的位置
     */
    public void show(View view, List<String> nameTeacher, int theChoice) {
        adapter.nameData = nameTeacher;
        adapter.theChoice = theChoice;
        adapter.notifyDataSetChanged();
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置pop弹出窗体的背景
        this.setBackgroundDrawable(dw);
        showAsDropDown(view);
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = window .getAttributes();
        lp.alpha = 0.4f; window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(lp);
    }

    /**
     * @param view
     * @param nameTeacher 列表数据
     */
    public void show(View view, List<String> nameTeacher) {
        this.show(view, nameTeacher, adapter.theChoice);
    }

    public interface OnClickListener {
        void onClick(int position);//点击列表监听

        void onDismiss();//关闭监听
    }
}
