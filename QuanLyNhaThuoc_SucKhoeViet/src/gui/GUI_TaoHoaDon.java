/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.PhieuNhapThuoc_DAO;
import dao.Thuoc_DAO;
import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.KhachHang;
import entities.NhaCungCap;
import entities.NhanVien;
import entities.Thuoc;


import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.json.ParseException;






/**
 *
 * @author NguyenThanhLuan
 */
public class GUI_TaoHoaDon extends javax.swing.JPanel{
	private Connection con;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DecimalFormat fmt = new DecimalFormat("###,###");
	private HoaDon hoaDon;
	private Thuoc_DAO thuoc_dao = new Thuoc_DAO();
	private ArrayList<Thuoc> danhSachThuoc = thuoc_dao.layTatCaThuocTonKho();
	private KhachHang_DAO khachhang_dao = new KhachHang_DAO();
	private HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();
	private ArrayList<KhachHang> danhSachKhachHang = khachhang_dao.layTatCaKhachHang();
    private DefaultTableModel modelChiTietHoaDon;
    private final float mienGiam = 0.7f;
	private boolean use_event_cbMaThuoc = false;
	private boolean use_event_cbMaKhachHang = false;
	private boolean use_event_spinner = false;
	private NhanVien emp;
	
	

	private KhachHang taoKhachHang() {
		String maKH = cbMaKhachHang.getSelectedItem().toString().split("-")[0].trim();
		KhachHang kh = new KhachHang(maKH);
		return danhSachKhachHang.get(danhSachKhachHang.indexOf(kh));
	}

	private Thuoc taoThuoc() {
		String maThuoc = cbMaThuoc.getSelectedItem().toString().split("-")[0].trim();
		Thuoc thuoc = new Thuoc(maThuoc);
		return danhSachThuoc.get(danhSachThuoc.indexOf(thuoc));
	}

	private ChiTietHoaDon taoChiTietHoaDon() {
		Thuoc thuoc = taoThuoc();
		int soLuong = Integer.parseInt(spinSoLuongBan.getValue().toString().trim());
		String donGiaBan = txtDonGiaBan.getText().trim();
		return new ChiTietHoaDon(thuoc, soLuong, Float.parseFloat(loaiBoDinhDangTien(donGiaBan)));
	}

	private HoaDon taoHoaDon(NhanVien emp) {
		String maHD = txtMaHoaDon.getText().split("-")[0].trim();
		KhachHang kh = taoKhachHang();
		Date ngayLapHoaDon = new Date();
		String theBaoHiem = "Không";
		if (radCo.isSelected()) {
			theBaoHiem = "Có";
		}
		return new HoaDon(maHD, kh, emp, ngayLapHoaDon, theBaoHiem, mienGiam);
	}

	private String phatSinhMaHoaDon() {
		try {
			HoaDon_DAO hoadon_dao = new HoaDon_DAO();
			String maHD_lastest = hoadon_dao.layMaHDCuoiCung().trim();
			String maHD = "HD" + String.valueOf(txtNgayLapHoaDon.getText().split("-")[2]);
			String stt_string_lastest = maHD_lastest.substring(6, maHD_lastest.length());
			int stt_int_lastest = Integer.parseInt(stt_string_lastest);
			String stt_current = String.valueOf(stt_int_lastest + 1);
			for (int i = 0; i < (9 - stt_current.length()); i++) {
				maHD += "0";
			}
			return maHD += stt_current;
		} catch (NullPointerException e) {
			return "HD" + LocalDate.now().getYear() + "000000001";
		}
	}

	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
	}

	private ArrayList<String> initCandidateThuoc(ArrayList<Thuoc> ds) {
		ArrayList<String> dataSet = new ArrayList<String>();
		for (Thuoc thuoc : ds) {
			String element = thuoc.getMaThuoc() + " - " + thuoc.getTenThuoc() + " - "
					+ thuoc.getNhaCungCap().getTenNCC();
			dataSet.add(element);
		}
		return dataSet;
	}

	private ArrayList<String> initCandidateKhachHang(ArrayList<KhachHang> ds) {
		ArrayList<String> dataSet = new ArrayList<String>();
		for (KhachHang khachHang : ds) {
			String element = khachHang.getMaKH() + " - " + khachHang.getHoTenKhachHang();
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

	private void generateThuocByMaThuoc(String maThuoc, ArrayList<Thuoc> ds) {
		Thuoc thuoc = new Thuoc(maThuoc);
		if (ds.contains(thuoc)) {
			thuoc = ds.get(ds.indexOf(thuoc));
			NhaCungCap nhaCungCap = thuoc.getNhaCungCap();
			txtTenThuoc.setText(thuoc.getTenThuoc().trim());
			int soLuongTon = thuoc.getSoLuongTon();
			if (soLuongTon > 0) {
				SpinnerNumberModel modelSoLuongBanThucTe = new SpinnerNumberModel();
				modelSoLuongBanThucTe.setValue(1);
				modelSoLuongBanThucTe.setMinimum(1);
				spinSoLuongBan.setModel(modelSoLuongBanThucTe);
				spinSoLuongBan.setEditor(new JSpinner.NumberEditor(spinSoLuongBan));
			} else {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sản phẩm hiện tại không có hàng", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				btnThem.setEnabled(false);
			}
			PhieuNhapThuoc_DAO phieunhapthuoc_dao = new PhieuNhapThuoc_DAO();
			float donGiaBan = phieunhapthuoc_dao.layDonGiaMoiNhatTheoMaThuoc(maThuoc);
			txtDonGiaBan.setText(fmt.format(donGiaBan * 1.15));
			txtLoaiThuoc.setText(thuoc.getLoaiThuoc().trim());
			txtDonViThuoc.setText(thuoc.getDonViThuoc().trim());
			txtXuatXu.setText(thuoc.getXuatXu().trim());
			txtTenNhaCungCap.setText(thuoc.getNhaCungCap().getTenNCC());
			btnThem.setEnabled(true);
		}
	}

	private void generateKhachHangByMaKH(String maKH, ArrayList<KhachHang> ds) {
		KhachHang khachHang = new KhachHang(maKH);
		if (ds.contains(khachHang)) {
			khachHang = ds.get(ds.indexOf(khachHang));
			txtTenKhachHang.setText(khachHang.getHoTenKhachHang().trim());
			txtEmail.setText(khachHang.getEmail().trim());
			radKhong.setSelected(true);
			txtSoDienThoai.setText(khachHang.getSoDienThoai().trim());
			textAreaDiaChi.setText(khachHang.getDiaChi().trim());
		}
	}

	private void xoaHetDuLieuTrenTableModel() {
		modelChiTietHoaDon.getDataVector().removeAllElements();
	}

	private void capNhatDuLieuTrenBang() {
		xoaHetDuLieuTrenTableModel();
		int soThuTu = 1;
		for (ChiTietHoaDon cthd : hoaDon.getDanhSachChiTietHoaDon()) {
			Thuoc thuoc = cthd.getThuoc();
			float thanhTien = cthd.getDonGiaBan() * cthd.getSoLuong();
			modelChiTietHoaDon.addRow(new Object[] { soThuTu++, thuoc.getMaThuoc(), thuoc.getTenThuoc(),
					thuoc.getLoaiThuoc(), thuoc.getDonViThuoc(), thuoc.getXuatXu(), thuoc.getNhaCungCap().getTenNCC(),
					fmt.format(cthd.getDonGiaBan()), cthd.getSoLuong(), fmt.format(thanhTien) });
		}
		tableChiTietHoaDon.setModel(modelChiTietHoaDon);
	}

	private float tinhMienGiam() {
		if (radKhong.isSelected()) {
			return 0;
		} else {
			return hoaDon.tinhTongThanhTien() * mienGiam;
		}
	}

	private float tinhTongTienConLai() {
		return hoaDon.tinhTongThanhTien() - tinhMienGiam();
	}

	private void fillForm(int row) {
		if (row != -1) {
			String maThuoc = String.valueOf(tableChiTietHoaDon.getValueAt(row, 1));
			Thuoc thuoc = new Thuoc(maThuoc);
			ArrayList<Thuoc> dsThuoc = danhSachThuoc;
			if (dsThuoc.contains(thuoc)) {
				thuoc = dsThuoc.get(danhSachThuoc.indexOf(thuoc));
				cbMaThuoc.removeAllItems();
				cbMaThuoc.addItem(thuoc.getMaThuoc());
				cbMaThuoc.setSelectedItem(thuoc.getMaThuoc());
				cbMaThuoc.setEditable(false);
				txtTenThuoc.setText(thuoc.getTenThuoc());
				txtLoaiThuoc.setText(thuoc.getLoaiThuoc());
				txtDonViThuoc.setText(thuoc.getDonViThuoc());
				txtXuatXu.setText(thuoc.getXuatXu());
				txtTenNhaCungCap.setText(thuoc.getNhaCungCap().getTenNCC());
				int soLuongBan = (int) tableChiTietHoaDon.getValueAt(row, 8);
				spinSoLuongBan.setValue(soLuongBan);
				String donGiaBan = (String) tableChiTietHoaDon.getValueAt(row, 7);
				txtDonGiaBan.setText(donGiaBan);
			}
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

	private void xoaTrang() {
		use_event_cbMaThuoc = false;
		use_event_cbMaKhachHang = false;
		use_event_spinner = false;
		cbMaThuoc.setEditable(true);
		cbMaThuoc.setSelectedItem("");
		lblMaThuoc.setText("Tìm mã thuốc:");
		btnGenerateMaThuoc.setEnabled(true);
		txtTenThuoc.setText("");
		SpinnerNumberModel modelSoLuongBan = new SpinnerNumberModel(1, 1, 1, 0);
		spinSoLuongBan.setModel(modelSoLuongBan);
		spinSoLuongBan.setEditor(new JSpinner.DefaultEditor(spinSoLuongBan));
		txtDonGiaBan.setText("");
		txtLoaiThuoc.setText("");
		txtDonViThuoc.setText("");
		txtXuatXu.setText("");
		txtTenNhaCungCap.setText("");
		tableChiTietHoaDon.clearSelection();
		btnThem.setEnabled(true);
		cbMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnGenerateMaThuoc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		cbMaKhachHang.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnGenerateMaKH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		cbMaThuoc.requestFocus();
	}

	private boolean validDataAdd() {
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
			cbMaThuoc.setSelectedItem("");
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

		return true;
	}

	private boolean validDataSave() {
		try {
			String maKH = cbMaKhachHang.getSelectedItem().toString().trim();
			if (maKH.equalsIgnoreCase("")) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(cbMaKhachHang, "Bạn phải nhập mã khách hàng", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				cbMaKhachHang.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				cbMaKhachHang.requestFocus();
				return false;
			} else {
				cbMaKhachHang.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		} catch (NullPointerException e) {
			cbMaKhachHang.setSelectedItem("");
			return false;
		}

		if (btnGenerateMaKH.isEnabled()) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(btnGenerateMaKH, "Bạn chưa xác nhận mã khách hàng", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			btnGenerateMaKH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
			btnGenerateMaKH.requestFocus();
			return false;
		} else {
			btnGenerateMaKH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		int count_row = tableChiTietHoaDon.getRowCount();
		if (count_row < 1) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Hóa đơn chưa có sản phẩm nào", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}
    /**
     * Creates new form GUI_TaoHoaDon
     */
    public GUI_TaoHoaDon(NhanVien nhanVien) {
    	this.emp = nhanVien;
		SpinnerNumberModel modelSoLuongBan = new SpinnerNumberModel(1, 1, 1, 0);
		spinSoLuongBan = new JSpinner(modelSoLuongBan);
		spinSoLuongBan.setEditor(new JSpinner.DefaultEditor(spinSoLuongBan));
		hoaDon = new HoaDon(phatSinhMaHoaDon(), new KhachHang(), emp, new Date(), "", mienGiam);
        initComponents();
		ButtonGroup group = new ButtonGroup();
		group.add(radKhong);
		group.add(radCo);
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
        txtNgayHienTai = new javax.swing.JTextField(sdf.format(new Date()));
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField(phatSinhMaHoaDon());
        jPanel16 = new javax.swing.JPanel();
        lblMaThuoc = new javax.swing.JLabel();
        cbMaThuoc = new javax.swing.JComboBox<>();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 32767));
        btnGenerateMaThuoc = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        spinSoLuongBan = new javax.swing.JSpinner();
        jPanel18 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtLoaiThuoc = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtXuatXu = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtNgayLapHoaDon = new javax.swing.JTextField(sdf.format(new Date()));
        jPanel22 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtTenThuoc = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtDonGiaBan = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtDonViThuoc = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtTenNhaCungCap = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        lblMaKhachHang = new javax.swing.JLabel();
        cbMaKhachHang = new javax.swing.JComboBox<>();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        btnGenerateMaKH = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        radKhong = new javax.swing.JRadioButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        radCo = new javax.swing.JRadioButton();
        jPanel31 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        textAreaDiaChi = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableChiTietHoaDon = new javax.swing.JTable();
        jPanel38 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtTongTienThanhToan = new javax.swing.JTextField();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        jLabel21 = new javax.swing.JLabel();
        txtMienGiam = new javax.swing.JTextField();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        jLabel22 = new javax.swing.JLabel();
        txtTongTienConLai = new javax.swing.JTextField();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767));
        jPanel39 = new javax.swing.JPanel();
        btnBatDauHoaDonMoi = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        ALL.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(444, 400));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nhanvien/pngegg.png"))); // NOI18N
        jPanel8.add(jLabel1);

        jPanel4.add(jPanel8);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(15, 102, 165));
        jLabel2.setText("TẠO HÓA ĐƠN");
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
        txtNgayHienTai.setPreferredSize(new java.awt.Dimension(180, 30));
        jPanel7.add(txtNgayHienTai);

        jPanel5.add(jPanel7);

        jPanel4.add(jPanel5);

        jPanel2.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin hóa đơn"));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        jPanel11.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        jPanel13.setPreferredSize(new java.awt.Dimension(450, 188));
        jPanel13.setLayout(new java.awt.GridLayout(6, 1, 0, 10));

        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.LINE_AXIS));

        jLabel4.setText("Mã hóa đơn ");
        jLabel4.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel15.add(jLabel4);

        txtMaHoaDon.setEditable(false);
        jPanel15.add(txtMaHoaDon);

        jPanel13.add(jPanel15);

        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        lblMaThuoc.setText("Tìm mã thuốc: ");
        lblMaThuoc.setMaximumSize(new java.awt.Dimension(85, 16));
        lblMaThuoc.setMinimumSize(new java.awt.Dimension(85, 16));
        lblMaThuoc.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel16.add(lblMaThuoc);

        cbMaThuoc.setEditable(true);
        cbMaThuoc.setMaximumSize(new java.awt.Dimension(280, 32767));
        cbMaThuoc.setMinimumSize(new java.awt.Dimension(280, 22));
        cbMaThuoc.setPreferredSize(new java.awt.Dimension(340, 22));
        cbMaThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMaThuocActionPerformed(evt);
            }
        });
        jPanel16.add(cbMaThuoc);
        jPanel16.add(filler2);

        btnGenerateMaThuoc.setText("Tìm kiếm");
        btnGenerateMaThuoc.setMaximumSize(new java.awt.Dimension(79, 35));
        btnGenerateMaThuoc.setPreferredSize(new java.awt.Dimension(79, 35));
        btnGenerateMaThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateMaThuocActionPerformed(evt);
            }
        });
        jPanel16.add(btnGenerateMaThuoc);

        jPanel13.add(jPanel16);

        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        jLabel6.setText("Số lượng bán :");
        jLabel6.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel17.add(jLabel6);
        jPanel17.add(spinSoLuongBan);

        jPanel13.add(jPanel17);

        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.LINE_AXIS));

        jLabel7.setText("Loại thuốc :");
        jLabel7.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel18.add(jLabel7);

        txtLoaiThuoc.setEditable(false);
        txtLoaiThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoaiThuocActionPerformed(evt);
            }
        });
        jPanel18.add(txtLoaiThuoc);

        jPanel13.add(jPanel18);

        jPanel19.setLayout(new javax.swing.BoxLayout(jPanel19, javax.swing.BoxLayout.LINE_AXIS));

        jLabel8.setText("Xuất xứ: ");
        jLabel8.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel19.add(jLabel8);

        txtXuatXu.setEditable(false);
        jPanel19.add(txtXuatXu);

        jPanel13.add(jPanel19);

        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.LINE_AXIS));
        jPanel13.add(jPanel20);

        jPanel11.add(jPanel13);

        jPanel14.setLayout(new java.awt.GridLayout(6, 1, 0, 10));

        jPanel21.setLayout(new javax.swing.BoxLayout(jPanel21, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setText("Ngày lập:");
        jLabel9.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel21.add(jLabel9);

        txtNgayLapHoaDon.setEditable(false);
        jPanel21.add(txtNgayLapHoaDon);

        jPanel14.add(jPanel21);

        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jLabel10.setText("Tên thuốc:");
        jLabel10.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel22.add(jLabel10);

        txtTenThuoc.setEditable(false);
        jPanel22.add(txtTenThuoc);

        jPanel14.add(jPanel22);

        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        jLabel11.setText("Đơn giá bán:");
        jLabel11.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel23.add(jLabel11);

        txtDonGiaBan.setEditable(false);
        jPanel23.add(txtDonGiaBan);

        jPanel14.add(jPanel23);

        jPanel24.setLayout(new javax.swing.BoxLayout(jPanel24, javax.swing.BoxLayout.LINE_AXIS));

        jLabel12.setText("Đơn vị thuốc:");
        jLabel12.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel24.add(jLabel12);

        txtDonViThuoc.setEditable(false);
        jPanel24.add(txtDonViThuoc);

        jPanel14.add(jPanel24);

        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.LINE_AXIS));

        jLabel13.setText("Nhà cung cấp:");
        jLabel13.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel25.add(jLabel13);

        txtTenNhaCungCap.setEditable(false);
        txtTenNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNhaCungCapActionPerformed(evt);
            }
        });
        jPanel25.add(txtTenNhaCungCap);

        jPanel14.add(jPanel25);

        jPanel26.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 144, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        jPanel26.add(jPanel27);

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					btnThemActionPerformed(evt);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        jPanel26.add(btnThem);

        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        jPanel26.add(btnXoaTrang);

        jPanel14.add(jPanel26);

        jPanel11.add(jPanel14);

        jPanel10.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel10);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin khách hàng"));
        jPanel9.setMaximumSize(new java.awt.Dimension(400, 2147483647));
        jPanel9.setMinimumSize(new java.awt.Dimension(400, 23));
        jPanel9.setPreferredSize(new java.awt.Dimension(400, 112));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        jPanel12.setDoubleBuffered(false);
        jPanel12.setLayout(new java.awt.GridLayout(6, 1, 0, 10));

        jPanel28.setLayout(new javax.swing.BoxLayout(jPanel28, javax.swing.BoxLayout.LINE_AXIS));

        lblMaKhachHang.setText("Tìm mã khách hàng:");
        lblMaKhachHang.setMaximumSize(new java.awt.Dimension(115, 16));
        lblMaKhachHang.setPreferredSize(new java.awt.Dimension(115, 16));
        jPanel28.add(lblMaKhachHang);

        cbMaKhachHang.setEditable(true);
        cbMaKhachHang.setMaximumSize(new java.awt.Dimension(150, 32767));
        cbMaKhachHang.setMinimumSize(new java.awt.Dimension(100, 22));
        cbMaKhachHang.setPreferredSize(new java.awt.Dimension(150, 22));
        cbMaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMaKhachHangActionPerformed(evt);
            }
        });
        jPanel28.add(cbMaKhachHang);
        jPanel28.add(filler4);

        btnGenerateMaKH.setText("Tìm kiếm");
        btnGenerateMaKH.setPreferredSize(new java.awt.Dimension(80, 30));
        btnGenerateMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateMaKHActionPerformed(evt);
            }
        });
        jPanel28.add(btnGenerateMaKH);

        jPanel12.add(jPanel28);

        jPanel29.setLayout(new javax.swing.BoxLayout(jPanel29, javax.swing.BoxLayout.LINE_AXIS));

        jLabel15.setText("Tên khách hàng: ");
        jLabel15.setPreferredSize(new java.awt.Dimension(115, 16));
        jPanel29.add(jLabel15);

        txtTenKhachHang.setEnabled(false);
        jPanel29.add(txtTenKhachHang);

        jPanel12.add(jPanel29);

        jPanel30.setLayout(new javax.swing.BoxLayout(jPanel30, javax.swing.BoxLayout.LINE_AXIS));

        jLabel16.setText("Thẻ bảo hiểm:");
        jLabel16.setMaximumSize(new java.awt.Dimension(115, 16));
        jLabel16.setMinimumSize(new java.awt.Dimension(115, 16));
        jLabel16.setPreferredSize(new java.awt.Dimension(115, 16));
        jPanel30.add(jLabel16);
        jPanel30.add(filler3);

        radKhong.setSelected(true);
        radKhong.setText("Không");
        jPanel30.add(radKhong);
        jPanel30.add(filler5);

        radCo.setText("Có");
        radCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radCoActionPerformed(evt);
            }
        });
        jPanel30.add(radCo);

        jPanel12.add(jPanel30);

        jPanel31.setLayout(new javax.swing.BoxLayout(jPanel31, javax.swing.BoxLayout.LINE_AXIS));

        jLabel17.setText("Email: ");
        jLabel17.setMaximumSize(new java.awt.Dimension(115, 16));
        jLabel17.setMinimumSize(new java.awt.Dimension(115, 16));
        jLabel17.setPreferredSize(new java.awt.Dimension(115, 16));
        jPanel31.add(jLabel17);

        txtEmail.setEnabled(false);
        jPanel31.add(txtEmail);

        jPanel12.add(jPanel31);

        jPanel32.setLayout(new javax.swing.BoxLayout(jPanel32, javax.swing.BoxLayout.LINE_AXIS));

        jLabel18.setText("Số điện thoại: ");
        jLabel18.setMaximumSize(new java.awt.Dimension(115, 16));
        jLabel18.setMinimumSize(new java.awt.Dimension(115, 16));
        jLabel18.setPreferredSize(new java.awt.Dimension(115, 16));
        jPanel32.add(jLabel18);

        txtSoDienThoai.setEnabled(false);
        jPanel32.add(txtSoDienThoai);

        jPanel12.add(jPanel32);

        jPanel33.setLayout(new javax.swing.BoxLayout(jPanel33, javax.swing.BoxLayout.LINE_AXIS));

        jLabel19.setText("Địa chỉ:");
        jLabel19.setMaximumSize(new java.awt.Dimension(115, 16));
        jLabel19.setMinimumSize(new java.awt.Dimension(115, 16));
        jLabel19.setPreferredSize(new java.awt.Dimension(115, 16));
        jPanel33.add(jLabel19);

        textAreaDiaChi.setEnabled(false);
        jPanel33.add(textAreaDiaChi);

        jPanel12.add(jPanel33);

        jPanel9.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel9);

        jPanel2.add(jPanel1, java.awt.BorderLayout.CENTER);

        ALL.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi tiết hóa đơn"));
        jPanel34.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setEnabled(false);

        tableChiTietHoaDon.setModel(modelChiTietHoaDon = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã thuốc", "Tên thuốc", "Loại thuốc", "Đơn vị thuốc", "Xuất xứ", "Nhà cung cấp", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ));
        tableChiTietHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableChiTietHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableChiTietHoaDon);

        jPanel34.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel38.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel35.setLayout(new javax.swing.BoxLayout(jPanel35, javax.swing.BoxLayout.LINE_AXIS));

        jLabel20.setText("Tổng tiền");
        jPanel35.add(jLabel20);

        txtTongTienThanhToan.setMinimumSize(new java.awt.Dimension(100, 22));
        txtTongTienThanhToan.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel35.add(txtTongTienThanhToan);
        jPanel35.add(filler7);

        jLabel21.setText("Miễn giảm");
        jPanel35.add(jLabel21);

        txtMienGiam.setMinimumSize(new java.awt.Dimension(100, 22));
        txtMienGiam.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel35.add(txtMienGiam);
        jPanel35.add(filler6);

        jLabel22.setText("Thành tiền: ");
        jPanel35.add(jLabel22);

        txtTongTienConLai.setMinimumSize(new java.awt.Dimension(100, 22));
        txtTongTienConLai.setPreferredSize(new java.awt.Dimension(100, 30));
        txtTongTienConLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienConLaiActionPerformed(evt);
            }
        });
        jPanel35.add(txtTongTienConLai);
        jPanel35.add(filler8);

        jPanel38.add(jPanel35);

        jPanel39.setPreferredSize(new java.awt.Dimension(600, 35));
        jPanel39.setLayout(new java.awt.GridLayout(1, 0, 8, 0));

        btnBatDauHoaDonMoi.setText("Làm mới");
        btnBatDauHoaDonMoi.setPreferredSize(new java.awt.Dimension(103, 23));
        btnBatDauHoaDonMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatDauHoaDonMoiActionPerformed(evt);
            }
        });
        jPanel39.add(btnBatDauHoaDonMoi);

        jButton12.setText("Tính tiền thừa");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel39.add(jButton12);

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel39.add(btnCapNhat);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel39.add(btnXoa);

        btnLuu.setText("Thanh toán");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });
        jPanel39.add(btnLuu);

        jPanel38.add(jPanel39);

        jPanel34.add(jPanel38, java.awt.BorderLayout.SOUTH);

        jPanel3.add(jPanel34, java.awt.BorderLayout.CENTER);

        ALL.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(ALL, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtLoaiThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoaiThuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoaiThuocActionPerformed

    private void txtTenNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNhaCungCapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNhaCungCapActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
       xoaTrang();
    }//GEN-LAST:event_btnXoaTrangActionPerformed
    
    // trả về 1 mảng các danh sách
	
    private void cbMaThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMaThuocActionPerformed
    	use_event_cbMaThuoc = true;
		ArrayList<String> cacPhanTuHienCo = initCandidateThuoc(danhSachThuoc);
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
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaThuoc, "Không tìm thấy mã thuốc nào phù hợp", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(cbMaThuoc, "Không tìm thấy mã thuốc nào trong CSDL", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
//GEN-LAST:event_cbMaThuocActionPerformed

    private void btnGenerateMaThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateMaThuocActionPerformed
    	if (use_event_cbMaThuoc) {
			String tuHienTai = cbMaThuoc.getSelectedItem().toString().trim();
			int row_count = cbMaThuoc.getItemCount();
			if (!tuHienTai.equalsIgnoreCase("")) {
				String[] tachTuHienTai = tuHienTai.split("-");
				String maThuoc = tachTuHienTai[0].trim();
				if (row_count > 0) {
					if (row_count == 1) {
						generateThuocByMaThuoc(maThuoc, danhSachThuoc);
					}
					cbMaThuoc.setSelectedItem(maThuoc);
					cbMaThuoc.setEditable(false);
					lblMaThuoc.setText("Mã thuốc:");
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaThuoc, "Không tìm thấy mã thuốc nào", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(cbMaThuoc, "Bạn phải nhập mã thuốc", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			}
			btnGenerateMaThuoc.setEnabled(false);
		} else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(cbMaThuoc,
					"Vui lòng nhấn Enter trong khung nhập liệu \"Tìm mã thuốc\" trước khi nhấn nút này",
					"Cảnh báo", JOptionPane.WARNING_MESSAGE);
		}
	}//GEN-LAST:event_btnGenerateMaThuocActionPerformed
	
	
	

    private void btnGenerateMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateMaKHActionPerformed
    	if (use_event_cbMaKhachHang) {
			String tuHienTai = cbMaKhachHang.getSelectedItem().toString().trim();
			int row_count = cbMaKhachHang.getItemCount();
			if (!tuHienTai.equalsIgnoreCase("")) {
				String[] tachTuHienTai = tuHienTai.split("-");
				String maKH = tachTuHienTai[0].trim();
				if (row_count > 0) {
					if (row_count == 1) {
						generateKhachHangByMaKH(maKH, danhSachKhachHang);
					}
					cbMaKhachHang.setSelectedItem(maKH);
					cbMaKhachHang.setEditable(false);
					lblMaKhachHang.setText("Mã khách hàng:");
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(cbMaKhachHang, "Không tìm thấy mã khách hàng nào",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(cbMaKhachHang, "Bạn phải nhập mã khách hàng", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			}
			btnGenerateMaKH.setEnabled(false);
		} else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(cbMaKhachHang,
					"Vui lòng nhấn Enter trong khung nhập liệu \"Tìm mã khách hàng\" trước khi nhấn nút này",
					"Cảnh báo", JOptionPane.WARNING_MESSAGE);
		}
	}//GEN-LAST:event_btnGenerateMaKHActionPerformed

    private void cbMaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMaKhachHangActionPerformed
    			use_event_cbMaKhachHang = true;
				ArrayList<String> cacPhanTuHienCo = initCandidateKhachHang(danhSachKhachHang);
				int soPhanTuHienCo = cacPhanTuHienCo.size();
				String tuHienTai = cbMaKhachHang.getSelectedItem().toString().trim();
				if (soPhanTuHienCo > 0) {
					if (tuHienTai.equalsIgnoreCase("")) {
						cbMaKhachHang.removeAllItems();
						for (int i = 0; i < soPhanTuHienCo; i++) {
							cbMaKhachHang.addItem(cacPhanTuHienCo.get(i));
						}
						cbMaKhachHang.setSelectedItem("");
						cbMaKhachHang.showPopup();
					} else {
						ArrayList<String> cacUngVien = searchCandidate(tuHienTai, cacPhanTuHienCo);
						int soUngVien = cacUngVien.size();
						if (soUngVien > 0) {
							cbMaKhachHang.removeAllItems();
							for (int i = 0; i < soUngVien; i++) {
								cbMaKhachHang.addItem(cacUngVien.get(i));
							}
							cbMaKhachHang.setSelectedItem(tuHienTai);
							cbMaKhachHang.showPopup();
						} else if (soUngVien == 0) {
							cbMaKhachHang.removeAllItems();
							Toolkit.getDefaultToolkit().beep();
							int confirm = JOptionPane.showConfirmDialog(cbMaKhachHang,
									"Không tìm thấy mã khách hàng này trong CSDL. Bạn có muốn thêm khách hàng này vào CSDL hay không?",
									"Thông báo", JOptionPane.YES_NO_OPTION);
							if (confirm == JOptionPane.YES_NO_OPTION) {
//								ThemKhacHang_GUI themKhacHang = new ThemKhacHang_GUI(emp);
//								themKhacHang.setVisible(true);
//								setVisible(false);
							} else {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(cbMaKhachHang,
										"Bạn không thể tiếp tục hóa đơn khi chưa thêm khách hàng vào CSDL",
										"Lỗi không thiếu thông tin khách hàng", JOptionPane.ERROR_MESSAGE);
								setVisible(false);
							}
						}
					} }
	}//GEN-LAST:event_cbMaKhachHangActionPerformed

    private void radCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radCoActionPerformed
    	if (use_event_cbMaKhachHang) {
			String tuHienTai = cbMaKhachHang.getSelectedItem().toString().trim();
			int row_count = cbMaKhachHang.getItemCount();
			if (!tuHienTai.equalsIgnoreCase("")) {
				String[] tachTuHienTai = tuHienTai.split("-");
				String maKH = tachTuHienTai[0].trim();
				KhachHang khachHang = new KhachHang(maKH);
				if (danhSachKhachHang.contains(khachHang)) {
					khachHang = danhSachKhachHang.get(danhSachKhachHang.indexOf(khachHang));
					if (khachHang.getMaTheBaoHiem().trim().equalsIgnoreCase("")) {
						radKhong.setSelected(true);
						radCo.setEnabled(false);
						Toolkit.getDefaultToolkit().beep();
						int confirm = JOptionPane.showConfirmDialog(radCo,
								"Khách hàng hiện tại chưa nhập mã thẻ bảo hiểm. Bạn có muốn cập nhật lại thông tin khách hàng này hay không?",
								"Thông báo", JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION) {
//							SuaThongTinKhacHang_GUI suaThongTinKhacHang = new SuaThongTinKhacHang_GUI(khachHang,
//									emp, getTitle());
//							suaThongTinKhacHang.setVisible(true);
						}
					}
				}
			}
		}
	}
//GEN-LAST:event_radCoActionPerformed

    private void btnBatDauHoaDonMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatDauHoaDonMoiActionPerformed
    }//GEN-LAST:event_btnBatDauHoaDonMoiActionPerformed
    
	
    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int row = tableChiTietHoaDon.getSelectedRow();
				if (row != -1) {
					Toolkit.getDefaultToolkit().beep();
					int confirm = JOptionPane.showConfirmDialog(null,
							"Bạn có chắc chắn muốn xóa dòng sản phẩm này hay không?", "Chú ý",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						if (hoaDon.xoaMotChiTietHoaDon(row)) {
							tableChiTietHoaDon.clearSelection();
							capNhatDuLieuTrenBang();
							xoaTrang();
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Đã xóa mục thành công", "Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn mục cần xóa", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				}
			}//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) throws java.text.ParseException {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        try {
					spinSoLuongBan.commitEdit();
					if (use_event_spinner) {
						String maThuoc = cbMaThuoc.getSelectedItem().toString().trim();
						Thuoc_DAO thuoc_dao = new Thuoc_DAO();
						int soLuongTon = thuoc_dao.laySoLuongTonCuaThuoc(maThuoc);
						Toolkit.getDefaultToolkit().beep();
						int confirm = JOptionPane.showConfirmDialog(null, "Bạn có muốn mua toàn bộ " + soLuongTon + " sản phẩm " + txtTenThuoc.getText() + " hay không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION) {
							if (validDataAdd()) {
								ChiTietHoaDon cthd = taoChiTietHoaDon();
								if (!hoaDon.themMotChiTietHoaDon(cthd)) {
									Toolkit.getDefaultToolkit().beep();
									JOptionPane.showMessageDialog(null, "Bị trùng mã thuốc", "Cảnh báo",
											JOptionPane.WARNING_MESSAGE);
								} else {
									capNhatDuLieuTrenBang();
									txtTongTienThanhToan.setText(fmt.format(hoaDon.tinhTongThanhTien()));
									txtMienGiam.setText(fmt.format(tinhMienGiam()));
								}
								btnThem.setEnabled(false);
							}
						}
					} else {
						if (validDataAdd()) {
							ChiTietHoaDon cthd = taoChiTietHoaDon();
							if (!hoaDon.themMotChiTietHoaDon(cthd)) {
								Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(null, "Bị trùng mã thuốc", "Cảnh báo",
										JOptionPane.WARNING_MESSAGE);
							} else {
								capNhatDuLieuTrenBang();
								txtTongTienThanhToan.setText(fmt.format(hoaDon.tinhTongThanhTien()));
								txtMienGiam.setText(fmt.format(tinhMienGiam()));
							}
						}
						btnThem.setEnabled(false);
					}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        int row = tableChiTietHoaDon.getSelectedRow();
				if (row != -1) {
					Toolkit.getDefaultToolkit().beep();
					int confirm = JOptionPane.showConfirmDialog(null,
							"Bạn có chắc chắn muốn cập nhật số lượng bán không?", "Chú ý", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						String maThuoc = cbMaThuoc.getSelectedItem().toString().split("-")[0].trim();
						int soLuongBanMoi = Integer.parseInt(spinSoLuongBan.getValue().toString().trim());
						if (hoaDon.capNhatSoLuongBan(maThuoc, soLuongBanMoi)) {
							tableChiTietHoaDon.clearSelection();
							capNhatDuLieuTrenBang();
							xoaTrang();
						} else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "Không thành công");
						}
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn cần chọn mục cần sửa số lượng bán", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
				}
			}//GEN-LAST:event_btnCapNhatActionPerformed
    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
    	boolean themHoaDon = false;
		int count_row = 0;

		txtTongTienThanhToan.setText(fmt.format(hoaDon.tinhTongThanhTien()));
		txtMienGiam.setText(fmt.format(tinhMienGiam()));
		txtTongTienConLai.setText(fmt.format(tinhTongTienConLai()));
		

		if (validDataSave()) {
			HoaDon_DAO hoadon_dao = new HoaDon_DAO();
			if (hoadon_dao.themHoaDon(taoHoaDon(emp), taoKhachHang())) {
				themHoaDon = true;
			} else {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Lỗi khi thêm thông tin hóa đơn vào CSDL",
						"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
			}

			ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();
			for (ChiTietHoaDon cthd : hoaDon.getDanhSachChiTietHoaDon()) {
				Thuoc thuoc = danhSachThuoc.get(danhSachThuoc.indexOf(cthd.getThuoc()));
				int soLuongTon = thuoc.getSoLuongTon() - cthd.getSoLuong();
				if (cthd_dao.themChiTietHoaDon(taoHoaDon(emp), cthd)) {
					if (thuoc_dao.capNhatSoLuongTonCuaThuoc(soLuongTon, cthd.getThuoc().getMaThuoc().trim())) {
						count_row += 1;
					} else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Lỗi cập nhật số lượng tồn của thuốc",
								"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Lỗi khi thêm danh sách sản phẩm vào CSDL",
							"Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
				}
			}

			if (themHoaDon && count_row == hoaDon.getDanhSachChiTietHoaDon().size()) {
				Toolkit.getDefaultToolkit().beep();
				if (JOptionPane.showConfirmDialog(null,
						"Lưu thông tin hóa đơn thành công. Bạn có muốn in hóa đơn hay không?",
						"Xác nhận in hóa đơn", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				}
				btnLuu.setEnabled(false);
			} else {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Lưu hóa đơn không thành công", "Lỗi thêm hóa đơn vào CSDL",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}//GEN-LAST:event_btnLuuActionPerformed
   
    private void tableChiTietHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableChiTietHoaDonMouseClicked
       		int row = tableChiTietHoaDon.getSelectedRow();
       		fillForm(row);
    }//GEN-LAST:event_tableChiTietHoaDonMouseClicked

    private void txtTongTienConLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienConLaiActionPerformed
       String soTienKhachDua = txtTongTienConLai.getText().trim();
	float float_SoTienKhachDua = Float.parseFloat(loaiBoDinhDangTien(soTienKhachDua));
	txtTongTienConLai.setText(fmt.format(float_SoTienKhachDua));
    }//GEN-LAST:event_txtTongTienConLaiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ALL;
    private javax.swing.JButton btnBatDauHoaDonMoi;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnGenerateMaKH;
    private javax.swing.JButton btnGenerateMaThuoc;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbMaKhachHang;
    private javax.swing.JComboBox<String> cbMaThuoc;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.JButton jButton12;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JPanel jPanel24;
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
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMaKhachHang;
    private javax.swing.JLabel lblMaThuoc;
    private javax.swing.JRadioButton radCo;
    private javax.swing.JRadioButton radKhong;
    private javax.swing.JSpinner spinSoLuongBan;
    private javax.swing.JTable tableChiTietHoaDon;
    private javax.swing.JTextField textAreaDiaChi;
    private javax.swing.JTextField txtDonGiaBan;
    private javax.swing.JTextField txtDonViThuoc;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLoaiThuoc;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMienGiam;
    private javax.swing.JTextField txtNgayHienTai;
    private javax.swing.JTextField txtNgayLapHoaDon;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTenNhaCungCap;
    private javax.swing.JTextField txtTenThuoc;
    private javax.swing.JTextField txtTongTienConLai;
    private javax.swing.JTextField txtTongTienThanhToan;
    private javax.swing.JTextField txtXuatXu;
    // End of variables declaration//GEN-END:variables
}
