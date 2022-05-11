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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author zLittleMasterz
 */
public class CLOCKKHACH extends Thread{
    private Date clockDate;
    private Date clockTime;
    private SimpleDateFormat ngayForm = new SimpleDateFormat("E, dd/MM/yyyy");
    private SimpleDateFormat gioForm = new SimpleDateFormat("HH:mm:ss a");
    private JLabel lblngay;
    private JLabel lblgio;
    private JRootPane rootPane;
    private GDKHACH gdnhanvien;
    private DSPHIEUDATTRUOC phieuDat;
    
    
    public CLOCKKHACH(JLabel lblngay, JLabel lblgio,JRootPane rootPane, GDKHACH gdnhanviex ){
        this.lblgio = lblgio;
        this.lblngay = lblngay;
        this.rootPane = rootPane;
        clockDate = new Date();
        clockTime = new Date();
        this.gdnhanvien = gdnhanviex;
        phieuDat =new DSPHIEUDATTRUOC(this.gdnhanvien.khach.getCMND());
       
    }
    
    
    
    public void loaiBoCtThueMuon(){
        SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayHienTai = new Date();
        try {
            ngayHienTai = dbDateFormat.parse(dbDateFormat.format(new Date()));
            System.out.println(ngayHienTai);
        } catch (ParseException ex) {
            Logger.getLogger(CLOCKKHACH.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (THONGTINPHIEUDAT phieudattruoc : phieuDat.DS) {

            for (CTDATTRUOC ctDt : phieudattruoc.DS){ 
                System.out.println(phieudattruoc.ngayDatDate);
                if (!phieudattruoc.ngayDatDate.after(ngayHienTai)) {
                    if (ctDt.isTime(clockTime) == -1) {
                        ctDt.xoaCTDatTruoc();
                    }
                }
            }
        }
        for(THONGTINPHIEUDAT phieudattruoc : phieuDat.DS)
        {
            if(THONGTINPHIEUDAT.kiemTraSLPhieuDatTruoc(phieudattruoc.getMaDatTruoc())==0)
            {
                THONGTINPHIEUDAT.xoaThongTinPhieuDat(phieudattruoc.getMaDatTruoc());
            }
        }
        this.gdnhanvien.settbThongTinDatTruoc_TTCN();
    }
   
    public Date getClockDate() {
        return clockDate;
    }

    public Date getClockTime() {
        return clockTime;
    }

    @Override
    public void run() {
        String ngay = ngayForm.format(clockDate);
        lblngay.setText(ngay);
        while (true) {
            try {
                clockTime = new Date();
                String gio = gioForm.format(clockTime);
                clockTime = gioForm.parse(gio);
                lblgio.setText(gio);
                
                loaiBoCtThueMuon();
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Lỗi đồng hồ: CLOCK.run()");
            } catch (ParseException ex) {
                System.out.println("Lỗi đồng hồ: CLOCK.run() 64");
            }
        }
    }
}
