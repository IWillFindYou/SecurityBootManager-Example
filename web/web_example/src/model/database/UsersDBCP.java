package model.database;

import java.sql.*; 
import java.util.*; 

import javax.sql.*; 
import javax.naming.*; 

//DBCP를 이용한 테이블 USERS 처리 데이터베이스 연동 자바빈즈 프로그램
public class UsersDBCP extends DBCP 
{
	// 데이터베이스 연결관련 변수 선언
	private PreparedStatement pstmt = null;

	// JDBC 드라이버 로드 메소드
	public UsersDBCP() {
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
	
	// 사용자의 모든 레코드를 반환 메서드
	public ArrayList<UserEntity> getUserList() {	
		connect();
		ArrayList<UserEntity> list = new ArrayList<UserEntity>();
		
		String SQL = "select * from users";
		try {
			pstmt = con.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				UserEntity user = new UserEntity();
				user.setId ( rs.getString("ID") );
				user.setPwd ( rs.getString("PWD") );
				user.setMac ( rs.getString("MAC") );
				//리스트에 추가
				list.add(user);
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

	// 주 키 id의 레코드를 반환하는 메서드
	public UserEntity getUser(String id) {	
		connect();
		String SQL = "select * from users where id = ?";
		UserEntity user = new UserEntity();
		
		try {
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();			
			rs.next();
			user.setId ( rs.getString("ID") );
			user.setPwd ( rs.getString("PWD") );
			user.setMac ( rs.getString("MAC") );
			rs.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			disconnect();
		}
		return user;
	}

	// 데이터 등록 메서드
	public boolean insertDB(UserEntity user) {
		boolean success = false; 
		connect();
		String sql ="insert into users values(?, ?, ?)";		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPwd());
			pstmt.setString(3, user.getMac());
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
	
	// 데이터 갱신을 위한 메서드
	public boolean updateDB(UserEntity user) {
		boolean success = false; 
		connect();		
		String sql ="update users set pwd=?, mac=? where id=?";	
		try {
			pstmt = con.prepareStatement(sql);
			// 인자로 받은 GuestBook 객체를 이용해 사용자가 수정한 값을 가져와 SQL문 완성
			pstmt.setString(1, user.getPwd());
			pstmt.setString(2, user.getMac());
			pstmt.setString(3, user.getId());
			int rowUdt = pstmt.executeUpdate();
			//System.out.println(rowUdt);
			if (rowUdt == 1) success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		}
		finally {
			disconnect();
		}
		return success;
	}
	
	// 데이터 수정을 위한 메서드
	public boolean deleteDB(String id) {
		boolean success = false; 
		connect();		
		String sql ="delete from users where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			// 인자로 받은 주 키인 id 값을 이용해 삭제
			pstmt.setString(1, id);
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

	// 데이터베이스에서 인자인 id가 존재하는지 검사하는 메소드
	public boolean isId(String id) {
		boolean success = false;
		connect();
		String sql = "select * from users where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int cnt = rs.getRow();
			if (cnt > 0)
				success = true;
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		} finally {
			disconnect();
		}
		return success;
	}
	
	// 데이터베이스에서 인자인 id와 passwd가 일치하는지 검사하는  메서드
	public boolean isPasswd(String id, String pwd) {
		boolean success = false;
		connect();		
		String sql ="select pwd from users where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String orgPasswd = rs.getString(1);
			if ( pwd.equals(orgPasswd) ) success = true; 
			//System.out.println(success);
			rs.close();			
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