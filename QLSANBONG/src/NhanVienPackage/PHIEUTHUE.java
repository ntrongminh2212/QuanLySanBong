/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

import java.util.ArrayList;
import java.util.Date;

public class PHIEUTHUE {

    private String maHoaDon;
    private KHACH khach;
    private NHANVIEN nvGhiPhieu;
    
    private Date ngayGhiPhieu;
    private ArrayList<CT_THUE> ctThue = new ArrayList<CT_THUE>();
    private int tongPhaiTra;

    public int getTongPhaiTra() {
        return tongPhaiTra;
    }

    public void setTongPhaiTra() {
        System.out.println(ctThue.size());
        for (CT_THUE ct_thue : ctThue) {
            for (SD_DICHVU sdDv : ct_thue.getDsDvSuDung()) {
                System.out.println(sdDv.getTienDv());
                tongPhaiTra = tongPhaiTra + sdDv.getTienDv();
            }
            tongPhaiTra = tongPhaiTra + ct_thue.getThanhTien();
        }
    }
    
    public void setTongPhaiTra(int tongPhaiTra) {
        this.tongPhaiTra = tongPhaiTra;
    }
    
    public Date getNgayGhiPhieu() {
        return ngayGhiPhieu;
    }

    public void setNgayGhiPhieu(Date ngayGhiPhieu) {
        this.ngayGhiPhieu = ngayGhiPhieu;
    }

    public ArrayList<CT_THUE> getCtThue() {
        return ctThue;
    }

    public void setCtThue(ArrayList<CT_THUE> ctThue) {
        this.ctThue = ctThue;
    }
    
    public PHIEUTHUE() {
        tongPhaiTra = 0;
    }

    public PHIEUTHUE(String maHoaDon, KHACH khach, NHANVIEN nvGhiPhieu,Date ngayGhiPhieu) {
        this.maHoaDon = maHoaDon;
        this.khach = khach;
        this.nvGhiPhieu = nvGhiPhieu;
        this.ngayGhiPhieu = ngayGhiPhieu;
        tongPhaiTra = 0;
    }

    public KHACH getKhach() {
        return khach;
    }

    public void setKhach(KHACH khach) {
        this.khach = khach;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public NHANVIEN getNvGhiPhieu() {
        return nvGhiPhieu;
    }

    public void setNvGhiPhieu(NHANVIEN nvGhiPhieu) {
        this.nvGhiPhieu = nvGhiPhieu;
    }
    
    public void addCtThue(CT_THUE newCtthue){
        this.ctThue.add(newCtthue);
    }
}
