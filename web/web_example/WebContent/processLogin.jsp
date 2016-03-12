<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Process Login</title>
</head>
<body>
<%
	String id=request.getParameter("userId");
	String pwd=request.getParameter("userPwd");
	
	if(id.equals("root") && pwd.equals("1234")){
		session.setAttribute("userId", id);
		response.sendRedirect("viewLog.jsp");
 	}else {
		//request에 오류메시지 담기.
		request.setAttribute("errMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}
%>
</body>
</html>