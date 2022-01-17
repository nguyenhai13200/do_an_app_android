package com.example.abong.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.abong.Api.ApiService;
import com.example.abong.R;
import com.example.abong.data_local.DataLocalManager;
import com.example.abong.modle.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuaThongTinThanhToanActivity extends AppCompatActivity {
    private EditText hovaten, sodienthoai, diachi;
    private ImageView back;
    private AppCompatButton btnThaydoi;
    private String username, fullname, phone, address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suathongtin);

        AnhXa();

        User user = DataLocalManager.getUser();
        hovaten.setText(user.getFullname());
        sodienthoai.setText(user.getPhone());
        diachi.setText(user.getAddress());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnThaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = user.getUsername();
                fullname = hovaten.getText().toString().trim();
                phone = sodienthoai.getText().toString().trim();
                address = diachi.getText().toString().trim();
                ApiService.apiService.suadiachi(username,fullname,phone,address).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String message = response.body();
                        if (message.equals("Success")){
                            User user1 = new User(user.getId(),user.getUsername(),fullname,user.getPassword(),user.getAvatar(),address,phone);
                            DataLocalManager.setUser(user1);
                            ThanhtoanActivity.hoten.setText(user1.getFullname());
                            ThanhtoanActivity.sodienthoai.setText(user1.getPhone());
                            ThanhtoanActivity.diachi.setText(user1.getAddress());
                            finish();
                        }else {
                            Toast.makeText(SuaThongTinThanhToanActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
//                        Log.e("DIA",t.getMessage());
                        Toast.makeText(SuaThongTinThanhToanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void AnhXa() {
        hovaten = findViewById(R.id.edt_fullname);
        sodienthoai = findViewById(R.id.edt_phonenumber);
        diachi = findViewById(R.id.edt_address);
        btnThaydoi = findViewById(R.id.btn_thaydoi);
        back = findViewById(R.id.back);
    }
}