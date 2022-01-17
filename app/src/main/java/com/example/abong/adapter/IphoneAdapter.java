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
import com.example.abong.Activity.GioHangActivity;
import com.example.abong.Activity.MainActivity;
import com.example.abong.R;
import com.example.abong.modle.Iphone;
import com.example.abong.Listener.ClickAddIphoneToCart;


import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class IphoneAdapter extends RecyclerView.Adapter<IphoneAdapter.RecyclerviewViewHolder>{
    private ArrayList<Iphone> arrayIphone;
    private Context context;
    private ClickAddIphoneToCart clickAddIphoneToCart;

    public IphoneAdapter(ArrayList<Iphone> arrayIphone, Context context) {
        this.arrayIphone = arrayIphone;
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
        Iphone iphone = arrayIphone.get(position);
        holder.txtTenDt.setText(iphone.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaDt.setText("Giá: "+decimalFormat.format(iphone.getGia()) + "đ");
        Glide.with(context).load(iphone.getHinhanh()).placeholder(R.drawable.qgyn).error(R.drawable.anhloi).into(holder.imgDt);
        if (iphone.isAddtoCart()){
            holder.imgAddCart.setBackgroundResource(R.drawable.bg_gray_cart);
        }else {
            holder.imgAddCart.setBackgroundResource(R.drawable.bg_blue_cart);
        }
        holder.imgAddCart.setOnClickListener(new View.OnClickListener() { // sự kiện click nút mua
            @Override
            public void onClick(View v) {
                if(!iphone.isAddtoCart()){
                    clickAddIphoneToCart.onClickAddToCart(holder.imgAddCart, iphone);
                    Intent intent = new Intent(context, GioHangActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("thongtiniphone",iphone);
                    intent.putExtras(bundle);
                }
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() { // sự kiện click item sang chi tiết
            @Override
            public void onClick(View v) {
                onClickGoToChitiet(iphone);
            }
        });
    }

    private void onClickGoToChitiet(Iphone iphone) {
        Intent intent = new Intent(context, ChitietIphoneActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("thongtiniphone",iphone);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return (arrayIphone != null) ? arrayIphone.size() : 0;
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

    public void setOnClickAddCart(ClickAddIphoneToCart clickAddCart){
        this.clickAddIphoneToCart = clickAddCart;
    }
}
