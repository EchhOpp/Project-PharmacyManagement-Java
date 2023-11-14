package entities;

public class Thuoc {
	// Các thuộc tính
	private String maThuoc;
	private String tenThuoc;
	private String loaiThuoc;
	private String donViThuoc;
	private String xuatXu;
	private int soLuongTon;
	private NhaCungCap nhaCungCap;

	// Các getter/setter
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

	public String getDonViThuoc() {
		return donViThuoc;
	}

	public void setDonViThuoc(String donViThuoc) {
		this.donViThuoc = donViThuoc;
	}

	public String getXuatXu() {
		return xuatXu;
	}

	public void setXuatXu(String xuatXu) {
		this.xuatXu = xuatXu;
	}

	public int getSoLuongTon() {
		return soLuongTon;
	}

	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}

	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	// Các constructors
	public Thuoc() {
		this("", "", "", "", "", 0, new NhaCungCap());
	}

	public Thuoc(String maThuoc) {
		this(maThuoc, "", "", "", "", 0, new NhaCungCap());
	}

	public Thuoc(String maThuoc, String tenThuoc, String loaiThuoc, String donViThuoc, String xuatXu, int soLuongTon,
			NhaCungCap nhaCungCap) {
		setMaThuoc(maThuoc);
		setTenThuoc(tenThuoc);
		setLoaiThuoc(loaiThuoc);
		setDonViThuoc(donViThuoc);
		setXuatXu(xuatXu);
		setSoLuongTon(soLuongTon);
		setNhaCungCap(nhaCungCap);
	}

	@Override
	public String toString() {
		return "Thuoc [maThuoc=" + maThuoc + ", tenThuoc=" + tenThuoc + ", loaiThuoc=" + loaiThuoc + ", donViThuoc="
				+ donViThuoc + ", xuatXu=" + xuatXu + ", soLuongTon=" + soLuongTon + ", nhaCungCap=" + nhaCungCap + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maThuoc == null) ? 0 : maThuoc.hashCode());
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
		Thuoc other = (Thuoc) obj;
		if (maThuoc == null) {
			if (other.maThuoc != null)
				return false;
		} else if (!maThuoc.equals(other.maThuoc))
			return false;
		return true;
	}
}
