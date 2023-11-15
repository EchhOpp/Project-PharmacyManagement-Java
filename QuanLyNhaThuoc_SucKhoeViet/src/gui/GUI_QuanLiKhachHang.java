/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entities.KhachHang;
import entities.NhanVien;
import entities.TaiKhoan;
import utilities.RegularExpression;

/**
 *
 * @author DELL
 */
public class GUI_QuanLiKhachHang extends javax.swing.JPanel implements WindowListener, MouseListener{

    private DefaultTableModel model;
    private KhachHang emp;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private File file;
	private KhachHang_DAO kh_dao = new KhachHang_DAO();
	
	
	private KhachHang taoKhachHang() {
		phatSinhMaKhachHang();
		String maKH = txtMa.getText().trim();
		String hoTen = txtTen.getText().trim();
		Date ngaySinh = txtNS.getDate();
		Date ngayDangKy = new Date();
		String maTheBaoHiem = txtBH.getText().trim();
		String gioiTinh = "Nam";
		if (radNu.isSelected())
			gioiTinh = "Nữ";
		String soCMND = txtCMND.getText().trim();
		String email = txtEmail.getText().trim();
		String soDienThoai = txtDT.getText().trim();
		String diaChi = txtDC.getText().trim();
		return new KhachHang(hoTen, ngaySinh, gioiTinh, soCMND, email, soDienThoai, diaChi, maKH, ngayDangKy,
				maTheBaoHiem);
	}
	private KhachHang suaKhachHang() {
		String maKH = txtMa.getText().trim();
		String hoTen = txtTen.getText().trim();
		Date ngaySinh = txtNS.getDate();
		Date ngayDangKy = new Date();
		String maTheBaoHiem = txtBH.getText().trim();
		String gioiTinh = "Nam";
		if (radNu.isSelected())
			gioiTinh = "Nữ";
		String soCMND = txtCMND.getText().trim();
		String email = txtEmail.getText().trim();
		String soDienThoai = txtDT.getText().trim();
		String diaChi = txtDC.getText().trim();
		return new KhachHang(hoTen, ngaySinh, gioiTinh, soCMND, email, soDienThoai, diaChi, maKH, ngayDangKy,
				maTheBaoHiem);
	}
	public void loadDB(ArrayList<KhachHang> ds) {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.getDataVector().removeAllElements();
		for (KhachHang kh : ds) {
			model.addRow(new Object[] { kh.getMaKH(), kh.getHoTenKhachHang(),kh.getSoCMND(),kh.getGioiTinh(),
					sdf.format(kh.getNgaySinh()),kh.getEmail(), kh.getSoDienThoai(),
					sdf.format(kh.getNgayDangKy()), kh.getMaTheBaoHiem(),kh.getDiaChi() });
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
		String dateNS = table.getValueAt(r, 4).toString();
		txtEmail.setText(table.getValueAt(r, 5).toString());
		txtDT.setText(table.getValueAt(r, 6).toString());

		String dataNDK = table.getValueAt(r, 7).toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date1, date2;
		try {
			date1 = dateFormat.parse(dateNS);
			date2 = dateFormat.parse(dataNDK);
			txtNS.setDate(date1);
			txtNDK.setDate(date2);
		txtBH.setText(table.getValueAt(r, 8).toString());
	
		txtDC.setText(table.getValueAt(r, 9).toString());
		
		
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	private boolean validData() {
		String hoTen = txtTen.getText().trim();
		if (hoTen.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtTen, "Bạn phải nhập họ và tên khách hàng", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtTen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtTen.requestFocus();
			return false;
		} else {
			//txtTen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			String regex = "^[a-zA-Z]+(\\s[a-zA-Z]+)*$";
			if (!RegularExpression.checkMatch(hoTen, regex)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtTen, "Họ tên nhập chưa đúng mẫu:^[a-zA-Z]+(\\\\s[a-zA-Z]+)*$", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				txtTen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtTen.requestFocus();
				txtTen.selectAll();
				return false;
			} else {
				txtTen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		}

		String ngaySinh = txtNS.getDateFormatString();
		if (ngaySinh.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtNS, "Bạn phải chọn ngày sinh", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
				txtNS.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtNS.requestFocus();
			return false;
		} else {
			txtNS.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		String maTheBaoHiem = txtBH.getText().trim();
		if (!maTheBaoHiem.equalsIgnoreCase("")) {
			String regex = "^[A-Z]{2,2}\\d{13,13}$";
			if (!RegularExpression.checkMatch(maTheBaoHiem, regex)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtBH, "Mã thẻ bảo hiểm chưa đúng định dạng:^[A-Z]{2,2}\\\\d{13,13}$", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				txtBH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtBH.requestFocus();
				txtBH.selectAll();
				return false;
			} else {
				txtBH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
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
		} else {
			//txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			String regex = "^[a-zA-Z0-9]+@gmail\\.com$";
			if (!RegularExpression.checkMatch(email, regex)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(txtEmail, "Họ tên nhập chưa đúng mẫu:^[a-zA-Z0-9]+@gmail\\\\.com$", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
				txtEmail.requestFocus();
				txtEmail.selectAll();
				return false;
			} else {
				txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			}
		}

		String soDienThoai = txtDT.getText().trim();
		if (soDienThoai.equalsIgnoreCase("")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(txtDT, "Bạn phải nhập số điện thoại", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			txtDT.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.RED));
			txtDT.requestFocus();
			return false;
		} else {
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
		} else {
			txtDC.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}

		return true;
	}
	
	private void phatSinhMaKhachHang() {
		String soCMND = txtCMND.getText().trim();
		if (!soCMND.equalsIgnoreCase("")) {
			String maKH = "KH" + soCMND;
			txtMa.setText(maKH);
		}
	}
	public void xoaRong() {
		txtMa.setText("");
		txtTen.setText("");
		txtNS.setDate(null);
		txtNDK.setDate(null);
		txtBH.setText("");
		radNam.setSelected(true);
		txtCMND.setText("");
		txtEmail.setText("");
		txtDT.setText("");
		txtDC.setText("");
		table.clearSelection();
		txtTen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtNS.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtNDK.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtBH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtCMND.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtEmail.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtDT.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtDC.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtTen.requestFocus();
		btnThem.setEnabled(true);
	}
    public GUI_QuanLiKhachHang() {
        initComponents();
    }

   
    private void initComponents() {

        jPanel19 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        lblTim = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        btnTim = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnXoaRong = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblMa = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        lblTen = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        lblNS = new javax.swing.JLabel();
        txtNS = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        lblDC = new javax.swing.JLabel();
        txtDC = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        lblCMND = new javax.swing.JLabel();
        txtCMND = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        lblGT = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        radNam = new javax.swing.JRadioButton();
        jPanel13 = new javax.swing.JPanel();
        radNu = new javax.swing.JRadioButton();
        jPanel15 = new javax.swing.JPanel();
        lblNDK = new javax.swing.JLabel();
        txtNDK = new com.toedter.calendar.JDateChooser();
        jPanel16 = new javax.swing.JPanel();
        lblDT = new javax.swing.JLabel();
        txtDT = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        lblBH = new javax.swing.JLabel();
        txtBH = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel19.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jPanel21.setLayout(new java.awt.GridLayout(3, 1, 20, 10));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel21.add(jPanel22);

        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        lblTim.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTim.setText("Nhập mã KH cần tìm:");
        jPanel23.add(lblTim);
        jPanel23.add(txtTim);

        jPanel21.add(jPanel23);

        jPanel24.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnTim.setBackground(new java.awt.Color(0, 153, 204));
        btnTim.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTim.setForeground(new java.awt.Color(255, 255, 255));
        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search.png"))); // NOI18N
        btnTim.setText("Tìm");
        jPanel24.add(btnTim);
        btnTim.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ma = txtTim.getText().toString().trim();
				
				if(ma.equals("")) {
					loadDB(kh_dao.layTatCaKhachHang());
				}
				else {
					ArrayList<KhachHang> ds = new ArrayList<KhachHang>();
					KhachHang kh = kh_dao.layKhachHang(ma);
					if(kh != null) {
						ds.add(kh);
						loadDB(ds);
					}else {
						JOptionPane.showMessageDialog(null, "Không tìm thấy");
					}
				}
				
			}
		});

        jPanel21.add(jPanel24);

        jPanel19.add(jPanel21);

        jPanel20.setLayout(new java.awt.GridLayout(3, 1, 0, 10));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel20.add(jPanel25);

        jPanel26.setLayout(new java.awt.GridLayout(1, 6, 20, 0));

        btnThem.setBackground(new java.awt.Color(0, 153, 204));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add.png"))); // NOI18N
        btnThem.setText("Thêm");
        jPanel26.add(btnThem);
        btnThem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validData()) {
					KhachHang_DAO kh_dao = new KhachHang_DAO();
					KhachHang kh = taoKhachHang();
					if(kh_dao.themKhachHang(kh)) {
						model.addRow(new Object[] { kh.getMaKH(), kh.getHoTenKhachHang(),kh.getSoCMND(),kh.getGioiTinh(),
								sdf.format(kh.getNgaySinh()),kh.getEmail(), kh.getSoDienThoai(),
								sdf.format(kh.getNgayDangKy()), kh.getMaTheBaoHiem(),kh.getDiaChi() });
						btnThem.setEnabled(false);
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công");
					}else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Thêm khách hàng không thành công");
					}
				}
				
			}
		});

        btnXoa.setBackground(new java.awt.Color(0, 153, 204));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Remove.png"))); // NOI18N
        btnXoa.setText("Xóa");
        jPanel26.add(btnXoa);
        btnXoa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String listMaKH = "";
				String maKH = "";
				System.out.println(table.getSelectedRow());
				if(table.getSelectedRow()<0) {
					JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để xóa");
				}else {
					//lay ra danh sach xoa tren gui
					int[] listIndex = table.getSelectedRows();
					listMaKH += table.getValueAt(listIndex[0], 0);
					for (int i = 1; i < table.getSelectedRowCount();i++) {
						listMaKH += ","+table.getValueAt(listIndex[i], 0);
					}
					//Hien thi thong tin xoa
					if(JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa?","Cảnh báo",JOptionPane.YES_NO_OPTION)
							== JOptionPane.YES_OPTION) {
						try {
							while(table.getSelectedRowCount()>0) {
								maKH = (String) table.getValueAt(table.getSelectedRow(), 0);
								if(!kh_dao.xoaMotKhachHang(maKH)) {
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
        jPanel26.add(btnXoaRong);
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
        jPanel26.add(btnSua);
        btnSua.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row < 0) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để sửa");
				}else {
					if(validData()) {
						KhachHang kh = suaKhachHang();
						KhachHang_DAO kh_dao = new KhachHang_DAO();
						System.out.println(kh);
						if(kh_dao.capNhatThongTinKhachHang(kh)) {
							JOptionPane.showMessageDialog(null, "Cập nhật thành công");
							loadDB(kh_dao.layTatCaKhachHang());
						}else {
							JOptionPane.showMessageDialog(null, "Không thể cập nhật");
							table.clearSelection();
						}
					}
				}
				
			}
		});

        jPanel20.add(jPanel26);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel20.add(jPanel27);

        jPanel19.add(jPanel20);

        add(jPanel19, java.awt.BorderLayout.SOUTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 153, 204))); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(1, 2, 30, 0));

        jPanel4.setLayout(new java.awt.GridLayout(6, 1, 0, 5));

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        lblMa.setText("Mã Khách Hàng:");
        lblMa.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel5.add(lblMa);
        jPanel5.add(txtMa);

        jPanel4.add(jPanel5);

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        lblTen.setText("Họ và Tên:");
        lblTen.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel6.add(lblTen);
        jPanel6.add(txtTen);

        jPanel4.add(jPanel6);

        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        lblNS.setText("Ngày sinh:");
        lblNS.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel7.add(lblNS);
        jPanel7.add(txtNS);

        jPanel4.add(jPanel7);

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        lblEmail.setText("Email:");
        lblEmail.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel8.add(lblEmail);
        jPanel8.add(txtEmail);

        jPanel4.add(jPanel8);

        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.LINE_AXIS));

        lblDC.setText("Địa chỉ:");
        lblDC.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel9.add(lblDC);
        jPanel9.add(txtDC);

        jPanel4.add(jPanel9);

        jPanel2.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(447, 300));
        jPanel3.setLayout(new java.awt.GridLayout(6, 1, 0, 5));

        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        lblCMND.setText("Số CMND:");
        lblCMND.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel10.add(lblCMND);
        jPanel10.add(txtCMND);

        jPanel3.add(jPanel10);

        jPanel11.setLayout(new java.awt.GridLayout(1, 3));

        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.LINE_AXIS));

        lblGT.setText("Giới Tính:");
        lblGT.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel14.add(lblGT);

        jPanel11.add(jPanel14);

        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.LINE_AXIS));

        radNam.setText("Nam");
        jPanel12.add(radNam);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radNam);
        buttonGroup.add(radNu);

        jPanel11.add(jPanel12);

        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.LINE_AXIS));

        radNu.setText("Nữ");
        jPanel13.add(radNu);

        jPanel11.add(jPanel13);

        jPanel3.add(jPanel11);

        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.LINE_AXIS));

        lblNDK.setText("Ngày Đăng Ký:");
        lblNDK.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel15.add(lblNDK);
        jPanel15.add(txtNDK);

        jPanel3.add(jPanel15);

        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        lblDT.setText("Số Điện Thoại:");
        lblDT.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel16.add(lblDT);
        jPanel16.add(txtDT);

        jPanel3.add(jPanel16);

        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        lblBH.setText("Mã BHYT:");
        lblBH.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel17.add(lblBH);
        jPanel17.add(txtBH);

        jPanel3.add(jPanel17);

        jPanel2.add(jPanel3);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.LINE_AXIS));

//        table.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] {
//                {null, null, null, null, null, null, null, null, null, null},
//                {null, null, null, null, null, null, null, null, null, null},
//                {null, null, null, null, null, null, null, null, null, null},
//                {null, null, null, null, null, null, null, null, null, null},
//                {null, null, null, null, null, null, null, null, null, null},
//                {null, null, null, null, null, null, null, null, null, null},
//                {null, null, null, null, null, null, null, null, null, null},
//                {null, null, null, null, null, null, null, null, null, null},
//                {null, null, null, null, null, null, null, null, null, null},
//                {null, null, null, null, null, null, null, null, null, null}
//            },
//            new String [] {
//                "Mã khách hàng", "Họ & Tên", "Số CMND", "Giới tính", "Ngày sinh", "Email", "Số điện thoại", "Ngày đăng ký", "Mã BHYT", "Địa chỉ"
//            }
//        ));
        String[] head = {"Mã khách hàng", "Họ và Tên", "Số CMND", "Giới tính", "Ngày sinh", "Email", "Số điện thoại", "Ngày đăng ký", "mã BHYT"
        	, "Địa chỉ"};
        model = new DefaultTableModel(head,0);
        table.setModel(model);
        table.addMouseListener(this);
        jScrollPane1.setViewportView(table);
        loadDB(kh_dao.layTatCaKhachHang());

        jPanel18.add(jScrollPane1);

        add(jPanel18, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaRong;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBH;
    private javax.swing.JLabel lblCMND;
    private javax.swing.JLabel lblDC;
    private javax.swing.JLabel lblDT;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGT;
    private javax.swing.JLabel lblMa;
    private javax.swing.JLabel lblNDK;
    private javax.swing.JLabel lblNS;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblTim;
    private javax.swing.JRadioButton radNam;
    private javax.swing.JRadioButton radNu;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtBH;
    private javax.swing.JTextField txtCMND;
    private javax.swing.JTextField txtDC;
    private javax.swing.JTextField txtDT;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMa;
    private com.toedter.calendar.JDateChooser txtNDK;
    private com.toedter.calendar.JDateChooser txtNS;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
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

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
