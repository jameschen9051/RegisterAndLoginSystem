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
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all" />
	<script type="text/javascript">
	
	</script>
</head>
<body>
    <!-- main -->
    <div class="main">
        <h1>
            	用户登录
        </h1>
        <form action="${pageContext.request.contextPath}/user/login.do" method="post">
        	<font color="red" >${errorMsg1}</font>
            <input type="text" value="${userName }"  name="userName" />
            <font color="red" >${errorMsg2}</font>
            <input type="password" value="${password }" name="password" />
            <img src="${pageContext.request.contextPath}/images/yanzhengma/${a}.jpg" alt="验证码" width="20" height="20">
            <img src="${pageContext.request.contextPath}/images/yanzhengma/${b}.jpg" alt="验证码" width="20" height="20">
            <img src="${pageContext.request.contextPath}/images/yanzhengma/${c}.jpg" alt="验证码" width="20" height="20">
            <img src="${pageContext.request.contextPath}/images/yanzhengma/${d}.jpg" alt="验证码" width="20" height="20">
            <font color="red" >${errorMsg3}</font>
            <input type="text" name="userInput" />
            <input type="submit" value="登录" >
        </form>
    </div>
    <p align=center><a href="${pageContext.request.contextPath}/user/preReg.do"><font color="red">还没有账号？点这里注册</font></a></p>
</body>
</html>