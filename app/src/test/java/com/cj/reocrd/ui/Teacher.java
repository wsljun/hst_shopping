package com.cj.reocrd.ui;

import java.util.List;

/**
 * Created by Lyndon.Li on 2018/3/29.
 * 测试
 */

public class Teacher {
    public String getName() {
        return name;
    }

    public List<User> getUserList() {
        return userList;
    }

    private String name;
    private List<User> userList;

    public void setName(String name) {
        this.name = name;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
