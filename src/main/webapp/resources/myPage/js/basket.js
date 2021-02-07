var replyService = (function() {
	
	function getList(param, callback, error) {
		
		var customer_code = param.customer_code;
		var page = param.page || 1;
		
		$.getJSON("/myPage/basket/" + page + ".json",
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

	return {
		getList : getList
	};
})();