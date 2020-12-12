package com.jnu.youownme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.sip.SipSession;
import android.net.sip.SipSession.Listener;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.youownme.dataprocessor.DataBank;
import com.jnu.youownme.dataprocessor.Date;
import com.jnu.youownme.dataprocessor.Reason;
import com.jnu.youownme.dataprocessor.Record;
import com.jnu.youownme.dataprocessor.Type;
import com.jnu.youownme.ui.home.HomeFragment;
import com.jnu.youownme.ui.render.RenderFragment;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_render, R.id.navigation_home, R.id.navigation_receive)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // 悬浮按钮的响应函数
        FloatingActionButton button =  findViewById(R.id.floatingButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过对话框编辑
                showEditDialog(DataBank.getSelectedDate(), -1);

                // 通过另一个Activity编辑
//                Intent intent;
//                intent = new Intent(MainActivity.this, EditActivity.class);
//                intent.putExtra("data", DataBank.getSelectedDate().toString());
//                intent.putExtra("position", -1);
//                startActivityForResult(intent, REQUEST_CODE_ADD_RECORD);
            }
        });

        context = this;
    }

    private Type type;
    private Reason reason;
    private void showEditDialog(final Date date, int position){
        // 设置布局和标题
        AlertDialog.Builder editDialog =
                new AlertDialog.Builder(MainActivity.this);
        final View dialogView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.dialog_edit,null);
        editDialog.setTitle(date.toString());
        editDialog.setView(dialogView);

        // 获取组件
        Button buttonOK = (Button) findViewById(R.id.button_ok);
        Button buttonCancel = findViewById(R.id.button_cancel);
        final EditText editTextName = dialogView.findViewById(R.id.edit_text_name);
        final EditText editTextMoney = dialogView.findViewById(R.id.edit_text_money);
        Spinner spinnerReason = dialogView.findViewById(R.id.spinner_reason);
        Spinner spinnerType = dialogView.findViewById(R.id.spinner_type);

        // 修改视图
        Record record;
        if(position < 0){
            record = new Record();
        }else {
            record = DataBank.getDateRecords().get(position);
        }
        type = record.getType();
        reason = record.getReason();
        editTextName.setText(record.getName());
        editTextMoney.setText(record.getMoney()+"");

        SpinnerAdapter sAdapter = spinnerReason.getAdapter();
        int len = sAdapter.getCount();
        String str = record.getReason().toString();
        for (int i=0; i < len;  ++i){
            if(str.equals(sAdapter.getItem(i).toString())){
                spinnerReason.setSelection(i, true);
                break;
            }
        }

        sAdapter = spinnerType.getAdapter();
        len = sAdapter.getCount();
        str = record.getType().toString();
        for (int i=0; i < len;  ++i){
            if(str.equals(sAdapter.getItem(i).toString())){
                spinnerType.setSelection(i, true);
                break;
            }
        }

        // 随收礼列表选择
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String info = parent.getItemAtPosition(position).toString();    // 获取选中的文本
                type = Type.getType(info);  // 转换为type
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 原因列表选择
        spinnerReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String info = parent.getItemAtPosition(position).toString();    // 获取选中的文本
                reason = Reason.getReason(info);  // 转换为type
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 确定按钮的响应函数
        editDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBank.add(
                                new Record(type, date,
                                        Double.parseDouble(editTextMoney.getText().toString()),
                                        reason, editTextName.getText().toString()));
                        DataBank.Save(context);

                        HomeFragment.notifyDataSetChanged();
                        RenderFragment.notifyDataSetChanged();
                    }
                });
        editDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        // 显示对话框
        editDialog.show();
    }
}