package com.cj.reocrd.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */

public class CommentBean {
    private List<Comment> clist;

    public List<Comment> getClist() {
        return clist;
    }

    public void setClist(List<Comment> clist) {
        this.clist = clist;
    }

    public class Comment {
        private String uid;
        private String name;
        private String comment;
        private String photo;
        private String createtime;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
