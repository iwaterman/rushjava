package org.memory.h2;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.h2.tools.RunScript;

public class H2SqlFile {

	static public void main(String[] args) throws Exception {
		String pwd = new File(".").getCanonicalPath();
		String dbPath = pwd + "\\jfx-jxc";

		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:" + dbPath, "sa", "");

		RunScript.execute(conn, new FileReader("test.sql"));

		// add application code here
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM TEST ");
		while (rs.next()) {
			System.out.println(rs.getInt("ID") + "," + rs.getString("NAME"));
		}
		conn.close();
	}
}