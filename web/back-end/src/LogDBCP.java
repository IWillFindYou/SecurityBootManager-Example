package model.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogDBCP extends DBCP 
{
	// 데이터베이스 연결관련 변수 선언
	private PreparedStatement pstmt = null;

	// JDBC 드라이버 로드 메소드
	public LogDBCP() {
		super();
	}
	
	// 데이터베이스 연결 헤제 메소드 
	public void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 게시판의 모든 레코드를 반환 메서드
	public ArrayList<LogEntity> getLogList() {	
		connect();
		ArrayList<LogEntity> list = new ArrayList<LogEntity>();
		
		String SQL = "select * from logs";
		try {
			pstmt = con.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				LogEntity log = new LogEntity();
				log.setId ( rs.getInt("ID") );
				log.setRegdate(rs.getTimestamp("REGDATE"));
				log.setIp ( rs.getString("IP") );
				log.setApSSID ( rs.getString("APSSID") );
				log.setApMAC ( rs.getString("APMAC") );
				//리스트에 추가
				list.add(log);
			}
			rs.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			disconnect();
		}
		return list;
	}
	
	public boolean insertDB(String remoteIP, String apSSID, String apMAC) {
		boolean success = false; 
		connect();
		String sql ="insert into logs values(0, sysdate(), ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, remoteIP);
			pstmt.setString(2, apSSID);
			pstmt.setString(3, apMAC);
			pstmt.executeUpdate();
			success = true; 
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		}
		finally {
			disconnect();
		}
		return success;
	}
	
}
