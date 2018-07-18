package com.scqkzqtz.information.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liaoinstan.springview.container.BaseFooter;
import com.scqkzqtz.information.R;

/**
 * Created by hghl on 2017/2/10.
 */

public class NewTransactionFooter extends BaseFooter {
    private TextView addMore;

    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.new_pull_to_refresh_footer, viewGroup, true);
        addMore = (TextView) view.findViewById(R.id.optional_footer);
        return view;
    }

    @Override
    public void onPreDrag(View rootView) {
        addMore.setText("上拉加载更多...");
    }

    @Override
    public void onDropAnim(View rootView, int dy) {

    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
        if (upORdown) {
            addMore.setText("松手立即加载...");
        } else {
            addMore.setText("上拉加载更多...");
        }
    }

    @Override
    public void onStartAnim() {
        addMore.setText("正在加载...");
    }

    @Override
    public void onFinishAnim() {

    }
}
