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
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.jnu.youownme.R;
import com.jnu.youownme.dataprocessor.DataBank;
import com.jnu.youownme.dataprocessor.Reason;
import com.jnu.youownme.dataprocessor.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiveFragment extends Fragment {
    private Context context;
    private static DataAdapter adapter;
//    private Spinner spinnerReason;
    private TextView tvReason;
    private PopupWindow mPopWindow;
    private List<Map<String, Object>> reasonList = null;

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
//        spinnerReason = view.findViewById(R.id.spinner_reason);

//        // 修改显示的Reason
//        SpinnerAdapter sAdapter = spinnerReason.getAdapter();
//        int len = sAdapter.getCount();
//        String str = DataBank.getSelectedReason().toString();
//        for (int i=0; i < len;  ++i){
//            if(str.equals(sAdapter.getItem(i).toString())){
//                spinnerReason.setSelection(i, true);
//                break;
//            }
//        }

        // 设置 List View 的适配器
        List<Record> tmp = (List<Record>)DataBank.getReasonRecords();
        adapter = new DataAdapter(context, R.layout.list_item, (List<Record>)DataBank.getReasonRecords());
        ListView listViewBooks = ((ListView) view.findViewById(R.id.list_view_data));
        listViewBooks.setAdapter(adapter);

        tvReason = view.findViewById(R.id.text_view_reason);
        tvReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

//        // 标题栏下拉框响应函数
//        spinnerReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String info = parent.getItemAtPosition(position).toString();    // 获取选中的文本
//                DataBank.setSelectedReason(Reason.getReason(info));  // 转换为type
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_menu, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT, true);
        mPopWindow.setContentView(contentView);

        //获取实例，设置各个控件的点击响应
        //注意：PopupWindow中各个控件的所在的布局是contentView，而不是在Activity中，所以，要在findViewById(R.id.tv)前指定根布局
        ListView lvReason = (ListView)contentView.findViewById(R.id.list_view_reason);
        // 列表加载
        reasonList = new ArrayList<Map<String, Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        for(Reason reason: Reason.values())
        {
            map = new HashMap<String, Object>();
            map.put("tvReasonItem", reason.toString());
            reasonList.add(map);
        }
        final MenuAdapter menuAdapter =
                new MenuAdapter(
                        context, reasonList, R.layout.popup_menu_item,
                        new String[] { "tvReasonItem"}, new int[] { R.id.tvReasonItem});
        lvReason.setAdapter(menuAdapter);

        lvReason.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Reason reason;
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reason = Reason.values()[position];
                DataBank.setSelectedReason(reason);
            }
        });
//        ClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                TextView tx = (TextView)v;
//                DataBank.setSelectedReason(Reason.getReason(tx.getText().toString()));
//                DataBank.Update();
//            }
//        });

//        //解决5.0以下版本点击外部不消失问题
//        mPopWindow.setOutsideTouchable(true);
//        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        //显示方式
        mPopWindow.showAsDropDown(lvReason);
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
            List<Record> tmp = (List<Record>)DataBank.getReasonRecords();
            String s = "";
            s.equals("");
        }
    }
}