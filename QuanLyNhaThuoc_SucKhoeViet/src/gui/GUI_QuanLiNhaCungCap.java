package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import dao.NhaCungCap_DAO;
import entities.NhaCungCap;

public class GUI_QuanLiNhaCungCap extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtMaNCC;
	private JTextField txtEmail;
	private JTextField txtSDT;
	private JTextField txtTimNCC;
	private JTextField txtTenNCC;
	private DefaultTableModel tblModelDSNCC;
	private JScrollPane scrDSNCC;
	private JTable tblDSNCC;
	private JTextArea txtDiaChi;
	private int edit;
	private JButton btnThem;
	private JButton btnXoa;
	private JButton btnCapNhat;
	private JButton btnXoaTrang;
	private JButton btnLuu;
	private NhaCungCap_DAO nhaCungCap_DAO;
	private static int nhaCungCapCounter = 1;
	/**
	 * Create the panel.
	 */
	public GUI_QuanLiNhaCungCap() {
		setLayout(new BorderLayout(0, 0));
		// Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set the size of the main panel to fill the screen
        setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		
		JPanel pnl_ALL = new JPanel();
		add(pnl_ALL, BorderLayout.CENTER);
		pnl_ALL.setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_TieuDe = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
	
		pnl_TieuDe.setPreferredSize(new Dimension(10, 50));
		pnl_ALL.add(pnl_TieuDe);
		pnl_ALL.add(pnl_TieuDe, BorderLayout.NORTH);
		
		JLabel lbl_TieuDe = new JLabel("Quản Lí Nhà Cung Cấp");
		lbl_TieuDe.setForeground(SystemColor.textHighlight);
		lbl_TieuDe.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		pnl_TieuDe.add(lbl_TieuDe);
		
		JPanel pnl_ThongTinNhap = new JPanel();
		
		pnl_ThongTinNhap.setBorder(new TitledBorder(null, "Th\u00F4ng tin nh\u00E0 cung c\u1EA5p", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnl_ALL.add(pnl_ThongTinNhap, BorderLayout.CENTER);
		pnl_ThongTinNhap.setLayout(null);
		
		JLabel lbl_MaNCC = new JLabel("Mã nhà cung cấp:");
		lbl_MaNCC.setBounds(47, 26, 139, 13);
		pnl_ThongTinNhap.add(lbl_MaNCC);
		
		txtMaNCC = new JTextField();
		txtMaNCC.setBounds(152, 23, 575, 26);
		pnl_ThongTinNhap.add(txtMaNCC);
		txtMaNCC.setColumns(10);
		txtMaNCC.setEnabled(false);
		
		JLabel lbl_TenNCC = new JLabel("Tên nhà cung cấp:");
		lbl_TenNCC.setBounds(773, 26, 135, 13);
		pnl_ThongTinNhap.add(lbl_TenNCC);
		
		txtTenNCC = new JTextField();
		txtTenNCC.setColumns(10);
		txtTenNCC.setBounds(897, 23, 573, 26);
		pnl_ThongTinNhap.add(txtTenNCC);
		
		JLabel lbl_Email = new JLabel("Email:");
		lbl_Email.setBounds(47, 62, 95, 13);
		pnl_ThongTinNhap.add(lbl_Email);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(152, 59, 575, 25);
		pnl_ThongTinNhap.add(txtEmail);
		
		JLabel lbl_SDT = new JLabel("Số điện thoại");
		lbl_SDT.setBounds(773, 62, 95, 13);
		pnl_ThongTinNhap.add(lbl_SDT);
		
		txtSDT = new JTextField();
		txtSDT.setColumns(10);
		txtSDT.setBounds(897, 59, 573, 25);
		pnl_ThongTinNhap.add(txtSDT);
		
		JLabel lbl_DiaChi = new JLabel("Địa chỉ");
		lbl_DiaChi.setBounds(47, 100, 95, 13);
		pnl_ThongTinNhap.add(lbl_DiaChi);
		
		txtDiaChi = new JTextArea();
		txtDiaChi.setBounds(152, 94, 1318, 39);
		pnl_ThongTinNhap.add(txtDiaChi);
		
		btnThem = new JButton("Thêm");
		btnThem.setBorderPainted(false);
		btnThem.setBackground(new java.awt.Color(0, 204, 204));
		btnThem.setBounds(311, 158, 85, 31);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				themNCC();
			}
		});
		pnl_ThongTinNhap.add(btnThem);
		
		btnXoa = new JButton("Xóa");
		btnXoa.setBorderPainted(false);
		btnXoa.setBackground(new java.awt.Color(0, 204, 204));
		btnXoa.setBounds(457, 158, 85, 31);
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xoaNCC();
			}
		});
		pnl_ThongTinNhap.add(btnXoa);
		
		btnCapNhat = new JButton("Cập Nhật");
		btnCapNhat.setBorderPainted(false);
		btnCapNhat.setBackground(new java.awt.Color(0, 204, 204));
		btnCapNhat.setBounds(612, 158, 101, 31);
		btnCapNhat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				capNhatNCC();
			}
		});
		pnl_ThongTinNhap.add(btnCapNhat);
		
		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBackground(new java.awt.Color(0, 204, 204));
		btnXoaTrang.setBorderPainted(false);
		btnXoaTrang.setBounds(792, 158, 107, 31);
		btnXoaTrang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xoaTrang();
			}
		});
		pnl_ThongTinNhap.add(btnXoaTrang);
		
		btnLuu = new JButton("Lưu");
		btnLuu.setBackground(new java.awt.Color(0, 204, 204));
		btnLuu.setBorderPainted(false);
		btnLuu.setBounds(965, 158, 85, 31);
		btnLuu.setEnabled(false);
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				luuNCCVaoDS();
			}
		});
		pnl_ThongTinNhap.add(btnLuu);
		
		JLabel lblTimNCC = new JLabel("Tìm nhà cung cấp:");
		lblTimNCC.setBounds(47, 218, 139, 13);
		pnl_ThongTinNhap.add(lblTimNCC);
		
		txtTimNCC = new JTextField();
		txtTimNCC.setBounds(152, 215, 149, 19);
		pnl_ThongTinNhap.add(txtTimNCC);
		txtTimNCC.setColumns(10);
		
		JButton btnTimNCC = new JButton("Tìm");
		btnTimNCC.setBorderPainted(false);
		btnTimNCC.setBackground(new java.awt.Color(0, 204, 204));
		btnTimNCC.setBounds(311, 214, 85, 21);
		btnTimNCC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timNCC();
			}
		});
		pnl_ThongTinNhap.add(btnTimNCC);
		
		JButton btnLamMoi = new JButton("Làm Mới");
		btnLamMoi.setBorderPainted(false);
		btnLamMoi.setBackground(new Color(0, 204, 204));
		btnLamMoi.setBounds(1112, 158, 107, 31);
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lamMoiBang();
			}
		});
		pnl_ThongTinNhap.add(btnLamMoi);
		
		JPanel pnl_DanhSachNCC = new JPanel();
		
		pnl_DanhSachNCC.setBorder(new TitledBorder(null, "Danh S\u00E1ch Nh\u00E0 Cung C\u1EA5p", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnl_DanhSachNCC.setPreferredSize(new Dimension(10, 350));
		String headers[] = "Mã nhà cung cấp;Tên nhà cung cấp;Email;Số điện thoại;Địa chỉ".split(";");
		tblModelDSNCC = new DefaultTableModel(headers, 0);
		pnl_DanhSachNCC.setLayout(new BorderLayout(0, 0));
		scrDSNCC = new JScrollPane();
		scrDSNCC.setViewportView(tblDSNCC = new JTable(tblModelDSNCC));
		pnl_DanhSachNCC.add(scrDSNCC);
		pnl_ALL.add(pnl_DanhSachNCC, BorderLayout.SOUTH);
		nhaCungCap_DAO = new NhaCungCap_DAO();
		updateTableData();
		
	}
	private void xoaTrang() {
		// TODO Auto-generated method stub
		txtTenNCC.setText("");
		txtEmail.setText("");
		txtSDT.setText("");
		txtDiaChi.setText("");
		txtTenNCC.requestFocus();
	}
	protected void xoaNCC() {
		// Kiểm tra hàng đẫ được chọn hay không
		int n = tblDSNCC.getSelectedRow();
		if (n != -1) {
			if (!tblDSNCC.getModel().getValueAt(n, 1).toString().equalsIgnoreCase("")) {
				int tb = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa?");
				if (tb == JOptionPane.YES_OPTION) {
					String maNCC = tblDSNCC.getModel().getValueAt(n, 0).toString();
					nhaCungCap_DAO.xoaMotNhaCungCap(maNCC);
					lamMoiBang();
				}
			}
		} else
			JOptionPane.showMessageDialog(null, "Hãy chọn dịch vụ cần xóa!");

	}
	protected void themNCC() {
		// TODO Auto-generated method stub
		edit = 1;
		if (btnThem.getText().equalsIgnoreCase("Thêm")) {
			xoaTrang();
			// vô hiệu hóa button cập nhật và button xóa
			btnCapNhat.setEnabled(false);
			btnXoa.setEnabled(false);
			btnThem.setText("Hủy");
			// Mở khóa Button lưu 
			btnLuu.setEnabled(true);
			txtTenNCC.requestFocus();
		} else if (btnThem.getText().equalsIgnoreCase("Hủy")) {
			edit = 0;
			btnCapNhat.setEnabled(true);
			btnXoa.setEnabled(true);
			btnLuu.setEnabled(false);
			btnThem.setText("Thêm");
			xoaTrang();
		}
	}
	private void capNhatNCC() {
	    edit = 2;
	    int row = tblDSNCC.getSelectedRow();
	    if (row >= 0) {
	    	String maNCC = txtMaNCC.getText();
			String tenNCC = txtTenNCC.getText();
			String email = txtEmail.getText();
			String sdt = txtSDT.getText();
			String diaChi = txtDiaChi.getText();
			if(btnCapNhat.getText().equalsIgnoreCase("Hủy")) {
				btnCapNhat.setText("Cập Nhật");
				btnThem.setEnabled(true);
				btnXoaTrang.setEnabled(true);
				btnLuu.setEnabled(false);
				btnXoa.setEnabled(true);
			}else
	        if (row != -1) {
				edit = 2;
				if (btnCapNhat.getText().equalsIgnoreCase("Cập Nhật")) {
					btnCapNhat.setText("Hủy");
					 btnLuu.setEnabled(true);
					 btnThem.setEnabled(false);
					 btnXoa.setEnabled(false);
					// Đưa dữ liệu từ bảng vào textField để cập nhật
					txtMaNCC.setText(tblDSNCC.getModel().getValueAt(row, 0).toString());
					txtTenNCC.setText(tblDSNCC.getModel().getValueAt(row, 1).toString());
					txtEmail.setText(tblDSNCC.getModel().getValueAt(row, 2).toString());
					txtSDT.setText(tblDSNCC.getModel().getValueAt(row, 3).toString());
					txtDiaChi.setText(tblDSNCC.getModel().getValueAt(row, 4).toString());
				}
		}else {
	            int dialogButton = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật thông tin không?", "Warning", JOptionPane.YES_NO_OPTION);
	            if (dialogButton == JOptionPane.YES_OPTION) {
	            	NhaCungCap ncc = new NhaCungCap(maNCC,tenNCC,email,sdt,diaChi);
	                if (nhaCungCap_DAO.capNhatThongTinNhaCungCap(ncc)) {
	                    tblModelDSNCC.setValueAt(maNCC, row, 0);
	                    tblModelDSNCC.setValueAt(tenNCC, row, 1);
	                    tblModelDSNCC.setValueAt(email, row, 2);
	                    tblModelDSNCC.setValueAt(sdt, row, 3);
	                    tblModelDSNCC.setValueAt(diaChi, row, 4);
	                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
	                    xoaTrang();
	                    btnCapNhat.setText("Cập Nhật");
	                } else {
	                    JOptionPane.showMessageDialog(this, "Cập nhật không thành công");
	                }
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng trong bảng để cập nhật thông tin nhân viên.");
	    }
	}
	protected void luuNCCVaoDS() {
		// TODO Auto-generated method stub
		String maNCC = txtMaNCC.getText();
		String tenNCC = txtTenNCC.getText();
		String email = txtEmail.getText();
		String sdt = txtSDT.getText();
		String diaChi = txtDiaChi.getText();
		if (tenNCC.equals("") || sdt.equals("") || email.equals("") || diaChi.equals("")) {
			JOptionPane.showMessageDialog(this, "Thông tin nhập không được để trống");
		} else {
			if (edit == 1) {
				if(validData()) {
					String ma = taoMaNCC();
					NhaCungCap ncc = new NhaCungCap(ma, tenNCC, email, sdt, diaChi);
					nhaCungCap_DAO.themNhaCungCap(ncc);
					lamMoiBang();
					JOptionPane.showMessageDialog(this, "Tạo mới thành công");
					xoaTrang();
					lamMoiBang();
					btnCapNhat.setEnabled(true);
					btnXoa.setEnabled(true);
					btnLuu.setEnabled(false);
					btnThem.setText("Thêm");		
				}
			}
			else if (edit == 2) {
				if(validData()) {
				NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, email, sdt, diaChi);
				nhaCungCap_DAO.capNhatThongTinNhaCungCap(ncc);
				JOptionPane.showMessageDialog(this, "Cập nhật thành công");
				xoaTrang();
				lamMoiBang();
				btnThem.setEnabled(true);
				btnXoa.setEnabled(true);
				btnLuu.setEnabled(false);
				btnCapNhat.setText("Cập Nhật");
				}
			}
		}
	}
	private boolean timMaNCC(String maNCC) {
		// TODO Auto-generated method stub
		maNCC = maNCC.toUpperCase();
		List<NhaCungCap>list = nhaCungCap_DAO.layTatCaNhaCungCap();
		for (NhaCungCap ncc : list) {
			if (ncc.getMaNCC().contains(maNCC))
				return false;
		}
		return true;
	}
	public String taoMaNCC() {
		String maNCC;
		// Sử dụng Formatter để định dạng chuỗi thành "NCCXX"
		Formatter formatter = new Formatter();
		do {
			formatter = new Formatter();
			// Lấy chuỗi đã định dạng từ Formatter và trả về
			maNCC = formatter.format("NCC%05d", nhaCungCapCounter).toString();
			nhaCungCapCounter++;
		} while (!timMaNCC(maNCC));
		formatter.close();
		return maNCC;
	}
	private void updateTableData() {
		List<NhaCungCap> list = nhaCungCap_DAO.layTatCaNhaCungCap();
		for(NhaCungCap ncc : list) {
			String [] rowData = {ncc.getMaNCC(),ncc.getTenNCC(),ncc.getEmail(),ncc.getSoDienThoai(),ncc.getDiaChi()};
			tblModelDSNCC.addRow(rowData);
		}
		tblDSNCC.setModel(tblModelDSNCC);
	}
	protected void lamMoiBang() {
		// TODO Auto-generated method stub
		tblModelDSNCC.setRowCount(0);
		updateTableData();
	}
	private void updata(List<NhaCungCap> list) {
		tblModelDSNCC.setRowCount(0);
		for(NhaCungCap ncc : list) {
			String [] rowData = {ncc.getMaNCC(),ncc.getTenNCC(),ncc.getEmail(),ncc.getSoDienThoai(),ncc.getDiaChi()};
			tblModelDSNCC.addRow(rowData);
		}
		tblDSNCC.setModel(tblModelDSNCC);
	}
	protected void timNCC() {
		String a = txtTimNCC.getText();
		String nhaCungCap = "";
		if (!a.equals("")) {
			// Tạo câu lệnh truy vấn tìm kiếm trên sql
			nhaCungCap = "MaNCC like N'%" + a + "%' or TenNCC like N'%" + a + "%' or Email like  N'%" + a
					+ "%' or SoDienThoai like N'%" + a + "%' or DiaChi like N'%"+ a + "%'";
			updata(nhaCungCap_DAO.timNhaCungCap(nhaCungCap));
		} else
			JOptionPane.showMessageDialog(null, "Hãy Nhập Thông Tin Tìm Kiếm!");
	}
	private boolean validData() {
		String sdt = txtSDT.getText();
		String email = txtEmail.getText();
		if (!(sdt.length() >= 10 && sdt.length() <= 11 && sdt.matches("\\d+"))) {
			JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10-11 chữ số và không chứa ký tự đặc biệt");
			return false;
		}
		if (!(email.matches(".+@gmail\\.com"))) {
			JOptionPane.showMessageDialog(this, "Email phải có ít nhất một ký tự trước @ và kết thúc bằng @gmail.com");
			return false;
		}
		return true;
	}
}
