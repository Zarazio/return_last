/**
 * 
 */
var wsocket;
var groupCode ;
var user_id ;

$(document).ready(function(){
	groupCode = $("#groupCode").text(); //schdulePageA groupCode
	user_id = $("#user_id").text();
	wsocket = new SockJS("http://localhost:8082/turn/chat.sockjs" );
		 wsocket.onopen = onOpen;
		 wsocket.onmessage = onMessage ;
		 wsocket.onclose = onClose; 
})

	 
function disconnect() {
	wsocket.close();
}
	 
function onOpen(evt) {
	 
}
		 
function onClose(evt) {
	  
}
	 
function onMessage(evt) {
	var data = evt.data;
	var afterStr = data.split(',');
	appendMessage(afterStr);
	  
}
	 
	 
function send() {
	console.log("usefd00 :" +user_id);
	var msg = $("#message").val();
	
	wsocket.send(user_id+","+msg );
	$.ajax({
			type : 'POST',
			url : 'chatStore',
			data : {
				groupCode : groupCode,
				content : msg 
			},
			success : function(){
				console.log("");	
			},
			error : function(){
				window.alert("오류가 발생하였습니다yy") ;
			}
		})
		
		$("#message").val("");
		
		  
}

function appendMessage(msg) {
	console.log(msg[0] );
	console.log(msg[1] );
		 
	var date = new Date() ;
	date = date.getHours() +"."+date.getMinutes() ;
		
	var target = $("#chat") ;
	
	//msg[0] : 사용자 id
	if(msg[0] == user_id){
		var message = $("<div></div>").addClass("self").css("clear","both") ;

		$("<div></div>").addClass("avatar user").append("<img src=''><span>"+user_id+"</span>").appendTo(message) ;
		$("<div></div>").addClass("msg").append("<p>"+msg+"</p>").append("<time>"+ date+"</time>").appendTo(message) ;
	}else{
		var message = $("<div></div>").addClass("other").css("clear","both") ;
			 
		$("<div></div>").addClass("avatar user").append("<img src=''><span>"+msg[0]+"</span>").appendTo(message) ;
		$("<div></div>").addClass("msg").append("<p>"+msg+"</p>").append("<time>"+ date+"</time>").appendTo(message) ;
	} 
		message.appendTo(target);
		setScrollDown();
		 
}
	 
function setScrollDown(){
	$(".chat").scrollTop($(".chat").height());
	 
}
	
$(document).ready(function() {
		 
	$('#message').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
			
		if(keycode == '13'){
			send(); 
		}
		
		event.stopPropagation();
	  
	});
		  
	$('#sendBtn').click(function() { send(); });
	$('#enterBtn').click(function() { connect(); });
	$('#exitBtn').click(function() { disconnect(); });
});
	
	 
// chatting 창 보이게 하기
function chattingWrap(){ 

	var chat =  $("#chattingWrap");
			
	if(chat.attr("data-check") == 0){
		$("#chat").empty() ;
		chat.css("display","block");
		chat.attr("data-check",1) ;
	}
	else if(chat.attr("data-check") == 1){
		chat.css("display","none") ;
		chat.attr("data-check",0) ;
	} 
		 
	$.ajax({
		url : "chatting",
		type : "POST",
		data : {
			groupCode : groupCode
		},
		success : function(data){
			if(data.length == 0){
				console.log("ddd");
			}else{
				console.log("dddd");
				for(var i=0; i<data.length ; i++){
					if(data[i].user_id == user_id){
						$("<div class='self'>" 
							+"<div class='avatar user'>" 
								+"<img src=''/>"
								+"<span>"+data[i].user_id +"</span>"
							+"</div>"
						  +"<div class='msg'>"
								+"<p>"+data[i].chatting_content+"</p>"
								+"<time>"+data[i].chatting_date+"</time>"
						  +"</div>"
							+"</div>").appendTo($("#chat"));
						}else{
						$("<div class='other'>" 
							+"<div class='avatar user'>" 
								+"<img src=''/>"
								+"<span>"+data[i].user_id +"</span>"
							+"</div>"
							+"<div class='msg'>"
								+"<p>"+data[i].chatting_content+"</p>"
								+"<time>"+data[i].chatting_date+"</time>"
							+"</div>"
						 +"</div>").appendTo($("#chat"));;
						}
					}
				}
				
			}
		}) 	
	 	
}