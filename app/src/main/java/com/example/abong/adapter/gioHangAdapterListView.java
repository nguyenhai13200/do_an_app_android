package com.example.abong.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abong.Activity.GioHangActivity;
import com.example.abong.Activity.MainActivity;
import com.example.abong.Listener.ClickDeleteCart;
import com.example.abong.R;
import com.example.abong.modle.CartModle;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class gioHangAdapterListView extends BaseAdapter {

    private ArrayList<CartModle> cartModleList;
    private Context context;
    private ClickDeleteCart clickDeleteCart;
    public gioHangAdapterListView(ArrayList<CartModle> cartModleList, Context context, ClickDeleteCart clickDeleteCart) {
        this.cartModleList = cartModleList;
        this.context = context;
        this.clickDeleteCart = clickDeleteCart;
    }

    @Override
    public int getCount() {
        return cartModleList.size();
    }

    @Override
    public Object getItem(int i) {
        return cartModleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHoler{
        public TextView txtTengiohang,txtGiagiohang, txtSoluonggiohang;
        public ImageView imgGiohang, btnDelete;
        public Button btnTang, btnGiam;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoler  viewHoler = null;
        if (view == null){
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewHoler.txtTengiohang = view.findViewById(R.id.ten_sp_giohang);
            viewHoler.txtGiagiohang = view.findViewById(R.id.gia_sp_giohang);
            viewHoler.imgGiohang = view.findViewById(R.id.img_sp_giohang);
            viewHoler.btnTang = view.findViewById(R.id.button_tang);
            viewHoler.btnGiam = view.findViewById(R.id.button_giam);
            viewHoler.txtSoluonggiohang = view.findViewById(R.id.text_view_quantity);
            viewHoler.btnDelete = view.findViewById(R.id.button_delete);
            view.setTag(viewHoler);
        }else {
            viewHoler = (ViewHoler) view.getTag();
        }

        CartModle cart = (CartModle) getItem(i);
        viewHoler.txtTengiohang.setText(cart.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtGiagiohang.setText(decimalFormat.format(cart.getGiasanpham())+"đ");
        Glide.with(context).load(cart.getHinhsanpham()).placeholder(R.drawable.qgyn).error(R.drawable.anhloi).into(viewHoler.imgGiohang);
        viewHoler.txtSoluonggiohang.setText(cart.getSoluong()+"");
        int quantity = Integer.parseInt(viewHoler.txtSoluonggiohang.getText().toString());
        if (quantity >= 10){
            viewHoler.btnTang.setVisibility(View.INVISIBLE);
            viewHoler.btnGiam.setVisibility(View.VISIBLE);
        }else if (quantity <= 1){
            viewHoler.btnGiam.setVisibility(View.INVISIBLE);
        }else if (quantity >1){
            viewHoler.btnGiam.setVisibility(View.VISIBLE);
            viewHoler.btnTang.setVisibility(View.VISIBLE);
        }
        ViewHoler finalViewHoler = viewHoler;
        viewHoler.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmoi = Integer.parseInt(finalViewHoler.txtSoluonggiohang.getText().toString()) + 1;
                int soluonghientai = MainActivity.manggiohang.get(i).getSoluong();
                int giahientai = MainActivity.manggiohang.get(i).getGiasanpham();
                MainActivity.manggiohang.get(i).setSoluong(soluongmoi);
                int giamoi = (giahientai * soluongmoi) / soluonghientai;        // quy tắc đường chéo
                MainActivity.manggiohang.get(i).setGiasanpham(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHoler.txtGiagiohang.setText(decimalFormat.format(giamoi)+"đ");
                GioHangActivity.EventUltil();
                if (soluongmoi > 9){
                    finalViewHoler.btnTang.setVisibility(View.INVISIBLE);
                    finalViewHoler.btnGiam.setVisibility(View.VISIBLE);
                    finalViewHoler.txtSoluonggiohang.setText(String.valueOf(soluongmoi));
                }else{
                    finalViewHoler.btnTang.setVisibility(View.VISIBLE);
                    finalViewHoler.btnGiam.setVisibility(View.VISIBLE);
                    finalViewHoler.txtSoluonggiohang.setText(String.valueOf(soluongmoi));
                }
            }
        });
        viewHoler.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmoi = Integer.parseInt(finalViewHoler.txtSoluonggiohang.getText().toString()) - 1;
                int soluonghientai = MainActivity.manggiohang.get(i).getSoluong();
                int giahientai = MainActivity.manggiohang.get(i).getGiasanpham();
                MainActivity.manggiohang.get(i).setSoluong(soluongmoi);
                int giamoi = (giahientai * soluongmoi) / soluonghientai;        // quy tắc đường chéo
                MainActivity.manggiohang.get(i).setGiasanpham(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHoler.txtGiagiohang.setText(decimalFormat.format(giamoi)+"đ");
                GioHangActivity.EventUltil();
                if (soluongmoi < 2){
                    finalViewHoler.btnGiam.setVisibility(View.INVISIBLE);
                    finalViewHoler.btnTang.setVisibility(View.VISIBLE);
                    finalViewHoler.txtSoluonggiohang.setText(String.valueOf(soluongmoi));
                }else{
                    finalViewHoler.btnTang.setVisibility(View.VISIBLE);
                    finalViewHoler.btnGiam.setVisibility(View.VISIBLE);
                    finalViewHoler.txtSoluonggiohang.setText(String.valueOf(soluongmoi));
                }
            }
        });
        viewHoler.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhanXoa(i);
            }
        });
        return view;
    }

    private void XacNhanXoa(int pos) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
//        dialogXoa.create();
        dialogXoa.setMessage("Bạn có muốn xóa sản phẩm này hay không?");
        dialogXoa.setTitle("Thông báo!");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickDeleteCart.onDelete(pos);
                MainActivity.setCountItemInCart(MainActivity.getCountt() - 1);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.create().show();
    }

}
