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
import java.util.ArrayList;

/**
 *
 * @author ad
 */
public class DSPHIEUDATTRUOC {
    public ArrayList<THONGTINPHIEUDAT> DS= new ArrayList<THONGTINPHIEUDAT>();
    
    public DSPHIEUDATTRUOC(String cmnd)
    {
        layThongTin(cmnd);
    }
    public void layThongTin(String cmnd)
    {
        THONGTINPHIEUDAT tam;
        Connection ketNoi = KetNoiMSSQL.layKetNoi();
        String sql = "select MADATTRUOC, CMND, NGAYDAT\n" +
                        "from PHIEUDATTRUOC\n" +
                            "where CMND='"+cmnd+"'";
        
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               tam= new THONGTINPHIEUDAT();
                tam.setMaDatTruoc(rs.getInt("MADATTRUOC"));
                
                tam.setCMND(rs.getString("CMND"));
                tam.setNgayDat(rs.getString("NGAYDAT"));
                tam.ngayDatDate=rs.getDate("NGAYDAT");
                tam.layThongTinPhieuDat();
                DS.add(tam);
            }
           
            rs.close();
            ps.close();
            ketNoi.close();
        } catch (SQLException ex) {
            System.out.println("loi ham DS phieu dat 1");
        }
    }
    
    
}
