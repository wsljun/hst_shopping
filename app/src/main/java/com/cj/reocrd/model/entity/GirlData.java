
package com.cj.reocrd.model.entity;

import java.util.List;

public class GirlData<T> {
    private boolean isError;
    private T results;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    private List<T> dataList;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public T getResults() {
        return results;
    }
}
