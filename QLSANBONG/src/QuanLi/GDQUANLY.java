/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuanLi;

import DANGNHAP.DANGNHAP;
import GIAODIENQL.DSTAIKHOAN;
import GIAODIENQL.JDBCCONNECTION;
import GIAODIENQL.QUANLY;
import NhanVienPackage.CT_THUE;
import NhanVienPackage.DANHMUC;
import NhanVienPackage.GDNHANVIEN;
import NhanVienPackage.IODATA;
import NhanVienPackage.LOAISAN;
import NhanVienPackage.NHANVIEN;
import NhanVienPackage.SANBONG;
import NhanVienPackage.TRANGTHAI;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ad
 */
public class GDQUANLY extends javax.swing.JFrame {

    /**
     * Creates new form QUANLI
     */
    private QUANLY quanLy=new QUANLY();
    private DefaultTableModel dftbDSSB = new DefaultTableModel();
    DefaultTableModel dftbTKDT = new DefaultTableModel();
    private IODATA iodata = new IODATA();
    private List<CT_THUE> dataSetThongKe = new ArrayList<CT_THUE>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a");
    DefaultCategoryDataset dataSetNgay;
    DefaultCategoryDataset dataSetThang;
    DefaultCategoryDataset datasetQuy;
            
    public GDQUANLY(String tenTkQl) {
        initComponents();
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(true);
        pnThemQL.setVisible(true);
        pnXoaQL.setVisible(false);
        lbThongTinNhanVien_QL.setForeground(new Color(204,204,204));

        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
        quanLy.loadThongTinQL(tenTkQl);
        setThongTinQL();
        layDSQuanLy();        
        layDSNhanVien();
        setThucAn_ThucUong();
        setDichVuKhac();
        setDichVuNgungCungCap();
        layHSKhungGio();
        layDSSanBong();
        loadComboBoxSanBong();
        dftbDSSB = (DefaultTableModel) tbDSSB.getModel();
        dftbTKDT = (DefaultTableModel) tbTKDT.getModel();
        cbxDanhMuc.addItem(DANHMUC.DOAN);
        cbxDanhMuc.addItem(DANHMUC.DOUONG);
        cbxDanhMuc.addItem(DANHMUC.DUNGCU);
        cbxDanhMuc.addItem(DANHMUC.NGUNGCUNGCAP);
    }
    
    public void setThongTinQL(){
        lbTenTaiKhoan.setText(quanLy.getTaiKhoan());
        setpnTTCNQL();
        setpnSuaTTQL();
    }
    
    public void setpnTTCNQL(){
        lbTenQL.setText(quanLy.getTenQL());
        lbCMNDQL.setText(quanLy.getCMNDQL());
    }
    
    public void setpnSuaTTQL(){
        txtSuaTenQL.setText(quanLy.getTenQL());
        txtSuaCMNDQL.setText(quanLy.getCMNDQL());
    }
    
    private void layDSQuanLy(){
        DefaultTableModel dtm= (DefaultTableModel ) tbDSQL.getModel();
        dtm.setNumRows(0);
        
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="Select NHANVIEN.*,DSTAIKHOAN.CHUCVU from NHANVIEN,DSTAIKHOAN where DSTAIKHOAN.TAIKHOAN=NHANVIEN.MANV\n"
                +"AND CHUCVU = 'QL'";
        Vector vt;
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while (rs.next()) {                
                vt=new Vector();
                vt.add(rs.getString(2));
                vt.add(rs.getString(3));
                vt.add(rs.getString(1));
                vt.add(rs.getString(5));
                dtm.addRow(vt);
            }
            tbDSQL.setModel(dtm);
            rs.close();
            ps.close();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách quản lý: GDQUANLY.layDSQuanLy()");
        }
    }
    
    private void layDSNhanVien(){
        DefaultTableModel dtm=(DefaultTableModel ) tbDSNV.getModel();
        dtm.setNumRows(0);        
        Connection connection = JDBCCONNECTION.getConnection();
        String sql="select NHANVIEN.*, DSTAIKHOAN.* from NHANVIEN, DSTAIKHOAN where DSTAIKHOAN.TAIKHOAN=NHANVIEN.MANV \n"
                +"AND CHUCVU = 'NV'";
        Vector vt;
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()) {                
                vt=new Vector();
                vt.add(rs.getString("MANV"));
                vt.add(rs.getString("TENNV"));
                vt.add(rs.getString("CMND"));
                vt.add(rs.getString("SDT"));
                vt.add(rs.getString("MATKHAU"));
                vt.add(rs.getString("CHUCVU"));
                dtm.addRow(vt);
            }           
            tbDSNV.setModel(dtm);            
            rs.close();
            ps.close();
            connection.close();
            
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách nhân viên: QUANLY.layDSNhanVien()");
        }
    }
    
    private void setThucAn_ThucUong()
    {
       DefaultTableModel dtm=(DefaultTableModel) tbThucAn_ThucUong.getModel();
       dtm.setNumRows(0);
       
       Connection connection=JDBCCONNECTION.getConnection();
       String sql="Select * from DICHVU,DANHMUC where DICHVU.DANHMUC=DANHMUC.MADANHMUC and (DICHVU.DANHMUC='1' or DICHVU.DANHMUC='2')";
       Vector vt;       
       try
       {
           PreparedStatement ps= connection.prepareStatement(sql);
           ResultSet rs= ps.executeQuery();
           while(rs.next())
           {
               vt =new Vector();
               vt.add(rs.getString("TENDV"));
               vt.add(rs.getString("DONVI"));
               vt.add(rs.getString("DONGIA"));
               vt.add(DANHMUC.findDanhMuc(Integer.parseInt(rs.getString("DANHMUC"))));
               dtm.addRow(vt);
           }          
           tbThucAn_ThucUong.setModel(dtm);
           rs.close();
           ps.close();
           connection.close();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi set thuc uong");
       }
  
    }
    private void setDichVuKhac()
    {
        DefaultTableModel dtm=(DefaultTableModel) tbDichVuKhac.getModel();
        dtm.setNumRows(0);
       
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="Select * from DICHVU,DANHMUC where DICHVU.DANHMUC=DANHMUC.MADANHMUC and DICHVU.DANHMUC='3'";
        Vector vt;
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next())
            {
                vt =new Vector();
                vt.add(rs.getString("TENDV"));
                vt.add(rs.getString("DONVI"));
                vt.add(rs.getString("DONGIA"));
                vt.add(DANHMUC.findDanhMuc(Integer.parseInt(rs.getString("DANHMUC"))));
                dtm.addRow(vt);
            }
            
            tbDichVuKhac.setModel(dtm);
            rs.close();
            ps.close();
            connection.close();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi set dich vu khác!");
       }
    }
    
    private void layHSKhungGio(){
        DefaultTableModel dtm=(DefaultTableModel) tbHeSoKhungGio.getModel();
        dtm.setNumRows(0);
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select * from GIATHEOGIO";
        Vector vt;
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next())
            {
                vt =new Vector();
                vt.add(rs.getString("MAKG"));
                vt.add(rs.getString("KHUNGGIO"));
                vt.add(rs.getString("HESO"));
                dtm.addRow(vt);
            }
            
            tbHeSoKhungGio.setModel(dtm);
            rs.close();
            ps.close();
            connection.close();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi lấy hệ số khung giờ");
       }
        
        
    }
    
    private void layDSSanBong(){
        DefaultTableModel dtm=(DefaultTableModel) tbDSSB.getModel();
        dtm.setNumRows(0);       
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select SANBONG.*,LOAISAN.* from SANBONG,LOAISAN where LOAISAN.MALOAISAN=SANBONG.MALOAISAN";
        Vector vt;
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()) {                
                vt=new Vector();
                vt.add(rs.getString("MASAN"));
                vt.add(rs.getString("MALOAISAN"));
                vt.add(rs.getString("TENLOAISAN"));
                vt.add(rs.getString("GIA"));
                vt.add(TRANGTHAI.findTrangThai(Integer.parseInt(rs.getString("TRANGTHAI"))));
                dtm.addRow(vt);
            }           
            tbDSSB.setModel(dtm);            
            rs.close();
            ps.close();
            connection.close();
            
        } catch (Exception e) {
        }
    }
    private void loadComboBoxSanBong()
    {
        cbLoaiSan.removeAllItems();
        cbSuaLoaiSan.removeAllItems();
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select distinct TENLOAISAN from LOAISAN";
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next())
            {
                cbLoaiSan.addItem(rs.getString("TENLOAISAN"));
                cbSuaLoaiSan.addItem(rs.getString("TENLOAISAN"));
            }
            
            rs.close();
            ps.close();
            connection.close();
        }
        catch(SQLException ex)
        {
            System.out.println("loi CBBOX");
                   
        }
    }
    
    public void layTKDT_TatCa(String tuNgay, String denNgay){    
        dataSetThongKe.removeAll(dataSetThongKe);
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select CT_THUE.*, LOAISAN.TENLOAISAN, PHIEUTHUE.NGAYLAPPHIEU from CT_THUE, SANBONG, LOAISAN, PHIEUTHUE where CT_THUE.MAHOADON=PHIEUTHUE.MAHOADON and CT_THUE.MASAN=SANBONG.MASAN and SANBONG.MALOAISAN=LOAISAN.MALOAISAN and '"+tuNgay+"'<=PHIEUTHUE.NGAYLAPPHIEU and PHIEUTHUE.NGAYLAPPHIEU<='"+denNgay+"' and PHIEUTHUE.TRANGTHAI='Đã thanh toán'\n"
                + "ORDER BY PHIEUTHUE.NGAYLAPPHIEU ASC";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()) {                
                CT_THUE ctThue=new CT_THUE();
                SANBONG sanBong = new SANBONG();
                ctThue.setMaHoaDon(rs.getString("MAHOADON"));
                sanBong.setMaSan(rs.getString("MASAN"));
                sanBong.setLoaisan(LOAISAN.findLoaiSan(rs.getString("TENLOAISAN")));
                ctThue.setSanBong(sanBong);
                ctThue.setNgayLapPhieu(rs.getDate("NGAYLAPPHIEU"));
                ctThue.setGioThue(rs.getTime("GIOTHUE"));
                ctThue.setGioTra(rs.getTime("GIOTRA"));
                ctThue.setThanhTien(rs.getInt("THANHTIEN"));
                ctThue.setTongTienDv(iodata.tienSdDv(ctThue.getSanBong().getMaSan(), ctThue.getMaHoaDon()));
                dataSetThongKe.add(ctThue);
            }                      
            rs.close();
            ps.close();
            connection.close();
            thongKe();
        } catch (SQLException e) {
            System.out.println("Lỗi hàm lấy thống kê doanh thu: GDQUANLY.layTKDT_TatCa()");
        }
    }
    public void layTKDT_LoaiSan(String tuNgay, String denNgay, String loaiSan){
        dataSetThongKe.removeAll(dataSetThongKe);
        Connection connection=JDBCCONNECTION.getConnection();
        String sql="select CT_THUE.*, LOAISAN.TENLOAISAN, PHIEUTHUE.NGAYLAPPHIEU from CT_THUE, SANBONG, LOAISAN, PHIEUTHUE where CT_THUE.MAHOADON=PHIEUTHUE.MAHOADON and CT_THUE.MASAN=SANBONG.MASAN and SANBONG.MALOAISAN=LOAISAN.MALOAISAN and '"+tuNgay+"'<=PHIEUTHUE.NGAYLAPPHIEU and PHIEUTHUE.NGAYLAPPHIEU<='"+denNgay+"' and LOAISAN.TENLOAISAN='"+loaiSan+"' and PHIEUTHUE.TRANGTHAI='Đã thanh toán'\n"
                + "ORDER BY PHIEUTHUE.NGAYLAPPHIEU ASC";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()) {                
                CT_THUE ctThue=new CT_THUE();
                SANBONG sanBong = new SANBONG();
                ctThue.setMaHoaDon(rs.getString("MAHOADON"));
                sanBong.setMaSan(rs.getString("MASAN"));
                sanBong.setLoaisan(LOAISAN.findLoaiSan(rs.getString("TENLOAISAN")));
                ctThue.setSanBong(sanBong);
                ctThue.setNgayLapPhieu(rs.getDate("NGAYLAPPHIEU"));
                ctThue.setGioThue(rs.getTime("GIOTHUE"));
                ctThue.setGioTra(rs.getTime("GIOTRA"));
                ctThue.setThanhTien(rs.getInt("THANHTIEN"));
                ctThue.setTongTienDv(iodata.tienSdDv(ctThue.getSanBong().getMaSan(), ctThue.getMaHoaDon()));
                dataSetThongKe.add(ctThue);
            }
            rs.close();
            ps.close();
            connection.close();
            thongKe();
        } catch (SQLException e) {
            System.out.println("Lỗi hàm lấy thống kê doanh thu: GDQUANLY.layTKDT_LoaiSan()");
        }
    }

     public void thongKe() {
        dftbTKDT.setRowCount(0);
        if (!dataSetThongKe.isEmpty()) {
            SimpleDateFormat thangFormat = new SimpleDateFormat("MM/yyyy");
            SimpleDateFormat quyFormat = new SimpleDateFormat("yyyy");
            dataSetNgay = new DefaultCategoryDataset();
            dataSetThang = new DefaultCategoryDataset();
            datasetQuy = new DefaultCategoryDataset();
            
            Date tungay = new Date();
            tungay = dcTuNgay.getDate();
            long ngay = 1000*60*60*24;
            while (dateFormat.format(tungay).compareTo(dateFormat.format(dcDenNgay.getDate()))!=0) {                
                try {
                    int number = dataSetNgay.getValue("Tiền Lãi", dateFormat.format(tungay)).intValue();
                } catch (Exception e) {
                    dataSetNgay.addValue(0, "Tiền Lãi", dateFormat.format(tungay));
                }
                tungay.setTime(tungay.getTime()+ngay);
            }
            for (CT_THUE ct_thue : dataSetThongKe) {
                dftbTKDT.addRow(new Object[]{
                    ct_thue.getMaHoaDon(),
                    ct_thue.getSanBong().getMaSan(),
                    ct_thue.getSanBong().getLoaisan(),
                    dateFormat.format(ct_thue.getNgayLapPhieu()),
                    timeFormat.format(ct_thue.getGioThue()),
                    timeFormat.format(ct_thue.getGioTra()),
                    ct_thue.getThanhTien(),
                    ct_thue.getTongTienDv()
                });
                try {
                    int number = dataSetNgay.getValue("Tiền Lãi", dateFormat.format(ct_thue.getNgayLapPhieu())).intValue();
                    dataSetNgay.addValue(number + ct_thue.getTongTienDv() + ct_thue.getThanhTien(), "Tiền Lãi", dateFormat.format(ct_thue.getNgayLapPhieu()));
                } catch (Exception e) {
                    dataSetNgay.addValue(ct_thue.getTongTienDv() + ct_thue.getThanhTien(), "Tiền Lãi", dateFormat.format(ct_thue.getNgayLapPhieu()));
                }
                try {
                    int number = dataSetThang.getValue("Tiền Lãi", thangFormat.format(ct_thue.getNgayLapPhieu())).intValue();
                    dataSetThang.addValue(number + ct_thue.getTongTienDv() + ct_thue.getThanhTien(), "Tiền Lãi", thangFormat.format(ct_thue.getNgayLapPhieu()));
                } catch (Exception e) {
                    dataSetThang.addValue(ct_thue.getTongTienDv() + ct_thue.getThanhTien(), "Tiền Lãi", thangFormat.format(ct_thue.getNgayLapPhieu()));
                }
                try {
                    int number = datasetQuy.getValue("Tiền Lãi", "Quý "+(ct_thue.getNgayLapPhieu().getMonth()/3+1) +"/"+ quyFormat.format(ct_thue.getNgayLapPhieu())).intValue();
                    datasetQuy.addValue(number + ct_thue.getTongTienDv() + ct_thue.getThanhTien(), "Tiền Lãi", "Quý "+(ct_thue.getNgayLapPhieu().getMonth()/3+1) +"/"+ quyFormat.format(ct_thue.getNgayLapPhieu()));
                } catch (Exception e) {
                    datasetQuy.addValue(ct_thue.getTongTienDv() + ct_thue.getThanhTien(), "Tiền Lãi",  "Quý "+(ct_thue.getNgayLapPhieu().getMonth()/3+1) +"/"+ quyFormat.format(ct_thue.getNgayLapPhieu()));
                }
            }
            cbxChonTKTheo.setSelectedIndex(0);
        }
    }

    public void dungBieuDo(DefaultCategoryDataset dataSet) {
        JFreeChart chart = ChartFactory.createLineChart("THỐNG KÊ DOANH THU " + String.valueOf(cbLoaiSanTKDT.getSelectedItem()), "Thời gian", "Doanh thu", dataSet);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(pnlBieuDoView.getWidth(), 700));

        pnlBieuDoView.removeAll();
        pnlBieuDoView.setLayout(new CardLayout());
        pnlBieuDoView.add(chartPanel);
        pnlBieuDoView.validate();
        pnlBieuDoView.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnMenu = new javax.swing.JPanel();
        lbDangXuat = new javax.swing.JLabel();
        lbNgayHienTai = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lbAboutUs = new javax.swing.JLabel();
        lbTroGiup = new javax.swing.JLabel();
        lbLienHe = new javax.swing.JLabel();
        lbGiayPhep = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        btnQuanLySanBong = new javax.swing.JPanel();
        lbQuanLySanBong = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnThongTinConNguoi = new javax.swing.JPanel();
        lbThongTinConNguoi = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbTenTaiKhoan = new javax.swing.JLabel();
        btnChucNangNhanVien = new javax.swing.JPanel();
        lbChucNangNhanVien = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnThongKe = new javax.swing.JPanel();
        lbThongKe = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        pnTong = new javax.swing.JPanel();
        pnThongTinCaNhan = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jSeparator21 = new javax.swing.JSeparator();
        pnTong_TTCN = new javax.swing.JPanel();
        pnSuaThongTinQL = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtSuaTenQL = new javax.swing.JTextField();
        txtSuaCMNDQL = new javax.swing.JTextField();
        lblWarnHoTen_Sua = new javax.swing.JLabel();
        lblWarnCMND_Sua = new javax.swing.JLabel();
        pnDoiMatKhauQL = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        pwMKCu = new javax.swing.JPasswordField();
        pwMKMoi = new javax.swing.JPasswordField();
        pwMKXacNhan = new javax.swing.JPasswordField();
        lbThongBaoLoi = new javax.swing.JLabel();
        pnTTCNQL = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lbTenQL = new javax.swing.JLabel();
        lbCMNDQL = new javax.swing.JLabel();
        btnSuaThongTinQL = new javax.swing.JButton();
        btnDoiMKQL = new javax.swing.JButton();
        pnThongTinQuanLy = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        lbThongTinQuanLy_QL = new javax.swing.JLabel();
        lbThongTinNhanVien_QL = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        pnDSQL = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDSQL = new javax.swing.JTable();
        pnTongThemQL = new javax.swing.JPanel();
        pnXoaQL = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        lbXoaTenQL = new javax.swing.JLabel();
        lbXoaCMNDQL = new javax.swing.JLabel();
        lbXoaTKQL = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtChucVuQL = new javax.swing.JTextField();
        pnThemQL = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtThemTenQL = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtThemCMNDQL = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtThemTKQL = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        pwThemMKQL = new javax.swing.JPasswordField();
        jLabel16 = new javax.swing.JLabel();
        pwThemXNMKQL = new javax.swing.JPasswordField();
        btnThemQL = new javax.swing.JButton();
        btnXoaQL = new javax.swing.JButton();
        pnThongTinSanBong = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        lbThongTinSanBong_SB = new javax.swing.JLabel();
        lbThongTinDichVu_SB = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        lbHeSoKhungGio_SB = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        pnSuaGiaSan = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        cbSuaLoaiSan = new javax.swing.JComboBox<>();
        txtSuaGiaSan = new javax.swing.JTextField();
        lblGiaLoaiSanWarn = new javax.swing.JLabel();
        pnThemSB = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        cbLoaiSan = new javax.swing.JComboBox<>();
        txtMaSan = new javax.swing.JTextField();
        lblTrangThaiSan = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbDSSB = new javax.swing.JTable();
        btnSuaGiaSan = new javax.swing.JButton();
        btnThemSB = new javax.swing.JButton();
        btnBaoTri_BoBaoTri = new javax.swing.JButton();
        pnThongTinDichVu = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
        jLabel139 = new javax.swing.JLabel();
        lbThongTinSanBong_DV = new javax.swing.JLabel();
        lbThongTinDichVu_DV = new javax.swing.JLabel();
        jSeparator23 = new javax.swing.JSeparator();
        lbHeSoKhungGio_DV = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbThucAn_ThucUong = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbDichVuKhac = new javax.swing.JTable();
        jLabel51 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        txtTenDV = new javax.swing.JTextField();
        txtGiaTienDV = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txtDonVi = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        btnThemDV = new javax.swing.JButton();
        btnSuaDV = new javax.swing.JButton();
        btnXoaDV = new javax.swing.JButton();
        lblWarnTenDV = new javax.swing.JLabel();
        lblWarnGiaTien = new javax.swing.JLabel();
        lblWarnDonVi = new javax.swing.JLabel();
        cbxDanhMuc = new javax.swing.JComboBox<>();
        jPanel19 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tbDVNgungCungCap = new javax.swing.JTable();
        pnThongTinNhanVien = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        lbThongTinQuanLy_NV = new javax.swing.JLabel();
        lbThongTinNhanVien_NV = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbDSNV = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pnThemNV = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        txtThemMaNV = new javax.swing.JTextField();
        txtThemTenNV = new javax.swing.JTextField();
        txtThemCMNDNV = new javax.swing.JTextField();
        txtThemSDTNV = new javax.swing.JTextField();
        pwThemMKNV = new javax.swing.JPasswordField();
        pwThemXNMKNV = new javax.swing.JPasswordField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        lblTenNvWarn_ThemNV = new javax.swing.JLabel();
        pnSuaXoaNV = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        txtSuaMaNV = new javax.swing.JTextField();
        txtSuaTenNV = new javax.swing.JTextField();
        pwSuaMKNV = new javax.swing.JPasswordField();
        txtSuaCMNDNV = new javax.swing.JTextField();
        txtSuaSDTNV = new javax.swing.JTextField();
        txtChucVuNV = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        btnThemNV = new javax.swing.JButton();
        btnSuaNV = new javax.swing.JButton();
        btnXoaNV = new javax.swing.JButton();
        pnHeSoKhungGio = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        lbThongTinSanBong_HSKG = new javax.swing.JLabel();
        lbThongTinDichVu_HSKG = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        lbHeSoKhungGio_HSKG = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        txtKhungGio = new javax.swing.JTextField();
        txtHeSo = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbHeSoKhungGio = new javax.swing.JTable();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        btnSuaHSKG = new javax.swing.JButton();
        lbMaKG = new javax.swing.JLabel();
        pnThongKe = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        lbThongKe_ThongKe = new javax.swing.JLabel();
        lbBieuDoThongKe_ThongKe = new javax.swing.JLabel();
        jSeparator26 = new javax.swing.JSeparator();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        dcTuNgay = new com.toedter.calendar.JDateChooser();
        dcDenNgay = new com.toedter.calendar.JDateChooser();
        cbLoaiSanTKDT = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbTKDT = new javax.swing.JTable();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        lbTienThueSan = new javax.swing.JLabel();
        lbTienDV = new javax.swing.JLabel();
        lbTongTien = new javax.swing.JLabel();
        pnBieuDoThongKe = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        lbThongKe_BDTK = new javax.swing.JLabel();
        lbBieuDoThongKe_BDTK = new javax.swing.JLabel();
        jSeparator27 = new javax.swing.JSeparator();
        pnlBieuDoView = new javax.swing.JPanel();
        cbxChonTKTheo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnMenu.setBackground(new java.awt.Color(255, 255, 255));
        pnMenu.setPreferredSize(new java.awt.Dimension(329, 885));
        pnMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        pnMenu.add(lbDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(199, 856, -1, -1));

        lbNgayHienTai.setBackground(new java.awt.Color(204, 204, 204));
        lbNgayHienTai.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbNgayHienTai.setForeground(new java.awt.Color(204, 204, 204));
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
        pnMenu.add(lbNgayHienTai, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 860, -1, -1));
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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/logo.png"))); // NOI18N
        jLabel2.setMaximumSize(new java.awt.Dimension(1111, 1111));
        pnMenu.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 0, -1, 109));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-account-100.png"))); // NOI18N
        pnMenu.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 122, -1, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("CHỨC VỤ: Quản lý");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        pnMenu.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));
        pnMenu.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 632, 287, 10));
        pnMenu.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 839, 287, 10));

        btnQuanLySanBong.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnQuanLySanBongMouseMoved(evt);
            }
        });
        btnQuanLySanBong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQuanLySanBongMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnQuanLySanBongMouseExited(evt);
            }
        });

        lbQuanLySanBong.setBackground(new java.awt.Color(204, 204, 204));
        lbQuanLySanBong.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbQuanLySanBong.setForeground(new java.awt.Color(204, 204, 204));
        lbQuanLySanBong.setText("QUẢN LÝ SÂN BÓNG");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-stadium-30.png"))); // NOI18N

        javax.swing.GroupLayout btnQuanLySanBongLayout = new javax.swing.GroupLayout(btnQuanLySanBong);
        btnQuanLySanBong.setLayout(btnQuanLySanBongLayout);
        btnQuanLySanBongLayout.setHorizontalGroup(
            btnQuanLySanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnQuanLySanBongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(lbQuanLySanBong)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        btnQuanLySanBongLayout.setVerticalGroup(
            btnQuanLySanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnQuanLySanBongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnQuanLySanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnQuanLySanBongLayout.createSequentialGroup()
                        .addComponent(lbQuanLySanBong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnMenu.add(btnQuanLySanBong, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 280, 53));

        btnThongTinConNguoi.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnThongTinConNguoiMouseMoved(evt);
            }
        });
        btnThongTinConNguoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThongTinConNguoiMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThongTinConNguoiMouseExited(evt);
            }
        });

        lbThongTinConNguoi.setBackground(new java.awt.Color(204, 204, 204));
        lbThongTinConNguoi.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbThongTinConNguoi.setForeground(new java.awt.Color(204, 204, 204));
        lbThongTinConNguoi.setText("THÔNG TIN CON NGƯỜI");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-account-30.png"))); // NOI18N

        javax.swing.GroupLayout btnThongTinConNguoiLayout = new javax.swing.GroupLayout(btnThongTinConNguoi);
        btnThongTinConNguoi.setLayout(btnThongTinConNguoiLayout);
        btnThongTinConNguoiLayout.setHorizontalGroup(
            btnThongTinConNguoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnThongTinConNguoiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThongTinConNguoi)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        btnThongTinConNguoiLayout.setVerticalGroup(
            btnThongTinConNguoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThongTinConNguoiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnThongTinConNguoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(lbThongTinConNguoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnMenu.add(btnThongTinConNguoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 280, 53));

        lbTenTaiKhoan.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbTenTaiKhoan.setForeground(new java.awt.Color(0, 0, 102));
        lbTenTaiKhoan.setText("TÊN TÀI KHOẢN");
        lbTenTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTenTaiKhoanMouseClicked(evt);
            }
        });
        pnMenu.add(lbTenTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 166, -1, -1));

        btnChucNangNhanVien.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnChucNangNhanVienMouseMoved(evt);
            }
        });
        btnChucNangNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChucNangNhanVienMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnChucNangNhanVienMouseExited(evt);
            }
        });

        lbChucNangNhanVien.setBackground(new java.awt.Color(204, 204, 204));
        lbChucNangNhanVien.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbChucNangNhanVien.setForeground(new java.awt.Color(204, 204, 204));
        lbChucNangNhanVien.setText("CHỨC NĂNG NHÂN VIÊN");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NhanVien/HINHANH/icons8-people-working-together-50.png"))); // NOI18N

        javax.swing.GroupLayout btnChucNangNhanVienLayout = new javax.swing.GroupLayout(btnChucNangNhanVien);
        btnChucNangNhanVien.setLayout(btnChucNangNhanVienLayout);
        btnChucNangNhanVienLayout.setHorizontalGroup(
            btnChucNangNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnChucNangNhanVienLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbChucNangNhanVien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnChucNangNhanVienLayout.setVerticalGroup(
            btnChucNangNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnChucNangNhanVienLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btnChucNangNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbChucNangNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnMenu.add(btnChucNangNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 280, 53));

        btnThongKe.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnThongKeMouseMoved(evt);
            }
        });
        btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThongKeMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThongKeMouseExited(evt);
            }
        });

        lbThongKe.setBackground(new java.awt.Color(204, 204, 204));
        lbThongKe.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbThongKe.setForeground(new java.awt.Color(204, 204, 204));
        lbThongKe.setText("THỐNG KÊ");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-statistics-30.png"))); // NOI18N

        javax.swing.GroupLayout btnThongKeLayout = new javax.swing.GroupLayout(btnThongKe);
        btnThongKe.setLayout(btnThongKeLayout);
        btnThongKeLayout.setHorizontalGroup(
            btnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThongKe)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        btnThongKeLayout.setVerticalGroup(
            btnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addComponent(lbThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnMenu.add(btnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 280, 53));

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
        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-information-50.png"))); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jLabel115)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnThongTinCaNhan.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 140));

        pnTong_TTCN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnSuaThongTinQL.setBackground(new java.awt.Color(254, 235, 208));
        pnSuaThongTinQL.setPreferredSize(new java.awt.Dimension(790, 350));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 102));
        jLabel30.setText("HỌ TÊN:");
        jLabel30.setToolTipText("");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 102));
        jLabel31.setText("CMND:");
        jLabel31.setToolTipText("");

        txtSuaTenQL.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtSuaTenQL.setForeground(new java.awt.Color(0, 0, 102));
        txtSuaTenQL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSuaTenQLMouseClicked(evt);
            }
        });
        txtSuaTenQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSuaTenQLActionPerformed(evt);
            }
        });

        txtSuaCMNDQL.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtSuaCMNDQL.setForeground(new java.awt.Color(0, 0, 102));
        txtSuaCMNDQL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSuaCMNDQLMouseClicked(evt);
            }
        });

        lblWarnHoTen_Sua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblWarnHoTen_Sua.setForeground(new java.awt.Color(255, 0, 0));
        lblWarnHoTen_Sua.setText("Họ tên không được để trống");
        lblWarnHoTen_Sua.setVisible(false);

        lblWarnCMND_Sua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblWarnCMND_Sua.setForeground(new java.awt.Color(255, 0, 0));
        lblWarnCMND_Sua.setText("CMND phải có định dạng 9 hoặc 12 số");
        lblWarnCMND_Sua.setVisible(false);

        javax.swing.GroupLayout pnSuaThongTinQLLayout = new javax.swing.GroupLayout(pnSuaThongTinQL);
        pnSuaThongTinQL.setLayout(pnSuaThongTinQLLayout);
        pnSuaThongTinQLLayout.setHorizontalGroup(
            pnSuaThongTinQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSuaThongTinQLLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addGroup(pnSuaThongTinQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addGap(36, 36, 36)
                .addGroup(pnSuaThongTinQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWarnCMND_Sua)
                    .addComponent(lblWarnHoTen_Sua)
                    .addComponent(txtSuaCMNDQL, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSuaTenQL, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        pnSuaThongTinQLLayout.setVerticalGroup(
            pnSuaThongTinQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSuaThongTinQLLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(pnSuaThongTinQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtSuaTenQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnHoTen_Sua)
                .addGap(33, 33, 33)
                .addGroup(pnSuaThongTinQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtSuaCMNDQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnCMND_Sua)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        pnTong_TTCN.add(pnSuaThongTinQL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnDoiMatKhauQL.setBackground(new java.awt.Color(254, 235, 208));
        pnDoiMatKhauQL.setPreferredSize(new java.awt.Dimension(790, 350));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 102));
        jLabel27.setText("MẬT KHẨU CŨ:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 102));
        jLabel28.setText("MẬT KHẨU MỚI:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 102));
        jLabel29.setText("XÁC NHẬN MK:");

        pwMKCu.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        pwMKCu.setForeground(new java.awt.Color(0, 0, 102));
        pwMKCu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pwMKCuMouseClicked(evt);
            }
        });

        pwMKMoi.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        pwMKMoi.setForeground(new java.awt.Color(0, 0, 102));
        pwMKMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pwMKMoiMouseClicked(evt);
            }
        });

        pwMKXacNhan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        pwMKXacNhan.setForeground(new java.awt.Color(0, 0, 102));
        pwMKXacNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pwMKXacNhanMouseClicked(evt);
            }
        });

        lbThongBaoLoi.setForeground(new java.awt.Color(255, 0, 0));
        lbThongBaoLoi.setText("THÔNG BÁO LỖI");

        javax.swing.GroupLayout pnDoiMatKhauQLLayout = new javax.swing.GroupLayout(pnDoiMatKhauQL);
        pnDoiMatKhauQL.setLayout(pnDoiMatKhauQLLayout);
        pnDoiMatKhauQLLayout.setHorizontalGroup(
            pnDoiMatKhauQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDoiMatKhauQLLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(pnDoiMatKhauQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbThongBaoLoi)
                    .addGroup(pnDoiMatKhauQLLayout.createSequentialGroup()
                        .addGroup(pnDoiMatKhauQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel27)
                            .addComponent(jLabel29))
                        .addGap(58, 58, 58)
                        .addGroup(pnDoiMatKhauQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pwMKMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pwMKCu, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pwMKXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        pnDoiMatKhauQLLayout.setVerticalGroup(
            pnDoiMatKhauQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDoiMatKhauQLLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnDoiMatKhauQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(pwMKCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(pnDoiMatKhauQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(pwMKMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(pnDoiMatKhauQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwMKXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(57, 57, 57)
                .addComponent(lbThongBaoLoi)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pnTong_TTCN.add(pnDoiMatKhauQL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnTTCNQL.setBackground(new java.awt.Color(254, 235, 208));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setText("HỌ TÊN:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 102));
        jLabel25.setText("CMND:");

        lbTenQL.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lbTenQL.setForeground(new java.awt.Color(0, 0, 102));
        lbTenQL.setText("HỌ TÊN");

        lbCMNDQL.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lbCMNDQL.setForeground(new java.awt.Color(0, 0, 102));
        lbCMNDQL.setText("CMND");

        javax.swing.GroupLayout pnTTCNQLLayout = new javax.swing.GroupLayout(pnTTCNQL);
        pnTTCNQL.setLayout(pnTTCNQLLayout);
        pnTTCNQLLayout.setHorizontalGroup(
            pnTTCNQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTTCNQLLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(pnTTCNQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addGap(138, 138, 138)
                .addGroup(pnTTCNQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbCMNDQL)
                    .addComponent(lbTenQL))
                .addContainerGap(423, Short.MAX_VALUE))
        );
        pnTTCNQLLayout.setVerticalGroup(
            pnTTCNQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTTCNQLLayout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addGroup(pnTTCNQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCMNDQL)
                    .addComponent(jLabel25))
                .addContainerGap(140, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTTCNQLLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTTCNQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTenQL)
                    .addComponent(jLabel24))
                .addGap(244, 244, 244))
        );

        pnTong_TTCN.add(pnTTCNQL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnThongTinCaNhan.add(pnTong_TTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 790, 350));

        btnSuaThongTinQL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSuaThongTinQL.setText("SỬA THÔNG TIN");
        btnSuaThongTinQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaThongTinQLActionPerformed(evt);
            }
        });
        pnThongTinCaNhan.add(btnSuaThongTinQL, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 650, -1, -1));

        btnDoiMKQL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDoiMKQL.setText("ĐỔI MẬT KHẨU");
        btnDoiMKQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMKQLActionPerformed(evt);
            }
        });
        pnThongTinCaNhan.add(btnDoiMKQL, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 650, -1, -1));

        pnTong.add(pnThongTinCaNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, -1));

        pnThongTinQuanLy.setBackground(new java.awt.Color(254, 235, 208));
        pnThongTinQuanLy.setForeground(new java.awt.Color(0, 0, 102));
        pnThongTinQuanLy.setMaximumSize(new java.awt.Dimension(9999, 9999));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setForeground(new java.awt.Color(0, 102, 102));
        jPanel30.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 0, 102));
        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-people-working-together-50.png"))); // NOI18N
        jLabel78.setText("THÔNG TIN CON NGƯỜI");

        lbThongTinQuanLy_QL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinQuanLy_QL.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinQuanLy_QL.setText("Thông Tin Quản Lý");

        lbThongTinNhanVien_QL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinNhanVien_QL.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinNhanVien_QL.setText("Thông Tin Nhân Viên");
        lbThongTinNhanVien_QL.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbThongTinNhanVien_QLMouseMoved(evt);
            }
        });
        lbThongTinNhanVien_QL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongTinNhanVien_QLMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThongTinNhanVien_QLMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbThongTinQuanLy_QL, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbThongTinNhanVien_QL)
                            .addComponent(jLabel78))))
                .addContainerGap(794, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbThongTinQuanLy_QL)
                    .addComponent(lbThongTinNhanVien_QL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnDSQL.setBackground(new java.awt.Color(202, 232, 249));
        pnDSQL.setPreferredSize(new java.awt.Dimension(430, 410));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 102));
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-add-list-30.png"))); // NOI18N
        jLabel32.setText("DANH SÁCH QUẢN LÝ");

        tbDSQL.setBackground(new java.awt.Color(240, 240, 240));
        tbDSQL.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbDSQL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Quản Lý", "CMND", "Tài Khoản", "Chức Vụ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDSQL.setRowHeight(30);
        tbDSQL.getTableHeader().setReorderingAllowed(false);
        tbDSQL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDSQLMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDSQL);
        if (tbDSQL.getColumnModel().getColumnCount() > 0) {
            tbDSQL.getColumnModel().getColumn(0).setResizable(false);
            tbDSQL.getColumnModel().getColumn(0).setPreferredWidth(90);
            tbDSQL.getColumnModel().getColumn(1).setResizable(false);
            tbDSQL.getColumnModel().getColumn(1).setPreferredWidth(60);
            tbDSQL.getColumnModel().getColumn(2).setResizable(false);
            tbDSQL.getColumnModel().getColumn(2).setPreferredWidth(30);
            tbDSQL.getColumnModel().getColumn(3).setResizable(false);
            tbDSQL.getColumnModel().getColumn(3).setPreferredWidth(30);
        }

        javax.swing.GroupLayout pnDSQLLayout = new javax.swing.GroupLayout(pnDSQL);
        pnDSQL.setLayout(pnDSQLLayout);
        pnDSQLLayout.setHorizontalGroup(
            pnDSQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDSQLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnDSQLLayout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(jLabel32)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnDSQLLayout.setVerticalGroup(
            pnDSQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDSQLLayout.createSequentialGroup()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pnTongThemQL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnXoaQL.setBackground(new java.awt.Color(254, 235, 208));
        pnXoaQL.setPreferredSize(new java.awt.Dimension(430, 410));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setText("HỌ TÊN:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("CMND:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 102));
        jLabel19.setText("TÀI KHOẢN:");

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 0, 102));
        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8_delete_user_male_30px_1.png"))); // NOI18N
        jLabel66.setText("XÓA QUẢN LÝ");

        lbXoaTenQL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbXoaCMNDQL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbXoaTKQL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 102));
        jLabel26.setText("CHỨC VỤ:");

        txtChucVuQL.setBackground(new java.awt.Color(254, 235, 208));

        javax.swing.GroupLayout pnXoaQLLayout = new javax.swing.GroupLayout(pnXoaQL);
        pnXoaQL.setLayout(pnXoaQLLayout);
        pnXoaQLLayout.setHorizontalGroup(
            pnXoaQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnXoaQLLayout.createSequentialGroup()
                .addGroup(pnXoaQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnXoaQLLayout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addGroup(pnXoaQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel26))
                        .addGap(89, 89, 89)
                        .addGroup(pnXoaQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbXoaCMNDQL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbXoaTKQL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbXoaTenQL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtChucVuQL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
                    .addGroup(pnXoaQLLayout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(jLabel66)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnXoaQLLayout.setVerticalGroup(
            pnXoaQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnXoaQLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel66)
                .addGap(31, 31, 31)
                .addGroup(pnXoaQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lbXoaTenQL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(pnXoaQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lbXoaCMNDQL))
                .addGap(53, 53, 53)
                .addGroup(pnXoaQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(lbXoaTKQL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(pnXoaQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtChucVuQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        pnTongThemQL.add(pnXoaQL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 340));

        pnThemQL.setBackground(new java.awt.Color(254, 235, 208));
        pnThemQL.setPreferredSize(new java.awt.Dimension(430, 410));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(37, 102, 142));
        jLabel1.setText("HỌ TÊN:");

        txtThemTenQL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtThemTenQLMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(37, 102, 142));
        jLabel4.setText("CMND:");

        txtThemCMNDQL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtThemCMNDQLMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(37, 102, 142));
        jLabel12.setText("TÀI KHOẢN:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(37, 102, 142));
        jLabel13.setText("MẬT KHẨU:");

        txtThemTKQL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtThemTKQLMouseClicked(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(37, 102, 142));
        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-add-administrator-30.png"))); // NOI18N
        jLabel62.setText("THÊM QUẢN LÝ");

        pwThemMKQL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pwThemMKQLMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(37, 102, 142));
        jLabel16.setText("XÁC NHẬN MK:");

        pwThemXNMKQL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pwThemXNMKQLMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnThemQLLayout = new javax.swing.GroupLayout(pnThemQL);
        pnThemQL.setLayout(pnThemQLLayout);
        pnThemQLLayout.setHorizontalGroup(
            pnThemQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemQLLayout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(jLabel62)
                .addContainerGap(202, Short.MAX_VALUE))
            .addGroup(pnThemQLLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(pnThemQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnThemQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pwThemXNMKQL, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(txtThemTenQL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(txtThemCMNDQL, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtThemTKQL, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pwThemMKQL))
                .addGap(88, 88, 88))
        );
        pnThemQLLayout.setVerticalGroup(
            pnThemQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemQLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62)
                .addGap(18, 18, 18)
                .addGroup(pnThemQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThemTenQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(29, 29, 29)
                .addGroup(pnThemQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThemCMNDQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(30, 30, 30)
                .addGroup(pnThemQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThemTKQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(29, 29, 29)
                .addGroup(pnThemQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwThemMKQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(28, 28, 28)
                .addGroup(pnThemQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(pwThemXNMKQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pnTongThemQL.add(pnThemQL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 340));

        btnThemQL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemQL.setText("THÊM QUẢN LÝ");
        btnThemQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemQLActionPerformed(evt);
            }
        });

        btnXoaQL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXoaQL.setText("XÓA QUẢN LÝ");
        btnXoaQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaQLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnThongTinQuanLyLayout = new javax.swing.GroupLayout(pnThongTinQuanLy);
        pnThongTinQuanLy.setLayout(pnThongTinQuanLyLayout);
        pnThongTinQuanLyLayout.setHorizontalGroup(
            pnThongTinQuanLyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinQuanLyLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(pnTongThemQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnDSQL, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnThongTinQuanLyLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(btnThemQL)
                .addGap(124, 124, 124)
                .addComponent(btnXoaQL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnThongTinQuanLyLayout.setVerticalGroup(
            pnThongTinQuanLyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinQuanLyLayout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addGroup(pnThongTinQuanLyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnTongThemQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnDSQL, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(pnThongTinQuanLyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemQL)
                    .addComponent(btnXoaQL))
                .addContainerGap(272, Short.MAX_VALUE))
        );

        pnTong.add(pnThongTinQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

        pnThongTinSanBong.setBackground(new java.awt.Color(254, 235, 208));
        pnThongTinSanBong.setForeground(new java.awt.Color(0, 0, 102));
        pnThongTinSanBong.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnThongTinSanBong.setPreferredSize(new java.awt.Dimension(1180, 900));

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setForeground(new java.awt.Color(0, 102, 102));
        jPanel40.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel109.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(0, 0, 102));
        jLabel109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-stadium-48.png"))); // NOI18N
        jLabel109.setText("QUẢN LÝ SÂN BÓNG");

        lbThongTinSanBong_SB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinSanBong_SB.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinSanBong_SB.setText("Thông Tin Sân Bóng");

        lbThongTinDichVu_SB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinDichVu_SB.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinDichVu_SB.setText("Thông Tin Dịch Vụ");
        lbThongTinDichVu_SB.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbThongTinDichVu_SBMouseMoved(evt);
            }
        });
        lbThongTinDichVu_SB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongTinDichVu_SBMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThongTinDichVu_SBMouseExited(evt);
            }
        });

        lbHeSoKhungGio_SB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbHeSoKhungGio_SB.setForeground(new java.awt.Color(0, 0, 102));
        lbHeSoKhungGio_SB.setText("Hệ Số Khung Giờ");
        lbHeSoKhungGio_SB.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbHeSoKhungGio_SBMouseMoved(evt);
            }
        });
        lbHeSoKhungGio_SB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbHeSoKhungGio_SBMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbHeSoKhungGio_SBMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(lbThongTinSanBong_SB)
                        .addGap(105, 105, 105)
                        .addComponent(lbThongTinDichVu_SB)
                        .addGap(114, 114, 114)
                        .addComponent(lbHeSoKhungGio_SB))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel109))))
                .addContainerGap(520, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel109)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbThongTinSanBong_SB)
                        .addComponent(lbThongTinDichVu_SB))
                    .addComponent(lbHeSoKhungGio_SB, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBackground(new java.awt.Color(254, 235, 208));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnSuaGiaSan.setBackground(new java.awt.Color(254, 235, 208));
        pnSuaGiaSan.setPreferredSize(new java.awt.Dimension(450, 200));

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 0, 102));
        jLabel60.setText("Giá Tiền:");

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 0, 102));
        jLabel61.setText("Loại Sân:");

        txtSuaGiaSan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSuaGiaSanKeyTyped(evt);
            }
        });

        lblGiaLoaiSanWarn.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblGiaLoaiSanWarn.setForeground(new java.awt.Color(255, 0, 0));
        lblGiaLoaiSanWarn.setText("Giá phải là số và không được để trống");
        lblGiaLoaiSanWarn.setVisible(false);

        javax.swing.GroupLayout pnSuaGiaSanLayout = new javax.swing.GroupLayout(pnSuaGiaSan);
        pnSuaGiaSan.setLayout(pnSuaGiaSanLayout);
        pnSuaGiaSanLayout.setHorizontalGroup(
            pnSuaGiaSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSuaGiaSanLayout.createSequentialGroup()
                .addGroup(pnSuaGiaSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnSuaGiaSanLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblGiaLoaiSanWarn))
                    .addGroup(pnSuaGiaSanLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(pnSuaGiaSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addGroup(pnSuaGiaSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSuaGiaSan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSuaLoaiSan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(47, 47, 47))
        );
        pnSuaGiaSanLayout.setVerticalGroup(
            pnSuaGiaSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSuaGiaSanLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(pnSuaGiaSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(cbSuaLoaiSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(pnSuaGiaSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(txtSuaGiaSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblGiaLoaiSanWarn)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jPanel5.add(pnSuaGiaSan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -10, 420, 210));

        pnThemSB.setBackground(new java.awt.Color(254, 235, 208));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 102));
        jLabel33.setText("Mã Sân:");

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 0, 102));
        jLabel63.setText("Loại Sân:");

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(0, 0, 102));
        jLabel64.setText("Trạng Thái Sân");

        lblTrangThaiSan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout pnThemSBLayout = new javax.swing.GroupLayout(pnThemSB);
        pnThemSB.setLayout(pnThemSBLayout);
        pnThemSBLayout.setHorizontalGroup(
            pnThemSBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemSBLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pnThemSBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64)
                    .addComponent(jLabel63)
                    .addComponent(jLabel33))
                .addGap(39, 39, 39)
                .addGroup(pnThemSBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThemSBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbLoaiSan, 0, 211, Short.MAX_VALUE)
                        .addComponent(txtMaSan))
                    .addComponent(lblTrangThaiSan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        pnThemSBLayout.setVerticalGroup(
            pnThemSBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemSBLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnThemSBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtMaSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(pnThemSBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(cbLoaiSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(pnThemSBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(lblTrangThaiSan))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel5.add(pnThemSB, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, -1));

        jPanel3.setBackground(new java.awt.Color(202, 232, 249));

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 0, 102));
        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-stadium-48.png"))); // NOI18N
        jLabel67.setText("DANH SÁCH SÂN BÓNG");

        tbDSSB.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tbDSSB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Sân", "Mã Loại Sân", "Tên Loại Sân", "Giá Tiền", "Trạng Thái Sân"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDSSB.setRowHeight(25);
        tbDSSB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbDSSB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDSSBMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbDSSB);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(jLabel67)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnSuaGiaSan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSuaGiaSan.setText("SỬA GIÁ SÂN");
        btnSuaGiaSan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaGiaSanActionPerformed(evt);
            }
        });

        btnThemSB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemSB.setText("THÊM SÂN BÓNG");
        btnThemSB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSBActionPerformed(evt);
            }
        });

        btnBaoTri_BoBaoTri.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBaoTri_BoBaoTri.setText("BẢO TRÌ SÂN");
        btnBaoTri_BoBaoTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaoTri_BoBaoTriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnThongTinSanBongLayout = new javax.swing.GroupLayout(pnThongTinSanBong);
        pnThongTinSanBong.setLayout(pnThongTinSanBongLayout);
        pnThongTinSanBongLayout.setHorizontalGroup(
            pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnThongTinSanBongLayout.createSequentialGroup()
                            .addGap(63, 63, 63)
                            .addComponent(btnThemSB, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                            .addGap(53, 53, 53)
                            .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSuaGiaSan, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(btnBaoTri_BoBaoTri, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnThongTinSanBongLayout.setVerticalGroup(
            pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinSanBongLayout.createSequentialGroup()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(pnThongTinSanBongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinSanBongLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnThemSB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBaoTri_BoBaoTri, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaGiaSan)
                        .addGap(395, 395, 395))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinSanBongLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(323, 323, 323))))
        );

        pnTong.add(pnThongTinSanBong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

        pnThongTinDichVu.setBackground(new java.awt.Color(254, 235, 208));
        pnThongTinDichVu.setForeground(new java.awt.Color(0, 0, 102));
        pnThongTinDichVu.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnThongTinDichVu.setPreferredSize(new java.awt.Dimension(1180, 900));

        jPanel51.setBackground(new java.awt.Color(255, 255, 255));
        jPanel51.setForeground(new java.awt.Color(0, 102, 102));
        jPanel51.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel139.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(0, 0, 102));
        jLabel139.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-stadium-48.png"))); // NOI18N
        jLabel139.setText("QUẢN LÝ SÂN BÓNG");

        lbThongTinSanBong_DV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinSanBong_DV.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinSanBong_DV.setText("Thông Tin Sân Bóng");
        lbThongTinSanBong_DV.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbThongTinSanBong_DVMouseMoved(evt);
            }
        });
        lbThongTinSanBong_DV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongTinSanBong_DVMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThongTinSanBong_DVMouseExited(evt);
            }
        });

        lbThongTinDichVu_DV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinDichVu_DV.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinDichVu_DV.setText("Thông Tin Dịch Vụ");

        lbHeSoKhungGio_DV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbHeSoKhungGio_DV.setForeground(new java.awt.Color(0, 0, 102));
        lbHeSoKhungGio_DV.setText("Hệ Số Khung Giờ");
        lbHeSoKhungGio_DV.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbHeSoKhungGio_DVMouseMoved(evt);
            }
        });
        lbHeSoKhungGio_DV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbHeSoKhungGio_DVMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbHeSoKhungGio_DVMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(lbThongTinSanBong_DV)
                        .addGap(105, 105, 105)
                        .addComponent(lbThongTinDichVu_DV)
                        .addGap(114, 114, 114)
                        .addComponent(lbHeSoKhungGio_DV))
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel139))
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel139)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbThongTinSanBong_DV)
                        .addComponent(lbThongTinDichVu_DV))
                    .addComponent(lbHeSoKhungGio_DV, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBackground(new java.awt.Color(179, 193, 135));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 102, 0));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-energy-drink-48.png"))); // NOI18N
        jLabel48.setText("THỨC ĂN VÀ THỨC UỐNG");

        tbThucAn_ThucUong.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbThucAn_ThucUong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN DỊCH VỤ", "ĐƠN VỊ", "GIÁ", "DANH MỤC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbThucAn_ThucUong.setRowHeight(32);
        tbThucAn_ThucUong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbThucAn_ThucUongMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbThucAn_ThucUong);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(202, 232, 249));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(37, 102, 142));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-bouncing-ball-50.png"))); // NOI18N
        jLabel49.setText("DỊCH VỤ KHÁC");

        tbDichVuKhac.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbDichVuKhac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN DỊCH VỤ", "ĐƠN VỊ", "GIÁ", "DANH MỤC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDichVuKhac.setGridColor(new java.awt.Color(0, 255, 102));
        tbDichVuKhac.setRowHeight(32);
        tbDichVuKhac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDichVuKhacMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tbDichVuKhac);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 102));
        jLabel51.setText("TÊN DỊCH VỤ:");
        jLabel51.setToolTipText("");

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(0, 0, 102));
        jLabel98.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-street-food-64.png"))); // NOI18N
        jLabel98.setText("DỊCH VỤ");

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 102));
        jLabel54.setText("GIÁ TIỀN:");
        jLabel54.setToolTipText("");

        txtTenDV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenDV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenDVMouseClicked(evt);
            }
        });

        txtGiaTienDV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtGiaTienDV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtGiaTienDVMouseClicked(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 102));
        jLabel55.setText("ĐƠN VỊ:");
        jLabel55.setToolTipText("");

        txtDonVi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDonVi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDonViMouseClicked(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 102));
        jLabel56.setText("DANH MỤC:");
        jLabel56.setToolTipText("");

        btnThemDV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemDV.setText("THÊM DỊCH VỤ");
        btnThemDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDVActionPerformed(evt);
            }
        });

        btnSuaDV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSuaDV.setText("SỬA DỊCH VỤ");
        btnSuaDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDVActionPerformed(evt);
            }
        });

        btnXoaDV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXoaDV.setText("XÓA DỊCH VỤ");
        btnXoaDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDVActionPerformed(evt);
            }
        });

        lblWarnTenDV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblWarnTenDV.setForeground(new java.awt.Color(255, 0, 0));
        lblWarnTenDV.setText("Tên dịch vụ không được để trống");
        lblWarnTenDV.setVisible(false);

        lblWarnGiaTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblWarnGiaTien.setForeground(new java.awt.Color(255, 0, 0));
        lblWarnGiaTien.setText("Giá tiền phải là số đúng dạng tiền tệ VND");
        lblWarnGiaTien.setVisible(false);

        lblWarnDonVi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblWarnDonVi.setForeground(new java.awt.Color(255, 0, 0));
        lblWarnDonVi.setText("Đơn vị không được trống");
        lblWarnDonVi.setVisible(false);

        cbxDanhMuc.setEnabled(false);

        jPanel19.setBackground(new java.awt.Color(204, 204, 204));
        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(37, 102, 142));
        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/cancel_subscription_48px.png"))); // NOI18N
        jLabel81.setText("NGỪNG CUNG CẤP");

        tbDVNgungCungCap.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbDVNgungCungCap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN DỊCH VỤ", "ĐƠN VỊ", "GIÁ", "DANH MỤC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDVNgungCungCap.setGridColor(new java.awt.Color(0, 255, 102));
        tbDVNgungCungCap.setRowHeight(32);
        tbDVNgungCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDVNgungCungCapMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tbDVNgungCungCap);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel81)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnThongTinDichVuLayout = new javax.swing.GroupLayout(pnThongTinDichVu);
        pnThongTinDichVu.setLayout(pnThongTinDichVuLayout);
        pnThongTinDichVuLayout.setHorizontalGroup(
            pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(pnThongTinDichVuLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel98)
                .addGap(96, 96, 96)
                .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addComponent(jLabel54)
                    .addComponent(jLabel55)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongTinDichVuLayout.createSequentialGroup()
                        .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenDV, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(txtGiaTienDV, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(txtDonVi, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(cbxDanhMuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWarnDonVi)
                            .addComponent(lblWarnGiaTien))
                        .addGap(119, 119, 119)
                        .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSuaDV)
                            .addComponent(btnThemDV)
                            .addComponent(btnXoaDV)))
                    .addComponent(lblWarnTenDV))
                .addGap(177, 177, 177))
            .addGroup(pnThongTinDichVuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        pnThongTinDichVuLayout.setVerticalGroup(
            pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinDichVuLayout.createSequentialGroup()
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongTinDichVuLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel98))
                    .addGroup(pnThongTinDichVuLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemDV)
                            .addComponent(jLabel51))
                        .addGap(2, 2, 2)
                        .addComponent(lblWarnTenDV)
                        .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnThongTinDichVuLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGiaTienDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel54))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblWarnGiaTien)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel55)))
                            .addGroup(pnThongTinDichVuLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(btnSuaDV)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarnDonVi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaDV)
                    .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel56)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(pnThongTinDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        pnTong.add(pnThongTinDichVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

        pnThongTinNhanVien.setBackground(new java.awt.Color(254, 235, 208));
        pnThongTinNhanVien.setForeground(new java.awt.Color(0, 0, 102));
        pnThongTinNhanVien.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnThongTinNhanVien.setPreferredSize(new java.awt.Dimension(1180, 900));

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setForeground(new java.awt.Color(0, 102, 102));
        jPanel31.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(0, 0, 102));
        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-people-working-together-50.png"))); // NOI18N
        jLabel80.setText("THÔNG TIN CON NGƯỜI");

        lbThongTinQuanLy_NV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinQuanLy_NV.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinQuanLy_NV.setText("Thông Tin Quản Lý");
        lbThongTinQuanLy_NV.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbThongTinQuanLy_NVMouseMoved(evt);
            }
        });
        lbThongTinQuanLy_NV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongTinQuanLy_NVMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThongTinQuanLy_NVMouseExited(evt);
            }
        });

        lbThongTinNhanVien_NV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinNhanVien_NV.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinNhanVien_NV.setText("Thông Tin Nhân Viên");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lbThongTinQuanLy_NV, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbThongTinNhanVien_NV, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel80))
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGap(205, 205, 205)
                                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(783, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel80)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbThongTinQuanLy_NV)
                    .addComponent(lbThongTinNhanVien_NV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel13.setBackground(new java.awt.Color(202, 232, 249));

        tbDSNV.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbDSNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Tên Nhân Viên", "CMND", "Số Điện Thoại", "Mật Khẩu", "Chức Vụ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDSNV.setRowHeight(30);
        tbDSNV.getTableHeader().setReorderingAllowed(false);
        tbDSNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDSNVMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbDSNV);
        if (tbDSNV.getColumnModel().getColumnCount() > 0) {
            tbDSNV.getColumnModel().getColumn(0).setResizable(false);
            tbDSNV.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbDSNV.getColumnModel().getColumn(1).setResizable(false);
            tbDSNV.getColumnModel().getColumn(1).setPreferredWidth(80);
            tbDSNV.getColumnModel().getColumn(2).setResizable(false);
            tbDSNV.getColumnModel().getColumn(2).setPreferredWidth(60);
            tbDSNV.getColumnModel().getColumn(3).setResizable(false);
            tbDSNV.getColumnModel().getColumn(3).setPreferredWidth(50);
            tbDSNV.getColumnModel().getColumn(4).setResizable(false);
            tbDSNV.getColumnModel().getColumn(4).setPreferredWidth(50);
            tbDSNV.getColumnModel().getColumn(5).setResizable(false);
            tbDSNV.getColumnModel().getColumn(5).setPreferredWidth(20);
        }

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-add-list-30.png"))); // NOI18N
        jLabel17.setText("DANH SÁCH NHÂN VIÊN");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        jPanel2.setBackground(new java.awt.Color(254, 235, 208));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnThemNV.setBackground(new java.awt.Color(254, 235, 208));

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 102));
        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-add-administrator-30.png"))); // NOI18N
        jLabel65.setText("THÊM NHÂN VIÊN");

        txtThemMaNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtThemMaNVMouseClicked(evt);
            }
        });

        txtThemTenNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtThemTenNVMouseClicked(evt);
            }
        });

        txtThemCMNDNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtThemCMNDNVMouseClicked(evt);
            }
        });

        txtThemSDTNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtThemSDTNVMouseClicked(evt);
            }
        });

        pwThemMKNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pwThemMKNVMouseClicked(evt);
            }
        });

        pwThemXNMKNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pwThemXNMKNVMouseClicked(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 102));
        jLabel35.setText("MÃ NHÂN VIÊN:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 102));
        jLabel36.setText("TÊN NHÂN VIÊN:");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 102));
        jLabel37.setText("CMND:");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 102));
        jLabel38.setText("SĐT:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 102));
        jLabel39.setText("MẬT KHẨU:");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 102));
        jLabel40.setText("XÁC NHẬN MK:");

        lblTenNvWarn_ThemNV.setForeground(new java.awt.Color(255, 0, 0));
        lblTenNvWarn_ThemNV.setText("Tên nhân viên không được để trống");
        lblTenNvWarn_ThemNV.setPreferredSize(new java.awt.Dimension(150, 16));
        lblTenNvWarn_ThemNV.setVisible(false);

        javax.swing.GroupLayout pnThemNVLayout = new javax.swing.GroupLayout(pnThemNV);
        pnThemNV.setLayout(pnThemNVLayout);
        pnThemNVLayout.setHorizontalGroup(
            pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemNVLayout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel65)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnThemNVLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel37)
                    .addComponent(jLabel36)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThemNVLayout.createSequentialGroup()
                        .addComponent(txtThemMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(29, Short.MAX_VALUE))
                    .addGroup(pnThemNVLayout.createSequentialGroup()
                        .addGroup(pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtThemTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(txtThemCMNDNV, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(txtThemSDTNV, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(pwThemMKNV, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(pwThemXNMKNV, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(lblTenNvWarn_ThemNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnThemNVLayout.setVerticalGroup(
            pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemNVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel65)
                .addGap(18, 18, 18)
                .addGroup(pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThemMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(19, 19, 19)
                .addGroup(pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThemTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(2, 2, 2)
                .addComponent(lblTenNvWarn_ThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThemCMNDNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(18, 18, 18)
                .addGroup(pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThemSDTNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(23, 23, 23)
                .addGroup(pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwThemMKNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addGap(18, 18, 18)
                .addGroup(pnThemNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwThemXNMKNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel2.add(pnThemNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 450, 340));

        pnSuaXoaNV.setBackground(new java.awt.Color(254, 235, 208));

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(37, 102, 142));
        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8_delete_user_male_30px_1.png"))); // NOI18N
        jLabel68.setText("SỬA VÀ XÓA NHÂN VIÊN");

        txtSuaTenNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSuaTenNVMouseClicked(evt);
            }
        });

        pwSuaMKNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pwSuaMKNVMouseClicked(evt);
            }
        });

        txtSuaCMNDNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSuaCMNDNVMouseClicked(evt);
            }
        });

        txtSuaSDTNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSuaSDTNVMouseClicked(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 102));
        jLabel43.setText("MÃ NHÂN VIÊN:");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 102));
        jLabel44.setText("TÊN NHÂN VIÊN:");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 102));
        jLabel45.setText("CMND:");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 102));
        jLabel46.setText("SĐT:");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 102));
        jLabel47.setText("MẬT KHẨU:");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 102));
        jLabel50.setText("CHỨC VỤ:");

        javax.swing.GroupLayout pnSuaXoaNVLayout = new javax.swing.GroupLayout(pnSuaXoaNV);
        pnSuaXoaNV.setLayout(pnSuaXoaNVLayout);
        pnSuaXoaNVLayout.setHorizontalGroup(
            pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSuaXoaNVLayout.createSequentialGroup()
                .addGroup(pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnSuaXoaNVLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(jLabel45)
                            .addComponent(jLabel44)
                            .addComponent(jLabel46)
                            .addComponent(jLabel47)
                            .addComponent(jLabel50))
                        .addGap(48, 48, 48)
                        .addGroup(pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSuaCMNDNV)
                            .addComponent(txtSuaTenNV)
                            .addComponent(txtSuaMaNV)
                            .addComponent(txtSuaSDTNV)
                            .addComponent(pwSuaMKNV)
                            .addComponent(txtChucVuNV, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                    .addGroup(pnSuaXoaNVLayout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel68)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        pnSuaXoaNVLayout.setVerticalGroup(
            pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSuaXoaNVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel68)
                .addGap(18, 18, 18)
                .addGroup(pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSuaMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGap(18, 18, 18)
                .addGroup(pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSuaTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addGap(18, 18, 18)
                .addGroup(pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSuaCMNDNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addGap(18, 18, 18)
                .addGroup(pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSuaSDTNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addGap(18, 18, 18)
                .addGroup(pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwSuaMKNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addGap(18, 18, 18)
                .addGroup(pnSuaXoaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChucVuNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jPanel2.add(pnSuaXoaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 340));

        btnThemNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemNV.setText("THÊM NHÂN VIÊN");
        btnThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNVActionPerformed(evt);
            }
        });

        btnSuaNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSuaNV.setText("SỬA THÔNG TIN");
        btnSuaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaNVActionPerformed(evt);
            }
        });

        btnXoaNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXoaNV.setText("XÓA NHÂN VIÊN");
        btnXoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnThongTinNhanVienLayout = new javax.swing.GroupLayout(pnThongTinNhanVien);
        pnThongTinNhanVien.setLayout(pnThongTinNhanVienLayout);
        pnThongTinNhanVienLayout.setHorizontalGroup(
            pnThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinNhanVienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinNhanVienLayout.createSequentialGroup()
                        .addComponent(btnSuaNV)
                        .addGap(155, 155, 155))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinNhanVienLayout.createSequentialGroup()
                        .addGroup(pnThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnXoaNV)
                            .addGroup(pnThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnThemNV)))
                        .addGap(9, 9, 9)))
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnThongTinNhanVienLayout.setVerticalGroup(
            pnThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinNhanVienLayout.createSequentialGroup()
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113)
                .addGroup(pnThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnThongTinNhanVienLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnThemNV)
                        .addGap(32, 32, 32)
                        .addComponent(btnSuaNV)
                        .addGap(32, 32, 32)
                        .addComponent(btnXoaNV)))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        pnTong.add(pnThongTinNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

        pnHeSoKhungGio.setBackground(new java.awt.Color(254, 235, 208));
        pnHeSoKhungGio.setForeground(new java.awt.Color(0, 0, 102));
        pnHeSoKhungGio.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnHeSoKhungGio.setPreferredSize(new java.awt.Dimension(1180, 900));

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setForeground(new java.awt.Color(0, 102, 102));
        jPanel41.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(0, 0, 102));
        jLabel111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-stadium-48.png"))); // NOI18N
        jLabel111.setText("QUẢN LÝ SÂN BÓNG");

        lbThongTinSanBong_HSKG.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinSanBong_HSKG.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinSanBong_HSKG.setText("Thông Tin Sân Bóng");
        lbThongTinSanBong_HSKG.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbThongTinSanBong_HSKGMouseMoved(evt);
            }
        });
        lbThongTinSanBong_HSKG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongTinSanBong_HSKGMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThongTinSanBong_HSKGMouseExited(evt);
            }
        });

        lbThongTinDichVu_HSKG.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinDichVu_HSKG.setForeground(new java.awt.Color(0, 0, 102));
        lbThongTinDichVu_HSKG.setText("Thông Tin Dịch Vụ");
        lbThongTinDichVu_HSKG.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbThongTinDichVu_HSKGMouseMoved(evt);
            }
        });
        lbThongTinDichVu_HSKG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongTinDichVu_HSKGMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThongTinDichVu_HSKGMouseExited(evt);
            }
        });

        lbHeSoKhungGio_HSKG.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbHeSoKhungGio_HSKG.setForeground(new java.awt.Color(0, 0, 102));
        lbHeSoKhungGio_HSKG.setText("Hệ Số Khung Giờ");

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(lbThongTinSanBong_HSKG)
                        .addGap(105, 105, 105)
                        .addComponent(lbThongTinDichVu_HSKG)
                        .addGap(114, 114, 114)
                        .addComponent(lbHeSoKhungGio_HSKG))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel111)))
                .addContainerGap(520, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(488, 488, 488))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel111)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbThongTinSanBong_HSKG)
                        .addComponent(lbThongTinDichVu_HSKG))
                    .addComponent(lbHeSoKhungGio_HSKG, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 102));
        jLabel41.setText("MÃ KHUNG GIỜ:");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 102));
        jLabel42.setText("KHUNG GIỜ:");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 102));
        jLabel52.setText("HỆ SỐ");

        jPanel14.setBackground(new java.awt.Color(202, 232, 249));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(37, 102, 142));
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-night-80.png"))); // NOI18N
        jLabel53.setText("TỐI");

        tbHeSoKhungGio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "MÃ KG", "KHUNG GIỜ", "HỆ SỐ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbHeSoKhungGio.setGridColor(new java.awt.Color(102, 102, 102));
        tbHeSoKhungGio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHeSoKhungGioMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tbHeSoKhungGio);
        if (tbHeSoKhungGio.getColumnModel().getColumnCount() > 0) {
            tbHeSoKhungGio.getColumnModel().getColumn(0).setPreferredWidth(1);
            tbHeSoKhungGio.getColumnModel().getColumn(2).setPreferredWidth(1);
        }

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(37, 102, 142));
        jLabel57.setText("HỆ SỐ KHUNG GIỜ");

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(37, 102, 142));
        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-afternoon-80.png"))); // NOI18N
        jLabel58.setText("CHIỀU");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(37, 102, 142));
        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-sunrise-80.png"))); // NOI18N
        jLabel59.setText("SÁNG");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel58)
                            .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel57)
                .addGap(141, 141, 141))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jLabel57)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(jLabel53)
                .addGap(59, 59, 59))
        );

        btnSuaHSKG.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSuaHSKG.setText("SỬA KHUNG GIỜ");
        btnSuaHSKG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaHSKGActionPerformed(evt);
            }
        });

        lbMaKG.setBackground(new java.awt.Color(202, 232, 249));

        javax.swing.GroupLayout pnHeSoKhungGioLayout = new javax.swing.GroupLayout(pnHeSoKhungGio);
        pnHeSoKhungGio.setLayout(pnHeSoKhungGioLayout);
        pnHeSoKhungGioLayout.setHorizontalGroup(
            pnHeSoKhungGioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(pnHeSoKhungGioLayout.createSequentialGroup()
                .addGroup(pnHeSoKhungGioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnHeSoKhungGioLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(pnHeSoKhungGioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(jLabel41)
                            .addComponent(jLabel52))
                        .addGap(62, 62, 62)
                        .addGroup(pnHeSoKhungGioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKhungGio, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                            .addComponent(txtHeSo, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                            .addComponent(lbMaKG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnHeSoKhungGioLayout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addComponent(btnSuaHSKG)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnHeSoKhungGioLayout.setVerticalGroup(
            pnHeSoKhungGioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHeSoKhungGioLayout.createSequentialGroup()
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addGroup(pnHeSoKhungGioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnHeSoKhungGioLayout.createSequentialGroup()
                        .addGroup(pnHeSoKhungGioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(lbMaKG))
                        .addGap(35, 35, 35)
                        .addGroup(pnHeSoKhungGioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(txtKhungGio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(pnHeSoKhungGioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(txtHeSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addComponent(btnSuaHSKG))
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(263, Short.MAX_VALUE))
        );

        pnTong.add(pnHeSoKhungGio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

        pnThongKe.setBackground(new java.awt.Color(254, 235, 208));
        pnThongKe.setForeground(new java.awt.Color(0, 0, 102));
        pnThongKe.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnThongKe.setPreferredSize(new java.awt.Dimension(1180, 900));

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setForeground(new java.awt.Color(0, 102, 102));
        jPanel39.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(0, 0, 102));
        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-statistics-50.png"))); // NOI18N
        jLabel87.setText("THỐNG KÊ");

        lbThongKe_ThongKe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongKe_ThongKe.setForeground(new java.awt.Color(0, 0, 102));
        lbThongKe_ThongKe.setText("Thống Kê");

        lbBieuDoThongKe_ThongKe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbBieuDoThongKe_ThongKe.setForeground(new java.awt.Color(0, 0, 102));
        lbBieuDoThongKe_ThongKe.setText("Biểu Đồ Thống Kê");
        lbBieuDoThongKe_ThongKe.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbBieuDoThongKe_ThongKeMouseMoved(evt);
            }
        });
        lbBieuDoThongKe_ThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbBieuDoThongKe_ThongKeMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbBieuDoThongKe_ThongKeMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lbThongKe_ThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(lbBieuDoThongKe_ThongKe))
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel87))
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator26, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(797, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel87)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbBieuDoThongKe_ThongKe, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbThongKe_ThongKe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator26, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 102));
        jLabel70.setText("TIỀN THUÊ SÂN:");

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 102));
        jLabel71.setText("TỪ NGÀY:");

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 0, 102));
        jLabel72.setText("ĐẾN NGÀY:");

        cbLoaiSanTKDT.setBackground(new java.awt.Color(153, 255, 255));
        cbLoaiSanTKDT.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        cbLoaiSanTKDT.setForeground(new java.awt.Color(255, 255, 255));
        cbLoaiSanTKDT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất Cả", "Sân 5", "Sân 7", "Sân 11" }));
        cbLoaiSanTKDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiSanTKDTActionPerformed(evt);
            }
        });

        jPanel15.setBackground(new java.awt.Color(200, 232, 249));

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(37, 102, 142));
        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-add-list-30.png"))); // NOI18N
        jLabel69.setText("CHI TIẾT THUÊ");

        tbTKDT.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbTKDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ HÓA ĐƠN", "TÊN SÂN", "LOẠI SÂN", "NGÀY THUÊ", "GIỜ ĐẾN", "GIỜ TRẢ", "GIÁ THUÊ SÂN", "TIỀN DỊCH VỤ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbTKDT.setRowHeight(30);
        jScrollPane10.setViewportView(tbTKDT);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(435, Short.MAX_VALUE)
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(415, 415, 415))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(0, 0, 102));
        jLabel73.setText("CHỌN LOẠI SÂN:");

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(0, 0, 102));
        jLabel74.setText("TIỀN DỊCH VỤ:");

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 102));
        jLabel75.setText("TỔNG TIỀN");

        lbTienThueSan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbTienThueSan.setForeground(new java.awt.Color(0, 0, 102));
        lbTienThueSan.setText("TỔNG TIỀN:");

        lbTienDV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbTienDV.setForeground(new java.awt.Color(0, 0, 102));
        lbTienDV.setText("TỔNG TIỀN:");

        lbTongTien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbTongTien.setForeground(new java.awt.Color(0, 0, 102));
        lbTongTien.setText("TỔNG TIỀN:");

        javax.swing.GroupLayout pnThongKeLayout = new javax.swing.GroupLayout(pnThongKe);
        pnThongKe.setLayout(pnThongKeLayout);
        pnThongKeLayout.setHorizontalGroup(
            pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(pnThongKeLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongKeLayout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addGap(67, 67, 67)
                        .addComponent(dcTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(jLabel72)
                        .addGap(65, 65, 65)
                        .addComponent(dcDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnThongKeLayout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addGap(67, 67, 67)
                        .addComponent(cbLoaiSanTKDT, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongKeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongKeLayout.createSequentialGroup()
                        .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70)
                            .addComponent(jLabel74)
                            .addComponent(jLabel75))
                        .addGap(87, 87, 87)
                        .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTongTien)
                            .addComponent(lbTienDV)
                            .addComponent(lbTienThueSan)))
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
        );
        pnThongKeLayout.setVerticalGroup(
            pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongKeLayout.createSequentialGroup()
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongKeLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbLoaiSanTKDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel73))
                        .addGap(39, 39, 39)
                        .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dcTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel71)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongKeLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel72, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dcDenNgay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(41, 41, 41)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(lbTienThueSan))
                .addGap(35, 35, 35)
                .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(lbTienDV))
                .addGap(43, 43, 43)
                .addGroup(pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(lbTongTien))
                .addGap(31, 31, 31))
        );

        pnTong.add(pnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

        pnBieuDoThongKe.setBackground(new java.awt.Color(254, 235, 208));
        pnBieuDoThongKe.setForeground(new java.awt.Color(0, 0, 102));
        pnBieuDoThongKe.setMaximumSize(new java.awt.Dimension(9999, 9999));
        pnBieuDoThongKe.setPreferredSize(new java.awt.Dimension(1180, 900));

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setForeground(new java.awt.Color(0, 102, 102));
        jPanel42.setPreferredSize(new java.awt.Dimension(1150, 110));

        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 0, 102));
        jLabel88.setIcon(new javax.swing.ImageIcon(getClass().getResource("/QuanLi/HINHANH/icons8-statistics-50.png"))); // NOI18N
        jLabel88.setText("THỐNG KÊ");

        lbThongKe_BDTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongKe_BDTK.setForeground(new java.awt.Color(0, 0, 102));
        lbThongKe_BDTK.setText("Thống Kê");
        lbThongKe_BDTK.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbThongKe_BDTKMouseMoved(evt);
            }
        });
        lbThongKe_BDTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongKe_BDTKMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThongKe_BDTKMouseExited(evt);
            }
        });

        lbBieuDoThongKe_BDTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbBieuDoThongKe_BDTK.setForeground(new java.awt.Color(0, 0, 102));
        lbBieuDoThongKe_BDTK.setText("Biểu Đồ Thống Kê");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lbThongKe_BDTK, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(lbBieuDoThongKe_BDTK))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel88))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addComponent(jSeparator27, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbBieuDoThongKe_BDTK, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbThongKe_BDTK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator27, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlBieuDoView.setBackground(new java.awt.Color(254, 235, 208));
        pnlBieuDoView.setPreferredSize(new java.awt.Dimension(1180, 732));

        javax.swing.GroupLayout pnlBieuDoViewLayout = new javax.swing.GroupLayout(pnlBieuDoView);
        pnlBieuDoView.setLayout(pnlBieuDoViewLayout);
        pnlBieuDoViewLayout.setHorizontalGroup(
            pnlBieuDoViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1180, Short.MAX_VALUE)
        );
        pnlBieuDoViewLayout.setVerticalGroup(
            pnlBieuDoViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 726, Short.MAX_VALUE)
        );

        cbxChonTKTheo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbxChonTKTheo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ngày", "Tháng", "Quý" }));
        cbxChonTKTheo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxChonTKTheoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Dựng biểu đồ theo: ");

        javax.swing.GroupLayout pnBieuDoThongKeLayout = new javax.swing.GroupLayout(pnBieuDoThongKe);
        pnBieuDoThongKe.setLayout(pnBieuDoThongKeLayout);
        pnBieuDoThongKeLayout.setHorizontalGroup(
            pnBieuDoThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
            .addGroup(pnBieuDoThongKeLayout.createSequentialGroup()
                .addComponent(pnlBieuDoView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnBieuDoThongKeLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(cbxChonTKTheo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnBieuDoThongKeLayout.setVerticalGroup(
            pnBieuDoThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBieuDoThongKeLayout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(pnBieuDoThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxChonTKTheo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBieuDoView, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnTong.add(pnBieuDoThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 900));

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

    private void lbDangXuatMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangXuatMouseMoved
        // TODO add your handling code here:
        lbDangXuat.setForeground(Color.red);
    }//GEN-LAST:event_lbDangXuatMouseMoved

    private void lbDangXuatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangXuatMouseExited
        // TODO add your handling code here:
        lbDangXuat.setForeground(new Color(0,153,153));
    }//GEN-LAST:event_lbDangXuatMouseExited

    private void lbNgayHienTaiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbNgayHienTaiMouseMoved
        // TODO add your handling code here:
        lbNgayHienTai.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbNgayHienTaiMouseMoved

    private void lbNgayHienTaiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbNgayHienTaiMouseExited
        // TODO add your handling code here:
        lbNgayHienTai.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbNgayHienTaiMouseExited

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

    private void pwMKCuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pwMKCuMouseClicked
        // TODO add your handling code here:
        pwMKCu.setForeground(Color.black);
    }//GEN-LAST:event_pwMKCuMouseClicked

    private void pwMKMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pwMKMoiMouseClicked
        // TODO add your handling code here:
        pwMKMoi.setForeground(Color.black);
    }//GEN-LAST:event_pwMKMoiMouseClicked

    private void pwMKXacNhanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pwMKXacNhanMouseClicked
        // TODO add your handling code here:
        pwMKXacNhan.setForeground(Color.black);
    }//GEN-LAST:event_pwMKXacNhanMouseClicked

    private void btnSuaThongTinQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaThongTinQLActionPerformed
        // TODO add your handling code here:
        if(btnSuaThongTinQL.getText().toString().equals("SỬA THÔNG TIN")){
            pnTTCNQL.setVisible(false);
            pnSuaThongTinQL.setVisible(true);
            pnDoiMatKhauQL.setVisible(false);
        
            btnSuaThongTinQL.setText("XÁC NHẬN SỬA");
            btnDoiMKQL.setText("ĐỔI MẬT KHẨU");
        }
        else if(btnSuaThongTinQL.getText().toString().equals("XÁC NHẬN SỬA"))
        {
            String tenQL=txtSuaTenQL.getText().toString();
            String CMND= txtSuaCMNDQL.getText().toString();
            
            boolean check=true;
            if(txtSuaTenQL.getText().length()==0)
            {
                lblWarnHoTen_Sua.setVisible(true);
                check=false;
            }
            if(CMND.matches("[0-9]{9}")==false&&CMND.matches("[0-9]{12}")==false)
            {
                lblWarnCMND_Sua.setVisible(true);
                check=false;
            }
            if(check==true){
                DSTAIKHOAN.suaQuanLy(tenQL, CMND, quanLy.getTaiKhoan());
                quanLy.setTenQL(tenQL);
                quanLy.setCMNDQL(CMND);                
                setThongTinQL();
                JOptionPane.showMessageDialog(this, "Sửa Thông Tin Thành Công");
                layDSQuanLy();
                lblWarnHoTen_Sua.setVisible(false);
                lblWarnCMND_Sua.setVisible(false);
                pnTTCNQL.setVisible(true);
                pnSuaThongTinQL.setVisible(false);
                pnDoiMatKhauQL.setVisible(false);
                btnSuaThongTinQL.setText("SỬA THÔNG TIN");
            }
        }
    }//GEN-LAST:event_btnSuaThongTinQLActionPerformed

    private void btnDoiMKQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMKQLActionPerformed
        // TODO add your handling code here:
        lbThongBaoLoi.setText("");
        if(btnDoiMKQL.getText().toString().equals("ĐỔI MẬT KHẨU"))
        {
            pnTTCNQL.setVisible(false);
            pnDoiMatKhauQL.setVisible(true);
            pnSuaThongTinQL.setVisible(false);
        
            btnDoiMKQL.setText("XÁC NHẬN ĐỔI");
            btnSuaThongTinQL.setText("SỬA THÔNG TIN");
            
        }
        else if(btnDoiMKQL.getText().toString().equals("XÁC NHẬN ĐỔI"))
        {
            String matKhauCu=pwMKCu.getText().toString();
            String matKhauMoi=pwMKMoi.getText().toString();
            String xacNhanMK=pwMKXacNhan.getText().toString();
            
            boolean check=true;
           
            
            if(matKhauMoi.matches("\\w{6,}")==false)
            {
                lbThongBaoLoi.setText("Mật khẩu mới không chuẩn");
                pwMKMoi.setForeground(Color.red);
                check=false;
            }
            if(xacNhanMK.matches("\\w{6,}")==false)
            {
                lbThongBaoLoi.setText("Xác Nhận Mật khẩu không chuẩn");
                pwMKXacNhan.setForeground(Color.red);
                check=false;
            }
            if(xacNhanMK.equals(matKhauMoi)==false)
            {
                lbThongBaoLoi.setText("Xác nhận mật khẩu sai");
                pwMKMoi.setForeground(Color.red);
                pwMKXacNhan.setForeground(Color.red);
                check=false;
            }
             if(matKhauCu.equals(quanLy.getMatKhau())==false)
            {
                pwMKCu.setForeground(Color.red);
                lbThongBaoLoi.setText("Mật khẩu cũ bị sai");
                check=false;
            }
             
            if(check==true){
                DSTAIKHOAN.suaDSTaiKhoan(quanLy.getTaiKhoan(), matKhauMoi);
                setThongTinQL();
                JOptionPane.showMessageDialog(this, "Đổi Mật Khẩu Thành Công");
                
                quanLy.loadThongTinQL(quanLy.getTaiKhoan());
                lbThongBaoLoi.setText("");
                pwMKCu.setText("");
                pwMKMoi.setText("");
                pwMKXacNhan.setText("");
                btnDoiMKQL.setText("ĐỔI MẬT KHẨU");
                pnTTCNQL.setVisible(true);
                pnDoiMatKhauQL.setVisible(false);
                pnSuaThongTinQL.setVisible(false);
            }     
        }
    }//GEN-LAST:event_btnDoiMKQLActionPerformed

    private void lbThongTinNhanVien_QLMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinNhanVien_QLMouseMoved
        // TODO add your handling code here:
        lbThongTinNhanVien_QL.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbThongTinNhanVien_QLMouseMoved

    private void lbThongTinNhanVien_QLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinNhanVien_QLMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(true);
        pnSuaXoaNV.setVisible(false);
        lbThongTinNhanVien_NV.setForeground(new Color(0,0,102));
        lbThongTinQuanLy_NV.setForeground(new Color(204,204,204));

        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_lbThongTinNhanVien_QLMouseClicked

    private void lbThongTinNhanVien_QLMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinNhanVien_QLMouseExited
        // TODO add your handling code here:
         lbThongTinNhanVien_QL.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbThongTinNhanVien_QLMouseExited

    private void lbThongTinDichVu_SBMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinDichVu_SBMouseMoved
        // TODO add your handling code here:
        lbThongTinDichVu_SB.setForeground(new Color(0,0,102));
        
    }//GEN-LAST:event_lbThongTinDichVu_SBMouseMoved

    private void lbThongTinDichVu_SBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinDichVu_SBMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(true);
        lbThongTinDichVu_DV.setForeground(new Color(0,0,102));
        lbThongTinSanBong_DV.setForeground(new Color(204,204,204));
        lbHeSoKhungGio_DV.setForeground(new Color(204,204,204));
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_lbThongTinDichVu_SBMouseClicked

    private void lbThongTinDichVu_SBMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinDichVu_SBMouseExited
        // TODO add your handling code here:
        lbThongTinDichVu_SB.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbThongTinDichVu_SBMouseExited

    private void lbHeSoKhungGio_SBMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbHeSoKhungGio_SBMouseMoved
        // TODO add your handling code here:
        lbHeSoKhungGio_SB.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbHeSoKhungGio_SBMouseMoved

    private void lbHeSoKhungGio_SBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbHeSoKhungGio_SBMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(true);
        lbHeSoKhungGio_HSKG.setForeground(new Color(0,0,102));
        lbThongTinDichVu_HSKG.setForeground(new Color(204,204,204));
        lbThongTinSanBong_HSKG.setForeground(new Color(204,204,204));
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_lbHeSoKhungGio_SBMouseClicked

    private void lbHeSoKhungGio_SBMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbHeSoKhungGio_SBMouseExited
        // TODO add your handling code here:
        lbHeSoKhungGio_SB.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbHeSoKhungGio_SBMouseExited

    private void btnQuanLySanBongMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQuanLySanBongMouseMoved
        // TODO add your handling code here:
        lbQuanLySanBong.setForeground(new Color(0,0,102));
        btnQuanLySanBong.setBackground(new Color(145,250,255));
    }//GEN-LAST:event_btnQuanLySanBongMouseMoved

    private void btnQuanLySanBongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQuanLySanBongMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(true);
        pnThemSB.setVisible(true);
        pnSuaGiaSan.setVisible(false);
        lbThongTinSanBong_SB.setForeground(new Color(0,0,102));
        lbThongTinDichVu_SB.setForeground(new Color(204,204,204));
        lbHeSoKhungGio_SB.setForeground(new Color(204,204,204));
        
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
        layDSSanBong();
    }//GEN-LAST:event_btnQuanLySanBongMouseClicked

    private void btnQuanLySanBongMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQuanLySanBongMouseExited
        // TODO add your handling code here:
        lbQuanLySanBong.setForeground(new Color(204,204,204));
	btnQuanLySanBong.setBackground(Color.white);
    }//GEN-LAST:event_btnQuanLySanBongMouseExited

    private void lbTenTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTenTaiKhoanMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(true);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        
        pnTTCNQL.setVisible(true);
        pnSuaThongTinQL.setVisible(false);
        pnDoiMatKhauQL.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
        lblWarnCMND_Sua.setVisible(false);
        lblWarnHoTen_Sua.setVisible(false);
    }//GEN-LAST:event_lbTenTaiKhoanMouseClicked

    private void lbThongTinDichVu_HSKGMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinDichVu_HSKGMouseMoved
        // TODO add your handling code here:
        lbThongTinDichVu_HSKG.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbThongTinDichVu_HSKGMouseMoved

    private void lbThongTinDichVu_HSKGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinDichVu_HSKGMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(true);
        lbThongTinDichVu_DV.setForeground(new Color(0,0,102));
        lbThongTinSanBong_DV.setForeground(new Color(204,204,204));
        lbHeSoKhungGio_DV.setForeground(new Color(204,204,204));
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_lbThongTinDichVu_HSKGMouseClicked

    private void lbThongTinDichVu_HSKGMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinDichVu_HSKGMouseExited
        // TODO add your handling code here:
        lbThongTinDichVu_HSKG.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbThongTinDichVu_HSKGMouseExited

    private void lbThongTinQuanLy_NVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinQuanLy_NVMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(true);
        lbThongTinQuanLy_QL.setForeground(new Color(0,0,102));
        lbThongTinNhanVien_QL.setForeground(new Color(204,204,204));

        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_lbThongTinQuanLy_NVMouseClicked

    private void lbThongTinSanBong_HSKGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinSanBong_HSKGMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(true);
        lbThongTinQuanLy_QL.setForeground(new Color(0,0,102));
        lbThongTinNhanVien_QL.setForeground(new Color(204,204,204));

        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_lbThongTinSanBong_HSKGMouseClicked

    private void lbThongTinQuanLy_NVMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinQuanLy_NVMouseMoved
        // TODO add your handling code here:
        lbThongTinQuanLy_NV.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbThongTinQuanLy_NVMouseMoved

    private void lbThongTinQuanLy_NVMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinQuanLy_NVMouseExited
        // TODO add your handling code here:
       lbThongTinQuanLy_NV.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbThongTinQuanLy_NVMouseExited

    private void lbThongTinSanBong_HSKGMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinSanBong_HSKGMouseMoved
        // TODO add your handling code here:
        lbThongTinSanBong_HSKG.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbThongTinSanBong_HSKGMouseMoved

    private void lbThongTinSanBong_HSKGMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinSanBong_HSKGMouseExited
        // TODO add your handling code here:
        lbThongTinSanBong_HSKG.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbThongTinSanBong_HSKGMouseExited

    private void lbGiayPhepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbGiayPhepMouseExited
        // TODO add your handling code here:
        lbGiayPhep.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbGiayPhepMouseExited

    private void lbGiayPhepMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbGiayPhepMouseMoved
        // TODO add your handling code here:
        lbGiayPhep.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbGiayPhepMouseMoved

    private void btnThongTinConNguoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongTinConNguoiMouseExited
        // TODO add your handling code here:
        lbThongTinConNguoi.setForeground(new Color(204,204,204));
        btnThongTinConNguoi.setBackground(Color.white);
    }//GEN-LAST:event_btnThongTinConNguoiMouseExited

    private void btnThongTinConNguoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongTinConNguoiMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(true);        
        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_btnThongTinConNguoiMouseClicked

    private void btnThongTinConNguoiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongTinConNguoiMouseMoved
        // TODO add your handling code here:
        lbThongTinConNguoi.setForeground(new Color(0,0,102));
        btnThongTinConNguoi.setBackground(new Color(145,250,255));
    }//GEN-LAST:event_btnThongTinConNguoiMouseMoved

    private void btnChucNangNhanVienMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChucNangNhanVienMouseMoved
        // TODO add your handling code here:
        lbChucNangNhanVien.setForeground(new Color(0,0,102));
        btnChucNangNhanVien.setBackground(new Color(145,250,255));
        
    }//GEN-LAST:event_btnChucNangNhanVienMouseMoved

    private void btnChucNangNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChucNangNhanVienMouseClicked
        GDNHANVIEN gdnhanvien = new GDNHANVIEN(quanLy.getTaiKhoan());
        gdnhanvien.setVisible(true);
    }//GEN-LAST:event_btnChucNangNhanVienMouseClicked

    private void btnChucNangNhanVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChucNangNhanVienMouseExited
        // TODO add your handling code here:
        lbChucNangNhanVien.setForeground(new Color(204,204,204));
	btnChucNangNhanVien.setBackground(Color.white);
    }//GEN-LAST:event_btnChucNangNhanVienMouseExited

    private void lbBieuDoThongKe_ThongKeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbBieuDoThongKe_ThongKeMouseMoved
        // TODO add your handling code here:
        lbBieuDoThongKe_ThongKe.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbBieuDoThongKe_ThongKeMouseMoved

    private void lbBieuDoThongKe_ThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbBieuDoThongKe_ThongKeMouseClicked
        // TODO add your handling code here:

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(true);
        lbBieuDoThongKe_BDTK.setForeground(new Color(0,0,102));
        lbThongKe_BDTK.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbBieuDoThongKe_ThongKeMouseClicked

    private void lbBieuDoThongKe_ThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbBieuDoThongKe_ThongKeMouseExited
        // TODO add your handling code here:
        lbBieuDoThongKe_ThongKe.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbBieuDoThongKe_ThongKeMouseExited

    private void lbThongKe_BDTKMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongKe_BDTKMouseMoved
        // TODO add your handling code here:
        lbThongKe_BDTK.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbThongKe_BDTKMouseMoved

    private void lbThongKe_BDTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongKe_BDTKMouseClicked
        // TODO add your handling code here:

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(true);
        lbThongKe_ThongKe.setForeground(new Color(0,0,102));
        lbBieuDoThongKe_ThongKe.setForeground(new Color(204,204,204));
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_lbThongKe_BDTKMouseClicked

    private void lbThongKe_BDTKMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongKe_BDTKMouseExited
        // TODO add your handling code here:
        lbThongKe_BDTK.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbThongKe_BDTKMouseExited

    private void txtSuaCMNDQLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSuaCMNDQLMouseClicked
        lblWarnCMND_Sua.setVisible(false);
    }//GEN-LAST:event_txtSuaCMNDQLMouseClicked

    private void txtSuaTenQLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSuaTenQLMouseClicked
        lblWarnHoTen_Sua.setVisible(false);
    }//GEN-LAST:event_txtSuaTenQLMouseClicked

    private void tbDSQLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDSQLMouseClicked
        // TODO add your handling code here:
        int i=tbDSQL.getSelectedRow();

        DefaultTableModel dtm= (DefaultTableModel ) tbDSQL.getModel();

        lbXoaTenQL.setText(dtm.getValueAt(i, 0).toString().trim());
        lbXoaCMNDQL.setText(dtm.getValueAt(i, 1).toString().trim());
        lbXoaTKQL.setText(dtm.getValueAt(i, 2).toString().trim());
        txtChucVuQL.setText(dtm.getValueAt(i, 3).toString().trim());

    }//GEN-LAST:event_tbDSQLMouseClicked

    private void txtThemTenQLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtThemTenQLMouseClicked
        // TODO add your handling code here:
        txtThemTenQL.setForeground(Color.black);
    }//GEN-LAST:event_txtThemTenQLMouseClicked

    private void txtThemCMNDQLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtThemCMNDQLMouseClicked
        // TODO add your handling code here:
        txtThemCMNDQL.setForeground(Color.black);
    }//GEN-LAST:event_txtThemCMNDQLMouseClicked

    private void btnThemQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemQLActionPerformed
        // TODO add your handling code here:
        pnThemQL.setVisible(true);
        pnXoaQL.setVisible(false);
        btnXoaQL.setText("XÓA QUẢN LÝ");
        String tenQL=txtThemTenQL.getText();
        String CMND=txtThemCMNDQL.getText();
        String taiKhoan=txtThemTKQL.getText();
        String matKhau=pwThemMKQL.getText();
        String xacNhanMK=pwThemXNMKQL.getText();
        String chucVu="QUANLY";

        boolean check=true;
        if(tenQL.isEmpty())
        {
            txtThemTenQL.setForeground(Color.red);
            JOptionPane.showMessageDialog(rootPane, "Tên quản lý không được trống");
            check=false;
        }
        if(CMND.matches("[0-9]{9}")==false&&CMND.matches("[0-9]{12}")==false)
        {
            txtThemCMNDQL.setForeground(Color.red);
            check=false;
        }
        if(taiKhoan.matches("\\w{1,}")==false)
        {
            txtThemTKQL.setForeground(Color.red);
            check=false;
        }
        if(matKhau.matches("\\w{6,}")==false)
        {           
            pwThemMKQL.setForeground(Color.red);
            check=false;
        }
        if(xacNhanMK.equals(matKhau)==false)
            {
                JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu sai!");
                pwThemMKQL.setForeground(Color.red);
                pwThemXNMKQL.setForeground(Color.red);
                check=false;
            }
        if(check==true){
            boolean kiemTra=DSTAIKHOAN.kiemTraQuanLy(CMND);
            if (kiemTra){
                JOptionPane.showMessageDialog(this, "Quản lý đã tồn tại, mời nhập lại thông tin");
            }
            else{
                boolean kiemTraTK=DSTAIKHOAN.kiemTraDSTK(taiKhoan);
                if(kiemTraTK){
                    JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại, mời nhập tài khoản khác!");
                }
                else{
                    DSTAIKHOAN.luuDSTK_QuanLy(taiKhoan, matKhau, tenQL, CMND);
                    JOptionPane.showMessageDialog(this, "Lưu tài khoản quản lý thành công");
                    layDSQuanLy();
                    pnTongThemQL.setVisible(true);
                    txtThemTenQL.setText("");
                    txtThemCMNDQL.setText("");
                    txtThemTKQL.setText("");
                    pwThemMKQL.setText("");
                    pwThemXNMKQL.setText("");
                }                
            }           
        }
    }//GEN-LAST:event_btnThemQLActionPerformed

    private void btnXoaQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaQLActionPerformed
        // TODO add your handling code here:
        if(btnXoaQL.getText().toString().equals("XÓA QUẢN LÝ"))
        {
            pnXoaQL.setVisible(true);
            pnThemQL.setVisible(false);
            txtChucVuQL.setEditable(false);
            btnXoaQL.setText("XÁC NHẬN XÓA");
        }
        else if(btnXoaQL.getText().toString().equals("XÁC NHẬN XÓA")){
            pnXoaQL.setVisible(true);
            pnThemQL.setVisible(false);
            
            String tenQL=lbXoaTenQL.getText();
            String CMND=lbXoaCMNDQL.getText();
            String taiKhoan=lbXoaTKQL.getText();
            String chucVu=txtChucVuQL.getText();

            boolean kiemTraHD=DSTAIKHOAN.kiemTraQL_DHD(taiKhoan);
            if (kiemTraHD){
                 Object[] objects={"Có","Không"};
                int choice =JOptionPane.showOptionDialog(this, "Quản lý này đã hoạt động, bạn có muốn đổi CHỨC VỤ cho quản lý này không?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, objects, objects[0]);
                if(choice==0){
                    DSTAIKHOAN.xoaTKQL_DHD(taiKhoan, "NGHI");
                    JOptionPane.showMessageDialog(this, "Đổi CHỨC VỤ cho quản lý thành công!");
                    layDSQuanLy();
                    btnXoaQL.setText("XÓA QUẢN LÝ");
                    lbXoaTenQL.setText("");
                    lbXoaCMNDQL.setText("");
                    lbXoaTKQL.setText("");
                    txtChucVuQL.setText("");
                }
            }
            else{
                Object[] objects={"Có","Không"};
                int choice =JOptionPane.showOptionDialog(this, "BẠN CÓ CHẮC CHẮN MUỐN XÓA KHÔNG ?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null , objects,objects[0]);
                if(choice==0){
                    DSTAIKHOAN.xoaQuanLy(taiKhoan);
                    JOptionPane.showMessageDialog(this, "Xóa quản lý thành công!");
                    layDSQuanLy();
                    btnXoaQL.setText("XÓA QUẢN LÝ");
                    lbXoaTenQL.setText("");
                    lbXoaCMNDQL.setText("");
                    lbXoaTKQL.setText("");
                    txtChucVuQL.setText("");
                    pnTongThemQL.setVisible(true);
                }
                else{
                    btnXoaQL.setText("XÓA QUẢN LÝ");
                }
                
            }
        }
    }//GEN-LAST:event_btnXoaQLActionPerformed

    private void txtThemTKQLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtThemTKQLMouseClicked
        // TODO add your handling code here:
        txtThemTKQL.setForeground(Color.black);
    }//GEN-LAST:event_txtThemTKQLMouseClicked

    private void pwThemMKQLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pwThemMKQLMouseClicked
        // TODO add your handling code here:
        pwThemMKQL.setForeground(Color.black);
    }//GEN-LAST:event_pwThemMKQLMouseClicked

    private void pwThemXNMKQLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pwThemXNMKQLMouseClicked
        // TODO add your handling code here:
        pwThemXNMKQL.setForeground(Color.black);
    }//GEN-LAST:event_pwThemXNMKQLMouseClicked

    private void tbDSNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDSNVMouseClicked
        // TODO add your handling code here:
        int i=tbDSNV.getSelectedRow();

        DefaultTableModel dtm= (DefaultTableModel ) tbDSNV.getModel();

        txtSuaMaNV.setText(dtm.getValueAt(i, 0).toString().trim());
        txtSuaTenNV.setText(dtm.getValueAt(i, 1).toString().trim());
        txtSuaCMNDNV.setText(dtm.getValueAt(i, 2).toString().trim());
        txtSuaSDTNV.setText(dtm.getValueAt(i, 3).toString().trim());
        pwSuaMKNV.setText(dtm.getValueAt(i, 4).toString().trim());
        txtChucVuNV.setText(dtm.getValueAt(i, 5).toString().trim());
    }//GEN-LAST:event_tbDSNVMouseClicked

    private void txtThemMaNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtThemMaNVMouseClicked
        // TODO add your handling code here:
        txtThemMaNV.setForeground(Color.black);       
    }//GEN-LAST:event_txtThemMaNVMouseClicked

    private void txtThemTenNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtThemTenNVMouseClicked
        // TODO add your handling code here:
        txtThemTenNV.setForeground(Color.black);
        lblTenNvWarn_ThemNV.setVisible(false);
    }//GEN-LAST:event_txtThemTenNVMouseClicked

    private void txtThemCMNDNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtThemCMNDNVMouseClicked
        // TODO add your handling code here:
        txtThemCMNDNV.setForeground(Color.black);
    }//GEN-LAST:event_txtThemCMNDNVMouseClicked

    private void txtThemSDTNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtThemSDTNVMouseClicked
        // TODO add your handling code here:
        txtThemSDTNV.setForeground(Color.black);
    }//GEN-LAST:event_txtThemSDTNVMouseClicked
    private void pwThemMKNVMouceClicked(java.awt.event.MouseEvent evt) {
        
    }
    private void btnThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNVActionPerformed
        // TODO add your handling code here:
        pnThemNV.setVisible(true);
        pnSuaXoaNV.setVisible(false);
        btnSuaNV.setText("SỬA THÔNG TIN");
        btnXoaNV.setText("XÓA NHÂN VIÊN");

        String maNV=txtThemMaNV.getText();
        String tenNV=txtThemTenNV.getText();
        String CMND=txtThemCMNDNV.getText();
        String SDT=txtThemSDTNV.getText();
        String matKhau=pwThemMKNV.getText();
        String xacNhanMK=pwThemXNMKNV.getText();

        boolean check=true;
        if(maNV.matches("\\w{2,}")==false){
            txtThemMaNV.setForeground(Color.red);
            check=false;
        }
        if(tenNV.isEmpty()){
            lblTenNvWarn_ThemNV.setVisible(true);
            txtThemTenNV.setForeground(Color.red);
            check=false;
        }
        if(CMND.matches("[0-9]{9}")==false&&CMND.matches("[0-9]{12}")==false){
            txtThemCMNDNV.setForeground(Color.red);
            check=false;
        }
        if(SDT.matches("[0]{1}+\\d{9}")==false){
            txtThemSDTNV.setForeground(Color.red);
            check=false;
        }
        if(matKhau.matches("\\w{6,}")==false){
            pwThemMKNV.setForeground(Color.red);
            check=false;
        }
        if(xacNhanMK.equals(matKhau)==false)
            {
                JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu sai!");
                pwThemMKNV.setForeground(Color.red);
                pwThemXNMKNV.setForeground(Color.red);
                check=false;
            }
        if(check==true){
            lblTenNvWarn_ThemNV.setVisible(false);
            boolean kiemTraTK=DSTAIKHOAN.kiemTraDSTK_NV(maNV);
            if(kiemTraTK){
                boolean kiemtraNV=DSTAIKHOAN.kiemTraNhanVien(maNV);
                if(kiemtraNV){
                    JOptionPane.showMessageDialog(this, "Tài khoản nhân viên này đã tồn tại, nếu bạn muốn sửa thông tin, vui lòng nhấn SỬA THÔNG TIN!");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại! Mời nhập tài khoản khác.");
                }
            }
            else{
                boolean kiemTraCMND=DSTAIKHOAN.kiemTraNhanVien_CMND(CMND);
                if(kiemTraCMND){
                    JOptionPane.showMessageDialog(this, "Đã tồn tại Nhân viên có CMND này, mời nhập lại CMND!");
                }
                else{
                    boolean kiemtraSDT=DSTAIKHOAN.kiemTraNhanVien_SDT(SDT);
                    if(kiemtraSDT){
                        JOptionPane.showMessageDialog(this, "Đã tồn tại Nhân viên có SDT này, mời nhập lại SDT!");
                    }
                    else{
                        DSTAIKHOAN.luuDSTK_NhanVien(maNV, matKhau, tenNV, CMND, SDT);
                        JOptionPane.showMessageDialog(this, "Lưu tài khoản nhân viên thành công");
                        layDSNhanVien();
                        txtThemMaNV.setText("");
                        txtThemTenNV.setText("");
                        txtThemCMNDNV.setText("");
                        txtThemSDTNV.setText("");
                        pwThemMKNV.setText("");
                        pwThemXNMKNV.setText("");
                    }
                }
            }
        }
    }//GEN-LAST:event_btnThemNVActionPerformed

    private void btnSuaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaNVActionPerformed
        // TODO add your handling code here:
        if(btnSuaNV.getText().toString().equals("SỬA THÔNG TIN")){
            pnSuaXoaNV.setVisible(true);
            pnThemNV.setVisible(false);
            txtSuaMaNV.setEditable(false);
            txtChucVuNV.setEditable(false);
            btnSuaNV.setText("XÁC NHẬN SỬA");
            btnThemNV.setText("THÊM NHÂN VIÊN");
            btnXoaNV.setText("XÓA NHÂN VIÊN");
        }
        else if(btnSuaNV.getText().toString().equals("XÁC NHẬN SỬA")){
            String maNV=txtSuaMaNV.getText();
            String tenNV=txtSuaTenNV.getText();
            String CMND=txtSuaCMNDNV.getText();
            String SDT=txtSuaSDTNV.getText();
            String matKhau=pwSuaMKNV.getText();

            boolean check=true;
            if(maNV.matches("\\w{2,}")==false){
                txtSuaMaNV.setForeground(Color.red);
                check=false;
            }
            if(tenNV.isEmpty()){
                txtSuaTenNV.setForeground(Color.red);
                JOptionPane.showMessageDialog(rootPane, "Họ tên nhân viên không được phép trống");
                check=false;
            }
            if(CMND.matches("[0-9]{9}")==false&&CMND.matches("[0-9]{12}")==false){
                txtSuaCMNDNV.setForeground(Color.red);
                check=false;
            }
            if(SDT.matches("[0]{1}+\\d{9}")==false){
                txtSuaSDTNV.setForeground(Color.red);
                check=false;
            }
            if(matKhau.matches("\\w{6,}")==false){
                pwSuaMKNV.setForeground(Color.red);
                check=false;               
            }
            if(check==true){
                Object[] objects={"Có","Không"};
                int choice =JOptionPane.showOptionDialog(this, "BẠN CÓ MUỐN SỬA ĐỔI THÔNG TIN NHÂN VIÊN KHÔNG?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null , objects,objects[0]);
                if(choice==0){
                    DSTAIKHOAN.suaNhanVien(maNV, tenNV, CMND, SDT, matKhau);
                    JOptionPane.showMessageDialog(this, "Sửa tài khoản nhân viên thành công");
                    layDSNhanVien();
                    btnSuaNV.setText("SỬA THÔNG TIN");
                }
                else{
                    btnSuaNV.setText("SỬA THÔNG TIN");
                }
                txtSuaMaNV.setText("");
                txtSuaTenNV.setText("");
                txtSuaCMNDNV.setText("");
                txtSuaSDTNV.setText("");
                pwSuaMKNV.setText("");
                txtChucVuNV.setText("");
            }
        }
    }//GEN-LAST:event_btnSuaNVActionPerformed

    private void pwThemMKNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pwThemMKNVMouseClicked
        // TODO add your handling code here:
        pwThemMKNV.setForeground(Color.black);
    }//GEN-LAST:event_pwThemMKNVMouseClicked

    private void pwThemXNMKNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pwThemXNMKNVMouseClicked
        // TODO add your handling code here:
        pwThemXNMKNV.setForeground(Color.black);
    }//GEN-LAST:event_pwThemXNMKNVMouseClicked

    private void btnXoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNVActionPerformed
        // TODO add your handling code here:
        if (btnXoaNV.getText().toString().equals("XÓA NHÂN VIÊN")){
            pnSuaXoaNV.setVisible(true);
            pnThemNV.setVisible(false);

            txtSuaMaNV.setEditable(false);
            txtSuaTenNV.setEditable(false);
            txtSuaCMNDNV.setEditable(false);
            txtSuaSDTNV.setEditable(false);
            pwSuaMKNV.setEditable(false);
            txtChucVuNV.setEditable(true);

            btnXoaNV.setText("XÁC NHẬN XÓA");
            btnThemNV.setText("THÊM NHÂN VIÊN");
            btnSuaNV.setText("SỬA THÔNG TIN");
        }
        else if(btnXoaNV.getText().toString().equals("XÁC NHẬN XÓA")){
            String maNV=txtSuaMaNV.getText();
            boolean kiemTraNVHD=DSTAIKHOAN.kiemTraNV_DHD(maNV);
            if(kiemTraNVHD){
                Object[] objects={"Có","Không"};
                int choice =JOptionPane.showOptionDialog(this, "Nhân viên bị xóa sẽ không thể đăng nhập lại để hoạt động.\n"
                        +"Bạn có chắc muốn xóa nhân viên này không?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, objects, objects[0]);
                if(choice==0){
                   DSTAIKHOAN.xoaTKNV_DHD(maNV, "NGHI"); 
                   JOptionPane.showMessageDialog(this, "Đổi CHỨC VỤ cho nhân viên thành công!");
                   layDSNhanVien();
                   btnXoaNV.setText("XÓA NHÂN VIÊN"); 
                }
                txtSuaMaNV.setEditable(false);            
                txtChucVuNV.setEditable(false);
                txtSuaMaNV.setText("");
                txtSuaTenNV.setText("");
                txtSuaCMNDNV.setText("");
                txtSuaSDTNV.setText("");
                pwSuaMKNV.setText("");
                txtChucVuNV.setText("");
            }
            else{
                Object[] objects={"Có","Không"};
                int choice =JOptionPane.showOptionDialog(this, "BẠN CÓ CHẮC CHẮN MUỐN XÓA NHÂN VIÊN NÀY KHÔNG?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, objects, objects[0]);
                if(choice==0){
                    DSTAIKHOAN.xoaNhanVien_TKNV(maNV);
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                    layDSNhanVien();
                    btnXoaNV.setText("XÓA NHÂN VIÊN");
                }
                else{
                    btnXoaNV.setText("XÓA NHÂN VIÊN");
                }
                txtSuaMaNV.setEditable(false);            
                txtChucVuNV.setEditable(false);
                txtSuaMaNV.setText("");
                txtSuaTenNV.setText("");
                txtSuaCMNDNV.setText("");
                txtSuaSDTNV.setText("");
                pwSuaMKNV.setText("");
                txtChucVuNV.setText("");
            }            
        }       
        txtSuaTenNV.setEditable(true);
        txtSuaCMNDNV.setEditable(true);
        txtSuaSDTNV.setEditable(true);
        pwSuaMKNV.setEditable(true);;
    }//GEN-LAST:event_btnXoaNVActionPerformed

    private void txtSuaTenNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSuaTenNVMouseClicked
        // TODO add your handling code here:
        txtSuaTenNV.setForeground(Color.black);
    }//GEN-LAST:event_txtSuaTenNVMouseClicked

    private void txtSuaCMNDNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSuaCMNDNVMouseClicked
        // TODO add your handling code here:
        txtSuaCMNDNV.setForeground(Color.black);
    }//GEN-LAST:event_txtSuaCMNDNVMouseClicked

    private void txtSuaSDTNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSuaSDTNVMouseClicked
        // TODO add your handling code here:
        txtSuaSDTNV.setForeground(Color.black);
    }//GEN-LAST:event_txtSuaSDTNVMouseClicked

    private void pwSuaMKNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pwSuaMKNVMouseClicked
        // TODO add your handling code here:
        pwSuaMKNV.setForeground(Color.black);
    }//GEN-LAST:event_pwSuaMKNVMouseClicked

    private void btnSuaHSKGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaHSKGActionPerformed
        // TODO add your handling code here:
        if (btnSuaHSKG.getText().toString().equals("SỬA KHUNG GIỜ")){
            btnSuaHSKG.setText("XÁC NHẬN SỬA");
        }
        else if(btnSuaHSKG.getText().toString().equals("XÁC NHẬN SỬA")){
            int maKG=Integer.parseInt(lbMaKG.getText());
            String khungGio=txtKhungGio.getText();
            float heSo=Float.parseFloat(txtHeSo.getText());
            
            boolean check=true;
            //
            if(check==true){
                DSTAIKHOAN.suaHSKhungGio(khungGio, heSo, maKG);
                JOptionPane.showMessageDialog(this, "Sửa hệ số khung giờ thành công!");
                layHSKhungGio();
                btnSuaHSKG.setText("SỬA KHUNG GIỜ");
                lbMaKG.setText("");
                txtKhungGio.setText("");
                txtHeSo.setText("");
            }        
        }
    }//GEN-LAST:event_btnSuaHSKGActionPerformed

    private void tbHeSoKhungGioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHeSoKhungGioMouseClicked
        // TODO add your handling code here:
        int i=tbHeSoKhungGio.getSelectedRow();
        DefaultTableModel dtm= (DefaultTableModel ) tbHeSoKhungGio.getModel();
        lbMaKG.setText(dtm.getValueAt(i, 0).toString());
        txtKhungGio.setText(dtm.getValueAt(i, 1).toString().trim());
        txtHeSo.setText(dtm.getValueAt(i, 2).toString().trim());
    }//GEN-LAST:event_tbHeSoKhungGioMouseClicked

    private void tbDSSBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDSSBMouseClicked
        // TODO add your handling code here:
        pnSuaGiaSan.setVisible(false);
        pnThemSB.setVisible(true);
        cbLoaiSan.setEnabled(false);
        btnSuaGiaSan.setText("SỬA GIÁ SÂN");
        btnThemSB.setText("THÊM SÂN BÓNG");
        int i=tbDSSB.getSelectedRow();
        DefaultTableModel dtm= (DefaultTableModel ) tbDSSB.getModel();
        txtMaSan.setText(dtm.getValueAt(i, 0).toString().trim());
        cbLoaiSan.setSelectedItem(dtm.getValueAt(i, 2).toString());
        lblTrangThaiSan.setText(dtm.getValueAt(i, 4).toString());
        if (TRANGTHAI.findTrangThai(lblTrangThaiSan.getText()) == TRANGTHAI.BAOTRI) {
            btnBaoTri_BoBaoTri.setText("KẾT THÚC BẢO TRÌ");
        }else  btnBaoTri_BoBaoTri.setText("BẢO TRÌ SÂN");
    }//GEN-LAST:event_tbDSSBMouseClicked

    private void btnThemSBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSBActionPerformed
        // TODO add your handling code here:
        btnSuaGiaSan.setText("SỬA GIÁ SÂN");
        btnBaoTri_BoBaoTri.setText("BẢO TRÌ SÂN");
        tbDSSB.clearSelection();
        pnThemSB.setVisible(true);
        pnSuaGiaSan.setVisible(false);
        txtMaSan.setEditable(true);
        cbLoaiSan.setEnabled(true);
        if (btnThemSB.getText().compareTo("THÊM SÂN BÓNG") == 0) {
            lblTrangThaiSan.setText(TRANGTHAI.TRONG.getTenTrangThai());
            txtMaSan.setText("");
            btnThemSB.setText("XÁC NHẬN THÊM");
        } else {
            String maSan = txtMaSan.getText();
            int trangThai = TRANGTHAI.findTrangThai(lblTrangThaiSan.getText()).getMatrangThai();
            int maLoaiSan = 0;
            if (cbLoaiSan.getSelectedItem().toString().equals("Sân 5")) {
                maLoaiSan = 1;
            } else if (cbLoaiSan.getSelectedItem().toString().equals("Sân 7")) {
                maLoaiSan = 2;
            } else if (cbLoaiSan.getSelectedItem().toString().equals("Sân 11")) {
                maLoaiSan = 3;
            }
            if (cbLoaiSan.getSelectedItem().toString().equals("Sân 5")) {
                if (maSan.matches("[5]{1}+[A-Z]{1}") == false) {
                    JOptionPane.showMessageDialog(rootPane, "Loại sân 5 có định dạng: '5' và thêm một chữ cái từ A-Z", "MÃ SÂN SAI ĐỊNH DẠNG", HEIGHT);
                    return;
                }
            } else if (cbLoaiSan.getSelectedItem().toString().equals("Sân 7")) {
                if (maSan.matches("[7]{1}+[A-Z]{1}") == false) {
                    JOptionPane.showMessageDialog(rootPane, "Loại sân 7 có định dạng: '7' và thêm một chữ cái từ A-Z", "MÃ SÂN SAI ĐỊNH DẠNG", HEIGHT);
                    return;
                }
            } else if (cbLoaiSan.getSelectedItem().toString().equals("Sân 11")) {
                if (maSan.matches("[1]{2}+[A-Z]{1}") == false) {
                    JOptionPane.showMessageDialog(rootPane, "Loại sân 11 có định dạng: '11' và thêm một chữ cái từ A-Z", "MÃ SÂN SAI ĐỊNH DẠNG", HEIGHT);
                    return;
                }
            }
            
            boolean kiemTraSB = DSTAIKHOAN.kiemTraSB(maSan);
            if (kiemTraSB) {
                JOptionPane.showMessageDialog(rootPane, "SÂN BÓNG NÀY ĐÃ TỒN TẠI");
            } else {
                DSTAIKHOAN.luuSanBong(maSan, maLoaiSan, trangThai);
                JOptionPane.showMessageDialog(this, "Lưu sân bóng thành công!");
                layDSSanBong();
                txtMaSan.setText("");
                lblTrangThaiSan.setText("");
                btnThemSB.setText("THÊM SÂN BÓNG");
            }
        }
    }//GEN-LAST:event_btnThemSBActionPerformed

    private void btnSuaGiaSanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaGiaSanActionPerformed
        // TODO add your handling code here:
        if(btnSuaGiaSan.getText().toString().equals("SỬA GIÁ SÂN"))
        {
            pnSuaGiaSan.setVisible(true);
            pnThemSB.setVisible(false);
            btnSuaGiaSan.setText("XÁC NHẬN SỬA");
            btnBaoTri_BoBaoTri.setText("BẢO TRÌ SÂN");
            btnThemSB.setText("THÊM SÂN BÓNG");
        }
        else if(btnSuaGiaSan.getText().toString().equals("XÁC NHẬN SỬA")){
            String loaiSan=cbSuaLoaiSan.getSelectedItem().toString();
            int giaTien=0;
            try {
                giaTien=Integer.parseInt(txtSuaGiaSan.getText());
            } catch (Exception e) {
                lblGiaLoaiSanWarn.setVisible(true);
            }
            boolean check=true;
            if(txtSuaGiaSan.getText().matches("[0-9]{1,}")==false){
                JOptionPane.showMessageDialog(this, "Mời nhập lại giá sân bóng!");
                check=false;
            }
            if(check==true){
                DSTAIKHOAN.suaGiaSan(loaiSan, giaTien);
                JOptionPane.showMessageDialog(this, "Sửa giá sân bóng thành công!");
                layDSSanBong();
                btnSuaGiaSan.setText("SỬA GIÁ SÂN");
                txtSuaGiaSan.setText("");
            }
        }
    }//GEN-LAST:event_btnSuaGiaSanActionPerformed

    private void btnBaoTri_BoBaoTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaoTri_BoBaoTriActionPerformed
        // TODO add your handling code here:
        int row = tbDSSB.getSelectedRow();
        pnSuaGiaSan.setVisible(false);
        pnThemSB.setVisible(true);
        txtMaSan.setEditable(false);
        if(row>=0){
            String maSan=txtMaSan.getText();
            
            if(iodata.trangThaiSan(maSan) == TRANGTHAI.DANGSUDUNG){
                JOptionPane.showMessageDialog(this, "Có khách đang thuê sân "+String.valueOf(tbDSSB.getValueAt(row, 0))+" nên không \n"
                        +" thể bảo trì bây giờ");
                layDSSanBong();
            }
            else if(TRANGTHAI.findTrangThai(lblTrangThaiSan.getText()) == TRANGTHAI.TRONG){
                DSTAIKHOAN.suaTrangThaiSan(maSan, String.valueOf(TRANGTHAI.BAOTRI.getMatrangThai()));
                JOptionPane.showMessageDialog(this, "Sân "+txtMaSan.getText() +" đã vào trạng thái bảo trì!");
                layDSSanBong();
                txtMaSan.setText("");
                lblTrangThaiSan.setText("");
            }
            else if(TRANGTHAI.findTrangThai(lblTrangThaiSan.getText()) == TRANGTHAI.BAOTRI){
                DSTAIKHOAN.suaTrangThaiSan(maSan, String.valueOf(TRANGTHAI.TRONG.getMatrangThai()));
                JOptionPane.showMessageDialog(this, "Bảo trì kết thúc. Sân "+txtMaSan.getText() +" đã sẵn sàng hoạt động!");
                layDSSanBong();
                txtMaSan.setText("");
                lblTrangThaiSan.setText("");
                btnBaoTri_BoBaoTri.setText("BẢO TRÌ SÂN");
            }
        }else JOptionPane.showMessageDialog(rootPane, "Hãy chọn sân muốn bảo trì");
    }//GEN-LAST:event_btnBaoTri_BoBaoTriActionPerformed

    private void cbLoaiSanTKDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiSanTKDTActionPerformed
        // TODO add your handling code here:
        String tuNgay=dcTuNgay.getDate().toLocaleString();
        String denNgay=dcDenNgay.getDate().toLocaleString();
        String loaiSan=cbLoaiSanTKDT.getSelectedItem().toString();
        if (cbLoaiSanTKDT.getSelectedItem().toString().equals("Tất Cả")){
            layTKDT_TatCa(tuNgay, denNgay);
        }
        else {
            layTKDT_LoaiSan(tuNgay, denNgay, loaiSan);
        }

        int tongTienThueSan = 0;
        int tongTienDV = 0;
        if (tbTKDT.getRowCount() > 0) {
            for (int row = 0; row < tbTKDT.getRowCount(); row++) {
                int tienSan = Integer.parseInt(tbTKDT.getValueAt(row, 6) + "");
                tongTienThueSan = tongTienThueSan + tienSan;

                int tienDV = Integer.parseInt(tbTKDT.getValueAt(row, 7) + "");
                tongTienDV = tongTienDV + tienDV;
            }
        }
        int tongTien = tongTienThueSan + tongTienDV;

        lbTienThueSan.setText(String.valueOf(tongTienThueSan));
        lbTienDV.setText(String.valueOf(tongTienDV));
        lbTongTien.setText(String.valueOf(tongTien));
    }//GEN-LAST:event_cbLoaiSanTKDTActionPerformed

    private void btnThongKeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMouseMoved
        lbThongKe.setForeground(new Color(0,0,102));
        btnThongKe.setBackground(new Color(145,250,255));
    }//GEN-LAST:event_btnThongKeMouseMoved

    private void btnThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMouseClicked
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);        
        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(true);
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_btnThongKeMouseClicked

    private void btnThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMouseExited
        lbThongKe.setForeground(new Color(204,204,204));
        btnThongKe.setBackground(Color.white);
    }//GEN-LAST:event_btnThongKeMouseExited

    private void lbDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangXuatMouseClicked
        new DANGNHAP().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lbDangXuatMouseClicked

    private void cbxChonTKTheoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxChonTKTheoActionPerformed
        if (cbxChonTKTheo.getSelectedIndex()==0) {
            dungBieuDo(dataSetNgay);
        }
        if (cbxChonTKTheo.getSelectedIndex()==1) {
            dungBieuDo(dataSetThang);
        }
        if (cbxChonTKTheo.getSelectedIndex()==2) {
            dungBieuDo(datasetQuy);
        }
    }//GEN-LAST:event_cbxChonTKTheoActionPerformed

    private void txtSuaGiaSanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSuaGiaSanKeyTyped
        lblGiaLoaiSanWarn.setVisible(false);
    }//GEN-LAST:event_txtSuaGiaSanKeyTyped

    private void btnXoaDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDVActionPerformed
        // TODO add your handling code here:
        if (btnXoaDV.getText().toString().equals("XÓA DỊCH VỤ")){
            btnThemDV.setText("THÊM DỊCH VỤ");
            btnSuaDV.setText("SỬA DỊCH VỤ");
            txtTenDV.setEditable(false);
            txtGiaTienDV.setEditable(false);
            txtDonVi.setEditable(false);
            cbxDanhMuc.setEnabled(true);
            btnXoaDV.setText("XÁC NHẬN XÓA");
        }
        else if(btnXoaDV.getText().toString().equals("XÁC NHẬN XÓA")){
            String tenDV=txtTenDV.getText();
            boolean check=true;
            if(check==true){
                boolean kiemTraDV_DSD=DSTAIKHOAN.kiemTraDV_DSD(tenDV);
                if(kiemTraDV_DSD){
                    Object[] objects={"Có","Không"};
                    int choice =JOptionPane.showOptionDialog(this, "Dịch vụ này đã được sử dụng, bạn có muốn đổi DANHMUC cho dịch vụ này không?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, objects, objects[0]);
                    if(choice==0){
                        DSTAIKHOAN.xoaDichVu_DSD(tenDV, 4);
                        setDichVuKhac();
                        setThucAn_ThucUong();
                        setDichVuNgungCungCap();
                        JOptionPane.showMessageDialog(this, "Đổi DANHMUC cho dịch vụ thành công!");
                        btnXoaDV.setText("XÓA DỊCH VỤ");
                        txtTenDV.setText("");
                        txtGiaTienDV.setText("");
                        txtDonVi.setText("");
                        cbxDanhMuc.setEnabled(false);
                    }
                }
                else{
                    Object[] objects={"Có","Không"};
                    int choice =JOptionPane.showOptionDialog(this, "BẠN CÓ CHẮC CHẮN MUỐN XÓA DỊCH VỤ NÀY KHÔNG?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, objects, objects[0]);
                    if(choice==0){
                        DSTAIKHOAN.xoaDichVu(tenDV);
                        JOptionPane.showMessageDialog(this, "Xóa dịch vụ thành công!");
                        setDichVuKhac();
                        setThucAn_ThucUong();
                        setDichVuNgungCungCap();
                        btnXoaDV.setText("XÓA DỊCH VỤ");
                        txtTenDV.setText("");
                        txtGiaTienDV.setText("");
                        txtDonVi.setText("");
                        cbxDanhMuc.setEnabled(false);
                    }
                    else{
                        btnXoaDV.setText("XÓA DỊCH VỤ");
                        txtTenDV.setText("");
                        txtGiaTienDV.setText("");
                        txtDonVi.setText("");
                        cbxDanhMuc.setEnabled(false);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnXoaDVActionPerformed

    private void btnSuaDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDVActionPerformed
        // TODO add your handling code here:
        if (btnSuaDV.getText().toString().equals("SỬA DỊCH VỤ")) {
            btnXoaDV.setText("XÓA DỊCH VỤ");
            btnThemDV.setText("THÊM DỊCH VỤ");
            btnSuaDV.setText("XÁC NHẬN SỬA");
            txtTenDV.setEditable(false);
            txtGiaTienDV.setEditable(true);
            txtDonVi.setEditable(true);
            cbxDanhMuc.setEnabled(true);
        } else if (btnSuaDV.getText().toString().equals("XÁC NHẬN SỬA")) {
            if (xetNgoaiLeDichVu()) {
                String tenDV = txtTenDV.getText();
                int giaTien = Integer.parseInt(txtGiaTienDV.getText());
                String donVi = txtDonVi.getText();
                int maDanhMuc = ((DANHMUC) cbxDanhMuc.getSelectedItem()).getMaDanhMuc();
                boolean kiemTra = DSTAIKHOAN.kiemTraDV(tenDV);
                if (kiemTra) {
                    DSTAIKHOAN.suaDichVu(tenDV, donVi, giaTien,maDanhMuc);
                    JOptionPane.showMessageDialog(this, "Sửa dịch vụ thành công!");
                    setThucAn_ThucUong();
                    setDichVuKhac();
                    setDichVuNgungCungCap();
                    txtTenDV.setText("");
                    txtGiaTienDV.setText("");
                    txtDonVi.setText("");
                    cbxDanhMuc.setEnabled(false);
                    btnSuaDV.setText("SỬA DỊCH VỤ");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Tên dịch vụ không tồn tại");
                    txtTenDV.setText("");
                    txtGiaTienDV.setText("");
                    txtDonVi.setText("");
                    btnSuaDV.setText("SỬA DỊCH VỤ");
                    cbxDanhMuc.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_btnSuaDVActionPerformed

    private void btnThemDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDVActionPerformed
        // TODO add your handling code here:
        if (btnThemDV.getText().toString().equals("THÊM DỊCH VỤ")){
            btnXoaDV.setText("XÓA DỊCH VỤ");
            btnSuaDV.setText("SỬA DỊCH VỤ");
            txtTenDV.setEditable(true);
            txtGiaTienDV.setEditable(true);
            txtDonVi.setEditable(true);
            btnThemDV.setText("XÁC NHẬN THÊM");
            cbxDanhMuc.setEnabled(true);
        } else if (btnThemDV.getText().toString().equals("XÁC NHẬN THÊM")) {
            if (xetNgoaiLeDichVu()) {
                String tenDV = txtTenDV.getText();
                int giaTien = Integer.parseInt(txtGiaTienDV.getText());
                String donVi = txtDonVi.getText();
                int maDanhMuc = ((DANHMUC) cbxDanhMuc.getSelectedItem()).getMaDanhMuc();
                boolean kiemTraDV = DSTAIKHOAN.kiemTraDV(tenDV);
                if (kiemTraDV) {
                    Object[] objects = {"Có", "Không"};
                    int choice =JOptionPane.showOptionDialog(this, "DỊCH VỤ NÀY ĐÃ TỒN TẠI, BẠN CÓ MUỐN SỬA KHÔNG?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, objects, objects[0]);
                    if(choice==0){
                        DSTAIKHOAN.suaDichVu(tenDV, donVi, giaTien, maDanhMuc);
                        JOptionPane.showMessageDialog(this, "Sửa dịch vụ thành công!");
                        setThucAn_ThucUong();
                        setDichVuKhac();
                        setDichVuNgungCungCap();
                        txtTenDV.setText("");
                        txtGiaTienDV.setText("");
                        txtDonVi.setText("");
                        btnThemDV.setText("THÊM DỊCH VỤ");
                        cbxDanhMuc.setEnabled(false);
                    }
                }
                else{
                    DSTAIKHOAN.luuDichVu(tenDV, donVi, giaTien, maDanhMuc);
                    JOptionPane.showMessageDialog(this, "Lưu dịch vụ thành công!");
                    setThucAn_ThucUong();
                    setDichVuKhac();
                    setDichVuNgungCungCap();
                    txtTenDV.setText("");
                    txtGiaTienDV.setText("");
                    txtDonVi.setText("");
                    btnThemDV.setText("THÊM DỊCH VỤ");
                    cbxDanhMuc.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_btnThemDVActionPerformed

    private void txtDonViMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDonViMouseClicked
        // TODO add your handling code here:
        txtDonVi.setForeground(Color.black);
        lblWarnDonVi.setVisible(false);
    }//GEN-LAST:event_txtDonViMouseClicked

    private void txtGiaTienDVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtGiaTienDVMouseClicked
        // TODO add your handling code here:
        txtGiaTienDV.setForeground(Color.black);
        lblWarnGiaTien.setVisible(false);
    }//GEN-LAST:event_txtGiaTienDVMouseClicked

    private void txtTenDVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTenDVMouseClicked
        // TODO add your handling code here:
        txtTenDV.setForeground(Color.black);
        lblWarnTenDV.setVisible(false);
    }//GEN-LAST:event_txtTenDVMouseClicked

    private void tbDichVuKhacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDichVuKhacMouseClicked
        // TODO add your handling code here:
        int i=tbDichVuKhac.getSelectedRow();
        DefaultTableModel dtm= (DefaultTableModel ) tbDichVuKhac.getModel();
        txtTenDV.setText(dtm.getValueAt(i, 0).toString());
        txtDonVi.setText(dtm.getValueAt(i, 1).toString().trim());
        txtGiaTienDV.setText(dtm.getValueAt(i, 2).toString().trim());
        cbxDanhMuc.setSelectedItem(DANHMUC.findDanhMuc(String.valueOf(dtm.getValueAt(i, 3))));
        cbxDanhMuc.setEnabled(false);
    }//GEN-LAST:event_tbDichVuKhacMouseClicked

    private void tbThucAn_ThucUongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbThucAn_ThucUongMouseClicked
        // TODO add your handling code here:
        txtGiaTienDVMouseClicked(evt);
        txtDonViMouseClicked(evt);
        txtTenDVMouseClicked(evt);
        btnThemDV.setText("THÊM DỊCH VỤ");
        btnXoaDV.setText("XÓA DỊCH VỤ");
        btnSuaDV.setText("SỬA DỊCH VỤ");
        int i=tbThucAn_ThucUong.getSelectedRow();
        DefaultTableModel dtm= (DefaultTableModel ) tbThucAn_ThucUong.getModel();
        txtTenDV.setText(dtm.getValueAt(i, 0).toString());
        txtDonVi.setText(dtm.getValueAt(i, 1).toString().trim());
        txtGiaTienDV.setText(dtm.getValueAt(i, 2).toString().trim());
        cbxDanhMuc.setSelectedItem(DANHMUC.findDanhMuc(String.valueOf(dtm.getValueAt(i, 3))));
        cbxDanhMuc.setEnabled(false);
    }//GEN-LAST:event_tbThucAn_ThucUongMouseClicked

    private void lbHeSoKhungGio_DVMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbHeSoKhungGio_DVMouseExited
        // TODO add your handling code here:
        lbHeSoKhungGio_DV.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbHeSoKhungGio_DVMouseExited

    private void lbHeSoKhungGio_DVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbHeSoKhungGio_DVMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(false);
        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(true);
        lbHeSoKhungGio_HSKG.setForeground(new Color(0,0,102));
        lbThongTinDichVu_HSKG.setForeground(new Color(204,204,204));
        lbThongTinSanBong_HSKG.setForeground(new Color(204,204,204));
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_lbHeSoKhungGio_DVMouseClicked

    private void lbHeSoKhungGio_DVMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbHeSoKhungGio_DVMouseMoved
        // TODO add your handling code here:
        lbHeSoKhungGio_DV.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbHeSoKhungGio_DVMouseMoved

    private void lbThongTinSanBong_DVMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinSanBong_DVMouseExited
        // TODO add your handling code here:
        lbThongTinSanBong_DV.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_lbThongTinSanBong_DVMouseExited

    private void lbThongTinSanBong_DVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinSanBong_DVMouseClicked
        // TODO add your handling code here:
        pnThongTinCaNhan.setVisible(false);

        pnThongTinNhanVien.setVisible(false);
        pnThongTinQuanLy.setVisible(false);
        pnThongTinSanBong.setVisible(true);
        lbThongTinSanBong_SB.setForeground(new Color(0,0,102));
        lbThongTinDichVu_SB.setForeground(new Color(204,204,204));
        lbHeSoKhungGio_SB.setForeground(new Color(204,204,204));

        pnThongTinDichVu.setVisible(false);
        pnHeSoKhungGio.setVisible(false);
        pnThongKe.setVisible(false);
        pnBieuDoThongKe.setVisible(false);
    }//GEN-LAST:event_lbThongTinSanBong_DVMouseClicked

    private void lbThongTinSanBong_DVMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongTinSanBong_DVMouseMoved
        // TODO add your handling code here:
        lbThongTinSanBong_DV.setForeground(new Color(0,0,102));
    }//GEN-LAST:event_lbThongTinSanBong_DVMouseMoved

    private void txtSuaTenQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSuaTenQLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuaTenQLActionPerformed

    private void tbDVNgungCungCapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDVNgungCungCapMouseClicked
        // TODO add your handling code here:
        int i=tbDVNgungCungCap.getSelectedRow();
        DefaultTableModel dtm= (DefaultTableModel ) tbDVNgungCungCap.getModel();
        txtTenDV.setText(dtm.getValueAt(i, 0).toString());
        txtDonVi.setText(dtm.getValueAt(i, 1).toString().trim());
        txtGiaTienDV.setText(dtm.getValueAt(i, 2).toString().trim());
        cbxDanhMuc.setSelectedItem(DANHMUC.findDanhMuc(String.valueOf(dtm.getValueAt(i, 3))));
        cbxDanhMuc.setEnabled(false);
    }//GEN-LAST:event_tbDVNgungCungCapMouseClicked

    public boolean xetNgoaiLeDichVu() {
        boolean check = true;
        String tenDV = txtTenDV.getText();
        String giaTien = txtGiaTienDV.getText();
        String donVi = txtDonVi.getText();
        if (tenDV.length()==0) {
            check = false;
            lblWarnTenDV.setVisible(true);
        }
        if (donVi.length()==0) {
            check = false;
            lblWarnDonVi.setVisible(true);
        }
        if (!giaTien.matches("\\d+0{3,}")) {
            check = false;
            lblWarnGiaTien.setVisible(true);
        }
        if ((txtGiaTienDV.getText()).matches("[0-9]{1,}") == false) {
            txtGiaTienDV.setForeground(Color.red);
            check = false;
        }
        return check;
    }
    
    private void setDichVuNgungCungCap()
    {
        DefaultTableModel dtm=(DefaultTableModel) tbDVNgungCungCap.getModel();
        dtm.setNumRows(0);
       
         Connection connection=JDBCCONNECTION.getConnection();
        String sql="Select * from DICHVU,DANHMUC where DICHVU.DANHMUC=DANHMUC.MADANHMUC and DICHVU.DANHMUC='4'";
        Vector vt;
        try
        {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next())
            {
                vt =new Vector();
                vt.add(rs.getString("TENDV"));
                vt.add(rs.getString("DONVI"));
                vt.add(rs.getString("DONGIA"));
                vt.add(DANHMUC.findDanhMuc(Integer.parseInt(rs.getString("DANHMUC"))));
                dtm.addRow(vt);
            }
            
            tbDVNgungCungCap.setModel(dtm);
            rs.close();
            ps.close();
            connection.close();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi set dich vu khác!");
       }
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaoTri_BoBaoTri;
    private javax.swing.JPanel btnChucNangNhanVien;
    private javax.swing.JButton btnDoiMKQL;
    private javax.swing.JPanel btnQuanLySanBong;
    private javax.swing.JButton btnSuaDV;
    private javax.swing.JButton btnSuaGiaSan;
    private javax.swing.JButton btnSuaHSKG;
    private javax.swing.JButton btnSuaNV;
    private javax.swing.JButton btnSuaThongTinQL;
    private javax.swing.JButton btnThemDV;
    private javax.swing.JButton btnThemNV;
    private javax.swing.JButton btnThemQL;
    private javax.swing.JButton btnThemSB;
    private javax.swing.JPanel btnThongKe;
    private javax.swing.JPanel btnThongTinConNguoi;
    private javax.swing.JButton btnXoaDV;
    private javax.swing.JButton btnXoaNV;
    private javax.swing.JButton btnXoaQL;
    private javax.swing.JComboBox<String> cbLoaiSan;
    private javax.swing.JComboBox<String> cbLoaiSanTKDT;
    private javax.swing.JComboBox<String> cbSuaLoaiSan;
    private javax.swing.JComboBox<String> cbxChonTKTheo;
    private javax.swing.JComboBox<DANHMUC> cbxDanhMuc;
    private com.toedter.calendar.JDateChooser dcDenNgay;
    private com.toedter.calendar.JDateChooser dcTuNgay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
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
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lbAboutUs;
    private javax.swing.JLabel lbBieuDoThongKe_BDTK;
    private javax.swing.JLabel lbBieuDoThongKe_ThongKe;
    private javax.swing.JLabel lbCMNDQL;
    private javax.swing.JLabel lbChucNangNhanVien;
    private javax.swing.JLabel lbDangXuat;
    private javax.swing.JLabel lbGiayPhep;
    private javax.swing.JLabel lbHeSoKhungGio_DV;
    private javax.swing.JLabel lbHeSoKhungGio_HSKG;
    private javax.swing.JLabel lbHeSoKhungGio_SB;
    private javax.swing.JLabel lbLienHe;
    private javax.swing.JLabel lbMaKG;
    private javax.swing.JLabel lbNgayHienTai;
    private javax.swing.JLabel lbQuanLySanBong;
    private javax.swing.JLabel lbTenQL;
    private javax.swing.JLabel lbTenTaiKhoan;
    private javax.swing.JLabel lbThongBaoLoi;
    private javax.swing.JLabel lbThongKe;
    private javax.swing.JLabel lbThongKe_BDTK;
    private javax.swing.JLabel lbThongKe_ThongKe;
    private javax.swing.JLabel lbThongTinConNguoi;
    private javax.swing.JLabel lbThongTinDichVu_DV;
    private javax.swing.JLabel lbThongTinDichVu_HSKG;
    private javax.swing.JLabel lbThongTinDichVu_SB;
    private javax.swing.JLabel lbThongTinNhanVien_NV;
    private javax.swing.JLabel lbThongTinNhanVien_QL;
    private javax.swing.JLabel lbThongTinQuanLy_NV;
    private javax.swing.JLabel lbThongTinQuanLy_QL;
    private javax.swing.JLabel lbThongTinSanBong_DV;
    private javax.swing.JLabel lbThongTinSanBong_HSKG;
    private javax.swing.JLabel lbThongTinSanBong_SB;
    private javax.swing.JLabel lbTienDV;
    private javax.swing.JLabel lbTienThueSan;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JLabel lbTroGiup;
    private javax.swing.JLabel lbXoaCMNDQL;
    private javax.swing.JLabel lbXoaTKQL;
    private javax.swing.JLabel lbXoaTenQL;
    private javax.swing.JLabel lblGiaLoaiSanWarn;
    private javax.swing.JLabel lblTenNvWarn_ThemNV;
    private javax.swing.JLabel lblTrangThaiSan;
    private javax.swing.JLabel lblWarnCMND_Sua;
    private javax.swing.JLabel lblWarnDonVi;
    private javax.swing.JLabel lblWarnGiaTien;
    private javax.swing.JLabel lblWarnHoTen_Sua;
    private javax.swing.JLabel lblWarnTenDV;
    private javax.swing.JPanel pnBieuDoThongKe;
    private javax.swing.JPanel pnDSQL;
    private javax.swing.JPanel pnDoiMatKhauQL;
    private javax.swing.JPanel pnHeSoKhungGio;
    private javax.swing.JPanel pnMenu;
    private javax.swing.JPanel pnSuaGiaSan;
    private javax.swing.JPanel pnSuaThongTinQL;
    private javax.swing.JPanel pnSuaXoaNV;
    private javax.swing.JPanel pnTTCNQL;
    private javax.swing.JPanel pnThemNV;
    private javax.swing.JPanel pnThemQL;
    private javax.swing.JPanel pnThemSB;
    private javax.swing.JPanel pnThongKe;
    private javax.swing.JPanel pnThongTinCaNhan;
    private javax.swing.JPanel pnThongTinDichVu;
    private javax.swing.JPanel pnThongTinNhanVien;
    private javax.swing.JPanel pnThongTinQuanLy;
    private javax.swing.JPanel pnThongTinSanBong;
    private javax.swing.JPanel pnTong;
    private javax.swing.JPanel pnTongThemQL;
    private javax.swing.JPanel pnTong_TTCN;
    private javax.swing.JPanel pnXoaQL;
    private javax.swing.JPanel pnlBieuDoView;
    private javax.swing.JPasswordField pwMKCu;
    private javax.swing.JPasswordField pwMKMoi;
    private javax.swing.JPasswordField pwMKXacNhan;
    private javax.swing.JPasswordField pwSuaMKNV;
    private javax.swing.JPasswordField pwThemMKNV;
    private javax.swing.JPasswordField pwThemMKQL;
    private javax.swing.JPasswordField pwThemXNMKNV;
    private javax.swing.JPasswordField pwThemXNMKQL;
    private javax.swing.JTable tbDSNV;
    private javax.swing.JTable tbDSQL;
    private javax.swing.JTable tbDSSB;
    private javax.swing.JTable tbDVNgungCungCap;
    private javax.swing.JTable tbDichVuKhac;
    private javax.swing.JTable tbHeSoKhungGio;
    private javax.swing.JTable tbTKDT;
    private javax.swing.JTable tbThucAn_ThucUong;
    private javax.swing.JTextField txtChucVuNV;
    private javax.swing.JTextField txtChucVuQL;
    private javax.swing.JTextField txtDonVi;
    private javax.swing.JTextField txtGiaTienDV;
    private javax.swing.JTextField txtHeSo;
    private javax.swing.JTextField txtKhungGio;
    private javax.swing.JTextField txtMaSan;
    private javax.swing.JTextField txtSuaCMNDNV;
    private javax.swing.JTextField txtSuaCMNDQL;
    private javax.swing.JTextField txtSuaGiaSan;
    private javax.swing.JTextField txtSuaMaNV;
    private javax.swing.JTextField txtSuaSDTNV;
    private javax.swing.JTextField txtSuaTenNV;
    private javax.swing.JTextField txtSuaTenQL;
    private javax.swing.JTextField txtTenDV;
    private javax.swing.JTextField txtThemCMNDNV;
    private javax.swing.JTextField txtThemCMNDQL;
    private javax.swing.JTextField txtThemMaNV;
    private javax.swing.JTextField txtThemSDTNV;
    private javax.swing.JTextField txtThemTKQL;
    private javax.swing.JTextField txtThemTenNV;
    private javax.swing.JTextField txtThemTenQL;
    // End of variables declaration//GEN-END:variables
}
