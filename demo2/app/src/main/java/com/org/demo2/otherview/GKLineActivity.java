package com.org.demo2.otherview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.org.demo2.R;

public class GKLineActivity extends AppCompatActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_k_line);

        listview = findViewById(R.id.lv_1);

        listview.setAdapter(new GKLineAdapter(GKLineActivity.this));
    }

}