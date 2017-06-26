$(document).ready(function(){
	$(window).scrollTop(0);// 초기화
	var value = 1500; // 디폴트 스크롤
	var currentPage = 1; // 현재페이지 - 변수
	var recordTimeline = 20; // 보여줄 타임라인수 - 상수
	var startPage;
	
	Timeline(); // 디폴트 타임라인
	
	// 해당엘리먼트 핸들링
	$(".bars").on("click",function(){
		
		if($(this).hasClass("currentive")) {
			return false;
		} else {
			
			$(".bars").removeClass('currentive');
			$(this).addClass("currentive");
			$(window).scrollTop(0); // 스크롤을 초기화해준다.
			currentPage = 1; // 현재페이지로 초기화
			value = 1500 // 다시초기화해준다.
			$(".divful").remove();
			Timeline();
			
		}
		
	});
	
	// 스크롤 타임라인 핸들링
	$(window).scroll(function(){
		console.log($(this).scrollTop()); // 현재스크롤 위치를 반환 
		
		if($(this).scrollTop() > value) {
			currentPage++;
			value += 1500;
			Timeline();
		}
		
	});
	
	
	function Timeline() {
		var logType = $(".currentive").attr("data-onlog");
		startRecord = getStartRecord(currentPage,recordTimeline);
		
		$.ajax({
			type : 'POST',
			url : 'logList',
			data : { 
				logType : logType,
				startRecord : startRecord,
				recordTimeline : recordTimeline
			},
			success : function(data) { // @ResponseBody
				
				console.log(data);
				
				bindAppend = 0; // 바인딩셋
				
				for(var i=0; i<data.length; i++) {
					
					var onImage = ""; // 이미지 타임라인
					var onNote = ""; // 그냥타임라인
					var onVideo = ""; // 비디오타임라인 
					
					if(data[i].file_content != ""){ // 파일이 존재할때  --> 이미지 파일 && 비디오파일
						
						if(checkImageType(data[i].file_content[0])) { // 이미지일때 
							
							// 이미지 on
							onImage = "<div class='img-hover margin-bottom-30 divful'>" +
											"<div class='timeline'>" +
												"<div class='images'>" +
						 							"<img class='img-responsive' src='" + webappType(data[i].write_type, data[i].file_content[0]) + "' alt=''>" +
												"</div>" + 
												"<div class='padding-10'>" + 
													"<div>" + 
											 			"<a><img class='thumbnail pull-left' src='displayProfile?fileName=" + data[i].user_profile + "' style='width:50px; height:50px;'></a>" + 
											 			"<h4 class='padding-10'>" + data[i].user_id + "</h4>" + 
													"</div>" + 
													"<div class='resource' style='clear:both;'>" + 
														"<h4 class='text-left'>" + data[i].board_title + "</h4>" + 
														"<p class='text-left'> " + data[i].board_content + "</p>" + 
														"<ul class='list-inline nomargin hashTagList'>";
														for(var j=0; j<data[i].hash_tag_content.length; j++) {
															onImage += "<li class='hashStyle'><a>" + data[i].hash_tag_content[j] + "</a></li>";
														} 
							onImage +=					"</ul>" + 
														"<ul class='text-left size-12 list-inline list-separator ultop'>" + 
									      	              "<li>" + 
																"<i class='fa fa-calendar-check-o'></i>" + dateParse(data[i].board_date) + 
															"</li>" + 
															"<li>" + 
																"<b>" + checkLogType(data[i].board_type_code) + "</b>" + 
															"</li>" + 
										                "</ul>" + 
									                "</div>" + 
							    	            "</div>" + 
						    	          	"</div>" + 
						    	       "</div>";
							
							if(bindAppend == 0) {
								$(".columnA").append(onImage);
								bindAppend++;
							} else if (bindAppend == 1) {
								$(".columnB").append(onImage);
								bindAppend++;
							} else if (bindAppend == 2) {
								$(".columnC").append(onImage);
								bindAppend = 0;
							}
												
						} else { // 이미지가 아닌 다른파일일때
							
							// 비디오 on
							onVideo = "<div class='img-hover margin-bottom-30 divful'>" +
											"<div class='timeline'>" +
												"<div class='videoTag'>" +
												"<iframe width='100%' height='250' src='" + data[i].file_content[0] + "' style='border-top-left-radius:3px; border-top-right-radius:3px;' frameborder='0' allowfullscreen></iframe>" + 
												"</div>" + 
												"<div class='padding-10'>" + 
													"<div>" + 
											 			"<a><img class='thumbnail pull-left' src='displayProfile?fileName=" + data[i].user_profile + "' style='width:50px; height:50px;'></a>" + 
											 			"<h4 class='padding-10'>" + data[i].user_id + "</h4>" + 
													"</div>" + 
													"<div class='resource' style='clear:both;'>" + 
														"<h4 class='text-left'>" + data[i].board_title + "</h4>" + 
														"<p class='text-left'> " + data[i].board_content + "</p>" + 
														"<ul class='list-inline nomargin hashTagList'>";
														for(var j=0; j<data[i].hash_tag_content.length; j++) {
															onVideo += "<li class='hashStyle'><a>" + data[i].hash_tag_content[j] + "</a></li>";
														} 
							onVideo +=					"</ul>" + 
														"<ul class='text-left size-12 list-inline list-separator ultop'>" + 
									      	              "<li>" + 
																"<i class='fa fa-calendar-check-o'></i>" + dateParse(data[i].board_date) + 
															"</li>" + 
															"<li>" + 
																"<b>" + checkLogType(data[i].board_type_code) + "</b>" + 
															"</li>" + 
										                "</ul>" + 
									                "</div>" + 
							    	            "</div>" + 
						    	          	"</div>" + 
						    	       "</div>";
							
							if(bindAppend == 0) {
								$(".columnA").append(onVideo);
								bindAppend++;
							} else if (bindAppend == 1) {
								$(".columnB").append(onVideo);
								bindAppend++;
							} else if (bindAppend == 2) {
								$(".columnC").append(onVideo);
								bindAppend = 0;
							}
							
						}
						
					} else {
						
						// 텍스트일때
						onNote = "<div class='img-hover margin-bottom-30 divful'>" +
										"<div class='timeline'>" +
											"<div class='padding-10'>" + 
												"<div>" + 
										 			"<a><img class='thumbnail pull-left' src='displayProfile?fileName=" + data[i].user_profile + "' style='width:50px; height:50px;'></a>" + 
										 			"<h4 class='padding-10'>" + data[i].user_id + "</h4>" + 
												"</div>" + 
												"<div class='resource' style='clear:both;'>" + 
													"<h4 class='text-left'>" + data[i].board_title + "</h4>" + 
													"<p class='text-left'> " + data[i].board_content + "</p>" + 
													"<ul class='list-inline nomargin hashTagList'>";
													for(var j=0; j<data[i].hash_tag_content.length; j++) {
														onNote += "<li class='hashStyle'><a>" + data[i].hash_tag_content[j] + "</a></li>";
													} 
						onNote +=					"</ul>" + 
													"<ul class='text-left size-12 list-inline list-separator ultop'>" + 
								      	              "<li>" + 
															"<i class='fa fa-calendar-check-o'></i>" + dateParse(data[i].board_date) + 
														"</li>" + 
														"<li>" + 
															"<b>" + checkLogType(data[i].board_type_code) + "</b>" + 
														"</li>" + 
									                "</ul>" + 
								                "</div>" + 
						    	            "</div>" + 
					    	          	"</div>" + 
					    	       "</div>";
						
						if(bindAppend == 0) {
							$(".columnA").append(onNote);
							bindAppend++;
						} else if (bindAppend == 1) {
							$(".columnB").append(onNote);
							bindAppend++;
						} else if (bindAppend == 2) {
							$(".columnC").append(onNote);
							bindAppend = 0;
						}
						
					}
					
				}
	
				
				$(".resource img").remove(); // 컨텐츠 이미지 삭제
				$(".resource iframe").remove(); // 동영상 아이프레임 삭제
				
			}, error : function() {
				alert("error!!");
			}
		});
		
	}
	
	
});

// 웹 앱 업로드타입 
function webappType(dataA, dataB) {
	if(dataA == '1') {
		dataB = "http://211.211.213.218:8084/turn/resources/upload/logs/" + dataB;
	} else if (dataB.match("http")){
		dataB = dataB;
	} else {
		dataB = "displayLogs?fileName=" + dataB;
	}
	return dataB;
}

// 타임라인 페이지정보
function getStartRecord(current,timeLineNum) {
	return ((current-1)*timeLineNum);
}

//로그 타입
function checkLogType(data) {
	var logtype = "";
	
	if(data == '1') {
		logtype = "Life-Log";
	} else if(data == '2')
		logtype = "Travel-Log";
	else if(data == '3'){
		logtype = "Step-Log";
	}
	return logtype;
}

// 이미지 체크 
function checkImageType(fileName) {
	var pattern = /jpg|gif|png|jpeg/i;
	return fileName.match(pattern);
}

// 날짜 데이터 파싱 
function dateParse(data) {
	var sysdate = new Date(data);
	var year = sysdate.getFullYear();
	var month = sysdate.getMonth()+1;
	var day = sysdate.getDate();
	return year + "-" 
			+ (month < 10 ? "0" + month : month) + "-"
			+ (day < 10 ? "0" + day : day);

}