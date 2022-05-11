package khach;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ad
 */
public class THONGTINKHACH {
    private String HoTen;
    private String CMND;
    private String SDT;
    private String taiKhoan;
    private String matKhau;

    public THONGTINKHACH() {
    }

    public THONGTINKHACH(String taiKhoan) {
        this.taiKhoan = taiKhoan;
        loadThongTin(taiKhoan);
    }

    
    
    
    
    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void loadThongTin(String taiKhoan)
    {
        Connection ketNoi = KetNoiMSSQL.layKetNoi();
        String sql = "select HOTEN,CMND,SDT from KHACHTHUE where KHACHTHUE.TAIKHOAN ='"+taiKhoan+"'";
        
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            this.HoTen= rs.getString("HOTEN").trim();
            this.CMND=rs.getString("CMND").trim();
            this.SDT=rs.getString("SDT").trim();
           
           
            rs.close();
            ps.close();
            //ketNoi.close();
        } catch (SQLException ex) {
            System.out.println("loi o ham Load Thong tin ca nhan 1");
        }
        
        
        String sql1="select TAIKHOAN,MATKHAU from DSTAIKHOAN where DSTAIKHOAN.TAIKHOAN ='"+taiKhoan+"'";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql1);
            ResultSet rs = ps.executeQuery();
            rs.next();
            this.taiKhoan= rs.getString("TAIKHOAN").trim();
            this.matKhau= rs.getString("MATKHAU").trim();
            
           
            rs.close();
            ps.close();
            ketNoi.close();
        } catch (SQLException ex) {
            System.out.println("loi o ham Load Thong tin ca nhan 2");
        }
        
    }
    public void luuThongTin()
    {
        Connection ketNoi = KetNoiMSSQL.layKetNoi();
        String sql = "insert into KHACHTHUE values(?,?,?,?)";
        
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1, HoTen);
            ps.setString(2, CMND);
            ps.setString(3, SDT);
            ps.setString(4, taiKhoan);
            ps.executeUpdate();
            ps.close();
            //ketNoi.close();
        } catch (SQLException ex) {
            System.out.println("loi o ham Load Thong tin ca nhan 1");
        }
        
    }
    public void updateThongTin()
    {
       Connection conn=KetNoiMSSQL.layKetNoi();
        String sql = "UPDATE KHACHTHUE SET HOTEN = ?,SDT=? WHERE CMND=?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, HoTen);
            statement.setString(2, SDT);
            statement.setString(3, CMND);
            int rs = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Loi khi thay doi trang thai san bong: IODATA.updateTrangThaiSan");
        }       
    }    
    
}
