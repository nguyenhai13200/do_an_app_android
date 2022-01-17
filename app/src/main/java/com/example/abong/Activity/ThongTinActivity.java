package com.example.abong.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abong.R;
import com.example.abong.data_local.DataLocalManager;
import com.example.abong.modle.User;

public class ThongTinActivity extends AppCompatActivity {
    public static TextView fullname, phone, address;
    private ImageView back;
    private AppCompatButton btnThayDoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);

        AnhXa();
        User user = DataLocalManager.getUser();
        fullname.setText(user.getFullname());
        phone.setText(user.getPhone());
        address.setText(user.getAddress());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SuaThongTinActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        fullname = findViewById(R.id.txt_fullname);
        phone = findViewById(R.id.txt_phone);
        address = findViewById(R.id.txt_address);
        btnThayDoi =findViewById(R.id.btn_thaydoi);
        back = findViewById(R.id.back);
    }
}