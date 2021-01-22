package com.gangtise.gdk.asynctask;

import android.os.AsyncTask;
import android.webkit.WebView;

import com.gangtise.gdk.api.GWebView;
import com.gangtise.gdk.common.TaskObj;
import com.gangtise.gdk.model.BasicModel;

/**
 * @author: created by qinsen
 * date: 2021/1/13
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 销毁请求对象任务
 **/
public class Task2ExecFunc extends AsyncTask<BasicModel, Void, Object> {

    @Override
    protected Object doInBackground(BasicModel... ts) {
        System.out.println("Task2ExecFunc doInBackground start---" );
        BasicModel t = ts[0];

        while(t.getId() == null){
            if(t.getId()!= null){
                System.out.println("Task2ExecFunc getId :" + t.getId());
                break;
            }
        }

        return t;
    }

    @Override
    protected void onPostExecute(Object o) {

        WebView myWebView = GWebView.getInstance();
        myWebView.evaluateJavascript("javascript:GSDK.destroyRequestItem("+o+");", null);
        System.out.println("Task2ExecFunc onPostExecute --- ");
        super.onPostExecute(o);

        //取消任务
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.cancel(true);
    }

/*    @Override
    protected void onProgressUpdate(Integer... values) {
        System.out.println("Task2SetParams onProgressUpdate ---");
        super.onProgressUpdate(values);
    }*/

    @Override
    protected void onPreExecute() {
        System.out.println("Task2ExecFunc onPreExecute ---");
        super.onPreExecute();
    }
}
