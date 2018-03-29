package com.cj.reocrd.api;

import java.util.List;

/**
 * Api响应结果的封装类.
 * @version 1.0
 */
public class ApiResponse<T> {
    private String statusCode;    // 返回码，//1成功 2失败
    private String message;      // 返回信息
    private T results;           // results 单个对象
    private List<T> dataList;    // 数组

    //构造函数
//    public ApiResponse(String statusCode, String message) {
//        this.statusCode = statusCode;
//        this.message = message;
//    }
//
//    ApiResponse(){
//
//    }

    public boolean isSuccess() {
        return statusCode.equals("1");
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

}
