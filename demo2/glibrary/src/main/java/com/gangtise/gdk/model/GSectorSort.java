package com.gangtise.gdk.model;

/**
 * @author: created by qinsen
 * date: 2021/1/14 11
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 板块排序
 **/
public class GSectorSort extends GRequestQuote{

    private String sectorId;  //板块id
    private String codes;  //排序代码列表
    private String sortField; //排序字段名称
    private String sortType; //排序方向 desc 降序  asc 升序 none 无序
    private int begin;  //开始位置
    private int count;  //条数

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;

        String cmd = "sectorId = '" + sectorId +"';";
        assembleTask2SetParams(cmd);
    }

    public void setCodes(String codes) {
        this.codes = codes;

        String cmd = "codes = '" + codes +"';";
        assembleTask2SetParams(cmd);
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;

        String cmd = "sortField = '" + sortField +"';";
        assembleTask2SetParams(cmd);
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;

        String cmd = "sortType = '" + sortType +"';";
        assembleTask2SetParams(cmd);
    }

    public void setBegin(int begin) {
        this.begin = begin;

        String cmd = "begin = " + begin +";";
        assembleTask2SetParams(cmd);
    }

    public void setCount(int count) {
        this.count = count;

        String cmd = "count = " + count +";";
        assembleTask2SetParams(cmd);
    }



    public String getSectorId() {
        return sectorId;
    }

    public String getCodes() {
        return codes;
    }

    public String getSortField() {
        return sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public int getBegin() {
        return begin;
    }

    public int getCount() {
        return count;
    }
}
