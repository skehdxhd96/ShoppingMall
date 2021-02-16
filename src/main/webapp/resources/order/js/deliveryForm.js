$(".submit-button").on("click", function() {
	var data= {
		"orderCode" : $("input[name=orderCode]").val(), 
		"recipient" : $("input[name=recipient]").val(), 
		"shippingAddress" : $("input[name=shippingAddress]").val(), 
		"deliverPhone" : $("input[name=deliverPhone]").val(),
		"requests" : $("input[name=requests]").val()
	}
	
	axios.post("/order/delivery/form", data).then(function(response) {
		if (response.data.result==0||response.data.orderCode==undefined) {	//업데이트를 실패하면
			alert("배송 업데이트 과정 중 오류가 발생했습니다.");
			location.href = "/order/orderError";	//주문 오류 발생 안내 페이지로
		}
		else {
			alert("배송지 입력이 완료되었습니다.");
			location.href = "/order/delivery/after?orderCode=" + response.data.orderCode;
		}
	}).catch(function(err) {
		console.log(err);
		console.log(data);
	});
})