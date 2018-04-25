package com.cj.reocrd.model.entity;

import java.util.List;

/**
 * Created by Lyndon.Li on 2018/4/15.
 */

public class OrderDetail {

    /**
     * sn : dadef3bbe14e4f198bcef277ef7a7bd5  // 订单编号
     * way : null                            // 支付方式
     * statusCode : 1
     * message : 获取成功
     * id : a3796d4a-32c6-4755-b614-fcf690c3cffb
     * amount : 30000
     * examount : 0
     * allamount : 30000
     * createtime : 2018-04-21 2124:27:10
     * status : 1
     * fuladdress : null
     * consignee : null
     * phone : null
     * opinion : null
     * isdel : 1
     * odlist : [{"id":"345cb165-1687-4089-bdb1-4dbcc0940d6a","mname":"小米","mid":"bd0aa62f-f833-4535-b32e-62d42212c36b","spec":"1KG","num":"1","price":"30000","totalPrice":"30000"}]
     */

    private String sn;
    private String way;
    private String statusCode;
    private String message;
    private String id;
    private String amount;
    private String examount;
    private String allamount;
    private String createtime;
    private String status;
    private String fuladdress;
    private String consignee;
    private String phone;
    private String opinion;
    private String isdel;
    private List<OrderBean.OdlistBean> odlist;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        int p = (Integer.parseInt(amount))/100;
        amount = String.valueOf(p);
        this.amount = amount;
    }

    public String getExamount() {
        return examount;
    }

    public void setExamount(String examount) {
        int p = (Integer.parseInt(examount))/100;
        examount = String.valueOf(p);
        this.examount = examount;
    }

    public String getAllamount() {
        return allamount;
    }

    public void setAllamount(String allamount) {
        int p = (Integer.parseInt(allamount))/100;
        allamount = String.valueOf(p);
        this.allamount = allamount;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public List<OrderBean.OdlistBean> getOdlist() {
        return odlist;
    }

    public void setOdlist(List<OrderBean.OdlistBean> odlist) {
        this.odlist = odlist;
    }

}
