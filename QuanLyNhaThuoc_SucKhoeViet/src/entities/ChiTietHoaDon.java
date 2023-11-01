package entities;

import java.util.Objects;

public class ChiTietHoaDon {
	private Thuoc thuoc;
	private int soLuong;
	private float donGiaBan;
	
	
	public ChiTietHoaDon(entities.Thuoc thuoc, int soLuong, float donGiaBan) {
		super();
		this.thuoc = thuoc;
		this.soLuong = soLuong;
		this.donGiaBan = donGiaBan;
	}

	public Thuoc getThuoc() {
		return thuoc;
	}

	public void setThuoc(Thuoc thuoc) {
		this.thuoc = thuoc;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public float getDonGiaBan() {
		return donGiaBan;
	}

	public void setDonGiaBan(float donGiaBan) {
		this.donGiaBan = donGiaBan;
	}

	@Override
	public int hashCode() {
		return Objects.hash(donGiaBan, soLuong, thuoc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietHoaDon other = (ChiTietHoaDon) obj;
		return Float.floatToIntBits(donGiaBan) == Float.floatToIntBits(other.donGiaBan) && soLuong == other.soLuong
				&& Objects.equals(thuoc, other.thuoc);
	}

	@Override
	public String toString() {
		return "ChiTietHoaDon [thuoc=" + thuoc + ", soLuong=" + soLuong + ", donGiaBan=" + donGiaBan + "]";
	}
	
	
}
