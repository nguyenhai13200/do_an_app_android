package com.example.abong.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.abong.Api.ApiService;
import com.example.abong.R;
import com.example.abong.adapter.ImageViewChitiet;
import com.example.abong.modle.CartModle;
import com.example.abong.modle.ImageChitiet;
import com.example.abong.modle.Mac;

import java.text.DecimalFormat;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChitietMacActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private ArrayList<ImageChitiet> imageChitietList;
    private ImageViewChitiet adapter;
    private TextView txtTen,txtMota,txtGia;
    private ImageView imgBack;
    private LinearLayout llMuangay;
    int id = 0;
    String TenChitiet = "";
    int GiaChitiet= 0;
    String HinhanhChitiet = "";
    String MotaChitiet = "";
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager2.getCurrentItem() == imageChitietList.size() - 1){
                mViewPager2.setCurrentItem(0);
            }else {
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);

        AnhXa();
        GetInFormation();
        Back();
        EventButton();
        // auto image
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,2500);
            }
        });
    }

    private void GetInFormation() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        Mac mac = (Mac) bundle.get("thongtinmac");
        id = mac.getId();
        TenChitiet = mac.getTen();
        GiaChitiet = mac.getGia();
        HinhanhChitiet = mac.getHinhanh();
        MotaChitiet = mac.getMota();
        txtTen.setText(TenChitiet);
        txtMota.setText(MotaChitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Giá: "+decimalFormat.format(GiaChitiet) + "đ");
        GetImageChitietMac();
    }

    private void GetImageChitietMac() {
        ApiService.apiService.getImageChitietMac(id).enqueue(new Callback<ArrayList<ImageChitiet>>() {
            @Override
            public void onResponse(Call<ArrayList<ImageChitiet>> call, Response<ArrayList<ImageChitiet>> response) {
                if (response != null){
                    for (int i = 0; i < response.body().size(); i++){
                        imageChitietList.add(response.body().get(i));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ImageChitiet>> call, Throwable t) {
                Toast.makeText(ChitietMacActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXa() {
        imageChitietList = new ArrayList<>();
        mViewPager2 = findViewById(R.id.viewpage2_chitiet);
        mCircleIndicator3 = findViewById(R.id.circle_indicator3);
        adapter = new ImageViewChitiet(imageChitietList,this);
        mViewPager2.setAdapter(adapter);
        mCircleIndicator3.setViewPager(mViewPager2);
        llMuangay = findViewById(R.id.mua_ngay);
        txtTen = findViewById(R.id.text_view_chitiet);
        txtMota = findViewById(R.id.text_view_idchitiet);
        txtGia = findViewById(R.id.gia_chi_tiet);
    }

    private void Back() {
        //back
        imgBack = findViewById(R.id.back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void EventButton() {
        llMuangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.manggiohang.size() > 0){
                    int sl = 1;
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.manggiohang.size(); i++){
                        if (MainActivity.manggiohang.get(i).getTensanpham().equals(TenChitiet) ){
                            MainActivity.manggiohang.get(i).setSoluong(MainActivity.manggiohang.get(i).getSoluong() + sl);
                            if (MainActivity.manggiohang.get(i).getSoluong() >= 10){
                                MainActivity.manggiohang.get(i).setSoluong(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasanpham(GiaChitiet * MainActivity.manggiohang.get(i).getSoluong());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soluong = 1;
                        MainActivity.manggiohang.add(new CartModle(id,TenChitiet,GiaChitiet,HinhanhChitiet,soluong));
                        MainActivity.setCountItemInCart(MainActivity.getCountt() + 1);
                    }
                }else {
                    int soluong = 1;
                    MainActivity.manggiohang.add(new CartModle(id,TenChitiet,GiaChitiet,HinhanhChitiet,soluong));
                    MainActivity.setCountItemInCart(MainActivity.getCountt() + 1);
                }
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });
    }
}