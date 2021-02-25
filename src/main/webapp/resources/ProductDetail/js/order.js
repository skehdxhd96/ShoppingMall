$("#Order_Btn").on('click', function() {
	var productCode = $('input[name=product_code]').val();
	var productQuantity = $('input[name=product_quantity]').val();
	var productPrice = $("h4#productPrice").text().slice(0, -1);
	var totalPrice = productPrice*productQuantity;
	var orderInfo = {
		"reqUrl" : window.location.href, 
		"products" : [
			{"productCode" : productCode, 
			 "productQuantity" : productQuantity, 
			 "productPrice" : productPrice
			}
		], 
		"totalPrice" : totalPrice
	}
	
	SendOrderData(orderInfo);
});

function SendOrderData(orderInfo) {
	console.log("여기는 order.js의 SendOrderData");
		
	axios.post("/order/delivery", orderInfo, {
		headers:{'Content-type': 'application/json;charset=UTF-8'}}).then(
		function(response) {
			if (response.data.result==0) {
				alert("로그인이 필요합니다.");
				location.href = "/login";
			}
			else if(response.data.result==2) {
				alert("주문 과정 중 오류가 발생했습니다.");
				location.href = "/order/orderError";
			}
			else {
				var url="/order/delivery/form?orderCode=" + response.data.orderCode;
				alert("배송지 입력 페이지로 이동합니다.");
				location.href = url;
			}
		}).catch(function(err) {
			alert("주문 과정 중 오류가 발생하였습니다.");
			location.href = "/order/orderError";
		});
}