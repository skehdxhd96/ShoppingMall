//회원가입, 회원정보 수정 submit 전 유효성 검사하는 함수
function checkBeforeSubmit() {
	var formControls = $(".form-control");
	var result = true;
	
	//제출 시 null값이 존재하는지 확인하는 유효성 검사
	for (var i=0; i<formControls.length; i++) {
		console.log(formControls[i].value);
		if (formControls[i].value=="") {
			alert("모든 값이 입력되지 않았습니다.");
			formControls[i].focus();
			result = false;
			
			return result;
		}
	}
	
	//전화번호 형식 유효성 검사
	var regPhone = /^\d{3}-\d{3,4}-\d{4}$/;
	var inputCustomerPhone = $(".form-control[name=customerPhone]");
	var inputCompanyPhone = $(".form-control[name=companyPhone]");
	
	if (!regPhone.test(inputCustomerPhone.val())||!regPhone.test(inputCompanyPhone.val())) {
		alert("휴대전화 형식을 맞춰주세요");
		result = false;
	}
	
	return result;
} 

//회원가입 submit 전 유효성 검사
$(".signupForm").submit(function() {
	var result = checkBeforeSubmit();
	
	if (result==true) {
		alert("회원가입이 완료되었습니다!");
	}

	return result;
});

//회원정보 수정 submit 전 유효성 검사
$(".modifySubmit").submit(function() {
	var result = checkBeforeSubmit();
	
	if (result==true) {
		alert("회원정보 수정이 완료되었습니다.");
	}
	
	return result;
})