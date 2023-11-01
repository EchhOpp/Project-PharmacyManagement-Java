package entities;

import java.util.Date;
import java.util.List;
import java.util.Objects;


public class HoaDon {
	private String maHoaDon;
	private Date ngayLapHoaDon;
	private String theBaoHiem;
	private float mienGiam;
	private List<ChiTietHoaDon> danhSachChiTietHoaDon;
	
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public Date getNgayLapHoaDon() {
		return ngayLapHoaDon;
	}
	public void setNgayLapHoaDon(Date ngayLapHoaDon) {
		this.ngayLapHoaDon = ngayLapHoaDon;
	}
	public String getTheBaoHiem() {
		return theBaoHiem;
	}
	public void setTheBaoHiem(String theBaoHiem) {
		this.theBaoHiem = theBaoHiem;
	}
	public float getMienGiam() {
		return mienGiam;
	}
	public void setMienGiam(float mienGiam) {
		this.mienGiam = mienGiam;
	}
	public List<ChiTietHoaDon> getDanhSachChiTietHoaDon() {
		return danhSachChiTietHoaDon;
	}
	public void setDanhSachChiTietHoaDon(List<ChiTietHoaDon> danhSachChiTietHoaDon) {
		this.danhSachChiTietHoaDon = danhSachChiTietHoaDon;
	}
	@Override
	public int hashCode() {
		return Objects.hash(danhSachChiTietHoaDon, maHoaDon, mienGiam, ngayLapHoaDon, theBaoHiem);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(danhSachChiTietHoaDon, other.danhSachChiTietHoaDon)
				&& Objects.equals(maHoaDon, other.maHoaDon)
				&& Float.floatToIntBits(mienGiam) == Float.floatToIntBits(other.mienGiam)
				&& Objects.equals(ngayLapHoaDon, other.ngayLapHoaDon) && Objects.equals(theBaoHiem, other.theBaoHiem);
	}
	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", ngayLapHoaDon=" + ngayLapHoaDon + ", theBaoHiem=" + theBaoHiem
				+ ", mienGiam=" + mienGiam + ", danhSachChiTietHoaDon=" + danhSachChiTietHoaDon + "]";
	}
	
	
}
