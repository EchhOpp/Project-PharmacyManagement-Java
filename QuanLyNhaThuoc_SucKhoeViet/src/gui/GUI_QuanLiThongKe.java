package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.SystemColor;

public class GUI_QuanLiThongKe extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public GUI_QuanLiThongKe() {
		setLayout(new BorderLayout(0, 0));
		// Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set the size of the main panel to fill the screen
        setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        
        JPanel pnl_ALL = new JPanel();
		add(pnl_ALL, BorderLayout.CENTER);
		pnl_ALL.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		pnl_ALL.add(tabbedPane);
		/*
		 * 
		 */
		JPanel pnl_ThuocHetHan = new JPanel();
		tabbedPane.addTab("Thống kê thuốc hết hạn", null, pnl_ThuocHetHan, null);
		
		JLabel lbl_TieuDe1 = new JLabel("Thống kê thuốc hết hạn");
		lbl_TieuDe1.setForeground(SystemColor.textHighlight);
		lbl_TieuDe1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		pnl_ThuocHetHan.add(lbl_TieuDe1);


		
		
		
		/*
		 *
		 */
		JPanel pnl_ThuocTonKho = new JPanel();
		tabbedPane.addTab("Thống kê thuốc tồn kho", null, pnl_ThuocTonKho, null);
		
		JLabel lbl_TieuDe2 = new JLabel("Thống kê thuốc tồn kho");
		lbl_TieuDe2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lbl_TieuDe2.setForeground(SystemColor.textHighlight);
		pnl_ThuocTonKho.add(lbl_TieuDe2);
		
		
		
		
		
		/*
		 * 
		 */
		JPanel pnl_DoanhSoBan = new JPanel();
		tabbedPane.addTab("Thống kê doanh số bán", null, pnl_DoanhSoBan, null);
		
		JLabel lbl_TieuDe3 = new JLabel("Thống kê doanh số bán");
		lbl_TieuDe3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lbl_TieuDe3.setForeground(SystemColor.textHighlight);
		pnl_DoanhSoBan.add(lbl_TieuDe3);
		
		
		
		
		
		
		/*
		 *
		 */
		JPanel pnl_SoLuongKhachHang = new JPanel();
		tabbedPane.addTab("Thống kê số lượng khách hàng", null, pnl_SoLuongKhachHang, null);
		
		JLabel lbl_TieuDe4 = new JLabel("Thống kê số lượng khách hàng");
		lbl_TieuDe4.setForeground(SystemColor.textHighlight);
		lbl_TieuDe4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		pnl_SoLuongKhachHang.add(lbl_TieuDe4);
		
	}

}
