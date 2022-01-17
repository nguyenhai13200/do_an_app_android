package com.example.abong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.abong.R;
import com.example.abong.modle.ImageChitiet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImageViewChitiet extends RecyclerView.Adapter<ImageViewChitiet.ImageHolder>{
    private List<ImageChitiet> imageChitiets;
    private Context context;
    public ImageViewChitiet(List<ImageChitiet> imageChitiets, Context context) {
        this.imageChitiets = imageChitiets;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_chitiet,parent,false);
        return new ImageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageViewChitiet.ImageHolder holder, int position) {
        ImageChitiet imageChitiet = imageChitiets.get(position);
        if (imageChitiet == null){
            return;
        }
        Glide.with(context).load(imageChitiet.getKhoHinhanh()).placeholder(R.drawable.qgyn).error(R.drawable.anhloi).into(holder.imgChitiet);
    }

    @Override
    public int getItemCount() {
        if (imageChitiets != null){
            return imageChitiets.size();
        }
        return 0;
    }

    public class ImageHolder extends RecyclerView.ViewHolder{
        private ImageView imgChitiet;

        public ImageHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgChitiet = itemView.findViewById(R.id.image_chitiet);
        }
    }
}
