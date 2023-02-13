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

<body id="page-top" style="font-family: Nunito">
	<div class="d-flex" id="wrapper">
		<!-- Sidebar -->
		<jsp:include page="sidebar.jsp" />
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<!-- navbar -->
			<jsp:include page="navbar.jsp" />

			<div class="container-fluid">
				<h1 class="mt-4" style="color: #007BFF; text-align: center;">Thông
					tin cá nhân</h1>
				<hr>
				<!-- Form add -->
				<div style="padding-left: 250px">
					<form id="formSubmit" action="EditUserInput.do" method="get"
						name="inputform">
						<input id="param" type="hidden" name="userId"
							value="${user.userId}" />
						<table class="tbl_input" border="0" width="100%" cellpadding="0"
							cellspacing="0">
							<tr>
								<td align="left">
									<div class="form_data">
										<table border="1" width="75%" class="tbl_input"
											cellpadding="4" cellspacing="0">
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Họ
													tên:</td>
												<td align="left"><c:out value="${user.fullName}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Tên
													đăng nhập:</td>
												<td align="left"><c:out value="${user.loginName}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Quyền
													hạn:</td>
												<td align="left"><c:out value="${user.role}" /></td>

											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Phòng
													ban:</td>
												<td align="left"><c:out value="${user.department}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Địa
													chỉ:</td>
												<td align="left"><c:out value="${user.address}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Số
													điện thoại:</td>
												<td align="left"><c:out value="${user.mobile}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Email:</td>
												<td align="left"><c:out value="${user.email}" /></td>
											</tr>
										</table>
									</div>
									</div>
								</td>
						</table>
				</div>
				<br>
						<!-- Begin vung button -->
						<div style="padding-left: 300px;">
							<table border="0" cellpadding="4" cellspacing="0" width="300px">
								<tr>
									<th width="200px" align="center">&nbsp;</th>
									<td><input class="btn btn-primary" type="submit"
										value="Chỉnh sửa thông tin" /></td>
									<td><input class="btn btn-primary" type="button"
										value="Xóa tài khoản"
										onclick="confirmDelete('DeleteUser.do', '${messConfirmDelete}')" /></td>
									<td><input class="btn btn-primary" type="button"
										value="Quay lại"
										onclick="window.location.href = 'ListUser.do?action=back' " /></td>
								</tr>
							</table>
							<!-- End vung button -->
					</form>
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