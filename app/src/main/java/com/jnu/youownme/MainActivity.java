package com.jnu.youownme;

import android.content.Intent;
import android.net.sip.SipSession;
import android.net.sip.SipSession.Listener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.youownme.dataprocessor.DataBank;
import com.jnu.youownme.dataprocessor.Reason;
import com.jnu.youownme.dataprocessor.Record;
import com.jnu.youownme.dataprocessor.Type;
import com.jnu.youownme.ui.home.HomeFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_ADD_RECORD = 100;

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
                Intent intent;
                intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("data", DataBank.getSelectedDate().toString());
                intent.putExtra("position", -1);
                startActivityForResult(intent, REQUEST_CODE_ADD_RECORD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_CODE_ADD_RECORD:
                if(resultCode == RESULT_OK && data != null){
                    // 获取传递的数据
                    String name = data.getStringExtra("name");
                    String typeString = data.getStringExtra("type");
                    double money = data.getDoubleExtra("money", -1);
                    String reasonString = data.getStringExtra("reason");
                    int position = data.getIntExtra("position", -1);

                    DataBank.add(
                            new Record(Type.getType(typeString), DataBank.getSelectedDate(),
                                    money, Reason.getReason(reasonString), name));
                    DataBank.Save(this);

                    HomeFragment.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}