package com.gangtise.gdk.result.dataprocess;

import com.alibaba.fastjson.JSONObject;
import com.gangtise.gdk.common.Constant;
import com.gangtise.gdk.common.ContentMap;
import com.gangtise.gdk.result.GDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: created by qinsen
 * date: 2021/1/19 11
 * email: qinshen@Gangtise.onaliyun.com
 *
 **/
public class GDataManager {

    /**
     * 源数据转换为GDataSet
     * @param sourceObject
     * {
     * 	"data": {
     * 	    "result": [{
     * 	    "httpId": 1,
     * 	    "table": {
     * 		    "data": [
     * 		    ...
     *
     * 	},
     * 	"id": 0
     * 	}
     * @return  GDataSet
     */
    public static GDataSet transformDataSource(JSONObject sourceObject){
        Object resultObject = sourceObject.get("data");
        GDataSet gDataSet = refrshDataSet((String) sourceObject.get("id"),resultObject);

        return gDataSet;
    }

    /**
     * GDataManager.js refrshDataSet
     *
     * @param id
     * @param resultObject
     * @return
     */
    public static GDataSet refrshDataSet(String id, Object resultObject){
        GDataSet gDataSet = new GDataSet();

        //解析数据源
        GData data = JSONObject.parseObject(resultObject.toString(), GData.class);
        List<Object> results =  data.getResult();
        if(results.size() == 0){
            return null;
        }
        //JSONObject每次只能解两层对象嵌套
        DataResult dataResult = JSONObject.parseObject(results.get(0).toString(),DataResult.class);
        //table
        Table table = dataResult.getTable();
        //info
        TableInfo tableInfo = JSONObject.parseObject(table.getInfo().toString(), TableInfo.class);
        //gid
        TableGid tableGid = null;
        if(table.getGid()!=null){
            tableGid = JSONObject.parseObject(table.getGid().toString(), TableGid.class);
        }

        //rowCount  要判断是行还是列数据
        if(tableInfo.getRank().equals(Constant.INFO_RANK_COL)){
            List<Object> data0 = table.getData() !=null ? table.getData().get(0) : new ArrayList<>();
            gDataSet.rowCount =data0.size();
        }else{
            gDataSet.rowCount = table.getData() !=null ? table.getData().size() : 0;
        }


        //columnInfos
        List<GColumnInfo> columnInfos = new ArrayList<>();
        if(table.getField()!=null){
            List<List<String>> fields = table.getField();
            int i = 0;
            for(List<String> field : fields){

                GColumnInfo columnInfo = new GColumnInfo();
                String filedName = field.get(0);
                String fieldType = field.get(1);

                //判断是不是字母
                String type = fieldType.substring(1);
                boolean isnumber = isNumber(type);
                Integer fieldDec = isnumber ? Integer.parseInt(type) : null;
                columnInfo.name = filedName;
                columnInfo.type = getFieldType(fieldType);
                columnInfo.index = i+1;
                i++;
                columnInfo.remark = field.size()<=2 ? null : field.get(2) ;
                columnInfo.dec = fieldDec;

                columnInfos.add(columnInfo);
            }
        }else{
            gDataSet.columnInfos = null;
        }
        gDataSet.columnInfos = columnInfos;

        gDataSet.name = tableInfo.getName();
        gDataSet.begin = tableInfo.getBegin();
        gDataSet.colCount = table.getField()!=null ? table.getField().size() : 0;
        gDataSet.totalRows = tableInfo.getTotalRows();

        //源数据data
/*        gDataSet.setData(table.getData());
        gDataSet.setFlags(table.getFlags());*/

        ContentMap.SOURCE_TABLE_MAP.put(id,table);
        //行、列模式
        gDataSet.rank = tableInfo.getRank();
        //id
        gDataSet.id = id;


        return gDataSet;
    }


    /**
     *
     * @param fieldType
     * @return
     */
    private static String getFieldType(String fieldType){
        String result = "text";
        if(fieldType.equals("t")){
            //ta 字符串
            result = GDataType.text;
        }else if(fieldType.equals("ta")){
            //ta 字符串数组
            result = GDataType.array_text;
        }else if(fieldType.equals("th") || fieldType.equals("tm") || fieldType.equals("tms") || fieldType.equals("ts")){
            //th        UTC 精确到小时
            //tm        UTC 精确到分钟
            //tms       UTC 精确到毫秒
            //ts        UTC 精确到秒
            result = GDataType.time;
        }else if(fieldType.equals("dy") || fieldType.equals("dq") || fieldType.equals("dm") || fieldType.equals("dd")){
            result = GDataType.date;
        }else if(fieldType.startsWith("f") || fieldType.startsWith("x")){
            //fx / xx 价类型
            result = GDataType.DOUBLE;
        }else if(fieldType.startsWith("g")){
            //gx.y 图形价类型
            result = GDataType.graph;
        }else if(fieldType.equals("ti")){
            //ti 时间段 数据组织;
            result = GDataType.timescale;
        }else if(fieldType.startsWith("i") && fieldType.indexOf(".") == -1){
            //ix 整型数
            result = GDataType.INT;
        }else if(fieldType.startsWith("i") && fieldType.indexOf(".") != -1){
            //ix.y 整型数组
            result = GDataType.array_int;
        }

        return  result;
    }

    /**
     * 判断是否是数字
     * @param str
     * @return
     */
    public  static boolean isNumber(String str) {
        String regex = "^[0-9]+$";
        return str.matches(regex);
    }
}
