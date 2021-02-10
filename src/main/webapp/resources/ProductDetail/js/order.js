$("#Order_Btn").on('click', function() {
	var productCode = $('input[name=product_code]').val();
	var productQuantity = $('input[name=product_quantity]').val();
	var productPrice = $("h4#productPrice").text().slice(0, -1);
	var totalPrice = productPrice*productQuantity;
	
	axios.post("/order/delivery", {
		"history" : "detail", 
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
		}
		else if (response.data.result==2) {
			alert("접근 권한이 없습니다.");
		}
		else {
			var url="/order/delivery/form?deliveryCode=" + response.data.deliveryCode;
			alert("주문이 완료되었습니다.");
			location.href = url;
		}
	})
})