package com.liuguilin.sqlitesample;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liuguilin.sqlitesample.db.DBHelper;
import com.liuguilin.sqlitesample.entity.Constans;
import com.liuguilin.sqlitesample.manager.DBManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_create;
    private DBHelper dbHelper;
    private Button btn_add;
    private Button btn_delete;
    private Button btn_change;
    private SQLiteDatabase sqLiteDatabase;
    private Button btn_api_add;
    private Button btn_api_delete;
    private Button btn_api_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        dbHelper = DBManager.getInstance(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        btn_create = (Button) findViewById(R.id.btn_create);
        btn_create.setOnClickListener(this);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        btn_change = (Button) findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);
        btn_api_add = (Button) findViewById(R.id.btn_api_add);
        btn_api_add.setOnClickListener(this);
        btn_api_delete = (Button) findViewById(R.id.btn_api_delete);
        btn_api_delete.setOnClickListener(this);
        btn_api_change = (Button) findViewById(R.id.btn_api_change);
        btn_api_change.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create:
                break;
            case R.id.btn_add:
                String sql = "insert into " + Constans.TAB_NAME + " values(1,'张三',20)";
                DBManager.execSQL(sqLiteDatabase, sql);
                sqLiteDatabase.close();
                break;
            case R.id.btn_delete:
                String sql_delete = "delete from " + Constans.TAB_NAME + " where _id 2";
                DBManager.execSQL(sqLiteDatabase, sql_delete);
                sqLiteDatabase.close();
                break;
            case R.id.btn_change:
                String sql_change = "update " + Constans.TAB_NAME + " set name = 'xiaoming' where _id = 1";
                DBManager.execSQL(sqLiteDatabase, sql_change);
                sqLiteDatabase.close();
                break;
            case R.id.btn_api_add:
                /**
                 * 表名
                 * null
                 * 集合
                 * 返回：表示插入数据的列数
                 */
                ContentValues values = new ContentValues();
                values.put("_id", 3);
                values.put("name", "lisi");
                values.put("age", 30);
                long result = sqLiteDatabase.insert(Constans.TAB_NAME, null, values);
                if (result > 0) {
                    Toast.makeText(this, "插入数据成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "插入数据失败", Toast.LENGTH_SHORT).show();
                }
                sqLiteDatabase.close();
                break;
            case R.id.btn_api_delete:
                /**
                 * 表名
                 * 删除的条件
                 * 删除条件的占位符
                 */
                int dele = sqLiteDatabase.delete(Constans.TAB_NAME,"_id=1",null);
                if (dele > 0) {
                    Toast.makeText(this, "删除数据成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "删除数据失败", Toast.LENGTH_SHORT).show();
                }
                sqLiteDatabase.close();
                break;
            case R.id.btn_api_change:
                /**
                 * 表名
                 * 键值对
                 * 当前修改的条件
                 * 修改条件的占位符
                 */
                ContentValues content = new ContentValues();
                content.put("name", "wangwu");
                int count = sqLiteDatabase.update(Constans.TAB_NAME, content, "_id=1", null);
                if (count > 0) {
                    Toast.makeText(this, "修改数据成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "修改数据失败", Toast.LENGTH_SHORT).show();
                }
                sqLiteDatabase.close();
                break;
        }
    }
}
