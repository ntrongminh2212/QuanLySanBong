/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GIAODIENQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QUANLY {
    public String tenQL;
    public String CMNDQL;
    public String taiKhoan;
    public String matKhau;
    
    public QUANLY(){
        
    }
    public QUANLY(String taiKhoan){
        this.taiKhoan = taiKhoan;
        loadThongTinQL(taiKhoan);
    }

    public String getTenQL() {
        return tenQL;
    }

    public void setTenQL(String tenQL) {
        this.tenQL = tenQL;
    }

    public String getCMNDQL() {
        return CMNDQL;
    }

    public void setCMNDQL(String CMNDQL) {
        this.CMNDQL = CMNDQL;
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
    
    public void loadThongTinQL(String taiKhoan){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql = "select TENNV,CMND from NHANVIEN where NHANVIEN.MANV ='"+taiKhoan+"'";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            this.tenQL  = rs.getString("TENNV").trim();
            this.CMNDQL=rs.getString("CMND").trim();
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Lỗi hàm loadTTQL");
        }
        
        String sql1="select TAIKHOAN,MATKHAU from DSTAIKHOAN where DSTAIKHOAN.TAIKHOAN ='"+taiKhoan+"'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql1);
            ResultSet rs = ps.executeQuery();
            rs.next();
            this.taiKhoan= rs.getString("TAIKHOAN").trim();
            this.matKhau= rs.getString("MATKHAU").trim();
            
           
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("lỗi hàm loadTTQL2");
        }     
    }
    
    public void luuQuanLy(){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="insert into DSQUANLY values(?,?,?)";
        try {     
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, CMNDQL);
            ps.setString(2, tenQL);
            ps.setString(3, taiKhoan);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
    public void xoaQuanLy(){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="delete from DSQUANLY where TAIKHOAN=?";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, taiKhoan);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
        xoaTKQL();
    }
    
    public void xoaTKQL(){
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="delete from DSTAIKHOAN where TAIKHOAN=?";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, taiKhoan);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
    
}

