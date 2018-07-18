package com.scqkzqtz.information;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.liaoinstan.springview.widget.SpringView;
import com.scqkzqtz.information.adapter.CommonTextInforAdapter;
import com.scqkzqtz.information.adapter.CommonThemeInforAdapter;
import com.scqkzqtz.information.entity.CommonInforEntity;
import com.scqkzqtz.information.entity.InforImageEntity;
import com.scqkzqtz.information.eventbus.InforCardEvent;
import com.scqkzqtz.information.eventbus.InforUpdateEvent;
import com.scqkzqtz.information.adapter.CommonInforAdapter;
import com.scqkzqtz.information.databinding.FragmentListviewBinding;
import com.scqkzqtz.information.utils.Utils;
import com.scqkzqtz.information.widget.NewTransactionFooter;
import com.scqkzqtz.information.widget.NewTransactionHeader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by hghl on 2017/6/16.
 */

public class InforCardAddFragment extends Fragment implements SpringView.OnFreshListener, View.OnClickListener {

    private FragmentListviewBinding binding;
    private CommonInforAdapter adapter;
    private CommonThemeInforAdapter themeAdapter;
    private CommonTextInforAdapter TextAdapter;

    public static final String FRAG_TAG = "index";
    private String Index;
    private boolean addData = false;

    private List<CommonInforEntity> adapterData = new ArrayList<>();
    private List<CommonInforEntity> listData = new ArrayList<>();

    private boolean showPic = true;
    private int skipPosition = 0;
    private int total = 20;
    private int picData = 0;

    public static InforCardAddFragment newInstance(String index, boolean addData, Boolean immediatelyLoad) {
        InforCardAddFragment f = new InforCardAddFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        args.putBoolean("addData", addData);
        args.putBoolean("immediatelyLoad", immediatelyLoad);
        f.setArguments(args);
        return f;
    }
    private AnimationDrawable mAnimationDrawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //FragmentListviewBinding
        binding = binding.inflate(inflater);
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            Index = arguments.getString(FRAG_TAG);
            addData = arguments.getBoolean("addData");
        }
        initView();
//        if (arguments != null) {
//            if (arguments.getBoolean("immediatelyLoad")) {
//               // onRefresh();
//            }
//        }
        return binding.getRoot();
    }

    private void initView() {

        binding.courseNoData.setOnClickListener(this);
        if (Index.equalsIgnoreCase("纯文字")) {

            for (int i = 0; i < 6; i++) {
                listData.add(new CommonInforEntity("贸易战升级，短期震荡反复", new String[]{"主题投资"}, "2018-07-18 09:54:10"));
                listData.add(new CommonInforEntity("贸易战升级，短期震荡反复,关注上档缺口及均线的压力", new String[]{"主题投资"},
                        "2018-07-18 09:54:10"));
            }
            TextAdapter = new CommonTextInforAdapter(InforCardAddFragment.this.getActivity(), listData);
            LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            binding.fragmentListView.setLayoutManager(llm);
            binding.fragmentListView.setAdapter(TextAdapter);
        }else
        if (Index.equalsIgnoreCase("主题投资")) {
            List<InforImageEntity> inforImageEntities = new ArrayList<>();
            for (int i =0;i<3;i++){
                inforImageEntities.add(new InforImageEntity("主题投资，因为专业，所以专业",
                        "http://cjsmc-cdn.cjs.com.cn/26c0d9bf5af396bd86b1.jpg"));
            }
            for (int i = 0; i < 6; i++) {
                listData.add(new CommonInforEntity("贸易战升级，短期震荡反复", new String[]{"主题投资"}, "30分钟前",
                        inforImageEntities));
                listData.add(new CommonInforEntity("贸易战升级，短期震荡反复,关注上档缺口及均线的压力", new String[]{"主题投资"},
                        "30分钟前",
                        inforImageEntities));
            }
            themeAdapter = new CommonThemeInforAdapter(InforCardAddFragment.this.getActivity(), listData);
            LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            binding.fragmentListView.setLayoutManager(llm);
            binding.fragmentListView.setAdapter(themeAdapter);

        } else {
            for (int i = 0; i < 6; i++) {
                listData.add(new CommonInforEntity("贸易战升级，短期震荡反复", new String[]{"大盘分析"}, "30分钟前",
                        "http://cjsmc-cdn.cjs.com.cn/26c0d9bf5af396bd86b1.jpg"));
                listData.add(new CommonInforEntity("贸易战升级，短期震荡反复,关注上档缺口及均线的压力", new String[]{"大盘分析"},
                        "30分钟前",
                        "http://cjsmc-cdn.cjs.com.cn/26c0d9bf5af396bd86b1.jpg"));
            }
            adapter = new CommonInforAdapter(InforCardAddFragment.this.getActivity(), listData);
            LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            binding.fragmentListView.setLayoutManager(llm);
            binding.fragmentListView.setAdapter(adapter);
            // }
        binding.courseNoData.setVisibility(View.GONE);
        binding.fragmentSpringView.setListener(this);
        binding.fragmentSpringView.setType(SpringView.Type.FOLLOW);
        binding.fragmentSpringView.setHeader(new NewTransactionHeader());
        binding.fragmentSpringView.setFooter(new NewTransactionFooter());

//        binding.imgProgress.setImageResource(R.drawable.loading_frame);
//        mAnimationDrawable = (AnimationDrawable) binding.imgProgress.getDrawable();
//        // 默认进入页面就开启动画
//        if (!mAnimationDrawable.isRunning()) {
//            mAnimationDrawable.start();
//        }
        }
    }

    private void initTestData(){
        listData.add(new CommonInforEntity("贸易战升级，短期震荡反复",new String[]{"大盘分析"},"2018-07-11 15:50:08",
                "http://cjsmc-cdn.cjs.com.cn/26c0d9bf5af396bd86b1.jpg"));
        listData.add(new CommonInforEntity("贸易战升级，短期震荡反复,关注上档缺口及均线的压力",new String[]{"大盘分析"},
                "2018-07-11 15:50:08",
                "http://cjsmc-cdn.cjs.com.cn/26c0d9bf5af396bd86b1.jpg"));
        if (adapter!=null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     *
     * @param skip
     */
    private void initData(final int skip) {
        Log.e("VDSFDSVFDBFBF", "======Index========" + Index + "==========addData=========" + addData);
        if (!addData) { //0-通用（未注册） 1-未注册 2-注册 3-正式 4-股票池 5-投顾版本  6-专家
            binding.fragmentSpringView.onFinishFreshAndLoad();
           // binding.noDataText.setText("没有权限查看哦");
            binding.courseNoData.setVisibility(View.VISIBLE);
            binding.fragmentSpringView.setVisibility(View.GONE);
            return;
        }
        binding.fragmentSpringView.setVisibility(View.VISIBLE);
        AVQuery<AVObject> query = AVQuery.getQuery("A_DxtInformation");
        query.whereEqualTo("categories", Index);
        query.orderByDescending("publishTime");
        if (skip ==  DATA_REFRESH) {
            query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
            query.limit(skipPosition);
        } else {
            query.skip(skipPosition);
            query.limit(total);
        }
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                binding.fragmentSpringView.onFinishFreshAndLoad();
                if (e!=null) {
                    e.printStackTrace();
                    return;
                }
                    Log.e("VDSFDSVFDBFBF", "==========" + Index + "========size=======" + list.size());
                    EventBus.getDefault().post(new InforUpdateEvent(Index, new Date()));
                    adapterData.clear();
                    if (skip ==  DATA_REFRESH) {
                        //stopLoading(binding.llProgressBar, binding.courseNoData);
                        cardNumber = 0;
                        listData.clear();
                        picData = 0;
                        if (list.size() == 0) {
                            binding.courseNoData.setVisibility(View.VISIBLE);
                           // binding.noDataText.setText("暂时无数据，请稍后查看");
                        } else {
                            binding.courseNoData.setVisibility(View.GONE);
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        AVObject avObject = list.get(i);
                        String title = avObject.getString("title");
                        String publishTime = Utils.getFormatTime(avObject.getDate("publishTime"), 6);
                        String thumbnail = avObject.getString("thumbnail");
                        String[] labels = new String[3];
                        if (avObject.getJSONArray("labels") != null && avObject.getJSONArray("labels").length() >= 0) {
                            int length = avObject.getJSONArray("labels").length();
                            try {
                            switch (length) {
                                case 1:
                                    labels[0] = avObject.getJSONArray("labels").get(0).toString();
                                    break;
                                case 2:
                                    labels[0] = avObject.getJSONArray("labels").get(0).toString();
                                    labels[1] = avObject.getJSONArray("labels").get(1).toString();
                                    break;
                                default:
                                    labels[0] = avObject.getJSONArray("labels").get(0).toString();
                                    labels[1] = avObject.getJSONArray("labels").get(1).toString();
                                    labels[2] = avObject.getJSONArray("labels").get(2).toString();
                                    break;
                            }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                        adapterData.add(new CommonInforEntity(title,labels,publishTime,thumbnail));
                    }
                    if (Index.equalsIgnoreCase("主题投资")) {
                      //  initLocatData(adapterData, listData, false);
                        Log.e("SDLVNSDLVSD", "========listData==============" + listData);
                        //themeAdapter.listData = listData;
                        themeAdapter.notifyDataSetChanged();
                    } else {
                       // initLocatData(adapterData, listData, true);
                        adapter.listData = listData;
                        adapter.notifyDataSetChanged();
                    }
                }
        });
    }
    // 开始动画
    protected void showLoading(RelativeLayout progressBar, LinearLayout errorRefresh) {
        progressBar.setVisibility(View.VISIBLE);
        errorRefresh.setVisibility(View.GONE);

        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }

    // 停止动画
    protected void stopLoading(RelativeLayout progressBar, LinearLayout errorRefresh) {
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        progressBar.setVisibility(View.GONE);
        errorRefresh.setVisibility(View.VISIBLE);
    }


    /**
     * 将新请求的数据随机每5个插入广告
     *
     * @param list  请求的数据
     * @param listData      处理后的数据
     * @param b
     */
    private int cardNumber = 0;

//    private void initLocatData(List<CommonInforEntity> list, List<CommonInforEntity> listData, boolean b) {
//        Random rand = new Random();
//        int position = 5 + rand.nextInt(5);
//        for (int i = 0; i < list.size(); i++) {
//            listData.add(list.get(i));
//            if (cardNumber < 2) {
//                if (i == position && b) {
//                    picData++;
//                    position = position + 5 + rand.nextInt(5);
//                    AVObject a_dxtInformation = new AVObject();
//                    if (showPic) {
//                        showPic = false;
//                        a_dxtInformation.put("LOCAL_TYPE", "picData");
//                    } else {
//                        showPic = true;
//                        a_dxtInformation.put("LOCAL_TYPE", "strData");
//                    }
//                    cardNumber = cardNumber + 1;
//                    listData.add(a_dxtInformation);
//                }
//            }
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 104)
    public void OptionalStockEvent(InforCardEvent event) {
//        addData = false;
//        for (int i = 0; i < event.getStrList().size(); i++) {
//            if (event.getStrList().get(i).equalsIgnoreCase(Index)) {
//                addData = true;
//            }
//        }
//        if (event.getTag().equalsIgnoreCase(Index)) {
//            onRefresh();
//        }
//
//        if (!addData) {
//            onRefresh();
//        }
//        EventBus.getDefault().removeAllStickyEvents();
    }

    @Override
    public void onRefresh() {
        if (Index.equalsIgnoreCase("主题投资")) {
            if (themeAdapter.listData.size() > 20) {
                skipPosition = themeAdapter.listData.size() - picData;
            } else {
                skipPosition = 20;
            }
            if (themeAdapter.listData.size() == 0) {
                showLoading(binding.llProgressBar, binding.courseNoData);
            }
        } else {
            if (adapter.listData.size() > 20) {
                skipPosition = adapter.listData.size() - picData;
            } else {
                skipPosition = 20;
            }
            if (adapter.listData.size() == 0) {
                //showLoading(binding.llProgressBar, binding.courseNoData);
            }
        }

        initData(DATA_REFRESH);
        // Utils.onCloseRefresh(binding.fragmentSpringView);
    }
    @Override
    public void onLoadmore() {
        if (Index.equalsIgnoreCase("主题投资")) {
            if (themeAdapter.listData.size() > 0) {
                skipPosition = themeAdapter.listData.size() - picData;
            }
        } else {
            if (adapter.listData.size() > 0) {
                skipPosition = adapter.listData.size() - picData;
            }
        }
        initData( DATA_LOADMORE);
//        Utils.onCloseRefresh(binding.fragmentSpringView);
    }

    private static final int DATA_REFRESH = 1;
    private final static int DATA_LOADMORE = 2;

    @Override
    public void onResume() {
        super.onResume();
//        binding.fragmentSpringView.callFresh();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.course_no_data:
                //reload data

                initData(0);
                break;
        }
    }
}
