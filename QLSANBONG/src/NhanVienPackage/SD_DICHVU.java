/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;


public class SD_DICHVU {
    private String maHoaDon;
    private String maSan;
    private DICHVU dichVu;
    private int sl;
    private int tienDv;
    
    public SD_DICHVU() {
    }

    public SD_DICHVU(String maHoaDon, String maSan, DICHVU dichVu, int sl, int tienDv) {
        this.maHoaDon = maHoaDon;
        this.maSan = maSan;
        this.dichVu = dichVu;
        this.sl = sl;
        this.tienDv = tienDv;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaSan() {
        return maSan;
    }

    public void setMaSan(String maSan) {
        this.maSan = maSan;
    }

    public DICHVU getDichVu() {
        return dichVu;
    }

    public void setDichVu(DICHVU dichVu) {
        this.dichVu = dichVu;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getTienDv() {
        return tienDv;
    }

    public void setTienDv(int tienDv) {
        this.tienDv = tienDv;
    }
    
    public int tinhTienDv(){
        tienDv = sl*dichVu.getDonGia();
        return tienDv;
    }
}
