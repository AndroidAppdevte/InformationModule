package com.scqkzqtz.information.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.scqkzqtz.information.R;
import com.scqkzqtz.information.databinding.TextCardBinding;

import java.util.List;

/**
 * Created by hghl on 2017/6/6.
 */

public class InforGridAdapter extends BaseAdapter {

    private Context context;
    public List<String> nameData;
    //    private Drawable drawable1 ;
//    private Drawable drawable2 ;
    public int theChoice = 0;

    public InforGridAdapter(Context context, List<String> nameData) {
        this.context = context;
        this.nameData = nameData;
//        this.drawable1 = drawable1;
//        this.drawable2 = drawable2;
    }

    @Override
    public int getCount() {
        return  nameData != null ? nameData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (nameData != null)
            return nameData.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        TextCardBinding binding;
//        if (convertView == null) {
       // binding = TextCardBinding.inflate(LayoutInflater.from(context));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.text_card,viewGroup,false);

        convertView = binding.getRoot();
//        convertView.setTag(binding);
//        } else {
//            binding = (TextviewQkqBinding) convertView.getTag();
//        }
        Drawable drawable = null;
        if ( position == theChoice) {
            drawable = ContextCompat.getDrawable(context, R.drawable.coner_back_red3);
            binding.nameTeacher.setTextColor(context.getResources().getColor(R.color.color_white));
        } else {
            drawable = ContextCompat.getDrawable(context, R.drawable.coner_back_gray3);
            binding.nameTeacher.setTextColor(Color.parseColor("#333333"));
        }
        binding.rootText.setBackground(drawable);
        binding.nameTeacher.setText(nameData.get(position));
//        binding.nameTeacher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (position == 0) {
//                    return;
//                }
//                theChoice = position;
//                this.notifyAll();
//            }
//        });
        return convertView;
    }
}
