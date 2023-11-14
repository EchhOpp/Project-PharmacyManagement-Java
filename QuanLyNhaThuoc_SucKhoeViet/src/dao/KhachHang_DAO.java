package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entities.KhachHang;



public class KhachHang_DAO {
	public boolean themKhachHang(KhachHang kh) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO KhachHang VALUES(?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, kh.getMaKH());
			ps.setString(2, kh.getHoTenKhachHang());
			ps.setDate(3, new Date(kh.getNgaySinh().getTime()));
			ps.setDate(4, new Date(kh.getNgayDangKy().getTime()));
			ps.setString(5, kh.getMaTheBaoHiem());
			ps.setString(6, kh.getGioiTinh());
			ps.setString(7, kh.getSoCMND());
			ps.setString(8, kh.getEmail());
			ps.setString(9, kh.getSoDienThoai());
			ps.setString(10, kh.getDiaChi());
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}

	public boolean xoaMotKhachHang(String maKH) {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("DELETE FROM KhachHang WHERE MaKH = ?");
			ps.setString(1, maKH);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}

	public ArrayList<KhachHang> layTatCaKhachHang() {
		try {
			ArrayList<KhachHang> danhSachKhachHang = new ArrayList<KhachHang>();
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT * FROM KhachHang");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				KhachHang kh = new KhachHang();
				kh.setMaKH(rs.getString(1));
				kh.setHoTenKhachHang(rs.getString(2));
				kh.setNgaySinh(rs.getDate(3));
				kh.setNgayDangKy(rs.getDate(4));
				kh.setMaTheBaoHiem(rs.getString(5));
				kh.setGioiTinh(rs.getString(6));
				kh.setSoCMND(rs.getString(7));
				kh.setEmail(rs.getString(8));
				kh.setSoDienThoai(rs.getString(9));
				kh.setDiaChi(rs.getString(10));
				danhSachKhachHang.add(kh);
			}
			return danhSachKhachHang;
		} catch (Exception e) {
			return null;
		}
	}

	public int laySoLuongKhachHangTheoThang(int thang, int nam) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("SELECT COUNT(MaKH) AS SoLuongKhachHang\r\n" + 
							"FROM KhachHang\r\n" + 
							"WHERE MONTH(NgayDangKy) = ? AND YEAR(NgayDangKy) = ?\r\n" + 
							"GROUP BY MONTH(NgayDangKy)");
			ps.setInt(1, thang);
			ps.setInt(2, nam);
			ResultSet rs = ps.executeQuery();
			int soLuongKhachHang = 0;
			while (rs.next()) {
				soLuongKhachHang = rs.getInt(1);
			}
			return soLuongKhachHang;
		} catch (Exception e) {
			return 0;
		}
	}

	public KhachHang layKhachHang(String maKH) {
		try {
			KhachHang kh = null;
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT * FROM KhachHang WHERE MaKH = ?");
			ps.setString(1, maKH);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				kh = new KhachHang();
				kh.setMaKH(rs.getString(1));
				kh.setHoTenKhachHang(rs.getString(2));
				kh.setNgaySinh(rs.getDate(3));
				kh.setNgayDangKy(rs.getDate(4));
				kh.setMaTheBaoHiem(rs.getString(5));
				kh.setGioiTinh(rs.getString(6));
				kh.setSoCMND(rs.getString(7));
				kh.setEmail(rs.getString(8));
				kh.setSoDienThoai(rs.getString(9));
				kh.setDiaChi(rs.getString(10));
			}
			return kh;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean capNhatThongTinKhachHang(KhachHang khachHang, String maKHCu) {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"UPDATE KhachHang SET MaKH = ?, HoTenKhachHang = ?, NgaySinh = ?, NgayDangKy = ?, MaTheBaoHiem = ?, GioiTinh = ?, SoCMND = ?, Email = ?, SoDienThoai = ?, DiaChi = ? WHERE MaKH = ?");
			ps.setString(1, khachHang.getMaKH());
			ps.setString(2, khachHang.getHoTenKhachHang());
			ps.setDate(3, new Date(khachHang.getNgaySinh().getTime()));
			ps.setDate(4, new Date(khachHang.getNgayDangKy().getTime()));
			ps.setString(5, khachHang.getMaTheBaoHiem());
			ps.setString(6, khachHang.getGioiTinh());
			ps.setString(7, khachHang.getSoCMND());
			ps.setString(8, khachHang.getEmail());
			ps.setString(9, khachHang.getSoDienThoai());
			ps.setString(10, khachHang.getDiaChi());
			ps.setString(11, maKHCu);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}

	public ArrayList<String> layCacNamDangKyCuaKhachHang() {
		try {
			ArrayList<String> danhSachNam = new ArrayList<String>();
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("SELECT DISTINCT YEAR(NgayDangKy) AS Nam FROM KhachHang");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				danhSachNam.add(String.valueOf(rs.getInt(1)));
			}
			return danhSachNam;
		} catch (Exception e) {
			return null;
		}
	}
}
