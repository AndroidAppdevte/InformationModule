package com.scqkzqtz.information.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.scqkzqtz.information.R;
import com.scqkzqtz.information.databinding.CommonBannerItemBinding;
import com.scqkzqtz.information.databinding.InforItemBinding;
import com.scqkzqtz.information.databinding.InforTextItemBinding;
import com.scqkzqtz.information.entity.CommonInforEntity;

import java.util.ArrayList;
import java.util.List;

public class CommonTextInforAdapter extends RecyclerView.Adapter{

    private Context mContext;
    public List<CommonInforEntity> listData = new ArrayList<>();
    public List<CommonInforEntity> listBanner = new ArrayList<>();
    public CommonTextInforAdapter(Context mContext, List<CommonInforEntity> listData) {
        this.mContext = mContext;
        this.listData = listData;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public InforTextItemBinding binding;
        //This constructor would switch what to findViewBy according to the type of viewType
        public ViewHolder(InforTextItemBinding itemBinding) {
            super(itemBinding.rootView);
            binding = itemBinding;
        }
    }
    class ViewHolder2 extends RecyclerView.ViewHolder {
        public CommonBannerItemBinding binding;
        public ViewHolder2(CommonBannerItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
    //i:viewType
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case 0:
                InforTextItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.infor_text_item,viewGroup,false);
                return new ViewHolder(binding);
            case 2:
                CommonBannerItemBinding bindingBanner = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.common_banner_item,viewGroup,false);
                return new ViewHolder2(bindingBanner);
            default:
                InforTextItemBinding bindingInfor = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.infor_text_item,viewGroup,false);
                return new ViewHolder(bindingInfor);
        }
    }
   private int iBanner = 0;
//i:position
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                ViewHolder viewHolder0 = (ViewHolder)viewHolder;
                final CommonInforEntity entity = listData.get(i);
                viewHolder0.binding.itemTitle.setText("" + entity.getTitle());
                viewHolder0.binding.itemTag1.setText(entity.getTag()[0]);
                viewHolder0.binding.itemTime.setText(entity.getPublishTime());
                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2)viewHolder;
                final CommonInforEntity entityBanner = listData.get(i);
                Glide.with(mContext)
                        .load(entityBanner.getThumbnail())
                        .placeholder(R.mipmap.infor_item_loading)
                        .error(R.mipmap.infor_item_nodate)
                        .into(viewHolder2.binding.cardNewImage);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        if (position%7 == 0) {
            return 2;
        }
        return 0;
    }
}
