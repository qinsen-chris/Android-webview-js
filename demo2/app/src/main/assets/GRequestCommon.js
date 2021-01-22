//通用服务请求接口
class GRequestCommon{
    

    constructor() {
        //生成对象id
        this.id;
        //通知回调
        this.onCallback;
        //private
        this.url;
        this.field;
        // #function; 不要了
        this.params = {};
        this.id = `${GSDK.count++}`;
    }

    /**
     * 设置地址
     * @param {string} url 
     */
    setUrl(url){
        this.url = url;
    };

    /**
     * 设置参数字段
     * @param {string} field 
     */
    setField(field){
        this.field = field;
    }

    /**
     * 设置参数
     * @param {string} key      参数名
     * @param {string} value    值
     */
    setParam(key,value){
        this.params[key] = value;
    };

    /**
     * 清空参数
     */
    clearParams(){
        this.params = {};
    }

    /**
     * 发送
     */
    request(){
        let requestId = GSDK.requestId++;
        //sendHttp
        let sendObj = {
            type:"http",
            objectId:this.id,
            requestId:requestId,
            url: this.url,
            field: this.field,
            params: this.params
        }
        GDataManager.sharedInstance().fireData(sendObj);
    }

}

//键盘精灵
class GKeyWizard{

    constructor() {
         //通知回调
        this.onCallback = GSDK_HOOK.getOnCallback();

        /**
         * 设置证券类型
         * @param {string} category "0"
         */
        this.category;

        /**
         * 设置市场类型
         * @param {string} market "1"
         */
        this.market;

        /**
         * 设置字段
         * @param {string} field "code , name"
         */
        this.field;


        /**
         * 开始位置
         * @param {int} begin 
         *  -1 倒数count根
         */
        this.begin = 0;

        /**
         * 条数
         * @param {int} count 
         */
        this.count = 10;

        this.search = GSDK.createRequestItem("GRequestCommon");
        this.id = this.search.id;
    }

    /**
     * 设置匹配关键字
     * @param {string} keyWord "000"
     */
     match(keyWord){
        if(!CheckMustParams(this,["category","market"])){
            return;
        }

        this.search.setUrl("keyboardsearch/secu/search/query");
        this.field = "sufSecuCode, secuAbbr";
        this.search.setField(this.field);
        this.search.setParam("keyword", keyWord);
        this.search.setParam("category",this.category);
        this.search.setParam("market",this.market);
        this.search.setParam("limit",`${this.begin},${this.count}`);
        this.search.onCallback = this.onCallback;
        this.search.request();
        return this.search.id;
     };
     clearParams(){
        this.search.clearParams();
    }
}