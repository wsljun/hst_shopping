package com.cj.reocrd.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Lyndon.Li on 2018/4/15.
 */

public class AddressBean {
    private String id ;
    private String re_id ; // 县级id
    private String c_re_id ; //市级id
    private String p_re_id ; //省级id
    private String address ; // 详细地址
    private String fuladdress ; // 地区全称+详细地址
    private String consignee ; // 收货人
    private String phone ; // 收货人地址
    private String postcode ; // 邮编
    private String isdefault ; // 是否默认 （1：是；2：不是）
    private String createtime ; // 创建时间
    private List<Regions> regions ; //


    public class Regions {
        /*
        * "name":"湖南省",
　　　　　　"id":"080c36e4-0e36-11e3-977a-d43d7e9c965f"
        * */
        private String id ;
        private String name ;

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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRe_id() {
        return re_id;
    }

    public void setRe_id(String re_id) {
        this.re_id = re_id;
    }

    public String getC_re_id() {
        return c_re_id;
    }

    public void setC_re_id(String c_re_id) {
        this.c_re_id = c_re_id;
    }

    public String getP_re_id() {
        return p_re_id;
    }

    public void setP_re_id(String p_re_id) {
        this.p_re_id = p_re_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFuladdress() {
        return fuladdress;
    }

    public void setFuladdress(String fuladdress) {
        this.fuladdress = fuladdress;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public List<Regions> getRegions() {
        return regions;
    }

    public void setRegions(List<Regions> regions) {
        this.regions = regions;
    }
}
