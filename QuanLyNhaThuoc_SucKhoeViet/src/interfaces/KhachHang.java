package interfaces;

import java.util.ArrayList;

public interface KhachHang {
	 public  ArrayList<KhachHang> getAllKhachHang();
	 public  ArrayList<KhachHang> findKhachHang();
	 public  boolean addKhachHang();
	 public  boolean updateKhachHang();
	 public  boolean deleteKhachHang();
}
