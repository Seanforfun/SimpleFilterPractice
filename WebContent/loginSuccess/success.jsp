<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Success</title>
</head>
<body>
	${user.username } login success!<br>
	<a href="${pageContext.request.contextPath }/book_add">book add</a><br>
	<a href="${pageContext.request.contextPath }/book_update">book update</a><br>
	<a href="${pageContext.request.contextPath }/book_delete">book delete</a><br>
	<a href="${pageContext.request.contextPath }/book_search">book search</a><br>
</body>
</html>