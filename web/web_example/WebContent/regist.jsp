<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Security Booting LogViewer</title>

<style type="text/css">
#error {
	color: red;
}
</style>
<%
	//request스코프에 담긴 오류메시지 얻어오기.
	String errMsg = (String) request.getAttribute("errMsg");
	if (errMsg == null) {
		errMsg = "";
	}
%>

<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1 user-scalable=no">
<link
	href='http://fonts.googleapis.com/css?family=Quattrocento+Sans:400,700'
	rel='stylesheet' type='text/css' />
<link href='./css/common.css' rel='stylesheet' type='text/css' />
<link href='./css/pc.css' rel='stylesheet' type='text/css' />
<link href='./css/phone.css' rel='stylesheet' type='text/css' />
<script type="text/javascript" src="./js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="./js/common.js"></script>
</head>
<body>
	<div class="container">
		<nav class="sidebar">
		<div class="side side-center clock">
			<div class="row-md-4"></div>
			<div class="row-md-4">
				<span class="time time-0"></span> <span class="time time-0"></span>
				<span class="time time-slide"></span> <span class="time time-0"></span>
				<span class="time time-0"></span>
			</div>
			<div class="row-md-2"></div>
		</div>
		<div class="side side-center">
			<button type="button" class="btn btn-login btn-long-orange"
				data-href="login.jsp">Sign In</button>
		</div>
		<div class="side side-center">
			<button type="button" class="btn btn-login btn-long-orange"
				data-href="regist.jsp">Sign Up</button>
		</div>

		</nav>
		<div class="main">
			<h1 class="title">Sign Up</h1>
			<form class="form-regist" action="Regist.do" method="post">
				<span style="display: inline-block; width: 80px">아이디</span><input
					type="text" class="form-input-text" name="userId" value=""
					placeholder="" />
				<% if (!(errMsg.equals("") || errMsg == null)) %>
						<div id ="error"><%=errMsg %></div>
					<br> <span
					style="display: inline-block; width: 80px">비밀번호</span><input
					type="password" class="form-input-text" name="userPwd" value=""
					placeholder="" /><br> <br> <span
					style="display: inline-block; width: 80px">Mac Address</span><input
					type="text" class="form-input-text" name="userMac" value=""
					placeholder="XX:XX:XX:XX:XX:XX" /><br> <br>
				<button type="submit" class="btn btn-login">Commit</button>

			</form>
		</div>
	</div>
</body>
</html>
