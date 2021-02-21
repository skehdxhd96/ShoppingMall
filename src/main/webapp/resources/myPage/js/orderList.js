$(".cancelBtn").on("click", function() {
	var checkCancel = confirm("정말로 주문을 취소하겠습니까?");
	if (checkCancel==true) {
		var orderCode = $(this).parent().siblings("#orderCode").text().slice(3);
		
		axios.post("/order/orderCancel", {
			"orderCode" : orderCode
		}).then(function(res) {
			if (res.data.result==0) {
				alert("주문 취소 중 오류가 발생했습니다.");
			}
			else {
				alert("주문 취소가 완료되었습니다.");
				location.reload();
			}
		});
	}
	else {
		console.log("취소하지 않습니다.");
	}
});