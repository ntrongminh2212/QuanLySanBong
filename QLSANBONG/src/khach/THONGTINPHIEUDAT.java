package khach;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
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
public class THONGTINPHIEUDAT {
    private int maDatTruoc;
    private String CMND;
    private String ngayDat;
    public Date ngayDatDate;

    public ArrayList<CTDATTRUOC> DS= new ArrayList<CTDATTRUOC>();

    public THONGTINPHIEUDAT() {
       layMaDatTruoc();
    }
    
    
    public THONGTINPHIEUDAT(int MADATTRUOC)
    {
        this.maDatTruoc= MADATTRUOC;
        layThongTinPhieuDat();
    }
    
    public int getMaDatTruoc() {
        return maDatTruoc;
    }

    public void setMaDatTruoc(int maDatTruoc) {
        this.maDatTruoc = maDatTruoc;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public ArrayList<CTDATTRUOC> getDS() {
        return DS;
    }

    public void setDS(ArrayList<CTDATTRUOC> DS) {
        this.DS = DS;
    }
    
    public static void xoaThongTinPhieuDat(int maPhieuDat)
    {
        Connection ketNoi= KetNoiMSSQL.layKetNoi();
        String sql2="delete from PHIEUDATTRUOC where MADATTRUOC=?";
        try
        {
            PreparedStatement ps= ketNoi.prepareStatement(sql2);
            ps.setInt(1, maPhieuDat);
           ps.executeUpdate();
        }
        catch(SQLException ex)
        {
            System.out.println("loi xoa PHIEUDATTRUOC");
        }
    }
    public static int kiemTraSLPhieuDatTruoc(int maPhieuDat)
    {
        Connection ketNoi= KetNoiMSSQL.layKetNoi();
        String sql2 ="select COUNT(MADATTRUOC) as count from CT_DATTRUOC where MADATTRUOC = ?";
        System.out.println("ma phieu dat: "+maPhieuDat);
        int dem = 1000;
        try
        {
            PreparedStatement ps=ketNoi.prepareStatement(sql2);
            ps.setInt(1, maPhieuDat);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                dem=rs.getInt(1);
            System.out.println("dem: "+dem);
            rs.close();
            ps.close();
            return dem;
        }
        catch(SQLException ex)
        {
            System.out.println("loi ham kiem tra sl phieudattruoc ben file thong tin dattruoc");
        }
        return dem;
    }
    
    
    public void layThongTinPhieuDat()
    {
        CTDATTRUOC tam;
        Connection ketNoi = KetNoiMSSQL.layKetNoi();
        String sql = "select CT_DATTRUOC.MADATTRUOC,CT_DATTRUOC.MASAN,tenloaisan,tg_dukienden,tg_dukientra\n" +
                        "from CT_DATTRUOC,SANBONG,LOAISAN\n" +
                            "where CT_DATTRUOC.MASAN= SANBONG.MASAN and SANBONG.MALOAISAN=LOAISAN.MALOAISAN and CT_DATTRUOC.MADATTRUOC='"+maDatTruoc+"'";
        
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("Ham lay thong tin phieu dat");
            while (rs.next()) {
                tam= new CTDATTRUOC();
                tam.setMaDatTruoc(rs.getInt("MADATTRUOC"));
                tam.setTenSan(rs.getString("MASAN"));
                tam.setTenLoaiSan(rs.getString("TENLOAISAN"));
                tam.setTgDuKienDen(rs.getString("TG_DUKIENDEN"));
                tam.setTgDuKienTra(rs.getString("TG_DUKIENTRA"));
               
                DS.add(tam);
            }
           
            rs.close();
            ps.close();
            ketNoi.close();
        } catch (SQLException ex) {
            System.out.println("loi ham Thong tin phieu dat 1");
        }
        
        
        
        
    }
    
    public void layMaDatTruoc()
    {
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
        String sql="select MAX(madattruoc) from PHIEUDATTRUOC ";
        int maPhieuDat=0;
       try
       {
           PreparedStatement ps= ketNoi.prepareStatement(sql);
           ResultSet rs=ps.executeQuery();
          
           rs.next();
           
           maPhieuDat=rs.getInt(1);
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi ham lay ma dat truoc");
       }
       
       this.maDatTruoc= ++maPhieuDat;
       
    }
    public void luuPhieuDat()
    {
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
        int maDatTruoc=this.maDatTruoc;
       String sql="insert into PHIEUDATTRUOC values (?,?,?)";
       
       try
       {
           PreparedStatement ps= ketNoi.prepareStatement(sql);
           ps.setInt(1, maDatTruoc);
           ps.setString(2,CMND);
           ps.setString(3, this.ngayDat);
       
           ps.executeUpdate();
       }
       catch(SQLException ex)
       {
           System.out.println("loi ham luu phieu dat");
       }
    }
}
