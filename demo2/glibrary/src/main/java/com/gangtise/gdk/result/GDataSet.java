package com.gangtise.gdk.result;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONObject;
import com.gangtise.gdk.common.Constant;
import com.gangtise.gdk.common.ContentMap;
import com.gangtise.gdk.common.GtsArrayList;
import com.gangtise.gdk.result.dataprocess.GColumnInfo;
import com.gangtise.gdk.result.dataprocess.GDataTool;
import com.gangtise.gdk.result.dataprocess.Table;
import com.gangtise.gdk.result.dataprocess.TableCellData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GDataSet implements Serializable {

    public String id; //请求对象id
    public String name;//表名
    public Integer begin;//开始位置
    public Integer rowCount = 0;//行数
    public Integer colCount = 0; //列数
    public Integer totalRows; //总数
    public List<GColumnInfo> columnInfos;  //表头信息

    //table -> data
    private List<List<Object>> data;
    //table -> flags
    private JSONObject flags;
    //col 列模式、row行模式
    public String rank;

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    public void setFlags(JSONObject flags) {
        this.flags = flags;
    }


    /**
     * 根据序号获取列表信息的对象
     * @param {int} colIndex 列号
     * @returns {GColumnInfo} 表信息
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public GColumnInfo getColumn(int colIndex){
        List<GColumnInfo> list = columnInfos.stream().filter(t -> t.index==colIndex).collect(Collectors.toList());

        GColumnInfo result = new GColumnInfo();
        if(list.size()>0){
            result = list.get(0);
        }
        return result;
    }

    /**
     * 根据名称获取列表信息的对象
     * @param colName  列字段名
     * @return 表信息
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public GColumnInfo getColumnByName(String colName){
        List<GColumnInfo> list = columnInfos.stream().filter(t -> t.name.toLowerCase().equals(colName.toLowerCase()))
                .collect(Collectors.toList());

        GColumnInfo result = new GColumnInfo();
        if(list.size()>0){
            result = list.get(0);
        }
        return result;
    }



    /**
     * 根据行号，列号获取单元格对象
     * @param {int} rowIndex  行号
     * @param {int} colIndex  列号
     * @return {GCellData} cellData 单元格数据
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public TableCellData getCellData(int rowIndex, int colIndex){
        int rIndex = rowIndex -1;
        int cIndex = colIndex -1;

        Table table = ContentMap.SOURCE_TABLE_MAP.get(id);
        if(table==null){
            Log.e("GDataSet","ContentMap.SOURCE_TABLE_MAP对应的请求id数据为空！");
            return null;
        }
        List<List<Object>> data = table.getData();
        if(data.size()==0){
            Log.e("GDataSet","table.getData()对应的数据为空！");
            return null;
        }

        //按行、列不同模式获取value
        Object value = null;
        if(rank == null){
            Log.e("GDataSet","rank的数据为空！");
            return null;
        }
        //行
        int rlength = 30;
        if(rank.equals(Constant.INFO_RANK_ROW)){
            List<Object> row = data.get(rIndex);
            value =  row.get(cIndex);

            rlength = row.size();
        }
        //列
        int clength = 30;
        if(rank.equals(Constant.INFO_RANK_COL)){
            List<Object> col = data.get(cIndex);
            value = col.get(rIndex);

            clength = col.size();
        }

        //获取fieldType
        List<List<String>> field = table.getField();
        List<String> colField = field.get(cIndex);
        String fieldType = colField.get(1);

        //获取flags
        Object flags = null ;
        if(table.getFlags() != null){
            String fieldName = colField.get(0);
            if(table.getFlags().get(fieldName.toLowerCase()) != null){
                List<Object> flag = (List<Object>)table.getFlags().get(fieldName.toLowerCase());
                flags = flag.get(rIndex);
            }
        }

        if(value != null){
            //cellData 按列式存储 table.cellData[cIndex][rIndex] = cData;
            if(table.getCellData() == null){
/*                GtsArrayList<GtsArrayList<TableCellData>> cellData = new
                        GtsArrayList<GtsArrayList<TableCellData>>();

                table.setCellData(cellData);*/

                table.setCellData(new TableCellData[clength][rlength]);
            }

            TableCellData[][] cellData = table.getCellData();

/*            if(cellData[cIndex] == null){
                cellData.set(cIndex,new GtsArrayList<TableCellData>());
            }*/
            if(cellData[cIndex] == null || cellData[cIndex][rIndex] == null ||
                    cellData[cIndex][rIndex] .getRawData() != value){

                TableCellData cData = new TableCellData();
                cData.setRawData(value);

                String strValue = GDataTool.getValueFormatString(value,fieldType,null,flags == null ? null : Integer.valueOf(flags.toString())); //格式化数据

                Integer colorFlags = GDataTool.GETGFLAG_COLOR((Integer) flags);  //0~3个 0 平，1 涨，2：跌 ，3：无效
                Integer isValid = GDataTool.GETGFLAG_ISINVALID((Integer) flags) == true ? 0 : 1;  //0 无效 1 有效       是否有效标识
                Integer decimal = GDataTool.GETGFLAG_DEC((Integer) flags);  //小数位 0~6位
                Integer upDownFlags = GDataTool.GET_UP_DOWN_FLAGS((Integer) flags);  //涨跌方向  0平， 1涨，2跌

                cData.setStrValue(strValue);
                cData.setColorFlags(colorFlags);
                cData.setIsValid(isValid);
                cData.setDecimal(decimal);
                cData.setUpDownFlags(upDownFlags);

/*                GtsArrayList<TableCellData> cellArr = cellData.get(cIndex);
                cellArr.set(rIndex,cData);
                cellData.set(cIndex,cellArr);*/

                TableCellData[] cellArr = cellData[cIndex];
                cellArr[rIndex] = cData;

                cellData[cIndex] = cellArr;
            }

            return cellData[cIndex][rIndex];
        }

        return null;
    }

    /**
     * 根据行号，列号获取字符串类型的数值（已格式化）
     * @param rowIndex
     * @param colIndex
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getCellText(int rowIndex, int colIndex){
        TableCellData cellData = getCellData(rowIndex,colIndex);
        if(cellData == null){
            return "-";
        }
        String strValue = cellData.getStrValue();
        return strValue;
    }


    /**
     * 根据行号，列名获取单元格对象
     * @param rowIndex
     * @param colName
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public TableCellData getCellDataByName(int rowIndex, String colName){
        GColumnInfo columnInfo = getColumnByName(colName);
        int colIndex = columnInfo.index;

        TableCellData cellData = getCellData(rowIndex,colIndex);
        return  cellData;
    }

    /**
     * 根据行号，列名获取字符串类型的数值（已格式化）
     * @param rowIndex
     * @param colName
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getCellTextByName(int rowIndex, String colName){
        GColumnInfo columnInfo = getColumnByName(colName);
        int colIndex = columnInfo.index;
        String strValue = getCellText(rowIndex,colIndex);

        return strValue;
    }

    /**
     * 根据列索引，获取整列数据
     * @param colIndex 列号， 从1开始
     * @return
     */
    public List<Object> getColValues(int colIndex){
        Table table = ContentMap.SOURCE_TABLE_MAP.get(id);
        if(table==null){
            Log.e("GDataSet","ContentMap.SOURCE_TABLE_MAP对应的请求id数据为空！");
            return null;
        }
        List<List<Object>> data = table.getData();
        if(data.size()==0){
            Log.e("GDataSet","table.getData()对应的数据为空！");
            return null;
        }

        //根据行、列模式获取数据
        List<Object> result = new ArrayList<>();
        if(rank == null){
            Log.e("GDataSet","rank的数据为空！");
            return null;
        }
        //行
        if(rank.equals(Constant.INFO_RANK_ROW)){
            for(List<Object> list : data){
                result.add(list.get(colIndex - 1));
            }
        }
        //列
        if(rank.equals(Constant.INFO_RANK_COL)){
            result = data.get(colIndex -1);
        }

        return result;
    }

    /**
     * 根据列名称，获取整列数据
     * @param colName
     * @return
     */
    public List<Object> getColValuesByName(String colName){
        GColumnInfo columnInfo = getColumnByName(colName);
        int colIndex = columnInfo.index;
        List<Object> result = getColValues(colIndex);

        return result;
    }

    /**
     * 根据行索引,获取整行格式化数据
     * @param rowIndex
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> getRowValues(int rowIndex){
        List<String> dataArr = new ArrayList<>();
        for(int k=0; k<columnInfos.size(); k++){
            String colName = columnInfos.get(k).name;

            String value = getCellTextByName(rowIndex,colName);
            dataArr.add(value);
        }

        return dataArr;
    }
}
