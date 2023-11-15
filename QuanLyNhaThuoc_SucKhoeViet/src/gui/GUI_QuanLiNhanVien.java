
package gui;

import java.awt.Color;
import java.awt.JobAttributes;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.DataFormatException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePickerImpl;

import com.formdev.flatlaf.json.ParseException;

import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entities.NhanVien;
import entities.TaiKhoan;
import utilities.RegularExpression;

/**
 *
 * @author DELL
 */
public class GUI_QuanLiNhanVien extends javax.swing.JPanel implements WindowListener, MouseListener {
	private NhanVien emp;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private File file;
	private NhanVien_DAO nv_dao = new NhanVien_DAO();
//private JDatePickerImpl datePickerNgaySinh;
//private JDatePickerImpl datePickerNgayVaoLam;
private DefaultTableModel model;
	
	
	private byte[] ConvertFile(String fileName) {
		FileInputStream fileInputStream = null;
		File file = new File(fileName);
		byte[] bFile = new byte[(int) file.length()];
		try {
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (Exception e) {
			bFile = null;
		}
		return bFile;
	}
	private String layNgayHienTai() {
		Date ngayHienTai = new Date();
		return sdf.format(ngayHienTai);
	}

	private TaiKhoan taoTaiKhoan() {
		String matKhau = txtMK.getText().trim();
		byte[] hinhAnh = ConvertFile(file.getAbsolutePath());
		String trangThai = cbTTTK.getItemAt(cbTTTK.getSelectedIndex());
		return new TaiKhoan(matKhau, hinhAnh, trangThai);
	}
	private NhanVien taoNhanVien() {
            phatSinhMaNhanVien();
		String maNV = txtMa.getText().trim();
		String hoTen = txtTen.getText().trim();
		Date ngaySinh = model_ngaysinh.getDate();
		Date ngayVaoLam = txtNVL.getDate();
		String chucVu = cbCV.getItemAt(cbCV.getSelectedIndex());
		String gioiTinh = "Nam";
		if (radNu.isSelected())
			gioiTinh = "Nữ";
		String tinhTrang = cbTT.getItemAt(cbTT.getSelectedIndex());
		String soCMND = txtCMND.getText().trim();
		String email = txtEmail.getText().trim();
		String soDienThoai = txtDT.getText().trim();
		String diaChi = txtDC.getText().trim();
		TaiKhoan tk = taoTaiKhoan();
		return new NhanVien(hoTen, ngaySinh, gioiTinh, soCMND, email, soDienThoai, diaChi, maNV, ngayVaoLam, chucVu, tinhTrang, tk);
	}
        
        private NhanVien suaNhanVien() {
            
		String maNV = txtMa.getText().trim();
		String hoTen = txtTen.getText().trim();
		Date ngaySinh = model_ngaysinh.getDate();
		Date ngayVaoLam = txtNVL.getDate();
		String chucVu = cbCV.getItemAt(cbCV.getSelectedIndex());
		String gioiTinh = "Nam";
		if (radNu.isSelected())
			gioiTinh = "Nữ";
		String tinhTrang = cbTT.getItemAt(cbTT.getSelectedIndex());
		String soCMND = txtCMND.getText().trim();
		String email = txtEmail.getText().trim();
		String soDienThoai = txtDT.getText().trim();
		String diaChi = txtDC.getText().trim();
		TaiKhoan tk = taoTaiKhoan();
		return new NhanVien(hoTen, ngaySinh, gioiTinh, soCMND, email, soDienThoai, diaChi, maNV, ngayVaoLam, chucVu, tinhTrang, tk);
	}
	public void loadDB(ArrayList<NhanVien> ds){
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.getDataVector().removeAllElements();
		for (NhanVien nv : ds) {
			model.addRow(new Object[] { nv.getMaNV(), nv.getHoTenNhanVien(),nv.getSoCMND(),nv.getGioiTinh(),
					sdf.format(nv.getNgaySinh()), nv.getEmail(), nv.getSoDienThoai(),
					sdf.format(nv.getNgayVaoLam()), nv.getChucVu(),
					nv.getTinhTrang(),nv.getDiaChi() });
		}
	}
	private void fillForm() {
		int r = table.getSelectedRow();
		if(r<0) {
			return;
		}
		txtMa.setText(table.getValueAt(r, 0).toString());
		txtTen.setText(table.getValueAt(r, 1).toString());
		txtCMND.setText(table.getValueAt(r, 2).toString());
		if(table.getValueAt(r, 3).toString().equals("Nam")) {
			
			radNam.setSelected(true);
		}else {
			radNu.setSelected(true);
		}
		
		String cv = new String(table.getValueAt(r, 8).toString());
		cbCV.setSelectedItem(cv);
		String tt = new String(table.getValueAt(r, 9).toString());
		cbTT.setSelectedItem(tt);
		
		String dateNS = table.getValueAt(r, 4).toString();
		String dataNVL = table.getValueAt(r, 7).toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date1, date2;
		try {
			date1 = dateFormat.parse(dateNS);
			date2 = dateFormat.parse(dataNVL);
			model_ngaysinh.setDate(date1);
			txtNVL.setDate(date2);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		txtEmail.setText(table.getValueAt(r, 5).toString());
		txtDT.setText(table.getValueAt(r, 6).toString());
		
//		txtNVL.setDate((Date)table.getValueAt(r, 7));
//		String dateNVL = table.getValueAt(r, 7).toString();
//		Date date;
//		try {
//			date = dataFormat.p
//		}
		
		txtDC.setText(table.getValueAt(r, 10).toString());
		
		 
		
	}
	private boolean validData() {
		String hoTen = txtTen.getText().trim();
		if (hoTen.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtTen, "Bạn phải nhập họ và tên nhân viên", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtTen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtTen.requestFocus();
			return false;
		} else {
			txtTen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String ngaySinh = model_ngaysinh.getDateFormatString();
		if (ngaySinh.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(model_ngaysinh, "Bạn phải chọn ngày sinh", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			model_ngaysinh.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			model_ngaysinh.requestFocus();
			return false;
		}
		else
		{
			model_ngaysinh.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String ngayVaoLam = txtNVL.getDateFormatString();
		if (ngayVaoLam.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtNVL, "Bạn phải chọn ngày vào làm", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtNVL.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtNVL.requestFocus();
			return false;
		}
		else
		{
			txtNVL.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}
		
		if (cbCV.getSelectedIndex() == 0) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(cbCV, "Bạn phải chọn chức vụ của nhân viên", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			cbCV.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			cbCV.requestFocus();
			return false;
		} else {
			cbCV.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String soCMND = txtCMND.getText().trim();
		if (soCMND.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtCMND, "Bạn phải nhập số chứng minh nhân dân", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtCMND.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtCMND.requestFocus();
			return false;
		} else {
			String regex = "^\\d{9,12}$";
			if (!RegularExpression.checkMatch(soCMND, regex)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtCMND, "Số CMND phải gồm 9 đến 12 ký số", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				txtCMND.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtCMND.requestFocus();
				txtCMND.selectAll();
				return false;
			} else {
				txtCMND.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		}
		
		String email = txtEmail.getText().trim();
		if (email.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtEmail, "Bạn phải nhập email", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtEmail.requestFocus();
			return false;
		}
		else
		{
			txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}
		
		String soDienThoai = txtDT.getText().trim();
		if (soDienThoai.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtDT, "Bạn phải nhập số điện thoại", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtDT.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtDT.requestFocus();
			return false;
		}
		else {
			String regex = "^\\d{10,11}$";
			if (!RegularExpression.checkMatch(soDienThoai, regex)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtDT, "Số điện thoại phải gồm 10 đến 11 ký số", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				txtDT.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtDT.requestFocus();
				txtDT.selectAll();
				return false;
			} else {
				txtDT.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		}
		
		String diaChi = txtDC.getText().trim();
		if (diaChi.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtDC, "Bạn phải nhập địa chỉ", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtDC.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtDC.requestFocus();
			return false;
		}
		else
		{
			txtDC.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}
		
		String matKhau = txtMK.getText().trim();
		if (matKhau.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtMK, "Bạn phải nhập mật khẩu", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtMK.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtMK.requestFocus();
			return false;
		}
		else
		{
			if (matKhau.length() < 6) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtMK, "Mật khẩu phải nhiều hơn 6 ký tự", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				txtMK.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtMK.requestFocus();
				txtMK.selectAll();
				return false;
			} else {
				txtMK.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		}
		
//		if (file == null) {
//			Toolkit.getDefaultToolkit().beep();
//			JOptionPane.showMessageDialog(btnAnh, "Bạn phải chọn một ảnh", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//			btnAnh.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
//			btnAnh.requestFocus();
//			return false;
//		}
//		else
//		{
//			btnAnh.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		}
		
		return true;
	}
	private void phatSinhMaNhanVien() {
		NhanVien_DAO nv_dao = new NhanVien_DAO();
		String maNV_lastest = nv_dao.layMaNVCuoiCung().trim();
		String maNV = "NV" + String.valueOf(txtNVL.getY());
		if (maNV_lastest != null) {
			String stt_string_lastest = maNV_lastest.substring(6, maNV_lastest.length());
			int stt_int_lastest = Integer.parseInt(stt_string_lastest);
			String stt_current = String.valueOf(stt_int_lastest + 1);
			for (int i = 0; i < (4 - stt_current.length()); i++) {
				maNV += "00";
			}
			maNV += stt_current;
			txtMa.setText(maNV);
		} else {
			maNV += "0001";
			txtMa.setText(maNV);
		}
	}
	public void xoaRong() {
		txtMa.setText("");
		txtTen.setText("");
		//datePickerNgaySinh.getJFormattedTextField().setText("");
		model_ngaysinh.setDate(null);
		cbCV.setSelectedIndex(0);
		radNam.setSelected(true);
		cbTT.setSelectedItem(0);
		txtCMND.setText("");
		txtEmail.setText("");
		txtDT.setText("");
		txtDC.setText("");
		cbTTTK.setSelectedItem("Tài khoản đang mở");
		txtMK.setText("");
		//datePickerNgayVaoLam.getJFormattedTextField().setText("");
		txtNVL.setDate(null);
		file = null;
		lblAnh.setIcon(new ImageIcon("src\\icon\\Avatar.png"));
		txtTen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		model_ngaysinh.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtNVL.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		cbCV.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtCMND.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtDT.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtDC.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtMK.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnAnh.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtTen.requestFocus();
		btnThem.setEnabled(true);
	}



    public GUI_QuanLiNhanVien() {
  
        initComponents();
    }

    private void initComponents() {

        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lblNHT = new javax.swing.JLabel();
        txtNHT = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        lblMa = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        lblTen = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        lblNS = new javax.swing.JLabel();
        model_ngaysinh = new com.toedter.calendar.JDateChooser();
        jPanel19 = new javax.swing.JPanel();
        lblCV = new javax.swing.JLabel();
        cbCV = new javax.swing.JComboBox<>();
        jPanel20 = new javax.swing.JPanel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        lblCMND = new javax.swing.JLabel();
        txtCMND = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        lblGT = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        radNam = new javax.swing.JRadioButton();
        jPanel26 = new javax.swing.JPanel();
        radNu = new javax.swing.JRadioButton();
        jPanel23 = new javax.swing.JPanel();
        lblNVL = new javax.swing.JLabel();
        txtNVL = new com.toedter.calendar.JDateChooser();
        jPanel24 = new javax.swing.JPanel();
        lblTT = new javax.swing.JLabel();
        cbTT = new javax.swing.JComboBox<>();
        jPanel25 = new javax.swing.JPanel();
        lblDT = new javax.swing.JLabel();
        txtDT = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        lblDC = new javax.swing.JLabel();
        txtDC = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        lblTTTK = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        lblMK = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        cbTTTK = new javax.swing.JComboBox<>();
        jPanel56 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        txtMK = new javax.swing.JTextField();
        jPanel57 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        cBoxHMK = new javax.swing.JCheckBox();
        jPanel50 = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
        btnCNTT = new javax.swing.JButton();
        jPanel52 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        btnAnh = new javax.swing.JButton();
        jPanel53 = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel54 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnXoaRong = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/NV.png"))); // NOI18N
        jPanel6.add(jLabel3);

        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("    Thông Tin Nhân Viên");
        jLabel1.setPreferredSize(new java.awt.Dimension(300, 32));
        jPanel5.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.GridBagLayout());

        lblNHT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNHT.setText("Ngày hiện tại:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(34, 36, 0, 0);
        jPanel7.add(lblNHT, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 74;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(31, 6, 26, 30);
        jPanel7.add(txtNHT, gridBagConstraints);

        jPanel2.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thêm Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 153, 204))); // NOI18N
        jPanel10.setPreferredSize(new java.awt.Dimension(500, 250));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.Y_AXIS));

        jPanel12.setPreferredSize(new java.awt.Dimension(590, 200));
        jPanel12.setLayout(new java.awt.GridLayout(1, 5, 20, 0));

        jPanel15.setLayout(new java.awt.GridLayout(5, 0, 20, 10));

        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        lblMa.setText("Mã Nhân Viên: ");
        jPanel16.add(lblMa);
        jPanel16.add(txtMa);

        jPanel15.add(jPanel16);

        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        lblTen.setText("Họ & Tên:");
        lblTen.setPreferredSize(new java.awt.Dimension(81, 16));
        jPanel17.add(lblTen);
        jPanel17.add(txtTen);

        jPanel15.add(jPanel17);

        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.LINE_AXIS));

        lblNS.setText("Ngày Sinh:");
        lblNS.setPreferredSize(new java.awt.Dimension(81, 16));
        jPanel18.add(lblNS);
        jPanel18.add(model_ngaysinh);

        jPanel15.add(jPanel18);

        jPanel19.setLayout(new javax.swing.BoxLayout(jPanel19, javax.swing.BoxLayout.LINE_AXIS));

        lblCV.setText("Chức Vụ:");
        lblCV.setPreferredSize(new java.awt.Dimension(81, 16));
        jPanel19.add(lblCV);

        cbCV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Nhân viên bán thuốc", "Nhân viên kho", "Quản lí nhà thuốc" }));
        jPanel19.add(cbCV);

        jPanel15.add(jPanel19);

        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.LINE_AXIS));

        lblEmail.setText("Email:");
        lblEmail.setPreferredSize(new java.awt.Dimension(81, 16));
        jPanel20.add(lblEmail);
        jPanel20.add(txtEmail);

        jPanel15.add(jPanel20);

        jPanel12.add(jPanel15);

        jPanel14.setLayout(new java.awt.GridLayout(5, 1, 0, 10));

        jPanel21.setLayout(new javax.swing.BoxLayout(jPanel21, javax.swing.BoxLayout.LINE_AXIS));

        lblCMND.setText("Số CMND:");
        lblCMND.setPreferredSize(new java.awt.Dimension(81, 16));
        jPanel21.add(lblCMND);
        jPanel21.add(txtCMND);

        jPanel14.add(jPanel21);

        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jPanel28.setPreferredSize(new java.awt.Dimension(100, 44));
        jPanel28.setLayout(new javax.swing.BoxLayout(jPanel28, javax.swing.BoxLayout.LINE_AXIS));

        lblGT.setText("Giới Tính:");
        lblGT.setPreferredSize(new java.awt.Dimension(81, 16));
        jPanel28.add(lblGT);

        jPanel22.add(jPanel28);

        radNam.setText("Nam");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(radNam)
                .addGap(0, 80, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radNam)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel22.add(jPanel27);

        radNu.setText("Nữ");
        
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radNam);
        buttonGroup.add(radNu);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(radNu)
                .addGap(0, 83, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radNu)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel22.add(jPanel26);

        jPanel14.add(jPanel22);

        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        lblNVL.setText("Ngày Vào Làm:");
        lblNVL.setPreferredSize(new java.awt.Dimension(81, 16));
        jPanel23.add(lblNVL);
        jPanel23.add(txtNVL);

        jPanel14.add(jPanel23);

        jPanel24.setLayout(new javax.swing.BoxLayout(jPanel24, javax.swing.BoxLayout.LINE_AXIS));

        lblTT.setText("Tình Trạng:");
        lblTT.setPreferredSize(new java.awt.Dimension(81, 16));
        jPanel24.add(lblTT);

        cbTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Đang làm việc", "Đã nghỉ việc" }));
        jPanel24.add(cbTT);

        jPanel14.add(jPanel24);

        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.LINE_AXIS));

        lblDT.setText("Số Điện Thoại:");
        lblDT.setPreferredSize(new java.awt.Dimension(81, 16));
        jPanel25.add(lblDT);
        jPanel25.add(txtDT);

        jPanel14.add(jPanel25);

        jPanel12.add(jPanel14);

        jPanel11.add(jPanel12);

        jPanel13.setPreferredSize(new java.awt.Dimension(590, 50));
        jPanel13.setLayout(new java.awt.GridLayout(3, 1));

        jPanel29.setLayout(null);
        jPanel13.add(jPanel29);

        jPanel30.setLayout(new javax.swing.BoxLayout(jPanel30, javax.swing.BoxLayout.LINE_AXIS));

        lblDC.setText("Địa Chỉ:");
        lblDC.setMinimumSize(new java.awt.Dimension(81, 16));
        lblDC.setPreferredSize(new java.awt.Dimension(81, 16));
        jPanel30.add(lblDC);
        jPanel30.add(txtDC);

        jPanel13.add(jPanel30);

        jPanel11.add(jPanel13);

        jPanel10.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel10);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đăng Nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 153, 204))); // NOI18N
        jPanel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(300, 319));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel33.setPreferredSize(new java.awt.Dimension(357, 200));
        jPanel33.setLayout(new java.awt.BorderLayout());

        jPanel36.setPreferredSize(new java.awt.Dimension(357, 50));
        jPanel36.setLayout(new javax.swing.BoxLayout(jPanel36, javax.swing.BoxLayout.LINE_AXIS));

        jPanel38.setPreferredSize(new java.awt.Dimension(90, 200));
        jPanel38.setLayout(new java.awt.GridLayout(7, 0));

        lblTTTK.setText("Trạng thái tài khoản:");
        jPanel39.add(lblTTTK);
        
        jPanel38.add(jPanel39);

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 186, Short.MAX_VALUE)
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel40);

        lblMK.setText("Mật khẩu:");
        lblMK.setPreferredSize(new java.awt.Dimension(108, 16));
        jPanel41.add(lblMK);

        jPanel38.add(jPanel41);

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 186, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel42);

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 186, Short.MAX_VALUE)
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel43);

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 186, Short.MAX_VALUE)
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel44);

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 186, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel45);

        jPanel36.add(jPanel38);

        jPanel37.setPreferredSize(new java.awt.Dimension(240, 200));
        jPanel37.setLayout(new java.awt.GridLayout(7, 1));

        jPanel46.setLayout(new javax.swing.BoxLayout(jPanel46, javax.swing.BoxLayout.LINE_AXIS));

        cbTTTK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Tài khoản đang mở", "Tài khoản đang khóa" }));
        jPanel46.add(cbTTTK);

        jPanel56.setPreferredSize(new java.awt.Dimension(20, 28));

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel46.add(jPanel56);

        jPanel37.add(jPanel46);

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel37.add(jPanel47);

        jPanel48.setLayout(new javax.swing.BoxLayout(jPanel48, javax.swing.BoxLayout.LINE_AXIS));
        jPanel48.add(txtMK);

        jPanel57.setPreferredSize(new java.awt.Dimension(180, 28));

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel48.add(jPanel57);

        jPanel37.add(jPanel48);

        jPanel49.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cBoxHMK.setText("Hiện mật khẩu");
        cBoxHMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cBoxHMKActionPerformed(evt);
            }
        });
        jPanel49.add(cBoxHMK);

        jPanel37.add(jPanel49);

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel37.add(jPanel50);

        jPanel51.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 0, 0));

        btnCNTT.setBackground(new java.awt.Color(204, 255, 255));
        btnCNTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCNTT.setText("Cập nhật thông tin đăng nhập");
        jPanel51.add(btnCNTT);

        jPanel37.add(jPanel51);

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel37.add(jPanel52);

        jPanel36.add(jPanel37);

        jPanel33.add(jPanel36, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel33, java.awt.BorderLayout.PAGE_END);

        jPanel32.setPreferredSize(new java.awt.Dimension(357, 30));
        jPanel32.setLayout(new javax.swing.BoxLayout(jPanel32, javax.swing.BoxLayout.Y_AXIS));

        jPanel34.setPreferredSize(new java.awt.Dimension(357, 35));
        jPanel34.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        lblAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Avatar.png"))); // NOI18N
        lblAnh.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanel34.add(lblAnh);

        jPanel32.add(jPanel34);

        jPanel35.setPreferredSize(new java.awt.Dimension(357, 55));
        jPanel35.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        btnAnh.setBackground(new java.awt.Color(204, 255, 255));
        btnAnh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnh.setText("Thêm ảnh");
        jPanel35.add(btnAnh);

        jPanel32.add(jPanel35);

        jPanel9.add(jPanel32, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel9);

        add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel53.setPreferredSize(new java.awt.Dimension(915, 300));
        jPanel53.setLayout(new javax.swing.BoxLayout(jPanel53, javax.swing.BoxLayout.Y_AXIS));

        jPanel55.setLayout(new java.awt.BorderLayout());
        
        String[] head = {"Mã nhân viên", "Họ và Tên", "Số CMND", "Giới tính", "Ngày sinh", "Email", "Số điện thoại", "Ngày vào làm", "Chức vụ", "Tình trạng", "Địa chỉ"};
        model = new DefaultTableModel(head,0);
        table.setModel(model);
        table.addMouseListener(this);
        loadDB(nv_dao.layTatCaNhanVien());
        jScrollPane1.setViewportView(table);

        
        jPanel55.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel53.add(jPanel55);

        jPanel54.setPreferredSize(new java.awt.Dimension(915, 100));
        jPanel54.setLayout(new java.awt.GridLayout(1, 14));

        btnThem.setBackground(new java.awt.Color(0, 153, 204));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validData()) {
					NhanVien nv = taoNhanVien();
					NhanVien_DAO nv_dao = new NhanVien_DAO();
					TaiKhoan_DAO tk_dao = new TaiKhoan_DAO();
					if(nv_dao.themNhanVien(nv)) {
						model.addRow(new Object[] { nv.getMaNV(), nv.getHoTenNhanVien(),nv.getSoCMND(),nv.getGioiTinh(),
								sdf.format(nv.getNgaySinh()), nv.getEmail(), nv.getSoDienThoai(),
								sdf.format(nv.getNgayVaoLam()), nv.getChucVu(),
								nv.getTinhTrang(),nv.getDiaChi() });
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công");
                                                
						btnThem.setEnabled(false);
					}else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhân viên vào CSDL","Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});

        btnXoa.setBackground(new java.awt.Color(0, 153, 204));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Remove.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String listMaNV = "";
				String maNV = "";
				if(table.getSelectedRow()==0) {
					JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để xóa");
				}else {
					//lay ra danh sach xoa tren gui
					int[] listIndex = table.getSelectedRows();
					listMaNV += table.getValueAt(listIndex[0], 0);
					for (int i = 1; i < table.getSelectedRowCount();i++) {
						listMaNV += ","+table.getValueAt(listIndex[i], 0);
					}
					//Hien thi thong tin xoa
					if(JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa?","Cảnh báo",JOptionPane.YES_NO_OPTION)
							== JOptionPane.YES_OPTION) {
						try {
							while(table.getSelectedRowCount()>0) {
								maNV = (String) table.getValueAt(table.getSelectedRow(), 0);
								if(!nv_dao.xoaMotNhanVien(maNV)) {
									JOptionPane.showMessageDialog(null, "Xóa thất bại" );
									table.clearSelection();
								}else {
									model.removeRow(table.getSelectedRow());
								}
							}
						}catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());// TODO: handle exception
						}
					}
					
				}
				
				
			}
		});

        btnXoaRong.setBackground(new java.awt.Color(0, 153, 204));
        btnXoaRong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaRong.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaRong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/RemoveChar.png"))); // NOI18N
        btnXoaRong.setText("Xóa Rỗng");
        btnXoaRong.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xoaRong();
				
			}
		});
        

        btnSua.setBackground(new java.awt.Color(0, 153, 204));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Change.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row < 0) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để sửa");
				}else {
					if(validData()) {
						NhanVien nv = suaNhanVien();
						NhanVien_DAO nv_dao = new NhanVien_DAO();
						if(nv_dao.capNhatThongTinNhanVien(nv)) {
							JOptionPane.showMessageDialog(null, "Cập nhật thành công");
						}else {
							JOptionPane.showMessageDialog(null, "Không thể cập nhật");
							table.clearSelection();
						}
					}
				}
				
			}
		});
        

        btnLuu.setBackground(new java.awt.Color(0, 153, 204));
        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Save.png"))); // NOI18N
        btnLuu.setText("Lưu");

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnThem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoaRong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLuu)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa)
                    .addComponent(btnXoaRong)
                    .addComponent(btnSua)
                    .addComponent(btnLuu))
                .addGap(22, 22, 22))
        );

        jPanel54.add(jPanel58);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 632, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 79, Short.MAX_VALUE)
        );

        jPanel54.add(jPanel31);

        jPanel53.add(jPanel54);

        add(jPanel53, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>                        

    private void cBoxHMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cBoxHMKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cBoxHMKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnh;
    private javax.swing.JButton btnCNTT;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaRong;
    private javax.swing.JCheckBox cBoxHMK;
    private javax.swing.JComboBox<String> cbCV;
    private javax.swing.JComboBox<String> cbTT;
    private javax.swing.JComboBox<String> cbTTTK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblCMND;
    private javax.swing.JLabel lblCV;
    private javax.swing.JLabel lblDC;
    private javax.swing.JLabel lblDT;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGT;
    private javax.swing.JLabel lblMK;
    private javax.swing.JLabel lblMa;
    private javax.swing.JLabel lblNHT;
    private javax.swing.JLabel lblNS;
    private javax.swing.JLabel lblNVL;
    private javax.swing.JLabel lblTT;
    private javax.swing.JLabel lblTTTK;
    private javax.swing.JLabel lblTen;
    private com.toedter.calendar.JDateChooser model_ngaysinh;
    private javax.swing.JRadioButton radNam;
    private javax.swing.JRadioButton radNu;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtCMND;
    private javax.swing.JTextField txtDC;
    private javax.swing.JTextField txtDT;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMK;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtNHT;
    private com.toedter.calendar.JDateChooser txtNVL;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowClosing(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		fillForm();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
