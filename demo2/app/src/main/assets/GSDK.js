
//应用信息
class GSDKInfo{
    constructor(){
        this.version = "v1.0.1";
    }
    
}

//返回信息
class GMessage {
    constructor() {
        this.message;//string
        this.code;//int
    }
}

class GLog {
    constructor(){
        this.level;//string  等级 log 记录流水操作 / info 记录关键操作 / error 记录错误操作
        this.msg;//string    消息
        this.time;//date     记录时间
    }
}


class GSDK{
   
   /**
    * 获取当前应用信息
    * @return {GSDKInfo}
    */
   static getInfo(){
       let info = new GSDKInfo();
       return info;
   };

   /**
    * 行情连接
    * @param {string} url        租户url
    * @param {string} userid     用户id
    * @param {string} token      用户token
    * @param {string} authServer 第三方认证环境码
    * @param {object} other      其他可扩展参数
    */
   static connect(url,userid,token,authServer = "default",other = null){
       let session = GSession.sharedInstance();
       let params = {
           url:url,
           userId:userid,
           accessToken:token,
           env:authServer,
       }
       if(other != null){
            for (const key in other) {
                if(other[key] != null){
                    params[key] = other[key];
                }
            }
        }
       session.login(params);
   };

   /**
    * 断开连接
    */
   static disconnect(){
        let session = GSession.sharedInstance();
        session.loginOut();
   };

   /**
    * 创建Requset对象
    * @param {string}  className 功能名
    * @return {object} 对应功能对象
    */
   static createRequestItem(className){
       //根据类名创建对应类
       try {
            let item = eval(`new ${className}()`);
            GRequestManager.addItems(item);
            return item;
       } catch (error) {
            //找不到类，抛异常
            console.log(error);
            if(GSDK.onNotification != null){
                let error = new GMessage();
                error.message = error.message;
                error.code = 1001;
                GSDK.onNotification(error);
            }
       }
   };

    /**
     * 销毁请求对象
     * @param {object} requestObj  请求对象
     */
   static destroyRequestItem(requestObj){
        if(requestObj != null && requestObj.id != null){
            GSDK.destroyRequestItemById(requestObj.id);
        }
   };


    /**
     * 根据id销毁请求对象
     * @param {string} id 
     */
   static destroyRequestItemById(id){
       if(id != null){
           GRequestManager.deleteItemsWithId(id);
           GDataSetManager.sharedInstance().delDataSetWithId(id);
           GDataSourceManager.sharedInstance().delDataSourceWithId(id);
       }
   }

   /**
    * 获取所有请求对象
    * @return {Array} 请求对象列表
    */
   static getAllRequestItems(){
      return GRequestManager.getAllRequestItems();
   };

   /**
    * 清空所有请求对象
    */
   static clearAllRequestItems(){};

    /**
     * 获取所有订阅列表
     */
   static getAllSubscribe(){};

    /**
     * 通过id获取请求对象 -- 不需要
     * @param {string} id 
     */
   static getRequestItemsById(id){
      return GRequestManager.getItemsWithId(id);
   };
  
}
GSDK.count = 0;//对象id计数器
GSDK.requestId = 0;//请求id计数器
GSDK.onRealLog = GSDK_HOOK.getOnRealLog();//实时日志回调
GSDK.onNotification = GSDK_HOOK.getOnNotification();//通知回调