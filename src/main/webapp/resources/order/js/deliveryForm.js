$(".submit-button").on("click", function() {
	var data= {
		"orderCode" : $("input[name=orderCode]").val(), 
		"recipient" : $("input[name=recipient]").val(), 
		"deliveryZipCode" : $("input[name=delveryZipcode]").val(),
		"shippingAddress" : $("input[name=shippingAddress]").val(), 
		"deliverPhone" : $("input[name=deliverPhone]").val(),
		"requests" : $("input[name=requests]").val()
	}
	
	//var IMP = window.IMP;
	//IMP.init('imp97827071');
	
	if ($(this).val()=="입력완료") {
		axios.post("/order/delivery/form", data).then(function(response) {
			if (response.data.result==0||response.data.orderCode==undefined) {	//업데이트를 실패하면
				alert("배송 업데이트 과정 중 오류가 발생했습니다.");
				location.href = "/order/orderError";	//주문 오류 발생 안내 페이지로
			}
			else {
				alert("배송지 입력이 완료되었습니다.");
				/*IMP.request_pay({
				    pg : 'inicis', // version 1.1.0부터 지원.
				    pay_method : 'card',
				    merchant_uid : 'merchant_' + new Date().getTime(),
				    name : '주문',
				    amount : 10
				}, function(rsp) {
				    if ( rsp.success ) {
				        var msg = '결제가 완료되었습니다.';
				        msg += '고유ID : ' + rsp.imp_uid;
				        msg += '결제 금액 : ' + rsp.paid_amount;
				        location.href = '/order/delivery/after?orderCode=' + response.data.orderCode + '&status=' + rsp.status;
				    } else {
				        var msg = '결제에 실패하였습니다.';
				        msg += '에러내용 : ' + rsp.error_msg;
				        location.href = '/order/orderError';
				    }
				    alert(msg);
				});*/
				location.href = "/order/delivery/after?orderCode=" + response.data.orderCode;
			}
		}).catch(function(err) {
			console.log(err);
			console.log(data);
		});
	}
	else {
		alert("배송지를 변경합시다!");
		console.log(data)
		axios.put("/order/delivery/update", data).then(function(res) {
			//console.log(data);
			console.log(res.data);
			console.log(res.headers);
			if (res.data.result==0) {
				alert("배송지 변경 중 오류가 발생했습니다.");
				location.href = "/order/orderError";
			} else {
				alert("배송지 변경이 완료되었습니다.");
			}
			location.reload();
		}).catch(function(err) {
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
})