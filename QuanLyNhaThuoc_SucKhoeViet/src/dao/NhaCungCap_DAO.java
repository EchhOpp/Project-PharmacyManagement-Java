package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entities.NhaCungCap;


public class NhaCungCap_DAO {
	List<NhaCungCap> dsncc;
	public List<NhaCungCap> layTatCaNhaCungCap() {
		List<NhaCungCap> dsncc = new ArrayList<NhaCungCap>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			String sql = "Select * from NhaCungCap";
			//System.out.println(sql);
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả vể đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maNCC = rs.getString(1);
				String tenNCC = rs.getString(2);
				String email = rs.getString(3);
				String sdt = rs.getString(4);
				String diaChi = rs.getString(5);
				NhaCungCap ncc = new NhaCungCap(maNCC,tenNCC,email,sdt,diaChi);
				dsncc.add(ncc);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dsncc;
	}
	public boolean themNhaCungCap(NhaCungCap s) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO NhaCungCap VALUES(?,?,?,?,?)");
			ps.setString(1, s.getMaNCC());
			ps.setString(2, s.getTenNCC());
			ps.setString(3, s.getEmail());
			ps.setString(4, s.getSoDienThoai());
			ps.setString(5, s.getDiaChi());
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}	
	public boolean xoaMotNhaCungCap(String maNCC) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("DELETE FROM NhaCungCap WHERE MaNCC = ?");
			ps.setString(1, maNCC);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
	
	public NhaCungCap layNhaCungCap(String maNCC) {
		try {
			NhaCungCap nhaCungCap = null;
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT * FROM NhaCungCap WHERE MaNCC = ?");
			ps.setString(1, maNCC);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				nhaCungCap = new NhaCungCap();
				nhaCungCap.setMaNCC(rs.getString(1));
				nhaCungCap.setTenNCC(rs.getString(2));
				nhaCungCap.setEmail(rs.getString(3));
				nhaCungCap.setSoDienThoai(rs.getString(4));
				nhaCungCap.setDiaChi(rs.getString(5));
			}
			return nhaCungCap;
		} catch (Exception e) {
			return null;
		}
	}

	public String layMaNCCCuoiCung() {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("SELECT TOP 1 MaNCC FROM NhaCungCap ORDER BY MaNCC DESC");
			ResultSet rs = ps.executeQuery();
			String s = null;
			while (rs.next()) {
				s = rs.getString(1);
			}
			return s;
		} catch (Exception e) {
			return null;
		}
	}
	public boolean capNhatThongTinNhaCungCap(NhaCungCap ncc) {
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			String sql = "UPDATE NhaCungCap SET TenNCC = ?, Email = ?, SoDienThoai = ?, DiaChi = ? WHERE MaNCC = ?";
			stmt = con.prepareStatement(sql);
			System.out.println(sql);
			stmt.setString(1, ncc.getTenNCC());
			stmt.setString(2, ncc.getEmail());
			stmt.setString(3, ncc.getSoDienThoai());
			stmt.setString(4, ncc.getDiaChi());
			stmt.setString(5, ncc.getMaNCC());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace(); 
		} 
		return n >0;
	}
	public List<NhaCungCap> timNhaCungCap(String ncc) {
		List<NhaCungCap> dsncc = new ArrayList<NhaCungCap>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			String sql = "Select * from NhaCungCap where " + ncc;
			Statement statement = con.createStatement();
			// Thực thi câu lệnh SQL trả vể đối tượng ResultSet
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				dsncc.add(new NhaCungCap(rs.getString("MaNCC"), rs.getString("TenNCC"), rs.getString("Email"),
						rs.getString("SoDienThoai"), rs.getString("DiaChi")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsncc;
	}
}
