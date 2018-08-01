package com.cj.reocrd.model.entity;

import com.cj.reocrd.utils.Utils;

/**
 * "xs": "100", //销售分佣
 "gl": "100",//管理津贴
 "dl": "100",//代理获利
 "jq": "100",//加权分红
 "cj": "100"//大转盘
 "leader": "100"，//领导分红
 "isleader": "1"//是否是领导人 1是 0否  如果是1就显示 领导分红
 "xz": "100"，//行政分红
 "isxzry": "1"//是否是行政人员 1是 2否  如果是1就显示
 }
 */

public class YongJINBean {
    private String xs;
    private String gl;
    private String dl;
    private String jq;
    private String cj;
    private String leader;
    private String isleader;
    private String xz;
    private String isxzry;

    public String getXz() {
        return Utils.strDivide(xz);
    }

    public void setXz(String xz) {
        this.xz = xz;
    }

    public String getIsxzry() {
        return isxzry;
    }

    public void setIsxzry(String isxzry) {
        this.isxzry = isxzry;
    }

    public String getIsleader() {
        return isleader;
    }

    public String getLeader() {
        return Utils.strDivide(leader);
    }

    public void setIsleader(String isleader) {
        this.isleader = isleader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

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
