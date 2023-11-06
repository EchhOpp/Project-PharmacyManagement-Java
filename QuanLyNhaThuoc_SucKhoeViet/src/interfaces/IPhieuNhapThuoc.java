package interfaces;

import java.util.ArrayList;

import entities.PhieuNhapThuoc;

public interface IPhieuNhapThuoc {
	public ArrayList<PhieuNhapThuoc> layTatCaTTPhieu();
	public boolean themPhieuNhapThuoc();
	public boolean suaPhieuNhapThuoc();
	public boolean xoaPhieuNhapThuoc();
}

