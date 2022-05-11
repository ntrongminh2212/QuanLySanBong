/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author zLittleMasterz
 */
public class PHIEUDATTRUOC {
    private int maDatTruoc;
    private KHACH kDatTruoc;
    private Date ngayDat;
    private ArrayList <CT_DATTRUOC> dsCtDatTruoc = new ArrayList<CT_DATTRUOC>();

    public PHIEUDATTRUOC() {
    }   

    public PHIEUDATTRUOC(int maDatTruoc, KHACH kDatTruoc, Date ngayDat, ArrayList<CT_DATTRUOC> ctDatTruoc) {
        this.maDatTruoc = maDatTruoc;
        this.kDatTruoc = kDatTruoc;
        this.ngayDat = ngayDat;
        this.dsCtDatTruoc = ctDatTruoc;
    }
    
    public KHACH getkDatTruoc() {
        return kDatTruoc;
    }

    public void setkDatTruoc(KHACH kDatTruoc) {
        this.kDatTruoc = kDatTruoc;
    }

    public ArrayList<CT_DATTRUOC> getDsCtDatTruoc() {
        return dsCtDatTruoc;
    }

    public void setCtDatTruoc(ArrayList<CT_DATTRUOC> ctDatTruoc) {
        this.dsCtDatTruoc = ctDatTruoc;
    }

    public int getMaDatTruoc() {
        return maDatTruoc;
    }

    public void setMaDatTruoc(int maDatTruoc) {
        this.maDatTruoc = maDatTruoc;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }
    
}
