package com.cj.reocrd.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */

public class CommentBean implements Parcelable {
    private List<Comment> clist;

    public CommentBean(){

    }

    protected CommentBean(Parcel in) {
        clist = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
        @Override
        public CommentBean createFromParcel(Parcel in) {
            return new CommentBean(in);
        }

        @Override
        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };

    public List<Comment> getClist() {
        return clist;
    }

    public void setClist(List<Comment> clist) {
        this.clist = clist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(clist);
    }

    public static class Comment implements Parcelable {
        private String uid;
        private String name;
        private String comment;
        private String photo;
        private String createtime;
        public Comment(){

        }

        protected Comment(Parcel in) {
            uid = in.readString();
            name = in.readString();
            comment = in.readString();
            photo = in.readString();
            createtime = in.readString();
        }

        public static final Creator<Comment> CREATOR = new Creator<Comment>() {
            @Override
            public Comment createFromParcel(Parcel in) {
                return new Comment(in);
            }

            @Override
            public Comment[] newArray(int size) {
                return new Comment[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(uid);
            dest.writeString(name);
            dest.writeString(comment);
            dest.writeString(photo);
            dest.writeString(createtime);
        }
    }
}
