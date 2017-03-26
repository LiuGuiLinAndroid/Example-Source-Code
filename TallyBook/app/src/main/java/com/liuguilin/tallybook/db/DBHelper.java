package com.liuguilin.tallybook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liuguilin.tallybook.entity.CostModel;

import static android.R.attr.id;

/*
 *  项目名：  TallyBook
 *  包名：    com.liuguilin.tallybook.db
 *  文件名:   dbHelper
 *  创建者:   LiuGuiLinAndroid
 *  创建时间:  2017/3/26 18:05
 *  描述：    数据库操作
 */
public class DBHelper extends SQLiteOpenHelper {

    //数据库的名称
    private static final String DB_NAME = "liuguilin_tally_book";
    //数据库的版本
    private static final int DB_VERSION = 1;
    //表名
    private static final String TABLE_NAME = "tally_book";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库的语句 表名：  四个字段：id title date money
        db.execSQL("create table if not exists " + TABLE_NAME + "(id integer primary key, title varchar, date varchar, money varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //添加数据
    public void insertData(CostModel model) {
        //获取写入数据库
        SQLiteDatabase database = getWritableDatabase();
        //键值对
        ContentValues values = new ContentValues();
        //存入数据到键值对
        values.put("title", model.getTitle());
        values.put("date", model.getDate());
        values.put("money", model.getMoney());
        //插入数据库
        database.insert(TABLE_NAME, null, values);
    }

    //查询所有数据
    public Cursor queryAllData() {
        //获取写入数据库
        SQLiteDatabase database = getWritableDatabase();
        //顺序排列
        return database.query(TABLE_NAME, null, null, null, null, null, "date ASC");
    }

    //删除数据
    public void deleteData(String date) {
        //获取写入数据库
        SQLiteDatabase database = getWritableDatabase();
        String sql = "delete from " + TABLE_NAME + " where date=?";
        database.execSQL(sql, new Object[]{date});
    }

    //删除所有数据
    public void deleteAllDate() {
        //获取写入数据库
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
    }
}
