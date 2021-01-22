package com.gangtise.gdk.asynctask;

import android.os.AsyncTask;
import android.webkit.WebView;

import com.gangtise.gdk.api.GWebView;
import com.gangtise.gdk.common.TaskObj;
import com.gangtise.gdk.model.BasicModel;

/**
 * @author: created by qinsen
 * date: 2021/1/10
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class Task2SetParams extends AsyncTask<TaskObj, Void, Object> {

    @Override
    protected Object doInBackground(TaskObj... ts) {
        System.out.println("Task2SetParams doInBackground start---" );
        TaskObj taskObj = ts[0];
        BasicModel t =(BasicModel) taskObj.requestObj;

        while(t.getId() == null){
            if(t.getId()!= null){
                System.out.println("Task2SetParams getId :" + t.getId());
                break;
            }
        }
        String cmd = "javascript:GSDK.getRequestItemsById('"+ t.getId() +"')." + taskObj.cmd;
        System.out.println("Task2SetParams cmd :" + cmd);
        return cmd;
    }

    @Override
    protected void onPostExecute(Object o) {

        WebView myWebView = GWebView.getInstance();
      /*  myWebView.evaluateJavascript(o.toString(),
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        if(!StringUtils.isEmpty(value)){
                            System.out.println("Task2SetParams ----------onPostExecute>>>>success： " +value);
                        }
                    }
                });*/
        myWebView.evaluateJavascript(o.toString(), null);
        System.out.println("Task2SetParams onPostExecute --- ");
        super.onPostExecute(o);

        //取消任务
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.cancel(true);
    }

/*    @Override
    protected void onProgressUpdate(Integer... values) {
        System.out.println("Task2SetParams onProgressUpdate ---");
        super.onProgressUpdate(values);
    }*/

    @Override
    protected void onPreExecute() {
        System.out.println("Task2SetParams onPreExecute ---");
        super.onPreExecute();
    }
}
