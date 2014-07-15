package org.memory.h2;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class App {
	public static void main(String[] args) throws Exception {
		
		File dot = new File(".");
		String dbPath = dot.getCanonicalPath() + "\\jfx-jxc";
		
		System.out.println(dbPath);
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:" + dbPath, "sa", "");
		// add application code here
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM TEST ");
		while (rs.next()) {
			System.out.println(rs.getInt("ID") + "," + rs.getString("NAME"));
		}
		conn.close();
	}
}
