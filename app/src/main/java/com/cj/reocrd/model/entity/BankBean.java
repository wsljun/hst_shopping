package com.cj.reocrd.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/26.
 */

public class BankBean implements Serializable {
    private String tax;
    private List<Bank> blist;

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public List<Bank> getBlist() {
        return blist;
    }

    public void setBlist(List<Bank> blist) {
        this.blist = blist;
    }

    public static class Bank implements Serializable {
        private String id;
        private String name;
        private String code;

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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }


}
