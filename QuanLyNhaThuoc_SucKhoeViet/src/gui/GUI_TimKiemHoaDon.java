/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.print.attribute.standard.MediaSize.NA;
import javax.swing.table.DefaultTableModel;

import dao.HoaDon_DAO;
import entities.HoaDon;
import entities.NhanVien;


/**
 *
 * @author NguyenThanhLuan
 */
public class GUI_TimKiemHoaDon extends javax.swing.JPanel {
	private DefaultTableModel modelHoaDon;
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private NhanVien emp;
	private HoaDon_DAO hoaDon_DAO;
	ArrayList<HoaDon> hoaDons = new ArrayList<HoaDon>();
    /**
     * 
     * Creates new form GUI_TimKiemThuoc
     */
    public GUI_TimKiemHoaDon() {
        initComponents();
        hoaDon_DAO = new HoaDon_DAO();
        loadDuLieu();
    }
    
	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
	}

    
    private void loadDuLieu() {
        tableHoaDon.removeAll();
        tableHoaDon.setRowSelectionAllowed(false);
        modelHoaDon.setRowCount(0);
    	hoaDons = hoaDon_DAO.layTatCaHoaDon();
    	int stt = 1;
    	for(int i = 0; i < hoaDons.size(); i++) {
    		modelHoaDon.addRow(new Object[] {stt++ ,hoaDons.get(i).getMaHD(), hoaDons.get(i).getNgayLapHoaDon(), hoaDons.get(i).getKhachHang().getMaKH(), hoaDons.get(i).getKhachHang().getHoTenKhachHang(), hoaDons.get(i).getNhanVien().getMaNV(),hoaDons.get(i).getNhanVien().getHoTenNhanVien(),hoaDons.get(i).getTheBaoHiem()});
    	}
    }
    
    private void doDuLieuLenMaHD() {
    	for(int i = 0; i < hoaDons.size(); i++) {
    		cbMaHoaDon.addItem(hoaDons.get(i).getMaHD());
    	}
    }
    
    private void doDuLieuLenMaKH() {
    	for(int i = 0; i < hoaDons.size(); i++) {
    		cbMaKhachHang.addItem(hoaDons.get(i).getKhachHang().getMaKH());
    	}
    }

    private void doDuLieuLenMaNV() {
    	for(int i = 0; i < hoaDons.size(); i++) {
    		cbMaNhanVien.addItem(hoaDons.get(i).getNhanVien().getMaNV());
    	}
    }
    
    private ArrayList<HoaDon> timKiem() {
    	Object selectedItem = cbMaHoaDon.getSelectedItem();
    	Object selectedItemKH = cbMaKhachHang.getSelectedItem();
    	Object selectedItemNV = cbMaNhanVien.getSelectedItem();
    	ArrayList<HoaDon> list = new ArrayList<HoaDon>();
    	if(selectedItem != null) {
    		String maHD = selectedItem.toString();
        	for(HoaDon hd : hoaDons) {
        		if(hd.getMaHD().contains(maHD))
        			list.add(hd);
        	}
    	}
    	if (ngaylaphoadon.getDate() != null) {
    	    java.util.Date dateUtil = ngaylaphoadon.getDate();
    	    java.time.LocalDate localDate = dateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	    java.sql.Date dateSql = java.sql.Date.valueOf(localDate);

    	    if (selectedItem == null) {
    	        for (HoaDon hd : hoaDons) {
    	            java.time.LocalDate hdLocalDate = ((java.sql.Date) hd.getNgayLapHoaDon()).toLocalDate();
    	            if (hdLocalDate.equals(localDate)) {
    	                list.add(hd);
    	            }
    	        }
    	    }
    	    if(selectedItem != null) {
    	    	String maHD = selectedItem.toString();
    	    	list.clear();
    	        for (HoaDon hd : hoaDons) {
    	            java.time.LocalDate hdLocalDate = ((java.sql.Date) hd.getNgayLapHoaDon()).toLocalDate();
    	            if (hdLocalDate.equals(localDate) && hd.getMaHD().contains(maHD)) {
    	                list.add(hd);
    	            }
    	        }
    	    }
    	}
    	if(selectedItemKH != null) {
    		String maKH = selectedItemKH.toString();
    		if(selectedItem == null && ngaylaphoadon.getDate() == null) {
            	for(HoaDon hd : hoaDons) {
            		if(hd.getKhachHang().getMaKH().contains(maKH))
            			list.add(hd);
            	}
    		}
    		if(selectedItem != null && ngaylaphoadon.getDate() == null) {
    			String maHD = selectedItem.toString();
    			list.clear();
            	for(HoaDon hd : hoaDons) {
            		if(hd.getKhachHang().getMaKH().contains(maKH) && hd.getMaHD().contains(maHD))
            			list.add(hd);
            	}
    		}
    		if(selectedItem == null && ngaylaphoadon.getDate() != null) {
        	    java.util.Date dateUtil = ngaylaphoadon.getDate();
        	    java.time.LocalDate localDate = dateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        	    java.sql.Date dateSql = java.sql.Date.valueOf(localDate);
    			list.clear();
            	for(HoaDon hd : hoaDons) {
            		java.time.LocalDate hdLocalDate = ((java.sql.Date) hd.getNgayLapHoaDon()).toLocalDate();
            		if(hd.getKhachHang().getMaKH().contains(maKH) && hdLocalDate.equals(localDate))
            			list.add(hd);
            	}
    		}
    	}
    	if(selectedItemNV != null) {
    		String maNV = selectedItemNV.toString();
    		if(selectedItem == null && ngaylaphoadon.getDate() == null && selectedItemKH == null) {
            	for(HoaDon hd : hoaDons) {
            		if(hd.getNhanVien().getMaNV().contains(maNV))
            			list.add(hd);
            	}
    		}
    		if(selectedItem != null && ngaylaphoadon.getDate() == null && selectedItemKH == null ) {
    			String maHD = selectedItem.toString();
    			list.clear();
            	for(HoaDon hd : hoaDons) {
            		if(hd.getNhanVien().getMaNV().contains(maNV) && hd.getMaHD().contains(maHD))
            			list.add(hd);
            	}
    		}
    		if(selectedItem == null && ngaylaphoadon.getDate() != null && selectedItemKH == null ) {
        	    java.util.Date dateUtil = ngaylaphoadon.getDate();
        	    java.time.LocalDate localDate = dateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        	    java.sql.Date dateSql = java.sql.Date.valueOf(localDate);
    			list.clear();
            	for(HoaDon hd : hoaDons) {
            		java.time.LocalDate hdLocalDate = ((java.sql.Date) hd.getNgayLapHoaDon()).toLocalDate();
            		if(hd.getNhanVien().getMaNV().contains(maNV) && hdLocalDate.equals(localDate))
            			list.add(hd);
            	}
    		}
    		if(selectedItem == null && ngaylaphoadon.getDate() == null && selectedItemKH != null) {
    			String maKH = selectedItemKH.toString();
    			list.clear();
            	for(HoaDon hd : hoaDons) {
            		if(hd.getNhanVien().getMaNV().contains(maNV) && hd.getKhachHang().getMaKH().contains(maKH))
            			list.add(hd);
            	}
    		}
    		if(selectedItem != null && ngaylaphoadon.getDate() != null && selectedItemKH == null) {
    			String maHD = selectedItem.toString();
    			java.util.Date dateUtil = ngaylaphoadon.getDate();
        	    java.time.LocalDate localDate = dateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        	    java.sql.Date dateSql = java.sql.Date.valueOf(localDate);
    			list.clear();
            	for(HoaDon hd : hoaDons) {
            		java.time.LocalDate hdLocalDate = ((java.sql.Date) hd.getNgayLapHoaDon()).toLocalDate();
            		if(hd.getMaHD().contains(maHD) && hdLocalDate.equals(localDate) && hd.getNhanVien().getMaNV().contains(maNV))
            			list.add(hd);
            	}
    		}
    		if(selectedItem == null && ngaylaphoadon.getDate() != null && selectedItemKH != null) {
    			String maKH = selectedItemKH.toString();
    			java.util.Date dateUtil = ngaylaphoadon.getDate();
        	    java.time.LocalDate localDate = dateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        	    java.sql.Date dateSql = java.sql.Date.valueOf(localDate);
    			list.clear();
            	for(HoaDon hd : hoaDons) {
            		java.time.LocalDate hdLocalDate = ((java.sql.Date) hd.getNgayLapHoaDon()).toLocalDate();
            		if(hd.getKhachHang().getMaKH().contains(maKH) && hdLocalDate.equals(localDate) && hd.getNhanVien().getMaNV().contains(maNV))
            			list.add(hd);
            	}
    		}
    		if(selectedItem != null && ngaylaphoadon.getDate() == null && selectedItemKH != null) {
    			String maKH = selectedItemKH.toString();
    			String maHD = selectedItem.toString();
    			list.clear();
            	for(HoaDon hd : hoaDons) {
            		java.time.LocalDate hdLocalDate = ((java.sql.Date) hd.getNgayLapHoaDon()).toLocalDate();
            		if(hd.getKhachHang().getMaKH().contains(maKH) && hd.getMaHD().contains(maHD) && hd.getNhanVien().getMaNV().contains(maNV))
            			list.add(hd);
            	}
    		}
    		if(selectedItem == null && ngaylaphoadon.getDate() == null && selectedItemKH == null && selectedItemNV == null){
    			loadDuLieu();
    		}
    	}
    	return list;
    }
    
    private void loadDuLieuTimKiem() {
        tableHoaDon.removeAll();
        tableHoaDon.setRowSelectionAllowed(false);
        modelHoaDon.setRowCount(0);
    	int stt = 1;
    	ArrayList<HoaDon> hoaDonsTimKiem;
    	hoaDonsTimKiem = timKiem();
    	for(int i = 0; i < hoaDonsTimKiem.size(); i++) {
    		modelHoaDon.addRow(new Object[] {stt++ ,hoaDonsTimKiem.get(i).getMaHD(), hoaDonsTimKiem.get(i).getNgayLapHoaDon(), hoaDonsTimKiem.get(i).getKhachHang().getMaKH(), hoaDonsTimKiem.get(i).getKhachHang().getHoTenKhachHang(), hoaDonsTimKiem.get(i).getNhanVien().getMaNV(),hoaDonsTimKiem.get(i).getNhanVien().getHoTenNhanVien(),hoaDonsTimKiem.get(i).getTheBaoHiem()});
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

        ALL = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        txtNgayHienTai = new javax.swing.JTextField(layNgayHienTai());
        jPanel12 = new javax.swing.JPanel();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 32767));
        jLabel4 = new javax.swing.JLabel();
        cbMaHoaDon = new javax.swing.JComboBox<>();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabel5 = new javax.swing.JLabel();
        ngaylaphoadon = new com.toedter.calendar.JDateChooser();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabel6 = new javax.swing.JLabel();
        cbMaKhachHang = new javax.swing.JComboBox<>();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabel7 = new javax.swing.JLabel();
        cbMaNhanVien = new javax.swing.JComboBox<>();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        btnTimKiem = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));

        setLayout(new java.awt.BorderLayout());

        ALL.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(444, 145));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nhanvien/pngegg.png"))); // NOI18N
        jPanel8.add(jLabel1);

        jPanel4.add(jPanel8);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(15, 102, 165));
        jLabel2.setText("TÌM KIẾM HÓA ĐƠN");
        jPanel6.add(jLabel2);

        jPanel4.add(jPanel6);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 30));

        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel3.setText("Ngày hiện tại");
        jPanel7.add(jLabel3);
        jPanel7.add(filler1);

        txtNgayHienTai.setEditable(false);
        txtNgayHienTai.setMinimumSize(new java.awt.Dimension(180, 22));
        txtNgayHienTai.setPreferredSize(new java.awt.Dimension(180, 35));
        jPanel7.add(txtNgayHienTai);

        jPanel5.add(jPanel7);

        jPanel4.add(jPanel5);

        jPanel2.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 30, 8, 8));
        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.LINE_AXIS));
        jPanel12.add(filler7);
        jPanel12.add(filler8);

        jLabel4.setText("Mã hóa đơn: ");
        jPanel12.add(jLabel4);

        cbMaHoaDon.setEditable(true);
        cbMaHoaDon.setMaximumSize(new java.awt.Dimension(180, 32767));
        cbMaHoaDon.setPreferredSize(new java.awt.Dimension(180, 22));
        cbMaHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbMaHoaDonMouseClicked(evt);
            }
        });
        cbMaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMaHoaDonActionPerformed(evt);
            }
        });
        jPanel12.add(cbMaHoaDon);
        jPanel12.add(filler3);

        jLabel5.setText("Ngày lập :");
        jPanel12.add(jLabel5);

        ngaylaphoadon.setPreferredSize(new java.awt.Dimension(180, 22));
        jPanel12.add(ngaylaphoadon);
        jPanel12.add(filler4);

        jLabel6.setText("Mã khách hàng :");
        jPanel12.add(jLabel6);

        cbMaKhachHang.setEditable(true);
        cbMaKhachHang.setMaximumSize(new java.awt.Dimension(180, 32767));
        cbMaKhachHang.setPreferredSize(new java.awt.Dimension(180, 22));
        cbMaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMaKhachHangActionPerformed(evt);
            }
        });
        jPanel12.add(cbMaKhachHang);
        jPanel12.add(filler5);

        jLabel7.setText("Mã nhân viên :");
        jPanel12.add(jLabel7);

        cbMaNhanVien.setEditable(true);
        cbMaNhanVien.setMaximumSize(new java.awt.Dimension(180, 32767));
        cbMaNhanVien.setPreferredSize(new java.awt.Dimension(180, 22));
        cbMaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMaNhanVienActionPerformed(evt);
            }
        });
        jPanel12.add(cbMaNhanVien);
        jPanel12.add(filler2);

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setMaximumSize(new java.awt.Dimension(85, 45));
        btnTimKiem.setMinimumSize(new java.awt.Dimension(85, 45));
        btnTimKiem.setPreferredSize(new java.awt.Dimension(85, 45));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel12.add(btnTimKiem);

        jPanel2.add(jPanel12, java.awt.BorderLayout.CENTER);

        ALL.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi tiết hóa đơn"));
        jPanel34.setLayout(new java.awt.BorderLayout());

        tableHoaDon.setModel(modelHoaDon = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Ngày lập", "Mã khách hàng", "Tên khách hàng", "Mã nhân viên", "Tên nhân viên", "Thẻ bảo hiểm"
            }
        ));
        jScrollPane1.setViewportView(tableHoaDon);

        jPanel34.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        jPanel1.add(filler6);

        jPanel34.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel3.add(jPanel34, java.awt.BorderLayout.CENTER);

        ALL.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(ALL, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        loadDuLieuTimKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void cbMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMaNhanVienActionPerformed
        doDuLieuLenMaNV();
    }//GEN-LAST:event_cbMaNhanVienActionPerformed

    private void cbMaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMaKhachHangActionPerformed
        doDuLieuLenMaKH();
    }//GEN-LAST:event_cbMaKhachHangActionPerformed

    private void cbMaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMaHoaDonActionPerformed
        doDuLieuLenMaHD();
    }//GEN-LAST:event_cbMaHoaDonActionPerformed

    private void cbMaHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbMaHoaDonMouseClicked
        doDuLieuLenMaHD();
    }//GEN-LAST:event_cbMaHoaDonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ALL;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cbMaHoaDon;
    private javax.swing.JComboBox<String> cbMaKhachHang;
    private javax.swing.JComboBox<String> cbMaNhanVien;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser ngaylaphoadon;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTextField txtNgayHienTai;
    // End of variables declaration//GEN-END:variables
}
