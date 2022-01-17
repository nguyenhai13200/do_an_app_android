package com.example.abong.Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.abong.Api.ApiService;
import com.example.abong.Fragment.AccountFragment;
import com.example.abong.R;
import com.example.abong.data_local.DataLocalManager;
import com.example.abong.modle.User;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangnhapActivity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView dangky;
    private EditText edtTaikhoan, edtMatkhau;
    private LinearLayout btnDangnhap;
    private String taikhoan, matkhau;
    private ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        AnhXa();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DangkyActivity.class);
                startActivity(intent);
            }
        });
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taikhoan = edtTaikhoan.getText().toString().trim();
                matkhau = edtMatkhau.getText().toString().trim();
                if (taikhoan.length() > 0 && matkhau.length() > 0){
                    ApiService.apiService.dangnhapUser(taikhoan,matkhau).enqueue(new Callback<ArrayList<User>>() {
                        @Override
                        public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                            if (response.body().size() != 0){
                                for (int i = 0; i < response.body().size(); i++){
                                    users.add(response.body().get(i));
                                }
                                User user = new User(users.get(0).getId(),users.get(0).getUsername(),users.get(0).getFullname(),users.get(0).getPassword(),users.get(0).getAvatar(),users.get(0).getAddress(),users.get(0).getPhone());
                                DataLocalManager.setUser(user);
                                MainActivity.isLogin = true;
                                MainActivity.viewPager2.setCurrentItem(0);
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(DangnhapActivity.this, "Sai toàn khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                            Toast.makeText(DangnhapActivity.this, "Lỗi máy chủ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(DangnhapActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        btnBack = findViewById(R.id.back);
        btnDangnhap = findViewById(R.id.btn_login_user);
        edtTaikhoan = findViewById(R.id.panel_username);
        edtMatkhau  = findViewById(R.id.panel_password);
        dangky = findViewById(R.id.btn_register);
    }
}