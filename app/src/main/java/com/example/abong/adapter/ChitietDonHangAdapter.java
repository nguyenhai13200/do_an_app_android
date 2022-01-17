package com.example.abong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.abong.R;
import com.example.abong.modle.Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChitietDonHangAdapter extends RecyclerView.Adapter<ChitietDonHangAdapter.MyViewHolder> {
    Context context;
    ArrayList<Item> listItem;

    public ChitietDonHangAdapter(Context context, ArrayList<Item> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_chitiet_donhang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChitietDonHangAdapter.MyViewHolder holder, int position) {
        Item item = listItem.get(position);
        holder.txtTen.setText(item.getTen());
        holder.txtSoluong.setText("Số lượng: "+item.getSoluong());
        holder.txtGia.setText(item.getGia()*item.getSoluong()+"");
        Glide.with(context).load(item.getHinhanh()).placeholder(R.drawable.qgyn).error(R.drawable.anhloi).into(holder.imgChitiet);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtTen, txtSoluong, txtGia;
        ImageView imgChitiet;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txt_ten_chitiet_item);
            txtSoluong = itemView.findViewById(R.id.txt_soluong_chitiet_item);
            txtGia = itemView.findViewById(R.id.txt_gia_chitiet_item);
            imgChitiet = itemView.findViewById(R.id.img_chitiet_item);
        }
    }
}
