package com.scqkzqtz.information;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.scqkzqtz.information.entity.InforEntity;
import com.scqkzqtz.information.entity.InforImageEntity;
import com.scqkzqtz.information.databinding.ActivityShowImageBinding;
import com.scqkzqtz.information.utils.CollectionUtil;
import com.scqkzqtz.information.utils.Utils;
import com.scqkzqtz.information.widget.PinchImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hghl on 2017/9/13.
 */

public class ShowImageActivity extends Activity implements View.OnClickListener {

    private ActivityShowImageBinding binding;
    ArrayList<InforImageEntity> inforImageEntities = new ArrayList<>();
    private InforEntity inforEntity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_image);
        shouji1 = ContextCompat.getDrawable(this,R.mipmap.white_colloct);
        shouji2 = ContextCompat.getDrawable(this, R.mipmap.icon_article_collect_yes);

        canshare = Utils.stringToList(Utils.getValue(ShowImageActivity.this, "canShareCategories"));

        initView();
        checkStatu();
        initPic();
    }

    private void initView() {
        Intent intent = getIntent();
        inforImageEntities = (ArrayList<InforImageEntity>)intent.getSerializableExtra("inforImageEntities");
        inforEntity = (InforEntity) intent.getSerializableExtra("inforEntity");
        binding.titleName.setText(inforEntity.getTitle());

        binding.bottomShare.setOnClickListener(this);
        binding.bottomCollect.setOnClickListener(this);
        binding.titleBack.setOnClickListener(this);
    }

    private ArrayList<View> pageview;
    private void initPic() {
        if (inforImageEntities.size() == 0)
            return;
        String strText = "" + 1 + "/" + inforImageEntities.size() + "   ";
        if (inforImageEntities.size() != 0) {
            strText = strText + inforImageEntities.get(0).getImageText();
        }
        binding.contentStr.setText(strText);

        LayoutInflater inflater =getLayoutInflater();
        pageview =new ArrayList<View>();
        for (int i = 0 ; i < inforImageEntities.size(); i ++) {
            View view1 = inflater.inflate(R.layout.show_image_item, null);
            PinchImageView imageView= (PinchImageView) view1.findViewById(R.id.img_show);
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(ShowImageActivity.this).load(inforImageEntities.get(i).getImageUrl()).placeholder(null)
                    .error(null).into(imageView);
            pageview.add(view1);
        }

        initAdapter();

    }


    private void initAdapter() {
        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter(){
            @Override
            public int getCount() {
                return pageview.size();
            }

            @Override
            //断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0==arg1;
            }
            //是从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1){
                ((ViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }
        };
        binding.viewPager.setAdapter(mPagerAdapter);
        binding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int a = position + 1;
                String strText = "" + a + "/" + inforImageEntities.size() + "   ";
                if ((inforImageEntities.size() - 1) >= position) {
                    strText = strText + inforImageEntities.get(position).getImageText();
                }
                for (int i = 0 ;i < pageview.size(); i ++)
                    if (i != position)
                        ((PinchImageView)pageview.get(i).findViewById(R.id.img_show)).reset();
                binding.contentStr.setText(strText);
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private List<String> canshare = new ArrayList<>();

    @Override
    public void onClick(View v) {
        int i1 = v.getId();
        if (i1 == R.id.title_back) {
            ShowImageActivity.this.finish();

        } else if (i1 == R.id.bottom_collect) {
            if (AVUser.getCurrentUser() == null) {
                Intent intent = new Intent();
               // EventBus.getDefault().postSticky(new ActivityJumpEvent(intent, "com.scqkzqtz.CaiJiSong.activity.myself.LoginActivity", ShowImageActivity.this));
            }
            if (Utils.isMainFastClick()) {
                return;
            }
            setCollect();   //添加/取消收藏

        } else if (i1 == R.id.bottom_share) {
            String shareUrl = "http://www.cjs.com.cn/ad/20170818/wap/index.html";
            for (int i = 0; i < canshare.size(); i++) {
                if (inforEntity.getTitle().equalsIgnoreCase(canshare.get(i))) {
                    shareUrl = inforEntity.getUrl();
                }
            }
            webInforShare(shareUrl, inforEntity.getArticleContent_KEY(), inforEntity.getTitle(), inforEntity.getArticleImage_KEY());

        }
    }


    private Drawable shouji1 = null, shouji2 = null;
    private AVObject collectAvObject;//已收藏返回的AVObject
    /**
     * 收藏1
     */
    private void checkStatu() {
//        CollectionUtil.queryCollect(inforEntity.getObjectId(), new CollectionUtil.OnCollectionListener() {
//            @Override
//            public void onCollection(List<AVObject> list, Exception e) {
//                if(e==null&&list!=null&&list.size()>0){
//                    collectAvObject=list.get(0);
//                    int number = inforEntity.getCollectNumber();
//                    int status=collectAvObject.getInt("status");
//                    if(status==1){
//                        number = number + 1;
//                        binding.collectImage.setImageDrawable(shouji2);
//                        binding.collect.setTextColor(Color.parseColor("#E94F53"));
//                    }else {
//                        binding.collectImage.setImageDrawable(shouji1);
//                        binding.collect.setTextColor(Color.parseColor("#ffffff"));
//                    }
//                }else {
//                    binding.collectImage.setImageDrawable(shouji1);
//                    binding.collect.setTextColor(Color.parseColor("#ffffff"));
//                }
//            }
//        });
    }

    /**
     * 收藏2
     */
    private void setCollect(){
        CollectionUtil.queryCollect(inforEntity.getObjectId(), new CollectionUtil.OnCollectionListener() {
            @Override
            public void onCollection(List<AVObject> list, Exception e) {
                if(e==null&&list!=null&&list.size()>0){
                    collectAvObject=list.get(0);
                    int status=collectAvObject.getInt("status");
                    if(status==1){//已收藏
                        CollectionUtil.deleteCollection(collectAvObject, new CollectionUtil.OnCollectionListener() {
                            @Override
                            public void onCollection(List<AVObject> list, Exception e) {
                                if (e == null) {
                                    binding.collectImage.setImageDrawable(shouji1);
                                    binding.collect.setTextColor(Color.parseColor("#ffffff"));
                                }
                            }
                        });
                    }else {//未收藏
                        CollectionUtil.addCollection(collectAvObject,inforEntity.getObjectId(),"information",
                                new CollectionUtil.OnCollectionListener() {
                                    @Override
                                    public void onCollection(List<AVObject> list, Exception e) {
                                        if (e == null) {
                                            int number = inforEntity.getCollectNumber() + 1;
                                            //ZhuGeEvent.setZhugeTrack(ShowImageActivity.this, "收藏", "收藏成功", "点击次数");
                                            binding.collectImage.setImageDrawable(shouji2);
                                            binding.collect.setTextColor(Color.parseColor("#E94F53"));
                                        }
                                    }
                                });
                    }
                }else {
                    CollectionUtil.addCollection(collectAvObject,inforEntity.getObjectId(),"information",
                            new CollectionUtil.OnCollectionListener() {
                                @Override
                                public void onCollection(List<AVObject> list, Exception e) {
                                    if (e == null) {
                                        int number = inforEntity.getCollectNumber() + 1;
                                       // ZhuGeEvent.setZhugeTrack(ShowImageActivity.this, "收藏", "收藏成功", "点击次数");
                                        binding.collectImage.setImageDrawable(shouji2);
                                        binding.collect.setTextColor(Color.parseColor("#E94F53"));
                                    }
                                }
                            });
                }
            }
        });
    }

    /**
     * 分享
     * @param content_url
     * @param ArticleContent_KEY
     * @param ArticleTitle_KEY
     * @param ImageUrl
     */
    private void webInforShare(String content_url, String ArticleContent_KEY, String ArticleTitle_KEY, String ImageUrl) {
        if (Utils.isMainFastClick()) {
            return;
        }
//        OnekeyShare oks = new OnekeyShare();
//
//        oks.disableSSOWhenAuthorize();   //关闭sso授权
//// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
////        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        if (!ArticleTitle_KEY.isEmpty()) {
//            oks.setTitle(ArticleTitle_KEY);
//        } else {
//            oks.setTitle("");
//        }
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl(content_url);
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText(ArticleContent_KEY);
//        if (ImageUrl.equalsIgnoreCase("")) {
//            oks.setImageUrl("http://cjsmc-cdn.cjs.com.cn/cjs.png");
//        } else {
//            oks.setImageUrl(ImageUrl);
//        }
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
////        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl(content_url);
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment(content_url);
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        if (!ArticleTitle_KEY.isEmpty()) {
//            oks.setSite(ArticleTitle_KEY);
//        } else {
//            oks.setSite("财急送");
//        }
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(content_url);
//        oks.setCallback(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                Log.e("SDVSDNVLDSVDS", "=====onComplete=========");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                Log.e("SDVSDNVLDSVDS", "=====onError=========");
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                Log.e("SDVSDNVLDSVDS", "=====onCancel=========");
//            }
//        });
//        oks.show(ShowImageActivity.this);
//
//        Properties properties = new Properties();
//        properties.putValue("objectId", inforEntity.getObjectId());
//        Analytics.with(this).track("information-share", properties);
    }

}
