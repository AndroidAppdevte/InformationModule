package com.scqkzqtz.information.widget;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liaoinstan.springview.container.BaseHeader;
import com.scqkzqtz.information.R;
import com.scqkzqtz.information.utils.Utils;


/**
 *可改变文字状态   需要改变springView.setType(SpringView.Type.FOLLOW);
 */

public class NewTransactionHeader extends BaseHeader {
    private TextView textViewTime,textViewTag;
    private ProgressBar mProgressBar;
    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.new_pull_to_refresh_header, viewGroup, true);
        mProgressBar=(ProgressBar)view.findViewById(R.id.header_img);

        if (android.os.Build.VERSION.SDK_INT > 22) {//android 6.0替换clip的加载动画
            final Drawable drawable =  viewGroup.getContext().
                    getApplicationContext().getResources()
                    .getDrawable(R.drawable.refresh_progress_loading);
            mProgressBar.setIndeterminateDrawable(drawable);
        }
        textViewTime = (TextView)view.findViewById(R.id.optional_header_time);
        textViewTag = (TextView)view.findViewById(R.id.optional_header_tag);
        textViewTime.setText("最后更新: 今天  "+ Utils.getHourTime());
        return view;

    }


    @Override
    public void onPreDrag(View rootView) {
        textViewTag.setText("下拉刷新");
    }

    @Override
    public void onDropAnim(View rootView, int dy) {

    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
        if (upORdown) {
            textViewTag.setText("下拉刷新");
        } else {
            textViewTag.setText("松手立即刷新");
        }
    }

    @Override
    public void onStartAnim() {
        textViewTag.setText("正在刷新");
    }

    @Override
    public void onFinishAnim() {
        textViewTime.setText("最后更新: 今天  " + Utils.getHourTime());
    }
}
