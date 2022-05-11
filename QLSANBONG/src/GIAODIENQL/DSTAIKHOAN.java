/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GIAODIENQL;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ad
 */
public class DSTAIKHOAN {
    public static int kiemTraChucVuTaiKhoan(String tenTaiKhoan, String matKhau)
    {
        int chucVu=0;
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select TAIKHOAN,MATKHAU,CHUCVU from DSTAIKHOAN  where TAIKHOAN='"+tenTaiKhoan+"' and MATKHAU='"+matKhau+"'";
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                if(rs.getString("CHUCVU").equals("QUANLY"))
                {
                    chucVu=1;
                }
                else if(rs.getString("CHUCVU").equals("NHANVIEN"))
                {
                    chucVu=2;
                }
                else if(rs.getString("CHUCVU").trim().equals("KHACH"))
                {
                    chucVu=3;
                }
               
            }
            
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex)
        {
            System.out.println("loi kiem tra ");
        }
        return chucVu;
    }
    public static boolean kiemTraDSTK(String TaiKhoan)
    {
        boolean tonTai=false;
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select TAIKHOAN from DSTAIKHOAN  where TAIKHOAN='"+TaiKhoan+"'";
        try{
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex){
            System.out.println("loi kiem tra ");
        }
        return tonTai;
    }
    
    //QUẢN LÝ
    public static void luuQuanLy(String CMND, String tenQL, String taiKhoan){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="insert into DSQUANLY values(?,?,?)";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, CMND);
            ps.setString(2, tenQL);
            ps.setString(3, taiKhoan);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
        }
    }
    
    public static void luuDSTK_QuanLy(String taiKhoan,String matKhau, String tenQL, String CMND){
        
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="insert into DSTAIKHOAN values(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,taiKhoan);           
            ps.setString(2, matKhau); 
            ps.setString(3, "QL");
            
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("loi ham luu DSTK_QL");
        }
        luuNhanVien(taiKhoan, tenQL, CMND, "");
    }
    
    public static void suaQuanLy(String tenQL, String CMND, String taiKhoan){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="update NHANVIEN set TENNV=N'"+tenQL+"', CMND='"+CMND+"' where MANV='"+taiKhoan+"'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);           
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Lỗi hàm sửa thông tin cá nhân QL!");
        }
    }
    
    public static void xoaQuanLy(String taiKhoan){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="delete from NHANVIEN where MANV=?";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, taiKhoan);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        xoaTKQL(taiKhoan);
    }
    
    public static void xoaTKQL(String taiKhoan){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="delete from DSTAIKHOAN where TAIKHOAN=?";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, taiKhoan);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Loi ham xoaTKQL"+ e.getMessage());
        }
    }
    
    public static void xoaTKQL_DHD(String taiKhoan, String chucVu){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="UPDATE DSTAIKHOAN SET CHUCVU='"+chucVu+"' where TAIKHOAN='"+taiKhoan+"'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);           
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("loi ham xóa QL đã nghỉ!");
        }
    }
    public static boolean kiemTraQuanLy(String taiKhoan){
        boolean tonTai=false;
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="select * from DSQUANLY where TAIKHOAN='"+taiKhoan+"'";
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex)
        {
            
        }
        return tonTai;
    }
    public static boolean kiemTraQL_DHD(String taiKhoan){
        boolean tonTai=false;
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select MANV from PHIEUTHUE where MANV='"+taiKhoan+"'";
        try{
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex){
            System.out.println("loi kiem tra QL HD ");
        }
        return tonTai;
    }
    
    //NHÂN VIÊN
    
    public static void luuNhanVien(String maNV, String tenNV, String CMND, String SDT){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="insert into NHANVIEN values(?,?,?,?)";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.setString(2, tenNV);
            ps.setString(3, CMND);
            ps.setString(4, SDT);
            
            ps.executeUpdate();
            ps.close();
            connection.close();
        }
        catch(SQLException ex){   
            System.out.println("Lỗi không thể lưu nhân viên: DSTAIKHOAN.luuNhanVien()");
        }
    }
    
    public static void luuDSTK_NhanVien(String maNV, String matKhau, String tenNV, String CMND, String SDT){
        
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="insert into DSTAIKHOAN values(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,maNV);           
            ps.setString(2, matKhau); 
            ps.setString(3, "NV");
            
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("loi ham luu DSTK_NV");
        }
        luuNhanVien(maNV, tenNV, CMND, SDT);
    }
    
//    public static void xoaNhanvien(String maNV){
//        Connection connection = JDBCCONNECTION.getConnection();
//        String sql="delete from NHANVIEN where MANV=?";
//        try {
//            PreparedStatement ps= connection.prepareStatement(sql);
//            ps.setString(1, maNV);
//            ps.executeUpdate();
//            ps.close();
//            connection.close();
//        } catch (SQLException e) {
//        }
//    }
    public static void suaNhanVien(String maNV, String tenNV, String CMND, String SDT, String matKhau){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="update NHANVIEN set TENNV= N'"+tenNV+"',CMND='"+CMND+"',SDT='"+SDT+"' where MANV='"+maNV+"'";
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            connection.close();           
        } catch (Exception e) {
            System.out.println("Lỗi hàm sửa NV!");
        }
        suaMKNV(maNV, matKhau);
    }
    public static void suaMKNV(String maNV, String matKhau){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="update DSTAIKHOAN set MATKHAU='"+matKhau+"' where TAIKHOAN='"+maNV+"'";
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            connection.close();           
        } catch (Exception e) {
            System.out.println("Lỗi hàm sửa MKNV!");
        }
    }
    
    public static void xoaNhanVien_TKNV(String maNV){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="delete from NHANVIEN where MANV=?";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
        }
        xoaTKNV(maNV);
    }
    
    public static void xoaTKNV(String maNV){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="delete from DSTAIKHOAN where TAIKHOAN=?";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Loi ham xoaTKNV");
        }
    }
    public static void xoaTKNV_DHD(String maNV, String chucVu){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="UPDATE DSTAIKHOAN SET CHUCVU='"+chucVu+"' where TAIKHOAN='"+maNV+"'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);           
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("loi ham xóa QL đã nghỉ!");
        }
    }
    
    public static boolean kiemTraNhanVien_CMND(String CMND){
        boolean tonTai=false;
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="select CMND from NHANVIEN where CMND='"+CMND+"'";
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Lỗi hàm kiểm tra CMND_NV");
        }
        return tonTai;
    }
    
    public static boolean kiemTraNhanVien_SDT(String SDT){
        boolean tonTai=false;
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="select SDT from NHANVIEN where SDT='"+SDT+"'";
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Lỗi hàm kiểm tra SDT_NV");
        }
        return tonTai;
    }
    public static boolean kiemTraNhanVien(String maNV){
        boolean tonTai=false;
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="select MANV from NHANVIEN where MANV='"+maNV+"'";
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Lỗi hàm kiểm tra NV");
        }
        return tonTai;
    }
    
    public static boolean kiemTraDSTK_NV(String maNV)
    {
        boolean tonTai=false;
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select TAIKHOAN from DSTAIKHOAN  where TAIKHOAN='"+maNV+"'";
        try{
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex){
            System.out.println("loi kiem tra ");
        }
        return tonTai;
    }
    
    public static boolean kiemTraNV_DHD(String maNV){
        boolean tonTai=false;
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select MANV from PHIEUTHUE where MANV='"+maNV+"'";
        try{
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex){
            System.out.println("loi kiem tra NV HD ");
        }
        return tonTai;
    }
    
    
    
    
    //KHÁCH THUÊ
    public static void luuKhachThue(String hoTen, String CMND, String SDT, String taiKhoan)
    {
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="insert into KHACHTHUE values(?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,hoTen);
            ps.setString(2, CMND);
            ps.setString(3, SDT);
            ps.setString(4,taiKhoan);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("loi ham luu KHACHTHUE");
        }
    }
    public static void luuDSTK_Khach(String taiKhoan,String matKhau, String hoTen, String CMND, String SDT)
    {
        
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="insert into DSTAIKHOAN values(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,taiKhoan);           
            ps.setString(2, matKhau); 
            ps.setString(3, "KHACH");
            
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("loi ham luu DSTK_Khach");
        }
        luuKhachThue(hoTen, CMND, SDT, taiKhoan);
        
    }
    
    public static void suaDSTaiKhoan(String taiKhoan, String matKhau)
    {
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="UPDATE DSTAIKHOAN SET MATKHAU='"+matKhau+"'where TAIKHOAN='"+taiKhoan+"'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);           
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("loi ham sửa ds tai khoan");
        }
    }
    
    
    //DỊCH VỤ
    
    public static void luuDichVu(String tenDV,String donVi, int giaTien, int maDanhMuc){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="insert into DICHVU values(?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,tenDV);
            ps.setString(2, donVi);
            ps.setInt(3, giaTien);
            ps.setInt(4, maDanhMuc);
            
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Lỗi hàm lưu DV");
        }
    }
    public static void xoaDichVu(String tenDV){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="delete from DICHVU where TENDV=?";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, tenDV);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Lỗi hàm xóa DV");
        }  

    }
    
    public static void suaDichVu(String tenDV,String donVi, int giaTien, int danhMuc){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="UPDATE DICHVU SET DONGIA='"+giaTien+"',DONVI=N'"+donVi+"',DANHMUC="+danhMuc+" where TENDV= N'"+tenDV+"'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);           
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("loi ham sửa gia DV!");
        }
    }    
    public static void suaDichVu_DM(String tenDV,String donVi, int giaTien, int maDanhMuc){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="UPDATE DICHVU SET DONGIA='"+giaTien+"',DONVI=N'"+donVi+"',DANHMUC='"+maDanhMuc+"' where TENDV='"+tenDV+"'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);           
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("loi ham sửa DV_DM!");
        }
    }
    public static void xoaDichVu_DSD(String tenDV,int maDanhMuc){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="UPDATE DICHVU SET DANHMUC= ? where TENDV=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);   
            ps.setInt(1, maDanhMuc);
            ps.setString(2, tenDV);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("loi ham xóa DV_DSD!");
        }
        
    } 
    public static boolean kiemTraDV(String tenDV){
        boolean tonTai=false;
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="select TENDV from DICHVU where TENDV=N'"+tenDV+"'";
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Lỗi hàm kiểm tra DV");
        }
        return tonTai;
    }
    
    public static boolean kiemTraDV_DSD(String tenDV){
        boolean tonTai=false;
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="select TENDV from SD_DICHVU where TENDV=N'"+tenDV+"'";
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Lỗi hàm kiểm tra DV");
        }
        return tonTai;
    }
    
    //SÂN BÓNG
    public static void luuSanBong(String maSan,int maLoaiSan, int trangThai){        
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="insert into SANBONG values(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maSan);
            ps.setInt(2, maLoaiSan);
            ps.setInt(3, trangThai);
            
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Lỗi hàm lưu San Bong");  
        }
    }
    
    public static void suaTrangThaiSan(String maSan, String trangThai){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="update SANBONG set TRANGTHAI='"+trangThai+"' where MASAN='"+maSan+"'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);           
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Lỗi hàm đổi TT sân bóng!");
        }
    }
    
    public static void suaGiaSan(String loaiSan, int giaTien){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="UPDATE LOAISAN set GIA='"+giaTien+"' where TENLOAISAN='"+loaiSan+"'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);           
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Lỗi hàm sửa giá sân bóng!");
        }
    }
    
    public static boolean kiemTraSB(String maSan){
        boolean tonTai=false;
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select MASAN from SANBONG where MASAN='"+maSan+"'";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Lỗi hàm kiểm tra SB");
        }
        return tonTai;
    }
    
    //HỆ SỐ KHUNG GIỜ
    public static void suaHSKhungGio(String khungGio, float heSo, int maKG){
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="update GIATHEOGIO set KHUNGGIO=N'"+khungGio+"',HESO=N'"+heSo+"' where MAKG='"+maKG+"'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);           
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("lỗi hàm sửa HSKG!");
        }
    }
    

}
