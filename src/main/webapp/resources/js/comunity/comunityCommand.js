$(document).ready(function(){
	
	// comuList.jsp
	$(".comu_info").click(function(){
		event.preventDefault();
		
		var tagetPaging = getQuerystring("page");
		
		var targetPost = $(this).attr("href");
		self.location.href = "./comuRead?page=" + tagetPaging + "&post=" + targetPost;
	});
	
	// comuList.jsp / pagination
	$(".pagination li a").on("click",function(){
		event.preventDefault();
		var tagetPaging = $(this).attr("href");
		self.location.href = "./comuList?page=" + tagetPaging;
	});
	
	// comuRead.jsp
	$(".comuDel").click(function(){
		event.preventDefault();
		if(confirm("정말로 삭제하시겠습니까?")) {
			var delNum = $(".numberPost").attr("data-src");
			self.location.href = "./comuDel?post=" + delNum;
		}
	});
	
});

// 쿼리스트링 주소데이터
function getQuerystring(paramName){ 
	
	var tempUrl = window.location.search.substring(1); //url에서 처음부터 '?'까지 삭제 
	var tempArray = tempUrl.split('&'); // '&'을 기준으로 분리하기 
	
	if(tempUrl == "") {
		return 1;
	}
	
	for(var i=0; tempArray.length; i++) { 
		var keyValuePair = tempArray[i].split('='); // '=' 을 기준으로 분리하기
		
		if(keyValuePair[0] == paramName){ // keyValuePair[0] : 파라미터 명 
			// keyValuePair[1] : 파라미터 값 
			return keyValuePair[1];
		}
	}
}
