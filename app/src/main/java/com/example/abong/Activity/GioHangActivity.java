package com.example.abong.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abong.Listener.ClickDeleteCart;
import com.example.abong.R;
import com.example.abong.adapter.gioHangAdapterListView;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    private ImageView imgBack;
    private ListView listView;
    private static TextView txtThongbao,txtTongtien;
    private LinearLayout llThanhtoan;
    private gioHangAdapterListView giohangAdapter;
    public static long tongtien;
    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        CheckData();
        EventUltil();
        EventButton();
    }

    private void EventButton() {
        //back
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //thanh toán
        llThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.manggiohang.size() == 0){
                    Toast.makeText(GioHangActivity.this, "Chưa có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }else{
                    if(MainActivity.isLogin==true){
                        Intent intent = new Intent(getApplicationContext(),ThanhtoanActivity.class);
                        intent.putExtra("tongtien",tongtien);
                        startActivity(intent);
                    }else {
                        Toast.makeText(GioHangActivity.this, "Vui lòng đăng nhập để thanh toán", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static void EventUltil() {
        tongtien = 0;
        for(int i = 0; i < MainActivity.manggiohang.size();i++){
            tongtien += MainActivity.manggiohang.get(i).getGiasanpham();
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txtTongtien.setText(decimalFormat.format(tongtien) + "đ");
        }
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size() <= 0){
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);

        }else{
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    private void AnhXa() {
        imgBack = findViewById(R.id.back);
        listView = findViewById(R.id.listview_giohang);
        txtThongbao = findViewById(R.id.text_view_thongbao);
        txtTongtien = findViewById(R.id.text_view_tongtien);
        llThanhtoan = findViewById(R.id.thanh_toan);
        giohangAdapter = new gioHangAdapterListView(MainActivity.manggiohang, this, new ClickDeleteCart() {
            @Override
            public void onDelete(int pos) {
                MainActivity.manggiohang.remove(pos);
                giohangAdapter.notifyDataSetChanged();
                CheckData();
                if(MainActivity.manggiohang.size() <= 0 ){
                    txtTongtien.setText(0 + "đ");
                }else{
                    EventUltil();
                }
            }
        });
        listView.setAdapter(giohangAdapter);
        giohangAdapter.notifyDataSetChanged();
    }
}
