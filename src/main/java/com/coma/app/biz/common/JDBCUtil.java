package com.coma.app.biz.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUtil {
//	private static final String driverName="oracle.jdbc.driver.OracleDriver";
//	private static final String url="jdbc:oracle:thin:@localhost:1521:xe";
//	private static final String userName="COMA"; 
//	private static final String password="1234";
	
	private static final String driverName="com.mysql.cj.jdbc.Driver";
	private static final String url="jdbc:mysql://root@localhost:3306/coma";
	private static final String userName="root"; 
	private static final String password="1234";
	
	
	public static Connection connect() {
		Connection conn=null;
			try {
				Class.forName(driverName);
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드 실패");
			} finally {
				System.out.println("model.JDBCUtil.driver load 진행");
				
			}
		
		try {
			conn=DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			System.err.println("conn 연결 실패");
		}finally {
			System.out.println("model.JDBCUtil.connection 진행");
		}
		return conn;
	}
	
	public static void disconnect(PreparedStatement pstmt, Connection conn) {
		try {
			pstmt.close();
			conn.close();	
		} catch (SQLException e) {
			System.err.println("연결 해제 실패");
		}finally {
			System.out.println("model.JDBCUtil.close 진행");
		}		
	}
}
