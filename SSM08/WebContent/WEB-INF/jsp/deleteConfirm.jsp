<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注销</title>
    <!-- for-mobile-apps -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <!-- //for-mobile-apps -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all" />

</head>
<body>
	<div style="float:right;"><a href="register.jsp">注册</a></div>
    <!-- main -->
    <div class="main">
        <h1>
            	请确认注销注销账号！
        </h1>
        <form action="${pageContext.request.contextPath}/user/deleteUser.do" method="post">
            <p>账号</p>
            <input type="text" value="${currentUser.userName }"  name="userName" readonly  unselectable="on"/>
            <font color="red" >${errorMsg}</font>
            <input type="password" value="${password }" name="password" />
            <input type="submit" value="确认">
        </form>
        
    </div>
</body>
</html>