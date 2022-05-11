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

/**
 *
 * @author ad
 */
public class LICHSUTHUE {
    private int maHoaDon;
    private String cmnd;
    private String ngayLapPhieu;
    private String trangThai;
    private long tongTien;
    
    private void layThongTin()
    {
         Connection ketNoi = KetNoiMSSQL.layKetNoi();
        String sql = "select MAHOADON, CMND, NGAYLAPPHIEU,TRANGTHAI from PHIEUTHUE where PHIEUTHUE.CMND='"+cmnd+"'";
        
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            this.maHoaDon= rs.getInt("MAHOADON");
            this.cmnd=rs.getString("CMND").trim();
            this.ngayLapPhieu=rs.getString("NGAYLAPPHIEU").trim();
            this.trangThai=rs.getString("TRANGTHAI").trim();
           
            rs.close();
            ps.close();
            ketNoi.close();
        } catch (SQLException ex) {
            System.out.println("loi o ham lay thong tin lich su thue");
        }
    }
    private void tinhTongTien()
    {
        
    }
    
    
}
