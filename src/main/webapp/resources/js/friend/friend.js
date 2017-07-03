$(document).ready(function(){
	
	var my_id = $(".friendList").attr("data-my");
	friendList();
	
	
	$(".searchon").click(function(){
		
		var input = $("#keyons").val();

		$.ajax({
			 url: "./friendSearch",
			 type: "POST",
			 data: {search:input},
			 success: function(data){
				 
				 $(".searchlist").empty();
				 
				 if(data.length == 0) {
					 alert("검색결과가 존재하지않습니다.");
				 }
				 
				 var searchData = ""
				 for(var i=0; i<data.length; i++) {
					 
					 searchData = "<tr>" + 
										"<td width='6%' class='text-center' ><img src='displayProfile?fileName=" + data[i].user_profile + "' style='width:45px; height:45px;'></td>" + 
										"<td width='40%' class='verticals'>" + data[i].user_id + "</td>" + 
										"<td class='verticals'><button class='req btn btn-blue btn-3d btn-xs' data-user='" + data[i].user_id +"'>친구요청</button></td>" + 
								  "</tr>";
						 
					 $(".searchlist").append(searchData);
						 
				 }
				 
			 }, error: function(){
				 alert("검색결과가 존재하지않습니다."); 
				 // error null 처리
			 }
		}); 
		
	});
	
	// 친구요청
	$(".searchlist").on("click",".req",function(){
		
		$.ajax({
			 url: "./friendReq",
			 type: "POST",
			 data: {
				 user_id:$(this).attr("data-user"),
				 my_id:my_id
			 },
			 success: function(data){
				 alert("완료되었습니다.");
			 }
		});
		
		friendList();
	});
	
	// 요청취소
	$(".friendList").on("click",".cancel",function(){
		
		$.ajax({
			 url: "./friendCancel",
			 type: "POST",
			 data: {
				 user_id:$(this).attr("data-c"),
				 my_id:my_id
			 },
			 success: function(data){
				 alert("완료되었습니다.");
			 }
		});
		
		friendList();
	});
	
	// 친구삭제 
	$(".friendList").on("click",".delete",function(){
		$.ajax({
			 url: "./friendDel",
			 type: "POST",
			 data: {
				 user_id:$(this).attr("data-d"),
				 my_id:my_id
			 },
			 success: function(data){
				 alert("완료되었습니다.");
			 }
		});
		
		friendList();
	});
	
	// 요청수락
	$(".friendList").on("click",".accept",function(){
		$.ajax({
			 url: "./friendAccept",
			 type: "POST",
			 data: {},
			 success: function(data){
				 alert("완료되었습니다.");
			 }
		});
		
		friendList();
	});
	
	
	
	
	
});

function friendList() {
	$(".friendList").empty();
	
	var myId = $(".friendList").attr("data-my");
	
	$.ajax({
		 url: "./friendList",
		 type: "GET",
		 data: {},
		 success: function(data) {
			console.log(data);
			var friendList = "";
			for(var i=0; i<data.length; i++) {
				
				if(data[i].friend_accept == 0 && data[i].user_id == myId) {
					friendList = "<tr>" + 
									"<td><img src='displayProfile?fileName=" + data[i].user_profile + "' style='width:45px; height:45px;'></td>" +
									"<td class='verticals'>" + data[i].friend_id + "</td>" +
									"<td class='verticals'>친구대기</td>" +
									"<td class='verticals'><button class='btn btn-blue btn-3d btn-xs cancel' data-c=" + data[i].friend_id +">요청취소</button></td>" +
								 "</tr>";
					$(".friendList").append(friendList);
				} else if(data[i].friend_accept == 1 && data[i].user_id == myId) {
					friendList = "<tr>" + 
									"<td><img src='displayProfile?fileName=" + data[i].user_profile + "' style='width:45px; height:45px;'></td>" + 
									"<td class='verticals'>" + data[i].friend_id +"</td>" +
									"<td class='verticals'>친구</td>" +
									"<td class='verticals'><button class='btn btn-red btn-3d btn-xs delete' data-d=" + data[i].friend_id +">친구삭제</button></td>" + 
								 "</tr>";
					$(".friendList").append(friendList);
				} else if(myId == data[i].friend_id) {
					friendList = "<tr>" + 
									"<td><img src='displayProfile?fileName=" + data[i].user_profile + "' style='width:45px; height:45px;'></td>" +
									"<td class='verticals'>" + data[i].user_id + "</td>" +
									"<td class='verticals'>친구요청</td>" +
									"<td class='verticals'><button class='btn btn-green btn-3d btn-xs accept' data-a=" + data[i].user_id +">요청수락</button></td>" +
								 "</tr>";
					$(".friendList").append(friendList);
				} 
				
			}
			 
		 }, error: function(){
			 alert("에러");
		 }
	}); 
	
}