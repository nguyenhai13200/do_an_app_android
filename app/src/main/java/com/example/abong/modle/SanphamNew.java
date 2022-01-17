package com.example.abong.modle;

import java.io.Serializable;

public class SanphamNew implements Serializable {
    public int id;
    public String tenspn;
    public Integer giaspn;
    public String hinhanhspn;
    public String motaspn;

    public SanphamNew(int id, String tensanpham, Integer giasanpham, String hinhsanpham, String motasanpham) {
        this.id = id;
        tenspn = tensanpham;
        giaspn = giasanpham;
        hinhanhspn = hinhsanpham;
        motaspn = motasanpham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenspn() {
        return tenspn;
    }

    public void setTenspn(String tenspn) {
        this.tenspn = tenspn;
    }

    public Integer getGiaspn() {
        return giaspn;
    }

    public void setGiaspn(Integer giaspn) {
        this.giaspn = giaspn;
    }

    public String getHinhanhspn() {
        return hinhanhspn;
    }

    public void setHinhanhspn(String hinhanhspn) {
        this.hinhanhspn = hinhanhspn;
    }

    public String getMotaspn() {
        return motaspn;
    }

    public void setMotaspn(String motaspn) {
        this.motaspn = motaspn;
    }

}
