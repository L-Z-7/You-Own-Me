package com.jnu.youownme.ui.render;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jnu.youownme.R;
import com.jnu.youownme.dataprocessor.MyExpandableListAdapter;

public class RenderFragment extends Fragment {
    private static ExpandableListView elv;
    private static MyExpandableListAdapter adapter;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_render, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        context = getContext();

        elv = view.findViewById(R.id.elv);
        adapter = new MyExpandableListAdapter(context);
        elv.setAdapter(adapter);

        //设置一级列表的点击事件
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(groupPosition);
                if (groupExpanded) {
                    parent.collapseGroup(groupPosition);
                } else {
                    parent.expandGroup(groupPosition, true);
                }
                adapter.setIndicatorState(groupPosition, groupExpanded);
                return true;
            }
        });

        //设置二级列表的点击事件
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    // 供外部调用通知要更新了
    public static void notifyDataSetChanged(){
        if(adapter != null){
            adapter.notifyDataSetChanged();
            for (int i=0; i < adapter.getGroupCount(); ++i){
                elv.collapseGroup(i);
                elv.expandGroup(i);
            }
        }
    }
}