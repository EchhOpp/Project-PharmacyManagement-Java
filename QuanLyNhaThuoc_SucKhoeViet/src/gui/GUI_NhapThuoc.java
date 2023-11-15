/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import dao.NhaCungCap_DAO;
import dao.PhieuNhapThuoc_DAO;
import dao.Thuoc_DAO;
import entities.NhaCungCap;
import entities.NhanVien;
import entities.PhieuNhapThuoc;
import entities.Thuoc;


/**
 *
 * @author NguyenThanhLuan
 */
public class GUI_NhapThuoc extends javax.swing.JPanel {
	private NhanVien emp;
	private DefaultTableModel modelPhieuNhapThuoc;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private DecimalFormat fmt = new DecimalFormat("###,###");
	private boolean use_event_cbMaThuoc = false;
	private boolean use_event_cbMaNhaCungCap = false;
	private boolean use_phatSinhMaThuoc = false;
	private boolean use_phatSinhMaNhaCungCap = false;
	
	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
	}

	private NhaCungCap taoNhaCungCap() {
		String maNCC = cbMaNhaCungCap.getSelectedItem().toString().split("-")[0].trim();
		String tenNCC = txtTenNhaCungCap.getText().trim();
		String email = txtEmail.getText().trim();
		String soDienThoai = txtSoDienThoai.getText().trim();
		String diaChi = textAreaDiaChi.getText().trim();
		return new NhaCungCap(maNCC, tenNCC, email, soDienThoai, diaChi);
	}

	private Thuoc taoThuoc() {
		String maThuoc = cbMaThuoc.getSelectedItem().toString().split("-")[0].trim();
		String tenThuoc = txtTenThuoc.getText().trim();
		String loaiThuoc = cbLoaiThuoc.getSelectedItem().toString().trim();
		String donViThuoc = txtDonViThuoc.getText().trim();
		String xuatXu = txtXuatXu.getText().trim();
		int soLuongTon = Integer.parseInt(txtSoLuongTon.getText().trim());
		int soLuongNhap = Integer.parseInt(spinSoLuongNhap.getValue().toString().trim());
		return new Thuoc(maThuoc, tenThuoc, loaiThuoc, donViThuoc, xuatXu, soLuongNhap + soLuongTon, taoNhaCungCap());
	}

	private PhieuNhapThuoc taoPhieuNhapThuoc() {
		String maPhieu = txtMaPhieuNhap.getText().trim();
		Date ngayNhap = new Date();
		java.util.Date ngaySX = ngaySanXuat.getDate();
		java.sql.Date ngaySXSQL = new java.sql.Date(ngaySX.getTime());
		java.util.Date ngayHH = ngayHetHan.getDate();
		java.sql.Date ngayHHSQL = new java.sql.Date(ngayHH.getTime());
		Date ngaySanXuat = ngaySXSQL;
		Date ngayHetHan = ngayHHSQL;
		String soTienNhap = txtDonGiaMua.getText().trim();
		float donGiaMua = Float.parseFloat(loaiBoDinhDangTien(soTienNhap));
		int soLuongNhap = Integer.parseInt(spinSoLuongNhap.getValue().toString().trim());
		return new PhieuNhapThuoc(maPhieu, emp, taoThuoc(), ngayNhap, ngaySanXuat, ngayHetHan, donGiaMua, soLuongNhap);
	}

	private String phatSinhMaPhieuNhapThuoc() {
		try {
			PhieuNhapThuoc_DAO phieunhapthuoc_dao = new PhieuNhapThuoc_DAO();
			String maphieu_lastest = phieunhapthuoc_dao.layMaPhieuNhapThuocCuoiCung().trim();
			Date ngayHienTai = new Date();
			String maPhieuNhapThuoc = "PH" + sdf.format(ngayHienTai).split("-")[2];
			String stt_string_lastest = maphieu_lastest.substring(6, maphieu_lastest.length());
			int stt_int_lastest = Integer.parseInt(stt_string_lastest);
			String stt_current = String.valueOf(stt_int_lastest + 1);
			for (int i = 0; i < (6 - stt_current.length()); i++) {
				maPhieuNhapThuoc += "0";
			}
			return maPhieuNhapThuoc += stt_current;
		} catch (NullPointerException e) {
			return "PH" + LocalDate.now().getYear() + "000001";
		}
	}

	private void phatSinhMaThuoc() {
		use_phatSinhMaThuoc = true;
		Thuoc_DAO thuoc_dao = new Thuoc_DAO();
		String maThuoc_lastest = thuoc_dao.layMaThuocCuoiCung().trim();
		String maThuoc = "TH";

		if (maThuoc_lastest != null) {
			String stt_string_lastest = maThuoc_lastest.substring(2, maThuoc_lastest.length());
			int stt_int_lastest = Integer.parseInt(stt_string_lastest);
			String stt_current = String.valueOf(stt_int_lastest + 1);
			for (int i = 0; i < (10 - stt_current.length()); i++) {
				maThuoc += "0";
			}
			maThuoc += stt_current;
			cbMaThuoc.setSelectedItem(maThuoc);
		} else {
			maThuoc += "0000000001";
			cbMaThuoc.setSelectedItem(maThuoc);
		}
	}

	private void phatSinhMaNhaCungCap() {
		use_phatSinhMaNhaCungCap = true;
		NhaCungCap_DAO nhacungcap_dao = new NhaCungCap_DAO();
		String maNhaCungCap_lastest = nhacungcap_dao.layMaNCCCuoiCung().trim();
		String maNhaCungCap = "NCC";

		if (maNhaCungCap_lastest != null) {
			String stt_string_lastest = maNhaCungCap_lastest.substring(3, maNhaCungCap_lastest.length());
			int stt_int_lastest = Integer.parseInt(stt_string_lastest);
			String stt_current = String.valueOf(stt_int_lastest + 1);
			for (int i = 0; i < (5 - stt_current.length()); i++) {
				maNhaCungCap += "0";
			}
			maNhaCungCap += stt_current;
			cbMaNhaCungCap.setSelectedItem(maNhaCungCap);
		} else {
			maNhaCungCap += "00001";
			cbMaNhaCungCap.setSelectedItem(maNhaCungCap);
		}
	}

	private ArrayList<String> initCandidateThuoc() {
		Thuoc_DAO thuoc_dao = new Thuoc_DAO();
		ArrayList<Thuoc> danhSachThuoc = thuoc_dao.layTatCaThuoc();
		ArrayList<String> dataSet = new ArrayList<String>();
		for (Thuoc thuoc : danhSachThuoc) {
			String element = thuoc.getMaThuoc() + " - " + thuoc.getTenThuoc() + " - "
					+ thuoc.getNhaCungCap().getTenNCC();
			dataSet.add(element);
		}
		return dataSet;
	}

	private ArrayList<String> initCandidateNhaCungCap() {
		NhaCungCap_DAO nhacungcap_dao = new NhaCungCap_DAO();
		ArrayList<NhaCungCap> danhSachNhaCungCap = nhacungcap_dao.layTatCaNhaCungCap();
		ArrayList<String> dataSet = new ArrayList<String>();
		for (NhaCungCap nhaCungCap : danhSachNhaCungCap) {
			String element = nhaCungCap.getMaNCC() + " - " + nhaCungCap.getTenNCC();
			dataSet.add(element);
		}
		return dataSet;
	}

	private ArrayList<String> searchCandidate(String textHienTai, ArrayList<String> s) {
		ArrayList<String> candidate = new ArrayList<String>();
		for (String element : s) {
			if (element.toLowerCase().trim().contains(textHienTai.toLowerCase().trim())) {
				candidate.add(element);
			}
		}
		return candidate;
	}

	private void generateThuocByMaThuoc(String maThuoc) {
		Thuoc_DAO thuoc_dao = new Thuoc_DAO();
		ArrayList<Thuoc> ds = thuoc_dao.layTatCaThuoc();
		Thuoc thuoc = new Thuoc(maThuoc);
		if (ds.contains(thuoc)) {
			thuoc = ds.get(ds.indexOf(thuoc));
			NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
			txtTenThuoc.setText(thuoc.getTenThuoc().trim());
			String loaiThuoc = String.valueOf(thuoc.getLoaiThuoc());
			cbLoaiThuoc.removeAllItems();
			cbLoaiThuoc.addItem(loaiThuoc);
			cbLoaiThuoc.setSelectedItem(loaiThuoc);
			txtDonViThuoc.setText(thuoc.getDonViThuoc().trim());
			txtDonGiaMua.setEditable(true);
			txtXuatXu.setText(thuoc.getXuatXu());
			txtSoLuongTon.setText(String.valueOf(thuoc.getSoLuongTon()));
			String maNhaCungCap = thuoc.getNhaCungCap().getMaNCC();
			cbMaNhaCungCap.addItem(maNhaCungCap);
			cbMaNhaCungCap.setSelectedItem(maNhaCungCap);
			cbMaNhaCungCap.setEditable(false);
			lblMaNhaCungCap.setText("Mã nhà cung cấp:");
			btnGenerateMaNCC.setEnabled(false);
			txtTenNhaCungCap.setText(nhaCungCap.getTenNCC().trim());
			txtEmail.setText(nhaCungCap.getEmail().trim());
			txtSoDienThoai.setText(nhaCungCap.getSoDienThoai().trim());
			textAreaDiaChi.setText(nhaCungCap.getDiaChi().trim());
//			btnLuuNhaCungCap.setEnabled(false);
		}
	}

	private void generateNhaCCByMaNCC(String maNhaCungCap) {
		NhaCungCap_DAO nhacungcap_dao = new NhaCungCap_DAO();
		ArrayList<NhaCungCap> ds = nhacungcap_dao.layTatCaNhaCungCap();
		NhaCungCap nhaCungCap = new NhaCungCap(maNhaCungCap);
		if (ds.contains(nhaCungCap)) {
			nhaCungCap = ds.get(ds.indexOf(nhaCungCap));
			txtTenNhaCungCap.setText(nhaCungCap.getTenNCC().trim());
			txtEmail.setText(nhaCungCap.getEmail().trim());
			txtSoDienThoai.setText(nhaCungCap.getSoDienThoai().trim());
			textAreaDiaChi.setText(nhaCungCap.getDiaChi().trim());
//			btnLuuNhaCungCap.setEnabled(false);
		}
	}

	private String loaiBoDinhDangTien(String soTien) {
		if (soTien.contains(",")) {
			String[] tachChuoi = soTien.split(",");
			soTien = "";
			for (String string : tachChuoi) {
				soTien += string;
			}
		}
		return soTien;
	}

	private boolean validData() {
		try {
			String maThuoc = cbMaThuoc.getSelectedItem().toString().trim();
			if (maThuoc.equalsIgnoreCase("")) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(cbMaThuoc, "Bạn phải nhập mã thuốc", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				cbMaThuoc.requestFocus();
				return false;
			} else {
				cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		} catch (NullPointerException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(cbMaThuoc, "Bạn phải nhập mã thuốc", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
			cbMaThuoc.requestFocus();
			return false;
		}

		if (btnGenerateMaThuoc.isEnabled()) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(btnGenerateMaThuoc, "Bạn chưa xác nhận mã thuốc", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			btnGenerateMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
			btnGenerateMaThuoc.requestFocus();
			return false;
		} else {
			btnGenerateMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String tenThuoc = txtTenThuoc.getText().trim();
		if (tenThuoc.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtTenThuoc, "Bạn phải nhập tên thuốc", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtTenThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtTenThuoc.requestFocus();
			return false;
		} else {
			txtTenThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		java.util.Date ngaySXUtil = ngaySanXuat.getDate();
		java.sql.Date ngaySVSQL = new java.sql.Date(ngaySXUtil.getTime());
		java.util.Date ngayHHUtil = ngayHetHan.getDate();
		java.sql.Date ngayHHSQL = new java.sql.Date(ngayHHUtil.getTime());
		if (ngaySanXuat.getDate() == null) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Bạn phải chọn ngày sản xuất", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return false;
		} 
		if (ngayHetHan.getDate() == null) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Bạn phải chọn ngày hết hạn", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
//	
//	private void fillForm(int row) {
//		if (row != -1) {
//			String maPhieuNhapThuoc = String.valueOf(tablePhieuNhapThuoc.getValueAt(row, 0));
//			PhieuNhapThuoc_DAO pnt_dao = new PhieuNhapThuoc_DAO();
//			PhieuNhapThuoc phieuNhapThuoc = pnt_dao.layPhieuNhapThuoc(maPhieuNhapThuoc);
//			Thuoc_DAO thuoc_dao = new Thuoc_DAO();
//			Thuoc thuoc = thuoc_dao.layThuoc(phieuNhapThuoc.getThuoc().getMaThuoc());
//			NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
//			cbMaThuoc.setSelectedItem(thuoc.getMaThuoc());
//			txtTenThuoc.setText(thuoc.getTenThuoc());
//			txtNgayNhap.setText(sdf.format(phieuNhapThuoc.getNgayNhap()));
//			model_ngaysanxuat.setValue(phieuNhapThuoc.getNgaySanXuat());
//			model_ngayhethan.setValue(phieuNhapThuoc.getNgayHetHan());
//			cbLoaiThuoc.setSelectedItem(thuoc.getLoaiThuoc());
//			txtDonViThuoc.setText(thuoc.getDonViThuoc());
//			txtDonGiaMua.setText(fmt.format(phieuNhapThuoc.getDonGiaMua()));
//			txtXuatXu.setText(thuoc.getXuatXu());
//			spinSoLuongNhap.setValue(phieuNhapThuoc.getSoLuongNhap());
//			txtSoLuongTon.setText(String.valueOf(thuoc.getSoLuongTon()));
//			cbMaNhaCungCap.setSelectedItem(nhaCungCap.getMaNCC());
//			txtTenNhaCungCap.setText(nhaCungCap.getTenNCC());
//			txtEmail.setText(nhaCungCap.getEmail());
//			txtSoDienThoai.setText(nhaCungCap.getSoDienThoai());
//			textAreaDiaChi.setText(nhaCungCap.getDiaChi());
//		}
//	}

//	private void xoaTrang() {
//		use_event_cbMaThuoc = false;
//		use_event_cbMaNhaCungCap = false;
//		txtMaPhieuNhap.setText(phatSinhMaPhieuNhapThuoc());
//		cbMaThuoc.setEditable(true);
//		cbMaThuoc.setSelectedItem("");
//		lblMaThuoc.setText("Tìm mã thuốc:");
//		btnGenerateMaThuoc.setEnabled(true);
//		cbMaNhaCungCap.setEditable(true);
//		cbMaNhaCungCap.setSelectedItem("");
//		lblMaNhaCungCap.setText("Tìm mã nhà cung cấp:");
//		btnGenerateMaThuoc.setEnabled(true);
//		txtTenThuoc.setEditable(false);
//		txtTenThuoc.setText("");
//		datePickerNgaySanXuat.getJFormattedTextField().setText("");
//		datePickerNgayHetHan.getJFormattedTextField().setText("");
//		cbLoaiThuoc.setSelectedIndex(-1);
//		txtDonViThuoc.setEditable(false);
//		txtDonViThuoc.setText("");
//		txtDonGiaMua.setEditable(false);
//		txtDonGiaMua.setText("");
//		txtXuatXu.setEditable(false);
//		txtXuatXu.setText("");
//		spinSoLuongNhap.setValue(1);
//		txtSoLuongTon.setText("0");
//		txtTenNhaCungCap.setEditable(false);
//		txtTenNhaCungCap.setText("");
//		txtEmail.setEditable(false);
//		txtEmail.setText("");
//		txtSoDienThoai.setEditable(false);
//		txtSoDienThoai.setText("");
//		textAreaDiaChi.setEditable(false);
//		textAreaDiaChi.setText("");
//		btnGenerateMaNCC.setEnabled(true);
//		btnLuuNhaCungCap.setEnabled(false);
//		btnThem.setEnabled(true);
//		tablePhieuNhapThuoc.clearSelection();
//		cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		btnGenerateMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		txtTenThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		datePickerNgaySanXuat.getJFormattedTextField().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		datePickerNgayHetHan.getJFormattedTextField().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		cbLoaiThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		txtDonViThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		txtDonGiaMua.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		txtXuatXu.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		cbMaNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		btnGenerateMaNCC.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		txtTenNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		txtSoDienThoai.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		textAreaDiaChi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		cbMaThuoc.requestFocus();
//	}
//	
    /**
     * Creates new form GUI_NhapThuoc
     */
    public GUI_NhapThuoc(NhanVien nv) {
    	this.emp = nv;
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
        jPanel38 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        txtNgayHienTai = new javax.swing.JTextField(layNgayHienTai());
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaPhieuNhap = new javax.swing.JTextField(phatSinhMaPhieuNhapThuoc());
        jPanel13 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTenThuoc = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        ngaySanXuat = new com.toedter.calendar.JDateChooser();
        jPanel15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cbLoaiThuoc = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtDonGiaMua = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        spinSoLuongNhap = new javax.swing.JSpinner();
        jPanel11 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        lblMaThuoc = new javax.swing.JLabel();
        cbMaThuoc = new javax.swing.JComboBox<>();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        btnGenerateMaThuoc = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtNgayNhap = new javax.swing.JTextField(layNgayHienTai());
        jPanel20 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        ngayHetHan = new com.toedter.calendar.JDateChooser();
        jPanel21 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtDonViThuoc = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtXuatXu = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtSoLuongTon = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        lblMaNhaCungCap = new javax.swing.JLabel();
        cbMaNhaCungCap = new javax.swing.JComboBox<>();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        btnGenerateMaNCC = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtTenNhaCungCap = new javax.swing.JTextField();
        jPanel28 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaDiaChi = new javax.swing.JTextArea();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtTongTienNhapThuoc = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 32767));
        jPanel37 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePhieuNhapThuoc = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel38.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nhanvien/pngegg.png"))); // NOI18N
        jPanel39.add(jLabel1);

        jPanel38.add(jPanel39);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(15, 102, 165));
        jLabel2.setText("TÌM KIẾM HÓA ĐƠN");
        jPanel6.add(jLabel2);

        jPanel38.add(jPanel6);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 30));

        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel3.setText("Ngày hiện tại");
        jPanel7.add(jLabel3);
        jPanel7.add(filler1);

        txtNgayHienTai.setMinimumSize(new java.awt.Dimension(180, 22));
        txtNgayHienTai.setPreferredSize(new java.awt.Dimension(180, 33));
        jPanel7.add(txtNgayHienTai);

        jPanel5.add(jPanel7);

        jPanel38.add(jPanel5);

        jPanel1.add(jPanel38);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 8, 1, 8));
        jPanel4.setMaximumSize(new java.awt.Dimension(33217, 300));
        jPanel4.setPreferredSize(new java.awt.Dimension(862, 300));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Nhập thông tin thuốc"));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jPanel10.setLayout(new java.awt.GridLayout(7, 1, 0, 10));

        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.LINE_AXIS));

        jLabel4.setText("   Mã phiếu:");
        jLabel4.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel12.add(jLabel4);

        txtMaPhieuNhap.setEditable(false);
        jPanel12.add(txtMaPhieuNhap);

        jPanel10.add(jPanel12);

        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setText("   Tên thuốc:");
        jLabel5.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel13.add(jLabel5);

        txtTenThuoc.setEditable(false);
        jPanel13.add(txtTenThuoc);

        jPanel10.add(jPanel13);

        jPanel14.setMinimumSize(new java.awt.Dimension(120, 22));
        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.LINE_AXIS));

        jLabel6.setText("   Ngày sản xuất:");
        jLabel6.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel14.add(jLabel6);
        jPanel14.add(ngaySanXuat);

        jPanel10.add(jPanel14);

        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.LINE_AXIS));

        jLabel7.setText("   Loại thuốc:");
        jLabel7.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel15.add(jLabel7);

        jPanel15.add(cbLoaiThuoc);

        jPanel10.add(jPanel15);

        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jLabel8.setText("   Đơn giá mua:");
        jLabel8.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel16.add(jLabel8);

        txtDonGiaMua.setEditable(false);
        jPanel16.add(txtDonGiaMua);

        jPanel10.add(jPanel16);

        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setText("   Số lượng nhập:");
        jLabel9.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel17.add(jLabel9);
        jPanel17.add(spinSoLuongNhap);

        jPanel10.add(jPanel17);

        jPanel8.add(jPanel10);

        jPanel11.setLayout(new java.awt.GridLayout(7, 1, 0, 10));

        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.LINE_AXIS));

        lblMaThuoc.setText("Tìm mã thuốc: ");
        lblMaThuoc.setMaximumSize(new java.awt.Dimension(85, 16));
        lblMaThuoc.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel18.add(lblMaThuoc);

        cbMaThuoc.setEditable(true);
        cbMaThuoc.setMaximumSize(new java.awt.Dimension(240, 32767));
        cbMaThuoc.setMinimumSize(new java.awt.Dimension(180, 22));
        cbMaThuoc.setPreferredSize(new java.awt.Dimension(240, 22));
        cbMaThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMaThuocActionPerformed(evt);
            }
        });
        jPanel18.add(cbMaThuoc);
        jPanel18.add(filler2);

        btnGenerateMaThuoc.setText("Tìm");
        btnGenerateMaThuoc.setMaximumSize(new java.awt.Dimension(75, 35));
        btnGenerateMaThuoc.setMinimumSize(new java.awt.Dimension(75, 35));
        btnGenerateMaThuoc.setPreferredSize(new java.awt.Dimension(75, 35));
        btnGenerateMaThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateMaThuocActionPerformed(evt);
            }
        });
        jPanel18.add(btnGenerateMaThuoc);

        jPanel11.add(jPanel18);

        jPanel19.setLayout(new javax.swing.BoxLayout(jPanel19, javax.swing.BoxLayout.LINE_AXIS));

        jLabel11.setText("Ngày nhập:");
        jLabel11.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel19.add(jLabel11);

        txtNgayNhap.setEditable(false);
        jPanel19.add(txtNgayNhap);

        jPanel11.add(jPanel19);

        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.LINE_AXIS));

        jLabel12.setText("Ngày hết hạn:");
        jLabel12.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel20.add(jLabel12);
        jPanel20.add(ngayHetHan);

        jPanel11.add(jPanel20);

        jPanel21.setLayout(new javax.swing.BoxLayout(jPanel21, javax.swing.BoxLayout.LINE_AXIS));

        jLabel13.setText("Đơn vị thuốc:");
        jLabel13.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel21.add(jLabel13);

        txtDonViThuoc.setEditable(false);
        jPanel21.add(txtDonViThuoc);

        jPanel11.add(jPanel21);

        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jLabel14.setText("Xuất xứ:");
        jLabel14.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel22.add(jLabel14);

        txtXuatXu.setEditable(false);
        jPanel22.add(txtXuatXu);

        jPanel11.add(jPanel22);

        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        jLabel15.setText("Số lượng tồn: ");
        jLabel15.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel23.add(jLabel15);

        txtSoLuongTon.setEditable(false);
        jPanel23.add(txtSoLuongTon);

        jPanel11.add(jPanel23);

        jPanel25.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel25.add(btnThem);

        btnXoaTrang.setText("Xóa trắng");
        jPanel25.add(btnXoaTrang);

        btnXoa.setText("Xóa");
        jPanel25.add(btnXoa);

        jButton1.setText("Lưu");
        jPanel25.add(jButton1);

        jPanel11.add(jPanel25);

        jPanel8.add(jPanel11);

        jPanel4.add(jPanel8);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Nhập nhà cung cấp"));
        jPanel9.setMaximumSize(new java.awt.Dimension(480, 32767));
        jPanel9.setMinimumSize(new java.awt.Dimension(480, 155));
        jPanel9.setPreferredSize(new java.awt.Dimension(480, 300));
        jPanel9.setLayout(new java.awt.GridLayout(7, 1, 0, 10));

        jPanel26.setLayout(new javax.swing.BoxLayout(jPanel26, javax.swing.BoxLayout.LINE_AXIS));

        lblMaNhaCungCap.setText("   Mã nhà cung cấp: ");
        lblMaNhaCungCap.setMaximumSize(new java.awt.Dimension(120, 16));
        lblMaNhaCungCap.setPreferredSize(new java.awt.Dimension(120, 16));
        jPanel26.add(lblMaNhaCungCap);

        cbMaNhaCungCap.setEditable(true);
        cbMaNhaCungCap.setMaximumSize(new java.awt.Dimension(240, 32767));
        cbMaNhaCungCap.setPreferredSize(new java.awt.Dimension(240, 22));
        cbMaNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMaNhaCungCapActionPerformed(evt);
            }
        });
        jPanel26.add(cbMaNhaCungCap);
        jPanel26.add(filler4);

        btnGenerateMaNCC.setText("Tìm");
        btnGenerateMaNCC.setMaximumSize(new java.awt.Dimension(72, 35));
        btnGenerateMaNCC.setMinimumSize(new java.awt.Dimension(72, 35));
        btnGenerateMaNCC.setPreferredSize(new java.awt.Dimension(72, 35));
        jPanel26.add(btnGenerateMaNCC);

        jPanel9.add(jPanel26);

        jPanel27.setLayout(new javax.swing.BoxLayout(jPanel27, javax.swing.BoxLayout.LINE_AXIS));

        jLabel17.setText("   Tên nhà cung cấp: ");
        jLabel17.setMaximumSize(new java.awt.Dimension(104, 16));
        jLabel17.setMinimumSize(new java.awt.Dimension(104, 16));
        jLabel17.setPreferredSize(new java.awt.Dimension(120, 16));
        jPanel27.add(jLabel17);

        txtTenNhaCungCap.setEditable(false);
        jPanel27.add(txtTenNhaCungCap);

        jPanel9.add(jPanel27);

        jPanel28.setLayout(new javax.swing.BoxLayout(jPanel28, javax.swing.BoxLayout.LINE_AXIS));

        jLabel18.setText("   Email:");
        jLabel18.setMaximumSize(new java.awt.Dimension(104, 16));
        jLabel18.setMinimumSize(new java.awt.Dimension(104, 16));
        jLabel18.setPreferredSize(new java.awt.Dimension(120, 16));
        jPanel28.add(jLabel18);

        txtEmail.setEditable(false);
        jPanel28.add(txtEmail);

        jPanel9.add(jPanel28);

        jPanel29.setLayout(new javax.swing.BoxLayout(jPanel29, javax.swing.BoxLayout.LINE_AXIS));

        jLabel19.setText("   Số điện thoại:");
        jLabel19.setMaximumSize(new java.awt.Dimension(104, 16));
        jLabel19.setMinimumSize(new java.awt.Dimension(104, 16));
        jLabel19.setPreferredSize(new java.awt.Dimension(120, 16));
        jPanel29.add(jLabel19);

        txtSoDienThoai.setEditable(false);
        jPanel29.add(txtSoDienThoai);

        jPanel9.add(jPanel29);

        jPanel30.setLayout(new javax.swing.BoxLayout(jPanel30, javax.swing.BoxLayout.LINE_AXIS));

        jLabel20.setText("   Địa chỉ: ");
        jLabel20.setMinimumSize(new java.awt.Dimension(104, 16));
        jLabel20.setPreferredSize(new java.awt.Dimension(120, 16));
        jPanel30.add(jLabel20);

        textAreaDiaChi.setEditable(false);
        textAreaDiaChi.setColumns(20);
        textAreaDiaChi.setRows(5);
        jScrollPane2.setViewportView(textAreaDiaChi);

        jPanel30.add(jScrollPane2);

        jPanel9.add(jPanel30);

        jPanel32.setLayout(new java.awt.GridLayout(1, 2));

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel32.add(jPanel33);

        jPanel35.setLayout(new java.awt.GridLayout(1, 0));

        jPanel36.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel36.add(jPanel34);

        jPanel35.add(jPanel36);

        jPanel32.add(jPanel35);

        jPanel9.add(jPanel32);

        jPanel31.setLayout(new javax.swing.BoxLayout(jPanel31, javax.swing.BoxLayout.LINE_AXIS));
        jPanel9.add(jPanel31);

        jPanel4.add(jPanel9);

        jPanel1.add(jPanel4);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 8, 10, 8));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 0, 51));
        jLabel21.setText("Tổng tiền thuốc nhập: ");
        jPanel3.add(jLabel21);

        txtTongTienNhapThuoc.setMaximumSize(new java.awt.Dimension(160, 35));
        txtTongTienNhapThuoc.setMinimumSize(new java.awt.Dimension(160, 35));
        txtTongTienNhapThuoc.setPreferredSize(new java.awt.Dimension(160, 35));
        jPanel3.add(txtTongTienNhapThuoc);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("VND");
        jPanel3.add(jLabel22);
        jPanel3.add(filler3);

        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách nhập thuốc"));
        jPanel37.setLayout(new java.awt.BorderLayout());

        tablePhieuNhapThuoc.setModel(modelPhieuNhapThuoc = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã phiếu", "Mã thuốc", "Tên thuốc", "Ngày nhập", "Ngày sản xuất", "Ngày hết hạn", "Loại thuốc", "Đơn vị thuốc", "Đơn giá mua", "Xuất xứ", "Số lượng nhập"
            }
        ));
        jScrollPane1.setViewportView(tablePhieuNhapThuoc);

        jPanel37.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel37, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        	boolean themThuoc = false;
			boolean themNCC = false;
			boolean themPhieuNhapThuoc = false;

			if (validData() && !btnGenerateMaThuoc.isEnabled() && !btnGenerateMaNCC.isEnabled()) {
				NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
				if (use_phatSinhMaNhaCungCap) {
					if (ncc_dao.themNhaCungCap(taoNhaCungCap())) {
						themNCC = true;
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhà cung cấp vào CSDL",
									"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
					}
					} else {
						// Nhà cung cấp đã được thêm thành công trước đó
						themNCC = true;
					}

					Thuoc_DAO thuoc_dao = new Thuoc_DAO();
					if (use_phatSinhMaThuoc) {
						if (thuoc_dao.themThuoc(taoThuoc())) {
							themThuoc = true;
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Lỗi khi thêm thuốc vào CSDL", "Lỗi kết nối CSDL",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						Thuoc thuoc = taoThuoc();
						if (thuoc_dao.capNhatSoLuongTonCuaThuoc(thuoc.getSoLuongTon(), thuoc.getMaThuoc())) {
							themThuoc = true;
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng tồn của thuốc",
									"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
						}
					}
					PhieuNhapThuoc phieuNhapThuoc = taoPhieuNhapThuoc();
					PhieuNhapThuoc_DAO phieunhapthuoc_dao = new PhieuNhapThuoc_DAO();
					if (phieunhapthuoc_dao.themPhieuNhapThuoc(phieuNhapThuoc, emp)) {
						themPhieuNhapThuoc = true;
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Lỗi khi thêm phiếu nhập thuốc vào CSDL",
								"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
					}

					if (themThuoc && themNCC && themPhieuNhapThuoc) {
						modelPhieuNhapThuoc.addRow(new Object[] { phieuNhapThuoc.getMaPhieu(),
								phieuNhapThuoc.getThuoc().getMaThuoc(), phieuNhapThuoc.getThuoc().getTenThuoc(),
								sdf.format(phieuNhapThuoc.getNgayNhap()), sdf.format(phieuNhapThuoc.getNgaySanXuat()),
								sdf.format(phieuNhapThuoc.getNgayHetHan()), phieuNhapThuoc.getThuoc().getLoaiThuoc(),
								phieuNhapThuoc.getThuoc().getDonViThuoc(), phieuNhapThuoc.getDonGiaMua(),
								phieuNhapThuoc.getThuoc().getXuatXu(), phieuNhapThuoc.getSoLuongNhap() });
						btnThem.setEnabled(false);
						btnXoa.setEnabled(true);
						float tongTienNhapThuoc = phieuNhapThuoc.getDonGiaMua() * phieuNhapThuoc.getSoLuongNhap();
						txtTongTienNhapThuoc.setText(fmt.format(tongTienNhapThuoc));
						JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thuốc thành công");
					} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thuốc không thành công", "Error",
								JOptionPane.ERROR_MESSAGE);
			}
		}
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnGenerateMaThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateMaThuocActionPerformed
       	if (use_event_cbMaThuoc) {
					String tuHienTai = cbMaThuoc.getSelectedItem().toString().trim();
					int row_count = cbMaThuoc.getItemCount();
					if (!tuHienTai.equalsIgnoreCase("")) {
						String[] tachTuHienTai = tuHienTai.split("-");
						String maThuoc = tachTuHienTai[0].trim();
						if (row_count > 0) {
							if (row_count == 1) {
								generateThuocByMaThuoc(maThuoc);
							}
							cbMaThuoc.setSelectedItem(maThuoc);
							cbMaThuoc.setEditable(false);
							lblMaThuoc.setText("Mã thuốc:");
						} else {
							phatSinhMaThuoc();
							cbMaThuoc.setEditable(false);
						}
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(cbMaThuoc, "Bạn phải nhập mã thuốc", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					}
					btnGenerateMaThuoc.setEnabled(false);
					btnGenerateMaThuoc.setBorder(BorderFactory.createEmptyBorder());
					cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				} else {
					cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
					cbMaThuoc.requestFocus();
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaThuoc,
							"Vui lòng nhấn Enter trong khung nhập liệu \"Tìm mã thuốc\" trước khi nhấn nút này",
							"Cảnh báo", JOptionPane.WARNING_MESSAGE);
				}
	}//GEN-LAST:event_btnGenerateMaThuocActionPerformed

    private void cbMaThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMaThuocActionPerformed
        // TODO add your handling code here:
    			use_event_cbMaThuoc = true;
				ArrayList<String> cacPhanTuHienCo = initCandidateThuoc();
				int soPhanTuHienCo = cacPhanTuHienCo.size();
				String tuHienTai = cbMaThuoc.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						cbMaThuoc.removeAllItems();
						for (int i = 0; i < soPhanTuHienCo; i++) {
							cbMaThuoc.addItem(cacPhanTuHienCo.get(i));
						}
						cbMaThuoc.setSelectedItem("");
						cbMaThuoc.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaThuoc.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaThuoc.addItem(cacUngVien.get(i));
							}
							cbMaThuoc.setSelectedItem(tuHienTai);
							cbMaThuoc.showPopup();
						} else if (soUngVien == 0) {
							cbMaThuoc.removeAllItems();
							phatSinhMaThuoc();
						}
					}
				} else {
					phatSinhMaThuoc();
				}
    }//GEN-LAST:event_cbMaThuocActionPerformed

    private void cbMaNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMaNhaCungCapActionPerformed
        // TODO add your handling code here:
        	use_event_cbMaNhaCungCap = true;
				ArrayList<String> cacPhanTuHienCo = initCandidateNhaCungCap();
				int soPhanTuHienCo = cacPhanTuHienCo.size();
				String tuHienTai = cbMaNhaCungCap.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						cbMaNhaCungCap.removeAllItems();
						for (int i = 0; i < soPhanTuHienCo; i++) {
							cbMaNhaCungCap.addItem(cacPhanTuHienCo.get(i));
						}
						cbMaNhaCungCap.setSelectedItem("");
						cbMaNhaCungCap.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaNhaCungCap.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaNhaCungCap.addItem(cacUngVien.get(i));
							}
							cbMaNhaCungCap.setSelectedItem(tuHienTai);
							cbMaNhaCungCap.showPopup();
						} else if (soUngVien == 0) {
							cbMaNhaCungCap.removeAllItems();
							phatSinhMaNhaCungCap();
						}
					}
				} else {
					phatSinhMaNhaCungCap();
		}
    }//GEN-LAST:event_cbMaNhaCungCapActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerateMaNCC;
    private javax.swing.JButton btnGenerateMaThuoc;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbLoaiThuoc;
    private javax.swing.JComboBox<String> cbMaNhaCungCap;
    private javax.swing.JComboBox<String> cbMaThuoc;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMaNhaCungCap;
    private javax.swing.JLabel lblMaThuoc;
    private com.toedter.calendar.JDateChooser ngayHetHan;
    private com.toedter.calendar.JDateChooser ngaySanXuat;
    private javax.swing.JSpinner spinSoLuongNhap;
    private javax.swing.JTable tablePhieuNhapThuoc;
    private javax.swing.JTextArea textAreaDiaChi;
    private javax.swing.JTextField txtDonGiaMua;
    private javax.swing.JTextField txtDonViThuoc;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaPhieuNhap;
    private javax.swing.JTextField txtNgayHienTai;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtSoLuongTon;
    private javax.swing.JTextField txtTenNhaCungCap;
    private javax.swing.JTextField txtTenThuoc;
    private javax.swing.JTextField txtTongTienNhapThuoc;
    private javax.swing.JTextField txtXuatXu;
    // End of variables declaration//GEN-END:variables
}
