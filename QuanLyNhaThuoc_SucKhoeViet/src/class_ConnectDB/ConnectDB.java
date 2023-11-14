package class_ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection con = null;
	private static ConnectDB instance = new ConnectDB();

	public static ConnectDB getInstance() {
		return instance;
	}

	public static void connect() {
		try {
			String url = "jdbc:sqlserver://localhost:1433;databasename=QuanLyQuayThuocBenhVienTest";
			String user = "sa";
			String password = "sapassword";
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (con == null) {
			connect();
		}
		return con;
	}
}
