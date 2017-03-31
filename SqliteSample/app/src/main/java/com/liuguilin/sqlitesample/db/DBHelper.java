package com.liuguilin.sqlitesample.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.liuguilin.sqlitesample.entity.Constans;

/*
 *  项目名：  SqliteSample 
 *  包名：    com.liuguilin.sqlitesample.db
 *  文件名:   DBHelper
 *  创建者:   刘某人程序员
 *  创建时间:  2017/3/28 21:23
 *  描述：    数据库操作
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, Constans.DB_NAME, null, Constans.DB_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * 数据库对象创建
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        String sql = "create table " + Constans.TAB_NAME + "(_id Integer primary key,name varchar(10),age Integer)";
        //执行sql语句
        db.execSQL(sql);
    }

    /**
     * 版本更新的调用
     *
     * @param db         数据库对象
     * @param oldVersion 版本
     * @param newVersion 新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
