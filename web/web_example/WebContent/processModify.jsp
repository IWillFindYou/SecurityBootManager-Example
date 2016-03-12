<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Process Modify</title>
</head>
<body>
	<%
		String id = request.getParameter("userId");
		String pwd = request.getParameter("userPwd");
		String mac = request.getParameter("userMac");
		
		// 수정 성공 또는 실패 구현
		RequestDispatcher rd = request.getRequestDispatcher("viewLog.jsp");
		rd.forward(request, response);
	%>
</body>
</html>