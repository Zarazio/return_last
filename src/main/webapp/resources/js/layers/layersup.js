

function bookMakeList(){
	
	$('#Category_select').empty();
	
}

function gifMakeList(){
	$('#Category_select').empty();
	
	var str = '<div id="form_div"><form id="form1" enctype="multipart/form-data">'
			+ '<input id="file" type="file" name="file" class="gifButton">'
			+ '<input id="makeGif" class="btn btn-default gifButton"  type="button" value="gif생성"> </form>'
			+ '<div class="uploadList"></div>'
			+ '<div class="uploadGif"></div></div>';
	
	$('#Category_select').append(str);
	
	$.ajax({
		type: "post" ,
		url : "gif_image_list",
		success : function(data){
			if(data.length > 0){
				var str="" ; 
				for(var i=0 ; i<data.length ; i++){
					console.log(data[i]);
					str += "<img src='http://211.211.213.218:8084/turn/resources/upload/logs"+data[i]+"'  onclick='input_image($(this))'>"
				}
				
				$(".uploadList").append(str);
			}
		},
		error : function(data){
			
		}
		
	})
	
}

function input_image(ddd){
	alert("ok?");
	
	console.log(ddd.attr("src"));
	
	$.ajax({
		url : "gif_list" ,
		type : "POST",
		data : {
			imagesA : ddd.attr("src")
		},
		success : function(){
			
		}
	})
	
	
	
	


	
}



$(document).on("change","#file",function(){

	alert("ok?");
	event.preventDefault();
	
	var form = $('#form1');
	var formData = new FormData();
	formData.append("file", this.files[0]);
	console.log(this.files[0]);
	$.ajax({
		url: 'layersupupload',
		data: formData,
		dataType: 'text',
		processData: false,
		contentType: false,
		type: 'POST',
		success: function(data){
			var str = "";
				
			if(checkImageType(data)){
				alert(data);
		        str = 
			     "<a href='displayGifFile?fileName="+getImageLink(data)+"' target='_blank'>"
			     + "<img src = 'displayGifFile?fileName=" + data + "'/>" //+ getImageLink(data)
			     + "</a><small data-src='" + data + "'>X</small>"
			}else {
				alert("이미지 파일만 가능합니다.");
			}
			$(".uploadList").append(str);
			$(".uploadList").on("click", "small", function(event){
				var small = $(this);
				$.ajax({
					url: "layersupdeleteFile",
					type: "delete",
					headers: {
						"Content-Type": "application/json",
						"X-HTTP-Method-Override": "DELETE"
					},
					data: {fileName: small.attr("data-src")},
					dataType: "text",
					success: function(result){	// 파일 데이터 삭제
						if(result == "deleted"){
							alert("deleted!");
							small.parent("div").remove(); // 화면에서 제거
						}
					}
				});
			});
		},error:function(){
		}
	});
	
	function checkImageType(fileName) {
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);
	}
	
	function getImageLink(fileName){	// 원본 파일 이름 찾기
		if(!checkImageType(fileName)){
			return;
	        }
		var end = fileName.substr(11);		// 파일 이름 앞의 'thumbNail_'까지를 제거
		return "/" + end;	         	// thumbNail_을 제외
	} 
});
$(document).on("click","#makeGif",function(){
	event.preventDefault();
	
	$.ajax({
		url: 'layersupmakeGif',
		data: {},
		dataType: 'text',
		type: 'POST',
		success: function(data){
			var str = "";
			
			if(checkImageType(data)){
				if($(".uploadGif").children("div").length > 0){
					alert("기존 gif를 지웁니다.");
					img.parent("div").remove();
				}
				alert(data);
				str = "<div>"
					+ "<img src = 'displayGifFile?fileName=" + data + "'/>" //+ getImageLink(data)
					+ "</div>";
			}else{ }
			$(".uploadGif").append(str);
		}
	});
	function checkImageType(fileName) {
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);
	}
});

