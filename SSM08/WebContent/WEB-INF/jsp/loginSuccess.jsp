<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
</head>
<body>
	<div style="float:right;">
	登录成功！用户名:${currentUser.userName }
	<a href="${pageContext.request.contextPath}/user/exit.do">退出登录</a>
	<a href="${pageContext.request.contextPath}/user/changePassword.do">修改密码</a>
	<a href="${pageContext.request.contextPath}/user/deleteConfirm.do">注销账号</a>
	</div>	
</body>
</html>