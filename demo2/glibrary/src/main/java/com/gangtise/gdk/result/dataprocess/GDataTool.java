package com.gangtise.gdk.result.dataprocess;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: created by qinsen
 * date: 2021/1/20 17
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class GDataTool {


    /**
     * 获取格式化后的数据
     * @param {object} value 原始值
     * @param {string} valueType 值类型
     * @param {string} format 格式化方式
     * @param {int} flags 标志位
     * @param {*} prop
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getValueFormatString(Object value, String valueType, String format, Integer flags){
        if (value == null) {
            if (format != null && format.indexOf("%N") >= 0)
                return "-";
            else
                return "";
        }

        if (flags!=null && (flags & 0x03) == 0x03){
            return "-";
        }

        if(value instanceof List){

            if (valueType.equals("ti")) {
                String t = "";
                List<List<String>> arr = (List<List<String>>)value;
                for (int i=0; i<arr.size(); i++) {
                    Date d1 = new Date();
                    d1.setTime(Long.parseLong(arr.get(i).get(0)));
                    Date d2 = new Date();
                    d2.setTime(Long.parseLong(arr.get(i).get(1)));

                    t += "[" + getValidDateTime(d1.getHours()) + ":" + getValidDateTime(d1.getMinutes()) + ","
                            + getValidDateTime(d2.getHours()) + ":" + getValidDateTime(d2.getMinutes()) + "]";

                }
                return t;
            }
            return JSONObject.toJSONString(value);
        }

        if (format!= null && format.equals("%d")){
            valueType = "i";
        }

        int dec = 2 ;
        if (valueType!= null){
            if (valueType.substring(0,1).equals("i")) {
                dec = 0;
            }
            //JS bug
            else if (valueType.length()>1 && isNumber(valueType.substring(1))) {
                dec = Integer.parseInt(valueType.substring(1));
            }
        }
        boolean  isText = isTextType(valueType);

        boolean isValue = false;
        isValue = isNumber(value);

        if(isText && isValue){
            isText = false ;
            String valueStr = value.toString();
            int pointIndex = valueStr.indexOf(".");
            if(pointIndex > 0){
                valueType = "f" + (valueStr.length() -pointIndex -1);
                dec = valueStr.length() -pointIndex -1 ;
            }else{
                valueType = "f";
                dec = 0;
            }
        }

        String strValue = value.toString();
/*        if (format != null) {
            //TODO
        }*/

        if(valueType != null){
            String firstTypeStr = valueType.substring(0,1);

            if (firstTypeStr.equals("f") || firstTypeStr.equals("x")) {
                if (flags != null && ((flags >> 5) & 0x07) > 0) {
                    dec = ((flags >> 5) & 0x07) - 1;
                }
                if (format != null && format.indexOf("%+") >= 0 && Integer.valueOf(value.toString()) >= 0){
                    BigDecimal bdValue = getBigDecimal(value);
                    bdValue = bdValue.setScale(dec,BigDecimal.ROUND_HALF_UP);
                    strValue = '+' + String.valueOf(bdValue);
                }
                if(isNumber(value)){
                    BigDecimal bdValue = getBigDecimal(value);
                    bdValue = bdValue.setScale(dec,BigDecimal.ROUND_HALF_UP);
                    strValue = String.valueOf(bdValue);
                }
                if(format != null && format.indexOf("%%") >= 0){
                    strValue += "%";
                }

                return strValue;
            }
            else if(firstTypeStr.equals("i")){
                if (format != null && format.indexOf("%+") >= 0 && Integer.valueOf(value.toString()) >= 0){
                    return '+' + String.valueOf(value);
                }
            }
            //未找到d 数据类型
            else if(valueType.equals("d")){
                return value.toString().substring(0,4) + "/" + value.toString().substring(4,6) + "/" + value.toString().substring(6,8);
            }

            else if(valueType.equals("th") || valueType.equals("tm") ||valueType.equals("ts") ||valueType.equals("tms") ||
                    valueType.equals("dd") || valueType.equals("dm") ||valueType.equals("dq") ||valueType.equals("dy")){
                //如果不是数字， 当成字符串 typeof value == 'string'
                if(!isNumber(value)){
                    return value.toString();
                }
                else {
                    boolean isDate = false;  // true :date类型   false：time 类型
                    Calendar calendar = Calendar.getInstance();
                    Date d = new Date();
                    String date = "";   // yyyy/mm/dd
                    String Time = "";   // 10:10:10  时分秒

                    //
                    if (Integer.valueOf(value.toString()) < 29991231 && (valueType.equals("dd") || valueType.equals("dm") || valueType.equals("dq") || valueType.equals("dy"))) {
                        Integer intValue = Integer.valueOf(value.toString());
                        calendar.set(intValue / 10000, (intValue / 100 % 100) - 1, intValue % 100);
                        isDate = true;
                    } else {
                        d.setTime(Long.parseLong(value.toString()));

                        calendar.setTime(d);
                    }

                    boolean utcTime = false;
                    if (!isDate && utcTime) {
                        //...
                    }
                    if (isDate) {
                        date = calendar.getWeekYear() + "/" + getValidDateTime(calendar.getTime().getMonth() + 1) + "/" + getValidDateTime(calendar.getTime().getDate());
                        Time = getValidDateTime(calendar.getTime().getHours()) + ":" + getValidDateTime(calendar.getTime().getMinutes()) + ":" + getValidDateTime(calendar.getTime().getSeconds());
                    }

                    String text = "";
                    if (format != null) {
                        text = format;
                        if (!isDate && utcTime) {
                            //...
                        } else {
                            text = text.replace("YYYY", "" + calendar.getWeekYear());
                            text = text.replace("YY", getValidDateTime(calendar.getWeekYear() % 100));
                            text = text.replace("MM", getValidDateTime(calendar.getTime().getMonth() + 1));
                            text = text.replace("WEEK", "" + calendar.getTime().getDay());  //getDay一周中第几天

                            if (!isDate) {
                                text = text.replace("DD", GDataTool.getValidDateTime(d.getDate()));
                                text = text.replace("HH", GDataTool.getValidDateTime(d.getHours()));
                                text = text.replace("mm", GDataTool.getValidDateTime(d.getMinutes()));
                                text = text.replace("SS", GDataTool.getValidDateTime(d.getSeconds()));
                            } else {
                                text = text.replace("DD,", getValidDateTime(d.getDate()));
                                text = text.replace("DD", getValidDateTime(d.getDate()));
                                text = text.replace("HH:", "");
                                text = text.replace("mm:", "");
                                text = text.replace("SS", "");
                                text = text.replace("HH", "");
                                text = text.replace("mm", "");
                            }

                        }

                    } else {
                        if (isDate) {
                            text = date;
                        } else {
                            text = date + "," + Time;
                        }
                    }
                    d = null;
                    return text;
                }

            }
            else if(valueType.equals("ti")){
                strValue = "";
                List<List<String>> arr = (List<List<String>>)value;
                for (int i=0; i<arr.size(); i++) {
                    Date d1 = new Date();
                    d1.setTime(Long.parseLong(arr.get(i).get(0)));
                    Date d2 = new Date();
                    d2.setTime(Long.parseLong(arr.get(i).get(1)));

                    strValue += "[" + getValidDateTime(d1.getHours()) + ":" + getValidDateTime(d1.getMinutes()) + ","
                            + getValidDateTime(d2.getHours()) + ":" + getValidDateTime(d2.getMinutes()) + "]";
                }
            }

        }

        return strValue;
    }

    /**
     * Object转BigDecimal
     * @param value
     * @return
     */
    public static BigDecimal getBigDecimal( Object value ) {
        BigDecimal ret = null;
        if( value != null ) {
            if( value instanceof BigDecimal ) {
                ret = (BigDecimal) value;
            } else if( value instanceof String ) {
                ret = new BigDecimal( (String) value );
            } else if( value instanceof BigInteger) {
                ret = new BigDecimal( (BigInteger) value );
            } else if( value instanceof Number ) {
                ret = new BigDecimal( ((Number)value).doubleValue() );
            } else {
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");
            }
        }
        return ret;
    }

    /**
     * 判断是否为数值类型
     * @param value
     * @return
     */
    public static boolean isNumber(Object value){
        if(value instanceof Integer || value instanceof Long || value instanceof Short || value instanceof Short
                || value instanceof Double || value instanceof Float ){
            return true;
        }
        return false;
    }


    /**
     * ------------------------------------JS---------------------------------------------
     *
     * t	文本
     * ta	字符串数组
     * ix	整型数, x表示10x ，用于显示简化，如i2，数据为1200，显示为12
     * dy	年 YYYYMMDD，只有年份，20190000
     * dq	季YYYYMMDD，只有年份月份，20190100
     * dm	月YYYYMMDD，只有年份月份，20190100
     * dd	日期（年月日）
     * th	UTC*1000(精确到小时) / (1000*60*60)
     * tm	UTC*1000(精确到分钟) / (1000*60)
     * ts	UTC*1000(精确到秒) / (1000)
     * tms	UTC*1000(精确到毫秒)
     * fx	价类型，x位小数(0-6), 比如:f0,f1,f2,f3,f4,f5,f6
     * xx	价类型，x位小数(0-6), 比如:f2, f4  如：x2，918用9.18表示
     * ix.y	整型数组, x表示10x ，用于显示简化，y表示数组总个数如： (i2.32)
     * gx.y	图型价类型数组，x位小数，总共有y组(x2.32)  数据组织:总量，类型，段, [第一段容量，数据…], [第二段容量, 数据….] 注：数据总数不包含 ’总量’，’类型’，’段’ 三个数据
     * ti	时间段  数据组织：[开始数据，结束数据，说明]，[开始数据，结束数据，说明],…时间格式：'YYYY-MM-DD, HH:mm:SS, Zoon', Zoon的缺省值为-480 例如：'2019-07-22,10:29:30,-480'
     *
     * c    默认
     * d    8位日期
     *
     * ------------------------------------行情---------------------------------------------
     * text(m)/char(m)	定长文本, m个字节长
     * varText	可变长文本
     * textArray(m,n)_	定长字符串数组, m个字节长, 共n组
     * int/int32	32位整型数（-21亿 至 +21亿）
     * bigint/int64	64位整型数
     * number(d)	Double，d位小数位
     * fixed(d)/fixed32(d)	32价格专用数据类型，d位小数位, 最大4位
     * bigFixed(d)/fixed64(d)	64价格专用数据类型，d位小数位，最大6位
     * Date	日期
     * Time_h	UTC*1000时间，精确到小数
     * Time_m	UTC*1000时间，精确到分钟
     * Time	UTC*1000时间，精确到秒
     * Time_ms	UTC*1000时间，精确到毫秒
     * lot/lot32	手数
     * bigLot/lot64	手数
     * intArray(n)/ int32Array(n)	32数组，共n组
     * bigintArray(n)/ int64Array(n)	64数组，共n组
     * graphFixed(d.n)/ graphFixed32(d.n)	32位价格图形，d位小数，n组数据(包含头3个数)
     * graphBigFixed(d,n)/ graphFixed64(d.n)	64位价格图形，d位小数，n组数据(包含头3个数)
     * timeScale(n)	时间段, n段
     *
     * @param valueType
     * @return
     */
    public static boolean isTextType(String valueType){
        if (valueType==null || valueType.equals("c") || valueType.equals("t") ||
                (valueType.substring(0,1).equals("t") && Integer.valueOf(valueType.substring(1,2)) >= 1 && Integer.valueOf(valueType.substring(1,2)) <=9)
        ) {
            return true;
        }

        return false;
    }

    /**
     * 读取颜色方案（0-3）  0 平 1涨 2跌 3无效
     * @param flag
     * @return
     */
    public static Integer GETGFLAG_COLOR(Integer flag){
        if(flag == null){
            return null;
        }
        return (flag >> 2) & 0x03 ;
    }

    /**
     * 判断数据是否无效
     * @param flag
     * @return
     */
    public static Boolean GETGFLAG_ISINVALID(Integer flag){
        if(flag == null){
            return true;
        }
        return (((flag & 0x03) == 0x03) ? true : false) ;
    }

    /**
     *
     * 读取小数位（0-6）
     * @param flag
     * @return
     */
    public static Integer GETGFLAG_DEC(Integer flag){
        if(flag == null){
            return null;
        }
        return ((flag >> 5) & 0x07) <=0  ? (((flag >> 5) & 0x07)-1) : -1;
    }

    /**
     * 获取涨跌方向
     * @param flag
     * @return 0平 1上涨 2下跌
     */
    public static Integer GET_UP_DOWN_FLAGS(Integer flag){
        if(flag == null){
            return null;
        }

        if(GDataTool.GETGFLAG_ISARROW_EQ(flag) == true) {
            return 0;
        }
        if(GDataTool.GETGFLAG_ISARROW_UP(flag) == true) {
            return 1;
        }
        if(GDataTool.GETGFLAG_ISARROW_DN(flag) == true) {
            return 2;
        }

        return 0;
    }

    private static Boolean GETGFLAG_ISARROW_EQ(Integer flag){
        return (((flag&0x03) == 0x00) ? true : false);
    }

    private static Boolean GETGFLAG_ISARROW_UP(int flag){
        return (((flag & 0x03) == 0x01) ? true : false);
    }

    private static Boolean GETGFLAG_ISARROW_DN(int flag){
        return (((flag&0x03) == 0x02) ? true : false);
    }

    /**
     * 月、天  不够10 前面加0
     * @param num
     * @return
     */
    private static String getValidDateTime(int num){
        if (num < 10){
            return "0" + num;
        }
        return "" + num;
    }

}
