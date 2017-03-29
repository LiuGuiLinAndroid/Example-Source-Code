package com.liuguilin.simpleweather.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.liuguilin.simpleweather.R;
import com.liuguilin.simpleweather.adapter.ListAdapter;

import java.util.ArrayList;

/*
 *项目名： SimpleWeather
 *包名：   com.liuguilin.simpleweather.ui
 *文件名:  AboutActivity
 *创建者:  LGL
 *创建时间:2016/11/251:22
 *描述:    关于
 */
public class AboutActivity extends BaseActivity {

    private ListView mListView;
    private ArrayList<String> mList = new ArrayList<>();
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);

        mList.add("作者:刘某人程序员");
        mList.add("QQ群:555974449");
        mList.add("博客:http://blog.csdn.net/qq_26787115");
        mList.add("GitHub:https://github.com/LiuGuiLinAndroid");

        mAdapter = new ListAdapter(this, mList);
        mListView.setAdapter(mAdapter);

    }
}
