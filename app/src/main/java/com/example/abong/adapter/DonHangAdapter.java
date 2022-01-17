package com.example.abong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abong.R;
import com.example.abong.modle.DonHang;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    ArrayList<DonHang> listDonHang;

    public DonHangAdapter(Context context, ArrayList<DonHang> listDonHang) {
        this.context = context;
        this.listDonHang = listDonHang;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_donhang,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DonHangAdapter.MyViewHolder holder, int position) {
        DonHang donHang = listDonHang.get(position);
        holder.txtMaDonHang.setText("Mã đơn hàng: "+donHang.getId());
        holder.txtTrangThai.setText("Trạng thái: "+donHang.getTrangthai());
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recyclerView.getContext(),LinearLayoutManager.VERTICAL,false);
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());

        //adapter chitiet_donhang
        ChitietDonHangAdapter chitietDonHangAdapter = new ChitietDonHangAdapter(context,donHang.getItem());
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(chitietDonHangAdapter);
        holder.recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return listDonHang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaDonHang, txtTrangThai;
        RecyclerView recyclerView;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtMaDonHang = itemView.findViewById(R.id.madonhang);
            txtTrangThai = itemView.findViewById(R.id.trangthai);
            recyclerView = itemView.findViewById(R.id.recyclerview_dong_donhang);
        }
    }
}
