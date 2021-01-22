package com.org.demo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.gangtise.gdk.api.GSDK;
import com.gangtise.gdk.api.GWebView;
import com.gangtise.gdk.asyncThread.WaitingIdThread;
import com.gangtise.gdk.common.ContentMap;
import com.gangtise.gdk.ginterface.GResultCallBack;
import com.gangtise.gdk.model.BasicModel;
import com.gangtise.gdk.model.GKLine;
import com.gangtise.gdk.model.GKeyWizard;
import com.gangtise.gdk.model.GSnapshotQuote;
import com.gangtise.gdk.result.GDataSet;
import com.gangtise.gdk.result.GSDKInfo;
import com.gangtise.gdk.result.dataprocess.GColumnInfo;
import com.gangtise.gdk.result.dataprocess.TableCellData;
import com.org.demo2.otherview.GKLineActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;

    private EditText keyWarz;

    private GKLine gkLine = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化webview
        GWebView.initContext(MainActivity.this);

        //btn0 获取JS版本
        btn0 = findViewById(R.id.btn_0);
        btn0.setOnClickListener(new MainClickListener());

        //btn1 建立连接
        btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(new MainClickListener());

        //按钮2，断开连接
        btn2 = findViewById(R.id.btn_2);
        btn2.setOnClickListener(new MainClickListener());

        //按钮3，获取K线数据
        btn3 = findViewById(R.id.btn_3);
        btn3.setOnClickListener(new MainClickListener());

        //按钮4，获取K线数据 清空参数
        btn4 = findViewById(R.id.btn_4);
        btn4.setOnClickListener(new MainClickListener());

        //按钮5，键盘精灵
        btn5 = findViewById(R.id.btn_5);
        btn5.setOnClickListener(new MainClickListener());

        //按钮6，行情订阅数据
        btn6 = findViewById(R.id.btn_6);
        btn6.setOnClickListener(new MainClickListener());

    }

    /**
     * 按钮点击事件
     */
    private class MainClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int iv = v.getId();
            switch (iv){
                case R.id.btn_0:  //获取JS版本
                    GSDKInfo gsdkInfo  = GSDK.getInfo();
                    break;
                case R.id.btn_1:  //建立连接
                    String url = "ztzq.gangtise.com.cn";
                    String userid = "1";
                    String token = "1";
                    GSDK.connect(url,userid,token,null,"");
                    break;
                case R.id.btn_2:  //断开连接
                    GSDK.disconnect();
                    break;
                case R.id.btn_3: //获取K线数据
                    //1、创建对象
                    gkLine = GSDK.createRequestItem("GKLine");
                    //2、设置参数
                    gkLine.setCode("000001.SZ");
                    gkLine.setPeriod("day");
                    //3、设置回调
                    gkLine.setOnCallback(new GKLineCallBack());
                    //4、发送请求
                    gkLine.request();
                    //5、传递对象到other active
                    break;
                case R.id.btn_4:  //清空行情请求参数
                    //1、创建对象
                    if(gkLine == null){
                        System.out.println("gkLine is null");
                    }else{
                        gkLine.clearParams();
                    }
                    break;
                case R.id.btn_5:  //键盘精灵
                    keyWarz = findViewById(R.id.keyWizard);
                    //1、创建对象
                    GKeyWizard gKeyWizard = GSDK.createRequestItem("GKeyWizard");
                    gKeyWizard.setCategory("0");
                    gKeyWizard.setMarket("1");
                    gKeyWizard.setBegin(0);
                    gKeyWizard.setCount(20);
                    gKeyWizard.setField("sufSecuCode, secuAbbr");
                    gKeyWizard.setOnCallback(new GKeyWizardCallBack());
                    gKeyWizard.match(keyWarz.getText().toString());

                    break;
                case R.id.btn_6: //行情订阅数据
                    GSnapshotQuote snapshotQuote = GSDK.createRequestItem("GSnapshotQuote");
                    snapshotQuote.setCodes("000001.SZ,000002.SZ");
                    snapshotQuote.setFields("code,last,change");
                    snapshotQuote.isSubscribe = true;
                    snapshotQuote.setOnCallback(new SnapshotQuoteCallBack());
                    snapshotQuote.request();
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * GKLine回调
     */
    class GKLineCallBack implements GResultCallBack {
        @Override
        public void onCallback(GDataSet dataSet) {
            System.out.println("GKLineCallBack------"+ dataSet.toString());

            List<GColumnInfo> columnInfos = dataSet.columnInfos;

//            GColumnInfo columnInfo = dataSet.getColumn(2);
//            System.out.println("GKLineCallBack------columnInfo "+ columnInfo.toString());
//
//            GColumnInfo columnInfo1 = dataSet.getColumnByName(columnInfos.get(0).name);
//            System.out.println("GKLineCallBack------columnInfo1 "+ columnInfo1.toString());
//
//            TableCellData cellData = dataSet.getCellData(1,2);
//            System.out.println("GKLineCallBack------cellData "+ cellData.toString());
//
//            String celltext = dataSet.getCellText(1,2);
//            System.out.println("GKLineCallBack------celltext "+ celltext);
//
//            TableCellData columnInfo2 = dataSet.getCellDataByName(1,"Open");
//            System.out.println("GKLineCallBack------columnInfo2 "+ columnInfo2.toString());
//
//            String celltext2 = dataSet.getCellTextByName(1,"Open");
//            System.out.println("GKLineCallBack------celltext2 "+ celltext2);
//
//            List<Object> list =  dataSet.getColValues(1);
//            System.out.println("GKLineCallBack------list "+ list.toString());
//
//            List<Object> list2 = dataSet.getColValuesByName("Open");
//            System.out.println("GKLineCallBack------list2 "+ list2.toString());
//
//            List<String> list3 = dataSet.getRowValues(1);
//            System.out.println("GKLineCallBack------list3 "+ list3.toString());
        }
    }

    /**
     * GKeyWizard回调
     */
    class GKeyWizardCallBack implements GResultCallBack{
        @Override
        public void onCallback(GDataSet dataSet) {
            System.out.println("GKeyWizardCallBack------"+ dataSet.toString());
            //跳转到另一个Activity
            Intent intent = new Intent(MainActivity.this, ListviewActivity.class);
            intent.putExtra("com.org.demo2.gKeyWizard", JSONObject.toJSONString(dataSet));
            startActivity(intent);

/*            Intent intent = new Intent(MainActivity.this, GKLineActivity.class);
            startActivity(intent);*/
        }
    }

    /**
     * SnapshotQuote回调
     */
    class SnapshotQuoteCallBack implements  GResultCallBack{
        @Override
        public void onCallback(GDataSet dataSet) {
            System.out.println("SnapshotQuoteCallBack------"+ dataSet.toString());
        }
    }

}