package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entities.HoaDon;
import entities.KhachHang;
import entities.NhanVien;

public class HoaDon_DAO {
	public boolean themHoaDon(HoaDon hd, KhachHang kh) {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("INSERT INTO HoaDon VALUES(?,?,?,?,?,?)");
			ps.setString(1, hd.getMaHD());
			ps.setString(2, kh.getMaKH());
			ps.setString(3, hd.getNhanVien().getMaNV());
			ps.setDate(4, new Date(hd.getNgayLapHoaDon().getTime()));
			ps.setString(5, hd.getTheBaoHiem());
			ps.setFloat(6, hd.getMienGiam());
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}

	public String layMaHDCuoiCung() {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("SELECT TOP 1 MaHD FROM HoaDon ORDER BY MaHD DESC");
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

	public ArrayList<HoaDon> layTatCaHoaDon() {
		try {
			ArrayList<HoaDon> danhSachHoaDon = new ArrayList<HoaDon>();
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT MaHD, KH.MaKH, HoTenKhachHang, NV.MaNV, HoTenNhanVien, NgayLapHoaDon, TheBaoHiem, MienGiam\r\n"
							+ "FROM HoaDon HD JOIN KhachHang KH ON HD.MaKH = KH.MaKH JOIN NhanVien NV ON HD.MaNV = NV.MaNV");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				HoaDon hd = new HoaDon();
				KhachHang kh = hd.getKhachHang();
				NhanVien nv = hd.getNhanVien();
				hd.setMaHD(rs.getString(1));
				kh.setMaKH(rs.getString(2));
				kh.setHoTenKhachHang(rs.getString(3));
				nv.setMaNV(rs.getString(4));
				nv.setHoTenNhanVien(rs.getString(5));
				hd.setNgayLapHoaDon(rs.getDate(6));
				hd.setTheBaoHiem(rs.getString(7));
				hd.setMienGiam(rs.getFloat(8));
				danhSachHoaDon.add(hd);
			}
			return danhSachHoaDon;
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean capNhatMienGiam(float mienGiam, String maHD) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("UPDATE HoaDon SET MienGiam = ? WHERE MaHD = ?");
			ps.setFloat(1, mienGiam);
			ps.setString(2, maHD);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
	
	public boolean xoaMotHoaDon(String maHD) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("DELETE FROM HoaDon WHERE MaHD = ?");
			ps.setString(1, maHD);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
	
	public float layDoanhSoBanTheoThang(int thang, int nam) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("SELECT SUM(SoLuong * DonGiaBan) AS TongTienBanDuoc\r\n" + 
							"FROM HoaDon HD JOIN ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD\r\n" + 
							"WHERE MONTH(NgayLapHoaDon) = ? AND YEAR(NgayLapHoaDon) = ?\r\n" + 
							"GROUP BY MONTH(NgayLapHoaDon)");
			ps.setInt(1, thang);
			ps.setInt(2, nam);
			ResultSet rs = ps.executeQuery();
			float doanhSoBan = 0;
			while (rs.next()) {
				doanhSoBan = rs.getFloat(1);
			}
			return doanhSoBan;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public ArrayList<String> layCacNamLapHoaDon() {
		try {
			ArrayList<String> danhSachNam = new ArrayList<String>();
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("SELECT DISTINCT YEAR(NgayLapHoaDon) AS Nam FROM HoaDon");
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
