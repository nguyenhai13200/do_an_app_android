package com.example.abong.modle;

import java.io.Serializable;

public class Iphone implements Serializable {
    public int id;
    public String ten;
    public Integer gia;
    public String hinhanh;
    public String mota;
    private boolean isAddtoCart;

    public Iphone(int id, String tensanpham, Integer giasanpham, String hinhsanpham, String motasanpham) {
        this.id = id;
        ten = tensanpham;
        gia = giasanpham;
        hinhanh = hinhsanpham;
        mota = motasanpham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public boolean isAddtoCart() {
        return isAddtoCart;
    }

    public void setAddtoCart(boolean addtoCart) {
        isAddtoCart = addtoCart;
    }
}
