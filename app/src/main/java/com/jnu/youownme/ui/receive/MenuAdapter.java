package com.jnu.youownme.ui.receive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<? extends Map<String, ?>> mData;
    //Map中的key
    private String[] mFrom;
    //view的id
    private int[] mTo;

    public MenuAdapter(Context context, List<? extends Map<String, ?>> data,
                                        int resource, String[] from, int[] to){
        this.context = context;
        mData = data;
        mResource = resource;
        mFrom = from;
        mTo = to;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MenuAdapter.ViewHolder holder;
        if(convertView == null){
            //使用自定义的list_items作为Layout
            convertView = LayoutInflater.from(context).inflate(mResource, parent, false);
            //使用减少findView的次数
            holder = new MenuAdapter.ViewHolder();
            holder.tvReasonItem = ((TextView) convertView.findViewById(mTo[0]));

            //设置标记
            convertView.setTag(holder);
        }else{
            holder = (MenuAdapter.ViewHolder) convertView.getTag();
        }
        //设置数据
        final Map<String, ?> dataSet = mData.get(position);
        if (dataSet == null) {
            return null;
        }
        //获取该行数据
        final Object tvReasonItem = dataSet.get(mFrom[0]);

        holder.tvReasonItem.setText(tvReasonItem.toString());

        return convertView;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView tvReasonItem;
    }
}
