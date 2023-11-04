package entities;

import java.util.Date;
import java.util.Objects;

public class NhanVien {
	private String maNV;
	private String hoTenNhanVien;
	private Date ngaySinh;
	private String gioiTinh;
	private String soCMND;
	private String email;
	private String soDienThoai;
	private String diaChi;
	private Date ngayVaoLam;
	private String chucVu;
	private String tinhTrang;
	private TaiKhoan taiKhoan;
	public NhanVien(String maNV, String hoTenNhanVien, Date ngaySinh, String gioiTinh, String soCMND, String email,
			String soDienThoai, String diaChi, Date ngayVaoLam, String chucVu, String tinhTrang, TaiKhoan taiKhoan) {
		super();
		this.maNV = maNV;
		this.hoTenNhanVien = hoTenNhanVien;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.soCMND = soCMND;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.diaChi = diaChi;
		this.ngayVaoLam = ngayVaoLam;
		this.chucVu = chucVu;
		this.tinhTrang = tinhTrang;
		this.taiKhoan = taiKhoan;
	}
	public NhanVien(String maNV) {
		this(maNV, "ho ten", new Date(), "gioi tinh", "cmnd", "email","sdt","dia chi",new Date(),"chuc vu","tinh trang",new TaiKhoan());
	}
	public NhanVien() {
		this("ma nhan vien");
	}
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
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
	@Override
	public int hashCode() {
		return Objects.hash(chucVu, diaChi, email, gioiTinh, hoTenNhanVien, maNV, ngaySinh, ngayVaoLam, soCMND,
				soDienThoai, taiKhoan, tinhTrang);
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
		return Objects.equals(chucVu, other.chucVu) && Objects.equals(diaChi, other.diaChi)
				&& Objects.equals(email, other.email) && Objects.equals(gioiTinh, other.gioiTinh)
				&& Objects.equals(hoTenNhanVien, other.hoTenNhanVien) && Objects.equals(maNV, other.maNV)
				&& Objects.equals(ngaySinh, other.ngaySinh) && Objects.equals(ngayVaoLam, other.ngayVaoLam)
				&& Objects.equals(soCMND, other.soCMND) && Objects.equals(soDienThoai, other.soDienThoai)
				&& Objects.equals(taiKhoan, other.taiKhoan) && Objects.equals(tinhTrang, other.tinhTrang);
	}
	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", hoTenNhanVien=" + hoTenNhanVien + ", ngaySinh=" + ngaySinh + ", gioiTinh="
				+ gioiTinh + ", soCMND=" + soCMND + ", email=" + email + ", soDienThoai=" + soDienThoai + ", diaChi="
				+ diaChi + ", ngayVaoLam=" + ngayVaoLam + ", chucVu=" + chucVu + ", tinhTrang=" + tinhTrang
				+ ", taiKhoan=" + taiKhoan + "]";
	}
	
}
