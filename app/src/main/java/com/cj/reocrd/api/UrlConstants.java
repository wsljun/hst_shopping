package com.cj.reocrd.api;

/**
 */

public class UrlConstants {
    //BaseURl
    public final static String BASE_URL = "http://www.rendaoyigou.com/"; //"http://mall.xdiandian.cn/mall/";
//    public final static String BASE_URL = "http://192.168.0.100:8080/mall/"; //"http://mall.xdiandian.cn/mall/";
    //test
//    public final static String BASE_URL = "http://mall.xdiandian.cn/mall/";
    public final static String URL_ABOUT = BASE_URL+"api/about";
    public final static String URL_HELP = BASE_URL+"api/help";
    public final static String URL_GOODS_DETAIL = BASE_URL+"api/meDetail?id=";
    public final static String URL_SHARE_REGISTER = BASE_URL+"api/appRegister?code=";
    public final static String URL = "app/outinterface";
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
        // 检查APP 更新
        public static final String CHECK_APP_UPDATE = "108";
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
        public static final String URL_GOODS_COMMENT = "210"; //   商品评论

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
        //获取提现手续比例ratio
        public static final String GET_RATIO="314";
        //银行列表
        public static final String CARD_LIST="315";
        //我的银行卡
        public static final String MY_CARD="316";
        //绑定银行卡
        public static final String BIND_CARD="317";
        //提现申请
        public static final String WALLET_GET="318";
        //福利列表
        public static final String WEAL_LIST="319";
        //福利列表convert兑换
        public static final String WEAL_CONVERT="320";
        //获取抽奖六个级别金额
        public static final String LOTTERY_LEVEL="321";
        //判断该用户当前时间段是否允许抽奖
        public static final String LOTTERY_CAN="322";
        //保存抽奖结果
        public static final String LOTTERY_SAVE_RESULT="323";
        //获取抽奖结果
        public static final String LOTTERY_GET_RESULT="324";
        //绑定支付宝账号
        public static final String ZHIFUBAO="325";
        //获取佣金
        public static final String URL_GET_YONGJIN="326";
        //我的团队
        public static final String GET_MYTEAM = "327";
        // 订单详情
        public static final String URL_ORDER_LIST = "401";//  order list
        public static final String URL_ORDER_DETAIL = "402";//  order detail
        public static final String URL_ORDER_FROM_CART = "403";//  order from cart
        public static final String URL_ORDER_FROM_DETAIL = "404";//  order from detail
        public static final String URL_CANCEL_ORDER = "405";//  order cancel
        public static final String URL_UPDATE_ORDER_ADDR = "406";//  update order addr
        public static final String URL_REFUND_ORDER = "407";//  order refund
        public static final String URL_ADDR_LIST = "408";//  addr list
        public static final String URL_ADD_ADDR = "409";//  addr add
        public static final String URL_UPDATE_ADDR = "410";//  addr update
        public static final String URL_DEFAULT_ADDR = "411";//  addr default
        public static final String URL_DEL_ADDR = "412";//  addr del
        public static final String URL_SELECT_ADDR_MAP = "413";//  addr map
        public static final String URL_OREDER_COMMENT= "414";//  order comment
        public static final String URL_OREDER_CONFIRM= "415";//  确认收货
        public static final String URL_OREDER_REFUND_DETAIL= "416";//  退款详情
        public static final String URL_OREDER_PAY_SUCCESS= "417";//  支付成功
        public static final String URL_OREDER_PAY_KEY= "418";//  支付key
        //关注好友
        public static final String FRIEDNS_KEEP = "501";
        //取消关注
        public static final String FRIEDNS_UNKEEP = "502";
        //以关注
        public static final String FRIEDNS_MYFANS = "503";
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
        //粉丝
        public static final String FRIEDNS_MYKEEP = "511";
    }

}
