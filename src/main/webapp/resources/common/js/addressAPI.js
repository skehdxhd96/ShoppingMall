function goPopup(){
	// 주소검색을 수행할 팝업 페이지를 호출합니다.
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
	var pop = window.open("/addressapi/popup","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
	// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
}

/*function jusoCallBack(roadFullAddr, zipNo) {
	$("input[name=delveryZipcode]").val(zipNo);
	$("input[name=shippingAddress]").val(roadFullAddr);  
}*/

function jusoCallBack(roadFullAddr, zipNo) {
	if (location.href.indexOf("/order/delivery/form")!=-1) {
		alert("배송페이지");
		$("input[name=delveryZipcode]").val(zipNo);
		$("input[name=shippingAddress]").val(roadFullAddr); 
	} else {
		$("input[name=zipcode]").val(zipNo);
		$("input[name=customerAddress]").val(roadFullAddr); 
	}
}