package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class GUI_QuanLiNhaCungCap extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtMa;
	private JTextField txtMaNCC;
	private JTextField txtEmail;
	private JTextField txtSDT;
	private JTextField txtTimNCC;
	private JTextField txtTenNCC;
	private DefaultTableModel tblModelDSNCC;
	private JScrollPane scrDSNCC;
	private JTable tblDSNCC;

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
		pnl_TieuDe.setBackground(Color.LIGHT_GRAY);
		pnl_TieuDe.setPreferredSize(new Dimension(10, 50));
		pnl_ALL.add(pnl_TieuDe);
		pnl_ALL.add(pnl_TieuDe, BorderLayout.NORTH);
		
		JLabel lbl_TieuDe = new JLabel("Quản Lí Nhà Cung Cấp");
		lbl_TieuDe.setForeground(SystemColor.textHighlight);
		lbl_TieuDe.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		pnl_TieuDe.add(lbl_TieuDe);
		
		JPanel pnl_ThongTinNhap = new JPanel();
		pnl_ThongTinNhap.setBackground(Color.LIGHT_GRAY);
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
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(152, 94, 1318, 39);
		pnl_ThongTinNhap.add(textArea);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setBorderPainted(false);
		btnThem.setBackground(Color.LIGHT_GRAY);
		btnThem.setBounds(311, 158, 85, 31);
		pnl_ThongTinNhap.add(btnThem);
		
		JButton btnXoa = new JButton("Xóa");
		btnXoa.setBorderPainted(false);
		btnXoa.setBackground(Color.LIGHT_GRAY);
		btnXoa.setBounds(457, 158, 85, 31);
		pnl_ThongTinNhap.add(btnXoa);
		
		JButton btnCapNhat = new JButton("Cập Nhật");
		btnCapNhat.setBorderPainted(false);
		btnCapNhat.setBackground(Color.LIGHT_GRAY);
		btnCapNhat.setBounds(612, 158, 101, 31);
		pnl_ThongTinNhap.add(btnCapNhat);
		
		JButton btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBackground(Color.LIGHT_GRAY);
		btnXoaTrang.setBorderPainted(false);
		btnXoaTrang.setBounds(792, 158, 107, 31);
		pnl_ThongTinNhap.add(btnXoaTrang);
		
		JButton btnLuu = new JButton("Lưu");
		btnLuu.setBorderPainted(false);
		btnLuu.setBackground(Color.LIGHT_GRAY);
		btnLuu.setBounds(965, 158, 85, 31);
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
		btnTimNCC.setBackground(Color.LIGHT_GRAY);
		btnTimNCC.setBounds(311, 214, 85, 21);
		pnl_ThongTinNhap.add(btnTimNCC);
		
		JPanel pnl_DanhSachNCC = new JPanel();
		pnl_DanhSachNCC.setBackground(new Color(192, 192, 192));
		pnl_DanhSachNCC.setBorder(new TitledBorder(null, "Danh S\u00E1ch Nh\u00E0 Cung C\u1EA5p", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnl_DanhSachNCC.setPreferredSize(new Dimension(10, 350));
		String headers[] = "Mã nhà cung cấp;Tên nhà cung cấp;Email;Số điện thoại;Địa chỉ".split(";");
		tblModelDSNCC = new DefaultTableModel(headers, 0);
		pnl_DanhSachNCC.setLayout(new BorderLayout(0, 0));
		scrDSNCC = new JScrollPane();
		scrDSNCC.setViewportView(tblDSNCC = new JTable(tblModelDSNCC));
		pnl_DanhSachNCC.add(scrDSNCC);
		pnl_ALL.add(pnl_DanhSachNCC, BorderLayout.SOUTH);
	}

}
