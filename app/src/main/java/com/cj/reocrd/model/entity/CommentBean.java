package com.cj.reocrd.model.entity;

/**
 * Created by Lyndon.Li on 2018/4/3.
 * 评论列表
 *"clist": [{  //评论列表
 "id": "abcd",   //评论ID
 "name": "abcd",   //评论人名字
 "photo": "http://www.baidu.com",   //评论人头像
 "rname": "rname",  //回复人姓名
 "rphoto": "rphoto",  //回复人头像
 "reply": "reply",  //回复内容
 "createtime": "2018-01-20 21:30:40",  //评论时间
 "replytime": "2018-01-20 21:30:40"  //回复时间
 },{},{}...]
 *
 */

public class CommentBean {
    private String id ;
    private String name ;
    private String photo ;
    private String rname ;
    private String rphoto;
    private String reply;
    private String createtime;
    private String replytime;


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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRphoto() {
        return rphoto;
    }

    public void setRphoto(String rphoto) {
        this.rphoto = rphoto;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getReplytime() {
        return replytime;
    }

    public void setReplytime(String replytime) {
        this.replytime = replytime;
    }
}
