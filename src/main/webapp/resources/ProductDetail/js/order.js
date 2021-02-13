$("#Order_Btn").on('click', function() {
	var productCode = $('input[name=product_code]').val();
	var productQuantity = $('input[name=product_quantity]').val();
	var productPrice = $("h4#productPrice").text().slice(0, -1);
	var totalPrice = productPrice*productQuantity;
	
	axios.post("/order/delivery", {
		"reqUrl" : window.location.href, 
		"products" : [
			{"productCode" : productCode, 
			 "productQuantity" : productQuantity, 
			 "productPrice" : productPrice
			}
		], 
		"totalPrice" : totalPrice
	}).then(function(response) {
		console.log(response.data);
		if (response.data.result==0) {
			alert("로그인이 필요합니다.");
			location.href = "/login";
		}
		else {
			var url="/order/delivery/form?deliveryCode=" + response.data.deliveryCode;
			alert("배송지 입력 페이지로 이동합니다.");
			location.href = url;
		}
	}).catch(function(err) {
		alert("주문 과정 중 오류가 발생하였습니다.");
	})
})