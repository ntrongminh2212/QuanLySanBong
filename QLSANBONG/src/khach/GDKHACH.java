/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khach;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import NhanVienPackage.IODATA;
/**
 *
 * @author ad
 */
public class GDKHACH extends javax.swing.JFrame {

    /**
     * Creates new form KHACH
     */
    private CLOCKKHACH clock;
    private SimpleDateFormat formmatNgay = new SimpleDateFormat("yyyy-MM-dd");
    public THONGTINKHACH khach=new THONGTINKHACH();
    private THONGTINPHIEUDAT thongTinPhieuDat;
    public GDKHACH() {
        
        initComponents();
        this.setLocationRelativeTo(null);
        pnDatTruocSan.setVisible(true);
        pnThongTinSanBong.setVisible(false);
        pnDichVu.setVisible(false);
        pnThongTinCaNhan.setVisible(false);
        
        pnThongTinDatTruoc.setVisible(false);
        pnLichSuThue.setVisible(false);
        
        
        pnSan5A.setBackground(new Color(153,255,153,150));
        pnSan7A.setBackground(new Color(153,255,153,150));
        pnSan5B.setBackground(new Color(153,255,153,150));
        pnSan7B.setBackground(new Color(153,255,153,150));
        pnSan5C.setBackground(new Color(153,255,153,150));
        pnSan11A.setBackground(new Color(153,255,153,150));
        
        
        khach.loadThongTin("tongkaka");
        setThongTin();
        clock = new CLOCKKHACH(lblNgayHomNay, lblGioHienTai,rootPane, GDKHACH.this);
        clock.start();
        
    }
    public GDKHACH(String taiKhoan) {
        
        initComponents();
        this.setLocationRelativeTo(null);
        pnDatTruocSan.setVisible(true);
        pnThongTinSanBong.setVisible(false);
        pnDichVu.setVisible(false);
        pnThongTinCaNhan.setVisible(false);
        
        pnThongTinDatTruoc.setVisible(false);
        pnLichSuThue.setVisible(false);
        
        
        pnSan5A.setBackground(new Color(153,255,153,150));
        pnSan7A.setBackground(new Color(153,255,153,150));
        pnSan5B.setBackground(new Color(153,255,153,150));
        pnSan7B.setBackground(new Color(153,255,153,150));
        pnSan5C.setBackground(new Color(153,255,153,150));
        pnSan11A.setBackground(new Color(153,255,153,150));
        
        
        khach.loadThongTin(taiKhoan);
        System.out.println("Tai khoan: "+ khach.getTaiKhoan());
        
        setThongTin();
        clock = new CLOCKKHACH(lblNgayHomNay, lblGioHienTai,rootPane, this);
        clock.start();
        
    }
     
    
   
    public void loadLichSuThue()
    {
        DefaultTableModel dtm=(DefaultTableModel) tbLichSuThue_LichSuThue.getModel();
       dtm.setNumRows(0);
       
       Connection ketNoi=KetNoiMSSQL.layKetNoi();
       String sql="select PHIEUTHUE.MAHOADON,PHIEUTHUE.NGAYLAPPHIEU,PHIEUTHUE.TONGPHAITRA from PHIEUTHUE where PHIEUTHUE.CMND='"+khach.getCMND()+"'";
       Vector vt;
       
       try
       {
           PreparedStatement ps= ketNoi.prepareStatement(sql);
           ResultSet rs= ps.executeQuery();
           while(rs.next())
           {
               vt =new Vector();
               vt.add(rs.getString("MAHOADON"));
               vt.add(rs.getString("NGAYLAPPHIEU"));
               vt.add(rs.getString("TONGPHAITRA"));
               
               dtm.addRow(vt);
              
               
           }
           
           tbLichSuThue_LichSuThue.setModel(dtm);
           
           rs.close();
           ps.close();
           ketNoi.close();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi load lichsuthue");
       }
    }
     public void loadSanBong_TTSB(String a)
    {
        DefaultTableModel dtm=(DefaultTableModel) tbSanBong_ThongTinSanBong.getModel();
       dtm.setNumRows(0);
       
       Connection ketNoi=KetNoiMSSQL.layKetNoi();
       String sql="select SANBONG.MASAN,LOAISAN.TENLOAISAN,LOAISAN.GIA from SANBONG,LOAISAN where SANBONG.MALOAISAN=LOAISAN.MALOAISAN";
       String sql1="select SANBONG.MASAN,LOAISAN.TENLOAISAN,LOAISAN.GIA from SANBONG,LOAISAN where SANBONG.MALOAISAN=LOAISAN.MALOAISAN and sanbong.maloaisan=1";
       String sql2="select SANBONG.MASAN,LOAISAN.TENLOAISAN,LOAISAN.GIA from SANBONG,LOAISAN where SANBONG.MALOAISAN=LOAISAN.MALOAISAN and sanbong.maloaisan=2";
       String sql3="select SANBONG.MASAN,LOAISAN.TENLOAISAN,LOAISAN.GIA from SANBONG,LOAISAN where SANBONG.MALOAISAN=LOAISAN.MALOAISAN and sanbong.maloaisan=3";
       Vector vt;
       
       try
       {
           PreparedStatement ps;
       
           if(a.equals("Sân 5"))
           {
                ps= ketNoi.prepareStatement(sql1);
           }
           else if(a.equals("Sân 7"))
           {
               ps= ketNoi.prepareStatement(sql2);
           }
           else if(a.equals("Sân 11"))
           {
               ps= ketNoi.prepareStatement(sql3);
           }
           else 
           {
               ps= ketNoi.prepareStatement(sql);
           }
           ResultSet rs= ps.executeQuery();
           while(rs.next())
           {
               vt =new Vector();
               vt.add(rs.getString("MASAN"));
               vt.add(rs.getString("TENLOAISAN"));
               vt.add(rs.getString("GIA"));
               dtm.addRow(vt);
              
           }
           
          
           tbSanBong_ThongTinSanBong.setModel(dtm);
           rs.close();
           ps.close();
           ketNoi.close();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi set san bong");
       }
    }
     
    public void loadHeSoTienTheoKhungGio()
    {
         Connection ketNoi=KetNoiMSSQL.layKetNoi();
       String sql="select GIATHEOGIO.HESO,GIATHEOGIO.MAKG from GIATHEOGIO";
       try
       {
           PreparedStatement ps= ketNoi.prepareStatement(sql);
           ResultSet rs= ps.executeQuery();
           while (rs.next())
           {
               if(rs.getString("MAKG").equals("1"))
               {
                   txtSang_ThongTinSanBong.setText("x"+rs.getString("HESO"));
               }
               else if(rs.getString("MAKG").equals("2"))
               {
                   txtChieu_ThongTinSanBong.setText("x"+rs.getString("HESO"));
               }
               else if(rs.getString("MAKG").equals("3"))
               {
                   txtToi_ThongTinSanBong.setText("x"+rs.getString("HESO"));
               }
           }
       }
       catch(SQLException ex)
       {
           System.out.println("loi loadHeSoTien");
       }
    }
    public String layLoaiSanTuTenSan(String tenSan)
    {
        String LoaiSan="";
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
       String sql="select LOAISAN.TENLOAISAN from SANBONG,LOAISAN where SANBONG.MALOAISAN=LOAISAN.MALOAISAN and SANBONG.MASAN='"+tenSan+"'";
       try
       {
           PreparedStatement ps=ketNoi.prepareStatement(sql);
           ResultSet rs=ps.executeQuery();
           rs.next();
           LoaiSan=rs.getString("TENLOAISAN");
           ps.close();
           rs.close();
           ketNoi.close();
       }
       catch(SQLException ex)
       {
           System.out.println("loi lay ten loai san tu ten san");
       }
       return LoaiSan;
    }
    
    public void setThucUong()
    {
        System.out.println("set thuc uong");
        DefaultTableModel dtm=(DefaultTableModel) tbThucUong_DichVu.getModel();
       dtm.setNumRows(0);
       
       Connection ketNoi=KetNoiMSSQL.layKetNoi();
       String sql="select * from DICHVU,DANHMUC where DICHVU.DANHMUC=DANHMUC.MADANHMUC and (DANHMUC.MADANHMUC= 1 or  DANHMUC.MADANHMUC=2)";
       Vector vt;
       
       try
       {
           PreparedStatement ps= ketNoi.prepareStatement(sql);
           ResultSet rs= ps.executeQuery();
           while(rs.next())
           {
               vt =new Vector();
               vt.add(rs.getString("TENDV"));
               vt.add(rs.getString("DONGIA"));
               
               dtm.addRow(vt);
               
           }
           
           tbThucUong_DichVu.setModel(dtm);
           rs.close();
           ps.close();
           ketNoi.close();
           
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi set thuc uong");
       }
       
       
       
    }
    public void setDichVuKhac()
    {
       DefaultTableModel dtm=(DefaultTableModel) tbDichVuKhac_DichVu.getModel();
       dtm.setNumRows(0);
       
       Connection ketNoi=KetNoiMSSQL.layKetNoi();
       String sql="select * from DICHVU where DICHVU.DANHMUC=3";
       Vector vt;
      
       try
       {
           PreparedStatement ps= ketNoi.prepareStatement(sql);
           ResultSet rs= ps.executeQuery();
           while(rs.next())
           {
               vt =new Vector();
               vt.add(rs.getString("TENDV"));
               vt.add(rs.getString("DONGIA"));
               
               dtm.addRow(vt);
               
               
           }
           
           tbDichVuKhac_DichVu.setModel(dtm);
           
           rs.close();
           ps.close();
           ketNoi.close();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi set dich vu khac");
       }
    }
   
    
    public void setThongTin()
    {
        System.out.println("setthongtin");
        lbTenTaiKhoan.setText(khach.getTaiKhoan());
        
        setpnCTTTCN_TTCN();
        setpnSuaThongTin_TTCN();
        settbThongTinDatTruoc_TTCN();
        setThucUong();
        setDichVuKhac();
        loadSanBong_TTSB("Select");
        loadHeSoTienTheoKhungGio();
        loadLichSuThue();
       
        loadCBLoaiSan_DatSan();
        setGioDenHienTai();
        
        
       
        setDatTruocSan(cbLoaiSan_DatSan.getSelectedItem().toString(), dcNgay_DatSan.getDate(),cbGioDen_DatSan.getSelectedItem().toString(), cbGioTra_DatSan.getSelectedItem().toString());
    }
    
    
    public void setpnCTTTCN_TTCN()
    {
        System.out.println("hàm setpnCTTTCN_TTTCN");
        lbHoTen_CTTTCN_TTCN.setText(khach.getHoTen());
        lbCMND_CTTTCN_TTCN.setText(khach.getCMND());
        lbSDT_CTTTCN_TTCN.setText(khach.getSDT());
    }
    public void setpnSuaThongTin_TTCN()
    {
        System.out.println("setpnSuaThongTin_TTCN");
        txtHoTen_SuaThongTin_TTCN.setText(khach.getHoTen());
        txtSDT_SuaThongTin_TTCN.setText(khach.getSDT());
    }
    public void settbThongTinDatTruoc_TTCN()
    {
        System.out.println("hàm settbThongTinDatTruoc_TTCN");
       DSPHIEUDATTRUOC dsPhieuDatTruoc= new DSPHIEUDATTRUOC(khach.getCMND());
       for(THONGTINPHIEUDAT a: dsPhieuDatTruoc.DS)
       {
           Date ngayHienTai= new Date();
           if(a.ngayDatDate.getDate()-ngayHienTai.getDate()<0)
           {
               CTDATTRUOC.xoaCTDatTruocAll(a.getMaDatTruoc());
               THONGTINPHIEUDAT.xoaThongTinPhieuDat(a.getMaDatTruoc());
           }
           
       }
       DefaultTableModel dtm=(DefaultTableModel) tbThongTinDatTruoc_TTDT.getModel();
       dtm.setNumRows(0);
        Vector vt;
       for(int i=0;i<dsPhieuDatTruoc.DS.size();i++)
       {
           vt=new Vector();
           vt.add(dsPhieuDatTruoc.DS.get(i).getMaDatTruoc());
           vt.add(dsPhieuDatTruoc.DS.get(i).getNgayDat());
           dtm.addRow(vt);
       }
       tbThongTinDatTruoc_TTDT.setModel(dtm);
        if (tbThongTinDatTruoc_TTDT.getRowCount()==0) {
            DefaultTableModel tbl =(DefaultTableModel) tbCTDatTruoc_TTDT.getModel();
            tbl.setRowCount(0);
        }
    }
    public void settbCTDatTruoc_TTCN(int maDatTruoc)
    {
        System.out.println("Hàm SetTbctDatTruoc_ttcn");
       thongTinPhieuDat= new THONGTINPHIEUDAT(maDatTruoc);
       DefaultTableModel dtm=(DefaultTableModel) tbCTDatTruoc_TTDT.getModel();
       dtm.setNumRows(0);
        Vector vt;
       for(int i=0;i<thongTinPhieuDat.DS.size();i++)
       {
           vt=new Vector();
           vt.add(thongTinPhieuDat.DS.get(i).getTenSan());
           System.out.println(thongTinPhieuDat.DS.get(i).getTenSan());
           vt.add(thongTinPhieuDat.DS.get(i).getTenLoaiSan());
           vt.add(thongTinPhieuDat.DS.get(i).getTgDuKienDen());
           vt.add(thongTinPhieuDat.DS.get(i).getTgDuKienTra());
           dtm.addRow(vt);
       }
       tbCTDatTruoc_TTDT.setModel(dtm);
    }
    
     public boolean dangChuan(String a)
    {
        return a.matches("\\w{1,}");
        
    }
    
     public int layGiaTien(String maSan)
    {
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
        String sql="select LOAISAN.GIA from SANBONG,LOAISAN where SANBONG.MALOAISAN=LOAISAN.MALOAISAN and SANBONG.MASAN='"+maSan+"'";
        String giaTien="";
        try
        {
            PreparedStatement ps= ketNoi.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            rs.next();
            giaTien=rs.getString("GIA");
            rs.close();
            ps.close();
            ketNoi.close();
        }
        catch(SQLException e)
        {
            System.out.println("loi o ham lay gia tien");
        }
        return Integer.parseInt(giaTien);
    }
     
    
     public void loadCBLoaiSan_DatSan()
     {
         
        javax.swing.JComboBox<String> a=new JComboBox<>();
        
         for (ActionListener listener : cbLoaiSan_DatSan.getActionListeners())
        {
           cbLoaiSan_DatSan.removeActionListener(listener);
          a.addActionListener(listener);
        }
         
         
         System.out.println("So luong phan tu"+cbLoaiSan_DatSan.getItemCount());
         while(cbLoaiSan_DatSan.getItemCount()!=1)
         {
             cbLoaiSan_DatSan.removeItemAt(0);
         }
         System.out.println("so luong phan tu sau xoa: "+cbLoaiSan_DatSan.getItemCount());
        
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
        String sql="select TENLOAISAN from LOAISAN";
        cbLoaiSan_DatSan.addItem("Tất Cả");
        try
        {
            PreparedStatement ps= ketNoi.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next())
            {
                cbLoaiSan_DatSan.addItem(rs.getString("TENLOAISAN"));
                
            }
            rs.close();
            ps.close();
            ketNoi.close();
        }
        catch(SQLException e)
        {
            System.out.println("loi o ham lay gia tien");
        }
        cbLoaiSan_DatSan.removeItemAt(0);
        
        
         for (ActionListener listener : a.getActionListeners())
        {
             cbLoaiSan_DatSan.addActionListener(listener);
        }
     }
     
    public void setDatTruocSan(String loaiSan,Date ngayDate,String gioDen, String gioTra)
    {
        lSanKhaDung_DatSan.removeAll();
       String ngay=formmatNgay.format(ngayDate);
       if(gioDen.equals("Đóng cửa"))
       {
           lSanKhaDung_DatSan.removeAll();
           return;
       }
        
            Vector vt=new Vector();
            Connection ketNoi = KetNoiMSSQL.layKetNoi();
        String sql = "SELECT SANBONG.MASAN              \n"
                + "		from SANBONG, LOAISAN\n"
                + "               where SANBONG.MALOAISAN = LOAISAN.MALOAISAN and  LOAISAN.TENLOAISAN='" + loaiSan + "' \n"
                + "AND TRANGTHAI!=2\n"
                + "AND MASAN NOT IN (SELECT MASAN\n"
                + "                 FROM CT_DATTRUOC,PHIEUDATTRUOC\n"
                + "                 WHERE PHIEUDATTRUOC.NGAYDAT = '" + ngay + "'\n"
                + "                AND PHIEUDATTRUOC.MADATTRUOC = CT_DATTRUOC.MADATTRUOC \n"
                + "                AND ((CT_DATTRUOC.TG_DUKIENTRA > '" + gioDen + "' AND CT_DATTRUOC.TG_DUKIENTRA < '" + gioTra + "')\n"
                + "                OR (CT_DATTRUOC.TG_DUKIENDEN > '" + gioDen + "' AND CT_DATTRUOC.TG_DUKIENDEN < '" + gioTra + "')\n"
                + "                OR (CT_DATTRUOC.TG_DUKIENDEN <= '" + gioDen + "' AND CT_DATTRUOC.TG_DUKIENTRA >= '" + gioTra + "')))\n"
                + "AND MASAN NOT IN (SELECT MASAN\n"
                + "                 FROM CT_THUE,PHIEUTHUE\n"
                + "                 WHERE PHIEUTHUE.NGAYLAPPHIEU = '" + ngay + "'\n"
                + "                 AND PHIEUTHUE.MAHOADON = CT_THUE.MAHOADON \n"
                + "                 AND CT_THUE.GIOTRA IS NULL  \n"
                + "                 AND ((CT_THUE.TG_DUKIENTRA > '" + gioDen + "' AND CT_THUE.TG_DUKIENTRA < '" + gioTra + "')\n"
                + "                    OR (CT_THUE.GIOTHUE > '" + gioDen + "' AND CT_THUE.GIOTHUE< '" + gioTra + "')\n"
                + "                    OR (CT_THUE.GIOTHUE <= '" + gioDen + "' AND CT_THUE.TG_DUKIENTRA >= '" + gioTra + "')))";
            
        String sql1 = "SELECT SANBONG.MASAN              \n"
                + "from SANBONG, LOAISAN\n"
                + "where SANBONG.MALOAISAN = LOAISAN.MALOAISAN\n"
                + "AND TRANGTHAI!=2\n"
                + "AND MASAN NOT IN (SELECT MASAN\n"
                + "                 FROM CT_DATTRUOC,PHIEUDATTRUOC\n"
                + "                 WHERE PHIEUDATTRUOC.NGAYDAT = '" + ngay + "'\n"
                + "                AND PHIEUDATTRUOC.MADATTRUOC = CT_DATTRUOC.MADATTRUOC \n"
                + "                AND ((CT_DATTRUOC.TG_DUKIENTRA > '" + gioDen + "' AND CT_DATTRUOC.TG_DUKIENTRA < '" + gioTra + "')\n"
                + "                OR (CT_DATTRUOC.TG_DUKIENDEN > '" + gioDen + "' AND CT_DATTRUOC.TG_DUKIENDEN < '" + gioTra + "')\n"
                + "                OR (CT_DATTRUOC.TG_DUKIENDEN <= '" + gioDen + "' AND CT_DATTRUOC.TG_DUKIENTRA >= '" + gioTra + "')))\n"
                + "AND MASAN NOT IN (SELECT MASAN\n"
                + "                 FROM CT_THUE,PHIEUTHUE\n"
                + "                 WHERE PHIEUTHUE.NGAYLAPPHIEU = '" + ngay + "'\n"
                + "                 AND PHIEUTHUE.MAHOADON = CT_THUE.MAHOADON \n"
                + "                 AND CT_THUE.GIOTRA IS NULL  \n"
                + "                 AND ((CT_THUE.TG_DUKIENTRA > '" + gioDen + "' AND CT_THUE.TG_DUKIENTRA < '" + gioTra + "')\n"
                + "                    OR (CT_THUE.GIOTHUE > '" + gioDen + "' AND CT_THUE.GIOTHUE< '" + gioTra + "')\n"
                + "                    OR (CT_THUE.GIOTHUE <= '" + gioDen + "' AND CT_THUE.TG_DUKIENTRA >= '" + gioTra + "')))";
            
            try
            {
                 PreparedStatement ps;
                if(loaiSan.equals("Tất Cả"))
                {
                    ps= ketNoi.prepareStatement(sql1);
                }
                else  ps= ketNoi.prepareStatement(sql);
                
                ResultSet rs= ps.executeQuery();
                while(rs.next())
                {
                     vt.add(rs.getString("MASAN"));
                }
               
                rs.close();
                ps.close();
                ketNoi.close();
           
            }
             catch(SQLException ex)
            {
             System.out.println("loi setDatTruoc san bong");
            }
            lSanKhaDung_DatSan.removeAll();
            lSanKhaDung_DatSan.setListData(vt);
    }
    public void setGioTra()
    {
        
        javax.swing.JComboBox<String> tamTra=new JComboBox<>();
        
        for (ActionListener listener : cbGioTra_DatSan.getActionListeners())
        {
         cbGioTra_DatSan.removeActionListener(listener);
           tamTra.addActionListener(listener);
        }
        
        String[] a ={"07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};
        String gioDen=cbGioDen_DatSan.getSelectedItem().toString();
        if(gioDen.equals("Đóng cửa"))
        {
             while(cbGioTra_DatSan.getItemCount()!=1)
                 {
                     cbGioTra_DatSan.removeItemAt(0);
                 }
                
                     cbGioTra_DatSan.addItem("Đóng cửa");
                 
                 cbGioTra_DatSan.removeItemAt(0);
        }
        for(int i=0;i<a.length;i++)
        {
             if(gioDen.equals(a[i])==true)
             {
                 while(cbGioTra_DatSan.getItemCount()!=1)
                 {
                     cbGioTra_DatSan.removeItemAt(0);
                 }
                 for(int j=i+1;j<a.length;j++)
                 {
                     cbGioTra_DatSan.addItem(a[j]);
                 }
                 cbGioTra_DatSan.removeItemAt(0);
                 break;
             }
        }
         for (ActionListener listener : tamTra.getActionListeners())
        {
             cbGioTra_DatSan.addActionListener(listener);
        }
    }
    public void setGioDen()
    {
        javax.swing.JComboBox<String> tamDen=new JComboBox<>();
        
        for (ActionListener listener : cbGioDen_DatSan.getActionListeners())
        {
         cbGioDen_DatSan.removeActionListener(listener);
           tamDen.addActionListener(listener);
        }
        
        while(cbGioDen_DatSan.getItemCount()!=1)
         {
           cbGioDen_DatSan.removeItemAt(0);
         }
         String[] a ={"07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00"};
         for(int i=0;i<a.length-1;i++)
         {
              cbGioDen_DatSan.addItem(a[i]);
         }
          cbGioDen_DatSan.removeItemAt(0);
          
          
           for (ActionListener listener : tamDen.getActionListeners())
        {
             cbGioDen_DatSan.addActionListener(listener);
        }
    }
    public void setGioDenHienTai()
    {
        javax.swing.JComboBox<String> tamDen=new JComboBox<>();
        
        for (ActionListener listener : cbGioDen_DatSan.getActionListeners())
        {
         cbGioDen_DatSan.removeActionListener(listener);
           tamDen.addActionListener(listener);
        }
        javax.swing.JComboBox<String> tamTra=new JComboBox<>();
        
        for (ActionListener listener : cbGioTra_DatSan.getActionListeners())
        {
         cbGioTra_DatSan.removeActionListener(listener);
           tamTra.addActionListener(listener);
        }
        
      
      
      
        String[] a ={"07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00"};
        Date ngayHienTai= new Date();
        
        if(formmatNgay.format(ngayHienTai).equals(formmatNgay.format(dcNgay_DatSan.getDate()))==true)
        {
            
           boolean kt=false;
            String gioHienTai=java.time.LocalTime.now().toString().substring(0,2);
            for(int i=0;i<a.length;i++)
            {
                if(gioHienTai.equals(a[i].substring(0, 2)))
                {
                    
                    while(cbGioDen_DatSan.getItemCount()!=1)
                    {
                        cbGioDen_DatSan.removeItemAt(0);
                    }
                    
                    for(int j=i+1;j<a.length;j++)
                    {
                        cbGioDen_DatSan.addItem(a[j]);
                        
                    }
                    
                    if(gioHienTai.equals("22"))
                    {
                        cbGioDen_DatSan.addItem("Đóng cửa");
                    }
                    cbGioDen_DatSan.removeItemAt(0);
                    setGioTra();
                    kt=true;
                    break;
                    
                }
            }
            if(kt==false)
            {
                 while(cbGioDen_DatSan.getItemCount()!=1)
                    {
                        cbGioDen_DatSan.removeItemAt(0);
                    }
                        cbGioDen_DatSan.addItem("Đóng cửa");
                        cbGioDen_DatSan.removeItemAt(0);
                 
                 while(cbGioTra_DatSan.getItemCount()!=1)
                    {
                        cbGioTra_DatSan.removeItemAt(0);
                    }
                    cbGioTra_DatSan.addItem("Đóng cửa");
                    cbGioTra_DatSan.removeItemAt(0);
                 
                 
            }
        }
        else
        {
            setGioDen();
            setGioTra();
        }
       
        for (ActionListener listener : tamDen.getActionListeners())
        {
             cbGioDen_DatSan.addActionListener(listener);
        }
        for (ActionListener listener : tamTra.getActionListeners())
        {
             cbGioTra_DatSan.addActionListener(listener);
        }
    }
    public boolean kiemTraNgayQuaKhu(Date ngay1)
    {
        Date NgayHienTaiDate = new Date();
        
        String ngayHienTai=formmatNgay.format(NgayHienTaiDate);
       
        int nam_HT=Integer.parseInt(ngayHienTai.substring(0, 4));
        int thang_HT=Integer.parseInt(ngayHienTai.substring(5, 7));
        int ngay_HT=Integer.parseInt(ngayHienTai.substring(8,10));
        
        String ngay= formmatNgay.format(ngay1);
        int nam_Check=Integer.parseInt(ngay.substring(0, 4));
        int thang_Check=Integer.parseInt(ngay.substring(5, 7));
        int ngay_Check=Integer.parseInt(ngay.substring(8,10));
        
        if(nam_Check<nam_HT) return false;
        else if(nam_Check>nam_HT) return true;
        else 
        {
            if(thang_Check>thang_HT) return true;
            else if(thang_Check<thang_HT) return false;
            else 
            {
                if(ngay_Check>=ngay_HT )return true;
                else return false;
            }
        }
        
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jPanel18 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tbCTDatTruoc_DatSan2 = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        lSanKhaDung_DatSan2 = new javax.swing.JList<>();
        btnThemSan_DatSan4 = new javax.swing.JButton();
        btnThemSan_DatSan5 = new javax.swing.JButton();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        cbLoaiSan_DatSan2 = new javax.swing.JComboBox<>();
        cbGioDen_DatSan4 = new javax.swing.JComboBox<>();
        cbGioDen_DatSan5 = new javax.swing.JComboBox<>();
        dcNgay_DatSan2 = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tbCTDatTruoc_DatSan3 = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        lSanKhaDung_DatSan3 = new javax.swing.JList<>();
        btnThemSan_DatSan6 = new javax.swing.JButton();
        btnThemSan_DatSan7 = new javax.swing.JButton();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        cbLoaiSan_DatSan3 = new javax.swing.JComboBox<>();
        cbGioDen_DatSan6 = new javax.swing.JComboBox<>();
        cbGioDen_DatSan7 = new javax.swing.JComboBox<>();
        dcNgay_DatSan3 = new com.toedter.calendar.JDateChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnMenu = new javax.swing.JPanel();
        lbDangXuat = new javax.swing.JLabel();
        lbNgayHienTai = new javax.swing.JLabel();
        btnThongTinSanBong = new javax.swing.JPanel();
        lbThongTinSanBong = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnXemDichVu = new javax.swing.JPanel();
        lbXemDichVu = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnDatTruocSan = new javax.swing.JPanel();
        lbDatTruocSan = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnLichSuThue = new javax.swing.JPanel();
        lbLichSuThue = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lbAboutUs = new javax.swing.JLabel();
        lbTroGiup = new javax.swing.JLabel();
        lbLienHe = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        pnTenTaiKhoan = new javax.swing.JPanel();
        lbTenTaiKhoan = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        pnDongHo = new javax.swing.JPanel();
        lblNgayHomNay = new javax.swing.JLabel();
        lblGioHienTai = new javax.swing.JLabel();
        lbDongHo = new javax.swing.JLabel();
        pnTong = new javax.swing.JPanel();
        pnDatTruocSan = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        lbThongTinDatTruoc_DatTruoc = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jPanel26 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tbCTDatTruoc_DatSan = new javax.swing.JTable();
        lbXoa_DatSan = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        lSanKhaDung_DatSan = new javax.swing.JList<>();
        btnXacNhanDat_DatSan = new javax.swing.JButton();
        btnThemSan_DatSan = new javax.swing.JButton();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        cbLoaiSan_DatSan = new javax.swing.JComboBox<>();
        cbGioDen_DatSan = new javax.swing.JComboBox<>();
        cbGioTra_DatSan = new javax.swing.JComboBox<>();
        dcNgay_DatSan = new com.toedter.calendar.JDateChooser();
        pnThongTinSanBong = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jPanel10 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbSanBong_ThongTinSanBong = new javax.swing.JTable();
        jLabel92 = new javax.swing.JLabel();
        cbLoaiSan_ThongTinSanBong = new javax.swing.JComboBox<>();
        jLabel93 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        txtSang_ThongTinSanBong = new javax.swing.JLabel();
        txtChieu_ThongTinSanBong = new javax.swing.JLabel();
        txtToi_ThongTinSanBong = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        pnSan5A = new javax.swing.JPanel();
        pnSan5B = new javax.swing.JPanel();
        pnSan7A = new javax.swing.JPanel();
        pnSan5C = new javax.swing.JPanel();
        pnSan11A = new javax.swing.JPanel();
        pnSan7B = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnDichVu = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jPanel30 = new javax.swing.JPanel();
        jLabel107 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbThucUong_DichVu = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        jLabel108 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tbDichVuKhac_DichVu = new javax.swing.JTable();
        pnThongTinDatTruoc = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        lbDatTruoc_ThongTinDatTruoc = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jSeparator20 = new javax.swing.JSeparator();
        jPanel34 = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbCTDatTruoc_TTDT = new javax.swing.JTable();
        btnHuyCtDatTruoc = new javax.swing.JButton();
        jPanel35 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbThongTinDatTruoc_TTDT = new javax.swing.JTable();
        pnThongTinCaNhan = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jSeparator21 = new javax.swing.JSeparator();
        pnTong_TTCN = new javax.swing.JPanel();
        pnSuaThongTin_TTCN = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtHoTen_SuaThongTin_TTCN = new javax.swing.JTextField();
        txtSDT_SuaThongTin_TTCN = new javax.swing.JTextField();
        lblWarnHoTen_SuaTT = new javax.swing.JLabel();
        lblWarnSDT_SuaTT = new javax.swing.JLabel();
        pnDoiMatKhau_TTCN = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        pfMKCu_DoiMK_TTCN = new javax.swing.JPasswordField();
        pfMKMoi_DoiMK_TTCN = new javax.swing.JPasswordField();
        pfXNMK_DoiMK_TTCN = new javax.swing.JPasswordField();
        lbThongBaoLoi = new javax.swing.JLabel();
        pnCTTTCN_TTCN = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lbHoTen_CTTTCN_TTCN = new javax.swing.JLabel();
        lbCMND_CTTTCN_TTCN = new javax.swing.JLabel();
        lbSDT_CTTTCN_TTCN = new javax.swing.JLabel();
        btnSuaThongTin_TTCN = new javax.swing.JButton();
        btnDoiMatKhau_TTCN = new javax.swing.JButton();
        pnLichSuThue = new javax.swing.JPanel();
        pnSoDoSanBong1 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel112 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbLichSuThue_LichSuThue = new javax.swing.JTable();
        jPanel37 = new javax.swing.JPanel();
        jLabel104 = new javax.swing.JLabel();
        lbDatTruoc_ThongTinDatTruoc1 = new javax.swing.JLabel();
        jSeparator22 = new javax.swing.JSeparator();
        btnXemHoaDon_LichSuThue = new javax.swing.JButton();

        jPanel16.setBackground(new java.awt.Color(254, 235, 208));
        jPanel16.setForeground(new java.awt.Color(0, 0, 102));
        jPanel16.setMaximumSize(new java.awt.Dimension(9999, 9999));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setForeground(new java.awt.Color(0, 102, 102));

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 0, 102));
        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-reserve-48 (1).png"))); // NOI18N
        jLabel62.setText("ĐẶT TRƯỚC SÂN");

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 0, 102));
        jLabel63.setText("ĐẶT TRƯỚC");

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(0, 0, 102));
        jLabel64.setText("THÔNG TIN ĐẶT TRƯỚC");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel62))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel63)
                        .addGap(105, 105, 105)
                        .addComponent(jLabel64))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(716, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(jLabel64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE)
                    .addComponent(jSeparator9)))
        );

        jPanel16.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 120));

        jPanel18.setBackground(new java.awt.Color(200, 232, 249));

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(37, 102, 142));
        jLabel65.setText("CHI TIẾT ĐẶT TRƯỚC");

        tbCTDatTruoc_DatSan2.setForeground(new java.awt.Color(255, 255, 255));
        tbCTDatTruoc_DatSan2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "TÊN SÂN", "LOẠI SÂN", "NGÀY THUÊ", "GIỜ ĐẾN", "GIỜ TRẢ", "GIÁ DỰ TÍNH"
            }
        ));
        jScrollPane11.setViewportView(tbCTDatTruoc_DatSan2);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(217, 217, 217))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 290, 710, 530));

        jPanel19.setBackground(new java.awt.Color(179, 193, 135));

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 102, 0));
        jLabel66.setText("SÂN KHẢ DỤNG");

        jScrollPane15.setViewportView(lSanKhaDung_DatSan2);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15)
                .addContainerGap())
        );

        jPanel16.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 390, 530));

        btnThemSan_DatSan4.setBackground(new java.awt.Color(28, 184, 160));
        btnThemSan_DatSan4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemSan_DatSan4.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSan_DatSan4.setText("XÁC NHẬN ĐẶT");
        btnThemSan_DatSan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSan_DatSan4ActionPerformed(evt);
            }
        });
        jPanel16.add(btnThemSan_DatSan4, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 830, -1, -1));

        btnThemSan_DatSan5.setBackground(new java.awt.Color(28, 184, 160));
        btnThemSan_DatSan5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemSan_DatSan5.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSan_DatSan5.setText("THÊM SÂN");
        btnThemSan_DatSan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSan_DatSan5ActionPerformed(evt);
            }
        });
        jPanel16.add(btnThemSan_DatSan5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 830, -1, -1));

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 0, 102));
        jLabel67.setText("GIỜ TRẢ:");
        jPanel16.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 230, -1, 20));

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(0, 0, 102));
        jLabel68.setText("CHỌN NGÀY:");
        jPanel16.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(0, 0, 102));
        jLabel69.setText("CHỌN LOẠI SÂN:");
        jPanel16.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 102));
        jLabel70.setText("GIỜ ĐẾN:");
        jPanel16.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, -1, 20));

        cbLoaiSan_DatSan2.setBackground(new java.awt.Color(153, 255, 255));
        cbLoaiSan_DatSan2.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        cbLoaiSan_DatSan2.setForeground(new java.awt.Color(255, 255, 255));
        cbLoaiSan_DatSan2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sân 5", "Sân 7", "Sân 11" }));
        cbLoaiSan_DatSan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiSan_DatSan2ActionPerformed(evt);
            }
        });
        jPanel16.add(cbLoaiSan_DatSan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 170, -1));

        cbGioDen_DatSan4.setBackground(new java.awt.Color(153, 255, 255));
        cbGioDen_DatSan4.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        cbGioDen_DatSan4.setForeground(new java.awt.Color(255, 255, 255));
        cbGioDen_DatSan4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" }));
        cbGioDen_DatSan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGioDen_DatSan4ActionPerformed(evt);
            }
        });
        jPanel16.add(cbGioDen_DatSan4, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 170, 170, -1));

        cbGioDen_DatSan5.setBackground(new java.awt.Color(153, 255, 255));
        cbGioDen_DatSan5.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        cbGioDen_DatSan5.setForeground(new java.awt.Color(255, 255, 255));
        cbGioDen_DatSan5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" }));
        cbGioDen_DatSan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGioDen_DatSan5ActionPerformed(evt);
            }
        });
        jPanel16.add(cbGioDen_DatSan5, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 230, 170, -1));

        dcNgay_DatSan2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcNgay_DatSan2PropertyChange(evt);
            }
        });
        jPanel16.add(dcNgay_DatSan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 170, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setForeground(new java.awt.Color(0, 102, 102));

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 102));
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-reserve-48 (1).png"))); // NOI18N
        jLabel71.setText("ĐẶT TRƯỚC SÂN");

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 0, 102));
        jLabel72.setText("ĐẶT TRƯỚC");

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(0, 0, 102));
        jLabel73.setText("THÔNG TIN ĐẶT TRƯỚC");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel71))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel72)
                        .addGap(105, 105, 105)
                        .addComponent(jLabel73))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(716, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(jLabel73))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE)
                    .addComponent(jSeparator11)))
        );

        jPanel2.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, -1));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(254, 235, 208));
        jPanel21.setForeground(new java.awt.Color(0, 0, 102));
        jPanel21.setMaximumSize(new java.awt.Dimension(9999, 9999));
        jPanel21.setPreferredSize(new java.awt.Dimension(1150, 770));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel23.setBackground(new java.awt.Color(200, 232, 249));

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(37, 102, 142));
        jLabel77.setText("CHI TIẾT ĐẶT TRƯỚC");

        tbCTDatTruoc_DatSan3.setForeground(new java.awt.Color(255, 255, 255));
        tbCTDatTruoc_DatSan3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "TÊN SÂN", "LOẠI SÂN", "NGÀY THUÊ", "GIỜ ĐẾN", "GIỜ TRẢ", "GIÁ DỰ TÍNH"
            }
        ));
        jScrollPane12.setViewportView(tbCTDatTruoc_DatSan3);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(217, 217, 217))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel21.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 710, 590));

        jPanel24.setBackground(new java.awt.Color(179, 193, 135));

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 102, 0));
        jLabel78.setText("SÂN KHẢ DỤNG");

        jScrollPane16.setViewportView(lSanKhaDung_DatSan3);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16)
                .addContainerGap())
        );

        jPanel21.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 390, 590));

        btnThemSan_DatSan6.setBackground(new java.awt.Color(28, 184, 160));
        btnThemSan_DatSan6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemSan_DatSan6.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSan_DatSan6.setText("XÁC NHẬN ĐẶT");
        btnThemSan_DatSan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSan_DatSan6ActionPerformed(evt);
            }
        });
        jPanel21.add(btnThemSan_DatSan6, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 730, -1, -1));

        btnThemSan_DatSan7.setBackground(new java.awt.Color(28, 184, 160));
        btnThemSan_DatSan7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemSan_DatSan7.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSan_DatSan7.setText("THÊM SÂN");
        btnThemSan_DatSan7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSan_DatSan7ActionPerformed(evt);
            }
        });
        jPanel21.add(btnThemSan_DatSan7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 730, -1, -1));

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 0, 102));
        jLabel79.setText("GIỜ TRẢ:");
        jPanel21.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, -1, 20));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(0, 0, 102));
        jLabel80.setText("CHỌN NGÀY:");
        jPanel21.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 0, 102));
        jLabel81.setText("CHỌN LOẠI SÂN:");
        jPanel21.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(0, 0, 102));
        jLabel82.setText("GIỜ ĐẾN:");
        jPanel21.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, 20));

        cbLoaiSan_DatSan3.setBackground(new java.awt.Color(153, 255, 255));
        cbLoaiSan_DatSan3.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        cbLoaiSan_DatSan3.setForeground(new java.awt.Color(255, 255, 255));
        cbLoaiSan_DatSan3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sân 5", "Sân 7", "Sân 11" }));
        cbLoaiSan_DatSan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiSan_DatSan3ActionPerformed(evt);
            }
        });
        jPanel21.add(cbLoaiSan_DatSan3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 170, -1));

        cbGioDen_DatSan6.setBackground(new java.awt.Color(153, 255, 255));
        cbGioDen_DatSan6.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        cbGioDen_DatSan6.setForeground(new java.awt.Color(255, 255, 255));
        cbGioDen_DatSan6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" }));
        cbGioDen_DatSan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGioDen_DatSan6ActionPerformed(evt);
            }
        });
        jPanel21.add(cbGioDen_DatSan6, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, 170, -1));

        cbGioDen_DatSan7.setBackground(new java.awt.Color(153, 255, 255));
        cbGioDen_DatSan7.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        cbGioDen_DatSan7.setForeground(new java.awt.Color(255, 255, 255));
        cbGioDen_DatSan7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" }));
        cbGioDen_DatSan7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGioDen_DatSan7ActionPerformed(evt);
            }
        });
        jPanel21.add(cbGioDen_DatSan7, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 80, 170, -1));

        dcNgay_DatSan3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcNgay_DatSan3PropertyChange(evt);
            }
        });
        jPanel21.add(dcNgay_DatSan3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 170, -1));

        jPanel3.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 770));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1150, 770));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnMenu.setBackground(new java.awt.Color(255, 255, 255));
        pnMenu.setPreferredSize(new java.awt.Dimension(329, 885));

        lbDangXuat.setBackground(new java.awt.Color(204, 204, 204));
        lbDangXuat.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbDangXuat.setForeground(new java.awt.Color(0, 153, 153));
        lbDangXuat.setText("Đăng xuất");
        lbDangXuat.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbDangXuatMouseMoved(evt);
            }
        });
        lbDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDangXuatMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbDangXuatMouseExited(evt);
            }
        });

        lbNgayHienTai.setBackground(new java.awt.Color(204, 204, 204));
        lbNgayHienTai.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbNgayHienTai.setForeground(new java.awt.Color(0, 0, 102));
        lbNgayHienTai.setText("19-4-2021");
        lbNgayHienTai.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbNgayHienTaiMouseMoved(evt);
            }
        });
        lbNgayHienTai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbNgayHienTaiMouseExited(evt);
            }
        });

        btnThongTinSanBong.setBackground(new java.awt.Color(255, 255, 255));
        btnThongTinSanBong.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnThongTinSanBongMouseMoved(evt);
            }
        });
        btnThongTinSanBong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThongTinSanBongMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThongTinSanBongMouseExited(evt);
            }
        });

        lbThongTinSanBong.setBackground(new java.awt.Color(204, 204, 204));
        lbThongTinSanBong.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbThongTinSanBong.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinSanBong.setText("THÔNG TIN SÂN BÓNG");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-stadium-30.png"))); // NOI18N

        javax.swing.GroupLayout btnThongTinSanBongLayout = new javax.swing.GroupLayout(btnThongTinSanBong);
        btnThongTinSanBong.setLayout(btnThongTinSanBongLayout);
        btnThongTinSanBongLayout.setHorizontalGroup(
            btnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnThongTinSanBongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbThongTinSanBong, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        btnThongTinSanBongLayout.setVerticalGroup(
            btnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThongTinSanBongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbThongTinSanBong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        btnXemDichVu.setBackground(new java.awt.Color(255, 255, 255));
        btnXemDichVu.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnXemDichVuMouseMoved(evt);
            }
        });
        btnXemDichVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXemDichVuMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXemDichVuMouseExited(evt);
            }
        });

        lbXemDichVu.setBackground(new java.awt.Color(204, 204, 204));
        lbXemDichVu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbXemDichVu.setForeground(new java.awt.Color(0, 0, 102));
        lbXemDichVu.setText("XEM DỊCH VỤ");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-food-cart-30.png"))); // NOI18N

        javax.swing.GroupLayout btnXemDichVuLayout = new javax.swing.GroupLayout(btnXemDichVu);
        btnXemDichVu.setLayout(btnXemDichVuLayout);
        btnXemDichVuLayout.setHorizontalGroup(
            btnXemDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnXemDichVuLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbXemDichVu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnXemDichVuLayout.setVerticalGroup(
            btnXemDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnXemDichVuLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(btnXemDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbXemDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        btnDatTruocSan.setBackground(new java.awt.Color(255, 255, 255));
        btnDatTruocSan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnDatTruocSanMouseMoved(evt);
            }
        });
        btnDatTruocSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDatTruocSanMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDatTruocSanMouseExited(evt);
            }
        });

        lbDatTruocSan.setBackground(new java.awt.Color(0, 0, 0));
        lbDatTruocSan.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbDatTruocSan.setForeground(new java.awt.Color(0, 0, 102));
        lbDatTruocSan.setText("ĐẶT TRƯỚC SÂN");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-reserve-32.png"))); // NOI18N

        javax.swing.GroupLayout btnDatTruocSanLayout = new javax.swing.GroupLayout(btnDatTruocSan);
        btnDatTruocSan.setLayout(btnDatTruocSanLayout);
        btnDatTruocSanLayout.setHorizontalGroup(
            btnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDatTruocSanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbDatTruocSan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnDatTruocSanLayout.setVerticalGroup(
            btnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDatTruocSanLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(btnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(lbDatTruocSan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnLichSuThue.setBackground(new java.awt.Color(255, 255, 255));
        btnLichSuThue.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnLichSuThueMouseMoved(evt);
            }
        });
        btnLichSuThue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLichSuThueMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLichSuThueMouseExited(evt);
            }
        });

        lbLichSuThue.setBackground(new java.awt.Color(204, 204, 204));
        lbLichSuThue.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbLichSuThue.setForeground(new java.awt.Color(0, 0, 102));
        lbLichSuThue.setText("LỊCH SỬ THUÊ");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-order-history-30.png"))); // NOI18N

        javax.swing.GroupLayout btnLichSuThueLayout = new javax.swing.GroupLayout(btnLichSuThue);
        btnLichSuThue.setLayout(btnLichSuThueLayout);
        btnLichSuThueLayout.setHorizontalGroup(
            btnLichSuThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnLichSuThueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(lbLichSuThue)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        btnLichSuThueLayout.setVerticalGroup(
            btnLichSuThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLichSuThueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnLichSuThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(lbLichSuThue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        lbAboutUs.setBackground(new java.awt.Color(204, 204, 204));
        lbAboutUs.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbAboutUs.setForeground(new java.awt.Color(0, 0, 102));
        lbAboutUs.setText("About us");
        lbAboutUs.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbAboutUsMouseMoved(evt);
            }
        });
        lbAboutUs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbAboutUsMouseExited(evt);
            }
        });

        lbTroGiup.setBackground(new java.awt.Color(204, 204, 204));
        lbTroGiup.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbTroGiup.setForeground(new java.awt.Color(0, 0, 102));
        lbTroGiup.setText("Trợ giúp");
        lbTroGiup.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbTroGiupMouseMoved(evt);
            }
        });
        lbTroGiup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbTroGiupMouseExited(evt);
            }
        });

        lbLienHe.setBackground(new java.awt.Color(204, 204, 204));
        lbLienHe.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbLienHe.setForeground(new java.awt.Color(0, 0, 102));
        lbLienHe.setText("Liên Hệ");
        lbLienHe.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbLienHeMouseMoved(evt);
            }
        });
        lbLienHe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbLienHeMouseExited(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/logo.png"))); // NOI18N

        pnTenTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        pnTenTaiKhoan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                pnTenTaiKhoanMouseMoved(evt);
            }
        });
        pnTenTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnTenTaiKhoanMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnTenTaiKhoanMouseExited(evt);
            }
        });
        pnTenTaiKhoan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTenTaiKhoan.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbTenTaiKhoan.setForeground(new java.awt.Color(0, 0, 102));
        lbTenTaiKhoan.setText("TÊN TÀI KHOẢN");
        lbTenTaiKhoan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbTenTaiKhoanMouseMoved(evt);
            }
        });
        lbTenTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTenTaiKhoanMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbTenTaiKhoanMouseExited(evt);
            }
        });
        pnTenTaiKhoan.add(lbTenTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-account-100.png"))); // NOI18N
        pnTenTaiKhoan.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel91.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(0, 0, 102));
        jLabel91.setText("CHỨC VỤ: KHÁCH");
        pnTenTaiKhoan.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        pnDongHo.setBackground(new java.awt.Color(255, 255, 255));

        lblNgayHomNay.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lblNgayHomNay.setForeground(new java.awt.Color(0, 0, 102));
        lblNgayHomNay.setText("dd/MM/yyyy");

        lblGioHienTai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblGioHienTai.setForeground(new java.awt.Color(0, 0, 102));
        lblGioHienTai.setText("HH:mm:ss a");

        lbDongHo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-clock-50.png"))); // NOI18N

        javax.swing.GroupLayout pnDongHoLayout = new javax.swing.GroupLayout(pnDongHo);
        pnDongHo.setLayout(pnDongHoLayout);
        pnDongHoLayout.setHorizontalGroup(
            pnDongHoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDongHoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbDongHo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(pnDongHoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGioHienTai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDongHoLayout.createSequentialGroup()
                        .addComponent(lblNgayHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        pnDongHoLayout.setVerticalGroup(
            pnDongHoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDongHoLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(pnDongHoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDongHo)
                    .addGroup(pnDongHoLayout.createSequentialGroup()
                        .addComponent(lblNgayHomNay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblGioHienTai))))
        );

        javax.swing.GroupLayout pnMenuLayout = new javax.swing.GroupLayout(pnMenu);
        pnMenu.setLayout(pnMenuLayout);
        pnMenuLayout.setHorizontalGroup(
            pnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnMenuLayout.createSequentialGroup()
                        .addComponent(pnTenTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pnMenuLayout.createSequentialGroup()
                        .addComponent(lbNgayHienTai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbDangXuat)
                        .addGap(55, 55, 55))
                    .addGroup(pnMenuLayout.createSequentialGroup()
                        .addGroup(pnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXemDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLichSuThue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(pnMenuLayout.createSequentialGroup()
                .addGroup(pnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnMenuLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(pnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbLienHe)
                            .addComponent(lbTroGiup)
                            .addComponent(lbAboutUs)))
                    .addGroup(pnMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDatTruocSan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThongTinSanBong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnDongHo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnMenuLayout.setVerticalGroup(
            pnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMenuLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(btnDatTruocSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnThongTinSanBong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXemDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLichSuThue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnDongHo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbAboutUs, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTroGiup, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbLienHe, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNgayHienTai)
                    .addComponent(lbDangXuat))
                .addGap(20, 20, 20))
        );

        pnTong.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnDatTruocSan.setBackground(new java.awt.Color(254, 235, 208));
        pnDatTruocSan.setForeground(new java.awt.Color(0, 0, 102));
        pnDatTruocSan.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnDatTruocSan.setPreferredSize(new java.awt.Dimension(1150, 890));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setForeground(new java.awt.Color(0, 102, 102));
        jPanel25.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(0, 0, 102));
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-reserve-48 (1).png"))); // NOI18N
        jLabel74.setText("ĐẶT TRƯỚC SÂN");

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 102));
        jLabel75.setText("ĐẶT TRƯỚC");

        lbThongTinDatTruoc_DatTruoc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinDatTruoc_DatTruoc.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinDatTruoc_DatTruoc.setText("THÔNG TIN ĐẶT TRƯỚC");
        lbThongTinDatTruoc_DatTruoc.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbThongTinDatTruoc_DatTruocMouseMoved(evt);
            }
        });
        lbThongTinDatTruoc_DatTruoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongTinDatTruoc_DatTruocMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThongTinDatTruoc_DatTruocMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel74))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel75)
                        .addGap(105, 105, 105)
                        .addComponent(lbThongTinDatTruoc_DatTruoc))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(729, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel74)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(lbThongTinDatTruoc_DatTruoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel26.setBackground(new java.awt.Color(200, 232, 249));

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(37, 102, 142));
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-more-details-30.png"))); // NOI18N
        jLabel83.setText("CHI TIẾT ĐẶT TRƯỚC");

        tbCTDatTruoc_DatSan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbCTDatTruoc_DatSan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN SÂN", "LOẠI SÂN", "NGÀY THUÊ", "GIỜ ĐẾN", "GIỜ TRẢ", "GIÁ DỰ TÍNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCTDatTruoc_DatSan.setRowHeight(35);
        jScrollPane13.setViewportView(tbCTDatTruoc_DatSan);

        lbXoa_DatSan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbXoa_DatSan.setForeground(new java.awt.Color(37, 102, 142));
        lbXoa_DatSan.setText("Xóa");
        lbXoa_DatSan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbXoa_DatSanMouseMoved(evt);
            }
        });
        lbXoa_DatSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbXoa_DatSanMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbXoa_DatSanMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                        .addGap(0, 659, Short.MAX_VALUE)
                        .addComponent(lbXoa_DatSan)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(245, 245, 245))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(lbXoa_DatSan))
        );

        jPanel27.setBackground(new java.awt.Color(179, 193, 135));

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(0, 102, 0));
        jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-stadium-32_1.png"))); // NOI18N
        jLabel84.setText("SÂN KHẢ DỤNG");

        lSanKhaDung_DatSan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        lSanKhaDung_DatSan.setVisibleRowCount(5);
        jScrollPane17.setViewportView(lSanKhaDung_DatSan);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(jLabel84)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnXacNhanDat_DatSan.setBackground(new java.awt.Color(28, 184, 160));
        btnXacNhanDat_DatSan.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        btnXacNhanDat_DatSan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanDat_DatSan.setText("XÁC NHẬN ĐẶT");
        btnXacNhanDat_DatSan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanDat_DatSanActionPerformed(evt);
            }
        });

        btnThemSan_DatSan.setBackground(new java.awt.Color(28, 184, 160));
        btnThemSan_DatSan.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        btnThemSan_DatSan.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSan_DatSan.setText("THÊM SÂN");
        btnThemSan_DatSan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSan_DatSanActionPerformed(evt);
            }
        });

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 0, 102));
        jLabel85.setText("GIỜ TRẢ:");

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(0, 0, 102));
        jLabel86.setText("CHỌN NGÀY:");

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(0, 0, 102));
        jLabel87.setText("CHỌN LOẠI SÂN:");

        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 0, 102));
        jLabel88.setText("GIỜ ĐẾN:");

        cbLoaiSan_DatSan.setBackground(new java.awt.Color(153, 255, 255));
        cbLoaiSan_DatSan.setFont(new java.awt.Font("Tahoma", 2, 17)); // NOI18N
        cbLoaiSan_DatSan.setForeground(new java.awt.Color(255, 255, 255));
        cbLoaiSan_DatSan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất Cả", "Sân 5", "Sân 7", "Sân 11" }));
        cbLoaiSan_DatSan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiSan_DatSanActionPerformed(evt);
            }
        });

        cbGioDen_DatSan.setBackground(new java.awt.Color(153, 255, 255));
        cbGioDen_DatSan.setFont(new java.awt.Font("Tahoma", 2, 17)); // NOI18N
        cbGioDen_DatSan.setForeground(new java.awt.Color(255, 255, 255));
        cbGioDen_DatSan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" }));
        cbGioDen_DatSan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGioDen_DatSanActionPerformed(evt);
            }
        });

        cbGioTra_DatSan.setBackground(new java.awt.Color(153, 255, 255));
        cbGioTra_DatSan.setFont(new java.awt.Font("Tahoma", 2, 17)); // NOI18N
        cbGioTra_DatSan.setForeground(new java.awt.Color(255, 255, 255));
        cbGioTra_DatSan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" }));
        cbGioTra_DatSan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGioTra_DatSanActionPerformed(evt);
            }
        });

        dcNgay_DatSan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcNgay_DatSanPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout pnDatTruocSanLayout = new javax.swing.GroupLayout(pnDatTruocSan);
        pnDatTruocSan.setLayout(pnDatTruocSanLayout);
        pnDatTruocSanLayout.setHorizontalGroup(
            pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(btnThemSan_DatSan)))
                        .addGap(20, 20, 20)
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                .addGap(340, 340, 340)
                                .addComponent(btnXacNhanDat_DatSan))))
                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel87)
                            .addComponent(jLabel86))
                        .addGap(18, 18, 18)
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbLoaiSan_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dcNgay_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                .addGap(328, 328, 328)
                                .addComponent(jLabel88)
                                .addGap(71, 71, 71)
                                .addComponent(cbGioDen_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                .addGap(323, 323, 323)
                                .addComponent(jLabel85)
                                .addGap(73, 73, 73)
                                .addComponent(cbGioTra_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnDatTruocSanLayout.setVerticalGroup(
            pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel87)
                            .addComponent(cbLoaiSan_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbGioDen_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(dcNgay_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel86))
                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbGioTra_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37)
                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemSan_DatSan)
                    .addComponent(btnXacNhanDat_DatSan))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pnTong.add(pnDatTruocSan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnThongTinSanBong.setBackground(new java.awt.Color(254, 235, 208));
        pnThongTinSanBong.setPreferredSize(new java.awt.Dimension(1150, 890));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setForeground(new java.awt.Color(0, 102, 102));

        jLabel89.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(0, 0, 102));
        jLabel89.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-stadium-48.png"))); // NOI18N
        jLabel89.setText("THÔNG TIN SÂN BÓNG");

        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(0, 0, 102));
        jLabel90.setText("Thông Tin Sân Bóng");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel90)
                            .addComponent(jLabel89))))
                .addContainerGap(786, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel89)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jLabel90)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel10.setBackground(new java.awt.Color(200, 232, 249));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(37, 102, 142));
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-stadium-32_1.png"))); // NOI18N
        jLabel49.setText("SÂN BÓNG");

        tbSanBong_ThongTinSanBong.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbSanBong_ThongTinSanBong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN SÂN", "LOẠI SÂN", "GIÁ THEO GIỜ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSanBong_ThongTinSanBong.setRowHeight(35);
        tbSanBong_ThongTinSanBong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanBong_ThongTinSanBongMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tbSanBong_ThongTinSanBong);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel92.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(0, 0, 102));
        jLabel92.setText("CHỌN LOẠI SÂN:");

        cbLoaiSan_ThongTinSanBong.setBackground(new java.awt.Color(153, 255, 255));
        cbLoaiSan_ThongTinSanBong.setFont(new java.awt.Font("Tahoma", 2, 17)); // NOI18N
        cbLoaiSan_ThongTinSanBong.setForeground(new java.awt.Color(255, 255, 255));
        cbLoaiSan_ThongTinSanBong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất Cả", "Sân 5", "Sân 7", "Sân 11" }));
        cbLoaiSan_ThongTinSanBong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiSan_ThongTinSanBongActionPerformed(evt);
            }
        });

        jLabel93.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(0, 0, 102));
        jLabel93.setText("VỊ TRÍ SÂN");

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-sunrise-80.png"))); // NOI18N

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-afternoon-80.png"))); // NOI18N

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-night-80.png"))); // NOI18N

        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(0, 0, 102));
        jLabel94.setText("HỆ SỐ TIỀN THEO KHUNG GIỜ:");

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(0, 0, 102));
        jLabel95.setText("SÁNG:");

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(0, 0, 102));
        jLabel96.setText("CHIỀU:");

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(0, 0, 102));
        jLabel97.setText("TỐI:");

        txtSang_ThongTinSanBong.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtSang_ThongTinSanBong.setForeground(new java.awt.Color(0, 0, 102));
        txtSang_ThongTinSanBong.setText("x1");

        txtChieu_ThongTinSanBong.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtChieu_ThongTinSanBong.setForeground(new java.awt.Color(0, 0, 102));
        txtChieu_ThongTinSanBong.setText("x1,5");

        txtToi_ThongTinSanBong.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtToi_ThongTinSanBong.setForeground(new java.awt.Color(0, 0, 102));
        txtToi_ThongTinSanBong.setText("x1.5");

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnSan5A.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnSan5ALayout = new javax.swing.GroupLayout(pnSan5A);
        pnSan5A.setLayout(pnSan5ALayout);
        pnSan5ALayout.setHorizontalGroup(
            pnSan5ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        pnSan5ALayout.setVerticalGroup(
            pnSan5ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel4.add(pnSan5A, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 120, 150));

        pnSan5B.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnSan5BLayout = new javax.swing.GroupLayout(pnSan5B);
        pnSan5B.setLayout(pnSan5BLayout);
        pnSan5BLayout.setHorizontalGroup(
            pnSan5BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
        pnSan5BLayout.setVerticalGroup(
            pnSan5BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel4.add(pnSan5B, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 110, 150));

        pnSan7A.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnSan7ALayout = new javax.swing.GroupLayout(pnSan7A);
        pnSan7A.setLayout(pnSan7ALayout);
        pnSan7ALayout.setHorizontalGroup(
            pnSan7ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );
        pnSan7ALayout.setVerticalGroup(
            pnSan7ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );

        jPanel4.add(pnSan7A, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 170, 130));

        pnSan5C.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnSan5CLayout = new javax.swing.GroupLayout(pnSan5C);
        pnSan5C.setLayout(pnSan5CLayout);
        pnSan5CLayout.setHorizontalGroup(
            pnSan5CLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        pnSan5CLayout.setVerticalGroup(
            pnSan5CLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );

        jPanel4.add(pnSan5C, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 120, 160));

        pnSan11A.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnSan11ALayout = new javax.swing.GroupLayout(pnSan11A);
        pnSan11A.setLayout(pnSan11ALayout);
        pnSan11ALayout.setHorizontalGroup(
            pnSan11ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        pnSan11ALayout.setVerticalGroup(
            pnSan11ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );

        jPanel4.add(pnSan11A, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 240, 160));

        pnSan7B.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnSan7BLayout = new javax.swing.GroupLayout(pnSan7B);
        pnSan7B.setLayout(pnSan7BLayout);
        pnSan7BLayout.setHorizontalGroup(
            pnSan7BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        pnSan7BLayout.setVerticalGroup(
            pnSan7BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        jPanel4.add(pnSan7B, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 140, 180));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/soDoSanBongNho.png"))); // NOI18N
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 380));

        javax.swing.GroupLayout pnThongTinSanBongLayout = new javax.swing.GroupLayout(pnThongTinSanBong);
        pnThongTinSanBong.setLayout(pnThongTinSanBongLayout);
        pnThongTinSanBongLayout.setHorizontalGroup(
            pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel94)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinSanBongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                        .addComponent(jLabel92)
                        .addGap(41, 41, 41)
                        .addComponent(cbLoaiSan_ThongTinSanBong, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel93)
                        .addGap(85, 85, 85)
                        .addComponent(txtToi_ThongTinSanBong)
                        .addGap(154, 154, 154))
                    .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinSanBongLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel95)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSang_ThongTinSanBong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(30, 30, 30)
                .addComponent(jLabel96)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtChieu_ThongTinSanBong)
                .addGap(172, 172, 172)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jLabel97)
                .addGap(205, 205, 205))
        );
        pnThongTinSanBongLayout.setVerticalGroup(
            pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92)
                    .addComponent(cbLoaiSan_ThongTinSanBong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel93))
                .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                        .addComponent(jLabel94)
                        .addGap(33, 33, 33)
                        .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel21))
                                .addComponent(jLabel95)
                                .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel96)
                                    .addComponent(txtChieu_ThongTinSanBong))
                                .addComponent(jLabel97))
                            .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                                .addComponent(txtSang_ThongTinSanBong)
                                .addGap(63, 63, 63))))
                    .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                        .addComponent(txtToi_ThongTinSanBong)
                        .addGap(63, 63, 63)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnTong.add(pnThongTinSanBong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnDichVu.setBackground(new java.awt.Color(254, 235, 208));
        pnDichVu.setPreferredSize(new java.awt.Dimension(1150, 890));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setForeground(new java.awt.Color(0, 102, 102));

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(0, 0, 102));
        jLabel98.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-street-food-64.png"))); // NOI18N
        jLabel98.setText("DỊCH VỤ");

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(0, 0, 102));
        jLabel99.setText("Xem Dịch Vụ");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel99))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel98)))
                .addContainerGap(908, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jLabel98)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jLabel99)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel30.setBackground(new java.awt.Color(179, 193, 135));

        jLabel107.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(0, 102, 0));
        jLabel107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-energy-drink-48.png"))); // NOI18N
        jLabel107.setText("THỨC ĂN VÀ THỨC UỐNG");

        tbThucUong_DichVu.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbThucUong_DichVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "TÊN DỊCH VỤ", "GIÁ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbThucUong_DichVu.setRowHeight(35);
        jScrollPane1.setViewportView(tbThucUong_DichVu);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel31.setBackground(new java.awt.Color(200, 232, 249));

        jLabel108.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(37, 102, 142));
        jLabel108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-bouncing-ball-50.png"))); // NOI18N
        jLabel108.setText("DỊCH VỤ KHÁC");

        tbDichVuKhac_DichVu.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbDichVuKhac_DichVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "TÊN DỊCH VỤ", "GIÁ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDichVuKhac_DichVu.setRowHeight(35);
        jScrollPane14.setViewportView(tbDichVuKhac_DichVu);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175))
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnDichVuLayout = new javax.swing.GroupLayout(pnDichVu);
        pnDichVu.setLayout(pnDichVuLayout);
        pnDichVuLayout.setHorizontalGroup(
            pnDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnDichVuLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnDichVuLayout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        pnDichVuLayout.setVerticalGroup(
            pnDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDichVuLayout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(pnDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pnTong.add(pnDichVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnThongTinDatTruoc.setBackground(new java.awt.Color(254, 235, 208));
        pnThongTinDatTruoc.setForeground(new java.awt.Color(0, 0, 102));
        pnThongTinDatTruoc.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnThongTinDatTruoc.setPreferredSize(new java.awt.Dimension(1150, 890));
        pnThongTinDatTruoc.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setForeground(new java.awt.Color(0, 102, 102));
        jPanel33.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(0, 0, 102));
        jLabel103.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-reserve-48 (1).png"))); // NOI18N
        jLabel103.setText("ĐẶT TRƯỚC SÂN");

        lbDatTruoc_ThongTinDatTruoc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbDatTruoc_ThongTinDatTruoc.setForeground(new java.awt.Color(0, 0, 102));
        lbDatTruoc_ThongTinDatTruoc.setText("ĐẶT TRƯỚC");
        lbDatTruoc_ThongTinDatTruoc.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbDatTruoc_ThongTinDatTruocMouseMoved(evt);
            }
        });
        lbDatTruoc_ThongTinDatTruoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDatTruoc_ThongTinDatTruocMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbDatTruoc_ThongTinDatTruocMouseExited(evt);
            }
        });

        jLabel105.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(0, 0, 102));
        jLabel105.setText("THÔNG TIN ĐẶT TRƯỚC");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(716, Short.MAX_VALUE))
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel103))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(lbDatTruoc_ThongTinDatTruoc)
                        .addGap(105, 105, 105)
                        .addComponent(jLabel105)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel103)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDatTruoc_ThongTinDatTruoc)
                    .addComponent(jLabel105))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnThongTinDatTruoc.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 110));

        jPanel34.setBackground(new java.awt.Color(200, 232, 249));

        jLabel106.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(37, 102, 142));
        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-more-details-30.png"))); // NOI18N
        jLabel106.setText("CHI TIẾT ĐẶT TRƯỚC");

        tbCTDatTruoc_TTDT.setAutoCreateRowSorter(true);
        tbCTDatTruoc_TTDT.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbCTDatTruoc_TTDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "TÊN SÂN", "LOẠI SÂN", "THƠI GIAN ĐẾN", "THỜI GIAN TRẢ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCTDatTruoc_TTDT.setGridColor(new java.awt.Color(107, 208, 219));
        tbCTDatTruoc_TTDT.setRowHeight(30);
        jScrollPane8.setViewportView(tbCTDatTruoc_TTDT);

        btnHuyCtDatTruoc.setBackground(new java.awt.Color(28, 184, 160));
        btnHuyCtDatTruoc.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        btnHuyCtDatTruoc.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyCtDatTruoc.setText("HUỶ ĐẶT TRƯỚC");
        btnHuyCtDatTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyCtDatTruocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap(258, Short.MAX_VALUE)
                .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnHuyCtDatTruoc)
                .addContainerGap())
            .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel34Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyCtDatTruoc))
                .addContainerGap(624, Short.MAX_VALUE))
            .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel34Layout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pnThongTinDatTruoc.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 690, 670));

        jPanel35.setBackground(new java.awt.Color(179, 193, 135));

        jLabel109.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(0, 102, 0));
        jLabel109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-reserve-32 (1).png"))); // NOI18N
        jLabel109.setText("DANH SÁCH ĐẶT TRƯỚC");

        tbThongTinDatTruoc_TTDT.setAutoCreateRowSorter(true);
        tbThongTinDatTruoc_TTDT.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbThongTinDatTruoc_TTDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "MÃ ĐẶT TRƯỚC", "NGÀY ĐẶT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbThongTinDatTruoc_TTDT.setGridColor(new java.awt.Color(107, 208, 219));
        tbThongTinDatTruoc_TTDT.setRowHeight(30);
        tbThongTinDatTruoc_TTDT.getTableHeader().setReorderingAllowed(false);
        tbThongTinDatTruoc_TTDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbThongTinDatTruoc_TTDTMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbThongTinDatTruoc_TTDT);

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel109)
                .addContainerGap(80, Short.MAX_VALUE))
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel35Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(634, Short.MAX_VALUE))
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel35Layout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pnThongTinDatTruoc.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 390, 680));

        pnTong.add(pnThongTinDatTruoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnThongTinCaNhan.setBackground(new java.awt.Color(254, 235, 208));
        pnThongTinCaNhan.setForeground(new java.awt.Color(0, 0, 102));
        pnThongTinCaNhan.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnThongTinCaNhan.setPreferredSize(new java.awt.Dimension(1150, 890));
        pnThongTinCaNhan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setForeground(new java.awt.Color(0, 102, 102));
        jPanel36.setPreferredSize(new java.awt.Dimension(1192, 115));

        jLabel114.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(0, 0, 102));
        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-information-50.png"))); // NOI18N
        jLabel114.setText("THÔNG TIN CÁ NHÂN");

        jLabel115.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(0, 0, 102));
        jLabel115.setText("THÔNG TIN CÁ NHÂN");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel114))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel115)
                            .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(813, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel114)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel115)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnThongTinCaNhan.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 140));

        pnTong_TTCN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnSuaThongTin_TTCN.setBackground(new java.awt.Color(255, 255, 255));
        pnSuaThongTin_TTCN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnSuaThongTin_TTCN.setPreferredSize(new java.awt.Dimension(790, 350));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 102));
        jLabel30.setText("HỌ TÊN:");
        jLabel30.setToolTipText("");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 102));
        jLabel32.setText("SDT:");
        jLabel32.setToolTipText("");

        txtHoTen_SuaThongTin_TTCN.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtHoTen_SuaThongTin_TTCN.setForeground(new java.awt.Color(0, 0, 102));
        txtHoTen_SuaThongTin_TTCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtHoTen_SuaThongTin_TTCNMouseClicked(evt);
            }
        });

        txtSDT_SuaThongTin_TTCN.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtSDT_SuaThongTin_TTCN.setForeground(new java.awt.Color(0, 0, 102));
        txtSDT_SuaThongTin_TTCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSDT_SuaThongTin_TTCNMouseClicked(evt);
            }
        });

        lblWarnHoTen_SuaTT.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblWarnHoTen_SuaTT.setForeground(new java.awt.Color(255, 0, 0));
        lblWarnHoTen_SuaTT.setText("Họ tên không được phép để trống ");
        lblWarnHoTen_SuaTT.setVisible(false);

        lblWarnSDT_SuaTT.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblWarnSDT_SuaTT.setForeground(new java.awt.Color(255, 0, 0));
        lblWarnSDT_SuaTT.setText("SDT phải có 10 đến 11 số");
        lblWarnSDT_SuaTT.setVisible(false);

        javax.swing.GroupLayout pnSuaThongTin_TTCNLayout = new javax.swing.GroupLayout(pnSuaThongTin_TTCN);
        pnSuaThongTin_TTCN.setLayout(pnSuaThongTin_TTCNLayout);
        pnSuaThongTin_TTCNLayout.setHorizontalGroup(
            pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSuaThongTin_TTCNLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel32))
                .addGap(36, 36, 36)
                .addGroup(pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWarnSDT_SuaTT)
                    .addComponent(lblWarnHoTen_SuaTT)
                    .addComponent(txtSDT_SuaThongTin_TTCN, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoTen_SuaThongTin_TTCN, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        pnSuaThongTin_TTCNLayout.setVerticalGroup(
            pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSuaThongTin_TTCNLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(txtHoTen_SuaThongTin_TTCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnHoTen_SuaTT)
                .addGap(59, 59, 59)
                .addGroup(pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtSDT_SuaThongTin_TTCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnSDT_SuaTT)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        pnTong_TTCN.add(pnSuaThongTin_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnDoiMatKhau_TTCN.setBackground(new java.awt.Color(255, 255, 255));
        pnDoiMatKhau_TTCN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnDoiMatKhau_TTCN.setPreferredSize(new java.awt.Dimension(790, 350));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 102));
        jLabel27.setText("MẬT KHẨU CŨ:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 102));
        jLabel28.setText("MẬT KHẨU MỚI:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 102));
        jLabel29.setText("XÁC NHẬN MK:");

        pfMKCu_DoiMK_TTCN.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        pfMKCu_DoiMK_TTCN.setForeground(new java.awt.Color(0, 0, 102));
        pfMKCu_DoiMK_TTCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pfMKCu_DoiMK_TTCNMouseClicked(evt);
            }
        });

        pfMKMoi_DoiMK_TTCN.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        pfMKMoi_DoiMK_TTCN.setForeground(new java.awt.Color(0, 0, 102));
        pfMKMoi_DoiMK_TTCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pfMKMoi_DoiMK_TTCNMouseClicked(evt);
            }
        });

        pfXNMK_DoiMK_TTCN.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        pfXNMK_DoiMK_TTCN.setForeground(new java.awt.Color(0, 0, 102));
        pfXNMK_DoiMK_TTCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pfXNMK_DoiMK_TTCNMouseClicked(evt);
            }
        });

        lbThongBaoLoi.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout pnDoiMatKhau_TTCNLayout = new javax.swing.GroupLayout(pnDoiMatKhau_TTCN);
        pnDoiMatKhau_TTCN.setLayout(pnDoiMatKhau_TTCNLayout);
        pnDoiMatKhau_TTCNLayout.setHorizontalGroup(
            pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDoiMatKhau_TTCNLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27)
                    .addComponent(jLabel29))
                .addGap(58, 58, 58)
                .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbThongBaoLoi, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pfMKMoi_DoiMK_TTCN, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pfMKCu_DoiMK_TTCN, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pfXNMK_DoiMK_TTCN, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        pnDoiMatKhau_TTCNLayout.setVerticalGroup(
            pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDoiMatKhau_TTCNLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(pfMKCu_DoiMK_TTCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pfMKMoi_DoiMK_TTCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(pfXNMK_DoiMK_TTCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(lbThongBaoLoi, javax.swing.GroupLayout.DEFAULT_SIZE, 12, Short.MAX_VALUE)
                .addGap(86, 86, 86))
        );

        pnTong_TTCN.add(pnDoiMatKhau_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnCTTTCN_TTCN.setBackground(new java.awt.Color(255, 255, 255));
        pnCTTTCN_TTCN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setText("HỌ TÊN:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 102));
        jLabel25.setText("CMND:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 102));
        jLabel26.setText("SDT:");

        lbHoTen_CTTTCN_TTCN.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lbHoTen_CTTTCN_TTCN.setForeground(new java.awt.Color(0, 0, 102));
        lbHoTen_CTTTCN_TTCN.setText("HỌ TÊN");

        lbCMND_CTTTCN_TTCN.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lbCMND_CTTTCN_TTCN.setForeground(new java.awt.Color(0, 0, 102));
        lbCMND_CTTTCN_TTCN.setText("CMND");

        lbSDT_CTTTCN_TTCN.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lbSDT_CTTTCN_TTCN.setForeground(new java.awt.Color(0, 0, 102));
        lbSDT_CTTTCN_TTCN.setText("SDT");

        javax.swing.GroupLayout pnCTTTCN_TTCNLayout = new javax.swing.GroupLayout(pnCTTTCN_TTCN);
        pnCTTTCN_TTCN.setLayout(pnCTTTCN_TTCNLayout);
        pnCTTTCN_TTCNLayout.setHorizontalGroup(
            pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCTTTCN_TTCNLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCTTTCN_TTCNLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(131, 131, 131)
                        .addComponent(lbHoTen_CTTTCN_TTCN))
                    .addGroup(pnCTTTCN_TTCNLayout.createSequentialGroup()
                        .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addGap(142, 142, 142)
                        .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbCMND_CTTTCN_TTCN)
                            .addComponent(lbSDT_CTTTCN_TTCN))))
                .addContainerGap(452, Short.MAX_VALUE))
        );
        pnCTTTCN_TTCNLayout.setVerticalGroup(
            pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCTTTCN_TTCNLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lbHoTen_CTTTCN_TTCN))
                .addGap(57, 57, 57)
                .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lbCMND_CTTTCN_TTCN))
                .addGap(59, 59, 59)
                .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lbSDT_CTTTCN_TTCN))
                .addContainerGap(122, Short.MAX_VALUE))
        );

        pnTong_TTCN.add(pnCTTTCN_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnThongTinCaNhan.add(pnTong_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 790, 350));

        btnSuaThongTin_TTCN.setBackground(new java.awt.Color(28, 184, 160));
        btnSuaThongTin_TTCN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSuaThongTin_TTCN.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaThongTin_TTCN.setText("SỬA THÔNG TIN");
        btnSuaThongTin_TTCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaThongTin_TTCNActionPerformed(evt);
            }
        });
        pnThongTinCaNhan.add(btnSuaThongTin_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 650, -1, -1));

        btnDoiMatKhau_TTCN.setBackground(new java.awt.Color(28, 184, 160));
        btnDoiMatKhau_TTCN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDoiMatKhau_TTCN.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiMatKhau_TTCN.setText("ĐỔI MẬT KHẨU");
        btnDoiMatKhau_TTCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhau_TTCNActionPerformed(evt);
            }
        });
        pnThongTinCaNhan.add(btnDoiMatKhau_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 650, -1, -1));

        pnTong.add(pnThongTinCaNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnLichSuThue.setBackground(new java.awt.Color(254, 235, 208));
        pnLichSuThue.setForeground(new java.awt.Color(0, 0, 102));
        pnLichSuThue.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnLichSuThue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnSoDoSanBong1.setBackground(new java.awt.Color(254, 235, 208));
        pnSoDoSanBong1.setPreferredSize(new java.awt.Dimension(1150, 890));

        jPanel39.setBackground(new java.awt.Color(179, 193, 135));

        jLabel112.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(0, 102, 0));
        jLabel112.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-order-history-30.png"))); // NOI18N
        jLabel112.setText("LỊCH SỬ THUÊ");

        tbLichSuThue_LichSuThue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tbLichSuThue_LichSuThue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "MÃ HÓA ĐƠN", "NGÀY THUÊ", "TỔNG TIỀN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbLichSuThue_LichSuThue.setGridColor(new java.awt.Color(107, 208, 219));
        tbLichSuThue_LichSuThue.setRowHeight(35);
        tbLichSuThue_LichSuThue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLichSuThue_LichSuThueMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tbLichSuThue_LichSuThue);

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7)
                .addContainerGap())
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(440, 440, 440)
                .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setForeground(new java.awt.Color(0, 102, 102));
        jPanel37.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel104.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(0, 0, 102));
        jLabel104.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-list-50.png"))); // NOI18N
        jLabel104.setText("LỊCH SỬ THUÊ");

        lbDatTruoc_ThongTinDatTruoc1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbDatTruoc_ThongTinDatTruoc1.setForeground(new java.awt.Color(0, 0, 102));
        lbDatTruoc_ThongTinDatTruoc1.setText("LỊCH SỬ THUÊ");
        lbDatTruoc_ThongTinDatTruoc1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbDatTruoc_ThongTinDatTruoc1MouseMoved(evt);
            }
        });
        lbDatTruoc_ThongTinDatTruoc1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDatTruoc_ThongTinDatTruoc1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbDatTruoc_ThongTinDatTruoc1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel104))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(lbDatTruoc_ThongTinDatTruoc1))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(886, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel104)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(lbDatTruoc_ThongTinDatTruoc1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnXemHoaDon_LichSuThue.setBackground(new java.awt.Color(28, 184, 160));
        btnXemHoaDon_LichSuThue.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnXemHoaDon_LichSuThue.setForeground(new java.awt.Color(255, 255, 255));
        btnXemHoaDon_LichSuThue.setText("XEM HÓA ĐƠN");
        btnXemHoaDon_LichSuThue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemHoaDon_LichSuThueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnSoDoSanBong1Layout = new javax.swing.GroupLayout(pnSoDoSanBong1);
        pnSoDoSanBong1.setLayout(pnSoDoSanBong1Layout);
        pnSoDoSanBong1Layout.setHorizontalGroup(
            pnSoDoSanBong1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSoDoSanBong1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSoDoSanBong1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnSoDoSanBong1Layout.createSequentialGroup()
                .addGap(469, 469, 469)
                .addComponent(btnXemHoaDon_LichSuThue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnSoDoSanBong1Layout.setVerticalGroup(
            pnSoDoSanBong1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSoDoSanBong1Layout.createSequentialGroup()
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXemHoaDon_LichSuThue)
                .addContainerGap())
        );

        pnLichSuThue.add(pnSoDoSanBong1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnTong.add(pnLichSuThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnTong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 886, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnTong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemSan_DatSan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSan_DatSan4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemSan_DatSan4ActionPerformed

    private void btnThemSan_DatSan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSan_DatSan5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemSan_DatSan5ActionPerformed

    private void cbLoaiSan_DatSan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiSan_DatSan2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbLoaiSan_DatSan2ActionPerformed

    private void cbGioDen_DatSan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGioDen_DatSan4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGioDen_DatSan4ActionPerformed

    private void cbGioDen_DatSan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGioDen_DatSan5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGioDen_DatSan5ActionPerformed

    private void dcNgay_DatSan2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcNgay_DatSan2PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_dcNgay_DatSan2PropertyChange

    private void btnThemSan_DatSan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSan_DatSan6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemSan_DatSan6ActionPerformed

    private void btnThemSan_DatSan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSan_DatSan7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemSan_DatSan7ActionPerformed

    private void cbLoaiSan_DatSan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiSan_DatSan3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbLoaiSan_DatSan3ActionPerformed

    private void cbGioDen_DatSan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGioDen_DatSan6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGioDen_DatSan6ActionPerformed

    private void cbGioDen_DatSan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGioDen_DatSan7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGioDen_DatSan7ActionPerformed

    private void dcNgay_DatSan3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcNgay_DatSan3PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_dcNgay_DatSan3PropertyChange

    private void btnXacNhanDat_DatSanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanDat_DatSanActionPerformed
        // TODO add your handling code here:
        System.out.println("btnXacNhanDat_DatSanActionPerformed");
        String ngayDat=formmatNgay.format(dcNgay_DatSan.getDate());
        DefaultTableModel dtm=(DefaultTableModel) tbCTDatTruoc_DatSan.getModel();
        int soDong=dtm.getRowCount();
        if (soDong==0) {
            return;
        }
        THONGTINPHIEUDAT phieuDat=new THONGTINPHIEUDAT();
        phieuDat.setCMND(khach.getCMND());
        phieuDat.setNgayDat(ngayDat);
        phieuDat.luuPhieuDat();
        int maDatTruoc=phieuDat.getMaDatTruoc();
        System.out.println(maDatTruoc);
        for(int i=0;i<soDong;i++)
        {
            String tenSan= dtm.getValueAt(i, 0).toString();
            String tgDuKienDen=dtm.getValueAt(i, 3).toString();
            String tgDuKienTra=dtm.getValueAt(i, 4).toString();
            
            CTDATTRUOC ct= new CTDATTRUOC();
            ct.setMaDatTruoc(maDatTruoc);
            ct.setTenSan(tenSan);
            ct.setTgDuKienDen(tgDuKienDen);
            ct.setTgDuKienTra(tgDuKienTra);
            ct.luuCTPhieuDat();
            
        }
        JOptionPane.showMessageDialog(this, "ĐẶT TRƯỚC THÀNH CÔNG");
        setDatTruocSan(cbLoaiSan_DatSan.getSelectedItem().toString(), dcNgay_DatSan.getDate(),cbGioDen_DatSan.getSelectedItem().toString(), cbGioTra_DatSan.getSelectedItem().toString());
        DefaultTableModel dtm1=(DefaultTableModel) tbCTDatTruoc_DatSan.getModel();
        dtm1.setNumRows(0);
        tbCTDatTruoc_DatSan.setModel(dtm1);
        settbThongTinDatTruoc_TTCN();
    }//GEN-LAST:event_btnXacNhanDat_DatSanActionPerformed

    private void btnThemSan_DatSanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSan_DatSanActionPerformed
        // TODO add your handling code here:
        System.out.println("btnThemSan_DatSanActionPerformed");
        
        String ngay=formmatNgay.format(dcNgay_DatSan.getDate());
        String gioDen=cbGioDen_DatSan.getSelectedItem().toString();
        String gioTra=cbGioTra_DatSan.getSelectedItem().toString();
        String tenSan= lSanKhaDung_DatSan.getSelectedValue().toString();
        String loaiSan = layLoaiSanTuTenSan(tenSan);
        Vector vt=new Vector();
        vt.add(tenSan);
        vt.add(loaiSan);
        vt.add(ngay);
        vt.add(gioDen);
        vt.add(gioTra);
        vt.add(layGiaTien(tenSan));
        
        boolean kt=true;
        
        DefaultTableModel dtm =(DefaultTableModel) tbCTDatTruoc_DatSan.getModel();
        System.out.println(dtm.getRowCount());
        for(int i=0;i<dtm.getRowCount();i++)
        {
            if((tenSan.equals(dtm.getValueAt(i, 0).toString()) && ngay.equals(dtm.getValueAt(i, 2))&& gioDen.equals(dtm.getValueAt(i, 3))) ||
                    (tenSan.equals(dtm.getValueAt(i, 0).toString()) && ngay.equals(dtm.getValueAt(i, 2))&& gioTra.equals(dtm.getValueAt(i, 4))))
            {
                kt=false;
            }
        }
        if(kt==true)
        {
            dtm.addRow(vt);
            System.out.println("them san vao ct san ");
             tbCTDatTruoc_DatSan.setModel(dtm);
             System.out.println(vt.get(1));
        }
        
    }//GEN-LAST:event_btnThemSan_DatSanActionPerformed

    private void cbLoaiSan_DatSanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiSan_DatSanActionPerformed
        // TODO add your handling code here:
        System.out.println("cbLoaiSan_DatSanActionPerformed");
        if(kiemTraNgayQuaKhu(dcNgay_DatSan.getDate())==true)
        {
            
            setDatTruocSan(cbLoaiSan_DatSan.getSelectedItem().toString(), dcNgay_DatSan.getDate(),cbGioDen_DatSan.getSelectedItem().toString(), cbGioTra_DatSan.getSelectedItem().toString());
        
        }
        else
        {
           
            Vector vt=new Vector();
            lSanKhaDung_DatSan.setListData(vt);
        
            
        }
    }//GEN-LAST:event_cbLoaiSan_DatSanActionPerformed

    private void cbGioDen_DatSanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGioDen_DatSanActionPerformed
        // TODO add your handling code here:
         System.out.println("cbGioDen_DatSanActionPerformed");
       if(kiemTraNgayQuaKhu(dcNgay_DatSan.getDate())==true)
        {
            setGioTra();
            setDatTruocSan(cbLoaiSan_DatSan.getSelectedItem().toString(), dcNgay_DatSan.getDate(),cbGioDen_DatSan.getSelectedItem().toString(), cbGioTra_DatSan.getSelectedItem().toString());
        
        }
        else
        {
           
            Vector vt=new Vector();
            lSanKhaDung_DatSan.setListData(vt);
        
            
        }
    }//GEN-LAST:event_cbGioDen_DatSanActionPerformed

    private void cbGioTra_DatSanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGioTra_DatSanActionPerformed
        // TODO add your handling code here:
        System.out.println(" cbGioTra_DatSanActionPerformed");
      if(kiemTraNgayQuaKhu(dcNgay_DatSan.getDate())==true)
        {
           
            setDatTruocSan(cbLoaiSan_DatSan.getSelectedItem().toString(), dcNgay_DatSan.getDate(),cbGioDen_DatSan.getSelectedItem().toString(), cbGioTra_DatSan.getSelectedItem().toString());
        
        }
        else
        {
           
            Vector vt=new Vector();
            lSanKhaDung_DatSan.setListData(vt);
        
            
        }
    }//GEN-LAST:event_cbGioTra_DatSanActionPerformed

    private void dcNgay_DatSanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcNgay_DatSanPropertyChange
        // TODO add your handling code here:
        String dcDay = formmatNgay.format(dcNgay_DatSan.getDate());
        String today = formmatNgay.format(new Date());
        try {
            if (formmatNgay.parse(dcDay).before(formmatNgay.parse(today))) {
                dcNgay_DatSan.setDate(formmatNgay.parse(today));
            }
        } catch (ParseException ex) {
            System.out.println("ERROR: khach.GDKHACH.dcNgay_DatSanPropertyChange() 3589");
        }
        
         DefaultTableModel dtm=(DefaultTableModel )tbCTDatTruoc_DatSan.getModel();
        if(dtm.getRowCount()!=0)
        {
            if(formmatNgay.format(dcNgay_DatSan.getDate()).equals(dtm.getValueAt(0,2).toString())==false)
            {
                Object[] options = {"Có","Không"};
                int n = JOptionPane.showOptionDialog(null,
                    "Nếu Đổi Ngày sẽ tạo lại Phiếu đặt trước khác, Bạn có muốn đổi ngày không ?",
                    "Xác Nhận",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                     options,
                    options[1]);
                if(n==1)
                {
                    try {
                        
                        String date =dtm.getValueAt(0,2).toString() ;
                        java.util.Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                        dcNgay_DatSan.setDate(date2);
                    } 
                    catch (ParseException ex) {
                        System.out.println("loi ham Ngay_DatSanPropertyChange");
                    }  
                   
                }
                else
                {
                    
                   DefaultTableModel dtm1=(DefaultTableModel)tbCTDatTruoc_DatSan.getModel();
                   while(dtm1.getRowCount()!=0)
                   {
                       dtm1.removeRow(0);
                   }
                   tbCTDatTruoc_DatSan.setModel(dtm1);
                   
                }
            }
            

        }
        
        if(kiemTraNgayQuaKhu(dcNgay_DatSan.getDate())==true)
        {
            setGioDenHienTai();
            setDatTruocSan(cbLoaiSan_DatSan.getSelectedItem().toString(), dcNgay_DatSan.getDate(),cbGioDen_DatSan.getSelectedItem().toString(), cbGioTra_DatSan.getSelectedItem().toString());
        }
        else
        {
            Vector vt=new Vector();
            lSanKhaDung_DatSan.setListData(vt);
 
        }
    }//GEN-LAST:event_dcNgay_DatSanPropertyChange

    private void cbLoaiSan_ThongTinSanBongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiSan_ThongTinSanBongActionPerformed
        // TODO add your handling code here:
        System.out.println("cbloaisan_TTSB");
        String a= cbLoaiSan_ThongTinSanBong.getSelectedItem().toString();
        loadSanBong_TTSB(a);
      
    }//GEN-LAST:event_cbLoaiSan_ThongTinSanBongActionPerformed

    private void tbThongTinDatTruoc_TTDTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbThongTinDatTruoc_TTDTMouseClicked
        // TODO add your handling code here:
       
         System.out.println("tbThongTinDatTruoc_TTCNMouseClicked ");
       
        
        int i= tbThongTinDatTruoc_TTDT.getSelectedRow();
      DefaultTableModel dtm= (DefaultTableModel)tbThongTinDatTruoc_TTDT.getModel();
      
      
      int maDatTruoc=Integer.parseInt(dtm.getValueAt(i, 0).toString());
      
        settbCTDatTruoc_TTCN(maDatTruoc);
       

    }//GEN-LAST:event_tbThongTinDatTruoc_TTDTMouseClicked

    private void pfMKCu_DoiMK_TTCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pfMKCu_DoiMK_TTCNMouseClicked
        // TODO add your handling code here:
        pfMKCu_DoiMK_TTCN.setForeground(Color.black);
        
    }//GEN-LAST:event_pfMKCu_DoiMK_TTCNMouseClicked

    private void pfMKMoi_DoiMK_TTCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pfMKMoi_DoiMK_TTCNMouseClicked
        // TODO add your handling code here:
       pfMKMoi_DoiMK_TTCN.setForeground(Color.black);
    }//GEN-LAST:event_pfMKMoi_DoiMK_TTCNMouseClicked

    private void pfXNMK_DoiMK_TTCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pfXNMK_DoiMK_TTCNMouseClicked
        // TODO add your handling code here:
        pfXNMK_DoiMK_TTCN.setForeground(Color.black);
    }//GEN-LAST:event_pfXNMK_DoiMK_TTCNMouseClicked

    private void txtHoTen_SuaThongTin_TTCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHoTen_SuaThongTin_TTCNMouseClicked
        // TODO add your handling code here:
        txtHoTen_SuaThongTin_TTCN.setForeground(Color.black);
        lblWarnHoTen_SuaTT.setVisible(false);
    }//GEN-LAST:event_txtHoTen_SuaThongTin_TTCNMouseClicked

    private void txtSDT_SuaThongTin_TTCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSDT_SuaThongTin_TTCNMouseClicked
        // TODO add your handling code here:
        txtSDT_SuaThongTin_TTCN.setForeground(Color.black);
        lblWarnSDT_SuaTT.setVisible(false);
    }//GEN-LAST:event_txtSDT_SuaThongTin_TTCNMouseClicked

    private void btnSuaThongTin_TTCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaThongTin_TTCNActionPerformed
        // TODO add your handling code here:
        System.out.println(" btnSuaThongTin_TTCNActionPerformed");
        
        if(btnSuaThongTin_TTCN.getText().toString().equals("SỬA THÔNG TIN"))
        {
            pnCTTTCN_TTCN.setVisible(false);
            pnDoiMatKhau_TTCN.setVisible(false);
            pnSuaThongTin_TTCN.setVisible(true);
            txtHoTen_SuaThongTin_TTCN.setText(khach.getHoTen());
            txtSDT_SuaThongTin_TTCN.setText(khach.getSDT());
            btnSuaThongTin_TTCN.setText("XÁC NHẬN SỬA");
            btnDoiMatKhau_TTCN.setText("ĐỔI MẬT KHẨU");
        }
        else if(btnSuaThongTin_TTCN.getText().toString().equals("XÁC NHẬN SỬA"))
        {
            String hoTen=txtHoTen_SuaThongTin_TTCN.getText().toString();
            String SDT= txtSDT_SuaThongTin_TTCN.getText().toString();
            
            boolean check=true;
            if(hoTen.length()==0)
            {
                txtHoTen_SuaThongTin_TTCN.setForeground(Color.red);
                lblWarnHoTen_SuaTT.setVisible(true);
                check=false;
            }
            if(SDT.matches("[0-9]{10,11}")==false)
            {
                txtSDT_SuaThongTin_TTCN.setForeground(Color.red);
                lblWarnSDT_SuaTT.setVisible(true);
                check=false;
            }
            
            if(check==true)
            {
                //THONGTINKHACH khach1 =new THONGTINKHACH(khach.getTaiKhoan());
                
                //khach1.setHoTen(hoTen); 
               // khach1.setCMND(CMND);
               // khach1.setSDT(SDT);
               khach.setHoTen(hoTen);
               khach.setSDT(SDT);
               khach.updateThongTin();
               setThongTin();
               JOptionPane.showMessageDialog(this, "Sửa Thông Tin Thành Công");
               pnCTTTCN_TTCN.setVisible(true);
                
                
                
                btnSuaThongTin_TTCN.setText("SỬA THÔNG TIN");
                btnDoiMatKhau_TTCN.setText("ĐỔI MẬT KHẨU");
                pnCTTTCN_TTCN.setVisible(true);
                pnSuaThongTin_TTCN.setVisible(false);
                pnDoiMatKhau_TTCN.setVisible(false);
            }
            
            
        }
       
    }//GEN-LAST:event_btnSuaThongTin_TTCNActionPerformed

    private void btnDoiMatKhau_TTCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhau_TTCNActionPerformed
        // TODO add your handling code here:
        System.out.println("btnDoiMatKhau_TTCNActionPerformed");
        lbThongBaoLoi.setText("");
        if(btnDoiMatKhau_TTCN.getText().toString().equals("ĐỔI MẬT KHẨU"))
        {
            pnCTTTCN_TTCN.setVisible(false);
            pnDoiMatKhau_TTCN.setVisible(true);
            pnSuaThongTin_TTCN.setVisible(false);
        
            btnDoiMatKhau_TTCN.setText("XÁC NHẬN ĐỔI");
            btnSuaThongTin_TTCN.setText("SỬA THÔNG TIN");
            
        }
        else if(btnDoiMatKhau_TTCN.getText().toString().equals("XÁC NHẬN ĐỔI"))
        {
            String matKhauCu=pfMKCu_DoiMK_TTCN.getText().toString();
            String matKhauMoi=pfMKMoi_DoiMK_TTCN.getText().toString();
            String xacNhanMK=pfXNMK_DoiMK_TTCN.getText().toString();
            
            boolean check=true;
           
            
            if(matKhauMoi.matches("\\w{6,}")==false)
            {
                lbThongBaoLoi.setText("Mật khẩu mới không chuẩn");
                pfMKMoi_DoiMK_TTCN.setForeground(Color.red);
                check=false;
            }
            if(xacNhanMK.matches("\\w{6,}")==false)
            {
                lbThongBaoLoi.setText("Xác Nhận Mật khẩu không chuẩn");
                pfXNMK_DoiMK_TTCN.setForeground(Color.red);
                check=false;
            }
            if(xacNhanMK.equals(matKhauMoi)==false)
            {
                lbThongBaoLoi.setText("Xác nhận mật khẩu sai");
                pfMKMoi_DoiMK_TTCN.setForeground(Color.red);
                pfXNMK_DoiMK_TTCN.setForeground(Color.red);
                check=false;
            }
             if(matKhauCu.equals(khach.getMatKhau())==false)
            {
                pfMKCu_DoiMK_TTCN.setForeground(Color.red);
                lbThongBaoLoi.setText("Mật khẩu cũ bị sai");
                check=false;
            }
            
            
            
            
            
            if(check==true)
            {
                
                DSTAIKHOAN.suaDSTaiKhoan(khach.getTaiKhoan(), matKhauMoi);
               setThongTin();
               JOptionPane.showMessageDialog(this, "Đổi Mật Khẩu Thành Công");
               pnCTTTCN_TTCN.setVisible(true);
               lbThongBaoLoi.setText("");
                
                
                
                btnDoiMatKhau_TTCN.setText("ĐỔI MẬT KHẨU");
                btnSuaThongTin_TTCN.setText("SỬA THÔNG TIN");
                pnCTTTCN_TTCN.setVisible(true);
                pnDoiMatKhau_TTCN.setVisible(false);
                pnSuaThongTin_TTCN.setVisible(false);
            }
            
            
        }
        
    }//GEN-LAST:event_btnDoiMatKhau_TTCNActionPerformed

    private void btnDatTruocSanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatTruocSanMouseMoved
        // TODO add your handling code here:
        lbDatTruocSan.setForeground(new Color(0,0,102));
        btnDatTruocSan.setBackground(new Color(145,250,255));
        
        
    }//GEN-LAST:event_btnDatTruocSanMouseMoved

    private void btnDatTruocSanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatTruocSanMouseExited
        // TODO add your handling code here:
        lbDatTruocSan.setForeground(new Color(0,0,102));
        btnDatTruocSan.setBackground(Color.white);
    }//GEN-LAST:event_btnDatTruocSanMouseExited

    private void btnThongTinSanBongMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongTinSanBongMouseMoved
        // TODO add your handling code here:
        lbThongTinSanBong.setForeground(new Color(0,0,102));
        btnThongTinSanBong.setBackground(new Color(145,250,255));
    }//GEN-LAST:event_btnThongTinSanBongMouseMoved

    private void btnXemDichVuMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXemDichVuMouseMoved
        // TODO add your handling code here:
        lbXemDichVu.setForeground(new Color(0,0,102));
        btnXemDichVu.setBackground(new Color(145,250,255));
    }//GEN-LAST:event_btnXemDichVuMouseMoved

    private void btnLichSuThueMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLichSuThueMouseMoved
        // TODO add your handling code here:
        lbLichSuThue.setForeground(new Color(0,0,102));
        btnLichSuThue.setBackground(new Color(145,250,255));
    }//GEN-LAST:event_btnLichSuThueMouseMoved

    private void btnThongTinSanBongMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongTinSanBongMouseExited
        // TODO add your handling code here:
        lbThongTinSanBong.setForeground(new Color(0,0,102));
        btnThongTinSanBong.setBackground(Color.white);
    }//GEN-LAST:event_btnThongTinSanBongMouseExited

    private void btnXemDichVuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXemDichVuMouseExited
        // TODO add your handling code here:
        lbXemDichVu.setForeground(new Color(0,0,102));
        btnXemDichVu.setBackground(Color.white);
    }//GEN-LAST:event_btnXemDichVuMouseExited

    private void btnLichSuThueMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLichSuThueMouseExited
        // TODO add your handling code here:
        lbLichSuThue.setForeground(new Color(0,0,102));
        btnLichSuThue.setBackground(Color.white);
    }//GEN-LAST:event_btnLichSuThueMouseExited

    private void lbAboutUsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAboutUsMouseMoved
        // TODO add your handling code here:
        lbAboutUs.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbAboutUsMouseMoved

    private void lbTroGiupMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTroGiupMouseMoved
        // TODO add your handling code here:
        lbTroGiup.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbTroGiupMouseMoved

    private void lbLienHeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLienHeMouseMoved
        // TODO add your handling code here:
        lbLienHe.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbLienHeMouseMoved

    private void lbAboutUsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAboutUsMouseExited
        // TODO add your handling code here:
        lbAboutUs.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbAboutUsMouseExited

    private void lbTroGiupMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTroGiupMouseExited
        // TODO add your handling code here:
        lbTroGiup.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbTroGiupMouseExited

    private void lbLienHeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLienHeMouseExited
        // TODO add your handling code here:
        lbLienHe.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbLienHeMouseExited

    private void lbNgayHienTaiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbNgayHienTaiMouseMoved
        // TODO add your handling code here:
        lbNgayHienTai.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbNgayHienTaiMouseMoved

    private void lbNgayHienTaiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbNgayHienTaiMouseExited
        // TODO add your handling code here:
        lbNgayHienTai.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbNgayHienTaiMouseExited

    private void lbDangXuatMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangXuatMouseMoved
        // TODO add your handling code here:
        lbDangXuat.setForeground(Color.red);
    }//GEN-LAST:event_lbDangXuatMouseMoved

    private void lbDangXuatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangXuatMouseExited
        // TODO add your handling code here:
        lbDangXuat.setForeground(new Color(0,153,153));
    }//GEN-LAST:event_lbDangXuatMouseExited

    private void lbThongTinDatTruoc_DatTruocMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinDatTruoc_DatTruocMouseMoved
        // TODO add your handling code here:
        lbThongTinDatTruoc_DatTruoc.setForeground(new Color(0,0,102));
        
    }//GEN-LAST:event_lbThongTinDatTruoc_DatTruocMouseMoved

    private void lbThongTinDatTruoc_DatTruocMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinDatTruoc_DatTruocMouseExited
        // TODO add your handling code here:
        lbThongTinDatTruoc_DatTruoc.setForeground(new Color(204,204,204));
        
    }//GEN-LAST:event_lbThongTinDatTruoc_DatTruocMouseExited

    private void lbDatTruoc_ThongTinDatTruocMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatTruoc_ThongTinDatTruocMouseMoved
        // TODO add your handling code here:
        lbDatTruoc_ThongTinDatTruoc.setForeground(new Color(0,0,102));
       
    }//GEN-LAST:event_lbDatTruoc_ThongTinDatTruocMouseMoved

    private void btnDatTruocSanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatTruocSanMouseClicked
        // TODO add your handling code here:
        pnDatTruocSan.setVisible(true);
        pnThongTinSanBong.setVisible(false);
        pnDichVu.setVisible(false);
        pnThongTinCaNhan.setVisible(false);
        
        pnThongTinDatTruoc.setVisible(false);
        pnLichSuThue.setVisible(false);
    }//GEN-LAST:event_btnDatTruocSanMouseClicked

    private void btnThongTinSanBongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongTinSanBongMouseClicked
        // TODO add your handling code here:
        pnDatTruocSan.setVisible(false);
        pnThongTinSanBong.setVisible(true);
        pnDichVu.setVisible(false);
        pnThongTinCaNhan.setVisible(false);
        
        pnThongTinDatTruoc.setVisible(false);
        pnLichSuThue.setVisible(false);
    }//GEN-LAST:event_btnThongTinSanBongMouseClicked

    private void btnXemDichVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXemDichVuMouseClicked
       // TODO add your handling code here:
        pnDatTruocSan.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnDichVu.setVisible(true);
        pnThongTinCaNhan.setVisible(false);
        
        pnThongTinDatTruoc.setVisible(false);
        pnLichSuThue.setVisible(false);
        setThucUong();
        setDichVuKhac();
    }//GEN-LAST:event_btnXemDichVuMouseClicked

    private void btnLichSuThueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLichSuThueMouseClicked
        //LUULS
        pnDatTruocSan.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnDichVu.setVisible(false);
        pnThongTinCaNhan.setVisible(false);
        
        pnThongTinDatTruoc.setVisible(false);
        pnLichSuThue.setVisible(true);
        loadLichSuThue();
    }//GEN-LAST:event_btnLichSuThueMouseClicked

    private void lbThongTinDatTruoc_DatTruocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinDatTruoc_DatTruocMouseClicked
        // TODO add your handling code here:
        pnDatTruocSan.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnDichVu.setVisible(false);
        pnThongTinCaNhan.setVisible(false);
        
        pnThongTinDatTruoc.setVisible(true);
        pnLichSuThue.setVisible(false);
    }//GEN-LAST:event_lbThongTinDatTruoc_DatTruocMouseClicked

    private void lbDatTruoc_ThongTinDatTruocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatTruoc_ThongTinDatTruocMouseClicked
        // TODO add your handling code here:
        pnDatTruocSan.setVisible(true);
        pnThongTinSanBong.setVisible(false);
        pnDichVu.setVisible(false);
        pnThongTinCaNhan.setVisible(false);
       
        pnThongTinDatTruoc.setVisible(false);
        pnLichSuThue.setVisible(false);
    }//GEN-LAST:event_lbDatTruoc_ThongTinDatTruocMouseClicked

    private void lbDatTruoc_ThongTinDatTruocMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatTruoc_ThongTinDatTruocMouseExited
        // TODO add your handling code here:
        lbDatTruoc_ThongTinDatTruoc.setForeground(new Color(204,204,204));
        
    }//GEN-LAST:event_lbDatTruoc_ThongTinDatTruocMouseExited

    private void lbDatTruoc_ThongTinDatTruoc1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatTruoc_ThongTinDatTruoc1MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_lbDatTruoc_ThongTinDatTruoc1MouseMoved

    private void lbDatTruoc_ThongTinDatTruoc1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatTruoc_ThongTinDatTruoc1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbDatTruoc_ThongTinDatTruoc1MouseClicked

    private void lbDatTruoc_ThongTinDatTruoc1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatTruoc_ThongTinDatTruoc1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lbDatTruoc_ThongTinDatTruoc1MouseExited

    private void tbLichSuThue_LichSuThueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLichSuThue_LichSuThueMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLichSuThue_LichSuThueMouseClicked

    private void lbTenTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenTaiKhoanMouseClicked
        // TODO add your handling code here:\ pnDatTruocSan.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnDichVu.setVisible(false);
        pnThongTinCaNhan.setVisible(true);
       
        pnThongTinDatTruoc.setVisible(false);
        pnLichSuThue.setVisible(false);
        
        pnCTTTCN_TTCN.setVisible(true);
        pnDoiMatKhau_TTCN.setVisible(false);
        pnSuaThongTin_TTCN.setVisible(false);
        
        
       
    }//GEN-LAST:event_lbTenTaiKhoanMouseClicked

    private void lbXoa_DatSanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbXoa_DatSanMouseMoved
        // TODO add your handling code here:
        lbXoa_DatSan.setForeground(Color.red);
    }//GEN-LAST:event_lbXoa_DatSanMouseMoved

    private void lbXoa_DatSanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbXoa_DatSanMouseExited
        // TODO add your handling code here:
        lbXoa_DatSan.setForeground(new Color(37,102,142));
    }//GEN-LAST:event_lbXoa_DatSanMouseExited

    private void lbXoa_DatSanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbXoa_DatSanMouseClicked
        // TODO add your handling code here:
        int i=tbCTDatTruoc_DatSan.getSelectedRow();
        DefaultTableModel dtm =(DefaultTableModel)tbCTDatTruoc_DatSan.getModel();
        dtm.removeRow(i);
        tbCTDatTruoc_DatSan.setModel(dtm);
    }//GEN-LAST:event_lbXoa_DatSanMouseClicked

    private void tbSanBong_ThongTinSanBongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanBong_ThongTinSanBongMouseClicked
        // TODO add your handling code here:
        DefaultTableModel dtm=(DefaultTableModel) tbSanBong_ThongTinSanBong.getModel();
        int i=tbSanBong_ThongTinSanBong.getSelectedRow();
        String sanBong= dtm.getValueAt(i, 0).toString();
        
        if(sanBong.equals("5A"))
        {
            
            pnSan5A.setVisible(true);
            pnSan5B.setVisible(false);
            pnSan5C.setVisible(false);
            pnSan11A.setVisible(false);
            pnSan7A.setVisible(false);
            pnSan7B.setVisible(false);
        }
        else if(sanBong.equals("5B"))
        {
            pnSan5A.setVisible(false);
            pnSan5B.setVisible(true);
            pnSan5C.setVisible(false);
            pnSan11A.setVisible(false);
            pnSan7A.setVisible(false);
            pnSan7B.setVisible(false);
        }
        else if(sanBong.equals("5C"))
        {
            pnSan5A.setVisible(false);
            pnSan5B.setVisible(false);
            pnSan5C.setVisible(true);
            pnSan11A.setVisible(false);
            pnSan7A.setVisible(false);
            pnSan7B.setVisible(false);
        }
        else if(sanBong.equals("7A"))
        {
            pnSan5A.setVisible(false);
            pnSan5B.setVisible(false);
            pnSan5C.setVisible(false);
            pnSan11A.setVisible(false);
            pnSan7A.setVisible(true);
            pnSan7B.setVisible(false);
        }
        else if(sanBong.equals("7B"))
        {
            pnSan5A.setVisible(false);
            pnSan5B.setVisible(false);
            pnSan5C.setVisible(false);
            pnSan11A.setVisible(false);
            pnSan7A.setVisible(false);
            pnSan7B.setVisible(true);
        }
        else if(sanBong.equals("11A"))
        {
            pnSan5A.setVisible(false);
            pnSan5B.setVisible(false);
            pnSan5C.setVisible(false);
            pnSan11A.setVisible(true);
            pnSan7A.setVisible(false);
            pnSan7B.setVisible(false);
        }
        
    }//GEN-LAST:event_tbSanBong_ThongTinSanBongMouseClicked

    private void btnXemHoaDon_LichSuThueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemHoaDon_LichSuThueActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dtm= (DefaultTableModel) tbLichSuThue_LichSuThue.getModel();
        int i= tbLichSuThue_LichSuThue.getSelectedRow();
        
        String maHoaDon= dtm.getValueAt(i, 0).toString();
        HoaDon HD= new HoaDon(maHoaDon);
        HD.setVisible(true);
    }//GEN-LAST:event_btnXemHoaDon_LichSuThueActionPerformed

    private void lbTenTaiKhoanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenTaiKhoanMouseMoved
        // TODO add your handling code here:
       
    }//GEN-LAST:event_lbTenTaiKhoanMouseMoved

    private void lbTenTaiKhoanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenTaiKhoanMouseExited
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lbTenTaiKhoanMouseExited

    private void pnTenTaiKhoanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnTenTaiKhoanMouseMoved
        // TODO add your handling code here:
        pnTenTaiKhoan.setBackground(new Color(240,240,240));
    }//GEN-LAST:event_pnTenTaiKhoanMouseMoved

    private void pnTenTaiKhoanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnTenTaiKhoanMouseExited
        // TODO add your handling code here:
        pnTenTaiKhoan.setBackground(Color.white);
    }//GEN-LAST:event_pnTenTaiKhoanMouseExited

    private void pnTenTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnTenTaiKhoanMouseClicked
        // TODO add your handling code here:
         pnDatTruocSan.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnDichVu.setVisible(false);
        pnThongTinCaNhan.setVisible(true);
       
        pnThongTinDatTruoc.setVisible(false);
        pnLichSuThue.setVisible(false);
        
        pnCTTTCN_TTCN.setVisible(true);
        pnDoiMatKhau_TTCN.setVisible(false);
        pnSuaThongTin_TTCN.setVisible(false);
        btnSuaThongTin_TTCN.setText("SỬA THÔNG TIN");
        btnDoiMatKhau_TTCN.setText("ĐỔI MẬT KHẨU");
        txtSDT_SuaThongTin_TTCNMouseClicked(evt);
        txtHoTen_SuaThongTin_TTCNMouseClicked(evt);
    }//GEN-LAST:event_pnTenTaiKhoanMouseClicked

    private void lbDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangXuatMouseClicked
        // TODO add your handling code here:
        dispose();
        new DANGNHAP.DANGNHAP().setVisible(true);
    }//GEN-LAST:event_lbDangXuatMouseClicked

    private void btnHuyCtDatTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyCtDatTruocActionPerformed
        int row;
        try {
            row = tbCTDatTruoc_TTDT.getSelectedRow();
            String tenSan = String.valueOf(tbCTDatTruoc_TTDT.getValueAt(row, 0));
            String dkDen = String.valueOf(tbCTDatTruoc_TTDT.getValueAt(row, 2));
            String dkTra = String.valueOf(tbCTDatTruoc_TTDT.getValueAt(row, 3));

            int choose = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn hủy "
                    + "đặt trước sân " + tenSan + " từ " + dkDen + " - " + dkTra + " ?",
                    "Hủy đặt trước sân?", JOptionPane.YES_NO_OPTION);
            if (choose == JOptionPane.YES_OPTION) {
                IODATA.huyCtDatTruoc(thongTinPhieuDat.getMaDatTruoc(), tenSan);
                JOptionPane.showMessageDialog(rootPane, "Đặt trước sân " + tenSan + " từ " + dkDen + " - " + dkTra + " đã hủy", "Hủy đặt trước thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Chưa chọn đặt trước muốn hủy",
                    "Không thể hủy đặt trước", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }//GEN-LAST:event_btnHuyCtDatTruocActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnDatTruocSan;
    private javax.swing.JButton btnDoiMatKhau_TTCN;
    private javax.swing.JButton btnHuyCtDatTruoc;
    private javax.swing.JPanel btnLichSuThue;
    private javax.swing.JButton btnSuaThongTin_TTCN;
    private javax.swing.JButton btnThemSan_DatSan;
    private javax.swing.JButton btnThemSan_DatSan4;
    private javax.swing.JButton btnThemSan_DatSan5;
    private javax.swing.JButton btnThemSan_DatSan6;
    private javax.swing.JButton btnThemSan_DatSan7;
    private javax.swing.JPanel btnThongTinSanBong;
    private javax.swing.JButton btnXacNhanDat_DatSan;
    private javax.swing.JPanel btnXemDichVu;
    private javax.swing.JButton btnXemHoaDon_LichSuThue;
    private javax.swing.JComboBox<String> cbGioDen_DatSan;
    private javax.swing.JComboBox<String> cbGioDen_DatSan4;
    private javax.swing.JComboBox<String> cbGioDen_DatSan5;
    private javax.swing.JComboBox<String> cbGioDen_DatSan6;
    private javax.swing.JComboBox<String> cbGioDen_DatSan7;
    private javax.swing.JComboBox<String> cbGioTra_DatSan;
    private javax.swing.JComboBox<String> cbLoaiSan_DatSan;
    private javax.swing.JComboBox<String> cbLoaiSan_DatSan2;
    private javax.swing.JComboBox<String> cbLoaiSan_DatSan3;
    private javax.swing.JComboBox<String> cbLoaiSan_ThongTinSanBong;
    private com.toedter.calendar.JDateChooser dcNgay_DatSan;
    private com.toedter.calendar.JDateChooser dcNgay_DatSan2;
    private com.toedter.calendar.JDateChooser dcNgay_DatSan3;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> lSanKhaDung_DatSan;
    private javax.swing.JList<String> lSanKhaDung_DatSan2;
    private javax.swing.JList<String> lSanKhaDung_DatSan3;
    private javax.swing.JLabel lbAboutUs;
    private javax.swing.JLabel lbCMND_CTTTCN_TTCN;
    private javax.swing.JLabel lbDangXuat;
    private javax.swing.JLabel lbDatTruocSan;
    private javax.swing.JLabel lbDatTruoc_ThongTinDatTruoc;
    private javax.swing.JLabel lbDatTruoc_ThongTinDatTruoc1;
    private javax.swing.JLabel lbDongHo;
    private javax.swing.JLabel lbHoTen_CTTTCN_TTCN;
    private javax.swing.JLabel lbLichSuThue;
    private javax.swing.JLabel lbLienHe;
    private javax.swing.JLabel lbNgayHienTai;
    private javax.swing.JLabel lbSDT_CTTTCN_TTCN;
    private javax.swing.JLabel lbTenTaiKhoan;
    private javax.swing.JLabel lbThongBaoLoi;
    private javax.swing.JLabel lbThongTinDatTruoc_DatTruoc;
    private javax.swing.JLabel lbThongTinSanBong;
    private javax.swing.JLabel lbTroGiup;
    private javax.swing.JLabel lbXemDichVu;
    private javax.swing.JLabel lbXoa_DatSan;
    private javax.swing.JLabel lblGioHienTai;
    private javax.swing.JLabel lblNgayHomNay;
    private javax.swing.JLabel lblWarnHoTen_SuaTT;
    private javax.swing.JLabel lblWarnSDT_SuaTT;
    private javax.swing.JPasswordField pfMKCu_DoiMK_TTCN;
    private javax.swing.JPasswordField pfMKMoi_DoiMK_TTCN;
    private javax.swing.JPasswordField pfXNMK_DoiMK_TTCN;
    private javax.swing.JPanel pnCTTTCN_TTCN;
    private javax.swing.JPanel pnDatTruocSan;
    private javax.swing.JPanel pnDichVu;
    private javax.swing.JPanel pnDoiMatKhau_TTCN;
    private javax.swing.JPanel pnDongHo;
    private javax.swing.JPanel pnLichSuThue;
    private javax.swing.JPanel pnMenu;
    private javax.swing.JPanel pnSan11A;
    private javax.swing.JPanel pnSan5A;
    private javax.swing.JPanel pnSan5B;
    private javax.swing.JPanel pnSan5C;
    private javax.swing.JPanel pnSan7A;
    private javax.swing.JPanel pnSan7B;
    private javax.swing.JPanel pnSoDoSanBong1;
    private javax.swing.JPanel pnSuaThongTin_TTCN;
    private javax.swing.JPanel pnTenTaiKhoan;
    private javax.swing.JPanel pnThongTinCaNhan;
    private javax.swing.JPanel pnThongTinDatTruoc;
    private javax.swing.JPanel pnThongTinSanBong;
    private javax.swing.JPanel pnTong;
    private javax.swing.JPanel pnTong_TTCN;
    private javax.swing.JTable tbCTDatTruoc_DatSan;
    private javax.swing.JTable tbCTDatTruoc_DatSan2;
    private javax.swing.JTable tbCTDatTruoc_DatSan3;
    private javax.swing.JTable tbCTDatTruoc_TTDT;
    private javax.swing.JTable tbDichVuKhac_DichVu;
    private javax.swing.JTable tbLichSuThue_LichSuThue;
    private javax.swing.JTable tbSanBong_ThongTinSanBong;
    private javax.swing.JTable tbThongTinDatTruoc_TTDT;
    private javax.swing.JTable tbThucUong_DichVu;
    private javax.swing.JLabel txtChieu_ThongTinSanBong;
    private javax.swing.JTextField txtHoTen_SuaThongTin_TTCN;
    private javax.swing.JTextField txtSDT_SuaThongTin_TTCN;
    private javax.swing.JLabel txtSang_ThongTinSanBong;
    private javax.swing.JLabel txtToi_ThongTinSanBong;
    // End of variables declaration//GEN-END:variables
}
