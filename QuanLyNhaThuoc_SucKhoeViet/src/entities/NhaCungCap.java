package entities;

import java.util.Objects;

public class NhaCungCap {
	private String maNhaCungCap;
	private String tenNhaCungCap;
	private String email;
	private String soDienThoai;
	private String diaChi;
	public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String email, String soDienThoai, String diaChi) {
		super();
		this.maNhaCungCap = maNhaCungCap;
		this.tenNhaCungCap = tenNhaCungCap;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.diaChi = diaChi;
	}
	public NhaCungCap(String maNhaCungCap) {
		super();
		this.maNhaCungCap = maNhaCungCap;
	}
	public NhaCungCap() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(maNhaCungCap);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhaCungCap other = (NhaCungCap) obj;
		return Objects.equals(maNhaCungCap, other.maNhaCungCap);
	}
	public String getMaNhaCungCap() {
		return maNhaCungCap;
	}
	public void setMaNhaCungCap(String maNhaCungCap) {
		this.maNhaCungCap = maNhaCungCap;
	}
	public String getTenNhaCungCap() {
		return tenNhaCungCap;
	}
	public void setTenNhaCungCap(String tenNhaCungCap) {
		this.tenNhaCungCap = tenNhaCungCap;
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
	@Override
	public String toString() {
		return "NhaCungCap [maNhaCungCap=" + maNhaCungCap + ", tenNhaCungCap=" + tenNhaCungCap + ", email=" + email
				+ ", soDienThoai=" + soDienThoai + ", diaChi=" + diaChi + "]";
	}
	
	
}

