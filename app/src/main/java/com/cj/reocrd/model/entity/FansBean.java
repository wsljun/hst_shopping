package com.cj.reocrd.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class FansBean {
    private List<Fans> fs;

    public List<Fans> getFs() {
        return fs;
    }

    public void setFs(List<Fans> fs) {
        this.fs = fs;
    }

    public class Fans {
        private String fid;
        private String img;
        private String name;
        private String accid;

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
