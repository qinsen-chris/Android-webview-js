package com.gangtise.gdk.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gangtise.gdk.common.GDKException;
import com.gangtise.gdk.ginterface.GJavascriptInterface;

/**
 * @author: created by qinsen
 * date: 2021/1/6
 * email: qinshen@Gangtise.onaliyun.com
 *  WebView初始化
 **/
public class GWebView {

    private static WebView myWebView;
    private static Context context;

    public static WebView getInstance(){
        //如何创建出WebView
        if(myWebView == null){
            if(context!=null){
                myWebView = new WebView(context);
                configWebView(myWebView);
            }else{
                throw new GDKException(900001,"Context为空！");
            }
        }

        return myWebView;
    }

    /**
     * 初始化WebView
     * @param wv
     */
    public static void initWebView(WebView wv){
        myWebView = wv;
        configWebView(myWebView);
    }

    /**
     *
     * @param ct
     */
    public static void initContext(Context ct){
        context = ct;
        myWebView = new WebView(context);
        configWebView(myWebView);
    }

    /**
     * WebView 配置，初始化加载js
     * @param wv
     */
    private static void configWebView(WebView wv){
        //开启JavaScript支持
        wv.getSettings().setJavaScriptEnabled(true);
        //添加js接口
        wv.addJavascriptInterface(new GJavascriptInterface(wv.getContext()),"myTosat");

        //设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        wv.getSettings().setDomStorageEnabled(true);

        wv.setWebViewClient(new WebViewClient() {
             @Override
             public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                 // TODO Auto-generated method stub
                 // handler.cancel();// Android默认的处理方式 super方法包含该方法导致部分手机无法访问页面
                 handler.proceed();// 接受所有网站的证书
                 // handleMessage(Message msg);// 进行其他处理
             }

/*             @Override
             public void onPageStarted(WebView view, String url, Bitmap favicon) {
                 // 在开始加载网页时会回调
                 super.onPageStarted(view, url, favicon);
             }*/
/*             @Override
             public boolean shouldOverrideUrlLoading(WebView view, String url) {
                 // 拦截 url 跳转,在里边添加点击链接跳转或者操作
                 view.loadUrl(url);
                 return true;
             }*/

             @Override
             public void onPageFinished(WebView view, String url) {
                 Log.d("---onPageFinished",url);
                 //加这句代码可以在谷歌浏览器调试H5代码 需要翻墙
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                     wv.setWebContentsDebuggingEnabled(true);
                 }
                 // 在结束加载网页时会回调(function(){**这里是需要执行JS代码**})()

                 super.onPageFinished(view, url);
             }

             @Override
             public void onReceivedError(WebView view, int errorCode,
                                         String description, String failingUrl) {
                 // 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
                 Log.e("---onReceivedError",description);
                 super.onReceivedError(view, errorCode, description, failingUrl);
             }

             @Override
             public WebResourceResponse shouldInterceptRequest(WebView view,
                                                               WebResourceRequest request) {
                 // 在每一次请求资源时，都会通过这个函数来回调
                 Log.d("---InterceptRequest",request.getUrl().getPath());
                 return super.shouldInterceptRequest(view, request);
             }
         }
        );

        wv.loadUrl("file:///android_asset/gangtise.html");
        Log.d("configWebView------","init success!");
    }
}
