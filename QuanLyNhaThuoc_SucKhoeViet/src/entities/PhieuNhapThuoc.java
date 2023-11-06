package entities;

import java.util.Date;
import java.util.Objects;

public class PhieuNhapThuoc {
	private String maPhieu;
	private Date ngaySanXuat;
	private Date ngayHetHan;
	private Date ngayNhap;
	private double donGiaNhap;
	private int soLuong;
	
	public PhieuNhapThuoc(String maPhieu, Date ngaySanXuat, Date ngayHetHan, Date ngayNhap, double donGiaNhap,
			int soLuong) {
		super();
		this.maPhieu = maPhieu;
		this.ngaySanXuat = ngaySanXuat;
		this.ngayHetHan = ngayHetHan;
		this.ngayNhap = ngayNhap;
		this.donGiaNhap = donGiaNhap;
		this.soLuong = soLuong;
	}

	public PhieuNhapThuoc(String maPhieu) {
		super();
		this.maPhieu = maPhieu;
	}

	public PhieuNhapThuoc() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(maPhieu);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhieuNhapThuoc other = (PhieuNhapThuoc) obj;
		return Objects.equals(maPhieu, other.maPhieu);
	}

	public String getMaPhieu() {
		return maPhieu;
	}

	public void setMaPhieu(String maPhieu) {
		this.maPhieu = maPhieu;
	}

	public Date getNgaySanXuat() {
		return ngaySanXuat;
	}

	public void setNgaySanXuat(Date ngaySanXuat) {
		this.ngaySanXuat = ngaySanXuat;
	}

	public Date getNgayHetHan() {
		return ngayHetHan;
	}

	public void setNgayHetHan(Date ngayHetHan) {
		this.ngayHetHan = ngayHetHan;
	}

	public Date getNgayNhap() {
		return ngayNhap;
	}

	public void setNgayNhap(Date ngayNhap) {
		this.ngayNhap = ngayNhap;
	}

	public double getDonGiaNhap() {
		return donGiaNhap;
	}

	public void setDonGiaNhap(double donGiaNhap) {
		this.donGiaNhap = donGiaNhap;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	@Override
	public String toString() {
		return "PhieuNhapThuoc [maPhieu=" + maPhieu + ", ngaySanXuat=" + ngaySanXuat + ", ngayHetHan=" + ngayHetHan
				+ ", ngayNhap=" + ngayNhap + ", donGiaNhap=" + donGiaNhap + ", soLuong=" + soLuong + "]";
	}
	
	
	
}
