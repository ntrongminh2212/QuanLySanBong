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
public enum LOAISAN {
    SAN5(1, "Sân 5"), SAN7(2, "Sân 7"), SAN11(3, "Sân 11"), TATCA(0,"Tất cả");
    private int maLoaiSan;
    private String tenLoaiSan;

    private LOAISAN(int maLoaiSan, String tenLoaiSan) {
        this.maLoaiSan = maLoaiSan;
        this.tenLoaiSan = tenLoaiSan;
    }

    public int getMaLoaiSan() {
        return maLoaiSan;
    }

    public String getTenLoaiSan() {
        return tenLoaiSan;
    }

    public static LOAISAN findLoaiSan(int maLoaiSan){
        for (LOAISAN loaiSan : LOAISAN.values()) {
            if (maLoaiSan == loaiSan.maLoaiSan) {
                return loaiSan;
            }
        }
        return null;
    }
   
    public static LOAISAN findLoaiSan(String tenLoaiSan){
        for (LOAISAN loaiSan : LOAISAN.values()) {
            if (tenLoaiSan.compareTo(loaiSan.tenLoaiSan)==0) {
                return loaiSan;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return tenLoaiSan;
    }
}
