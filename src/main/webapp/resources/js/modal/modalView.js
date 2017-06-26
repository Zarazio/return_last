$(document).ready(function(){
	
	var count = 0;
	
	// 일정등록버튼클릭했을때 
	$("#modals").click(function(){
		
		$(".turn-modal").css("display","none");
	});
	
	// 친구목록의 그룹창을 닫았을시
	$(".closeGroup").click(function(){
		
		$(".turn-modal").css("display","none");
		count = 0;
	});
	
	// 그룹추가 버튼을 클릭했을시
	$("#addGroup").click(function(){
		if(count == 0) {
			$(".turn-modal").css("display","block");
			$(".friend-list").empty();
			
			friend_list();
			count++;
			
		} else {
			$(".turn-modal").css("display","none");
			count = 0;
		}
	});
	
	// 모달버튼이 사라질때 이벤트 발생함수
	$('#myModal').on('hidden.bs.modal', function (e) {
		  count = 0;
	});
	
	// =========== 페이지 이동 함수 =============== // 
	
	$(".nextPage").on("click",function(){
		
		if(confirm("다음으로 넘어가시겠습니까?")) {
			$('#submitData').attr('method','post');
			$('#submitData').attr('action','scheduleSet');
			$('#submitData')[0].submit();
		}
	});
	
	
});


//친구 리스트를 띄움
function friend_list(){
   
	$.ajax({
		url : "friend_list" ,
		type : "POST" ,
		success : function(data){
			var content = "<div>" ;
			for(var i=0 ; i<data.length ; i++) {
				content += "<div class='friendList' data-userId='"+data[i].user_id +"' data-check='0' onClick='select(this)' style='width : 100% ;height : 62px; border:1px solid black; margin-bottom : 8px; padding : 5px; cursor: pointer;'>" 
						+ "<div class='friendProfile' style='float:left; width:65px; height:50px; border:1px solid black; line-height : 50px'>" + data[i].user_profile + "</div>"
						+ "<p style='line-height : 50px'>"+ data[i].user_id +"</p>"
						+"</div>";
            
            
			}
			content += "</div>"
            console.log("content : "+content)
         
            $(".friend-list").append(content);
		},
		error : function(){
          alert("에러납니까?");
		}
   })
}

// 친구목록에서 같이 여행할 친구 추가! 할때!
// data-check를 통해서 1이면 추가, 0이면 제외 => 1인 친구들을 다 array에 넣어줌.
function select(user_Id){

	if($(user_Id).attr("data-check")==0){
         
		$(user_Id).attr("data-check",1);
		$(user_Id).css("background", "#9e9e9e");
		$(user_Id).css("color","white");
      
		friend_inputCheck($(user_Id).attr("data-userId"));
       
         
	} else if($(user_Id).attr("data-check") == 1){
		$(user_Id).attr("data-check",0);
		$(user_Id).css("background", "") ;
		$(user_Id).css("color","black") ;
		var a = $(user_Id).attr("data-userId")

		$("#"+a).remove() ;
      
	}

}

function friend_inputCheck(user_Id){
   
	var submitData = $("#submitData");
	$("<input type='checkbox' id='"+user_Id+"' name='friend' value='"+user_Id+"' checked >").appendTo(submitData);
   
}