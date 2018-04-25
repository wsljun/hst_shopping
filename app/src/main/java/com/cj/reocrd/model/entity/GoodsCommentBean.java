package com.cj.reocrd.model.entity;

/**
 * Created by Lyndon.Li on 2018/4/25.
 * 商品详细下的评论
 */

public class GoodsCommentBean {


    /**
     * content : 浙江安吉发吧积分了
     * id : 721f7d35-a40e-4747-a8ed-3f9a3c6d3756
     * name : 曹星星
     * photo : mall.xdiandian.cn/static/img/photo.png
     * rname : 管理员
     * rphoto : static/img/photo.png
     * reply : asdasdasd
     * createtime : 2018-04-24 23:03:04
     * replytime : 2018-04-24 23:53:56
     */

    private String content;
    private String id;
    private String name;
    private String photo;
    private String rname;
    private String rphoto;
    private String reply;
    private String createtime;
    private String replytime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
