package com.liuguilin.universaladapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.liuguilin.universaladapter.adapter.MyAdapter;
import com.liuguilin.universaladapter.model.DataBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private MyAdapter myAdapter;
    private List<DataBean> mlist = new ArrayList<>();
    private String[] title = {"Hello", "World"};
    private String[] content = {"Hello你好", "World世界"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        for (int i = 0; i < title.length; i++) {
            DataBean bean = new DataBean();
            bean.setTitle(title[i]);
            bean.setContent(content[i]);
            mlist.add(bean);
        }
        myAdapter = new MyAdapter(this, mlist,R.layout.list_item);
        mListView.setAdapter(myAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
