package com.gangtise.gdk.asynctask;

import android.os.AsyncTask;
import android.webkit.WebView;

import com.gangtise.gdk.api.GWebView;
import com.gangtise.gdk.model.BasicModel;

/**
 * @author: created by qinsen
 * date: 2021/1/11
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class Task2Request extends AsyncTask<BasicModel, Void, Object> {

    @Override
    protected Object doInBackground(BasicModel... ts) {
        BasicModel t = ts[0];

        while(t.getId() == null){
            if(t.getId()!= null){
                System.out.println("Task2Request getId :" + t.getId());
                break;
            }
        }

        return t.getId();
    }

    @Override
    protected void onPostExecute(Object o) {
        System.out.println("Task2Request onPostExecute --->" + Thread.currentThread().getId() +",id:"+ o.toString());
        //发送请求
        WebView myWebView = GWebView.getInstance();
        myWebView.evaluateJavascript("javascript:GSDK.getRequestItemsById('"+o.toString()+"').request()",null);

        super.onPostExecute(o);
    }

/*    @Override
    protected void onProgressUpdate(Integer... values) {
        System.out.println("Task2Request onProgressUpdate --->" + Thread.currentThread().getId());
        super.onProgressUpdate(values);
    }*/

    @Override
    protected void onPreExecute() {
        System.out.println("Task2Request onPreExecute ---" );
        super.onPreExecute();
    }
}
