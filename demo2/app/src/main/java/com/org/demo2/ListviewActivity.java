package com.org.demo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.gangtise.gdk.result.GDataSet;

import java.util.List;

public class ListviewActivity extends AppCompatActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        Intent intent = getIntent();
        String obj = intent.getStringExtra("com.org.demo2.gKeyWizard");

        //解析数据
       /* JSONObject jsonObject = JSONObject.parseObject(obj);
        GDataSet gDataSet = JSONObject.parseObject(JSONObject.toJSONString(jsonObject),GDataSet.class);*/
        GDataSet gDataSet = JSONObject.parseObject(obj, GDataSet.class);
        List<Object> list1 = gDataSet.getColValuesByName("sufSecuCode");
        List<Object> list2 = gDataSet.getColValuesByName("secuAbbr");

        //为ListView 设置adapter
        lv = findViewById(R.id.lv_2);
        GKeyWizardAdapter myAdapter = new GKeyWizardAdapter(ListviewActivity.this,list1,list2);
        lv.setAdapter(myAdapter);

/*        //处理数据  ArrayAdapter
        List<String> codes = new ArrayList<>();
        List<String> names = new ArrayList<>();

        for(Object o : resultList){
            List<String> jsonObject11 = (List<String>)o;
            codes.add(jsonObject11.get(0));
            names.add(jsonObject11.get(1));
        }
        lv.setAdapter(new ArrayAdapter<String>(this,R.layout.layout_gkwizard,R.id.tv_code,codes));
        lv.setAdapter(new ArrayAdapter<String>(this,R.layout.layout_gkwizard,R.id.tv_name,names));*/



    }
}