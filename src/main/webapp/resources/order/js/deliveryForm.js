$(".submit-button").on("click", function() {
	var data= {
		"orderCode" : $("input[name=orderCode]").val(), 
		"recipient" : $("input[name=recipient]").val(), 
		"shippingAddress" : $("input[name=shippingAddress]").val(), 
		"deliverPhone" : $("input[name=deliverPhone]").val(),
		"requests" : $("input[name=requests]").val()
	}
	
	/*결제코드 : merchant_uid
	결제방법 : pay_method
	사용포인트 : 
	적립포인트 : $("input[name=used_point]").val() 
	포인트사용여부 : 항상True(사용안할시에는 0)
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
				    amount : 10
				}, function(rsp) {
				    if ( rsp.success ) {
				    	
				    	axios({
				            url: "/payment", // 가맹점 서버
				            method: "post",
				            headers: { "Content-Type": "application/json" },
				            data: {
				              imp_uid: rsp.imp_uid,
				              merchant_uid: rsp.merchant_uid
				            }
				          }).then((data) => {
				            // 가맹점 서버 결제 API 성공시 로직
				        	var msg = '결제가 완료되었습니다.';
				        	msg += '고유ID : ' + rsp.imp_uid;
				        	msg += '결제 금액 : ' + rsp.paid_amount;
				        	location.href = '/order/delivery/after?orderCode=' + response.data.orderCode + '&status=' + rsp.status;
				          })
				    } else {
				        var msg = '결제에 실패하였습니다.';
				        msg += '에러내용 : ' + rsp.error_msg;
				        location.href = '/order/orderError';
				    }
				    alert(msg);
				});
				location.href = "/order/delivery/after?orderCode=" + response.data.orderCode;
			}
		}).catch(function(err) {
			console.log(err);
			console.log(data);
		});
	}
	else {
		alert("배송지를 변경합시다!");
		axios.patch("/order/delivery/form", data).then(function(res) {
			if (res.data.result==0) {
				alert("배송지 변경 중 오류가 발생했습니다.");
			} else {
				alert("배송지 변경이 완료되었습니다.");
			}
			location.reload();
		});
	}
})