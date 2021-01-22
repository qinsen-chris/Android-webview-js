package com.gangtise.gdk.api;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONObject;
import com.gangtise.gdk.asyncThread.WaitingIdThread;
import com.gangtise.gdk.asynctask.Task2CreateRequestItem;
import com.gangtise.gdk.asynctask.Task2ExecFunc;
import com.gangtise.gdk.common.ContentMap;
import com.gangtise.gdk.common.TaskObj;
import com.gangtise.gdk.model.BasicModel;
import com.gangtise.gdk.model.GKeyWizard;
import com.gangtise.gdk.model.GRequestCommon;
import com.gangtise.gdk.result.GSDKInfo;
import com.gangtise.gdk.result.RequestItems;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: created by qinsen
 * date: 2021/1/6
 * email: qinshen@Gangtise.onaliyun.com
 *
 *
 **/
public class GSDK {

    /**
     * 行情连接
     * @param url 租户url
     * @param userid 用户id
     * @param token 用户token
     * @param authServer 第三方认证环境码
     * @param other 其他可扩展参数
     */
    public static void connect(String url,String userid,String token,String authServer,Object other){
        WebView myWebView = GWebView.getInstance();

        String oth = "{redirecturi:'http://40.73.10.101:9528/thirdParty/checkUserInfo'}";

        myWebView.evaluateJavascript("javascript:GSDK.connect('" +url + "','"+userid + "','" + token  + "'," + authServer + "," +oth + ")" ,
                null);

    }

    /**
     * 断开连接
     */
    public static void disconnect(){
        WebView myWebView = GWebView.getInstance();
        myWebView.evaluateJavascript("javascript:GSDK.disconnect()", null);
    }

    /**
     * 创建请求对象
     * @param className 请求对象名称 com.gangtise.gdk.model包下的对象
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static <T extends BasicModel> T createRequestItem(String className){
        String packagename = "com.gangtise.gdk.model.";
        Class c = null;
        T obj = null;
        try {
            c = Class.forName(packagename + className);
            obj =(T) c.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        T finalObj = obj;

        //如果是键盘精灵， 则创建GRequestCommon请求
        if(className.equals("GKeyWizard")){
            className = "GRequestCommon";
            GKeyWizard gKeyWizard = new GKeyWizard();

/*            GRequestCommon gRequestCommon = new GRequestCommon();
            syncharonizedObj((T) gRequestCommon,className);
            gKeyWizard.setSearch(gRequestCommon);*/

            GRequestCommon gRequestCommon = new GRequestCommon();
            gKeyWizard.setSearch(gRequestCommon);
            syncharonizedObj(gKeyWizard,className);
            return (T) gKeyWizard;
        }

        syncharonizedObj(finalObj,className);
        return finalObj;
    }


    private static <T extends BasicModel> void  syncharonizedObj(T obj, String className) {
        WebView myWebView = GWebView.getInstance();

        myWebView.evaluateJavascript("javascript:GSDK.createRequestItem('" +className + "')",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                        if(!StringUtils.isEmpty(value)){
                            JSONObject jsonObject = JSONObject.parseObject(value);
                            if(jsonObject.get("id") != null){
                                obj.setId((String)jsonObject.get("id"));

                                System.out.println("createRequestItem onReceiveValue: "+ jsonObject.get("id"));
                            }
                        }
                    }
                });


/*        Task2CreateRequestItem task = new Task2CreateRequestItem();
        task.execute(obj);*/
    }

    /**
     * 销毁请求对象
     */
    public static <T extends BasicModel> void destroyRequestItem(T  t){
/*        Task2ExecFunc task = new Task2ExecFunc();
        task.execute(t);*/

        WebView myWebView = GWebView.getInstance();
        myWebView.evaluateJavascript("javascript:GSDK.destroyRequestItem("+t+");", null);
    }

    /**
     * 根据id销毁对象
     * @param id
     */
    public static void destroyRequestItemById(String id){
        WebView myWebView = GWebView.getInstance();
        myWebView.evaluateJavascript("javascript:GSDK.destroyRequestItemById('"+id+"');", null);
    }

    /**
     * 获取所有请求对象
     * @return
     */
    public static List<RequestItems> getAllRequestItems(){
        WebView myWebView = GWebView.getInstance();
        myWebView.evaluateJavascript("javascript:GSDK.getAllRequestItems();", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //TODO
            }
        });

        return new ArrayList<>();
    }


    /**
     * 获取当前应用信息JS版本信息
     * @return
     */
    public static GSDKInfo getInfo(){
        WebView myWebView = GWebView.getInstance();
        GSDKInfo gsdkInfo = new GSDKInfo();
        myWebView.evaluateJavascript("javascript:GSDK.getInfo()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

                if (!StringUtils.isEmpty(value)) {
                    JSONObject jsonObject = JSONObject.parseObject(value);
                    if (jsonObject.get("version") != null) {
                        gsdkInfo.setVersion((String) jsonObject.get("version"));

                        Toast.makeText(myWebView.getContext(),"GSDKInfo :"+ gsdkInfo.version,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return gsdkInfo;
    }


}
