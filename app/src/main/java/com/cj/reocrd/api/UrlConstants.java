package com.cj.reocrd.api;

/**
 * Created by Lyndon.Li on 2018/3/17.
 */

public class UrlConstants {
    //BaseURl
//    public final static String BASE_URL = "http://gank.io/api/";
    public final static String BASE_URL = "http://mall.xdiandian.cn/mall/";
    public final static String URL = "app/outinterface";
//    public final static String PID = "123222212121";  //  TODO  pid
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
        public static final String REGISTER= "104";
        //修改密码
        public static final String UPDATE_PWD= "106";
        //验证推荐号码是否存在
        public static final String CHECK_RECOMMEND= "107";
        //首页
        public static final  String GET_HOME_DATA = "201";
        //搜索
        public static final String  URL_SEARCH = "202"; // todo search
        //获取分类
        public static final String  URL_ALL_GOODS_TYPE = "203";
        //全部商品
        public static final String  URL_ALL_GOODS = "204";
        //商品详情
        public static final String  URL_GOODS_DETAIL = "205"; // todo all goods
        public static final String  URL_ADD_TO_CART = "206"; // todo all goods
        public static final String  URL_DEL_CART = "207"; // todo del goods
        public static final String  URL_CART_DATA= "208"; // todo cart data
        public static final String  URL_ADD_CART_GOODSNUM= "209"; // todo add cart goods num

        //我的首页
        public static final  String MY_HOME = "301";
        //userinfo
        public static final  String USERINFO = "302";
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

        // 订单详情
        public static final String URL_ORDER_LIST = "401";// todo order list
        public static final String URL_ORDER_DETAIL = "402";// todo order detail
        public static final String URL_ORDER_FROM_CART = "403";// todo order from cart
        public static final String URL_ORDER_FROM_DETAIL = "404";// todo order from detail
        public static final String URL_CANCEL_ORDER = "405";// todo order cancel
        public static final String URL_Update_ORDER_addr = "406";// todo update order addr
        public static final String URL_refund_ORDER = "407";// todo order refund
        public static final String URL_ADDR_LIST = "408";// todo addr list
        public static final String URL_ADD_ADDR = "409";// todo addr add
        public static final String URL_UPDATE_ADDR= "410";// todo addr update
        public static final String URL_DEFAULT_ADDR = "411";// todo addr default
        public static final String URL_DEL_ADDR= "412";// todo addr del
        public static final String URL_Select_ADDR_MAP= "413";// todo addr map
   }

}
