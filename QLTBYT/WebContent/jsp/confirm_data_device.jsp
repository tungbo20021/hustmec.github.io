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
				<h1 class="mt-4" style="color: #007BFF; text-align: center;">Xác
					nhận lại thông tin</h1>
				<hr>
				<!-- Form add -->
				<div class="form-add-user">
					<form
						<c:choose>
							<c:when test="${action eq 'edit' }">
								action = "EditDeviceOK.do?dynamicKey=${dynamicKey}"
							</c:when>
							<c:otherwise>
								action = "AddDeviceOK.do?dynamicKey=${dynamicKey}"
							</c:otherwise>
						</c:choose>
						method="post" name="inputform">
						<table class="data-input" width="55%" cellpadding="0"
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
												<td width="30%" class="lbl_left" style="font-weight: bold">Tên
													thiết bị:</td>
												<td align="left"><c:out value="${device.deviceName}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Model:</td>
												<td align="left"><c:out value="${device.deviceModel}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Serial:</td>
												<td align="left"><c:out value="${device.deviceSerial}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Khoa phòng:</td>
												<td align="left"><c:out
														value="${device.department}" /></td>
											</tr>
											
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Hãng SX/Nước SX:</td>
												<td align="left"><c:out
														value="${device.brand}" /></td>
											</tr>
											
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Năm
													sản xuất:</td>
												<td align="left"><c:out value="${device.productDate}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Tháng năm đưa vào
													sử dụng:</td>
												<td align="left"><c:out
														value="${device.importDate}" /></td>
											</tr>
											
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Số
													lượng:</td>
												<td align="left"><c:out value="${device.amount}" /></td>
											</tr>
											
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Đơn
													vị tính:</td>
												<td align="left"><c:out value="${device.unit}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Nguồn gốc kinh phí:</td>
												<td align="left"><c:out value="${device.source}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Tình trạng khi nhận:</td>
												<td align="left"><c:out value="${device.status}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Điểm
													chức năng:</td>
												<td align="left"><c:out value="${device.funcScore}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Điểm
													ứng dụng:</td>
												<td align="left"><c:out value="${device.appScore}" /></td>
											</tr>
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Điểm
													bảo trì:</td>
												<td align="left"><c:out value="${device.maintainScore}" /></td>
											</tr>
											
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Điểm lịch sử:</td>
												<td align="left"><c:out value="${device.histoScore}" /></td>
											</tr>
											
											<tr>
												<td width="30%" class="lbl_left" style="font-weight: bold">Giá trị EM:</td>
												<td align="left"><c:out value="${device.emScore}" /></td>
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
							<table border="0" cellpadding="4" cellspacing="0">
								<tr>
									<td><input class="btn btn-primary" type="submit"
										value="OK" /></td>
									<td><input class="btn btn-primary" type="button"
										value="Quay lại"
										<c:choose>
										<c:when test="${action eq 'edit' }">
										onclick="window.location.href = 'EditDeviceInput.do?action=back&dynamicKey=${dynamicKey}&deviceId=${device.deviceId}'"
										</c:when>
									<c:otherwise>
										onclick="window.location.href = 'AddDeviceInput.do?action=back&dynamicKey=${dynamicKey}'"
									</c:otherwise>
									</c:choose> /></td>
								</tr>
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