var click=1;
var totalPage = Math.ceil($.cookie("orderDoneCnt")/5);

$(window).ready(function() {
	var data = {"page":click, "orderStatus":"done"};
	pagingDone(data);
	
	if (totalPage>click) {
		html = "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-next\">다음</button>";
	}
	$(".paging-container").html(html);
});

$(".paging-container").on("click", ".btn-next", function() {
	click++;
	
	var data = {"page":click, "orderStatus":"done"};
	pagingDone(data);
	
	if (totalPage==click) {
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
	pagingDone(data);
	
	if (click==1) {
		html = "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-next\">다음</button>";
	} else {
		html = "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-prev\">이전</button>";
		html += "<button type=\"button\" class=\"btn btn-secondary btn-sm btn-next\">다음</button>";
	}
	$(".paging-container").html(html);
});

function pagingDone(data) {
	axios.post("/myPage/order/paging", data).then(function(res) {
	 	console.log(res.data);
		var html = "";
		for (var i=0; i<res.data.length; i++) {
			html += "<table class=\"table table-sm order-list-table\">";
			html += "<tr>";
			html += "<th id=\"orderCode\">No." + res.data[i].order_code + "<a href=\"/order/detail?orderCode=" + res.data[i].order_code + "\">  >주문상세보기</a></th>";
			html += "<th><button type=\"button\" class=\"btn btn-secondary btn-sm cancelBtn\">주문 취소</button></th>";
			html += "</tr>";
			html += "<tr>";
			html += "<th>" + res.data[i].order_date + "</th>";
			if (res.data[i].delivery_status == "preparing") {
				html += "<th>배송 준비중<a href=\"/order/delivery/form?orderCode=" + res.data[i].order_code + "\"><button type=\"button\" class=\"btn btn-secondary btn-sm DeliUpdateBtn\">배송지 변경</button></a></th>";
			} else if (res.data[i].delivery_status == "start") {
				html += "<th>배송중</th>";
			} else if (res.data[i].delivery_status == "arrive") {
				html += "<th>배송 완료</th>";
			} else {
				html += "<th>배송 취소</th>";
			}
			html += "</tr>";
			
			for (var j=0; j<res.data[i].odProInfo.length; j++) {
				html += "<tr>";
				html += "<th rowspan=\"3\"><a href=\"/ProductDetail/" + res.data[i].odProInfo[j].product_code + "\"><img src=\"" + res.data[i].odProInfo[j].thumbnail_url + "}\" ></a></th>";
				html += "<th><a href=\"/ProductDetail/" + res.data[i].odProInfo[j].product_code + "\">" + res.data[i].odProInfo[j].product_name + "</a></th>";
				html += "</tr>";
				html += "<tr>";
				html += "<th>" + res.data[i].odProInfo[j].product_quantity + "</th>";
				html += "</tr>";
				html += "<tr>";
				html += "<th>" + res.data[i].odProInfo[j].product_price*res.data[i].odProInfo[j].product_quantity + "원</th>";
				html += "</tr>";
			}
			
			html += "<tr>";
			html += "<th>최종 결제금액</th>";
			if (res.data[i].totalPaymentPrice!==undefined)
				html += "<th>" +  res.data[i].totalPaymentPrice + "원</th>";
			else 
				html += "<th> </th>";
			html += "</tr>";
			html += "</table>";
		}
		
		$(".table-container").html(html);
	});
};

$(".table-container").on("click", ".DeliUpdateBtn", function() {
	var orderCode = $(this).parent().parent().siblings().find("#orderCode").text().slice(3);
	
	location.href = "/order/delivery/form?orderCode=" + orderCode;
});

/*$(".DeliUpdateBtn").on("click", function() {
	var orderCode = $(this).parent().parent().siblings().find("#orderCode").text().slice(3);
	
	location.href = "/order/delivery/form?orderCode=" + orderCode;
});*/

$(".main-col-lg-9").on("click", ".cancelBtn", function() {
	var checkCancel = confirm("정말로 주문을 취소하겠습니까?");
	if (checkCancel==true) {
		var orderCode = $(this).parent().siblings("#orderCode").text().slice(3);
		
		axios.post("/order/orderCancel", {
			"orderCode" : orderCode
		}).then(function(res) {
			if (res.data.result==0) {
				alert("주문 취소 중 오류가 발생했습니다.");
			}
			else {
				alert("주문 취소가 완료되었습니다.");
				location.reload();
			}
		});
	}
	else {
		console.log("취소하지 않습니다.");
	}
});