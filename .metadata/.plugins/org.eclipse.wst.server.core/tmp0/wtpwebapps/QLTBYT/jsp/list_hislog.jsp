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

<body id="page-top"
	style="font-family: Nunito">
	<div class="d-flex" id="wrapper">
		<!-- Sidebar -->
		<jsp:include page="sidebar.jsp" />
		<!-- /#sidebar-wrapper -->
		<!-- Page Content -->
		<div id="page-content-wrapper">
			<!-- navbar -->
			<jsp:include page="navbar.jsp" />
			<div class="container-fluid">
				<h1 class="mt-4" style="color: #007BFF; text-align: center;">Lịch sử đăng nhập</h1>
				<hr>
				<div class="table-search" style="margin-bottom: 10px">
					<form action="ListHisLog.do" method="get" name="mainform">
						<input type="hidden" name="action" value="search" />
						<table style="width:35%;">
							<tbody>
								<tr>
									<td style="width = 70%;"><input type="text"
										name="logTime" value="<c:out value="${logTime}" />"
										class="form-control bg-light border-1 small"
										placeholder=" Nhập ngày cần tìm kiếm (dd-MM-yyyy)" aria-label="Search"
										aria-describedby="basic-addon2"></td>
									<td style="padding: 5px;" rowspan="2"><button
											class="btn btn-primary" type="submit">
											<i class="fa fa-search fa-sm"></i> Tìm kiếm
										</button></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<hr>
				<!-- DataTable -->
				<table class="table table-bordered" border="1" cellpadding="4"
					cellspacing="0" width="80%">

					<tr class="tr2">
						<th align="left" width="23%">ID</th>
						<th align="left" width="25%">Thời gian đăng nhập</th>
						<th align="left" width="25%">User</th>

					</tr>
					<c:forEach items="${listHisLogs}" var="hisLog">
						<tr>
							<td align="left"><c:out value="${hisLog.logId}" /></td>
							<td><c:out value="${hisLog.logTime}" /></td>
							<td><c:out value="${hisLog.userName}" /></td>
						</tr>
					</c:forEach>
				</table>
				<!-- Begin vung paging -->
				<c:url value="ListHisLog.do" var="url_paging">
					<c:param name="action" value="paging" />
					<c:param name="logTime" value="${logTime}" />
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