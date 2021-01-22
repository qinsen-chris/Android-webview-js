package com.gangtise.gdk.model;

/**
 * @author: created by qinsen
 * date: 2021/1/14 10
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 获取指数涨跌家数统计数据
 **/
public class GSnapshotUpdowns extends GRequestQuote {

    private String code; //代码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        String cmd = "code('" + code +"');";
        assembleTask2SetParams(cmd);

        this.code = code;
    }


}
