<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="<c:url value="./resources/js/upload/uploadRead.js"/>" ></script>
<c:if test="${info == 'admin'}">
<section>
	<div class="container">
		<form role="form">
		</form>
		<table class="table table-striped">
		<tr>
			<td>
				<label for="place_name">장소명 : </label>
				${place.place_name}
			</td>
		</tr>
		<tr>
			<td>
				<label for="place_content">장소내용 : </label>
			</td>
		</tr>
		<tr>
			<td class="textc" style="height:350px;">
				${place.place_content}
			</td>
		</tr>
		<tr>
			<td><label for="place_address">주소 : </label>
				${place.place_address}
			</td>
		</tr>
		<tr>
			<td>
				<div id="google_map" style="width:100%; height:400px;"></div>
			</td>
		</tr>
		<tr>
			<td>
				<label for="place_lat">위도 : </label>
				<span id="lat">${place.place_lat}</span>
			</td>
		</tr>
		<tr>
			<td>
				<label for="place_lng">경도 : </label>
				<span id="lng">${place.place_lng}</span>
			</td>
		</tr>
		<tr>
			<td>
				<label for="place_type">장소분류 : </label>
				${place.place_type}
			</td>
		</tr>
		<tr>
			<td>
				<label for="place_on">장소설정 : </label>
				<c:choose>
	        		<c:when test="${place.place_on == 0}">비활성화</c:when>
	        		<c:otherwise>활성화</c:otherwise>
	    		</c:choose>
	
			</td>
		</tr>	
		</table>
		
		<div class="imgOn row">
			<c:forEach items="${list}" var="list">
				<div class="box col-md-3 col-sm-3">
					<div class='design-level'>
					<img src="displayFile?fileName=${fn:replace(list.place_img,'/','/s_')}" style="width:100%; height:170px;"/>
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
						</h5>
					</div>
					<input type="hidden" class="imgDel" name="imgDel" value="${fn:replace(list.place_img,'/','/s_')}">
					</div>
				 </div>
			</c:forEach>
		</div>
		<div>
			<button type="submit" class="btn btn-warning">Modify</button>
			<button type="submit" class="btn btn-danger">Remove</button>
			<button type="submit" class="btn btn-primary">List</button>
		</div>
		<div class="pagelist">
			<input type="hidden" id="page" name="page" value="${pagination.page}">
			<input type="hidden" id="recordPage" name="recordPage" value="${pagination.recordPage}">
			<input type="hidden" class="no" name="no" value="${place.place_code}">
		</div>
		<script>
		var map;
		
		var lat = document.getElementById("lat").innerHTML;
		var lng = document.getElementById("lng").innerHTML;
		
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