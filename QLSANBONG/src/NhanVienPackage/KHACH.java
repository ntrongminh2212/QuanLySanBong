package NhanVienPackage;

public class KHACH {
    private String CMND;
    private String hoTen;
    private String sdt;
    private String tenTk;
    private String matKhau;
    
    public KHACH(){
        
    }
    public KHACH(String CMND, String hoTen, String sdt) {
        this.CMND = CMND;
        this.hoTen = hoTen;
        this.sdt = sdt;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenTk() {
        return tenTk;
    }

    public void setTenTk(String tenTk) {
        this.tenTk = tenTk;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
    @Override
    public String toString() {
        return " " + CMND;
    }
}
