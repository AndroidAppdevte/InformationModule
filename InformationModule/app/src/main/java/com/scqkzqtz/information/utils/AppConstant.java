package com.scqkzqtz.information.utils;


import com.scqkzqtz.information.R;

/**
 * 全局常量
 * Created by hef on 2017/5/26.
 */

public class AppConstant {
    //用户当前权限
    public static final String USERROLE_ZPFXB = "USERROLE_ZPFXB";//用户 早盘风向标后台权限
    public static final String BOOLEAN_ZPFXB = "BOOLEAN_ZPFXB";//用户 早盘风向标后台权限

    //视频点击选中状态
    public static final String VIDEO_TOUCH = "VIDEO_TOUCH";

    //app下载链接
    public static final String DOWNLOAD_URL = "http://www.cjs.com.cn/ad/20170818/wap/index.html";

    public static final String OPEN_ACCOUNT_URL = "http://webapp.cjs.com.cn/m/opens/index.html";//开户url
    public static final String VIDEO_SHARE_URL = "http://webapp.cjs.com.cn/m/video/index.html?objectId=";//视频分享url

    public static final String QUIT_LOGIN = "quit_login";//退出登录
    public static final String LOGIN = "login";//登录
    /**
     * app router 地址
     */
    private static final String ROUTER_ADDRESS =  "http://mcappapi.cjs.com.cn";//leancloud地址


    public static final String EACH_DAY_REQUEST_TIME = "each_day_request";//A_DxtLogIn 每天请求一次云函数
    public static final String CLIENT_ID = "clientId";
    public static final String USER_PHOTO = "photo";
    public static final String MATCH_OBJECT_ID = "matchObjectId";//达人赛ID
    public static final String IS_RISK_RATE = "isRiskRate";//是否做了风险评估

    public static final String PUSH_GET_MSG_DATA = "push_msg";//收到推送

    public static final String PUSH_STOCK = "push_stock";//关联个股时间
    public static final String PUSH_PUBLIC = "push_public";//公开课直播视频开播/关播
    public static final String PUSH_NETSHARE = "push_netshare";// 网络分享会直播视频开播/关播
    public static final String PUSH_DIURNAL = "push_diurnal";// 日刊直播视频开播/关播
    public static final String PUSH_EDUCATION = "push_education";//实战教学直播视频开播/关播
    public static final String PUSH_SENIOR = "push_senior";//高级课直播视频开播/关播

    /**
     * 视频直播通知
     */
    public static final String PUSH_TEAM = "push_team";//战队直播视频开播/关播

    public static final String REFRESH_HONE_LABEL = "refreshLabel";//首页产品卡刷新完成发送eventbus事件

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

    //首页
    public static final String NavigationTopChnage = "NavigationTopChnage";//个人中心头部颜色改变
    public static final String MainChangeTab = "mainChangeTab";//更新首页功能顺序
    public static final String CHANGEMAINTITLECOLOR = "changeMainTitleColor";//首页状态栏颜色改变
    public static final String ALL_MENU_BACK = "allMenuBack";//更新首页功能顺序
    public static final String VOICE_BACK = "VoiceBack";//盘中语音播放状态改变
    public static final String HOME_HIDE = "HomeHide";//隐藏产品卡，后面跟产品卡名称
    public static final String HOME_SHOW = "HomeShow";//显示产品卡，后面跟产品卡名称
    public static final String REFRESH_CARD = "refresh_card";//刷新产品卡
    public static final String REFRESH_POOL_CARD = "refresh_pool_card";//钱坤战略池/趋势领涨池 详情页返回时刷新首页产品卡
    public static final String CLOSEREFRESH_STOCKPOOLDETAILS = "closerefresh_stockPoolDetails";//关闭股票池详情页刷新状态

    public static final String ADD_OPTIONAL_STOCK_GROUP = "add_optional_stock_group";//添加了一个自选股分类
    public static final String DELECT_OPTIONAL_STOCK_GROUP = "delect_optional_stock_group";//删除了一个自选股分类
    public static final String CLICK_EDIT_OPTIONAL_STOCK_GROUP = "click_edit_optional_stock_group";//点击编辑自选股

    public static final String IS_SHOE_LAYOUT_INFOR_HINT = "is_show_layout_infor_hint";//是否显示最新资讯产品卡的提示按钮

    public static final String INFOR_CARD_SHOW_TYPE = "infor_card_show_type";//首页资讯产品卡显示是列表还是卡片

    //推送
    public static final String PUSH_INFOPUSHFLAG = "infoPushFlag";//资讯消息提醒开关- infoPushFlag
    public static final String PUSH_ZJHGPUSHFLAG = "zjhgPushFlag";//战绩消息提醒开关- zjhgPushFlag
    public static final String PUSH_BATCHPUSHFLAG = "batchPushFlag";//活动消息提醒开关-  batchPushFlag
    public static final String PUSH_ANALOGPUSHFLAG = "analogPushFlag";//达人赛关注提醒开关- analogPushFlag
    public static final String PUSH_NOTICEPUSHFLAG = "noticePushFlag";//通知消息提醒开关- noticePushFlag

    public static final String SHARE_NOEQUALS = "直接分享";
    public static final String COLUNUCODE = "columnCode";
    /**
     * 推荐服务line  自选股，智能诊股，投资顾问，短线王中王，钱坤金股，热点前瞻，早盘风向标
     */
    public static final String[] RECOMMENDSERVICE_NAME = {"自选股", "智能诊股", "投资顾问", "短线王中王", "热点前瞻", "早盘风向标"
            , "智能选股", "AI选股", "形态识别"};
    public static final String[] RECOMMENDSERVICE_INTRO = {"定制个人股票", "股票信息获取", "1v1投资指导", "精英模拟操盘", "热点全景分析", "挑选价值牛股"
            , "指标选股+形态选股", "机器算法自动识别", "科学算法形态捕捉"};
    /**
     * 专题教学line
     */
    public static final String[] PROJECTTEACHING_NAME = {"公开课", "CCTV", "实战教学", "名师课程", "网络分享会", "技术学堂"};
    public static final String[] PROJECTTEACHING_INTRO = {"高手进阶之路", "专业财经媒体", "提升实战技能", "掌握投资技巧", "名师交流分享", "告别股市小白"};
    /**
     * 智能策略line
     */
    public static final String[] SMARTSTRATEGY_NAME = {"精选研报", "公告淘股", "A股风向标", "智赢组合"};
    public static final String[] SMARTSTRATEGY_INTRO = {"海量资讯 深度加工", "跟随主力 甄选牛股", "指数展示 趋势先知", "择时而动 择优而入"};
    /**
     * 钱坤金股http://webapp.cjs.com.cn/m/products/stock/index.html
     */
    public static final String RECOMMENDSERVICE_QXJG = "http://webapp.cjs.com.cn/m/products/stock/index.html";
    /**
     * 网络分享会
     */
    public static final String ONLINESHARING_VIDEO = "http://webapp.cjs.com.cn/m/sharemetting/zhibo.html";
    /**
     * 钱坤金股
     */
    public static final String QIANKUN_STOCK = "http://webapp.cjs.com.cn/m/products/stock/cont.html?objectId=";
    /**
     * 文字直播 http://webapp.cjs.com.cn/webapp/src/views/live-text/index.html#!/?stockTeamObjectId=
     */
    public static final String WZZB = "http://webapp.cjs.com.cn/webapp/src/views/live-text/index.html#!/?stockTeamObjectId=";

/**
 开发环境：http://deveHs.cjs.com.cn
 测试环境：http://testHs.cjs.com.cn
 生产环境：http://hs.cjs.com.cn
 */
    public static final String HSBaseUrl = "http://hs.cjs.com.cn";

}
