package com.liuguilin.universaladapter.adapter;

import android.content.Context;

import com.liuguilin.universaladapter.R;
import com.liuguilin.universaladapter.holder.ViewHolder;
import com.liuguilin.universaladapter.model.DataBean;

import java.util.List;

/*
 *  项目名：  UniversalAdapter 
 *  包名：    com.liuguilin.universaladapter.adapter
 *  文件名:   UniversalAdapter
 *  创建者:   刘某人程序员
 *  创建时间:  2017/5/1 18:28
 *  描述：    万能的Adapter
 */
public class MyAdapter extends UniversalAdapter<DataBean> {

    public MyAdapter(Context context, List<DataBean> mList,int layoutId) {
        super(context, mList,layoutId);
    }

    @Override
    public void convert(ViewHolder holder, DataBean dataBean) {
        holder.setText(R.id.tv_title, dataBean.getTitle()).setText(R.id.tv_content, dataBean.getContent());
        //holder.getView(R.id.tv_title);
    }
}

