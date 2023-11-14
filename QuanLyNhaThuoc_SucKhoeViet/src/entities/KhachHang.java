package entities;

import java.util.Date;

public class KhachHang {
	// Các thuộc tính
	private String hoTenKhachHang;
	private Date ngaySinh;
	private String gioiTinh;
	private String soCMND;
	private String email;
	private String soDienThoai;
	private String diaChi;
	private String maKH;
	private Date ngayDangKy;
	private String maTheBaoHiem;

	// Các getter/setter
	public String getHoTenKhachHang() {
		return hoTenKhachHang;
	}

	public void setHoTenKhachHang(String hoTenKhachHang) {
		this.hoTenKhachHang = hoTenKhachHang;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getSoCMND() {
		return soCMND;
	}

	public void setSoCMND(String soCMND) {
		this.soCMND = soCMND;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public Date getNgayDangKy() {
		return ngayDangKy;
	}

	public void setNgayDangKy(Date ngayDangKy) {
		this.ngayDangKy = ngayDangKy;
	}

	public String getMaTheBaoHiem() {
		return maTheBaoHiem;
	}

	public void setMaTheBaoHiem(String maTheBaoHiem) {
		this.maTheBaoHiem = maTheBaoHiem;
	}

	// Các constructors
	public KhachHang() {
		this("", new Date(), "", "", "", "", "", "", new Date(), "");
	}

	public KhachHang(String maKH) {
		this("", new Date(), "", "", "", "", "", maKH, new Date(), "");
	}

	public KhachHang(String hoTenKhachHang, Date ngaySinh, String gioiTinh, String soCMND, String email,
			String soDienThoai, String diaChi, String maKH, Date ngayDangKy, String maTheBaoHiem) {
		setHoTenKhachHang(hoTenKhachHang);
		setNgaySinh(ngaySinh);
		setGioiTinh(gioiTinh);
		setSoCMND(soCMND);
		setEmail(email);
		setSoDienThoai(soDienThoai);
		setDiaChi(diaChi);
		setMaKH(maKH);
		setNgayDangKy(ngayDangKy);
		setMaTheBaoHiem(maTheBaoHiem);
	}

	@Override
	public String toString() {
		return "KhachHang [hoTenKhachHang=" + hoTenKhachHang + ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh
				+ ", soCMND=" + soCMND + ", email=" + email + ", soDienThoai=" + soDienThoai + ", diaChi=" + diaChi
				+ ", maKH=" + maKH + ", ngayDangKy=" + ngayDangKy + ", maTheBaoHiem=" + maTheBaoHiem + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maKH == null) ? 0 : maKH.hashCode());
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
		KhachHang other = (KhachHang) obj;
		if (maKH == null) {
			if (other.maKH != null)
				return false;
		} else if (!maKH.equals(other.maKH))
			return false;
		return true;
	}
}
