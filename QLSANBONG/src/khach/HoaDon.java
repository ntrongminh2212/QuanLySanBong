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
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ad
 */
public class HoaDon extends javax.swing.JFrame {

    /**
     * Creates new form HoaDon
     */
    public HoaDon() {
        initComponents();
        loadThongTin("MHD09");
        loadChiTietHoaDon("MHD09");
    }
    public HoaDon(String maHoaDon) 
    {
        initComponents();
        loadThongTin(maHoaDon);
        loadChiTietHoaDon(maHoaDon);
    }
    public void loadDichVuTuongUng(String maHoaDon, String maSan)
    {
        DefaultTableModel dtm=(DefaultTableModel) tbDichVu_HoaDon.getModel();
       dtm.setNumRows(0);
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
       String sql="select * from SD_DICHVU where SD_DICHVU.MAHOADON='"+maHoaDon+"' and SD_DICHVU.MASAN='"+maSan+"'";
       
       Vector vt;
       try
       {
           PreparedStatement ps= ketNoi.prepareStatement(sql);
           ResultSet rs= ps.executeQuery();
           int tongTien=0;
           while(rs.next())
           {
               vt=new Vector();
               vt.add(rs.getString("TENDV"));
               vt.add(rs.getString("SL"));
               vt.add(rs.getString("TIENDV"));
               tongTien+=rs.getInt("TIENDV");
               dtm.addRow(vt);
           }
           tbDichVu_HoaDon.setModel(dtm);
           lbTienDichVu.setText(String.valueOf(tongTien));
           rs.close();
           ps.close();
           ketNoi.close();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi load DV Tuong ung");
       }
    }
    public void loadChiTietHoaDon(String maHoaDon) 
    {
        DefaultTableModel dtm=(DefaultTableModel) tbCTDatTruoc_HoaDon.getModel();
       dtm.setNumRows(0);
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
       String sql="select CT_THUE.MASAN,LOAISAN.TENLOAISAN,LOAISAN.GIA,CT_THUE.GIOTHUE,CT_THUE.GIOTRA,CT_THUE.THANHTIEN\n" +
             "from CT_THUE,SANBONG,LOAISAN\n" +
             "where CT_THUE.MASAN=SANBONG.MASAN and SANBONG.MALOAISAN=LOAISAN.MALOAISAN\n" +
                "and CT_THUE.MAHOADON='"+maHoaDon+"'";
       
       Vector vt;
       try
       {
           PreparedStatement ps= ketNoi.prepareStatement(sql);
           ResultSet rs= ps.executeQuery();
           while(rs.next())
           {
               vt=new Vector();
               vt.add(rs.getString("MASAN"));
               vt.add(rs.getString("TENLOAISAN"));
               vt.add(rs.getString("GIA"));
               vt.add(rs.getString("GIOTHUE"));
               vt.add(rs.getString("GIOTRA"));
               vt.add(rs.getString("THANHTIEN"));
               dtm.addRow(vt);
           }
           tbCTDatTruoc_HoaDon.setModel(dtm);
           rs.close();
           ps.close();
           ketNoi.close();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi load CTHOADON");
       }
    }

    public void loadThongTin(String maHoaDon)
    {
        Connection ketNoi=KetNoiMSSQL.layKetNoi();
       String sql="select KHACHTHUE.HOTEN,KHACHTHUE.CMND,KHACHTHUE.SDT,PHIEUTHUE.NGAYLAPPHIEU,NHANVIEN.TENNV,PHIEUTHUE.TONGPHAITRA\n" +
            "from PHIEUTHUE,NHANVIEN,KHACHTHUE \n" +
             "where KHACHTHUE.CMND=PHIEUTHUE.CMND and NHANVIEN.MANV = PHIEUTHUE.MANV \n" +
                "	and PHIEUTHUE.MAHOADON='"+maHoaDon+"'";
       
       try
       {
           PreparedStatement ps= ketNoi.prepareStatement(sql);
           ResultSet rs= ps.executeQuery();
           rs.next();
           lbMaHoaDon_HoaDon.setText(maHoaDon);
           lbHoTen_HoaDon.setText(rs.getString("HOTEN"));
           lbCMND_HoaDon.setText(rs.getString("CMND"));
           lbSDT_HoaDon.setText(rs.getString("SDT"));
           lbNgayThue_HoaDon.setText(rs.getString("NGAYLAPPHIEU"));
           lbNhanVienPT_HoaDon.setText(rs.getString("TENNV"));
           lbTongTien_HoaDon.setText(rs.getString("TONGPHAITRA"));
           
           rs.close();
           ps.close();
           ketNoi.close();
           
       }
       catch(SQLException ex)
       {
           System.out.println("loi load thong tin hoa don");
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

        pnHoaDon = new javax.swing.JPanel();
        lbMaHoaDon_HoaDon = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbCTDatTruoc_HoaDon = new javax.swing.JTable();
        jLabel60 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tbDichVu_HoaDon = new javax.swing.JTable();
        sadad = new javax.swing.JLabel();
        lbTienDichVu = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        btnQuayLai_HoaDon = new javax.swing.JButton();
        lbHoTen_HoaDon = new javax.swing.JLabel();
        lbCMND_HoaDon = new javax.swing.JLabel();
        lbSDT_HoaDon = new javax.swing.JLabel();
        lbNgayThue_HoaDon = new javax.swing.JLabel();
        lbTongTien_HoaDon = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        lbNhanVienPT_HoaDon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnHoaDon.setBackground(new java.awt.Color(254, 235, 208));
        pnHoaDon.setPreferredSize(new java.awt.Dimension(797, 654));

        lbMaHoaDon_HoaDon.setBackground(new java.awt.Color(255, 255, 255));
        lbMaHoaDon_HoaDon.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lbMaHoaDon_HoaDon.setForeground(new java.awt.Color(0, 0, 102));
        lbMaHoaDon_HoaDon.setText("M?? H??A ????N");

        jLabel51.setBackground(new java.awt.Color(255, 255, 255));
        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 102));
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-invoice-50.png"))); // NOI18N
        jLabel51.setText("H??A ????N:");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 102));
        jLabel52.setText("H??? T??N KH??CH H??NG:");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 0, 102));
        jLabel53.setText("CMND:");

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 102));
        jLabel55.setText("T???NG TI???N:");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 102));
        jLabel56.setText("NG??Y THU??:");

        jPanel6.setBackground(new java.awt.Color(200, 232, 249));

        tbCTDatTruoc_HoaDon.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbCTDatTruoc_HoaDon.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null}
            },
            new String [] {
                "T??N S??N", "LO???I S??N", "GI?? 1 GI???", "GI??? THU??", "GI??? TR???", "GI?? THU??"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCTDatTruoc_HoaDon.setRowHeight(35);
        tbCTDatTruoc_HoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCTDatTruoc_HoaDonMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tbCTDatTruoc_HoaDon);

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(37, 102, 142));
        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-more-details-30.png"))); // NOI18N
        jLabel60.setText("CHI TI???T THU??");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(266, 266, 266))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 102));
        jLabel54.setText("SDT:");

        jPanel7.setBackground(new java.awt.Color(179, 193, 135));

        tbDichVu_HoaDon.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tbDichVu_HoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "T??N D???CH V???", "S??? L?????NG", "GI??"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDichVu_HoaDon.setRowHeight(35);
        jScrollPane11.setViewportView(tbDichVu_HoaDon);

        sadad.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        sadad.setForeground(new java.awt.Color(255, 255, 255));
        sadad.setText("TI???N D???CH V???:");

        lbTienDichVu.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lbTienDichVu.setForeground(new java.awt.Color(255, 255, 255));

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 102, 0));
        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/khach/HINHANH/icons8-energy-drink-30.png"))); // NOI18N
        jLabel59.setText("D???CH V??? T????NG ???NG");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(sadad)
                        .addGap(18, 18, 18)
                        .addComponent(lbTienDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel59)
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(sadad)
                        .addGap(0, 17, Short.MAX_VALUE))
                    .addComponent(lbTienDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnQuayLai_HoaDon.setBackground(new java.awt.Color(255, 0, 51));
        btnQuayLai_HoaDon.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnQuayLai_HoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnQuayLai_HoaDon.setText("THO??T");
        btnQuayLai_HoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLai_HoaDonActionPerformed(evt);
            }
        });

        lbHoTen_HoaDon.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lbHoTen_HoaDon.setForeground(new java.awt.Color(0, 0, 102));
        lbHoTen_HoaDon.setText("CMND:");

        lbCMND_HoaDon.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lbCMND_HoaDon.setForeground(new java.awt.Color(0, 0, 102));
        lbCMND_HoaDon.setText("CMND:");

        lbSDT_HoaDon.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lbSDT_HoaDon.setForeground(new java.awt.Color(0, 0, 102));
        lbSDT_HoaDon.setText("CMND:");

        lbNgayThue_HoaDon.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lbNgayThue_HoaDon.setForeground(new java.awt.Color(0, 0, 102));
        lbNgayThue_HoaDon.setText("CMND:");

        lbTongTien_HoaDon.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lbTongTien_HoaDon.setForeground(new java.awt.Color(0, 0, 102));
        lbTongTien_HoaDon.setText("CMND:");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 0, 102));
        jLabel57.setText("NH??N VI??N PT:");

        lbNhanVienPT_HoaDon.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lbNhanVienPT_HoaDon.setForeground(new java.awt.Color(0, 0, 102));
        lbNhanVienPT_HoaDon.setText("CMND:");

        javax.swing.GroupLayout pnHoaDonLayout = new javax.swing.GroupLayout(pnHoaDon);
        pnHoaDon.setLayout(pnHoaDonLayout);
        pnHoaDonLayout.setHorizontalGroup(
            pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHoaDonLayout.createSequentialGroup()
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnHoaDonLayout.createSequentialGroup()
                        .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnHoaDonLayout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel53)
                                    .addComponent(jLabel52)
                                    .addComponent(jLabel54))
                                .addGap(28, 28, 28)
                                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbSDT_HoaDon)
                                    .addComponent(lbHoTen_HoaDon)
                                    .addComponent(lbCMND_HoaDon))
                                .addGap(422, 422, 422)
                                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel57)
                                    .addComponent(jLabel55)
                                    .addComponent(jLabel56))
                                .addGap(18, 18, 18)
                                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbNgayThue_HoaDon)
                                    .addComponent(lbNhanVienPT_HoaDon)
                                    .addComponent(lbTongTien_HoaDon)))
                            .addGroup(pnHoaDonLayout.createSequentialGroup()
                                .addGap(363, 363, 363)
                                .addComponent(jLabel51)
                                .addGap(18, 18, 18)
                                .addComponent(lbMaHoaDon_HoaDon)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnHoaDonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pnHoaDonLayout.createSequentialGroup()
                .addGap(465, 465, 465)
                .addComponent(btnQuayLai_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnHoaDonLayout.setVerticalGroup(
            pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHoaDonLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMaHoaDon_HoaDon)
                    .addComponent(jLabel51))
                .addGap(10, 10, 10)
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbNgayThue_HoaDon))
                    .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel52)
                        .addComponent(lbHoTen_HoaDon)))
                .addGap(20, 20, 20)
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNhanVienPT_HoaDon)
                    .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel53)
                        .addComponent(lbCMND_HoaDon)
                        .addComponent(jLabel57)))
                .addGap(18, 18, 18)
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(lbSDT_HoaDon)
                    .addComponent(jLabel55)
                    .addComponent(lbTongTien_HoaDon))
                .addGap(18, 18, 18)
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(btnQuayLai_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 1174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuayLai_HoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayLai_HoaDonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnQuayLai_HoaDonActionPerformed

    private void tbCTDatTruoc_HoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCTDatTruoc_HoaDonMouseClicked
        // TODO add your handling code here:
        String maHoaDon=lbMaHoaDon_HoaDon.getText();
        DefaultTableModel dtm=(DefaultTableModel)tbCTDatTruoc_HoaDon.getModel();
        int i=tbCTDatTruoc_HoaDon.getSelectedRow();
        
        String maSan= dtm.getValueAt(i, 0).toString();
        loadDichVuTuongUng(maHoaDon, maSan);
    }//GEN-LAST:event_tbCTDatTruoc_HoaDonMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnQuayLai_HoaDon;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JLabel lbCMND_HoaDon;
    private javax.swing.JLabel lbHoTen_HoaDon;
    private javax.swing.JLabel lbMaHoaDon_HoaDon;
    private javax.swing.JLabel lbNgayThue_HoaDon;
    private javax.swing.JLabel lbNhanVienPT_HoaDon;
    private javax.swing.JLabel lbSDT_HoaDon;
    private javax.swing.JLabel lbTienDichVu;
    private javax.swing.JLabel lbTongTien_HoaDon;
    private javax.swing.JPanel pnHoaDon;
    private javax.swing.JLabel sadad;
    private javax.swing.JTable tbCTDatTruoc_HoaDon;
    private javax.swing.JTable tbDichVu_HoaDon;
    // End of variables declaration//GEN-END:variables
}
