package com.example.abong.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.abong.Activity.ChitietIphoneActivity;
import com.example.abong.Activity.ChitietSanphamNewActivity;
import com.example.abong.R;
import com.example.abong.modle.SanphamNew;


import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamnewAdapter extends RecyclerView.Adapter<SanphamnewAdapter.ItemHolder> {
    Context context;
    ArrayList<SanphamNew> arraySanpham;

    public SanphamnewAdapter(@NonNull Context context, ArrayList<SanphamNew> arraySanpham) {
        this.context = context;
        this.arraySanpham = arraySanpham;
    }

    @Override
    public ItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemHolder holder, int position) {
        SanphamNew sanpham = arraySanpham.get(position);
        holder.txtTensanpham.setText(sanpham.getTenspn());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiasanpham.setText("Giá: "+decimalFormat.format(sanpham.getGiaspn()) + "đ");
        Glide.with(context).load(sanpham.getHinhanhspn()).placeholder(R.drawable.qgyn).error(R.drawable.anhloi).into(holder.imgHinhsanpham);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToChitiet(sanpham);
            }
        });
    }

    private void onClickGoToChitiet(SanphamNew sanphamNew) {
        Intent intent = new Intent(context, ChitietSanphamNewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("thongtinspn",sanphamNew);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return arraySanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public ImageView imgHinhsanpham;
        public TextView txtTensanpham, txtGiasanpham;

        public ItemHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgHinhsanpham = itemView.findViewById(R.id.ImageViewSpn);
            txtTensanpham = itemView.findViewById(R.id.TextViewTenspn);
            txtGiasanpham = itemView.findViewById(R.id.TextViewGiaspn);
            cardView = itemView.findViewById(R.id.CardView);
        }
    }
}
