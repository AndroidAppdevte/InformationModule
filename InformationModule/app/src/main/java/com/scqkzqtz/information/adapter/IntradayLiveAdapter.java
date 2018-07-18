package com.scqkzqtz.information.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.avos.avoscloud.AVObject;
import com.scqkzqtz.information.R;
import com.scqkzqtz.information.databinding.IntradayLiveItemBinding;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 盘中直播适配器
 * Created by hef on 2017/11/8.
 */

public class IntradayLiveAdapter extends RecyclerView.Adapter<IntradayLiveAdapter.ViewHolder> {

    private Context context;
    private List<AVObject> dataList;
    private SimpleDateFormat sdf2;
    private SimpleDateFormat sdf3;

    public IntradayLiveAdapter(Context context, List<AVObject> dataList) {
        this.context = context;
        this.dataList = dataList;
        sdf2=new SimpleDateFormat("yyyy年MM月dd日");
        sdf3=new SimpleDateFormat("HH:mm:ss");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.intraday_live_item,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,int position) {
        IntradayLiveItemBinding binding= (IntradayLiveItemBinding) holder.getBinding();
        final AVObject avObject=dataList.get(position);

        try {
            binding.intradayLiveTopTime.setText(sdf2.format(avObject.getDate("createdAt")));
            binding.liveTime.setText(sdf3.format(avObject.getDate("createdAt")));
            CharSequence charSequence= Html.fromHtml(avObject.getString("content"));
            binding.liveContent.setText(charSequence);
//            binding.liveContent.setMovementMethod(LinkMovementMethod.getInstance());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }


}
