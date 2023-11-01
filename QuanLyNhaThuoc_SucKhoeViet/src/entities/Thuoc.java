package entities;

import java.util.Objects;

public class Thuoc {
	private String maThuoc;
	private String tenThuoc;
	private String loaiThuoc;
	private String donViTinh;
	private String nuocXuatXu;
	private int soLuongTon;
	public String getMaThuoc() {
		return maThuoc;
	}
	public void setMaThuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}
	public String getTenThuoc() {
		return tenThuoc;
	}
	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}
	public String getLoaiThuoc() {
		return loaiThuoc;
	}
	public void setLoaiThuoc(String loaiThuoc) {
		this.loaiThuoc = loaiThuoc;
	}
	public String getDonViTinh() {
		return donViTinh;
	}
	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}
	public String getNuocXuatXu() {
		return nuocXuatXu;
	}
	public void setNuocXuatXu(String nuocXuatXu) {
		this.nuocXuatXu = nuocXuatXu;
	}
	public int getSoLuongTon() {
		return soLuongTon;
	}
	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}
	@Override
	public int hashCode() {
		return Objects.hash(donViTinh, loaiThuoc, maThuoc, nuocXuatXu, soLuongTon, tenThuoc);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Thuoc other = (Thuoc) obj;
		return Objects.equals(donViTinh, other.donViTinh) && Objects.equals(loaiThuoc, other.loaiThuoc)
				&& Objects.equals(maThuoc, other.maThuoc) && Objects.equals(nuocXuatXu, other.nuocXuatXu)
				&& soLuongTon == other.soLuongTon && Objects.equals(tenThuoc, other.tenThuoc);
	}
	@Override
	public String toString() {
		return "Thuoc [maThuoc=" + maThuoc + ", tenThuoc=" + tenThuoc + ", loaiThuoc=" + loaiThuoc + ", donViTinh="
				+ donViTinh + ", nuocXuatXu=" + nuocXuatXu + ", soLuongTon=" + soLuongTon + "]";
	}
	
	
}
