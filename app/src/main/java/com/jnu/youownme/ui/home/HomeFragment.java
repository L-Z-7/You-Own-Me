package com.jnu.youownme.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.jnu.youownme.EditActivity;
import com.jnu.youownme.R;
import com.jnu.youownme.dataprocessor.DataBank;
import com.jnu.youownme.dataprocessor.Date;
import com.jnu.youownme.dataprocessor.Reason;
import com.jnu.youownme.dataprocessor.Record;
import com.jnu.youownme.dataprocessor.Type;

import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment {
    private CalendarView calendarView;
    static private DataAdapter adapter;
    static private Context context;
    private final int CONTEXT_MENU_ITEM_DELETE = 435;
    private final int CONTEXT_MENU_ITEM_UPDATE = CONTEXT_MENU_ITEM_DELETE + 1;
    private final int REQUEST_CODE_CHANGE = 809;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initData(view);
        initView(view);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData(View view){
        context = this.getContext();

        DataBank.Load(context);

        // 设置当前日期
        Calendar calendar = Calendar.getInstance();//取得当前时间的年月日 时分秒
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DataBank.setSelectedDate(new Date(year, month, day));
        DataBank.Save(context);
    }

    private void initView(View view){
        calendarView = view.findViewById(R.id.calenderView);

        // 日历组件添加监视器
        myDateChangeListener listener = new myDateChangeListener();
        listener.setView(view);
        calendarView.setOnDateChangeListener(listener);

        // 设置 List View 的适配器
        List<Record> tmp = (List<Record>)DataBank.getReasonRecords();
        List<Record> tmp2 = (List<Record>)DataBank.getReasonRecords();
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
            ((TextView) view.findViewById(R.id.text_money)).setText("￥"+records.getMoney());

            return view;
        }
    }

    // 日历的监视器
    public class myDateChangeListener implements CalendarView.OnDateChangeListener {
        private View mView;

        public void setView(View view){
            mView = view;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            // 修改当前日期
            DataBank.setSelectedDate(new Date(year, month, dayOfMonth));
            DataBank.Save(context);

            notifyDataSetChanged();
        }
    }

    // 供外部调用通知要更新了
    public static void notifyDataSetChanged(){
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if(v == this.getActivity().findViewById(R.id.list_view_data)){
            menu.add(1, CONTEXT_MENU_ITEM_DELETE,1,"删除");
            menu.add(1, CONTEXT_MENU_ITEM_UPDATE,1,"修改");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo;
        Intent intent;

        menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position=menuInfo.position;


        switch (item.getItemId()){
            case CONTEXT_MENU_ITEM_UPDATE:
                intent = new Intent(context, EditActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("data", DataBank.getSelectedDate().toString());
                startActivityForResult(intent, REQUEST_CODE_CHANGE);
                break;
            case CONTEXT_MENU_ITEM_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle(getResources().getString(R.string.context_menu_item_delete_title));
                builder.setMessage(
                        getResources().getString(R.string.context_menu_item_delete_ask));
                builder.setCancelable(true);
                builder.setPositiveButton(getResources().getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataBank.remove(position);
                                DataBank.Save(context);
                                adapter.notifyDataSetChanged();
                            }
                        });  //正面的按钮（肯定）
                builder.setNegativeButton(getResources().getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        }); //反面的按钮（否定)
                builder.create().show();
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case REQUEST_CODE_CHANGE:
                if(resultCode == RESULT_OK && data != null){
                    // 获取传递的数据
                    String name = data.getStringExtra("name");
                    String typeString = data.getStringExtra("type");
                    double money = data.getDoubleExtra("money", -1);
                    String reasonString = data.getStringExtra("reason");
                    int position = data.getIntExtra("position", -1);
                    String postData = data.getStringExtra("postData");

                    DataBank.change(postData,
                            new Record(Type.getType(typeString), DataBank.getSelectedDate(),
                                    money, Reason.getReason(reasonString), name));
                    DataBank.Save(context);

                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}