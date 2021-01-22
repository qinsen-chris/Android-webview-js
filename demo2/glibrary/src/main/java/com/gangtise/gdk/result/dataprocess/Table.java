package com.gangtise.gdk.result.dataprocess;

import com.alibaba.fastjson.JSONObject;
import com.gangtise.gdk.common.GtsArrayList;

import java.util.List;

/**
 * @author: created by qinsen
 * date: 2021/1/20 09
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class Table {

    private List<List<String>> field;
    //        private TableInfo info;
//        private TableGid gid;  //socket
    private Object info;
    private Object gid;  //socket
    private JSONObject flags;
    private List<List<Object>> data;
    private List<List<Object>> adjust; //socket
    private JSONObject factor; //socket

    //GDataSet中getCellData，创建cellData ,  按列式存储 table.cellData[cIndex][rIndex] = cData;
/*    private GtsArrayList<GtsArrayList<TableCellData>> cellData;

    public GtsArrayList<GtsArrayList<TableCellData>> getCellData() {
        return cellData;
    }

    public void setCellData(GtsArrayList<GtsArrayList<TableCellData>> cellData) {
        this.cellData = cellData;
    }*/
    TableCellData [][] cellData ;

    public TableCellData[][] getCellData() {
        return cellData;
    }

    public void setCellData(TableCellData[][] cellData) {
        this.cellData = cellData;
    }


    public List<List<String>> getField() {
        return field;
    }

    public void setField(List<List<String>> field) {
        this.field = field;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public Object getGid() {
        return gid;
    }

    public void setGid(Object gid) {
        this.gid = gid;
    }

    public JSONObject getFlags() {
        return flags;
    }

    public void setFlags(JSONObject flags) {
        this.flags = flags;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    public List<List<Object>> getAdjust() {
        return adjust;
    }

    public void setAdjust(List<List<Object>> adjust) {
        this.adjust = adjust;
    }

    public JSONObject getFactor() {
        return factor;
    }

    public void setFactor(JSONObject factor) {
        this.factor = factor;
    }
}
