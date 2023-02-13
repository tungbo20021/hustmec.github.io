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
<script src="https://code.jquery.com/jquery-latest.js"></script>
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
				<c:choose>
					<c:when test="${action eq 'edit' }">
						<h1 class="mt-4" style="color: #007BFF; text-align: center;">Chỉnh
							sửa thông tin thiết bị</h1>
					</c:when>
					<c:otherwise>
						<h1 class="mt-4" style="color: #007BFF; text-align: center;">Thêm
							mới thiết bị</h1>
					</c:otherwise>
				</c:choose>
				<hr>
				<!-- Form add -->
				<div class="form-add-user">
					<form
						<c:choose>
							<c:when test="${action eq 'edit' }">
								action = "EditDeviceValidate.do?action=confirm&deviceId=${device.deviceId}"
							</c:when>
							<c:otherwise>
								action = "AddDeviceValidate.do?action=confirm"
							</c:otherwise>
						</c:choose>
						method="post" name="inputform">
						<table class="data-input" width="80%" cellpadding="0"
							cellspacing="0">
							<tbody>
								<c:forEach items="${listErrorMess}" var="errorMess">
									<tr>
										<td style="padding-left: 0px; color: red; font-style: italic;"
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
														<td class="lbl_left"><font color="red">*</font> Tên
															thiết bị:</td>
														<td align="left"><input required autofocus
															class="txBox" type="text" name="deviceName"
															value="<c:out value="${device.deviceName}" />" size="40" /></td>
													</tr>
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Model:</td>
														<td align="left"><input required class="txBox" type="text"
															name="deviceModel"
															value="<c:out value="${device.deviceModel}" />" size="40" /></td>
													</tr>
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Serial:</td>
														<td align="left"><input required class="txBox" type="text"
															name="deviceSerial"
															value="<c:out value="${device.deviceSerial}" />"
															size="40" /></td>
													</tr>
													
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Khoa phòng:</td>
														<td align="left"><input required class="txBox" type="text"
															name="department"
															value="<c:out value="${device.department}" />"
															size="40" /></td>
													</tr>
													
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Hãng SX/Nước SX:</td>
														<td align="left"><input required class="txBox" type="text"
															name="brand"
															value="<c:out value="${device.brand}" />"
															size="40" /></td>
													</tr>
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Năm sản xuất:</td>
														<td align="left"><input required class="txBox" type="text"
															name="productDate"
															value="<c:out value="${device.productDate}" />"
															size="40" /></td>
													</tr>
													
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Tháng năm đưa vào sử dụng:</td>
														<td align="left"><input required class="txBox" type="text"
															name="importDate"
															value="<c:out value="${device.importDate}" />"
															size="40" /></td>
													</tr>
													
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Số lượng:</td>
														<td align="left"><input required class="txBox" type="text"
															name="amount"
															value="<c:out value="${device.amount}" />" size="40" /></td>
													</tr>
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Đơn vị tính:</td>
														<td align="left"><input required class="txBox" type="text"
															name="unit"
															value="<c:out value="${device.unit}" />" size="40" /></td>
													</tr>
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Nguồn gốc kinh phí:</td>
														<td align="left"><input required class="txBox" type="text"
															name="source"
															value="<c:out value="${device.source}" />"
															size="40" /></td>
													</tr>
													
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Tình trạng khi nhận:</td>
														<td align="left"><input required class="txBox"
															type="text" name="status"
															value="<c:out value="${device.status}" />" size="40" /></td>
													</tr>

													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Điểm chức năng:</td>
														<td align="left"><input required class="txBox"
															type="text" name="funcScore"
															value="<c:out value="${device.funcScore}" />" size="40" /></td>
													</tr>
													
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Điểm ứng dụng:</td>
														<td align="left"><input required class="txBox"
															type="text" name="appScore"
															value="<c:out value="${device.appScore}" />" size="40" /></td>
													</tr>
													
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Điểm bảo trì:</td>
														<td align="left"><input required class="txBox"
															type="text" name="maintainScore"
															value="<c:out value="${device.maintainScore}" />" size="40" /></td>
													</tr>
													
													<tr>
														<td class="lbl_left"><font color="red">*</font>
															Điểm lịch sử:</td>
														<td align="left"><input required class="txBox"
															type="text" name="histoScore"
															value="<c:out value="${device.histoScore}" />" size="40" /></td>
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
											onclick="window.location.href = '${pageContext.request.contextPath}/ListDevice.do'"
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