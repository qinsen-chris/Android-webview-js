package com.gangtise.gdk.common;

import com.gangtise.gdk.model.BasicModel;
import com.gangtise.gdk.result.dataprocess.Table;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: created by qinsen
 * date: 2021/1/9
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class ContentMap {

    /** 请求对象Map （key：id ,value: BasicModel) */
    public static Map<String, BasicModel> BASIC_MODEL_MAP = new ConcurrentHashMap<>();
    /** result -> table 数据 （key：id ,value: Table)  */
    public static Map<String, Table> SOURCE_TABLE_MAP = new ConcurrentHashMap<>();
}
