package com.liuguilin.tallybook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liuguilin.tallybook.R;
import com.liuguilin.tallybook.entity.CostModel;

import java.util.List;

/*
 *  项目名：  TallyBook
 *  包名：    com.liuguilin.tallybook.adapter
 *  文件名:   CostAdapter
 *  创建者:   LiuGuiLinAndroid
 *  创建时间:  2017/3/26 17:50
 *  描述：    数据适配器
 */
public class CostAdapter extends BaseAdapter {

    private Context mContext;
    private List<CostModel> mList;
    private LayoutInflater inflater;

    public CostAdapter(Context mContext, List<CostModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CostModel model = mList.get(position);

        viewHolder.tv_title.setText(model.getTitle());
        viewHolder.tv_date.setText(model.getDate());
        viewHolder.tv_money.setText(model.getMoney() );

        return convertView;
    }

    class ViewHolder {
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_money;
    }

}
