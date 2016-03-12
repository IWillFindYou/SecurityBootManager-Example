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
	String errMsg = (String)request.getAttribute("errMsg");
	if(errMsg==null){
		errMsg="";
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

		<!--
        <div class="side side-center side-bottom">
          <button type="button" class="btn btn-short-rect"><span class="icon-user-setting"></span></button>
          &nbsp;
          <button type="button" class="btn btn-short-rect"><span class="icon-send-mail"></span></button>
        </div>
		--> </nav>
		<div class="main">
			<h1 class="title">User Login</h1>
			<form class="form-login" action="Login.do" method="post">
				<input type="text" class="form-input-text" name="userId" value=""
					placeholder="아이디" /> <input type="password"
					class="form-input-text" name="userPwd" value="" placeholder="비밀번호" />
				<button type="submit" class="btn btn-login">User Login</button>
				
				<%if (!(errMsg.equals("") || errMsg == null)) {%>
				<div class="info" id="error">
					<img src="./image/info.png" alt=""
						style="width: 16px; float: left;" />&nbsp;
					<%=errMsg%>
				</div>
				<%}%>
				<div class="info">
					<img src="./image/info.png" alt=""
						style="width: 16px; float: left;" />&nbsp; <u>비회원</u>이신 경우 회원가입
					페이지를 이용해 주세요.
				</div>
				<div class="info">
					<img src="./image/info.png" alt=""
						style="width: 16px; float: left;" />&nbsp; 버그 및 추가 편의 사항이 필요 할 경우
					<a href="https://github.com/IWillFindYou/SecurityBootManager/"><u><strong>SecurityBootFramework
								Team</strong></u></a>에게 말해주시기 바랍니다.
				</div>
			</form>
		</div>
	</div>
</body>
</html>
