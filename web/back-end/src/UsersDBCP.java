癤퓈ackage model.database;

import java.sql.*; 
import java.util.*; 

import javax.sql.*; 
import javax.naming.*; 

//DBCP瑜??댁슜???뚯씠釉?USERS 泥섎━ ?곗씠?곕쿋?댁뒪 ?곕룞 ?먮컮鍮덉쫰 ?꾨줈洹몃옩
public class UsersDBCP extends DBCP 
{
	// ?곗씠?곕쿋?댁뒪 ?곌껐愿??蹂???좎뼵
	private PreparedStatement pstmt = null;

	// JDBC ?쒕씪?대쾭 濡쒕뱶 硫붿냼??
	public UsersDBCP() {
		super();
	}
	
	// ?곗씠?곕쿋?댁뒪 ?곌껐 ?ㅼ젣 硫붿냼??
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
	
	// ?ъ슜?먯쓽 紐⑤뱺 ?덉퐫?쒕? 諛섑솚 硫붿꽌??
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
				//由ъ뒪?몄뿉 異붽?
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

	// 二???id???덉퐫?쒕? 諛섑솚?섎뒗 硫붿꽌??
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

	// ?곗씠???깅줉 硫붿꽌??
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
	
	// ?곗씠??媛깆떊???꾪븳 硫붿꽌??
	public boolean updateDB(UserEntity user) {
		boolean success = false; 
		connect();		
		String sql ="update users set pwd=?, mac=? where id=?";	
		try {
			pstmt = con.prepareStatement(sql);
			// ?몄옄濡?諛쏆? GuestBook 媛앹껜瑜??댁슜???ъ슜?먭? ?섏젙??媛믪쓣 媛?몄? SQL臾??꾩꽦
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
	
	// ?곗씠???섏젙???꾪븳 硫붿꽌??
	public boolean deleteDB(String id) {
		boolean success = false; 
		connect();		
		String sql ="delete from users where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			// ?몄옄濡?諛쏆? 二??ㅼ씤 id 媛믪쓣 ?댁슜????젣
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

	// ?곗씠?곕쿋?댁뒪?먯꽌 ?몄옄??id媛 議댁옱?섎뒗吏 寃?ы븯??硫붿냼??
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
	
	// ?곗씠?곕쿋?댁뒪?먯꽌 ?몄옄??id? passwd媛 ?쇱튂?섎뒗吏 寃?ы븯?? 硫붿꽌??
	public boolean isPasswd(String id, String pwd) {
		boolean success = false;
		connect();		
		String sql ="select pwd from users where id=?";
		try {
			
			pstmt = con.prepareStatement(sql);
			System.out.println(sql);
			pstmt.setString(1, id);
			System.out.println(sql);
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
