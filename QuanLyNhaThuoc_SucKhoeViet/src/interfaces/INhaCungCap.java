package interfaces;

import java.util.ArrayList;

import entities.NhaCungCap;

public interface INhaCungCap {
	public ArrayList<NhaCungCap> layTatCaTTNhaCungCap();
	public boolean themNhaCungCap();
	public boolean suaNhaCungCap();
	public boolean xoaNhaCungCap();
}

