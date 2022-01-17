package com.example.abong.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.abong.Api.ApiService;
import com.example.abong.R;
import com.example.abong.adapter.SanphamSearchAdapter;
import com.example.abong.adapter.SanphamnewAdapter;
import com.example.abong.modle.SanPhamSearch;
import com.example.abong.modle.SanphamNew;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemActivity extends AppCompatActivity {
    ImageView back;
    EditText edtSearch;
    RecyclerView recyclerViewSearch;
    SanphamSearchAdapter sanphamSearchAdapter;
    ArrayList<SanPhamSearch> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        AnhXa();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                arrayList.clear();
                getDataSearch();
            }
        });
    }

    private void getDataSearch() {
        String str_search = edtSearch.getText().toString().trim();
        ApiService.apiService.search(str_search).enqueue(new Callback<ArrayList<SanPhamSearch>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPhamSearch>> call, Response<ArrayList<SanPhamSearch>> response) {

                    for (int i = 0; i < response.body().size();i++){
                        arrayList.add(response.body().get(i));
                    }
                    Log.d("SEARCH",arrayList.toString());
                    sanphamSearchAdapter.notifyDataSetChanged();
                }


            @Override
            public void onFailure(Call<ArrayList<SanPhamSearch>> call, Throwable t) {
                Log.e("Search",t.getMessage());
            }
        });
    }

    private void AnhXa() {
        back = findViewById(R.id.back);
        edtSearch = findViewById(R.id.editText_timkiem);
        arrayList = new ArrayList<>();
        recyclerViewSearch = findViewById(R.id.recyclerview_search);
        sanphamSearchAdapter = new SanphamSearchAdapter(getApplicationContext(),arrayList);
        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewSearch.setAdapter(sanphamSearchAdapter);
    }
}