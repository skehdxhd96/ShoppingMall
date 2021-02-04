//구매자인지 판매자인지에 따라 다른 입력폼을 제공하는 이벤트
$("select[name=customerType]").change(function() {
	var formHtml = "";
	var selectedValue = $(this).val();
	
	$(".addinput").empty();
	
	if (selectedValue==1) {
		formHtml = "";
		formHtml += "<div class=\"form-desc\">주소</div>" +
			"<div class=\"form-group input-group\">" +
			"<div class=\"input-group-prepend\">" +
			"<span class=\"input-group-text\">" +
			"<i class=\"fas fa-home\"></i></span>" +
			"</div>" +
			"<input name=\"customerAddress\" class=\"form-control\" placeholder=\"\" type=\"text\" value=\"\">" +
			"</div>";
	}
	else if (selectedValue==2) {
		fomHtml = "";
		formHtml += "<div class=\"form-desc\">회사명</div>" +
			"<div class=\"form-group input-group\">" +
			"<div class=\"input-group-prepend\">" +
			"<span class=\"input-group-text\">" +
			"<i class=\"fas fa-building\"></i></span>" +
			"</div>" +
			"<input name=\"companyName\" class=\"form-control\" placeholder=\"\" type=\"text\" value=\"\">" +
			"</div>" + 
			"<div class=\"form-desc\">회사 주소</div>" +
			"<div class=\"form-group input-group\">" +
			"<div class=\"input-group-prepend\">" +
			"<span class=\"input-group-text\">" +
			"<i class=\"fas fa-building\"></i></span>" +
			"</div>" +
			"<input name=\"customerAddress\" class=\"form-control\" placeholder=\"\" type=\"text\" value=\"\">" +
			"</div>" + 
			"<div class=\"form-desc\">회사 전화번호</div>" +
			"<div class=\"form-group input-group\">" +
			"<div class=\"input-group-prepend\">" +
			"<span class=\"input-group-text\">" +
			"<i class=\"fa fa-phone\"></i></span>" +
			"</div>" +
			"<input name=\"companyPhone\" class=\"form-control\" placeholder=\"\" type=\"text\" value=\"\">" +
			"</div>";
	}
	
	$(".addinput").append(formHtml);
});