<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Quản lí thiết bị y tế</title>
<!-- BootStrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<!-- Font awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
<!-- Font google -->
<link href="https://fonts.googleapis.com/css?family=Nunito&display=swap"
	rel="stylesheet">
<!-- Link file JS -->
<script type="text/javascript" src="js/user.js"></script>
<script src="function.js"></script>
<!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/simple-sidebar.css" rel="stylesheet">
</head>
<!-- Sidebar -->
<div class="bg-light border-right" id="sidebar-wrapper">
	<div class="sidebar-heading">
		<i class="fa fa-ambulance"></i> &nbsp; HustMec
	</div>
	<div class="list-group list-group-flush">
		<ul class="mainmenu">
			<c:if test="${isAdmin}">
				<li><a href="${pageContext.request.contextPath}/ViewDetailUser.do?userId=${userIdFile}"
					class="list-group-item list-group-item-action bg-light">Thông tin cá nhân</a></li>
				<li><a href="${pageContext.request.contextPath}/ListUser.do"
					class="list-group-item list-group-item-action bg-light">Danh sách người dùng</a></li>
				<li><a href="${pageContext.request.contextPath}/ListDevice.do"
					class="list-group-item list-group-item-action bg-light">Danh sách thiết bị</a></li>
				<li><a href="${pageContext.request.contextPath}/ListFile.do"
					class="list-group-item list-group-item-action bg-light">Danh sách file upload</a></li>
				<li><a href="${pageContext.request.contextPath}/ListHisLog.do"
					class="list-group-item list-group-item-action bg-light">Lịch sử đăng nhập</a></li>
			</c:if>
			<c:if test="${not isAdmin}">
				<li><a href="${pageContext.request.contextPath}/ViewDetailUser.do?userId=${userIdFile}"
					class="list-group-item list-group-item-action bg-light">Thông tin cá nhân</a></li>
				<li><a href="${pageContext.request.contextPath}/ListDevice.do"
					class="list-group-item list-group-item-action bg-light">Danh sách thiết bị</a></li>
				<li><a href="${pageContext.request.contextPath}/ListFile.do"
					class="list-group-item list-group-item-action bg-light">Danh sách file upload</a></li>
			</c:if>
		</ul>
	</div>
</div>