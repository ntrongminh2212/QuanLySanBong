/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

public class DICHVU {
    private String tenDichVu;
    private int donGia;
    private String donVi;
    private DANHMUC danhmuc;

    public DICHVU(String tenDichVu, int donGia, String donVi, DANHMUC danhmuc) {
        this.tenDichVu = tenDichVu;
        this.donGia = donGia;
        this.donVi = donVi;
        this.danhmuc = danhmuc;
    }

    public DICHVU() {
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public DANHMUC getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(DANHMUC danhmuc) {
        this.danhmuc = danhmuc;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }
}
