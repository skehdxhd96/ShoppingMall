var BasketService = (function() {
	
	function getList(param, callback, error) {
		
		var customer_code = param.customer_code;
		var page = param.page || 1;
		
		$.getJSON("/myPage/basket/" + customer_code + "/" + page + ".json",
				function(data) {
				if(callback) {
					callback(data.replyCount, data.list);
				}
		}).fail(function(xhr, status, err) {
			if(error) {
				error();
			}
		});
	}
	
	/*function SendData(CheckedData, callback, error) {

		$.ajax({
			
			type : 'POST',
			url : '/order/delivery',
			data :JSON.stringify(CheckedData),
			contentType : "application/json;charset=UTF-8",
			success : function(result, status, xhr) {
				var resultJson = JSON.parse(result);
				if (resultJson.result==0) {
					alert("로그인이 필요합니다.");
					location.href = "/login";
				}
				else if(resultJson.result==2) {
					alert("주문 과정 중 오류가 발생했습니다.");
					location.href = "/order/orderError";
				}
				else {
					var url="/order/delivery/form?orderCode=" + resultJson.orderCode;
					alert("배송지 입력 페이지로 이동합니다.");
					location.href = url;
				}
				//if(callback) {
					//callback(result);
				//}
			},
			error : function(xhr, status, er) {
				if(error) {
					error(er);
				}
			}
		})
	}*/
	
	function add(basket, callback, error) {
		
		$.ajax({
			type : 'POST',
			url : '/myPage/basket/new',
			data : JSON.stringify(basket),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if(callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if(error) {
					error(er);
				}
			}
		})
	}
	
	function remove(param, callback, error) {
		
		var customer_code = param.customer_code;
		var product_code = param.product_code;
		
		$.ajax({
			type : 'delete',
			url : '/myPage/basket/delete/' + customer_code  + "/" + product_code,
			success : function(deleteResult, status, xhr) {
				if(callback) {
					callback(deleteResult);
				}
			},
			error : function(xhr, status, er) {
				if(error) {
					error(er);
				}
			}
		})
	}
	
	return {
		getList : getList,
		add : add,
		remove : remove,
		//SendData : SendData
	};
})();