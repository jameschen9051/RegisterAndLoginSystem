<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>登录</title>
    <!-- for-mobile-apps -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <!-- //for-mobile-apps -->
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />

</head>
<body>
    <!-- main -->
    <div class="main">
        <h1>
            	首页
        </h1>
        <form action="${pageContext.request.contextPath}/user/preLogin.do" method="post">
            <input type="submit" value="登录">
        </form>
        <p></p><br/>
        <form action="${pageContext.request.contextPath}/user/preReg.do" method="post">
            <input type="submit" value="注册">
        </form>
    </div>
</body>
</html>