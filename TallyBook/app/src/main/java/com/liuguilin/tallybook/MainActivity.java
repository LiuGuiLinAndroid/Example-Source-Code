package com.liuguilin.tallybook;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liuguilin.tallybook.adapter.CostAdapter;
import com.liuguilin.tallybook.db.DBHelper;
import com.liuguilin.tallybook.entity.CostModel;
import com.liuguilin.tallybook.ui.SettingActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private CostAdapter costAdapter;
    private List<CostModel> mList = new ArrayList<>();

    //数据库对象
    private DBHelper db;

    //悬浮按钮
    private FloatingActionButton mFab;
    //提示框
    private Dialog dialog;

    private EditText et_title;
    private EditText et_money;
    private Button btn_add_data;

    //没有数据
    private TextView tv_no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    //初始化数据
    private void initData() {
        db = new DBHelper(this);
        //查询
        Cursor cursor = db.queryAllData();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CostModel model = new CostModel();
                model.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                model.setDate(cursor.getString(cursor.getColumnIndex("date")));
                model.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                mList.add(model);
            }
        }
    }

    private void initView() {
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        mListView = (ListView) findViewById(R.id.mListView);
        //倒序
        Collections.reverse(mList);
        costAdapter = new CostAdapter(this, mList);
        mListView.setAdapter(costAdapter);
        mFab = (FloatingActionButton) findViewById(R.id.mFab);
        mFab.setOnClickListener(this);

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("账单详情")
                        .setMessage("说明：" + mList.get(position).getTitle() + "\n"
                                + "时间：" + mList.get(position).getDate() + "\n"
                                + "金额：" + mList.get(position).getMoney())
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //数据库的也删除
                                db.deleteData(mList.get(position).getDate());
                                mList.remove(position);
                                costAdapter.notifyDataSetChanged();

                                //没有数据
                                if (mList.size() == 0) {
                                    mListView.setVisibility(View.GONE);
                                    tv_no_data.setVisibility(View.VISIBLE);
                                }

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });

        //没有数据
        if (mList.size() == 0) {
            mListView.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mFab:
                dialog = new Dialog(this);
                dialog.setContentView(LayoutInflater.from(this).inflate(R.layout.dialog_item, null));
                et_title = (EditText) dialog.findViewById(R.id.et_title);
                et_money = (EditText) dialog.findViewById(R.id.et_money);
                btn_add_data = (Button) dialog.findViewById(R.id.btn_add_data);
                btn_add_data.setOnClickListener(this);
                //屏幕外点击无效
                //dialog.setCancelable(false);
                dialog.show();
                break;
            case R.id.btn_add_data:
                String title = et_title.getText().toString().trim();
                String money = et_money.getText().toString().trim();
                if (!TextUtils.isEmpty(title)) {
                    if (!TextUtils.isEmpty(money)) {
                        //当前时间
                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        CostModel model = new CostModel();
                        model.setTitle(title);
                        model.setMoney(money + " 元");
                        model.setDate(date);
                        //数据库添加
                        db.insertData(model);
                        //当前列表添加
                        mList.add(0, model);
                        //数据适配器
                        costAdapter.notifyDataSetChanged();
                        dialog.dismiss();

                        if (mListView.getVisibility() != View.VISIBLE) {
                            mListView.setVisibility(View.VISIBLE);
                        }
                        if (tv_no_data.getVisibility() != View.GONE) {
                            tv_no_data.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(this, "金额不能为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_clear_all:
                if (mList.size() != 0) {
                    new AlertDialog.Builder(this)
                            .setTitle("警告")
                            .setMessage("如果点击确定，将删除所有账单数据且不能恢复")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.deleteAllDate();
                                    mList.clear();
                                    costAdapter.notifyDataSetChanged();
                                    //没有数据
                                    if (mList.size() == 0) {
                                        mListView.setVisibility(View.GONE);
                                        tv_no_data.setVisibility(View.VISIBLE);
                                    }
                                }
                            })
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    Toast.makeText(this, "讨厌，你都还没有记账！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
