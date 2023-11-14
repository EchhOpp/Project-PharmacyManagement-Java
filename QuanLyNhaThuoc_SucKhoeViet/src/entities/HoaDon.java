package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDon {
	// Các thuộc tính
	private String maHD;
	private KhachHang khachHang;
	private NhanVien nhanVien;
	private Date ngayLapHoaDon;
	private String theBaoHiem;
	private float mienGiam;
	private List<ChiTietHoaDon> danhSachChiTietHoaDon;

	//Các getter/setter
	public String getMaHD() {
		return maHD;
	}

	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
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

	// Các constructors
	public HoaDon() {
		this("", new KhachHang(), new NhanVien(), new Date(), "", 0f);
	}

	public HoaDon(String maHD) {
		this(maHD, new KhachHang(), new NhanVien(), new Date(), "", 0f);
	}
	
	public HoaDon(String maHD, KhachHang khachHang, NhanVien nhanVien, Date ngayLapHoaDon, String theBaoHiem,
			float mienGiam) {
		setMaHD(maHD);
		setKhachHang(khachHang);
		setNhanVien(nhanVien);
		setNgayLapHoaDon(ngayLapHoaDon);
		setTheBaoHiem(theBaoHiem);
		setMienGiam(mienGiam);
		danhSachChiTietHoaDon = new ArrayList<ChiTietHoaDon>();
	}
	
	public boolean themMotChiTietHoaDon(ChiTietHoaDon cthd) {
		if (danhSachChiTietHoaDon.contains(cthd)) {
			return false;
		}
		return danhSachChiTietHoaDon.add(cthd);
	}
	
	public boolean xoaMotChiTietHoaDon(int index) {
		if (index >= 0 && index < danhSachChiTietHoaDon.size()) {
			danhSachChiTietHoaDon.remove(index);
			return true;
		}
		return false;
	}
	
	public boolean capNhatSoLuongBan(String maThuoc, int soLuongBanMoi) {
		for (ChiTietHoaDon cthd : danhSachChiTietHoaDon) {
			if (cthd.getThuoc().getMaThuoc().equalsIgnoreCase(maThuoc)) {
				cthd.setSoLuong(soLuongBanMoi);
				return true;
			}
		}
		return false;
	}
	
	public float tinhTongThanhTien() {
		float tongThanhTien = 0;
		for (ChiTietHoaDon cthd : danhSachChiTietHoaDon) {
			float thanhTien = cthd.getDonGiaBan() * cthd.getSoLuong();
			tongThanhTien += thanhTien;
		}
		return tongThanhTien;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maHD == null) ? 0 : maHD.hashCode());
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
		HoaDon other = (HoaDon) obj;
		if (maHD == null) {
			if (other.maHD != null)
				return false;
		} else if (!maHD.equals(other.maHD))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [maHD=" + maHD + ", khachHang=" + khachHang + ", nhanVien=" + nhanVien + ", ngayLapHoaDon="
				+ ngayLapHoaDon + ", theBaoHiem=" + theBaoHiem + ", mienGiam=" + mienGiam + ", danhSachChiTietHoaDon=" + danhSachChiTietHoaDon
				+ "]";
	}
}
