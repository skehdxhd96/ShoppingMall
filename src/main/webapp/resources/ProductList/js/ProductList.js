/*$(document).ready(function() {
	$.ajax({
		url:"/categoryList", 
		type:"POST", 
		success:function(data) {
			var html = "";
			for (i=0; i<data.length; i++) {
				html += "<a href=\"/ProductList/" + data[i].category_code + "\" class=\"list-group-item\">" + data[i].category_name + "</a>";
			}
			$("#categories").html(html);
			
		},
		error:function() {
			alert("ERROR!!");
		}
	});
})*/

$(document).on("click", "#pageButton", function() {
	var startIdx = 6*(Number($(this).text())-1);
	var categoryCode = Number($(location).attr('pathname').slice(-1));
	var dataTransfer = {"startIdx":startIdx,
						"categoryCode":categoryCode};
	
	$.ajax({
		url:"/ProductList/paging", 
		type:"POST", 
		data:JSON.stringify(dataTransfer),
		contentType: "application/json; charset=utf-8;",
	    dataType: "json",
		success:function(data) {
			console.log(startIdx);
			var list = "";
			for (var i=0; i<data.length; i++) {
				list += data[i].product_name + " ";
			}
			console.log(list);
		},
		error:function() {
			alert("ERROR");
		}
	});
});