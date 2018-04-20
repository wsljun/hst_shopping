package com.cj.reocrd.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lyndon.Li on 2018/4/15.
 * mView.acticonAddToCart(apiResponse); //
 * {"consignee":"新增3","phone":"18825142536",
 * "fuladdress":"湖北省荆州市荆州区湖北人","oid":"9fd17ffc-f146-44ad-8d26-19fa0abfad8d","
 * message":"操作成功","aid":"湖北省荆州市荆州区湖北人","statusCode":"1"}
 */

public class OrderBean implements Parcelable{
    private String fuladdress ; // 详细地址
    private String consignee ; // 收货人
    private String phone ; // 收货人地址
    private String oid;// 订单id
    private String aid ; // 地址id


    public OrderBean(){

    }

    protected OrderBean(Parcel in) {
        fuladdress = in.readString();
        consignee = in.readString();
        phone = in.readString();
        oid = in.readString();
        aid = in.readString();
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel in) {
            return new OrderBean(in);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fuladdress);
        dest.writeString(consignee);
        dest.writeString(phone);
        dest.writeString(oid);
        dest.writeString(aid);

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

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }
}
