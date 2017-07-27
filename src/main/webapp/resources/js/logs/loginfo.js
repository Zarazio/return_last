$(document).ready(function(){
	
	$(window).scrollTop(0);// 초기화
	
	var value = 1500; // 디폴트 스크롤
	var currentPage = 1; // 현재페이지 - 변수
	var recordTimeline = 20; // 보여줄 타임라인수 - 상수
	var startPage;
	
	var clickData; // 가져온 오브젝트 리스트 카피 속도개선
	var slideIndex = 1; // defaultSlider 
	var clickTarget; // 클릭한 엘리먼트타겟
	var modifyState; 
	
	
	clickData = new Array();
	var promise = Timeline();
	promise.done(function() {
		animations();
    });
	
	
	// 해당엘리먼트 핸들링
	$(".bars").on("click",function(){
		
		if($(this).hasClass("currentive")) {
			return false;
		} else {
			
			$("#imgToggle").attr("style", "visibility:hidden");
			
			$(".bars").removeClass('currentive');
			$(this).addClass("currentive");
			$(window).scrollTop(0); // 스크롤을 초기화해준다.
			currentPage = 1; // 현재페이지로 초기화
			value = 1500 // 다시초기화해준다.
			
			$(".divTarget").remove();
			clickData = new Array();

			promise = Timeline();
			promise.done(function() {
				animations();
		    });
			
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
	
	// 타임라인 리스트박스 출력
	function Timeline() {
		var deferred = $.Deferred(); // 순차핸들링
		
		var logType = $(".currentive").attr("data-onlog");
		startRecord = getStartRecord(currentPage,recordTimeline);
		
		setTimeout(function() {
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
						
						clickData.push(data[i]); // 배열쌓기 
						
						var onImage = ""; // 이미지 타임라인
						var onNote = ""; // 그냥타임라인
						var onVideo = ""; // 비디오타임라인 
						
						if(data[i].file_content != ""){ // 파일이 존재할때  --> 이미지 파일 && 비디오파일
							
							if(checkImageType(data[i].file_content[0])) { // 이미지일때 
								
								// 이미지 on
								onImage = "<div class='img-hover margin-bottom-30 divTarget' data-board='" + data[i].board_code + "' data-index='" + (clickData.length-1) + "'>" +
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
															"<ul class='text-left size-14 list-inline list-separator ultop targeting'>" + 
										      	              	"<li>" + 
																	"<i class='fa fa-calendar-check-o'></i><b>" + dateParse(data[i].board_date) + "</b>" + 
																"</li>" + 
																"<li>" + 
																	"<b>" + checkLogType(data[i].board_type_code) + "</b>" + 
																"</li>";
																if(data[i].my_like == 1) {
																	onImage += "<li>" + 
																				   "<i class='fa fa-heart'></i><b>" + data[i].like_count + "</b>" +
																				"</li>";
																} else {
																	onImage += "<li>" + 
																	  			   "<i class='fa fa-heart-o'></i><b>" + data[i].like_count + "</b>" +
																				"</li>";
																}
								onImage +=			        "</ul>" + 
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
								onVideo = "<div class='img-hover margin-bottom-30 divTarget' data-board='" + data[i].board_code + "' data-index='" + (clickData.length-1) + "'>" +
												"<div class='timeline'>" +
													"<div class='videoTag'>" +
													"<img width='100%' height='250' src=" + webappType(data[i].write_type, data[i].file_content[0]) + " style='border-top-left-radius:3px; border-top-right-radius:3px;'>" + 
													"</div>" + 
													"<div class='padding-10'>" + 
														"<div>" + 
												 			"<a><img class='thumbnail pull-left' src='displayProfile?fileName=" + data[i].user_profile + "' style='width:50px; height:50px;'></a>" + 
												 			"<h4 class='padding-10'>" + data[i].user_id + "</h4>" + 
														"</div>" + 
														"<div class='resource' style='clear:both;'>";
								onVideo +=					"<h4 class='text-left'>" + data[i].board_title + "</h4>";
															if(data[i].file_content[0].match("●")) {
																
															} else {
																onVideo += "<p class='text-left'> " + data[i].board_content + "</p>";
															}
								onVideo +=							"<ul class='list-inline nomargin hashTagList'>";
															for(var j=0; j<data[i].hash_tag_content.length; j++) {
																onVideo += "<li class='hashStyle'><a>" + data[i].hash_tag_content[j] + "</a></li>";
															} 
								onVideo +=					"</ul>" + 
															"<ul class='text-left size-14 list-inline list-separator ultop targeting'>" + 
										      	              	"<li>" + 
																	"<i class='fa fa-calendar-check-o'></i><b>" + dateParse(data[i].board_date) + "</b>" + 
																"</li>" + 
																"<li>" + 
																	"<b>" + checkLogType(data[i].board_type_code) + "</b>" + 
																"</li>"; 
																if(data[i].my_like == 1) {
																	onVideo += "<li>" + 
																				   "<i class='fa fa-heart'></i><b>" + data[i].like_count + "</b>" +
																				"</li>";
																} else {
																	onVideo += "<li>" + 
																	  			   "<i class='fa fa-heart-o'></i><b>" + data[i].like_count + "</b>" + 
																				"</li>";
																}
								onVideo +=				    "</ul>" + 
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
							onNote = "<div class='img-hover margin-bottom-30 divTarget' data-board='" + data[i].board_code + "' data-index='" + (clickData.length-1) + "'>" +
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
														"<ul class='text-left size-14 list-inline list-separator ultop targeting'>" + 
									      	              	"<li>" + 
																"<i class='fa fa-calendar-check-o'></i><b>" + dateParse(data[i].board_date) + "</b>" + 
															"</li>" + 
															"<li>" + 
																"<b>" + checkLogType(data[i].board_type_code) + "</b>" + 
															"</li>";
															if(data[i].my_like == 1) {
																onNote += "<li>" + 
																			   "<i class='fa fa-heart'></i><b>" + data[i].like_count + "</b>" + 
																			"</li>";
															} else {
																onNote += "<li>" + 
																  			   "<i class='fa fa-heart-o'></i><b>" + data[i].like_count + "</b>" + 
																			"</li>";
															}
							onNote +=		             "</ul>" + 
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
					alert("에러");
				}
			});
		
		deferred.resolve(); // 모든결과가 완료되었을시 1 
		}, 0);
		
		return deferred.promise(); // 모든결과가 완료되었을시 2 리턴
	}
	
	
	/** ============================ 타임라인 LightBox ============================ **/
	
	function openInfo(board_code, index) {
		
		$(".lightBox-content").empty(); // 내용비우기 
		
		var no = 1;
		
		if(clickData[index].board_code == board_code) {
			if(clickData[index].states == 0) {
				no = 0
				clickData[index].states = 1
			} else {
				no = 1
			}
		}

		
		$.ajax({ 
			type : 'GET',
			url : 'viewCount',
			data : {
				no : board_code,
				state : no
			},
			success : function(view) {
				infoOn(view, board_code, index); // 루프 호출
			}, error:function(){
				alert("오류A");
			}
		 })
		
		// 댓글불러오기  loopOn(function)
		 
		function infoOn(view, board_code, index) {
			
			$.ajax({ 
				type : 'GET',
				url : 'replyAll',
				data : {
					no : board_code
				},
				success : function(reply) {
					
					console.log(reply);
					
					// 전역배열데이터 clickData
					// 세션체크유저 checkUser
					var checkUser = $("#users").attr("data-check");
						
						if(clickData[index].board_code == board_code) {

							var lightbox = "";
							
							lightbox = "<div class='lightA inline-block'>" + 
											"<div class='light-1'>";
												
												for(var j=0; j<clickData[index].file_content.length; j++) {
													if(clickData[index].file_content[j] == "") {
														lightbox +=	 "<div style='width:100%; height:500px; background-color:#262626; border:1px solid black;'>" +
								      										"<h2 style='text-align:center; padding:230px 0px 0px 0px; color:white;'>이미지가 없습니다.<h2>" + 
								      								 "</div>";
													} else if(!(clickData[index].file_content[j] == "") && clickData[index].file_content.length == 1) {	
														
														lightbox +=	 "<div class='mySlides'>" +
												      					"<img src='" + webappTypeB(clickData[index].write_type, clickData[index].file_content[j]) + "' style='width:100%; height:500px;'>" +
												      				 "</div>";
													} else {
														
														lightbox +=	"<a class='prev'>&#10094;</a>" +
								    								"<a class='next'>&#10095;</a>" + 
																	"<div class='mySlides'>" +
												      					"<div class='numbertext'>" + (j+1) + " / " + clickData[index].file_content.length + "</div>" + 
												      					"<img src='" + webappTypeB(clickData[index].write_type, clickData[index].file_content[j]) + "' style='width:100%; height:500px;'>" +
												      				"</div>";
													}
												}
											
							lightbox +=		"</div>" + 
									    	"<div class='light-2 text-overset'>" +
									    		"<ul class='list-inline nomargin hashTagList'>";
													
													for(var j=0; j<clickData[index].hash_tag_content.length; j++) {
														lightbox += "<li class='hashStyle2'><a>" + clickData[index].hash_tag_content[j] + "</a></li>"
													}
									    			
							lightbox +=			 "</ul>" +
									    	"</div>" +
						    			"</div>" +
					    
									    "<div class='lightB inline-block resizeinfo'>" + 
									    	"<div class='nomargin sky-form boxed restyle'>" + 
									    		"<header class='size-18' style='background:rgba(0, 0, 0, 0.1) !important;'>" +
									    			"<b>" + clickData[index].board_title + "</b>" +
									    		"</header>" +
									    		"<fieldset class='nomargin light-content text-overset'>" +
									    			"<div>" + clickData[index].board_content +"</div>" +
									    		"</fieldset>" +
									    		"<fieldset class='nomargin light-user size-16'>" +
									    			"<div class='inline-block'>" +
									    				"<img src='displayProfile?fileName=" + clickData[index].user_profile + "' style='width:40px; height:40px;'>&nbsp; &nbsp;" +
									    				"<b>" + clickData[index].user_id +"</b>&nbsp; &nbsp;" +
									    			"</div>" +
									    			"<div class='light-day inline-block' data-target=" + clickData[index].board_code + " data-idx=" + index + ">";
													if(clickData[index].my_like == 1) {
														lightbox +=	"<a class='log-like'><i class='fa fa-heart'></i><b>" + clickData[index].like_count + "</b></a>";
													} else {
														lightbox +=	"<a class='log-like'><i class='fa fa-heart-o'></i><b>" + clickData[index].like_count + "</b></a>";
													}
							lightbox +=		    		"<i class='fa fa-eye'></i><b>" + view + "&nbsp;</b>" + 
									    				"<i class='fa fa-calendar-check-o'></i><b>" + dateParse(clickData[index].board_date) +"</b>" +
									    			"</div>" + 
									    		"</fieldset>" + 
									    		"<fieldset class='nomargin light-reply text-overset'>" +
										    		"<div class='form-group apply-all'>" +
														"<div class='replyText'>댓글 " + reply.length + " 개</div>" +
														"<div class='userComments'>";
														if(reply.length == 0) {
															
														} else {
															for(var a=0; a<reply.length; a++) {
																if(reply[a].reply_state == 1) {
																	lightbox +=	"<div>" + 
																					"<span class='profile-img'>" + 
																						"<img src='displayProfile?fileName=" + reply[a].user_profile + "' style='width:30px; height:30px;'>" +
																					"</span>" +
																					"<div class='user-reply-info'>" + 
																						"<div class='reply-a'>" + 
																							"<button class='btn btn-brown btn-xs margin-right-3 replyDelete' data-reply-code='" + reply[a].board_code + "' data-target-board='" + reply[a].reply_code + "'>삭제</button>" + 
																							"<button class='btn btn-purple btn-xs margin-right-6 margin-left-0 replyModify' data-reply-code='" + reply[a].board_code + "' data-target-board='" + reply[a].reply_code + "'>수정</button>" +
																							"<b>" + dateParse(reply[a].board_date) + "</b>" +
																						"</div>" +
																						"<div class='reply-b'><b>" + reply[a].user_id + "</b></div>" +
																						"<div class='reply-c margin-top-6'>" + reply[a].board_content + "</div>" +
																					"</div>" +
																				"</div>";
																} else {
																	lightbox +=	"<div>" +
																					"<span class='profile-img'>" + 
																						"<img src='displayProfile?fileName=" + reply[a].user_profile + "' style='width:30px; height:30px;'>" +
																					"</span>" + 
																					"<div class='user-reply-info'>" + 
																						"<div class='reply-a'><b>" + dateParse(reply[a].board_date) + "</b></div>" +
																						"<div class='reply-b'><b>" + reply[a].user_id + "</b></div>" + 
																						"<div class='reply-c margin-top-6'>" + reply[a].board_content + "</div>" +
																					"</div>" +
																				"</div>";
																}
																
															}
														}
														if(checkUser != "") {
															lightbox +=	"<textarea class='commentReply' maxlength='5000' rows='2' required></textarea>" +
																		"<button class='replyWrite margin-top-3 btn btn-3d btn-reveal btn-black pull-right'>" + 
																			"<i class='fa fa-plus'></i>" +
																			"<span>댓글 쓰기</span>" +
																		"</button>" + 
																		"<div data-reply='" + board_code +"'></div>";
														} else {
															lightbox += "<div>";
																			if(reply.length == 0) {
																				lightbox += "<div class='loginAccess text-center'>" +
																								"<b>로그인후 이용하실수 있습니다.</b>" +
																								"<a href=''>로그인하기</a>"
																							"</div>";
																			} else {
																				lightbox += "<div class='loginAccess text-center' style='margin-top:25px;'>" +
																								"<b>로그인후 이용하실수 있습니다.</b>" +
																								"<a href=''>로그인하기</a>"
																							"</div>";
																			}
															lightbox += "</div>";
														}
							lightbox +=					"</div>" +
													"</div>" +
									    		"</fieldset>" +
									    	"</div>" +
										"</div>";
							
							$(".lightBox-content").append(lightbox);
							
						}

					
					$(".text-overset p img").remove();
					
					showSlides(slideIndex);
					
				}, error:function(){
					alert("오류B");
				}
			 })
			
		}
		
	}
	
	// 댓글 작성
	$(".lightBox-content").on("click",".replyWrite",function(){
		
		var textarea = $(this).prev().val(); // 작성내용
		var no = $(this).next().attr("data-reply"); // 글번호
		
		replyCommend(textarea, no, 1, 0);
		
	});
	
	// 댓글 삭제 클릭
	$(".lightBox-content").on("click",".replyDelete",function(){
		
		var replyno = $(this).attr("data-reply-code");
		var no = $(this).attr("data-target-board");
		
		if(confirm("댓글을 삭제하시겠습니까?")) {
			replyCommend("Delete!", no, 3, replyno);
		}
		
	});
	
	// 댓글 수정클릭
	$(".lightBox-content").on("click",".replyModify",function(){
		
		var buttonName = $(this).text();
		
		if(buttonName == "수정") {
			var replyno = $(this).attr("data-reply-code");
			var no = $(this).attr("data-target-board");
			var textarea = $(this).parent().next().next();
			var text = textarea.text();
			textarea.empty();
			var areaElement = "<textarea class='commentReply' maxlength='5000' rows='2' required>" + text + " </textarea>" +
							  "<button class='replyConfirm margin-top-3 btn btn-3d btn-reveal btn-black pull-right' data-reply-code=" + replyno + " data-target-board='" + no + "'>" + 
							  	  "<i class='fa fa-plus'></i>" +
							  	  "<span>수정하기</span>" +
							  "</button>";
			textarea.append(areaElement);
			$(this).text("수정취소");
			
		} else {
			
			var textarea = $(this).parent().next().next();
			var text = textarea.children("textarea").text();
			
			textarea.empty();
			textarea.text(text);
			$(this).text("수정");
				
		}
		
	});
	
	// 댓글 수정하기 클릭
	$(".lightBox-content").on("click",".replyConfirm",function(){
		var no = $(this).attr("data-target-board");	
		var replyno = $(this).attr("data-reply-code");
		var textarea = $(this).prev().val();

		replyCommend(textarea, no, 2, replyno);

	});
	
	function replyCommend(textarea, no, replytype, replyno) {
		
		if(textarea == "") {
			alert("내용을 입력해주십시오.");
			return false;
		}
		
		$.ajax({ 
			type : 'POST',
			url : 'replyGo',
			data : {
				no : no,
				replytype : replytype,
				replyno : replyno,
				text : textarea
			},
			success : function(reply) {
				
				console.log(reply);
				$(".replyText").text("댓글 " + reply.length + " 개");
				$(".userComments").empty();
				var checkUser = $("#users").attr("data-check");
				
				var replybox = "";
				
					
				if(reply.length == 0) {
					
				} else {
					for(var i=0; i<reply.length; i++) {
						if(reply[i].reply_state == 1) {
							replybox +=	"<div>" + 
											"<span class='profile-img'>" + 
												"<img src='displayProfile?fileName=" + reply[i].user_profile + "' style='width:30px; height:30px;'>" +
											"</span>" +
											"<div class='user-reply-info'>" + 
												"<div class='reply-a'>" + 
													"<button class='btn btn-brown btn-xs margin-right-3 replyDelete' data-reply-code='" + reply[i].board_code + "' data-target-board='" + reply[i].reply_code + "'>삭제</button>" + 
													"<button class='btn btn-purple btn-xs margin-right-6 margin-left-0 replyModify' data-reply-code='" + reply[i].board_code + "' data-target-board='" + reply[i].reply_code + "'>수정</button>" +
													"<b>" + dateParse(reply[i].board_date) + "</b>" +
												"</div>" +
												"<div class='reply-b'><b>" + reply[i].user_id + "</b></div>" +
												"<div class='reply-c margin-top-6'>" + reply[i].board_content + "</div>" +
											"</div>" +
										"</div>";
						} else {
							replybox +=	"<div>" +
											"<span class='profile-img'>" + 
												"<img src='displayProfile?fileName=" + reply[i].user_profile + "' style='width:30px; height:30px;'>" +
											"</span>" + 
											"<div class='user-reply-info'>" + 
												"<div class='reply-a'><b>" + dateParse(reply[i].board_date) + "</b></div>" +
												"<div class='reply-b'><b>" + reply[i].user_id + "</b></div>" + 
												"<div class='reply-c margin-top-6'>" + reply[i].board_content + "</div>" +
											"</div>" +
										"</div>";
						}
						
					}
				}
				
				if(checkUser != "") {
					replybox +=	"<textarea class='commentReply' maxlength='5000' rows='2' required></textarea>" +
								"<button class='replyWrite margin-top-3 btn btn-3d btn-reveal btn-black pull-right'>" + 
									"<i class='fa fa-plus'></i>" +
									"<span>댓글 쓰기</span>" +
								"</button>" + 
								"<div data-reply='" + no +"'></div>";
				} else {
					replybox += "<div>";
									if(reply.length == 0) {
										replybox += "<div class='loginAccess text-center'>" +
														"<b>로그인후 이용하실수 있습니다.</b>" +
														"<a href=''>로그인하기</a>"
													"</div>";
									} else {
										replybox += "<div class='loginAccess text-center' style='margin-top:25px;'>" +
														"<b>로그인후 이용하실수 있습니다.</b>" +
														"<a href=''>로그인하기</a>"
													"</div>";
									}
					replybox += "</div>";
	
				}
				
				$(".userComments").append(replybox);
				
			}, error:function(){
				alert("에러");
			}
		})
		
	}
	
	
	
	
	// 타겟 좋아요 클릭
	$(".lightBox-content").on("click",".log-like",function(){
	
		var checkUser = $("#users").attr("data-check"); // 체크유저
		
		if(checkUser == "") {
			alert("로그인후 이용해주십시오.");
			return false;
		}

		var thisData = $(this).parent("div").attr("data-target"); // board값 
		var index = $(this).parent("div").attr("data-idx"); // index값
		
		var clickTimeline = $(this);
		var whylike = $(this).children("i").prop("class");
		var idx = whylike.indexOf(" ") + 1;
		whylike = whylike.substr(idx);
			
			if(clickData[index].board_code == thisData) {
				
				var states = 1 // 현재상태
				var count = index; // 현재인덱스 번호
				
				if(whylike == 'fa-heart') {
					states = 0;
				} 
				
				if(whylike == 'fa-heart-o') {
					states = 1;
				}
				
				$.ajax({
					type : 'GET',
					url : 'likeConfirm',
					data : { 
						states : states,
						no : thisData
					},
					success : function(data) {
						
						if(whylike == 'fa-heart') {
							// 좋아요 취소
							clickData[count].like_count = data;
							clickData[count].my_like = 0
							clickTimeline.html("<i class='fa fa-heart-o'></i><b>" + (data) + "</b>");
							clickTarget.html("<i class='fa fa-heart-o'></i><b>" + (data) + "</b>");
						} 
						
						if(whylike == 'fa-heart-o') {
							// 좋아요 구독
							clickData[count].like_count = data;
							clickData[count].my_like = 1;
							clickTimeline.html("<i class='fa fa-heart'></i><b>" + (data) + "</b>");
							clickTarget.html("<i class='fa fa-heart'></i><b>" + (data) + "</b>");
						}
						
					}, error:function(){ }
					
				});
				
			}
		
		
	});
	
	// 타겟 타임라인 클릭
	$("#allCell").on("click",".divTarget",function(){
		
		clickTarget = $(this).children("div")
							 .children("div")
							 .children(".resource")
							 .children(".targeting")
							 .children("li").eq(2); // 저장된 타겟정보
		
		var board_code = $(this).attr("data-board");
		var index = $(this).attr("data-index");
		
		slideIndex = 1;
		openInfo(board_code, index); // 정보열기
		
		openLightBox();
	});
	
	// 타임라인 x표시 클릭 
	$(".lightClose").click(function(){
		
		closeLightBox();
		
	});
	
	// 컨텐츠 클릭부분
	$(document).on("click",function(e){
		if(!$(e.target).has(".lightBox-content").length) {
			
		} else {
			
			closeBackground();
			
		}
	});
	
	/***** slide handle *****/
	
	$(".lightBox-content").on("click",".prev",function(){
		plusSlides(-1);
	});
	
	$(".lightBox-content").on("click",".next",function(){
		plusSlides(1);
	});
	
	function plusSlides(n) {
		  showSlides(slideIndex += n);
	}

	function currentSlide(n) {
		  showSlides(slideIndex = n);
	}

	function showSlides(n) {
		  var i;		  
		  var slides = $(".mySlides");
		  
		  console.log(slides);
		  
		  if (n > slides.length) {slideIndex = 1}
		  if (n < 1) {slideIndex = slides.length}
		  for (i = 0; i < slides.length; i++) {
		    $(slides[i]).css("display","none");
		  }
		  $(slides[slideIndex-1]).css("display","block");
	}
	
	/** ============================ 타임라인 LightBox ============================ **/
	
	
});

// 웹 앱 업로드타입 (타임라인 리스트)
function webappType(dataA, dataB) {
	if(dataA == '1') {
		dataB = "http://211.211.213.218:8084/turn/resources/upload/logs" + dataB; // 모바일 이미지 출력 
	} else if (dataB.match("http")){
		dataB = dataB;
		
	} else if (dataB.match("●")){
		
		var arr = [];
		var err = [];
		arr = dataB.split("●");

		var google = "http://maps.googleapis.com/maps/api/staticmap?size=300x300&scale=2&path=color:0x0000ff|weight:5|";
		
		for(var i=0; i<arr.length; i++) {
			err = arr[i].split(",");
			if(err[0] == "") {
				google += "";
			} else {
				
				google += err[1] + "," + err[0];
				
				if(i != (arr.length-2)) {
					google += "|";
				} else {
					google += "&key=AIzaSyAF8iTF3JtdLLhprWyASWE8APl6RM6BGBQ";
				}
				
			}
		}
		
//		alert(google);
		
		dataB = google;
		
	} else {
		dataB = "displayLogs?fileName=" + dataB; // 이미지 출력 
	}
	return dataB;
}

// 웹 앱 원본 업로드 타입 (타임라인상세보기)
function webappTypeB(dataA, dataB) { // 클릭해서 볼때
	
	var idx = dataB.indexOf("_") + 1;
	var dataB = dataB.substr(idx);
	
	if(dataA == '1') {
		dataB = "http://211.211.213.218:8084/turn/resources/upload/logs" + dataB; // 모바일 이미지 출력 
	} else if (dataB.match("http")){
		dataB = dataB;
	} else {
		dataB = "displayLogs?fileName=/" + dataB; // 이미지 출력
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

function animations() {

	$("#allCell").animate({
		opacity: 1
	}, 2000);
	
	setTimeout(function() {
		$("#imgToggle").attr("style", "visibility:visible");
	}, 1500);
	
}


/***************** Lightbox 부분 *******************/

function openLightBox() {
	$(".lightBox").fadeIn();
	$(".lightBox").css("display","block");
	$(".menu-vertical").parent().css("overflow","hidden");
	$(".menu-vertical").parent().css("padding-right","17px");
}

function closeLightBox() {
	$(".lightBox").fadeOut();
	$(".lightBox").css("display","none");
	$(".menu-vertical").parent().removeAttr("style");
}

function closeBackground() {
	$(".lightBox").fadeOut();
	$(".lightBox").css("display","none");
	$(".menu-vertical").parent().removeAttr("style");
}


