//package com.example.abong.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.abong.R;
//import com.example.abong.modle.CartModle;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//
//public class GiohangAdapter extends RecyclerView.Adapter<GiohangAdapter.RecyclerviewViewHolder> {
//    private ArrayList<CartModle> cartModleList;
//    private Context context;
//
//    public GiohangAdapter(ArrayList<CartModle> cartModleList, Context context) {
//        this.cartModleList = cartModleList;
//        this.context = context;
//    }
//
//    @NonNull
//    @NotNull
//    @Override
//    public RecyclerviewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.dong_giohang, parent, false);
//        return new RecyclerviewViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull @NotNull RecyclerviewViewHolder holder, int position) {
//        CartModle cart = cartModleList.get(position);
//        holder.txtTenSp.setText(cart.getTensanpham());
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.txtGiaSp.setText("Giá: " + decimalFormat.format(cart.getGiasanpham()) + "đ");
//        Glide.with(context).load(cart.getHinhsanpham()).placeholder(R.drawable.qgyn).error(R.drawable.anhloi).into(holder.imgSp);
//
//        holder.txtQuantity.setText(cart.getSoluong() + "");
//    }
//
//    @Override
//    public int getItemCount() {
//        return (cartModleList != null) ? cartModleList.size() : 0;
//    }
//
//    public class RecyclerviewViewHolder extends RecyclerView.ViewHolder {
//        public TextView txtGiaSp, txtTenSp, txtQuantity;
//        public Button btnTang, btnGiam;
//        public ImageView imgSp;
//        public CardView cardView;
//
//        public RecyclerviewViewHolder(@NonNull @NotNull View itemView) {
//            super(itemView);
//            txtTenSp = itemView.findViewById(R.id.ten_sp_giohang);
//            txtGiaSp = itemView.findViewById(R.id.gia_sp_giohang);
//            txtQuantity = itemView.findViewById(R.id.text_view_quantity);
//            btnTang = itemView.findViewById(R.id.button_tang);
//            btnGiam = itemView.findViewById(R.id.button_giam);
//            imgSp = itemView.findViewById(R.id.img_sp_giohang);
//            cardView = itemView.findViewById(R.id.CardViewGiohang);
//        }
//    }
//}
