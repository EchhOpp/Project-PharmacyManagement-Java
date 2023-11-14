package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entities.NhaCungCap;

public class NhaCungCap_DAO {
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

	public ArrayList<NhaCungCap> layTatCaNhaCungCap() {
		try {
			ArrayList<NhaCungCap> danhSachNhaCungCap = new ArrayList<NhaCungCap>();
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT * FROM NhaCungCap");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				NhaCungCap nhaCungCap = new NhaCungCap();
				nhaCungCap.setMaNCC(rs.getString(1));
				nhaCungCap.setTenNCC(rs.getString(2));
				nhaCungCap.setEmail(rs.getString(3));
				nhaCungCap.setSoDienThoai(rs.getString(4));
				nhaCungCap.setDiaChi(rs.getString(5));
				danhSachNhaCungCap.add(nhaCungCap);
			}
			return danhSachNhaCungCap;
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

	public boolean capNhatThongTinNhaCungCap(NhaCungCap nhaCungCap) {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"UPDATE NhaCungCap SET TenNCC = ?, Email = ?, SoDienThoai = ?, DiaChi = ? WHERE MaNCC = ?");
			ps.setString(1, nhaCungCap.getTenNCC());
			ps.setString(2, nhaCungCap.getEmail());
			ps.setString(3, nhaCungCap.getSoDienThoai());
			ps.setString(4, nhaCungCap.getDiaChi());
			ps.setString(5, nhaCungCap.getMaNCC());
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
}
