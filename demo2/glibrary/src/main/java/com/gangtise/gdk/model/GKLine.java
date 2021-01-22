package com.gangtise.gdk.model;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

import com.gangtise.gdk.api.GWebView;
import com.gangtise.gdk.asyncThread.WaitingIdThread;
import com.gangtise.gdk.asynctask.Task2Request;
import com.gangtise.gdk.common.ContentMap;

import java.util.Map;

/**
 * @author: created by qinsen
 * date: 2021/1/6
 * email: qinshen@Gangtise.onaliyun.com
 *
 * K线请求
 **/
public class GKLine extends GSeQuence {

    private String period;
    private Integer cqMode;
    private String beginDate;
    private String endDate;

    public GKLine() {
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;

/*        String cmd = "period = '" + period +"';";
        assembleTask2SetParams(cmd);*/

    }

    public Integer getCqMode() {
        return cqMode;
    }

    public void setCqMode(Integer cqMode) {
        this.cqMode = cqMode;

     /*   String cmd = "cqMode = " + cqMode +";";
        assembleTask2SetParams(cmd);
*/
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;

/*        String cmd = "beginDate = '" + beginDate +"';";
        assembleTask2SetParams(cmd);*/
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;

/*        String cmd = "endDate = '" + endDate +"';";
        assembleTask2SetParams(cmd);*/
    }

    /**
     * 发送请求
     */
    public void request(){
        //AsyncTask 方式异步请求，等待id
/*        Task2Request gkLineRequest = new Task2Request();
        gkLineRequest.execute(this);*/

        //HandlerThread 异步方式
        if(getOnCallback()==null){
            Log.e("GKLine","OnCallback is must be set!");
            return;
        }
        HandlerThread handlerThread = new HandlerThread("GKLine"+System.currentTimeMillis());
        Handler handler;
        handlerThread.start();

        Handler uihandle = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                GKLine gkLine = (GKLine)msg.obj ;

                WebView myWebView = GWebView.getInstance();

                if(gkLine.getPeriod()!=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').period = '" + gkLine.getPeriod() +"';" , null);
                }
                if(gkLine.getCqMode() !=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').cqMode = " + gkLine.getCqMode() +";" , null);
                }
                if(gkLine.getBeginDate() !=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').beginDate = '" + gkLine.getBeginDate() +"';" , null);
                }
                if(gkLine.getEndDate() != null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').endDate = '" + gkLine.getEndDate() +"';" , null);
                }
                //GSeQuence--------------------
                if(gkLine.getCode()!=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').code = '" + gkLine.getCode() +"';" , null);
                }
                if(gkLine.getBegin()!= null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').begin = " + gkLine.getBegin() +";" , null);
                }
                if(gkLine.getCount() !=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').count = " + gkLine.getCount() +";" , null);
                }

                //GRequestQuote--------------------
                if(gkLine.isSubscribe){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').subscribe = " + gkLine.isSubscribe +";" , null);
                }
                if(gkLine.getField() !=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').field = '" + gkLine.getField() +"';" , null);
                }
                if(gkLine.getUrl() !=null){
                    myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').url = '" + gkLine.getUrl() +"';" , null);
                }
                if( !gkLine.getParams().isEmpty()){
                    Map<String, Object> map = gkLine.getParams().getInnerMap();
                    for (Map.Entry<String, Object> m : map.entrySet()){
                        if(m.getValue() instanceof String){
                            myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"')."+ "setParam('" + m.getKey() +"','"+m.getValue()+ "');" , null);
                        }else{
                            myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"')."+ "setParam('" + m.getKey() +"',"+m.getValue()+ ");" , null);
                        }
                    }
                }

                ContentMap.BASIC_MODEL_MAP.put(gkLine.getId(),gkLine);

                myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+gkLine.getId()+"').request()",null);

                handlerThread.quit();
            }
        };

        handler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                System.out.println("receive message.whatA=" + msg.what);
                return true;
            }
        });

        handler.post(new WaitingIdThread(this,uihandle));

    }


}

