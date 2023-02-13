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
<script type="text/javascript" src="js/device.js"></script>
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
				<h1 class="mt-4" style="color: #075E54; text-align: center;">Danh
					sách thiết bị trong file đã chọn</h1>
				</div>
				<!-- DataTable -->
				<div style="overflow-x:auto;">
				<table class="table table-bordered" border="1" cellpadding="4"
					cellspacing="0" width="100%">

					<tr class="tr2">
						<th align="center" width="2%">ID</th>
						<c:url value="ListDevice.do" var="url_sort">
							<c:param name="action" value="sort" />
							<c:param name="deviceName" value="${deviceName}" />
							<c:param name="page" value="${currentPage}" />
						</c:url>
						<th align="left" width="10%">Tên thiết bị <c:if
								test="${sortByDeviceName == 'ASC'}">
								<a href="${url_sort}&sortByDeviceName=DESC">▲▽</a>
							</c:if> <c:if test="${sortByDeviceName == 'DESC'}">
								<a href="${url_sort}&sortByDeviceName=ASC">△▼</a>
							</c:if>
						</th>
						<th align="left" width="10%">Khoa phòng</th>
						<th align="left" width="10%">Hãng SX/Nước SX</th>
						<th align="left" width="5%">Năm Sản xuất</th>
						<th align="left" width="5%">Tháng năm đưa vào sử dụng</th>
						<th align="left" width="10%">Tình trạng đưa vào khi nhận</th>			
						<th align="left" width="10%">Điểm chức năng</th>
						<th align="left" width="10%">Điểm ứng dụng</th>
						<th align="left" width="10%">Điểm bảo trì</th>
						<th align="left" width="10%">Điểm lịch sử</th>
						<th align="left" width="5%">EM</th>
						<th align="left" width="5%">Loại</th>
						<th align="left" width="10%">Tần suất bảo trì</th>
						
					</tr>
					<c:forEach items="${listDevices}" var="device">
						<tr>
							<form id="formSubmit" action="" method="get" name="inputform">
							</form>
							<td><c:out value="${device.deviceId}" /></td>
							<td align="left"><a
								href="ViewDetailDevice.do?deviceId=${device.deviceId}"><c:out
										value="${device.deviceName}" /></a></td>
							<td style="text-align: left"><c:out
									value="${device.department}" /></td>
							<td><c:out value="${device.brand}" /></td>

							<td><c:out value="${device.productDate}" /></td>

							<td><c:out value="${device.importDate}" /></td>
							<td><c:out value="${device.status}" /></td>
							<td><c:out value="${device.funcScore}" /></td>

							<td><c:out value="${device.appScore}" /></td>

							<td><c:out value="${device.maintainScore}" /></td>
							<td><c:out value="${device.histoScore}" /></td>
							<td><c:out value="${device.emScore}" /></td>
							<td><c:out value="${device.type}" /></td>
							<td><c:out value="${device.fre}" /></td>
						
							
						</tr>
					</c:forEach>
				</table>
</div>
				<!-- Begin vung paging -->
				<c:url value="ListDevice.do" var="url_paging">
					<c:param name="action" value="paging" />
					<c:param name="deviceName" value="${deviceName}" />
					<c:param name="sortByDeviceName" value="${sortByDeviceName}" />
				</c:url>
				<nav aria-label="Page navigation example">
					<ul class="pagination">
						<c:if test="${previousPage != null}">
							<li class="page-item"><a class="page-link"
								href="${url_paging}&page=${previousPage}" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span> <span class="sr-only">Previous</span>
							</a></li>
						</c:if>

						<c:forEach var="page" begin="0" end="3" items="${listPaging}">
							<li class="page-item ${page}" id="activePage"><a
								class="page-link" href="${url_paging}&page=${page}"
								onclick="activeState()">${page}</a></li>
						</c:forEach>
						<c:if test="${nextPage != null}">
							<li class="page-item"><a class="page-link" aria-label="Next"
								href="${url_paging}&page=${nextPage}"><span
									aria-hidden="true">&raquo;</span> <span class="sr-only">Next</span>
							</a></li>
						</c:if>

					</ul>
				</nav>
				<br>
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