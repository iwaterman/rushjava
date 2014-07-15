package com.mybatis3.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mybatis3.domain.Student;

public class OldStudentService {

	public Student findStudentById(int studId) {
		Student student = null;
		Connection conn = null;
		try {
			// obtain connection
			conn = getDatabaseConnection();
			String sql = "SELECT * FROM STUDENTS WHERE STUD_ID=?";
			// create PreparedStatement
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// set input parameters
			pstmt.setInt(1, studId);
			ResultSet rs = pstmt.executeQuery();
			// fetch results from database and populate into Java objects
			if (rs.next()) {
				student = new Student();
				student.setStudId(rs.getInt("stud_id"));
				student.setName(rs.getString("name"));
				student.setEmail(rs.getString("email"));
				student.setDob(rs.getDate("dob"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// close connection
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return student;
	}

	public void createStudent(Student student)
	{
	Connection conn = null;
	try{
	//obtain connection
	conn = getDatabaseConnection();
	String sql = "INSERT INTO STUDENTS(STUD_ID,NAME,EMAIL,DOB)	VALUES(?,?,?,?)";
	//create a PreparedStatement
	PreparedStatement pstmt = conn.prepareStatement(sql);
	//set input parameters
	pstmt.setInt(1, student.getStudId());
	pstmt.setString(2, student.getName());
	pstmt.setString(3, student.getEmail());
	pstmt.setDate(4, new
	java.sql.Date(student.getDob().getTime()));
	pstmt.executeUpdate();
	} catch (SQLException e){
	throw new RuntimeException(e);
	}finally{
	//close connection
	if(conn!= null){
	try {
	conn.close();
	} catch (SQLException e){ }
	}
	}
	}

	protected Connection getDatabaseConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "admin");
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
