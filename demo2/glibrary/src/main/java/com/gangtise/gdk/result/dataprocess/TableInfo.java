package com.gangtise.gdk.result.dataprocess;

/**
 * @author: created by qinsen
 * date: 2021/1/20 09
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class TableInfo {

    private String type;
    private String rank;  //col 列模式、row行模式
    private String compressed;
    private String mode;
    private Integer rows;
    private Integer begin;
    private Integer totalRows;
    private Integer thisBegin;
    private Integer dataRange;

    private Integer dataBegin;  //http
    private Integer id;  //http
    private String msg;  //http
    private String name;  //http
    private String dataRows;  //http


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getCompressed() {
        return compressed;
    }

    public void setCompressed(String compressed) {
        this.compressed = compressed;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getThisBegin() {
        return thisBegin;
    }

    public void setThisBegin(Integer thisBegin) {
        this.thisBegin = thisBegin;
    }

    public Integer getDataRange() {
        return dataRange;
    }

    public void setDataRange(Integer dataRange) {
        this.dataRange = dataRange;
    }

    public Integer getDataBegin() {
        return dataBegin;
    }

    public void setDataBegin(Integer dataBegin) {
        this.dataBegin = dataBegin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataRows() {
        return dataRows;
    }

    public void setDataRows(String dataRows) {
        this.dataRows = dataRows;
    }
}
