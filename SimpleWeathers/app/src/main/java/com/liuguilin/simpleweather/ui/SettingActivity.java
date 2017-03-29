package com.liuguilin.simpleweather.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.liuguilin.simpleweather.R;
import com.liuguilin.simpleweather.adapter.ListAdapter;

import java.util.ArrayList;

/*
 *项目名： SimpleWeather
 *包名：   com.liuguilin.simpleweather.ui
 *文件名:  SettingActivity
 *创建者:  LGL
 *创建时间:2016/11/250:49
 *描述:    设置
 */
public class SettingActivity extends BaseActivity {

    private ListView mListView;
    private ArrayList<String> mList;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        Intent intent = getIntent();
        mList = intent.getStringArrayListExtra("info");

        mList.add("版本号：" + getVersion());
        mList.add("意见反馈");
        mList.add("关于");

        mAdapter = new ListAdapter(this, mList);
        mListView.setAdapter(mAdapter);

        //设置监听
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 7:
                        startActivity(new Intent(SettingActivity.this,BackActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(SettingActivity.this,AboutActivity.class));
                        break;
                }
            }
        });
    }

    //获取版本号
    private String getVersion() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "无法获取版本号";
        }
    }
}
