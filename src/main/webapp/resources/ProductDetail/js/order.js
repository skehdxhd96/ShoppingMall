$("#Order_Btn").on('click', function() {
	var productCode = $('input[name=product_code]').val();
	var productQuantity = $('input[name=product_quantity]').val();
	var productPrice = $("h4#productPrice").text().slice(0, -1);;
	
	axios.post("/order/delivery", {
		"history" : "detail", 
		"products" : [
			{"productCode" : productCode, 
			 "productQuantity" : productQuantity, 
			 "productPrice" : productPrice
			}
		]
	}).then(function(response) {
		console.log(response);
		if (response.data==0) {
			alert("로그인이 필요합니다.");
		}
		else if (response.data==2) {
			alert("접근 권한이 없습니다.");
		}
		else {
			alert("주문이 완료되었습니다.");
		}
	})
})