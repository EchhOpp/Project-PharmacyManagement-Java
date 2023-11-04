package interfaces;

import java.util.ArrayList;

public interface NhanVien {
	 public  ArrayList<NhanVien> getAllNhanVien();
	 public  ArrayList<NhanVien> findNhanVien();
	 public  boolean addNhanVien();
	 public  boolean updateNhanVien();
	 public  boolean deleteNhanVien();
}
