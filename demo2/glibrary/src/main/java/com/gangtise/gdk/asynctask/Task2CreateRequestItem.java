package com.gangtise.gdk.asynctask;

import android.text.PrecomputedText;

import javax.xml.transform.Result;
import android.os.AsyncTask;
import android.graphics.Bitmap;

import com.gangtise.gdk.common.ContentMap;
import com.gangtise.gdk.model.BasicModel;
import com.gangtise.gdk.model.GKLine;

/**
 * @author: created by qinsen
 * date: 2021/1/11
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class Task2CreateRequestItem extends AsyncTask<BasicModel, Void, Object> {

    @Override
    protected Object doInBackground(BasicModel... ts) {
        System.out.println("Task2CreateRequestItem doInBackground start---");
        BasicModel t = ts[0];

        while(t.getId() == null){
            if(t.getId()!= null){
                System.out.println("Task2CreateRequestItem getId :" + t.getId());
                break;
            }
        }

        //把t注册到Map中
        ContentMap.BASIC_MODEL_MAP.put(t.getId(),t);

        return t.getId();
    }

    @Override
    protected void onPostExecute(Object o) {

        System.out.println("Task2CreateRequestItem onPostExecute --threadid:" + Thread.currentThread().getId() +"--id:"+ o.toString());
        super.onPostExecute(o);
    }

/*    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }*/

    @Override
    protected void onPreExecute() {
        System.out.println("Task2CreateRequestItem onPreExecute ---threadid:" + Thread.currentThread().getId());
        super.onPreExecute();
    }
}
