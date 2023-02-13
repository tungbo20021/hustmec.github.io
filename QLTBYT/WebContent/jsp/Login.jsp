<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Đăng nhập hệ thống</title>
<!-- BootStrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
	<!-- Font awesome -->
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<!-- Font google -->
		<link
			href="https://fonts.googleapis.com/css?family=Nunito&display=swap"
			rel="stylesheet">
			<!-- Link file CSS -->
<!-- 			<link href="css/style.css" rel="stylesheet"> -->
			<style>
input[type=text], select {
/*   margin: 8px 0; */
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

input[type=submit] {
  background-color: #4CAF50;
  color: white;
/*   margin: 8px 0; */
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}
}
</style>
				<!-- Link file JS -->
</head>
<body id="page-top">
<br></br>
<br></br>

	<form action="Login.do" method="post">
		<center>
			<table class="tbl_input" cellpadding="4" cellspacing="0"
				width="400px">
				<tbody>
					<c:forEach items="${listErrorMess}" var="errorMess">
						<tr>
							<td class="errMsg" colspan="2">${errorMess}</td>
						</tr>
					</c:forEach>
					<tr>
						<td class="lbl_left" style="font-size: 16px;"><img
							src="img/logo1.png" alt="Logo"
							style="width: 120px; height: 120px;"></img></td>
						<td align="left"><h2 style="font-size: 25px;">QUẢN LÍ
								THIẾT BỊ Y TẾ</h2></td>

					</tr>
					<tr align="left">
						<td class="lbl_left" style="font-size: 16px;">Tên đăng nhập:</td>
						<td align="left"><input class="txBox" type="text"
							name="loginName" value="" autofocus <c:out value="${loginName}"/>
							size="30"></td>
					</tr>
					<tr>
						<td class="lbl_left" style="font-size: 16px;">Mật khẩu:</td>
						<td align="left"><input class="txBox" type="password"
							name="password" value="" size="30"></td>
					</tr>
					<tr>
						<td></td>
						<td align="left"><input class="btn btn-danger" type="submit"
							value="Đăng nhập"></td>
					</tr>
				</tbody>
			</table>
		</center>
	</form>
</body>
</html>