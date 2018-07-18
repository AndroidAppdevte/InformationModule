package com.scqkzqtz.information;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.scqkzqtz.information.R;
import com.scqkzqtz.information.adapter.FragmentAdapter;
import com.scqkzqtz.information.databinding.FragmentInforMainBinding;
import com.scqkzqtz.information.dialog.InforHomeDialog;
import com.scqkzqtz.information.dialog.SelGridPopWindow;
import com.scqkzqtz.information.eventbus.InforCardEvent;
import com.scqkzqtz.information.utils.AppConstant;
import com.scqkzqtz.information.utils.Utils;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hghl on 2017/5/23.
 */

public class InformationFragment extends Fragment implements View.OnClickListener {

    private FragmentInforMainBinding binding;
    private List<String> validList = new ArrayList<>();
    private List<String> popList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private SelGridPopWindow gridPopWindow;
    private boolean isTrue = false;
    private InforHomeDialog inforHomeDialog;
    private JSONObject jsonData;
//    private int pageNum = 0;

//    private Drawable picUp, picDown;
public static final String PUSH_GET_MSG_DATA = "push_msg";//收到推送
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_infor_main, container, false);
        EventBus.getDefault().register(this);
        init();
      //  refreshMSG(PUSH_GET_MSG_DATA);用于消息数量提示？
        return binding.getRoot();
    }

    private String showValid = "allCategories";

    private void init() {
        Boolean isshowTieie = true;
        Bundle bundle = getArguments();
        if (bundle != null) {
            isshowTieie = bundle.getBoolean("showTielt");
            if (bundle.getString("showValid") != null) {
                showValid = bundle.getString("showValid");
            }
        }
        if (!isshowTieie) {
            binding.linlayTitle.setVisibility(View.GONE);
        } else {
            binding.linlayTitle.setVisibility(View.VISIBLE);
        }
        inforHomeDialog = new InforHomeDialog(this.getActivity());
    }

    private void testTabtitles(){
        initView();
    }
//tab titles
    private void initData() {
        int roleId = 1;
        if (AVUser.getCurrentUser() != null) {
            roleId = AVUser.getCurrentUser().getInt("roleId");
        }
        Log.e("VDSFDSVFDBFBF", "=========roleId================" + roleId);
        //http://mcappapi.cjs.com.cn/1.1/classes/A_DxtInformation
        //null/1.1/classes/A_DxtRoleInformation
        AVQuery<AVObject> query = AVQuery.getQuery("A_DxtRoleInformation");
        //query.setCachePolicy(AVQuery.CachePolicy.CACHE_THEN_NETWORK);
        //where={"roleId":99}&order=-recTime&limit=5
        query.whereEqualTo("roleId", 99);
        query.orderByDescending("publishTime");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Log.e("SLDKVNDLSVSD", "====list.====" + list);
                    if (list.size() == 0) {
                        initView();
                        return;
                    }
                    if (list.size() > 0) {
                        onReData = true;
                        List<String> canShareList = new ArrayList<>();
                        canShareList.clear();
                        canShareList = list.get(0).getList("canShareCategories");
                        canShareList.add("最新资讯");
                        Utils.putValue(InformationFragment.this.getActivity(), "canShareCategories", Utils.listToString(canShareList));
                        jsonData = list.get(0).getJSONObject("categoryRoleMap");
                    }
                    if (isTrue) {
                        if (notEqualList(popList, list.get(0).getList(showValid))) {
                            changeFragment(popList, list.get(0).getList(showValid));
                            popList = list.get(0).getList(showValid);
                        }
                        if (notEqualList(validList, list.get(0).getList("validCategories"))) {
                            if (currentPosition < validList.size())
                                EventBus.getDefault().post(new InforCardEvent(validList.get(currentPosition), validList));
                        }
                        validList = list.get(0).getList("validCategories");
                        if (currentPosition == 1 || currentPosition == 0) {
                        } else {
                            if (!boolInto(currentStr)) {
                                binding.viewpager.setCurrentItem(0);
                            } else if (!validList.toString().contains(currentStr)) {
                                binding.viewpager.setCurrentItem(0);
                                inforHomeDialog.show("您还没有该权限哦，\n快去查看怎么获取权限吧！", new InforHomeDialog.OnConfirmListener() {
                                    @Override
                                    public void onSure() {
                                        intoWeb(currentStr);
                                    }
                                });
                            }
                        }
                    } else {
                        isTrue = true;
                        popList = list.get(0).getList(showValid);
                        validList = list.get(0).getList("validCategories");
                        initView();
                    }
                }else{
                    Log.e("SLDKVNDLSVSD", "====e====" + e);
                }
//                //新增的 其他界面跳转过来 切换到对应的tab
//                if (pageNum != 0) {
//                    binding.viewpager.setCurrentItem(pageNum);
//                }
            }
        });
    }

    public boolean notEqualList(List list1, List list2) {
        if (list1.size() != list2.size())
            return true;
        for (Object object : list1) {
            if (!list2.contains(object))
                return true;
        }
        return false;
    }

    public void changeFragment(List<String> list1, List<String> list2) {
        for (int i = (list1.size() - 1); i > -1; i--) {
            if (!list2.contains(list1.get(i))) {
                fragments.remove(i + 2);
                titleList.remove(i + 2);
                binding.tabs.removeTabAt(i + 2);
                mFragmentAdapteradapter.notifyDataSetChanged();
            }
        }

        for (int i = (list2.size() - 1); i > -1; i--) {
            if (!list1.contains(list2.get(i))) {
                fragments.add(new InforCardAddFragment().newInstance(list2.get(i), true, false));
                titleList.add(list2.get(i));
                binding.tabs.addTab(binding.tabs.newTab().setText(list2.get(i)));
                mFragmentAdapteradapter.notifyDataSetChanged();
            }
        }

        if (list1.size() != list2.size())
            return;
        for (Object object : list1) {
            if (!list2.contains(object)) ;
        }
        return;
    }

    private Handler handler = null;
    private int currentPosition = 0;
    private String currentStr = "";
    private List<Fragment> fragments;
    private FragmentAdapter mFragmentAdapteradapter = null;

    private void initView() {
//        //新增的 其他界面跳转过来 切换到对应的tab
//        if (pageNum != 0) {
//            binding.viewpager.setCurrentItem(pageNum);
//        }
        binding.inforMenu.setOnClickListener(this);
        binding.titleLeft.setOnClickListener(this);
        binding.titleMessage.setOnClickListener(this);
        titleList.clear();
//
//        if (showValid.equalsIgnoreCase("allCategories")) {
//            titleList.add("最新");
//            titleList.add("栏目更新");
//            titleList.add("自选股");
//        }

        for (int i = 0; i < popList.size(); i++) {
            titleList.add(popList.get(i));
        }

        titleList.add("大盘分析");
        titleList.add("主题投资");
        titleList.add("纯文字");

        for (int i = 0; i < titleList.size(); i++) {
            binding.tabs.addTab(binding.tabs.newTab().setText(titleList.get(i)));
        }
        fragments = new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
//            boolean initData = false;
//            for (int j = 0; j < validList.size(); j++) {
//                if (popList.get(i).equalsIgnoreCase(validList.get(j))) {
//                    initData = true;
//                }
//            }
            fragments.add(InforCardAddFragment.newInstance(titleList.get(i), false, false));
        }

        mFragmentAdapteradapter = new FragmentAdapter(getChildFragmentManager(), fragments, titleList);
        binding.tabs.setTabMode(TabLayout.MODE_FIXED);
        binding.tabs.setupWithViewPager(binding.viewpager); //将TabLayout和ViewPager关联起来。
        binding.viewpager.setAdapter(mFragmentAdapteradapter); //给ViewPager设置适配器
        try {
            binding.viewpager.setOffscreenPageLimit(titleList.size());
        } catch (Exception e) {
            Log.e("SDLVNDSLVSDVVDSSDV", "=============1====e==" + e);
        }

        if (!TextUtils.isEmpty(CurrentName)) {
            for (int i = 0; i < titleList.size(); i++) {
                if (titleList.get(i).equals(CurrentName)) {
                    binding.viewpager.setCurrentItem(i);
                    break;
                }
            }
        }
        CurrentName = "";
        if (!titleList.get(0).equalsIgnoreCase("最新")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().postSticky(new InforCardEvent(titleList.get(0), validList));
                    //ZhuGeEvent.setZhugeTrack(_mActivity, "资讯-查看分类资讯", "资讯类别", "最新");
                }
            }, 30);
        }
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                   // ZhugeSDK.getInstance().track(_mActivity, tab.getText().toString());
                }
                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2) {
                    currentPosition = tab.getPosition();
                    currentStr = tab.getText().toString();
                    //ZhuGeEvent.setZhugeTrack(_mActivity, "资讯-查看分类资讯", "资讯类别", currentStr);
                    if (!titleList.get(0).equalsIgnoreCase("最新")) {
                        EventBus.getDefault().post(new InforCardEvent(titleList.get(tab.getPosition()), validList));
                    }
                    return;
                }

//                if (!boolInto(tab.getText().toString())) {
//                    if (handler == null) {
//                        handler = new Handler();
//                    }
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            binding.viewpager.setCurrentItem(currentPosition);
//                        }
//                    }, 30);
//                    return;
//                }
                if (!validList.contains(tab.getText().toString())) {
                    if (currentPosition == tab.getPosition()) {
                        binding.viewpager.setCurrentItem(0);
                        return;
                    }

                    if (AVUser.getCurrentUser() == null) {
                        if (handler == null) {
                            handler = new Handler();
                        }
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                binding.viewpager.setCurrentItem(currentPosition);
//                            }
//                        }, 30);
                        Intent intent = new Intent();
                        //EventBus.getDefault().postSticky(new ActivityJumpEvent(intent, "com.scqkzqtz.CaiJiSong.activity.myself.LoginActivity", _mActivity));
                        return;
                    }

                    inforHomeDialog.show("您还没有该权限哦，\n快去查看怎么获取权限吧！", new InforHomeDialog.OnConfirmListener() {
                        @Override
                        public void onSure() {
                            intoWeb(tab.getText().toString());
                        }
                    });

                    if (handler == null) {
                        handler = new Handler();
                    }
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            binding.viewpager.setCurrentItem(currentPosition);
//                        }
//                    }, 30);
                    return;
                }
                currentStr = tab.getText().toString();
                currentPosition = tab.getPosition();
                EventBus.getDefault().post(new InforCardEvent(titleList.get(currentPosition), validList));
               // ZhuGeEvent.setZhugeTrack(_mActivity, "资讯-查看分类资讯", "资讯类别", currentStr);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    public static final String MainChangeTab = "mainChangeTab";//更新首页功能顺序
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_left) {
           // EventBus.getDefault().post(new ShowMyselfCenterEvent());

        } else if (i == R.id.title_message) {
            EventBus.getDefault().post(MainChangeTab + 4);

        } else if (i == R.id.infor_menu) {
            if (gridPopWindow == null) {
                gridPopWindow = new SelGridPopWindow(InformationFragment.this.getActivity(), new SelGridPopWindow.OnClickListener() {
                    @Override
                    public void onClick(final int position) {
                        intoTabs(position);
                    }

                    @Override
                    public void onDismiss() {
                    }
                });
            }
            gridPopWindow.show(binding.popUp, titleList, currentPosition);

        }
    }

    private boolean onReData = false;

    @Override
    public void onResume() {
        super.onResume();
        if (!onReData) {
            if ((fragments == null || fragments.size() == 0)) {
                //initData();
                testTabtitles();
            }
        }
       // ZhuGeEvent.startTrack("进入资讯页面");
        EventBus.getDefault().post("changeMainTitleColor");
    }

    @Override
    public void onPause() {
        super.onPause();
       // ZhuGeEvent.endTrack("进入资讯页面", " ", " ");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
           // ZhuGeEvent.endTrack("进入资讯页面", " ", " ");
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 接收EventBus事件刷新
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshMSG(String s) {
        if (s.equals(PUSH_GET_MSG_DATA)) {
            getMSGNumber(InformationFragment.this.getActivity(), binding.viewMsg);
        }
    }

    private static Boolean isShowMsg = false;

    public interface queryMsgNumberListene {
        void MsgNumber(List<Integer> msgList);
    }

    /**
     * 消息红点展示
     *
     * @param context
     * @param view
     */
    public static void getMSGNumber(Context context, final View view) {
        if (isShowMsg) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
        if (AVUser.getCurrentUser() != null) {
            List<String> list = new ArrayList<>();
            list.add("预警消息");
            list.add("资讯消息");
            list.add("通知消息");
            list.add("战绩消息");
            list.add("投顾服务");
            list.add("短线王中王关注");
            list.add("达人赛关注");
            list.add("活动消息");
            list.add("战队消息");
//            if (CardRoleUtils.getCardRole() >= 4) {//股票池去除显示
//                list.add("股票池");
//            }
            queryMsgNumber(context, list, new ArrayList<Integer>(), new queryMsgNumberListene() {
                @Override
                public void MsgNumber(List<Integer> msgList) {
                    int number = 0;
                    for (Integer integer : msgList) {
                        number = number + integer;
                    }
                    if (number == 0) {
                        isShowMsg = false;
                        view.setVisibility(View.GONE);
                    } else {
                        isShowMsg = true;
                        view.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            view.setVisibility(View.GONE);
        }
    }
    public static final String QUERY_TIME_WARNING = "query_time_warning";//预警消息-最后查询时间
    public static final String QUERY_TIME_INFORMATION = "query_time_information";//资讯消息-最后查询时间
    public static final String QUERY_TIME_NOTICE = "query_time_notice";//通知消息-最后查询时间
    public static final String QUERY_TIME_ACTIVITY = "query_time_activity";//活动消息-最后查询时间
    public static final String QUERY_TIME_REVIEW = "query_time_review";//战绩回顾-最后查询时间
    public static final String QUERY_TIME_DRSGZ = "query_time_drsgz";//达人赛关注-最后查询时间
    public static final String QUERY_TIME_WZWGZ = "query_time_wzwgz";//王中王关注-最后查询时间
    public static final String QUERY_TIME_TGFW = "query_time_tgfw";//投顾服务-最后查询时间
    public static final String QUERY_TIME_GPC = "query_time_gpc";//股票池-最后查询时间
    public static final String QUERY_TIME_ZDXX = "query_time_zdxx";//战队消息-最后查询时间
    /**
     * 查询消息数量
     *
     * @param nameList 需要查询的名称
     * @param msgList  查询的结果
     * @return
     */
    public static void queryMsgNumber(final Context context, final List<String> nameList, final List<Integer> msgList, final queryMsgNumberListene msgNumberListene) {
        String name = nameList.get(0);
        String queryTime = null;
        Date queryDate = null;
        String ObjectId = "";
        if (AVUser.getCurrentUser() != null) {
            ObjectId = AVUser.getCurrentUser().getObjectId();
        }
        switch (name) {
            case "预警消息":
                queryTime = Utils.getValue(context, QUERY_TIME_WARNING + ObjectId);
                break;
            case "资讯消息":
                queryTime = Utils.getValue(context, QUERY_TIME_INFORMATION + ObjectId);
                break;
            case "通知消息":
                queryTime = Utils.getValue(context, QUERY_TIME_NOTICE + ObjectId);
                break;
            case "活动消息":
                queryTime = Utils.getValue(context, QUERY_TIME_ACTIVITY + ObjectId);
                break;
            case "战绩消息":
                queryTime = Utils.getValue(context, QUERY_TIME_REVIEW + ObjectId);
                break;
            case "达人赛关注":
                queryTime = Utils.getValue(context, QUERY_TIME_DRSGZ + ObjectId);
                break;
            case "短线王中王关注":
                queryTime = Utils.getValue(context, QUERY_TIME_WZWGZ + ObjectId);
                break;
            case "投顾服务":
                queryTime = Utils.getValue(context, QUERY_TIME_TGFW + ObjectId);
                break;
            case "股票池":
                queryTime = Utils.getValue(context, QUERY_TIME_GPC + ObjectId);
                break;
            case "战队消息":
                queryTime = Utils.getValue(context, QUERY_TIME_ZDXX + ObjectId);
                break;
        }
        if (!TextUtils.isEmpty(queryTime)) {
            queryDate = Utils.getDateTime(queryTime);
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.DATE, -7);  //减7天
            queryDate = Utils.getDateTime(df.format(cal.getTime()));
        }

        AVQuery<AVObject> query = getMsgQuery(name);
        query.whereGreaterThanOrEqualTo("createdAt", queryDate);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int i, AVException e) {
                int index = 0;
                if (e == null) {
                    index = i;
                }
                Log.d("消息数量：" + nameList.get(0), "  " + index);
                nameList.remove(0);
                msgList.add(index);
                if (nameList.size() != 0) {
                    queryMsgNumber(context, nameList, msgList, msgNumberListene);
                } else {
                    msgNumberListene.MsgNumber(msgList);
                }
            }
        });
    }

    /**
     * 获取消息查询的条件
     *
     * @param msgName
     * @return
     */
    public static AVQuery<AVObject> getMsgQuery(String msgName) {
        final AVQuery<AVObject> query1 = new AVQuery<>("A_DxtIMmessageSystem");
        if (AVUser.getCurrentUser() != null) {
           // if (CardRoleUtils.Interior) {
                query1.whereEqualTo("roleId", 99);
//            } else {
//                query1.whereEqualTo("roleId", CardRoleUtils.getCardRole());
//            }
        }

        final AVQuery<AVObject> query2 = new AVQuery<>("A_DxtIMmessageSystem");
        query2.whereEqualTo("roleId", 0);

        final AVQuery<AVObject> query3 = new AVQuery<>("A_DxtIMmessageSystem");
        if (AVUser.getCurrentUser() != null) {
            query3.whereEqualTo("userObjectId", AVUser.getCurrentUser().getObjectId());
        }

        AVQuery<AVObject> queryOr = AVQuery.or(Arrays.asList(query1, query2, query3));
        AVQuery<AVObject> queryClassify = new AVQuery<>("A_DxtIMmessageSystem");
        AVQuery<AVObject> query = null;
        switch (msgName) {
            case "预警消息":
                queryClassify.whereEqualTo("classify", "预警消息");
                query = AVQuery.and(Arrays.asList(queryClassify, queryOr));
                break;
            case "资讯消息":
                queryClassify.whereEqualTo("classify", "热点资讯");
                query = AVQuery.and(Arrays.asList(queryClassify, queryOr));
                break;
            case "通知消息":
                queryClassify.whereEqualTo("classify", "通知消息");
                queryClassify.whereNotContainedIn("type", Arrays.asList("达人赛关注提醒", "短线王中王关注提醒", "投资组合", "股票池"
                        , "战队文字直播", "战队付费直播", "战队观点", "战队投资锦囊", "战队问股回复", "战队免费直播", "战队金股池"));
                query = AVQuery.and(Arrays.asList(queryClassify, queryOr));
                break;
            case "战绩消息":
                queryClassify.whereEqualTo("classify", "战绩回顾");
                query = AVQuery.and(Arrays.asList(queryClassify, queryOr));
                break;
            case "达人赛关注":
                queryClassify.whereEqualTo("classify", "通知消息");
                queryClassify.whereEqualTo("type", "达人赛关注提醒");
                query = AVQuery.and(Arrays.asList(queryClassify, queryOr));
                break;
            case "短线王中王关注":
                queryClassify.whereEqualTo("classify", "通知消息");
                queryClassify.whereEqualTo("type", "短线王中王关注提醒");
                query = AVQuery.and(Arrays.asList(queryClassify, queryOr));
                break;
            case "投顾服务":
                queryClassify.whereEqualTo("classify", "通知消息");
                queryClassify.whereEqualTo("type", "投资组合");
                query = AVQuery.and(Arrays.asList(queryClassify, queryOr));
                break;
            case "股票池":
                queryClassify.whereEqualTo("classify", "通知消息");
                queryClassify.whereEqualTo("type", "股票池");
                query = AVQuery.and(Arrays.asList(queryClassify, queryOr));
                break;
            case "活动消息":
                queryClassify.whereEqualTo("classify", "活动消息");
                query = AVQuery.and(Arrays.asList(queryClassify, queryOr));
                break;
            case "战队消息":
                queryClassify.whereEqualTo("classify", "战队消息");
                queryClassify.whereNotContainedIn("type", Arrays.asList("战队问股回复"));
                query = AVQuery.and(Arrays.asList(queryClassify, queryOr));
                break;
        }
        query.orderByDescending("createdAt");
        return query;
    }

    /**
     * 登录退出登录刷新
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void event(String s) {
        if (AppConstant.LOGIN.equals(s) || AppConstant.QUIT_LOGIN.equals(s)) {
            initData();
        }
    }

    private String CurrentName = "";

    private void intoWeb(String strType) {
        String str = "CP-TG";
        try {
            switch (jsonData.getInt(strType)) {//0-通用（未注册） 1-未注册 2-注册 3-正式 4-股票池 5-投顾版本  6-专家
                case 3:
                    str = "CP-ZS";
                    break;
                case 4:
                    str = "CP-GP";
                    break;
                case 5:
                    str = "CP-TG";
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void intoTabs(final int position) {
        boolean boolStr = false;
        if (position == 1 || position == 0 || position == 2 || "钱坤观点".equals(titleList.get(position))) {
            boolStr = true;
        } else {
            for (String str : validList) {//8
                if (str.equalsIgnoreCase(popList.get(position - 3))) {//17
                    boolStr = true;
                }
            }
        }
        if (AVUser.getCurrentUser() != null && position > 1) {
            if (!boolInto(popList.get(position - 3))) {
                return;
            }
        }
        if (boolStr) {
            binding.viewpager.setCurrentItem(position);
        } else {
            if (AVUser.getCurrentUser() == null) {
                Intent intent = new Intent();
//                EventBus.getDefault().postSticky(new ActivityJumpEvent(intent,
//                        "com.scqkzqtz.CaiJiSong.activity.myself.LoginActivity", _mActivity));
                return;
            }

            inforHomeDialog.show("您还没有该权限哦，\n快去查看怎么获取权限吧！", new InforHomeDialog.OnConfirmListener() {
                @Override
                public void onSure() {
                    Log.e("SLDNVDSKVNSDL", "======popList=========" + popList.get(position - 3));
                    intoWeb(popList.get(position - 3));
                }
            });
        }
    }

    private boolean boolInto(String currentItem) {
        if (AVUser.getCurrentUser() == null) {
            return true;
        }
        Iterator<String> aa = jsonData.keys();
        while (aa.hasNext()) {
            if (currentItem.equalsIgnoreCase(aa.next().toString())) {
                boolean isRiskRate = Utils.getBooleanValue(InformationFragment.this.getContext(), AppConstant.IS_RISK_RATE);
                if (isRiskRate) {
                } else {
                   // RiskRateDialog1 rateDialog1 = new RiskRateDialog1(_mActivity);
                   /// rateDialog1.show();
                    return false;
                }
            }
        }
        return true;
    }
}
