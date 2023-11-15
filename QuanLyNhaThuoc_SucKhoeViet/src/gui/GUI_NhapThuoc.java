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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietHoaDon_DAO;
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
	private Thuoc_DAO thuoc_DAO = new Thuoc_DAO();
	private NhaCungCap_DAO nhaCungCap_DAO = new NhaCungCap_DAO();
	private PhieuNhapThuoc_DAO phieuNhapThuoc_DAO = new PhieuNhapThuoc_DAO();
	private ArrayList<PhieuNhapThuoc> phieuNhapThuocs  = new ArrayList<PhieuNhapThuoc>();
	private static float tongTien;
	
	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
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

	private String phatSinhMaThuoc() {
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
	    } else {
	        maThuoc += "0000000001";
	    }

	    return maThuoc;
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
		ArrayList<NhaCungCap> danhSachNhaCungCap = nhacungcap_dao.layTatCaNhaCungCap1();
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
		}
	}

	private void generateNhaCCByMaNCC(String maNhaCungCap) {
		NhaCungCap_DAO nhacungcap_dao = new NhaCungCap_DAO();
		ArrayList<NhaCungCap> ds = nhacungcap_dao.layTatCaNhaCungCap1();
		NhaCungCap nhaCungCap = new NhaCungCap(maNhaCungCap);
		if (ds.contains(nhaCungCap)) {
			nhaCungCap = ds.get(ds.indexOf(nhaCungCap));
			txtTenNhaCungCap.setText(nhaCungCap.getTenNCC().trim());
			txtEmail.setText(nhaCungCap.getEmail().trim());
			txtSoDienThoai.setText(nhaCungCap.getSoDienThoai().trim());
			textAreaDiaChi.setText(nhaCungCap.getDiaChi().trim());
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
	
	private void kiemtraDuLieu() {
		String tenThuoc = txtTenNhaCungCap.getText();
		
	}

    /**
     * Creates new form GUI_NhapThuoc
     */
    public GUI_NhapThuoc(NhanVien nv) {
    	this.emp = nv;
    	SpinnerNumberModel modelSoLuongNhap = new SpinnerNumberModel(1, 1, 1, 0);
		spinSoLuongNhap = new JSpinner(modelSoLuongNhap);
        initComponents();
        cbMaThuoc.setSelectedItem(phatSinhMaThuoc());;
        txtMaThuoc.setText(phatSinhMaThuoc());
    }
    
    private void Huy() {
    	if(!btnGenerateMaThuoc.isEnabled()) {
    		cbLoaiThuoc.addItem("Thuốc kê đơn");
    		cbLoaiThuoc.addItem("Thuốc không kê đơn");
    		cbLoaiThuoc.addItem("Thực phẩm chức năng");
    		cbLoaiThuoc.addItem("Thực phẩm chăm sóc sức khỏe");
    	}
    	txtTenThuoc.setText("");
		ngayHetHan.setDate(null);
		ngaySanXuat.setDate(null);
		txtDonGiaMua.setText("");
		txtDonViThuoc.setText("");
		txtXuatXu.setText("");
		spinSoLuongNhap.setValue(1);
		txtTenNhaCungCap.setText("");
		txtEmail.setText("");
		txtSoDienThoai.setText("");
		textAreaDiaChi.setText("");
		btnGenerateMaNCC.setEnabled(true);
		btnGenerateMaThuoc.setEnabled(true);
		cbMaThuoc.setEditable(true);
		cbMaThuoc.setSelectedItem("");
		cbMaNhaCungCap.setEditable(true);
		cbMaNhaCungCap.setSelectedItem("");
		cbLoaiThuoc.setSelectedIndex(1);
    }
    
    private boolean kiemTraDuLieu() {
		String donGiaMua = txtDonGiaMua.getText();
		String donViThuoc = txtDonViThuoc.getText();
		String xuatXu = txtXuatXu.getText();
		int spin = (int) spinSoLuongNhap.getValue();
		Object selectCBMa = cbMaNhaCungCap.getSelectedItem();
		if(ngayHetHan.getDate() == null) {
			java.util.Date utitngayHH = ngayHetHan.getDate();
			java.sql.Date ngayHH = new java.sql.Date(utitngayHH.getTime());
			JOptionPane.showMessageDialog(this, "Phải chọn ngày hết hạn");
			return false;
		}
		if(ngaySanXuat.getDate() == null) {
			java.util.Date utitngaySX = ngaySanXuat.getDate();
			java.sql.Date ngaySX = new java.sql.Date(utitngaySX.getTime());
			JOptionPane.showMessageDialog(this, "Phải chọn sản xuất");
			return false;
		}
		if(ngaySanXuat.getDate().after(ngayHetHan.getDate())) {
			JOptionPane.showMessageDialog(this, "Ngày hết hạn phải sau ngày hiện tại");
			return false;
		}
		if(!(donGiaMua.length() > 0 && donGiaMua.matches("^\\d+$") && Float.valueOf(donGiaMua) > 0)) {
			JOptionPane.showMessageDialog(this, "Đơn giá mua phải lớn hơn 0");
			return false;
		}
		if(selectCBMa == null) {
			JOptionPane.showMessageDialog(this, "Phải chọn nhà cung cấp");
			return false;
		}
		return true;
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
        jPanel40 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtMaThuoc = new javax.swing.JTextField(phatSinhMaThuoc());
        jPanel13 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTenThuoc = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        ngaySanXuat = new com.toedter.calendar.JDateChooser();
        jPanel15 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        ngayHetHan = new com.toedter.calendar.JDateChooser();
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
        jLabel7 = new javax.swing.JLabel();
        cbLoaiThuoc = new javax.swing.JComboBox<>();
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
        btnLuu = new javax.swing.JButton();
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
        jLabel2.setText("NHẬP THUỐC");
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
        txtMaPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPhieuNhapActionPerformed(evt);
            }
        });
        jPanel12.add(txtMaPhieuNhap);

        jPanel10.add(jPanel12);

        jPanel40.setLayout(new javax.swing.BoxLayout(jPanel40, javax.swing.BoxLayout.LINE_AXIS));

        jLabel16.setText("   Mã thuốc :");
        jLabel16.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel40.add(jLabel16);

        txtMaThuoc.setEditable(false);
        jPanel40.add(txtMaThuoc);

        jPanel10.add(jPanel40);

        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setText("   Tên thuốc:");
        jLabel5.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel13.add(jLabel5);
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

        jLabel12.setText("   Ngày hết hạn:");
        jLabel12.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel15.add(jLabel12);
        jPanel15.add(ngayHetHan);

        jPanel10.add(jPanel15);

        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jLabel8.setText("   Đơn giá mua:");
        jLabel8.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel16.add(jLabel8);
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

        jLabel7.setText(" Loại thuốc:");
        jLabel7.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel20.add(jLabel7);

        cbLoaiThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thuốc kê đơn", "Thuốc không kê đơn", "Thực phẩm chức năng" }));
        jPanel20.add(cbLoaiThuoc);

        jPanel11.add(jPanel20);

        jPanel21.setLayout(new javax.swing.BoxLayout(jPanel21, javax.swing.BoxLayout.LINE_AXIS));

        jLabel13.setText("Đơn vị thuốc:");
        jLabel13.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel21.add(jLabel13);
        jPanel21.add(txtDonViThuoc);

        jPanel11.add(jPanel21);

        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jLabel14.setText("Xuất xứ:");
        jLabel14.setPreferredSize(new java.awt.Dimension(85, 16));
        jPanel22.add(jLabel14);
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
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        jPanel25.add(btnXoaTrang);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel25.add(btnXoa);

        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });
        jPanel25.add(btnLuu);

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
        btnGenerateMaNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateMaNCCActionPerformed(evt);
            }
        });
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
    	if(kiemTraDuLieu()) {
        	boolean themThuoc = false;
    		boolean themPhieuNhapThuoc = false;
    		
    		String maPhieu = txtMaPhieuNhap.getText();
    		String maThuoc = txtMaThuoc.getText();
    		String maNCC = cbMaNhaCungCap.getSelectedItem().toString();
    		String tenThuoc = txtTenThuoc.getText();
    		Date ngayNhap1 = new Date();
    		java.sql.Date ngayNhap = new java.sql.Date(ngayNhap1.getTime());
    		java.util.Date utitngaySX = ngaySanXuat.getDate();
    		java.util.Date utitngayHH = ngayHetHan.getDate();
    		java.sql.Date ngaySX = new java.sql.Date(utitngaySX.getTime());
    		java.sql.Date ngayHH = new java.sql.Date(utitngayHH.getTime());
    		float donGia = Float.valueOf(txtDonGiaMua.getText());
    		int soLuong = (int) spinSoLuongNhap.getValue();
    		String loaiThuoc = cbLoaiThuoc.getSelectedItem()+"";
    		String donVi= txtDonViThuoc.getText();
    		String xuatXu = txtXuatXu.getText();
    		NhaCungCap ncc = nhaCungCap_DAO.layNhaCungCap(maNCC);
    		Thuoc thuoc = new Thuoc(maThuoc, tenThuoc, loaiThuoc, loaiThuoc, xuatXu, soLuong, ncc);
    		PhieuNhapThuoc phieuNhapThuoc = new PhieuNhapThuoc(maPhieu, emp, thuoc, ngayNhap, ngaySX, ngayHH, donGia, soLuong);
    		phieuNhapThuocs.add(phieuNhapThuoc);
    		tablePhieuNhapThuoc.removeAll();
    		tablePhieuNhapThuoc.setRowSelectionAllowed(false);
    	    modelPhieuNhapThuoc.setRowCount(0);
    	    txtMaThuoc.setText(phatSinhMaThuoc());
    	    tongTien += donGia*soLuong;
    	    txtTongTienNhapThuoc.setText(tongTien+"");
    	    int stt = 1;
    	    for(PhieuNhapThuoc pn : phieuNhapThuocs) {
    	    	modelPhieuNhapThuoc.addRow(new Object[] {stt++,pn.getMaPhieu(), pn.getThuoc().getMaThuoc(), pn.getThuoc().getTenThuoc(), pn.getNgayNhap(), pn.getNgaySanXuat(), pn.getNgayHetHan(), pn.getThuoc().getLoaiThuoc(), pn.getThuoc().getDonViThuoc(), pn.getDonGiaMua(),pn.getThuoc().getXuatXu(),pn.getSoLuongNhap()});
    	    }
	    	txtMaThuoc.setText(phatSinhMaThuoc());
    	}
    	else {
    		JOptionPane.showMessageDialog(this, "Lỗi nhập dữ liệu");
    	}
    }//GEN-LAST:event_btnThemActionPerformed

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
                    txtMaThuoc.setText(maThuoc);
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

    private void txtMaPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPhieuNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaPhieuNhapActionPerformed

    private void btnGenerateMaNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateMaNCCActionPerformed
        // TODO add your handling code here:
    	if (use_event_cbMaNhaCungCap) {
			String tuHienTai = cbMaNhaCungCap.getSelectedItem().toString().trim();
			int row_count = cbMaNhaCungCap.getItemCount();
			if (!tuHienTai.equalsIgnoreCase("")) {
				String[] tachTuHienTai = tuHienTai.split("-");
				String maNhaCungCap = tachTuHienTai[0].trim();
				if (row_count > 0) {
					if (row_count == 1) {
						generateNhaCCByMaNCC(maNhaCungCap);
					}
					cbMaNhaCungCap.setSelectedItem(maNhaCungCap);
					cbMaNhaCungCap.setEditable(false);
					lblMaNhaCungCap.setText("Mã nhà cung cấp:");
				} else {
					phatSinhMaNhaCungCap();
					cbMaNhaCungCap.setEditable(false);
					lblMaNhaCungCap.setText("Mã nhà cung cấp:");
					txtTenNhaCungCap.setEditable(true);
					txtEmail.setEditable(true);
					txtSoDienThoai.setEditable(true);
					textAreaDiaChi.setEditable(true);
				}
			} else {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(cbMaNhaCungCap, "Bạn phải nhập mã nhà cung cấp", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			}
			btnGenerateMaNCC.setEnabled(false);
			btnGenerateMaNCC.setBorder(BorderFactory.createEmptyBorder());
			cbMaNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		} else {
			cbMaNhaCungCap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			cbMaNhaCungCap.requestFocus();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(cbMaNhaCungCap,
					"Vui lòng nhấn Enter trong khung nhập liệu \"Tìm mã nhà cung cấp\" trước khi nhấn nút này",
					"Cảnh báo", JOptionPane.WARNING_MESSAGE);
		}
    }//GEN-LAST:event_btnGenerateMaNCCActionPerformed
    private boolean kiemTraTrungMa(String maPhieu) {
    	ArrayList<Thuoc> list = new ArrayList<Thuoc>();
    	list = thuoc_DAO.layTatCaThuoc();
    	for(PhieuNhapThuoc pn : phieuNhapThuocs) {
    		for(Thuoc thuoc : list) {
    			if(thuoc.getMaThuoc().equals(maPhieu))
    				return true;
    		}
    	}
    	return false;
    }
    
    private int soLuongCapNhat(String maPhieu) {
    	ArrayList<Thuoc> list = new ArrayList<Thuoc>();
    	list = thuoc_DAO.layTatCaThuoc();
    	for(PhieuNhapThuoc pn : phieuNhapThuocs) {
    		for(Thuoc thuoc : list) {
    			if(thuoc.getMaThuoc().equals(maPhieu))
    				return thuoc.getSoLuongTon() + pn.getSoLuongNhap();
    		}
    	}
    	return 0;
    }
    
    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
    	boolean capNhat = false;
    	ArrayList<Thuoc> list = new ArrayList<Thuoc>();
    	list = thuoc_DAO.layTatCaThuoc();
    	if(phieuNhapThuocs.size() > 0) {
        	for(PhieuNhapThuoc pn : phieuNhapThuocs) {
        		if(kiemTraTrungMa(pn.getThuoc().getMaThuoc())) {
        			thuoc_DAO.capNhatSoLuongTonCuaThuoc(soLuongCapNhat(pn.getThuoc().getMaThuoc()), pn.getThuoc().getMaThuoc());
        		}
        		
        		thuoc_DAO.themThuoc(pn.getThuoc());
        	
    			phieuNhapThuoc_DAO.themPhieuNhapThuoc(pn, emp);
        	}
        	txtMaThuoc.setText(phatSinhMaThuoc());
        	txtMaPhieuNhap.setText(phatSinhMaPhieuNhapThuoc());
        	JOptionPane.showMessageDialog(this, "Lưu thành công");
    	}
    	else {
    		JOptionPane.showMessageDialog(this, "Chưa nhập danh sách thuốc");
    	}
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
    	Huy();
    }//GEN-LAST:event_btnXoaTrangActionPerformed

	public int getIndex(String ma) {
		int index = -1;
		for(int i = 0; i < phieuNhapThuocs.size(); i++ ) {
			if(phieuNhapThuocs.get(i).getMaPhieu().equals(ma)) 
				index = i;
		}
		return index;
	}
	
    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
    	int row = tablePhieuNhapThuoc.getSelectedRow();
		if(row != -1) {
			int tb = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa", "Delete", JOptionPane.YES_NO_OPTION);
			if(tb == JOptionPane.YES_OPTION) {
				tongTien -= (phieuNhapThuocs.get(getIndex(tablePhieuNhapThuoc.getValueAt(row, 1)+"")).getDonGiaMua() *  phieuNhapThuocs.get(getIndex(tablePhieuNhapThuoc.getValueAt(row, 1)+"")).getSoLuongNhap());
				txtTongTienNhapThuoc.setText(tongTien+"");
				phieuNhapThuocs.remove(getIndex(tablePhieuNhapThuoc.getValueAt(row, 1)+""));
				modelPhieuNhapThuoc.removeRow(row);
			}
		}
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerateMaNCC;
    private javax.swing.JButton btnGenerateMaThuoc;
    private javax.swing.JButton btnLuu;
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JPanel jPanel40;
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
    private javax.swing.JTextField txtMaThuoc;
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
