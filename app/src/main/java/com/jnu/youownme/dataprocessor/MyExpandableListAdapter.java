package com.jnu.youownme.dataprocessor;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jnu.youownme.R;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private GroupViewHolder groupViewHolder;
    private ChildViewHolder childViewHolder;

    public MyExpandableListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.first_list_layout, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tv_group = convertView.findViewById(R.id.tv_group);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        //设置显示数据
        groupViewHolder.tv_group.setText(DataBank.getYearGroup().get(groupPosition)+"");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.second_list_layout, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_child = convertView.findViewById(R.id.tv_child);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tv_child.setText(
                DataBank.getYearChild().get(groupPosition).get(childPosition).toString()
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

    class GroupViewHolder {
        TextView tv_group;
    }

    class ChildViewHolder {
        TextView tv_child;
    }
}