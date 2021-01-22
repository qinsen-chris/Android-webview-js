package com.gangtise.gdk.common;

import com.gangtise.gdk.result.dataprocess.TableCellData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: created by qinsen
 * date: 2021/1/21 14
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 模拟二维数组操作  TableCellData[][]
 **/
public class GtsArrayList<TableCellData> extends ArrayList<TableCellData> {

    @Override
    public TableCellData get(int index) {

        if(index >= this.size()){
            return null;
        }

        return super.get(index);
    }

    @Override
    public TableCellData set(int index, TableCellData element) {
        return super.set(index, element);
    }
}
