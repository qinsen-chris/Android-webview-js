
 //  问题：是不是只表示数据类型，而不表示该字段的含义. tms 本质是 int64类型， tms又表示毫秒级时间戳。建议该类型只是基础数据类型。
function GDataType(){}
GDataType.dtBool = 0;
GDataType.dtString = 1;
GDataType.dtInt = 2;
GDataType.dtInt64 = 3;
GDataType.dtFloat = 4;
GDataType.dtDouble = 5;
GDataType.dtDateTime = 6;
GDataType.dtTimeStamp = 7;
GDataType.dtTimeStampMs = 8;
GDataType.dtDataSet = 9;
GDataType.dtArray = 10;
GDataType.dtStringArray = 11;



class GColumnInfo{
    constructor() {
        this.index = null;   //列序号    int
        this.name = null;    //列名称    string
        this.dataType = null;//列类型   GDataType
        this.data = null;    //列附加数据
    }
}

class GCellData{
    
    /**
     * 获取文本类型的字符串
     * @returns {string} 格式化后的字符串
     */
    getValueText(){}

    /**
     * 获取原始数据
     * @returns {*} 原始数据
     */
    getRawData(){}


    /**
     * 
     * [0,1,1,3]
     * 
     * ------
     *  0       是否有效标识
     * ------
     *  2       0~6位 小数位
     * ------
     *  3       0~6个 颜色标志
     * ------
     *  1       0：涨   1：跌   2：平   3：无效
     * ------
     */
    /**
     * 是否有效标志
     * @returns {int} 0 无效， 1 有效
     */
    isValid(){};

    /**
     * 获取小数位
     * @returns {int} 0～6位
     */
    getDecimal(){};

    /**
     * 获取颜色标志
     * @returns {int} 0~6个颜色标志
     */
    getColorFlags(){};

    /**
     * 获取涨跌方向
     * @returns {int} 0：涨   1：跌   2：平   3：无效
     */
    getUpDownFlags(){};


}

//GDataSet
class GDataSet{
   

    constructor() {
         //id
        //集合名、表名  string
        this.name;
        //行数        int
        this.rowCount;
        //列数        int
        this.colCount;
        //开始位置
        this.begin;
        // //条数
        // this.count;
        //总数
        this.totalRows;
        //表头
        this.columnInfos;
    }

    /**
     * 根据序号获取列表信息的对象
     * @param {int} colIndex 列号
     * @returns {GColumnInfo} 表信息
     */
    getColumn(colIndex){
        for (let key in this.columnInfos) {
             if(this.columnInfos[key].index == colIndex){
                 return this.columnInfos[key];
             }
        }
    };

    /**
     * 根据名称获取列表信息的对象
     * @param {string} colName 列字段名
     * @returns {GColumnInfo} 表信息
     */
    getColumnByName(colName){
        for (const name in this.columnInfos) {
            if(colName.toLowerCase() === name.toLowerCase()){
                return this.columnInfos[name];
            }
        }
    };

    /**
     * 根据行号，列号获取单元格对象
     * @param {int} rowIndex  行号
     * @param {int} colIndex  列号
     * @return {GCellData} cellData 单元格数据
     * 
     */
    getCellData(rowIndex,colIndex){
        //单元格
        let dsManager = GDataSourceManager.sharedInstance();
        let table = dsManager.getDataSetSourceWithId(this.id);
        let rIndex = rowIndex - 1;
        let cIndex = colIndex - 1;
        let value = table.data[cIndex][rIndex];
        let fieldType = table.field[cIndex][1];
        let flags;
        if(table.flags != null) {
            let fieldName = table.field[cIndex][0];
            if(table.flags[fieldName.toLowerCase()] != null) {
               flags = table.flags[fieldName.toLowerCase()][rIndex];
            }
        }
        if(value != null){
            if(table.cellData == null){
                table.cellData = [];
            }
            if(table.cellData[cIndex] == null){
                table.cellData[cIndex]  = [];
            }
            if(table.cellData[cIndex][rIndex] == null || table.cellData[cIndex][rIndex].getRawData() != value){
                let cData = new GCellData();
                let strValue = `${GDataTool.getValueFormatString(value,fieldType,null,flags)}`;
                let rawData = value;
                let colorFlags = GDataTool.GETGFLAG_COLOR(flags); //0~3个 0 平，1 涨，2：跌 ，3：无效
                let isValid = GDataTool.GETGFLAG_ISINVALID(flags) == true ? 0 : 1; //0 无效 1 有效       是否有效标识
                let decimal = GDataTool.GETGFLAG_DEC(flags); //小数位 0~6位
                let upDownFlags = GDataTool.GET_UP_DOWN_FLAGS(flags); //涨跌方向  0平， 1涨，2跌
                cData.getValueText = ()=>{
                    return strValue;
                }
                cData.getRawData = ()=>{
                    return rawData;
                }
                cData.isValid = ()=>{
                    return isValid;
                }
                cData.getDecimal = ()=>{
                    return decimal;
                }
                cData.getColorFlags = ()=>{
                    return colorFlags;
                }
                cData.getUpDownFlags = ()=>{
                    return upDownFlags;
                }
                table.cellData[cIndex][rIndex] = cData;
            }
            return table.cellData[cIndex][rIndex];
        }
    };

    /**
     * 根据行号，列号获取字符串类型的数值（已格式化）
     * @param {int} rowIndex  行号
     * @param {int} colIndex  列号
     * @returns {string} valueStr 格式化后的值
     */
    getCellText(rowIndex,colIndex){
        let cData = this.getCellData(rowIndex,colIndex);
        if(cData == null){
            return "-";
        }
        let strValue = cData.getValueText();
        return strValue;
    };


    /**
     * 根据行号，列名获取单元格对象
     * @param {int} rowIndex    行号
     * @param {string} colName  列字段名
     * @return {GCellData} cellData 单元格数据
     */
    getCellDataByName(rowIndex,colName){
        let colInfo = this.getColumnByName(colName);
        let colIndex = colInfo.index;
        let cData = this.getCellData(rowIndex,colIndex);
        return cData;
    };

    /**
     * 根据行号，列名获取字符串类型的数值（已格式化）
     * @param {int} rowIndex    行号
     * @param {string} colName  列字段名
     * @returns {string} valueStr 格式化后的值
     */
    getCellTextByName(rowIndex,colName){
        let colInfo = this.getColumnByName(colName);
        let colIndex = colInfo.index;
        let strValue = this.getCellText(rowIndex,colIndex);
        return strValue;
    };


    /**
     * 根据列索引，获取整列数据
     * @param {int} colIndex 列号
     * @returns {Array} colArray 整列数组
     */
    getColValues(colIndex){
        let dsManager = GDataSourceManager.sharedInstance();
        let table = dsManager.getDataSetSourceWithId(this.id);
        let data = table.data;
        return data[colIndex-1];
    };

    /**
     * 根据列名称，获取整列数据
     * @param {string} colName 列字段名
     * @returns {Array} colArray 整列数组
     */
    getColValuesByName(colName){
        let colInfo = this.getColumnByName(colName);
        let data = this.getColValues(colInfo.index);
        return data;
    };


    /**
     * 根据行索引,获取整行格式化数据
     * @param {int} rowIndex 行号
     * @returns {Array} rowArray 整行数组
     */
    getRowValues(rowIndex){
        let dataArr = [];
        for(let key in this.columnInfos){
            let name = this.columnInfos[key].name;
            let value = this.getCellTextByName(rowIndex,name);
            dataArr.push(value);
        }
        return dataArr;
    }

    
}