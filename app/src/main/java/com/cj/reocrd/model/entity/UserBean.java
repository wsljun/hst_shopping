package com.cj.reocrd.model.entity;

/**
 * Created by Administrator on 2018/3/30.
 */

public class UserBean {
    private String id; //用户ID
    private String name;//用户姓名
    private String phone;//手机号  没有名字就填手机号
    private String photo;//头像地址
    private String sex;//性别 1男 2女
    private String recommend;//推荐码
    private String ic;//身份证号

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
