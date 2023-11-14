package entities;

import java.util.Date;

public class PhieuNhapThuoc {
	// Các thuộc tính
	private String maPhieu;
	private NhanVien nhanVien;
	private Thuoc thuoc;
	private Date ngayNhap;
	private Date ngaySanXuat;
	private Date ngayHetHan;
	private float donGiaMua;
	private int soLuongNhap;

	// Các getter/setter
	public String getMaPhieu() {
		return maPhieu;
	}

	public void setMaPhieu(String maPhieu) {
		this.maPhieu = maPhieu;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public Thuoc getThuoc() {
		return thuoc;
	}

	public void setThuoc(Thuoc thuoc) {
		this.thuoc = thuoc;
	}

	public Date getNgayNhap() {
		return ngayNhap;
	}

	public void setNgayNhap(Date ngayNhap) {
		this.ngayNhap = ngayNhap;
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

	public float getDonGiaMua() {
		return donGiaMua;
	}

	public void setDonGiaMua(float donGiaMua) {
		this.donGiaMua = donGiaMua;
	}

	public int getSoLuongNhap() {
		return soLuongNhap;
	}

	public void setSoLuongNhap(int soLuongNhap) {
		this.soLuongNhap = soLuongNhap;
	}

	// Các constructors
	public PhieuNhapThuoc() {
		this("", new NhanVien(), new Thuoc(), new Date(), new Date(), new Date(), 0f, 0);
	}

	public PhieuNhapThuoc(String maPhieu) {
		this(maPhieu, new NhanVien(), new Thuoc(), new Date(), new Date(), new Date(), 0f, 0);
	}
	
	public PhieuNhapThuoc(String maPhieu, NhanVien nhanVien, Thuoc thuoc, Date ngayNhap, Date ngaySanXuat,
			Date ngayHetHan, float donGiaMua, int soLuongNhap) {
		setMaPhieu(maPhieu);
		setNhanVien(nhanVien);
		setThuoc(thuoc);
		setNgayNhap(ngayNhap);
		setNgaySanXuat(ngaySanXuat);
		setNgayHetHan(ngayHetHan);
		setDonGiaMua(donGiaMua);
		setSoLuongNhap(soLuongNhap);
	}

	@Override
	public String toString() {
		return "PhieuNhapThuoc [maPhieu=" + maPhieu + ", nhanVien=" + nhanVien + ", thuoc=" + thuoc + ", ngayNhap="
				+ ngayNhap + ", ngaySanXuat=" + ngaySanXuat + ", ngayHetHan=" + ngayHetHan + ", donGiaMua=" + donGiaMua
				+ ", soLuongNhap=" + soLuongNhap + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maPhieu == null) ? 0 : maPhieu.hashCode());
		return result;
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
		if (maPhieu == null) {
			if (other.maPhieu != null)
				return false;
		} else if (!maPhieu.equals(other.maPhieu))
			return false;
		return true;
	}
}
