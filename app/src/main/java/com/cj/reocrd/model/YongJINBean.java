package com.cj.reocrd.model;

import com.cj.reocrd.utils.Utils;

/**
 * "xs": "100", //销售分佣
 "gl": "100",//管理津贴
 "dl": "100",//代理获利
 "jq": "100",//加权分红
 "cj": "100"//大转盘
 }
 */

public class YongJINBean {
    private String xs;
    private String gl;
    private String dl;
    private String jq;
    private String cj;

    public String getXs() {
        return Utils.strDivide(xs);
    }

    public void setXs(String xs) {
        this.xs = xs;
    }

    public String getGl() {
        return Utils.strDivide(gl);
    }

    public void setGl(String gl) {
        this.gl = gl;
    }

    public String getDl() {
        return Utils.strDivide(dl);
    }

    public void setDl(String dl) {
        this.dl = dl;
    }

    public String getJq() {
        return Utils.strDivide(jq);
    }

    public void setJq(String jq) {
        this.jq = jq;
    }

    public String getCj() {
        return Utils.strDivide(cj);
    }

    public void setCj(String cj) {
        this.cj = cj;
    }
}
