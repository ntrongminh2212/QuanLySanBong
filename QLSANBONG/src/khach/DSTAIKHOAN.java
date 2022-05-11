/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ad
 */
public class DSTAIKHOAN {
    public static int kiemTraChucVuTaiKhoan(String tenTaiKhoan, String matKhau)
    {
        int chucVu=0;
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
        String sql="select TAIKHOAN,MATKHAU,CHUCVU from DSTAIKHOAN  where TAIKHOAN='"+tenTaiKhoan+"' and MATKHAU='"+matKhau+"'";
        try
        {
            PreparedStatement ps= ketNoi.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                if(rs.getString("CHUCVU").equals("QL"))
                {
                    chucVu=1;
                }
                else if(rs.getString("CHUCVU").equals("NV"))
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
            ketNoi.close();
        }
        catch(SQLException ex)
        {
            System.out.println("loi kiem tra ");
        }
        return chucVu;
    }
    public static boolean kiemTraDanhSachTaiKhoan(String tenTaiKhoan)
    {
        boolean tonTai=false;
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
        String sql="select TAIKHOAN from DSTAIKHOAN  where TAIKHOAN='"+tenTaiKhoan+"'";
        try
        {
            PreparedStatement ps= ketNoi.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                tonTai=true;
            }
            rs.close();
            ps.close();
            ketNoi.close();
        }
        catch(SQLException ex)
        {
            System.out.println("loi kiem tra ");
        }
        return tonTai;
    }
    public static void luuKhachThue(String hoTen, String CMND, String SDT, String taiKhoan)
    {
        Connection ketNoi= KetNoiMSSQL.layKetNoi();
        String sql="insert into KHACHTHUE values(?,?,?,?)";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1,hoTen);
            ps.setString(2, CMND);
            ps.setString(3, SDT);
            ps.setString(4,taiKhoan);
            ps.executeUpdate();
            ps.close();
            ketNoi.close();
        } catch (SQLException ex) {
            System.out.println("loi ham luu KHACHTHUE");
        }
    }
    public static void luuDSTaiKhoan(String taiKhoan,String matKhau, String hoTen, String cmnd, String SDT)
    {
        
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
        String sql="insert into DSTAIKHOAN values(?,?,?)";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1,taiKhoan);
            
            ps.setString(2, matKhau);
            
            
            ps.setString(3, "KHACH");
            
            ps.executeUpdate();
            ps.close();
            ketNoi.close();
        } catch (SQLException ex) {
            System.out.println("loi ham luu DSTK1");
        }
        luuKhachThue(hoTen, cmnd, SDT,taiKhoan);
        
    }
    
    public static void suaDSTaiKhoan(String taiKhoan, String matKhau)
    {
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
        String sql="UPDATE DSTAIKHOAN SET MATKHAU='"+matKhau+"'where TAIKHOAN='"+taiKhoan+"'";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
           
            ps.executeUpdate();
            ps.close();
            ketNoi.close();
        } catch (SQLException ex) {
            System.out.println("loi ham luu DSTK1");
        }
    }
}
