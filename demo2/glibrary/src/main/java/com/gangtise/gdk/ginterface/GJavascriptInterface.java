package com.gangtise.gdk.ginterface;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.gangtise.gdk.common.ContentMap;
import com.gangtise.gdk.model.BasicModel;
import com.gangtise.gdk.result.GDataSet;
import com.gangtise.gdk.result.dataprocess.DataResult;
import com.gangtise.gdk.result.dataprocess.DataSource;
import com.gangtise.gdk.result.dataprocess.GDataManager;
import com.gangtise.gdk.result.dataprocess.Table;
import com.gangtise.gdk.result.dataprocess.TableGid;
import com.gangtise.gdk.result.dataprocess.TableInfo;

import java.util.List;

/**
 * @author: created by qinsen
 * date: 2021/1/7
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class GJavascriptInterface {

    private Context mcontex;

    public GJavascriptInterface(Context context){
        mcontex = context;
    }

    @JavascriptInterface
    public void showMessage(){
        Toast.makeText(mcontex,"你点我了！",Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void sdk_java_callback(String obj){
        if(obj != null){
            //Toast.makeText(mcontex,"sdk_java_callback--- "+ obj,Toast.LENGTH_SHORT).show();
            Log.d("sdk_java_callback：",obj);

            JSONObject jsonObject = JSONObject.parseObject(obj);
            if(jsonObject.get("id") != null){
                //1、根据id获取实例对象
                BasicModel model = ContentMap.BASIC_MODEL_MAP.get(jsonObject.get("id"));
                System.out.println("sdk_java_callback--- BasicModel:" +  model.toString());
                //2、处理DataSet
                GDataSet gDataSet = GDataManager.transformDataSource(jsonObject);

                //3、调用实例对象的回调函数
                model.getOnCallback().onCallback(gDataSet);

                //4、ContentMap.BASIC_MODEL_MAP移除
                //ContentMap.BASIC_MODEL_MAP.remove(jsonObject.get("id"));
            }

        }else{
            Log.d("sdk_java_callback...","obj is null");
        }

    }

    @JavascriptInterface
    public void sdk_java_log(String obj){
        if(obj != null ){
            //Toast.makeText(mcontex,"sdk_java_log--- "+ obj,Toast.LENGTH_SHORT).show();
            Log.d("sdk_java_log：",obj);
        }else{
            Log.d("sdk_java_log...","obj is null");
        }
    }

    @JavascriptInterface
    public void sdk_java_notification(String obj){
        if(obj != null ){
            //Toast.makeText(mcontex,"sdk_java_notification--- "+ obj,Toast.LENGTH_SHORT).show();
            Log.d("sdk_java_notification：",obj);
        }else{
            Log.d("sdk_java_notification","obj is null");
        }
    }


}
