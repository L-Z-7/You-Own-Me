package com.jnu.youownme.dataprocessor;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.youownme.R;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private GroupViewHolder groupViewHolder;
    private ChildViewHolder childViewHolder;
    // 用于存放Indicator的集合
    private SparseArray<ImageView> mIndicators;

    // 根据分组的展开闭合状态设置指示器
    public void setIndicatorState(int groupPosition, boolean isExpanded) {
        if (isExpanded) {
            mIndicators.get(groupPosition).setImageResource(R.mipmap.ic_expand);
        } else {
            mIndicators.get(groupPosition).setImageResource(R.mipmap.ic_collapse);
        }
    }

    public MyExpandableListAdapter(Context context) {
        this.context = context;
        mIndicators = new SparseArray<>();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.first_list_layout, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tv_group = convertView.findViewById(R.id.tv_group);
            groupViewHolder.iv_indicator = convertView.findViewById(R.id.iv_indicator);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        //设置显示数据
        groupViewHolder.tv_group.setText(DataBank.getYearGroup().get(groupPosition)+"");
        // 把位置和图标添加到Map
        mIndicators.put(groupPosition, groupViewHolder.iv_indicator);
        // 根据分组状态设置Indicator
        setIndicatorState(groupPosition, isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.second_list_layout, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_type = convertView.findViewById(R.id.text_type);
            childViewHolder.tv_reason = convertView.findViewById(R.id.text_reason);
            childViewHolder.tv_name = convertView.findViewById(R.id.text_name);
            childViewHolder.tv_money = convertView.findViewById(R.id.text_money);
            childViewHolder.tv_date = convertView.findViewById(R.id.text_date);

            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.tv_type.setText(
                DataBank.getYearChild().get(groupPosition).get(childPosition).getType().toString()
        );

        childViewHolder.tv_date.setText(
                DataBank.getYearChild().get(groupPosition).get(childPosition).getDate().toString()
        );

        childViewHolder.tv_reason.setText(
                DataBank.getYearChild().get(groupPosition).get(childPosition).getReason().toString()
        );

        childViewHolder.tv_name.setText(
                DataBank.getYearChild().get(groupPosition).get(childPosition).getName()
        );

        childViewHolder.tv_money.setText(
                "￥"+DataBank.getYearChild().get(groupPosition).get(childPosition).getMoney()
        );
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return DataBank.getYearGroup().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return DataBank.getYearChild().get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return DataBank.getYearGroup().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return DataBank.getYearChild().get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    class GroupViewHolder {
        TextView tv_group;
        ImageView iv_indicator;
    }

    class ChildViewHolder {
        TextView tv_type;
        TextView tv_date;
        TextView tv_reason;
        TextView tv_name;
        TextView tv_money;
    }
}