package com.gangtise.gdk.model;

/**
 * @author: created by qinsen
 * date: 2021/1/6
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 时间序列数据基类
 **/
public class GSeQuence extends GRequestQuote {
    private String code;
    private Integer begin = -1;
    private Integer count = 25;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;

    /*    String cmd = "code = '" + code +"';";
        assembleTask2SetParams(cmd);*/

    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;

/*        String cmd = "begin = " + begin +";";
        assembleTask2SetParams(cmd);*/
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;

/*        String cmd = "count = " + count +";";
        assembleTask2SetParams(cmd);*/
    }
}
