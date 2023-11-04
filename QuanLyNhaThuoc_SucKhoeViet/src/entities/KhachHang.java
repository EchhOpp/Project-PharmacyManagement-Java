package entities;

import java.util.Date;
import java.util.Objects;

public class KhachHang {
	private String maKH;
	private String hoTenKhachHang;
	private Date ngaySinh;
	private String gioiTinh;
	private String soCMND;
	private String email;
	private String soDienThoai;
	private String diaChi;
	private Date ngayDangKy;
	private String maTheBaoHiem;
	public KhachHang(String maKH, String hoTenKhachHang, Date ngaySinh, String gioiTinh, String soCMND, String email,
			String soDienThoai, String diaChi, Date ngayDangKy, String maTheBaoHiem) {
		super();
		this.maKH = maKH;
		this.hoTenKhachHang = hoTenKhachHang;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.soCMND = soCMND;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.diaChi = diaChi;
		this.ngayDangKy = ngayDangKy;
		this.maTheBaoHiem = maTheBaoHiem;
	}
	public KhachHang(String maKH) {
		this(maKH, "ho ten", new Date(), "gioi tinh", "cmnd", "email","sdt","dia chi",new Date(),"ma the bao hiem");
	}
	public KhachHang() {
		this("ma khach hang");
	}
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
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
	@Override
	public int hashCode() {
		return Objects.hash(diaChi, email, gioiTinh, hoTenKhachHang, maKH, maTheBaoHiem, ngayDangKy, ngaySinh, soCMND,
				soDienThoai);
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
		return Objects.equals(diaChi, other.diaChi) && Objects.equals(email, other.email)
				&& Objects.equals(gioiTinh, other.gioiTinh) && Objects.equals(hoTenKhachHang, other.hoTenKhachHang)
				&& Objects.equals(maKH, other.maKH) && Objects.equals(maTheBaoHiem, other.maTheBaoHiem)
				&& Objects.equals(ngayDangKy, other.ngayDangKy) && Objects.equals(ngaySinh, other.ngaySinh)
				&& Objects.equals(soCMND, other.soCMND) && Objects.equals(soDienThoai, other.soDienThoai);
	}
	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", hoTenKhachHang=" + hoTenKhachHang + ", ngaySinh=" + ngaySinh
				+ ", gioiTinh=" + gioiTinh + ", soCMND=" + soCMND + ", email=" + email + ", soDienThoai=" + soDienThoai
				+ ", diaChi=" + diaChi + ", ngayDangKy=" + ngayDangKy + ", maTheBaoHiem=" + maTheBaoHiem + "]";
	}
	
}
