<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="<c:url value="./resources/css/upload/uploadDrop.css"/>" />
<script src="<c:url value="./resources/js/upload/uploadRead.js"/>" ></script>
<script src="<c:url value="./resources/js/upload/uploadDrop.js"/>" ></script>
<c:if test="${info == 'admin'}">
<section>
	<div class="container">
		<form method="post" enctype="multipart/form-data" id="textbr" onsubmit="return false;">
			<ul>
				<li>
					<div class="bs-example bs-example-bg-classes">
						<p class="bg-primary">글번호 : ${place.place_code}</p>
					</div>
				<li>
					<label for="place_name">장소명</label>
					<input id="place_name" name="place_name" type="text"
					size="60" maxlength="60" class="form-control" value="${place.place_name}" >
				<li>
					<label for="place_content">장소내용 </label>
					<textarea rows="4" id="place_content" name="place_content" class="form-control">${place.place_content}</textarea>
				<li>
					<label for="place_address">주소</label>
					<input id="place_address" name="place_address" type="text"
					size="80" maxlength="80" class="form-control" value="${place.place_address}" >
				<li>
					<div id="google_map" style="width:100%; height:400px;"></div>
				<li>
					<label for="place_lat">위도</label>
					<input id="place_lat" name="place_lat" type="text"
					size="80" maxlength="80" class="form-control" value="${place.place_lat}" >
					<label for="place_lng">경도</label>
					<input id="place_lng" name="place_lng" type="text"
					size="80" maxlength="80" class="form-control" value="${place.place_lng}" >
				<li>
					<label for="place_type">장소분류</label>
					<select name="place_type" class="form-control choose">
						<option value="맛집">맛집</option>
						<option value="숙박시설">숙박시설</option>
						<option value="실내활동">실내활동</option>
						<option value="야외활동">야외활동</option>
						<option value="쇼핑몰">쇼핑몰</option>
					</select>
					<input id="selectData" type="hidden" value="${place.place_type}" >
				<li>
					<label for="place_on">장소설정
					<input type="hidden" id="checkradio" value="${place.place_on}">
						<input id="place_on" name="place_on" value ="1" type="radio"> 활성화
						<input id="place_off" name="place_on" value ="0" type="radio"> 비활성화
					</label>
				<li>
					<h3>Select File Upload</h3>
					<input type="file" value="이미지 업로드" class="fileUplodeBtn" multiple>	
				<li>
					<h3>Drag and Drop File Upload</h3>
					<div class="fileDrop" id="check"></div>
				<li>
					<div class="uploadedList row">
						<c:forEach items="${list}" var="list">
							<div class="classCheck col-md-3 col-sm-3">
								<div class='design-level'>
									<a href="displayFile?fileName=${list.place_img}">
										<img src="displayFile?fileName=${fn:replace(list.place_img,'/','/s_')}" style="width:100%; height:170px;">
									</a>
									<div>
										<h5 class='tresize'>
											<c:choose>
	           									<c:when test="${fn:length(list.file_name) > 25}">
									            	<c:out value="${fn:substring(list.file_name,0,20)}"/>...
									           	</c:when>
									           	<c:otherwise>
									            	<c:out value="${list.file_name}"/>
									           	</c:otherwise> 
									        </c:choose>
											<a><i class="smal tboder fa fa-close fa-border pull-right" data-src="${fn:replace(list.place_img,'/','/s_')}"></i></a>
										</h5>
									</div>
									<input type="hidden" name="files" value="${list.place_img}">
								</div>
							</div>
						</c:forEach>
					</div>
				<li>
					<input type="submit" value="수정하기" id="btnsub">
			</ul>
		</form>
		<script>
		var map;
		
		var lat = document.getElementById("place_lat").value;
		var lng = document.getElementById("place_lng").value;
		
		function initialize() {
		
		  var mapOptions = { //구글 맵 옵션 설정
		      zoom : 16, //기본 확대율
		      center : new google.maps.LatLng(lat, lng), // 지도 중앙 위치
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
	</div>
</section>
</c:if>
<c:if test="${info == null || info == 'user'}">
잘못된접근입니다.
</c:if>
