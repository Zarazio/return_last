$(document).ready(function(){
	$(".memDel").click(function(){
		var choice = confirm("회원을 탈퇴하면 모든정보가 사라지게됩니다.");
		var choiceE = confirm("정말로 회원을 탈퇴하시겠습니까?");
		if(choice) {
			if(choiceE){
				$(".memDel").attr("href","myDelete");
			} else {
				
			}
		} else {
			
		}
	});
});