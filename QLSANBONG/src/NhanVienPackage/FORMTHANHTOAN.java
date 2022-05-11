/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NhanVienPackage;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author zLittleMasterz
 */
public class FORMTHANHTOAN extends javax.swing.JFrame {

    /**
     * Creates new form THANHTOAN
     */
    PHIEUTHUE phieuThanhToan;
    DefaultTableModel dftblDsSanDaThue = new DefaultTableModel();
    DefaultTableModel dftblCtDichVuThue = new DefaultTableModel();
    private SimpleDateFormat formatGio = new SimpleDateFormat("HH:mm a");
    private SimpleDateFormat formatNgay = new SimpleDateFormat("MM/dd/yyyy");
    private File f;
    
    public FORMTHANHTOAN(PHIEUTHUE phieuThanhToan) {
        initComponents();
        String ngayThue = formatNgay.format(phieuThanhToan.getNgayGhiPhieu());
        String gioThue = formatGio.format(phieuThanhToan.getCtThue().get(0).getGioThue());
        lblNgayGioThue.setText(ngayThue+"  "+gioThue);
        lblMaHoaDon.setText(phieuThanhToan.getMaHoaDon());
        lblHoTenKhach.setText(phieuThanhToan.getKhach().getHoTen());
        String nvPhucVu = phieuThanhToan.getNvGhiPhieu().getTenNV().trim();
        lbltenNhanVien.setText(nvPhucVu);
        dftblDsSanDaThue = (DefaultTableModel) tblDsSanDaThue.getModel();
        dftblCtDichVuThue = (DefaultTableModel) tblCtDichVuThue.getModel();
        this.phieuThanhToan = phieuThanhToan;
        lblTongPhaiTra.setText(String.valueOf(phieuThanhToan.getTongPhaiTra())+"đ");
        addtblDsSanDaThue();
        tblDsSanDaThue.setRowSelectionInterval(0, 0);
        addtblCtDichVuThue(phieuThanhToan.getCtThue().get(0).getDsDvSuDung());
        f = HOADONWORD.inHoaDon(phieuThanhToan);
    }
    
    public FORMTHANHTOAN() {
        initComponents();      
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblHoTenKhach = new javax.swing.JLabel();
        lblNgayGioThue = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblMaHoaDon = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCtDichVuThue = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDsSanDaThue = new javax.swing.JTable();
        tblDsSanDaThue.getTableHeader().setFont(new Font("Cambria Math", Font.BOLD, 17));
        tblDsSanDaThue.getTableHeader().setOpaque(false);
        tblDsSanDaThue.getTableHeader().setBackground(new Color(245, 80, 21));
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        bttInHoaDon = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblTongPhaiTra = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbltenNhanVien = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 253, 249));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel1.setText("Mr/Mrs: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel2.setText("Ngày giờ thuê: ");

        lblHoTenKhach.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lblHoTenKhach.setText("tenKhach");

        lblNgayGioThue.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lblNgayGioThue.setText("dd/mm/yyyy  00/00 pm");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel5.setText("Hóa đơn số:");

        lblMaHoaDon.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lblMaHoaDon.setText("mhdtext");

        tblCtDichVuThue.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblCtDichVuThue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "Đơn giá", "Số lượng", "Tổng giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCtDichVuThue.setRowHeight(30);
        tblCtDichVuThue.getTableHeader().setReorderingAllowed(false);
        tblCtDichVuThue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCtDichVuThueMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblCtDichVuThue);
        if (tblCtDichVuThue.getColumnModel().getColumnCount() > 0) {
            tblCtDichVuThue.getColumnModel().getColumn(0).setResizable(false);
            tblCtDichVuThue.getColumnModel().getColumn(1).setResizable(false);
            tblCtDichVuThue.getColumnModel().getColumn(2).setResizable(false);
            tblCtDichVuThue.getColumnModel().getColumn(3).setResizable(false);
        }

        tblDsSanDaThue.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tblDsSanDaThue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sân", "Giá theo khung giờ ", "Giờ thuê", "Giờ trả", "Khung giờ", "Số giờ thuê", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDsSanDaThue.setFocusable(false);
        tblDsSanDaThue.setGridColor(new java.awt.Color(255, 255, 255));
        tblDsSanDaThue.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblDsSanDaThue.setRowHeight(35);
        tblDsSanDaThue.setSelectionBackground(new java.awt.Color(153, 153, 153));
        tblDsSanDaThue.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDsSanDaThue.getTableHeader().setReorderingAllowed(false);
        tblDsSanDaThue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDsSanDaThueMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblDsSanDaThueMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblDsSanDaThue);
        if (tblDsSanDaThue.getColumnModel().getColumnCount() > 0) {
            tblDsSanDaThue.getColumnModel().getColumn(0).setResizable(false);
            tblDsSanDaThue.getColumnModel().getColumn(2).setResizable(false);
            tblDsSanDaThue.getColumnModel().getColumn(3).setResizable(false);
            tblDsSanDaThue.getColumnModel().getColumn(4).setResizable(false);
            tblDsSanDaThue.getColumnModel().getColumn(5).setResizable(false);
            tblDsSanDaThue.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 51));
        jLabel7.setText("HÓA ĐƠN THANH TOÁN");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 77, 207));
        jLabel8.setText("Danh sách sân đã thuê");

        bttInHoaDon.setBackground(new java.awt.Color(255, 255, 51));
        bttInHoaDon.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bttInHoaDon.setText("In Hóa Đơn");
        bttInHoaDon.setBorderPainted(false);
        bttInHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttInHoaDonMouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 77, 207));
        jLabel9.setText("Chi tiết dịch vụ từng sân");

        lblTongPhaiTra.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        lblTongPhaiTra.setText("0 đ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel6.setText("TỔNG PHẢI TRẢ:");

        lbltenNhanVien.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        lbltenNhanVien.setText("000000000");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel3.setText("Nhân viên:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbltenNhanVien))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblMaHoaDon))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblHoTenKhach))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblNgayGioThue)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTongPhaiTra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bttInHoaDon)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel7)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lblMaHoaDon))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblHoTenKhach))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblNgayGioThue))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbltenNhanVien)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lblTongPhaiTra))
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bttInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblCtDichVuThueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCtDichVuThueMouseClicked

    }//GEN-LAST:event_tblCtDichVuThueMouseClicked

    private void tblDsSanDaThueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDsSanDaThueMouseClicked
        int row = tblDsSanDaThue.getSelectedRow();
        dftblCtDichVuThue.setRowCount(0);
        addtblCtDichVuThue(phieuThanhToan.getCtThue().get(row).getDsDvSuDung());
    }//GEN-LAST:event_tblDsSanDaThueMouseClicked

    private void tblDsSanDaThueMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDsSanDaThueMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDsSanDaThueMouseReleased

    private void bttInHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttInHoaDonMouseClicked
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(f);
        } catch (IOException ex) {
            System.out.println("Không thể mở file hóa đơn");;
        }
       
        this.dispose();
    }//GEN-LAST:event_bttInHoaDonMouseClicked

    /**
     * @param args the command line arguments
     */
    
    void addtblDsSanDaThue()
    {
        for (CT_THUE ctThue : phieuThanhToan.getCtThue()) {
            dftblDsSanDaThue.addRow(new Object[]{
                ctThue.getSanBong().getMaSan(),
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
            dftblCtDichVuThue.addRow(new Object[]{
                sd_dichvu.getDichVu().getTenDichVu(),
                sd_dichvu.getDichVu().getDonGia(),
                sd_dichvu.getSl(),
                sd_dichvu.getTienDv()
            });
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttInHoaDon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblHoTenKhach;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblNgayGioThue;
    private javax.swing.JLabel lblTongPhaiTra;
    private javax.swing.JLabel lbltenNhanVien;
    private javax.swing.JTable tblCtDichVuThue;
    private javax.swing.JTable tblDsSanDaThue;
    // End of variables declaration//GEN-END:variables
}
