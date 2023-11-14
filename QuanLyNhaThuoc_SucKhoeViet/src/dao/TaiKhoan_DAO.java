package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connectDB.ConnectDB;
import entities.NhanVien;
import entities.TaiKhoan;
import utilities.MyExtension;


public class TaiKhoan_DAO {
	public boolean themTaiKhoan(NhanVien e) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO TaiKhoan VALUES(?, ?, ?, ?)");
			ps.setString(1, e.getMaNV());
			ps.setString(2, MyExtension.ConvertHashToString(e.getTaiKhoan().getMatKhau()));
			ps.setBytes(3, e.getTaiKhoan().getHinhAnh());
			ps.setString(4, e.getTaiKhoan().getTrangThai());
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}

	public TaiKhoan timTaiKhoan(String id) {
		try {
			TaiKhoan acc = null;
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT * FROM TaiKhoan WHERE id = ?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				acc = new TaiKhoan();
				acc.setMatKhau(rs.getString("MatKhau"));
				acc.setHinhAnh(rs.getBytes("HinhAnh"));
				acc.setTrangThai(rs.getString("TrangThai"));
			}
			return acc;
		} catch (Exception e1) {
			return null;
		}
	}

	public boolean khoaTaiKhoan(String id) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("UPDATE TaiKhoan SET TrangThai = N'Tài khoản đang khóa' WHERE MaNV = ?");
			ps.setString(1, id);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
	
	public boolean capNhatMatKhau(String matKhauMoi,String maNV) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("UPDATE TaiKhoan SET MatKhau = ? WHERE MaNV = ?");
			ps.setString(1, MyExtension.ConvertHashToString(matKhauMoi));
			ps.setString(2, maNV);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
	
	public boolean capNhatHinhAnh(byte[] hinhAnh,String maNV) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("UPDATE TaiKhoan SET HinhAnh = ? WHERE MaNV = ?");
			ps.setBytes(1, hinhAnh);
			ps.setString(2, maNV);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
	
	public boolean capNhatTrangThai(String trangThai,String maNV) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("UPDATE TaiKhoan SET TrangThai = ? WHERE MaNV = ?");
			ps.setString(1, trangThai);
			ps.setString(2, maNV);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
}
