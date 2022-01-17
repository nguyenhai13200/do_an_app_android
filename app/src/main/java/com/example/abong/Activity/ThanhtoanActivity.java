package com.example.abong.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abong.Api.ApiService;
import com.example.abong.R;
import com.example.abong.data_local.DataLocalManager;
import com.example.abong.modle.User;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhtoanActivity extends AppCompatActivity {
    public static TextView hoten, sodienthoai, diachi, tongtiensp, tongtientt;
    private LinearLayout panel_thongtin, dathang;
    private CheckBox cbCham, cbNhanh;
    private ImageView back;
    private String hinhthuc;
    User user = DataLocalManager.getUser();
    private long tongtien, tongtienthanhtoan = tongtien;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);

        AnhXa();
        ThongTinNguoiMua();
        eventButton();
    }

    private void eventButton() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cbCham.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){ //bỏ check nhanh
                    cbNhanh.setChecked(false);
                    tongtienthanhtoan = tongtien + 10000;
                    tongtientt.setText(decimalFormat.format(tongtienthanhtoan) + "đ");
                }else {
                    tongtienthanhtoan = tongtienthanhtoan - 10000;
                    tongtientt.setText(decimalFormat.format(tongtienthanhtoan) + "đ");
                }
            }
        });
        cbNhanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    cbCham.setChecked(false);
                    tongtienthanhtoan = tongtien + 20000;
                    tongtientt.setText(decimalFormat.format(tongtienthanhtoan) + "đ");
                }else {
                    tongtienthanhtoan = tongtienthanhtoan - 20000;
                    tongtientt.setText(decimalFormat.format(tongtienthanhtoan) + "đ");
                }
            }
        });
        panel_thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThanhtoanActivity.this, SuaThongTinThanhToanActivity.class);
                startActivity(intent);
            }
        });
        dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (diachi.length() > 0){
                    if (cbCham.isChecked()){
                        hinhthuc = "Vận chuyển chậm";
                        ApiService.apiService.insertDonhang(user.getId(),tongtienthanhtoan,hinhthuc,new Gson().toJson(MainActivity.manggiohang)).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String message = response.body();
                                if (message.equals("Success")){
                                    Toast.makeText(getApplicationContext(), "Bạn đã đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                    MainActivity.manggiohang.clear();
                                    MainActivity.viewPager2.setCurrentItem(0);
                                    finish();
                                }else {
                                    Toast.makeText(ThanhtoanActivity.this, "Có vẻ như việc đặt hàng đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(ThanhtoanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (cbNhanh.isChecked()){
                        hinhthuc = "Vận chuyển nhanh";
                        ApiService.apiService.insertDonhang(user.getId(),tongtienthanhtoan,hinhthuc,new Gson().toJson(MainActivity.manggiohang)).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String message = response.body();
                                if (message.equals("Success")){
                                    Toast.makeText(getApplicationContext(), "Bạn đã đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                    MainActivity.manggiohang.clear();
                                    MainActivity.setCountItemInCart(0);

                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    MainActivity.viewPager2.setCurrentItem(0);
                                    startActivity(intent);

                                    finish();
                                }else {
                                    Toast.makeText(ThanhtoanActivity.this, "Có vẻ như việc đặt hàng đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(ThanhtoanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(ThanhtoanActivity.this, "Vui lòng chọn hình thức giao hàng", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ThanhtoanActivity.this, "Vui lòng điền đầy đủ địa chỉ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ThongTinNguoiMua() {

        hoten.setText(user.getFullname());
        sodienthoai.setText(user.getPhone());
        diachi.setText(user.getAddress());
        tongtien = getIntent().getLongExtra("tongtien",0);
        tongtiensp.setText(decimalFormat.format(tongtien) + "đ");
        tongtientt.setText(decimalFormat.format(tongtien) + "đ");
    }

    private void AnhXa() {
        hoten = findViewById(R.id.fullname);
        sodienthoai = findViewById(R.id.phonenumber);
        diachi = findViewById(R.id.address);
        tongtiensp = findViewById(R.id.tiensanpham);
        tongtientt = findViewById(R.id.tongtienthanhtoan);
        panel_thongtin = findViewById(R.id.thongtin_nguoimua);
        dathang = findViewById(R.id.dat_hang);
        back = findViewById(R.id.back);
        cbCham = findViewById(R.id.cb_giao_hang_cham);
        cbNhanh = findViewById(R.id.cb_giao_hang_nhanh);
    }
}