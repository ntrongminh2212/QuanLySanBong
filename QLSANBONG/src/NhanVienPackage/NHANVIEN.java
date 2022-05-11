/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

/**
 *
 * @author zLittleMasterz
 */
public class NHANVIEN {
    private String maNV;
    private String tenNV;
    private String CMND;
    private String matKhau;
    private String sdt;

    public NHANVIEN() {
    }

    public NHANVIEN(String maNV, String tenNV, String CMND, String matKhau, String sdt) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.CMND = CMND;
        this.matKhau = matKhau;
        this.sdt = sdt;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    
}
