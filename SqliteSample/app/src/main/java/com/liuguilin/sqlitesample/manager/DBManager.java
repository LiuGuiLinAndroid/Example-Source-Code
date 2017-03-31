package com.liuguilin.sqlitesample.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.liuguilin.sqlitesample.db.DBHelper;

/*
 *  项目名：  SqliteSample 
 *  包名：    com.liuguilin.sqlitesample.manager
 *  文件名:   DBManager
 *  创建者:   刘某人程序员
 *  创建时间:  2017/3/30 21:16
 *  描述：    TODO
 */
public class DBManager {

    private static DBHelper dbHelper;

    public static DBHelper getInstance(Context mContext) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(mContext);
        }
        return dbHelper;
    }

    //根据SQL语句在数据库中执行语句
    public static void execSQL(SQLiteDatabase db, String sql) {
        if (db != null) {
            if (!TextUtils.isEmpty(sql)) {
                db.execSQL(sql);
            }
        }
    }

}
