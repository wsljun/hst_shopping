package com.cj.reocrd.ui;

import android.util.ArrayMap;

import com.alibaba.fastjson.JSONObject;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.FirstBean;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.http.Query;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void test_apimodel_parse(){
        User u1 = new User();
        u1.setName("a");

        User u2 = new User();
        u2.setName("b");

        List<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);


        Teacher teacher = new Teacher();
        teacher.setName("c");
        teacher.setUserList(userList);



        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("ssss");
        apiResponse.setStatusCode("1");

        apiResponse.setResults(userList);

//        ApiModel.parse(apiResponse, User.class);
        ApiResponse apiResponse1 = ApiModel.parseFastJson(JSONObject.toJSONString(apiResponse), User.class);
//        System.out.println("");
    }


    @Test
    public void getData(){
        // 说明参数
        String phone = "15311780968";
        int type = 1;
        // 封装cipher秘文
        String data = "{\"phone\":\"" + phone + "\",\"type\":\"" + type + "\"}";
        String pid = "123222212121";

        HashMap<String,Object> map = new HashMap<>();
        map.put("phone",phone);   // 请求接口
        map.put("type",pid); // 设备唯一码
//        ApiModel.getInstance().getData("101",pid,map,FirstBean.class);

    }

}