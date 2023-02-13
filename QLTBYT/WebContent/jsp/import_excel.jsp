<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<title>Import File Excel</title>
</head>
<body id="page-top" style="font-family: Nunito">

	<!-- Begin vung input-->
	<form action="ImportDevice.do" method="post" enctype="multipart/form-data">
		<label for="myfile">Chọn file excel:</label>
		<input type="file" id="myfile" name="myfile"><br><br>
		<input type="submit">
	</form>
	<!-- End vung input -->

</body>
</html>