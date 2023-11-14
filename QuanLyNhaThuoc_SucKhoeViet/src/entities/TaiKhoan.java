package entities;

import java.util.Arrays;

public class TaiKhoan {
	// Các thuộc tính
	private String matKhau;
	private byte[] hinhAnh;
	private String trangThai;

	//Các getter/setter
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

	// Các constructors
	public TaiKhoan() {
		this("", new byte[] {}, "");
	}

	public TaiKhoan(String matKhau, byte[] hinhAnh, String trangThai) {
		setMatKhau(matKhau);
		setHinhAnh(hinhAnh);
		setTrangThai(trangThai);
	}

	@Override
	public String toString() {
		return "Account [matKhau=" + matKhau + ", hinhAnh=" + Arrays.toString(hinhAnh) + ", trangThai=" + trangThai
				+ "]";
	}
}
