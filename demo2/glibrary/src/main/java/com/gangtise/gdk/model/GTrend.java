package com.gangtise.gdk.model;

/**
 * @author: created by qinsen
 * date: 2021/1/14 10
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 获取分时数据（含多日）
 **/
public class GTrend extends GSeQuence{

    private int days; //获取分时数据的天数 1-10

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;

        String cmd = "setParam('" + days +"',"+days+ ");" ;
        assembleTask2SetParams(cmd);
    }

}
