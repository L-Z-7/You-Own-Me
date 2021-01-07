package com.jnu.youownme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jnu.youownme.dataprocessor.DataBank;
import com.jnu.youownme.dataprocessor.Date;
import com.jnu.youownme.dataprocessor.Reason;
import com.jnu.youownme.dataprocessor.Record;
import com.jnu.youownme.dataprocessor.Type;

public class EditActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextMoney;
    private Spinner spinnerReason;
    private Spinner spinnerType;
    private TextView textViewData;

    private Type type = Type.getType("");
    private Reason reason = Reason.getReason("");
    private int position;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // 获取组件
        Button buttonOK = (Button) findViewById(R.id.button_ok);
        Button buttonCancel = findViewById(R.id.button_cancel);
        editTextName = EditActivity.this.findViewById(R.id.edit_text_name);
        editTextMoney = EditActivity.this.findViewById(R.id.edit_text_money);
        spinnerReason = EditActivity.this.findViewById(R.id.spinner_reason);
        spinnerType = EditActivity.this.findViewById(R.id.spinner_type);
        textViewData = EditActivity.this.findViewById(R.id.text_view_data);

        // 获取数据
        position = getIntent().getIntExtra("position", -1);
        date = new Date(getIntent().getStringExtra("data"));

        // 修改视图
        Record record;
        if(position < 0){
            record = new Record();
        }else {
            record = DataBank.getDateRecords().get(position);
        }
        textViewData.setText(date.toString());
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

        // 按钮的响应函数
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                // 回传数据
                intent.putExtra("postData", DataBank.getDateRecords().get(position).toString());
                intent.putExtra("name", editTextName.getText().toString());
                intent.putExtra("money", Double.parseDouble(editTextMoney.getText().toString()));
                intent.putExtra("reason", reason.toString());
                intent.putExtra("type", type.toString());
                intent.putExtra("position", position);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}