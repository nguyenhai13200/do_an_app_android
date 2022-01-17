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
import com.example.abong.Activity.ChitietMacActivity;
import com.example.abong.Listener.ClickAddMacToCart;
import com.example.abong.R;
import com.example.abong.modle.Mac;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MacAdapter extends RecyclerView.Adapter<MacAdapter.RecyclerviewViewHolder>{
    private ArrayList<Mac> arraySanpham;
    private Context context;
    private ClickAddMacToCart clickAddMacToCart;

    public MacAdapter(ArrayList<Mac> arraySanpham, Context context) {
        this.arraySanpham = arraySanpham;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerviewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.dong_sanpham,parent,false);
        return new RecyclerviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerviewViewHolder holder, int position) {
        Mac mac = arraySanpham.get(position);
        holder.txtTenDt.setText(mac.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaDt.setText("Giá: "+decimalFormat.format(mac.getGia()) + "đ");
        Glide.with(context).load(mac.getHinhanh()).placeholder(R.drawable.qgyn).error(R.drawable.anhloi).into(holder.imgDt);
        if (mac.isAddtoCart()){
            holder.imgAddCart.setBackgroundResource(R.drawable.bg_gray_cart);
        }else {
            holder.imgAddCart.setBackgroundResource(R.drawable.bg_blue_cart);
        }
        holder.imgAddCart.setOnClickListener(new View.OnClickListener() { // sự kiện click nút mua
            @Override
            public void onClick(View v) {
                if(!mac.isAddtoCart()){
                    clickAddMacToCart.onClickAddMacToCart(holder.imgAddCart,mac);
                }
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() { // sự kiện click item sang chi tiết
            @Override
            public void onClick(View v) {
                onClickGoToChitiet(mac);
            }
        });
    }

    private void onClickGoToChitiet(Mac mac) {
        Intent intent = new Intent(context, ChitietMacActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("thongtinmac",mac);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return (arraySanpham != null) ? arraySanpham.size() : 0;
    }

    public class RecyclerviewViewHolder extends RecyclerView.ViewHolder{
        TextView txtGiaDt, txtTenDt;
        ImageView imgDt, imgAddCart;
        CardView cardView;
        public RecyclerviewViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtTenDt = itemView.findViewById(R.id.TextViewTensp);
            txtGiaDt = itemView.findViewById(R.id.TextViewGiasp);
            imgDt = itemView.findViewById(R.id.ImageViewSp);
            imgAddCart = itemView.findViewById(R.id.img_add_cart);
            cardView = itemView.findViewById(R.id.CardViewSanPham);
        }
    }

    public void setOnClickAddMacToCart(ClickAddMacToCart clickAddCart){
        this.clickAddMacToCart = clickAddCart;
    }
}
