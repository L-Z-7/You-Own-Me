package com.jnu.youownme.ui.receive;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jnu.youownme.R;
import com.jnu.youownme.dataprocessor.DataBank;
import com.jnu.youownme.dataprocessor.Date;
import com.jnu.youownme.dataprocessor.Record;
import com.jnu.youownme.ui.home.HomeFragment;

import java.util.Calendar;
import java.util.List;

public class ReceiveFragment extends Fragment {
    private Context context;
    private ReceiveViewModel dashboardViewModel;
    private static DataAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(ReceiveViewModel.class);
        View view = inflater.inflate(R.layout.fragment_receive, container, false);

        initData(view);
        initView(view);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData(View view){
        context = this.getContext();
    }

    private void initView(View view){
        // 设置 List View 的适配器
        adapter = new DataAdapter(context, R.layout.list_item, (List<Record>)DataBank.getDateRecords());
        ListView listViewBooks = ((ListView) view.findViewById(R.id.list_view_data));
        listViewBooks.setAdapter(adapter);

        // 注册菜单
        this.registerForContextMenu(listViewBooks);
    }

    // list view的适配器
    private class DataAdapter extends ArrayAdapter<Record> {
        private int resourceId;

        public DataAdapter(@NonNull Context context, int resource, @NonNull List<Record> objects) {
            super(context, resource, objects);
            this.resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Record records = getItem(position);
            View view;
            if(null == convertView)
                view = LayoutInflater.from(getContext()).inflate(this.resourceId, parent, false);
            else
                view = convertView;

            // 设置5个内容
            ((TextView) view.findViewById(R.id.text_type)).setText(records.getType().toString());
            ((TextView) view.findViewById(R.id.text_name)).setText(records.getName());
            ((TextView) view.findViewById(R.id.text_date)).setText(records.getDate().toString());
            ((TextView) view.findViewById(R.id.text_reason)).setText(records.getReason().toString());
            ((TextView) view.findViewById(R.id.text_money)).setText(records.getMoney()+"");

            return view;
        }
    }

    // 供外部调用通知要更新了
    public static void notifyDataSetChanged(){
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }
}