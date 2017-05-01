package com.liuguilin.universaladapter.holder;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*
 *  项目名：  UniversalAdapter 
 *  包名：    com.liuguilin.universaladapter.holder
 *  文件名:   ViewHolder
 *  创建者:   刘某人程序员
 *  创建时间:  2017/5/1 12:14
 *  描述：    TODO
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context context;

    //构造方法
    public ViewHolder(Context context, ViewGroup parent, int position, int layoutId) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        this.context = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    //获取Viewholder
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int position, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(context, parent, position, layoutId);
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
            return viewHolder;
        }
    }

    public View getmConvertView() {
        return mConvertView;
    }

    //获取View
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    //对外提供View方法
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageRes(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setBackgroundResource(resId);
        return this;
    }

    public ViewHolder setImageUrl(int viewId, String url) {
        ImageView iv = getView(viewId);
        //Glide.xxx(context,url);
        return this;
    }
}
