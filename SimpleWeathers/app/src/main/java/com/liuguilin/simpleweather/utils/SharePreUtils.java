package com.liuguilin.simpleweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
 *项目名： SimpleWeather
 *包名：   com.liuguilin.simpleweather.utils
 *文件名:  SharePreUtils
 *创建者:  LGL
 *创建时间:2016/11/2423:58
 *描述:    SharedPreferences封装
 */
public class SharePreUtils {

    public static final String SHARE_NAME = "config";

    public static void putString(Context mContext, String key, String values) {
        SharedPreferences sp = mContext.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, values).commit();
    }

    public static String getString(Context mContext, String key, String values) {
        SharedPreferences sp = mContext.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, values);
    }

}
