package com.cj.reocrd.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lyndon.Li on 2018/4/3.
 * 商品规格
 * "slist": [{  //规格
 "id": "abcd",   //规格ID
 "num": "10",  //规格数值 比如 10KG
 "price": "100",   //价格
 "stock": "100",   //库存
 "blocknum": "100"  //售出数量
 },{},{}...],
 */

public class SkuBean implements Parcelable {
    private String id ;
    private String num ;
    private String price ;
    private String stock ;
    private String blocknum;

    public SkuBean(){

    }


    protected SkuBean(Parcel in) {
        id = in.readString();
        num = in.readString();
        price = in.readString();
        stock = in.readString();
        blocknum = in.readString();
    }

    public static final Creator<SkuBean> CREATOR = new Creator<SkuBean>() {
        @Override
        public SkuBean createFromParcel(Parcel in) {
            return new SkuBean(in);
        }

        @Override
        public SkuBean[] newArray(int size) {
            return new SkuBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        int p = (Integer.parseInt(price))/100;
        price = String.valueOf(p);
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getBlocknum() {
        return blocknum;
    }

    public void setBlocknum(String blocknum) {
        this.blocknum = blocknum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(num);
        dest.writeString(price);
        dest.writeString(stock);
        dest.writeString(blocknum);
    }
}
