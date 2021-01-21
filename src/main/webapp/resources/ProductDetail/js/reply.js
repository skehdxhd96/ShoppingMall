console.log("Reply Module");

var replyService = (function() {
	
	function add(reply, callback, error) {
		console.log("add reply........");
		
		$.ajax({
			type : 'POST',
			url : '/replies/new',
			data : JSON.stringify(reply),
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
	
	function getList(param, callback, error) {
		
		var product_code = param.product_code;
		var page = param.page || 1;
		
		$.getJSON("replies/pages/" + product_code + "/" + page + ".json",
				function(data) {
				if(callback) {
					callback(data);
				}
		}).fail(function(xhr, status, err) {
			if(error) {
				error();
			}
		});
	}
	
	function remove(review_code, callback, error) {
		
		$.ajax({
			type : 'delete',
			url : '/replies/' + review_code,
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
		});
	}
	
	function update(reply, callback, error) {
		
		console.log("review_code: " + reply.reiew_code);
		
		$.ajax({
			type : 'PUT',
			url : '/replies/' + reply.review_code,
			data : JSON.stringify(reply),
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
		});
	}
	
	function get(review_code, callback, error) {
		
		$.get("/replies/" + review_code + ".json", function(result) {
			
			if(callback) {
				callback(result);
			}
		}).fail(function(xhr, status, err) {
			
			if(error) {
				error();
			}
		});
	}

	return {
		add : add,
		getList : getList,
		remove : remove,
		update : update,
		get : get
	};
})();