package com.cj.reocrd.model.entity;

import java.util.List;

/**
 * Created by Lyndon.Li on 2018/4/15.
 * mView.acticonAddToCart(apiResponse); //403
 * {"consignee":"新增3","phone":"18825142536",
 * "fuladdress":"湖北省荆州市荆州区湖北人","oid":"9fd17ffc-f146-44ad-8d26-19fa0abfad8d","
 * message":"操作成功","aid":"湖北省荆州市荆州区湖北人","statusCode":"1"}
 */

public class OrderBean {

    /**
     * id : a861dc73-641b-499d-802b-3c4de602e4c8
     * amount : 40000   // 订单金额
     * examount : 0     // k快递费
     * allamount : 40000    // 总额
     * createtime : 2018-04-21 2124:45:44  //创建时间
     * status : 1    // 订单状态
     * isdel : 1    //删除状态(1．未删除 2.已删除3:回收站删除)
     * odlist : [{"imgurl":"static/upload/attimg/4db698008458489fbe92857056a1615e.jpg","id":"15e263bd-1069-48fc-b4b8-83960bbab547","mname":"牛仔裤","mid":"1d7e5bcd-a6be-42da-90ec-3a61e4759003","spec":"1条","num":"1","price":"20000","totalPrice":"20000"},{"imgurl":"static/upload/attimg/9d6588479d0b4f438216debc2a2eb3ee.jpg","id":"f4a17bcc-101c-4186-8bb5-5c71bc9b6736","mname":"大米","mid":"0342a43b-45d4-4546-90f3-b7252b5e09b8","spec":"10斤","num":"1","price":"20000","totalPrice":"20000"}]
     */

    private String id;
    private String amount;
    private String examount;
    private String allamount;
    private String createtime;
    private String status;
    private String isdel;
    private List<OdlistBean> odlist;
    private String oid;  // 同id 立即购买时返回oid 

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public List<OdlistBean> getOdlist() {
        return odlist;
    }

    public void setOdlist(List<OdlistBean> odlist) {
        this.odlist = odlist;
    }

    public static class OdlistBean {
        /**
         * imgurl : static/upload/attimg/4db698008458489fbe92857056a1615e.jpg 商品图片
         * id : 15e263bd-1069-48fc-b4b8-83960bbab547    // 详情id
         * mname : 牛仔裤                              // 商品名称
         * mid : 1d7e5bcd-a6be-42da-90ec-3a61e4759003  // 商品id
         * spec : 1条                                  //规格
         * num : 1                                     // 数量
         * price : 20000                               // 价格
         * totalPrice : 20000
         */

        private String imgurl;
        private String id;
        private String mname;
        private String mid;
        private String spec;
        private String num;
        private String price;
        private String totalPrice;


        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
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
            this.price = price;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            int p = (Integer.parseInt(totalPrice))/100;
            totalPrice = String.valueOf(p);
            this.totalPrice = totalPrice;
        }
    }
}
