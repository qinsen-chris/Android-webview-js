package com.gangtise.gdk.model;

/**
 * @author: created by qinsen
 * date: 2021/1/14 10
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 获取历史分时数据
 **/
public class GHisTrend extends GSeQuence{

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;

        String cmd = "date = '" + date +"';";
        assembleTask2SetParams(cmd);
    }
}
