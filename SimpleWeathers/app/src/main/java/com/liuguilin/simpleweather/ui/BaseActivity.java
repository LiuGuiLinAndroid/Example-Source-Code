package com.liuguilin.simpleweather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/*
 *项目名： SimpleWeather
 *包名：   com.liuguilin.simpleweather.ui
 *文件名:  BaseActivity
 *创建者:  LGL
 *创建时间:2016/11/250:47
 *描述:    Activity基类
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //去掉阴影
        getSupportActionBar().setElevation(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
