package khach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CTDATTRUOC {
    private int maDatTruoc;
    private String tenSan;
    private String tenLoaiSan;
    private String tgDuKienDen;
    private String tgDuKienTra;
    

   

    public int getMaDatTruoc() {
        return maDatTruoc;
    }

    public void setMaDatTruoc(int maDatTruoc) {
        this.maDatTruoc = maDatTruoc;
    }

    public String getTenSan() {
        return tenSan;
    }

    public void setTenSan(String tenSan) {
        this.tenSan = tenSan;
    }

    public String getTenLoaiSan() {
        return tenLoaiSan;
    }

    public void setTenLoaiSan(String tenLoaiSan) {
        this.tenLoaiSan = tenLoaiSan;
    }

    public String getTgDuKienDen() {
        return tgDuKienDen;
    }

    public void setTgDuKienDen(String tgDuKienDen) {
        this.tgDuKienDen = tgDuKienDen;
    }

    public String getTgDuKienTra() {
        return tgDuKienTra;
    }

    public void setTgDuKienTra(String tgDuKienTra) {
        this.tgDuKienTra = tgDuKienTra;
    }
    
   public int isTime(Date clockTime){
        long time = Integer.parseInt(this.tgDuKienDen.substring(0,2))- clockTime.getHours();
        System.out.println("long time : "+ time);
        double phut = clockTime.getMinutes();
        System.out.println("Phuts "+ phut);
        if (time==0&& phut >=15) {
            return -1;
        }
        else if(time <0)
        {
            return -1;
        }
        else {
            return 0;
        }
    }
  
    public void luuCTPhieuDat()
    {
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
       String sql="insert into CT_DATTRUOC values (?,?,?,?)";
       
       try
       {
           PreparedStatement ps= ketNoi.prepareStatement(sql);
           ps.setInt(1, maDatTruoc);
           ps.setString(2,tenSan);
           ps.setString(3, tgDuKienDen);
           ps.setString(4,tgDuKienTra);
           
           ps.executeUpdate();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi ham luu thong tin ct dat truoc");
       }
    }
    public void xoaCTDatTruoc()
    {
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
        String sql="delete from CT_DATTRUOC where MADATTRUOC=? and MASAN=?";
        try
        {
            PreparedStatement ps= ketNoi.prepareStatement(sql);
            ps.setInt(1, maDatTruoc);
            ps.setString(2,tenSan);
           ps.executeUpdate();
        }
        catch(SQLException ex)
        {
            System.out.println("loi xoa CTDATTRUOC");
        }
    }
    public static void xoaCTDatTruocAll(int maDatTruoc)
    {
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
        String sql="delete from CT_DATTRUOC where MADATTRUOC=?";
        try
        {
            PreparedStatement ps= ketNoi.prepareStatement(sql);
            ps.setInt(1, maDatTruoc);
           
           ps.executeUpdate();
        }
        catch(SQLException ex)
        {
            System.out.println("loi xoa CTDATTRUOC all");
        }
    }   
}
