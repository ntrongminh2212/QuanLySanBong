/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

/**
 *
 * @author zLittleMasterz
 */
public class CLOCKNV extends Thread{
    private Date clockDate;
    private Date clockTime;
    private SimpleDateFormat ngayForm = new SimpleDateFormat("E, dd/MM/yyyy");
    private SimpleDateFormat gioForm = new SimpleDateFormat("HH:mm:ss a");
    private JLabel lblngay;
    private JLabel lblgio;
    private JRootPane rootPane;
    private IODATA iodata;
    private GDNHANVIEN gdnhanvien;
    
    public CLOCKNV(JLabel lblngay, JLabel lblgio,JRootPane rootPane,IODATA iodata, GDNHANVIEN gdnhanvien ){
        this.lblgio = lblgio;
        this.lblngay = lblngay;
        this.rootPane = rootPane;
        this.iodata = iodata;
        clockDate = new Date();
        clockTime = new Date();
        this.gdnhanvien = gdnhanvien;
    }
    
    public void loaiBoCtThueMuon() {
        for (PHIEUDATTRUOC phieudattruoc : gdnhanvien.getDsPhieuDTs()) {
            for (CT_DATTRUOC ctDt : phieudattruoc.getDsCtDatTruoc()) {
                if (ctDt.isTime(clockTime) == -1) {
                    iodata.xoaCtDatTruoc(ctDt, phieudattruoc.getMaDatTruoc());
                    iodata.xetXoaPhieuDt(phieudattruoc);
                    gdnhanvien.refreshTblDsPhieuDt();
                    gdnhanvien.thongBaoXoaCtDt(ctDt);
                }
            }
        }
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
