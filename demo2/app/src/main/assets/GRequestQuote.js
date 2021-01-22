//行情请求接口
class GRequestQuote {

    constructor() {
        //生成id
        this.id;
        //是否订阅 boolean  true    是  ,  false   否（默认）
        this.isSubscribe = false;
        //字段
        this.field;
        //通知回调
        this.onCallback;
        //private
        this.url;
        this.params = {};
        //默认生成id
        this.id = `${GSDK.count++}`;
    }

    //设置地址
    /**
     * 设置Url
     * @param {string} url 
     */
    setUrl(url) {
        this.url = url;
    };

    /**
     * 设置参数字段
     * @param {string} field 
     */
    setField(field) {
        this.field = field;
    }

    /**
     * 设置参数 
     * @param {string} key 
     * @param {string} value 
     */
    setParam(key, value) {
        /* 
            {
                type: "select",                         //类型
                field: ["*"],                           //字段参数
                table: "md.quote/pv/price",             //表名
                keys: ["000001.SZ", 10];                //表参数
                where: {code:['000001.SZ','000001.SH']} //条件 script 字符串表达式
                limit: [-1,20],                         //分页 ,-1表示从最后开始
                // includeFlags: 1,                        //颜色标志位
                //buffer: {begin:0, rows:10, totalRows:20, time:1230000},//buffer缓存标记
                //orderby: {field:"code", desc:true},     //排序
                //relations: ["exchTime", "factor"],      //关系
                //resample: {freq:"week", time:'date', how:{open:"open", high:"high", low:"low", close:"close"}}, //变频
                subscribe: 1000                         //订阅时间
            }
        */
        this.params[key] = value;
    };

    /**
     * 清空参数
     */
    clearParams() {
        this.params = {};
    }


    /**
     * 启动
     */
    request() {
        // let requestId = GSDK.requestId++;
        let requestId = 0;
        let sendObj = {
            type: "quote",
            objectId: this.id,
            requestId: requestId,
            url: this.url,
            params: this.params,
            field: this.field,
        }
        GDataManager.sharedInstance().fireData(sendObj);
    }

    /**
     * 关闭
     */
    stop() {
        this.isSubscribe = false;
        this.request();
    };

    
}


//获取行情订阅数据
class GSnapshotQuote extends GRequestQuote {
    
    

    constructor() {
        super();
        //代码列表
        this.codes;

        //指标列表
        this.fields;

        
    }

    /**
    * 设置多个代码
    * @param {string}} codes "000001.SZ,000002.SZ"
    */
    setCodes(codes) {
        this.codes = codes;
    };

    /**
     * 添加代码
     * @param {string} code "600000.SH,000002.SZ"
     */
    addCode(code) {
        let arr = this.codes.split(',');
        arr.push(code);
        this.codes = arr.toString();
    };

    /**
     * 删除代码
     * @param {string} code 600000.SH
     */
    delCode(code) {
        let arr = this.codes.split(',');
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == code) {
                delete arr[i];
                break;
            }
        }
        if(i!=arr.length){
            for(;i<arr.length;i++){
                arr[i] = arr[i+1];
            }
            arr.length-=1;
        }
        this.codes = arr.toString();
    };

    /**
     * 设置多个指标 
     * @param {string} fields "last,change"
     */
    setFields(fields) {
        this.fields = fields;
    };

    /**
     * 添加指标
     * @param {string} field "AskPrice1"
     */
    addField(field) {
        let arr = this.fields.split(',');
        arr.push(field);
        this.fields = arr.toString();
    };


    /**
     * 删除指标
     * @param {string} field "AskPrice1"
     */
    delField(field) {
        let arr = this.fields.split(',');
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == field) {
                delete arr[i];
                break;
            }
        }
        if(i!=arr.length){
            for(;i<arr.length;i++){
                arr[i] = arr[i+1];
            }
            arr.length-=1;
        }
        this.fields = arr.toString();
    };
    
    request() {

        if(!CheckMustParams(this,["codes","fields"])){
            return;
        }
        let codeStr = "";
        //代码列表组合
        if (this.codes != null) {
            let codeArr = this.codes.split(',');
            for (let i = 0; i < codeArr.length; i++) {
                i == codeArr.length - 1 ? codeStr += `'${codeArr[i]}'` : codeStr += `'${codeArr[i]}',`;
            }
        }
        this.setUrl("md.quote");
        this.setParam("condition", `code in (${codeStr})`);
        this.setParam("field", this.fields);
        this.setParam("subscribe", this.isSubscribe);
        super.request();
    }

}

//获取指数涨跌家数统计数据
class GSnapshotUpdowns extends GRequestQuote {

    constructor() {
        super();
         /**
         * 设置代码
         * @param {string} code 
         */
        this.code;

         
    }

    //重写request
    request() {
        if(!CheckMustParams(this,["code"])){
            return;
        }
        this.setUrl("dd.quote");
        this.setField("risecount,SuspensionCount,FallCount");
        this.setParam("condition", `code = '${this.code}'`);
        this.setParam("subscribe", this.isSubscribe);
        super.request();
    }

}

//时间序列数据基类
class GSeQuence extends GRequestQuote {

    constructor() {
        super();
        /**
         * 设置代码
         * @param {string} code 
         */
        this.code;

        /**
         * 开始位置
         * @param {int} begin 
         *  -1 倒数count根
         */
        this.begin =-1;

        /**
         * 条数
         * @param {int} count 
         */
        this.count = 25;
        
    };

}

//---------支持按根数、时间区间请求--------
//K线请求
class GKLine extends GSeQuence {

    constructor() {
         super();
        /**
         * 设置K线周期类型
         * @param {string} period 
         *  1：1分钟K线
         *  5：5分钟K线
         *  15：15分钟K线
         *  30：30分钟K线
         *  60：60分钟K线
         *  120：120分钟K线
         *  day：日线
         *  week：周线
         *  month：月线
         *  year：年线
         */
        this.period;

        /**
         * 设置除权模式
         * @param {int} cqMode
         *  0：不复权
         *  1：前复权
         *  2：后复权
         */
        this.cqMode;

        /**
         * 按日期区间请求
         * @param {date} beginDate 开始日期  20201121
         * @param {date} endDate   结束日期  20201124
         */
        //区间-开始日期
        this.beginDate;
        //区间-结束日期
        this.endDate;
       
    }

    //重写request
    request() {
        if(!CheckMustParams(this,["code","period"])){
            return;
        }
        let getUrl = function (type) {
            let minutesUrl = "md.quote/minute";
            let minutes5Url = "md.quote/minute5";
            let dayUrl = "md.quote/day";
            switch (type) {
                case "day":
                    return dayUrl;
                case "minute":
                    return minutesUrl;
                default:
                    return minutes5Url;
            }
        }

        let getFields = function (type) {
            let minuteFields = `Time,Open,High,Low,Close,Volume,Amount`;
            let dayFields = `Date,Open,High,Low,Close,Volume,Amount`;
            switch (type) {
                case "minute":
                    return minuteFields;
                default:
                    return dayFields;
            }
        }

        let getResample = function (type, cycles) {
            switch (type) {
                case "minute":
                    return `freq='${cycles} minutes', time='time', how='open:open, high:high,low:low,close:close'`;
                case "day":
                    return `freq='${cycles}', time='date', how='open:open, high:high,low:low,close:close'`;
            }
        }
        let fields;
        let url;
        let resample;
        let relations = "factor,adjust:cq";
        let condition;
        switch (this.period) {
            case "1":
                url = getUrl("minute");
                fields = getFields("minute");
                break;
            case "5":
                url = getUrl("minute5");
                fields = getFields("minute");
                break;
            case "15":
            case "30":
            case "60":
            case "120":
                url = getUrl("minute" + this.period);
                resample = getResample("minute", this.period);
                fields = getFields("minute");
                break;
            case "day":
            case "week":
            case "month":
            case "quarter":
            case "year":
                url = getUrl("day");
                if (this.period != "day") {
                    resample = getResample("day", this.period);
                }
                fields = getFields("day");
                break;
            default:
                return;
        }
        this.setUrl(url);
        this.setField(fields);
        this.setParam("keys", `'${this.code}'`);
        this.setParam("subscribe", this.isSubscribe);
        this.setParam("relations", relations);
        if (resample != null) {
            this.setParam("resample", resample);
        }
        if (this.begin != null && this.count != null) {
            this.setParam("limit", `${this.begin},${this.count}`);
        }
        let atDate;
        if (this.beginDate != null) {
            atDate = `date >= ${this.beginDate}`;
        }
        if (this.endDate != null) {
            if (atDate != null) {
                atDate += ` and date <= ${this.endDate}`
            }
        }
        if (atDate != null) {
            this.setParam("date", atDate);
        } else {
            this.setParam("date", `date > 19900101`);
        }
        super.request();
    }

    
}

//获取逐笔委托数据
class GOrder extends GSeQuence {

    //重写request
    request() {
        //组拼数据
        let params = {
            code: this.code,
        }
        super.setParams(params);
        super.request();
    }

    constructor() {
        super();
    }
}

//获取逐笔成交数据
class GStep extends GSeQuence {

    //重写request
    request() {
        //组拼数据
        let params = {
            code: this.code,
        }
        super.setParams(params);
        super.request();
    }

    constructor() {
        super();
    }
}

//获取分笔成交数据
class GTick extends GSeQuence {

    //重写request
    request() {

        if(!CheckMustParams(this,["code"])){
            return;
        }
        //组拼数据
        this.setField("TIME,price,Volume,DealLots");
        this.setUrl("md.quote/tick");
        this.setParam("keys", `'${this.code}'`);
        this.setParam("includeFlags", true);
        this.setParam("subscribe", this.isSubscribe);
        this.setParam("limit", `${this.begin},${this.count}`);

        super.request();
    }

    constructor() {
        super();
    }
}

//-----------支持按日期、天数请求---------
//获取分时数据（含多日）
class GTrend extends GSeQuence {

    
    constructor() {
        super();
        /**
         * 获取分时数据的天数
         * @param {int} days 1-10
         */
        this.days;    
    }

    //重写request
    request() {
        //组拼数据
        if(!CheckMustParams(this,["code","days"])){
            return;
        }
        this.setUrl(`md.quote/trend`);
        this.setField(`Time, Price, Volume, Amount, VWAP, netValue`);
        if (this.days == null) {
            this.days = 1;
        }
        if (this.days != 1) {
            this.setParam("last", `${this.days}`);
        }
        this.setParam("relation", "exchTime");
        this.setParam("keys", `'${this.code}'`);
        this.setParam("subscribe", this.isSubscribe);
        super.request();
    }

    

}

//获取集合竞价数据
class GCallAution extends GSeQuence {

    //重写request
    request() {
        //组拼数据
        if(!CheckMustParams(this,["code"])){
            return;
        }
        this.setField(`time, Price, MatchVolume`);
        this.setUrl("md.quote/auction");
        this.setParam("keys", `'${this.code}'`);
        this.setParam("subscribe", this.isSubscribe);
        this.setParam("limit", `${this.begin},${this.count}`);
        super.request();
    }

    constructor() {
        super();
    }
}

//获取历史分时数据
class GHisTrend extends GSeQuence {
    
    constructor() {
        super();
        /**
         * 指定日期
         * @param {date} date 20201124
         */
        this.date;
        
    }

    //重写request
    request() {
        //组拼数据
        if(!CheckMustParams(this,["code","date"])){
            return;
        }
        this.setUrl(`md.quote/trend`);
        this.setField(`Time, Price, Volume, Amount, VWAP, netValue`);
        this.setParam("date", `date = ${this.date}`);  
        this.setParam("relation", "exchTime");
        this.setParam("keys", `'${this.code}'`);
        super.request();
    }

    
}

//板块排序
class GSectorSort extends GRequestQuote {


    constructor() {
         super();
        /**
         * 板块id
         * @param {string} sectorId 
         */
        this.sectorId;

        /**
         * 排序代码列表
         * @param {Array} codes 
         */
        this.codes;

        /**
         * 排序字段名称
         * @param {string} sortField 
         */
        this.sortField;

        /**
         * 排序方向
         * @param {int} sortType 
         *  desc 降序
         *  asc 升序
         *  none 无序
         */
        this.sortType;

        /**
         * 开始位置
         * @param {int} begin 
         *  -1 倒数count根
         */
        this.begin;

        /**
         * 条数
         * @param {int} count 
         */
        this.count;
       
    }

    //重写request
    //codes排序   md.quote
    //sector排序  md.quote 嵌套 rd.sector/composition
    //筛选排序    meson.screen 
    request() {

        //组拼数据
        if(!this.sectorId&&!this.codes){
            GSDKLog.error("sectorId和codes不能同时为空");
            return;
        }
        if(!CheckMustParams(this,["sortField"])){
            return;
        }
        this.setUrl("md.quote");
        this.setField("code");

        let conditionStr = "";
        let codeStr = "";

        //代码列表组合
        if (this.codes != null) {
            let codeArr = this.codes.split(',');
            for (let i = 0; i < codeArr.length; i++) {
                i == codeArr.length - 1 ? codeStr += `'${codeArr[i]}'` : codeStr += `'${codeArr[i]}',`;
            }
        }

        if (this.condition) {
            //有筛选
            if (this.sectorId) {
                //有板块id
                conditionStr = `code in (select code from meson.screen where blockId = '${this.sectorId}' filter ${this.condition})'`;
            } else {
                //无板块id

            }
        } else {
            //无筛选
            if (this.sectorId) {
                //有板块id
                conditionStr = `code in (select code from rd.sector/composition('${this.sectorId}'))`;
            } else {
                //无板块id
                conditionStr = `code in (${codeStr})`;
            }
        }

        this.setParam("condition", conditionStr);

        //严格按照文档输入,不做输入容错,默认升序
        if (this.sortType == null) {
            this.sortField += ` asc`;
        }
        else if (this.sortType == "none") {

        } else {
            this.sortField += ` ${this.sortType}`;
        }
        this.setParam("orderby", `${this.sortField}`);
        this.setParam("limit", `${this.begin},${this.count}`);

        super.request();
    }

   

}
