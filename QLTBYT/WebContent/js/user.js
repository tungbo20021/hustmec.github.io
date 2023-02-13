//Ẩn và hiện 1 khối html
function hideOrShow() {
	var display = document.getElementsByClassName("hideOrShow");
	if (display[1].style.display == "none") {
		display[1].style.display  = "block";
	} else {
		display[1].style.display[1] = "none";
	}
}

function confirmDelete(path, messConfirmDelete) {
	//Nếu chọn OK trong hộp thoại confirm
	if (window.confirm(messConfirmDelete)) {
		//Lấy form ở trang
		const form = document.getElementById('formSubmit');
		//set method cho form là post
		form.method = 'post';
		//set action cho form khi submit
		form.action = path;
		//Submit form
		form.submit();
	}
}

function enterDateRepair() {
	var person = prompt("Nhập ngày bắt đầu sửa chữa", "Harry Potter");

	if (person != null) {
	  document.getElementById("demo").innerHTML =
	  "Hello " + person + "! How are you today?";
	}
}

function activeState() {
	if (document.getElementById("activePage").className == "page-item") {
		document.getElementById("activePage").className = "page-item active";
		return true;
	} 
}
