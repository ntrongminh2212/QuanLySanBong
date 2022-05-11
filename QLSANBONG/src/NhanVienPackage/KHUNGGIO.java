/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zLittleMasterz
 */
public enum KHUNGGIO{
    BUOISANG(1,"Buổi sáng (06:00-12:00)","06:00","11:59"),
    BUOICHIEU(2,"Buổi chiều (12:00-18:00)","12:00","17:59"),
    BUOITOI(3,"Buổi tối (18:00-23:00)","18:00","23:00");
    
    private int maKG;
    private String KG;
    private String startHour;
    private String endHour;

    private KHUNGGIO(int maKG, String KG, String startHour, String endHour) {
        this.maKG = maKG;
        this.KG = KG;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getMaKG() {
        return maKG;
    }

    public void setMaKG(int maKG) {
        this.maKG = maKG;
    }

    public String getKG() {
        return KG;
    }

    public void setKG(String khungGio) {
        this.KG = khungGio;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public static KHUNGGIO findKhungGio(String kg){
        for (KHUNGGIO khungGio : KHUNGGIO.values()) {
            if (kg.compareTo(khungGio.KG)==0) {
                return khungGio;
            }
        }
        return null;
    }
    
    public static KHUNGGIO findKhungGio(int maKg){
        for (KHUNGGIO khungGio : KHUNGGIO.values()) {
            if (maKg==khungGio.maKG) {
                return khungGio;
            }
        }
        return null;
    }
    
    public static KHUNGGIO findKhungGio(Date now)
    {
        SimpleDateFormat formatGio = new SimpleDateFormat("HH:mm");
        Date start = new Date();
        Date end = new Date();
        String nowHour = formatGio.format(now.getTime());
        for (KHUNGGIO kg : KHUNGGIO.values()) {
            try {
                Date nowTemp = formatGio.parse(nowHour);
                start = formatGio.parse(kg.getStartHour());
                end = formatGio.parse(kg.getEndHour());
  
                if (nowTemp.after(start) && nowTemp.before(end) || (nowTemp.equals(start) || (nowTemp.equals(end)))) {
                    return kg;
                }
            } catch (ParseException ex) {
                System.out.println("Loi xac dinh KHUNGGIO: KHUNGGIO.autoKhungGio");
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return KG;
    }
}
