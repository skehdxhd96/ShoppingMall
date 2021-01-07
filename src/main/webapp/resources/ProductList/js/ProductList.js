//페이지버튼 클릭했을 때
$(document).on("click", ".pageButton", function() {
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
			//해당페이지 상품리스트 정보들을 담은 html을 jsp에 추가하는 함수 호출
			paging(data);
		},
		error:function() {
			alert("ERROR");
		}
	});
});

//다음페이지 클릭했을 때
$(document).on("click", "#nextButton", function() {
	var startPage = Number($(".pageButton")[4].textContent)+1;
	var categoryCode = Number($(location).attr('pathname').slice(-1));
	var dataTransfer = {"startIdx":6*(startPage-1),
						"categoryCode":categoryCode};
						
	$.ajax({
		url:"/ProductList/nextButton", 
		type:"POST", 
		data:JSON.stringify(dataTransfer), 
		contentType: "application/json; charset=utf-8;",
	    dataType: "json", 
		success:function(data) {
			//해당페이지 상품리스트 정보들을 담은 html을 jsp에 추가하는 함수 호출
			paging(data.productList);
			
			//버튼 새로고침
			var buttonHtml = "";
			var totalPage = data.totalPage;
			buttonHtml += "<button id=\"prevButton\" type=\"button\" class=\"btn btn-light\">이전</button>";
			if (startPage+4>=totalPage) {
				for (var page=startPage; page<=totalPage; page++) {
					buttonHtml += "<button type=\"button\" class=\"btn btn-light pageButton\">" + page + "</button>";
				}
			}
			else {
				for (var page=startPage; page<=startPage+4; page++) {
					buttonHtml += "<button type=\"button\" class=\"btn btn-light pageButton\">" + page + "</button>";
				}
				buttonHtml += "<button id=\"nextButton\" type=\"button\" class=\"btn btn-light\">다음</button>";
			}
			
			$("#pageButtonGroup").html(buttonHtml);
		}, 
		error:function() {
			alert("ERROR!!");
		}
	});
});

//이전버튼 클릭했을 때
$(document).on("click", "#prevButton", function() {
	var lastPage = Number($(".pageButton")[0].textContent)-1;
	var categoryCode = Number($(location).attr('pathname').slice(-1));
	var dataTransfer = {"startIdx":6*(lastPage-1),
						"categoryCode":categoryCode};
						
	$.ajax({
		url:"/ProductList/paging", 
		type:"POST", 
		data:JSON.stringify(dataTransfer), 
		contentType:"application/json; charset=utf-8", 
		dataType:"json", 
		success:function(data) {
			//해당페이지 상품리스트 정보들을 담은 html을 jsp에 추가하는 함수 호출
			paging(data);
			
			//버튼 새로고침
			var buttonHtml = "";
			if (lastPage-4==1) {
				for (var page=1; page<=lastPage; page++) {
					buttonHtml += "<button type=\"button\" class=\"btn btn-light pageButton\">" + page + "</button>";
				}
			}
			else {
				buttonHtml += "<button id=\"prevButton\" type=\"button\" class=\"btn btn-light\">이전</button>";
				for (var page=lastPage-4; page<=lastPage; page++) {
					buttonHtml += "<button type=\"button\" class=\"btn btn-light pageButton\">" + page + "</button>";
				}
			}
			buttonHtml += "<button id=\"nextButton\" type=\"button\" class=\"btn btn-light\">다음</button>";
			
			$("#pageButtonGroup").html(buttonHtml);
		}, 
		error:function() {
			alert("ERROR!!");
		}
	});
});

//해당페이지 상품리스트 정보들을 담은 html을 jsp에 추가하는 함수
function paging(data) {
	var html = "";
	for (var i=0; i<data.length; i++) {
		html += "<div class=\"col-lg-4 col-md-6 mb-4\">";
		html += "<div class=\"card h-100\">";
		html += "<a href=\"#\"><img class=\"card-img-top\" src=\"http://placehold.it/700x400\" alt=\"\"></a>";
		html += "<div class=\"card-body\">";
		html += "<h4 class=\"card-title\">";
		html += "<a href=\"#\">" + data[i].product_name + "</a>";
		html += "</h4>";
		html += "<h5>" + data[i].product_price + "원</h5>";
		html += "<p class=\"card-text\">" + data[i].product_manufacturer + "</p>";
		html += "</div>";
		html += "<div class=\"card-footer\">" + data[i].product_score + "</div>";
		html += "<div>" + data[i].product_code + "</div>";
		html += "</div>";
		html += "</div>";
	}
			
	$("#productRow").html(html);
};