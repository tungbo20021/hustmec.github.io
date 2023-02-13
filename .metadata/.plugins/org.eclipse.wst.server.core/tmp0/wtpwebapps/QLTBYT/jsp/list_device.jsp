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

        tr:hover{
            background-color:#ddd;
            cursor:pointer;
        }
    </style>
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
					sách thiết bị</h1>
				<hr>

				<form id="Form2" action="ImportDevice.do" method="post"
					enctype="multipart/form-data">
					<label for="myfile">Upload thiết bị từ file Excel:</label> <input
						type="file" id="myfile" name="myfile">
					<button class="btn btn-primary" type="submit" value="Form2">
						<i class="fa fa-upload"></i> Upload
					</button>
					<button class="btn btn-primary" type="button"
					onclick="window.location.href = 'ExportDevice.do'">
					<i class="fa fa-download"></i> Download danh sách thiết bị
				</button>
				
				</form>
				<div class="table-search" style="margin-bottom: 10px">
					<form id="Form1" action="ListDevice.do" method="get"
						name="mainform">
						<input type="hidden" name="action" value="search" />
						<table>
							<tbody>
								<tr>
									<td style="padding: 5px;"><input type="text"
										name="deviceName" value="<c:out value="${deviceName}" />"
										class="form-control bg-light border-1 small"
										placeholder=" Nhập tên..." aria-label="Search"
										aria-describedby="basic-addon2"></td>

									<td style="padding: 5px;" rowspan="2"><button
											class="btn btn-primary" type="submit" value="Form1">
											<i class="fa fa-search fa-sm"></i> Tìm kiếm
										</button></td>
								</tr>
							</tbody>
						</table>						
					</form>
							<br>
					<!-- DataTable -->
				<form method="post">
				<div style="overflow-x:auto;">
				<table id = "table" class="table table-bordered" border="1" cellpadding="4"
					cellspacing="0" width="100%">

					<tr class="tr2">
						<th width="2%">ID</th>
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
						<th align="left" width="15%">Tần suất bảo trì</th>
						
					</tr>
					<c:forEach items="${listDevices}" var="device">
						<tr class="txtMult">
<!-- 							<form id="formSubmit" action="" method="get" name="inputform"> -->
<!-- 							</form> -->
							<td ><input style="width: 60px;" type = "text" name = "id" class = "id" readonly value = "<c:out value="${device.deviceId}" />"/></td>
							<td><a
								href="ViewDetailDevice.do?deviceId=${device.deviceId}"><input type = "text" name = "name" readonly value ="<c:out
										value="${device.deviceName}" />"/></a></td>
							<td><input style="width: 100px;"type = "text" name = "department" readonly value = "<c:out
									value="${device.department}" />"/></td>
							<td><input style="width: 100px;" type = "text" name = "brand" readonly value = "<c:out value="${device.brand}" />"/></td>
							<td><input style="width: 60px;" type = "text" name = "productDate" readonly value = "<c:out value="${device.productDate}" />"/></td>
							<td><input style="width: 100px;" type = "text" name = "importDate" readonly value = "<c:out value="${device.importDate}" />"/></td>
							<td><input style="width: 80px;" type = "text" name = "status" readonly value = "<c:out value="${device.status}" />"/></td>
							<td><select style="width: 200px;"name = "funcScore" class="val1" autofocus >
								<c:set var = "func" scope = "session" value = "${device.funcScore}"/>
							      <c:if test = "${func == 0}">
							    	<option value="0" selected >Choose here</option>
							      </c:if>
							      <optgroup label="Thiết bị điều trị">
							      <c:if test = "${func == 10}">
								    <option value="10" selected>Hỗ trợ sự sống: 10</option>
							      </c:if>
							      <c:if test = "${func != '10'}">
								    <option value="10" >Hỗ trợ sự sống: 10</option>
							      </c:if>
							      <c:if test = "${func == 9}">
								    <option value="9" selected>Phẫu thuật, chăm sóc: 9</option>
							      </c:if>
							      <c:if test = "${func != '9'}">
								    <option value="9">Phẫu thuật, chăm sóc: 9</option>
							      </c:if>
							      <c:if test = "${func == '8'}">
								    <option value="8"selected>Trị liệu, điều trị: 8</option>
							      </c:if>
							      <c:if test = "${func != '8'}">
								    <option value="8">Trị liệu, điều trị: 8</option>
							      </c:if>
							      </optgroup>
							     
							      <optgroup label="Thiết bị chẩn đoán">
							      
							      <c:if test = "${func == '7'}">
								    <option value="7"selected>Theo dõi, CSSK: 7</option>
							      </c:if>
							      <c:if test = "${func != '7'}">
								    <option value="7">Theo dõi, CSSK: 7</option>
							      </c:if>
							      <c:if test = "${func == '6'}">
								    <option value="6"selected>Chẩn đoán, theo dõi: 6</option>
							      </c:if>
							      <c:if test = "${func != '6'}">
								    <option value="6">Chẩn đoán, theo dõi: 6</option>
							      </c:if>
  								  </optgroup>
							      <optgroup label="Thiết bị phân tích">
							
							      <c:if test = "${func == '5'}">
								    <option value="5"selected>Thiết bị phân tích PTN: 5</option>
							      </c:if>
      							  <c:if test = "${func != '5'}">
								    <option value="5">Thiết bị phân tích PTN: 5</option>
							      </c:if>
							      <c:if test = "${func == '4'}">
								    <option value="4"selected>Phụ kiện PTN: 4</option>
							      </c:if>
							      <c:if test = "${func != '4'}">
								    <option value="4">Phụ kiện PTN: 4</option>
							      </c:if>
							      <c:if test = "${func == '3'}">
								    <option value="3"selected>Máy tính, thiết bị liên quan: 3</option>
							      </c:if>
							      <c:if test = "${func != '3'}">
								    <option value="3">Máy tính, thiết bị liên quan: 3</option>
							      </c:if>
							      </optgroup>
								  <optgroup label="Thiết bị khác: 2">
							      <c:if test = "${func == '2'}">
								    <option value="2"selected>Thiết bị khác: 2</option>
							      </c:if>	
							      <c:if test = "${func != '2'}">
								    <option value="2">Thiết bị khác: 2</option>
								    </c:if>
							      
							</select>
							<td><select style="width: 200px;"name = "appScore" class="val2" autofocus >
							<c:set var = "app" scope = "session" value = "${device.appScore}"/>
							      <c:if test = "${app == '0'}">
							    	<option value="0" selected >Choose here</option>
							      </c:if>
							      <c:if test = "${app != '0'}">
							    	<option value="0" >Choose here</option>
							      </c:if>
							      <c:if test = "${app == '5'}">
							<option value = "5"selected>Tiềm ẩn nguy cơ tử vong: 5</option>
							      </c:if>
							      <c:if test = "${app != '5'}">
							<option value = "5">Tiềm ẩn nguy cơ tử vong: 5</option>
							      </c:if>
							      <c:if test = "${app == '4'}">
							<option value = "4"selected>Phẫu thuật chấn thương: 4</option>
							      </c:if>
							      <c:if test = "${app != '4'}">
							<option value = "4">Phẫu thuật chấn thương: 4</option>
							      </c:if>
							      <c:if test = "${app == '3'}">
							<option value = "3"selected>Chẩn đoán sai: 3</option>
							      </c:if>
							      <c:if test = "${app != '3'}">
							<option value = "3">Chẩn đoán sai: 3</option>
							      </c:if>
							      <c:if test = "${app == '2'}">
							<option value = "2"selected>Thiết bị hỏng: 2</option>
							      </c:if>
							      <c:if test = "${app != '2'}">
							<option value = "2">Thiết bị hỏng: 2</option>
							      </c:if>
							      <c:if test = "${app == '1'}">
							<option value = "1"selected>Không gây nguy hiểm: 1</option>
							      </c:if>
							      <c:if test = "${app != '1'}">
							<option value = "1">Không gây nguy hiểm: 1</option>
							      </c:if>
							</select>
							</td>
							<td><select style="width: 200px;"name = "maintainScore" class="val3" autofocus>
							<c:set var = "maintain" scope = "session" value = "${device.maintainScore}"/>
							      <c:if test = "${maintain == '0'}">
							    	<option value="0" selected>Choose here</option>
							      </c:if>
							      <c:if test = "${maintain != '0'}">
							    	<option value="0" >Choose here</option>
							      </c:if>
							      <c:if test = "${maintain == '5'}">
							<option value = "5"selected>Mở rộng: Hiệu chuẩn thường xuyên và yêu cầu thay thế một phần: 5</option>
							      </c:if>
							      <c:if test = "${maintain != '5'}">
							<option value = "5">Mở rộng: Hiệu chuẩn thường xuyên và yêu cầu thay thế một phần: 5</option>
							      </c:if>
							      <c:if test = "${maintain == '4'}">
							<option value = "4"selected>Trên mức trung bình: 4</option>
							      </c:if>
							      <c:if test = "${maintain != '4'}">
							<option value = "4">Trên mức trung bình: 4</option>
							      </c:if>
							      <c:if test = "${maintain == '3'}">
							<option value = "3"selected>Trung bình: Xác minh hiệu suất và kiểm tra an toàn: 3</option>
							      </c:if>
							      <c:if test = "${maintain != '3'}">
							<option value = "3">Trung bình: Xác minh hiệu suất và kiểm tra an toàn: 3</option>
							      </c:if>
							      <c:if test = "${maintain == '2'}">
							<option value = "2"selected>Dưới mức trung bình: 2</option>
							      </c:if>
							      <c:if test = "${maintain != '2'}">
							<option value = "2">Dưới mức trung bình: 2</option>
							      </c:if>
							      <c:if test = "${maintain == '1'}">
							<option value = "1"selected>Tối thiểu: Kiểm tra bằng mắt: 1</option>
							      </c:if>
							      <c:if test = "${maintain != '1'}">
							<option value = "1">Tối thiểu: Kiểm tra bằng mắt: 1</option>
							      </c:if>
							</select>
							</td>							
							<td><select style="width: 200px;"name = "histoScore" class="val4" autofocus>
							<c:set var = "histo" scope = "session" value = "${device.histoScore}"/>
							      <c:if test = "${histo == '0'}">
							    	<option value="0" selected >Choose here</option>
							      </c:if>
							      <c:if test = "${histo != '0'}">
							    	<option value="0"  >Choose here</option>
							      </c:if>
							      <c:if test = "${histo == '2'}">
							<option value = "2"selected>Lỗi đáng kể (hơn 6 tháng một lần): +2</option>
							      </c:if>
							      <c:if test = "${histo != '2'}">
							<option value = "2">Lỗi đáng kể (hơn 6 tháng một lần): +2</option>
							      </c:if>
							      <c:if test = "${histo == '1'}">
							<option value = "1"selected>Lỗi tương đối (Cứ sau 6 tháng đến 9 tháng 1 lần lỗi): +1</option>
							      </c:if>
							      <c:if test = "${histo != '1'}">
							<option value = "1">Lỗi tương đối (Cứ sau 6 tháng đến 9 tháng 1 lần lỗi): +1</option>
							      </c:if>
							      <c:if test = "${histo == '0'}">
							<option value = "0"selected>Lỗi trung bình (Cứ 9 tháng đến 18 tháng lỗi 1 lần): 0</option>
							      </c:if>
							      <c:if test = "${histo != '0'}">
							<option value = "0">Lỗi trung bình (Cứ 9 tháng đến 18 tháng lỗi 1 lần): 0</option>
							      </c:if>
							      <c:if test = "${histo == '-1'}">
							<option value = "-1"selected>Lỗi tối thiểu (1 lỗi sau 18 đến 30 tháng): -1</option>
							      </c:if>
							      <c:if test = "${histo != '-1'}">
							<option value = "-1">Lỗi tối thiểu (1 lỗi sau 18 đến 30 tháng): -1</option>
							      </c:if>
							      <c:if test = "${histo == '-2'}">
							<option value = "-2"selected>Không đáng kể (Ít hơn một trong 30 tháng qua): -2</option>
							      </c:if>
							      <c:if test = "${histo != '-2'}">
							<option value = "-2">Không đáng kể (Ít hơn một trong 30 tháng qua): -2</option>
							      </c:if>
							</select>
							</td>
							<td><input readonly style="width: 50px;"type = "text" name = "emScore" class = "multTotal"value = "<c:out value="${device.emScore}" />"/>
							<td><input readonly style="width: 50px;"type = "text" name = "type" class = "type"value = "<c:out value="${device.type}" />"/>
							<td><input readonly style="width: 50px;"type = "text" name = "fre" class = "fre"value = "<c:out value="${device.fre}" />"/>
						
							</td>
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
						&nbsp&nbsp<input class="btn btn-primary"type="submit" value = "Save"/>	
						
					</ul>
				</nav>
								</form>
			</div>
		</div>
		<!-- /#page-content-wrapper -->
				</div>
				

	</div>
	<!-- /#wrapper -->

	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

	<!-- Menu Toggle Script -->
	<script>
		$("#menu-toggle").click(function(e) {
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
		});

		$(document).ready(function () {
		       $(".txtMult select").change(multInputs);
		       $("#table").load(multInputs);
		       function multInputs() {
		           var mult = 0;
		           $("tr.txtMult").each(function () {
		               var $val1 = $('.val1', this).val();
		               var $val2 = $('.val2', this).val();
		               var $val3 = $('.val3', this).val();
		               var $val4 = $('.val4', this).val();
		               var $total = ($val1 * 1) + ($val2 * 1)+ ($val3 * 1)+ ($val4 * 1);
		               $(".multTotal",this).val($total);
		               if($total>=12)
		            	   {
			               $(".type", this).val('I');
			               if($total>=19)
			            	   {
			            	   $(".fre", this).val('T');
			            	   }
			               else if($total>15&&$total<19)
			            	   {
			            	   $(".fre", this).val('S');
			            	   }
			               else if($total==15)
			            	   {
			            	   if($val3<4&&$val4<0)
				            	   $(".fre", this).val('A');
			            	   else
				            	   $(".fre", this).val('S');
			            	   }
			               else
			            	   {
			            	   $(".fre", this).val('A');
			            	   }
		            	   }
		               else {
		            	   $(".type", this).val('N');
		            	   $(".fre", this).val('-');
		               }
		           });
		       }
		  });
// 		$("table tr").click(function() {
// 			alert($('.id', this).val());
// 		});
		
	</script>
</body>

</html>