package com.gangtise.gdk.model;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

import com.alibaba.fastjson.JSONObject;
import com.gangtise.gdk.api.GWebView;
import com.gangtise.gdk.asyncThread.WaitingIdThread;
import com.gangtise.gdk.common.ContentMap;

import java.util.Map;

/**
 * @author: created by qinsen
 * date: 2021/1/6
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 行情请求接口
 **/
public class GRequestQuote extends BasicModel {

    public boolean isSubscribe = false;

    private String field;

    private String url;
    private JSONObject params = new JSONObject();


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;

    }

    public JSONObject getParams() {
        return params;
    }


    /**
     * 设置参数
     * @param key
     * @param value
     */
    public void setParam(String key,Object value) {
        this.params.put(key,value);

        String cmd = "";
/*        if(value instanceof String){
            cmd = "setParam('" + key +"','"+value+ "');" ;
        }else{
            cmd = "setParam('" + key +"',"+value+ ");" ;
        }
        assembleTask2SetParams(cmd);*/
    }

    /**
     * 清空参数
     */
    public void clearParams(){
        this.params = new JSONObject();

/*        String cmd = "clearParams();" ;
        assembleTask2SetParams(cmd);*/

        //HandlerThread 异步方式
        HandlerThread handlerThread = new HandlerThread("GRequestQuote");
        Handler handler;
        handlerThread.start();

        Handler uihandle = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                WebView myWebView = GWebView.getInstance();
                myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+ getId() +"').clearParams();", null);
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
     * 关闭
     */
    public void stop(){
/*        String cmd = "stop();" ;
        assembleTask2SetParams(cmd);*/

        //HandlerThread 异步方式
        HandlerThread handlerThread = new HandlerThread("GRequestQuote");
        Handler handler;
        handlerThread.start();

        Handler uihandle = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                WebView myWebView = GWebView.getInstance();
                myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+ getId() +"').stop();", null);
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
     * 发送请求
     */
    public void request(){

        //HandlerThread 异步方式

        if(getOnCallback()==null){
            Log.e("GRequestQuote","OnCallback is must be set!");
            return;
        }

        HandlerThread handlerThread = new HandlerThread("GRequestQuote");
        Handler handler;
        handlerThread.start();

        Handler uihandle = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                GRequestQuote model = (GRequestQuote)msg.obj ;

                WebView myWebView = GWebView.getInstance();

                //GRequestQuote--------------------
                if(model.isSubscribe){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"').isSubscribe = " + model.isSubscribe +";" , null);
                }
                if(model.getField() !=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"').field = '" + model.getField() +"';" , null);
                }
                if(model.getUrl() !=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"').url = '" + model.getUrl() +"';" , null);
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

}
