package com.scqkzqtz.information.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.scqkzqtz.information.R;
import com.scqkzqtz.information.databinding.CommonBannerItemBinding;
import com.scqkzqtz.information.databinding.InforItemBinding;
import com.scqkzqtz.information.databinding.InforThemeItemBinding;
import com.scqkzqtz.information.entity.CommonInforEntity;
import com.scqkzqtz.information.entity.InforImageEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class CommonThemeInforAdapter extends RecyclerView.Adapter{

    private Context mContext;
    public List<CommonInforEntity> listData = new ArrayList<>();
    public List<CommonInforEntity> listBanner = new ArrayList<>();
    public CommonThemeInforAdapter(Context mContext, List<CommonInforEntity> listData) {
        this.mContext = mContext;
        this.listData = listData;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public InforThemeItemBinding binding;
        //This constructor would switch what to findViewBy according to the type of viewType
        public ViewHolder(InforThemeItemBinding itemBinding) {
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
                InforThemeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.infor_theme_item,
                        viewGroup,false);
                return new ViewHolder(binding);
            case 2:
                CommonBannerItemBinding bindingBanner = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.common_banner_item,viewGroup,false);
                return new ViewHolder2(bindingBanner);
            default:
                InforThemeItemBinding bindingInfor = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.infor_theme_item,viewGroup,false);
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
                if (entity.getThumbnail()!= null) {
                    if (!entity.getThumbnail().equalsIgnoreCase("")) {
                        Glide.with(mContext.getApplicationContext())
                                .load(entity.getThumbnail())
                                .placeholder(R.mipmap.infor_item_loading)
                                .error(R.mipmap.infor_item_nodate)
                                .into(viewHolder0.binding.itemImage);
                    }
                }
                viewHolder0.binding.itemTitle2.setText("" + entity.getTitle());
                viewHolder0.binding.itemTag4.setText(entity.getTag()[0]);
                viewHolder0.binding.itemTime2.setText(entity.getPublishTime());
//                if (entity.get("images") != null) {
//                    if (((JSONObject) entity.get("images")).size() != 0) {
//                        binding.itemNormal.setVisibility(View.GONE);
//                        binding.showImageRes.setVisibility(View.VISIBLE);
//                        JSONObject jsonObject = (JSONObject) entity.get("images");
//                        JSONObject imageDescs = (JSONObject) entity.get("imageDescs");
//                        TreeSet<String> keySet = new TreeSet<>(jsonObject.keySet());
//                        List<InforImageEntity> inforImageEntities = new ArrayList<>();
//                        for (String k : keySet) {
//                            inforImageEntities.add(new InforImageEntity(imageDescs == null ? "" : imageDescs.get(k), jsonObject.get(k)));
//                        }
//                    }
//                }
                showImage(entity.getInforImageEntities(),viewHolder0.binding);
                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2)viewHolder;
                final CommonInforEntity entityBanner = listData.get(i);
                Glide.with(mContext)
                        .load(entityBanner.getInforImageEntities().get(0).getImageUrl())
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
    private void showImage(List<InforImageEntity> inforImageEntities, InforThemeItemBinding binding) {
        binding.showImage01.setVisibility(View.GONE);
        binding.showImage02.setVisibility(View.GONE);
        binding.showImage03.setVisibility(View.GONE);
        for (int i = 0; i < (inforImageEntities.size() < 3 ? inforImageEntities.size() : 3); i++) {
            switch (i) {
                case 0:
                    binding.showImage01.setVisibility(View.VISIBLE);
                    Glide.with(mContext.getApplicationContext()).load(inforImageEntities.get(i).getImageUrl()).placeholder(R.mipmap.infor_item_loading).error(R.mipmap.infor_item_nodate).into(binding.showImage01);
                    break;
                case 1:
                    binding.showImage02.setVisibility(View.VISIBLE);
                    Glide.with(mContext.getApplicationContext()).load(inforImageEntities.get(i).getImageUrl()).placeholder(R.mipmap.infor_item_loading).error(R.mipmap.infor_item_nodate).into(binding.showImage02);
                    break;
                case 2:
                    binding.showImage03.setVisibility(View.VISIBLE);
                    Glide.with(mContext.getApplicationContext())
                            .load(inforImageEntities.get(i).getImageUrl())
                            .placeholder(R.mipmap.infor_item_loading)
                            .error(R.mipmap.infor_item_nodate).into(binding.showImage03);
                    break;
            }
        }

        binding.showImageNum.setVisibility(View.GONE);
        if (inforImageEntities.size() >= 3) {
            binding.showImageNum.setVisibility(View.VISIBLE);
            binding.showImageNum.setText("" + inforImageEntities.size() + "图");
        }
    }
}
