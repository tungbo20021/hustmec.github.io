<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
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
<title>System Error</title>
</head>
<body id="page-top" style="font-family: Nunito">

	<!-- Begin vung input-->
	<form action="ListUser.do" method="get" name="inputform">
		<table class="tbl_input" border="0" width="80%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center" colspan="2">
					<div style="height: 50px"></div>
				</td>
			</tr>

			<tr>
				<td class="errMsg" colspan="2"><font size="4px" color=#007BFF><p
							align="center">${mess}</p></font></td>
			</tr>

			<tr>
				<td align="center" colspan="2">
					<div style="height: 30px"></div>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2"><input class="btn btn-primary" type="submit"
					value="OK" onclick="" /></td>
			</tr>
		</table>
	</form>
	<!-- End vung input -->

</body>
</html>