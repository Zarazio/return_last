/**
 * 
 */

var socket;
var groupCode ;
var user_id ;
var planDay ;

$(document).ready(function(){
	groupCode = $("#groupCode").text(); //schdulePageA groupCode
	user_id = $("#user_id").text();
	
	socket = new SockJS("/turn/echo.sockjs");
	socket.onopen = onOpen;
	socket.onmessage = onMessage2;
	socket.onclose = onClose;

})

	 
function disconnect() {
	socket.close();
}
	 
function onOpen(evt) {
	 
}
		 
function onClose(evt) {
	  
}
	 
function onMessage2(evt) {
	console.log("messager admin kakao");
	modifyScheduleList();
}
	 
	 
function esend(day) {
	
	  alert("dddsend");
	  socket.send();
	  planday = day ;
}

function modifyScheduleList(){
	console.log("admin, kakao");
    $(".selectPlace").each(function(){
        
        var selectThis = $(this) ;
        var select = $(this).attr("data-nal");
        
        if(select == planDay){
           $(this).css("display", "block");
           
           $.ajax({
              type : "POST" ,
              url :  'planDayList',
              data : {
                 group : groupCode,
                   plan : planDay
              },
              dataType : "json",
              success :function(data){    
                 
                 for(var i=0 ; i<data.length; i++){
                    $("<div data-code="+data[i].place_code+" data-pri="+ data[i].travel_priority +" data-lat="+ data[i].place_lat 
                    +" data-lng="+ data[i].place_lng +" data-name="+ data[i].place_name + " style='width:100%; padding:5px; height:160px; background : #333333; border-bottom : 1px solid #3a3c3f'></div>")
                    .addClass("planList")
                    .addClass("priority")
                    .append("<img src='displayFile?fileName="+ thumb(data[i].place_img) + "' style='width:150px ; height:150px'><span>"+data[i].place_name+"</span>")
                    .append("<div class='planPlaceDelete' data-code='"+data[i].place_code+"'><a href='#'>삭제</a></div>") 
                    .css("border-bottom","1px solid #3a3c3f").appendTo(selectThis) ;
                 }
                 
                 placeMarker();
              }
           
           })
        }
     
     });
}

	 

	

	