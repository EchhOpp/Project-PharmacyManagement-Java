package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entities.Thuoc;


public class Thuoc_DAO {
	public boolean themThuoc(Thuoc m) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO Thuoc VALUES(?,?,?,?,?,?,?)");
			ps.setString(1, m.getMaThuoc());
			ps.setString(2, m.getTenThuoc());
			ps.setString(3, m.getLoaiThuoc());
			ps.setString(4, m.getDonViThuoc());
			ps.setString(5, m.getXuatXu());
			ps.setInt(6, m.getSoLuongTon());
			ps.setString(7, m.getNhaCungCap().getMaNCC());
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
	
	public int laySoLuongTonCuaThuoc(String maThuoc) {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT SoLuongTon FROM Thuoc WHERE MaThuoc = ?");
			ps.setString(1, maThuoc);
			ResultSet rs = ps.executeQuery();
			int soLuongTon = 0;
			while (rs.next()) {
				soLuongTon = rs.getInt(1);
			}
			return soLuongTon;
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean capNhatSoLuongTonCuaThuoc(int soLuongTon, String maThuoc) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("UPDATE Thuoc SET SoLuongTon = ? WHERE MaThuoc = ?");
			ps.setInt(1, soLuongTon);
			ps.setString(2, maThuoc);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}

	public ArrayList<Thuoc> layTatCaThuoc() {
		try {
			ArrayList<Thuoc> danhSachThuoc = new ArrayList<Thuoc>();
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT [MaThuoc],[TenThuoc],[LoaiThuoc],[DonViThuoc],[XuatXu],[SoLuongTon],NCC.[MaNCC],[TenNCC],[Email],[SoDienThoai],[DiaChi] FROM Thuoc TH JOIN NhaCungCap NCC ON TH.MaNCC = NCC.MaNCC");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Thuoc thuoc = new Thuoc();
				thuoc.setMaThuoc(rs.getString(1));
				thuoc.setTenThuoc(rs.getString(2));
				thuoc.setLoaiThuoc(rs.getString(3));
				thuoc.setDonViThuoc(rs.getString(4));
				thuoc.setXuatXu(rs.getString(5));
				thuoc.setSoLuongTon(rs.getInt(6));
				thuoc.getNhaCungCap().setMaNCC(rs.getString(7));
				thuoc.getNhaCungCap().setTenNCC(rs.getString(8));
				thuoc.getNhaCungCap().setEmail(rs.getString(9));
				thuoc.getNhaCungCap().setSoDienThoai(rs.getString(10));
				thuoc.getNhaCungCap().setDiaChi(rs.getString(11));
				danhSachThuoc.add(thuoc);
			}
			return danhSachThuoc;
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<Thuoc> layTatCaThuocTonKho() {
		try {
			ArrayList<Thuoc> danhSachThuoc = new ArrayList<Thuoc>();
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT [MaThuoc],[TenThuoc],[LoaiThuoc],[DonViThuoc],[XuatXu],[SoLuongTon],NCC.[MaNCC],[TenNCC],[Email],[SoDienThoai],[DiaChi]\r\n"
							+ "FROM Thuoc TH JOIN NhaCungCap NCC ON TH.MaNCC = NCC.MaNCC\r\n" + "WHERE SoLuongTon > 0");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Thuoc thuoc = new Thuoc();
				thuoc.setMaThuoc(rs.getString(1));
				thuoc.setTenThuoc(rs.getString(2));
				thuoc.setLoaiThuoc(rs.getString(3));
				thuoc.setDonViThuoc(rs.getString(4));
				thuoc.setXuatXu(rs.getString(5));
				thuoc.setSoLuongTon(rs.getInt(6));
				thuoc.getNhaCungCap().setMaNCC(rs.getString(7));
				thuoc.getNhaCungCap().setTenNCC(rs.getString(8));
				thuoc.getNhaCungCap().setEmail(rs.getString(9));
				thuoc.getNhaCungCap().setSoDienThoai(rs.getString(10));
				thuoc.getNhaCungCap().setDiaChi(rs.getString(11));
				danhSachThuoc.add(thuoc);
			}
			return danhSachThuoc;
		} catch (Exception e) {
			return null;
		}
	}

	public String layMaThuocCuoiCung() {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("SELECT TOP 1 MaThuoc FROM Thuoc ORDER BY MaThuoc DESC");
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

	public int laySoLuongThuocKeDonConTonKho() {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT SUM(SoLuongTon) AS SoLuong\r\n" + "FROM Thuoc\r\n" + "WHERE LoaiThuoc = N'Thuốc kê đơn'");
			ResultSet rs = ps.executeQuery();
			int soLuongThuocHetHan = 0;
			while (rs.next()) {
				soLuongThuocHetHan = rs.getInt(1);
			}
			return soLuongThuocHetHan;
		} catch (Exception e) {
			return 0;
		}
	}

	public int laySoLuongThuocKhongKeDonConTonKho() {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT SUM(SoLuongTon) AS SoLuong\r\n"
					+ "FROM Thuoc\r\n" + "WHERE LoaiThuoc = N'Thuốc không kê đơn'");
			ResultSet rs = ps.executeQuery();
			int soLuongThuocHetHan = 0;
			while (rs.next()) {
				soLuongThuocHetHan = rs.getInt(1);
			}
			return soLuongThuocHetHan;
		} catch (Exception e) {
			return 0;
		}
	}

	public int laySoLuongThucPhamChucNangConTonKho() {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT SUM(SoLuongTon) AS SoLuong\r\n"
					+ "FROM Thuoc\r\n" + "WHERE LoaiThuoc = N'Thực phẩm chức năng'");
			ResultSet rs = ps.executeQuery();
			int soLuongThuocHetHan = 0;
			while (rs.next()) {
				soLuongThuocHetHan = rs.getInt(1);
			}
			return soLuongThuocHetHan;
		} catch (Exception e) {
			return 0;
		}
	}

	public int laySoLuongThucPhamChamSocSucKhoeConTonKho() {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT SUM(SoLuongTon) AS SoLuong\r\n"
					+ "FROM Thuoc\r\n" + "WHERE LoaiThuoc = N'Chăm sóc sức khỏe'");
			ResultSet rs = ps.executeQuery();
			int soLuongThuocHetHan = 0;
			while (rs.next()) {
				soLuongThuocHetHan = rs.getInt(1);
			}
			return soLuongThuocHetHan;
		} catch (Exception e) {
			return 0;
		}
	}

	public Thuoc layThuoc(String maThuoc) {
		try {
			Thuoc thuoc = null;
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT [MaThuoc],[TenThuoc],[LoaiThuoc],[DonViThuoc],[XuatXu],[SoLuongTon],NCC.[MaNCC],[TenNCC],[Email],[SoDienThoai],[DiaChi]\r\n"
							+ "FROM Thuoc TH JOIN NhaCungCap NCC ON TH.MaNCC = NCC.MaNCC \r\n" + "WHERE MaThuoc = ?");
			ps.setString(1, maThuoc);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				thuoc = new Thuoc();
				thuoc.setMaThuoc(rs.getString(1));
				thuoc.setTenThuoc(rs.getString(2));
				thuoc.setLoaiThuoc(rs.getString(3));
				thuoc.setDonViThuoc(rs.getString(4));
				thuoc.setXuatXu(rs.getString(5));
				thuoc.setSoLuongTon(rs.getInt(6));
				thuoc.getNhaCungCap().setMaNCC(rs.getString(7));
				thuoc.getNhaCungCap().setTenNCC(rs.getString(8));
				thuoc.getNhaCungCap().setEmail(rs.getString(9));
				thuoc.getNhaCungCap().setSoDienThoai(rs.getString(10));
				thuoc.getNhaCungCap().setDiaChi(rs.getString(11));
			}
			return thuoc;
		} catch (Exception e) {
			return null;
		}
	}
}
