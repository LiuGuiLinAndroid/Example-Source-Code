package com.liuguilin.tallybook.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.liuguilin.tallybook.R;
import com.liuguilin.tallybook.adapter.ListAdapter;

import java.util.ArrayList;

/*
 *  项目名：  TallyBook
 *  包名：    com.liuguilin.tallybook.ui
 *  文件名:   SettingActivity
 *  创建者:   LiuGuiLinAndroid
 *  创建时间:  2017/3/26 19:57
 *  描述：    设置
 */
public class SettingActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<String> mList = new ArrayList<>();
    private ListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("设置");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setElevation(0);
        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
