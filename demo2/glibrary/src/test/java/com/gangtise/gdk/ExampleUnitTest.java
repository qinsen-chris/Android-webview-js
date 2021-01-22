package com.gangtise.gdk;

import com.alibaba.fastjson.JSONObject;
import com.gangtise.gdk.common.GtsArrayList;
import com.gangtise.gdk.model.GKLine;
import com.gangtise.gdk.result.dataprocess.GDataTool;
import com.gangtise.gdk.result.dataprocess.TableCellData;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
        public void testStrsToList() {

        String codes = "";
        String[] arr = new String[0];
        List<String> strsToList1= Arrays.asList(arr);
        System.out.println(strsToList1);
        System.out.println(arr.toString());

        List<String> arrList = new ArrayList(strsToList1);
        arrList.add("a");
        arrList.add("bb");
        arrList.add("ccc");
        String result = "";
        for(String s : arrList){
            result = result + s + ",";
        }
        codes = result.substring(0,result.length()-1);

        System.out.println(codes);

        arrList.remove("bb");
        System.out.println(arrList);

    }

    @Test
    public void testCallback() {
        GKLine gkLine = new GKLine();

        System.out.println(gkLine);
        System.out.println(gkLine.getOnCallback());
    }

    @Test
    public void testArr() {
        TableCellData[][] arr = new TableCellData[30][30];

        TableCellData cellData = new TableCellData();
        cellData.setIsValid(1);
        arr[3][2] = cellData;

        if(arr[3] == null){
            System.out.println("arr[3] is null");
        }

        if(arr[3][1] == null){
            System.out.println("arr[3][1] is null");
        }

        arr[3][1] = cellData;

        if(arr[3] == null || arr[3][1] == null ||
                arr[3][1].getRawData() != "1"){
            System.out.println("arr[3][1].getRawData() is null ");
        }

    }

    @Test
    public void testGtsArrayList(){
        GtsArrayList<GtsArrayList<TableCellData>> cellData = new
                GtsArrayList<GtsArrayList<TableCellData>>();

        GtsArrayList<TableCellData> c1 = new GtsArrayList<TableCellData>();
        TableCellData t1 = new TableCellData();
        t1.setIsValid(1);
        c1.add(t1);

        TableCellData t2 = new TableCellData();
        t2.setIsValid(2);
        c1.add(t2);

        GtsArrayList<TableCellData> c2 = new GtsArrayList<TableCellData>();
        TableCellData t3 = new TableCellData();
        t3.setIsValid(1);
        c2.add(t1);

        cellData.add(c1);
        cellData.add(c2);
        System.out.println(cellData);

        if(cellData.get(3) == null){
            cellData.set(3,new GtsArrayList<TableCellData>());

            GtsArrayList<TableCellData> cellArr = cellData.get(3);
            cellArr.set(1,t3);
            cellData.set(3,cellArr);
        }
    }

    @Test
    public void testJSNumber(){
        Object value = 1;
        if(value instanceof Integer || value instanceof Long || value instanceof Short || value instanceof Short
                || value instanceof Double || value instanceof Float ){
            System.out.println("is num ");
        }

        Object value2 = 1.1;
        if(value2 instanceof Integer || value2 instanceof Long || value2 instanceof Short || value2 instanceof Short
                || value2 instanceof Double || value2 instanceof Float ){
            System.out.println("is num ");
        }
    }

    @Test
    public void testAnWeiYu(){
        int flags = 5;
        int result = ((flags >> 5) & 0x07);
        if ( result> 0  ){
            System.out.println("--------------");
        }

        for(int i=0; i<100 ; i++){
            int res = ((i >> 5) & 0x07);
            if ( res> 0  ){
                System.out.println(i + "--------------"+res);
            }
        }
    }

    @Test
    public void testBigdecimalSetscale(){
        BigDecimal bdValue = GDataTool.getBigDecimal(5.2356);
        bdValue = bdValue.setScale(2,BigDecimal.ROUND_HALF_UP);
        String strValue = '+' + String.valueOf(bdValue);

        System.out.println("--------------"+strValue);

        Object o = 5.1;

        System.out.println("" + o);

        int i = Integer.valueOf("29991231");

        System.out.println(i +1);
    }

    /**
     *  January 、February 、March、April、May、June、July、August、September、October、November、December
     */
    @Test
    public void testDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021,0,17);
        System.out.println(calendar.getTime());
        System.out.println(calendar.getWeekYear());  //返回整数年
        System.out.println(calendar.getTime().getYear() + "-" + calendar.getTime().getMonth() + "-" +calendar.getTime().getDay() //getDay() 0-6  周日-周六
        +"-" + calendar.getTime().getHours() + "-" + calendar.getTime().getMinutes() + "-" +calendar.getTime().getSeconds() );
        System.out.println(calendar.getFirstDayOfWeek());

        System.out.println("---------------------------------");
        int intValue = 20220611;
        int dat = intValue % 100 ;
        System.out.println(dat);
        calendar.set(intValue/10000,(intValue / 100 % 100)-1,intValue % 100);  //月是从“0”开始算起的 月份从0-11
        System.out.println(calendar.getTime());
        System.out.println(calendar.getWeekYear() + "-" + (calendar.getTime().getMonth()+1) + "-" +calendar.getTime().getDate()
                +"-" + calendar.getTime().getHours() + "-" + calendar.getTime().getMinutes() + "-" +calendar.getTime().getSeconds() );  //getMonth 要+1
        System.out.println(calendar.getFirstDayOfWeek());
        System.out.println("---------------------------------");

        Date d = new Date();
        d.setTime(System.currentTimeMillis());
        calendar.setTime(d);
        System.out.println(calendar.getWeekYear() + "-" + calendar.getTime().getMonth()+1 + "-" +calendar.getTime().getDate()
                +"-" + calendar.getTime().getHours() + "-" + calendar.getTime().getMinutes() + "-" +calendar.getTime().getSeconds() );  //getMonth 要+1

        System.out.println("-----------------d----------------");
        System.out.println(d.getDate());
        System.out.println(d.getHours());
        System.out.println(d.getMinutes());
        System.out.println(d.getSeconds());

    }

}