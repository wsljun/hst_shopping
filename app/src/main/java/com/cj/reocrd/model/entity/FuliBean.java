package com.cj.reocrd.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26.
 */

public class FuliBean {
    private List<Fuli> wlist;
    private List<Fuli> rlist;

    public List<Fuli> getWlist() {
        return wlist;
    }

    public void setWlist(List<Fuli> wlist) {
        this.wlist = wlist;
    }

    public List<Fuli> getRlist() {
        return rlist;
    }

    public void setRlist(List<Fuli> rlist) {
        this.rlist = rlist;
    }

    public class Fuli {
        private String id;
        private String detail;
        private String stock;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }
    }
}
