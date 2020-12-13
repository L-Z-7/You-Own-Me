package com.jnu.youownme.ui.receive;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import com.jnu.youownme.dataprocessor.Reason;
import com.jnu.youownme.dataprocessor.Record;
import com.jnu.youownme.ui.home.HomeFragment;

import java.util.Calendar;
import java.util.List;

public class ReceiveFragment extends Fragment {
    private Context context;
    private static DataAdapter adapter;
    private Spinner spinnerReason;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
        spinnerReason = view.findViewById(R.id.spinner_reason);

        // 修改显示的Reason
        SpinnerAdapter sAdapter = spinnerReason.getAdapter();
        int len = sAdapter.getCount();
        String str = DataBank.getSelectedReason().toString();
        for (int i=0; i < len;  ++i){
            if(str.equals(sAdapter.getItem(i).toString())){
                spinnerReason.setSelection(i, true);
                break;
            }
        }

        // 设置 List View 的适配器
        adapter = new DataAdapter(context, R.layout.list_item, (List<Record>)DataBank.getReasonRecords());
        ListView listViewBooks = ((ListView) view.findViewById(R.id.list_view_data));
        listViewBooks.setAdapter(adapter);

        // 标题栏下拉框响应函数
        spinnerReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String info = parent.getItemAtPosition(position).toString();    // 获取选中的文本
                DataBank.setSelectedReason(Reason.getReason(info));  // 转换为type
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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