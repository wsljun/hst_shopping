package com.cj.reocrd.api;

/**
 * Created by Lyndon.Li on 2018/3/17.
 */

public class UrlConstants {
    //BaseURl
//    public final static String BASE_URL = "http://gank.io/api/";
    public final static String BASE_URL = "http://mall.xdiandian.cn/mall/";
    public final static String URL_ABOUT = "http://mall.xdiandian.cn/mall/api/about";
    public final static String URL_HELP = "http://mall.xdiandian.cn/mall/api/help";
    public final static String URL_GOODS_DETAIL = "http://mall.xdiandian.cn/mall/api/meDetail?id=";
    public final static String URL = "app/outinterface";
    //    public final static String PID = "123222212121";  //    pid
    public final static String SUCCESE_CODE = "1"; // 返回结果成功

    public static class key {
        public static final String USERID = "userid";
        public static final String PID = "pid";
    }

    public static class codeType {
        //1注册2登录 3修改密码 4修改手机号（如果是注册，该接口判断手机号是否存在）
        public static final String REGISTER = "1";
        public static final String LOGIN = "2";
        public static final String UPDATE_PWD = "3";
        public static final String UPDATE_PHONE = "4";
    }

    public static class UrLType {
        // 发送验证码
        public static final String GET_CODE = "101";
        // 登陆-密码
        public static final String LOGIN_PWD = "102";
        // 登陆-验证码
        public static final String LOGIN_CODE = "103";
        // 注册
        public static final String REGISTER = "104";
        //修改密码
        public static final String UPDATE_PWD = "106";
        //验证推荐号码是否存在
        public static final String CHECK_RECOMMEND = "107";
        //首页
        public static final String GET_HOME_DATA = "201";
        //搜索
        public static final String URL_SEARCH = "202"; //  search
        //获取分类
        public static final String URL_ALL_GOODS_TYPE = "203";
        //全部商品
        public static final String URL_ALL_GOODS = "204";
        //商品详情
        public static final String URL_GOODS_DETAIL = "205"; //   goods
        public static final String URL_ADD_TO_CART = "206"; //  all goods
        public static final String URL_DEL_CART = "207"; //  del goods
        public static final String URL_CART_DATA = "208"; //  cart data
        public static final String URL_ADD_CART_GOODSNUM = "209"; //  add cart goods num
        public static final String URL_GOODS_COMMENT = "210"; // todo  商品评论

        //我的首页
        public static final String MY_HOME = "301";
        //userinfo
        public static final String USERINFO = "302";
        //修改头像
        public static final String UPDATE_PHOTO = "clientupload/updateUserPhoto";
        //修改名字
        public static final String UPDATE_NAME = "304";
        //修改性别
        public static final String UPDATE_SEX = "305";
        //修改手机
        public static final String UPDATE_PHONE = "306";
        //验证原手机号验证码
        public static final String CHECK_PHONE = "307";
        //修改身份证号
        public static final String UPDATE_IC = "308";
        // 收藏
        public static final String URL_COLLECT_GOODS= "309";
        // 取消收藏
        public static final String URL_COLLECT_DELETE= "310";
        // 收藏
        public static final String URL_COLLECT_LIST= "311";
        // 收藏
        public static final String URL_COLLECT_BROWSE= "312";
        //钱包
        public static final String MY_WALLET="313";
        // 订单详情
        public static final String URL_ORDER_LIST = "401";//  order list
        public static final String URL_ORDER_DETAIL = "402";//  order detail
        public static final String URL_ORDER_FROM_CART = "403";//  order from cart
        public static final String URL_ORDER_FROM_DETAIL = "404";//  order from detail
        public static final String URL_CANCEL_ORDER = "405";//  order cancel
        public static final String URL_UPDATE_ORDER_ADDR = "406";//  update order addr
        public static final String URL_refund_ORDER = "407";// todo order refund
        public static final String URL_ADDR_LIST = "408";//  addr list
        public static final String URL_ADD_ADDR = "409";//  addr add
        public static final String URL_UPDATE_ADDR = "410";//  addr update
        public static final String URL_DEFAULT_ADDR = "411";//  addr default
        public static final String URL_DEL_ADDR = "412";//  addr del
        public static final String URL_Select_ADDR_MAP = "413";//  addr map
        public static final String URL_OREDER_COMMENT= "414";// todo order comment
        //关注好友
        public static final String FRIEDNS_KEEP = "501";
        //取消关注
        public static final String FRIEDNS_UNKEEP = "502";
        //获取我的好友
        public static final String FRIEDNS_GET = "503";
        //发布圈子
        public static final String FRIEDNS_SEND = "clientupload/addCommunity";
        //删除圈子
        public static final String FRIEDNS_DELETE = "505";
        //圈子列表
        public static final String FRIEDNS_LIST = "506";
        //圈子详情
        public static final String FRIEDNS_DETAIL = "507";
        //圈子评论列表
        public static final String FRIEDNS_MESSAGE = "508";
        //点赞
        public static final String FRIEDNS_LIKE = "509";
        //评论
        public static final String FRIEDNS_COMMENT = "510";

    }

}
