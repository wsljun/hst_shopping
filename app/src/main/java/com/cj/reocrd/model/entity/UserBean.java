package com.cj.reocrd.model.entity;

/**
 * 用户info
 */

public class UserBean {
    private String id; //用户ID
    private String name;//用户姓名
    private String phone;//手机号  没有名字就填手机号
    private String photo;//头像地址
    private String sex;//性别 1男 2女
    private String recommend;//推荐码
    private String ic;//身份证号
    private String alipay;//支付宝账号
    private String accid;//云信id
    private String token;//云信token
    private String manlevel; //"manlevel": "0 " ,//0 普通消费者 1主管 2主任3经理4总监
    private String num;//我的团队人数
    private String codeimg; // 分享注册二维码
    private String type ;   // 1是，2否 （是否消费商）

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setCodeimg(String codeimg) {
        this.codeimg = codeimg;
    }

    public String getCodeimg() {
        return codeimg;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getManlevel() {
        return manlevel;
    }

    public void setManlevel(String manlevel) {
        this.manlevel = manlevel;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
