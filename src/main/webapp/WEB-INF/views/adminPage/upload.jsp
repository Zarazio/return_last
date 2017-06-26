<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value="./resources/css/upload/uploadDrop.css"/>" />
<script src="<c:url value="./resources/js/upload/uploadDrop.js"/>" ></script>
<script>
	var map;
	
	function initialize() {
	
	  var mapOptions = { //구글 맵 옵션 설정
	      zoom : 16, //기본 확대율
	      center : new google.maps.LatLng(37.5651, 126.98955), // 지도 중앙 위치
	      scrollwheel : true, //마우스 휠로 확대 축소 사용 여부
	      mapTypeControl : true //맵 타입 컨트롤 사용 여부
	  };
	
	  map = new google.maps.Map(document.getElementById('google_map'), mapOptions); //구글 맵을 사용할 타겟
	  
	  var image = './resources/img/placeholder.png'; //마커 이미지 설정
	
	  var marker = new google.maps.Marker({ //마커 설정
	      map : map,
	      position : map.getCenter(), //마커 위치
	      icon : image //마커 이미지
	  });
	
	  google.maps.event.addDomListener(window, "resize", function() { //리사이즈에 따른 마커 위치
	      var center = map.getCenter();
	      google.maps.event.trigger(map, "resize");
	      map.setCenter(center); 
	  });
	
	}
</script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAF8iTF3JtdLLhprWyASWE8APl6RM6BGBQ&callback=initialize"></script>
<c:if test="${info == 'admin'}">
<section>
<div class="container">
	<form action="uploadForm" method="post" enctype="multipart/form-data" id="textbr" onsubmit="return false;">
		<ul>
			<li>
				<label for="place_name">장소명</label>
				<input id="place_name" name="place_name" type="text"
				size="60" maxlength="60" class="form-control" autofocus>
			<li>
				<label for="place_content">장소내용 </label>
				<textarea rows="4" id="place_content" name="place_content" class="form-control"></textarea>
			<li>
				<label for="place_address">주소</label>
				<input id="place_address" name="place_address" type="text"
				size="80" maxlength="80" class="form-control" autofocus>
			<li>
				<div id="google_map" style="width:100%; height:400px;"></div>
			<li>
				<label for="place_lat">위도</label>
				<input id="place_lat" name="place_lat" type="text"
				size="80" maxlength="80" class="form-control" autofocus>
				<label for="place_lng">경도</label>
				<input id="place_lng" name="place_lng" type="text"
`				size="80" maxlength="80" class="form-control" autofocus>
			<li>
				<label for="place_type">장소분류</label>
				<select name="place_type" class="form-control">
					<option value="맛집">맛집</option>
					<option value="숙박시설">숙박시설</option>
					<option value="실내활동">실내활동</option>
					<option value="야외활동">야외활동</option>
					<option value="쇼핑몰">쇼핑몰</option>
				</select>
			<li>
				<label for="place_on">장소설정
					<input id="place_on" name="place_on" value ="1" type="radio" checked> 활성화
					<input id="place_off" name="place_on" value ="0" type="radio"> 비활성화
				</label>
			<li>
				<h3>Select File Upload</h3>
				<input type="file" value="이미지 업로드" class="fileUplodeBtn" multiple>	
			<li>
				<h3>Drag and Drop File Upload</h3>
				<div class="fileDrop" id="check"></div>
			<li>	
				<div class="uploadedList row"></div>
			<li>
				<input type="submit" value="등록하기" id="btnsub">
		</ul>
	</form>
</div>
</section>
</c:if>
<c:if test="${info == null || info == 'user'}">
잘못된접근입니다.
</c:if>