/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

import DANGNHAP.*;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerDateModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author ad
 */
public class GDNHANVIEN extends javax.swing.JFrame {

    private DefaultTableModel dftblDsSanDangThue = new DefaultTableModel();
    private DefaultTableModel dftblDsPhieuDatTruoc = new DefaultTableModel();
    private IODATA ioData = new IODATA();
    private ArrayList<CT_THUE> dsCt_thues = new ArrayList<CT_THUE>();
    private ArrayList<CT_THUE> dsCtSanDangThue = new ArrayList<CT_THUE>();
    private ArrayList<PHIEUDATTRUOC> dsPhieuDTs = new ArrayList<PHIEUDATTRUOC>();
    private CLOCKNV clock;
    
    private DefaultTableModel dftblSanKhaDung_ThueNgay = new DefaultTableModel();
    private DefaultTableModel dftblCtPhieuThueNgay = new DefaultTableModel();
    private SimpleDateFormat formatGio = new SimpleDateFormat("HH:mm a");
    private SimpleDateFormat formatNgay = new SimpleDateFormat("MM/dd/yyyy");
    private ArrayList<SANBONG> dsSanBongKhaDung_ThueNgay = new ArrayList<SANBONG>();
    private ArrayList<SANBONG> dsAllSanBongs = new ArrayList<SANBONG>();
    private ArrayList<KHACH> dsKhachHang = new ArrayList<KHACH>();
    
    private DefaultTableModel dftblDoUong = new DefaultTableModel();
    private DefaultTableModel dftblDoAn = new DefaultTableModel();
    private DefaultTableModel dftblDungCu = new DefaultTableModel();
    private DefaultTableModel dftblCtDichVuThue = new DefaultTableModel();
    private ArrayList<DICHVU> dsDv = new ArrayList<DICHVU>();
    
    private DefaultTableModel dftblSan5 = new DefaultTableModel();
    private DefaultTableModel dftblSan7 = new DefaultTableModel();
    private DefaultTableModel dftblSan11 = new DefaultTableModel();
    private DefaultTableModel dftblCtDatTruoc = new DefaultTableModel();
    private ArrayList<SANBONG> dsSanKhaDung_DatTruoc = new ArrayList<SANBONG>();
    private Date ngayDatOldValue = new Date();
    private SimpleDateFormat spnHourFormat = new SimpleDateFormat("HH:00 a");
    private SimpleDateFormat dcDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    private DefaultTableModel dftblAllPhieuThue_ThanhToan= new DefaultTableModel();
    private DefaultTableModel dftblDichVu_HoaDon = new DefaultTableModel();
    private DefaultTableModel dftblCtThue_HoaDon = new DefaultTableModel();
    private ArrayList<PHIEUTHUE> dsPhieuThue_ThanhToan = new ArrayList<PHIEUTHUE>();
    private PHIEUTHUE phieuThue = new PHIEUTHUE();
    private NHANVIEN nhanVien = null;
    
    private Color sanTrong = new Color(153,255,153,150);
    private Color sanDangThue = new Color(255,127,129,130);
    private Color sanBaoTri = new Color(252,227,25,130);
    
    File fileHoaDon;
    
    private DefaultTableModel dftblDsPhieuDatTruoc_TTDT = new DefaultTableModel();
    private DefaultTableModel dftblCTDatTruoc_TTDT = new DefaultTableModel();
    private ArrayList<PHIEUDATTRUOC> allPhieuDTs = new ArrayList<PHIEUDATTRUOC>();
    
    public GDNHANVIEN(String maNV) {
        initComponents();
        pnThongTinCaNhan.setVisible(false);
        pnDatDichVu.setVisible(false);
        pnSoDoSanBong.setVisible(false);
        pnDatTruocSan.setVisible(false);
        pnDatSanNgay.setVisible(false);
        pnQuanLyPhieuThue.setVisible(true);
        pnLichSuThue.setVisible(false);   
        pnThongTinDatTruoc.setVisible(false);
        
        clock = new CLOCKNV(lblNgayHomNay, lblGioHienTai,rootPane, ioData, GDNHANVIEN.this);
        clock.start();
        
        dftblSanKhaDung_ThueNgay = (DefaultTableModel) tblSanKhaDung_ThueNgay.getModel();
        dftblCtPhieuThueNgay = (DefaultTableModel) tblCtPhieuThueNgay.getModel();
        
        cbxChonLoaiSan.addItem(LOAISAN.TATCA);
        cbxChonLoaiSan.addItem(LOAISAN.SAN5);
        cbxChonLoaiSan.addItem(LOAISAN.SAN7);
        cbxChonLoaiSan.addItem(LOAISAN.SAN11);
        cbxChonLoaiSan.setSelectedIndex(0);
        
        dsAllSanBongs = ioData.getAlldsSanBong();
        
        dsKhachHang = ioData.getDsKhachHang();
        AutoCompleteDecorator.decorate(cbxCMND_ThueNgay);
        AutoCompleteDecorator.decorate(cbxCMND_DatTruoc);
        
        dftblDsSanDangThue = (DefaultTableModel) tblDsSanDangThue.getModel();
        dftblDsPhieuDatTruoc = (DefaultTableModel) tblPhieuDatTruoc.getModel();
        
        addTblDsPhieuDT();
        refreshTblDsSanDangThue();
        
        dftblDoAn = (DefaultTableModel) tblDoAn.getModel();
        dftblDoUong = (DefaultTableModel) tblDoUong.getModel();
        dftblDungCu = (DefaultTableModel) tblDungCu.getModel();
        dftblCtDatTruoc = (DefaultTableModel) tblCTDatTruoc.getModel();
        
        dftblCtDichVuThue = (DefaultTableModel) tblCtDichVuThue.getModel();
        dftblCtDichVuThue.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 2 && row>=0) {
                    String slst = String.valueOf(tblCtDichVuThue.getValueAt(row, column));
                    System.out.println(slst);
                    if (isNumeric(slst)) {
                        int sl = Integer.parseInt(slst);
                        int dongia = timDichVu(String.valueOf(tblCtDichVuThue.getValueAt(row, 0))).getDonGia();
                        tblCtDichVuThue.setValueAt(sl*dongia, row, 3);
                    } else{
                        tblCtDichVuThue.setValueAt(1, row, column);
                        int dongia = timDichVu(String.valueOf(tblCtDichVuThue.getValueAt(row, 0))).getDonGia();
                        tblCtDichVuThue.setValueAt(dongia, row, 3);
                        JOptionPane.showMessageDialog(rootPane, "Số lượng phải là một số","ERROR!" , HEIGHT);
                    }
                }
            }
        });
        
        dftblSan5 = (DefaultTableModel) tblSan5.getModel();
        dftblSan7 = (DefaultTableModel) tblSan7.getModel();
        dftblSan11 = (DefaultTableModel) tblSan11.getModel();
        dftblCtDatTruoc = (DefaultTableModel) tblCTDatTruoc.getModel();
        Date spnTemp = new Date();
        long hour = 60*1000*60;
        spnTemp.setTime(spnTemp.getTime()+hour);
        String temp = spnHourFormat.format(spnTemp);
        try {
            spnGioHenDen_DatSan.setValue(spnHourFormat.parse(temp));
            spnGioHenTra_DatSan.setValue(spnHourFormat.parse(temp));
        } catch (ParseException ex) {
            System.out.println("Không thể gán spnHour: qlsanbong.GDNHANVIEN.<init>() 140");
        }
        
        dftblAllPhieuThue_ThanhToan = (DefaultTableModel) tblAllPhieuThue_HoaDon.getModel();
        addTblAllPhieuThue_ThanhToan();
        dftblCtThue_HoaDon = (DefaultTableModel) tblCtThue_HoaDon.getModel();
        dftblDichVu_HoaDon = (DefaultTableModel) tblDichVu_HoaDon.getModel();
        
        ghiTtNhanVien(maNV);
        refreshSoDoSanBong();
        dftblDsPhieuDatTruoc_TTDT = (DefaultTableModel) tblDsPhieuDatTruoc_TTDT.getModel();
        dftblCTDatTruoc_TTDT = (DefaultTableModel) tblCTDatTruoc_TTDT.getModel();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jSeparator6 = new javax.swing.JSeparator();
        pnMenu = new javax.swing.JPanel();
        btnThongTinPhieuThue = new javax.swing.JPanel();
        lbThongTinPhieuThue = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lbAboutUs = new javax.swing.JLabel();
        lbTroGiup = new javax.swing.JLabel();
        lbLienHe = new javax.swing.JLabel();
        lbGiayPhep = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        btnDatTruocSan = new javax.swing.JPanel();
        lbThongTinDatTruoc = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnSoDoSanBong = new javax.swing.JPanel();
        lbQuanLiSanBong = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pnlTaiKhoan = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblHoTenNv_Menu = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        lblNgayHomNay = new javax.swing.JLabel();
        lblGioHienTai = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JButton();
        pnTong = new javax.swing.JPanel();
        pnThongTinCaNhan = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jSeparator21 = new javax.swing.JSeparator();
        pnTong_TTCN = new javax.swing.JPanel();
        pnSuaThongTin_TTCN = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        btnXacNhanSua = new javax.swing.JButton();
        txtHoTenUpdate = new javax.swing.JTextField();
        txtSdtUpdate = new javax.swing.JTextField();
        lblWarnHoTen = new javax.swing.JLabel();
        lblWarnSdt = new javax.swing.JLabel();
        pnDoiMatKhau_TTCN = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtMkCu = new javax.swing.JPasswordField();
        lblWarnSaiMk = new javax.swing.JLabel();
        txtMkMoi = new javax.swing.JPasswordField();
        txtMkXacNhan = new javax.swing.JPasswordField();
        lblWarnMkKhongTrung = new javax.swing.JLabel();
        btnXacNhanDoiMk = new javax.swing.JButton();
        pnCTTTCN_TTCN = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblHotenNV = new javax.swing.JLabel();
        lblCmndNV = new javax.swing.JLabel();
        lblSdtNV = new javax.swing.JLabel();
        lblMaNv_TTCN = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        btnSuaThongTin_TTCN = new javax.swing.JButton();
        btnDoiMatKhau_TTCN = new javax.swing.JButton();
        pnSoDoSanBong = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        pnl5B = new javax.swing.JPanel();
        lblTt5B = new javax.swing.JLabel();
        pnl5A = new javax.swing.JPanel();
        lblTt5A = new javax.swing.JLabel();
        pnl11A = new javax.swing.JPanel();
        lblTt11A = new javax.swing.JLabel();
        pnl5C = new javax.swing.JPanel();
        lblTt5C = new javax.swing.JLabel();
        pnl7B = new javax.swing.JPanel();
        lblTt7B = new javax.swing.JLabel();
        pnl7A = new javax.swing.JPanel();
        lblTt7A = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnDatTruocSan = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        lblDatSanNgay_DSN1 = new javax.swing.JLabel();
        lblThongTinDatTruoc_DTS = new javax.swing.JLabel();
        jSeparator23 = new javax.swing.JSeparator();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtHoTen_DatTruoc = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblCTDatTruoc = new javax.swing.JTable();
        bttBoCtDatTruoc = new javax.swing.JButton();
        txtSDT_DatTruoc = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        btnThemSan_DatSan = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblSan5 = new javax.swing.JTable();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblSan7 = new javax.swing.JTable();
        jScrollPane21 = new javax.swing.JScrollPane();
        tblSan11 = new javax.swing.JTable();
        dcNgayDatTruoc = new com.toedter.calendar.JDateChooser();
        cbxCMND_DatTruoc = new javax.swing.JComboBox<>();

        Date date1 = new Date();
        SpinnerDateModel sm1 = new SpinnerDateModel(date1, null, null, Calendar.HOUR_OF_DAY);
        spnGioHenDen_DatSan = new javax.swing.JSpinner(sm1);
        Date date2 = new Date();
        SpinnerDateModel sm2 = new SpinnerDateModel(date2, null, null, Calendar.HOUR_OF_DAY);
        spnGioHenTra_DatSan = new javax.swing.JSpinner(sm2);
        btnXacNhanDat_DatTruoc = new javax.swing.JButton();
        lblWarnHoTen_DatTruoc = new javax.swing.JLabel();
        lblWarnCMND_DatTruoc = new javax.swing.JLabel();
        lblWarnSDT_DatTruoc = new javax.swing.JLabel();
        pnDatSanNgay = new javax.swing.JPanel();
        cbxCMND_ThueNgay = new javax.swing.JComboBox<>();

        txtSdt = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        lblDatSanNgay_DSN = new javax.swing.JLabel();
        lbThongTinPhieuThue_DSN = new javax.swing.JLabel();
        jSeparator22 = new javax.swing.JSeparator();
        lblLichSuThue_DSN = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblSanKhaDung_ThueNgay = new javax.swing.JTable();
        jLabel71 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        cbxChonLoaiSan = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblCtPhieuThueNgay = new javax.swing.JTable();
        tblCtPhieuThueNgay.getTableHeader().setFont(new Font("Cambria Math", Font.BOLD, 17));
        tblCtPhieuThueNgay.getTableHeader().setOpaque(false);
        tblCtPhieuThueNgay.getTableHeader().setBackground(new Color(245, 80, 21));
        btnXacNhan = new javax.swing.JButton();
        Date date = new Date();
        SpinnerDateModel clock =
        new SpinnerDateModel(date,null,null,Calendar.HOUR_OF_DAY);
        spnGioHenTra = new javax.swing.JSpinner(clock);
        jLabel69 = new javax.swing.JLabel();
        pnQuanLyPhieuThue = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        lbDatSanNgay_TTPT = new javax.swing.JLabel();
        lbThongTinPhieuThue_QLPT = new javax.swing.JLabel();
        jSeparator24 = new javax.swing.JSeparator();
        lblLichSuThue_TTPT = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblPhieuDatTruoc = new javax.swing.JTable();
        btnNhanSan = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        tblDsSanDangThue = new javax.swing.JTable();
        jLabel63 = new javax.swing.JLabel();
        btnTraSan = new javax.swing.JButton();
        btnThemDv = new javax.swing.JButton();
        pnDatDichVu = new javax.swing.JPanel();
        btnXacNhanDatDv = new javax.swing.JButton();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        lblSanDatDv = new javax.swing.JLabel();
        lblMHDdatDv = new javax.swing.JLabel();
        lblTenKhachDatDv = new javax.swing.JLabel();
        bttThemDv = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblDoUong = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDoAn = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblDungCu = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        tblCtDichVuThue = new javax.swing.JTable();
        bttBoDv = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel34 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        pnLichSuThue = new javax.swing.JPanel();
        pnlAllPhieuThue_ThanhToan = new javax.swing.JPanel();
        btnXemCtHoaDon = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblAllPhieuThue_HoaDon = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        txtTimKiemLichSu = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        lbDatSanNgay_LST = new javax.swing.JLabel();
        lbThongTinPhieuThue_LST = new javax.swing.JLabel();
        jSeparator25 = new javax.swing.JSeparator();
        lbThongTinNhanVien_QL7 = new javax.swing.JLabel();
        pnlHoaDon = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblCtThue_HoaDon = new javax.swing.JTable();
        jLabel60 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblDichVu_HoaDon = new javax.swing.JTable();
        jLabel66 = new javax.swing.JLabel();
        btnInHoaDon = new javax.swing.JButton();
        lblHoTen_HoaDon = new javax.swing.JLabel();
        lblTenNVPV_HoaDon = new javax.swing.JLabel();
        lblNgayThue_HoaDon = new javax.swing.JLabel();
        lblTongTien_HoaDon = new javax.swing.JLabel();
        lblMaHoaDon_HoaDon = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        pnlBack_HoaDon = new javax.swing.JPanel();
        lblBack = new javax.swing.JLabel();
        pnThongTinDatTruoc = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        lblDatTruocSan_TTDT = new javax.swing.JLabel();
        lblLichSuThue_DSN2 = new javax.swing.JLabel();
        jSeparator26 = new javax.swing.JSeparator();
        jPanel41 = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblDsPhieuDatTruoc_TTDT = new javax.swing.JTable();
        jPanel42 = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        tblCTDatTruoc_TTDT = new javax.swing.JTable();

        jScrollPane1.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnMenu.setBackground(new java.awt.Color(255, 255, 255));
        pnMenu.setPreferredSize(new java.awt.Dimension(329, 885));
        pnMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnThongTinPhieuThue.setBackground(new java.awt.Color(220, 220, 230));
        btnThongTinPhieuThue.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnThongTinPhieuThueMouseMoved(evt);
            }
        });
        btnThongTinPhieuThue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThongTinPhieuThueMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThongTinPhieuThueMouseExited(evt);
            }
        });

        lbThongTinPhieuThue.setBackground(new java.awt.Color(204, 204, 204));
        lbThongTinPhieuThue.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbThongTinPhieuThue.setForeground(new java.awt.Color(51, 51, 51));
        lbThongTinPhieuThue.setText("QUẢN LÍ PHIẾU THUÊ");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-add-list-30.png"))); // NOI18N

        javax.swing.GroupLayout btnThongTinPhieuThueLayout = new javax.swing.GroupLayout(btnThongTinPhieuThue);
        btnThongTinPhieuThue.setLayout(btnThongTinPhieuThueLayout);
        btnThongTinPhieuThueLayout.setHorizontalGroup(
            btnThongTinPhieuThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnThongTinPhieuThueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbThongTinPhieuThue)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        btnThongTinPhieuThueLayout.setVerticalGroup(
            btnThongTinPhieuThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThongTinPhieuThueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnThongTinPhieuThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(lbThongTinPhieuThue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnMenu.add(btnThongTinPhieuThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 280, 70));
        pnMenu.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 649, 1, 10));

        lbAboutUs.setBackground(new java.awt.Color(204, 204, 204));
        lbAboutUs.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbAboutUs.setForeground(new java.awt.Color(204, 204, 204));
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
        pnMenu.add(lbAboutUs, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 666, -1, 28));

        lbTroGiup.setBackground(new java.awt.Color(204, 204, 204));
        lbTroGiup.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbTroGiup.setForeground(new java.awt.Color(204, 204, 204));
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
        pnMenu.add(lbTroGiup, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 712, -1, 28));

        lbLienHe.setBackground(new java.awt.Color(204, 204, 204));
        lbLienHe.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbLienHe.setForeground(new java.awt.Color(204, 204, 204));
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
        pnMenu.add(lbLienHe, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 758, -1, 28));

        lbGiayPhep.setBackground(new java.awt.Color(204, 204, 204));
        lbGiayPhep.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbGiayPhep.setForeground(new java.awt.Color(204, 204, 204));
        lbGiayPhep.setText("Giấy phép");
        lbGiayPhep.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbGiayPhepMouseMoved(evt);
            }
        });
        lbGiayPhep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbGiayPhepMouseExited(evt);
            }
        });
        pnMenu.add(lbGiayPhep, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 804, -1, 28));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/logo.png"))); // NOI18N
        jLabel2.setMaximumSize(new java.awt.Dimension(1111, 1111));
        pnMenu.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 0, -1, 109));
        pnMenu.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 632, 287, 10));
        pnMenu.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 839, 287, 10));

        btnDatTruocSan.setBackground(new java.awt.Color(220, 220, 230));
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

        lbThongTinDatTruoc.setBackground(new java.awt.Color(204, 204, 204));
        lbThongTinDatTruoc.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbThongTinDatTruoc.setForeground(new java.awt.Color(51, 51, 51));
        lbThongTinDatTruoc.setText("ĐẶT TRƯỚC SÂN");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-reserve-32.png"))); // NOI18N

        javax.swing.GroupLayout btnDatTruocSanLayout = new javax.swing.GroupLayout(btnDatTruocSan);
        btnDatTruocSan.setLayout(btnDatTruocSanLayout);
        btnDatTruocSanLayout.setHorizontalGroup(
            btnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDatTruocSanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbThongTinDatTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );
        btnDatTruocSanLayout.setVerticalGroup(
            btnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDatTruocSanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbThongTinDatTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnMenu.add(btnDatTruocSan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 280, 70));

        btnSoDoSanBong.setBackground(new java.awt.Color(220, 220, 230));
        btnSoDoSanBong.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnSoDoSanBongMouseMoved(evt);
            }
        });
        btnSoDoSanBong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSoDoSanBongMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSoDoSanBongMouseExited(evt);
            }
        });

        lbQuanLiSanBong.setBackground(new java.awt.Color(204, 204, 204));
        lbQuanLiSanBong.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbQuanLiSanBong.setForeground(new java.awt.Color(51, 51, 51));
        lbQuanLiSanBong.setText("SƠ ĐỒ SÂN BÓNG");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-stadium-30.png"))); // NOI18N

        javax.swing.GroupLayout btnSoDoSanBongLayout = new javax.swing.GroupLayout(btnSoDoSanBong);
        btnSoDoSanBong.setLayout(btnSoDoSanBongLayout);
        btnSoDoSanBongLayout.setHorizontalGroup(
            btnSoDoSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnSoDoSanBongLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbQuanLiSanBong)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        btnSoDoSanBongLayout.setVerticalGroup(
            btnSoDoSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSoDoSanBongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnSoDoSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(lbQuanLiSanBong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnMenu.add(btnSoDoSanBong, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 280, 70));

        pnlTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        pnlTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlTaiKhoanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlTaiKhoanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlTaiKhoanMouseExited(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-account-100.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("CHỨC VỤ: Nhân viên");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        lblHoTenNv_Menu.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        lblHoTenNv_Menu.setForeground(new java.awt.Color(0, 0, 102));
        lblHoTenNv_Menu.setText("Họ tên");
        lblHoTenNv_Menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHoTenNv_MenuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHoTenNv_MenuMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlTaiKhoanLayout = new javax.swing.GroupLayout(pnlTaiKhoan);
        pnlTaiKhoan.setLayout(pnlTaiKhoanLayout);
        pnlTaiKhoanLayout.setHorizontalGroup(
            pnlTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTaiKhoanLayout.createSequentialGroup()
                .addGroup(pnlTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTaiKhoanLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(7, 7, 7)
                        .addComponent(lblHoTenNv_Menu))
                    .addGroup(pnlTaiKhoanLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)))
                .addGap(0, 107, Short.MAX_VALUE))
        );
        pnlTaiKhoanLayout.setVerticalGroup(
            pnlTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTaiKhoanLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(pnlTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(pnlTaiKhoanLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(lblHoTenNv_Menu))
                    .addGroup(pnlTaiKhoanLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnMenu.add(pnlTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 310, 140));

        jPanel8.setBackground(new java.awt.Color(220, 220, 230));
        jPanel8.setPreferredSize(new java.awt.Dimension(224, 70));

        lblNgayHomNay.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lblNgayHomNay.setForeground(new java.awt.Color(51, 51, 51));
        lblNgayHomNay.setText("dd/MM/yyyy");

        lblGioHienTai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblGioHienTai.setForeground(new java.awt.Color(51, 51, 51));
        lblGioHienTai.setText("HH:mm:ss a");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/clock - icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNgayHomNay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 68, Short.MAX_VALUE)
                        .addComponent(lblGioHienTai, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(lblNgayHomNay)
                                .addGap(2, 2, 2)
                                .addComponent(lblGioHienTai, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5))))
        );

        pnMenu.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 280, 70));

        btnDangXuat.setBackground(new java.awt.Color(255, 51, 0));
        btnDangXuat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDangXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/logout - icon.png"))); // NOI18N
        btnDangXuat.setText(" ĐĂNG XUẤT");
        btnDangXuat.setMaximumSize(new java.awt.Dimension(79, 24));
        btnDangXuat.setMinimumSize(new java.awt.Dimension(79, 24));
        btnDangXuat.setPreferredSize(new java.awt.Dimension(158, 48));
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        pnMenu.add(btnDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 850, 290, 40));

        pnTong.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-information-50.png"))); // NOI18N
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
                .addContainerGap(843, Short.MAX_VALUE))
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

        pnThongTinCaNhan.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 140));

        pnTong_TTCN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnSuaThongTin_TTCN.setBackground(new java.awt.Color(254, 235, 208));
        pnSuaThongTin_TTCN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 0, 153)));
        pnSuaThongTin_TTCN.setPreferredSize(new java.awt.Dimension(790, 350));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 102));
        jLabel30.setText("HỌ TÊN:");
        jLabel30.setToolTipText("");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 102));
        jLabel32.setText("SDT:");
        jLabel32.setToolTipText("");

        btnXacNhanSua.setBackground(new java.awt.Color(28, 184, 160));
        btnXacNhanSua.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnXacNhanSua.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanSua.setText("Xác nhận sửa");
        btnXacNhanSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXacNhanSuaMouseClicked(evt);
            }
        });

        txtHoTenUpdate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtHoTenUpdate.setPreferredSize(new java.awt.Dimension(262, 35));
        txtHoTenUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtHoTenUpdateMouseClicked(evt);
            }
        });

        txtSdtUpdate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSdtUpdate.setPreferredSize(new java.awt.Dimension(262, 35));
        txtSdtUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSdtUpdateMouseClicked(evt);
            }
        });

        lblWarnHoTen.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblWarnHoTen.setForeground(new java.awt.Color(255, 0, 0));
        lblWarnHoTen.setText("Họ tên trống");
        lblWarnHoTen.setVisible(false);

        lblWarnSdt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblWarnSdt.setForeground(new java.awt.Color(255, 51, 51));
        lblWarnSdt.setText("SDT phải là 10 hoặc 11 số");
        lblWarnSdt.setVisible(false);

        javax.swing.GroupLayout pnSuaThongTin_TTCNLayout = new javax.swing.GroupLayout(pnSuaThongTin_TTCN);
        pnSuaThongTin_TTCN.setLayout(pnSuaThongTin_TTCNLayout);
        pnSuaThongTin_TTCNLayout.setHorizontalGroup(
            pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSuaThongTin_TTCNLayout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addGroup(pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblWarnHoTen)
                    .addComponent(txtSdtUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblWarnSdt)
                    .addComponent(txtHoTenUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(236, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSuaThongTin_TTCNLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhanSua, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(290, 290, 290))
        );
        pnSuaThongTin_TTCNLayout.setVerticalGroup(
            pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSuaThongTin_TTCNLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtHoTenUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnHoTen)
                .addGap(27, 27, 27)
                .addGroup(pnSuaThongTin_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtSdtUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnSdt)
                .addGap(49, 49, 49)
                .addComponent(btnXacNhanSua, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        pnTong_TTCN.add(pnSuaThongTin_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnDoiMatKhau_TTCN.setBackground(new java.awt.Color(254, 235, 208));
        pnDoiMatKhau_TTCN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
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

        txtMkCu.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txtMkCu.setPreferredSize(new java.awt.Dimension(300, 35));
        txtMkCu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMkCuMouseClicked(evt);
            }
        });

        lblWarnSaiMk.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblWarnSaiMk.setForeground(new java.awt.Color(255, 0, 0));
        lblWarnSaiMk.setText("Sai mật khẩu");
        lblWarnSaiMk.setVisible(false);

        txtMkMoi.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txtMkMoi.setPreferredSize(new java.awt.Dimension(300, 35));

        txtMkXacNhan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txtMkXacNhan.setPreferredSize(new java.awt.Dimension(300, 35));
        txtMkXacNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMkXacNhanMouseClicked(evt);
            }
        });

        lblWarnMkKhongTrung.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblWarnMkKhongTrung.setForeground(new java.awt.Color(255, 0, 0));
        lblWarnMkKhongTrung.setText("Không trùng khớp vơi mật khẩu mới");
        lblWarnMkKhongTrung.setVisible(false);

        btnXacNhanDoiMk.setBackground(new java.awt.Color(28, 184, 160));
        btnXacNhanDoiMk.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnXacNhanDoiMk.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanDoiMk.setText("Thay đổi");
        btnXacNhanDoiMk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXacNhanDoiMkMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnDoiMatKhau_TTCNLayout = new javax.swing.GroupLayout(pnDoiMatKhau_TTCN);
        pnDoiMatKhau_TTCN.setLayout(pnDoiMatKhau_TTCNLayout);
        pnDoiMatKhau_TTCNLayout.setHorizontalGroup(
            pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDoiMatKhau_TTCNLayout.createSequentialGroup()
                .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDoiMatKhau_TTCNLayout.createSequentialGroup()
                        .addGap(296, 296, 296)
                        .addComponent(btnXacNhanDoiMk, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnDoiMatKhau_TTCNLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnDoiMatKhau_TTCNLayout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addComponent(lblWarnMkKhongTrung))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDoiMatKhau_TTCNLayout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMkXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnDoiMatKhau_TTCNLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(24, 24, 24)
                                .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblWarnSaiMk, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMkCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDoiMatKhau_TTCNLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMkMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(234, Short.MAX_VALUE))
        );
        pnDoiMatKhau_TTCNLayout.setVerticalGroup(
            pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDoiMatKhau_TTCNLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtMkCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnSaiMk)
                .addGap(18, 18, 18)
                .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtMkMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(pnDoiMatKhau_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMkXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnMkKhongTrung)
                .addGap(33, 33, 33)
                .addComponent(btnXacNhanDoiMk, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnTong_TTCN.add(pnDoiMatKhau_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnCTTTCN_TTCN.setBackground(new java.awt.Color(255, 255, 255));
        pnCTTTCN_TTCN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255), 2));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setText("HỌ TÊN:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 102));
        jLabel25.setText("CMND:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 102));
        jLabel26.setText("SDT:");

        lblHotenNV.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblHotenNV.setForeground(new java.awt.Color(0, 0, 102));
        lblHotenNV.setText("HỌ TÊN");

        lblCmndNV.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblCmndNV.setForeground(new java.awt.Color(0, 0, 102));
        lblCmndNV.setText("CMND");

        lblSdtNV.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblSdtNV.setForeground(new java.awt.Color(0, 0, 102));
        lblSdtNV.setText("SDT");

        lblMaNv_TTCN.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblMaNv_TTCN.setForeground(new java.awt.Color(0, 0, 102));
        lblMaNv_TTCN.setText("HỌ TÊN");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 102));
        jLabel31.setText("Mã NV:");

        javax.swing.GroupLayout pnCTTTCN_TTCNLayout = new javax.swing.GroupLayout(pnCTTTCN_TTCN);
        pnCTTTCN_TTCN.setLayout(pnCTTTCN_TTCNLayout);
        pnCTTTCN_TTCNLayout.setHorizontalGroup(
            pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCTTTCN_TTCNLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnCTTTCN_TTCNLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(28, 28, 28)
                        .addComponent(lblMaNv_TTCN))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnCTTTCN_TTCNLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(lblHotenNV))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnCTTTCN_TTCNLayout.createSequentialGroup()
                        .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addGap(31, 31, 31)
                        .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCmndNV)
                            .addComponent(lblSdtNV))))
                .addGap(533, 533, 533))
        );
        pnCTTTCN_TTCNLayout.setVerticalGroup(
            pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCTTTCN_TTCNLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(lblMaNv_TTCN))
                .addGap(36, 36, 36)
                .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lblHotenNV))
                .addGap(38, 38, 38)
                .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lblCmndNV))
                .addGap(39, 39, 39)
                .addGroup(pnCTTTCN_TTCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lblSdtNV))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        pnTong_TTCN.add(pnCTTTCN_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, -1));

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
        pnThongTinCaNhan.add(btnSuaThongTin_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 650, 200, 40));

        btnDoiMatKhau_TTCN.setBackground(new java.awt.Color(28, 184, 160));
        btnDoiMatKhau_TTCN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDoiMatKhau_TTCN.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiMatKhau_TTCN.setText("ĐỔI MẬT KHẨU");
        btnDoiMatKhau_TTCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhau_TTCNActionPerformed(evt);
            }
        });
        pnThongTinCaNhan.add(btnDoiMatKhau_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(581, 650, 200, 40));

        pnTong.add(pnThongTinCaNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, -1));

        pnSoDoSanBong.setBackground(new java.awt.Color(254, 235, 208));
        pnSoDoSanBong.setForeground(new java.awt.Color(0, 0, 102));
        pnSoDoSanBong.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnSoDoSanBong.setPreferredSize(new java.awt.Dimension(1180, 900));
        pnSoDoSanBong.setLayout(null);

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setForeground(new java.awt.Color(0, 102, 102));
        jPanel40.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel109.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(0, 0, 102));
        jLabel109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-stadium-48 (1).png"))); // NOI18N
        jLabel109.setText("SƠ ĐỒ SÂN BÓNG");

        jLabel110.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(0, 0, 102));
        jLabel110.setText("Sơ Đồ Sân Bóng");

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel110))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel109))))
                .addContainerGap(871, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel109)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel110)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnSoDoSanBong.add(jPanel40);
        jPanel40.setBounds(0, 0, 1180, 110);

        pnl5B.setBackground(new java.awt.Color(255, 127, 129));

        lblTt5B.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTt5B.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTt5B.setText("jLabel26");

        javax.swing.GroupLayout pnl5BLayout = new javax.swing.GroupLayout(pnl5B);
        pnl5B.setLayout(pnl5BLayout);
        pnl5BLayout.setHorizontalGroup(
            pnl5BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl5BLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTt5B, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl5BLayout.setVerticalGroup(
            pnl5BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl5BLayout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(lblTt5B)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        pnSoDoSanBong.add(pnl5B);
        pnl5B.setBounds(400, 160, 210, 280);

        pnl5A.setBackground(new java.awt.Color(153, 255, 153));

        lblTt5A.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTt5A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTt5A.setText("jLabel21");

        javax.swing.GroupLayout pnl5ALayout = new javax.swing.GroupLayout(pnl5A);
        pnl5A.setLayout(pnl5ALayout);
        pnl5ALayout.setHorizontalGroup(
            pnl5ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl5ALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTt5A, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl5ALayout.setVerticalGroup(
            pnl5ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl5ALayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(lblTt5A)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        pnSoDoSanBong.add(pnl5A);
        pnl5A.setBounds(160, 160, 230, 280);

        lblTt11A.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTt11A.setForeground(new java.awt.Color(255, 51, 51));
        lblTt11A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTt11A.setText("ĐANG SỬ DỤNG");

        javax.swing.GroupLayout pnl11ALayout = new javax.swing.GroupLayout(pnl11A);
        pnl11A.setLayout(pnl11ALayout);
        pnl11ALayout.setHorizontalGroup(
            pnl11ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl11ALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTt11A, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl11ALayout.setVerticalGroup(
            pnl11ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl11ALayout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(lblTt11A)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        pnSoDoSanBong.add(pnl11A);
        pnl11A.setBounds(170, 500, 450, 300);

        lblTt5C.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTt5C.setForeground(new java.awt.Color(0, 188, 37));
        lblTt5C.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTt5C.setText("TRỐNG");

        javax.swing.GroupLayout pnl5CLayout = new javax.swing.GroupLayout(pnl5C);
        pnl5C.setLayout(pnl5CLayout);
        pnl5CLayout.setHorizontalGroup(
            pnl5CLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl5CLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTt5C, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl5CLayout.setVerticalGroup(
            pnl5CLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl5CLayout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(lblTt5C)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        pnSoDoSanBong.add(pnl5C);
        pnl5C.setBounds(630, 500, 230, 300);

        lblTt7B.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTt7B.setForeground(new java.awt.Color(241, 208, 16));
        lblTt7B.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTt7B.setText("BẢO TRÌ");

        javax.swing.GroupLayout pnl7BLayout = new javax.swing.GroupLayout(pnl7B);
        pnl7B.setLayout(pnl7BLayout);
        pnl7BLayout.setHorizontalGroup(
            pnl7BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl7BLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTt7B, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl7BLayout.setVerticalGroup(
            pnl7BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl7BLayout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(lblTt7B)
                .addContainerGap(187, Short.MAX_VALUE))
        );

        pnSoDoSanBong.add(pnl7B);
        pnl7B.setBounds(870, 500, 250, 350);

        pnl7A.setBackground(new java.awt.Color(252, 227, 25));

        lblTt7A.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTt7A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTt7A.setText("jLabel27");

        javax.swing.GroupLayout pnl7ALayout = new javax.swing.GroupLayout(pnl7A);
        pnl7A.setLayout(pnl7ALayout);
        pnl7ALayout.setHorizontalGroup(
            pnl7ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl7ALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTt7A, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl7ALayout.setVerticalGroup(
            pnl7ALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl7ALayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(lblTt7A)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        pnSoDoSanBong.add(pnl7A);
        pnl7A.setBounds(620, 200, 330, 240);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/SƠ ĐỒ SÂN BÓNG.png"))); // NOI18N
        pnSoDoSanBong.add(jLabel1);
        jLabel1.setBounds(20, 130, 1140, 750);

        pnTong.add(pnSoDoSanBong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

        pnDatTruocSan.setBackground(new java.awt.Color(254, 235, 208));
        pnDatTruocSan.setForeground(new java.awt.Color(0, 0, 102));
        pnDatTruocSan.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnDatTruocSan.setPreferredSize(new java.awt.Dimension(1180, 900));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setForeground(new java.awt.Color(0, 102, 102));
        jPanel33.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 0, 102));
        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-reserve-48 (1).png"))); // NOI18N
        jLabel81.setText("ĐẶT TRƯỚC SÂN");

        lblDatSanNgay_DSN1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDatSanNgay_DSN1.setForeground(new java.awt.Color(0, 0, 102));
        lblDatSanNgay_DSN1.setText("Đặt trước sân");

        lblThongTinDatTruoc_DTS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblThongTinDatTruoc_DTS.setForeground(new java.awt.Color(204, 204, 204));
        lblThongTinDatTruoc_DTS.setText("Thông tin đặt trước");
        lblThongTinDatTruoc_DTS.setPreferredSize(new java.awt.Dimension(89, 28));
        lblThongTinDatTruoc_DTS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThongTinDatTruoc_DTSMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblThongTinDatTruoc_DTSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblThongTinDatTruoc_DTSMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(lblDatSanNgay_DSN1)
                        .addGap(47, 47, 47)
                        .addComponent(lblThongTinDatTruoc_DTS, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatSanNgay_DSN1)
                    .addComponent(lblThongTinDatTruoc_DTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel51.setBackground(new java.awt.Color(0, 0, 0));
        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel51.setText("HỌ TÊN:");

        jLabel52.setBackground(new java.awt.Color(0, 0, 0));
        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel52.setText("SĐT:");

        jLabel45.setBackground(new java.awt.Color(0, 0, 0));
        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel45.setText("NGÀY:");

        jLabel53.setBackground(new java.awt.Color(0, 0, 0));
        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel53.setText("CMND:");

        jLabel46.setBackground(new java.awt.Color(0, 0, 0));
        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel46.setText("GIỜ HẸN ĐẾN :");

        txtHoTen_DatTruoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtHoTen_DatTruoc.setPreferredSize(new java.awt.Dimension(235, 35));
        txtHoTen_DatTruoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtHoTen_DatTruocMouseClicked(evt);
            }
        });

        jLabel47.setBackground(new java.awt.Color(0, 0, 0));
        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel47.setText("GIỜ HẸN TRẢ:");

        jPanel9.setBackground(new java.awt.Color(200, 232, 249));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(37, 102, 142));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("CHI TIẾT ĐẶT TRƯỚC");

        tblCTDatTruoc.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tblCTDatTruoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN SÂN", "LOẠI SÂN", "KHUNG GIỜ", "GIỜ NHẬN ", "GIỜ TRẢ", "GIÁ THEO KG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCTDatTruoc.setRowHeight(35);
        tblCTDatTruoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblCTDatTruoc.getTableHeader().setReorderingAllowed(false);
        jScrollPane10.setViewportView(tblCTDatTruoc);
        if (tblCTDatTruoc.getColumnModel().getColumnCount() > 0) {
            tblCTDatTruoc.getColumnModel().getColumn(0).setResizable(false);
            tblCTDatTruoc.getColumnModel().getColumn(0).setPreferredWidth(15);
            tblCTDatTruoc.getColumnModel().getColumn(1).setResizable(false);
            tblCTDatTruoc.getColumnModel().getColumn(1).setPreferredWidth(15);
            tblCTDatTruoc.getColumnModel().getColumn(2).setResizable(false);
            tblCTDatTruoc.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblCTDatTruoc.getColumnModel().getColumn(3).setResizable(false);
            tblCTDatTruoc.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblCTDatTruoc.getColumnModel().getColumn(4).setResizable(false);
            tblCTDatTruoc.getColumnModel().getColumn(4).setPreferredWidth(30);
            tblCTDatTruoc.getColumnModel().getColumn(5).setResizable(false);
            tblCTDatTruoc.getColumnModel().getColumn(5).setPreferredWidth(60);
        }

        bttBoCtDatTruoc.setFont(new java.awt.Font("Tw Cen MT", 1, 20)); // NOI18N
        bttBoCtDatTruoc.setForeground(new java.awt.Color(255, 51, 0));
        bttBoCtDatTruoc.setText("X");
        bttBoCtDatTruoc.setPreferredSize(new java.awt.Dimension(45, 29));
        bttBoCtDatTruoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttBoCtDatTruocMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bttBoCtDatTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(bttBoCtDatTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtSDT_DatTruoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSDT_DatTruoc.setPreferredSize(new java.awt.Dimension(235, 35));
        txtSDT_DatTruoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSDT_DatTruocMouseClicked(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(179, 193, 135));

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 102, 0));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("SÂN KHẢ DỤNG");

        btnThemSan_DatSan.setBackground(new java.awt.Color(153, 255, 153));
        btnThemSan_DatSan.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnThemSan_DatSan.setForeground(new java.awt.Color(0, 153, 51));
        btnThemSan_DatSan.setText("+");
        btnThemSan_DatSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemSan_DatSanMouseClicked(evt);
            }
        });

        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        tblSan5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tblSan5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN SÂN", "ĐƠN GIÁ "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSan5.setGridColor(new java.awt.Color(107, 208, 219));
        tblSan5.setRowHeight(35);
        tblSan5.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblSan5.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(tblSan5);
        if (tblSan5.getColumnModel().getColumnCount() > 0) {
            tblSan5.getColumnModel().getColumn(0).setResizable(false);
            tblSan5.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblSan5.getColumnModel().getColumn(1).setResizable(false);
        }

        jTabbedPane2.addTab("Sân 5", jScrollPane9);

        tblSan7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tblSan7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN SÂN", "ĐƠN GIÁ "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSan7.setGridColor(new java.awt.Color(107, 208, 219));
        tblSan7.setRowHeight(35);
        tblSan7.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblSan7.getTableHeader().setReorderingAllowed(false);
        jScrollPane17.setViewportView(tblSan7);
        if (tblSan7.getColumnModel().getColumnCount() > 0) {
            tblSan7.getColumnModel().getColumn(0).setResizable(false);
            tblSan7.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblSan7.getColumnModel().getColumn(1).setResizable(false);
        }

        jTabbedPane2.addTab("Sân 7", jScrollPane17);

        tblSan11.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tblSan11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN SÂN", "ĐƠN GIÁ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSan11.setGridColor(new java.awt.Color(107, 208, 219));
        tblSan11.setRowHeight(35);
        tblSan11.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblSan11.getTableHeader().setReorderingAllowed(false);
        jScrollPane21.setViewportView(tblSan11);
        if (tblSan11.getColumnModel().getColumnCount() > 0) {
            tblSan11.getColumnModel().getColumn(0).setResizable(false);
            tblSan11.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblSan11.getColumnModel().getColumn(1).setResizable(false);
        }

        jTabbedPane2.addTab("Sân 11", jScrollPane21);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThemSan_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemSan_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addContainerGap())
        );

        dcNgayDatTruoc.setDate(new Date());
        dcNgayDatTruoc.setDateFormatString("dd/MM/yyyy");
        dcNgayDatTruoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dcNgayDatTruoc.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcNgayDatTruocPropertyChange(evt);
            }
        });

        cbxCMND_DatTruoc.setEditable(true);
        cbxCMND_DatTruoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxCMND_DatTruoc.setMinimumSize(new java.awt.Dimension(235, 35));
        cbxCMND_DatTruoc.setPreferredSize(new java.awt.Dimension(235, 28));
        cbxCMND_DatTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCMND_DatTruocActionPerformed(evt);
            }
        });
        cbxCMND_DatTruoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbxCMND_DatTruocKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbxCMND_DatTruocKeyTyped(evt);
            }
        });

        JSpinner.DateEditor de1 = new JSpinner.DateEditor(spnGioHenDen_DatSan, "HH:00 a");

        spnGioHenDen_DatSan.setEditor(de1);
        spnGioHenDen_DatSan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        spnGioHenDen_DatSan.setPreferredSize(new java.awt.Dimension(150, 35));
        ((DefaultEditor) spnGioHenDen_DatSan.getEditor()).getTextField().setEditable(false);
        spnGioHenDen_DatSan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnGioHenDen_DatSanStateChanged(evt);
            }
        });

        JSpinner.DateEditor de2 = new JSpinner.DateEditor(spnGioHenTra_DatSan, "HH:00 a");
        spnGioHenTra_DatSan.setEditor(de2);
        spnGioHenTra_DatSan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        spnGioHenTra_DatSan.setPreferredSize(new java.awt.Dimension(150, 35));
        ((DefaultEditor) spnGioHenTra_DatSan.getEditor()).getTextField().setEditable(false);
        spnGioHenTra_DatSan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnGioHenTra_DatSanStateChanged(evt);
            }
        });

        btnXacNhanDat_DatTruoc.setBackground(new java.awt.Color(28, 184, 160));
        btnXacNhanDat_DatTruoc.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnXacNhanDat_DatTruoc.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanDat_DatTruoc.setText("XÁC NHẬN ĐẶT");
        btnXacNhanDat_DatTruoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXacNhanDat_DatTruocMouseClicked(evt);
            }
        });

        lblWarnHoTen_DatTruoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblWarnHoTen_DatTruoc.setForeground(new java.awt.Color(255, 51, 51));
        lblWarnHoTen_DatTruoc.setText("Họ tên không được trống");
        lblWarnHoTen_DatTruoc.setVisible(false);

        lblWarnCMND_DatTruoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblWarnCMND_DatTruoc.setForeground(new java.awt.Color(255, 51, 51));
        lblWarnCMND_DatTruoc.setText("CMND phải là dãy 9 hoặc 12 số ");
        lblWarnCMND_DatTruoc.setVisible(false);

        lblWarnSDT_DatTruoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblWarnSDT_DatTruoc.setForeground(new java.awt.Color(255, 51, 51));
        lblWarnSDT_DatTruoc.setText("SDT phải là dãy 10 hoặc 11 số");
        lblWarnSDT_DatTruoc.setVisible(false);

        javax.swing.GroupLayout pnDatTruocSanLayout = new javax.swing.GroupLayout(pnDatTruocSan);
        pnDatTruocSan.setLayout(pnDatTruocSanLayout);
        pnDatTruocSanLayout.setHorizontalGroup(
            pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel51)
                                            .addComponent(jLabel45))
                                        .addGap(48, 48, 48)
                                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtHoTen_DatTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dcNgayDatTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblWarnHoTen_DatTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblWarnCMND_DatTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                        .addComponent(jLabel53)
                                        .addGap(60, 60, 60)
                                        .addComponent(cbxCMND_DatTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(136, 136, 136)
                                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                                .addComponent(jLabel52)
                                                .addGap(106, 106, 106))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDatTruocSanLayout.createSequentialGroup()
                                                .addComponent(jLabel46)
                                                .addGap(18, 18, 18)))
                                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(spnGioHenDen_DatSan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSDT_DatTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblWarnSDT_DatTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                        .addComponent(jLabel47)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(spnGioHenTra_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(85, 85, 85))))
                            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                                .addGap(466, 466, 466)
                                .addComponent(btnXacNhanDat_DatTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 232, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnDatTruocSanLayout.setVerticalGroup(
            pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSDT_DatTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblWarnSDT_DatTruoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spnGioHenDen_DatSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46)))
                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxCMND_DatTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel53))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblWarnCMND_DatTruoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHoTen_DatTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnHoTen_DatTruoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcNgayDatTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnDatTruocSanLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel47)
                        .addComponent(spnGioHenTra_DatSan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(pnDatTruocSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXacNhanDat_DatTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        pnTong.add(pnDatTruocSan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

        pnDatSanNgay.setBackground(new java.awt.Color(254, 235, 208));
        pnDatSanNgay.setForeground(new java.awt.Color(0, 0, 102));
        pnDatSanNgay.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnDatSanNgay.setPreferredSize(new java.awt.Dimension(1180, 900));

        cbxCMND_ThueNgay.setEditable(true);
        cbxCMND_ThueNgay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxCMND_ThueNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCMND_ThueNgayActionPerformed(evt);
            }
        });
        cbxCMND_ThueNgay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbxCMND_ThueNgayKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbxCMND_ThueNgayKeyTyped(evt);
            }
        });

        txtSdt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtHoTen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setForeground(new java.awt.Color(0, 102, 102));
        jPanel35.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(0, 0, 102));
        jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-list-50.png"))); // NOI18N
        jLabel84.setText("QUẢN LÍ PHIẾU THUÊ");

        lblDatSanNgay_DSN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDatSanNgay_DSN.setForeground(new java.awt.Color(0, 0, 102));
        lblDatSanNgay_DSN.setText("Đặt Sân Ngay");

        lbThongTinPhieuThue_DSN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinPhieuThue_DSN.setForeground(new java.awt.Color(204, 204, 204));
        lbThongTinPhieuThue_DSN.setText("Quản lý sân thuê");
        lbThongTinPhieuThue_DSN.setPreferredSize(new java.awt.Dimension(151, 28));
        lbThongTinPhieuThue_DSN.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbThongTinPhieuThue_DSNMouseMoved(evt);
            }
        });
        lbThongTinPhieuThue_DSN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongTinPhieuThue_DSNMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThongTinPhieuThue_DSNMouseExited(evt);
            }
        });

        lblLichSuThue_DSN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLichSuThue_DSN.setForeground(new java.awt.Color(204, 204, 204));
        lblLichSuThue_DSN.setText("Lịch Sử Thuê");
        lblLichSuThue_DSN.setPreferredSize(new java.awt.Dimension(89, 28));
        lblLichSuThue_DSN.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblLichSuThue_DSNMouseMoved(evt);
            }
        });
        lblLichSuThue_DSN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLichSuThue_DSNMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLichSuThue_DSNMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel35Layout.createSequentialGroup()
                            .addGap(36, 36, 36)
                            .addComponent(jLabel84))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lbThongTinPhieuThue_DSN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDatSanNgay_DSN, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(lblLichSuThue_DSN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel84)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDatSanNgay_DSN)
                            .addComponent(lblLichSuThue_DSN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbThongTinPhieuThue_DSN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel67.setBackground(new java.awt.Color(0, 0, 0));
        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel67.setText("SDT");

        jLabel68.setBackground(new java.awt.Color(0, 0, 0));
        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel68.setText("CMND:");

        jLabel70.setBackground(new java.awt.Color(0, 0, 0));
        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel70.setText("GIỜ HẸN TRẢ:");

        jPanel15.setBackground(new java.awt.Color(244, 244, 244));
        jPanel15.setForeground(new java.awt.Color(255, 230, 137));

        tblSanKhaDung_ThueNgay.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblSanKhaDung_ThueNgay.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSanKhaDung_ThueNgay.setGridColor(new java.awt.Color(107, 208, 219));
        tblSanKhaDung_ThueNgay.setRowHeight(28);
        tblSanKhaDung_ThueNgay.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane13.setViewportView(tblSanKhaDung_ThueNgay);

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 102));
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("SÂN KHẢ DỤNG");

        btnThem.setBackground(new java.awt.Color(153, 255, 153));
        btnThem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnThem.setForeground(new java.awt.Color(51, 153, 0));
        btnThem.setText("+");
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });

        cbxChonLoaiSan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxChonLoaiSanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(cbxChonLoaiSan, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnThem))
                    .addComponent(jScrollPane13))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem)
                    .addComponent(cbxChonLoaiSan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(255, 230, 137));

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 0, 102));
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setText("DANH SÁCH SÂN");

        tblCtPhieuThueNgay.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tblCtPhieuThueNgay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sân", "Loại sân ", "Đơn giá", "Ngày thuê", "Giờ thuê", "Khung giờ", "Giờ hẹn trả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCtPhieuThueNgay.setFocusable(false);
        tblCtPhieuThueNgay.setGridColor(new java.awt.Color(255, 255, 255));
        tblCtPhieuThueNgay.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblCtPhieuThueNgay.setRowHeight(35);
        tblCtPhieuThueNgay.setSelectionBackground(new java.awt.Color(153, 153, 153));
        tblCtPhieuThueNgay.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCtPhieuThueNgay.getTableHeader().setReorderingAllowed(false);
        tblCtPhieuThueNgay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCtPhieuThueNgayMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblCtPhieuThueNgay);

        btnXacNhan.setBackground(new java.awt.Color(0, 204, 102));
        btnXacNhan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("XÁC NHẬN ĐẶT");
        btnXacNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXacNhanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1132, Short.MAX_VALUE)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(473, 473, 473))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
        );

        JSpinner.DateEditor de = new JSpinner.DateEditor(spnGioHenTra, "HH:mm a");
        spnGioHenTra.setEditor(de);
        spnGioHenTra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        spnGioHenTra.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnGioHenTraStateChanged(evt);
            }
        });

        jLabel69.setBackground(new java.awt.Color(0, 0, 0));
        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel69.setText("HỌ TÊN:");

        javax.swing.GroupLayout pnDatSanNgayLayout = new javax.swing.GroupLayout(pnDatSanNgay);
        pnDatSanNgay.setLayout(pnDatSanNgayLayout);
        pnDatSanNgayLayout.setHorizontalGroup(
            pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(pnDatSanNgayLayout.createSequentialGroup()
                .addGroup(pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnDatSanNgayLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnDatSanNgayLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnDatSanNgayLayout.createSequentialGroup()
                                .addComponent(jLabel67)
                                .addGap(18, 18, 18)
                                .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnDatSanNgayLayout.createSequentialGroup()
                                .addGroup(pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel70)
                                    .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel69, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGroup(pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnDatSanNgayLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(spnGioHenTra, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnDatSanNgayLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cbxCMND_ThueNgay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnDatSanNgayLayout.setVerticalGroup(
            pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDatSanNgayLayout.createSequentialGroup()
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnDatSanNgayLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxCMND_ThueNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel69))
                        .addGap(18, 18, 18)
                        .addGroup(pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel67))
                        .addGap(13, 13, 13)
                        .addGroup(pnDatSanNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spnGioHenTra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel70))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnTong.add(pnDatSanNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnQuanLyPhieuThue.setBackground(new java.awt.Color(254, 235, 208));
        pnQuanLyPhieuThue.setForeground(new java.awt.Color(0, 0, 102));
        pnQuanLyPhieuThue.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnQuanLyPhieuThue.setPreferredSize(new java.awt.Dimension(1180, 900));

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setForeground(new java.awt.Color(0, 102, 102));
        jPanel37.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 0, 102));
        jLabel85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-list-50.png"))); // NOI18N
        jLabel85.setText("QUẢN LÍ PHIẾU THUÊ");

        lbDatSanNgay_TTPT.setBackground(new java.awt.Color(204, 204, 204));
        lbDatSanNgay_TTPT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbDatSanNgay_TTPT.setForeground(new java.awt.Color(204, 204, 204));
        lbDatSanNgay_TTPT.setText("Đặt Sân Ngay");
        lbDatSanNgay_TTPT.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbDatSanNgay_TTPTMouseMoved(evt);
            }
        });
        lbDatSanNgay_TTPT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDatSanNgay_TTPTMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbDatSanNgay_TTPTMouseExited(evt);
            }
        });

        lbThongTinPhieuThue_QLPT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinPhieuThue_QLPT.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinPhieuThue_QLPT.setText("Quản lý sân thuê");
        lbThongTinPhieuThue_QLPT.setPreferredSize(new java.awt.Dimension(151, 28));

        lblLichSuThue_TTPT.setBackground(new java.awt.Color(204, 204, 204));
        lblLichSuThue_TTPT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLichSuThue_TTPT.setForeground(new java.awt.Color(204, 204, 204));
        lblLichSuThue_TTPT.setText("Lịch Sử Thuê");
        lblLichSuThue_TTPT.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblLichSuThue_TTPTMouseMoved(evt);
            }
        });
        lblLichSuThue_TTPT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLichSuThue_TTPTMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLichSuThue_TTPTMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel85))
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbThongTinPhieuThue_QLPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbDatSanNgay_TTPT, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(lblLichSuThue_TTPT))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel85)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblLichSuThue_TTPT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbDatSanNgay_TTPT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbThongTinPhieuThue_QLPT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBackground(new java.awt.Color(179, 193, 135));
        jPanel7.setPreferredSize(new java.awt.Dimension(430, 410));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 102, 0));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("                          ĐẶT TRƯỚC HÔM NAY");

        tblPhieuDatTruoc.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tblPhieuDatTruoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ma Phiếu ĐT", "Sân đặt", "Khách đặt", "Giờ hẹn đến", "Giờ hẹn trả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuDatTruoc.setFocusable(false);
        tblPhieuDatTruoc.setRowHeight(35);
        tblPhieuDatTruoc.setSelectionBackground(new java.awt.Color(153, 153, 153));
        tblPhieuDatTruoc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPhieuDatTruoc.getTableHeader().setReorderingAllowed(false);
        tblPhieuDatTruoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuDatTruocMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblPhieuDatTruoc);
        if (tblPhieuDatTruoc.getColumnModel().getColumnCount() > 0) {
            tblPhieuDatTruoc.getColumnModel().getColumn(0).setResizable(false);
            tblPhieuDatTruoc.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblPhieuDatTruoc.getColumnModel().getColumn(1).setResizable(false);
            tblPhieuDatTruoc.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblPhieuDatTruoc.getColumnModel().getColumn(2).setResizable(false);
            tblPhieuDatTruoc.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblPhieuDatTruoc.getColumnModel().getColumn(3).setResizable(false);
            tblPhieuDatTruoc.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblPhieuDatTruoc.getColumnModel().getColumn(4).setResizable(false);
            tblPhieuDatTruoc.getColumnModel().getColumn(4).setPreferredWidth(30);
        }
        tblPhieuDatTruoc.getTableHeader().setFont(new Font("Cambria Math", Font.BOLD, 17));
        tblPhieuDatTruoc.getTableHeader().setOpaque(false);
        tblPhieuDatTruoc.getTableHeader().setBackground(new Color(245, 80, 21));

        btnNhanSan.setBackground(new java.awt.Color(28, 184, 160));
        btnNhanSan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnNhanSan.setForeground(new java.awt.Color(255, 255, 255));
        btnNhanSan.setText("NHẬN SÂN");
        btnNhanSan.setMaximumSize(new java.awt.Dimension(1000, 1000));
        btnNhanSan.setMinimumSize(new java.awt.Dimension(0, 0));
        btnNhanSan.setPreferredSize(new java.awt.Dimension(279, 76));
        btnNhanSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNhanSanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNhanSan, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNhanSan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(200, 232, 249));

        tblDsSanDangThue.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tblDsSanDangThue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sân", "Mã hóa đơn", "Khách thuê", "Giờ thuê", "Giờ hẹn trả", "Khung giờ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDsSanDangThue.setFocusable(false);
        tblDsSanDangThue.setGridColor(new java.awt.Color(255, 255, 255));
        tblDsSanDangThue.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblDsSanDangThue.setRowHeight(35);
        tblDsSanDangThue.setSelectionBackground(new java.awt.Color(153, 153, 153));
        tblDsSanDangThue.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDsSanDangThue.getTableHeader().setReorderingAllowed(false);
        tblDsSanDangThue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDsSanDangThueMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblDsSanDangThueMouseReleased(evt);
            }
        });
        jScrollPane20.setViewportView(tblDsSanDangThue);
        if (tblDsSanDangThue.getColumnModel().getColumnCount() > 0) {
            tblDsSanDangThue.getColumnModel().getColumn(0).setResizable(false);
            tblDsSanDangThue.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblDsSanDangThue.getColumnModel().getColumn(1).setResizable(false);
            tblDsSanDangThue.getColumnModel().getColumn(1).setPreferredWidth(15);
            tblDsSanDangThue.getColumnModel().getColumn(2).setResizable(false);
            tblDsSanDangThue.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblDsSanDangThue.getColumnModel().getColumn(3).setResizable(false);
            tblDsSanDangThue.getColumnModel().getColumn(3).setPreferredWidth(20);
            tblDsSanDangThue.getColumnModel().getColumn(4).setResizable(false);
            tblDsSanDangThue.getColumnModel().getColumn(4).setPreferredWidth(20);
            tblDsSanDangThue.getColumnModel().getColumn(5).setResizable(false);
            tblDsSanDangThue.getColumnModel().getColumn(5).setPreferredWidth(90);
        }
        tblDsSanDangThue.getTableHeader().setFont(new Font("Cambria Math", Font.BOLD, 17));
        tblDsSanDangThue.getTableHeader().setOpaque(false);
        tblDsSanDangThue.getTableHeader().setBackground(new Color(245, 80, 21));

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(37, 102, 142));
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("SÂN ĐANG THUÊ");

        btnTraSan.setBackground(new java.awt.Color(28, 184, 160));
        btnTraSan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTraSan.setForeground(new java.awt.Color(255, 255, 255));
        btnTraSan.setText("TRẢ SÂN");
        btnTraSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTraSanMouseClicked(evt);
            }
        });

        btnThemDv.setBackground(new java.awt.Color(28, 184, 160));
        btnThemDv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemDv.setForeground(new java.awt.Color(255, 255, 255));
        btnThemDv.setText("THÊM DỊCH VỤ");
        btnThemDv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDvActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 1132, Short.MAX_VALUE)
                    .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThemDv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTraSan, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemDv, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTraSan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnQuanLyPhieuThueLayout = new javax.swing.GroupLayout(pnQuanLyPhieuThue);
        pnQuanLyPhieuThue.setLayout(pnQuanLyPhieuThueLayout);
        pnQuanLyPhieuThueLayout.setHorizontalGroup(
            pnQuanLyPhieuThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(pnQuanLyPhieuThueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnQuanLyPhieuThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnQuanLyPhieuThueLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnQuanLyPhieuThueLayout.setVerticalGroup(
            pnQuanLyPhieuThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnQuanLyPhieuThueLayout.createSequentialGroup()
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnTong.add(pnQuanLyPhieuThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

        pnDatDichVu.setBackground(new java.awt.Color(254, 235, 208));
        pnDatDichVu.setPreferredSize(new java.awt.Dimension(1180, 900));

        btnXacNhanDatDv.setBackground(new java.awt.Color(0, 204, 102));
        btnXacNhanDatDv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXacNhanDatDv.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanDatDv.setText("XÁC NHẬN ĐẶT");
        btnXacNhanDatDv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXacNhanDatDvMouseClicked(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(0, 0, 102));
        jLabel77.setText("SÂN:");

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 0, 102));
        jLabel78.setText("MÃ HÓA ĐƠN:");

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 0, 102));
        jLabel79.setText("KHÁCH ĐẶT:");

        lblSanDatDv.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lblSanDatDv.setForeground(new java.awt.Color(0, 0, 102));
        lblSanDatDv.setText("SÂN MỸ ĐÌNH");

        lblMHDdatDv.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lblMHDdatDv.setForeground(new java.awt.Color(0, 0, 102));
        lblMHDdatDv.setText("TONGKAKA");

        lblTenKhachDatDv.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lblTenKhachDatDv.setForeground(new java.awt.Color(0, 0, 102));
        lblTenKhachDatDv.setText("TONGKAKA");

        bttThemDv.setBackground(new java.awt.Color(51, 255, 0));
        bttThemDv.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bttThemDv.setForeground(new java.awt.Color(51, 153, 0));
        bttThemDv.setText("+");
        bttThemDv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttThemDvActionPerformed(evt);
            }
        });

        jTabbedPane4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        tblDoUong.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblDoUong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên ", "Đơn vị", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoUong.setRowHeight(35);
        tblDoUong.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblDoUong.getTableHeader().setReorderingAllowed(false);
        jScrollPane14.setViewportView(tblDoUong);
        if (tblDoUong.getColumnModel().getColumnCount() > 0) {
            tblDoUong.getColumnModel().getColumn(0).setResizable(false);
            tblDoUong.getColumnModel().getColumn(1).setResizable(false);
            tblDoUong.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblDoUong.getColumnModel().getColumn(2).setResizable(false);
        }
        tblDoUong.getTableHeader().setFont(new Font("Cambria Math", Font.BOLD, 17));
        tblDoUong.getTableHeader().setOpaque(false);
        tblDoUong.getTableHeader().setBackground(new Color(245, 80, 21));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("ĐỒ UỐNG", jPanel22);

        tblDoAn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblDoAn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên ", "Đơn vị", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoAn.setRowHeight(35);
        tblDoAn.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblDoAn.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblDoAn);
        if (tblDoAn.getColumnModel().getColumnCount() > 0) {
            tblDoAn.getColumnModel().getColumn(0).setResizable(false);
            tblDoAn.getColumnModel().getColumn(1).setResizable(false);
            tblDoAn.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblDoAn.getColumnModel().getColumn(2).setResizable(false);
        }
        tblDoAn.getTableHeader().setFont(new Font("Cambria Math", Font.BOLD, 17));
        tblDoAn.getTableHeader().setOpaque(false);
        tblDoAn.getTableHeader().setBackground(new Color(245, 80, 21));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("ĐỒ ĂN NHANH", jPanel23);

        tblDungCu.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblDungCu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên ", "Đơn vị", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDungCu.setRowHeight(35);
        tblDungCu.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblDungCu.getTableHeader().setReorderingAllowed(false);
        jScrollPane15.setViewportView(tblDungCu);
        if (tblDungCu.getColumnModel().getColumnCount() > 0) {
            tblDungCu.getColumnModel().getColumn(0).setResizable(false);
            tblDungCu.getColumnModel().getColumn(1).setResizable(false);
            tblDungCu.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblDungCu.getColumnModel().getColumn(2).setResizable(false);
        }
        tblDungCu.getTableHeader().setFont(new Font("Cambria Math", Font.BOLD, 17));
        tblDungCu.getTableHeader().setOpaque(false);
        tblDungCu.getTableHeader().setBackground(new Color(245, 80, 21));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("DỤNG CỤ", jPanel24);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 77, 207));
        jLabel10.setText("DỊCH VỤ");

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(0, 77, 207));
        jLabel76.setText("DANH SÁCH DỊCH VỤ");

        tblCtDichVuThue.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblCtDichVuThue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "Đơn vị", "Số lượng", "Tổng giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCtDichVuThue.setRowHeight(35);
        tblCtDichVuThue.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane18.setViewportView(tblCtDichVuThue);
        tblCtDichVuThue.getTableHeader().setFont(new Font("Cambria Math", Font.BOLD, 17));
        tblCtDichVuThue.getTableHeader().setOpaque(false);
        tblCtDichVuThue.getTableHeader().setBackground(new Color(245, 80, 21));

        bttBoDv.setFont(new java.awt.Font("Tw Cen MT", 1, 20)); // NOI18N
        bttBoDv.setForeground(new java.awt.Color(255, 51, 0));
        bttBoDv.setText("X");
        bttBoDv.setPreferredSize(new java.awt.Dimension(45, 29));
        bttBoDv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttBoDvMouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 102));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 102));
        jSeparator2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSeparator2.setPreferredSize(new java.awt.Dimension(200, 7));

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setForeground(new java.awt.Color(0, 102, 102));
        jPanel34.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(0, 0, 102));
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/services - icon.png"))); // NOI18N
        jLabel82.setText(" ĐẶT DỊCH VỤ");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel82)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel82)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jSeparator7.setForeground(new java.awt.Color(0, 0, 102));

        javax.swing.GroupLayout pnDatDichVuLayout = new javax.swing.GroupLayout(pnDatDichVu);
        pnDatDichVu.setLayout(pnDatDichVuLayout);
        pnDatDichVuLayout.setHorizontalGroup(
            pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
            .addGroup(pnDatDichVuLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDatDichVuLayout.createSequentialGroup()
                        .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel76)
                            .addGroup(pnDatDichVuLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnDatDichVuLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel77)
                                    .addGroup(pnDatDichVuLayout.createSequentialGroup()
                                        .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel78)
                                            .addComponent(jLabel79))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblSanDatDv)
                                            .addComponent(lblMHDdatDv)
                                            .addComponent(lblTenKhachDatDv))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 1064, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDatDichVuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bttBoDv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(pnDatDichVuLayout.createSequentialGroup()
                        .addComponent(bttThemDv)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDatDichVuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhanDatDv, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(485, 485, 485))
        );
        pnDatDichVuLayout.setVerticalGroup(
            pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDatDichVuLayout.createSequentialGroup()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDatDichVuLayout.createSequentialGroup()
                        .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnDatDichVuLayout.createSequentialGroup()
                                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnDatDichVuLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(bttThemDv, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bttBoDv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnDatDichVuLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel77)
                            .addComponent(lblSanDatDv))
                        .addGap(31, 31, 31)
                        .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel78)
                            .addComponent(lblMHDdatDv))
                        .addGap(34, 34, 34)
                        .addGroup(pnDatDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenKhachDatDv)
                            .addComponent(jLabel79))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXacNhanDatDv, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pnTong.add(pnDatDichVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnLichSuThue.setBackground(new java.awt.Color(254, 235, 208));
        pnLichSuThue.setPreferredSize(new java.awt.Dimension(1180, 900));
        pnLichSuThue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlAllPhieuThue_ThanhToan.setBackground(new java.awt.Color(254, 235, 208));
        pnlAllPhieuThue_ThanhToan.setPreferredSize(new java.awt.Dimension(1180, 783));

        btnXemCtHoaDon.setBackground(new java.awt.Color(28, 184, 160));
        btnXemCtHoaDon.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXemCtHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnXemCtHoaDon.setText("XEM HÓA ĐƠN");
        btnXemCtHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemCtHoaDonActionPerformed(evt);
            }
        });

        tblAllPhieuThue_HoaDon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblAllPhieuThue_HoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ HÓA ĐƠN", "KHÁCH ĐẶT", "NGÀY THUÊ", "CHI PHÍ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAllPhieuThue_HoaDon.setRowHeight(35);
        tblAllPhieuThue_HoaDon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAllPhieuThue_HoaDon.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tblAllPhieuThue_HoaDon);
        if (tblAllPhieuThue_HoaDon.getColumnModel().getColumnCount() > 0) {
            tblAllPhieuThue_HoaDon.getColumnModel().getColumn(0).setResizable(false);
            tblAllPhieuThue_HoaDon.getColumnModel().getColumn(1).setResizable(false);
            tblAllPhieuThue_HoaDon.getColumnModel().getColumn(2).setResizable(false);
            tblAllPhieuThue_HoaDon.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 51, 102));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/searchFile - icon.png"))); // NOI18N
        jLabel33.setText("LỊCH SỬ PHIẾU THUÊ");

        txtTimKiemLichSu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemLichSuActionPerformed(evt);
            }
        });
        txtTimKiemLichSu.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtTimKiemLichSuPropertyChange(evt);
            }
        });
        txtTimKiemLichSu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiemLichSuKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("TÌM KIẾM:");

        javax.swing.GroupLayout pnlAllPhieuThue_ThanhToanLayout = new javax.swing.GroupLayout(pnlAllPhieuThue_ThanhToan);
        pnlAllPhieuThue_ThanhToan.setLayout(pnlAllPhieuThue_ThanhToanLayout);
        pnlAllPhieuThue_ThanhToanLayout.setHorizontalGroup(
            pnlAllPhieuThue_ThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAllPhieuThue_ThanhToanLayout.createSequentialGroup()
                .addGroup(pnlAllPhieuThue_ThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAllPhieuThue_ThanhToanLayout.createSequentialGroup()
                        .addGap(997, 997, 997)
                        .addComponent(btnXemCtHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAllPhieuThue_ThanhToanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKiemLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(166, 166, 166)
                        .addComponent(jLabel33))
                    .addGroup(pnlAllPhieuThue_ThanhToanLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAllPhieuThue_ThanhToanLayout.setVerticalGroup(
            pnlAllPhieuThue_ThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAllPhieuThue_ThanhToanLayout.createSequentialGroup()
                .addGroup(pnlAllPhieuThue_ThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlAllPhieuThue_ThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiemLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXemCtHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        pnLichSuThue.add(pnlAllPhieuThue_ThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, -1));
        pnlAllPhieuThue_ThanhToan.setVisible(false);

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setForeground(new java.awt.Color(0, 102, 102));
        jPanel38.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(0, 0, 102));
        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-list-50.png"))); // NOI18N
        jLabel86.setText("QUẢN LÍ PHIẾU THUÊ");

        lbDatSanNgay_LST.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbDatSanNgay_LST.setForeground(new java.awt.Color(204, 204, 204));
        lbDatSanNgay_LST.setText("Đặt Sân Ngay");
        lbDatSanNgay_LST.setPreferredSize(new java.awt.Dimension(95, 28));
        lbDatSanNgay_LST.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbDatSanNgay_LSTMouseMoved(evt);
            }
        });
        lbDatSanNgay_LST.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDatSanNgay_LSTMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbDatSanNgay_LSTMouseExited(evt);
            }
        });

        lbThongTinPhieuThue_LST.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinPhieuThue_LST.setForeground(new java.awt.Color(204, 204, 204));
        lbThongTinPhieuThue_LST.setText("Quản lý sân thuê");
        lbThongTinPhieuThue_LST.setPreferredSize(new java.awt.Dimension(151, 28));
        lbThongTinPhieuThue_LST.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbThongTinPhieuThue_LSTMouseMoved(evt);
            }
        });
        lbThongTinPhieuThue_LST.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongTinPhieuThue_LSTMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThongTinPhieuThue_LSTMouseExited(evt);
            }
        });

        lbThongTinNhanVien_QL7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinNhanVien_QL7.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinNhanVien_QL7.setText("Lịch Sử Thuê");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel86))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbThongTinPhieuThue_LST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbDatSanNgay_LST, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(lbThongTinNhanVien_QL7))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(345, 345, 345)
                        .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(679, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel86)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDatSanNgay_LST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbThongTinPhieuThue_LST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbThongTinNhanVien_QL7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnLichSuThue.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, -1));

        pnlHoaDon.setBackground(new java.awt.Color(254, 235, 208));
        pnlHoaDon.setPreferredSize(new java.awt.Dimension(1180, 783));
        pnlHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlHoaDonMouseEntered(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel55.setText("Họ tên khách thuê");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel57.setText("TỔNG ĐÃ TRẢ:");

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel58.setText("Ngày thuê:");

        jPanel14.setBackground(new java.awt.Color(200, 232, 249));

        tblCtThue_HoaDon.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblCtThue_HoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN SÂN", "LOẠI SÂN", "GIÁ THEO GIỜ", "GIỜ THUÊ", "GIỜ TRẢ", "KHUNG GIỜ", "SỐ GIỜ THUÊ", "THÀNH TIỀN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCtThue_HoaDon.setRowHeight(35);
        tblCtThue_HoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCtThue_HoaDonMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tblCtThue_HoaDon);

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(37, 102, 142));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("CHI TIẾT THUÊ");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel59.setText("Nhân viên phục vụ:");

        jPanel17.setBackground(new java.awt.Color(179, 193, 135));

        tblDichVu_HoaDon.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblDichVu_HoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN DỊCH VỤ", "ĐƠN GIÁ", "SL", "THÀNH TIỀN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDichVu_HoaDon.setRowHeight(35);
        jScrollPane12.setViewportView(tblDichVu_HoaDon);

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 102, 0));
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("DỊCH VỤ SỬ DỤNG");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(468, 468, 468))
        );

        btnInHoaDon.setBackground(new java.awt.Color(255, 0, 51));
        btnInHoaDon.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnInHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnInHoaDon.setText("IN HÓA ĐƠN");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        lblHoTen_HoaDon.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lblHoTen_HoaDon.setForeground(new java.awt.Color(0, 0, 102));
        lblHoTen_HoaDon.setText("CMND:");

        lblTenNVPV_HoaDon.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lblTenNVPV_HoaDon.setForeground(new java.awt.Color(0, 0, 102));
        lblTenNVPV_HoaDon.setText("CMND:");

        lblNgayThue_HoaDon.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lblNgayThue_HoaDon.setForeground(new java.awt.Color(0, 0, 102));
        lblNgayThue_HoaDon.setText("CMND:");

        lblTongTien_HoaDon.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lblTongTien_HoaDon.setText("CMND:");

        lblMaHoaDon_HoaDon.setBackground(new java.awt.Color(255, 255, 255));
        lblMaHoaDon_HoaDon.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblMaHoaDon_HoaDon.setForeground(new java.awt.Color(0, 0, 138));
        lblMaHoaDon_HoaDon.setText("MÃ HÓA ĐƠN");

        jLabel54.setBackground(new java.awt.Color(255, 255, 255));
        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/bill-icon.png"))); // NOI18N
        jLabel54.setText("HÓA ĐƠN:");

        pnlBack_HoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBack_HoaDonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBack_HoaDonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBack_HoaDonMouseExited(evt);
            }
        });

        lblBack.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/back - icon.png"))); // NOI18N
        lblBack.setText("BACK");
        lblBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBackMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBackMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBack_HoaDonLayout = new javax.swing.GroupLayout(pnlBack_HoaDon);
        pnlBack_HoaDon.setLayout(pnlBack_HoaDonLayout);
        pnlBack_HoaDonLayout.setHorizontalGroup(
            pnlBack_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBack_HoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBack_HoaDonLayout.setVerticalGroup(
            pnlBack_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBack, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlHoaDonLayout = new javax.swing.GroupLayout(pnlHoaDon);
        pnlHoaDon.setLayout(pnlHoaDonLayout);
        pnlHoaDonLayout.setHorizontalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHoaDonLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel57)
                        .addGap(18, 18, 18)
                        .addComponent(lblTongTien_HoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHoaDonLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel59)
                            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                                .addComponent(jLabel58)
                                .addGap(12, 12, 12)
                                .addComponent(lblNgayThue_HoaDon)))
                        .addGap(9, 9, 9)
                        .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHoTen_HoaDon)
                            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(lblTenNVPV_HoaDon)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHoaDonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlHoaDonLayout.createSequentialGroup()
                        .addComponent(pnlBack_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(257, 257, 257)
                        .addComponent(jLabel54)
                        .addGap(7, 7, 7)
                        .addComponent(lblMaHoaDon_HoaDon)
                        .addGap(0, 492, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlHoaDonLayout.setVerticalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54)
                    .addGroup(pnlHoaDonLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblMaHoaDon_HoaDon))
                    .addComponent(pnlBack_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlHoaDonLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                                .addComponent(jLabel55)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel59)
                                .addGap(28, 28, 28)
                                .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel58)
                                    .addComponent(lblNgayThue_HoaDon)))
                            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                                .addComponent(lblHoTen_HoaDon)
                                .addGap(28, 28, 28)
                                .addComponent(lblTenNVPV_HoaDon)))))
                .addGap(13, 13, 13)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongTien_HoaDon)
                    .addComponent(btnInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addGap(9, 9, 9))
        );

        pnLichSuThue.add(pnlHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, -1));

        pnTong.add(pnLichSuThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnThongTinDatTruoc.setBackground(new java.awt.Color(254, 235, 208));
        pnThongTinDatTruoc.setForeground(new java.awt.Color(0, 0, 102));
        pnThongTinDatTruoc.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnThongTinDatTruoc.setPreferredSize(new java.awt.Dimension(1180, 900));

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setForeground(new java.awt.Color(0, 102, 102));
        jPanel39.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(0, 0, 102));
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-reserve-48 (1).png"))); // NOI18N
        jLabel83.setText("ĐẶT TRƯỚC SÂN");

        lblDatTruocSan_TTDT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDatTruocSan_TTDT.setForeground(new java.awt.Color(204, 204, 204));
        lblDatTruocSan_TTDT.setText("Đặt trước sân");
        lblDatTruocSan_TTDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDatTruocSan_TTDTMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDatTruocSan_TTDTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDatTruocSan_TTDTMouseExited(evt);
            }
        });

        lblLichSuThue_DSN2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLichSuThue_DSN2.setForeground(new java.awt.Color(0, 51, 102));
        lblLichSuThue_DSN2.setText("Thông tin đặt trước");
        lblLichSuThue_DSN2.setPreferredSize(new java.awt.Dimension(89, 28));

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator26, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addComponent(lblDatTruocSan_TTDT)
                        .addGap(47, 47, 47)
                        .addComponent(lblLichSuThue_DSN2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 856, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatTruocSan_TTDT)
                    .addComponent(lblLichSuThue_DSN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jSeparator26, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel41.setBackground(new java.awt.Color(179, 193, 135));

        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(0, 102, 0));
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-reserve-32 (1).png"))); // NOI18N
        jLabel111.setText("DANH SÁCH ĐẶT TRƯỚC");

        tblDsPhieuDatTruoc_TTDT.setAutoCreateRowSorter(true);
        tblDsPhieuDatTruoc_TTDT.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tblDsPhieuDatTruoc_TTDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ ĐẶT TRƯỚC", "KHÁCH ĐẶT", "ĐẶT NGÀY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDsPhieuDatTruoc_TTDT.setGridColor(new java.awt.Color(107, 208, 219));
        tblDsPhieuDatTruoc_TTDT.setRowHeight(33);
        tblDsPhieuDatTruoc_TTDT.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDsPhieuDatTruoc_TTDT.getTableHeader().setReorderingAllowed(false);
        tblDsPhieuDatTruoc_TTDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDsPhieuDatTruoc_TTDTMouseClicked(evt);
            }
        });
        jScrollPane16.setViewportView(tblDsPhieuDatTruoc_TTDT);
        if (tblDsPhieuDatTruoc_TTDT.getColumnModel().getColumnCount() > 0) {
            tblDsPhieuDatTruoc_TTDT.getColumnModel().getColumn(0).setResizable(false);
            tblDsPhieuDatTruoc_TTDT.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblDsPhieuDatTruoc_TTDT.getColumnModel().getColumn(1).setResizable(false);
            tblDsPhieuDatTruoc_TTDT.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblDsPhieuDatTruoc_TTDT.getColumnModel().getColumn(2).setResizable(false);
            tblDsPhieuDatTruoc_TTDT.getColumnModel().getColumn(2).setPreferredWidth(70);
        }

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                    .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane16)
                .addContainerGap())
        );

        jPanel42.setBackground(new java.awt.Color(200, 232, 249));

        jLabel106.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(37, 102, 142));
        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-more-details-30.png"))); // NOI18N
        jLabel106.setText("CHI TIẾT ĐẶT TRƯỚC");

        tblCTDatTruoc_TTDT.setAutoCreateRowSorter(true);
        tblCTDatTruoc_TTDT.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tblCTDatTruoc_TTDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        tblCTDatTruoc_TTDT.setGridColor(new java.awt.Color(107, 208, 219));
        tblCTDatTruoc_TTDT.setRowHeight(33);
        tblCTDatTruoc_TTDT.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane19.setViewportView(tblCTDatTruoc_TTDT);
        if (tblCTDatTruoc_TTDT.getColumnModel().getColumnCount() > 0) {
            tblCTDatTruoc_TTDT.getColumnModel().getColumn(0).setResizable(false);
            tblCTDatTruoc_TTDT.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblCTDatTruoc_TTDT.getColumnModel().getColumn(1).setResizable(false);
            tblCTDatTruoc_TTDT.getColumnModel().getColumn(1).setPreferredWidth(15);
            tblCTDatTruoc_TTDT.getColumnModel().getColumn(2).setResizable(false);
            tblCTDatTruoc_TTDT.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblCTDatTruoc_TTDT.getColumnModel().getColumn(3).setResizable(false);
            tblCTDatTruoc_TTDT.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnThongTinDatTruocLayout = new javax.swing.GroupLayout(pnThongTinDatTruoc);
        pnThongTinDatTruoc.setLayout(pnThongTinDatTruocLayout);
        pnThongTinDatTruocLayout.setHorizontalGroup(
            pnThongTinDatTruocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(pnThongTinDatTruocLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnThongTinDatTruocLayout.setVerticalGroup(
            pnThongTinDatTruocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinDatTruocLayout.createSequentialGroup()
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(pnThongTinDatTruocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(55, 55, 55))
        );

        pnTong.add(pnThongTinDatTruoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThongTinPhieuThueMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongTinPhieuThueMouseMoved
        // TODO add your handling code here:
        lbThongTinPhieuThue.setForeground(new Color(0,0,102));
        btnThongTinPhieuThue.setBackground(new Color(145,250,255));

    }//GEN-LAST:event_btnThongTinPhieuThueMouseMoved

    private void btnThongTinPhieuThueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongTinPhieuThueMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);
        pnSoDoSanBong.setVisible(false);
        pnDatTruocSan.setVisible(false);
        pnDatSanNgay.setVisible(false);
        pnQuanLyPhieuThue.setVisible(true);
        pnDatDichVu.setVisible(false);
        pnLichSuThue.setVisible(false);
        pnThongTinDatTruoc.setVisible(false);
        refreshTblDsSanDangThue();
        refreshTblDsPhieuDt();
    }//GEN-LAST:event_btnThongTinPhieuThueMouseClicked

    private void btnThongTinPhieuThueMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongTinPhieuThueMouseExited
        // TODO add your handling code here:
        lbThongTinPhieuThue.setForeground(Color.black);
        btnThongTinPhieuThue.setBackground(new Color(220,220,230));
    }//GEN-LAST:event_btnThongTinPhieuThueMouseExited

    private void lbAboutUsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAboutUsMouseMoved
        // TODO add your handling code here:
        lbAboutUs.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbAboutUsMouseMoved

    private void lbAboutUsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAboutUsMouseExited
        // TODO add your handling code here:
        lbAboutUs.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbAboutUsMouseExited

    private void lbTroGiupMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTroGiupMouseMoved
        // TODO add your handling code here:
        lbTroGiup.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbTroGiupMouseMoved

    private void lbTroGiupMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTroGiupMouseExited
        // TODO add your handling code here:
        lbTroGiup.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbTroGiupMouseExited

    private void lbLienHeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLienHeMouseMoved
        // TODO add your handling code here:
        lbLienHe.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbLienHeMouseMoved

    private void lbLienHeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLienHeMouseExited
        // TODO add your handling code here:
        lbLienHe.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbLienHeMouseExited

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jLabel9MouseClicked

    private void btnSuaThongTin_TTCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaThongTin_TTCNActionPerformed
        // TODO add your handling code here:
        pnCTTTCN_TTCN.setVisible(false);
        pnSuaThongTin_TTCN.setVisible(true);
        pnDoiMatKhau_TTCN.setVisible(false);
        txtHoTenUpdate.setText(nhanVien.getTenNV());
        txtSdtUpdate.setText(nhanVien.getSdt());
        lblWarnHoTen.setVisible(false);
        lblWarnSdt.setVisible(false);
    }//GEN-LAST:event_btnSuaThongTin_TTCNActionPerformed

    private void btnDoiMatKhau_TTCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhau_TTCNActionPerformed
        // TODO add your handling code here:
        pnCTTTCN_TTCN.setVisible(false);
        pnSuaThongTin_TTCN.setVisible(false);
        pnDoiMatKhau_TTCN.setVisible(true);
        txtMkCu.setText("");
        txtMkMoi.setText("");
        txtMkXacNhan.setText("");
        lblWarnSaiMk.setVisible(false);
        lblWarnMkKhongTrung.setVisible(false);
    }//GEN-LAST:event_btnDoiMatKhau_TTCNActionPerformed

    private void btnDatTruocSanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatTruocSanMouseMoved
        // TODO add your handling code here:
        lbThongTinDatTruoc.setForeground(new Color(0,0,102));
        btnDatTruocSan.setBackground(new Color(145,250,255));
    }//GEN-LAST:event_btnDatTruocSanMouseMoved

    private void btnDatTruocSanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatTruocSanMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);
        pnLichSuThue.setVisible(false);
        pnSoDoSanBong.setVisible(false);
        pnDatDichVu.setVisible(false);
	pnDatTruocSan.setVisible(true);
        pnDatSanNgay.setVisible(false);
        pnQuanLyPhieuThue.setVisible(false);
        pnThongTinDatTruoc.setVisible(false);
        dsSanKhaDung_DatTruoc = ioData.getDsSanKhaDung(dcNgayDatTruoc.getDate(),(Date) spnGioHenDen_DatSan.getValue(),(Date) spnGioHenTra_DatSan.getValue());
        dftblSan5.setRowCount(0);
        dftblSan7.setRowCount(0);
        dftblSan11.setRowCount(0);
        xetKhaDungHomNay();
        addTblSanKhaDung_DatTruoc(dsSanKhaDung_DatTruoc);
        ngayDatOldValue = dcNgayDatTruoc.getDate();
        dsKhachHang = ioData.getDsKhachHang();
        insertCbxCMND(dsKhachHang);
    }//GEN-LAST:event_btnDatTruocSanMouseClicked

    private void btnDatTruocSanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatTruocSanMouseExited
        // TODO add your handling code here:
        lbThongTinDatTruoc.setForeground(Color.BLACK);
	btnDatTruocSan.setBackground(new Color(220,220,230));
    }//GEN-LAST:event_btnDatTruocSanMouseExited

    private void btnSoDoSanBongMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSoDoSanBongMouseMoved
        // TODO add your handling code here:
        lbQuanLiSanBong.setForeground(new Color(0,0,102));
        btnSoDoSanBong.setBackground(new Color(145, 250, 255));
    }//GEN-LAST:event_btnSoDoSanBongMouseMoved

    private void btnSoDoSanBongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSoDoSanBongMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);
        pnLichSuThue.setVisible(false);
        pnSoDoSanBong.setVisible(true);
        pnDatDichVu.setVisible(false);
        pnDatTruocSan.setVisible(false);
        pnDatSanNgay.setVisible(false);
        pnQuanLyPhieuThue.setVisible(false);
        pnThongTinDatTruoc.setVisible(false);
        refreshSoDoSanBong();
    }//GEN-LAST:event_btnSoDoSanBongMouseClicked

    private void btnSoDoSanBongMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSoDoSanBongMouseExited
        // TODO add your handling code here:
        lbQuanLiSanBong.setForeground(Color.black);
	btnSoDoSanBong.setBackground(new Color(220,220,230));
    }//GEN-LAST:event_btnSoDoSanBongMouseExited

    private void lblHoTenNv_MenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHoTenNv_MenuMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(true);
        pnSoDoSanBong.setVisible(false);      
        pnCTTTCN_TTCN.setVisible(true);
        pnSuaThongTin_TTCN.setVisible(false);
        pnDoiMatKhau_TTCN.setVisible(false);       
        pnDatTruocSan.setVisible(false);
        pnDatSanNgay.setVisible(false);
        pnQuanLyPhieuThue.setVisible(false); 
        pnDatDichVu.setVisible(false);
        pnLichSuThue.setVisible(false);
        pnThongTinDatTruoc.setVisible(false);
    }//GEN-LAST:event_lblHoTenNv_MenuMouseClicked

    private void lbDatSanNgay_TTPTMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatSanNgay_TTPTMouseMoved
        // TODO add your handling code here:
        lbDatSanNgay_TTPT.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbDatSanNgay_TTPTMouseMoved

    private void lbDatSanNgay_TTPTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatSanNgay_TTPTMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);
        pnLichSuThue.setVisible(false);
        pnSoDoSanBong.setVisible(false);
        pnDatDichVu.setVisible(false);
	pnDatTruocSan.setVisible(false);
        pnDatSanNgay.setVisible(true);
        pnQuanLyPhieuThue.setVisible(false);
        dsSanBongKhaDung_ThueNgay = ioData.getDsSanKhaDung(clock.getClockDate(), clock.getClockTime(), (Date) spnGioHenTra.getValue());
        dftblCtPhieuThueNgay.setRowCount(0);
        addTblSanBongKhaDungThueNgay(dsSanBongKhaDung_ThueNgay);
        dsKhachHang = ioData.getDsKhachHang();
        insertCbxCMND(dsKhachHang);
    }//GEN-LAST:event_lbDatSanNgay_TTPTMouseClicked

    private void lbDatSanNgay_TTPTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatSanNgay_TTPTMouseExited
        // TODO add your handling code here:
        lbDatSanNgay_TTPT.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbDatSanNgay_TTPTMouseExited

    private void lbGiayPhepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbGiayPhepMouseExited
        // TODO add your handling code here:
        lbGiayPhep.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbGiayPhepMouseExited

    private void lbGiayPhepMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbGiayPhepMouseMoved
        // TODO add your handling code here:
        lbGiayPhep.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbGiayPhepMouseMoved

    private void tblPhieuDatTruocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuDatTruocMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPhieuDatTruocMouseClicked

    private void btnNhanSanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhanSanMouseClicked
        int[] row = tblPhieuDatTruoc.getSelectedRows();

        if (row.length > 0) {
            int maDt = Integer.parseInt(String.valueOf(tblPhieuDatTruoc.getValueAt(row[0], 0)));
            String maSan = String.valueOf(tblPhieuDatTruoc.getValueAt(row[0], 1));
            for (PHIEUDATTRUOC pdt : dsPhieuDTs) {
                for (CT_DATTRUOC ctdt : pdt.getDsCtDatTruoc()) {
                    if (maDt == pdt.getMaDatTruoc() && maSan.compareTo(ctdt.getSanDatTruoc().getMaSan()) == 0) {
                        if (ioData.trangThaiSan(ctdt.getSanDatTruoc().getMaSan())==TRANGTHAI.BAOTRI) {
                            JOptionPane.showMessageDialog(rootPane, "Sân " + maSan + "đang bảo trì nên chi tiết đặt trước này không thể\n"
                                    + "nhận sân, rất xin lỗi vì sự bất tiện này");
                            ioData.xoaCtDatTruoc(ctdt, maDt);
                            ioData.xetXoaPhieuDt(pdt);
                            refreshTblDsPhieuDt();
                            return;
                        }
                        if (ctdt.isTime(clock.getClockTime()) == 0) {
                            if (tblDsSanDangThue.getRowCount()>0) {
                                for (int i = 0; i < tblDsSanDangThue.getRowCount(); i++) {
                                    if (maSan.compareToIgnoreCase(String.valueOf(tblDsSanDangThue.getValueAt(i, 0)))==0) {
                                        int chon = JOptionPane.showConfirmDialog(rootPane,"Hiện đang có người thuê sân " + maSan+". Trả sân " + maSan+" ngay \n" 
                                                + "bây giờ để phiếu đặt trước "+maDt+" nhận sân ?","ĐANG CÓ NGƯỜI THUÊ SÂN" +maSan, JOptionPane.YES_NO_OPTION);
                                        if (chon==JOptionPane.YES_OPTION) {
                                            tblDsSanDangThue.setRowSelectionInterval(i, i);
                                            btnTraSanMouseClicked(evt);
                                        } else
                                            return;
                                    }
                                }
                            }
                            PHIEUTHUE phieuNhanSan = new PHIEUTHUE(ioData.sinhMaHoaDon(), pdt.getkDatTruoc(), null, pdt.getNgayDat());
                            CT_THUE ctNhanSan = new CT_THUE(ctdt.getSanDatTruoc(), ctdt.getTgDuKienDen(), ctdt.getTgDuKienTra(), KHUNGGIO.findKhungGio(ctdt.getTgDuKienDen()));
                            ioData.insertphieuthue(phieuNhanSan);
                            ioData.insertCT_Thue(ctNhanSan, phieuNhanSan.getMaHoaDon());
                            ioData.xoaCtDatTruoc(ctdt, pdt.getMaDatTruoc());                            
                            ioData.xetXoaPhieuDt(pdt);
                            
                            refreshTblDsPhieuDt();
                            refreshTblDsSanDangThue();
                            refreshSoDoSanBong();
                        } else if (ctdt.isTime(clock.getClockTime()) == 1) {
                            JOptionPane.showMessageDialog(rootPane, "Vẫn còn quá sớm để đơn đặt trước này có thể nhận sân. Thời gian \n"
                                    + "được phép nhận sân là từ 5 phút trước giờ hẹn nhận đến 15 phút sau giờ hẹn nhận");
                        } else if (ctdt.isTime(clock.getClockTime()) == -1) {
                            JOptionPane.showMessageDialog(rootPane, "Xin lỗi! đơn đặt trước này đã đến quá muộn, không thể nhận sân nữa. Thời gian \n"
                                    + "được phép nhận sân là từ 5 phút trước giờ hẹn nhận đến 15 phút sau giờ hẹn nhận");
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnNhanSanMouseClicked

    private void tblDsSanDangThueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDsSanDangThueMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDsSanDangThueMouseClicked

    private void tblDsSanDangThueMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDsSanDangThueMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDsSanDangThueMouseReleased

    private void btnTraSanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTraSanMouseClicked
        int row = tblDsSanDangThue.getSelectedRow();
        if (row >= 0) {
            CT_THUE sanTra = dsCtSanDangThue.get(row);
            Date gioTra = new Date();
            try {
                String gioTraStr = formatGio.format(gioTra);
                gioTra = formatGio.parse(gioTraStr);
                sanTra.setGioTra(gioTra);
                ioData.traSanCT_Thue(sanTra); 
                refreshTblDsSanDangThue();
                JOptionPane.showMessageDialog(rootPane, "Sân "+sanTra.getSanBong().getMaSan()+" đã được trả lại");
                if (ioData.isTraHet(sanTra.getMaHoaDon())) {
                    JOptionPane.showMessageDialog(rootPane, "Phiếu thuê mã " + sanTra.getMaHoaDon() + " đã trả hết sân.\n Hãy thanh toán");
                    //Thanh toán phiếu thuê apache word
                    PHIEUTHUE phieuThanhToan = ioData.getTtThanhToan(sanTra.getMaHoaDon());
                    phieuThanhToan.setNvGhiPhieu(nhanVien);
                    ioData.thanhToan(phieuThanhToan);
                    System.out.println("Đã thanh toán");
                    new FORMTHANHTOAN(phieuThanhToan).setVisible(true);
                }
            } catch (ParseException ex) {
                System.out.println("Lỗi xử lý bttTraSan: FORMQLPHIEUTHUE.bttTraSanMouseClicked");
            }
        }
    }//GEN-LAST:event_btnTraSanMouseClicked

    private void btnThemDvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDvActionPerformed
        // TODO add your handling code here:
        dsDv = ioData.getDsDichVu();
        insertTblDsDv();
        int row = tblDsSanDangThue.getSelectedRow();
        if (row >= 0) {
            pnThongTinCaNhan.setVisible(false);
            pnDatDichVu.setVisible(true);
            pnDatTruocSan.setVisible(false);
            pnSoDoSanBong.setVisible(false);
            pnQuanLyPhieuThue.setVisible(false);
            pnQuanLyPhieuThue.setVisible(false);
            dftblCtDichVuThue.setRowCount(0);
            lblSanDatDv.setText(String.valueOf(tblDsSanDangThue.getValueAt(row, 0)));
            String maHoaDon = String.valueOf(tblDsSanDangThue.getValueAt(row, 1));
            lblMHDdatDv.setText(maHoaDon);
            lblTenKhachDatDv.setText(String.valueOf(ioData.findKhach(maHoaDon)));
        } else {
            JOptionPane.showMessageDialog(rootPane, "Hãy chọn sân đặt dịch vụ trên bảng");
        }
    }//GEN-LAST:event_btnThemDvActionPerformed

    private void lbThongTinPhieuThue_DSNMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinPhieuThue_DSNMouseExited
        // TODO add your handling code here:
        lbThongTinPhieuThue_DSN.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbThongTinPhieuThue_DSNMouseExited

    private void lbThongTinPhieuThue_DSNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinPhieuThue_DSNMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);
        pnSoDoSanBong.setVisible(false);
        pnDatTruocSan.setVisible(false);
        pnDatSanNgay.setVisible(false);
        pnDatSanNgay.setVisible(false);
        pnQuanLyPhieuThue.setVisible(true);
        pnLichSuThue.setVisible(false);
        refreshTblDsPhieuDt();
        refreshTblDsSanDangThue();
    }//GEN-LAST:event_lbThongTinPhieuThue_DSNMouseClicked

    private void lbThongTinPhieuThue_DSNMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinPhieuThue_DSNMouseMoved
        // TODO add your handling code here:
        lbThongTinPhieuThue_DSN.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbThongTinPhieuThue_DSNMouseMoved

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        int[] rows = tblSanKhaDung_ThueNgay.getSelectedRows();
        int[] cols = tblSanKhaDung_ThueNgay.getSelectedColumns();
        int i = 0;

        for (int row : rows) {
            String sanThue = String.valueOf(tblSanKhaDung_ThueNgay.getValueAt(row-i, 0));
            String gioHenDen = null;
            gioHenDen = ioData.isTrungGio(sanThue,(Date) spnGioHenTra.getValue());
            if (gioHenDen != null) {
                JOptionPane.showMessageDialog(rootPane, "Có người đặt trước sân " + sanThue + " vào " + gioHenDen + ". Hãy chỉnh giờ hẹn trả của bạn trước thời gian này."
                    + " Nếu người đặt trước không đến nhận sân bạn vẫn có thể tiếp tục thuê sân, ngược lại bạn phải nhường sân cho người đặt trước",
                    "GIỜ HẸN TRẢ TRÙNG VỚI GIỜ HẸN ĐẾN CỦA MỘT PHIẾU ĐẶT TRƯỚC", HEIGHT);
            } else {
                if (ioData.isKhaDung(new Date(),new Date(),(Date) spnGioHenTra.getValue(),sanThue)) {
                    dftblCtPhieuThueNgay.addRow(new Object[]{
                        sanThue,
                        tblSanKhaDung_ThueNgay.getValueAt(row-i, 1),
                        tblSanKhaDung_ThueNgay.getValueAt(row-i, 2),
                        formatNgay.format(new Date()),
                        formatGio.format(new Date()),
                        String.valueOf(KHUNGGIO.findKhungGio(new Date())),
                        formatGio.format(spnGioHenTra.getValue())
                    });
                    dftblSanKhaDung_ThueNgay.removeRow(row - i);
                    i++;
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Rất tiếc! Sân " + sanThue + "đã có người thuê trước");
                    dftblSanKhaDung_ThueNgay.removeRow(row - i);
                    i++;
                }
            }
        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void cbxCMND_ThueNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCMND_ThueNgayActionPerformed
        int i = cbxCMND_ThueNgay.getSelectedIndex();
        if (i != -1) {
            System.out.println(i);
            txtHoTen.setText(dsKhachHang.get(i).getHoTen());
            txtSdt.setText(dsKhachHang.get(i).getSdt());
        }
        else{
            txtHoTen.setText("");
            txtSdt.setText("");
        }
    }//GEN-LAST:event_cbxCMND_ThueNgayActionPerformed

    private void cbxCMND_ThueNgayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxCMND_ThueNgayKeyPressed

    }//GEN-LAST:event_cbxCMND_ThueNgayKeyPressed

    private void cbxCMND_ThueNgayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxCMND_ThueNgayKeyTyped

    }//GEN-LAST:event_cbxCMND_ThueNgayKeyTyped

    private void tblCtPhieuThueNgayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCtPhieuThueNgayMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            int[] rows = tblCtPhieuThueNgay.getSelectedRows();
            int[] cols = tblCtPhieuThueNgay.getSelectedColumns();

            for (int row : rows) {
                dftblCtPhieuThueNgay.removeRow(row);
                dsSanBongKhaDung_ThueNgay = ioData.getDsSanKhaDung(clock.getClockDate(), clock.getClockTime(), (Date) spnGioHenTra.getValue());
                dftblSanKhaDung_ThueNgay.setRowCount(0);
                addTblSanBongKhaDungThueNgay(dsSanBongKhaDung_ThueNgay);
            }
        }
    }//GEN-LAST:event_tblCtPhieuThueNgayMouseClicked

    private void spnGioHenTraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnGioHenTraStateChanged
        if (((Date) spnGioHenTra.getValue()).before(clock.getClockTime())) {
            spnGioHenTra.setValue(clock.getClockTime());
            return;
        }
        try {
            if (((Date) spnGioHenTra.getValue()).after(formatGio.parse("23:00 PM"))) {
                spnGioHenTra.setValue(formatGio.parse("23:00 PM"));
                return;
            }
        } catch (ParseException ex) {
            System.out.println("Lỗi: GDNHANVIEN.spnGioHenTraStateChanged() 3660");
        }

        dsSanBongKhaDung_ThueNgay = ioData.getDsSanKhaDung(clock.getClockDate(), clock.getClockTime(),(Date) spnGioHenTra.getValue());
        dftblSanKhaDung_ThueNgay.setRowCount(0);
        addTblSanBongKhaDungThueNgay(dsSanBongKhaDung_ThueNgay);
    }//GEN-LAST:event_spnGioHenTraStateChanged

    private void btnXacNhanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanMouseClicked
        // ngoại lệ
        if (cbxCMND_ThueNgay.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Hãy nhập CMND của khách thuê hoặc chọn CMND của khách đã thuê" ,"CMND không được phép để trống",HEIGHT);
        }
        else if (txtHoTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Họ tên người thuê sân không được để trống");
        }
        else if (txtSdt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "SDT người thuê không dược phép để trống");
        }
        else if (dftblCtPhieuThueNgay.getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(rootPane, "Hãy chọn sân mà khách muốn thuê");
        }
        else {
            //Thêm phiếu thuê mới
            PHIEUTHUE newPhieuthue = new PHIEUTHUE();
            String cmnd = String.valueOf(cbxCMND_ThueNgay.getSelectedItem());
            KHACH newKhach = new KHACH(cmnd, txtHoTen.getText(), txtSdt.getText());
            
            //Xét khách đặt sân đã tồn tại chưa. Nếu chưa tồn tại thì thêm khách mới vào KHACHTHUE
            if (!ioData.khachIsExist(cmnd)) {
                ioData.insertKhachThue(newKhach);
                dsKhachHang = ioData.getDsKhachHang();
                insertCbxCMND(dsKhachHang);
            }
            
            //Giờ bắt đầu thuê là giờ hiện tại
            Date ngayHienTai = new Date();
            newPhieuthue.setMaHoaDon(ioData.sinhMaHoaDon());
            newPhieuthue.setKhach(newKhach);
            newPhieuthue.setNgayGhiPhieu(ngayHienTai);

            //Thêm phiếu thuê mới vào PHIEUTHUE
            ioData.insertphieuthue(newPhieuthue);
            
            //Thêm các chi tiết thuê của phiếu thuê mới vào CT_THUE
            for (int row = 0; row < tblCtPhieuThueNgay.getRowCount(); row++) {
                String sanBong = String.valueOf(dftblCtPhieuThueNgay.getValueAt(row, 0));
                String loaiSan = String.valueOf(dftblCtPhieuThueNgay.getValueAt(row, 1));
                SANBONG sanThue = new SANBONG(sanBong, LOAISAN.findLoaiSan(loaiSan), TRANGTHAI.DANGSUDUNG);
                String KhungGio = String.valueOf(dftblCtPhieuThueNgay.getValueAt(row, 5));
                if (ioData.trangThaiSan(sanBong) == TRANGTHAI.DANGSUDUNG) {
                    for (int i = 0; i < tblDsSanDangThue.getRowCount(); i++) {
                        if (sanBong.compareToIgnoreCase(String.valueOf(tblDsSanDangThue.getValueAt(i, 0))) == 0) {
                            JOptionPane.showMessageDialog(rootPane, "Hiện đang có người thuê sân " + sanBong + ". Trả sân " + sanBong + " ngay \n"
                                    + "bây giờ để phiếu thuê "+newPhieuthue.getMaHoaDon()+" nhận sân.");
                            tblDsSanDangThue.setRowSelectionInterval(i, i);
                            btnTraSanMouseClicked(evt);
                            JOptionPane.showMessageDialog(rootPane, "Sân "+sanBong+" đã được nhường lại cho phiếu thuê này");
                        }
                    }
                }
                try {
                    Date gioThue = new Date();
                    Date tgdukientra = new Date();
                    gioThue = formatGio.parse(String.valueOf(dftblCtPhieuThueNgay.getValueAt(row, 4)));
                    tgdukientra = formatGio.parse(String.valueOf(dftblCtPhieuThueNgay.getValueAt(row, 6)));

                    CT_THUE newCt_thue = new CT_THUE(sanThue, gioThue, tgdukientra, KHUNGGIO.findKhungGio(KhungGio));
                    newPhieuthue.addCtThue(newCt_thue);
                    ioData.insertCT_Thue(newCt_thue, newPhieuthue.getMaHoaDon());
                } catch (ParseException ex) {
                    System.out.println("Loi dinh dang ngay gio");
                }
            }
            JOptionPane.showMessageDialog(rootPane, "ĐẶT SÂN THÀNH CÔNG");
            refresh();
            refreshSoDoSanBong();
            btnThongTinPhieuThueMouseClicked(evt);
        }
    }//GEN-LAST:event_btnXacNhanMouseClicked

    private void pnlTaiKhoanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTaiKhoanMouseExited
        pnlTaiKhoan.setBackground(Color.WHITE);
    }//GEN-LAST:event_pnlTaiKhoanMouseExited

    private void pnlTaiKhoanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTaiKhoanMouseEntered
        pnlTaiKhoan.setBackground(new Color(240,240,240));
    }//GEN-LAST:event_pnlTaiKhoanMouseEntered

    private void pnlTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTaiKhoanMouseClicked
        lblHoTenNv_MenuMouseClicked(evt);
    }//GEN-LAST:event_pnlTaiKhoanMouseClicked

    private void lblHoTenNv_MenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHoTenNv_MenuMouseEntered
        pnlTaiKhoanMouseEntered(evt);
    }//GEN-LAST:event_lblHoTenNv_MenuMouseEntered

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        if (IODATA.xacThucLogin(nhanVien.getMaNV(), nhanVien.getMatKhau())==2) {
            this.dispose();
            new DANGNHAP().setVisible(true);
        }else this.dispose();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void bttBoCtDatTruocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttBoCtDatTruocMouseClicked
        int[] rows = tblCTDatTruoc.getSelectedRows();
        if (rows.length==0) {
            return;
        }
        dftblCtDatTruoc.removeRow(rows[0]);
        addTblSanKhaDung_DatTruoc(dsSanKhaDung_DatTruoc);
        boolean khaDung = false;
        for (int row = 0; row < tblCTDatTruoc.getRowCount(); row++) {
            for (SANBONG sanKhaDung : dsSanKhaDung_DatTruoc) {
                if (String.valueOf(tblCTDatTruoc.getValueAt(row, 0)).compareTo(sanKhaDung.getMaSan())==0) {
                    khaDung = true;
                    break;
                }
            }
            if (khaDung) {
                removeSanKhaDung(row);
                khaDung = false;
            }
        }
    }//GEN-LAST:event_bttBoCtDatTruocMouseClicked

    private void btnThemSan_DatSanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemSan_DatSanMouseClicked

        int[] rows5 = tblSan5.getSelectedRows();
        int i = 0;

        KHUNGGIO khungGio = KHUNGGIO.findKhungGio((Date) spnGioHenDen_DatSan.getValue());

        for (int row : rows5) {
            String sanThue = String.valueOf(tblSan5.getValueAt(row - i, 0));
            SANBONG san5 = timSan(dsSanKhaDung_DatTruoc, sanThue);
            String gioHenTra = String.valueOf(formatGio.format((Date) spnGioHenTra_DatSan.getValue()));
            String gioNhanSan = String.valueOf(formatGio.format((Date) spnGioHenDen_DatSan.getValue()));
            san5.setGiaTheoGio(ioData.timHeSo_Kg(khungGio.getMaKG()));
            dftblCtDatTruoc.addRow(new Object[]{
                sanThue,
                "Sân 5",
                khungGio.getKG(),
                gioNhanSan,
                gioHenTra,
                san5.getGiaTheoGio() +"đ /h"
            });
            dftblSan5.removeRow(row - i);
            i++;
        }

        int[] rows7 = tblSan7.getSelectedRows();
        int k = 0;

        for (int row : rows7) {
            String sanThue = String.valueOf(tblSan7.getValueAt(row - k, 0));
            SANBONG san7 = timSan(dsSanKhaDung_DatTruoc, sanThue);
            String gioHenTra = String.valueOf(formatGio.format((Date) spnGioHenTra_DatSan.getValue()));
            String gioNhanSan = String.valueOf(formatGio.format((Date) spnGioHenDen_DatSan.getValue()));
            san7.setGiaTheoGio(ioData.timHeSo_Kg(khungGio.getMaKG()));
            dftblCtDatTruoc.addRow(new Object[]{
                sanThue,
                "Sân 7",
                khungGio.getKG(),
                gioNhanSan,
                gioHenTra,
                san7.getGiaTheoGio()+"đ /h"
            });
            dftblSan7.removeRow(row - k);
            k++;
        }

        int[] rows11 = tblSan11.getSelectedRows();
        int j = 0;

        for (int row : rows11) {
            String sanThue = String.valueOf(tblSan11.getValueAt(row - j, 0));
            SANBONG san11 = timSan(dsSanKhaDung_DatTruoc, sanThue);
            String gioHenTra = String.valueOf(formatGio.format((Date) spnGioHenTra_DatSan.getValue()));
            String gioNhanSan = String.valueOf(formatGio.format((Date) spnGioHenDen_DatSan.getValue()));
            san11.setGiaTheoGio(ioData.timHeSo_Kg(khungGio.getMaKG()));
            dftblCtDatTruoc.addRow(new Object[]{
                sanThue,
                "Sân 11",
                khungGio.getKG(),
                gioNhanSan,
                gioHenTra,
                san11.getGiaTheoGio()+"đ /h"
            });
            dftblSan11.removeRow(row - j);
            j++;
        }
    }//GEN-LAST:event_btnThemSan_DatSanMouseClicked

    private void dcNgayDatTruocPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcNgayDatTruocPropertyChange

        try {
            if (dcNgayDatTruoc.getDate().before(formatNgay.parse(formatNgay.format(new Date())))) {
                dcNgayDatTruoc.setDate(ngayDatOldValue);
            }
            else {
                if (dftblCtDatTruoc.getRowCount() != 0) {
                    int chon = JOptionPane.showConfirmDialog(rootPane, "Thay đổi ngày đặt sân sẽ xóa hết chi tiết đặt sân cũ. Bạn có muốn tiếp tục?", "CHÚ Ý", JOptionPane.YES_NO_OPTION);
                    if (chon == JOptionPane.YES_OPTION) {
                        dsSanKhaDung_DatTruoc = ioData.getDsSanKhaDung(dcNgayDatTruoc.getDate(), (Date) spnGioHenDen_DatSan.getValue(), (Date) spnGioHenTra_DatSan.getValue());
                        addTblSanKhaDung_DatTruoc(dsSanKhaDung_DatTruoc);
                        ngayDatOldValue = dcNgayDatTruoc.getDate();
                        dftblCtDatTruoc.setRowCount(0);
                    } else {
                        dcNgayDatTruoc.setDate(ngayDatOldValue);
                    }
                } else {
                    dsSanKhaDung_DatTruoc = ioData.getDsSanKhaDung(dcNgayDatTruoc.getDate(), (Date) spnGioHenDen_DatSan.getValue(), (Date) spnGioHenTra_DatSan.getValue());
                    addTblSanKhaDung_DatTruoc(dsSanKhaDung_DatTruoc);
                    System.out.println("OKKKKKKKKKKK");
                }
            }
        } catch (ParseException ex) {
            System.out.println("Lỗi format thời gian: GDNHANVIEN.dcNgayDatTruocPropertyChange() 3533");
        }
    }//GEN-LAST:event_dcNgayDatTruocPropertyChange

    private void cbxCMND_DatTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCMND_DatTruocActionPerformed
        lblWarnCMND_DatTruoc.setVisible(false);
        int i = cbxCMND_DatTruoc.getSelectedIndex();
        if (i != -1) {
            System.out.println(i);
            txtHoTen_DatTruoc.setText(dsKhachHang.get(i).getHoTen());
            txtSDT_DatTruoc.setText(dsKhachHang.get(i).getSdt());
            lblWarnHoTen_DatTruoc.setVisible(false);
            lblWarnSDT_DatTruoc.setVisible(false);
        }
        else{
            txtHoTen_DatTruoc.setText("");
            txtSDT_DatTruoc.setText("");
        }
    }//GEN-LAST:event_cbxCMND_DatTruocActionPerformed

    private void cbxCMND_DatTruocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxCMND_DatTruocKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxCMND_DatTruocKeyPressed

    private void cbxCMND_DatTruocKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxCMND_DatTruocKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxCMND_DatTruocKeyTyped

    private void spnGioHenDen_DatSanStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnGioHenDen_DatSanStateChanged

        if (isToday()) {
            String spnhour = spnHourFormat.format((Date) spnGioHenDen_DatSan.getValue());
            String now = spnHourFormat.format(new Date().getTime()+(60*60*1000));
            try {
                Date spnHourDate = spnHourFormat.parse(spnhour);
                Date nowDate = spnHourFormat.parse(now);
                if (spnHourDate.before(nowDate)) {
                    spnGioHenDen_DatSan.setValue(nowDate);
                }
            } catch (ParseException ex) {
                System.out.println("Lỗi định dạng ngày giờ: GDNHANVIEN.spnGioHenDen_DatSanStateChanged() 3346");
            }
        }
        try {
            if (((Date) spnGioHenDen_DatSan.getValue()).before(spnHourFormat.parse("06:00 AM"))) {
                spnGioHenDen_DatSan.setValue(spnHourFormat.parse("06:00 AM"));
                return;
            }
            if (((Date) spnGioHenDen_DatSan.getValue()).after(spnHourFormat.parse("23:00 PM"))) {
                spnGioHenDen_DatSan.setValue(spnHourFormat.parse("23:00 PM"));
                return;
            }
        } catch (ParseException ex) {
            System.out.println("Lỗi định dạng ngày giờ: GDNHANVIEN.spnGioHenDen_DatSanStateChanged() 3354");
        }

        if (((Date)spnGioHenTra_DatSan.getValue()).before((Date) spnGioHenDen_DatSan.getValue())) {
            spnGioHenTra_DatSan.setValue(spnGioHenDen_DatSan.getValue());
            return;
        }
        dsSanKhaDung_DatTruoc = ioData.getDsSanKhaDung(dcNgayDatTruoc.getDate(),(Date) spnGioHenDen_DatSan.getValue(),(Date) spnGioHenTra_DatSan.getValue());
        addTblSanKhaDung_DatTruoc(dsSanKhaDung_DatTruoc);
        boolean khaDung = false;
        for (int row = 0; row < tblCTDatTruoc.getRowCount(); row++) {
            for (SANBONG sanKhaDung : dsSanKhaDung_DatTruoc) {
                if (String.valueOf(tblCTDatTruoc.getValueAt(row, 0)).compareTo(sanKhaDung.getMaSan())==0) {
                    khaDung = true;
                    break;
                }
            }
            if (khaDung) {
                removeSanKhaDung(row);
                khaDung = false;
            }
        }
    }//GEN-LAST:event_spnGioHenDen_DatSanStateChanged

    private void spnGioHenTra_DatSanStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnGioHenTra_DatSanStateChanged

        if (((Date)spnGioHenTra_DatSan.getValue()).before((Date) spnGioHenDen_DatSan.getValue())) {
            spnGioHenTra_DatSan.setValue(spnGioHenDen_DatSan.getValue());
            return;
        }
        try {
            if (((Date) spnGioHenTra_DatSan.getValue()).after(spnHourFormat.parse("23:00 PM"))) {
                spnGioHenTra_DatSan.setValue(spnHourFormat.parse("23:00 PM"));
                return;
            }
        } catch (ParseException ex) {
            System.out.println("Lỗi định dạng ngày giờ: GDNHANVIEN.spnGioHenTra_DatSanStateChanged() 3421");
        }
        dsSanKhaDung_DatTruoc = ioData.getDsSanKhaDung(dcNgayDatTruoc.getDate(),(Date) spnGioHenDen_DatSan.getValue(),(Date) spnGioHenTra_DatSan.getValue());
        addTblSanKhaDung_DatTruoc(dsSanKhaDung_DatTruoc);
        boolean khaDung = false;
        for (int row = 0; row < tblCTDatTruoc.getRowCount(); row++) {
            for (SANBONG sanKhaDung : dsSanKhaDung_DatTruoc) {
                if (String.valueOf(tblCTDatTruoc.getValueAt(row, 0)).compareTo(sanKhaDung.getMaSan())==0) {
                    khaDung = true;
                    break;
                }
            }
            if (khaDung) {
                removeSanKhaDung(row);
                khaDung = false;
            }
        }
        /*
        for (int row = 0; row < tblCTDatTruoc.getRowCount(); row++) {
            for (SANBONG sanKhaDung : dsSanKhaDung_DatTruoc) {
                if (String.valueOf(tblCTDatTruoc.getValueAt(row, 0)).compareTo(sanKhaDung.getMaSan())==0) {
                    khaDung = true;
                    break;
                }
            }
            if (!khaDung) {
                String sanBo = String.valueOf(tblCTDatTruoc.getValueAt(row, 0));
                dftblCtDatTruoc.removeRow(row);
                row--;
                JOptionPane.showMessageDialog(rootPane, "Sân "+sanBo+" đã có người thuê lúc "+formatGio.format((Date) spnGioHenDen_DatSan.getValue())
                    +" nên không còn khả dụng. Chi tiết thuê sân này đã bị xóa");
            }else{
                removeSanKhaDung(row);
                khaDung = false;
            }
        }*/
    }//GEN-LAST:event_spnGioHenTra_DatSanStateChanged

    private void btnXacNhanDat_DatTruocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanDat_DatTruocMouseClicked
        int row = tblCTDatTruoc.getRowCount();
        if (row==0) {
            return;
        }
        boolean check =true;
        if (cbxCMND_DatTruoc.getSelectedIndex()==-1) {
            check=false;
            lblWarnCMND_DatTruoc.setVisible(true);
        }
        if (txtHoTen_DatTruoc.getText().length()==0) {
            check=false;
            lblWarnHoTen_DatTruoc.setVisible(true);
        }
        if (txtSDT_DatTruoc.getText().length()==0) {
            check=false;
            lblWarnSDT_DatTruoc.setVisible(true);
        }
        if (!check) {
            return;
        }
        for (int i = 0; i < row; i++) {
            String maSan = String.valueOf(tblCTDatTruoc.getValueAt(i, 0));
            Date gioNhan = new Date();
            Date gioTra = new Date();
            try {
                gioNhan = formatGio.parse(String.valueOf(tblCTDatTruoc.getValueAt(i, 3)));
                gioTra = formatGio.parse(String.valueOf(tblCTDatTruoc.getValueAt(i, 4)));
            } catch (ParseException ex) {
                System.out.println("ERROR: GDNHANVIEN.btnXacNhanDat_DatSanMouseClicked() 3544");
                return;
            }

            if (!ioData.isKhaDung(dcNgayDatTruoc.getDate(), gioNhan, gioTra, maSan)) {
                dftblCtDatTruoc.removeRow(i);
                JOptionPane.showMessageDialog(rootPane, "Rất tiếc! Đã có người đặt trước sân " + maSan + " từ " + formatGio.format(gioNhan)
                    + " đến " + formatGio.format(gioTra) + "\n. Chi tiết thuê sân này đã bị xóa đi");
                dsSanKhaDung_DatTruoc = ioData.getDsSanKhaDung(dcNgayDatTruoc.getDate(), (Date) spnGioHenDen_DatSan.getValue(), (Date) spnGioHenTra_DatSan.getValue());
                addTblSanKhaDung_DatTruoc(dsSanKhaDung_DatTruoc);
                return;
            }
            if (isToday()) {
                if (!ioData.isKhaDungDatNgay(gioNhan, gioTra, maSan)) {
                    dftblCtDatTruoc.removeRow(i);
                    JOptionPane.showMessageDialog(rootPane, "Rất tiếc! Sân " + maSan + " đã có người thuê hoặc đang bảo trì");
                    dsSanKhaDung_DatTruoc = ioData.getDsSanKhaDung(dcNgayDatTruoc.getDate(), (Date) spnGioHenDen_DatSan.getValue(), (Date) spnGioHenTra_DatSan.getValue());
                    addTblSanKhaDung_DatTruoc(dsSanKhaDung_DatTruoc);
                    return;
                }
            }
        }

        PHIEUDATTRUOC newPdt = new PHIEUDATTRUOC();
        String cmnd = String.valueOf(cbxCMND_DatTruoc.getSelectedItem());
        KHACH newKhach = new KHACH(cmnd, txtHoTen_DatTruoc.getText(), txtSDT_DatTruoc.getText());
        if (!ioData.khachIsExist(cmnd)) {
            ioData.insertKhachThue(newKhach);
            dsKhachHang = ioData.getDsKhachHang();
            insertCbxCMND(dsKhachHang);
        }
        newPdt.setkDatTruoc(newKhach);
        newPdt.setNgayDat(dcNgayDatTruoc.getDate());
        newPdt.setMaDatTruoc(ioData.sinhMaDatTruoc());
        for (int i = 0; i < row; i++) {
            CT_DATTRUOC newCtdt = new CT_DATTRUOC();
            newCtdt.setSanDatTruoc(new SANBONG(String.valueOf(tblCTDatTruoc.getValueAt(i, 0)), LOAISAN.SAN5, TRANGTHAI.TRONG));
            try {
                newCtdt.setTgDuKienDen(formatGio.parse(String.valueOf(tblCTDatTruoc.getValueAt(i, 3))));
                newCtdt.setTgDuKienTra(formatGio.parse(String.valueOf(tblCTDatTruoc.getValueAt(i, 4))));
            } catch (ParseException ex) {
                System.out.println("Lỗi giờ đặt trước: GDNHANVIEN.btnXacNhanDat_DatSanMouseClicked() 3599");
            }
            newPdt.getDsCtDatTruoc().add(newCtdt);
        }

        ioData.insertPhieuDatTruoc(newPdt);
        for(CT_DATTRUOC ctDt : newPdt.getDsCtDatTruoc()){
            ioData.insertCT_DatTruoc(ctDt, newPdt.getMaDatTruoc());
        }
        JOptionPane.showMessageDialog(rootPane, "ĐẶT TRƯỚC THÀNH CÔNG.\n        Mã phiếu: "+newPdt.getMaDatTruoc());
        dftblCtDatTruoc.setRowCount(0);       
    }//GEN-LAST:event_btnXacNhanDat_DatTruocMouseClicked

    private void bttBoDvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttBoDvMouseClicked
        int[] rows = tblCtDichVuThue.getSelectedRows();
        dftblCtDichVuThue.removeRow(rows[0]);
    }//GEN-LAST:event_bttBoDvMouseClicked

    private void bttThemDvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttThemDvActionPerformed
        int[] rows = tblDoAn.getSelectedRows();
        int[] cols = tblDoAn.getSelectedColumns();
        for (int row : rows) {
            dftblCtDichVuThue.addRow(new Object[]{
                tblDoAn.getValueAt(row, 0),
                tblDoAn.getValueAt(row, 1),
                1,
                tblDoAn.getValueAt(row, 2),});
        }

        rows = tblDoUong.getSelectedRows();
        cols = tblDoUong.getSelectedColumns();
        for (int row : rows) {
            dftblCtDichVuThue.addRow(new Object[]{
                tblDoUong.getValueAt(row, 0),
                tblDoUong.getValueAt(row, 1),
                1,
                tblDoUong.getValueAt(row, 2),});
        }

        rows = tblDungCu.getSelectedRows();
        cols = tblDungCu.getSelectedColumns();
        for (int row : rows) {
            dftblCtDichVuThue.addRow(new Object[]{
                tblDungCu.getValueAt(row, 0),
                tblDungCu.getValueAt(row, 1),
                1,
                tblDungCu.getValueAt(row, 2),});
        }

        tblDoAn.clearSelection();
        tblDoUong.clearSelection();
        tblDungCu.clearSelection();
    }//GEN-LAST:event_bttThemDvActionPerformed

    private void tblCtThue_HoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCtThue_HoaDonMouseClicked
        int row = tblCtThue_HoaDon.getSelectedRow();
        dftblDichVu_HoaDon.setRowCount(0);
        addtblCtDichVuThue(phieuThue.getCtThue().get(row).getDsDvSuDung());
    }//GEN-LAST:event_tblCtThue_HoaDonMouseClicked

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(fileHoaDon);
        } catch (IOException ex) {
            System.out.println("Không thể mở file hóa đơn");;
        }
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void lblLichSuThue_TTPTMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLichSuThue_TTPTMouseMoved
        lblLichSuThue_TTPT.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lblLichSuThue_TTPTMouseMoved

    private void lblLichSuThue_TTPTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLichSuThue_TTPTMouseClicked
        pnThongTinCaNhan.setVisible(false);
        pnLichSuThue.setVisible(true);
        pnSoDoSanBong.setVisible(false);
        pnDatDichVu.setVisible(false);
	pnDatTruocSan.setVisible(false);
        pnDatSanNgay.setVisible(false);
        pnQuanLyPhieuThue.setVisible(false);
        pnlAllPhieuThue_ThanhToan.setVisible(true);
        pnlHoaDon.setVisible(false);
        addTblAllPhieuThue_ThanhToan();
    }//GEN-LAST:event_lblLichSuThue_TTPTMouseClicked

    private void lblLichSuThue_TTPTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLichSuThue_TTPTMouseExited
        lblLichSuThue_TTPT.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lblLichSuThue_TTPTMouseExited

    private void lblLichSuThue_DSNMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLichSuThue_DSNMouseMoved
        lblLichSuThue_DSN.setForeground(new Color(0, 0, 102));
    }//GEN-LAST:event_lblLichSuThue_DSNMouseMoved

    private void lblLichSuThue_DSNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLichSuThue_DSNMouseClicked
        pnThongTinCaNhan.setVisible(false);
        pnLichSuThue.setVisible(true);
        pnSoDoSanBong.setVisible(false);
        pnDatDichVu.setVisible(false);
	pnDatTruocSan.setVisible(false);
        pnDatSanNgay.setVisible(false);
        pnQuanLyPhieuThue.setVisible(false);
        pnlAllPhieuThue_ThanhToan.setVisible(true);
        pnlHoaDon.setVisible(false);
        addTblAllPhieuThue_ThanhToan();
    }//GEN-LAST:event_lblLichSuThue_DSNMouseClicked

    private void lblLichSuThue_DSNMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLichSuThue_DSNMouseExited
       lblLichSuThue_DSN.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lblLichSuThue_DSNMouseExited

    private void lbDatSanNgay_LSTMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatSanNgay_LSTMouseMoved
       lbDatSanNgay_LST.setForeground(new Color(0, 0, 102));
    }//GEN-LAST:event_lbDatSanNgay_LSTMouseMoved

    private void lbDatSanNgay_LSTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatSanNgay_LSTMouseClicked
        pnThongTinCaNhan.setVisible(false);
        pnLichSuThue.setVisible(false);
        pnSoDoSanBong.setVisible(false);
        pnDatDichVu.setVisible(false);
	pnDatTruocSan.setVisible(false);
        pnDatSanNgay.setVisible(true);
        pnQuanLyPhieuThue.setVisible(false);
        refresh();
        dsKhachHang = ioData.getDsKhachHang();
        insertCbxCMND(dsKhachHang);
    }//GEN-LAST:event_lbDatSanNgay_LSTMouseClicked

    private void lbDatSanNgay_LSTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatSanNgay_LSTMouseExited
        lbDatSanNgay_LST.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbDatSanNgay_LSTMouseExited

    private void lbThongTinPhieuThue_LSTMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinPhieuThue_LSTMouseMoved
        lbThongTinPhieuThue_LST.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbThongTinPhieuThue_LSTMouseMoved

    private void lbThongTinPhieuThue_LSTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinPhieuThue_LSTMouseClicked
        pnThongTinCaNhan.setVisible(false);
        pnLichSuThue.setVisible(false);
        pnSoDoSanBong.setVisible(false);
        pnDatDichVu.setVisible(false);
	pnDatTruocSan.setVisible(false);
        pnDatSanNgay.setVisible(false);
        pnQuanLyPhieuThue.setVisible(true);
        refreshTblDsPhieuDt();
        refreshTblDsSanDangThue();
    }//GEN-LAST:event_lbThongTinPhieuThue_LSTMouseClicked

    private void lbThongTinPhieuThue_LSTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinPhieuThue_LSTMouseExited
       lbThongTinPhieuThue_LST.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbThongTinPhieuThue_LSTMouseExited

    private void btnXemCtHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemCtHoaDonActionPerformed
        // TODO add your handling code here:
        int row = tblAllPhieuThue_HoaDon.getSelectedRow();
        if (row >= 0) {
            pnlAllPhieuThue_ThanhToan.setVisible(false);
            pnDatDichVu.setVisible(false);
            pnDatTruocSan.setVisible(false);
            pnlHoaDon.setVisible(true);
            pnSoDoSanBong.setVisible(false);

            dftblCtThue_HoaDon.setRowCount(0);
            dftblDichVu_HoaDon.setRowCount(0);
            for (PHIEUTHUE pt : dsPhieuThue_ThanhToan) {
                if (pt.getMaHoaDon().compareTo(String.valueOf(tblAllPhieuThue_HoaDon.getValueAt(row, 0)))==0) {
                    phieuThue = pt;
                    break;
                }
            }
            lblHoTen_HoaDon.setText(phieuThue.getKhach().getHoTen());
            lblMaHoaDon_HoaDon.setText(phieuThue.getMaHoaDon());
            lblTenNVPV_HoaDon.setText(phieuThue.getNvGhiPhieu().getTenNV());
            lblNgayThue_HoaDon.setText(formatNgay.format(phieuThue.getNgayGhiPhieu()));
            lblTongTien_HoaDon.setText(String.valueOf(phieuThue.getTongPhaiTra()));
            phieuThue.setCtThue(ioData.getCtThue_MHD(phieuThue.getMaHoaDon()));
            addtblDsSanDaThue(phieuThue.getCtThue());
            addtblCtDichVuThue(phieuThue.getCtThue().get(0).getDsDvSuDung());
            tblCtThue_HoaDon.setRowSelectionInterval(0, 0);
            fileHoaDon = HOADONWORD.inHoaDon(phieuThue);
        }
    }//GEN-LAST:event_btnXemCtHoaDonActionPerformed

    private void lblBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackMouseClicked
        pnlAllPhieuThue_ThanhToan.setVisible(true);
        pnDatDichVu.setVisible(false);
        pnDatTruocSan.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnSoDoSanBong.setVisible(false);
    }//GEN-LAST:event_lblBackMouseClicked

    private void lblBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackMouseEntered
        pnlBack_HoaDonMouseEntered(evt);
    }//GEN-LAST:event_lblBackMouseEntered

    private void pnlHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHoaDonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlHoaDonMouseEntered

    private void pnlBack_HoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBack_HoaDonMouseEntered
        pnlBack_HoaDon.setBackground(new Color(204,204,204));
    }//GEN-LAST:event_pnlBack_HoaDonMouseEntered

    private void pnlBack_HoaDonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBack_HoaDonMouseExited
       pnlBack_HoaDon.setBackground(new Color(240,240,240));
    }//GEN-LAST:event_pnlBack_HoaDonMouseExited

    private void pnlBack_HoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBack_HoaDonMouseClicked
        lblBackMouseClicked(evt);
    }//GEN-LAST:event_pnlBack_HoaDonMouseClicked

    private void btnXacNhanSuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanSuaMouseClicked
        String hoTenUpdate = txtHoTenUpdate.getText();
        String sdtUpdate = txtSdtUpdate.getText();
        boolean check = true;
        if (hoTenUpdate.length() == 0) {
            lblWarnHoTen.setVisible(true);
            check =false;
        }
        if (sdtUpdate.matches("[0-9]{10,11}")==false) {
            lblWarnSdt.setVisible(true);
            check =false;
        } 
        if(check){
            ioData.updateTtNhanVien(nhanVien.getMaNV(), hoTenUpdate, sdtUpdate);
            lblWarnHoTen.setVisible(false);
            lblWarnSdt.setVisible(false);
            ghiTtNhanVien(nhanVien.getMaNV());
            txtHoTenUpdate.setText("");
            txtSdtUpdate.setText("");
            JOptionPane.showMessageDialog(rootPane, "Đã cập nhật thông tin cá nhân");
        }
    }//GEN-LAST:event_btnXacNhanSuaMouseClicked

    private void btnXacNhanDoiMkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanDoiMkMouseClicked
        String mkCu = txtMkCu.getText();
        String mkMoi = txtMkMoi.getText();
        String mkXacNhan = txtMkXacNhan.getText();
        nhanVien.setMatKhau(nhanVien.getMatKhau().trim());

        if (mkCu.compareTo(nhanVien.getMatKhau())==0) {
            lblWarnSaiMk.setVisible(false);
            if (mkMoi.compareTo(mkXacNhan)==0) {
                lblWarnMkKhongTrung.setVisible(false);
                ioData.updateMkTaiKhoan(nhanVien.getMaNV(), mkMoi);
                ghiTtNhanVien(nhanVien.getMaNV());
                txtMkCu.setText("");
                txtMkMoi.setText("");
                txtMkXacNhan.setText("");
                JOptionPane.showMessageDialog(rootPane,"Đã đổi mật khẩu");
            }else{
                lblWarnMkKhongTrung.setVisible(true);
            }
        }else{
            lblWarnSaiMk.setVisible(true);
        }
    }//GEN-LAST:event_btnXacNhanDoiMkMouseClicked

    private void cbxChonLoaiSanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxChonLoaiSanActionPerformed
        dftblSanKhaDung_ThueNgay.setRowCount(0);
        addTblSanBongKhaDungThueNgay(dsSanBongKhaDung_ThueNgay);
    }//GEN-LAST:event_cbxChonLoaiSanActionPerformed

    private void txtTimKiemLichSuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemLichSuActionPerformed

    }//GEN-LAST:event_txtTimKiemLichSuActionPerformed

    private void txtTimKiemLichSuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemLichSuKeyTyped
        char type = evt.getKeyChar();
        int n = (int) type;
        dftblAllPhieuThue_ThanhToan.setRowCount(0);
        String find = txtTimKiemLichSu.getText();
        if ((n>64&&n<91)||(n>96&&n<123)||(n>47&&n<58)) {
            find = find.concat(String.valueOf(type));   
        }
        if (find.length() <= 1) {
            addTblAllPhieuThue_ThanhToan();
            return;
        }
        for (PHIEUTHUE phieuthue : dsPhieuThue_ThanhToan) {
            try {
                if (phieuthue.getMaHoaDon().substring(0, find.length()).compareToIgnoreCase(find) == 0) {;
                    dftblAllPhieuThue_ThanhToan.addRow(new Object[]{
                        phieuthue.getMaHoaDon(),
                        phieuthue.getKhach().getHoTen(),
                        phieuthue.getNgayGhiPhieu(),
                        phieuthue.getTongPhaiTra()
                    });
                }
            } catch (Exception e){
                
            } 
            }
    }//GEN-LAST:event_txtTimKiemLichSuKeyTyped

    private void txtTimKiemLichSuPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTimKiemLichSuPropertyChange

    }//GEN-LAST:event_txtTimKiemLichSuPropertyChange

    private void txtHoTenUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHoTenUpdateMouseClicked
        lblWarnHoTen.setVisible(false);
    }//GEN-LAST:event_txtHoTenUpdateMouseClicked

    private void txtSdtUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSdtUpdateMouseClicked
        lblWarnSdt.setVisible(false);
    }//GEN-LAST:event_txtSdtUpdateMouseClicked

    private void txtMkCuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMkCuMouseClicked
        txtMkCu.setVisible(false);
    }//GEN-LAST:event_txtMkCuMouseClicked

    private void txtMkXacNhanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMkXacNhanMouseClicked
        txtMkXacNhan.setVisible(false);
    }//GEN-LAST:event_txtMkXacNhanMouseClicked

    private void btnXacNhanDatDvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanDatDvMouseClicked
        int rows = tblCtDichVuThue.getRowCount();
        for (int i = 0; i < rows; i++) {
            String tendv = String.valueOf(tblCtDichVuThue.getValueAt(i, 0));
            DICHVU newDv = ioData.getDichvu(tendv);
            if (newDv == null) {
                JOptionPane.showMessageDialog(rootPane, "Dịch vụ " + tendv + " không thể sửa dụng nữa do đã bị xóa đi.\n Xin lỗi quý khách vì sự bất tiện này");
                dsDv = ioData.getDsDichVu();
                insertTblDsDv();
                dftblCtDichVuThue.setRowCount(0);
                return;
            }
        }
        for (int i = 0; i < rows; i++) {
            DICHVU newDv = ioData.getDichvu(String.valueOf(tblCtDichVuThue.getValueAt(i, 0)));

            String maHoaDon = lblMHDdatDv.getText();
            String maSan = lblSanDatDv.getText();
            int sl = Integer.parseInt(String.valueOf(tblCtDichVuThue.getValueAt(i, 2)));
            int tienDv = Integer.parseInt(String.valueOf(tblCtDichVuThue.getValueAt(i, 3)));
            SD_DICHVU newSdDv = new SD_DICHVU(maHoaDon, maSan, newDv, sl, tienDv);
            ioData.insertSD_DichVu(newSdDv);
        }
        JOptionPane.showMessageDialog(rootPane, "ĐÃ ĐẶT DỊCH VỤ!");
        dftblCtDichVuThue.setRowCount(0);
        btnThongTinPhieuThueMouseClicked(evt);
    }//GEN-LAST:event_btnXacNhanDatDvMouseClicked

    private void txtHoTen_DatTruocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHoTen_DatTruocMouseClicked
        lblWarnHoTen_DatTruoc.setVisible(false);
    }//GEN-LAST:event_txtHoTen_DatTruocMouseClicked

    private void txtSDT_DatTruocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSDT_DatTruocMouseClicked
        lblWarnSDT_DatTruoc.setVisible(false);
    }//GEN-LAST:event_txtSDT_DatTruocMouseClicked

    private void lblDatTruocSan_TTDTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDatTruocSan_TTDTMouseClicked
        btnDatTruocSanMouseClicked(evt);
    }//GEN-LAST:event_lblDatTruocSan_TTDTMouseClicked

    private void lblThongTinDatTruoc_DTSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongTinDatTruoc_DTSMouseClicked
        pnDatTruocSan.setVisible(false);
        pnThongTinDatTruoc.setVisible(true);
        refreshThongTinDatTruoc();
    }//GEN-LAST:event_lblThongTinDatTruoc_DTSMouseClicked

    private void tblDsPhieuDatTruoc_TTDTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDsPhieuDatTruoc_TTDTMouseClicked
        dftblCTDatTruoc_TTDT.setRowCount(0);
        int row = tblDsPhieuDatTruoc_TTDT.getSelectedRow();
        PHIEUDATTRUOC thisPdt = allPhieuDTs.get(row);
        SimpleDateFormat gio = new SimpleDateFormat("HH:mm:ss");
        for (CT_DATTRUOC ctdt : thisPdt.getDsCtDatTruoc()) {
            dftblCTDatTruoc_TTDT.addRow(new Object[]{
                ctdt.getSanDatTruoc().getMaSan(),
                ctdt.getSanDatTruoc().getLoaisan(),
                gio.format(ctdt.getTgDuKienDen()),
                gio.format(ctdt.getTgDuKienTra())
            });
        }
    }//GEN-LAST:event_tblDsPhieuDatTruoc_TTDTMouseClicked

    private void lblThongTinDatTruoc_DTSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongTinDatTruoc_DTSMouseEntered
        lblThongTinDatTruoc_DTS.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lblThongTinDatTruoc_DTSMouseEntered

    private void lblThongTinDatTruoc_DTSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongTinDatTruoc_DTSMouseExited
        lblThongTinDatTruoc_DTS.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lblThongTinDatTruoc_DTSMouseExited

    private void lblDatTruocSan_TTDTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDatTruocSan_TTDTMouseEntered
       lblDatTruocSan_TTDT.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lblDatTruocSan_TTDTMouseEntered

    private void lblDatTruocSan_TTDTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDatTruocSan_TTDTMouseExited
        lblDatTruocSan_TTDT.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lblDatTruocSan_TTDTMouseExited

    public void refresh()
    {
        dftblCtPhieuThueNgay.setRowCount(0);
        dftblSanKhaDung_ThueNgay.setRowCount(0);
        dsSanBongKhaDung_ThueNgay = ioData.getDsSanKhaDung(clock.getClockDate(), clock.getClockTime(), (Date) spnGioHenTra.getValue());
        addTblSanBongKhaDungThueNgay(dsSanBongKhaDung_ThueNgay);
    }

    public void addTblSanBongKhaDungThueNgay(ArrayList<SANBONG> ds) {
        boolean check = true;
        dftblSanKhaDung_ThueNgay.setRowCount(0);
        for (SANBONG san : ds) {
            if (san.getLoaisan() == cbxChonLoaiSan.getSelectedItem() || cbxChonLoaiSan.getSelectedItem() == LOAISAN.TATCA) {
                for (int row = 0; row < tblCtPhieuThueNgay.getRowCount(); row++) {
                    String maSan = String.valueOf(tblCtPhieuThueNgay.getValueAt(row, 0));
                    if (san.getMaSan().compareTo(maSan) == 0) {
                        check = false;
                        break;
                    }
                }
                if (ioData.isKhaDungDatNgay(new Date(), (Date) spnGioHenTra.getValue(), san.getMaSan())&&check == true) {
                    dftblSanKhaDung_ThueNgay.addRow(new Object[]{
                        san.getMaSan(), san.getLoaisan(), san.getGiaTheoGio()
                    });
                }
                check = true;
            }
        }
    }

    public void addTblSanKhaDung_DatTruoc(ArrayList<SANBONG> ds) {

        dftblSan5.setRowCount(0);
        dftblSan7.setRowCount(0);
        dftblSan11.setRowCount(0);
        if (isToday()) {
            xetKhaDungHomNay();
        }

        for (SANBONG san : ds) {
            if (san.getLoaisan().getMaLoaiSan() == 1) {
                dftblSan5.addRow(new Object[]{
                    san.getMaSan(), san.getDonGiaSan()
                });
            } else if (san.getLoaisan().getMaLoaiSan() == 2) {
                dftblSan7.addRow(new Object[]{
                    san.getMaSan(), san.getDonGiaSan()
                });
            } else {
                dftblSan11.addRow(new Object[]{
                    san.getMaSan(), san.getDonGiaSan()
                });
            }
        }
    }
    
    public void insertCbxCMND(ArrayList<KHACH> dsKhachHang) {
        cbxCMND_ThueNgay.removeAllItems();
        cbxCMND_DatTruoc.removeAllItems();
        for (KHACH khach : dsKhachHang) {
            cbxCMND_ThueNgay.addItem(khach.getCMND());
            cbxCMND_DatTruoc.addItem(khach.getCMND());
        }
    }
    
    public void addTblDsSanDangThue() {
        dsCtSanDangThue = ioData.getDsCtSanDangThue();
        for (CT_THUE ctThue : dsCtSanDangThue) {
                dftblDsSanDangThue.addRow(new Object[]{
                    ctThue.getSanBong().getMaSan(),
                    ctThue.getMaHoaDon(),
                    ioData.findKhach(ctThue.getMaHoaDon()),
                    formatGio.format(ctThue.getGioThue()),
                    formatGio.format(ctThue.getTgDuKienTra()),
                    ctThue.getKhungGio()
                });
        }
    }

    public void addTblDsPhieuDT() {
        dsPhieuDTs = ioData.getDsPhieuDatTruocHomNay();
        for (PHIEUDATTRUOC phieuDT : dsPhieuDTs) {
            for (CT_DATTRUOC Ctdt : phieuDT.getDsCtDatTruoc()) {
                dftblDsPhieuDatTruoc.addRow(new Object[]{
                    phieuDT.getMaDatTruoc(),
                    Ctdt.getSanDatTruoc().getMaSan(),
                    ioData.findKhachDatTruoc(phieuDT.getMaDatTruoc()),
                    Ctdt.getTgDuKienDen(),
                    Ctdt.getTgDuKienTra()
                });
            }
        }
    }

    public String dsSanDttoString(PHIEUDATTRUOC phieuDT)
    {
        String dsSanDtStr = "";
        for (CT_DATTRUOC ctDatTruoc : phieuDT.getDsCtDatTruoc()) {
            dsSanDtStr = dsSanDtStr + ctDatTruoc.getSanDatTruoc().getMaSan()+", ";
        }
        return  dsSanDtStr;
    }
    
    public void refreshTblDsSanDangThue(){
        dftblDsSanDangThue.setRowCount(0);
        addTblDsSanDangThue();
    }
    
    public void refreshTblDsPhieuDt(){
        dftblDsPhieuDatTruoc.setRowCount(0);
        addTblDsPhieuDT();
    }
    
    public boolean isNumeric(String sl){
        String form = "\\d+";
        return sl.matches(form);
    }
    
    public void insertTblDsDv() {
        dftblDoAn.setRowCount(0);
        dftblDoUong.setRowCount(0);
        dftblDungCu.setRowCount(0);
        for (DICHVU dichvu : dsDv) {
            if (dichvu.getDanhmuc().getMaDanhMuc() == 1) {
                dftblDoAn.addRow(new Object[]{
                    dichvu.getTenDichVu(),
                    dichvu.getDonVi(),
                    dichvu.getDonGia()
                });
            } else if (dichvu.getDanhmuc().getMaDanhMuc() == 2) {
                dftblDoUong.addRow(new Object[]{
                    dichvu.getTenDichVu(),
                    dichvu.getDonVi(),
                    dichvu.getDonGia()
                });
            } else {
                dftblDungCu.addRow(new Object[]{
                    dichvu.getTenDichVu(),
                    dichvu.getDonVi(),
                    dichvu.getDonGia()
                });
            }
        }
    }
    
    public DICHVU timDichVu(String tenDv){
        for (DICHVU dichvu : dsDv) {
            if (tenDv.compareTo(dichvu.getTenDichVu())==0) {
                return dichvu;
            }
        }
        return null;
    }

    public SANBONG timSan(ArrayList<SANBONG> dsSanBong, String tenSan) {
        for (SANBONG sb : dsSanBong) {
            if (sb.getMaSan().compareTo(tenSan) == 0) {
                return sb;
            }
        }
        return null;
    }

    public void removeSanKhaDung(int row){
        if (String.valueOf(tblCTDatTruoc.getValueAt(row, 1)).compareTo("Sân 5")==0) {
            for (int i = 0; i < tblSan5.getRowCount(); i++) {
                if (String.valueOf(tblSan5.getValueAt(i, 0)).compareTo(String.valueOf(tblCTDatTruoc.getValueAt(row, 0)))==0) {
                    dftblSan5.removeRow(i);
                    break;
                }
            }
        }else if (String.valueOf(tblCTDatTruoc.getValueAt(row, 1)).compareTo("Sân 7")==0) {
            for (int i = 0; i < tblSan7.getRowCount(); i++) {
                if (String.valueOf(tblSan7.getValueAt(i, 0)).compareTo(String.valueOf(tblCTDatTruoc.getValueAt(row, 0)))==0) {
                    dftblSan7.removeRow(i);
                    break;
                }
            }
        }else {
            for (int i = 0; i < tblSan11.getRowCount(); i++) {
                if (String.valueOf(tblSan11.getValueAt(i, 0)).compareTo(String.valueOf(tblCTDatTruoc.getValueAt(row, 0)))==0) {
                    dftblSan11.removeRow(i);
                    break;
                }
            }
        }
    }

    public ArrayList<PHIEUDATTRUOC> getDsPhieuDTs() {
        dsPhieuDTs = ioData.getDsPhieuDatTruocHomNay();
        return dsPhieuDTs;
    }
    
    public void thongBaoXoaCtDt(CT_DATTRUOC biXoa){
        JOptionPane.showMessageDialog(rootPane, "Đặt trước sân " + biXoa.getSanDatTruoc().getMaSan() + " lúc "+ formatGio.format(biXoa.getTgDuKienDen()) + " đã trễ quá 15 phút \n"
                + "nên không được phép nhận sân nữa. Chi tiết thuê này đã bị loại bỏ");
    }

    public void xetKhaDungHomNay() {
        for (int i = 0; i < dsSanKhaDung_DatTruoc.size(); i++) {
            String tgDuKienDen = formatGio.format((Date) spnGioHenDen_DatSan.getValue());
            String tgDuKienTra = formatGio.format((Date) spnGioHenTra_DatSan.getValue());
            try {
                if (!ioData.isKhaDungDatNgay(formatGio.parse(tgDuKienDen), formatGio.parse(tgDuKienTra), dsSanKhaDung_DatTruoc.get(i).getMaSan())) {
                    dsSanKhaDung_DatTruoc.remove(dsSanKhaDung_DatTruoc.get(i));
                    i--;
                }
            } catch (ParseException ex) {
                Logger.getLogger(GDNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isToday(){
        
        String today = dcDateFormat.format(new Date());
        String dcDate = dcDateFormat.format(dcNgayDatTruoc.getDate());
        if (dcDate.compareTo(today) == 0) {
            return true;
        }
        return false;
    }
    
    public void addTblAllPhieuThue_ThanhToan(){
        dsPhieuThue_ThanhToan = ioData.getAllPhieuthues_ThanhToan();
        dftblAllPhieuThue_ThanhToan.setRowCount(0);
        for(PHIEUTHUE phieuThue : dsPhieuThue_ThanhToan){
            dftblAllPhieuThue_ThanhToan.addRow(new Object[]{
                phieuThue.getMaHoaDon(),
                phieuThue.getKhach().getHoTen(),
                phieuThue.getNgayGhiPhieu(),
                phieuThue.getTongPhaiTra()
            });
        }
    }
    
    void addtblDsSanDaThue(ArrayList<CT_THUE> dsCtThue)
    {
        for (CT_THUE ctThue : dsCtThue) {
            dftblCtThue_HoaDon.addRow(new Object[]{
                ctThue.getSanBong().getMaSan(),
                ctThue.getSanBong().getLoaisan(),
                ctThue.getSanBong().getGiaTheoGio(),
                ctThue.getGioThue(),
                ctThue.getGioTra(),
                ctThue.getKhungGio(),
                ctThue.getSoGioThue(),
                ctThue.getThanhTien()
            });
        }
    }
    
    void addtblCtDichVuThue(ArrayList<SD_DICHVU> dsDv){
        for (SD_DICHVU sd_dichvu : dsDv) {
            dftblDichVu_HoaDon.addRow(new Object[]{
                sd_dichvu.getDichVu().getTenDichVu(),
                sd_dichvu.getDichVu().getDonGia(),
                sd_dichvu.getSl(),
                sd_dichvu.getTienDv()
            });
        }
    }
    
    void ghiTtNhanVien(String maNv){
        nhanVien = ioData.getNhanVienLogIn(maNv);
        System.out.print("Cap nhat thong tin Nv...");
        lblMaNv_TTCN.setText(nhanVien.getMaNV());
        lblCmndNV.setText(nhanVien.getCMND());
        lblHotenNV.setText(nhanVien.getTenNV());
        lblSdtNV.setText(nhanVien.getSdt());
        lblHoTenNv_Menu.setText(nhanVien.getTenNV());
        System.out.println("done");
    }
    
    public void refreshSoDoSanBong(){
        dsAllSanBongs = ioData.getAlldsSanBong();
        for (SANBONG sanBong : dsAllSanBongs) {
            if (sanBong.getMaSan().compareTo("5A")==0) {
                if (sanBong.getTrangthai() == TRANGTHAI.TRONG) 
                {
                    pnl5A.setBackground(sanTrong);
                    lblTt5A.setText("TRỐNG");
                    lblTt5A.setForeground(new Color(0,188,37));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.DANGSUDUNG)
                { 
                    pnl5A.setBackground(sanDangThue);
                    lblTt5A.setText("ĐANG SỬ DỤNG");
                    lblTt5A.setForeground(new Color(255,51,51));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.BAOTRI)
                {
                    pnl5A.setBackground(sanBaoTri);
                    lblTt5A.setText("BẢO TRÌ");
                    lblTt5A.setForeground(new Color(241,208,16));
                }
            }
            if (sanBong.getMaSan().compareTo("5B")==0) {
                if (sanBong.getTrangthai() == TRANGTHAI.TRONG)
                {
                    pnl5B.setBackground(sanTrong);
                    lblTt5B.setText("TRỐNG");
                    lblTt5B.setForeground(new Color(0,188,37));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.DANGSUDUNG)
                {
                    pnl5B.setBackground(sanDangThue);
                    lblTt5B.setText("ĐANG SỬ DỤNG");
                    lblTt5B.setForeground(new Color(255,51,51));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.BAOTRI)
                {
                    pnl5B.setBackground(sanBaoTri);
                    lblTt5B.setText("BẢO TRÌ");
                    lblTt5B.setForeground(new Color(241,208,16));
                }
            }
            if (sanBong.getMaSan().compareTo("5C")==0) {
                if (sanBong.getTrangthai() == TRANGTHAI.TRONG) 
                {
                    pnl5C.setBackground(sanTrong);
                    lblTt5C.setText("TRỐNG");
                    lblTt5C.setForeground(new Color(0,188,37));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.DANGSUDUNG)
                {
                    pnl5C.setBackground(sanDangThue);
                    lblTt5C.setText("ĐANG SỬ DỤNG");
                    lblTt5C.setForeground(new Color(255,51,51));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.BAOTRI)
                {
                    pnl5C.setBackground(sanBaoTri);
                    lblTt5C.setText("BẢO TRÌ");
                    lblTt5C.setForeground(new Color(241,208,16));
                }
            }
            if (sanBong.getMaSan().compareTo("7A")==0) {
                if (sanBong.getTrangthai() == TRANGTHAI.TRONG) 
                {
                    pnl7A.setBackground(sanTrong);
                    lblTt7A.setText("TRỐNG");
                    lblTt7A.setForeground(new Color(0,188,37));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.DANGSUDUNG)
                {
                    pnl7A.setBackground(sanDangThue);
                    lblTt7A.setText("ĐANG SỬ DỤNG");
                    lblTt7A.setForeground(new Color(255,51,51));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.BAOTRI)
                {
                    pnl7A.setBackground(sanBaoTri);
                    lblTt7A.setText("BẢO TRÌ");
                    lblTt7A.setForeground(new Color(241,208,16));
                }
            }
            if (sanBong.getMaSan().compareTo("7B")==0) {
                if (sanBong.getTrangthai() == TRANGTHAI.TRONG)
                {
                    pnl7B.setBackground(sanTrong);
                    lblTt7B.setText("TRỐNG");
                    lblTt7B.setForeground(new Color(0,188,37));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.DANGSUDUNG)
                {
                    pnl7B.setBackground(sanDangThue);
                    lblTt7B.setText("ĐANG SỬ DỤNG");
                    lblTt7B.setForeground(new Color(255,51,51));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.BAOTRI)
                {
                    pnl7B.setBackground(sanBaoTri);
                    lblTt7B.setText("BẢO TRÌ");
                    lblTt7B.setForeground(new Color(241,208,16));
                }
            }
            if (sanBong.getMaSan().compareTo("11A")==0) {
                if (sanBong.getTrangthai() == TRANGTHAI.TRONG)
                { 
                    pnl11A.setBackground(sanTrong);
                    lblTt11A.setText("TRỐNG");
                    lblTt11A.setForeground(new Color(0,188,37));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.DANGSUDUNG)
                {
                    pnl11A.setBackground(sanDangThue);
                    lblTt11A.setText("ĐANG SỬ DỤNG");
                    lblTt11A.setForeground(new Color(255,51,51));
                }
                if (sanBong.getTrangthai() == TRANGTHAI.BAOTRI)
                {
                    pnl11A.setBackground(sanBaoTri);
                    lblTt11A.setText("BẢO TRÌ");
                    lblTt11A.setForeground(new Color(241,208,16));
                }
            }
        }
    }
    
    public void refreshThongTinDatTruoc(){
        dftblDsPhieuDatTruoc_TTDT.setRowCount(0);
        dftblCTDatTruoc_TTDT.setRowCount(0);
        allPhieuDTs = ioData.getAllPhieuDatTruoc();
        if (allPhieuDTs==null) {
            return;
        }
        for (PHIEUDATTRUOC phieudt : allPhieuDTs) {
            dftblDsPhieuDatTruoc_TTDT.addRow(new Object[]{
                phieudt.getMaDatTruoc(),
                ioData.findKhachDatTruoc(phieudt.getMaDatTruoc()),
                dcDateFormat.format(phieudt.getNgayDat())
            });
        }
        tblDsPhieuDatTruoc_TTDT.setRowSelectionInterval(0, 0);
    }
    
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JPanel btnDatTruocSan;
    private javax.swing.JButton btnDoiMatKhau_TTCN;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnNhanSan;
    private javax.swing.JPanel btnSoDoSanBong;
    private javax.swing.JButton btnSuaThongTin_TTCN;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemDv;
    private javax.swing.JButton btnThemSan_DatSan;
    private javax.swing.JPanel btnThongTinPhieuThue;
    private javax.swing.JButton btnTraSan;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXacNhanDatDv;
    private javax.swing.JButton btnXacNhanDat_DatTruoc;
    private javax.swing.JButton btnXacNhanDoiMk;
    private javax.swing.JButton btnXacNhanSua;
    private javax.swing.JButton btnXemCtHoaDon;
    private javax.swing.JButton bttBoCtDatTruoc;
    private javax.swing.JButton bttBoDv;
    private javax.swing.JButton bttThemDv;
    private javax.swing.JComboBox<String> cbxCMND_DatTruoc;
    private javax.swing.JComboBox<String> cbxCMND_ThueNgay;
    private javax.swing.JComboBox<LOAISAN> cbxChonLoaiSan;
    private com.toedter.calendar.JDateChooser dcNgayDatTruoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel lbAboutUs;
    private javax.swing.JLabel lbDatSanNgay_LST;
    private javax.swing.JLabel lbDatSanNgay_TTPT;
    private javax.swing.JLabel lbGiayPhep;
    private javax.swing.JLabel lbLienHe;
    private javax.swing.JLabel lbQuanLiSanBong;
    private javax.swing.JLabel lbThongTinDatTruoc;
    private javax.swing.JLabel lbThongTinNhanVien_QL7;
    private javax.swing.JLabel lbThongTinPhieuThue;
    private javax.swing.JLabel lbThongTinPhieuThue_DSN;
    private javax.swing.JLabel lbThongTinPhieuThue_LST;
    private javax.swing.JLabel lbThongTinPhieuThue_QLPT;
    private javax.swing.JLabel lbTroGiup;
    private javax.swing.JLabel lblBack;
    private javax.swing.JLabel lblCmndNV;
    private javax.swing.JLabel lblDatSanNgay_DSN;
    private javax.swing.JLabel lblDatSanNgay_DSN1;
    private javax.swing.JLabel lblDatTruocSan_TTDT;
    private javax.swing.JLabel lblGioHienTai;
    private javax.swing.JLabel lblHoTenNv_Menu;
    private javax.swing.JLabel lblHoTen_HoaDon;
    private javax.swing.JLabel lblHotenNV;
    private javax.swing.JLabel lblLichSuThue_DSN;
    private javax.swing.JLabel lblLichSuThue_DSN2;
    private javax.swing.JLabel lblLichSuThue_TTPT;
    private javax.swing.JLabel lblMHDdatDv;
    private javax.swing.JLabel lblMaHoaDon_HoaDon;
    private javax.swing.JLabel lblMaNv_TTCN;
    private javax.swing.JLabel lblNgayHomNay;
    private javax.swing.JLabel lblNgayThue_HoaDon;
    private javax.swing.JLabel lblSanDatDv;
    private javax.swing.JLabel lblSdtNV;
    private javax.swing.JLabel lblTenKhachDatDv;
    private javax.swing.JLabel lblTenNVPV_HoaDon;
    private javax.swing.JLabel lblThongTinDatTruoc_DTS;
    private javax.swing.JLabel lblTongTien_HoaDon;
    private javax.swing.JLabel lblTt11A;
    private javax.swing.JLabel lblTt5A;
    private javax.swing.JLabel lblTt5B;
    private javax.swing.JLabel lblTt5C;
    private javax.swing.JLabel lblTt7A;
    private javax.swing.JLabel lblTt7B;
    private javax.swing.JLabel lblWarnCMND_DatTruoc;
    private javax.swing.JLabel lblWarnHoTen;
    private javax.swing.JLabel lblWarnHoTen_DatTruoc;
    private javax.swing.JLabel lblWarnMkKhongTrung;
    private javax.swing.JLabel lblWarnSDT_DatTruoc;
    private javax.swing.JLabel lblWarnSaiMk;
    private javax.swing.JLabel lblWarnSdt;
    private javax.swing.JPanel pnCTTTCN_TTCN;
    private javax.swing.JPanel pnDatDichVu;
    private javax.swing.JPanel pnDatSanNgay;
    private javax.swing.JPanel pnDatTruocSan;
    private javax.swing.JPanel pnDoiMatKhau_TTCN;
    private javax.swing.JPanel pnLichSuThue;
    private javax.swing.JPanel pnMenu;
    private javax.swing.JPanel pnQuanLyPhieuThue;
    private javax.swing.JPanel pnSoDoSanBong;
    private javax.swing.JPanel pnSuaThongTin_TTCN;
    private javax.swing.JPanel pnThongTinCaNhan;
    private javax.swing.JPanel pnThongTinDatTruoc;
    private javax.swing.JPanel pnTong;
    private javax.swing.JPanel pnTong_TTCN;
    private javax.swing.JPanel pnl11A;
    private javax.swing.JPanel pnl5A;
    private javax.swing.JPanel pnl5B;
    private javax.swing.JPanel pnl5C;
    private javax.swing.JPanel pnl7A;
    private javax.swing.JPanel pnl7B;
    private javax.swing.JPanel pnlAllPhieuThue_ThanhToan;
    private javax.swing.JPanel pnlBack_HoaDon;
    private javax.swing.JPanel pnlHoaDon;
    private javax.swing.JPanel pnlTaiKhoan;
    private javax.swing.JSpinner spnGioHenDen_DatSan;
    private javax.swing.JSpinner spnGioHenTra;
    private javax.swing.JSpinner spnGioHenTra_DatSan;
    private javax.swing.JTable tblAllPhieuThue_HoaDon;
    private javax.swing.JTable tblCTDatTruoc;
    private javax.swing.JTable tblCTDatTruoc_TTDT;
    private javax.swing.JTable tblCtDichVuThue;
    private javax.swing.JTable tblCtPhieuThueNgay;
    private javax.swing.JTable tblCtThue_HoaDon;
    private javax.swing.JTable tblDichVu_HoaDon;
    private javax.swing.JTable tblDoAn;
    private javax.swing.JTable tblDoUong;
    private javax.swing.JTable tblDsPhieuDatTruoc_TTDT;
    private javax.swing.JTable tblDsSanDangThue;
    private javax.swing.JTable tblDungCu;
    private javax.swing.JTable tblPhieuDatTruoc;
    private javax.swing.JTable tblSan11;
    private javax.swing.JTable tblSan5;
    private javax.swing.JTable tblSan7;
    private javax.swing.JTable tblSanKhaDung_ThueNgay;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtHoTenUpdate;
    private javax.swing.JTextField txtHoTen_DatTruoc;
    private javax.swing.JPasswordField txtMkCu;
    private javax.swing.JPasswordField txtMkMoi;
    private javax.swing.JPasswordField txtMkXacNhan;
    private javax.swing.JTextField txtSDT_DatTruoc;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSdtUpdate;
    private javax.swing.JTextField txtTimKiemLichSu;
    // End of variables declaration//GEN-END:variables
}
