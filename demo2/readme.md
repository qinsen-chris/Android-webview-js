1、环境搭建
Setup Type: Standard
SDK Folder: C:\Users\admin\AppData\Local\Android\Sdk
JDK Location: C:\Program Files\Android\Android Studio\jre (Note: Gradle may be using JAVA_HOME when invoked from command line. More info...)

错误：
SDK emulator directory is missing

版本控制：

Gradle Plugin Version: Gradle 插件版本  ：com.android.tools.build:gradle:3.1.2
Gradle Version: Gradle发行版本 ：gradle-5.6.4-bin.zip
Android SDK Build Tools Version：Android SDK 构建工具版本  ：buildToolsVersion "30.0.3"

minSdkVersion 21

2、开发计划
2021.1.5-2021.1.7：  android相关知识点学习
2021.1.8-2021.1.12：验证调用js、接收返回值、注册回调函数、异步获取返回值等相关功能点及可行性分析
2021.1.13-2021.1.16: 使用android框架封装JS
2021.1.18-2021.1.21：测试及修改

3、android SDK
软件开发工具包（Software Development Kit，缩写SDK）一般是一些软件工程师为特定的软件包、软件框架、硬件平台、操作系统等建立应用软件时的开发工具的集合。
Android SDK一般以jar包、aar包、so库等方式进行封装导入App的项目中，然后提供一些公开的API供接入方调用。所以作为SDK的开发者呢，
就需要将自己SDK项目变成library，通过AndroidStudio生成jar或者aar包，提供给接入方使用。

https://developer.android.google.cn/studio/projects/android-library

4、允许远程调试JS ？？
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }


5、Android Studio 中添加js资源  、调用js
https://blog.csdn.net/FL1623863129/article/details/73811520?utm_source=blogxgwz1
https://www.csdn.net/tags/OtTaIg4sMTE5Ni1ibG9n.html



webview :  
	加载html
	Native和JavaScript相互调用

//
1、java调用jsdk hock方法 ， java直接调用js中方法
2、html引入min.js
3、回调测试，html中自定义回调方法
4、mWvMain.evaluateJavascript("javascrpit:",null);  函数直接返回值接收测试

bug:   1、 js端必须对数据进行处理JSON.stringify(obj ); 2、回调方法中用String对象接收
    var jsonStr = JSON.stringify(obj );
    window.myTosat.sdk_java_notification(jsonStr);

public void sdk_java_callback(String obj){}


//-----------------
GSDK_HOOK.start() 调用时机问题

//-----------------
初始化webview : 单例模式

connect ,    通知、日志
disconnect ：主动断开（被动断开有通知）

//------------------
封装GKLine接口，
创建请求对象：
	createRequestItem ： 返回一个对象如：GKLine .请求对象id
设置参数 ：
	 GKLine setCode ：调用js，设置参数
//设置回调地址
	
发送请求：
	request  ：  通知、日志、callback 
封装GDataSet：
	//执行回调


//------------------
同步、异步问题：

//------------------
android webview调试模式

--------------------问题
E/AndroidRuntime: FATAL EXCEPTION: main
    Process: com.org.myapplication, PID: 2938
    java.lang.NoClassDefFoundError: Failed resolution of: Lorg/apache/commons/lang3/StringUtils;
方案： arr包中的第三方依赖，引入到当前项目中


I/Choreographer: Skipped 18281 frames!  The application may be doing too much work on its main thread.
原因：AsyncTask 任务过多
解决：
1、android.os.	


------------------------------异步任务阻塞
I/art: Do partial code cache collection, code=110KB, data=106KB
I/art: After code cache collection, code=110KB, data=106KB
    Increasing code cache capacity to 512KB

------------------------------手机端无法发送请求
D/NetworkSecurityConfig: No Network Security Config specified, using platform default

解决：
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />