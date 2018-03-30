package com.cj.reocrd.api;

/**
 * Created by Lyndon.Li on 2018/3/17.
 */

public class UrlConstants {
    //BaseURl
//    public final static String BASE_URL = "http://gank.io/api/";
    public final static String BASE_URL = "http://mall.xdiandian.cn/mall/";
    public final static String URL = "app/outinterface";
    public final static String PID = "123222212121";  //  TODO  pid
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
        // 注册
        public static final String REGISTER= "104";
        //todo add more url type
        public static final  String GET_HOME_DATA = "201";

   }

}
