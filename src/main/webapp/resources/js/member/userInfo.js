var group_code ;
var targetparent ;
var planPlace = [] ;
var map;
var mapId ;
var markers ;

// 사용자의 여행계획 리스트를 불러주는 페이지로 이동 
function travel_plan_list(){
	window.location = "userScheduleList" ;
}

// 계획중인 리스트들을 불러줌.
function going_plan(going){
	
	var box = $("#user_group_list_box") ;
	var count = 0; 
	
	$.ajax({
		url : "userScheduleListCheck" ,
		type : "POST",
		data : {
			state : going 
		},
		dataType : "json",
		success : function(data){
			box.empty();
			if(data.length>0){
				for(var i=0; i<data.length ; i++){
					count++ ;
					$("<div class='user_group_list' data-code='"+ data[i].group_Code+"' data-name='"+ data[i].travel_Title+"' data-start='"+ data[i].start_Date+"' data-end='"+ data[i].end_Date+"' onclick='planDetail($(this))'>"
							+"<div class='plan-list-img'>" 
								+"<div class='plan-list-delete' data-toggle='modal' data-whatever='"+data[i].group_Code+"'>X</div>"
							+"</div>"
							+"<div class='plan-content'>"
								+"<div class='plan-list-title'>"+data[i].travel_Title+"</div>"
								+"<div class='plan-date'>" 
									+"<span>"+data[i].start_Date+"</span>" 
									+"<span> ~ </span>"
									+"<span>"+data[i].end_Date+"</span>"
								+"</div>"
							+"</div>" 
						+"</div>").appendTo(box) ;
				}
			}
			else{
				$("<h4 class='list-empty'>진행중인 계획이 없습니다</h4>").appendTo(box);
			}
		}
	})
}

// 완료된 일정을 불러줌
function finish_plan(finish){
	
	var box = $("#user_group_list_box") ;
	var count = 0 ;
	
	$.ajax({
		url : "userScheduleListCheck" ,
		type : "POST",
		data : {
			state : finish 
		},
		dataType : "json",
		success : function(data){
			box.empty();
			
			if(data.length > 0){
				for(var i=0; i<data.length ; i++){
					count++ ;
					$("<div class='user_group_list' data-code='"+ data[i].group_Code+"' data-name='"+ data[i].travel_Title+"' data-start='"+ data[i].start_Date+"' data-end='"+ data[i].end_Date+"' onclick='planDetail($(this))'>"
							+"<div class='plan-list-img'>" 
								+"<div class='plan-list-delete' data-toggle='modal' data-whatever='"+data[i].group_Code+"'>X</div>"
							+"</div>"
							+"<div class='plan-content'>"
								+"<div class='plan-list-title'>"+data[i].travel_Title+"</div>"
								+"<div class='plan-date'>" 
									+"<span>"+data[i].start_Date+"</span>" 
									+"<span> ~ </span>"
									+"<span>"+data[i].end_Date+"</span>"
								+"</div>"
							+"</div>" 
						+"</div>").appendTo(box) ;
				}
			}
			else{
				$("<h4 class='list-empty'>완료된 계획이 없습니다</h4>").appendTo(box);
			}
			
		}
	})
	
	
}

$(document).ready(function() {
	
	$("#user_group_list_box").on("click", " .plan-list-delete" ,function(event){
		console.log($(this));
		alert($(this));
		event.stopImmediatePropagation();
	
		$("#planlist-delete").modal();
		
		var target = $(this).attr("data-whatever");
		console.log("targe  : " + target );
		targetparent = $(this).parent().parent();
		group_code = $(this).attr("data-whatever"); 
	});
	
	mapId = document.getElementById("plan_map") ;
	map = new naver.maps.Map(mapId) ;
	
	var count = 0 ;
	planPlace = [] ;
	$(".plan-day-list").each(function(){
		planPlace[count] = new Array(2) ;
		console.log($(this).attr("data-lat"));
		planPlace[count][0] = $(this).attr("data-lat") ;
		planPlace[count][1] = $(this).attr("data-lng") ;
		
		count++ ;
		
	})
	
	placeMarker();

});

// 리스트를 삭제
function plan_confirm(){
	
	$.ajax({
		url : "plan_list_delete",
		type : "POST",
		data : {
			group  : group_code 
		}, 
		success : function(){
		
			targetparent.remove();
		}
	})
}

//상세 일정 페이지로 넘어가기
function planDetail(plan){
	console.log(plan.children().eq(1))
    var aa = plan.children().eq(0).attr("class") ;
	console.log(aa);
	console.log(event.target.className) ;
	
	// 이중이벤트 방지
	if(event.target.className != aa) return false ;
	
	var group = plan.attr("data-code");
	var title = plan.attr("data-name");
	var start = plan.attr("data-start");
	var end = plan.attr("data-end");
	window.location = "userPlanDetail?group_Code=" + group +"&&travel_Title="+title+"&&startDate="+start+"&&endDate="+end;

}

// 여행일정부분 보여주기
function travel_plan() {
	$(".plan_section").empty() ;
	
	var group_code = $("#plan-title").attr("data-code");
	
	$.ajax({
		url : "plandetailAsnyc" ,
		type : "POST" ,
		data : {
			group_code : group_code 
		},
		dataType: "json",
		success : function(data){
			console.log(data.days[0]) ;
			console.log(data.list[1].place_name);
		},
		error : function(data){
			alert("ddd");
		}
		
	})
	
	
	
}


// 마커 찍어주기 
function placeMarker(){
	if(planPlace.length == 0){
		return false ;
	}
	
	markers = [] ;
	
	 map = new naver.maps.Map(mapId , {
         center : new naver.maps.LatLng(planPlace[0][0],planPlace[0][1]) ,
         zoom : 5
      })
	
	for(var i=0 ; i<planPlace.length ; i++){
		
		var marker = new naver.maps.Marker({
			position : new naver.maps.LatLng(planPlace[i][0],planPlace[i][1]),
			map : map,
			icon : {
                content: [
                   '<div class="cs_mapbridge marker">',
                '<img src="./resources/img/map/place_marker.png">',
                '<div class="priority2">'+(i+1)+'</div>',
                '</div>'
             ].join(''),
             size: new naver.maps.Size(22, 35),
               anchor: new naver.maps.Point(25,48)
           },
             zIndex : 100 
			
		})
		
		markers.push(marker) ;
	}
	

	
	naver.maps.Event.addListener(map,'idle', function(){
		updateMarkers(map, markers) ;
	})
	
	var polyline = new naver.maps.Polyline({
        map: map,
        path: [],
        endIcon: naver.maps.PointingIcon.OPEN_ARROW,
        //아이콘의 크기를 지정합니다. 단위는 픽셀입니다.
        endIconSize: 20,
        strokeColor: '#5347AA',
        strokeWeight: 2
    });
	
	for(var i=0 ; i<planPlace.length ; i++){
        var path = polyline.getPath() ;
        
        path.push(new naver.maps.LatLng(planPlace[i][0], planPlace[i][1]));
        console.log(path);
     }
	
	
}

function updateMarkers(map , markers) {
    var mapBounds = map.getBounds() ; // 현재 지도의 좌표경계
    var marker , position; 
    
    for(var i=0 ; i < markers.length ; i++ ){
       marker = markers[i] ;
       position = marker.getPosition(); // 마커의 위치 반환
       
       if(mapBounds.hasLatLng(position)){
          showMarker(map, marker) ;             
       }else {
          hideMarker(map, marker) ;
       }
    }
 }

function showMarker(map, marker) {

    if (marker.setMap()) return;
    marker.setMap(map);
}

function hideMarker(map, marker) {

    if (!marker.setMap()) return;
    marker.setMap(null);
}




//이미지 썸네일
function thumb(data){
 console.log(data)
 var idx = data.indexOf("/") + 1 ;
 var idxA = "/s_" + data.substr(idx) ;
 
 return idxA ;
}

