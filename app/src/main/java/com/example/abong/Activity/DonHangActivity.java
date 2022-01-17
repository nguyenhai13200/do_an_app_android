package com.example.abong.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abong.Api.ApiService;
import com.example.abong.R;
import com.example.abong.adapter.DonHangAdapter;
import com.example.abong.data_local.DataLocalManager;
import com.example.abong.modle.DonHang;
import com.example.abong.modle.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHangActivity extends AppCompatActivity {
    TextView txtThongBao;
    ImageView back;
    User user = DataLocalManager.getUser();
    RecyclerView recyclerViewDonHang;
    ArrayList<DonHang> donHangArrayList;
    DonHangAdapter donHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        AnhXa();
        getDonHang();
//        CheckData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDonHang() {
        ApiService.apiService.listDonhang(user.getId()).enqueue(new Callback<ArrayList<DonHang>>() {
            @Override
            public void onResponse(Call<ArrayList<DonHang>> call, Response<ArrayList<DonHang>> response) {
                donHangArrayList = response.body();
                CheckData();
                donHangAdapter = new DonHangAdapter(getApplicationContext(),response.body());
                recyclerViewDonHang.setAdapter(donHangAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DonHang>> call, Throwable t) {

            }
        });
    }

    private void CheckData() {
        if(donHangArrayList.size() <=0 ){
            txtThongBao.setVisibility(View.VISIBLE);
            recyclerViewDonHang.setVisibility(View.INVISIBLE);

        }else{
            txtThongBao.setVisibility(View.INVISIBLE);
            recyclerViewDonHang.setVisibility(View.VISIBLE);
        }
    }
    private void AnhXa() {
        txtThongBao = findViewById(R.id.text_view_thongbao);
        back = findViewById(R.id.back);
        recyclerViewDonHang = findViewById(R.id.recyclerview_donhang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewDonHang.setLayoutManager(layoutManager);
    }
}