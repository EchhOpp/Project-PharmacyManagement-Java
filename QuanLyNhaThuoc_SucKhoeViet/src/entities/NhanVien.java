package entities;

import java.util.Date;

public class NhanVien {
	// Các thuộc tính
	private String hoTenNhanVien;
	private Date ngaySinh;
	private String gioiTinh;
	private String soCMND;
	private String email;
	private String soDienThoai;
	private String diaChi;
	private String maNV;
	private Date ngayVaoLam;
	private String chucVu;
	private String tinhTrang;
	private TaiKhoan taiKhoan;

	// Các getter/setter
	public String getHoTenNhanVien() {
		return hoTenNhanVien;
	}

	public void setHoTenNhanVien(String hoTenNhanVien) {
		this.hoTenNhanVien = hoTenNhanVien;
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

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}

	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public String getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	// Các constructors
	public NhanVien() {
		this("", new Date(), "", "", "", "", "", "", new Date(), "", "", new TaiKhoan());
	}

	public NhanVien(String maNV) {
		this("", new Date(), "", "", "", "", "", maNV, new Date(), "", "", new TaiKhoan());
	}
	
	public NhanVien(String hoTenNhanVien, Date ngaySinh, String gioiTinh, String soCMND, String email,
			String soDienThoai, String diaChi, String maNV, Date ngayVaoLam, String chucVu, String tinhTrang,
			TaiKhoan taiKhoan) {
		setHoTenNhanVien(hoTenNhanVien);
		setNgaySinh(ngaySinh);
		setGioiTinh(gioiTinh);
		setSoCMND(soCMND);
		setEmail(email);
		setSoDienThoai(soDienThoai);
		setDiaChi(diaChi);
		setMaNV(maNV);
		setNgayVaoLam(ngayVaoLam);
		setChucVu(chucVu);
		setTinhTrang(tinhTrang);
		setTaiKhoan(taiKhoan);
	}

	@Override
	public String toString() {
		return "NhanVien [hoTenNhanVien=" + hoTenNhanVien + ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh
				+ ", soCMND=" + soCMND + ", email=" + email + ", soDienThoai=" + soDienThoai + ", diaChi=" + diaChi
				+ ", maNV=" + maNV + ", ngayVaoLam=" + ngayVaoLam + ", chucVu=" + chucVu + ", tinhTrang=" + tinhTrang
				+ ", taiKhoan=" + taiKhoan + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maNV == null) ? 0 : maNV.hashCode());
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
		NhanVien other = (NhanVien) obj;
		if (maNV == null) {
			if (other.maNV != null)
				return false;
		} else if (!maNV.equals(other.maNV))
			return false;
		return true;
	}
}
