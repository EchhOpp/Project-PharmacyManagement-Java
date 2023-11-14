package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.Thuoc;



public class ChiTietHoaDon_DAO {
	public boolean themChiTietHoaDon(HoaDon hd, ChiTietHoaDon cthd) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO ChiTietHoaDon VALUES(?,?,?,?)");
			ps.setString(1, hd.getMaHD());
			ps.setString(2, cthd.getThuoc().getMaThuoc());
			ps.setInt(3, cthd.getSoLuong());
			ps.setFloat(4, cthd.getDonGiaBan());
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
	
	public ArrayList<ChiTietHoaDon> layDSChiTietHoaDon(String maHD) {
		try {
			ArrayList<ChiTietHoaDon> danhSachChiTietHoaDon = new ArrayList<ChiTietHoaDon>();
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement(
					"SELECT MaThuoc, SoLuong, DonGiaBan\r\n" + 
					"FROM ChiTietHoaDon CTHD JOIN HoaDon HD ON CTHD.MaHD = HD.MaHD\r\n" + 
					"WHERE HD.MaHD = ?");
			ps.setString(1, maHD);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ChiTietHoaDon cthd = new ChiTietHoaDon();
				cthd.setThuoc(new Thuoc(rs.getString(1)));
				cthd.setSoLuong(rs.getInt(2));
				cthd.setDonGiaBan(rs.getFloat(3));
				danhSachChiTietHoaDon.add(cthd);
			}
			return danhSachChiTietHoaDon;
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean xoaMotChiTietHoaDon(String maHD, String maThuoc) {
		try {
			PreparedStatement ps = ConnectDB.getConnection()
					.prepareStatement("DELETE FROM ChiTietHoaDon WHERE MaHD = ? AND MaThuoc = ?");
			ps.setString(1, maHD);
			ps.setString(2, maThuoc);
			return ps.executeUpdate() > 0;
		} catch (Exception e1) {
			return false;
		}
	}
}
