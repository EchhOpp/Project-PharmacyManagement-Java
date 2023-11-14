package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entities.NhanVien;


public class NhanVien_DAO {
	public boolean themNhanVien(NhanVien e) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO NhanVien VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, e.getMaNV());
			ps.setString(2, e.getHoTenNhanVien());
			ps.setDate(3, new Date(e.getNgaySinh().getTime()));
			ps.setDate(4, new Date(e.getNgayVaoLam().getTime()));
			ps.setString(5, e.getGioiTinh());
			ps.setString(6, e.getSoCMND());
			ps.setString(7, e.getEmail());
			ps.setString(8, e.getSoDienThoai());
			ps.setString(9, e.getDiaChi());
			ps.setString(10, e.getChucVu());
			ps.setString(11, e.getTinhTrang());
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
	
	public boolean xoaMotNhanVien(String maNV) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("DELETE FROM NhanVien WHERE MaNV = ?");
			ps.setString(1, maNV);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}

	public NhanVien layNhanVien(String maNV) {
		try {
			NhanVien emp = null;
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT NV.MaNV,[HoTenNhanVien],[NgaySinh],[NgayVaoLam],[GioiTinh],[SoCMND],[Email],[SoDienThoai],[DiaChi],[ChucVu],[TinhTrang],[MatKhau],[HinhAnh],[TrangThai]\r\n"
							+ "FROM NhanVien NV JOIN TaiKhoan TK ON NV.MaNV=TK.MaNV\r\n" + "WHERE NV.MaNV = ?");
			ps.setString(1, maNV);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				emp = new NhanVien();
				emp.setMaNV(rs.getString(1));
				emp.setHoTenNhanVien(rs.getString(2));
				emp.setNgaySinh(rs.getDate(3));
				emp.setNgayVaoLam(rs.getDate(4));
				emp.setGioiTinh(rs.getString(5));
				emp.setSoCMND(rs.getString(6));
				emp.setEmail(rs.getString(7));
				emp.setSoDienThoai(rs.getString(8));
				emp.setDiaChi(rs.getString(9));
				emp.setChucVu(rs.getString(10));
				emp.setTinhTrang(rs.getString(11));
				emp.getTaiKhoan().setMatKhau(rs.getString(12));
				emp.getTaiKhoan().setHinhAnh(rs.getBytes(13));
				emp.getTaiKhoan().setTrangThai(rs.getString(14));
			}
			return emp;
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<NhanVien> layTatCaNhanVien() {
		try {
			ArrayList<NhanVien> danhSachNhanVien = new ArrayList<NhanVien>();
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT NV.MaNV,[HoTenNhanVien],[NgaySinh],[NgayVaoLam],[GioiTinh],[SoCMND],[Email],[SoDienThoai],[DiaChi],[ChucVu],[TinhTrang],[MatKhau],[HinhAnh],[TrangThai]\r\n"
							+ "FROM NhanVien NV JOIN TaiKhoan TK ON NV.MaNV=TK.MaNV");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				NhanVien nhanVien = new NhanVien();
				nhanVien.setMaNV(rs.getString(1));
				nhanVien.setHoTenNhanVien(rs.getString(2));
				nhanVien.setNgaySinh(rs.getDate(3));
				nhanVien.setNgayVaoLam(rs.getDate(4));
				nhanVien.setGioiTinh(rs.getString(5));
				nhanVien.setSoCMND(rs.getString(6));
				nhanVien.setEmail(rs.getString(7));
				nhanVien.setSoDienThoai(rs.getString(8));
				nhanVien.setDiaChi(rs.getString(9));
				nhanVien.setChucVu(rs.getString(10));
				nhanVien.setTinhTrang(rs.getString(11));
				nhanVien.getTaiKhoan().setMatKhau(rs.getString(12));
				nhanVien.getTaiKhoan().setHinhAnh(rs.getBytes(13));
				nhanVien.getTaiKhoan().setTrangThai(rs.getString(14));
				danhSachNhanVien.add(nhanVien);
			}
			return danhSachNhanVien;
		} catch (Exception e) {
			return null;
		}
	}

	public String layMaNVCuoiCung() {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("SELECT TOP 1 MaNV FROM NhanVien ORDER BY MaNV DESC");
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
	
	public boolean capNhatThongTinNhanVien(NhanVien nhanVien) {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"UPDATE NhanVien SET HoTenNhanVien = ?, NgaySinh = ?, NgayVaoLam = ?, GioiTinh = ?, SoCMND = ?, Email = ?, SoDienThoai = ?, DiaChi = ?, ChucVu = ?, TinhTrang = ? WHERE MaNV = ?");
			ps.setString(1, nhanVien.getHoTenNhanVien());
			ps.setDate(2, new Date(nhanVien.getNgaySinh().getTime()));
			ps.setDate(3, new Date(nhanVien.getNgayVaoLam().getTime()));
			ps.setString(4, nhanVien.getGioiTinh());
			ps.setString(5, nhanVien.getSoCMND());
			ps.setString(6, nhanVien.getEmail());
			ps.setString(7, nhanVien.getSoDienThoai());
			ps.setString(8, nhanVien.getDiaChi());
			ps.setString(9, nhanVien.getChucVu());
			ps.setString(10, nhanVien.getTinhTrang());
			ps.setString(11, nhanVien.getMaNV());
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
}
