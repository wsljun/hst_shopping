package com.cj.reocrd.model.entity;

/**
 * Created by Lyndon.Li on 2018/4/2.
 * 商品分类
 */

public class GoodsType {
       private String id ; // 类型id
       private String name ; // 类型名称
       private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
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
}
