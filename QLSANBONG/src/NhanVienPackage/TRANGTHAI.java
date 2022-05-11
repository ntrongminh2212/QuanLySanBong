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
public enum TRANGTHAI {
    TRONG(0, "Trống"), DANGSUDUNG(1, "Đang sử dụng"), BAOTRI(2, "Bảo trì");
    private int matrangThai;
    private String tenTrangThai;

    private TRANGTHAI(int matrangThai, String tenTrangThai) {
        this.matrangThai = matrangThai;
        this.tenTrangThai = tenTrangThai;
    }

    public int getMatrangThai() {
        return matrangThai;
    }

    public void setMatrangThai(int matrangThai) {
        this.matrangThai = matrangThai;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public static TRANGTHAI findTrangThai (int maTT){
        for (TRANGTHAI tt : TRANGTHAI.values()) {
            if (tt.matrangThai==maTT) {
                return tt;
            }
        }
        return null;
    }
    
    public static TRANGTHAI findTrangThai (String tenTT){
        for (TRANGTHAI tt : TRANGTHAI.values()) {
            if (tt.tenTrangThai.compareTo(tenTT)==0) {
                return tt;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return tenTrangThai;
    }
}
