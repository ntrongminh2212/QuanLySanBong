package NhanVienPackage;

public class SANBONG {
    private String maSan;
    private LOAISAN loaisan;
    private TRANGTHAI trangthai;
    private int donGiaSan;
    private int giaTheoGio;
    public SANBONG() {
        this.trangthai = TRANGTHAI.TRONG;
        giaTheoGio = 0;
    }

    public SANBONG(String maSan, LOAISAN loaisan, TRANGTHAI trangthai) {
        this.maSan = maSan;
        this.loaisan = loaisan;
        this.trangthai = trangthai;
    }

    public String getMaSan() {
        return maSan;
    }

    public void setMaSan(String maSan) {
        this.maSan = maSan;
    }

    public LOAISAN getLoaisan() {
        return loaisan;
    }

    public void setLoaisan(LOAISAN loaisan) {
        this.loaisan = loaisan;
    }

    public TRANGTHAI getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(TRANGTHAI trangthai) {
        this.trangthai = trangthai;
    }

    public int getDonGiaSan() {
        return donGiaSan;
    }

    public void setDonGiaSan(int donGiaSan) {
        this.donGiaSan = donGiaSan;
    }

    public void setGiaTheoGio(double heSo) {
        giaTheoGio = (int) (donGiaSan * heSo);
    }
    
    public double getGiaTheoGio(){
        return giaTheoGio;
    }
}
