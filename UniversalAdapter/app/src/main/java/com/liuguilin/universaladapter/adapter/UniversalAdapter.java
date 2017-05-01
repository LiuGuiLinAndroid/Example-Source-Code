package com.liuguilin.universaladapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.liuguilin.universaladapter.holder.ViewHolder;

import java.util.List;


/*
 *  项目名：  UniversalAdapter 
 *  包名：    com.liuguilin.universaladapter.adapter
 *  文件名:   UniversalAdapter
 *  创建者:   刘某人程序员
 *  创建时间:  2017/5/1 19:00
 *  描述：    万能的适配器
 */
public abstract class UniversalAdapter<T> extends BaseAdapter {

    /**
     * 焦点抢占了怎么办？
     * 1.focusable = false
     * 2.descendantFocusability = blocksDescendants
     * <p>
     * 控件复用了怎么办？
     * 1.private boolean isCheck;
     * 2.if cb.ischeck new List.add(holder.getPosition) else remove(Integer)
     * 3.xxx
     */

    protected Context mContext;
    protected List<T> mList;
    protected LayoutInflater inflater;
    private int layoutId;

    public UniversalAdapter(Context mContext, List<T> mList,int layoutId) {
        this.mContext = mContext;
        this.mList = mList;
        this.layoutId = layoutId;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, position, layoutId);
        convert(holder, getItem(position));
        return holder.getmConvertView();
    }

    //对外方法
    public abstract void convert(ViewHolder holder, T t);
}
