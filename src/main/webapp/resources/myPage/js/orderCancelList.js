var click=0;
var totalPage = Math.ceil($.cookie("orderCancelCnt")/5);

$(window).ready(function() {
	var data = {"page":click, "orderStatus":"cancel"};
	pagingCancel(data);
	
	if (totalPage>click+1) {
		html = "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-next\">다음</button>";
	}
	$(".paging-container").html(html);
});

$(".paging-container").on("click", ".btn-next", function() {
	click++;
	
	var data = {"page":click, "orderStatus":"cancel"};
	pagingCancel(data);
	
	if (totalPage==click+1) {
		html = "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-prev\">이전</button>";
	} else {
		html = "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-prev\">이전</button>";
		html += "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-next\">다음</button>";
	}
	$(".paging-container").html(html);
});

$(".paging-container").on("click", ".btn-prev", function() {
	click--;
	
	var data = {"page":click, "orderStatus":"done"};
	pagingCancel(data);
	
	if (click==0) {
		html = "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-next\">다음</button>";
	} else {
		html = "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-prev\">이전</button>";
		html += "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-next\">다음</button>";
	}
	$(".paging-container").html(html);
});

function pagingCancel(data) {
	axios.post("/myPage/order/paging", data).then(function(res) {
	 	console.log(res.data);
		var html = "";
		for (var i=0; i<res.data.length; i++) {
			html += "<table class=\"table table-sm order-list-table\">";
			html += "<tr>";
			html += "<th colspan=\"2\" id=\"orderCode\">No." + res.data[i].order_code + "</th>";
			html += "</tr>";
			html += "<tr>";
			html += "<th colspan=\"2\">" + res.data[i].order_date + "</th>";
        	html +=	"</tr>";
			for (var j=0; j<res.data[i].odProInfo.length; j++) {
				html += "<tr>";
				html += "<th rowspan=\"3\"><a href=\"/ProductDetail/" + res.data[i].odProInfo[j].product_code + "\"><img src=\"" + res.data[i].odProInfo[j].thumbnail_url + "}\" ></a></th>";
				html += "<th><a href=\"/ProductDetail/" + res.data[i].odProInfo[j].product_code + "\">" + res.data[i].odProInfo[j].product_name + "</a></th>";
				html += "</tr>";
				html += "<tr>";
				html += "<th>" + res.data[i].odProInfo[j].product_quantity + "</th>";
				html += "</tr>";
				html += "<tr>";
				html += "<th>결제금액(할인, 포인트 사용후)</th>";
				html += "</tr>";
			}
			html += "<tr>";
			html += "<th>총 결제금액</th>";
			html += "<th>총 결제금액(할인, 포인트 사용후)</th>";
			html += "</tr>";
			html += "</table>";		
		}
		
		$(".table-container").html(html);
	});
};
