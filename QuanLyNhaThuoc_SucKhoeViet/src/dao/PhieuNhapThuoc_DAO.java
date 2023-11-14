package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entities.NhaCungCap;
import entities.NhanVien;
import entities.PhieuNhapThuoc;
import entities.Thuoc;


public class PhieuNhapThuoc_DAO {
	public boolean themPhieuNhapThuoc(PhieuNhapThuoc inventory, NhanVien e) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO PhieuNhapThuoc VALUES(?,?,?,?,?,?,?,?)");
			ps.setString(1, inventory.getMaPhieu());
			ps.setString(2, e.getMaNV());
			ps.setString(3, inventory.getThuoc().getMaThuoc());
			ps.setDate(4, new Date(inventory.getNgayNhap().getTime()));
			ps.setDate(5, new Date(inventory.getNgaySanXuat().getTime()));
			ps.setDate(6, new Date(inventory.getNgayHetHan().getTime()));
			ps.setFloat(7, inventory.getDonGiaMua());
			ps.setInt(8, inventory.getSoLuongNhap());
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}

	public String layMaPhieuNhapThuocCuoiCung() {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("SELECT TOP 1 MaPhieu FROM PhieuNhapThuoc ORDER BY MaPhieu DESC");
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

	public float layDonGiaMoiNhatTheoMaThuoc(String maThuoc) {
		try {
			float donGiaMua = 0;
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT TOP 1 DonGiaMua FROM PhieuNhapThuoc PH JOIN Thuoc TH ON PH.MaThuoc = TH.MaThuoc WHERE PH.MaThuoc = ? ORDER BY NgayNhap DESC, DonGiaMua DESC");
			ps.setString(1, maThuoc);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				donGiaMua = rs.getFloat("DonGiaMua");
			}
			return donGiaMua;
		} catch (Exception e1) {
			return 0;
		}
	}

	public PhieuNhapThuoc layPhieuNhapThuoc(String maPhieuNhapThuoc) {
		try {
			PhieuNhapThuoc pnt = null;
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT PNT.MaPhieu, NgayNhap, PNT.MaThuoc, TenThuoc, NgaySanXuat, NgayHetHan, LoaiThuoc, DonViThuoc, XuatXu, SoLuongTon, TH.MaNCC, TenNCC, NCC.Email, NCC.SoDienThoai, NCC.DiaChi, PNT.MaNV, HoTenNhanVien, DonGiaMua, SoLuongNhap\r\n" + 
					"FROM PhieuNhapThuoc PNT JOIN Thuoc TH ON PNT.MaThuoc = TH.MaThuoc\r\n" + 
					"						JOIN NhaCungCap NCC ON TH.MaNCC = NCC.MaNCC\r\n" + 
					"						JOIN NhanVien NV ON PNT.MaNV = NV.MaNV\r\n" + 
					"WHERE PNT.MaPhieu = ?");
			ps.setString(1, maPhieuNhapThuoc);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pnt = new PhieuNhapThuoc();
				Thuoc thuoc = pnt.getThuoc();
				NhaCungCap ncc = thuoc.getNhaCungCap();
				NhanVien nv = pnt.getNhanVien();
				pnt.setMaPhieu(rs.getString(1));
				pnt.setNgayNhap(rs.getDate(2));
				thuoc.setMaThuoc(rs.getString(3));
				thuoc.setTenThuoc(rs.getString(4));
				pnt.setNgaySanXuat(rs.getDate(5));
				pnt.setNgayHetHan(rs.getDate(6));
				thuoc.setLoaiThuoc(rs.getString(7));
				thuoc.setDonViThuoc(rs.getString(8));
				thuoc.setXuatXu(rs.getString(9));
				thuoc.setSoLuongTon(rs.getInt(10));
				ncc.setMaNCC(rs.getString(11));
				ncc.setTenNCC(rs.getString(12));
				ncc.setEmail(rs.getString(13));
				ncc.setSoDienThoai(rs.getString(14));
				ncc.setDiaChi(rs.getString(15));
				nv.setMaNV(rs.getString(16));
				nv.setHoTenNhanVien(rs.getString(17));
				pnt.setDonGiaMua(rs.getFloat(18));
				pnt.setSoLuongNhap(rs.getInt(19));
			}
			return pnt;
		} catch (Exception e) {
			return null;
		}
	}
	
	public ArrayList<PhieuNhapThuoc> layTatCaPhieuNhapThuoc() {
		try {
			ArrayList<PhieuNhapThuoc> danhSachPhieuNhapThuoc = new ArrayList<PhieuNhapThuoc>();
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT PNT.MaPhieu, NgayNhap, PNT.MaThuoc, TenThuoc, NgaySanXuat, NgayHetHan, LoaiThuoc, DonViThuoc, XuatXu, SoLuongTon, TH.MaNCC, TenNCC, NCC.Email, NCC.SoDienThoai, NCC.DiaChi, PNT.MaNV, HoTenNhanVien, DonGiaMua, SoLuongNhap\r\n"
							+ "FROM PhieuNhapThuoc PNT JOIN Thuoc TH ON PNT.MaThuoc = TH.MaThuoc \r\n"
							+ "     JOIN NhaCungCap NCC ON TH.MaNCC = NCC.MaNCC \r\n"
							+ "	 JOIN NhanVien NV ON PNT.MaNV = NV.MaNV");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PhieuNhapThuoc pnt = new PhieuNhapThuoc();
				Thuoc thuoc = pnt.getThuoc();
				NhaCungCap ncc = thuoc.getNhaCungCap();
				NhanVien nv = pnt.getNhanVien();
				pnt.setMaPhieu(rs.getString(1));
				pnt.setNgayNhap(rs.getDate(2));
				thuoc.setMaThuoc(rs.getString(3));
				thuoc.setTenThuoc(rs.getString(4));
				pnt.setNgaySanXuat(rs.getDate(5));
				pnt.setNgayHetHan(rs.getDate(6));
				thuoc.setLoaiThuoc(rs.getString(7));
				thuoc.setDonViThuoc(rs.getString(8));
				thuoc.setXuatXu(rs.getString(9));
				thuoc.setSoLuongTon(rs.getInt(10));
				ncc.setMaNCC(rs.getString(11));
				ncc.setTenNCC(rs.getString(12));
				ncc.setEmail(rs.getString(13));
				ncc.setSoDienThoai(rs.getString(14));
				ncc.setDiaChi(rs.getString(15));
				nv.setMaNV(rs.getString(16));
				nv.setHoTenNhanVien(rs.getString(17));
				pnt.setDonGiaMua(rs.getFloat(18));
				pnt.setSoLuongNhap(rs.getInt(19));
				danhSachPhieuNhapThuoc.add(pnt);
			}
			return danhSachPhieuNhapThuoc;
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<PhieuNhapThuoc> layTatCaPhieuNhapThuocHetHan() {
		try {
			ArrayList<PhieuNhapThuoc> danhSachPhieuNhapThuoc = new ArrayList<PhieuNhapThuoc>();
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT PNT.MaPhieu, NgayNhap, PNT.MaThuoc, TenThuoc, NgaySanXuat, NgayHetHan, LoaiThuoc, DonViThuoc, XuatXu, SoLuongTon, TH.MaNCC, TenNCC, NCC.Email, NCC.SoDienThoai, NCC.DiaChi, PNT.MaNV, HoTenNhanVien, DonGiaMua, SoLuongNhap\r\n"
							+ "FROM PhieuNhapThuoc PNT JOIN Thuoc TH ON PNT.MaThuoc = TH.MaThuoc\r\n"
							+ "						JOIN NhaCungCap NCC ON TH.MaNCC = NCC.MaNCC\r\n"
							+ "						JOIN NhanVien NV ON PNT.MaNV = NV.MaNV\r\n"
							+ "WHERE NgayHetHan < GETDATE()");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PhieuNhapThuoc pnt = new PhieuNhapThuoc();
				Thuoc thuoc = pnt.getThuoc();
				NhaCungCap ncc = thuoc.getNhaCungCap();
				NhanVien nv = pnt.getNhanVien();
				pnt.setMaPhieu(rs.getString(1));
				pnt.setNgayNhap(rs.getDate(2));
				thuoc.setMaThuoc(rs.getString(3));
				thuoc.setTenThuoc(rs.getString(4));
				pnt.setNgaySanXuat(rs.getDate(5));
				pnt.setNgayHetHan(rs.getDate(6));
				thuoc.setLoaiThuoc(rs.getString(7));
				thuoc.setDonViThuoc(rs.getString(8));
				thuoc.setXuatXu(rs.getString(9));
				thuoc.setSoLuongTon(rs.getInt(10));
				ncc.setMaNCC(rs.getString(11));
				ncc.setTenNCC(rs.getString(12));
				ncc.setEmail(rs.getString(13));
				ncc.setSoDienThoai(rs.getString(14));
				ncc.setDiaChi(rs.getString(15));
				nv.setMaNV(rs.getString(16));
				nv.setHoTenNhanVien(rs.getString(17));
				pnt.setDonGiaMua(rs.getFloat(18));
				pnt.setSoLuongNhap(rs.getInt(19));
				danhSachPhieuNhapThuoc.add(pnt);
			}
			return danhSachPhieuNhapThuoc;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean xoaMotPhieuNhapThuoc(String maPhieuNhapThuoc) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("DELETE FROM PhieuNhapThuoc WHERE MaPhieu = ?");
			ps.setString(1, maPhieuNhapThuoc);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}

	public int laySoLuongThuocKhongKeDonHetHan() {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT SUM(SoLuongTon) AS SoLuong\r\n"
					+ "FROM PhieuNhapThuoc PNT JOIN Thuoc TH ON PNT.MaThuoc = TH.MaThuoc\r\n"
					+ "WHERE NgayHetHan < GETDATE() AND LoaiThuoc = N'Thuốc không kê đơn'\r\n" + "GROUP BY LoaiThuoc");
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

	public int laySoLuongThuocKeDonHetHan() {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT SUM(SoLuongTon) AS SoLuong\r\n"
					+ "FROM PhieuNhapThuoc PNT JOIN Thuoc TH ON PNT.MaThuoc = TH.MaThuoc\r\n"
					+ "WHERE NgayHetHan < GETDATE() AND LoaiThuoc = N'Thuốc kê đơn'\r\n" + "GROUP BY LoaiThuoc");
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

	public int laySoLuongThucPhamChucNangHetHan() {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT SUM(SoLuongTon) AS SoLuong\r\n"
					+ "FROM PhieuNhapThuoc PNT JOIN Thuoc TH ON PNT.MaThuoc = TH.MaThuoc\r\n"
					+ "WHERE NgayHetHan < GETDATE() AND LoaiThuoc = N'Thực phẩm chức năng'\r\n" + "GROUP BY LoaiThuoc");
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

	public int laySoLuongThucPhamChamSocSucKhoeHetHan() {
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("SELECT SUM(SoLuongTon) AS SoLuong\r\n"
					+ "FROM PhieuNhapThuoc PNT JOIN Thuoc TH ON PNT.MaThuoc = TH.MaThuoc\r\n"
					+ "WHERE NgayHetHan < GETDATE() AND LoaiThuoc = N'Chăm sóc sức khỏe'\r\n" + "GROUP BY LoaiThuoc");
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
}
