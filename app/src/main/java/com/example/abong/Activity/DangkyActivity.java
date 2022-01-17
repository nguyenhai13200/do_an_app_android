package com.example.abong.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.abong.Api.ApiService;
import com.example.abong.R;
import com.example.abong.adapter.ViewPager2Adapter;
import com.example.abong.modle.CartModle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangkyActivity extends AppCompatActivity {
    private EditText edtTaikhoan, edtFullname,edtMatkhau, edtMatkhau2, edtPhone;
    private LinearLayout btnDangky;
    private ImageView btnBack;
    private String taikhoan, hovaten, matkhau, matkhau2, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        AnhXa();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taikhoan = edtTaikhoan.getText().toString().trim();
                hovaten = edtFullname.getText().toString().trim();
                matkhau = edtMatkhau.getText().toString().trim();
                matkhau2 = edtMatkhau2.getText().toString().trim();
                phone = edtPhone.getText().toString().trim();
                if (taikhoan.length() >0 && hovaten.length() > 0 && matkhau.length() >0 && matkhau2.length() >0 && phone.length() >0){
                    if (matkhau.equals(matkhau2)){
                        ApiService.apiService.dangkyUser(taikhoan,hovaten,matkhau,phone).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String message = response.body();
                                if (message.equals("Success")){
                                    Toast.makeText(DangkyActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),DangnhapActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else if (message.equals("Failed")){
                                    Toast.makeText(DangkyActivity.this, "Tên tài khoản đã tồn tại vui lòng đăng ký tên tài khoản khác", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.e("ABD",t.getMessage());
                            }
                        });
                    }else{
                        Toast.makeText(DangkyActivity.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DangkyActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        edtTaikhoan = findViewById(R.id.panel_username);
        edtFullname = findViewById(R.id.panel_fullname);
        edtMatkhau = findViewById(R.id.panel_password);
        edtMatkhau2 = findViewById(R.id.panel_password2);
        edtPhone = findViewById(R.id.panel_phonenumber);
        btnDangky = findViewById(R.id.btn_register_user);
        btnBack = findViewById(R.id.back);
    }
}