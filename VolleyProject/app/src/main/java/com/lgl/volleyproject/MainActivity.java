package com.lgl.volleyproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //列表
    private ListView mListView;
    //数据源
    private ArrayAdapter<String>adapter;
    //数据
    private String [] mData = {"归属地查询","QQ测试吉凶","天气预报"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        mListView = (ListView) findViewById(R.id.mListView);

        //初始化数据源
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mData);

        //设置数据源
        mListView.setAdapter(adapter);

        //列表的点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    startActivity(new Intent(MainActivity.this,CallActivity.class));
                }else if(position == 1){
                    startActivity(new Intent(MainActivity.this,QQActivity.class));
                }else if(position == 2){
                    startActivity(new Intent(MainActivity.this,WeatherActivity.class));

                }
            }
        });

    }
}
