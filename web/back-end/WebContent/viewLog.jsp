<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>Security Booting LogViewer</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1 user-scalable=no">

    <link href='http://fonts.googleapis.com/css?family=Quattrocento+Sans:400,700' rel='stylesheet' type='text/css' />
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
            <span class="time time-0"></span>
            <span class="time time-0"></span>
            <span class="time time-slide"></span>
            <span class="time time-0"></span>
            <span class="time time-0"></span>
          </div>
          <div class="row-md-2"></div>
        </div>
        <div class="side side-center">
          <%
          	String userId = (String)session.getAttribute("userId");
          	out.println(userId + "님 . 반갑습니다.");
          %>
        </div>
        <div class="side side-center">
          <button type="button" class="btn btn-login btn-long-orange" data-href="processLogout.jsp">
          	Sign Out
          </button>
        </div>
        <div class="side side-left side-menu">
          <ul>
            <li class="">
              <span class="icon-user-profile"></span> <a href="viewLog.jsp">로그관리</a>
              <ul>
                <li>+ <a href="viewLog.jsp">로그조회</a></li>
              </ul>
            </li>
            <li class="">
              <span class="icon-product"></span> <a href="modify.jsp">회원관리</a>
              <ul>
                <li>+ <a href="modify.jsp">정보수정</a></li>
              </ul>
            </li>
          </ul>
          <!--<hr class="line-gray" /> -->
        </div>
		<!--
        <div class="side side-center side-bottom">
          <button type="button" class="btn btn-short-rect"><span class="icon-user-setting"></span></button>
          &nbsp;
          <button type="button" class="btn btn-short-rect"><span class="icon-send-mail"></span></button>
        </div>
		-->
      </nav>
      
  	  <%@ page
			import="java.util.ArrayList, model.database.LogEntity, java.text.SimpleDateFormat"%>
		<jsp:useBean id="logdb" class="model.database.LogDHCP" scope="page" />
		<%
			//로그 배열리스트를 자바빈즈를 이용하여 얻는다.
			ArrayList<LogEntity> list = logdb.getLogList();
			int counter = list.size();
			int row = 0;

			if (counter > 0) {
		%>
      
      <div class="main">
        <h1 class="title">User's Logs</h1>
        
		<table class="responstable">
			
			<tr>
				<th>
					#
				</th>
				<th>
					날짜/시간
				</th>
				<th>
					ip
				</th>
			</tr>
		
			<%
				//게시 등록일을 2010-3-15 10:33:21 형태로 출력하기 위한 클래스 
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					for (LogEntity log : list) {
			%>
		
			<tr>
				<td>
					<%=log.getId() %>
				</td>
				<td>
					<%=log.getRegdate() %>
				</td>
				<td>
					<%=log.getIp() %>
				</td>
			</tr>
			<%
				}
			%>
		</table>
		<%
			}
		%>
		
      </div>
    </div>
  </body>
</html>
