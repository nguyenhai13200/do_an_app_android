package com.example.abong.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.abong.Api.ApiService;
import com.example.abong.R;
import com.example.abong.data_local.DataLocalManager;
import com.example.abong.modle.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoimatkhauActivity extends AppCompatActivity {
    private ImageView back;
    private EditText matkhaucu, matkhaumoi, matkhaumoi2;
    private LinearLayout btnSetPassNew;
    private String taikhoan, passold, passnew, passnew2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);
        
        AnhXa();
        initDoimatkhau();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initDoimatkhau() {
        btnSetPassNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = DataLocalManager.getUser();
                taikhoan = user.getUsername();
                passold = matkhaucu.getText().toString().trim();
                passnew = matkhaumoi.getText().toString().trim();
                passnew2 = matkhaumoi2.getText().toString().trim();
                if (passold.length() > 0 && passnew.length() >0 && passnew2.length() >0){
                    if (passnew.equals(passnew2)){
                        ApiService.apiService.doimatkhau(taikhoan,passold,passnew).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String message = response.body();
                                if (message.equals("Success")){
                                    Toast.makeText(DoimatkhauActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else if (message.equals("Fail")){
                                    Toast.makeText(DoimatkhauActivity.this, "Sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(DoimatkhauActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(DoimatkhauActivity.this, "Mật khẩu mới không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(DoimatkhauActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        back = findViewById(R.id.back);
        matkhaucu = findViewById(R.id.panel_oldpassword);
        matkhaumoi = findViewById(R.id.panel_passwordnew);
        matkhaumoi2 = findViewById(R.id.panel_passwordnew2);
        btnSetPassNew = findViewById(R.id.btn_setpasswordnew);
    }
}