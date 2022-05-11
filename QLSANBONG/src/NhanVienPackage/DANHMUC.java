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
public enum DANHMUC{
    //có những dịch vụ gì có thể điền vào đây
    DOAN("Đồ ăn",1), DOUONG("Đồ uống",2), DUNGCU("Dụng cụ",3), NGUNGCUNGCAP("Ngừng cung cấp",4);
    private String tenDanhMuc;
    private int maDanhMuc;

    private DANHMUC(String tenDanhMuc, int maDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    @Override
    public String toString() {
        return tenDanhMuc;
    }
    
    
    public static DANHMUC findDanhMuc(int maDanhMuc){
        for (DANHMUC dm : DANHMUC.values()) {
            if (maDanhMuc == dm.maDanhMuc) {
                return dm;
            }
        }
        return null;
    }
    public static DANHMUC findDanhMuc(String tenDanhMuc){
        for (DANHMUC dm : DANHMUC.values()) {
            if (tenDanhMuc.compareTo(dm.tenDanhMuc)==0) {
                return dm;
            }
        }
        return null;
    }
}
