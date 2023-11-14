package entities;

public class ChiTietHoaDon {
	// Các thuộc tính
	private Thuoc thuoc;
	private int soLuong;
	private float donGiaBan;

	// Các getter/setter
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

	public ChiTietHoaDon(Thuoc thuoc) {
		this(thuoc, 0, 0f);
	}

	// Các constructors
	public ChiTietHoaDon() {
		this(new Thuoc(), 0, 0f);
	}

	public ChiTietHoaDon(Thuoc thuoc, int soLuong, float donGiaBan) {
		setThuoc(thuoc);
		setSoLuong(soLuong);
		setDonGiaBan(donGiaBan);
	}

	@Override
	public String toString() {
		return "OrderDetail [thuoc=" + thuoc + ", soLuong=" + soLuong + ", donGiaBan="
				+ donGiaBan + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((thuoc == null) ? 0 : thuoc.hashCode());
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
		ChiTietHoaDon other = (ChiTietHoaDon) obj;
		if (thuoc == null) {
			if (other.thuoc != null)
				return false;
		} else if (!thuoc.equals(other.thuoc))
			return false;
		return true;
	}
}
