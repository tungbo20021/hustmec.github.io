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
</head>

<body id="page-top" style="font-family: Nunito" >
	<div class="d-flex" id="wrapper">
		<!-- Sidebar -->
		<jsp:include page="sidebar.jsp" />
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<!-- navbar -->
			<jsp:include page="navbar.jsp" />

			<div class="container-fluid">
				<c:choose>
					<c:when test="${action eq 'edit' }">
						<h1 class="mt-4" style="color: #007BFF; text-align: center;">Chỉnh
							sửa thông tin cá nhân</h1>
					</c:when>
					<c:otherwise>
						<h1 class="mt-4" style="color: #007BFF; text-align: center;">Thêm
							mới nhân viên</h1>
					</c:otherwise>
				</c:choose>
<!-- 				<hr> -->
				<!-- Form add -->
				<div style="padding-left: 250px;">
				
					<form 
						<c:choose>
							<c:when test="${action eq 'edit' }">
								action = "EditUserValidate.do?action=confirm&userId=${user.userId}"
							</c:when>
							<c:otherwise>
								action = "AddUserValidate.do?action=confirm"
							</c:otherwise>
						</c:choose>
						method="post" name="inputform">
						<table class="data-input" width="100%" cellpadding="0"
							cellspacing="0">
							<tbody>
								<c:forEach items="${listErrorMess}" var="errorMess">
									<tr>
										<td style="padding-left: 250px; color: red; font-style: italic;"
											class="errMsg" colspan="2">- ${errorMess}</td>
									</tr>
								</c:forEach>
								<tr>
									<td>
										<div class="form_data">
											<hr>
											<table width="100%" class="tbl_input" cellpadding="4"
												cellspacing="0">
												<tbody>
													<tr>
														<td><font color="red">*</font> Họ
															tên:</td>
														<td align="left"><input autofocus class="txBox"
															type="text" name="fullName"
															value="<c:out value="${user.fullName}" />" size="30" /></td>
													</tr>
													<tr>
														<td class="lbl_left"><font color="red">*</font> Tên
															đăng nhập:</td>
														<c:choose>
															<c:when test="${action eq 'edit' }">
																<td align="left"><input class="txBox" readonly
																	type="text" name="loginName"
																	value="<c:out value="${user.loginName}" />" size="30" /></td>
															</c:when>
															<c:otherwise>
																<td align="left"><input class="txBox" type="text"
																	name="loginName"
																	value="<c:out value="${user.loginName}" />" size="30" /></td>
															</c:otherwise>
														</c:choose>

													</tr>
													
													<c:if test="${user.role eq 'User' }">
													<tr>
														<td class="lbl_left"><font color="red">*</font> Quyền
															hạn:</td>
														<td align="left"><input type="radio" name="role"
															value="Admin" > Admin<br> <input type="radio"
															name="role" value="User" checked> User<br>
													</tr>
													</c:if>
													
													<c:if test="${user.role eq 'Admin' || user.role eq ''}">
													<tr>
														<td class="lbl_left"><font color="red">*</font> Quyền
															hạn:</td>
														<td align="left"><input type="radio" name="role"
															value="Admin" checked> Admin<br> <input type="radio"
															name="role" value="User" > User<br>
													</tr>
													</c:if>
													
													
													<c:if test="${action ne 'edit' }">
														<tr>
															<td class="lbl_left"><font color="red">*</font> Mật
																khẩu:</td>
															<td align="left"><input class="txBox"
																type="password" name="password" value="" size="30" /></td>
														</tr>
														<tr>
															<td class="lbl_left"><font color="red">*</font> Nhập
																lại mật khẩu:</td>
															<td align="left"><input class="txBox"
																type="password" name="passwordConfirm" value=""
																size="30" /></td>
														</tr>
													</c:if>
													<tr>
														<td class="lbl_left"><font color="red">*</font> Phòng
															ban:</td>
														<td align="left"><input class="txBox" type="text"
															name="department" value="<c:out value="${user.department}" />"
															size="30" /></td>
													</tr>
													<tr>
														<td class="lbl_left"><font color="red">*</font> Địa
															chỉ:</td>
														<td align="left"><input class="txBox" type="text"
															name="address" value="<c:out value="${user.address}" />"
															size="30" /></td>
													</tr>
													<tr>
														<td class="lbl_left"><font color="red">*</font> Số
															điện thoại:</td>
														<td align="left"><input class="txBox" type="text"
															name="mobile" value="<c:out value="${user.mobile}" />"
															size="30" /></td>
													</tr>
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Email:</td>
														<td align="left"><input class="txBox" type="text"
															name="email" value="<c:out value="${user.email}" />"
															size="30" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<div style="padding-left: 100px;">&nbsp;</div>
						<!-- Begin vung button -->
						<div style="padding-left: 150px">
							<table cellpadding="4" cellspacing="0">
								<tbody>
									<tr>
										<td><input class="btn btn-primary" type="submit"
											value="Xác nhận"></td>
										<td><input class="btn btn-primary" type="button"
											onclick="window.location.href = '${pageContext.request.contextPath}/ViewDetailUser.do?userId=${user.userId}'"
											value="Quay lại"></td>
									</tr>
								</tbody>
							</table>
							<!-- End vung button -->
						</div>
						<!-- End vung input -->
					</form>
					</div>
				</div>
			</div>
		</div>
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Menu Toggle Script -->
	<script>
		$("#menu-toggle").click(function(e) {
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
		});
	</script>
</body>

</html>