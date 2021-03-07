$(".submit-button").on("click", function() {
	var data= {
		"orderCode" : $("input[name=orderCode]").val(), 
		"recipient" : $("input[name=recipient]").val(), 
		"deliveryZipCode" : $("input[name=delveryZipcode]").val(),
		"shippingAddress" : $("input[name=shippingAddress]").val(), 
		"deliverPhone" : $("input[name=deliverPhone]").val(),
		"requests" : $("input[name=requests]").val()
	}
	
	/*결제코드 : merchant_uid
	결제방법 : pay_method
	사용포인트 :$("input[name=used_point]").val()
	적립포인트 :  $("input[name=getTotalPoint]").val()
	포인트사용여부 : 항상True(사용안할시에는 가격 : 0)
	최종결제금액 : amount
	결제상태 : status
	주문코드 : data.orderCode
	현재가지고있는포인트(getPoint) < 사용할포인트(used_point)이면 오류출력하고 지움.(키이벤트)*/
	
	var IMP = window.IMP;
	IMP.init('imp97827071');
	
	if ($(this).val()=="입력완료") {
		axios.post("/order/delivery/form", data).then(function(response) {
			if (response.data.result==0||response.data.orderCode==undefined) {	//업데이트를 실패하면
				alert("배송 업데이트 과정 중 오류가 발생했습니다.");
				location.href = "/order/orderError";	//주문 오류 발생 안내 페이지로
			}
			else {
				alert("배송지 입력이 완료되었습니다.");
				IMP.request_pay({
				    pg : 'inicis', // version 1.1.0부터 지원.
				    pay_method : 'card',
				    merchant_uid : 'merchant_' + new Date().getTime(),
				    name : '주문',
				    amount : $("input[name=getTotalPrice]").val() - $("input[name=used_point]").val(),
				    buyer_tel : '010-1234-5678'
				}, rsp => {
				    if ( rsp.success ) {
				        axios({
				            url: "/payment", // 가맹점 서버
				            method: "post",
				            headers: { "Content-Type": "application/json" },
				            data: {
				            	payment_code : rsp.merchant_uid,
				            	payment_method : rsp.pay_method,
				            	used_point : $("input[name=used_point]").val(),
				            	saved_point : $("input[name=getTotalPoint]").val(),
				            	point_check : 1,
				            	total_payment_price : $("input[name=getTotalPrice]").val() - $("input[name=used_point]").val(),
				            	payment_status : rsp.status,
				            	order_code : response.data.orderCode
				            }
				          }).then((data) => {
				            // 가맹점 서버 결제 API 성공시 로직
				        	var msg = '결제가 완료되었습니다.';
				        	msg += '고유ID : ' + rsp.imp_uid;
				        	msg += '결제 금액 : ' + rsp.paid_amount;
				        	alert(msg);
				        	location.href = '/order/delivery/after?orderCode=' + response.data.orderCode + '&status=' + rsp.status;
				          }).catch(function(err) {	//결제 axios 오류
								if (err.response) {
							      // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
							      console.log(err.response.data);
							      console.log(err.response.status);
							      console.log(err.response.headers);
							    }
							    else if (err.request) {
							      // 요청이 이루어 졌으나 응답을 받지 못했습니다.
							      // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
							      // Node.js의 http.ClientRequest 인스턴스입니다.
							      console.log(err.request);
							    }
							});
				    } else {
				        var msg = '결제에 실패하였습니다.';
				        msg += '에러내용 : ' + rsp.error_msg;
				        location.href = '/order/orderError';
						alert(msg);
				    }
				});
			}
		}).catch(function(err) {	//배송지 입력 axios 오류
			if (err.response) {
		      // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
		      console.log(err.response.data);
		      console.log(err.response.status);
		      console.log(err.response.headers);
		    }
		    else if (err.request) {
		      // 요청이 이루어 졌으나 응답을 받지 못했습니다.
		      // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
		      // Node.js의 http.ClientRequest 인스턴스입니다.
		      console.log(err.request);
		    }
		});
	}
	else {
		alert("배송지를 변경합시다!");
		console.log(data)
		axios.put("/order/delivery/update", data).then(function(res) {
			if (res.data.result==0) {
				alert("배송지 변경 중 오류가 발생했습니다.");
				location.href = "/order/orderError";
			} else {
				alert("배송지 변경이 완료되었습니다.");
			}
			location.reload();
		}).catch(function(err) {	//배송지 수정 axios 오류
			if (err.response) {
		      // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
		      console.log(err.response.data);
		      console.log(err.response.status);
		      console.log(err.response.headers);
		    }
		    else if (err.request) {
		      // 요청이 이루어 졌으나 응답을 받지 못했습니다.
		      // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
		      // Node.js의 http.ClientRequest 인스턴스입니다.
		      console.log(err.request);
		    }
		});
	}
});

$(document).ready(function() {
	$(document).on('keydown', '#UsedPoint', function() {
		if($("input[name=used_point]").val() > $("input[name=getPoint]").val()) {
			alert($("input[name=getPoint]").val() + "포인트 이하만 입력 가능합니다.");
			$("input[name=used_point]").val('');
		}
	})
});