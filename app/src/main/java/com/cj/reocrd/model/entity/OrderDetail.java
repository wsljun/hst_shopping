package com.cj.reocrd.model.entity;

import com.cj.reocrd.utils.Utils;
import com.google.gson.annotations.SerializedName;

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
    /**
     * expname : 百世快递
     * expinfo : {"message":"ok","state":"3","data":[{"context":"北京市|北京市【金盏分部】，拍照签收 已签收","time":"2018-04-22 12:53:39","ftime":"2018-04-22 12:53:39"},{"context":"北京市|北京市【金盏分部】，【闫长登/17710705604】正在派件","time":"2018-04-22 07:32:09","ftime":"2018-04-22 07:32:09"},{"context":"北京市|到北京市【金盏分部】","time":"2018-04-21 23:42:44","ftime":"2018-04-21 23:42:44"},{"context":"北京市|北京市【北京转运中心】，正发往【金盏分部】","time":"2018-04-21 09:04:40","ftime":"2018-04-21 09:04:40"},{"context":"北京市|到北京市【北京转运中心】","time":"2018-04-21 08:42:59","ftime":"2018-04-21 08:42:59"},{"context":"太原市|太原市【太原转运中心】，正发往【北京转运中心】","time":"2018-04-20 23:38:15","ftime":"2018-04-20 23:38:15"},{"context":"太原市|到太原市【太原转运中心】","time":"2018-04-20 19:45:16","ftime":"2018-04-20 19:45:16"},{"context":"太原市|太原市【清徐县】，正发往【太原转运中心】","time":"2018-04-20 17:15:43","ftime":"2018-04-20 17:15:43"},{"context":"太原市|太原市【清徐县】，【程永刚/18534572555】已揽收","time":"2018-04-20 16:56:21","ftime":"2018-04-20 16:56:21"}],"condition":"F00","status":"200","ischeck":"1","com":"huitongkuaidi","nu":"50905742808412"}
     */

    private String expname;
    private ExpinfoBean expinfo;


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
        amount = Utils.strDivide(amount);
        this.amount = amount;
    }

    public String getExamount() {
        return examount;
    }

    public void setExamount(String examount) {
        examount = Utils.strDivide(examount);
        this.examount = examount;
    }

    public String getAllamount() {
        return allamount;
    }

    public void setAllamount(String allamount) {
        allamount = Utils.strDivide(allamount);
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

    public String getExpname() {
        return expname;
    }

    public void setExpname(String expname) {
        this.expname = expname;
    }

    public ExpinfoBean getExpinfo() {
        return expinfo;
    }

    public void setExpinfo(ExpinfoBean expinfo) {
        this.expinfo = expinfo;
    }

    public static class ExpinfoBean {
        /**
         * message : ok
         * state : 3
         * data : [{"context":"北京市|北京市【金盏分部】，拍照签收 已签收","time":"2018-04-22 12:53:39","ftime":"2018-04-22 12:53:39"},{"context":"北京市|北京市【金盏分部】，【闫长登/17710705604】正在派件","time":"2018-04-22 07:32:09","ftime":"2018-04-22 07:32:09"},{"context":"北京市|到北京市【金盏分部】","time":"2018-04-21 23:42:44","ftime":"2018-04-21 23:42:44"},{"context":"北京市|北京市【北京转运中心】，正发往【金盏分部】","time":"2018-04-21 09:04:40","ftime":"2018-04-21 09:04:40"},{"context":"北京市|到北京市【北京转运中心】","time":"2018-04-21 08:42:59","ftime":"2018-04-21 08:42:59"},{"context":"太原市|太原市【太原转运中心】，正发往【北京转运中心】","time":"2018-04-20 23:38:15","ftime":"2018-04-20 23:38:15"},{"context":"太原市|到太原市【太原转运中心】","time":"2018-04-20 19:45:16","ftime":"2018-04-20 19:45:16"},{"context":"太原市|太原市【清徐县】，正发往【太原转运中心】","time":"2018-04-20 17:15:43","ftime":"2018-04-20 17:15:43"},{"context":"太原市|太原市【清徐县】，【程永刚/18534572555】已揽收","time":"2018-04-20 16:56:21","ftime":"2018-04-20 16:56:21"}]
         * condition : F00
         * status : 200
         * ischeck : 1
         * com : huitongkuaidi
         * nu : 50905742808412
         */

        @SerializedName("message")
        private String messageX;
        private String state;
        private String condition;
        @SerializedName("status")
        private String statusX;
        private String ischeck;
        private String com;
        private String nu;
        private List<DataBean> data;

        public String getMessageX() {
            return messageX;
        }

        public void setMessageX(String messageX) {
            this.messageX = messageX;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getStatusX() {
            return statusX;
        }

        public void setStatusX(String statusX) {
            this.statusX = statusX;
        }

        public String getIscheck() {
            return ischeck;
        }

        public void setIscheck(String ischeck) {
            this.ischeck = ischeck;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getNu() {
            return nu;
        }

        public void setNu(String nu) {
            this.nu = nu;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * context : 北京市|北京市【金盏分部】，拍照签收 已签收
             * time : 2018-04-22 12:53:39
             * ftime : 2018-04-22 12:53:39
             */

            private String context;
            private String time;
            private String ftime;

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getFtime() {
                return ftime;
            }

            public void setFtime(String ftime) {
                this.ftime = ftime;
            }
        }
    }
}
