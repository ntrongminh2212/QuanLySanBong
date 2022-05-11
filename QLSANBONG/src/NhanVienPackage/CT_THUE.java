/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

import java.util.ArrayList;
import java.util.Date;

public class CT_THUE {
    private String maHoaDon;
    private SANBONG sanBong;
    private Date gioThue;
    private Date tgDuKienTra;
    private Date gioTra;
    private Date ngayLapPhieu;
    private KHUNGGIO khungGio;
    private double soGioThue;
    private int thanhTien;
    private int tongTienDv;
    private ArrayList<SD_DICHVU> dsDvSuDung = new ArrayList<SD_DICHVU>();
    
    public CT_THUE(SANBONG sanBong, Date gioThue, Date tgDuKienTra,KHUNGGIO khungGio) {
        this.sanBong = sanBong;
        this.gioThue = gioThue;
        this.tgDuKienTra = tgDuKienTra;
        this.khungGio = khungGio;
    }
    
    public CT_THUE(){
    }
    public Date getTgDuKienTra() {
        return tgDuKienTra;
    }

    public void setTgDuKienTra(Date tgDuKienTra) {
        this.tgDuKienTra = tgDuKienTra;
    }

    public KHUNGGIO getKhungGio() {
        return khungGio;
    }

    public void setKhungGio(KHUNGGIO khungGio) {
        this.khungGio = khungGio;
    }

    public SANBONG getSanBong() {
        return sanBong;
    }

    public void setSanBong(SANBONG sanBong) {
        this.sanBong = sanBong;
    }

    public Date getGioThue() {
        return gioThue;
    }

    public void setGioThue(Date ngayGiothue) {
        this.gioThue = ngayGiothue;
    }

    public Date getGioTra() {
        return gioTra;
    }

    public void setGioTra(Date ngayGioTra) {
        this.gioTra = ngayGioTra;
        if (gioTra != null) {
            setSoGioThue();
        }
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public double getSoGioThue() {
        return soGioThue;
    }

    public void setSoGioThue(double soGioThue) {
        this.soGioThue = soGioThue;
    }

    public void setSoGioThue() {
        double soPhut = (gioTra.getTime()-gioThue.getTime()) / (60 * 1000);
        soGioThue = (double) Math.round((soPhut/60) *10)/10;
    }
    
    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public ArrayList<SD_DICHVU> getDsDvSuDung() {
        return dsDvSuDung;
    }

    public int getTongTienDv() {
        return tongTienDv;
    }

    public void setTongTienDv(int tongTienDv) {
        this.tongTienDv = tongTienDv;
    }

    public Date getNgayLapPhieu() {
        return ngayLapPhieu;
    }

    public void setNgayLapPhieu(Date ngayLapPhieu) {
        this.ngayLapPhieu = ngayLapPhieu;
    }
      
}
