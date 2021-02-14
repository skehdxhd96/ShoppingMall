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

	return {
		getList : getList,
		add : add
	};
})();