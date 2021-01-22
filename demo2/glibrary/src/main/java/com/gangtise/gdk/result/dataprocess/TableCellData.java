package com.gangtise.gdk.result.dataprocess;

/**
 * @author: created by qinsen
 * date: 2021/1/20 16
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 对应JS -> GCellData
 **/
public class TableCellData {

    private String strValue; //文本类型的字符串
    private Object rawData ; //源数据
    private Integer colorFlags; //颜色方案 0~3  0 平，1 涨，2：跌 ，3：无效
    private Integer isValid; //判断数据是否无效  0 无效 1 有效
    private Integer decimal; //读取小数位（0-6）
    private Integer upDownFlags; //获取涨跌方向 0平 1上涨 2下跌

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public Object getRawData() {
        return rawData;
    }

    public void setRawData(Object rawData) {
        this.rawData = rawData;
    }

    public Integer getColorFlags() {
        return colorFlags;
    }

    public void setColorFlags(Integer colorFlags) {
        this.colorFlags = colorFlags;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getDecimal() {
        return decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public Integer getUpDownFlags() {
        return upDownFlags;
    }

    public void setUpDownFlags(Integer upDownFlags) {
        this.upDownFlags = upDownFlags;
    }
}
