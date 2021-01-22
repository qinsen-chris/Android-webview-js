package com.gangtise.gdk.result.dataprocess;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: created by qinsen
 * date: 2021/1/19 20
 * email: qinshen@Gangtise.onaliyun.com
 *
 *     /**
 *      * {
 *      * 	"data": {
 *      * 		"result": [{
 *      * 	        "table": {
 *      *
 **/

public class DataResult {
    private Table table;
    private Integer httpId; //http请求
    private Integer updateCounter ; //http请求

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Integer getHttpId() {
        return httpId;
    }

    public void setHttpId(Integer httpId) {
        this.httpId = httpId;
    }

    public Integer getUpdateCounter() {
        return updateCounter;
    }

    public void setUpdateCounter(Integer updateCounter) {
        this.updateCounter = updateCounter;
    }


}
