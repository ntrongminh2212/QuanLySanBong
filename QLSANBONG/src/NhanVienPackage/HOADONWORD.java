/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author zLittleMasterz
 */
public class HOADONWORD {
    public static File inHoaDon(PHIEUTHUE phieuThanhToan){
        XWPFDocument hoaDon = new XWPFDocument();
        XWPFParagraph noiDung = hoaDon.createParagraph();
        XWPFRun run = noiDung.createRun();
        SimpleDateFormat dateForm = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm a");
        String space = "                                            ";
        run.addBreak();
        run.addBreak();
        run.setText("Mã hóa đơn: "); run.setText(phieuThanhToan.getMaHoaDon()); run.addBreak();
        run.setText("Khách thuê: "); run.setText(phieuThanhToan.getKhach().getHoTen()); run.addBreak();
        run.setText("Ngày giờ thuê: "); run.setText(dateForm.format(phieuThanhToan.getNgayGhiPhieu())); run.setText("   ");
        run.setText(timeForm.format(phieuThanhToan.getCtThue().get(0).getGioThue())); run.addBreak();
        run.setText("Nhân viên phục vụ: "); run.setText(phieuThanhToan.getNvGhiPhieu().getTenNV().trim()); run.addBreak(); run.addBreak();
        run.setText("---------------------------------------------------------------------------"); run.addBreak();
        run.addBreak();
        run.setText("Sân thuê          ");  
        run.setText("Giờ trả                ");
        run.setText("Số giờ               "); 
        run.setText("Thành tiền ");
        run.addBreak();
        for (CT_THUE ctSanThue : phieuThanhToan.getCtThue()) {
            run.setText("     "+ctSanThue.getSanBong().getMaSan()+"              ");
            run.setText(timeForm.format(ctSanThue.getGioTra())+"              ");
            run.setText(String.valueOf(ctSanThue.getSoGioThue())+"                       ");
            run.setText(String.valueOf(ctSanThue.getThanhTien()));
            run.addBreak();
        }
        run.addBreak();
        run.setText("---------------------------------------------------------------------------"); run.addBreak();
        run.setText("                        CHI TIẾT DỊCH VỤ"); run.addBreak();
        run.setText("Sân thuê           ");  
        run.setText("Dịch vụ               ");
        run.setText("Số lượng          "); 
        run.setText("Thành tiền");
        run.addBreak();
        int i = 0;
        for (CT_THUE ctSanThue : phieuThanhToan.getCtThue()) {
            if (!ctSanThue.getDsDvSuDung().isEmpty()) {
                run.setText("     " + ctSanThue.getSanBong().getMaSan() + ":" + space.substring(1, 18 - (ctSanThue.getDsDvSuDung().get(0).getDichVu().getTenDichVu().length() / 2)));
            }
            for (SD_DICHVU ctDv : ctSanThue.getDsDvSuDung()) {
                if (i!=0) {
                    run.setText(space.substring(1, (18 - ctDv.getDichVu().getTenDichVu().length()/2)*2));
                }
                run.setText(ctDv.getDichVu().getTenDichVu() + space.substring(1, (15 - ctDv.getDichVu().getTenDichVu().length()/2)*2));
                run.setText(String.valueOf(ctDv.getSl()) + "                      ");
                run.setText(String.valueOf(ctDv.getTienDv()));
                run.addBreak();
                i++;
            }
            run.addBreak();
            i = 0;
        }
        run.setText("---------------------------------------------------------------------------"); run.addBreak();
        run.setFontSize(14);
        run.setText("                                                                     TỔNG:    ");
        run.setText(String.valueOf(phieuThanhToan.getTongPhaiTra()));
        
        File f = new File("D:\\hoadon.docx");
        try {
            FileOutputStream docx = new FileOutputStream(f);
            hoaDon.write(docx);
            docx.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Không thể tạo file hóa đơn: HOADONWORD.inHoaDon, dòng 90");;
        } catch (IOException ex){
            System.out.println("Không thể ghi hóa đơn: HOADONWORD.inHoaDon, dòng 92");
        }
        return f;
    }
}
