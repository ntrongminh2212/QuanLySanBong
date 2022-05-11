/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.cos.COSName;

/**
 *
 * @author zLittleMasterz
 */
public class IODATA {

    public ArrayList<SANBONG> getAlldsSanBong() {
        ArrayList<SANBONG> dsSanBongTrong = new ArrayList<SANBONG>();
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT MASAN,SANBONG.MALOAISAN,GIA,TRANGTHAI\n"
                + "FROM SANBONG,LOAISAN\n"
                + "WHERE LOAISAN.MALOAISAN=SANBONG.MALOAISAN";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SANBONG newSan = new SANBONG();
                newSan.setMaSan(rs.getString(1));
                newSan.setLoaisan(LOAISAN.findLoaiSan(rs.getInt(2)));
                newSan.setDonGiaSan(rs.getInt(3));
                newSan.setTrangthai(TRANGTHAI.findTrangThai(rs.getInt(4)));
                dsSanBongTrong.add(newSan);
            }
            System.out.println("Da lay tat ca thong tin san bong !");
            return dsSanBongTrong;
        } catch (SQLException ex) {
            System.out.println("Khong the lay thong tin");
        }
        return null;
    }
    
    public ArrayList<KHACH> getDsKhachHang() {
        ArrayList<KHACH> dskhachHang = new ArrayList<KHACH>();
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM KHACHTHUE\n";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                KHACH newKhach = new KHACH();
                newKhach.setHoTen(rs.getString(1));
                newKhach.setCMND(rs.getString(2));
                newKhach.setSdt(rs.getString(3));
                dskhachHang.add(newKhach);
            }
            System.out.println("Da lay tat ca thong tin KHACHTHUE");
            return dskhachHang;
        } catch (SQLException ex) {
            System.out.println("Khong the lay thong tin KHACHTHUE: IODATA.getDsKhachHang !");
        }
        return null;
    }

    public ArrayList<CT_THUE> getAllCt_Thues() {
        ArrayList<CT_THUE> dsCt_Thues = new ArrayList<CT_THUE>();
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT CT_THUE.MASAN,CT_THUE.MAHOADON,GIOTHUE,TG_DUKIENTRA,GIOTRA,MAKG,SOGIOTHUE,THANHTIEN,LOAISAN.MALOAISAN,SANBONG.TRANGTHAI,GIA\n"
                + "FROM CT_THUE, SANBONG, LOAISAN\n"
                + "WHERE CT_THUE.MASAN = SANBONG.MASAN\n"
                + "AND SANBONG.MALOAISAN = LOAISAN.MALOAISAN\n";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CT_THUE newCt_thue = new CT_THUE();
                SANBONG sanDangThue = new SANBONG();
                
                newCt_thue.setKhungGio(KHUNGGIO.findKhungGio(rs.getInt(6)));
                sanDangThue.setMaSan(rs.getString(1));
                sanDangThue.setDonGiaSan(rs.getInt(11));
                sanDangThue.setLoaisan(LOAISAN.findLoaiSan(rs.getInt(9)));
                sanDangThue.setTrangthai(TRANGTHAI.findTrangThai(rs.getInt(10)));
                sanDangThue.setGiaTheoGio(timHeSo_Kg(newCt_thue.getKhungGio().getMaKG()));
                newCt_thue.setSanBong(sanDangThue);
                newCt_thue.setMaHoaDon(rs.getString(2));
                newCt_thue.setGioThue(rs.getTime(3));
                newCt_thue.setTgDuKienTra(rs.getTime(4));
                newCt_thue.setGioTra(rs.getTime(5));
                newCt_thue.setSoGioThue(rs.getDouble(7));
                newCt_thue.setThanhTien(rs.getInt(8));
                getSdDvsOfCtThue(newCt_thue);
                dsCt_Thues.add(newCt_thue);
            }
            System.out.println("Da lay tat ca thong tin CT_THUE !");
            return dsCt_Thues;
        } catch (SQLException ex) {
            System.out.println("Khong the lay thong tin CT_THUE: IODATA.getAllCt_Thues");
        }
        return null;
    }

    public ArrayList<CT_THUE> getDsCtSanDangThue() {
        ArrayList<CT_THUE> dsCt_Thues = new ArrayList<CT_THUE>();
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM CT_THUE,SANBONG,LOAISAN\n"
                + "WHERE CT_THUE.MASAN = SANBONG.MASAN\n"
                + "AND SANBONG.MALOAISAN = LOAISAN.MALOAISAN\n"
                + "AND GIOTRA IS NULL";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CT_THUE newCt_thue = new CT_THUE();
                SANBONG sanDangThue = new SANBONG();
                newCt_thue.setKhungGio(KHUNGGIO.findKhungGio(rs.getInt(6)));
                sanDangThue.setMaSan(rs.getString(1));
                sanDangThue.setDonGiaSan(rs.getInt(14));
                sanDangThue.setLoaisan(LOAISAN.findLoaiSan(10));
                newCt_thue.setSanBong(sanDangThue);
                newCt_thue.setMaHoaDon(rs.getString(2));
                newCt_thue.setGioThue(rs.getTime(3));
                newCt_thue.setTgDuKienTra(rs.getTime(4));
                getSdDvsOfCtThue(newCt_thue);
                dsCt_Thues.add(newCt_thue);
            }
            System.out.println("Da lay tat ca thong tin CT_THUE !");
            return dsCt_Thues;
        } catch (SQLException ex) {
            System.out.println("Khong the lay thong tin CT_THUE: IODATA.getDsCtSanDangThue");
        }
        return null;
    }
    
    public ArrayList<PHIEUTHUE> getAllPhieuthues_ThanhToan() {
        ArrayList<PHIEUTHUE> dsPhieuThue_ThanhToan = new ArrayList<PHIEUTHUE>();
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT MAHOADON,PHIEUTHUE.CMND,HOTEN,PHIEUTHUE.MANV,TENNV,NGAYLAPPHIEU,TONGPHAITRA\n"
                + "FROM PHIEUTHUE,KHACHTHUE,NHANVIEN\n"
                + "WHERE PHIEUTHUE.CMND = KHACHTHUE.CMND\n"
                + "AND PHIEUTHUE.MANV = NHANVIEN.MANV\n"
                + "AND TRANGTHAI != '.'\n"
                + "ORDER BY NGAYLAPPHIEU DESC";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PHIEUTHUE phieuthue = new PHIEUTHUE();
                KHACH khachThue = new KHACH();
                NHANVIEN nvGhiPhieu = new NHANVIEN();
                
                phieuthue.setMaHoaDon(rs.getString(1));
                khachThue.setCMND(rs.getString(2));
                khachThue.setHoTen(rs.getString(3));
                phieuthue.setKhach(khachThue);
                nvGhiPhieu.setMaNV(rs.getString(4));
                nvGhiPhieu.setTenNV(rs.getString(5));
                phieuthue.setNvGhiPhieu(nvGhiPhieu);
                phieuthue.setNgayGhiPhieu(rs.getDate(6));
                phieuthue.setTongPhaiTra(rs.getInt(7));
                
                dsPhieuThue_ThanhToan.add(phieuthue);
            }
            System.out.println("Da lay tat ca thong tin PHIEUTHUE da thanh toan !");
            return dsPhieuThue_ThanhToan;
        } catch (SQLException ex) {
            System.out.println("Khong the lay thong tin PHIEUTHUE: IODATA.getAllPhieuthues_ThanhToan");
        }
        return null;
    }
   
    public ArrayList<PHIEUTHUE> getLichSuDatSan(String cmnd) {
        ArrayList<PHIEUTHUE> dsPhieuThue_ThanhToan = new ArrayList<PHIEUTHUE>();
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT MAHOADON,PHIEUTHUE.CMND,HOTEN,PHIEUTHUE.MANV,TENNV,NGAYLAPPHIEU,TONGPHAITRA\n"
                + "FROM PHIEUTHUE,KHACHTHUE,NHANVIEN\n"
                + "WHERE PHIEUTHUE.CMND = KHACHTHUE.CMND\n"
                + "AND PHIEUTHUE.MANV = NHANVIEN.MANV\n"
                + "AND TRANGTHAI != '.'\n"
                + "AND PHIEUTHUE.CMND = ?\n"
                + "ORDER BY NGAYLAPPHIEU DESC";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, cmnd);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PHIEUTHUE phieuthue = new PHIEUTHUE();
                KHACH khachThue = new KHACH();
                NHANVIEN nvGhiPhieu = new NHANVIEN();
                
                phieuthue.setMaHoaDon(rs.getString(1));
                khachThue.setCMND(rs.getString(2));
                khachThue.setHoTen(rs.getString(3));
                phieuthue.setKhach(khachThue);
                nvGhiPhieu.setMaNV(rs.getString(4));
                nvGhiPhieu.setTenNV(rs.getString(5));
                phieuthue.setNvGhiPhieu(nvGhiPhieu);
                phieuthue.setNgayGhiPhieu(rs.getDate(6));
                phieuthue.setTongPhaiTra(rs.getInt(7));
                
                dsPhieuThue_ThanhToan.add(phieuthue);
            }
            System.out.println("Da lay tat ca thong tin PHIEUTHUE da thanh toan !");
            return dsPhieuThue_ThanhToan;
        } catch (SQLException ex) {
            System.out.println("Khong the lay thong tin PHIEUTHUE: IODATA.getAllPhieuthues_ThanhToan");
        }
        return null;
    }
    
    public void getSdDvsOfCtThue(CT_THUE ctThue) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT MAHOADON, MASAN, DICHVU.TENDV,DONGIA, SL, TIENDV\n"
                + "FROM SD_DICHVU, DICHVU\n"
                + "WHERE SD_DICHVU.TENDV = DICHVU.TENDV \n"
                + "AND MAHOADON = ? \n"
                + "AND MASAN = ? ";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, ctThue.getMaHoaDon());
            statement.setString(2, ctThue.getSanBong().getMaSan());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {                
                SD_DICHVU sd_dichvu = new SD_DICHVU();
                DICHVU dv = new DICHVU();
                sd_dichvu.setMaHoaDon(rs.getString(1));
                sd_dichvu.setMaSan(rs.getString(2));
                dv.setTenDichVu(rs.getString(3));
                dv.setDonGia(rs.getInt(4));
                sd_dichvu.setDichVu(dv);
                sd_dichvu.setSl(rs.getInt(5));
                sd_dichvu.setTienDv(rs.getInt(6));
                ctThue.getDsDvSuDung().add(sd_dichvu);
            }
        } catch (SQLException ex) {
            System.out.println("Loi khi lay danh sach SD_DichVu: IODATA.getSdDvsOfCtThue");
        }     
    }
    
    public ArrayList<DICHVU> getDsDichVu()
    {
        Connection conn = JDBCCONNECTION.getConnection();
        ArrayList<DICHVU> dsDichVus = new ArrayList<DICHVU>();
        String sql = "SELECT *"
                + "FROM DICHVU \n"
                + "WHERE DANHMUC !=4";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               DICHVU tam = new DICHVU();
                tam.setTenDichVu(rs.getString("TENDV"));
                tam.setDonVi(rs.getString("DONVI"));
                tam.setDonGia(rs.getInt("DONGIA"));
                tam.setDanhmuc(DANHMUC.findDanhMuc(rs.getInt("DANHMUC")));
                dsDichVus.add(tam);
            }
            rs.close();
            ps.close();
            conn.close();
            System.out.println("Da lay het danh sach DICHVU");
            return dsDichVus;
        } catch (SQLException ex) {
            System.out.println("Loi lay danh sach DICHVU: IODATA.getDsDichVu");
        }
        return null;
    }

    public void getAllCt_dattruocs(PHIEUDATTRUOC phieuDT) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM CT_DATTRUOC, SANBONG, LOAISAN\n"
                + "WHERE CT_DATTRUOC.MASAN = SANBONG.MASAN\n"
                + "AND LOAISAN.MALOAISAN = SANBONG.MALOAISAN\n"
                + "AND MADATTRUOC = ?";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, phieuDT.getMaDatTruoc());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CT_DATTRUOC ct_datTruoc = new CT_DATTRUOC();
                SANBONG sanDT = new SANBONG();
                sanDT.setMaSan(rs.getString(2));
                sanDT.setLoaisan(LOAISAN.findLoaiSan(rs.getInt(6)));
                sanDT.setDonGiaSan(rs.getInt(10));
                ct_datTruoc.setTgDuKienDen(rs.getTime(3));
                ct_datTruoc.setTgDuKienTra(rs.getTime(4));
                sanDT.setGiaTheoGio(timHeSo_Kg(KHUNGGIO.findKhungGio(ct_datTruoc.getTgDuKienDen()).getMaKG()));
                ct_datTruoc.setSanDatTruoc(sanDT);
                phieuDT.getDsCtDatTruoc().add(ct_datTruoc);
            }
            System.out.println("Da lay tat ca thong tin CT_DATTRUOC !");
        } catch (SQLException ex) {
            System.out.println("Khong the lay thong tin CT_DATTRUOC: IODATA.getAllCt_dattruocs");
        }
    }
    
    public ArrayList<CT_THUE> getCtThue_MHD(String mhd) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM CT_THUE,SANBONG,LOAISAN\n"
                + "WHERE SANBONG.MASAN = CT_THUE.MASAN\n"
                + "AND SANBONG.MALOAISAN = LOAISAN.MALOAISAN\n"
                + "AND MAHOADON = ?";
        PreparedStatement statement;
        ArrayList<CT_THUE> dsCtThue_PhieuThue = new ArrayList<CT_THUE>();
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, mhd);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CT_THUE ct_thue = new CT_THUE();
                SANBONG sanThue = new SANBONG();
                
                ct_thue.setKhungGio(KHUNGGIO.findKhungGio(rs.getInt(6)));
                sanThue.setMaSan(rs.getString(1));
                sanThue.setLoaisan(LOAISAN.findLoaiSan(rs.getInt(10)));
                sanThue.setDonGiaSan(rs.getInt(14));
                sanThue.setGiaTheoGio(timHeSo_Kg(ct_thue.getKhungGio().getMaKG()));
                ct_thue.setSanBong(sanThue);
                ct_thue.setGioThue(rs.getTime(3));
                ct_thue.setGioTra(rs.getTime(5));
                ct_thue.setSoGioThue(rs.getDouble(7));
                ct_thue.setThanhTien(rs.getInt(8));
                ct_thue.setMaHoaDon(mhd);
                getSdDvsOfCtThue(ct_thue);
                dsCtThue_PhieuThue.add(ct_thue);
            }
            System.out.println("Da lay tat ca thong tin CT_DATTRUOC !");
            return dsCtThue_PhieuThue;
        } catch (SQLException ex) {
            System.out.println("Khong the lay thong tin CT_DATTRUOC: IODATA.getCtThue_MHD");
        }
        return null;
    }
    
    public ArrayList<PHIEUDATTRUOC> getDsPhieuDatTruocHomNay() {
        ArrayList<PHIEUDATTRUOC> dsPhieuDatTruoc = new ArrayList<PHIEUDATTRUOC>();
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM PHIEUDATTRUOC\n"
                + "WHERE PHIEUDATTRUOC.NGAYDAT = CAST(GETDATE() AS date)";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PHIEUDATTRUOC phieuDT = new PHIEUDATTRUOC();
                KHACH khachDT = new KHACH();
                
                phieuDT.setMaDatTruoc(rs.getInt(1));
                khachDT.setCMND(rs.getString(2));
                phieuDT.setkDatTruoc(khachDT);
                phieuDT.setNgayDat(rs.getDate(3));
                getAllCt_dattruocs(phieuDT);
                dsPhieuDatTruoc.add(phieuDT);
            }
            System.out.println("Đã lấy danh sách phiếu đặt trước !");
            return dsPhieuDatTruoc;
        } catch (SQLException ex) {
            System.out.println("Khong the lay danh sách phiếu đặt trước: IODATA.getDsPhieuDatTruoc");
        }
        return null;
    }
    
    public ArrayList<PHIEUDATTRUOC> getAllPhieuDatTruoc() {
        ArrayList<PHIEUDATTRUOC> dsPhieuDatTruoc = new ArrayList<PHIEUDATTRUOC>();
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM PHIEUDATTRUOC";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PHIEUDATTRUOC phieuDT = new PHIEUDATTRUOC();
                KHACH khachDT = new KHACH();
                
                phieuDT.setMaDatTruoc(rs.getInt(1));
                khachDT.setCMND(rs.getString(2));
                phieuDT.setkDatTruoc(khachDT);
                phieuDT.setNgayDat(rs.getDate(3));
                getAllCt_dattruocs(phieuDT);
                dsPhieuDatTruoc.add(phieuDT);
            }
            System.out.println("Đã lấy danh sách phiếu đặt trước !");
            return dsPhieuDatTruoc;
        } catch (SQLException ex) {
            System.out.println("Khong the lay danh sách phiếu đặt trước: IODATA.getDsPhieuDatTruoc");
        }
        return null;
    }
    
    public ArrayList<PHIEUDATTRUOC> getKhachPhieuDatTruocs(String cmnd) {
        ArrayList<PHIEUDATTRUOC> dsPhieuDatTruoc = new ArrayList<PHIEUDATTRUOC>();
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM PHIEUDATTRUOC\n"
                + "WHERE CMND = ?";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, cmnd);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PHIEUDATTRUOC phieuDT = new PHIEUDATTRUOC();
                KHACH khachDT = new KHACH();
                
                phieuDT.setMaDatTruoc(rs.getInt(1));
                khachDT.setCMND(rs.getString(2));
                phieuDT.setkDatTruoc(khachDT);
                phieuDT.setNgayDat(rs.getDate(3));
                getAllCt_dattruocs(phieuDT);
                if (!phieuDT.getDsCtDatTruoc().isEmpty()) {
                    dsPhieuDatTruoc.add(phieuDT);
                }
            }
            System.out.println("Đã lấy danh sách phiếu đặt trước cua tai khoan!");
            return dsPhieuDatTruoc;
        } catch (SQLException ex) {
            System.out.println("Khong the lay danh sách phiếu đặt trước: IODATA.getKhachPhieuDatTruoc");
        }
        return null;
    }
    
    public ArrayList<SANBONG> getDsSanKhaDung(Date ngayDt, Date gioNhanSan, Date gioHenTra) {
        ArrayList<SANBONG> dsSanKhaDung_DatTruoc = new ArrayList<SANBONG>();
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "from SANBONG, LOAISAN\n"
                + "where SANBONG.MALOAISAN = LOAISAN.MALOAISAN\n"
                + "AND SANBONG.TRANGTHAI != 2"
                + "AND MASAN NOT IN (SELECT MASAN\n"
                + "                  FROM CT_DATTRUOC,PHIEUDATTRUOC\n"
                + "                  WHERE PHIEUDATTRUOC.NGAYDAT = ?\n"
                + "                  AND PHIEUDATTRUOC.MADATTRUOC = CT_DATTRUOC.MADATTRUOC \n"
                + "                  AND ((CT_DATTRUOC.TG_DUKIENTRA > ? AND CT_DATTRUOC.TG_DUKIENTRA < ?)\n"
                + "                       OR (CT_DATTRUOC.TG_DUKIENDEN > ? AND CT_DATTRUOC.TG_DUKIENDEN < ?)\n"
                + "                       OR (CT_DATTRUOC.TG_DUKIENDEN <= ? AND CT_DATTRUOC.TG_DUKIENTRA >= ?)))";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, new SimpleDateFormat("YYYY-MM-dd").format(ngayDt));
            statement.setString(2, new SimpleDateFormat("HH:mm:ss").format(gioNhanSan));
            statement.setString(3, new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            statement.setString(4, new SimpleDateFormat("HH:mm:ss").format(gioNhanSan));
            statement.setString(5, new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            statement.setString(6, new SimpleDateFormat("HH:mm:ss").format(gioNhanSan));
            statement.setString(7, new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SANBONG sanKhaDung = new SANBONG();
                sanKhaDung.setMaSan(rs.getString(1));
                sanKhaDung.setLoaisan(LOAISAN.findLoaiSan(rs.getInt(2)));
                sanKhaDung.setDonGiaSan(rs.getInt(6));
                sanKhaDung.setGiaTheoGio(timHeSo_Kg(KHUNGGIO.findKhungGio(new Date()).getMaKG()));
                sanKhaDung.setTrangthai(TRANGTHAI.findTrangThai(rs.getInt(3)));
                dsSanKhaDung_DatTruoc.add(sanKhaDung);
            }
            return dsSanKhaDung_DatTruoc;
        } catch (SQLException ex) {
            System.out.println("Lỗi khi lấy danh sách sân khả dụng đặt trước: IODATA.getDsSanKhaDung_DatTruoc");
        }
        return null;
    }
    
    public String findKhach(String maHoaDon)
    {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT KHACHTHUE.HOTEN \n"
                + "FROM KHACHTHUE, PHIEUTHUE, CT_THUE\n"
                + "WHERE CT_THUE.MAHOADON = PHIEUTHUE.MAHOADON\n"
                + "AND PHIEUTHUE.CMND = KHACHTHUE.CMND\n"
                + "AND CT_THUE.MAHOADON = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,maHoaDon);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                maHoaDon = rs.getString(1);
                System.out.println("Đã tìm thấy HOTEN khách thuê");
                return maHoaDon;
            }
        } catch (SQLException ex) {
            System.out.println("Loi khi tim HOTEN khach hang: IODATA.findKhach");
        }
        return maHoaDon;
    }
    
    public String findKhachDatTruoc(int madattruoc) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT KHACHTHUE.HOTEN\n"
                + "FROM PHIEUDATTRUOC, KHACHTHUE\n"
                + "WHERE PHIEUDATTRUOC.CMND = KHACHTHUE.CMND\n"
                + "AND MADATTRUOC =?";
        String ten = null;
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,madattruoc);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                ten = rs.getString(1);
                System.out.println("Đã tìm thấy HOTEN khách thuê");
                return ten;
            }
        } catch (SQLException ex) {
            System.out.println("Loi khi tim HOTEN khach hang: IODATA.findKhach");
        }
        return null;
    }
    
    public boolean khachIsExist(String CMND)
    {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT * FROM KHACHTHUE WHERE CMND = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, CMND);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Loi khi tim CMND khach hang");
        }
        return false;
    }
    
    public void insertKhachThue(KHACH newKhach){
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "INSERT INTO KHACHTHUE(HOTEN,CMND,SDT) VALUES(?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newKhach.getHoTen());
            statement.setString(2, newKhach.getCMND());
            statement.setString(3, newKhach.getSdt());
            int rs = statement.executeUpdate();
            System.out.println("Da them khach hang moi!");
        } catch (SQLException ex) {
            System.out.println("Loi khi them khach hang moi");
        }
    }
    
    public String sinhMaHoaDon() {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "use QLSANBONG\n"
                + "exec MHD_TuSinh";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            String newMHD = new String();
            while (rs.next()) {                
                newMHD = rs.getString(1);
            }
            System.out.println("Da sinh MHD moi");
            return newMHD;
        } catch (SQLException ex) {
            System.out.println("Sinh MHD moi that bai");;
        }
        return null;
    }
    
    public int sinhMaDatTruoc() {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "use QLSANBONG\n"
                + "exec MDT_TuSinh";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            int newMDT = 0;
            while (rs.next()) {                
                newMDT = rs.getInt(1);
            }
            System.out.println("Da sinh MDT moi");
            return newMDT;
        } catch (SQLException ex) {
            System.out.println("Sinh MDT moi that bai");;
        }
        return 0;
    }
    
    public void insertphieuthue(PHIEUTHUE newPhieuThue) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "INSERT INTO PHIEUTHUE(MAHOADON,CMND,NGAYLAPPHIEU,TRANGTHAI) VALUES(?,?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newPhieuThue.getMaHoaDon());
            statement.setString(2, newPhieuThue.getKhach().getCMND());
            statement.setDate(3, new java.sql.Date(newPhieuThue.getNgayGhiPhieu().getTime()));
            statement.setString(4, ".");
            int rs = statement.executeUpdate();
            System.out.println("Da them PHIEUTHUE moi!");
        } catch (SQLException ex) {
            System.out.println("Loi khi them PHIEUTHUE moi");
        } 
    }
    
    public void insertPhieuDatTruoc(PHIEUDATTRUOC newPhieudt) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "INSERT INTO PHIEUDATTRUOC(MADATTRUOC,CMND,NGAYDAT) VALUES(?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, newPhieudt.getMaDatTruoc());
            statement.setString(2, newPhieudt.getkDatTruoc().getCMND());
            statement.setDate(3, new java.sql.Date(newPhieudt.getNgayDat().getTime()));
            int rs = statement.executeUpdate();
            System.out.println("Da them PHIEUDATTRUOC moi!");
        } catch (SQLException ex) {
            System.out.println("Loi khi them PHIEUDATTRUOC moi");
        } 
    }
    
    public boolean isSanTrong(String maSan)
    {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT * FROM SANBONG WHERE MASAN = ? AND TRANGTHAI = '0'";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, maSan);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Loi khi xac dinh trang thai san" + maSan +": IODATA.isSanTrong");
        }
        return false;
    }
    
    public void updateTrangThaiSan(int trangThai,String maSan)
    {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "UPDATE SANBONG SET TRANGTHAI = ? WHERE MASAN = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, trangThai);
            statement.setString(2, maSan);
            int rs = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Loi khi thay doi trang thai san bong: IODATA.updateTrangThaiSan");
        }       
    }
    
    public void insertSD_DichVu(SD_DICHVU newSdDv){
        Connection conn = JDBCCONNECTION.getConnection();
        String sql ="SELECT * FROM SD_DICHVU WHERE MAHOADON = ? AND MASAN = ? AND TENDV = ?";
        String sql2 ="UPDATE SD_DICHVU SET SL = SL + ?, TIENDV = TIENDV + ? WHERE MAHOADON = ? AND MASAN = ? AND TENDV = ?";
        String sql3 = "INSERT INTO SD_DICHVU VALUES(?,?,?,?,?)";
        try {
            PreparedStatement findSdDv = conn.prepareStatement(sql);
            findSdDv.setString(1, newSdDv.getMaHoaDon());
            findSdDv.setString(2, newSdDv.getMaSan());
            findSdDv.setString(3, newSdDv.getDichVu().getTenDichVu());
            ResultSet rs = findSdDv.executeQuery();
            if (rs.next()) {
                PreparedStatement updateSdDv = conn.prepareStatement(sql2);
                updateSdDv.setInt(1, newSdDv.getSl());
                updateSdDv.setInt(2, newSdDv.getTienDv());
                updateSdDv.setString(3, newSdDv.getMaHoaDon());
                updateSdDv.setString(4, newSdDv.getMaSan());
                updateSdDv.setString(5, newSdDv.getDichVu().getTenDichVu());
                int rs2 = updateSdDv.executeUpdate();
                System.out.println("Đã update dòng SD_DICHVU đã tồn tại");
            }else{
                PreparedStatement insertNewSdDv = conn.prepareStatement(sql3);
                insertNewSdDv.setString(1, newSdDv.getMaHoaDon());
                insertNewSdDv.setString(2, newSdDv.getMaSan());
                insertNewSdDv.setString(3, newSdDv.getDichVu().getTenDichVu());
                insertNewSdDv.setInt(4, newSdDv.getSl());
                insertNewSdDv.setInt(5, newSdDv.getTienDv());
                int rs3 = insertNewSdDv.executeUpdate();
                System.out.println("Đã thêm dòng mới vào bảng SD_DICHVU");
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi khi thêm SD_DICHVU: IODATA.insertSdDichVuExist");
        }
        
    }
    
    public void insertCT_Thue(CT_THUE newCt_thue,String maHoaDon) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "INSERT INTO CT_THUE(MASAN,MAHOADON,GIOTHUE,TG_DUKIENTRA,MAKG) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newCt_thue.getSanBong().getMaSan());
            statement.setString(2, maHoaDon);
            statement.setTime(3, new java.sql.Time(newCt_thue.getGioThue().getTime()));
            statement.setTime(4, new java.sql.Time(newCt_thue.getTgDuKienTra().getTime()));
            statement.setInt(5, newCt_thue.getKhungGio().getMaKG());
            int rs = statement.executeUpdate();
            System.out.println("Da them CT_THUE moi!");
        } catch (SQLException ex) {
            System.out.println("Loi khi them CT_THUE moi");
        }
        updateTrangThaiSan(TRANGTHAI.DANGSUDUNG.getMatrangThai(),newCt_thue.getSanBong().getMaSan());
    }
    
    public void insertCT_DatTruoc(CT_DATTRUOC newCt_datTruoc,int maDT) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "INSERT INTO CT_DATTRUOC(MADATTRUOC,MASAN,TG_DUKIENDEN,TG_DUKIENTRA) VALUES(?,?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(2, newCt_datTruoc.getSanDatTruoc().getMaSan());
            statement.setInt(1, maDT);
            statement.setTime(3, new java.sql.Time(newCt_datTruoc.getTgDuKienDen().getTime()));
            statement.setTime(4, new java.sql.Time(newCt_datTruoc.getTgDuKienTra().getTime()));
            int rs = statement.executeUpdate();
            System.out.println("Da them CT_DATTRUOC moi!");
        } catch (SQLException ex) {
            System.out.println("Loi khi them CT_DATTRUOC moi: IODATA.insertCT_DatTruoc");
        }
    }

    public void updateTtNhanVien(String maNv, String hoTenUpdate, String sdtUpdate) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "UPDATE NHANVIEN SET SDT = ?, TENNV = ?\n"
                + "WHERE MANV = ?";
        try {
            System.out.println(maNv);
            System.out.println(hoTenUpdate);
            System.out.println(sdtUpdate);
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, sdtUpdate);
            statement.setString(2, hoTenUpdate);
            statement.setString(3, maNv);
            int rs = statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Lỗi không thể sủa tt nhân viên: IODATA.updateTtNhanVien()");;
        }
    }

    public void updateTtKhach(String tenTk, String hoTenUpdate, String sdtUpdate) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "UPDATE KHACHTHUE SET SDT = ?, HOTEN = ?\n"
                + "WHERE TAIKHOAN = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, sdtUpdate);
            statement.setString(2, hoTenUpdate);
            statement.setString(3, tenTk);
            int rs = statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Lỗi không thể sủa tt nhân viên: IODATA.updateTtNhanVien()");;
        }
    }
    
    public void updateMkTaiKhoan(String tenTk, String mkMoi) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "UPDATE DSTAIKHOAN SET MATKHAU = ?\n"
                + "WHERE TAIKHOAN = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, mkMoi);
            statement.setString(2, tenTk);
            int rs = statement.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Lỗi không thể sủa mk nhân viên: IODATA.updateMkNhanVien()");;
        }      
    }
    
    public double timHeSo_Kg(int maKg){
        Connection conn = JDBCCONNECTION.getConnection();
        String timHeSosql = "SELECT HESO \n"
                + "FROM GIATHEOGIO\n"
                + "WHERE MAKG = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(timHeSosql);
            statement.setInt(1, maKg);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {                
                double heSo = rs.getDouble(1);
                return heSo;
            }
        } catch (SQLException ex) {
            System.out.println("Không thể xác định hệ số khung giờ: IODATA.timHeSo_Kg");;
        }   
        return 0;
    }
    
    public void traSanCT_Thue(CT_THUE sanTra) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "UPDATE CT_THUE SET GIOTRA = ?, SOGIOTHUE = ?, THANHTIEN = ?\n"
                + "WHERE MAHOADON = ? AND MASAN = ?";
        try {
            //Truy vấn tìm giá sân theo tên sân
            int donGiaSan = sanTra.getSanBong().getDonGiaSan();
            
            //ìm hệ số giá sân theo mã khung giờ
            double heSo = timHeSo_Kg(sanTra.getKhungGio().getMaKG());
  
            System.out.println(sanTra.getSoGioThue()*donGiaSan*heSo);
            //Trả sân
            PreparedStatement statement2 = conn.prepareStatement(sql);
            statement2.setTime(1,new java.sql.Time(sanTra.getGioTra().getTime()));
            statement2.setDouble(2, sanTra.getSoGioThue());
            int thanhtien = (int)(sanTra.getSoGioThue()*donGiaSan*heSo);
            thanhtien = thanhtien+(1000-(thanhtien%1000));
            statement2.setInt(3,thanhtien);
            statement2.setString(4, sanTra.getMaHoaDon());
            statement2.setString(5, sanTra.getSanBong().getMaSan());
            int rs2 = statement2.executeUpdate();
            updateTrangThaiSan(TRANGTHAI.TRONG.getMatrangThai(), sanTra.getSanBong().getMaSan());
            System.out.println("Đã ghi lại thông tin trả sân");
        } catch (SQLException ex) {
            System.out.println("Lỗi khi ghi thông tin trả sân: IODATA.traSanCT_Thue");
        }
    }

    public String isTrungGio(String maSan, Date gioHenTra) {
        Connection conn = JDBCCONNECTION.getConnection();
        String tgDuKienDen = null;
        String sql = "select *\n"
                + "from PHIEUDATTRUOC, CT_DATTRUOC\n"
                + "where PHIEUDATTRUOC.MADATTRUOC = CT_DATTRUOC.MADATTRUOC\n"
                + "AND PHIEUDATTRUOC.NGAYDAT = CAST(GETDATE() AS date)\n"
                + "AND CT_DATTRUOC.MASAN = ?\n"
                + "AND DATEDIFF(MI,CT_DATTRUOC.TG_DUKIENDEN,CAST(GETDATE() AS time))<=15\n"
                + "AND ((CT_DATTRUOC.TG_DUKIENTRA <= ? AND CT_DATTRUOC.TG_DUKIENTRA >= CAST(GETDATE() AS time))\n"
                + "    OR (CT_DATTRUOC.TG_DUKIENDEN <= ? AND CT_DATTRUOC.TG_DUKIENDEN >= CAST(GETDATE() AS time)))";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, maSan);
            statement.setString(2,  new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            statement.setString(3,  new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                tgDuKienDen = new SimpleDateFormat("HH:mm a").format(rs.getTime(6));
                return tgDuKienDen;
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi xác định trùng giờ đặt trước: IODATA.isTrungGio");
        }
        return tgDuKienDen;
    }

    public void xetXoaPhieuDt(PHIEUDATTRUOC phieuBiXoa) {
        Connection conn = JDBCCONNECTION.getConnection();
        String xoaPhieu = "delete from PHIEUDATTRUOC WHERE MADATTRUOC =? ";
        String timCtDt = "SELECT * FROM CT_DATTRUOC WHERE MADATTRUOC = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(timCtDt);
            statement.setInt(1, phieuBiXoa.getMaDatTruoc());
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                PreparedStatement xoaStatement = conn.prepareStatement(xoaPhieu);
                xoaStatement.setInt(1, phieuBiXoa.getMaDatTruoc());
                int i = xoaStatement.executeUpdate();
                System.out.println("Đã xóa phiếu đặt trước");
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi không thể xóa phiếu đặt trước");
        }
    }
    
    public void xoaCtDatTruoc(CT_DATTRUOC ctDtBiXoa, int maDt){
        Connection conn = JDBCCONNECTION.getConnection();
        String xoaCtDatTruoc ="DELETE FROM CT_DATTRUOC WHERE MADATTRUOC =? AND MASAN = ?";
        try {
            PreparedStatement statement1 = conn.prepareStatement(xoaCtDatTruoc);
            statement1.setInt(1, maDt);
            statement1.setString(2, ctDtBiXoa.getSanDatTruoc().getMaSan());
            int rs = statement1.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Lỗi không thể xóa chi tiết đặt trước");
        }        
    }
    
    public PHIEUTHUE getTtThanhToan(String maHoaDon) {//luu
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT MAHOADON,KHACHTHUE.CMND,NGAYLAPPHIEU,HOTEN\n"
                + "FROM PHIEUTHUE, KHACHTHUE\n"
                + "WHERE KHACHTHUE.CMND=PHIEUTHUE.CMND\n"
                + "AND MAHOADON = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, maHoaDon);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                PHIEUTHUE phieuThanhToan = new PHIEUTHUE();
                KHACH khachThanhToan = new KHACH();
                khachThanhToan.setCMND(rs.getString(2));
                khachThanhToan.setHoTen(rs.getString(4));
                phieuThanhToan.setKhach(khachThanhToan);
                phieuThanhToan.setMaHoaDon(rs.getString(1));
                phieuThanhToan.setNgayGhiPhieu(rs.getDate(3));
                phieuThanhToan.setCtThue(getCtThue_MHD(phieuThanhToan.getMaHoaDon()));
                phieuThanhToan.setTongPhaiTra();
                return phieuThanhToan;
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi lấy thông tin thanh toán: IODATA.getTtThanhToan");
        }
        return null;
    }
    
    public void thanhToan(PHIEUTHUE phieuThanhToan){
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "UPDATE PHIEUTHUE SET TRANGTHAI = 'Đã thanh toán',MANV = ?,TONGPHAITRA = ?\n"
                + "WHERE MAHOADON = ?";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(2, phieuThanhToan.getTongPhaiTra());
            statement.setString(1, phieuThanhToan.getNvGhiPhieu().getMaNV());
            statement.setString(3, phieuThanhToan.getMaHoaDon());
            int rs = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Lỗi xác nhận thanh toán: IODATA.thanhToan");;
        }
    }
    
    public boolean isTraHet(String maHoaDon) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM CT_THUE\n"
                + "WHERE MAHOADON = ?\n"
                + "AND THANHTIEN IS NULL";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, maHoaDon);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            System.out.println("Lỗi kiểm tra phiếu thuê đã trả hết sân: IODATA.isTraHet");;
        }
        return false; 
    }
    
    public boolean isKhaDung(Date ngayDt, Date gioNhanSan, Date gioHenTra, String maSan){
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT MASAN\n"
                + "FROM CT_DATTRUOC,PHIEUDATTRUOC\n"
                + "WHERE PHIEUDATTRUOC.NGAYDAT = ?\n"
                + "AND PHIEUDATTRUOC.MADATTRUOC = CT_DATTRUOC.MADATTRUOC \n"
                + "AND MASAN = ?\n"
                + "AND ((CT_DATTRUOC.TG_DUKIENTRA > ? AND CT_DATTRUOC.TG_DUKIENTRA < ?)\n"
                + "   OR (CT_DATTRUOC.TG_DUKIENDEN > ? AND CT_DATTRUOC.TG_DUKIENDEN < ?)\n"
                + "   OR (CT_DATTRUOC.TG_DUKIENDEN <= ? AND CT_DATTRUOC.TG_DUKIENTRA >= ?))";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, new SimpleDateFormat("YYYY-MM-dd").format(ngayDt));
            statement.setString(2, maSan);
            statement.setString(3, new SimpleDateFormat("HH:mm:ss").format(gioNhanSan));
            statement.setString(4, new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            statement.setString(5, new SimpleDateFormat("HH:mm:ss").format(gioNhanSan));
            statement.setString(6, new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            statement.setString(7, new SimpleDateFormat("HH:mm:ss").format(gioNhanSan));
            statement.setString(8, new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi xác định sân khả dụng đặt trước: IODATA.isKhaDung");
        }
        return true;
    }

    public boolean isKhaDungDatNgay(Date gioNhanSan, Date gioHenTra, String maSan) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT MASAN\n"
                + "FROM CT_THUE,PHIEUTHUE\n"
                + "WHERE PHIEUTHUE.NGAYLAPPHIEU = CAST(GETDATE() AS date)\n"
                + "AND PHIEUTHUE.MAHOADON = CT_THUE.MAHOADON\n"
                + "AND MASAN = ?\n"
                + "AND CT_THUE.GIOTRA IS NULL \n"
                + "AND ((CT_THUE.TG_DUKIENTRA > ? AND CT_THUE.TG_DUKIENTRA < ?)\n"
                + "   OR (CT_THUE.GIOTHUE > ? AND CT_THUE.GIOTHUE< ?)\n"
                + "   OR (CT_THUE.GIOTHUE <= ? AND CT_THUE.TG_DUKIENTRA >= ?))";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, maSan);
            statement.setString(2, new SimpleDateFormat("HH:mm:ss").format(gioNhanSan));
            statement.setString(3, new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            statement.setString(4, new SimpleDateFormat("HH:mm:ss").format(gioNhanSan));
            statement.setString(5, new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            statement.setString(6, new SimpleDateFormat("HH:mm:ss").format(gioNhanSan));
            statement.setString(7, new SimpleDateFormat("HH:mm:ss").format(gioHenTra));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi xác định sân khả dụng đặt trước: IODATA.isKhaDung");
        }
        return true;
    }
    
    public NHANVIEN getNhanVienLogIn(String manv) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM NHANVIEN, DSTAIKHOAN\n"
                + "WHERE MANV = ?\n"
                + "AND MANV = TAIKHOAN";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, manv);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {                
                NHANVIEN nv = new NHANVIEN();
                nv.setMaNV(rs.getString(1));
                nv.setTenNV(rs.getString(2));
                nv.setSdt(rs.getString(4));
                nv.setCMND(rs.getString(3));
                nv.setMatKhau(rs.getString(6));
                return nv;
            }
        } catch (SQLException ex) {
            System.out.println("Loi khi lay thong tin dang nhap nhan vien");
        }
        return null;
    }

    public KHACH getKhachLogIn(String tenTk) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM KHACHTHUE, DSTAIKHOAN\n"
                + "WHERE KHACHTHUE.TAIKHOAN = ?\n"
                + "AND KHACHTHUE.TAIKHOAN = DSTAIKHOAN.TAIKHOAN";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tenTk);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {                
                KHACH khach = new KHACH();
                khach.setHoTen(rs.getString(1));
                khach.setCMND(rs.getString(2));
                khach.setSdt(rs.getString(3));
                khach.setTenTk(rs.getString(4));
                khach.setMatKhau(rs.getString(6));
                return khach;
            }
        } catch (SQLException ex) {
            System.out.println("Loi khi xác thực khách đăng nhập");
        }
        return null;
    }
    
    public static void huyCtDatTruoc(int maDt,String maSan) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "EXEC SP_HUYCTDATTRUOC ?,?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, maDt);
            statement.setString(2, maSan);
            int rs = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERROR:"+ex.getMessage()+" IODATA.getKhachLogIn()");
        }
    }
    
    public static int xacThucLogin(String tenTaiKhoan, String matKhau)
    {
        int chucVu=0;
        Connection conn = JDBCCONNECTION.getConnection();
        String sql="select TAIKHOAN,MATKHAU,CHUCVU from DSTAIKHOAN where TAIKHOAN=? and MATKHAU=?";
        try
        {
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setString(1, tenTaiKhoan);
            ps.setString(2, matKhau);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                if(rs.getString(3).compareTo("QL")==0)
                    chucVu=1;
                else if(rs.getString(3).compareTo("NV")==0)
                    chucVu=2;
                else if(rs.getString(3).compareTo("KHACH")==0)
                    chucVu=3;
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Loi xac thuc tai khoan ");
        }
        return chucVu;
    }

    public static boolean taiKhoanExist(String tenTk) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT * FROM DSTAIKHOAN\n"
                + "WHERE TAIKHOAN = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tenTk);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;   
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi kiểm tra tài khoản: IODATA.taiKhoanExist()");
        }
        return false;
    }

    public void themTaiKhoanMoi(KHACH newKhach, String tenTk, String mk) {
        String sql = "INSERT INTO DSTAIKHOAN VALUES(?,?,?)";
        String sql2 = "UPDATE KHACHTHUE SET TAIKHOAN = ? WHERE CMND =?";
        Connection conn = JDBCCONNECTION.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tenTk);
            statement.setString(2, mk);
            statement.setString(3, "KHACH");
            int rs = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Lỗi đăng ký tài khoản: IODATA.themTaiKhoanMoi()");
        }
        
        if (khachIsExist(newKhach.getCMND())) {
            updateTtKhach(tenTk, newKhach.getHoTen(), newKhach.getSdt());
        } else {
            insertKhachThue(newKhach);
        }
        
        PreparedStatement statement2;
        try {
            statement2 = conn.prepareStatement(sql2);
            statement2.setString(1, tenTk);
            statement2.setString(2, newKhach.getCMND());
            int rs = statement2.executeUpdate();
        } catch (SQLException ex) {
           System.out.println("Lỗi đăng ký tài khoản statment2: IODATA.themTaiKhoanMoi()");
        }
    }
    
    public TRANGTHAI trangThaiSan(String maSan){
        String sql = "SELECT TRANGTHAI FROM SANBONG WHERE MASAN = ?";
        Connection conn = JDBCCONNECTION.getConnection();
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, maSan);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println(TRANGTHAI.findTrangThai(Integer.parseInt(rs.getString(1))));
                return TRANGTHAI.findTrangThai(Integer.parseInt(rs.getString(1)));
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi xác định trạng thái sân: IODATA.trangThaiSan()");
        }
        return null;
    }

    public int tienSdDv(String maSan, String maHoaDon) {
        String sql = "SELECT SUM(TIENDV)\n"
                + "FROM SD_DICHVU\n"
                + "WHERE MASAN = ?\n"
                + "AND MAHOADON = ?";
        
        Connection conn = JDBCCONNECTION.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, maSan);
            statement.setString(2, maHoaDon);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi lấy tiền dịch vụ: IODATA.tienSdDv()");
        }
        return 0;
    }

    public DICHVU getDichvu(String tendv) {
        Connection conn = JDBCCONNECTION.getConnection();
        String sql = "SELECT *\n"
                + "FROM DICHVU\n"
                + "WHERE TENDV =?\n"
                + "AND DANHMUC !=4";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tendv);
            ResultSet rs =statement.executeQuery();
            if (rs.next()) {
                DICHVU dv = new DICHVU();
                dv.setTenDichVu(rs.getString(1));
                dv.setDonVi(rs.getString(2));
                dv.setDonGia(rs.getInt(3));
                dv.setDanhmuc(DANHMUC.findDanhMuc(rs.getInt(4)));
                return dv;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IODATA.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
}
