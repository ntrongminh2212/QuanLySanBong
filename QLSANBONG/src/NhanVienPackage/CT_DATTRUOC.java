/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author zLittleMasterz
 */
public class CT_DATTRUOC {
    private SANBONG sanDatTruoc;
    private Date tgDuKienDen;
    private Date tgDuKienTra;
    
    public CT_DATTRUOC() {
    }

    public SANBONG getSanDatTruoc() {
        return sanDatTruoc;
    }

    public void setSanDatTruoc(SANBONG sanDatTruoc) {
        this.sanDatTruoc = sanDatTruoc;
    }

    public Date getTgDuKienDen() {
        return tgDuKienDen;
    }

    public void setTgDuKienDen(Date tgDuKienDen) {
        this.tgDuKienDen = tgDuKienDen;
    }

    public Date getTgDuKienTra() {
        return tgDuKienTra;
    }

    public void setTgDuKienTra(Date tgDuKienTra) {
        this.tgDuKienTra = tgDuKienTra;
    }
    
    public int isTime(Date clockTime){
        long time = this.tgDuKienDen.getTime() - clockTime.getTime();
        double phut = time/(60 * 1000);
        
        if (phut >=5) {
            return 1;
        }
        else if (phut <= -15) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
