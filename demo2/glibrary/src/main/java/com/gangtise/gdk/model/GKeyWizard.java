package com.gangtise.gdk.model;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

import com.gangtise.gdk.api.GWebView;
import com.gangtise.gdk.asyncThread.WaitingIdThread;
import com.gangtise.gdk.common.ContentMap;

import java.util.Map;

/**
 * @author: created by qinsen
 * date: 2021/1/12
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class GKeyWizard extends BasicModel {

    private String category; //证券类型
    private String market;  //市场类型
    private String field; //设置字段 "code , name"
    private Integer begin; //开始位置
    private Integer count; //条数
    private GRequestCommon search;  //请求对象


/*    public GKeyWizard() {
        CommonApi<GRequestCommon> api = new CommonApi<GRequestCommon>();
        search = api.createRequestItem("GRequestCommon");
    }*/

    /**
     * 键盘精灵查询
     * @param keyWord 输入条件
     */
    public void match(String keyWord){
        search.setUrl("keyboardsearch/secu/search/query");
        search.setField(this.field==null? "sufSecuCode, secuAbbr": this.field);
        search.setParam("keyword",keyWord);
        search.setParam("category",this.category);
        search.setParam("market",this.market);
        search.setParam("limit",begin + "," + count);
        search.setOnCallback(this.getOnCallback());


        //search.request();

        //HandlerThread 异步方式
        if(getOnCallback()==null){
            Log.e("GKeyWizard","OnCallback is must be set!");
            return;
        }
        HandlerThread handlerThread = new HandlerThread("GKeyWizard");
        Handler handler;
        handlerThread.start();

        Handler uihandle = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                GKeyWizard gKeyWizard = (GKeyWizard)msg.obj ;
                GRequestCommon model = gKeyWizard.getSearch() ;
                //GKeyWizard 返回的id 赋值给GRequestCommon
                model.setId(gKeyWizard.getId());

                WebView myWebView = GWebView.getInstance();
                if(model.getUrl() !=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"').setUrl('" + model.getUrl() +"');" , null);
                }
                if(model.getField() !=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"').setField('" + model.getField() +"');" , null);
                }

                if( !model.getParams().isEmpty()){
                    Map<String, Object> map = model.getParams().getInnerMap();
                    for (Map.Entry<String, Object> m : map.entrySet()){
                        if(m.getValue() instanceof String){
                            myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"')."+ "setParam('" + m.getKey() +"','"+m.getValue()+ "');" , null);
                        }else{
                            myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"')."+ "setParam('" + m.getKey() +"',"+m.getValue()+ ");" , null);
                        }
                    }
                }

                ContentMap.BASIC_MODEL_MAP.put(model.getId(),model);

                myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"').request()",null);
            }
        };

        handler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                System.out.println("receive message.whatA=" + msg.what);
                return false;
            }
        });

        handler.post(new WaitingIdThread(this,uihandle));

    }

    /**
     * 清除search参数
     */
    public void clearParams(){
        WebView myWebView = GWebView.getInstance();
        myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+getId()+"').clearParams()",null);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public GRequestCommon getSearch() {
        return search;
    }

    public void setSearch(GRequestCommon search) {
        this.search = search;
    }


}
