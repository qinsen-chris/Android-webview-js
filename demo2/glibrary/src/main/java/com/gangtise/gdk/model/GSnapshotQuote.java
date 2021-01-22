package com.gangtise.gdk.model;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

import com.gangtise.gdk.api.GWebView;
import com.gangtise.gdk.asyncThread.WaitingIdThread;
import com.gangtise.gdk.common.ContentMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: created by qinsen
 * date: 2021/1/14 10
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 获取行情订阅数据
 **/
public class GSnapshotQuote extends GRequestQuote {
    private String codes;   //代码列表 "000001.SZ,000002.SZ"
    private String fields;  //指标列表 "last,change"

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    /**
     * 添加代码
     * @param code "600000.SH,000002.SZ"
     */
    public void addCode(String code){
        String[] arr = null;
        if(this.codes == null){
            arr =  new String[0];
        }else{
            arr = this.codes.split(",");
        }

        List<String> strsToList= Arrays.asList(arr);

        //添加code
        List<String> arrList = new ArrayList(strsToList);
        arrList.add(code);
        String result = "";
        for(String s : arrList){
            result = result + s + ",";
        }
        this.codes = result.substring(0,result.length()-1);
    }

    /**
     * 删除代码
     * @param code 600000.SH
     */
    public void delCode(String code){
        if(this.codes != null){
            String[] arr = this.codes.split(",");
            List<String> strsToList= Arrays.asList(arr);
            //删除code
            List<String> arrList = new ArrayList(strsToList);
            arrList.remove(code);

            if(arrList.isEmpty()){
                this.codes = null;
            }
            else{
                String result = "";
                for(String s : arrList){
                    result = result + s + ",";
                }
                this.codes = result.substring(0,result.length()-1);
            }

        }
    }

    /**
     * 添加指标
     * @param field "AskPrice1"
     */
    public void addField(String field){
        String[] arr = null;
        if(this.fields == null){
            arr =  new String[0];
        }else{
            arr = this.fields.split(",");
        }

        List<String> strsToList= Arrays.asList(arr);

        //添加field
        List<String> arrList = new ArrayList(strsToList);
        arrList.add(field);
        String result = "";
        for(String s : arrList){
            result = result + s + ",";
        }
        this.fields = result.substring(0,result.length()-1);
    }

    /**
     * 删除指标
     * @param field "AskPrice1"
     */
    public void delField(String field){
        if(this.fields != null){
            String[] arr = this.fields.split(",");
            List<String> strsToList= Arrays.asList(arr);
            //删除field
            List<String> arrList = new ArrayList(strsToList);
            arrList.remove(field);

            if(arrList.isEmpty()){
                this.fields = null;
            }
            else{
                String result = "";
                for(String s : arrList){
                    result = result + s + ",";
                }
                this.fields = result.substring(0,result.length()-1);
            }

        }
    }

    /**
     * 发送请求
     */
    public void request(){

        //HandlerThread 异步方式
        if(getOnCallback()==null){
            Log.e("GSnapshotQuote","OnCallback is must be set!");
            return;
        }

        HandlerThread handlerThread = new HandlerThread("GSnapshotQuote");
        Handler handler;
        handlerThread.start();

        Handler uihandle = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                GSnapshotQuote model = (GSnapshotQuote)msg.obj ;

                WebView myWebView = GWebView.getInstance();

                //GSnapshotQuote--------------------
                if(model.isSubscribe){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"').isSubscribe = " + model.isSubscribe +";" , null);
                }
                if(model.getCodes() !=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"').codes = '" + model.getCodes() +"';" , null);
                }
                if(model.getFields() != null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+model.getId()+"').fields = '" + model.getFields() +"';" , null);
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
