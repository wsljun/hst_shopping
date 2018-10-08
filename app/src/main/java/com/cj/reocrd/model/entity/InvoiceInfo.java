package com.cj.reocrd.model.entity;

import com.cj.reocrd.utils.Utils;

/**
 * 发票信息
 * "list": [{
 “id”:”订单ID”,
 “sn”:”订单编号”,
 “isapply”:” 1已申请 2未申请 ”
 “invoicemoney”:”发票金额   分”
 “createtime”:”下单时间”
 },{},{}...]
 */

public class InvoiceInfo {

    private String id;
    private String sn ; // 订单号
    private String isapply;
    private String invoicemoney; // 发票金额
    private String createtime; // 订单时间
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getIsapply() {
        return isapply;
    }

    public void setIsapply(String isapply) {
        this.isapply = isapply;
    }

    public String getInvoicemoney() {
        return invoicemoney;
    }

    public void setInvoicemoney(String invoicemoney) {
        this.invoicemoney = Utils.strDivide(invoicemoney);
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
