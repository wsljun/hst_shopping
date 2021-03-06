package com.cj.reocrd.model.entity;

import com.cj.reocrd.utils.Utils;

/**
 */

public class RefundDetail {

    /**
     * reason : JFK疾病防控
     * money : 20000
     * osn : 4ffed43f-208d-4954-8859-9a393bead06b
     * status : 5
     */

    private String reason;
    private String money;
    private String osn;
    private String status;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        money = Utils.strDivide(money);
        this.money = money;
    }

    public String getOsn() {
        return osn;
    }

    public void setOsn(String osn) {
        this.osn = osn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
