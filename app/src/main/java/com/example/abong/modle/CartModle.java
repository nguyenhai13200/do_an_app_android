package com.example.abong.modle;

public class CartModle {
    public int id;
    public String tensanpham;
    public Integer giasanpham;
    public String hinhsanpham;
    private int soluong;

    public CartModle(int id, String tensanpham, Integer giasanpham, String hinhsanpham, int soluong) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.hinhsanpham = hinhsanpham;
        this.soluong = soluong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public Integer getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(Integer giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getHinhsanpham() {
        return hinhsanpham;
    }

    public void setHinhsanpham(String hinhsanpham) {
        this.hinhsanpham = hinhsanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
