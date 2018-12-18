<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
</head>
<body style="text-align:center">
	注册成功！您的用户名：${currentUser.userName }<br/>
	<a href="${pageContext.request.contextPath}/user/preLogin.do">前往登录</a>
</body>
</html>