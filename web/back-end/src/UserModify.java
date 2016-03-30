package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.database.UserEntity;
import model.database.UsersDBCP;

/**
 * Servlet implementation class UserModify
 */
@WebServlet("/UserModify")
public class UserModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	* @see HttpServlet#HttpServlet()
	*/
    public UserModify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("userId");
		String pwd = request.getParameter("userPwd");
		String mac = request.getParameter("userMac");
		
		UsersDBCP u_dbcp = new UsersDBCP();
		UserEntity user = new UserEntity();
		user.setId(id);
		user.setPwd(pwd);
		user.setMac(mac);
		if(u_dbcp.updateDB(user)) {
			RequestDispatcher rd = request.getRequestDispatcher("viewLog.jsp");
			rd.forward(request, response);
		} else {
			request.setAttribute("errMsg", "수정을 실패 했습니다.");
			RequestDispatcher rd = request.getRequestDispatcher("modify.jsp");
			rd.forward(request, response);
		}
	}

	/**
	* @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
