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
 * Servlet implementation class UserRegist
 */
@WebServlet("/UserRegist")
public class UserRegist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
	* @see HttpServlet#HttpServlet()
	*/
    public UserRegist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("userId");
		String pwd = request.getParameter("userPwd");
		String mac = request.getParameter("userMac");
		
		UsersDBCP u_dbcp = new UsersDBCP();
		if(u_dbcp.isId(id)){
			//request에 오류메시지 담기.
			request.setAttribute("errMsg", "같은 아이디가 존재합니다.");
			RequestDispatcher rd = request.getRequestDispatcher("regist.jsp");
			rd.forward(request, response);
		} else{
			UserEntity user = new UserEntity();
			user.setId(id);
			user.setPwd(pwd);
			user.setMac(mac);
			if(u_dbcp.insertDB(user)){
				HttpSession session = request.getSession();
				session.setAttribute("userId", id);
				response.sendRedirect("viewLog.jsp");
			} else{
				request.setAttribute("errMsg", "사용자 등록에 실패했습니다.");
				RequestDispatcher rd = request.getRequestDispatcher("regist.jsp");
				rd.forward(request, response);
			}
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
