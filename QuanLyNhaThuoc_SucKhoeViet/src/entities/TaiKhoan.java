package entities;

import java.util.Arrays;
import java.util.Date;

public class TaiKhoan {
	private String matKhau;
	private byte[] hinhAnh;
	private String trangThai;
	public TaiKhoan(String matKhau, byte[] hinhAnh, String trangThai) {
		super();
		this.matKhau = matKhau;
		this.hinhAnh = hinhAnh;
		this.trangThai = trangThai;
	}
	public TaiKhoan() {
		this("", new byte[] {}, "");
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public byte[] getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(byte[] hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	@Override
	public String toString() {
		return "TaiKhoan [matKhau=" + matKhau + ", hinhAnh=" + Arrays.toString(hinhAnh) + ", trangThai=" + trangThai
				+ "]";
	}
	
}
