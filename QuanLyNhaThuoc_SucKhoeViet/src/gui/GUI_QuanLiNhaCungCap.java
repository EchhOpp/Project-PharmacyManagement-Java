package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class GUI_QuanLiNhaCungCap extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtMaNCC;
	private JTextField textField;
	private JTextField txtEmail;
	private JTextField txtSDT;
	private JTextField textField_1;
	private DefaultTableModel tblModelDSNCC;
	private JScrollPane scrDSNCC;
	private JTable tblDSNCC;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_QuanLiNhaCungCap frame = new GUI_QuanLiNhaCungCap();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI_QuanLiNhaCungCap() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 662, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, screenSize.height);
		this.setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_TieuDe = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnl_TieuDe.setPreferredSize(new Dimension(10, 50));
		contentPane.add(pnl_TieuDe);
		contentPane.add(pnl_TieuDe, BorderLayout.NORTH);
		
		JLabel lbl_TieuDe = new JLabel("Quản Lí Nhà Cung Cấp");
		lbl_TieuDe.setForeground(new Color(0, 191, 255));
		lbl_TieuDe.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnl_TieuDe.add(lbl_TieuDe);
		
		JPanel pnl_DanhSachNCC = new JPanel();
		pnl_DanhSachNCC.setBorder(new TitledBorder(null, "Danh S\u00E1ch Nh\u00E0 Cung C\u1EA5p", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnl_DanhSachNCC.setPreferredSize(new Dimension(10, 500));
		String headers[] = "Mã nhà cung cấp;Tên nhà cung cấp;Email;Số điện thoại;Địa chỉ".split(";");
		tblModelDSNCC = new DefaultTableModel(headers, 0);
		pnl_DanhSachNCC.setLayout(null);
		scrDSNCC = new JScrollPane();
		scrDSNCC.setBounds(0, 22, 1512, 421);
		scrDSNCC.setViewportView(tblDSNCC = new JTable(tblModelDSNCC));
		pnl_DanhSachNCC.add(scrDSNCC);
		contentPane.add(pnl_DanhSachNCC, BorderLayout.SOUTH);
		
		JButton btnTroVeTrangChu = new JButton("Trở Về Trang Chủ");
		btnTroVeTrangChu.setBounds(1312, 453, 147, 37);
		btnTroVeTrangChu.setBorderPainted(false);
		btnTroVeTrangChu.setBackground(new Color(0, 191, 255));
		pnl_DanhSachNCC.add(btnTroVeTrangChu);
		
		JButton btnDangXuat = new JButton("Đăng xuất");
		btnDangXuat.setBounds(1196, 453, 106, 37);
		btnDangXuat.setBorderPainted(false);
		btnDangXuat.setBackground(new Color(0, 191, 255));
		pnl_DanhSachNCC.add(btnDangXuat);
		
		JPanel pnl_ThongTinNhap = new JPanel();
		pnl_ThongTinNhap.setBorder(new TitledBorder(null, "Th\u00F4ng tin nh\u00E0 cung c\u1EA5p", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(pnl_ThongTinNhap, BorderLayout.CENTER);
		pnl_ThongTinNhap.setLayout(null);
		
		JLabel lbl_MaNCC = new JLabel("Mã nhà cung cấp:");
		lbl_MaNCC.setBounds(47, 26, 139, 13);
		pnl_ThongTinNhap.add(lbl_MaNCC);
		
		txtMaNCC = new JTextField();
		txtMaNCC.setBounds(152, 23, 575, 19);
		pnl_ThongTinNhap.add(txtMaNCC);
		txtMaNCC.setColumns(10);
		
		JLabel lbl_TenNCC = new JLabel("Tên nhà cung cấp:");
		lbl_TenNCC.setBounds(773, 26, 135, 13);
		pnl_ThongTinNhap.add(lbl_TenNCC);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(897, 23, 573, 19);
		pnl_ThongTinNhap.add(textField);
		
		JLabel lbl_Email = new JLabel("Email:");
		lbl_Email.setBounds(47, 62, 95, 13);
		pnl_ThongTinNhap.add(lbl_Email);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(152, 59, 575, 19);
		pnl_ThongTinNhap.add(txtEmail);
		
		JLabel lbl_SDT = new JLabel("Số điện thoại");
		lbl_SDT.setBounds(773, 62, 95, 13);
		pnl_ThongTinNhap.add(lbl_SDT);
		
		txtSDT = new JTextField();
		txtSDT.setColumns(10);
		txtSDT.setBounds(897, 59, 573, 19);
		pnl_ThongTinNhap.add(txtSDT);
		
		JLabel lbl_DiaChi = new JLabel("Địa chỉ");
		lbl_DiaChi.setBounds(47, 100, 95, 13);
		pnl_ThongTinNhap.add(lbl_DiaChi);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(152, 94, 1318, 39);
		pnl_ThongTinNhap.add(textArea);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setBorderPainted(false);
		btnThem.setBackground(new Color(0, 191, 255));
		btnThem.setBounds(311, 158, 85, 31);
		pnl_ThongTinNhap.add(btnThem);
		
		JButton btnXoa = new JButton("Xóa");
		btnXoa.setBorderPainted(false);
		btnXoa.setBackground(new Color(0, 191, 255));
		btnXoa.setBounds(457, 158, 85, 31);
		pnl_ThongTinNhap.add(btnXoa);
		
		JButton btnCapNhat = new JButton("Cập Nhật");
		btnCapNhat.setBorderPainted(false);
		btnCapNhat.setBackground(new Color(0, 191, 255));
		btnCapNhat.setBounds(612, 158, 101, 31);
		pnl_ThongTinNhap.add(btnCapNhat);
		
		JButton btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBackground(new Color(0, 191, 255));
		btnXoaTrang.setBorderPainted(false);
		btnXoaTrang.setBounds(792, 158, 107, 31);
		pnl_ThongTinNhap.add(btnXoaTrang);
		
		JButton btnLuu = new JButton("Lưu");
		btnLuu.setBorderPainted(false);
		btnLuu.setBackground(new Color(0, 191, 255));
		btnLuu.setBounds(965, 158, 85, 31);
		pnl_ThongTinNhap.add(btnLuu);
		
		JLabel lblTimNCC = new JLabel("Tìm nhà cung cấp:");
		lblTimNCC.setBounds(47, 218, 139, 13);
		pnl_ThongTinNhap.add(lblTimNCC);
		
		textField_1 = new JTextField();
		textField_1.setBounds(152, 215, 149, 19);
		pnl_ThongTinNhap.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnTimNCC = new JButton("Tìm");
		btnTimNCC.setBorderPainted(false);
		btnTimNCC.setBackground(new Color(0, 191, 255));
		btnTimNCC.setBounds(311, 214, 85, 21);
		pnl_ThongTinNhap.add(btnTimNCC);
	}
}
